package com.den.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.den.demo.activity.HomePageActivity;
import com.den.demo.database.AppDatabase;
import com.den.demo.net.http.NetServiceHandler;
import com.den.demo.net.http.entity.ResponseMessage;
import com.den.demo.net.http.entity.UserInfoVo;
import com.den.demo.net.protocol.ProtocolHandler;
import com.den.demo.util.NotificationUtil;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static Context context;

    public static final  String CHANNEL_ID = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 创建通知渠道
        NotificationUtil.createNotificationChannel();

        // 创建数据库
        AppDatabase.getInstance(getApplicationContext());


        //如果有token,跳转到主页
        SharedPreferences preferences = context.getSharedPreferences("data",MODE_PRIVATE);
        String token = preferences.getString("x-auth-token","");

        if (!"".equals(token)) {
            // 跳转
            Intent intent=new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
            //登录聊天服务器
            ProtocolHandler.getInstance().login();
        }

        Button loginBtn = findViewById(R.id.login_btn);
        // 开始登录
        loginBtn.setOnClickListener(v -> {
            EditText accountText =  (EditText)findViewById((R.id.account_text));
            EditText passwordText =  (EditText)findViewById((R.id.passwd_text));
            String phoneNumber =  accountText.getText().toString().trim();
            String password =  passwordText.getText().toString().trim();
            Call<ResponseMessage> call =  NetServiceHandler.handler().login(phoneNumber,password);

            // 网络调用不能和ui线程在一个线程
            Thread thread = new Thread(() -> {
                try {
                    Response<ResponseMessage> response = call.execute();
                    if(response.body() == null) {
                        System.out.println("请求失败");
                        return;
                    }
                    // 响应码为0，登录成功
                    if(response.body().getCode() == 0) {
                        String token1 = response.headers().get("x-auth-token");
                        UserInfoVo userInfo = new Gson().fromJson(response.body().getData().toString(), UserInfoVo.class);
                        //重复登录，再次请求，会带上token，返回token会变为空,之后限制重复登录
                        if(!"".equals(token1) && token1 != null) {
                            // 使用SharedPreferences储存登录令牌
                            SharedPreferences preferences1 = context.getSharedPreferences("data",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences1.edit();
                            editor.putString("x-auth-token", token1);
                            editor.putLong("userId", userInfo.getUserId());
                            editor.apply();
                        }
                        // 跳转
                        Intent intent=new Intent(LoginActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        //登录聊天服务器
                        ProtocolHandler.getInstance().login();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("调用成功");
            });
            thread.start();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public static Context getContextOfApplication(){
        return context;
    }




}
