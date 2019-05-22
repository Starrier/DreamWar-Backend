package org.starrier.dreamwar.controller.mail;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.starrier.dreamwar.service.interfaces.MailService;

/**
 * @author  Starrier
 * @date  2018/10/16.
 */
@RestController
@RequestMapping(value = "v1/emails")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ResponseBody
    @SneakyThrows(Exception.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public JSONObject add(@RequestBody JSONObject jsonObject) {
        String article = jsonObject.toString();
        mailService.sendEmail(article);
        return jsonObject;
    }
}