package com.structure.test.opengl;

/**
 * Created by yuchao on 11/22/16.
 */

public interface SurfaceDrawListener {

   void surfaceChanged();

   void surfaceCreated();

   void onDraw(float[] mvpMatrix);

}
