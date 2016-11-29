package com.structure.utils.system;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import com.structure.utils.LogUtils;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

import static android.R.attr.value;
import static timber.log.Timber.d;
import static timber.log.Timber.e;

/**
 * Created by yuchao on 11/25/16.
 */

public class SystemRomUtils {

  public enum SystemRomType {
    MIUI,
    FLYME,
    OTHER
  }

  private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
  private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
  private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
  private static final String KEY_EMUI_VERSION_CODE = "ro.build.hw_emui_api_level";
  private static final String KEY_FLAME_VERSION_NAME = "ro.build.fingerprint";


  public static boolean isMIUIRom() {
    try {
      final BuildProperties prop = BuildProperties.newInstance();
      return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
          || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
          || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static String getMIUIRomVersion() {
    String version = null;
    try {
      final BuildProperties prop = BuildProperties.newInstance();
      if (prop.getProperty(KEY_MIUI_VERSION_NAME) != null){
        version = prop.getProperty(KEY_MIUI_VERSION_NAME);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return version;
  }

  public static String getFlameVersion() {
    String version = null;
    try {
      final BuildProperties prop = BuildProperties.newInstance();
      if (prop.getProperty(KEY_FLAME_VERSION_NAME) != null){
        version = prop.getProperty(KEY_FLAME_VERSION_NAME);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    Pattern pattern = Pattern.compile("Flame_OS_");
    Matcher matcher = pattern.matcher(version);
    if (matcher.matches()) {
      version = matcher.group(0);
      LogUtils.log(version);
    }

    return null;
  }


  public static boolean isHuaWeiRom() {
    try {
      final BuildProperties prop = BuildProperties.newInstance();
      return prop.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return false;
  }
  // 魅族ROM
  public static boolean isFlymeRom() {
    try {
      final Method method = Build.class.getMethod("hasSmartBar");
      return method != null;
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return false;
  }

  static class BuildProperties {

    private final Properties properties;

    private BuildProperties() throws IOException {
      properties = new Properties();
      properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
      LogUtils.log("===tag=== " + toString());
    }

    public static BuildProperties newInstance() throws IOException{
      return new BuildProperties();
    }

    public String getProperty(final String name) {
      return properties.getProperty(name);
    }

    public String getProperty(final String name, String defaultProperty) {
      return properties.getProperty(name, defaultProperty);
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
        int keyLength = ((String)entry.getKey()).length();
        buffer.append(i++ +  " Entry key : " + entry.getKey());
        if (keyLength >=32) {
          buffer.append("| value : " + entry.getValue());
        } else if (keyLength >= 24){
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
