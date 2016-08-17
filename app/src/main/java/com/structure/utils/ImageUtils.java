package com.structure.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

/**
 * Created by yuchao on 8/10/16.
 */
public class ImageUtils {



  public Bitmap scaleBitmap(String filePath) {
    if (TextUtils.isEmpty(filePath)) {
      return null;
    }
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(filePath,options);
    // 长宽比
    double scale =  ((double)options.outHeight/(double)options.outWidth);
    int minHeight = 100;
    int minWidth = 100;
    int maxHeight = 450;
    int maxWidth = 450;

    int reqWidth = 0;
    int reqHeight = 0;

    LogV("=================filepath=================== "+filePath);
    LogV("scale = " + scale);
    LogV("outHeight, outWidth " + options.outHeight +", "+ options.outWidth );

    if (options.outWidth >= minWidth && options.outWidth <= maxWidth
        && options.outHeight >= minHeight && options.outHeight <= maxHeight) {
      options.inSampleSize = 1;
    } else {
      // 以最大高度为准
      if (scale >= 1) {
        reqHeight = maxHeight;
        reqWidth = (int)(maxHeight / scale) ;
      } else {
        reqWidth = maxWidth;
        reqHeight = (int)(maxHeight * scale) ;
      }
      LogV("reqHeight,reqWidth = " + reqHeight +"," + reqWidth);
      options.inSampleSize = calculateSampleSize(options,reqWidth,reqHeight);
    }
    options.inScaled = true;
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(filePath,options);
  }

  public int calculateSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
      final int halfHeight = height / 2;
      final int halfWidth = width / 2;
      while ((halfHeight / inSampleSize) > reqHeight
          && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
      }
    }
    LogV("sample size " + inSampleSize);
    return inSampleSize;
  }

  private Bitmap degreeBitmap(String filePath,Bitmap bitmap) {
    ExifInterface exif = null;
    try {
      exif = new ExifInterface(filePath);
    } catch (IOException e) {
      e.printStackTrace();
      exif = null;
    }
    int degree = 0;
    if(null != exif) {
      int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
          ExifInterface.ORIENTATION_UNDEFINED);
      switch (ori) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          degree = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          degree = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          degree = 270;
          break;
        default:degree = 0;break;
      }
    }
    if (degree!=0) {
      Matrix m = new Matrix();
      m.postRotate(degree);
      bitmap = Bitmap.createBitmap(bitmap, 0,0,
          bitmap.getWidth(),bitmap.getHeight(),
          m,true);
    }
    return bitmap;
  }




  private void LogV(String log) {
    Log.v("===scale=== ", log);
  }


}
