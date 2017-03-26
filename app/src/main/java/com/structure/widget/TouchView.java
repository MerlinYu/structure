package com.structure.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by yuchao on 7/8/16.
 */
public class TouchView extends ImageView {
  String TAG = "===TouchView===";
  public TouchView(Context context) {
    super(context);
  }

  public TouchView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public TouchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    LogV("onTouchEvent ");
    super.onTouchEvent(event);

    return false;
  }




  public void LogV(String log) {
    Log.v(TAG,log);
  }
}
