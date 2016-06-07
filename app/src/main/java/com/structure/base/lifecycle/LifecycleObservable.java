package com.structure.base.lifecycle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yuchao on 6/7/16.
 */
public class LifecycleObservable<T> {

  private ArrayList<WeakReference<LifecycleListener<T>>> lifeListeners;


  public void onLifecycleChanged(T obj, LifecycleData data) {
    if (null == lifeListeners || null == obj) {
      return;
    }
    Iterator<WeakReference<LifecycleListener<T>>> iterator = lifeListeners.iterator();
    while (iterator.hasNext()) {
      LifecycleListener<T> listener = iterator.next().get();
      if (null == listener) {
        iterator.remove();
      } else {
        listener.onLifeChanged(obj, data);
      }
    }
  }

  public void clear() {
    if (null != lifeListeners) {
      lifeListeners.clear();
    }
  }

  public void subscribe(LifecycleListener<T> listener) {
    if (null == listener) {
      return;
    }
    if (null == lifeListeners) {
      lifeListeners = new ArrayList<>();
    }
    boolean contains = false;
    Iterator<WeakReference<LifecycleListener<T>>> iterator = lifeListeners.iterator();
    while (iterator.hasNext()) {
      WeakReference<LifecycleListener<T>> ref = iterator.next();
      LifecycleListener<T> lis = ref.get();
      if (lis.equals(listener)) {
        contains = true;
        break;
      }
    }

    if (!contains) {
      lifeListeners.add(new WeakReference<>(listener));
    }

  }

  public void unsubscribe(LifecycleListener<T> listener) {
    if (null == lifeListeners || null == listener) {
      return;
    }
    Iterator<WeakReference<LifecycleListener<T>>> iterator = lifeListeners.iterator();
    while (iterator.hasNext()) {
      LifecycleListener<T> lis = iterator.next().get();
      if (lis.equals(lis)) {
        iterator.remove();
        break;
      }
    }
  }
}
