package com.structure;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

//import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.structure.collection.DeviceInfo;

/**
 * Created by yuchao.
 */
public class StructureApplication extends Application {

  public static StructureApplication sApplication;
  private RefWatcher mRefWatcher;


  private void initService() {
    RetrofitApiService.init(this);

  }

  public static Context getContext() {
    return getContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mRefWatcher = LeakCanary.install(this);
    sApplication = this;
    initService();
    initDeviceInfo();
//    Stetho.initializeWithDefaults(this);
  }

  private void initDeviceInfo() {

    DisplayMetrics dm = this.getResources().getDisplayMetrics();
    DeviceInfo.screenHeight = dm.heightPixels;
    DeviceInfo.screenWidth = dm.widthPixels;
    DeviceInfo.density = dm.density;


  }

  public static void watchRef(Object obj) {
    if (null != sApplication && null != sApplication.mRefWatcher) {
      sApplication.mRefWatcher.watch(obj);
    }
  }


}
