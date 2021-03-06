package com.example.yaroslav.socialapp.presenter.facebook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.yaroslav.socialapp.R;
import com.example.yaroslav.socialapp.model.FacebookRequester;
import com.example.yaroslav.socialapp.model.FacebookRequesterImpl;
import com.example.yaroslav.socialapp.model.data.User;
import com.example.yaroslav.socialapp.view.MainView;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FbPresenterImpl implements FbPresenter, FacebookCallback<LoginResult> {

    private MainView view;
    private Context c;
    FacebookRequester requester;

    GraphRequest.GraphJSONObjectCallback callback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            User user = new User();
            try {
                user.setPicUrl((String) object.getJSONObject("picture").getJSONObject("data").get("url"));
                user.setFullName((String) object.get("name"));
                user.setBirthday((String) object.get("birthday"));
                user.setEmail((String) object.get("email"));

                Log.d("FB", object.toString());
                view.setUser(user);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    public FbPresenterImpl(Activity activity){

        view = (MainView) activity;
        c = activity.getApplicationContext();
        requester = new FacebookRequesterImpl(c);
    }

    @Override
    public void fbShare(Activity activity) {
        LoginManager fbManager = LoginManager.getInstance();
        fbManager.logInWithPublishPermissions(activity, Arrays.asList("publish_actions"));
        Bitmap image = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
        SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
        ShareApi.share(content, null);

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



}
