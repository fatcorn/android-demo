package com.den.demo.net.http;

import com.den.demo.net.http.entity.ResponseMessage;
import com.den.demo.net.http.enums.CommonEnum;

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

    /**
     * 用户检索
     * @param retrieveInfo
     * @return
     */
    @GET("/UCENTER/uc/user/userRetrieve")
    Call<ResponseMessage> userRetrieve(@Query("retrieveInfo") String retrieveInfo);

    /**
     * 查询好友请求历史
     * @return
     */
    @GET("/UCENTER/uc/user/getFriendRequest")
    Call<ResponseMessage> getFriendRequest();

    /**
     * 调用好友请求出理接口
     * @param requestId
     * @param result
     * @return
     */
    @GET("/UCENTER/uc/user/handFriendRequest")
    Call<ResponseMessage> handFriendRequest(@Query("requestId") Long requestId, @Query("result") CommonEnum result);

    /**
     * 获取好友列表
     * @return
     */
    @GET("/UCENTER/uc/user/getContacts")
    Call<ResponseMessage> getContacts();
}
