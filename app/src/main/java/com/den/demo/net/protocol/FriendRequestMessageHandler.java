package com.den.demo.net.protocol;

import android.content.Context;
import android.content.SharedPreferences;

import com.den.demo.LoginActivity;
import com.google.protobuf.InvalidProtocolBufferException;

import static android.content.Context.MODE_PRIVATE;

public class FriendRequestMessageHandler extends ProtocolAdapter {

    @Override
    void doProcess(Protocol.message message) throws InvalidProtocolBufferException {
        //收到请求，发送响应
        ResponseMessageHandler.SendResponse(message.getHeader().getSeq(), Protocol.message.Response_Type.NONE);

        //收到添加请求，发送通知显示

    }

    /**
     * 发送好友添加请求
     * @param ApplyTo  申请好友id
     */
   public static void SendRequest(Long ApplyTo) {

        // 获取Token
        Context context = LoginActivity.getContextOfApplication();
        SharedPreferences sharedPreferences = context.getSharedPreferences("data",MODE_PRIVATE);
        long token = sharedPreferences.getLong("userId",0);

        //构建请求消息
        Protocol.message.Friend_Request.Builder requestBuilder = Protocol.message.Friend_Request.newBuilder();
        requestBuilder.setApplyTo(ApplyTo);
        requestBuilder.setApplyFrom(token);

        //组装通知消息
        Protocol.message responseMessage = ProtocolHandler.assembleMessage(FRIEND_REQUEST_MESSAGE_TYPE,requestBuilder.build().toByteArray());
        //将响应加入队列
        ProtocolHandler.getInstance().getMessageQueue().add(responseMessage);
    }
}
