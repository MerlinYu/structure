package com.structure.api;


import com.structure.main.data.BaseResponse;
import com.structure.main.data.KeyWordsData;
import com.structure.main.data.TestKeyData;
import com.structure.main.data.weather.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by yuchao.
 */
public interface TestAPI {


  @GET("http://api.openweathermap.org/data/2.5/weather")
  Call<WeatherData> getWeatherFromApi(@Query("q") String cityName, @Query("APPID") String appId);

  @GET("items/hot_keywords")
  Call<TestKeyData> getKeyWords();

  @GET("items/hot_keywords")
  Call<String> getKeyV1Words();

  @GET("http://api-test.momoso.com/9394/ios/v2/items/hot_keywords")
  Call<TestKeyData> getKeyV2Words();


  @GET("http://api-test.momoso.com/9394/ios/v1/items/hot_keywords")
  Call<BaseResponse<KeyWordsData>> getKeyV3Words();











}
