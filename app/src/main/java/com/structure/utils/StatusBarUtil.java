package com.structure.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.structure.utils.system.SystemRomUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import timber.log.Timber;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.M;
import static com.structure.utils.system.SystemRomUtils.SystemRomType.FLYME;
import static com.structure.utils.system.SystemRomUtils.SystemRomType.MIUI;


/**
 * Created by yuchao on 11/24/16.
 * 目前只有MIUI, Flyme, Android6.0+ 可以设置状态栏黑色字体
 */

public class StatusBarUtil {

  // miui 6+ can set dark status bar text
  public static final String MIUI_STATUS_BAR_VERSION = "V6";
  // flame 4 can set dark status bar text
  public static final String FLYME_STATUS_BAR_VERSION = "V4.0";


  /**
   * @param activity
   * @param statusColorId    status bar color
   * @param isStatusTextDark isStatusTextDark = true the status bar text will be set dark.
   */
  public static void setStatusStyle(Activity activity, int statusColorId, boolean isStatusTextDark) {
    //TODO:需要注意的是 status color and status text color need combine to set
    if (canSetStatusBarMode()) {
      setStatusBarMode(activity, isStatusTextDark);
      setStatusBarColor(activity, statusColorId);
    }

  }


  /**
   * @return status bar can be set with color
   */
  private static boolean canSetStatusBarColor() {
    return Build.VERSION.SDK_INT >= KITKAT;
  }

  private static boolean canSetStatusBarMode() {
    return canMIUIStatusBarDark() || canFlymeStatusBarDark() || (Build.VERSION.SDK_INT >= M);
  }
  /**
   * NOTE:support android 4.4 version up
   *
   * @param activity
   * @param colorId
   */
  public static boolean setStatusBarColor(Activity activity, int colorId) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = activity.getWindow();
      window.setStatusBarColor(activity.getResources().getColor(colorId));
      return true;
    } else if (Build.VERSION.SDK_INT >= KITKAT) {
      //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
      transparencyBar(activity);
      SystemBarTintManager tintManager = new SystemBarTintManager(activity);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setStatusBarTintResource(colorId);
      return true;
    }
    return false;
  }

  /**
   * set status bar color transparent
   *
   * @param activity
   */
  @TargetApi(KITKAT)
  public static void transparencyBar(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = activity.getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
          | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      window.setNavigationBarColor(Color.TRANSPARENT);
    } else if (Build.VERSION.SDK_INT >= KITKAT) {
      Window window = activity.getWindow();
      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }

  /**
   * @return
   */
  private static void setStatusBarMode(Activity activity, boolean isDark) {
    LogUtils.log(canMIUIStatusBarDark());
    if (canMIUIStatusBarDark()) {
      setMIUIStatusBarMode(activity.getWindow(), isDark);
    } else if(canFlymeStatusBarDark()) {
      setFlymeStatusBarMode(activity.getWindow(), isDark);
    } else if(Build.VERSION.SDK_INT >= M) {
      activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
  }


  public static boolean canFlymeStatusBarDark() {
    if (SystemRomUtils.getSystemType() == FLYME) {
      String romVersion = SystemRomUtils.getFlameVersion();
      return compareRomVersion(FLYME_STATUS_BAR_VERSION, romVersion);
    }
    return false;
  }


  /**
   * 设置状态栏图标为深色和魅族特定的文字风格
   * 可以用来判断是否为Flyme用户
   *
   * @param window 需要设置的窗口
   * @param dark   是否把状态栏字体及图标颜色设置为深色
   * @return boolean 成功执行返回true
   */
  public static boolean setFlymeStatusBarMode(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
      try {
        WindowManager.LayoutParams lp = window.getAttributes();
        Field darkFlag = WindowManager.LayoutParams.class
            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
        Field meizuFlags = WindowManager.LayoutParams.class
            .getDeclaredField("meizuFlags");
        darkFlag.setAccessible(true);
        meizuFlags.setAccessible(true);
        int bit = darkFlag.getInt(null);
        int value = meizuFlags.getInt(lp);
        if (dark) {
          value |= bit;
        } else {
          value &= ~bit;
        }
        meizuFlags.setInt(lp, value);
        window.setAttributes(lp);
        result = true;
      } catch (Exception e) {

      }
    }
    return result;
  }

  /**
   * @return
   */
  public static boolean canMIUIStatusBarDark() {
    if (SystemRomUtils.getSystemType() == MIUI) {
      String romVersion = SystemRomUtils.getMIUIRomVersion();
      return compareRomVersion(MIUI_STATUS_BAR_VERSION, romVersion);
    }
    return false;
  }


  /**
   * 设置状态栏字体图标为深色，需要MIUIV6以上
   *
   * @param window 需要设置的窗口
   * @param dark   是否把状态栏字体及图标颜色设置为深色
   * @return boolean 成功执行返回true
   */
  public static boolean setMIUIStatusBarMode(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
      Class clazz = window.getClass();
      try {
        int darkModeFlag = 0;
        Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        if (dark) {
          extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
        } else {
          extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
        }
        result = true;
      } catch (Exception e) {

      }
    }
    return result;
  }


  /**
   * clear status bar dark mode
   */
  public static void clearStatusBarDarkMode(Activity activity, int type) {
    if (type == 1) {
      setMIUIStatusBarMode(activity.getWindow(), false);
    } else if (type == 2) {
      setFlymeStatusBarMode(activity.getWindow(), false);
    } else if (type == 3) {
      activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }
  }

  /**
   * @param requireVersion Vx.x
   * @param romVersion     Vx.x
   */
  private static boolean compareRomVersion(String requireVersion, String romVersion) {
    if (TextUtils.isEmpty(requireVersion) || TextUtils.isEmpty(romVersion)) {
      return false;
    }
    float requireVersionCode = Float.valueOf(requireVersion.substring(1, requireVersion.length()));
    float romVersionCode = Float.valueOf(romVersion.substring(1, romVersion.length()));
    return romVersionCode >= requireVersionCode;
  }


}