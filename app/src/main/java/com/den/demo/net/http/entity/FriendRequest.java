package com.den.demo.net.http.entity;

import com.den.demo.net.http.enums.FriendRequestStatusEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Date;
import java.io.Serializable;

/**
 * 好友添加请求(FriendRequest)实体类
 *
 * @author makejava
 * @since 2019-10-24 16:11:09
 */
@Data
public class FriendRequest implements Serializable {

    //id
    private Long id;

    //请求用户id
    private Long requestId;

    //target_id
    private Long targetId;


    //状态{0:已拒绝,1:验证中,2:已接受,3:已过期}
    @JsonDeserialize(using = FriendRequestStatusEnumDeserializer.class)
    private FriendRequestStatusEnum status;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;



}
class FriendRequestStatusEnumDeserializer  extends JsonDeserializer<FriendRequestStatusEnum> {

    @Override
    public FriendRequestStatusEnum deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        return FriendRequestStatusEnum.forValue(parser.getValueAsString());
    }
}