package com.qianxunclub.chatgptproxy.util;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.qianxunclub.chatgptproxy.openai.entity.FunctionCallResult;
import com.qianxunclub.chatgptproxy.openai.entity.Message;
import com.qianxunclub.chatgptproxy.openai.entity.Model;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UtilityClass
public class TokensUtil {

    private static final Map<String, Encoding> modelEncodingMap = new HashMap<>();
    private static final EncodingRegistry encodingRegistry = Encodings.newDefaultEncodingRegistry();

    static {
        for (Model model : Model.values()) {
            Optional<Encoding> encodingForModel = encodingRegistry.getEncodingForModel(model.getName());
            encodingForModel.ifPresent(encoding -> modelEncodingMap.put(model.getName(), encoding));
        }
    }

    /**
     * 计算tokens
     *
     * @param modelName 模型名称
     * @param messages  消息列表
     * @return 计算出的tokens数量
     */

    public static int tokens(String modelName, List<Message> messages) {
        Encoding encoding = modelEncodingMap.get(modelName);
        if (encoding == null) {
            throw new IllegalArgumentException("Unsupported model: " + modelName);
        }

        int tokensPerMessage = 0;
        int tokensPerName = 0;
        if (modelName.contains(Model.GPT_4.getName())
                || modelName.equals(Model.GPT_4_0314.getName())
                || modelName.equals(Model.GPT_4_32K_0314.getName())
        ) {
            tokensPerMessage = 3;
            tokensPerName = 1;
        } else if (modelName.equals(Model.GPT_3_5_TURBO_0301.getName())) {
            tokensPerMessage = 4;
            tokensPerName = -1;
        } else if (modelName.contains(Model.GPT_3_5_TURBO.getName())) {
            tokensPerMessage = 3;
            tokensPerName = 1;
        } else {
            log.warn("不支持的model {}. See https://github.com/openai/openai-python/blob/main/chatml.md 更多信息.", modelName);
        }
        int sum = 0;
        for (Message msg : messages) {
            sum += tokensPerMessage;
            sum += encoding.encode(msg.getContent()).size();
            sum += encoding.encode(msg.getRole()).size();
            sum += encoding.encode(msg.getName()).size();
            FunctionCallResult functionCall = msg.getFunctionCall();
            sum += Objects.isNull(functionCall) ? 0 : encoding.encode(functionCall.toString()).size();
            if (StringUtils.isNotBlank(msg.getName())) {
                sum += tokensPerName;
            }
        }
        sum += 3;
        return sum;
    }
}