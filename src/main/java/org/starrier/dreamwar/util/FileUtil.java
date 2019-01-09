package org.starrier.dreamwar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);


    public static void fileupload(byte[] file, String filePath, String fileName) throws IOException {
        //目标目录
        File targetFile = new File(filePath);

        LOGGER.info("Before  File Path: [[]]", filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
            LOGGER.info("After  File Path: [[]]", filePath);
        }

        //二进制流写入
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
