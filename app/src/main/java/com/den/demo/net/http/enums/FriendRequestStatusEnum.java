package com.den.demo.net.http.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 好友请求状态枚举
 *
 * @author fatKarin
 * @date 2019/9/6 10:44
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum FriendRequestStatusEnum {
    //0
    REFUSE("已拒绝"),
    //1
    VERIFYING("验证中"),
    //2
    ACCEPTED("已接受"),
    //3
    EXPIRED("已过期"),
    ;
    //
    private String cnName;

    public static FriendRequestStatusEnum forValue(String value) {
        return FriendRequestStatusEnum.valueOf(value);  // 比较
    }
}
