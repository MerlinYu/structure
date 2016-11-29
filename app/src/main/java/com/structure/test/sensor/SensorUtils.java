package com.structure.test.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by yuchao on 11/21/16.
 * 传感器
 */


public class SensorUtils {


  public static List<Sensor> getSensorList(Context context) {
    SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    return sensorManager.getSensorList(Sensor.TYPE_ALL);
  }


}
