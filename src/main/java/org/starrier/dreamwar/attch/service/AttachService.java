package org.starrier.dreamwar.attch.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Starrier
 * @date 2019/3/21.
 * <p>
 * Description :
 */
public interface AttachService {
    /**
     * upload.
     *
     * @param request
     * @param multipartFiles
     */
    void upload(HttpServletRequest request, MultipartFile[] multipartFiles);
}
