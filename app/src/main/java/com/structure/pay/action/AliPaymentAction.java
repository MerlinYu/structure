package com.structure.pay.action;

import android.app.Activity;

import com.structure.pay.PaymentManager;

import java.util.Map;

import static android.R.string.cancel;

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

//    if cancel
//    PaymentManager.getInstance(activity).payCancel();
//    if success
//    PaymentManager.getInstance(activity).paySuccess();
//    if failed
//    PaymentManager.getInstance(activity).payFailed();

  }
}
