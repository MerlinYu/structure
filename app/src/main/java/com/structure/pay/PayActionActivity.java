package com.structure.pay;

import com.structure.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuchao on 2/7/17.
 * pay activity
 * TODO: add pay structure demo
 *
 */

public class PayActionActivity extends BaseActivity<PayActionActivityPresenter>{


  @Override
  public PayActionActivityPresenter createPresenter() {
    return null;
  }

  @Override
  public int getContentViewId() {
    return 0;
  }


  public void payOrder() {

    String orderId = "55034";
    SupportPayment payment = SupportPayment.AliPay;
    Map<String, String> parameters = new HashMap<>();
    parameters.put("time", String.valueOf(System.currentTimeMillis()));
    PaymentManager.PaymentResultCallback callback = new PaymentManager.PaymentResultCallback() {
      @Override
      public void onPaymentSuccess(SupportPayment actionType, String orderId, Map<String, String> parameters) {
        //TODO: success
      }

      @Override
      public void onPaymentFailed(int errCode, String errMessage, SupportPayment actionType, String orderId, Map<String, String> parameters) {
        //TODO: failed
      }

      @Override
      public void onPaymentCancel(SupportPayment actionType, String orderId, Map<String, String> parameters) {
        //TODO: cancel
      }
    };

    PaymentManager.getInstance(this).pay(this, orderId, payment, parameters, callback);

  }




}
