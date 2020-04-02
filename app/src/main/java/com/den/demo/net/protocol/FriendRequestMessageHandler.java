package com.den.demo.net.protocol;

import android.content.Context;
import android.content.SharedPreferences;
import com.den.demo.LoginActivity;
import com.den.demo.util.UICommunityUtil;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashMap;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;

public class FriendRequestMessageHandler extends ProtocolAdapter  {

    @Override
    void doProcess(Protocol.message message) throws InvalidProtocolBufferException {
        //收到好友请求消息，发送响应, 改变记录状态
        Protocol.message.Friend_Request friendRequest = Protocol.message.Friend_Request.parseFrom(message.getBody());
        Map<String,Long> responseDataMap = new HashMap<>();
        responseDataMap.put("request_id", friendRequest.getRecordsId());
        String responseData = new Gson().toJson(responseDataMap);
        ResponseMessageHandler.SendResponse(message.getHeader().getSeq(), Protocol.message.Response_Type.CHANGE_REQUEST_STATUS, responseData);

        //收到添加请求，发送通知显示
        android.os.Message uiMessage = new android.os.Message();
        uiMessage.what = 3;

        // 没有直接和主线程通信，通过中间类建立联系
        while (true) {
            if (UICommunityUtil.handler != null) {
                UICommunityUtil.handler.sendMessage(uiMessage);
                System.out.println("消息已发送");
                break;
            }
        }
    }

    /**
     * 发送好友添加请求
     * @param ApplyTo  申请好友id
     */
   public static void SendRequest(Long ApplyTo) {

        // 获取Token
        Context context = LoginActivity.getContextOfApplication();
        SharedPreferences sharedPreferences = context.getSharedPreferences("data",MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId",0);

        //构建请求消息
        Protocol.message.Friend_Request.Builder requestBuilder = Protocol.message.Friend_Request.newBuilder();
        requestBuilder.setApplyTo(ApplyTo);
        requestBuilder.setApplyFrom(userId);

        //组装通知消息
        Protocol.message responseMessage = ProtocolHandler.assembleMessage(FRIEND_REQUEST_MESSAGE_TYPE,requestBuilder.build().toByteArray());
        //将响应加入队列
        ProtocolHandler.getInstance().getMessageQueue().add(responseMessage);
    }


}
