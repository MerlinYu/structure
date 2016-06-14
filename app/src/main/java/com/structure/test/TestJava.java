package com.structure.test;

import android.graphics.drawable.NinePatchDrawable;

/**
 * Created by yuchao on 6/14/16.
 */
public class TestJava {

  public static String NAME = "Merlin";

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
    TestJava test = new TestJava();
    System.out.println("===name=== " + NAME + NAME.hashCode());

    String name = new String("test");
    test.TestString(name);
    System.out.println("===string=== " + name + name.hashCode());


  }
}
