package com.structure.main;


import android.util.Log;
import android.util.TimeFormatException;
import android.widget.Toast;

import com.structure.RetrofitApiService;
import com.structure.api.TestAPI;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.main.data.BaseResponse;
import com.structure.main.data.KeyWords;
import com.structure.main.data.KeyWordsData;
import com.structure.main.data.TestKeyData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by yuchao.
 */
public class MainPresenter extends ActivityPresenter<MainActivity, ActivityModule<TestAPI>> {

  final static String TAG = "===tag===";

  public MainPresenter(MainActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(TestAPI.class, TAG));
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
//    mCallManager.addRetrofitCall(call, name);


  /*  call.enqueue(new Callback<TestKeyData>() {
      @Override
      public void onResponse(Call<TestKeyData> call, Response<TestKeyData> response) {
        String str = response.isSuccessful() ? "success" : "failed";
        Log.d(TAG, "  ==========" + str);

      }

      @Override
      public void onFailure(Call<TestKeyData> call, Throwable t) {

      }
    });
*/

 /*   mModule.asRetrofit().getKeyV1Words().enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String str = response.isSuccessful() ? "success" : "failed";
        Log.d(TAG, "  ======v1====" +str);
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {
        Log.d(TAG, "  ======v1====" +" error " + t.getMessage());

      }
    });
*/
/*
    mModule.asRetrofit().getKeyV2Words().enqueue(new Callback<TestKeyData>() {
      @Override
      public void onResponse(Call<TestKeyData> call, Response<TestKeyData> response) {
        String str = response.isSuccessful() ? "success" : "failed";
        Log.d(TAG, "  ======v2====" +str);
        Log.d(TAG, "  ======v2====" +response.errorBody().toString());
//        Log.d(TAG, "  ======v2====" + response.body().toString());

      }

      @Override
      public void onFailure(Call<TestKeyData> call, Throwable t) {

      }
    });
*/
  }




}
