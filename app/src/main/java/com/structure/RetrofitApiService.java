package com.structure;


import android.app.Application;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;
import com.structure.base.ActivityModule;
import com.structure.internet.StructureClient;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by yuchao.
 * 网络请求使用第三方库Retrofit 请求服务器的service
 */
public final class RetrofitApiService {

  private static RestAdapter mRestAdapter;
  private static StructureClient mClient;
  private static RequestInterceptor headerInterceptor;
  private static RestAdapter.LogLevel logLevel;

  static final String WEATHER_URL = "http://api.openweathermap.org";
  public static final String OPEN_WEATHER_API = "51337ba29f38cb7a5664cda04d84f4cd";


  public static void init(Application application) {
    initRestAdapter(application);
  }

  public static <T> ActivityModule<T> create(Class<T> service, String Tag) {
    T retrofitInterface = getRestAdapter().create(service);
    return new ActivityModule<>(retrofitInterface);
  }

  public static RestAdapter getRestAdapter() {
    if (mRestAdapter == null) {
      throw new IllegalStateException(
          "Can't create api service. Did you forget to initial RetrofitApiService in Application class?");
    }
    return mRestAdapter;
  }


  private static void initRestAdapter(Application application) {
    final OkHttpClient okHttpClient = new OkHttpClient();
    okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
    okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    okHttpClient.setCache(new Cache(application.getCacheDir(), 1024L * 1024L * 30));
    okHttpClient.setConnectionPool(new ConnectionPool(5, 1000L * 60));

    mClient = new StructureClient(okHttpClient);

    mRestAdapter = new RestAdapter.Builder().setEndpoint(WEATHER_URL)
//                .setRequestInterceptor(getHeaderInterceptor())
//                .setConverter(new GsonConverter(new Gson()))
        .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setClient(mClient)
//                .setLog()
        .build();
  }


}
