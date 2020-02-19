package com.den.demo.net.http.entity;

import lombok.Data;

@Data
public class ResponseMessage {

    private int code;

    private String message;

    private Object data;

    private boolean success;
}
