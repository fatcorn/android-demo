package com.den.demo.util;

/**
 * 协议工具类
 */
public class ProtocolUtil {
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
    public static byte[] spliceMessage(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1,0,data3,0,data1.length);
        System.arraycopy(data2,0,data3,data1.length, data2.length);
        return data3;
    }

    // 计算报文长度
    public static int SpliceMessageLength(byte[] lengthByte){
        int messageLength = lengthByte[0] + lengthByte[1]*128 + lengthByte[2]*128*128 + lengthByte[3]*128*128*128;
        return messageLength;
    }
}
