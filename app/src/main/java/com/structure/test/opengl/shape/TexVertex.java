package com.structure.test.opengl.shape;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.support.annotation.DrawableRes;

import com.structure.test.opengl.OpenGlUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * Created by yuchao on 11/16/16.
 */

public class TexVertex {


  private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;" +
      "attribute vec4 vPosition;" +
      "attribute vec2 a_texCoord;" +
      "varying vec2 v_texCoord;" +
      "void main() {" +
      "  gl_Position = uMVPMatrix * vPosition;" +
      "  v_texCoord = a_texCoord;" +
      "}";
  private static final String FRAGMENT_SHADER = "precision mediump float;" +
      "varying vec2 v_texCoord;" +
      "uniform sampler2D s_texture;" +
      "void main() {" +
      "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
      "}";



  int[] mTexNames ;


  private static final float[] VERTEX = {   // in counterclockwise order:
      0.5f, 0.5f, 0,   // top right
      -0.5f, 0.5f, 0,  // top left
      -0.5f, -0.5f, 0, // bottom left
      0.5f, -0.5f, 0,  // bottom right
  };

  private static final short[] VERTEX_INDEX = { 0, 1, 2, 2, 0, 3 };
  private static final float[] UV_TEX_VERTEX = {   // in clockwise order:
      1, 0,  // bottom right
      0, 0,  // bottom left
      0, 1,  // top left
      1, 1,  // top right
  };


  private  FloatBuffer mVertexBuffer;
  private  ShortBuffer mVertexIndexBuffer;
  private  FloatBuffer mUvTexVertexBuffer;



  int mProgram;
  int mPositionHandle;

  private int mMatrixHandle;
  private int mTexCoordHandle;
  private int mTexSamplerHandle;


  public TexVertex() {

    mVertexBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(VERTEX);
    mVertexBuffer.position(0);

    mVertexIndexBuffer = ByteBuffer.allocateDirect(VERTEX_INDEX.length * 2)
        .order(ByteOrder.nativeOrder())
        .asShortBuffer()
        .put(VERTEX_INDEX);
    mVertexIndexBuffer.position(0);

    mUvTexVertexBuffer = ByteBuffer.allocateDirect(UV_TEX_VERTEX.length * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(UV_TEX_VERTEX);
    mUvTexVertexBuffer.position(0);

    int vertexShader = OpenGlUtils.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
    int fragmentShader = OpenGlUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
    mProgram = GLES20.glCreateProgram();
    GLES20.glAttachShader(mProgram, vertexShader);
    GLES20.glAttachShader(mProgram,fragmentShader);
    GLES20.glLinkProgram(mProgram);


  }

  public void setDrawable(Resources resources, @DrawableRes int resID) {
    mTexNames = new int[1];
    GLES20.glGenTextures(1, mTexNames, 0);
    Bitmap bitmap = BitmapFactory.decodeResource(resources, resID);
    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexNames[0]);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
        GLES20.GL_LINEAR);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
        GLES20.GL_LINEAR);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
        GLES20.GL_REPEAT);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
        GLES20.GL_REPEAT);
    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
    bitmap.recycle();
  }

  public void draw(Resources resources, @DrawableRes int resID, float[] mvpMatrix) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    GLES20.glUseProgram(mProgram);

    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    mTexCoordHandle = GLES20.glGetAttribLocation(mProgram, "a_texCoord");
    mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    mTexSamplerHandle = GLES20.glGetUniformLocation(mProgram, "s_texture");

    setDrawable(resources,resID);


    GLES20.glEnableVertexAttribArray(mPositionHandle);
    GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0,
        mVertexBuffer);

    GLES20.glEnableVertexAttribArray(mTexCoordHandle);
    GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0,
        mUvTexVertexBuffer);

    GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mvpMatrix, 0);
    GLES20.glUniform1i(mTexSamplerHandle, 0);

    GLES20.glDrawElements(GLES20.GL_TRIANGLES, VERTEX_INDEX.length,
        GLES20.GL_UNSIGNED_SHORT, mVertexIndexBuffer);

    GLES20.glDisableVertexAttribArray(mPositionHandle);
    GLES20.glDisableVertexAttribArray(mTexCoordHandle);

//    Utils.sendImage(mWidth, mHeight);



  }






}
