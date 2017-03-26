package com.structure.test.algorithm;

/**
 * Created by yuchao on 11/24/16.
 */

public class NumberNSum {


  public long number() {
    long sum = 0;

    for (int i = 1; i<= 3; i++) {
      long index = 1;
      for(int j = 1; j <= i; j++) {
        index = index * j;
//        System.out.println(index);
      }
      sum = sum + index;
    }

    return sum;

  }
  public static void main(String[] args) {
    NumberNSum sum = new NumberNSum();
    System.out.print(sum.number());

  }
}
