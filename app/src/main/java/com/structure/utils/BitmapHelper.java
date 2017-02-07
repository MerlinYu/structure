package com.structure.utils;

/**
 * Created by yuchao on 12/27/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.structure.utils.bitmap.BlurHelper;

import java.io.IOException;
import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 */
public final class BitmapHelper {

  private BitmapHelper() {}

  /**
   * Resizes the bitmap from local storage.
   *
   * @param targetWidth   The width of the result bitmap.
   * @param targetHeight  The height of the result bitmap.
   * @param imageFilePath The local image file path.
   * @return A resized bitmap.
   */
  public static Bitmap resize(int targetWidth, int targetHeight, String imageFilePath) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imageFilePath, options);
    int originalWidth = options.outWidth;
    int originalHeight = options.outHeight;

    int scaleFactor = Math.min(originalWidth / targetWidth, originalHeight / targetHeight);

    options.inJustDecodeBounds = false;
    options.inSampleSize = scaleFactor;
    return BitmapFactory.decodeFile(imageFilePath, options);
  }


  public static Subscription setBlurImage(WeakReference<View> viewReference, String path, int targetWidth, int targetHeight) {
    Context context = viewReference.get().getContext();

    Subscription subscription = loadHttpImage(context, path,targetWidth,targetHeight)
        .map(bitmap -> loadBlurImage(context, bitmap))
        .flatMap(new Func1<Bitmap, Observable<BitmapDrawable>>() {
          @Override
          public Observable<BitmapDrawable> call(Bitmap bitmap) {
            Observable<BitmapDrawable> drawableObservable = Observable.create(new Observable.OnSubscribe<BitmapDrawable>() {
              @Override
              public void call(Subscriber<? super BitmapDrawable> subscriber) {
                BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                subscriber.onNext(drawable);
              }
            });
            return drawableObservable;
          }
        })
        .filter(drawable -> {return drawable != null;})
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(drawable -> {
          LogUtils.log(" et image ");
          if (drawable == null) {
            LogUtils.log(" drawable == null ");

          } else {
            LogUtils.log(" drawable != null ");
          }


          View view = viewReference.get();
          if (view != null) {
            LogUtils.log(" view != null ");
//            view.set

            view.setBackgroundDrawable(drawable);
            if (drawable instanceof BitmapDrawable) {
//              drawable.getBitmap().recycle();
            }
          }
        });
    return subscription;
  }


  // load image form intent
  public static Observable<Bitmap> loadHttpImage(Context context, String url, int targetWidth, int targetHeight) {
    Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
      @Override
      public void call(Subscriber<? super Bitmap> subscriber) {
        try {
          Bitmap bitmap = Picasso.with(context)
              .load(url)
              .resize(targetWidth, targetHeight)
              .centerInside()
              .get();

          subscriber.onNext(bitmap);

        } catch (IOException e) {
          e.printStackTrace();
          subscriber.onError(e);
        }
      }
    });
    return observable;
  }

  public static Bitmap loadBlurImage(Context context, Bitmap bitmap) {
//    BlurHelper.blur(bitmap, context, 0);
//    return bitmap;
    Bitmap re = BlurHelper.blur(bitmap, context, 50);
    if (re == null) {
      LogUtils.log(" blur bull;");
    }
    return re;
  }
}
