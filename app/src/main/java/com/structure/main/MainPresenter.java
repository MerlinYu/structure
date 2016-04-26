package com.structure.main;

import android.util.Log;

import com.structure.RetrofitApiService;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.main.weeatherdata.*;
import com.structure.main.weeatherdata.WeatherModuleAPI;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yuchao.
 */
public class MainPresenter extends ActivityPresenter<MainActivity, ActivityModule<WeatherModuleAPI>> {

  final static String TAG = "===tag===";

  public MainPresenter(MainActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(WeatherModuleAPI.class, TAG));
  }


  public void getWeatherInfo() {

    mModule.asRetrofit().getWeatherFromApi("China", RetrofitApiService.OPEN_WEATHER_API, new Callback<WeatherData>() {
      @Override
      public void success(WeatherData weatherData, Response response) {
        Log.d(TAG, weatherData.toString());
        if (!isDestroyed()) {
          mDisplay.refreshUI();
        }
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(TAG, "===tag=== weather failed " + error.getMessage());
      }
    });
  }
}
