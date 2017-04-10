package com.structure.cache;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jakewharton.disklrucache.DiskLruCache;

import timber.log.Timber;


/**
 * Created by yuchao on 17/3/26.
 * disk cache util
 * server has half hour cache so the client will also set this cache time
 */
public class DiskCacheUtil {

  //  缓存区大小
  private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10M
  private static final int CACHE_BYTE_SIZE = 4 * 1024; // 4 k
  private static final long CACHE_TIME_DIVIDER = 30 * 60 * 1000;// 30 min
  private static final String CACHE_FILE = "cache_file";
  private static final String TIME_STAMP = "time_";
  private static byte[] mByteBuffer;
  private static DiskLruCache mDiskLruCache;

  public static void init(Application context) {
    File cacheDir = getDiskCacheDir(context, CACHE_FILE);
    if (!cacheDir.exists()) {
      cacheDir.mkdirs();
    }
    try {
      mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, CACHE_SIZE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  //I use http request url as the cache key but some times the http query key has different sort
  // like : http://api.com/name?key=name&value=wang and http://api.com/name?value=wang&key=name is the same key
  public static boolean writeCache(final String key, final String value) {
    if (mDiskLruCache == null || TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
      Timber.e("not init DiskCacheUtil, please init first!");
      return false;
    }
    // time stamp

    try {
      String hashKey = cacheKeyForDisk(key);
      DiskLruCache.Editor editor = mDiskLruCache.edit(hashKey);
      OutputStream outputStream = editor.newOutputStream(0);
      outputStream.write(value.getBytes());
      outputStream.flush();
      outputStream.close();
      editor.commit();

      DiskLruCache.Editor timeEditor = mDiskLruCache.edit(TIME_STAMP + hashKey);
      OutputStream timeOutputStream = timeEditor.newOutputStream(0);
      timeOutputStream.write(String.valueOf(System.currentTimeMillis()).getBytes());
      timeOutputStream.flush();
      timeOutputStream.close();
      timeEditor.commit();
      mDiskLruCache.flush();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }


  public static String readCache(String key) {
    Timber.d("===tag=== real cache key " + key);
    if (mDiskLruCache == null || isNeedUpdateData(key)) {
      return null;
    }

    InputStream is = null;
    try {
      String hashKey = cacheKeyForDisk(key);
      DiskLruCache.Snapshot snapShot = mDiskLruCache.get(hashKey);
      if (snapShot != null) {
        is = snapShot.getInputStream(0);
        StringBuffer out = new StringBuffer();
        mByteBuffer = new byte[CACHE_BYTE_SIZE];
        int n;
        while ((n = is.read(mByteBuffer)) != -1) {
          out.append(new String(mByteBuffer, 0, n));
        }
        mByteBuffer = null;
        return out.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  //  depend last time stamp to calculate if need update data
  private static boolean isNeedUpdateData(String key) {
    try {
      String hashKey = TIME_STAMP + cacheKeyForDisk(key);
      // time stamp
      long dataLastTimeStamp;

      DiskLruCache.Snapshot snapShot = mDiskLruCache.get(hashKey);
      if (snapShot != null) {
        InputStream is = snapShot.getInputStream(0);
        StringBuffer out = new StringBuffer();
        mByteBuffer = new byte[CACHE_BYTE_SIZE];
        int n;
        while ((n = is.read(mByteBuffer)) != -1) {
          out.append(new String(mByteBuffer, 0, n));
        }
        mByteBuffer = null;
        is.close();
        dataLastTimeStamp = Long.valueOf(out.toString());
        if (System.currentTimeMillis() <= dataLastTimeStamp + CACHE_TIME_DIVIDER) {
          return false;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }


  //  language and md5
  private static String cacheKeyForDisk(final String key) {
    String cacheKey;
    try {
      final MessageDigest mDigest = MessageDigest.getInstance("MD5");
      mDigest.update(key.getBytes());
      cacheKey = bytesToHexString(mDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      cacheKey = String.valueOf(key.hashCode());
    }
    return cacheKey;
  }

  private static String bytesToHexString(byte[] bytes) {
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


  private static File getDiskCacheDir(Context context, String uniqueName) {
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