package com.structure.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.structure.R;

/**
 * Created by yuchao on 8/19/16.
 * 自定义view，根据ratio重新计算view的高度。
 * ratio为宽与高之比
 */
public class CustomerView extends ImageView {

  private float ratio;
  private final static float DEFAULT_RATIO = 0.0f;
  public CustomerView(Context context) {
    super(context);
  }

  public CustomerView(Context context, AttributeSet attrs) {
    this(context,attrs,0);
  }

  public CustomerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomerView,defStyleAttr,0);
    ratio = array.getFloat(R.styleable.CustomerView_ratio,DEFAULT_RATIO);
    array.recycle();
  }


  @Override
  public boolean onFilterTouchEventForSecurity(MotionEvent event) {
    return super.onFilterTouchEventForSecurity(event);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // motto
    String motto = "hello customer view";
    Paint paint = new Paint();
    if (!TextUtils.isEmpty(motto)) {
      paint.setColor(getResources().getColor(R.color.black));
      paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.card_font_normal_size));
      Rect textRect = new Rect();
      paint.getTextBounds(motto,0, motto.length(), textRect);
      int paddingLeft = 20;
      int paddingTop = 50;
      canvas.drawText(motto, paddingLeft, paddingTop, paint);
    }

  }

  /**
   * widthMeasureSpec 模式加宽度
  * **/
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (ratio == DEFAULT_RATIO) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      return;
    }
    // 三种模式：MeasureSpec.EXACTLY ,MeasureSpec.AT_MOST,MeasureSpec.UNSPECIFIED
    int mode = MeasureSpec.getMode(widthMeasureSpec);
    final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    String modeStr;
    switch (mode) {
      case MeasureSpec.AT_MOST:
        modeStr = "AT_MOST";
        break;
      case MeasureSpec.EXACTLY:
        modeStr = "EXACTLY";
        break;
      case MeasureSpec.UNSPECIFIED:
        modeStr = "UNSPECIFIED";
        break;
      default:
        modeStr = "";
        break;
    }
    //TODO:针对于不同的模式进行measure处理
    mode = MeasureSpec.EXACTLY;
    LogV("ratio : " + ratio + " , type : " + modeStr + " width,height " + widthSize + ","  + heightSize);
    if (widthSize == 0 && heightSize == 0) {
      // If there are no constraints on size, let Button measure
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      return;
    }
    widthMeasureSpec = measureWidth(false, widthSize, mode);
    heightMeasureSpec = measureHeight(true, widthSize, mode);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
  }

  @Override
  protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }



  private int measureWidth(boolean isWidthDynamic, int size,int mode) {
    if (isWidthDynamic) {
      return MeasureSpec.makeMeasureSpec((int) (size * ratio), mode);
    } else {
      return MeasureSpec.makeMeasureSpec(size, mode);
    }
  }

  private int measureHeight(boolean isHeightDynamic, int size,int mode) {
    if (isHeightDynamic) {
      return MeasureSpec.makeMeasureSpec((int) (size / ratio), mode);
    } else {
      return MeasureSpec.makeMeasureSpec(size, mode);
    }
  }

  public void setRatio(float ratio) {
    this.ratio = ratio;
    requestLayout();
  }

  public float gerRatio() {
    return ratio;
  }


  private void LogV(String string) {
    if (string == null) {
      return;
    }
    Log.v("===view=== ",string);
  }


}
