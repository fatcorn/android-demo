package com.den.demo.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

/**
 * 聊天列表
 */
@Entity(tableName = "chat_list")
public class ChatList {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    /**
     * 用户id
     */
    @ColumnInfo(name = "contact_id")
    public Long contactId;

    // 备注昵称
    @ColumnInfo(name = "nick_name")
    public String nickName;

    // 最新消息
    @ColumnInfo(name = "last_message")
    public String lastMessage;

    // item 最新消息接收时间
    @ColumnInfo(name = "last_message_receive_time")
    public Date lastMessageReceiveTime;
}
