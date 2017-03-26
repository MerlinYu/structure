package com.structure.test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yuchao on 6/3/16.
 * for testing java reflect
 * 反射可以实现动态代理，可以以输入string参数的去forname生成一个类，然后就可以得到其中属性，方法，进行调用。
 * 竟然可以通过反射调用类的私有方法，唯一需要知道的是私有方法的方法名，通过反射得不到私有方法。
 * 可以通过反编译器得到私有的方法
 */
public class BasketballGame extends Game {

  public String name = BasketballGame.class.getName();
  protected int size;
  private int price;

  public BasketballGame() {
    System.out.println("BasketballGame "+ name);
  }

  public BasketballGame(String country) {
    System.out.println("BasketballGame  country "+ country);
  }

  private void setPrice(int price) {
    this.price = price;
  }

  public static void main(String[] args) {

    BasketballGame game = new BasketballGame("china");
    Class<?> demo = null;
    Game baseGame = null;
    try {
      demo = Class.forName("com.structure.test.reflect.BasketballGame");
      System.out.println("===============本类属性==============");
      Field[] field = demo.getDeclaredFields();
      // field = demo.getFields(); 父类属性
      int length = field.length;
      for (int i = 0; i < length; i++) {
        // 权限修饰符
        int mo = field[i].getModifiers();
        String priv = java.lang.reflect.Modifier.toString(mo);
        // 属性类型
        Class<?> type = field[i].getType();
        System.out.println(priv + " " + type.getName() + " " + field[i].getName());
      }
      System.out.println("===============本类方法==============");
      Method[] methods = demo.getMethods();
      {
        for (Method method : methods) {
          int modifiers = method.getModifiers();
          System.out.println(java.lang.reflect.Modifier.toString(modifiers) + " method : " + method.getName());
        }
      }
      System.out.println("===============本类父类==============");
      Class superclass = demo.getSuperclass();
      System.out.println("super class name " + superclass.getName());
      // 相当于new Game()
      baseGame = (Game) demo.newInstance();
//      System.out.println(baseGame.getGameName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    Game game1 = new Game();
    // 反射调用private 方法
    callPrivateMethod(game1, "getShape");
  }


  static void callPrivateMethod(Object object,String methodName) {
    try {
      Method method = object.getClass().getDeclaredMethod(methodName);
      method.setAccessible(true);
      method.invoke(object);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
