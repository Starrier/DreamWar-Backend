package org.starrier.dreamwar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.starrier.dreamwar.config.CustomSink;
import org.starrier.dreamwar.util.User;


import java.util.Map;

/**
 * @author Starrier
 * @date 2018/12/31.
 */
public class NettyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyService.class);

    @StreamListener(value = CustomSink.USER_INPUT)
    public void userReceive(@Payload User user, @Headers Map headers) {
        LOGGER.info(headers.get("contentType").toString());
        LOGGER.info("Received from {} channel username: {}", CustomSink.USER_INPUT, user.getUsername());
    }
}
