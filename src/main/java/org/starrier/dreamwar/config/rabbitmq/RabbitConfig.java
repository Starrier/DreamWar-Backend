package org.starrier.dreamwar.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.starrier.dreamwar.utils.common.enums.ExchangeEnum;
import org.starrier.dreamwar.utils.common.enums.QueueEnum;

/**
 * @author Starrier
 * @date 2018/11/11.
 */
@Slf4j
@Configuration
public class RabbitConfig {

    public static final String TOPIC_QUEUE = "topic.queue";
    public static final String TOPIC_EXCHANGE = "commentexchange";

    /**
     * 用户评论队列
     */
    @Bean
    public Queue queue() {
        return new Queue(TOPIC_QUEUE, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("topic.#");
    }

    @Bean
    public TopicExchange userTopicExchange() {
        TopicExchange topicExchange = new TopicExchange(ExchangeEnum.USER_REGISTER_TOPIC_EXCHANGE.getName());
        log.info("用户注册交换机。");
        return topicExchange;
    }

    /**
     * 配置用户注册
     * 发送激活邮件消息队列
     * 并设置持久化队列
     *
     * @return
     */
    @Bean
    public Queue sendRegisterMailQueue() {
        Queue queue = new Queue(QueueEnum.USER_REGISTER_SEND_MAIL.getName(), true);
        log.info("创建用户注册，发送邮件队列");
        return queue;
    }

    /**
     * 配置用户注册.
     * 创建账户消息队列.
     * 并设置持久化队列.
     *
     * @return return the new queue.
     */
    @Bean
    public Queue createAccountQueue() {
        Queue queue = new Queue(QueueEnum.USER_REGISTER_CREATE_ACCOUNT.name(), true);
        log.info("创建用户注册账号，操作成功队列.");
        return queue;
    }

    /**
     * 绑定用户发送注册激活邮件队列到用户注册主题交换配置.
     *
     * @return return the new Binding.
     */
    @Bean
    public Binding sendMailBinding() {
        Binding binding = BindingBuilder.bind(sendRegisterMailQueue()).to(userTopicExchange()).with(QueueEnum.USER_REGISTER_SEND_MAIL.getRoutingKey());
        log.info("绑定发送邮件到注册交换成功");
        return binding;
    }

    /**
     * 绑定用户创建账户到用户注册主题交换配置.
     *
     * @return the new Binding.
     */
    @Bean
    public Binding createAccountBinding() {
        Binding binding = BindingBuilder.bind(createAccountQueue()).to(userTopicExchange()).with(QueueEnum.USER_REGISTER_CREATE_ACCOUNT.getRoutingKey());
        log.info("绑定创建账号到注册交换成功。");
        return binding;
    }

    /**
     * 因为要设置回调类，所以应是prototype类型，
     * 如果是singleton类型，则回调类为最后一次设置.
     *
     * @return return the Override rabbitTemplate {@link RabbitTemplate}.
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}


