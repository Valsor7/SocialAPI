package com.example.yaroslav.socialapp.presenter.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yaroslav.socialapp.Utility;
import com.example.yaroslav.socialapp.model.data.MyUser;
import com.example.yaroslav.socialapp.model.data.facebook.FbFields;
import com.example.yaroslav.socialapp.presenter.Presenter;
import com.example.yaroslav.socialapp.view.MainActivity;
import com.example.yaroslav.socialapp.view.MainView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by yaroslav on 31.05.16.
 */
public class TwitterPresenter extends Callback<TwitterSession> implements Presenter {
    private static final int REQ_SELECT_PHOTO = 3;
    MainView view;
    Activity activity;

    private static final String LOG_TAG = TwitterPresenter.class.getSimpleName();

    public TwitterPresenter(Activity activity){
        view = (MainView) activity;
        this.activity = activity;
    }

    @Override
    public void success(Result<TwitterSession> result) {
        TwitterSession session = result.data;
        Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false, new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                User twitterUser = result.data;
                MyUser myUser = new MyUser();
                myUser.setPicUrl(twitterUser.profileImageUrl);
                myUser.setFullName(twitterUser.name);
                myUser.setBirthday("");
                myUser.setEmail(twitterUser.email);
                myUser.setSocialLogedIn(Utility.TWITTER_SIGNIN);
                Log.d(LOG_TAG, twitterUser.name);
                Utility.saveUser(activity, myUser);
                view.setMyUser(myUser);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
        Log.d(LOG_TAG, session.getUserName());
    }

    @Override
    public void failure(TwitterException exception) {
        Log.d(LOG_TAG, "ERROR TWITTER LOGIN");
    }

    @Override
    public void doLogin() {

    }

    @Override
    public void onResult(Intent resultIntent) {

    }

    @Override
    public void share() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("video/*, image/*");
        activity.startActivityForResult(photoPicker, REQ_SELECT_PHOTO);

    }

    @Override
    public void logOut() {
        Twitter.logOut();
        Utility.logOutUser(activity);

        Log.d(LOG_TAG,"facebook logged out");

        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
    }
}
