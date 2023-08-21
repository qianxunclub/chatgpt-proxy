package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Model {
    /**
     * gpt-3.5
     */
    // 稳定版本
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_16K("gpt-3.5-turbo-16k"),

    // 最新开发版本
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),

    /**
     * GPT4.0
     */
    // 稳定版本
    GPT_4("gpt-4"),
    GPT_4_32K("gpt-4-32k"),

    // 最新开发版本
    GPT_4_0314("gpt-4-0314"),
    GPT_4_32K_0314("gpt-4-32k-0314"),

    ;
    private String name;
}