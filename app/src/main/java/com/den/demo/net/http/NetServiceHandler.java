package com.den.demo.net.http;


import android.content.Context;
import android.content.SharedPreferences;

import com.den.demo.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class NetServiceHandler {

    // 静态retrofit对象，目的创建NetService单例模式
    Context context = LoginActivity.getContextOfApplication();


    /**
     * 接口调用handler
     * @return NetService
     */
    public static NetService handler() {
        NetServiceHandler netServiceHandler = new NetServiceHandler();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9201/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(netServiceHandler.genericClient())
                .build();
        return retrofit.create(NetService.class);
    }

    /**
     *
     * @return
     */
    private OkHttpClient genericClient() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("data",MODE_PRIVATE);
        String token = sharedPreferences.getString("x-auth-token","");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("x-auth-token", token)
                            .build();
                    return chain.proceed(request);
                })
                .build();

        return client;
    }
}
