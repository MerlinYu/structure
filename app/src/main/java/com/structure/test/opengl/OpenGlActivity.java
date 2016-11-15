package com.structure.test.opengl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by yuchao on 11/8/16.
 */

public class OpenGlActivity extends Activity {

  BaseGlSurfaceView glSurfaceView;


  public static Intent createIntent(Context context) {
    Intent intent = new Intent(context,OpenGlActivity.class);
    return intent;
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    glSurfaceView = new BaseGlSurfaceView(this);
    glSurfaceView.start();
    setContentView(glSurfaceView);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }


  @Override
  protected void onResume() {
    super.onResume();
    glSurfaceView.onResume();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onPause() {
    super.onPause();
    glSurfaceView.onPause();
  }
}
