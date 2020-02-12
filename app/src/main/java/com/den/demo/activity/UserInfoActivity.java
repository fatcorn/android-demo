package com.den.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.den.demo.R;
import com.den.demo.net.entity.UserInfoVo;
import com.den.demo.net.protocol.FriendRequestMessageHandler;
import com.google.gson.Gson;

import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {
    //头像
    private ImageView headView;
    // 用户名
    private TextView userNameTextView;
    // 地区
    private TextView areaTextView;
    // 添加好友button
    private Button requestButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        headView = (ImageView) findViewById(R.id.userInfoHeadImage);
        userNameTextView = (TextView) findViewById(R.id.userNickName);
        areaTextView = (TextView) findViewById(R.id.userLocation);
        requestButton = (Button) findViewById(R.id.action_friends_request);

        String userInfo = getIntent().getStringExtra("userInfo");
        UserInfoVo userInfoObj = new Gson().fromJson(userInfo, UserInfoVo.class);

        userNameTextView.setText(userInfoObj.getNickName());
        String location = userInfoObj.getCountry() + "  " + userInfoObj.getCity();
        areaTextView.setText(location);

        //发起请求
        requestButton.setOnClickListener(view -> {
            //  禁止点击
            requestButton.setClickable(false);
            FriendRequestMessageHandler.SendRequest(userInfoObj.getUserId());
        });
    }
}
