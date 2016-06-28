package com.structure.test.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by yuchao on 6/27/16.
 */
public class TouchButton extends Button{

  public void LogV(String log){
    Log.v("===tag=== button ",log);
  }
  public TouchButton(Context context) {
    super(context);
  }

  public TouchButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    LogV("dispatchTouchEvent");
    return super.dispatchTouchEvent(event);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    LogV("onTouchEvent");
    return super.onTouchEvent(event);
  }






}
