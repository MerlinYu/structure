package com.structure.person;

import com.structure.RetrofitApiService;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;


/**
 * Created by yuchao.
 */
public class PersonPresenter extends ActivityPresenter<PersonActivity, ActivityModule<PersonApi>> {


  private final static String TAG = "PersonPresenter";

  public PersonPresenter(PersonActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(PersonApi.class, TAG));

  }
}
