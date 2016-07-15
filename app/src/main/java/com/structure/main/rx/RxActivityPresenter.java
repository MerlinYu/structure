package com.structure.main.rx;

import com.structure.RetrofitApiService;
import com.structure.api.TestAPI;
import com.structure.base.ActivityModule;
import com.structure.base.ActivityPresenter;
import com.structure.main.data.weather.WeatherData;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by yuchao on 7/13/16.
 *
 */
public class RxActivityPresenter extends ActivityPresenter<RxActivity, ActivityModule<TestAPI>> {

  Observable<String>  observable;
  Subscriber<String> subscriber;
  final static String TAG  = "===RxActivityPresenter=== ";

  public RxActivityPresenter(RxActivity mDisplay) {
    super(mDisplay, RetrofitApiService.create(TestAPI.class, TAG));
  }


  public void createObservable() {

    // 完整创建方法
    observable = Observable.create(
        new Observable.OnSubscribe<String>() {
          @Override
          public void call(Subscriber<? super String> subscriber) {
            System.out.println("observable  send :hello world ");
            String hello = "hello world ";
            subscriber.onNext(hello);
            subscriber.onCompleted();
          }
        }
    );
    // 简单
//    observable = Observable.just("hello world");
  }

  public void createSubscriber() {
    // 完整创建方法
    subscriber = new Subscriber<String>() {
      @Override
      public void onCompleted() {
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onNext(String s) {
        System.out.println("subscriber " + s);
      }
    };

  }

  // 订阅
  public void subscribe() {
    if (null == observable || null == subscriber) {
      return;
    }
    observable.subscribe(subscriber);
    // 其他简捷写法
    observable.just("hello world").subscribe(s -> {
      System.out.println("subscriber " + s);
    });
    observable = Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {

        System.out.println("hello");
        subscriber.onNext("hello");
      }
    });
//    observable.subscribe();

  }

  public void mapDealObservableResult() {
    //完整写法，可以用Lambda精简写法
  /*  observable.just("hello world").map(new Func1<String, String>() {
      @Override
      public String call(String s) {
        return s + " rx java";
      }
    }).subscribe(new Action1<String>() {

      @Override
      public void call(String o) {
        System.out.println(o);
      }
    });*/

    observable.just("hello world")
        .map(s -> s + " rx java")
        .subscribe(o -> {System.out.println(o);
    });

  }

  // 操作符，flatmap, filter,form
  public void operationalCharacter() {

    Observable<List<String>> listObservable = Observable.create(new Observable.OnSubscribe<List<String>>() {
      @Override
      public void call(Subscriber<? super List<String>> subscriber) {
        List<String> list = new ArrayList<>(6);
        list.add("world");
        list.add("");
        list.add("merlin");
        subscriber.onNext(list);
        subscriber.onCompleted();
      }
    });

    listObservable
        // list 变 string 处理,流处理 .from()
        .flatMap(strings -> Observable.from(strings))
        // 过滤
        .filter(string -> null != string && string.length() > 0 )
        // 结果变换
        .map(string -> "hello " + string)
        // 结果数量限制
        .take(5)
        // 额外操作
        .doOnNext(s -> System.out.println("do something for " + s))
        .subscribe(string -> System.out.println(string));


  }

  // 异常和完成结果通知
  public void errorAndCompleted() {
    observable.just("world","merlin", null)
        .map(string -> {
          //抛出异常
          System.out.println("string length " + string.length());
          return string;})
        .subscribe(s -> System.out.println("hello " + s),
            throwable -> throwable.printStackTrace(),
            () -> System.out.println("subscribe job completed"));
  }

  //观察者和订阅者运行的线程
  public void runThreadTime() {
    Observable<WeatherData> weather = mModule.asRetrofit()
        .getObservableWeatherFromApi("shenzhen", "ea574594b9d36ab688642d5fbeab847e");
    Subscription subscription = weather
        // 观察者运行线程，一般进行异步耗时操作
        .subscribeOn(Schedulers.io())
        // 订阅者运行线程，如果需要刷新UI，需要指定
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(weatherData -> {
          mDisplay.weatherToast(weatherData);
        });

    //TODO:销毁时取消
    if (null != subscription && subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

  public void mergeObservable() {
    // Observable 其中某个observable发生错误并不影响合并
    Observable.mergeDelayError(Observable.just("merlin "), Observable.just("world "), Observable.just("friends"))
        .subscribe(s -> System.out.println(" hello "+s));

  }
}
