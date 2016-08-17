package com.structure.test;

import com.structure.test.algorithm.SortAlgorithm;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yuchao on 6/6/16.
 * It is a file for testing algorithm
 * bubbleSort:冒泡算法
 * selectedSort:选择排序
 */
public class TestAlgorithm {


  public static void main(String[] args) {

    //intersect(aa,bb);
//    reverseVowels("hello, mast,you heb,yoiu, are ");
//    int rabbits = getRabits(6);
//    System.out.println(" rabit " + rabbits);
    SortAlgorithm sortAlgorithm =new SortAlgorithm();
    sortAlgorithm.test();
    
  }


  public static int[] intersect(int[] nums1, int[] nums2) {
    int shortNums[];
    int longNums[];
    if(nums1.length > nums2.length) {
      longNums = nums1;
      shortNums = nums2;
    } else {
      longNums = nums2;
      shortNums = nums1;
    }

    ArrayList<Integer> resultSet = new ArrayList<>();
    for (int i = 0; i < shortNums.length; i++) {
      for (int k=0; k < longNums.length; k++) {
        if(shortNums[i]==longNums[k]) {
          resultSet.add(shortNums[i]);
          break;
        }
      }
    }
    int[] resultNums = new int[resultSet.size()];
    Iterator iterator = resultSet.iterator();
    int count = 0;
    while (iterator.hasNext()) {
      resultNums[count++] = (Integer) iterator.next();
      System.out.println(resultNums[count-1]);

    }
    String s = "hello";

    char[] reversedChar = new char[s.length()];

    for (char c : s.toCharArray()) {
      reversedChar[count] = c;
    }
    String.valueOf(reversedChar);
    return resultNums;

  }

  public static String reverseVowels(String s) {
    char[] charArray = s.toCharArray();

    if(s.length()<=1) {
      return s;
    }

    int[] vowelCharArray = new int[charArray.length];

    int preCount = 0;
    for (int i = 0; i < charArray.length; i++) {
      if (isVowel(charArray[i])) {
        vowelCharArray[preCount++] = i;
      }
    }
    char[] reversedArray = new char[vowelCharArray.length];

    for (int j = 0 ; j < preCount; j++) {
      reversedArray[j] = charArray[vowelCharArray[preCount - j -1]];
      System.out.println(" re " +  reversedArray[j]);
    }

    for (int j = 0 ; j < preCount; j++) {
      charArray[vowelCharArray[j]] = reversedArray[j];
    }
    System.out.println("result " + String.valueOf(charArray));
    return String.valueOf(charArray);

  }

  private static boolean isVowel(char c) {
    char[] vowelsChar = {'a','e','i','o','u'};
    for (int i=0; i < vowelsChar.length; i++) {
      if(c == vowelsChar[i]) {
        return true;
      }
    }
    return false;
  }

  // 兔子问题，菲波列一对兔子，小兔子3个月之后每月便可以生一对兔子，
  // return 兔子对数
  // 另一种简单的实现是用递归实现
  public static  int getRabits(int months) {
    int rabbitsDouble = 1;
    if (months < 3) {
      return rabbitsDouble;
    }
    int first = 1;
    int second  = 1;
    int rabit = 0;
    for (int i = 0; i < months -2 ; i ++) {
      rabit = first + second;
      first = second;
      second = rabit;
    }
    return rabit;
  }







}
