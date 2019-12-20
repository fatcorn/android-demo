package com.den.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.den.demo.net.NetServiceHandler;
import com.den.demo.net.entity.ResponseMessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText accountText =  (EditText)findViewById((R.id.account_text));
                EditText passwordText =  (EditText)findViewById((R.id.passwd_text));
                String phoneNumber =  accountText.getText().toString().trim();
                String password =  passwordText.getText().toString().trim();
                Call<ResponseMessage> call =  NetServiceHandler.handler().login(phoneNumber,password);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response<ResponseMessage> response = call.execute();
                            if(response.body() == null) {
                                System.out.println("请求失败");
                                return;
                            }
                            if(response.body().getCode() == 0) {
                                String token = response.headers().get("x-auth-token");
                                SharedPreferences.Editor editor = context.getSharedPreferences("data",MODE_PRIVATE).edit();
                                editor.putString("x-auth-token",token);
                                editor.apply();

                                Call<ResponseMessage> helloCall =  NetServiceHandler.handler().hello("1163690799456124930");
                                Response<ResponseMessage> test = helloCall.execute();
                                System.out.println(test.body().getData().toString());
                                System.out.println("请求头添加成功");
                                System.out.println("登录成功");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("调用成功");
                    }
                });
                thread.start();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContextOfApplication(){
        return context;
    }
}
