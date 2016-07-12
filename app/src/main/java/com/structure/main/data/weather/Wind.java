package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao on 7/12/16.
 */
public class Wind implements Parcelable{
  @Expose
  @SerializedName("speed")
  public String speed;
  @Expose
  @SerializedName("deg")
  public int deg;


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.speed);
    dest.writeInt(this.deg);
  }

  public Wind() {
  }

  protected Wind(Parcel in) {
    this.speed = in.readString();
    this.deg = in.readInt();
  }

  public static final Creator<Wind> CREATOR = new Creator<Wind>() {
    @Override
    public Wind createFromParcel(Parcel source) {
      return new Wind(source);
    }

    @Override
    public Wind[] newArray(int size) {
      return new Wind[size];
    }
  };
}
