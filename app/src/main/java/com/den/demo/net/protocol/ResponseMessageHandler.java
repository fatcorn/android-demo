package com.den.demo.net.protocol;


import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 响应助手
 */
class ResponseMessageHandler extends ProtocolAdapter {

    @Override
    void doProcess(Protocol.message message) {
        try {
            Protocol.message.Response response = Protocol.message.Response.parseFrom(message.getBody());
            System.out.println("已分配消息");
            switch (response.getType()) {
                // 消息已发送响应,R1
                case CHAT:
                    break;
                case NONE:
                    break;
                // 停止发送通知响应 r4
                case STOP_NOTIFY:
                    break;
                // 收到此类响应，移除消息缓存（M(seq1)）,发送通知N1
                case NOTIFY:
                    //移除消息缓存
                    ProtocolHandler.getInstance().getMessageBuffer().remove(response.getAck() -1);
                    //构建通知消息
                    Protocol.message.Notify.Builder notifyBuilder = Protocol.message.Notify.newBuilder();
                    notifyBuilder.setSeq(response.getAck() -1);
                    //组装通知消息
                    Protocol.message notifyMessage = ProtocolHandler.assembleMessage("notify_message",notifyBuilder.build().toByteArray());
                    //将通知加入队列
                    ProtocolHandler.getInstance().getMessageQueue().add(notifyMessage);
                    break;
            }
        } catch (InvalidProtocolBufferException e) {
            System.out.println("响应消息转换失败");
            e.printStackTrace();
        }
    }

    /**
     * 发送响应
     * @param seq  对应seq
     * @param type 响应类型
     */
    static void SendResponse(long seq, Protocol.message.Response_Type type,@Nullable String data) {
        //构建响应消息
        Protocol.message.Response.Builder responseBuilder = Protocol.message.Response.newBuilder();
        responseBuilder.setAck(seq + 1);
        responseBuilder.setIsSuccess(true);
        responseBuilder.setType(type);
        responseBuilder.setResponseData(data);

        //组装通知消息
        Protocol.message responseMessage = ProtocolHandler.assembleMessage("response_message",responseBuilder.build().toByteArray());
        //将响应加入队列
        ProtocolHandler.getInstance().getMessageQueue().add(responseMessage);
    }
}
