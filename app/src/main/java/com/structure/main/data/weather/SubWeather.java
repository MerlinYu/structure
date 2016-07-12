package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao.
 */
public class SubWeather implements Parcelable{
    @Expose
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("main")
    public String main;
    @Expose
    @SerializedName("description")
    public String description;
    @Expose
    @SerializedName("icon")
    public String icon;

    protected SubWeather(Parcel in) {
        id = in.readInt();
        main = in.readString();
        description = in.readString();
        icon = in.readString();
    }

    public static final Creator<SubWeather> CREATOR = new Creator<SubWeather>() {
        @Override
        public SubWeather createFromParcel(Parcel in) {
            return new SubWeather(in);
        }

        @Override
        public SubWeather[] newArray(int size) {
            return new SubWeather[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(main);
        dest.writeString(description);
        dest.writeString(icon);
    }
}
