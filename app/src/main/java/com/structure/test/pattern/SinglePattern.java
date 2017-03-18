package com.structure.test.pattern;

/**
 * Created by yuchao on 3/7/17.
 */

public class SinglePattern {

//volatile 在有些编译器上会对代码运行的顺序优化,而volatile是为了禁止编译器进行优化的关键字。
// eg:    _instance = new SinglePattern() 会分为三步进行初始化下面是正常的初始化:
//  memory = allocate // 分配对象空间 step:1
//  ctorInstance(_instance) // 初始化对象 step:2
//  _instance = memory  // 设置instance指向刚才分配的内存的地址 step:3
//  Java语言设计规范在程序执行时必须遵守intra-thread semantics.这个规范保证重排序不会改变单线程执行的结果,
// 所以上面的步骤 2和3在有的编译器上会重新排序在单线程中这样做是没有问题的,但是在多线程中,第2步和第3步重新排序的话,其他线程可能
// 访问到 _instance = memory 从而判断instance不为空,其实这还没有完成初始化工作,只是分配了内存。
//

  private volatile static SinglePattern _instance;
  private SinglePattern() {
  }


//   为什么双重锁定会比单重锁能避免重复初始化呢?
//  A,B两线线程同时访问 getDefault,A,B线程同时进入到 第一重不为空的判断, 结果A进程synchronized 完成初始化,然后B进程被唤醒,拿到同步锁
//  需要第二次进行判断。这就是为什么使用双重锁。
//



//  这种单重锁并没有加synchronized 关键字,假如有多个线程同时访问此方法,会造成重复初始化
//  synchronized多线程并发时,很耗性能
  public SinglePattern getSingleDefault() {
    if (_instance == null) {
      _instance = new SinglePattern();
    }
    return _instance;
  }



  public SinglePattern getDefault() {
    if (_instance == null) {
      synchronized (SinglePattern.class) {
        if (_instance == null) {
          _instance = new SinglePattern();
        }
      }
    }
    return _instance;
  }



}
