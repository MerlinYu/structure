package com.structure.main.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by yuchao.
 */
public class KeyWordsData implements Parcelable, BaseResponse.ResponseData{


  @Expose
  @SerializedName("keywords")
  public ArrayList<KeyWords> keyList;
  private String TAG = " ===tag=== KeyWordsData ";
  public BaseResponse baseData;


  protected KeyWordsData(Parcel in) {

    keyList = in.createTypedArrayList(KeyWords.CREATOR);
    Log.d(TAG, " KeyWordsData");
  }

  public static final Creator<KeyWordsData> CREATOR = new Creator<KeyWordsData>() {
    @Override
    public KeyWordsData createFromParcel(Parcel in) {
      return new KeyWordsData(in);
    }

    @Override
    public KeyWordsData[] newArray(int size) {
      return new KeyWordsData[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(keyList);
    Log.d(TAG, " writeToParcel");

  }

  public String toString() {
    return new StringBuffer().append("message = "  + "\n" + keyList.toString()).toString();
  }

  @Override
  public void setResponseData(BaseResponse baseResponse) {
    baseData = baseResponse;
    Log.d(TAG, " setResponseData " + baseData.toString());
  }

  @Override
  public BaseResponse getBaseResponse() {
    Log.d(TAG, " getBaseResponse " + baseData.toString());

    return baseData;
  }
}
