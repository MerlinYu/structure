package com.structure.test;

import android.graphics.drawable.shapes.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yuchao on 8/25/16.
 */
public class JavaCollection {


  public static void test() {
    testCapacity();
    testHashMapIterator();
  }


  private static void testCapacity() {

    ArrayList<String> capacityList = new ArrayList<>(2);
    capacityList.add("hello");
    capacityList.add(" world");
    capacityList.add(" !");
    // 测试ArrayList指定容量，超出容量之后会不会自动扩容，结果证明会自动扩容
    System.out.println(capacityList.toString() + " length " + capacityList.size());


    // arraylist 的克隆
    ArrayList<String> bArray = (ArrayList<String>) capacityList.clone();
    bArray.add(" who are you?");
    System.out.println("a " +capacityList.toString() + " length " + capacityList.size());
    System.out.println("b "+bArray.toString() + " length " + bArray.size());
    // 输出结果：
    // a [hello,  world,  !] length 3
    // b [hello,  world,  !,  who are you?] length 4

  }



  private static void testHashMapIterator() {

    HashMap<String, String> cityMap = new HashMap<>();
    cityMap.put("中国","北京");
    cityMap.put("美国","华盛顿");
    cityMap.put("日本","东京");
    // hashmap的迭代方式
    Iterator<Map.Entry<String, String>> it = cityMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String,String> entry = it.next();
      String value = entry.getValue();
      String key = entry.getKey();
    }
  }
}
