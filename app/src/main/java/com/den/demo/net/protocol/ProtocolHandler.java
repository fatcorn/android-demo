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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


import lombok.Getter;

import static android.content.Context.MODE_PRIVATE;

// 通信助手,单例
public class ProtocolHandler {
    //实列对象
    private static ProtocolHandler instance = new ProtocolHandler();
    //私有构造方法
    private ProtocolHandler() {}
    //获取实列方法
    public static ProtocolHandler getInstance() {
        return instance;
    }
    // socket
    private Socket socket;

    @Getter
    // 消息队列
    private LinkedBlockingQueue<Protocol.message> messageQueue = new LinkedBlockingQueue<>();

    @Getter
    // 消息缓存,收到响应再移除
    private ConcurrentHashMap<Long,Protocol.message> messageBuffer = new ConcurrentHashMap<>();

    // 登录聊天服务器
    public void login() {

        Thread thread = new Thread(() -> {
            try {
                socket = new Socket("10.0.2.2",5678);
                socket.setTcpNoDelay(true);

                // 构建body
                Protocol.message.Login.Builder loginBuilder = Protocol.message.Login.newBuilder();
                // 获取Token
                Context context = LoginActivity.getContextOfApplication();
                SharedPreferences sharedPreferences = context.getSharedPreferences("data",MODE_PRIVATE);
                String token = sharedPreferences.getString("x-auth-token","");
                loginBuilder.setToken(token);
                // body 数组
                byte[] bodyBytes = loginBuilder.build().toByteArray();
                Protocol.message message = assembleMessage(ProtocolAdapter.LOGIN_MESSAGE_TYPE,bodyBytes);

                // 将消息入队
                messageQueue.add(message);
                System.out.println("发送数据：" + Arrays.toString(message.toByteArray()));
                System.out.println("已发送协议");
                // 开起消息发送线程
                new Thread(new Transmitter()).start();
                // 开启消息接收线程
                new Thread(new Receiver()).start();
            } catch (IOException e) {
                e.printStackTrace();
                //提示连接聊天服务器失败
            }
        });
        thread.start();
    }

    /**
     * 组装消息
     * @param messageType 消息类型
     * @param bodyBytes   消息体字节
     * @return message
     */
    static Protocol.message assembleMessage(String messageType, byte[] bodyBytes) {

        // 构建协议
        Protocol.message.Builder messageBuilder = Protocol.message.newBuilder();
        //  构建头
        Protocol.message.Header.Builder headerBuilder = Protocol.message.Header.newBuilder();
        headerBuilder.setMessageType(messageType);
        headerBuilder.setProtocolVersion(1.0f);
        headerBuilder.setSeq(ProtocolUtil.generateNextSeq());

        // 填充协议
        messageBuilder.setHeader(headerBuilder.build());
        messageBuilder.setBody(ByteString.copyFrom(bodyBytes));

        return messageBuilder.build();
    }

    // 消息发送器
    class Transmitter implements Runnable {

        @Override
        public void run() {
            for(;;) {
                try {
                    //阻塞 取消息
                    Protocol.message message = messageQueue.take();
                    //获取输出流
                    OutputStream os = socket.getOutputStream();

                    System.out.println("写入消息:" + Arrays.toString(ProtocolUtil.spliceMessage(message.toByteArray())));
                    //写入拼装报文
                    os.write(ProtocolUtil.spliceMessage(message.toByteArray()));

                    // 将消息存入缓存消息
                    messageBuffer.put(message.getHeader().getSeq(), message);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 监听线程
     * 收包逻辑分析
     * -------------------------------------------------------------------
     * |                                                                  |
     * |    |______|________|______________|______|________|              |
     * |    0  p1     p2          ...                pn   255             |
     * |    接收报文情况说明:                                               |
     * |    1: 接收一个包:                                                 |
     * |       a.p1.len <= 256                                            |
     * |       b.p2.len >  256                                            |
     * |    2: 接收多个包                                                  |
     * |       a.(p1 + p2).len <= 256                                     |
     * |       b.(p1 + p2).len > 256                                      |
     * |                                                                  |
     * --------------------------------------------------------------------
     *
     */
    // 监听线程
    class Receiver implements Runnable {
        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                // 字节缓冲区
                ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
                //缓冲区
                byte[] buffer = new byte[256];
                // 读取流的长度
                int streamLen;
                // 报文长度,等于0报文解析完成的标志
                int messageLength = 0;
                // 不停的读取数据
                for (;;){
                    // 字节缓冲区为0时，开始读取缓冲区中新的报文
                    streamLen = is.read(buffer);
                    bufferStream.write(buffer, bufferStream.size(), streamLen);

                    //报文加载完标志
                    boolean messageLoadOverFlag = true;

                    // 缓冲区有报文，且当前报文包已加载完，继续读，直到缓冲区报文读完
                    while (bufferStream.size() > 0 && messageLoadOverFlag) {
                        //读取缓冲区字节
                        byte[] streamByte = bufferStream.toByteArray();

                        //报文长度声明报文字节段 中解析出 报文长度
                        if (streamByte.length >= 4 && messageLength == 0) {
                            //报文长度声明报文字节段
                            byte[] messageLengthByte = new byte[4];
                            System.arraycopy(streamByte,0,messageLengthByte,0,messageLengthByte.length);
                            messageLength = ProtocolUtil.spliceMessageLength(messageLengthByte);
                        }

                        // 报文长度大于0，且已读取出所有报文，开始解析数据报文
                        if(messageLength > 0 && streamByte.length - 4 >= messageLength) {
                            // 报文正文字节数组
                            byte[] messageByte = new byte[messageLength];
                            // 流中剩余字节
                            byte[] leftByte = new byte[streamByte.length - messageLength - 4];
                            //拷贝出报文正文字节数组
                            System.arraycopy(streamByte,4,messageByte,0,messageLength);
                            //拷贝出流中剩余字节
                            System.arraycopy(streamByte, 4 + messageLength,leftByte ,0,leftByte.length);
                            //清空缓存区,并将未处理报文写入
                            bufferStream.reset();
                            bufferStream.write(leftByte,0, leftByte.length);
                            // 考虑解析时发生异常
                            Protocol.message message =  Protocol.message.parseFrom(messageByte);
                            //next 处理消息
                            ProtocolAdapter.distributeMessage(message);
                            // 报文长度置0，表示一个报文数据已读取,指示读取下一个报文长度
                            messageLength = 0;
                        }
                        else {
                            // 报文未加载完，跳出循环
                            messageLoadOverFlag = false;
                        }
                    }
                }
            } catch (IOException e) {
                //sock 已关闭,尝试再次登录
                ProtocolHandler.getInstance().login();
                e.printStackTrace();
            }
        }
    }
}
