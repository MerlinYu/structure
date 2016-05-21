package com.structure.base;


/**
 * Created by yuchao.
 */
public class ActivityPresenter<V extends ActivityDisplay, M extends BaseModule> implements BasePresenter {

  protected V mDisplay;
  protected M mModule;


  protected RetrofitCallManager mCallManager;
  public ActivityPresenter(V mDisplay, M mModule) {
    this.mDisplay = mDisplay;
    this.mModule = mModule;
  }

  @Override
  public V getDisplay() {
    return mDisplay;
  }

  @Override
  public M getModule() {
    return mModule;
  }


  public boolean isDestroyed() {
    if (null == mDisplay) {
      return true;
    }
    return false;
  }

/*
  public   void addCall(retrofit2.Call call) {
    if (null == mCallManager) {
      mCallManager = new RetrofitCallManager();
    }
    mCallManager.addRetrofitCall(call);


  }

*/

}
