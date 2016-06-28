package com.structure.test.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by yuchao on 6/27/16.
 */
public class TouchLinerLayout extends LinearLayout {

  public static void LogV(String log) {
//    ArrayList
    Log.v("===tag=== view group ",log);
  }
  public TouchLinerLayout(Context context) {
    super(context);
  }

  public TouchLinerLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    LogV("dispatchTouchEvent");

    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    LogV("onInterceptTouchEvent");
    return super.onInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    LogV("onTouchEvent");
    return super.onTouchEvent(event);
  }


}
