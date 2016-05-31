package com.example.yaroslav.socialapp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MyUser implements Parcelable {
    public static final String User = "user";

    private String socialLogedIn;
    private String email;
    private String fullName;
    private String birthday;
    private String picUrl;

    public String getSocialLogedIn() {
        return socialLogedIn;
    }

    public void setSocialLogedIn(String socialLogedIn) {
        this.socialLogedIn = socialLogedIn;
    }

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

    public MyUser(){}
    public MyUser(String email, String fullName, String birthday, String picUrl, String social) {
        this.email = email;
        this.fullName = fullName;
        this.birthday = birthday;
        this.picUrl = picUrl;
        this.socialLogedIn = social;
    }

    public int describeContents() {
        return 0;
    }



    public void writeToParcel(Parcel out, int flags) {
        out.writeStringArray(new String[]{email, fullName, birthday, picUrl, socialLogedIn});
    }

    public static final Parcelable.Creator<MyUser> CREATOR = new Parcelable.Creator<MyUser>() {
        public MyUser createFromParcel(Parcel in) {
            return new MyUser(in);
        }

        public MyUser[] newArray(int size) {
            return new MyUser[size];
        }
    };

    private MyUser(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        email = data[0];
        fullName = data[1];
        birthday = data[2];
        picUrl = data[3];
        socialLogedIn = data[4];
    }
}
