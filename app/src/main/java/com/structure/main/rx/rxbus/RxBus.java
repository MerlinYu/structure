package com.structure.main.rx.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by yuchao on 3/7/17.
 *
 * RxBus 实现EventBus类似功能
 * 相对EventBus实现简单,自已控制线程的切换,代码少
 * 缺点是:1. Event多时性能有所下降;
 *RxJava对于事件的监听可能需要放在onCreate或onResume中
 */

public class RxBus {


  private volatile static RxBus _instance;

  private final Subject<Object, Object> bus;

  private RxBus() {
    bus = new SerializedSubject<>(PublishSubject.create());
  }

  public RxBus getDefault() {
    if (_instance == null) {
      synchronized (RxBus.class) {
        if (_instance == null) {
          _instance = new RxBus();
        }
      }
    }
    return _instance;
  }

  public void post(Object event) {
    bus.onNext(event);
  }

  public <T> Observable<T> toObserrvable(Class<T> eventType) {
    return bus.ofType(eventType);
  }
}