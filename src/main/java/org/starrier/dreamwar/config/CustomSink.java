package org.starrier.dreamwar.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @author Starrier
 * @date 2018/12/31.
 */
@Component
public interface CustomSink {

    String USER_INPUT = "focusUser";

    /**
     * {@link SubscribableChannel}
     * */
    @Input(USER_INPUT)
    SubscribableChannel focusUser();
}
