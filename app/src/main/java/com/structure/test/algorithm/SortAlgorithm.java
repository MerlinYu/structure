package com.structure.test.algorithm;

/**
 * Created by yuchao on 8/17/16.
 * 排序算法
 * bubbleSort:冒泡算法
 * selectedSort:选择排序
 * quickSort:快速排序
 * insertSort:插入排序
 * 先了解基本算法，然后再算法进阶，研究一些
 * think:算法思路僵化，贫瘠。目前算法对于我来说太难了
 */
public class SortAlgorithm {


//  final int number[] = {234,12,89,0,34,51,34,33,98,71};

  public void test() {
    bubbleSort();
    selectedSort();
    quickSort();
    insertSort();
  }

  /**
   * 冒泡算法，最经典的排序算法
   * 算法思路：每次循环将最小的数字寻找出来，并通过交换排序。
   * */
  public void bubbleSort() {
    int ten[] = {234,12,89,0,30,51,34,33,98,71};
    int length = ten.length;
    for (int i = 0; i < length; i++) {
      for(int j = i + 1; j < length; j++) {
        if (ten[i] > ten[j]) {
          int temp = ten[i];
          ten[i] = ten[j];
          ten[j] = temp;
        }
      }
    }
    System.out.println("bubble sort number is " + intToString(ten));
  }

  /**
   * 选择排序，冒泡算法的进阶
   * 算法思路：在i 与 n-1 之间选择出最小的数，然后交换，每次循环只进行一次交换。
   * */
  public void selectedSort() {
    int ten[] = {234,12,89,0,30,51,34,33,98,71};
    int length = ten.length;
    for (int i = 0; i < length; i++) {
      int selected = i;
      int temp = ten[i];
      for(int j = i + 1; j < length; j++) {
        if (temp > ten[j]) {
          selected = j;
          temp = ten[j];
        }
      }
      // exchange
      if (selected != i) {
        ten[selected] = ten[i];
        ten[i] = temp;
      }
    }
    System.out.println("selected sort number is " + intToString(ten));
  }

  public void minNumber() {
    int ten[] = {234,12,89,0,34,51,34,33,98,71};

    int temp = ten[0];
    for (int i = 0; i < ten.length; i++) {
      if (temp > ten[i]) {
        temp = ten[i];
      }
    }
    System.out.println("min number = " + temp);
  }



  public String intToString(int[] len) {
    StringBuffer buffer = new StringBuffer(len.length*2);
    for(int a : len) {
      buffer.append(a + ",");
    }
    return buffer.toString();
  }

  /**
   * 快递排序：本质上也是交换排序
   * 算法思路：以第一个元素为基准，分割数组
   * */
  public void quickSort() {
    int ten[] = {33,89,0,30,51,34,12,98,71,234};
    quickSort(ten,0,9);
    System.out.println("quick sort : " + intToString(ten));
  }

  public void quickSort(int[] number, int low,int high) {
    int dp;
    if (low < high) {
      dp = partition(number, low, high);
      quickSort(number, low, dp-1);
      quickSort(number, dp+1,high);
    }
  }


  // 分割数组
  public int partition(int number[],int low,int high) {
    int temp = number[low];
    while (low < high) {
      while (low < high && number[high] > temp) {
        high--;
      }
      if (low < high) {
        number[low++] = number[high];
      }
      while (low < high && number[low] <= temp) {
        low++;
      }
      if (low < high) {
        number[high--] = number[low];
      }
    }
    number[low] =temp;
    return low;
  }

  /**
   * 插入排序
   * 算法思路：将一个值插入有序的列表当中
   * */
  public void insertSort() {
    int ten[] = {33,89,51,30,0,34,12,98,234,71};
    int length = ten.length;
    for (int i = 1; i < length; i++) {
      for (int j= 0; j < i; j++) {
        if (ten[i] < ten[j]) {
          int temp ;
          // 插入
          temp = ten[j];
          ten[j] = ten[i];
          // 后移
          for (j = j+1; j <=i ; j++) {
            ten[i] = ten[j];
            ten[j] = temp;
            temp = ten[i];
          }
        }
      }
    }
    System.out.println("insert sort: " + intToString(ten));
  }

}
