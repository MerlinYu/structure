package com.structure.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by yuchao.
 */
public abstract class BaseActivity<P extends ActivityPresenter> extends AppCompatActivity implements ActivityDisplay {

  protected P mPresenter;

  protected View contentView;

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
  protected void onDestroy() {
    if (null != mPresenter) {
      mPresenter = null;
    }
    ButterKnife.reset(this);
    super.onDestroy();
  }
}
