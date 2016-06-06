package com.structure.test.reflect;

import java.lang.reflect.Field;

/**
 * Created by yuchao on 6/3/16.
 * for testing java reflect
 */
public class BasketballGame extends Game {

  String name = BasketballGame.class.getName();

  public BasketballGame() {
    System.out.println(name);
  }

  public BasketballGame(String country) {
    System.out.println(country);
  }

  public static void main(String[] args) {

    BasketballGame game = new BasketballGame("china");
    Class<?> demo = null;
    Game baseGame = null;
    try {
      demo = Class.forName("com.processor.test.BasketballGame");
      System.out.println("===========本类属性========");
      Field[] field = demo.getDeclaredFields();
      // field = demo.getFields(); 父类属性
      int length = field.length;
      for(int i = 0; i < length; i++) {
        // 权限修饰符
        int mo = field[i].getModifiers();
        String priv = java.lang.reflect.Modifier.toString(mo);
        // 属性类型
        Class <?> type = field[i].getType();
        System.out.println(priv + " " + type.getName() + " " + field[i].getName());
      }

      baseGame = (Game) demo.newInstance();
      System.out.println(baseGame.getGameName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
