package org.starrier.dreamwar.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.starrier.dreamwar.util.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
@Service
public class FilePathService {

    /**
     * filePathRepository {@link FilePathRepository}.
     * */
    @Autowired
    private FilePathRepository filePathRepository;

    /**
     * <p>Upload Images</p>
     * 1.Determine the file whether exist or not.
     * 2.
     * @param file MultipartFile  The request parameter must be of  type {@link MultipartFile}.
     *                        And {@link RequestParam}request the param file.
     * @return return the result.
     * */
    @Retryable(value = FileNotFoundException.class, maxAttempts = 4, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public String upload(final @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            // 获取文件名称,包含后缀
            String fileName = file.getOriginalFilename();

            // 存放在这个路径下：该路径是该工程目录下的static文件下：(注：该文件可能需要自己创建)
            // 放在static下的原因是，存放的是静态文件资源，即通过浏览器输入本地服务器地址，加文件名时是可以访问到的
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/images";

            try {
                // 该方法是对文件写入的封装，在util类中，导入该包即可使用，后面会给出方法
                FileUtil.fileupload(file.getBytes(), path, fileName);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 接着创建对应的实体类，将以下路径进行添加，然后通过数据库操作方法写入
            Files saveUrl = new Files();
            saveUrl.setPath(fileName);
            filePathRepository.save(saveUrl);

        }
        return "success";
    }

    @Recover
    public ResponseEntity uploadFile(FileNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
