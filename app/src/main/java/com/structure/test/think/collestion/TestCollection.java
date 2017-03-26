package com.structure.test.think.collestion;

import com.structure.test.opengl.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

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
//    string[] 转换成list
//    array.aslist 会生成一个基于固定大小的数组,任何改变大小的操作都会被拒绝
    list.addAll(Arrays.asList(a));
//    list 转换成string[]
    String[] array = new String[list.size()];
    list.toArray(array);
//    NOTE:不能使用 String[] array = (String[]) list.toArray()方法直接转换,因为不能强制类型转换为String[],需要再遍历进一步转换为String

//   test String hash code
    for (int i = 0 ; i < list.size(); i++) {
      System.out.println( " " + list.get(i) + " : hash code " + list.get(i).hashCode());
    }
// 结果 :
//    abc : hash code 96354
//    hello : hash code 99162322
//    hello : hash code 99162322
//    pqr : hash code 111249

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
//    填充List容器
    int length = 4;
    List<String> list = Collections.nCopies(length, "hello");
    List<String> list2 = new ArrayList<>(length);
    System.out.println(list2.size());
    // result size = 0; because length 保是指定的初始容量,而不是真实的长度。
    list2.addAll(Collections.nCopies(length, "hello"));

    Collections.fill(list2, "world");

    System.out.println("list Collections.nCopies " + list.toString());
    System.out.println("list2 Collections.fill " + list2.size() + " , " + list2.toString() );

//    保证List在多全程中同步
    Collections.synchronizedList(list);

//   线程同步的几种Collection
    ConcurrentHashMap map;
    CopyOnWriteArrayList copyOnWriteArrayList;
    CopyOnWriteArraySet set;

//    HashTable实现了线程同步,在内部使用Synchronize 来保证线程安全,但是效率低下,目前已经被 ConcurrentHashMap代替。
    Hashtable<String, String> hashtable;

  }

  public  static void main(String[] args) {
    TestCollection collection = new TestCollection();
    collection.testHashMap();
    collection.testList();

    collection.testCollection();



  }

}
