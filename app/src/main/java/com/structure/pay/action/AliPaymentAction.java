package com.structure.pay.action;

import android.app.Activity;

import java.util.Map;

/**
 * Created by yuchao on 2/7/17.
 */

public class AliPaymentAction implements PaymentAction{

  private Activity activity;
  public AliPaymentAction(Activity atx) {
    activity = atx;
  }


  @Override
  public void pay(String orderID, Map<String, String> parameters) {

  }
}
