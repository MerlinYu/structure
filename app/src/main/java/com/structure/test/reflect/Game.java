package com.structure.test.reflect;

/**
 * Created by yuchao on 6/3/16.
 */

public class Game {

  public String name = Game.class.getName();
  private String shape = "circle";
  public Game() {
    System.out.println(name);
  }

  public String getGameName() {
    return name;
  }

}
