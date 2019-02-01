package org.starrier.dreamwar.common.enums;

import lombok.Getter;

import java.io.Serializable;

/**
 * @Author Starrier
 * @Time 2018/11/11.
 */
@Getter
public enum ExchangeEnum implements Serializable
{
    /**
     * 用户注册交换配置枚举
     */
    USER_REGISTER_TOPIC_EXCHANGE("register.topic.exchange")
    ;
    private String name;

    ExchangeEnum(String name) {
        this.name = name;
    }
}
