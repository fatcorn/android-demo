package com.den.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.den.demo.R;
import com.den.demo.net.http.NetServiceHandler;
import com.den.demo.net.http.entity.ResponseMessage;
import com.den.demo.net.http.entity.UserInfoVo;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class FriendSearch extends AppCompatActivity {

    // 收索框
    private SearchView searchView;

    //未找到用户提示
    private TextView textView;
    // ui刷新助手
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);

        handler = new Handler(msg -> {
            // call update gui method.
            if (msg.what == 1) {
                textView.setVisibility(View.VISIBLE);
            }
            return true;
        });


        searchView = (SearchView) findViewById(R.id.friendSearchView);
        textView = (TextView) findViewById(R.id.userUnFindTips);
        textView.setVisibility(View.INVISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                // 提交
                @Override
                public boolean onQueryTextSubmit(String query) {
                        Thread thread = new Thread(() -> {
                            try {
                                Call<ResponseMessage> call =  NetServiceHandler.handler().userRetrieve(query);
                                Response<ResponseMessage> response = call.execute();
                                if(response.body() == null) {
                                    System.out.println("请求失败");
                                    return;
                                }
                                // 查询成功
                                if (response.body().getCode() == 0) {
                                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                                    intent.putExtra("userInfo", new Gson().toJson(response.body().getData()));
                                    startActivity(intent);
                                    System.out.println(response.body().getData());
                                } else {
                                    //用户不存在
                                    Message message = new Message();

                                    message.what = 1;
                                    handler.sendMessage(message);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        thread.start();
                 return false;
            }
            //提示
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
