package com.den.demo.net;

import com.den.demo.net.entity.ResponseMessage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetService {
    /**
     * 登录
     * @param phoneNumber
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/UCENTER/uc/login/login")
    Call<ResponseMessage> login(@Field("phoneNumber") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("/UCENTER/uc/user/getUserInfo")
    Call<ResponseMessage> hello(@Field("userId") String userId);

    /**
     * 用户检索
     * @param retrieveInfo
     * @return
     */
    @GET("/UCENTER/uc/user/userRetrieve")
    Call<ResponseMessage> userRetrieve(@Query("retrieveInfo") String retrieveInfo);
}
