package org.starrier.dreamwar.file;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.starrier.dreamwar.utils.DateUtils;
import org.starrier.dreamwar.utils.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Starrier
 * @date 2018/12/9.
 */
@Api(value = "File", tags = {"upload File", "upload Images"})
@RestController
@RequestMapping("/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * @param uploadFilePath
     * */
    private String uploadFilePath="F:/test";

    @Autowired
    FilePathRepository filePathRepository;

    @ApiOperation(value = "Images", notes = "Upload Images")
    @ApiImplicitParam(paramType = "query", name = "images", value = "upload Images", required = true, dataType = "MultipartFile")
    @PostMapping(value = "/img")
    public List<String> uploadImages(final @RequestParam("file") MultipartFile[] files) {

        /**
         * @param projectName
         */
        String projectName = "file";
        String savePath = String.format("/%s/img/%s/%s", projectName, RandomUtils.randomNum(2), RandomUtils.randomNum(2));
        List<String> paths= Lists.newArrayList();
        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();
            String suffix = "";
            if (suffix.lastIndexOf(".") != -1) {
                suffix = name.substring(name.lastIndexOf("."));
            }
            if (StringUtils.isEmpty(suffix)) {
                suffix = ".jpg";
            }
            name = String.format("%s-%s%s", DateUtils.getCurrentTime("yyyyMMdd"), getRandomUniqName(), suffix);
            boolean success = saveFile(savePath, name, file);
            if (success) {
                paths.add(String.format("%s/%s", savePath, name));
            } else {
                paths.add("");
            }
        }
        return paths;
    }


    /**
     *<p>Upload File</p>
     *  1. Determines whether current param files is empty or not.
     *  2. Gets the file name of the current uploaded file
     *  3. Define the path to save for the upload file
     *  4. Determines whether the save path exists or not
     *  5. And  try to save the file with path if it is exist,however,create the path and retry.
     *
     * @param files This file must be multi type
     * @return ResponseEntity {@link ResponseEntity ;}
     */
    @ApiOperation(value = "上传文件", notes = "上传图片")
    @ApiImplicitParam(paramType = "query", name = "files", value = "图片上传", required = true, dataType = "MultipartFile")
    @PostMapping(value = "/file")
    public ResponseEntity<Object> file(final @RequestParam("file") MultipartFile files) {
        if (files.isEmpty()) {
            return new ResponseEntity<>("上传文件为空", HttpStatus.OK);
        }
        String fileName = files.getOriginalFilename();

        if (LOGGER.isInfoEnabled()) {
            int size = (int) files.getSize();
            LOGGER.info(fileName + "-->" + size);
        }


        String path = "F:/test";
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            files.transferTo(dest);
            return new ResponseEntity<>("Upload Image has been saved successfully!", HttpStatus.OK);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Upload Image has failed!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 获取随机值:10w以内基本可保证唯一，但非绝对不唯一
     *
     * @return
     */
    private static String getRandomUniqName(){
        String prefix = RandomUtils.randomNum(1);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return String.format("%s%015d",prefix, hashCodeV);
    }

    /**
     * @param file     MultipartFile.
     * @param name     String
     * @param savePath String.
     */
    private boolean saveFile(final String savePath, final String name, final MultipartFile file) {

        String path = String.format("%s%s", uploadFilePath, savePath);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            file.transferTo(new File(String.format("%s/%s", path, name)));
        } catch (IOException e) {
            LOGGER.error("save file due to error", e);
            return false;
        }
        return true;
    }

    /**
     * 实现多文件上传.
     * @param request List type
     * @return String type with the result.
     */
    @PostMapping(value = "/multifileUpload")
    @ResponseBody
    public String multiFileUpload(final @RequestParam("fileName") List<MultipartFile> request) {

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");

        if (files.isEmpty()) {
            return "false";
        }

        String path = "F:/test";

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if (file.isEmpty()) {
                return "false";
            } else {
                File dest = new File(path + "/" + fileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }

}



















