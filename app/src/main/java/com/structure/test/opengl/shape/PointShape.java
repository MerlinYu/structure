package com.structure.test.opengl.shape;

import android.opengl.GLES20;
import android.support.annotation.NonNull;

/**
 * Created by yuchao on 11/22/16.
 */

public class PointShape extends Shape {

  private final String VERTEX_SHADER =
      "precision mediump float;" +
          "attribute vec4 vPosition;" +
          "attribute float vPointSize;" +
          "uniform mat4 uMVPMatrix;" +
          "void main() {" +
            "gl_PointSize = vPointSize;"+
            "gl_Position =  uMVPMatrix * vPosition;" +
          "}";
  private final String FRAGMENT_SHADER =
      "precision mediump float;" +
          "uniform vec4 vColor;" +
          "void main() {" +
          "  gl_FragColor = vColor;" +
          "}";

  private int mPointSize;

  public void buildTestShape() {
    float[] color = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };
    float[] vertexCoords = {
        0.5f, 0.5f, 0.0f,
        -0.5f, 0.5f, 0.0f,
        0, -0.5f,0,
        0,0.5f,0
    };
    setColor(color)
        .setMode(GLES20.GL_POINTS)
        .setFragmentShader(FRAGMENT_SHADER)
        .setVertexShader(VERTEX_SHADER)
        .setVertexCoords(vertexCoords)
        .build();
  }

  @Override
  public void shapeChanged() {
    super.shapeChanged();
    mPointSize = GLES20.glGetAttribLocation(mProgram, "vPointSize");
  }

  @Override
  public void draw(@NonNull float[] mvpMatrix) {
    GLES20.glUseProgram(mProgram);
    GLES20.glEnableVertexAttribArray(mPositionHandle);
    GLES20.glVertexAttribPointer(mPositionHandle, VERTEX_PRE_COORDS,
        GLES20.GL_FLOAT, false, VERTEX_PRE_COORDS * 4, mVertexBuffer);
    GLES20.glUniform4fv(mColorHandle, 1, color, 0);
    GLES20.glVertexAttrib1f(mPointSize, 40.0f);
    GLES20.glUniformMatrix4fv(mUMVPMatrixHandle, 1, false, mvpMatrix, 0);
    GLES20.glDrawArrays(GLES20.GL_POINTS, 0, (vertexCoords.length / VERTEX_PRE_COORDS));
    GLES20.glDisableVertexAttribArray(mPositionHandle);
  }

}
