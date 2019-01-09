package org.starrier.dreamwar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消息发送确认，通过实现 ConfirmCallback 接口，消息发送到指定交换器 Exchange 后触发回调.
 *
 * @author  Starrier
 * @date  2018/11/11.
 */
@Component
public class SuccessConfirmCalBack implements RabbitTemplate.ConfirmCallback {

    /**
     * LOGGER record log.
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(SuccessConfirmCalBack.class);

    /**
     * rabbitTemplate invoke {@link RabbitTemplate}.
     * */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * init method.
     * @see PostConstruct
     * */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(final CorrelationData correlationData, final boolean ack, final String cause) {

        LOGGER.info(" 消息 唯一 标识符:{}", correlationData.getId());
        if (ack) {
            LOGGER.info(" 消息 发送，确认成功:{}", ack);
        } else {
            LOGGER.error(" 消息 发送，确认失败:{}", cause);
        }
    }
}
