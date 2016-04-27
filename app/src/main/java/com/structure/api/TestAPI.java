package com.structure.api;


import com.structure.main.moduledata.TestKeyData;
import com.structure.main.weeatherdata.WeatherData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by yuchao.
 */
public interface TestAPI {


  @GET("http://api.openweathermap.org/data/2.5/weather")
  Call<WeatherData> getWeatherFromApi(@Query("q") String cityName, @Query("APPID") String appId);

  @GET("items/hot_keywords")
  Call<TestKeyData> getKeyWords();








}
