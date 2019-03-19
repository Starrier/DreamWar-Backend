package org.starrier.dreamwar.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.starrier.dreamwar.user.entity.User;
import org.starrier.dreamwar.utils.common.enums.ExchangeEnum;
import org.starrier.dreamwar.mail.MailService;
import org.starrier.dreamwar.utils.JsonConvertUtils;

/**
 * @Author Starrier
 * @Time 2018/11/11.
 */
@Component
public class RabbitmqServiceImpl implements RabbitmqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqService.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private MailService mailService;

    @Autowired
    public RabbitmqServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

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
    public void send(ExchangeEnum exchangeEnum, String routingKey, Object message) throws Exception {

        rabbitTemplate.convertAndSend(exchangeEnum.getName(),routingKey,message);
    }


    @Async
    @Override
    @RabbitListener(queues = "register.mail")
    @RabbitHandler
    public void userRegisterSendAndAck(ExchangeEnum exchangeEnum, String routingKey, @Payload JSONObject message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchangeEnum.getName(), routingKey, message, correlationData);

        User user = JsonConvertUtils.convertJSONToObject(message);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("UserDto:[{}]", user);
        }

        String stringBuffer = "Hello，" +
                user.getUsername() +
                ",Your account has been registered successfully";
        mailService.sendHtmlMail(user.getEmail(), "test", stringBuffer);
    }
}