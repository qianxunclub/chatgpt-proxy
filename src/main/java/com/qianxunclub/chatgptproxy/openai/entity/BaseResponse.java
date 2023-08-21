package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;

import java.util.List;

@Data
public class BaseResponse<T> {
    private String object;
    private List<T> data;
    private Error error;


    @Data
    public static class Error {
        private String message;
        private String type;
        private String param;
        private String code;
    }
}
