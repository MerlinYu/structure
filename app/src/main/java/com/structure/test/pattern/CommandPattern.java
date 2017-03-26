package com.structure.test.pattern;

/**
 * Created by yuchao on 8/15/16.
 * 命令模式，不要局限于形式，真正理解的是由这种模式的思想。
 */


interface Command{
  void exe();
}

class MyCommand implements Command {

  private Receiver receiver;

  public MyCommand(Receiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public void exe() {
    receiver.action();
  }
}

class Receiver {
  public void action() {
    System.out.println("receiver action!");
  }
}

class Invoker{
  public Command command;
  public Invoker(Command command) {
    this.command = command;
  }

  public void action() {
    System.out.println("Commander send command!");
    command.exe();
  }
}


public class CommandPattern {

  public void sendCommand() {
    Receiver receiver = new Receiver();
    Command command = new MyCommand(receiver);
    Invoker invoker = new Invoker(command);
    invoker.action();
  }


}
