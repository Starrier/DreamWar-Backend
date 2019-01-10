package org.starrier.dreamwar.config.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消息发送确认，通过实现 ReturnCallback 接口，如果消息从交换器发送到对应队列失败时触发
 * 比如，根据发送消息时指定的 routing Key 找不到队列时会触发
 *
 * @Author Starrier
 * @Time 2018/11/11.
 */
@Component
public class FailConfirmCallback implements RabbitTemplate.ReturnCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailConfirmCallback.class);

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
        LOGGER.info("消息主体 message:{}", message);
        LOGGER.info("消息主体 message:{}", replyCode);
        LOGGER.info("描述:{}",replyText);
        LOGGER.info("消息使用的交换器:{}", exchange);
        LOGGER.info("消息使用的路由键:{}", routingkey);
    }
}

























