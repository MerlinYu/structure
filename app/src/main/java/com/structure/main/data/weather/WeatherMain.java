package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao.
 */
public class WeatherMain implements Parcelable {
    @Expose
    @SerializedName("temp")
    public float temp;
    @Expose
    @SerializedName("pressure")
    public double pressure;
    @Expose
    @SerializedName("humidity")
    public float humidity;
    @Expose
    @SerializedName("temp_min")
    public float temp_min;
    @Expose
    @SerializedName("temp_max")
    public float temp_max;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.temp);
        dest.writeDouble(this.pressure);
        dest.writeFloat(this.humidity);
        dest.writeFloat(this.temp_min);
        dest.writeFloat(this.temp_max);
    }

    public WeatherMain() {
    }

    protected WeatherMain(Parcel in) {
        this.temp = in.readFloat();
        this.pressure = in.readDouble();
        this.humidity = in.readFloat();
        this.temp_min = in.readFloat();
        this.temp_max = in.readFloat();
    }

    public static final Creator<WeatherMain> CREATOR = new Creator<WeatherMain>() {
        @Override
        public WeatherMain createFromParcel(Parcel source) {
            return new WeatherMain(source);
        }

        @Override
        public WeatherMain[] newArray(int size) {
            return new WeatherMain[size];
        }
    };
}
