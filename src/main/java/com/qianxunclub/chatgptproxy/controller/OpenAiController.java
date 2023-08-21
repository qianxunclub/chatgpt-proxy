package com.qianxunclub.chatgptproxy.controller;

import com.qianxunclub.chatgptproxy.openai.OpenAiApi;
import com.qianxunclub.chatgptproxy.openai.entity.ChatCompletion;
import com.qianxunclub.chatgptproxy.openai.entity.ChatCompletionResponse;
import com.qianxunclub.chatgptproxy.openai.entity.ImageRequest;
import com.qianxunclub.chatgptproxy.openai.entity.ImageResponse;
import com.qianxunclub.chatgptproxy.openai.entity.Message;
import com.qianxunclub.chatgptproxy.openai.entity.Model;
import com.qianxunclub.chatgptproxy.openai.entity.SubscriptionData;
import com.qianxunclub.chatgptproxy.openai.entity.UseageResponse;
import com.qianxunclub.chatgptproxy.util.TokensUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenAiController {

    @Autowired
    private OpenAiApi openAiApi;

    @PostMapping("countTokens")
    public int countTokens(@RequestBody ChatCompletion chatCompletion) {
        return TokensUtil.tokens(chatCompletion.getModel(), chatCompletion.getMessages());
    }

    @GetMapping("models")
    public Model[] models() {
        return Model.values();
    }

    @PostMapping("chatCompletion")
    public ChatCompletionResponse chatCompletion(HttpServletRequest request, @RequestBody ChatCompletion chatCompletion) {
        return openAiApi.chatCompletion(request, chatCompletion);
    }

    @PostMapping("chat")
    public ChatCompletionResponse chatMessage(HttpServletRequest request, @RequestBody List<Message> messages) {
        ChatCompletion chatCompletion = new ChatCompletion();
        chatCompletion.setMessages(messages);
        return openAiApi.chatCompletion(request, chatCompletion);
    }

    @PostMapping("question")
    public ChatCompletionResponse question(HttpServletRequest request, @RequestBody String message) {
        ChatCompletion chatCompletion = new ChatCompletion();
        chatCompletion.setMessages(List.of(Message.of(message)));
        return openAiApi.chatCompletion(request, chatCompletion);
    }

    @GetMapping("subscription")
    public SubscriptionData subscription(HttpServletRequest request) {
        return openAiApi.subscription(request);
    }

    @GetMapping("usage")
    public UseageResponse usage(HttpServletRequest request, String startDate, String endDate) {
        return openAiApi.usage(request, startDate, endDate);
    }

    @PostMapping("images/generations")
    public ImageResponse imageGenerations(HttpServletRequest request, @RequestBody ImageRequest imageRequest) {
        return openAiApi.imageGenerations(request, imageRequest);
    }

}
