package com.structure.pay.action;

import java.util.Map;

/**
 * Created by yuchao on 2/7/17.
 */

public interface PaymentAction {

  void pay(String orderID, Map<String, String> parameters);
}
