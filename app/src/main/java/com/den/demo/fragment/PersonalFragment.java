package com.den.demo.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.den.demo.LoginActivity;
import com.den.demo.R;
import com.den.demo.activity.ChatWindowActivity;
import com.den.demo.activity.HomePageActivity;
import com.den.demo.activity.RequestHistoryActivity;

import static android.content.Context.MODE_PRIVATE;

public class PersonalFragment extends Fragment {
    // 好友请求按钮
    private LinearLayout requestRow;

    // 登出
    private TextView logoutTv;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        requestRow = view.findViewById(R.id.requestRecords);
        logoutTv =  view.findViewById(R.id.fp_logout);
        logoutTv.setOnClickListener(x -> {
            // 退出到登录也页面
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("data",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("x-auth-token", "");
            editor.apply();
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
        });
        // 点击调转到好友请求历史页面，查看最新与历史的请求
        requestRow.setOnClickListener(x -> {
            Intent intent = new Intent(inflater.getContext(), RequestHistoryActivity.class);
            startActivity(intent);
        });
        return view;
    }

}
