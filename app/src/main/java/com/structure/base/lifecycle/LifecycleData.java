package com.structure.base.lifecycle;

/**
 * Created by yuchao on 6/7/16.
 */
public class LifecycleData {

  private LifecycleEvent lifecycleEvent;

  public LifecycleData(LifecycleEvent event) {
    lifecycleEvent = event;
  }

  public LifecycleEvent getLifecycle() {
    return lifecycleEvent;
  }

  public static LifecycleData create(LifecycleEvent life) {
    if (null == life) {
      throw new IllegalArgumentException(" life is null");
    }
    return new LifecycleData(life);
  }

}
