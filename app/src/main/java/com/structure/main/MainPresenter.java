package com.structure.main;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.structure.RetrofitApiService;
import com.structure.RetrofitBaseCallback;
import com.structure.api.TestAPI;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.cache.DiskCacheUtil;
import com.structure.main.data.BaseResponse;
import com.structure.main.data.KeyWordsData;
import com.structure.main.data.TestKeyData;
import com.structure.main.data.weather.WeatherData;
import com.structure.person.event.GenerateCardEvent;
import com.structure.test.material.MainActivity;
import com.structure.utils.LogUtils;
import com.structure.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static okhttp3.internal.Internal.logger;


/**
 * Created by yuchao.
 */
public class MainPresenter extends ActivityPresenter<MainActivity, ActivityModule<TestAPI>> {

  final static String TAG = "===MainPresenter=== ";
  LoadingDialog dialog;
  private static final Charset UTF8 = Charset.forName("UTF-8");

  private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10M
  private static final String CACHE_FILE = "cloudmall_cache";

  private static byte[] mByteBuffer;


  public MainPresenter(MainActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(TestAPI.class, TAG));
  }


  public void retrofitRequest() {
    mModule.asRetrofit().logOut().enqueue(new Callback<BaseResponse<BaseResponse.ResponseData>>() {
      @Override
      public void onResponse(Call<BaseResponse<BaseResponse.ResponseData>> call, Response<BaseResponse<BaseResponse.ResponseData>> response) {
        LogUtils.log("success ");
      }

      @Override
      public void onFailure(Call<BaseResponse<BaseResponse.ResponseData>> call, Throwable t) {
        t.printStackTrace();
      }
    });

  }

  public void getTestKeyWord() {

    Callback<ResponseBody> callback1 = new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        for (String heade: response.headers().names()) {
          Timber.d("===tag=== header name response : " + heade + "  :  " + response.headers().get(heade));
        }

        String responseDate = response.headers().get("Date");
        Timber.d("====tag=== responseDate" + responseDate);


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        Timber.d("====tag=== now date " + str);

//        1491634454581

        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddhhmmssSSS", Locale.getDefault());
//        Date date1 = format.parse(responseDate)
        String timeMap = "1491634454581";

        try {
//          Date date1 = format.parse(timeMap);
//          Timber.d("===tag=== time 1 " + date1.getTime());

          Date date2 = format.parse(responseDate);
          Timber.d("===tag=== time 1 " + date2.getTime());


        } catch (Exception e) {
          e.printStackTrace();
        }








        try {
          long contentLength = response.body().contentLength();


          long sTime = System.currentTimeMillis();
          BufferedSource source = response.body().source();
          source.request(Long.MAX_VALUE); // Buffer the entire body.
          Buffer buffer = source.buffer();
          Timber.d("===tag=== buffer time " + (System.currentTimeMillis() - sTime));


          Charset charset = UTF8;
          MediaType contentType = response.body().contentType();
          if (contentType != null) {
            try {
              charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
              e.printStackTrace();
            }
          }

          if (contentLength != 0) {

            String data = buffer.clone().readString(charset);

            Log.d("===tag=== 1 ", data);

            Log.d("===tag=== 2 ", buffer.readString(charset));

          }

          long streamTime = System.currentTimeMillis();
          InputStream is = response.body().byteStream();

          StringBuffer out = new StringBuffer();
          mByteBuffer = new byte[4096];
          int n;
          while ((n = is.read(mByteBuffer)) != -1) {
            out.append(new String(mByteBuffer, 0, n));
          }
          Timber.d("===tag=== buffer stream " + (System.currentTimeMillis() - streamTime));


          Timber.d("===tag==== length  " + out.length());
          Timber.d("===tag=== stream " + out.toString());

          mByteBuffer = null;

        } catch (IOException e) {
          e.printStackTrace();
        }

      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {

      }
    };

    mModule.asRetrofit().getKeyRespobseWords().enqueue(callback1);


    Callback<TestKeyData> callback = new RetrofitBaseCallback<TestKeyData>(false) {
      @Override
      public void onRequestSuccess(TestKeyData body) {


      }

      @Override
      public void onRequestFailed(String errorMsg, int code) {

      }
    };

//    mModule.asRetrofit().getKeyV2Words().enqueue(callback);


  }

  public void getShenZhenWeather() {
    String city = "ShenZhen";
    String appId = "ea574594b9d36ab688642d5fbeab847e";
    showLoadingDialog();

    String url = "http://api.openweathermap.org/data/2.5/weather?q=ShenZhen&APPID=ea574594b9d36ab688642d5fbeab847e";
    String value = DiskCacheUtil.readCache(url);

    Timber.d("===tag==== read cache " + value);


    mModule.asRetrofit().getWeatherFromApi(city, appId).enqueue(new Callback<WeatherData>() {
      @Override
      public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
        Log.v(TAG, " get shen zhen weather");
        if (response.isSuccessful()) {
          if (null != response.body()) {
            WeatherData data = response.body();
            Gson gson = new Gson();

            String baseUrl = response.raw().request().url().toString();
            Timber.d("===tag=== base url " + baseUrl);
            DiskCacheUtil.writeCache(baseUrl,
                gson.toJson(data));
            mDisplay.showWeather(data);
          }
        }
        if (!mDisplay.isDestroyed()) {
          dismissLoadingDialog();
        }
      }

      @Override
      public void onFailure(Call<WeatherData> call, Throwable t) {
        if (!mDisplay.isDestroyed()) {
          dismissLoadingDialog();
        }
      }
    });
  }


  @Override
  protected void onCreate() {
    super.onCreate();
    Log.v(TAG, " on create presenter ");
//    android.R.styleable
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onDestroy() {
    if (null != dialog) {
      if (dialog.isShowing()) {
        dialog.dismiss();
      }
      dialog = null;
    }
    super.onDestroy();
    Log.v(TAG, " on destroy presenter ");
    EventBus.getDefault().unregister(this);
  }


  @Subscribe
  public void onEventMainThread(GenerateCardEvent event) {
    if (event.result == GenerateCardEvent.SUCCESS) {
      mDisplay.setImage(event.cardUri);
    }
  }

  public void showLoadingDialog() {
    if (null == dialog) {
      dialog = new LoadingDialog(getDisplay());
    }
    dialog.show();
  }

  public void dismissLoadingDialog() {
    if (dialog.isShowing()) {
      dialog.dismiss();
    }
  }

  public void startActivity(Intent intent) {
    mDisplay.startActivity(intent);
  }


}
