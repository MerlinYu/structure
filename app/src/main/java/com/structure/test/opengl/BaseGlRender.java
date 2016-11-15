package com.structure.test.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.structure.test.opengl.shape.Square;
import com.structure.test.opengl.shape.Triangle;
import com.structure.utils.LogUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by yuchao on 11/11/16.
 */

public class BaseGlRender implements GLSurfaceView.Renderer {

  private Triangle mTriangle;
  private Square mSquare;


  private final float[] mMVPMatrix = new float[16];
  private final float[] mProjectionMatrix = new float[16];
  private final float[] mViewMatrix = new float[16];
  private float[] mRotationMatrix = new float[16];

  private float mAngle;


  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    mTriangle = new Triangle();
    mSquare = new Square();
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES20.glViewport(0, 0, width, height);
    float ratio = (float) width / height;
    Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    LogUtils.log("width,height " + width + " , " + height);
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

//    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//    mTriangle.draw();

//    Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
 //   Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
//    mTriangle.draw(mMVPMatrix);

    onRotateDraw(gl);

  }

  public void onRotateDraw(GL10 gl) {

//    float[] scratch = new float[16];
//    Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
//    Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
//    mTriangle.draw(scratch);

    float[] scratch = new float[16];

    // Draw background color

    // Set the camera position (View matrix)
    Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    // Calculate the projection and view transformation
    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

    // Draw square
   // mSquare.draw(mMVPMatrix);

    // Create a rotation for the triangle

    // Use the following code to generate constant rotation.
    // Leave this code out when using TouchEvents.
    // long time = SystemClock.uptimeMillis() % 4000L;
    // float angle = 0.090f * ((int) time);

    Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);

    // Combine the rotation matrix with the projection and camera view
    // Note that the mMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

    // Draw triangle
    mTriangle.draw(scratch);


  }



  /**
   * Returns the rotation angle of the triangle shape (mTriangle).
   *
   * @return - A float representing the rotation angle.
   */
  public float getAngle() {
    return mAngle;
  }

  /**
   * Sets the rotation angle of the triangle shape (mTriangle).
   */
  public void setAngle(float angle) {
    mAngle = angle;
  }

}
