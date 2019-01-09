package org.starrier.dreamwar.controller;

import com.alibaba.fastjson.JSONObject;
//import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.service.MailService;

import javax.annotation.Resource;

/**
 * @Author Starrier
 * @Time 2018/10/16.
 */
@RestController
@RequestMapping(value = "v1/emails")
public class MailController {

    @Resource
    private MailService mailService;

    /**
     * ｛
     *      "to":"3241807181@qq.com"
     *      "subject":"xxxx"
     *      "text":"xxxxx"
     *  }
     */

    @ResponseBody
    @PostMapping(produces = "application/json",consumes = "application/json")
    public JSONObject add(@RequestBody JSONObject jsonObject)throws Exception {
        String article = jsonObject.toString();

        mailService.sendEmail(article);
        return  jsonObject;

    }




}
