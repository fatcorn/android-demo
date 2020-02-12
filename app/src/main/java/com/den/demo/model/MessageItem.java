package com.den.demo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息列表item数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageItem {

    // 备注昵称
    private String nickName;

    // 最新消息
    private String lastMessage;

    // item 最新消息接收时间
    private Date lastMessageReceiveTime;

    public MessageItem(String nickName, String lastMessage) {
        this.nickName = nickName;
        this.lastMessage = lastMessage;
    }
}
