package com.example.yaroslav.socialapp.model.twitter_requester;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by yaroslav on 01.06.16.
 */
public interface TwitterRequester {
    void request(TwitterSession session, Callback<User> callback);
}
