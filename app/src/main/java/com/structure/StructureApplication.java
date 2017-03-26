package com.structure;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

//import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.structure.cache.DiskCacheUtil;
import com.structure.collection.DeviceInfo;
import com.structure.test.database.DbManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import timber.log.Timber;

/**
 * Created by yuchao.
 */
public class StructureApplication extends Application {

  public static StructureApplication sApplication;
  private RefWatcher mRefWatcher;


  @Override
  public void onCreate() {
    super.onCreate();
    mRefWatcher = LeakCanary.install(this);
    sApplication = this;
    Timber.plant(new Timber.DebugTree());
    DiskCacheUtil.init(this);


    CrashReport.initCrashReport(getApplicationContext(), AppKey.BUGLY_APP_ID, BuildConfig.DEBUG);



    initService();
    initDeviceInfo();
    initDatabase();
    // 捕捉异常
    CrashHandler.getInstance().init();
    // samung ClipboardUIManager 内存泄漏
    try {
      Class cls = Class.forName("android.sec.clipboard.ClipboardUIManager");
      Method m = cls.getDeclaredMethod("getInstance",Context.class);
      m.setAccessible(true);
      m.invoke(null, sApplication);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
 //    Stetho.initializeWithDefaults(this);
  }



  private void initService() {
    RetrofitApiService.init(this);
  }

  private void initDatabase() {
    DbManager.getInstance().init(this);
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
