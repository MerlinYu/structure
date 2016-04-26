package com.structure.person.fragment;

import com.structure.RetrofitApiService;
import com.structure.base.ActivityModule;
import com.structure.base.fragment.BaseFragmentPresenter;
import com.structure.person.PersonApi;

/**
 * Created by yuchao.
 */
public class PersonFragmentPresenter extends BaseFragmentPresenter<PersonFragment, ActivityModule<PersonApi>> {

  private static final String TAG = "PersonFragmentPresenter";

  public PersonFragmentPresenter(PersonFragment baseFragment) {
    super(baseFragment, RetrofitApiService.create(PersonApi.class, TAG));
  }

}
