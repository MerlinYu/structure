package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao.
 */
public class WeatherSys implements Parcelable {
    @Expose
    @SerializedName("type")
    public int type;
    @Expose
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("message")
    public float message;
    @Expose
    @SerializedName("country")
    public String country;
    @Expose
    @SerializedName("sunrise")
    public long sunrise;
    @Expose
    @SerializedName("sunset")
    public long sunset;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeInt(this.id);
        dest.writeFloat(this.message);
        dest.writeString(this.country);
        dest.writeLong(this.sunrise);
        dest.writeLong(this.sunset);
    }

    public WeatherSys() {
    }

    protected WeatherSys(Parcel in) {
        this.type = in.readInt();
        this.id = in.readInt();
        this.message = in.readFloat();
        this.country = in.readString();
        this.sunrise = in.readLong();
        this.sunset = in.readLong();
    }

    public static final Creator<WeatherSys> CREATOR = new Creator<WeatherSys>() {
        @Override
        public WeatherSys createFromParcel(Parcel source) {
            return new WeatherSys(source);
        }

        @Override
        public WeatherSys[] newArray(int size) {
            return new WeatherSys[size];
        }
    };
}
