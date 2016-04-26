package com.structure.person;

import com.structure.RetrofitApiService;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.person.userdata.User;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yuchao.
 */
public class PersonPresenter extends ActivityPresenter<PersonActivity, ActivityModule<PersonApi>> {


  private final static String TAG = "PersonPresenter";

  public PersonPresenter(PersonActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(PersonApi.class, TAG));

  }

  public void getUser() {
    mModule.asRetrofit().getUserInfo("", new Callback<User>() {
      @Override
      public void success(User user, Response response) {

      }

      @Override
      public void failure(RetrofitError error) {

      }
    });

  }
}
