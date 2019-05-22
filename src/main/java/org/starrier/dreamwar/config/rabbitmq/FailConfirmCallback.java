package org.starrier.dreamwar.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Starrier
 * @date 2018/11/11.
 */
@Slf4j
@Component
public class FailConfirmCallback implements RabbitTemplate.ReturnCallback {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public FailConfirmCallback(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingkey) {
        log.info("消息主体 message:{}", message);
        log.info("消息主体 message:{}", replyCode);
        log.info("描述:{}", replyText);
        log.info("消息使用的交换器:{}", exchange);
        log.info("消息使用的路由键:{}", routingkey);
    }
}

























