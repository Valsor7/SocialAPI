package com.example.yaroslav.socialapp.presenter.gplus;

import android.app.Activity;
import android.content.Intent;;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.yaroslav.socialapp.Utility;
import com.example.yaroslav.socialapp.model.data.MyUser;
import com.example.yaroslav.socialapp.presenter.Presenter;
import com.example.yaroslav.socialapp.view.MainActivity;
import com.example.yaroslav.socialapp.view.MainView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GplusPresenterImpl implements Presenter, GoogleApiClient.OnConnectionFailedListener {
    private final static String LOG_TAG = GplusPresenterImpl.class.getSimpleName();
    private static final int GPLUS_REQUEST = 1;
    private static final int REQ_SELECT_PHOTO = 2;
    MainView mainView;
    Activity aContext;

    private GoogleApiClient gApiClient;

    public GplusPresenterImpl(Activity activity) {
        mainView = (MainView) activity;
        aContext = activity;
    }

    @Override
    public void doLogin() {
        if (gApiClient == null) {
            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            gApiClient = new GoogleApiClient.Builder(aContext)
                    .enableAutoManage((FragmentActivity) aContext, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                    .build();
        }
        Intent gActivityIntent = Auth.GoogleSignInApi.getSignInIntent(gApiClient);
        aContext.startActivityForResult(gActivityIntent, GPLUS_REQUEST);
    }

    public void onResult(Intent resultIntent){
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(resultIntent);
        MyUser myUser = null;
        if (result.isSuccess()) {
            myUser = new MyUser();
            GoogleSignInAccount account = result.getSignInAccount();
            myUser.setEmail(account.getEmail() == null ? "" : account.getEmail());
            myUser.setFullName(account.getDisplayName());
            myUser.setPicUrl(account.getPhotoUrl() == null ? "" : account.getPhotoUrl().toString());
            myUser.setSocialLogedIn(Utility.GPLUS_SIGNIN);

            Utility.saveUser(aContext, myUser);
            Log.d(LOG_TAG, String.format("%1$S %2$S %3$S", myUser.getEmail(), myUser.getFullName(), myUser.getPicUrl()));

            mainView.setMyUser(myUser);
        }else {
            Log.d(LOG_TAG, "GPLUS ERROR");
        }

    }

    @Override
    public void share() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("video/*, image/*");
        aContext.startActivityForResult(photoPicker, REQ_SELECT_PHOTO);
    }

    @Override
    public void logOut() {
        Utility.logOutUser(aContext);
        Log.d(LOG_TAG, "gplus logged out");

        Intent i = new Intent(aContext, MainActivity.class);
        aContext.startActivity(i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "GPLUS ERROR");
    }
}
