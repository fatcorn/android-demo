package com.den.demo.net.http.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 0, 1 公共常量
 *
 * @author fatKarin
 * @date 2019/9/6 10:44
 */
@AllArgsConstructor
@Getter
public enum CommonEnum {
    DISABLE("否"),
    ENABLE("是");

    private String cnName;
}
