package com.structure;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;


/**
 * Created by yuchao on 1/3/17.
 * RetrofitBaseCallback do two main function:1. statis intent status and store local disk cache
 * DiskLueCache rule: is isLocalCache = true  and request is GET and request.body has data
 *
 */

public abstract class RetrofitBaseCallback<T> implements Callback<T> {

  private static final Charset UTF8 = Charset.forName("UTF-8");

  private static final int ERROR_CODE_UNKNOWN = -1;

  protected boolean isLocalCache = false ;


  public RetrofitBaseCallback() {

  }


  public RetrofitBaseCallback(boolean cache) {
    isLocalCache = cache;
  }


  @Override
  public void onResponse(Call<T> call, Response<T> response) {

//    Date, OkHttp-Received-Millis, OkHttp-Sent-Millis, Server
    Request request = response.raw().request();
    String url = request.url().toString();

    long startTime = Long.valueOf(request.header("OkHttp-Sent-Millis") == null ?
        "0" : request.header("OkHttp-Sent-Millis"));
    long endTime = Long.valueOf(request.header("OkHttp-Received-Millis") == null ?
        "0" : request.header("OkHttp-Received-Millis"));
    int code = response.code();

    Timber.d("===tag=== " + url);
    Timber.d("===tag=== response.body() == null " + (response.body() == null));


    if (!response.isSuccessful() || response.body() == null) {
      statisticIntentState(url, code, startTime, endTime, 0);
      onRequestFailed(response.message(), response.code());
      return;
    }

    onRequestSuccess(response.body());

    String requestMethod = response.raw().request().method();
    long dataSize = 0;

//    Retrofit retrofit = RetrofitApiService.getRestAdapter();
//    OkHttpClient client ;
//    client.interceptors()




    try {
      T data = response.body();

      long sTime = System.currentTimeMillis();
      Gson gson = new Gson();
      Timber.d("===tag==== gson " + gson.toJson(data));
      Timber.d("===tag==== parse json  " + (System.currentTimeMillis() - sTime));

      Timber.d("===tag=== body string " + response.raw().body().string());

      long contentLength = response.raw().body().contentLength();
      Timber.d("===tag=== " + contentLength);
      BufferedSource source = response.raw().body().source();
      source.request(Long.MAX_VALUE); // Buffer the entire body.
      Buffer buffer = source.buffer();
      dataSize = buffer.size();
      Timber.d("===tag==== length  " + buffer.size());

      Charset charset = UTF8;
      MediaType contentType = response.raw().body().contentType();
      if (contentType != null) {
          charset = contentType.charset(UTF8);
        }
      String responseString = buffer.clone().readString(charset);
      // parse code
//      code = BaseResponse.getMessageCode(new JSONObject(responseString));
      Timber.d("===tag==== code " + responseString);
      statisticIntentState(url, code, startTime, endTime, dataSize);
      if (requestMethod != null && requestMethod.equalsIgnoreCase("GET") && isLocalCache) {
        doLocalCache(url, responseString);
      }

    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }





  }

  @Override
  public void onFailure(Call<T> call, Throwable t) {
    Timber.e("retrofit request failed!" + t);
  }

  private void statisticIntentState(String url, int code, long startTime, long endTime, long dataSize) {
    // device id
    // ip address


  }

  private void doLocalCache(String url, String data) {


  }


  public abstract void onRequestSuccess(T body);

  public abstract void onRequestFailed(String errorMsg, int code);
}
