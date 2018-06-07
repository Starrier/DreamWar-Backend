package org.starrier.dreamwar.util;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void helpMethod(){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
    }
}

