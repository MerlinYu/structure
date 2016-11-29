package com.structure.test.opengl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Window;

import com.structure.sevice.CommunicationService;
import com.structure.sevice.aidl.IData;

import timber.log.Timber;

import static android.R.attr.data;

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
//    glSurfaceView.draw();

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
