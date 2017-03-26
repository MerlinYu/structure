package com.structure.main;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.structure.main.data.BaseResponse;

import static android.R.attr.name;

/**
 * Created by yuchao on 1/7/17.
 */

public class BaseData implements BaseResponse.ResponseData , Parcelable{

  @Expose
  @SerializedName("name")
  public String name;

  protected BaseData(Parcel in) {
    name = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<BaseData> CREATOR = new Creator<BaseData>() {
    @Override
    public BaseData createFromParcel(Parcel in) {
      return new BaseData(in);
    }

    @Override
    public BaseData[] newArray(int size) {
      return new BaseData[size];
    }
  };

  @Override
  public void setResponseData(BaseResponse baseResponse) {

  }

  @Override
  public BaseResponse getBaseResponse() {
    return null;
  }
}
