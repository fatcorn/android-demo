package com.den.demo.util;

import java.time.Instant;
import java.util.Locale;

/**
 * 协议工具类
 */
public class ProtocolUtil {

    private static int seq = -1;

    // 报文长度声明占据的字节数
    private static int spaceLength = 4;

    // 报文长度声明进制采用128进制
    private static int messageLengthDeclareNumberBase = 128;
    // 报文长度声明计算百位数的根
    private static int hundredBitNumberBase = 128 * 128;
    // 报文长度声明计算千位数的根
    private static int thousandBitNumberBase = 128 * 128 * 128;

    // 拼装报文长度声明byte[],低位进高位，逢128 进1
    public static byte[] sliceMessageLengthDeclareArray(Integer length) {
        byte[] bytes = new byte[spaceLength];
        bytes[0] = (byte) (length%messageLengthDeclareNumberBase);
        bytes[1] = (byte) (length/messageLengthDeclareNumberBase);
        bytes[2] = (byte) (length/hundredBitNumberBase);
        bytes[3] = (byte) (length/thousandBitNumberBase);
        return bytes;
    }

    // 拼接报文 = 报文长度声明+报文正文
    public static byte[] spliceMessage(byte[] messageByte) {
        // 计算报文长度声明
        byte[] lengthDeclareBytes = new byte[spaceLength];
        lengthDeclareBytes[0] = (byte) (messageByte.length%messageLengthDeclareNumberBase);
        lengthDeclareBytes[1] = (byte) (messageByte.length/messageLengthDeclareNumberBase);
        lengthDeclareBytes[2] = (byte) (messageByte.length/hundredBitNumberBase);
        lengthDeclareBytes[3] = (byte) (messageByte.length/thousandBitNumberBase);
        //连接报文产犊声明字节 + 报文正文
        byte[] result = new byte[lengthDeclareBytes.length + messageByte.length];
        System.arraycopy(lengthDeclareBytes,0,result,0,lengthDeclareBytes.length);
        System.arraycopy(messageByte,0,result,lengthDeclareBytes.length, messageByte.length);
        return result;
    }

    // 计算报文长度
    public static int spliceMessageLength(byte[] lengthByte){
        int messageLength = lengthByte[0] + lengthByte[1]*128 + lengthByte[2]*128*128 + lengthByte[3]*128*128*128;
        return messageLength;
    }

    // 产生消息请求头部序列,微秒timestamp + 000-999
    public static long generateNextSeq() {
        if (seq == 999) {
            seq = 0;
        } else {
            seq++;
        }
        String seqStr = String.format(Locale.CHINA,"%03d", seq);
        //纳秒部分
        String nanoString =  String.valueOf(Instant.now().getNano());
        //seq 基数
        String timestamp = Instant.now().getEpochSecond() + nanoString.substring(0,nanoString.length() -3);
        return Long.valueOf(timestamp + seq);
    }
}
