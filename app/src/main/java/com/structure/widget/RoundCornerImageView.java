package com.structure.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.structure.R;

/**
 * Created by yuchao on 7/5/16.
 * 圆角view
 */
public class RoundCornerImageView extends ImageView {

  private final static int DEFAULT_RADIUS = 20;
  private final static ScaleType SCALE_TYPE_ENTER   = ScaleType.CENTER_CROP;
  private static final Bitmap.Config BITMAP_CONFIG  = Bitmap.Config.ARGB_8888;
  private static final int COLOR_DRAWABLE_DIMENSION = 2;

  private Bitmap mBitmap;
  private BitmapShader mBitmapShader;

  private RectF mDrawableRect = new RectF();
  private Paint mBitmapPaint = new Paint();
  private float mDrawableRadius;


  public RoundCornerImageView(Context context) {
    super(context);
  }

  public RoundCornerImageView(Context context, AttributeSet attrs) {
    this(context,attrs,0);
  }

  public RoundCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView,defStyleAttr,0);
    mDrawableRadius = type.getDimensionPixelSize(R.styleable.RoundCornerImageView_round_corner_radius,DEFAULT_RADIUS);
    type.recycle();
  }

  public void setCornerRadius(float radius) {
    mDrawableRadius = radius;
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (getDrawable() == null) {
      return;
    }
    try {
      // 在ondraw中一般不进行重新生成bitmap的操作
      //TODO:
      mBitmap = getBitmapFromDrawable(getDrawable());
      setBitmapShader();
      canvas.drawRoundRect(mDrawableRect, mDrawableRadius, mDrawableRadius, mBitmapPaint);
    } catch (Exception e) {
      e.printStackTrace();
      super.onDraw(canvas);
    }
  }



  private void setBitmapShader(){
    if (null == mBitmap) {
      return;
    }

    mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    float scaleX = 1.0f;
    float scaleY = 1.0f;
    scaleX = getWidth()*1.0f/mBitmap.getWidth();
    scaleY = getHeight()*1.0f/mBitmap.getHeight();
    Matrix mMatrix = new Matrix();
    mMatrix.setScale(Math.max(scaleX, scaleY),Math.max(scaleX, scaleY));
    mBitmapShader.setLocalMatrix(mMatrix);
    mBitmapPaint.setAntiAlias(true);
    mBitmapPaint.setShader(mBitmapShader);
    mDrawableRect.set(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingRight(), getHeight()- getPaddingBottom());
  }

  private Bitmap getBitmapFromDrawable(Drawable drawable) {
    if (null == drawable) {
      return null;
    }
    if (drawable instanceof BitmapDrawable) {
      return ((BitmapDrawable) drawable).getBitmap();
    }
    try {
      Bitmap bitmap;
      if (drawable instanceof ColorDrawable) {
        bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, Parcelable.CONTENTS_FILE_DESCRIPTOR,BITMAP_CONFIG);
      } else {
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), BITMAP_CONFIG);
        Log.v("===tag===,height,width", String.valueOf(drawable.getIntrinsicHeight())
            + ", " + String.valueOf(drawable.getIntrinsicWidth()));
      }
      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0,0,canvas.getWidth(),getHeight());
      drawable.draw(canvas);
      return bitmap;
    } catch (OutOfMemoryError e){
      e.printStackTrace();
    }
    return null;
  }
}
