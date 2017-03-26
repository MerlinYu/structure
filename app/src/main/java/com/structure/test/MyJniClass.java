package com.structure.test;

/**
 * Created by yuchao on 6/4/16.
 */
public class MyJniClass {

  static {
    System.loadLibrary("jni-hello");
  }
  private native void DisplayHello();
  private native String getDisplayName();
  private native int getNativeName();

  public void JniPrint() {
    DisplayHello();
  }

  public String getJniDisplayName() {
    return getDisplayName();
  }


}
