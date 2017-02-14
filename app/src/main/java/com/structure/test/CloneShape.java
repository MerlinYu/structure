package com.structure.test;

import android.content.SharedPreferences;
import android.graphics.drawable.shapes.Shape;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
  static class Shape implements Cloneable, Parcelable {
    String name;

    public Shape() {

    }

    protected Shape(Parcel in) {
      name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(name);
    }

    @Override
    public int describeContents() {
      return 0;
    }

    public static final Creator<Shape> CREATOR = new Creator<Shape>() {
      @Override
      public Shape createFromParcel(Parcel in) {
        return new Shape(in);
      }

      @Override
      public Shape[] newArray(int size) {
        return new Shape[size];
      }
    };

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  //错误的深度克隆 demo1
  static class Circle implements Cloneable {
    Shape shape;
    float radius = 1.0f;

    public Circle() {
      shape = new Shape();
      shape.name = "circle";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }

    @Override
    public String toString() {
      return shape.name + " ,radius " + radius;
    }
  }

  //深度克隆 demo2
  static class Square implements Cloneable, Parcelable {
    // 通过Parcelable 流式读写来实现深度克隆

    Shape shape;

    public Square() {
      shape = new Shape();
      shape.name = "square";
    }

    protected Square(Parcel in) {
      shape = in.readParcelable(Shape.class.getClassLoader());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
      //先调用父类的克隆方法进行克隆操作
      Square cloneFooB = (Square) super.clone();
      //对于克隆后出的对象cloneFooB,如果其成员fooA为null,则不能调用clone(),否则出空指针异常
      if (this.shape != null) {
        cloneFooB.shape = (Shape) this.shape.clone();
      }
      return cloneFooB;

      //另一种深度克隆的方法,这也是一种常用的方法
      //将对象写到流里
      /*try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        //从流里读回来
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();

      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;*/
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(shape, flags);
    }

    @Override
    public int describeContents() {
      return 0;
    }

    public static final Creator<Square> CREATOR = new Creator<Square>() {
      @Override
      public Square createFromParcel(Parcel in) {
        return new Square(in);
      }

      @Override
      public Square[] newArray(int size) {
        return new Square[size];
      }
    };

    @Override
    public String toString() {
      return "name : " + shape.name;
    }
  }


  private static void testClone() {


    try {
      Shape a = new Shape();
      a.name = "merlin";
      Shape b = (Shape) a.clone();
      b.name = "smart ";
      // 输出结果 merlin start
      a.name = "hello";
      System.out.println("影子复制 or 浅度复制 " + a.name + " ,b " + b.name);

      Circle circle = new Circle();

      Circle circle2 = (Circle) circle.clone();
      circle2.shape.name = "clone circle 2 name ";
      circle2.radius = 2.0f;
      System.out.println("错误的深度复制 circle1 " + circle.toString() + " ,circle2 " + circle2.toString());

      Square square = new Square();
      Square square2 = (Square) square.clone();
      square2.shape.name = " clone square 2 name";
      System.out.println("深度复制 square1 " + square.toString() + " ,square2 " + square2.toString());

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}
