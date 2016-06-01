package com.example.yaroslav.socialapp.presenter.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.yaroslav.socialapp.R;
import com.example.yaroslav.socialapp.Utility;
import com.example.yaroslav.socialapp.model.fb_requester.FacebookRequester;
import com.example.yaroslav.socialapp.model.fb_requester.FacebookRequesterImpl;
import com.example.yaroslav.socialapp.model.data.MyUser;
import com.example.yaroslav.socialapp.model.data.facebook.FbFields;
import com.example.yaroslav.socialapp.model.data.facebook.FbPermissions;
import com.example.yaroslav.socialapp.presenter.Presenter;
import com.example.yaroslav.socialapp.view.MainActivity;
import com.example.yaroslav.socialapp.view.MainView;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FbPresenterImpl implements Presenter, FacebookCallback<LoginResult> {
    private final static String LOG_TAG = FbPresenterImpl.class.getSimpleName();
    private MainView view;
    private Context c;
    FacebookRequester requester;

    GraphRequest.GraphJSONObjectCallback callback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            MyUser myUser = new MyUser();
            try {
                myUser.setPicUrl((String) object.getJSONObject(FbFields.PICTURE).getJSONObject("data").get("url"));
                myUser.setFullName((String) object.get(FbFields.NAME));
                myUser.setBirthday((String) object.get(FbFields.BIRTHDAY));
                myUser.setEmail((String) object.get(FbFields.EMAIL));
                myUser.setSocialLogedIn(Utility.FB_SIGNIN);
                Log.d(LOG_TAG, object.toString());
                Utility.saveUser(c, myUser);
                view.setMyUser(myUser);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    public FbPresenterImpl(Activity activity){

        view = (MainView) activity;
        c = activity;
        requester = new FacebookRequesterImpl(c);
    }

    @Override
    public void share() {
        LoginManager fbManager = LoginManager.getInstance();
        fbManager.logInWithPublishPermissions((Activity) c, Arrays.asList(FbPermissions.PUBLISH));
        Bitmap image = BitmapFactory.decodeResource(c.getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
        SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
        ShareDialog.show((Activity) c, content);
    }

    @Override
    public void logOut() {
        LoginManager fbManager = LoginManager.getInstance();
        fbManager.logOut();
        Utility.logOutUser(c);

        Log.d(LOG_TAG,"facebook logged out");

        Intent i = new Intent(c, MainActivity.class);
        c.startActivity(i);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        requester.facebookRequest(loginResult, callback);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }


    @Override
    public void doLogin() {

    }

    @Override
    public void onResult(Intent resultIntent) {

    }
}
