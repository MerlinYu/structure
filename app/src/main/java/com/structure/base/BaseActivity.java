package com.structure.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.structure.StructureApplication;
import com.structure.base.lifecycle.LifecycleData;
import com.structure.base.lifecycle.LifecycleEvent;
import com.structure.base.lifecycle.LifecycleObservable;

import butterknife.ButterKnife;

/**
 * Created by yuchao.
 */
public abstract class BaseActivity<P extends ActivityPresenter> extends AppCompatActivity implements ActivityDisplay {

  protected P mPresenter;
  protected View contentView;
  private LifecycleObservable<BaseActivity> lifeObserVable = new LifecycleObservable<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    onPreCreate();
    super.onCreate(savedInstanceState);
    int resID = getContentViewId();
    if (resID > 0) {
      setContentView(resID);
      contentView = this.findViewById(android.R.id.content);
    } else {
      contentView = createContentView();
      setContentView(contentView);
    }

    if (null != contentView) {
      ButterKnife.inject(this, contentView);
    }
    onViewCreated();
    mPresenter = createPresenter();
    lifeObserVable.subscribe(mPresenter);
    lifeObserVable.onLifecycleChanged(this, LifecycleData.create(LifecycleEvent.CREATE));

  }

  private View createContentView() {
    return null;
  }

  public abstract P createPresenter();

  public abstract int getContentViewId();

  public void onPreCreate() {

  }

  public void onViewCreated() {

  }

  @Override
  protected void onStart() {
    super.onStart();
    lifeObserVable.onLifecycleChanged(this, LifecycleData.create(LifecycleEvent.START));

  }

  @Override
  protected void onStop() {
    super.onStop();
    lifeObserVable.onLifecycleChanged(this, LifecycleData.create(LifecycleEvent.STOP));

  }

  @Override
  protected void onResume() {
    super.onResume();
    lifeObserVable.onLifecycleChanged(this, LifecycleData.create(LifecycleEvent.RESUME));
  }

  @Override
  protected void onDestroy() {
    ButterKnife.reset(this);
    lifeObserVable.onLifecycleChanged(this, LifecycleData.create(LifecycleEvent.DESTROY));
    lifeObserVable.unsubscribe(mPresenter);
    lifeObserVable.clear();
    if (null != mPresenter) {
      mPresenter = null;
    }
    lifeObserVable = null;
    super.onDestroy();
    StructureApplication.watchRef(this);
  }
}
