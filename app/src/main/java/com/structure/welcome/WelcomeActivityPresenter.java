package com.structure.welcome;

import android.graphics.Bitmap;

import com.structure.RetrofitApiService;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.base.BaseModule;
import com.structure.main.MainActivity;
import com.structure.utils.FileUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuchao on 7/13/16.
 *
 */
public class WelcomeActivityPresenter extends ActivityPresenter<WelcomeActivity, ActivityModule<WelcomeApi>>{

  private static final String TAG = "WelcomeActivityPresenter";
  public WelcomeActivityPresenter(WelcomeActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(WelcomeApi.class, TAG));
  }


  public void loadWelcome() {
    // 2s 定时
  /*  Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        .map(l -> {
          mDisplay.startActivity(MainActivity.createIntent(mDisplay));
          mDisplay.finish();
          return null;
        })
        .subscribe();
  */
    // Observable 其中某个observable发生错误并不影响合并
    Observable.mergeDelayError(loadADFromLocal().subscribeOn(Schedulers.io()),
        loadADFromIntent().subscribeOn(Schedulers.newThread()))
        .filter(bitmap -> null != bitmap)
        .sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        .flatMap(bitmap -> {
          mDisplay.setWelcomeLayout(bitmap);
          return Observable.timer(3,TimeUnit.SECONDS);
        })
        .subscribe(
            timer -> { mDisplay.startMainActivity();},
            error -> { mDisplay.startMainActivity();},
            () ->    { mDisplay.startMainActivity();}
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
}
