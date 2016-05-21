package com.structure.main.data;

import android.os.Parcel;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchao on 16/5/13.
 *
 */
public class BaseResponse<T extends BaseResponse.ResponseData> {

  @Expose
  @SerializedName("code")
  public int code;
  @Expose
  @SerializedName("error")
  public String error;

  public T data;

  private String TAG = " ===tag=== base ";

  public  BaseResponse() {
    Log.d(TAG, " BaseResponse ");
 //   data.setResponseData(this);
  }
  /**
   * Only use this when subclass needs to implements {@link android.os.Parcelable}
   *
   * @param dest The Parcel in which the object should be written.
   */
  protected void writeBaseParcel(Parcel dest) {
    dest.writeInt(this.code);
    dest.writeString(this.error);
    Log.d(TAG, " writeBaseParcel ");
  }

  /**
   * Only use this when subclass needs to implements {@link android.os.Parcelable}
   *
   * @param in The Parcel to read the object's data from.
   */
  protected void readBaseParcel(Parcel in) {
    this.code = in.readInt();
    this.error = in.readString();
    Log.d(TAG, " readBaseParcel ");
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("BaseResponse { "
        + " code = " + code
        + " error = " + error
        + " data = ");
    return buffer.toString();
  }


  public interface ResponseData {
    void setResponseData(BaseResponse baseResponse);
    BaseResponse getBaseResponse();
  }
}
