package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;

@Data
public class ChatChoice {
    private long index;
    /**
     * 请求参数stream为true返回是delta
     */
    private Message delta;
    /**
     * 请求参数stream为false返回是message
     */
    private Message message;
    private String finishReason;
}
