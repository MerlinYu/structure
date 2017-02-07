package com.structure.test.pattern;

/**
 * Created by yuchao on 8/15/16.
 * 代理模式
 */


interface NetWork {
  void browse();
}

class Real implements NetWork {

  @Override
  public void browse() {
    System.out.println("browse intent!");
  }
}

class Proxy implements NetWork {

  private NetWork netWork;

  public Proxy(NetWork netWork) {
    this.netWork = netWork;
  }


// 代理了上网功能
  @Override
  public void browse() {
    if (null != netWork) {
      netWork.browse();
    }
  }
}

public class ProxyPattern {

  public void browseInternet() {
    NetWork netWork;
    netWork = new Proxy(new Real());
    netWork.browse();
  }

}

