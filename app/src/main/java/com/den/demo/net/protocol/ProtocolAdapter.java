package com.den.demo.net.protocol;

import com.google.common.base.CaseFormat;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//协议适配器
abstract class ProtocolAdapter {
    //线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    //处理接收消息
    abstract void doProcess(Protocol.message message) throws InvalidProtocolBufferException;


    // 分发消息给各个消息handler
    static void distributeMessage(Protocol.message message) {
        //将消息提交给线程池处理
        executorService.submit(() ->
            {
                String className = TypeHandler(message.getHeader().getMessageType());
            try {
                Class obj = Class.forName("com.den.demo.net.protocol." + className);
                ProtocolAdapter protocolAdapter = (ProtocolAdapter) obj.newInstance();
                System.out.println("以收到:" + obj.getName() + "消息");
                protocolAdapter.doProcess(message);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
            });
    }

    // 将消息头部类型转换为驼峰格式,组装成对应类名
    private static String TypeHandler(String messageType) {
        // 下划线转驼峰
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, messageType) + "Handler";
    }
}
