package com.structure.main;


import com.structure.RetrofitApiService;
import com.structure.api.TestAPI;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.main.moduledata.TestKeyData;
import com.structure.main.weeatherdata.*;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by yuchao.
 */
public class MainPresenter extends ActivityPresenter<MainActivity, ActivityModule<TestAPI>> {

  final static String TAG = "===tag===";

  public MainPresenter(MainActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(TestAPI.class, TAG));
  }

  public void getTestKeyWord() {
    mModule.asRetrofit().getKeyWords().enqueue(new Callback<TestKeyData>() {
      @Override
      public void onResponse(Response<TestKeyData> response, Retrofit retrofit) {
        mDisplay.setTextView(response.body().toString());
      }

      @Override
      public void onFailure(Throwable t) {

      }
    });
  }


  public void getWeatherInfo() {
    final String OPEN_WEATHER_API = "51337ba29f38cb7a5664cda04d84f4cd";
    mModule.asRetrofit().getWeatherFromApi("china", OPEN_WEATHER_API).enqueue(new Callback<WeatherData>() {
      @Override
      public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
//        Log.d(TAG,  response.body().toString());
      }

      @Override
      public void onFailure(Throwable t) {

      }
    });

  }
}
