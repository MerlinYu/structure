package com.structure.widget.view;

import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.base.BaseModule;
import com.structure.base.BasePresenter;

/**
 * Created by yuchao on 9/12/16.
 */
public class ViewActivityPresenter extends ActivityPresenter<ViewActivity,ActivityModule> {
  public ViewActivityPresenter(ViewActivity mDisplay, ActivityModule mModule) {
    super(mDisplay, mModule);
  }


  public void loadSurfaceView() {
    mDisplay.loadSurfaceView();

  }

  public void loadCustomerView(){
    mDisplay.loadCustomerView();
  }

}
