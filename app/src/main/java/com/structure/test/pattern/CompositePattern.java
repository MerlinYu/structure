package com.structure.test.pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yuchao on 8/15/16.
 * 组合模式 将多个对象组合在一起进行操作，常用于表示树形结构中，例如二叉树，数等。
 *
 * */
public class CompositePattern {
  public void sendBroadCast(Context context) {
    context.sendBroadcast(null);
  }
  BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {

    }


  };

}
