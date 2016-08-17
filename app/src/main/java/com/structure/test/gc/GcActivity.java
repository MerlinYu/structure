package com.structure.test.gc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by yuchao on 8/17/16.
 */
public class GcActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  public void Gc(Activity context) {

    ReferenceQueue<Activity> queue = new ReferenceQueue<>();
    WeakReference<Activity> weakReference = new WeakReference<>(context,queue);



  }
}
