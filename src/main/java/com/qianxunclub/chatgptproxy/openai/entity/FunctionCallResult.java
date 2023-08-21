package com.qianxunclub.chatgptproxy.openai.entity;


import lombok.Data;

@Data
public class FunctionCallResult {

    String name;

    String arguments;
}
