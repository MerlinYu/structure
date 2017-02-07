package com.structure.test.pattern;

/**
 * Created by yuchao on 8/15/16.
 * 外观模式，降低类与类之间的依赖关系，这种模式在开发中应该经常无意中用到，将功能封装与其他类尽量避免依赖。
 * 耦合，降低依赖
 */

class CPU {
  public void start(){
    System.out.println(" CPU start!");
  }
  public void end() {
    System.out.println(" CPU end!");

  }
}

class Disk {
  public void start() {
    System.out.println(" Disk start!");

  }
  public void end() {
    System.out.println(" Disk end!");

  }
}

class Computer {
  private CPU cpu;
  private Disk disk;

  public Computer() {
    cpu = new CPU();
    disk = new Disk();
  }

  public void start() {
    cpu.start();
    disk.start();
  }

  public void end() {
    cpu.end();
    disk.end();
  }

}

public class FacadePattern {

  public static void main(String ...args) {
    Computer computer = new Computer();
    computer.start();
    computer.end();
  }
}
