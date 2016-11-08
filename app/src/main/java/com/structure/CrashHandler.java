package com.structure;

import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.currentTimeMillis;

/**
 * Created by yuchao on 10/8/16.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

  private static volatile CrashHandler crashHandler;
  private Thread.UncaughtExceptionHandler defaultCrashHandler;
  private File crashFilePath;

  private CrashHandler() {
    final String storageState = Environment.getExternalStorageState();
    if (!TextUtils.isEmpty(storageState) && storageState.equals(Environment.MEDIA_MOUNTED)) {
      File dir = Environment.getExternalStorageDirectory();
      crashFilePath = new File(dir,"structure/crash");
      if (!crashFilePath.exists()) {
        crashFilePath.mkdir();
      }
    }
  }

  public static CrashHandler getInstance() {
    if (crashHandler == null) {
      synchronized (CrashHandler.class) {
        if (crashHandler == null) {
          crashHandler = new CrashHandler();
        }
      }
    }
    return crashHandler;
  }

  public void init() {
    defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    handleException(ex);
    // 无论是否成功处理 exception，都在 log 中打印出来
    if (defaultCrashHandler != null) {
      defaultCrashHandler.uncaughtException(thread,ex);
    }
    android.os.Process.killProcess(android.os.Process.myPid());
  }

  private void handleException(final Throwable ex) {
    Writer writer = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(writer);
    ex.printStackTrace(printWriter);
    final String result = writer.toString();

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_HH_mm", Locale.getDefault());
    String fileName = dateFormat.format(new Date(System.currentTimeMillis())) + ".txt";
    File logFile = new File(crashFilePath, fileName);
    try {
      FileOutputStream out = new FileOutputStream(logFile);
      byte bytes[] = result.getBytes();
      out.write(bytes);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (!BuildConfig.DEBUG) {
        //TODO: 非debug模式下直接kill app无任何提醒


      }
    }
  }


}
