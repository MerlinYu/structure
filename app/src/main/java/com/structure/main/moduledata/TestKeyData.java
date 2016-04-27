package com.structure.main.moduledata;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by yuchao.
 */
public class TestKeyData  implements Parcelable{

  @Expose
  @SerializedName("message")
  public String  message;

  @Expose
  @SerializedName("keywords")
  public ArrayList<KeyWords> keyList;


  protected TestKeyData(Parcel in) {
    message = in.readString();
    keyList = in.createTypedArrayList(KeyWords.CREATOR);
  }

  public static final Creator<TestKeyData> CREATOR = new Creator<TestKeyData>() {
    @Override
    public TestKeyData createFromParcel(Parcel in) {
      return new TestKeyData(in);
    }

    @Override
    public TestKeyData[] newArray(int size) {
      return new TestKeyData[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
    dest.writeTypedList(keyList);
  }

  public String toString() {
    return new StringBuffer().append("message = " + message + "\n" + keyList.toString()).toString();
  }
}
