package com.structure.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.net.Uri;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 播放 gif 动画以后可以添加Looper , lopperTime 等类
 *
 */

public class GifMovieImageView extends ImageView {

  private InputStream mInputStream;
  private Movie mMovie;
  private int mMovieWidth, mMovieHeight;
  private long mStart;
  private Context mContext;

  public GifMovieImageView(Context context) {
    super(context);
    this.mContext = context;
  }

  public GifMovieImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public GifMovieImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.mContext = context;
    if (attrs.getAttributeName(1).equals("background")) {
      int id = Integer.parseInt(attrs.getAttributeValue(1).substring(1));
      setGifImageResource(id);
    }
  }


  private void init() {
    setFocusable(true);
    mMovie = Movie.decodeStream(mInputStream);
    mMovieWidth = mMovie.width();
    mMovieHeight = mMovie.height();
//    Log.d("===tag===width ", String.valueOf(mMovieWidth));
//    Log.d("===tag===height ", String.valueOf(mMovieHeight));

    requestLayout();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);


  }

  @Override
  protected void onDraw(Canvas canvas) {

    long now = SystemClock.uptimeMillis();

    if (mStart == 0) {
      mStart = now;
    }

    if (mMovie != null) {

      int duration = mMovie.duration();
      if (duration == 0) {
        duration = 1000;
      }

      int relTime = (int) ((now - mStart) % duration);

      mMovie.setTime(relTime);

      int mViewWidth = getWidth();
      int mViewHeight = getHeight();

      float scaleX = 1.0f;
      float scaleY = 1.0f;


      scaleY = (float) mViewHeight / (float) mMovieHeight;

      scaleX = (float) mViewWidth / (float) mMovieWidth;
      //等比例缩放, 取大的比例值
      float sampleRate = scaleX > scaleY ? scaleX : scaleY;

      System.out.println("mViewWidth, mViewHeight " + mViewWidth + " , " + mViewHeight);
      System.out.println("scalex, sacley " + scaleX + " , " + scaleY);

      canvas.scale(sampleRate, sampleRate);

      mMovie.draw(canvas, 0, 0);
      invalidate();
    }
  }

  public void setGifImageResource(int id) {
    mInputStream = mContext.getResources().openRawResource(id);
    init();
  }

  public void setGifImageUri(Uri uri) {
    try {
      mInputStream = mContext.getContentResolver().openInputStream(uri);
      init();
    } catch (FileNotFoundException e) {
      Log.e("GIfImageView", "File not found");
    }
  }
}
