package com.structure.web;

import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.person.PersonApi;

/**
 * Created by yuchao on 17/3/23.
 */

public class WebActivityPresenter extends ActivityPresenter<WebActivity, ActivityModule<PersonApi>> {
  public WebActivityPresenter(WebActivity mDisplay, ActivityModule<PersonApi> mModule) {
    super(mDisplay, mModule);
  }
}
