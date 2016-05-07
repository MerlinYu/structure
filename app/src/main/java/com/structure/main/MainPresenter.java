package com.structure.main;


import com.structure.RetrofitApiService;
import com.structure.api.TestAPI;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.main.moduledata.TestKeyData;
import com.structure.main.weeatherdata.*;

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
    mModule.asRetrofit().getKeyWords().enqueue(new Callback<TestKeyData>() {
      @Override
      public void onResponse(Call<TestKeyData> call, Response<TestKeyData> response) {

      }

      @Override
      public void onFailure(Call<TestKeyData> call, Throwable t) {

      }
    });

  }


}
