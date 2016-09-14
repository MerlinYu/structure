package com.structure.widget.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.structure.R;
import com.structure.base.BaseActivity;
import com.structure.utils.ImageUtils;
import com.structure.widget.CustomerView;
import com.structure.widget.ThreadView;

import butterknife.InjectView;

/**
 * Created by yuchao on 9/12/16.
 */
public class ViewActivity extends BaseActivity<ViewActivityPresenter> {

  @InjectView(R.id.view_content)
  LinearLayout mViewContent;
  @InjectView(R.id.drawable_img)
  CustomerView mDrawableImage;


  public static Intent createIntent(Context context) {
    Intent intent = new Intent(context,ViewActivity.class);
    return intent;
  }

  @Override
  public ViewActivityPresenter createPresenter() {
    return new ViewActivityPresenter(this,null);
  }

  @Override
  public int getContentViewId() {
    return R.layout.activity_view;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter.loadSurfaceView();
    mPresenter.loadCustomerView();
  }


  public void loadCustomerView() {

    String filePath = "/storage/emulated/0/Tencent/MicroMsg/1456464085573_36c7bd88.jpg_resize.jpg";
    Bitmap bitmap = ImageUtils.scaleBitmap(filePath);

    mDrawableImage.setImageBitmap(bitmap);
//    旋转角度
//    mDrawableImage.setRotation(120.0f);
//    mDrawableImage.setPivotX(10.0f);
//    mDrawableImage.setPivotY(10.0f);
//    mDrawableImage.setAlpha(0.5f);
//    mDrawableImage.invalidate();
//    Bitmap bitmap1 = mDrawableImage.getDrawingCache();
//    String str = mDrawableImage.getDisplay().getName();
//    mDrawableImage.setCameraDistance(180.0f);

  }



  public void loadSurfaceView() {

    ThreadView view = new ThreadView(this);

    view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

    mViewContent.addView(view);

  }



}
