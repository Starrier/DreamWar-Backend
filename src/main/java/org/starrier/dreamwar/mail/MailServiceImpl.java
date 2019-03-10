package org.starrier.dreamwar.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.starrier.dreamwar.user.entity.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author Starrier
 * @Time 2018/7/6.
 */
@Component
public class MailServiceImpl implements MailService {

    private final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    private AmqpTemplate rabbitmqTemplate;


    /**
     * @Param from    邮件发送方
     * @Param to      邮件发送对象
     * @Param subject 邮件主题
     * */
    @Value(value = "1342878298@qq.com")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value(value = "register.topic.exchange")
    private String exchange;

    @Value(value = "register.user")
    private String routeKey;

    private static final String TITLE_SIGN_UP="Register User Active";

    private static final String CONTENT = "";

    public void userValidate(User user, String token) {

      /*  try {
            String link = "http://localhost:8080/validate/" + token;
            String message = String.format(CONTENT + user.getUsername(), link, link);

        }*/
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            LOGGER.info("Mail send to：{}，from from:{},and subject:{}", to, from, subject);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Main send fail:{}",e);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {

        LOGGER.info("发送 HTML Email,Start......");
        MimeMessage message =mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            LOGGER.info("html邮件发送成功:{}",helper);
        } catch (MessagingException e) {
            LOGGER.error("发送html邮件时发生异常！:{}", e);
        }
    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content,String filePath) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            LOGGER.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            LOGGER.error("发送带附件的邮件时发生异常！", e);

        }
    }

    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String resourcePath, String resourceId) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(resourcePath));
            helper.addInline(resourceId, res);

            mailSender.send(message);
            LOGGER.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            LOGGER.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

    @Override
    public void sendTemplateMail(String to,String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("id", "wenter");
            String emailContent = templateEngine.process("emailTemplate", context);
            helper.setText(emailContent, true);
        } catch (MessagingException e) {
            throw new RuntimeException("Messaging  Exception !", e);
        }
        mailSender.send(message);
    }
    @Override
    public void sendEmail(String message) throws Exception {

        try {
            rabbitmqTemplate.convertAndSend(exchange, routeKey, message);
        } catch (Exception e) {
            LOGGER.error("Error ", Exception.class);
        }
    }


}
