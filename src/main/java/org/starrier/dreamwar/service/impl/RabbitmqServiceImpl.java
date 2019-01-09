package org.starrier.dreamwar.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.starrier.dreamwar.enums.ExchangeEnum;
import org.starrier.dreamwar.service.RabbitmqService;

/**
 * @Author Starrier
 * @Time 2018/11/11.
 */
@Component
public class RabbitmqServiceImpl implements RabbitmqService {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息到rabbitmq消息队列
     *
     * @param message      消息内容
     * @param exchangeEnum 交换配置枚举
     * @param routingKey   路由key
     * @throws Exception
     */
    @Async
    @Override
    public void send(ExchangeEnum exchangeEnum, String routingKey,Object message) throws Exception {

        rabbitTemplate.convertAndSend(exchangeEnum.getName(),routingKey,message);
    }


    @Async
    @Override
    public void sendAndAck(ExchangeEnum exchangeEnum, String routingKey, Object message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchangeEnum.getName(), routingKey, message, correlationData);
    }
}
