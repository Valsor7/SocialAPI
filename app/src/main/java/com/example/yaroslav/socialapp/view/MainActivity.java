package com.example.yaroslav.socialapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yaroslav.socialapp.AccountActivity;
import com.example.yaroslav.socialapp.R;
import com.example.yaroslav.socialapp.model.data.User;
import com.example.yaroslav.socialapp.presenter.facebook.FbPresenter;
import com.example.yaroslav.socialapp.presenter.facebook.FbPresenterImpl;
import com.example.yaroslav.socialapp.presenter.gplus.GplusPresenter;
import com.example.yaroslav.socialapp.presenter.gplus.GplusPresenterImpl;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MainView, GoogleApiClient.OnConnectionFailedListener {


    private CallbackManager fbManager;
    private FbPresenter fbPresenter;
    private GplusPresenter gplusPresenter;
    private LoginButton fBtn;
    private SignInButton gBtn;
    Context c = this;
    private GoogleApiClient account;
    private static final int GOOGLE_SIGNIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        fbManager = CallbackManager.Factory.create();
        fBtn = (LoginButton) findViewById(R.id.fb_btn);
        fBtn.setReadPermissions(Arrays.asList("public_profile","email","user_birthday"));

        fbPresenter = new FbPresenterImpl(this);
        fbLogin();

        gplusPresenter = new GplusPresenterImpl(this);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        gBtn = (SignInButton) findViewById(R.id.google_btn);
        gBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gplusPresenter.allowLogin(googleApiClient);
            }
        });
    }


    private void fbLogin(){
        fBtn.registerCallback(fbManager, (FacebookCallback<LoginResult>) fbPresenter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGNIN_REQUEST){
           gplusPresenter.onResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    @Override
    public void setUser(User user) {
        if (user != null){
            sendIntent(user);
        } else {
            Log.d("GPLUS", "GPLUS ERROR");
        }
    }

    private void sendIntent(User user){
        Intent i = new Intent(this, AccountActivity.class);
        i.putExtra("user", user);
        startActivity(i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("GPLUS", "GPLUS ERROR");
    }
}
