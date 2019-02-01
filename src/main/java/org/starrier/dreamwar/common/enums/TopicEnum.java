package org.starrier.dreamwar.common.enums;

import lombok.Getter;

/**
 * @Author Starrier
 * @Time 2018/11/11.
 */
@Getter
public enum TopicEnum {
    /**
     * 用户注册topic路由key配置
     */
    USER_REGISTER("register.user")
    ;

    private String topicRouteKey;

    TopicEnum(String topicRouteKey) {
        this.topicRouteKey = topicRouteKey;
    }
}