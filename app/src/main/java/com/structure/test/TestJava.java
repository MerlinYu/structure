package com.structure.test;


import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.name;

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
  static {
    System.out.println(" TestJava ");
  }

/*

  public TestJava() {
    System.out.println("test java constructor");
  }
*/


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


    String colorStr = "#e423H";
    String colorStr2 = "#ffffff";
    String colorStr3 = "#123456";
    String colorStr4 = "#222fff77";
    String colorStr5 = "#zzavff77";

    String check = "#[\\d,a-f,A-F]{6,8}";

    Pattern pattern = Pattern.compile(check);

    System.out.println("color match " + pattern.matcher(colorStr).matches());
    System.out.println("color match " + pattern.matcher(colorStr2).matches());
    System.out.println("color match " + pattern.matcher(colorStr3).matches());
    System.out.println("color match " + pattern.matcher(colorStr4).matches());
    System.out.println("color match " + pattern.matcher(colorStr5).matches());


    System.out.println("float 1.0f " + convertFloatPrice(1.0f));
    System.out.println("float 1.02f " + convertFloatPrice(1.02f));
    System.out.println("float 1.30f " + convertFloatPrice(1.30f));
    System.out.println("float 1.333f " + convertFloatPrice(1.000333f));
    System.out.println("float 1f " + convertFloatPrice(1f));





//    logicalOperator();
//    JavaCollection.test();
//    CloneShape.test();

//    TestJava test = new TestJava();
//    System.out.println("===name=== " + NAME + NAME.hashCode());
//    String name = new String("test");
//    test.TestString(name);
//    test.test();
//    System.out.println("===string=== " + name + name.hashCode());
//    test.testFinally();
  }


  @Deprecated // 过时的方法
  public void test() {

  }

  // finally 执行在 return 之后
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

  public static void logicalOperator() {
    int number1 = 0x101; // 257;
    int number2 = 0x110; // 272;
//    | 位运算符-或 0|0为0其他的都为1；
    System.out.println("number1,number2: 0x101, 0x110");
    System.out.format(" | 运算符 result:%x\n" , (number1|number2));

//    & 位运算符-并 1&1为1其他的都为0；
    System.out.format(" & 运算符 result:%x\n" , (number1&number2));

//    ^ 位运算符-异或 0|0 为0，1|1为0，其他为1；
    System.out.format(" 异或运算符 result:%x\n" , (number1^number2));

//    ~ 位运算符-反 ~0为1，~1为0；
    System.out.format(" 反运算符 result:%x\n" , (~number1));


    System.out.println("number1 十进制：" + number1);
//    <<左移，>>右移，相当于给一个数*2，/2的效果；
    System.out.format(" 左移运算符 result:%d\n" , (number1<<1));
    System.out.format(" 右移运算符 result:%d\n" , (number1>>1));

//    运算符优先级高低依次
//    ！ == != && ||
    int a ;
    int b;

//    if (a&&b)

  }


  private static String convertFloatPrice(float price) {
//    保留两位小数
    price =  ((float) (int)(price * 100) / 100);
    boolean  tenthIsInt = (int)(price * 10 % 10) == 0;
    boolean  percentileIsInt = (int)(price * 100 % 10) == 0;

    System.out.println(" ten, percent price " + tenthIsInt + " , " + percentileIsInt + " , " + price );

    float strPrice = price;

    if (tenthIsInt && percentileIsInt) {
      return String.valueOf((int) price);
    }
    return String.valueOf(strPrice);
  }

}
