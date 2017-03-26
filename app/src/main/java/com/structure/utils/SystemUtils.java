package com.structure.utils;

import android.os.Build;

import com.structure.utils.system.SystemRomUtils;
import com.structure.utils.system.SystemRomUtils.SystemRomType;

/**
 * Created by yuchao on 11/25/16.
 */

public class SystemUtils {




  public static SystemRomType getSystemType() {
    if (SystemRomUtils.isMIUIRom()) {
      return SystemRomType.MIUI;
    }
    if (SystemRomUtils.isFlymeRom()) {
      return SystemRomType.FLYME;
    }
    return SystemRomType.OTHER;
  }




  public static boolean isAndroid_M() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }


}
