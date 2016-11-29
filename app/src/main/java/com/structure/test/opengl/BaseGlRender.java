package com.structure.test.opengl;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.structure.test.opengl.shape.LineShape;
import com.structure.test.opengl.shape.TriangleShape;
import com.structure.test.opengl.shape.PointShape;
import com.structure.test.opengl.shape.Square;
import com.structure.test.opengl.shape.TexVertex;
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
  private TexVertex mTexVertex;
  private TriangleShape mShape;

  TriangleShape triangleShape;
  PointShape pointShape;
  LineShape lineShape;


  private final float[] mMVPMatrix = new float[16];
  private final float[] mProjectionMatrix = new float[16];
  private final float[] mCameraMatrix = new float[16];
  private float[] mRotationMatrix = new float[16];

  private float mAngle;
  private Resources mRes;

  public BaseGlRender(Resources resources) {
    mRes = resources;
  }


  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    mTriangle = new Triangle();
    mSquare = new Square();
    mTexVertex = new TexVertex();
    mShape = new TriangleShape();
    triangleShape = new TriangleShape();
    triangleShape.buildTestShape();

    pointShape = new PointShape();
    pointShape.buildTestShape();

    lineShape = new LineShape();
    lineShape.buildTestShape();

  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES20.glViewport(0, 0, width, height);
    float ratio = (float) width / height;
    // 投影变换
    Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    LogUtils.log("width,height " + width + " , " + height);
    mShape.shapeChanged();
    triangleShape.shapeChanged();
    pointShape.shapeChanged();
    lineShape.shapeChanged();
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    //相机变换
    Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    // Calculate the projection and view transformation
    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mCameraMatrix, 0);
//    mShape.draw(mMVPMatrix);
//    triangleShape.draw(mMVPMatrix);
    pointShape.draw(mMVPMatrix);
    lineShape.draw(mMVPMatrix);




    //    mSquare.draw(mMVPMatrix);
//    Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
    // Combine the rotation matrix with the projection and camera view
    // Note that the mMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
//    float[] scratch = new float[16];
//    Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
//    mTriangle.draw(scratch);


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
