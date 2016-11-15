package com.structure.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.structure.utils.ImageUtils;

/**
 * Created by yuchao on 9/10/16.
 */
public class ThreadView extends SurfaceView implements SurfaceHolder.Callback{

  private SurfaceHolder holder;
  private ViewThread viewThread;

  public ThreadView(Context context) {
    super(context);
    this.holder = this.getHolder();
    this.holder.addCallback(this);
    viewThread = new ViewThread(holder);

  }

  public ThreadView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    Log.v("===tag===", "surfaceCreated");

    viewThread.start();

  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {


  }


}

class ViewThread extends Thread{

  private SurfaceHolder holder;
  private boolean isRun;

  public ViewThread(final SurfaceHolder holder) {
    this.holder = holder;
  }

  public boolean getIsRun() {
    return isRun;
  }

  public void setRun(boolean isRun) {
    this.isRun = isRun;
  }


  @Override
  public void run() {
    super.run();
    //TODO:内存泄漏的情形
    if (holder == null) {
      return;
    }
    synchronized (holder) {
      Canvas canvas ;
      canvas = holder.lockCanvas();
      canvas.drawColor(Color.WHITE);
      Paint paint = new Paint();
      paint.setColor(Color.BLACK);
      paint.setTextSize(36);
      Rect textRect = new Rect();
      String str = "This is a surface view";
      paint.getTextBounds(str,0, str.length(),textRect);
      canvas.drawText(str,20,50,paint);
      holder.unlockCanvasAndPost(canvas);
    }
  }
}
