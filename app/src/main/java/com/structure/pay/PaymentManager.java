package com.structure.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.structure.pay.action.PaymentAction;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuchao on 2/7/17.
 */

public class PaymentManager {

  private static volatile PaymentManager instance;

  private Context context;
  private Handler mainHandler;


  private HashMap<String, WeakReference<PaymentResultCallback>> payCallbacks;



  private PaymentManager(Context context) {
    this.context = context.getApplicationContext();
    mainHandler = new Handler(context.getMainLooper());
    payCallbacks = new HashMap<>();
  }
// 单例
  public static PaymentManager getInstance(Activity activity) {
    if (instance == null) {
      synchronized (PaymentManager.class) {
        if (instance == null) {
          instance = new PaymentManager(activity);
        }
      }
    }
    return instance;
  }
// map key
  public String buildPayBackKey(SupportPayment actionType, String orderID) {
    StringBuilder builder = new StringBuilder(actionType.getPayName());
    builder.append("?order=" + orderID);
    return builder.toString();
  }


//  支付
  public void pay(@NonNull Activity activity, @NonNull String orderID, @NonNull SupportPayment actionType,
                  Map<String, String> parameters, PaymentResultCallback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("payment action callback can't be null");
    }
    PaymentAction paymentAction = SupportPayment.toPaymentAction(activity, actionType);
    if (paymentAction != null) {
//      PaymentResultCallback 加入到Map列表当中,为支付结果回调做准备
      payCallbacks.put(buildPayBackKey(actionType, orderID), new WeakReference<>(callback));
      paymentAction.pay(orderID, parameters);
    }
  }

// 支付成功
  public void paySuccess(SupportPayment actionType, String orderID,
                         Map<String, String> parameters) {
    String key = buildPayBackKey(actionType, orderID);
    WeakReference<PaymentResultCallback> callbackRef = payCallbacks.remove(key);
    if (callbackRef != null) {
      final PaymentResultCallback callback = callbackRef.get();
      if (callback != null) {
        mainHandler.post(() -> {
          callback.onPaymentSuccess(actionType, orderID, parameters);
        });
      }
    }
  }
  // 支付失败
  public void payFailed(int errorCode, String errMsg, SupportPayment actionType, String orderID,
                        Map<String, String> parameters) {
    String key = buildPayBackKey(actionType, orderID);
    WeakReference<PaymentResultCallback> callbackRef = payCallbacks.remove(key);
    if (callbackRef != null) {
      final PaymentResultCallback callback = callbackRef.get();
      if (callback != null) {
        mainHandler.post(() -> {
          callback.onPaymentFailed(errorCode, errMsg, actionType, orderID, parameters);
          ;
        });
      }
    }
  }
//取消支付
  public void payCancel(SupportPayment actionType, String orderID,
                        Map<String, String> parameters) {
    String key = buildPayBackKey(actionType, orderID);
    WeakReference<PaymentResultCallback> callbackRef = payCallbacks.remove(key);
    if (callbackRef != null) {
      final PaymentResultCallback callback = callbackRef.get();
      if (callback != null) {
        mainHandler.post(() -> {
          callback.onPaymentCancel(actionType, orderID, parameters);
          ;
        });
      }
    }
  }


  public interface PaymentResultCallback {
    void onPaymentSuccess(SupportPayment actionType, String orderId,
                          Map<String, String> parameters);

    void onPaymentFailed(int errCode, String errMessage, SupportPayment actionType, String orderId,
                         Map<String, String> parameters);

    void onPaymentCancel(SupportPayment actionType, String orderId,
                         Map<String, String> parameters);
  }


}
