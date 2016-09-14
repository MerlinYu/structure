package com.structure.test;

import android.graphics.drawable.shapes.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yuchao on 8/25/16.
 * test class clone
 */
public class CloneShape {

  public static void test() {
    testClone();
  }


  // 影子克隆也称简单克隆当类中属性是简单的String 类型时可以使用，如果类中有数组或复杂的类时必须使用尝试深度克隆
  static class Shape implements Cloneable{
    String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }



  private static void testClone() {


    try {
      Shape a = new Shape();
      a.name = "merlin";
      Shape b = (Shape) a.clone();
      b.name = "smart ";
      System.out.println("a " + a.name + " b " + b.name);
      // 输出结果 merlin start

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}
