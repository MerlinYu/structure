package com.structure.test;


import android.widget.TextView;

import com.squareup.leakcanary.ExcludedRefs;

import java.lang.annotation.Documented;

/**
 * Created by yuchao on 6/14/16.
 */

public class TestJava {

  public static final String NAME = "Merlin";
  String TAG = "tag";
  private static final int MAX = 1024;
  private static final int MIN = 1;
  private static int tempLength = 1024;
  private static int mixLength = 0;



  public void TestString(String string) {
    System.out.println("===string=== " + string.hashCode());

    String name = string;
    System.out.println("===hash code=== " + name.hashCode());
    System.out.println("===string=== " + string.hashCode());

    name = "smart";
    System.out.println("===hash code=== " + name.hashCode());

    name = new String("smart");
    System.out.println("===hash code=== " + name.hashCode());
    string = "yes";
    System.out.println("===string=== " + string + string.hashCode());

  }

  public static void main(String args[]) {
    int number = MAX * MIN;
    int length = mixLength + tempLength;
    int size = number * length;
//    TextView


    String log = "tag";

    TestJava test = new TestJava();
    System.out.println("===name=== " + NAME + NAME.hashCode());

    String name = new String("test");
    test.TestString(name);
    test.test();
    System.out.println("===string=== " + name + name.hashCode());
    test.testFinally();
  }


  @Deprecated // 过时的方法
  public void test() {

  }

  public void testFinally(){
    try{
      String str = null;
      int length = str.length();
      return;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("null exception");
      return;
    }
    finally {
      System.out.println("finally");
    }
  }

//  public void
}
