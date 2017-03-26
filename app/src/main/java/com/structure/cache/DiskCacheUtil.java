package com.structure.cache;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.internal.DiskLruCache;
import timber.log.Timber;


/**
 * Created by yuchao on 17/3/26.
 * disk cache util
 */

public class DiskCacheUtil {

//  缓存区大小
  private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10M
  private static final String CACHE_FILE = "cloudmall_cache";

  private static byte[] mByteBuffer;



  private static DiskLruCache mDiskLruCache;


  public static void init(Application context) {
    Timber.d("===tag=== disk init ");
    try {
      File cacheDir = getDiskCacheDir(context, CACHE_FILE);
      if (!cacheDir.exists()) {
        cacheDir.mkdirs();
      }
      Timber.d("===tag=== " + cacheDir.toString());
      mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context),
          1, CACHE_SIZE);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  public  static boolean writeCache(String key, String value) {
    if (mDiskLruCache == null) {
      Timber.e("not init DiskCacheUtil, please init first!");
      return false;
    }
    try {
      String hashKey = hashKeyForDisk(key);
      DiskLruCache.Editor editor = mDiskLruCache.edit(hashKey);
      OutputStream outputStream = editor.newOutputStream(0);
      outputStream.write(value.getBytes());
      outputStream.flush();
      outputStream.close();
      editor.commit();
      mDiskLruCache.flush();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static String readCache(String key) {
    if (mDiskLruCache == null) {
      return null;
    }
    try {
      String hashKey = hashKeyForDisk(key);
      DiskLruCache.Snapshot snapShot = mDiskLruCache.get(hashKey);
      if (snapShot != null) {
        InputStream is = snapShot.getInputStream(0);
        StringBuffer out = new StringBuffer();
        mByteBuffer = new byte[4096];
        int n;
        while ((n = is.read(mByteBuffer)) != -1) {
          out.append(new String(mByteBuffer, 0, n));
        }
        mByteBuffer = null;
        return out.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  private static   String hashKeyForDisk(String key) {
    String cacheKey;
    try {
      final MessageDigest mDigest = MessageDigest.getInstance("MD5");
      mDigest.update(key.getBytes());
      cacheKey = bytesToHexString(mDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      cacheKey = String.valueOf(key.hashCode());
    }
    Timber.d("===tag=== cacheKey " + cacheKey);
    return cacheKey;
  }

  private  static String bytesToHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(0xFF & bytes[i]);
      if (hex.length() == 1) {
        sb.append('0');
      }
      sb.append(hex);
    }
    return sb.toString();
  }


  private  static File getDiskCacheDir(Context context, String uniqueName) {
    String cachePath;
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
        || !Environment.isExternalStorageRemovable()) {
      cachePath = context.getExternalCacheDir().getPath();
    } else {
      cachePath = context.getCacheDir().getPath();
    }
    return new File(cachePath + File.separator + uniqueName);
  }


  private static int getAppVersion(Context context) {
    try {
      PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return info.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return 1;
  }


}
