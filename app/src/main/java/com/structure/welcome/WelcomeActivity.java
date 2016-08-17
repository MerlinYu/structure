package com.structure.welcome;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.google.common.util.concurrent.ServiceManager;
import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.main.MainActivity;

import java.util.HashMap;

import butterknife.InjectView;
import rx.Observable;

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
//
    mPresenter.loadWelcome();
//    Service;
//        Activity

//    Binder
//ActivityManager
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
