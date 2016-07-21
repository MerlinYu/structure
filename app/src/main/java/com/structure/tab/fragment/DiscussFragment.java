package com.structure.tab.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.structure.R;
import com.structure.base.fragment.BaseFragment;
import com.structure.base.fragment.BaseFragmentPresenter;

import butterknife.InjectView;

/**
 * Created by yuchao on 6/15/16.
 */
public class DiscussFragment extends BaseFragment<BaseFragmentPresenter> {

  @InjectView(R.id.anim_image)
  ImageView mAnimImage;

  @InjectView(R.id.move_anim)
  Button mMoveBtn;
  @InjectView(R.id.alpha_anim)
  Button mAlphaBtn;

  @InjectView(R.id.rotation_anim)
  Button mRotationBtn;

  @InjectView(R.id.anim_text)
  TextView mAnimText;

  @InjectView(R.id.property_btn1)
  Button propertyBtn1;
  @InjectView(R.id.property_btn2)
  Button propertyBtn2;

  @InjectView(R.id.property_btn3)
  Button propertyBtn3;

  @InjectView(R.id.property_btn4)
  Button propertyBtn4;


  /*@InjectView(R.id.)
      Button
*/
//  AnimationDrawable drawableAnim;

  @Override
  protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_discuss_layout,container, false);
  }

  @Override
  protected BaseFragmentPresenter createPresenter() {
    return null;
  }

  @Override
  protected void onViewCreated() {
    super.onViewCreated();
    viewAnim();
    propertyAnim();
  }

  //属性动画
  public void propertyAnim() {
    AnimationDrawable drawable = (AnimationDrawable)mAnimImage.getBackground();
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAnimImage.getLayoutParams();
    params.gravity = Gravity.CENTER;
    mAnimImage.setLayoutParams(params);

    propertyBtn1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        drawable.start();
        // x轴旋转
       /* ObjectAnimator.ofFloat(mAnimImage,"rotationX", 0.0f,360.f)
            .setDuration(1000)
            .start();
       */
        ObjectAnimator.ofFloat(mAnimImage,"rotationY", 0.0f,180.f).setDuration(1000).start();

       // ObjectAnimator.ofFloat(mAnimImage, "translationY", 0f,200f).setDuration(1000).start();
       // ObjectAnimator.ofFloat(mAnimImage, "translationX", 0f,200f).setDuration(1000).start();
        ObjectAnimator.ofFloat(mAnimImage, "rotation", 0,360f).setDuration(1000).start();

      }
    });

    propertyBtn2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 这种写法更节省动画资源
        PropertyValuesHolder p1=PropertyValuesHolder.ofFloat("rotation", 0,360f);
        PropertyValuesHolder p2=PropertyValuesHolder.ofFloat("translationX", 0f,200f);
        PropertyValuesHolder p3=PropertyValuesHolder.ofFloat("translationY", 0f,200f);
        ObjectAnimator.ofPropertyValuesHolder(mAnimImage, p1,p2,p3).setDuration(1000).start();

      }
    });

    propertyBtn3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ObjectAnimator animator1=ObjectAnimator.ofFloat(mAnimImage, "rotation", 0,360f);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(mAnimImage, "translationX", 0,200f);
        ObjectAnimator animator3=ObjectAnimator.ofFloat(mAnimImage, "translationY", 0,200f);

        AnimatorSet set=new AnimatorSet();
        //set.playTogether(animator1,animator2,animator3);
//        set.playSequentially(animator2,animator3);
        set.playSequentially(animator1,animator2,animator3);
        set.setDuration(1000);
        set.start();
      }
    });



  }

  // 一般动画，平移，缩放，旋转，透明
  public void viewAnim() {
    AnimationDrawable drawable = (AnimationDrawable)mAnimImage.getBackground();

    mMoveBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
       // anim.setInterpolator(new LinearInterpolator());
//        v.setAnimation(anim);

        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
          @Override
          public void onAnimationStart(Animation animation) {
            drawable.start();
            mAnimText.setVisibility(View.VISIBLE);
            mAnimText.setText(R.string.move_anim_start);
          }

          @Override
          public void onAnimationEnd(Animation animation) {
            drawable.stop();
            mAnimText.setText(R.string.move_anim_end);
          }
          @Override
          public void onAnimationRepeat(Animation animation) {

          }
        });
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAnimImage.getLayoutParams();
        params.gravity = Gravity.RIGHT;
        mAnimImage.setLayoutParams(params);
        mAnimImage.startAnimation(anim);
      }
    });

    mAlphaBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
          @Override
          public void onAnimationStart(Animation animation) {
            drawable.start();
            mAnimText.setVisibility(View.VISIBLE);
            mAnimText.setText(R.string.alpha_anim_start);
          }

          @Override
          public void onAnimationEnd(Animation animation) {
            drawable.stop();
            mAnimText.setText(R.string.alpha_anim_end);
          }

          @Override
          public void onAnimationRepeat(Animation animation) {

          }
        });
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAnimImage.getLayoutParams();
        params.gravity = Gravity.CENTER;
        mAnimImage.setLayoutParams(params);
        mAnimImage.startAnimation(animation);

      }
    });

    mRotationBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_roate);

        animation.setFillAfter(true);
        int duration = (int)animation.getDuration();
        animation.setAnimationListener(new Animation.AnimationListener() {
          @Override
          public void onAnimationStart(Animation animation) {
            drawable.start();
            mAnimText.setVisibility(View.VISIBLE);
            mAnimText.setText(R.string.rota_anim_start);
          }

          @Override
          public void onAnimationEnd(Animation animation) {
            drawable.stop();
            mAnimText.setText(R.string.rota_anim_end);
          }

          @Override
          public void onAnimationRepeat(Animation animation) {

          }
        });

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAnimImage.getLayoutParams();
        params.gravity = Gravity.CENTER;
        mAnimImage.setLayoutParams(params);
//        ((LinearLayout.LayoutParams) mAnimImage.getLayoutParams()).gravity =
        mAnimImage.startAnimation(animation);
      }
    });

  }


}
