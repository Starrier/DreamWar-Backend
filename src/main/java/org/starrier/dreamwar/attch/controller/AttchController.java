package org.starrier.dreamwar.attch.controller;

import org.elasticsearch.rest.RestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.starrier.dreamwar.attch.service.AttachService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

/**
 * @author Starrier
 * @date 2019/3/21.
 * <p>
 * Description :
 */
@RequestMapping
@RestController
public class AttchController {

    @Resource
    private AttachService attachServcice;

    @PostMapping(value = "/upload")
    @ResponseBody
    public RestResponse upload(HttpServletRequest request, @RequestParam("file")MultipartFile[] multipartFiles) {

        attachServcice.upload(request, multipartFiles);

        return RestResponse;
    }
}
