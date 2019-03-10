package org.starrier.dreamwar.mail;

/**
 * @Author Starrier
 * @Time 2018/7/6.
 */
public interface MailService {

    /**
     * 发送普通邮件
     * @param to
     * @param subject
     * @param content
     */
     void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送 HTML 邮件
     * @param to
     * @param subject
     * @param content
     */
     void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送 附件 邮件
     * @param to
     * @param subject
     * @param content
     */
    void sendAttachmentMail(String to, String subject, String content, String filePath);

    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param to
     * @param subject
     * @param content
     */
    void sendInlineResourceMail(String to, String subject, String content, String resourcePath, String resourceId);

    /**
     *   dreamwar email template
     * @param
     * @param
     *
     * */
    void sendTemplateMail(String to, String subject);

    void sendEmail(String message)throws Exception;

}
