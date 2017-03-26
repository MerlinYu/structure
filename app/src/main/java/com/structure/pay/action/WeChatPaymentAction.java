package com.structure.pay.action;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

/**
 * Created by yuchao on 2/7/17.
 */

public class WeChatPaymentAction implements PaymentAction {

  private Activity activity;

  public WeChatPaymentAction(Activity activity) {
    this.activity = activity;
  }


  @Override
  public void pay(String orderID, Map<String, String> parameters) {

  }
}
