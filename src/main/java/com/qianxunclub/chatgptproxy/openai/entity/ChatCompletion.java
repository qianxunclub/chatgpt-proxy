package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class ChatCompletion {

    private String model = Model.GPT_3_5_TURBO.getName();

    private List<Message> messages;
    /**
     * 使用什么取样温度，0到2之间。越高越奔放。越低越保守。
     * <p>
     * 不要同时改这个和topP
     */
    private double temperature = 0.9;

    /**
     * 0-1
     * 建议0.9
     * 不要同时改这个和temperature
     */
    private double topP = 0.9;


    /**
     * auto
     */
    String function_call;

    List<ChatFunction> functions;

    /**
     * 结果数。
     */
    private Integer n = 1;


    /**
     * 是否流式输出.
     * default:false
     */
    private boolean stream = false;
    /**
     * 停用词
     */
    private List<String> stop;
    /**
     * 3.5 最大支持4096
     * 4.0 最大32k
     */
    private Integer maxTokens;


    private double presencePenalty;

    /**
     * -2.0 ~~ 2.0
     */
    private double frequencyPenalty;

    private Map logitBias;
    /**
     * 用户唯一值，确保接口不被重复调用
     */
    private String user;
}


