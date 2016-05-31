package com.example.yaroslav.socialapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yaroslav.socialapp.R;
import com.example.yaroslav.socialapp.Utility;
import com.example.yaroslav.socialapp.model.data.MyUser;
import com.example.yaroslav.socialapp.model.data.facebook.FbPermissions;
import com.example.yaroslav.socialapp.presenter.Presenter;
import com.example.yaroslav.socialapp.presenter.facebook.FbPresenterImpl;
import com.example.yaroslav.socialapp.presenter.gplus.GplusPresenterImpl;
import com.example.yaroslav.socialapp.presenter.twitter.TwitterPresenter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MainView {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "	ZSdO4p6i6tyd3Mz4v1wYYwl1i";
    private static final String TWITTER_SECRET = "MCgpQDI3rbBDDITSBdpzkhFXULZREhYQxgSXMQR0nBIscd7uF9";


    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int GOOGLE_SIGNIN_REQUEST = 1;

    private CallbackManager fbManager;
    private Presenter fbPresenter;
    private Presenter gplusPresenter;
    private Presenter twitterPresenter;
    private LoginButton fBtn;
    private SignInButton gBtn;
    private TwitterLoginButton tBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());

        MyUser myUser = Utility.getUser(this);
        if (!myUser.getSocialLogedIn().isEmpty()){
            sendIntent(myUser);
            finish();
        }
        setContentView(R.layout.activity_main);
        fBtn = (LoginButton) findViewById(R.id.fb_btn);
        gBtn = (SignInButton) findViewById(R.id.google_btn);
        tBtn = (TwitterLoginButton) findViewById(R.id.twitter_btn);
        gplusPresenter = new GplusPresenterImpl(this);
        fbPresenter = new FbPresenterImpl(this);
        twitterPresenter = new TwitterPresenter(this);
        fbLogin();
        gplusLogin();
        twitterLogin();
    }

    private void fbLogin(){
        fbManager = CallbackManager.Factory.create();
        fBtn.setReadPermissions(Arrays.asList(FbPermissions.PUBLIC_PROFILE,FbPermissions.EMAIL,FbPermissions.BIRTHDAY));

        fBtn.registerCallback(fbManager, (FacebookCallback<LoginResult>) fbPresenter);
    }

    private void gplusLogin(){
        gBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gplusPresenter.doLogin();
            }
        });
    }

    private void twitterLogin(){
        tBtn.setCallback((Callback<TwitterSession>) twitterPresenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"Destroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbManager.onActivityResult(requestCode, resultCode, data);
        tBtn.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGNIN_REQUEST){
           gplusPresenter.onResult(data);
        }
    }

    public void setMyUser(MyUser myUser) {
        if (myUser != null){
            sendIntent(myUser);
        } else {
            Log.d(LOG_TAG, "USER INFO ERROR");
        }
    }

    private void sendIntent(MyUser myUser){
        Intent i = new Intent(this, AccountActivity.class);
        i.putExtra(MyUser.User, myUser);
        startActivity(i);
    }
}
