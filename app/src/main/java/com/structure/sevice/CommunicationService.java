package com.structure.sevice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.structure.sevice.aidl.IData;

/**
 * Created by yuchao on 11/25/16.
 */

public class CommunicationService extends Service {

//  private IData.

  IData.Stub mBinder = new IData.Stub() {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public int getNumber() throws RemoteException {
      return 10;
    }
  };


  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }
}
