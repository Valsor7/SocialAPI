package com.example.yaroslav.socialapp.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaroslav.socialapp.R;
import com.example.yaroslav.socialapp.Utility;
import com.example.yaroslav.socialapp.model.data.MyUser;
import com.example.yaroslav.socialapp.presenter.Presenter;
import com.example.yaroslav.socialapp.presenter.facebook.FbPresenterImpl;
import com.example.yaroslav.socialapp.presenter.gplus.GplusPresenterImpl;
import com.example.yaroslav.socialapp.presenter.twitter.TwitterPresenter;
import com.google.android.gms.plus.PlusShare;
import com.squareup.picasso.Picasso;


public class AccountActivity extends AppCompatActivity implements MainView{
    private static final int REQ_GPLUS_PHOTO = 2;
    private static final int REQ_TWITTER_PHOTO = 3;
    private static final int REQ_START_SHARE = 3;
    private Button shareBtn;
    private Button logoutBtn;
    Presenter presenter;
    private MyUser myUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView emailView = (TextView) findViewById(R.id.emailView);
        TextView bDayView = (TextView) findViewById(R.id.birthdayView);
        ImageView avatar = (ImageView) findViewById(R.id.avatar);

        shareBtn = (Button) findViewById(R.id.shareBtn);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAction(view);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutAction(view);
            }
        });

        Intent fbInfoIntent = getIntent();
        myUser = fbInfoIntent.getParcelableExtra(MyUser.User);
        String name = myUser.getFullName();
        String email = myUser.getEmail();
        String birthday = myUser.getBirthday();
        String picUrl = myUser.getPicUrl();
        Picasso.with(this).load(picUrl).into(avatar);

        if (name != null) nameView.setText(name);
        if (email != null) emailView.setText(email);
        if (birthday != null) bDayView.setText(birthday);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GPLUS_PHOTO){
            if (resultCode == RESULT_OK) {
                presenter.onResult(data);
                Uri imgUri = data.getData();
                ContentResolver cr = this.getContentResolver();
                String mimeType = cr.getType(imgUri);

                PlusShare.Builder share = new PlusShare.Builder(this);
                share.setText("hello everyone!");
                share.addStream(imgUri);
                share.setType(mimeType);
                startActivityForResult(share.getIntent(), REQ_START_SHARE);
            }
        }
        if (requestCode == REQ_TWITTER_PHOTO){
            if (resultCode == RESULT_OK) {
                Uri imgUri = data.getData();
                ContentResolver cr = this.getContentResolver();
                String mimeType = cr.getType(imgUri);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "some message");
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_STREAM, imgUri);
                intent.setType(mimeType);
                intent.setPackage("com.twitter.android");
                startActivity(intent);
            }
        }
    }

    private void shareAction(View v){
        switch (myUser.getSocialLogedIn()){
            case Utility.FB_SIGNIN:
                presenter = new FbPresenterImpl(this);
                presenter.share();
                break;
            case Utility.GPLUS_SIGNIN:
                presenter = new GplusPresenterImpl(this);
                presenter.share();
                break;
            case Utility.TWITTER_SIGNIN:
                presenter = new TwitterPresenter(this);
                presenter.share();
                break;
        }
    }

    private void logOutAction(View v){
        switch (myUser.getSocialLogedIn()){
            case Utility.FB_SIGNIN:
                presenter = new FbPresenterImpl(this);
                presenter.logOut();
                break;
            case Utility.GPLUS_SIGNIN:
                presenter = new GplusPresenterImpl(this);
                presenter.logOut();
                break;
            case Utility.TWITTER_SIGNIN:
               presenter = new TwitterPresenter(this);
                presenter.logOut();
                break;
        }
    }

    public void setMyUser(MyUser myUser) {

    }
}
