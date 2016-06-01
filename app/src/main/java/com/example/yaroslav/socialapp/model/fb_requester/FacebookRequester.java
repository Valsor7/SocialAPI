package com.example.yaroslav.socialapp.model.fb_requester;

import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;

/**
 * Created by Yaroslav on 27-May-16.
 */
public interface FacebookRequester {
    public void facebookRequest(LoginResult loginResult, GraphRequest.GraphJSONObjectCallback callback);
}
