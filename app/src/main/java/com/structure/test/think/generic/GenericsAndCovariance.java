package com.structure.test.think.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchao on 2/3/17.
 * 泛型配符
 *
 */

class  Fruit {}
class Apple extends Fruit{


}

class Jonathan extends Apple{

}

class Orange extends Fruit{

}

public class GenericsAndCovariance {

  public static void main(String[] args) {
    List<? extends Fruit> fruits;
    List<Apple> apples = new ArrayList<>();
    apples.add(new Apple());

    fruits = apples;
    Fruit fruit = fruits.get(0);



//    fruits = new ArrayList<Apple>();
//    fruits.add(new Fruit());


  }



}
