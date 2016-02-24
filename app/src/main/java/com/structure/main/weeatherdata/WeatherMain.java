package com.structure.main.weeatherdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuchao.
 */
public class WeatherMain implements Parcelable {
    double temp;
    double pressure;
    int humidity;

    protected WeatherMain(Parcel in) {
        temp = in.readDouble();
        pressure = in.readDouble();
        humidity = in.readInt();
    }

    public static final Creator<WeatherMain> CREATOR = new Creator<WeatherMain>() {
        @Override
        public WeatherMain createFromParcel(Parcel in) {
            return new WeatherMain(in);
        }

        @Override
        public WeatherMain[] newArray(int size) {
            return new WeatherMain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(temp);
        dest.writeDouble(pressure);
        dest.writeInt(humidity);
    }
}
