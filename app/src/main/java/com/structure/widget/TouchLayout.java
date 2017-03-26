package com.structure.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by yuchao on 7/8/16.
 */
public class TouchLayout extends LinearLayout {

  String TAG = "===TouchLayout===";

  public TouchLayout(Context context) {
    super(context);
  }

  public TouchLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public TouchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    LogV("dispatchTouchEvent");
    return super.dispatchTouchEvent(ev);
    //TODO:简单重写，可以同时响应layout和imageview的点击事件，如使用需要优化
 /*   if (onInterceptTouchEvent(ev)) {
      onTouchEvent(ev);
      return true;
    }
    int childCount = getChildCount();
    onTouchEvent(ev);
    for (int i = 0; i < childCount; i++) {
      View view = getChildAt(i);
      view.onTouchEvent(ev);
    }
    return true;*/
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    LogV("onTouchEvent");
    return super.onTouchEvent(event);
  }




  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    LogV("onInterceptTouchEvent "  +  super.onInterceptTouchEvent(ev));
    return super.onInterceptTouchEvent(ev);
  }

  public void LogV(String log) {
    Log.v(TAG,log);
  }





}
