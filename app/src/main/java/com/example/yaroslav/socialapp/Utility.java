package com.example.yaroslav.socialapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yaroslav.socialapp.model.data.MyUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static final String FB_SIGNIN = "facebook";
    public static final String GPLUS_SIGNIN = "gplus";
    public static final String TWITTER_SIGNIN = "twitter";

    public static void saveUser(Context c, MyUser myUser){
        SharedPreferences prefs = c.getSharedPreferences(MyUser.User, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", myUser.getEmail());
        editor.putString("name", myUser.getFullName());
        editor.putString("bDay", myUser.getBirthday());
        editor.putString("picUrl", myUser.getPicUrl());
        editor.putString("social", myUser.getSocialLogedIn());
        editor.apply();
    }

    public static MyUser getUser(Context c){
        MyUser myUser = new MyUser();
        SharedPreferences prefs = c.getSharedPreferences(MyUser.User, Context.MODE_PRIVATE);
        myUser.setEmail(prefs.getString("email", ""));
        myUser.setFullName(prefs.getString("name", ""));
        myUser.setBirthday(prefs.getString("bDay", ""));
        myUser.setPicUrl(prefs.getString("picUrl", ""));
        myUser.setSocialLogedIn(prefs.getString("social", ""));

        return myUser;
    }

    public static void logOutUser(Context c){
        SharedPreferences prefs = c.getSharedPreferences(MyUser.User, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("social", "");
        editor.apply();
    }

    public String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("mm/DD/yyyy", Locale.getDefault());
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

}