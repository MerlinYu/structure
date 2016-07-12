package com.structure.main.data.weather;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewDebug;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao on 7/12/16.
 */
public class Rain implements Parcelable{

  @Expose
  @SerializedName("1h")
  public float oneHonourQuantity;

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeFloat(this.oneHonourQuantity);
  }

  public Rain() {
  }

  protected Rain(Parcel in) {
    this.oneHonourQuantity = in.readFloat();
  }

  public static final Creator<Rain> CREATOR = new Creator<Rain>() {
    @Override
    public Rain createFromParcel(Parcel source) {
      return new Rain(source);
    }

    @Override
    public Rain[] newArray(int size) {
      return new Rain[size];
    }
  };
}
