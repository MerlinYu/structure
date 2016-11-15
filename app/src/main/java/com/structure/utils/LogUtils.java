package com.structure.utils;

import android.util.Log;

/**
 * Created by yuchao on 11/12/16.
 */

public class LogUtils {
  public final static String TAG = "===tag===";
  public static void log(int i) {
    Log.v(TAG, String.valueOf(i));
  }

  public static void log(String log){
    Log.v(TAG, log);
  }


  public static void log(float f) {
    Log.v(TAG, String.valueOf(f));
  }


}
