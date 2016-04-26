package com.structure;

import android.app.Application;
import android.content.Context;

/**
 * Created by yuchao.
 */
public class StructureApplication extends Application {


  public static void getDeviceInfo() {

  }

  private void initService() {
    RetrofitApiService.init(this);

  }

  public static Context getContext() {
    return getContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    initService();
  }

  static class DeviceInfo {
    /**
     * 屏幕密度
     */
    int denisty;
    /**
     * 手机品牌
     */
    String brand;
    /**
     * 运营商
     */
    String serviceProvider;
  }

}
