package com.structure.test.sensor;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.structure.R;
import com.structure.sevice.CommunicationService;
import com.structure.sevice.aidl.IData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchao on 11/21/16.
 */

public class SensorActivity extends Activity {
  public static final String ACTION = "android.intent.action.CommunicationService";


  public static Intent createIntent(Context context) {
    Intent intent = new Intent(context, SensorActivity.class);
    return intent;
  }

  private IData data;
  private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      data = IData.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      Log.i("ServiceConnection", "onServiceDisconnected() called");
    }
  };

  @Override
  protected void onDestroy() {
    unbindService(connection);
    super.onDestroy();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_linear);

    Intent intent = new Intent(this, CommunicationService.class);
    intent.setAction(ACTION);
    bindService(intent, connection, Context.BIND_AUTO_CREATE);


    TextView view = (TextView) this.findViewById(R.id.text);
    view.setText(getSensorText());

    Button button = (Button) this.findViewById(R.id.btn);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Toast.makeText(SensorActivity.this, String.valueOf(data.getNumber()), Toast.LENGTH_SHORT).show();

        } catch (RemoteException e) {

          e.printStackTrace();
        }
      }
    });








  }



  private String getSensorText() {
    StringBuffer buffer = new StringBuffer(1024);
    List<Sensor> sensors = SensorUtils.getSensorList(this);
    buffer.trimToSize();
    buffer.append("sensors size : " + sensors.size() + "\n");
    for (Sensor sensor : sensors) {
      buffer.append("\n");
      switch (sensor.getType()) {
        case Sensor.TYPE_GRAVITY:
          buffer.append("重力感应器");
          break;
        case Sensor.TYPE_ACCELEROMETER:
          buffer.append("加速度度");
          break;
        case Sensor.TYPE_GYROSCOPE:
          buffer.append("陀螺仪");
          break;
        case Sensor.TYPE_ORIENTATION:
          buffer.append("方向感应器");
          break;
        case Sensor.TYPE_LIGHT:
          buffer.append("光线感应器");
          break;
        case Sensor.TYPE_MAGNETIC_FIELD:
          buffer.append("电磁感应器");
          break;
        default:
          buffer.append("其他传感器");
          break;
      }
      buffer.append("\ndevice name : " + sensor.getName())
          .append(" ;\ndevice version : " + sensor.getVersion())
          .append(" ;\nsupply vendor : " + sensor.getVendor());
    }
    return buffer.toString();
  }
}
