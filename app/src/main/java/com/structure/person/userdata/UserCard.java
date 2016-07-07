package com.structure.person.userdata;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by yuchao on 7/5/16.
 */
public class UserCard implements Parcelable {

  String name;
  String motto;
  String avatar;
  String describe;
  String email;
  String phoneNumber;
  Address address;
  int age;


  protected UserCard(Parcel in) {
    name = in.readString();
    motto = in.readString();
    avatar = in.readString();
    describe = in.readString();
    email = in.readString();
    phoneNumber = in.readString();
    address = in.readParcelable(Address.class.getClassLoader());
    age = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(motto);
    dest.writeString(avatar);
    dest.writeString(describe);
    dest.writeString(email);
    dest.writeString(phoneNumber);
    dest.writeParcelable(address, flags);
    dest.writeInt(age);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UserCard> CREATOR = new Creator<UserCard>() {
    @Override
    public UserCard createFromParcel(Parcel in) {
      return new UserCard(in);
    }

    @Override
    public UserCard[] newArray(int size) {
      return new UserCard[size];
    }
  };



  public UserCard(String name,
                  int age,
                  String avatar,
                  String phoneNumber,
                  String describe,
                  String email,
                  String motto) {
    this.name = name;
    this.age = age;
    this.avatar = avatar;
    this.phoneNumber = phoneNumber;
    this.motto = motto;
    this.describe = describe;
    this.email = email;

  }


  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getLineDescribe(String describe,int textSize, int picWidth,int padding) {
    if (null == describe || picWidth == 0 || textSize == 0) {
      return null;
    }
    StringBuffer buffer = new StringBuffer();
    int maxLineCharacter = (picWidth - 2*padding)/textSize;
    int length = describe.length();
    buffer.append("\t");
    int start = 0;
    int end = 0;
    while(start < length) {
      end = start + maxLineCharacter;
      if (end >= length) {
        end = length;
      }
      String str = describe.substring(start, end);
      buffer.append(str + "\n");
      start = end;
    }
    Log.v("===tag=== line ", buffer.toString());
    return buffer.toString();

  }




}
