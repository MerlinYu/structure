package com.structure.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.structure.R;

/**
 * Created by yuchao on 7/11/16.
 */
public class LoadingTextView extends TextView {

  String TAG = "===LoadingTextView=== ";

  private AnimationDrawable loadingAnimDrawable;
  public LoadingTextView(Context context) {
    super(context);
    setText("正在加载...");
    setTextColor(context.getResources().getColor(R.color.white_gray));
    setTextSize(13.f);
    setGravity(Gravity.CENTER);
    initAnimDrawable();
  }

  public LoadingTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public LoadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public LoadingTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }


  private void initAnimDrawable() {
    if (null == loadingAnimDrawable) {
      LogV("onAttachedToWindow");
      loadingAnimDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_list);
      loadingAnimDrawable.setCallback(this);
      loadingAnimDrawable.setVisible(true, false);
      setCompoundDrawablesWithIntrinsicBounds(null,loadingAnimDrawable,null,null);
    }
  }


  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    if (loadingAnimDrawable == null) {
      return;
    }
    if (visibility == VISIBLE) {
      loadingAnimDrawable.start();
    } else {
      loadingAnimDrawable.stop();
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    loadingAnimDrawable = null;
  }

  private void LogV(String log) {
    if (null == log) {
      return;
    }
    Log.v(TAG,log);
  }

  public void startAnim() {
    if (null == loadingAnimDrawable) {
      return;
    }
    if (loadingAnimDrawable.isRunning()) {
      return;
    }
    LogV("start anim");
    loadingAnimDrawable.start();
  }
}
