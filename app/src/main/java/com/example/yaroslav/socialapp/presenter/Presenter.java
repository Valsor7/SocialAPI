package com.example.yaroslav.socialapp.presenter;

import android.content.Intent;

/**
 * Created by yaroslav on 31.05.16.
 */
public interface Presenter {
    void doLogin();
    void onResult(Intent resultIntent);
    void share();
    void logOut();
}
