package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Datum {
    private String object;
    private String id;
    /**
     * 赠送金额：美元
     */
    private BigDecimal grantAmount;
    /**
     * 使用金额：美元
     */
    private BigDecimal usedAmount;
    /**
     * 生效时间戳
     */
    private Long effectiveAt;
    /**
     * 过期时间戳
     */
    private Long expiresAt;
}
