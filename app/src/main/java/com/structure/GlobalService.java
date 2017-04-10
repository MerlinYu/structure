package com.structure;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by yuchao on 4/6/17.
 */

public class GlobalService extends Service {

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
