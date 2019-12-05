package com.den.demo.net;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetService {
    @FormUrlEncoded
    @POST("/UCENTER/uc//login/login")
    Call<Object> login(@Field("phoneNumber") String phoneNumber, @Field("password") String password);
}
