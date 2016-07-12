package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao.
 */
public class WeatherCoord implements Parcelable {
    @Expose
    @SerializedName("lon")
    float lon;
    @Expose
    @SerializedName("lat")
    float lat;

    protected WeatherCoord(Parcel in) {
        lon = in.readFloat();
        lat = in.readFloat();
    }

    public static final Creator<WeatherCoord> CREATOR = new Creator<WeatherCoord>() {
        @Override
        public WeatherCoord createFromParcel(Parcel in) {
            return new WeatherCoord(in);
        }

        @Override
        public WeatherCoord[] newArray(int size) {
            return new WeatherCoord[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lon);
        dest.writeFloat(lat);
    }
}
