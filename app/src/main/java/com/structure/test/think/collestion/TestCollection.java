package com.structure.test.think.collestion;

import com.structure.test.opengl.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuchao on 3/6/17.
 * apply Thinking in Java about Collection knowledge
 * 1. hashMap
 * 2. list
 * 3. hash code and equals
 * 4. CollectionUtils
 *
 *
 */

public class TestCollection {


  public void testList() {
    String a[] = new String[]{"abc","hello","hello","pqr"};
    List<String> list = new ArrayList<>();
    list.addAll(Arrays.asList(a));



    for (int i = 0 ; i < list.size(); i++) {
      System.out.println( " " + list.get(i) + " : hash code " + list.get(i).hashCode());
    }

    // removeAll
    list.clear();
  }

  public void testHashMap() {

    HashMap<NameKey, String> map = new HashMap<>();

    NameKey nameKey = new NameKey();
    nameKey.name = "hello";

    NameKey secondKey = new NameKey();
    secondKey.name = "hello";
    secondKey.age = 20;

    if (secondKey.equals(nameKey)) {
      System.out.println("equals ");
    } else {
      System.out.println(" not equals ");
    }
    map.put(nameKey, "hello");
    map.put(secondKey, "hello");
    System.out.println(map.toString());

  }

  public void testCollection() {
//    Collections.

  }

  public  static void main(String[] args) {
    TestCollection collestion = new TestCollection();
    collestion.testCollection();
    collestion.testHashMap();
    collestion.testList();


  }

}
