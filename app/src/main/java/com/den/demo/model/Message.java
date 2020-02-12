package com.den.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天窗口message数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    // 最新消息
    private String message;

    // 发送者id
    private Long messageFrom;

    // 是否是我发的 true(右侧显示) false(左侧显示)
    private boolean textByMe;

    // item 最新消息接收时间
    private long ReceiveTime;

}
