package com.structure.main.moduledata;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao.
 */
public class KeyWords implements Parcelable {

  @Expose
  @SerializedName("red")
  public boolean red;


  @Expose
  @SerializedName("text")
  public String text;

  protected KeyWords(Parcel in) {
    red = in.readByte() != 0;
    text = in.readString();
  }

  public static final Creator<KeyWords> CREATOR = new Creator<KeyWords>() {
    @Override
    public KeyWords createFromParcel(Parcel in) {
      return new KeyWords(in);
    }

    @Override
    public KeyWords[] newArray(int size) {
      return new KeyWords[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte) (red ? 1 : 0));
    dest.writeString(text);
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    return buffer.append("red = " + red + " text = " + text).toString() + "\n";
  }
}
