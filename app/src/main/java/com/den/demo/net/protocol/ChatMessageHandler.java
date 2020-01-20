package com.den.demo.net.protocol;

class ChatMessageHandler extends ProtocolAdapter {

    @Override
    void doProcess(Protocol.message message) {
        //将消息解析拿来显示、存入数据库

        // 再发送响应
        ResponseMessageHandler.SendResponse(message.getHeader().getSeq(), Protocol.message.Response_Type.CHAT);
    }

    void sendChatMessage() {

    }
}
