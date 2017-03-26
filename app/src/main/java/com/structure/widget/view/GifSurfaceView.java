package com.structure.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.structure.R;

import java.io.InputStream;
import java.lang.ref.SoftReference;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.R.attr.id;

/**
 * Created by yuchao on 3/13/17.
 *
 * Gif surface view 采用在线程中刷新背景图片的方法实现gif动画
 *  NOTE: 不是最优的解决方案,只是一次实践
 */
public class GifSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

  private SurfaceHolder mHolder;
  private GifViewThread mGifThread;
  private GifAnimCallback mGifCallback;


  public interface GifAnimCallback {
    void onGifCancel();

    void onGifEnd();
  }


  public GifSurfaceView(Context context) {
    super(context);
    init(context);
  }

  public GifSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public GifSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  public GifSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }


  private void init(Context context) {
    mHolder = getHolder();
    mHolder.addCallback(this);
    mGifThread = new GifViewThread(new SoftReference<>(mHolder), new SoftReference<>(context), mGifCallback);
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    cancelAnim();
  }

  public void startGifAnim() {
    mGifThread.startGif();
  }

  public void setGifLooper(boolean looper) {
    mGifThread.setLopper(looper);
  }

  public boolean getGifEnd() {
    return mGifThread.isEnd();
  }

  public void setAnimDuration(int time) {
    mGifThread.setGifDurationTime(time);
  }

  public void setAnimFrameCount(int count) {
    mGifThread.setAnimFrameCount(count);
  }

  public void setResourcePref(String resourcePref) {
    mGifThread.setDrawableResourcePref(resourcePref);
  }

  public void setGifCallback(GifAnimCallback callback) {
    this.mGifCallback = callback;
  }


  public void cancelAnim() {
    mGifThread.cancel();
    mGifThread.setLopper(false);
    try {
      mGifThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  class GifViewThread extends Thread {

    private SoftReference<SurfaceHolder> holderWeak;
    private SoftReference<Context> contextWeak;
    private boolean cancel;
    private boolean isEnd;
    private boolean isLopper;

    private Drawable cacheDrawable;
    //    动画图片资源的前缀
    private String drawableResourcePref;
    // 帧动画的帧数
    private int animFrameCount;
    private int currentframeIndex = 0;
    private int cacheIndex = 0;

    // 帧动画的的时间
    private int gifDurationTime;

    private GifAnimCallback callback;
    private Subscription subscription;

    private  Movie mMovie;



    public GifViewThread(SoftReference<SurfaceHolder> softReference, SoftReference<Context> contextWeak) {
      this.holderWeak = softReference;
      this.contextWeak = contextWeak;
    }

    public GifViewThread(SoftReference<SurfaceHolder> softReference,
                         SoftReference<Context> contextWeak, GifAnimCallback callback) {
      this.holderWeak = softReference;
      this.contextWeak = contextWeak;
      this.callback = callback;
    }

    public void startGif() {
      cancel = false;
      InputStream mInputStream = getContext().getResources().openRawResource(R.drawable.launch);

      mMovie = Movie.decodeStream(mInputStream);
      start();
    }


    @Override
    public void run() {
      super.run();
      if (holderWeak.get() == null) {
        return;
      }

      long now = System.currentTimeMillis();

      while (!cancel && !isEnd) {
        try {
          synchronized (holderWeak.get()) {
            drawMovieFrame();
            cancel = true;
            if (currentframeIndex >= animFrameCount) {
              isEnd = true;
              if (isLopper) {
                currentframeIndex = 0;
                isEnd = false;
              } else {
                long endTime = System.currentTimeMillis();
                Timber.d("===tag=== run time " + (endTime - now));

                if (mGifCallback != null) {
                  mGifCallback.onGifEnd();
                }
              }
            } else {
//              int frameDuration = gifDurationTime / animFrameCount;
//              Timber.d("===tag=== duration time " + frameDuration);

              Thread.sleep(0);
            }
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      if (isCancel() && mGifCallback != null) {
        mGifCallback.onGifCancel();
      }
    }


    private void drawMovieFrame() {
      Timber.d("===tag==== drawMovieFrame");
      long now = SystemClock.uptimeMillis();
      long mStart = 0;
      Canvas canvas = holderWeak.get().lockCanvas();
      if (canvas == null) {
        return;
      }

      if (mStart == 0) {
        mStart = now;
      }
      if (mMovie != null) {
        int duration = mMovie.duration();
        if (duration == 0) {
          duration = 1000;
        }
        // 算出需要显示第几帧
        int relTime = (int) ((now - mStart) % duration);
//      显示第几帧
        mMovie.setTime(relTime);
        Timber.d("===tag=== frame " + relTime);
        mMovie.draw(canvas, 0, 0);
      }
      holderWeak.get().unlockCanvasAndPost(canvas);
    }

    private void drawFrame() {
      Canvas canvas;
      canvas = holderWeak.get().lockCanvas();
      if (canvas == null) {
        return;
      }


      Drawable drawable = getAnimDrawable(currentframeIndex);
      if (drawable == null) {
        return;
      }
      Paint paint = new Paint();
      Bitmap bitmapDrawable = ((BitmapDrawable)drawable).getBitmap();
      if (bitmapDrawable != null) {
        canvas.drawBitmap(((BitmapDrawable) drawable).getBitmap(), 0, 0, paint);
      }


      holderWeak.get().unlockCanvasAndPost(canvas);
      currentframeIndex++;
    }

    //双缓冲
    private Drawable getCacheDrawable(int index) {
      if (index < 0 || index > animFrameCount) {
        return null;
      }
      Drawable drawable;
      if (index == 0) {
        drawable = getAnimDrawable(index);
      } else {
        if (cacheDrawable != null && cacheIndex == index) {
          drawable = cacheDrawable;
          cacheDrawable = null;
        } else {
          drawable = getAnimDrawable(index);
        }
      }

      subscription = Observable.create(new Observable.OnSubscribe<Drawable>() {
        @Override
        public void call(Subscriber<? super Drawable> subscriber) {
          Drawable drawable = getAnimDrawable(index + 1);
          if (drawable != null) {
            cacheIndex = index + 1;
            subscriber.onNext(drawable);
            subscriber.onCompleted();
          } else {
            subscriber.onNext(null);
            subscriber.onCompleted();
          }
        }
      })
          .observeOn(Schedulers.newThread())
          .filter(drawable1 -> drawable1 != null)
          .subscribe(drawable1 -> {
            cacheDrawable = drawable1;
          }, Throwable::printStackTrace, () -> {
          });

      return drawable;
    }

    private Drawable getAnimDrawable(int index) {
      String drawableStr = drawableResourcePref + String.valueOf(index);
      int drawableID = contextWeak.get().getResources().getIdentifier(drawableStr, "mipmap", "com.structure");
      if (drawableID <= 0) {
        Timber.e("can't find drawable mipmap resource " + drawableStr);
        return null;
      }
      return contextWeak.get().getResources().getDrawable(drawableID);
    }

    public boolean isCancel() {
      return cancel;
    }

    public boolean isEnd() {
      return isEnd;
    }

    public boolean isLopper() {
      return isLopper;
    }

    public void setDrawableResourcePref(String drawableResourcePref) {
      this.drawableResourcePref = drawableResourcePref;
    }

    public void setAnimFrameCount(int animFrameCount) {
      this.animFrameCount = animFrameCount;
    }

    public String getDrawableResourcePref() {
      return drawableResourcePref;
    }

    public int getAnimFrameCount() {
      return animFrameCount;
    }


    public void setGifDurationTime(int time) {
      gifDurationTime = time;
    }

    public int getGifDuration() {
      return gifDurationTime;
    }

    public void cancel() {
      cancel = true;
      isEnd = true;

    }

    public void setLopper(boolean lopper) {
      isLopper = lopper;
    }

    public boolean getLopper() {
      return isLopper;
    }

  }

}
