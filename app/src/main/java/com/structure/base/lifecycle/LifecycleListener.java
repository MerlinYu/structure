package com.structure.base.lifecycle;

/**
 * Created by yuchao on 6/7/16.
 */
public interface LifecycleListener<T> {

  void onLifeChanged(T obj, LifecycleData data);
}
