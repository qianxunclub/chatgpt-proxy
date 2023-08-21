package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;

import java.util.List;

/**
 * chat答案类
 */
@Data
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;
}
