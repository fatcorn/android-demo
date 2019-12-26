package com.den.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
