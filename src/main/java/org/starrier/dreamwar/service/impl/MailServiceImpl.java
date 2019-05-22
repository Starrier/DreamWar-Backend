package org.starrier.dreamwar.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.starrier.dreamwar.model.vo.User;
import org.starrier.dreamwar.service.interfaces.MailService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author Starrier
 * @date 2018/7/6.
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    /**
     * @Param from    邮件发送方
     * @Param to      邮件发送对象
     * @Param subject 邮件主题
     */
    @Value(value = "1342878298@qq.com")
    private String from;

    @Value(value = "register.topic.exchange")
    private String exchange;

    @Value(value = "register.user")
    private String routeKey;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final RabbitTemplate rabbitTemplate;


    private static final String TITLE_SIGN_UP = "Register UserBO Active";

    private static final String CONTENT = "";

    public MailServiceImpl(TemplateEngine templateEngine, JavaMailSender mailSender, RabbitTemplate rabbitTemplate) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     *  Send  email for user to validate account.
     *
     * @param user {@link User}
     * @param token {@link String}
     */
    public void userValidate(User user, String token) {
        String link = "http://localhost:8080/validate/" + token;
        String message = String.format(CONTENT + user.getUsername(), link, link);
    }

    @Override
    @SneakyThrows(Exception.class)
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        log.info("Mail send to：{}，from from:{},and subject:{}", to, from, subject);
    }

    @Override
    @SneakyThrows(MessagingException.class)
    public void sendHtmlMail(String to, String subject, String content) {
        log.info("发送 HTML Email,Start......");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        log.info("html邮件发送成功:{}", helper);

    }

    @Override
    @SneakyThrows(MessagingException.class)
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = prepareMessage(to, subject, content, message);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }

    @Override
    @SneakyThrows(MessagingException.class)
    public void sendInlineResourceMail(String to, String subject, String content, String resourcePath, String resourceId) {
        MimeMessage message = mailSender.createMimeMessage();
        FileSystemResource res = new FileSystemResource(new File(resourcePath));
        MimeMessageHelper helper = prepareMessage(to, subject, content, message);
        helper.addInline(resourceId, res);
        mailSender.send(message);
    }

    @Override
    @SneakyThrows(MessagingException.class)
    public void sendTemplateMail(String to, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        Context context = new Context();
        context.setVariable("id", "wenter");
        String emailContent = templateEngine.process("emailTemplate", context);
        helper.setText(emailContent, true);
        mailSender.send(message);
    }

    @Override
    @SneakyThrows(MessagingException.class)
    public void sendEmail(String message) {
        rabbitmqTemplate.convertAndSend(exchange, routeKey, message);
    }

    @SneakyThrows(MessagingException.class)
    private MimeMessageHelper prepareMessage(String to, String subject, String content, MimeMessage message) {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        return helper;
    }
}
