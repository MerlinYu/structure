package com.structure.test.opengl.shape;

import android.opengl.GLES20;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.structure.test.opengl.OpenGlUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by yuchao on 11/11/16.
 * open gl base shape
 */
public abstract class Shape {
  private final String TAG = "Shape";
  public static final int VERTEX_PRE_COORDS = 3; //

  enum ShapeMode {
    linear,
    point,
    triangle
  }

  public int mode;
  protected int mProgram;
  protected int mPositionHandle;
  protected int mColorHandle;
  protected int mUMVPMatrixHandle;

  protected FloatBuffer mVertexBuffer;
  protected String vertexShaderCode;
  protected String fragmentShaderCode;

  protected float[] vertexCoords;
  protected float[] color;

  public Shape() {
  }

  public void build() {
    if (vertexCoords == null) {
      throw new NullPointerException("vertex coords is null, please set it first.");
    }
    if (TextUtils.isEmpty(vertexShaderCode) || TextUtils.isEmpty(fragmentShaderCode)) {
      throw new NullPointerException("vertex or fragment shader code  is null, please set it first.");
    }
    ByteBuffer bb = ByteBuffer.allocateDirect(vertexCoords.length * 4);
    bb.order(ByteOrder.nativeOrder());
    mVertexBuffer = bb.asFloatBuffer();
    mVertexBuffer.put(vertexCoords);
    mVertexBuffer.position(0);

    int vertexShader = OpenGlUtils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    int fragmentShader = OpenGlUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
    mProgram = GLES20.glCreateProgram();
    GLES20.glAttachShader(mProgram, vertexShader);
    GLES20.glAttachShader(mProgram, fragmentShader);
    GLES20.glLinkProgram(mProgram);
  }

  public void shapeChanged() {
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    mUMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
  }

  public abstract void draw(@NonNull float[] mvpMatrix);


  public Shape setVertexShader(@NonNull String str) {
    this.vertexShaderCode = str;
    return this;
  }

  public  String getVertexShader() {
    return vertexShaderCode;
  }

  public Shape setFragmentShader(@NonNull String str) {
    this.fragmentShaderCode = str;
    return this;
  }

  public  String getFragmentShader() {
    return fragmentShaderCode;
  }


  public  Shape setVertexCoords(@NonNull float[] vertexCoords) {
    this.vertexCoords = vertexCoords;
    return this;
  }

  public float[] getVertexCoords() {
    return vertexCoords;
  }

  public float[] getColor() {
    return color;
  }

  public Shape setColor(float[] color) {
    this.color = color;
    return this;
  }

  public Shape setMode(int mode) {
    switch (mode) {
      case GLES20.GL_TRIANGLES:
      case GLES20.GL_LINEAR:
      case GLES20.GL_POINTS:
        this.mode = mode;
        break;
      default:
        //not support other mode
        break;
    }
    return this;
  }

  public int getMode() {
    return mode;
  }

  public String getShapeName() {
    switch (mode) {
      case GLES20.GL_TRIANGLES:
        return ShapeMode.triangle.name();
      case GLES20.GL_LINEAR:
        return ShapeMode.linear.name();
      case GLES20.GL_POINTS:
        return ShapeMode.point.name();
      default:
        return TAG;
    }
  }
}
