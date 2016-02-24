package com.structure.person.userdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuchao.
 */
public class User implements Parcelable {

    String userName;
    String userId;
    String avatarUrl;

    protected User(Parcel in) {
        userName = in.readString();
        userId = in.readString();
        avatarUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userId);
        dest.writeString(avatarUrl);
    }
}
