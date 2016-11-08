package com.structure.test.algorithm;

import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Created by yuchao on 10/10/16.
 */

public class QueueAlgorithm {

  public void priorityQueue() {
    PriorityQueue queue = new PriorityQueue();
    queue.add("file");
    queue.add("data");
    queue.add("apple");
    queue.add("zone");
    queue.add("easy");
    System.out.println("init queue: " + queue.toString());
    while(!queue.isEmpty()) {
      System.out.println("poll:" + queue.poll());
      for (Object s : queue) {
        System.out.print(s.toString() + " ");
      }
      System.out.println("");
    }
  }


  public static void main(String[] args) {
    QueueAlgorithm queueAlgorithm = new QueueAlgorithm();
    queueAlgorithm.priorityQueue();



  }
}
