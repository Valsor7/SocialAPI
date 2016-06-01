package com.example.yaroslav.socialapp.model.fb_requester;

import android.content.Context;
import android.os.Bundle;

import com.example.yaroslav.socialapp.model.data.facebook.FbFields;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;


public class FacebookRequesterImpl implements FacebookRequester  {
    Context c;
    public FacebookRequesterImpl(Context c){
        this.c = c;
    }

    @Override
    public void facebookRequest(LoginResult loginResult, GraphRequest.GraphJSONObjectCallback callback) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), callback);
        Bundle bundle = new Bundle();
        bundle.putString("fields", String.format(
                "%1$s,%2$s,%3$s,%4$s",
                FbFields.NAME,FbFields.PICTURE,FbFields.BIRTHDAY,FbFields.EMAIL));
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }


}
