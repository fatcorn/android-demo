package com.den.demo.net.protocol;

import androidx.core.app.NotificationCompat;

import com.den.demo.R;

class ChatMessageHandler extends ProtocolAdapter {

    @Override
    void doProcess(Protocol.message message) {
        // 发送响应
        ResponseMessageHandler.SendResponse(message.getHeader().getSeq(), Protocol.message.Response_Type.CHAT);
        // 将消息解析拿来显示、存入数据库


    }

    void sendChatMessage() {

    }
}
