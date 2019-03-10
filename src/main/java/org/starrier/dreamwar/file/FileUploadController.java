package org.starrier.dreamwar.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.starrier.dreamwar.file.FilePathService;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
@RestController
public class FileUploadController {

    @Autowired
    private FilePathService filePathService;
    // 传入的参数file是我们指定的文件
    @RequestMapping("/uploadUrl")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        return filePathService.upload(file);
    }

}
