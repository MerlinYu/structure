package com.structure.person.userdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuchao on 7/6/16.
 */
public class Address implements Parcelable {
  public String convince;
  public String city;
  public String district;

  protected Address(Parcel in) {
    convince = in.readString();
    city = in.readString();
    district = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(convince);
    dest.writeString(city);
    dest.writeString(district);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Address> CREATOR = new Creator<Address>() {
    @Override
    public Address createFromParcel(Parcel in) {
      return new Address(in);
    }

    @Override
    public Address[] newArray(int size) {
      return new Address[size];
    }
  };
}
