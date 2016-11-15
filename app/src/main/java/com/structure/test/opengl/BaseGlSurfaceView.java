package com.structure.test.opengl;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.structure.BuildConfig;

/**
 * Created by yuchao on 11/11/16.
 */

public class BaseGlSurfaceView extends GLSurfaceView {

  private  BaseGlRender mRender;
  private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
  private float mPreviousX;
  private float mPreviousY;


  public BaseGlSurfaceView(Context context) {
    super(context);
    setEGLContextClientVersion(2);
   }

  public void start() {
    getHolder().setFormat(PixelFormat.TRANSLUCENT);
    if (BuildConfig.DEBUG) {
      setDebugFlags(DEBUG_CHECK_GL_ERROR); // find surface view debug info

    }
//    setEGLConfigChooser(); default RGB_888 16 bits
    mRender = new BaseGlRender();
    setRenderer(mRender);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
  }



  @Override
  public boolean onTouchEvent(MotionEvent e) {
    // MotionEvent reports input details from the touch screen
    // and other input controls. In this case, you are only
    // interested in events where the touch position changed.

    float x = e.getX();
    float y = e.getY();

    switch (e.getAction()) {
      case MotionEvent.ACTION_MOVE:

        float dx = x - mPreviousX;
        float dy = y - mPreviousY;

        // reverse direction of rotation above the mid-line
        if (y > getHeight() / 2) {
          dx = dx * -1 ;
        }

        // reverse direction of rotation to left of the mid-line
        if (x < getWidth() / 2) {
          dy = dy * -1 ;
        }

        mRender.setAngle(
            mRender.getAngle() +
                ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
        requestRender();
    }

    mPreviousX = x;
    mPreviousY = y;
    return true;
  }

}
