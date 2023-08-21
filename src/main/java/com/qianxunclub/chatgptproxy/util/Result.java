package com.qianxunclub.chatgptproxy.util;

import lombok.Data;
import lombok.Getter;

@Getter
public class Result<T> {
    private boolean success;
    private T data;
    private Error error;

    public static Result success(Object data) {
        Result result = new Result();
        result.data = data;
        return result;
    }

    public static Result failed(Error error) {
        Result result = new Result();
        result.success = false;
        result.error = error;
        return result;
    }

    @Override
    public String toString() {
        return String.format("success: %s, error: %s", success, error.toString());
    }

    @Data
    public static class Error {
        private String message;
        private String type;
        private String param;
        private String code;
    }

}
