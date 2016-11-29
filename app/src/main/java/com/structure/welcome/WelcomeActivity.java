package com.structure.welcome;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.test.material.MainActivity;

import butterknife.InjectView;

/**
 * Created by yuchao on 7/13/16.
 */
public class WelcomeActivity extends BaseActivity<WelcomeActivityPresenter> {

  @InjectView(R.id.welcome_view)
  FrameLayout welcomeLayout;
  @Override
  public WelcomeActivityPresenter createPresenter() {
    return new WelcomeActivityPresenter(this);
  }

  @Override
  public int getContentViewId() {
    return R.layout.activity_welcome;
  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter.loadWelcome();
  }


  public void setWelcomeLayout(Bitmap bitmap) {
    if (null == bitmap) {
      return;
    }
    welcomeLayout.setBackground(new BitmapDrawable(bitmap));
  }

  @Override
  public void finish() {
    super.finish();
  }

  public void startMainActivity() {
    startActivity(MainActivity.createIntent(this));
  }


}
