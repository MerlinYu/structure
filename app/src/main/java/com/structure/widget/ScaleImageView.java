package com.structure.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import timber.log.Timber;

/**
 * Created by yuchao on 3/4/17.
 * 选中时view 变大,未被选中时view恢复原来的大小
 *
 */

public class ScaleImageView extends ImageView {


  private boolean isSelected;
  private int selectedWidth;
  private int selectedHeight;


  public ScaleImageView(Context context) {
    super(context);
  }

  public ScaleImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  //  正常的大小
  public void setImageSize(int width, int height) {


    notify();

  }


  //  选中的大小
  public void setSelectedSize(int width, int height) {
    selectedWidth = width;
    selectedHeight = height;
    if (isSelected) {

    }
    notify();


  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    Timber.d("===tag=== measure " + widthMeasureSpec + " , " + heightMeasureSpec);
    if (isSelected) {


    }




    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }




}
