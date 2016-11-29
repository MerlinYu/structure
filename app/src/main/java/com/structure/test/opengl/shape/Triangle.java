package com.structure.test.opengl.shape;

import android.opengl.GLES20;

import com.structure.test.opengl.OpenGlUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by yuchao on 11/11/16.
 */

public class Triangle {
//  private final String vertexShaderCode =
//      "attribute vec4 vPosition;" +
//          "void main() {" +
//          "  gl_Position = vPosition;" +
//          "}";

  private final String fragmentShaderCode =
      "precision mediump float;" +
          "uniform vec4 vColor;" +
          "void main() {" +
          "  gl_FragColor = vColor;" +
          "}";

  static final int COORDS_PER_VERTEX = 3;
  // number of coordinates per vertex int this array
  static float triangleCoords[] = {
      0.0f, 0.26f, 0.0f, //top
      -0.5f, -0.5f, 0.0f,//bottom left
      0.5f, -0.5f, 0.0f // bottom right
  };

  private final String vertexShaderCode =
      // This matrix member variable provides a hook to manipulate
      // the coordinates of the objects that use this vertex shader
      "uniform mat4 uMVPMatrix;" +
          "attribute vec4 vPosition;" +
          "void main() {" +
          // the matrix must be included as a modifier of gl_Position
          // Note that the uMVPMatrix factor *must be first* in order
          // for the matrix multiplication product to be correct.
          "  gl_Position = uMVPMatrix * vPosition;" +
          "}";

  // Use to access and set the view transformation
  private int mMVPMatrixHandle;

  float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };


  private int mProgram;
  private FloatBuffer vertexBuffer;
  private int mPositionHandle;
  private int mColorHandle;
  private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
  private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex;


  public Triangle() {
    vertexBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(triangleCoords);
    vertexBuffer.position(0);

    int vertexShader = OpenGlUtils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    int fragmentShader = OpenGlUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
    mProgram = GLES20.glCreateProgram();
    GLES20.glAttachShader(mProgram,vertexShader);
    GLES20.glAttachShader(mProgram, fragmentShader);
    GLES20.glLinkProgram(mProgram);
  }

  public void draw() {
    // use program to OpenGl Environment
    GLES20.glUseProgram(mProgram);
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    GLES20.glEnableVertexAttribArray(mPositionHandle);
    GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                 GLES20.GL_FLOAT, false,
                                 vertexStride, vertexBuffer);
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    GLES20.glUniform4fv(mColorHandle, 1, color, 0);
    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
    GLES20.glDisableVertexAttribArray(mPositionHandle);

  }

  public void draw(float[] mvpMatrix) {
    GLES20.glUseProgram(mProgram);
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    GLES20.glEnableVertexAttribArray(mPositionHandle);
    GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
                                 vertexStride, vertexBuffer);
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    GLES20.glUniform4fv(mColorHandle, 1, color, 0);

    mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
    GLES20.glDisableVertexAttribArray(mPositionHandle);

  }

//  public void draw(float[] mvpMatrix) {
//    // Add program to OpenGL environment
//    GLES20.glUseProgram(mProgram);
//
//    // get handle to vertex shader's vPosition member
//    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//
//    // Enable a handle to the triangle vertices
//    GLES20.glEnableVertexAttribArray(mPositionHandle);
//
//    // Prepare the triangle coordinate data
//    GLES20.glVertexAttribPointer(
//        mPositionHandle, COORDS_PER_VERTEX,
//        GLES20.GL_FLOAT, false,
//        vertexStride, vertexBuffer);
//
//    // get handle to fragment shader's vColor member
//    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
//
//    // Set color for drawing the triangle
//    GLES20.glUniform4fv(mColorHandle, 1, color, 0);
//
//    // get handle to shape's transformation matrix
//    mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
////    MyGLRenderer.checkGlError("glGetUniformLocation");
//
//    // Apply the projection and view transformation
//    GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
////    MyGLRenderer.checkGlError("glUniformMatrix4fv");
//
//    // Draw the triangle
//    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
//
//    // Disable vertex array
//    GLES20.glDisableVertexAttribArray(mPositionHandle);
//  }



}
