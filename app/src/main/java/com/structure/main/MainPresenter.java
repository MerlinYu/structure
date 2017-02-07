package com.structure.main;


import android.content.Intent;
import android.util.Log;

import com.structure.RetrofitApiService;
import com.structure.api.TestAPI;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.main.data.BaseResponse;
import com.structure.main.data.KeyWordsData;
import com.structure.main.data.weather.WeatherData;
import com.structure.person.event.GenerateCardEvent;
import com.structure.test.material.MainActivity;
import com.structure.utils.LogUtils;
import com.structure.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by yuchao.
 */
public class MainPresenter extends ActivityPresenter<MainActivity, ActivityModule<TestAPI>> {

  final static String TAG = "===MainPresenter=== ";
  LoadingDialog dialog;


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
    mModule.asRetrofit().getKeyV3Words().enqueue(new Callback<BaseResponse<KeyWordsData>>() {
      @Override
      public void onResponse(Call<BaseResponse<KeyWordsData>> call, Response<BaseResponse<KeyWordsData>> response) {
        if (response.isSuccessful()) {

          if (null != response.body()) {
            Log.d(TAG, response.body().data.toString().toString());
            mDisplay.setTextView(response.body().toString());
            response.body().data.setResponseData(response.body());
            response.body().data.getBaseResponse();
          }
        }
      }

      @Override
      public void onFailure(Call<BaseResponse<KeyWordsData>> call, Throwable t) {

      }
    });
  }

  public void getShenZhenWeather() {
    String city = "ShenZhen";
    String appId = "ea574594b9d36ab688642d5fbeab847e";
    showLoadingDialog();
    mModule.asRetrofit().getWeatherFromApi(city,appId).enqueue(new Callback<WeatherData>() {
      @Override
      public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
        Log.v(TAG, " get shen zhen weather");
        if (response.isSuccessful()) {
          if (null != response.body()) {
            WeatherData data = response.body();
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
