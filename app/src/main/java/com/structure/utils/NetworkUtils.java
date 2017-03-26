package com.structure.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.structure.StructureApplication;

/**
 * Created by yuchao.
 */
public class NetworkUtils {

  private NetworkUtils() {}

  public static String getMacAddress(final Context c) {
    if (c == null) {
      return "";
    }
    final WifiManager wm = (WifiManager) StructureApplication.sApplication.getSystemService(Context.WIFI_SERVICE);
    if (wm.getConnectionInfo() != null) {
      final String address = wm.getConnectionInfo().getMacAddress();
      return address == null ? "" : address;
    }
    return "";
  }


}
