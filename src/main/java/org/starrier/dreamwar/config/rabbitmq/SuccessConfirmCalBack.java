package org.starrier.dreamwar.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消息发送确认，通过实现 ConfirmCallback 接口，消息发送到指定交换器 Exchange 后触发回调.
 *
 * @author Starrier
 * @date 2018/11/11.
 */
@Slf4j
@Component
public class SuccessConfirmCalBack implements RabbitTemplate.ConfirmCallback {

    /**
     * rabbitTemplate invoke {@link RabbitTemplate}.
     */
    private final RabbitTemplate rabbitTemplate;

    public SuccessConfirmCalBack(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * init method.
     *
     * @see PostConstruct
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(final CorrelationData correlationData, final boolean ack, final String cause) {

        log.info(" 消息 唯一 标识符:{}", correlationData.getId());
        if (ack) {
            log.info(" 消息 发送，确认成功:{}", ack);
        } else {
            log.error(" 消息 发送，确认失败:{}", cause);
        }
    }
}
