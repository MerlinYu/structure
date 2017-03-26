package com.structure.base;

/**
 * Created by yuchao.
 * 数据通用接口，动态加载各自类需要的接口
 */
public class ActivityModule<T> implements BaseModule {

  private T retrofitInterface;

  public ActivityModule(T wrapper) {
    this.retrofitInterface = wrapper;
  }

  public T asRetrofit() {
    return this.retrofitInterface;
  }

}
