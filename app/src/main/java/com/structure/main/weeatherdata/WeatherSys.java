package com.structure.main.weeatherdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuchao.
 */
public class WeatherSys implements Parcelable {
    int type;
    int id;
    String country;
    long sunrise;
    long sunset;

    protected WeatherSys(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        country = in.readString();
        sunrise = in.readLong();
        sunset = in.readLong();
    }

    public static final Creator<WeatherSys> CREATOR = new Creator<WeatherSys>() {
        @Override
        public WeatherSys createFromParcel(Parcel in) {
            return new WeatherSys(in);
        }

        @Override
        public WeatherSys[] newArray(int size) {
            return new WeatherSys[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeString(country);
        dest.writeLong(sunrise);
        dest.writeLong(sunset);
    }


}
