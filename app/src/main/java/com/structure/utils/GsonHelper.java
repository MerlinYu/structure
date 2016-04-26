package com.structure.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by yuchao.
 */

public final class GsonHelper {

  private static Gson sSingleton = new Gson();

  public static Gson getInst() {
    return sSingleton;
  }

  public static <T> T toObject(String string, Class<T> clazz) {
    if (TextUtils.isEmpty(string)) {
      return null;
    }
    try {
      return getInst().fromJson(string, clazz);
    } catch (Exception ex) {
//            Timber.e(ex, ex.getMessage());
      return null;
    }
  }

  public static <T> T toObject(String string, Type type) {
    if (TextUtils.isEmpty(string)) {
      return null;
    }
    try {
      return getInst().fromJson(string, type);
    } catch (Exception ex) {
//            Timber.e(ex, "exception when to object, type is " + ex.getMessage());
      return null;
    }
  }

  public static String toJSONString(Object object) {
    if (null == object) {
      return null;
    }
    try {
      return getInst().toJson(object);
    } catch (Exception ex) {
//            Timber.e(ex, "exception when to json string : " + String.valueOf(object));
      return null;
    }
  }
}
