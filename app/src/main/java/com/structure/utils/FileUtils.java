package com.structure.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuchao on 7/1/16.
 */
public class FileUtils {

  public static final String FILE_CACHE = "cache.txt";
  public static final String FILE_TEMP_DIR = "temp/";


  public static String writeCacheFile(Context ctx,String fileName, byte[] data){
    WeakReference<Context> ctxWeak = new WeakReference<>(ctx);
    if (TextUtils.isEmpty(fileName)) {
      fileName = FILE_CACHE;
    }
    if (!fileName.endsWith(".txt") && !fileName.endsWith(".json")) {
      fileName = fileName + ".txt";
    }
    File file = new File(getCacheDir(ctxWeak.get()), fileName);
    Log.d("===write file path===", file.getAbsolutePath());

    if (writeFile(file,data)) {
      return file.getAbsolutePath();
    }
    return null;
  }

  public static boolean writeFile(File file, byte[] data) {
    if (null==file) {
      return false;
    }
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      out.write(data);
      out.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (null != out) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  public static byte[] readFileByte(File file) {
    if (null == file || !file.exists()) {
      return null;
    }
    try {
      InputStream is = new FileInputStream(file.getAbsolutePath());
      byte[] buffer = new byte[1024];
      ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
      int len = 0;
      while ((len = is.read(buffer)) != -1) {
        byteOutputStream.write(buffer,0, len);
      }
      try{
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      Log.d("===read file data=== " , byteOutputStream.toString());
      return byteOutputStream.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  public static File getCacheDir(Context context) {
    File  dir = context.getCacheDir();
    return dir;
  }

  public static File getSdCacheDir(Context ctx){
    File file = ctx.getExternalCacheDir();
    return file;
  }

  public static File getTempDir() {
    if (!isExternalStorageAvailable()) {
      return null;
    }
    File dir = new File(Environment.getExternalStorageDirectory(), FILE_TEMP_DIR);
    if (dir.exists() || dir.mkdir() || dir.isDirectory()) {
      return dir;
    }
    return null;
  }

  public static boolean isExternalStorageAvailable(){
    return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
  }

  public static Bitmap downloadImage(String url) {
    if(TextUtils.isEmpty(url)) {
      return null;
    }
    HttpURLConnection connection = null;
    Bitmap bitmap = null;
    try {
      URL urlObj = new URL(url);
      connection = (HttpURLConnection) urlObj.openConnection();
      connection.setConnectTimeout(10*1000);
      connection.setReadTimeout(10*1000);
      int responseCode = connection.getResponseCode();
      if (responseCode >= 300) {
        connection.disconnect();
        throw new IOException("load image error");
      }
      InputStream inputStream = connection.getInputStream();
      String path = FileUtils.writeFile(inputStream,true);
      System.out.println("load intent bitmap path: " + path);
      bitmap = BitmapFactory.decodeFile(path);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (null != connection) {
        connection.disconnect();
      }
    }
    System.out.println("null == bitmap " + (null == bitmap));
    return bitmap;
  }



  public static String writeFile(InputStream stream,boolean isCloseStream) {
    if (null == stream){
      return null;
    }
    File file = new File(getTempDir(),System.currentTimeMillis() + ".png");
    try {
      FileOutputStream outputStream = new FileOutputStream(file);
      byte[] buffer = new byte[4096];
      int length = 0;
      while(-1 != (length=stream.read(buffer)) ) {
        outputStream.write(buffer,0,length);
      }
      outputStream.flush();
      outputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (isCloseStream) {
        try{
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return file.getAbsolutePath();
  }


}
