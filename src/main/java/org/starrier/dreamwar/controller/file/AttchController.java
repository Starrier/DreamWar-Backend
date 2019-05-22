package org.starrier.dreamwar.controller.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.starrier.dreamwar.service.interfaces.AttachService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Starrier
 * @date 2019/3/21.
 * <p>
 * Description :
 */
@RequestMapping
@RestController
public class AttchController {

    private final AttachService attachServcice;

    public AttchController(AttachService attachServcice) {
        this.attachServcice = attachServcice;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) {

        attachServcice.upload(request, multipartFiles);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
