package org.starrier.dreamwar.attch.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.starrier.dreamwar.attch.service.AttachService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Starrier
 * @date 2019/3/21.
 * <p>
 * Description :
 */
@Service
public class AttachServiceImpl implements AttachService {
    /**
     * upload.
     *
     * @param request
     * @param multipartFiles
     */
    @Override
    public void upload(HttpServletRequest request, MultipartFile[] multipartFiles) {

        List<String> errorFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            if (multipartFile.getSize() <= WebCOnst > MAX_FIZE_SIZE) {
                String fileKey = TaleUtils.getFileKey(fileName);
                String fileType ;
                File file = new File();
                File
            }
        }
    }
}
