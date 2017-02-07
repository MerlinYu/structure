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


  private HashMap<String, WeakReference<PaymentActionCallback>> payCallbacks;


  private PaymentManager(Context context) {
    this.context = context.getApplicationContext();
    mainHandler = new Handler(context.getMainLooper());
    payCallbacks = new HashMap<>();
  }

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

  public String buildPayBackKey(SupportPayment actionType, String orderID) {
    StringBuilder builder = new StringBuilder(actionType.getPayName());
    builder.append("?order="+orderID);
    return builder.toString();
  }



  public void pay(@NonNull Activity activity, @NonNull String orderID, @NonNull SupportPayment actionType,
                  Map<String, String> parameters, PaymentActionCallback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("payment action callback can't be null");
    }
    PaymentAction paymentAction = SupportPayment.toPaymentAction(activity, actionType);
    if (paymentAction != null) {
      payCallbacks.put(buildPayBackKey(actionType, orderID), new WeakReference<>(callback));
      paymentAction.pay(orderID, parameters);
    }
  }

  public void paySuccess(SupportPayment actionType, String orderID,
                          Map<String, String> parameters) {
    String key = buildPayBackKey(actionType, orderID);
    WeakReference<PaymentActionCallback> callbackRef = payCallbacks.remove(key);
    if (callbackRef != null) {
      final PaymentActionCallback callback = callbackRef.get();
      if (callback != null) {
        mainHandler.post(() -> {
          callback.onPaymentSuccess(actionType, orderID, parameters);;
        });
      }
    }

  }

  public void payFailed(int errorCode, String errMsg, SupportPayment actionType, String orderID,
                          Map<String, String> parameters) {
    String key = buildPayBackKey(actionType, orderID);
    WeakReference<PaymentActionCallback> callbackRef = payCallbacks.remove(key);
    if (callbackRef != null) {
      final PaymentActionCallback callback = callbackRef.get();
      if (callback != null) {
        mainHandler.post(() -> {
          callback.onPaymentFailed(errorCode, errMsg, actionType, orderID, parameters);;
        });
      }
    }
  }

  public void payCancel(SupportPayment actionType, String orderID,
                          Map<String, String> parameters) {
    String key = buildPayBackKey(actionType, orderID);
    WeakReference<PaymentActionCallback> callbackRef = payCallbacks.remove(key);
    if (callbackRef != null) {
      final PaymentActionCallback callback = callbackRef.get();
      if (callback != null) {
        mainHandler.post(() -> {
          callback.onPaymentCancel(actionType, orderID, parameters);;
        });
      }
    }



  }

  public interface PaymentActionCallback {
    void onPaymentSuccess(SupportPayment actionType, String orderId,
                            Map<String, String> parameters);

    void onPaymentFailed(int errCode, String errMessage, SupportPayment actionType, String orderId,
                          Map<String, String> parameters);

    void onPaymentCancel(SupportPayment actionType, String orderId,
                          Map<String, String> parameters);
  }


}
