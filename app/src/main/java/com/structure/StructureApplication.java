package com.structure;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.structure.collection.DeviceInfo;

/**
 * Created by yuchao.
 */
public class StructureApplication extends Application {

  public static StructureApplication sApplication;

  private void initService() {
    RetrofitApiService.init(this);

  }

  public static Context getContext() {
    return getContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sApplication = this;
    initService();
    initDeviceInfo();
  }

  private void initDeviceInfo() {

    DisplayMetrics dm = this.getResources().getDisplayMetrics();
    DeviceInfo.screenHeight = dm.heightPixels;
    DeviceInfo.screenWidth = dm.widthPixels;

  }


}
