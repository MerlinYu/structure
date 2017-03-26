package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewDebug;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao on 7/12/16.
 */
public class Clouds implements Parcelable{

  @Expose
  @SerializedName("all")
  public int all;

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.all);
  }

  public Clouds() {
  }

  protected Clouds(Parcel in) {
    this.all = in.readInt();
  }

  public static final Creator<Clouds> CREATOR = new Creator<Clouds>() {
    @Override
    public Clouds createFromParcel(Parcel source) {
      return new Clouds(source);
    }

    @Override
    public Clouds[] newArray(int size) {
      return new Clouds[size];
    }
  };
}

