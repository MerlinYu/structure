package com.structure.utils.system;

import android.os.Build;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by yuchao on 11/25/16.
 */
public class SystemRomUtils {

  public enum SystemRomType {
    MIUI,
    FLYME,
    OTHER
  }

  private static final int DEFAULT_ROM = -1;

  private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
  private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
  private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
  private static final String KEY_EMUI_VERSION_CODE = "ro.build.hw_emui_api_level";
  private static final String KEY_FLAME_VERSION_NAME = "ro.build.fingerprint";



  private static  SystemRomType systemRomType;
  private static int miuiRom = DEFAULT_ROM; // 未初始化,防止多次查找判断
  private static int flameRom = DEFAULT_ROM;// 未初始化


  public static SystemRomUtils.SystemRomType getSystemType() {
    if (systemRomType == null) {
      if (SystemRomUtils.isMIUIRom()) {
        systemRomType =  SystemRomUtils.SystemRomType.MIUI;
      } else if (SystemRomUtils.isFlymeRom()) {
        systemRomType = SystemRomUtils.SystemRomType.FLYME;
      } else{
        systemRomType = SystemRomUtils.SystemRomType.OTHER;
      }
    }
    return systemRomType;
  }


  public static boolean isAndroid_M() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }


  public static boolean isMIUIRom() {
    if (miuiRom == -1) {
      try {
        final BuildProperties prop = BuildProperties.newInstance();
        if(prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
            || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
            || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
          miuiRom = 1;
        }
        prop.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return miuiRom == 1;
  }

  public static String getMIUIRomVersion() {
    String version = null;
    try {
      final BuildProperties prop = BuildProperties.newInstance();
      if (prop.getProperty(KEY_MIUI_VERSION_NAME) != null) {
        version = prop.getProperty(KEY_MIUI_VERSION_NAME);
      }
      prop.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return version;
  }

  public static String getFlameVersion() {
    //TODO:需要魅族系统测试,蛋疼的一件事是魅族系统连接不上ADB
    // 魅族系统Rom版本与android版本号一致
    return "V"+Build.VERSION.RELEASE;
  }

  // 魅族ROM
  public static boolean isFlymeRom() {
    if (flameRom == -1) {
      try {
        final Method method = Build.class.getMethod("hasSmartBar");
        flameRom =  method != null ? 1 : flameRom;
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    return flameRom == 1;
  }

  static class BuildProperties {

    private final Properties properties;

    private FileInputStream fileInputStream;

    private BuildProperties() throws IOException {
      properties = new Properties();
      fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
      properties.load(fileInputStream);
    }

    public static BuildProperties newInstance() throws IOException {
      return new BuildProperties();
    }

    public String getProperty(final String name) {
      return properties.getProperty(name);
    }

    public String getProperty(final String name, String defaultProperty) {
      return properties.getProperty(name, defaultProperty);
    }

    public void close() {
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }


    public int size() {
      return properties.size();
    }

    public boolean containKey(final Object key) {
      return properties.containsKey(key);
    }

    public boolean containValue(final Object value) {
      return properties.containsValue(value);
    }

    public boolean isEmpty() {
      return properties.isEmpty();
    }

    public Enumeration<Object> keys() {
      return properties.keys();
    }

    public Set<Object> keySet() {
      return properties.keySet();
    }

    public Collection<Object> values() {
      return properties.values();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
      return properties.entrySet();
    }

    @Override
    public String toString() {
      Set<Map.Entry<Object, Object>> set = entrySet();
      Iterator<Map.Entry<Object, Object>> iterator = set.iterator();
      StringBuffer buffer = new StringBuffer();
      int i = 0;
      while (iterator.hasNext()) {
        Map.Entry<Object, Object> entry = iterator.next();
        int keyLength = ((String) entry.getKey()).length();
        buffer.append(i++ + " Entry key : " + entry.getKey());
        if (keyLength >= 32) {
          buffer.append("| value : " + entry.getValue());
        } else if (keyLength >= 24) {
          buffer.append("\t| value : " + entry.getValue());
        } else {
          buffer.append("\t\t| value : " + entry.getValue());
        }
        buffer.append("\n");
      }
      return buffer.toString();
    }
  }
}