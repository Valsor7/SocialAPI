package com.example.yaroslav.socialapp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String email;
    String fullName;
    String birthday;
    String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){}
    public User(String email, String fullName, String birthday, String picUrl) {
        this.email = email;
        this.fullName = fullName;
        this.birthday = birthday;
        this.picUrl = picUrl;
    }

    public int describeContents() {
        return 0;
    }



    public void writeToParcel(Parcel out, int flags) {
        out.writeStringArray(new String[]{email, fullName, birthday, picUrl});
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        email = data[0];
        fullName = data[1];
        birthday = data[2];
        picUrl = data[3];
    }
}
