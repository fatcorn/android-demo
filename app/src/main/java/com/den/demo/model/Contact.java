package com.den.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 联系人列表item数据
 */
@Data
@AllArgsConstructor
public class Contact {

    // 备注昵称
    private String nickName;

    //排序字符
    private String sortTag;

    public Contact(String nickName) {
        this.nickName = nickName;
    }
}
