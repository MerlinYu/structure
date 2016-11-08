package com.structure.welcome;

import android.graphics.Bitmap;

import com.structure.RetrofitApiService;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.utils.FileUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuchao on 7/13/16.
 *
 */
public class WelcomeActivityPresenter extends ActivityPresenter<WelcomeActivity, ActivityModule<WelcomeApi>>{

  private static final String TAG = "WelcomeActivityPresenter";
  private Subscription sub;
  public WelcomeActivityPresenter(WelcomeActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(WelcomeApi.class, TAG));
  }


  public void loadWelcome() {
    // Observable 其中某个observable发生错误并不影响合并
    sub = Observable.mergeDelayError(loadADFromLocal().subscribeOn(Schedulers.io()),
        loadADFromIntent().subscribeOn(Schedulers.newThread()))
        .sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        .flatMap(bitmap -> {
          if (null == bitmap) {
            // 在Null情况下如果 直接返回Observable.empty会造成completed无法执行
            return Observable.timer(0,TimeUnit.SECONDS);
          } else {
            mDisplay.setWelcomeLayout(bitmap);
            return Observable.timer(2, TimeUnit.SECONDS);
          }
        })
        .subscribe(
            timer -> {
              System.out.println("========on next");
              mDisplay.startMainActivity();
              mDisplay.finish();
            },
            error -> {
              System.out.println("=========on error");
              mDisplay.startMainActivity();
              mDisplay.finish();
            },
            () ->{
              System.out.println("=========on finish");
            }
        );
  }

 // 从本地加载广告图片
  private Observable<Bitmap>  loadADFromLocal() {
    Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
      @Override
      public void call(Subscriber<? super Bitmap> subscriber) {
        //TODO:加载本地图片
        subscriber.onNext(null);
      }
    });
    return observable;

  }

  // 从网络加载广告图片
  private Observable<Bitmap>  loadADFromIntent() {
    String url = "http://img.zcool.cn/community/0111cb554231790000019ae98ea8df.jpg";
    Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
      @Override
      public void call(Subscriber<? super Bitmap> subscriber) {
        subscriber.onNext(FileUtils.downloadImage(url));
      }
    });
    return observable;
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (null != sub) {
      sub.unsubscribe();
    }
  }
}
