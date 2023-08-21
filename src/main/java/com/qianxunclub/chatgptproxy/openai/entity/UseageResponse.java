package com.qianxunclub.chatgptproxy.openai.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 余额查询接口返回值
 *
 * @author plexpt
 */
@Data
public class UseageResponse {

    /**
     * 总使用金额：美元
     */
    private BigDecimal totalUsage;

}
