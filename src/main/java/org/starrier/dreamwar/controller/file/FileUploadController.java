package org.starrier.dreamwar.controller.file;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.starrier.dreamwar.service.impl.FilePathService;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
@RestController
@RequestMapping("api/v1/file")
public class FileUploadController {

    private final FilePathService filePathService;

    public FileUploadController(FilePathService filePathService) {
        this.filePathService = filePathService;
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) {
        return filePathService.upload(file);
    }

}
