package com.qianxunclub.chatgptproxy.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app-config")
public class AppProperties {

    private String apiHost = "https://api.openai.com/";
    private Proxy proxy = new Proxy();
    private String openaiKey;
    private int timeout = 300;
    private List<String> swaggerBaseUrl = new ArrayList<>();

    private List<String> tokens = new ArrayList<>();

    @Data
    static class Proxy {
        private boolean enable = false;
        private java.net.Proxy.Type type;
        private String ip;
        private Integer prot;
    }
}
