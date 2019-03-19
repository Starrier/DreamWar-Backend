package org.starrier.dreamwar.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;
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

    @SneakyThrows(IOException.class)
    public static void fileupload(byte[] file, String filePath, String fileName) {
        //目标目录
        File targetFile = new File(filePath);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Before  File Path: [[]]", filePath);
        }
        if (!targetFile.exists()) {
            targetFile.mkdirs();
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("After  File Path: [[]]", filePath);
            }
        }

        //二进制流写入
        @Cleanup
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
    }

}
