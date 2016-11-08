package com.structure.test.algorithm;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import static com.structure.share.command.ShareType.qq;

/**
 * Created by yuchao on 8/17/16.
 * 二叉树算法
 */
public class TreeAlgorithm {


  /**
   * 快速排序：本质上也是交换排序
   * 算法思路：以第一个元素为基准，分割数组
   * 它的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，
   * 然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
   * */

  public void quickSort() {

    int[] number = {1,3,0,34,23,90,80,12,89,10};
    quick(number,0, number.length-1);



  }

  public void quick(int[] number,int lowIndex ,int highIndex) {
    if (lowIndex < highIndex) {
      int dp = partition(number,lowIndex,highIndex);
      quick(number,lowIndex, dp-1);
      quick(number,dp+1, highIndex);
    }

  }

  public int partition(int [] number,int lowIndex, int highIndex) {
//    number[lowIndex];
    // 第一次以Number[0]为限分割数组,并将其大于它的数和小于它的数移至左右两边
    int temp = number[lowIndex];
    while(lowIndex < highIndex) {
      //从数组的后边找到第一个比temp小的数
      while (lowIndex < highIndex && number[highIndex] > temp) {
        highIndex--;
      }
      // 将其放在temp之前
      if (lowIndex < highIndex) {
        number[lowIndex] = number[highIndex];
        lowIndex++;
      }
      // 从数组的前边开始寻找找到比temp大的数
      while (lowIndex < highIndex && number[lowIndex] <= temp) {
        lowIndex++;
      }
      if (lowIndex < highIndex) {
        number[highIndex] = number[lowIndex];
        highIndex--;
      }
    }
    number[lowIndex] = temp;
//    Stack
    Queue queue = new PriorityQueue(20);
    return lowIndex;
  }





}
