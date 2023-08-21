package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;

@Data
public class Usage {
    private long promptTokens;
    private long completionTokens;
    private long totalTokens;
}
