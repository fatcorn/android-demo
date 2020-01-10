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
            while (true) {
                try {
                    InputStream is = socket.getInputStream();
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while((len = is.read(buffer)) != -1){
                        outStream.write(buffer,0,len);
                    }
                    byte[] messageByte = outStream.toByteArray();
                    System.out.println("响应数据:"+Arrays.toString(messageByte));
                    outStream.close();
                    is.close();
                    Thread.sleep(200);

                } catch (IOException | InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        }
    }
}
