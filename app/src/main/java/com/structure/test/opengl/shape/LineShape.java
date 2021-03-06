package com.structure.test.opengl.shape;

import android.opengl.GLES20;
import android.support.annotation.NonNull;

/**
 * Created by yuchao on 11/22/16.
 */

public class LineShape extends Shape {

  private final String VERTEX_SHADER =
      "precision mediump float;" +
          "attribute vec4 vPosition;" +
          "uniform mat4 uMVPMatrix;" +
          "void main() {" +
            "gl_Position =  uMVPMatrix * vPosition;" +
          "}";

  private final String FRAGMENT_SHADER =
      "precision mediump float;" +
          "uniform vec4 vColor;" +
          "void main() {" +
          "  gl_FragColor = vColor;" +
          "}";


  public void buildTestShape() {

    float[] color = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };
    float[] vertexCoords = {
        0.5f, 0.5f, 0.0f,
        -0.5f, 0.5f, 0.0f
    };
    setColor(color)
        .setMode(GLES20.GL_LINES)
        .setFragmentShader(FRAGMENT_SHADER)
        .setVertexShader(VERTEX_SHADER)
        .setVertexCoords(vertexCoords)
        .build();
  }

  @Override
  public void shapeChanged() {
    super.shapeChanged();
  }

  @Override
  public void draw(@NonNull float[] mvpMatrix) {
    GLES20.glUseProgram(mProgram);
    GLES20.glEnableVertexAttribArray(mPositionHandle);
    GLES20.glVertexAttribPointer(mPositionHandle, VERTEX_PRE_COORDS,
        GLES20.GL_FLOAT, false, VERTEX_PRE_COORDS * 4, mVertexBuffer);
    GLES20.glUniform4fv(mColorHandle, 1, color, 0);
    GLES20.glUniformMatrix4fv(mUMVPMatrixHandle, 1, false, mvpMatrix, 0);
    GLES20.glLineWidth(20.0f);
    GLES20.glDrawArrays(mode, 0, 2);
    GLES20.glDisableVertexAttribArray(mPositionHandle);
  }
  
}
