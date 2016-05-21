package com.structure;


import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.structure.base.ActivityModule;
import com.structure.collection.HeadersCollection;
import com.structure.utils.NetworkUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by yuchao.
 * 网络请求使用第三方库Retrofit 请求服务器的service
 */
public final class RetrofitApiService {

  private static Retrofit mRestAdapter;
  private static  Map<String, String> sHeaders;

  public static void init(Application application) {
    initHeaders(application);
    initRestAdapter(application);
  }

  public static <T> ActivityModule<T> create(Class<T> service, String Tag) {
    T retrofitInterface = getRestAdapter().create(service);
    return new ActivityModule<>(retrofitInterface);
  }

  public static Retrofit getRestAdapter() {
    if (mRestAdapter == null) {
      throw new IllegalStateException(
          "Can't create api service. Did you forget to initial RetrofitApiService in Application class?");
    }
    return mRestAdapter;
  }


  private static void initRestAdapter(Application application) {

    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .cache(new Cache(application.getCacheDir(), 1024L * 1024L * 30))
        .connectionPool(new ConnectionPool())
        .addInterceptor(getHeaderInterceptor())
        .addInterceptor(getLoggingInterceptor())
        .build();

    mRestAdapter = new Retrofit.Builder().baseUrl("http://api-test.momoso.com/9394/ios/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build();
  }


  private static HttpLoggingInterceptor getLoggingInterceptor() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    switch ("body") {
      case "body":
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        break;
      case "none":
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        default:break;
    }
    return loggingInterceptor;
  }

  private static Interceptor getHeaderInterceptor() {
    Interceptor interceptor = new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        for (Map.Entry<String,String> entry : getHeaders().entrySet()) {
          builder.addHeader(entry.getKey(),entry.getValue());
        }
        Request request = builder.build();
        return chain.proceed(request);
      }
    };

    return interceptor;

  }

  private static Map<String, String> getHeaders() {
    if (null == sHeaders) {
      throw new IllegalStateException(
          "request headers is null ,please init headers first");
    }
    return sHeaders;
  }


  private static void initHeaders(Application application) {
    try {
      String packageName = application.getPackageName();
      PackageInfo info = application.getPackageManager().getPackageInfo(packageName, 0);
      String macAddr = NetworkUtils.getMacAddress(application);
      if (sHeaders == null) {
        sHeaders = new HashMap<>();
      }
      sHeaders.put(HeadersCollection.APP_VERSION, "android/" + info.versionName);
      sHeaders.put(HeadersCollection.APP_NAME, "android/" + info.packageName);
      sHeaders.put("Cookie", "remember_token=558f647c20058915ffa8544f|7a679bc4a767620c597aedfcce835226273d16a2; session=f09841fd-350f-46ad-bd0b-bcc32111ead4");
      //5d115b3a-d9c3-4110-83a0-70792037f76a
      sHeaders.put(HeadersCollection.MAC_ADDRESS, macAddr);

    } catch (PackageManager.NameNotFoundException ignore) {
      ignore.printStackTrace();
    }
  }


}
