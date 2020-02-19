package com.den.demo.net.http.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (UserFriends)实体类
 *
 * @author makejava
 * @since 2019-10-24 16:12:54
 */
@Data
public class UserFriends implements Serializable {

    //id
    private Long id;

    //用户id
    private Long userId;

    //好友id
    private Long friendId;

    //分组id
    private Long groupId;

    //备注名
    private String remark;

    //添加好友时间
    private Date createTime;

}