package com.structure.pay;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.structure.pay.action.AliPaymentAction;
import com.structure.pay.action.PaymentAction;
import com.structure.pay.action.WeChatPaymentAction;


/**
 * Created by yuchao on 2/7/17.
 * support pay way
 */

public class SupportPayment implements Parcelable{
  /**支付唯一标识*/
  private int actionID;
  private String name;

  private SupportPayment(int actionID, String name) {
    this.actionID = actionID;
    this.name = name;
  }

  public int getActionID() {
    return actionID;
  }

  public String getPayName() {
    return name;
  }

  public static SupportPayment valueOf(String name) {
    if (TextUtils.isEmpty(name)) {
      return null;
    }
    if (name.equals(NAME_WECHAT)) {
      return new SupportPayment(ID_WECHAT, NAME_WECHAT);
    } else if (name.equals(NAME_ALIPAY)) {
      return new SupportPayment(ID_ALIPAY, NAME_ALIPAY);
    } else {
      return null;
    }
  }


  protected SupportPayment(Parcel in) {
    actionID = in.readInt();
    name = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(actionID);
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SupportPayment> CREATOR = new Creator<SupportPayment>() {
    @Override
    public SupportPayment createFromParcel(Parcel in) {
      return new SupportPayment(in);
    }

    @Override
    public SupportPayment[] newArray(int size) {
      return new SupportPayment[size];
    }
  };


  /**ID 支付宝*/
  public static final int ID_ALIPAY = 0x100;

  /**ID 微信*/
  public static final int ID_WECHAT = 0x200;

  /**name 支付宝*/
  public static final String NAME_ALIPAY = "alipay";

  /**name 微信*/
  public static final String NAME_WECHAT = "wechat";

  public  static SupportPayment AliPay = new SupportPayment(ID_ALIPAY, NAME_ALIPAY);

  public static SupportPayment WeChat = new SupportPayment(ID_WECHAT, NAME_WECHAT);

  public static String[] SupportPayList = {NAME_WECHAT, NAME_ALIPAY};


  public static PaymentAction toPaymentAction(Activity activity,
                                              SupportPayment actionType) {
    switch (actionType.actionID) {
      case ID_ALIPAY:
        return new AliPaymentAction(activity);
      case ID_WECHAT:
        return new WeChatPaymentAction(activity);
    }
    return null;
  }
}
