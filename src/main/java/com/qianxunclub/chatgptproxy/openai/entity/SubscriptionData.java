package com.qianxunclub.chatgptproxy.openai.entity;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionData {

    /**
     * 赠送金额：美元
     */
    private BigDecimal hardLimitUsd;
}
