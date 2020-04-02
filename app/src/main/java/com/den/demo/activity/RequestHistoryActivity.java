package com.den.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.den.demo.R;
import com.den.demo.adapter.ChatWindowDataAdapter;
import com.den.demo.adapter.FriendRequestDataAdapter;
import com.den.demo.model.Message;
import com.den.demo.net.http.NetServiceHandler;
import com.den.demo.net.http.entity.FriendRequest;
import com.den.demo.net.http.entity.ResponseMessage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

public class RequestHistoryActivity extends AppCompatActivity {

    // 数据展示
    private ListView listView;

    // 无数据提示框
    private TextView textView;
    // ui刷新助手
    private Handler handler = null;



    // 好友请求历史列表
    private List<FriendRequest> requestArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);

        // 接收消息
        handler = new Handler(msg -> {
            listView = findViewById(R.id.arh_requestHistory);
            textView = findViewById(R.id.arh_WithDataView);


            if(requestArrayList.size() == 0) {
                textView.setVisibility(View.VISIBLE);
            } else {
                FriendRequestDataAdapter friendRequestDataAdapter = new FriendRequestDataAdapter(this.getBaseContext(), requestArrayList);
                listView.setAdapter(friendRequestDataAdapter);
            }
            return true;
        });

        // 拉取数据
        Thread thread = new Thread(() -> {
            android.os.Message message = new android.os.Message();
            Call<ResponseMessage> call =  NetServiceHandler.handler().getFriendRequest();
            Response<ResponseMessage> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response == null ||  response.body() == null) {
                System.out.println("请求失败");
                handler.sendMessage(message);
                return;
            }
            if(response.body().getCode() == 0) {
                //Jaskson工具类
                ObjectMapper objectMapper = new ObjectMapper();
                // 引号配置
                objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                JavaType t  = objectMapper.getTypeFactory().constructParametricType(List.class, FriendRequest.class);
                try {
                    requestArrayList = objectMapper.readValue(objectMapper.writeValueAsString(response.body().getData()), t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(message);
            }
        });
        thread.start();

    }
}
