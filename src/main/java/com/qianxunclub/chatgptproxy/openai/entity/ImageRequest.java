package com.qianxunclub.chatgptproxy.openai.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ImageRequest {

    @NotNull
    private String prompt;
    private int n = 1;
    private String size = "512x512";
}
