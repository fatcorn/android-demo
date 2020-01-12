package com.den.demo.net.protocol;

import android.content.Context;
import android.content.SharedPreferences;

import com.den.demo.LoginActivity;
import com.den.demo.util.ProtocolUtil;
import com.google.protobuf.ByteString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

// 通信助手
public class ChatHandler {

    private Socket socket;

    // 登录聊天服务器
    public void login() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.0.2.2",5678);
                    socket.setTcpNoDelay(true);

                    OutputStream os = socket.getOutputStream();

                    // 构建协议
                    Protocol.message.Builder messageBuilder = Protocol.message.newBuilder();
                    //  构建头
                    Protocol.message.Header.Builder headerBuilder = Protocol.message.Header.newBuilder();
                    headerBuilder.setMessageType("login_message");
                    headerBuilder.setProtocolVersion(1.0f);
                    // 构建body
                    Protocol.message.Login.Builder loginBuiler = Protocol.message.Login.newBuilder();

                    // 获取Token
                    Context context = LoginActivity.getContextOfApplication();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("data",MODE_PRIVATE);
                    String token = sharedPreferences.getString("x-auth-token","");
                    loginBuiler.setToken(token);

                    // 填充协议
                    messageBuilder.setHeader(headerBuilder.build());
                    messageBuilder.setBody(ByteString.copyFrom(loginBuiler.build().toByteArray()));

                    System.out.println("发送数据：" + Arrays.toString(messageBuilder.build().toByteArray()));
                    //拼装报文长度声明报文
                    byte[] messageLengthDeclareArray = ProtocolUtil.sliceMessageLengthDeclareArray(messageBuilder.build().toByteArray().length);
                    //写入拼装报文
                    os.write(ProtocolUtil.spliceMessage(messageLengthDeclareArray, messageBuilder.build().toByteArray()));
                    System.out.println("已发送协议");

                    new Thread(new Receiver()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // 消息发送器
    class Sender implements Runnable {

        @Override
        public void run() {

        }
    }

    public static ChatHandler newInstance() {
        return new ChatHandler();
    }

    // 监听线程
    class Receiver implements Runnable {

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[256];
                // 读取流的长度
                int streamLen = 0;
                // 报文长度
                int messageLength = 0;
                // 不停的读取数据
                for (;;){
                    streamLen = is.read(buffer);
                    bufferStream.write(buffer, 0, streamLen);
                    //已读取字节
                    byte[] streamByte = bufferStream.toByteArray();
                    //报文长度声明报文字节段 中解析出 报文长度
                    if (streamByte.length >= 4 && messageLength == 0) {
                        //报文长度声明报文字节段
                        byte[] messageLengthByte = new byte[4];
                        System.arraycopy(streamByte,0,messageLengthByte,0,messageLengthByte.length);
                        messageLength = ProtocolUtil.SpliceMessageLength(messageLengthByte);
                    }
                    // 报文长度大于0，且已读取出所有报文，开始解析数据报文
                    if(messageLength > 0 && streamByte.length - 4 >= messageLength) {
                        // 报文正文字节数组
                        byte[] messageByte = new byte[messageLength];
                        // 流中剩余字节
                        byte[] leftByte = new byte[streamByte.length - messageLength - 4];
                        //拷贝出报文正文字节数组
                        System.arraycopy(streamByte,4,messageByte,0,messageLength);
                        //如果流中还有报文,拷贝出来，再清空字节缓存流
                        if(leftByte.length > 0) {
                            //拷贝出流中剩余字节
                            System.arraycopy(streamByte,streamByte.length - 4 - messageLength,leftByte,0,leftByte.length);
                            //清空缓存区,并将未处理报文写入
                            bufferStream.reset();
                            bufferStream.write(leftByte,0, leftByte.length);
                        }
                        Protocol.message message =  Protocol.message.parseFrom(messageByte);
                        //next 处理消息
                    }
                    Thread.sleep(200);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
