package com.structure.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by yuchao on 7/11/16.
 */
public class LoadingDialog extends Dialog {
  View contentView;

  private static LoadingDialog dialog;
  public LoadingDialog(Context context) {
    super(context);
    setCancelable(true);
    setTitle(null);
    Window window = getWindow();
    if (null != window) {
      window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      window.setTitle(null);
    }
  }

  public LoadingDialog(Context context, int themeResId) {
    super(context, themeResId);
  }

  protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
    super(context, cancelable, cancelListener);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoadingTextView textView = new LoadingTextView(getContext());
    textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    contentView = textView;
    setContentView(contentView);
  }

  public static LoadingDialog getIntent(Context context) {
    dialog = new LoadingDialog(context);
    return dialog;
  }

  @Override
  public void dismiss() {
    setOnCancelListener(null);
    super.dismiss();

  }

  @Override
  public void show() {
    super.show();
    ((LoadingTextView) contentView).startAnim();
  }
}
