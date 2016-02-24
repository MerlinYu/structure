package com.structure.main;

import com.structure.StructureApiService;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.main.weeatherdata.*;
import com.structure.main.weeatherdata.WeatherModuleAPI;
import com.structure.utils.DebugLog;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yuchao.
 */
public class MainPresenter extends ActivityPresenter<MainActivity, ActivityModule<com.structure.main.weeatherdata.WeatherModuleAPI>> {

    final static String TAG = "MainPresenter";
    public MainPresenter(MainActivity mDisplay) {
        super(mDisplay, StructureApiService.create(WeatherModuleAPI.class, TAG));
    }

    public void getInfo() {

        mModule.asRetrofit().getWeatherFromApi("China", StructureApiService.OPEN_WEATHER_API, new Callback<WeatherData>() {
            @Override
            public void success(WeatherData weatherData, Response response) {
                DebugLog.d("===tag=== weather success" + weatherData.toString());


            }

            @Override
            public void failure(RetrofitError error) {
                DebugLog.d("===tag=== weather failed " + error.getMessage());

            }
        });
    }




}
