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
import com.structure.person.event.GenerateCardEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
  }


  @Override
  protected void onCreate() {
    super.onCreate();
    Log.v("===tag=== ", " on create presenter ");
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.v("===tag=== ", " on destroy presenter ");

    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void onEventMainThread(GenerateCardEvent event) {
    if (event.result == GenerateCardEvent.SUCCESS) {
      mDisplay.setImage(event.cardUri);
    }
  }


}
