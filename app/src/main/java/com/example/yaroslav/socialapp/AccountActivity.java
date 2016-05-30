package com.example.yaroslav.socialapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaroslav.socialapp.model.data.User;
import com.example.yaroslav.socialapp.view.AccountView;
import com.squareup.picasso.Picasso;


public class AccountActivity extends AppCompatActivity implements AccountView {

    private Button shareBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView emailView = (TextView) findViewById(R.id.emailView);
        TextView bDayView = (TextView) findViewById(R.id.birthdayView);
        ImageView avatar = (ImageView) findViewById(R.id.avatar);

        shareBtn = (Button) findViewById(R.id.shareBtn);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Intent fbInfoIntent = getIntent();
        User user = fbInfoIntent.getParcelableExtra("user");
        String name = user.getFullName();
        String email = user.getEmail();
        String birthday = user.getBirthday();
        String picUrl = user.getPicUrl();
        Picasso.with(this).load(picUrl).into(avatar);

        if (name != null) nameView.setText(name);
        if (email != null) emailView.setText(email);
        if (birthday != null) bDayView.setText(birthday);
    }
}
