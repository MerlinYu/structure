package com.structure.test.reflect;

/**
 * Created by yuchao on 6/3/16.
 */

public class Game {

  public String name = Game.class.getName();
  private String shape = "circle";
  public Game() {
    System.out.println("基类无参构造 : " + name);
  }

  public String getGameName() {
    System.out.println("game name is : " + name);
    return name;
  }

  private String getShape() {
    System.out.println("private method get type of shape : " + shape);
    return shape;
  }

}
