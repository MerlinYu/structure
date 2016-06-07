package com.structure.base;


import com.structure.StructureApplication;
import com.structure.base.lifecycle.LifecycleData;
import com.structure.base.lifecycle.LifecycleEvent;
import com.structure.base.lifecycle.LifecycleListener;

/**
 * Created by yuchao.
 */
public class ActivityPresenter<V extends ActivityDisplay, M extends BaseModule>
    implements BasePresenter, LifecycleListener<BaseActivity> {

  protected V mDisplay;
  protected M mModule;
  protected RetrofitCallManager mCallManager;


  private LifecycleEvent life;

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

  public boolean isViewVisible() {
    return true;
  }

  @Override
  public void onLifeChanged(BaseActivity obj, LifecycleData data) {
    life = data.getLifecycle();
    switch (life) {
      case CREATE:
        onCreate();
        break;
      case START:
        onStart();
        break;
      case RESUME:
        onResume();
        break;
      case STOP:
        onStop();
        break;
      case DESTROY:
        onDestroy();
        break;
      default:break;
    }
  }

  protected void onCreate() {

  }

  protected void onStart() {

  }

  protected void onResume() {

  }

  protected void onStop() {

  }

  protected void onDestroy() {
    StructureApplication.watchRef(this);
  }

}
