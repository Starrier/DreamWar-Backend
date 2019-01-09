package org.starrier.dreamwar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.starrier.dreamwar.service.MailService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



/**
 * 
 * @author liangming.deng
 * @date   2017年7月29日
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("3241807181@qq.com","test simple mail"," hello this is simple mail");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("3241807181@qq.com","test simple mail",content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath="d:\\tmp\\iot-springboot.log";
        mailService.sendAttachmentMail("email_test_123@163.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }



    @Test
    public void sendInlineResourceMail() {
        String rscId = "test006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "D:\\natie.jpg";

        mailService.sendInlineResourceMail("3241807181@qq.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }


    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail("3241807181@qq.com","主题：这是模板邮件",emailContent);
    }
}
