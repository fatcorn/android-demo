package com.den.demo.net;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetServiceHandler{

    public static NetService handler() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/9201/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NetService.class);
    }
}
