package com.structure.test.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

import com.structure.test.opengl.shape.Triangle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.structure.test.opengl.OpenGlUtils.loadShader;
import static com.structure.test.opengl.OpenGlUtils.log;

/**
 * Created by yuchao on 11/8/16.
 */

public class OpenGlRender implements GLSurfaceView.Renderer {
  private static final String VERTEX_SHADER = "attribute vec4 vPosition;\n"
      + "void main() {\n"
      + "  gl_Position = vPosition;\n"
      + "}";
  private static final String FRAGMENT_SHADER = "precision mediump float;\n"
      + "void main() {\n"
      + "  gl_FragColor = vec4(0.5,0,0,1);\n"
      + "}";

  private static final String VERTEX_SHADER_1 = "attribute vec4 vPosition;\n"
      + "uniform mat4 uMVPMatrix;\n"
      + "void main() {\n"
      + "  gl_Position = uMVPMatrix * vPosition;\n"
      + "}";

  private static final float[] VERTEX = { -0.5f,  0.5f, 0.0f,   // top left
      -0.5f, -0.5f, 0.0f,   // bottom left
      0.5f, -0.5f, 0.0f,   // bottom right
      0.5f,  0.5f, 0.0f }; // top right

//  private final FloatBuffer mVertexBuffer;
  private final float[] mProjectionMatrix = new float[16];
  private final float[] mCameraMatrix = new float[16];
  private final float[] mMVPMatrix = new float[16];

  private int mProgram;
  private int mPositionHandle;
  private int mMatrixHandle;

  private Triangle triangle;

  public OpenGlRender() {
//    mVertexBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
//        .order(ByteOrder.nativeOrder())
//        .asFloatBuffer()
//        .put(VERTEX);
//    log(" new open gl render !");
//    mVertexBuffer.position(0);
    triangle = new Triangle();
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  // OpenGL docs.
//    gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs. // Depth buffer setup.
//    gl.glClearDepthf(1.0f);// OpenGL docs.
//    gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL
//    gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs
//    gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,  GL10.GL_NICEST);

  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
//    mProgram = GLES20.glCreateProgram();
//    log(mProgram);
//    int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_1);
//    log(" vertexShader " + vertexShader);
//    int fragmentShader = OpenGlUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
//    log("fragmentShader " + fragmentShader);
//    GLES20.glAttachShader(mProgram, vertexShader);
//    GLES20.glAttachShader(mProgram, fragmentShader);
//    GLES20.glLinkProgram(mProgram);
//
//    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//    mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//
//    float ratio = (float) width / height;
//    OpenGlUtils.log("width " + width + " height " + height);
//    // 矩阵变换 将view 坐标变换成opengl坐标
//    Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
//    Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, 3, 0, 0, 0, 1, 1, 0);
//    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mCameraMatrix, 0);
//
//    OpenGlUtils.log(mProjectionMatrix);
//    log(mCameraMatrix);
//    log(mMVPMatrix);
//    log("mPositionHandle " + mPositionHandle);

  }

  @Override
  public void onDrawFrame(GL10 gl) {
    triangle.draw();


//    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//    GLES20.glUseProgram(mProgram);
//    GLES20.glEnableVertexAttribArray(mPositionHandle);
//    GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 12,
//        mVertexBuffer);
//
//    GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mMVPMatrix, 0);
//    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
//    GLES20.glDisableVertexAttribArray(mPositionHandle);
  }


}
