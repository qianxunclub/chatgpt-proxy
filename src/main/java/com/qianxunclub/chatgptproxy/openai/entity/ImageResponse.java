package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ImageResponse {
    private String created;
    private List<Map> data = new ArrayList<>();
}
