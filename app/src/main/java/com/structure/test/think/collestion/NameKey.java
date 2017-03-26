package com.structure.test.think.collestion;

import com.structure.test.opengl.shape.Shape;

import static android.R.attr.shape;

/**
 * Created by yuchao on 3/8/17.
 * 重写 hashcode 和 equals 方法
 */

public class NameKey {


  public String name;
  public int age;

  public String getName() {
    return name;
  }

  boolean isMan;
  public double money;
  public float height;
  public Shape shape;


  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }


  @Override
  public int hashCode() {
    int result = 0;
    result = 31 * result + (isMan ? 1 : 0);
    result = 31 * result + age;
    result = 31 * result + name.hashCode();
    result = 31 * result + Float.floatToIntBits(height);
    result = 31 * result + (int) (Double.doubleToLongBits(money) ^ (Double.doubleToLongBits(money) >>> 32));
    result = 31 * result + (shape != null ? shape.hashCode() : 0);
    return result;
  }


  @Override
  public boolean equals(Object o) {
    if (!(o instanceof NameKey)) {
      return false;
    }
    if (name != null && !name.equals(((NameKey) o).name)) {
      return false;
    }
    if (age != ((NameKey) o).age) {
      return false;
    }
    if (isMan != ((NameKey) o).isMan) {
      return false;
    }
    if (height != ((NameKey) o).height) {
      return false;
    }
    if (money != ((NameKey) o).money) {
      return false;
    }
    if (shape != null && !shape.equals(((NameKey) o).shape)) {
      return false;
    }
    return true;
  }
}
