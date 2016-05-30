package com.example.yaroslav.socialapp.presenter.gplus;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.yaroslav.socialapp.model.data.User;
import com.example.yaroslav.socialapp.view.MainView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GplusPresenterImpl implements GplusPresenter {

    private static final int GPLUS_REQUEST = 1;
    MainView mainView;
    Activity aContext;
    public GplusPresenterImpl(Activity activity) {
        mainView = (MainView) activity;
        aContext = activity;
    }

    @Override
    public void allowLogin(GoogleApiClient gApiClient) {
        Intent gActivityIntent = Auth.GoogleSignInApi.getSignInIntent(gApiClient);
        aContext.startActivityForResult(gActivityIntent, GPLUS_REQUEST);
    }

    public void onResult(GoogleSignInResult result){
        User user = null;
        if (result.isSuccess()) {
            user = new User();
            GoogleSignInAccount account = result.getSignInAccount();
            user.setEmail(account.getEmail() == null ? "" : account.getEmail());
            user.setFullName(account.getDisplayName());
            user.setPicUrl(account.getPhotoUrl() == null ? "" : account.getPhotoUrl().toString());

            Log.d("GPLUS", String.format("%1$S %2$S %3$S", user.getEmail(), user.getFullName(), user.getPicUrl()));

            mainView.setUser(user);
        }else {
            Log.d("GPLUS", "GPLUS ERROR");
        }

    }
}
