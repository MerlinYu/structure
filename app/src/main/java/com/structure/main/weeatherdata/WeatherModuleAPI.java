package com.structure.main.weeatherdata;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by yuchao.
 */
public interface WeatherModuleAPI {


  @GET("/data/2.5/weather")
  void getWeatherFromApi(@Query("q") String cityName, @Query("APPID") String appId, Callback<WeatherData> callback);


}
