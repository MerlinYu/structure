package com.structure.test.think;

import java.util.ArrayList;

/**
 * Created by yuchao on 8/18/16.
 * thinking in java中空对象的概念page:341
 * 空对象可以运用在这样的一种情境中：刚开一个公司，需要在各个position上安排人员，但是有些position并没有合适的人选
 * 此时可以将一个NULl对象安排在该上，当以后有新员工进来时，可以先判断该岗位上是否为NULL
 */

interface  NUll{}
class Person {
  public static final Person NULL = new NullPerson();

  String name;
  String phone;

  public Person(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }

  public static class NullPerson extends Person implements NUll{
    private NullPerson() {
      super("null","null");
    }
  }
}

class Position {

  String title;
  Person person;

  public Position(String title,Person person) {
    if (null == person) {
      this.person = Person.NULL;
    } else {
      this.person = person;
    }
    this.title = title;
  }

  public Position(String title) {
    this.title = title;
    person = Person.NULL;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    if (null == person) {
      this.person = Person.NULL;
    } else {
      this.person = person;
    }
  }
}



public class blankObject {

  ArrayList<Position> staff = new ArrayList<>();

  public void setStaff(String ...args) {
    for (String title : args) {
      staff.add(new Position(title));
    }
  }

  public void addPosition(Position position) {
    if (isPositionAvailable(position.getTitle())) {
      staff.add(position);
    }
  }

  public boolean isPositionAvailable(String title) {
    //TODO:
    return false;
  }

}
