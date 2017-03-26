package com.structure.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuchao on 11/25/16.
 */

public class IpUtils {
    public static boolean isNetworkAvailable(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
      return activeNetwork != null &&
          activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isWIFIAvailable(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      return networkInfo != null && networkInfo.isConnected();
    }

    public static String getIMEI(Context context) {
      TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      return tm.getDeviceId();
    }

  /*public static String getMacAddress(Context context) {
    WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = manager.getConnectionInfo();
    return info.getMacAddress();
  }*/

    public static boolean externalStorageAvailable() {
      String state = Environment.getExternalStorageState();
      return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static String urlEncode(String string) {
      try {
        return URLEncoder.encode(string, "utf-8");
      } catch (UnsupportedEncodingException ignore) {
      }
      return string;
    }

    public static long parse(String text, SimpleDateFormat format) {
      try {
        return format.parse(text).getTime();
      } catch (ParseException e) {
        Log.wtf("CommonHelper", "Parsing text \"" + text + "\" to pattern [" + format.toPattern() + "] fail!", e);
      }
      return 0;
    }

    public static int getScreenWidth(Context context) {
      DisplayMetrics metrics = context.getResources().getDisplayMetrics();
      return metrics.widthPixels;
    }

    public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
      // Raw height and width of image
      final int height = options.outHeight;
      final int width = options.outWidth;
      int inSampleSize = 1;

      if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
            && (halfWidth / inSampleSize) > reqWidth) {
          inSampleSize *= 2;
        }
      }

      return inSampleSize;
    }

    public static String getIPAddress(boolean useIPv4) {
      try {
        List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface intf : interfaces) {
          List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
          for (InetAddress addr : addrs) {
            if (!addr.isLoopbackAddress()) {
              String sAddr = addr.getHostAddress().toUpperCase();
              boolean isIPv4 = addr instanceof Inet4Address;
              if (useIPv4) {
                if (isIPv4)
                  return sAddr;
              } else {
                if (!isIPv4) {
                  int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                  return delim < 0 ? sAddr : sAddr.substring(0, delim);
                }
              }
            }
          }
        }
      } catch (Exception ex) {
      } // for now eat exceptions
      return "";
    }

    public static String quote(String string) {
      return "'" + string + "'";
    }

    public static String wrap(String wrappedString, String symbol) {
      return symbol + wrappedString + symbol;
    }


}
