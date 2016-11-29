package com.structure.collection;

import android.os.Build;

/**
 * Created by yuchao.
 */
public class DeviceInfo {


  /**
   * 相对于160dp的分辨倍数
   */
  public static float density;
  /**
   * 手机品牌
   */
  public static String brand;
  /**
   * 运营商
   */
  public static String serviceProvider;

  public static int screenWidth;
  public static int screenHeight;



  public static String getDeviceInfo() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(" model : " + Build.MODEL)
        .append(" brand : " + Build.BRAND)
        .append(" manufacturer : " + Build.MANUFACTURER)
        .append(" product : " + Build.PRODUCT)
        .append(" boot loader : " + Build.BOOTLOADER)
        .append(" sdk_int : " + Build.VERSION.SDK_INT)
        .append(" device : " + Build.DEVICE);
    return buffer.toString();
  }

}
