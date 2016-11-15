package com.structure.test.opengl;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by yuchao on 11/11/16.
 */

public class OpenGlUtils {


  public static int loadShader(int type, String shaderCode) {
    int shader = GLES20.glCreateShader(type);
    GLES20.glShaderSource(shader, shaderCode);
    GLES20.glCompileShader(shader);
    return shader;
  }


  public static void log(int i) {
    Log.d("===tag=== open gl ", String.valueOf(i));
  }

  public static void log(String i) {
    Log.d("===tag=== open gl " , i);

  }

  public static void log(float i) {
    Log.d("===tag=== open gl ", String.valueOf(i));
  }

  public static void log(float[] floatArray) {
    StringBuffer buffer = new StringBuffer(216);

    for (int i = 0; i< floatArray.length; i++) {
      buffer.append(" " + floatArray[i]);
    }
    log(buffer.toString());
  }


}
