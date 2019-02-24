package org.starrier.dreamwar.common;

import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * <p>Singleton Pattern</p>
 *
 * <p>Some methods may cause the singleton pattern to be corrupted.</p>
 *  <u>Serializable</u>
 *  <u>clone()</u>
 *  <u>reflect method/u>
 *
 * @author Starrier
 * @date 2019/2/7.
 */
public class LoggerManger implements Serializable,Cloneable {


    private static final long serialVersionUID = -2733768728107499200L;

    private static final String DEFAULTLOGFILEPATHNAME = "";

    private Properties properties;

    private volatile static LoggerManger loggerManger;

    private static InputStream inputStream;

    private static PrintWriter printWriter;

    private static String LOGFILENAME;

    private LoggerManger() {
        init();
    }

    public static void log(String message) {
        if (loggerManger == null || printWriter == null) {
            synchronized (LoggerManger.class) {
                if (loggerManger == null) {
                    loggerManger = new LoggerManger();
                    printWriter.println(new Date() + ":" + message);
                }
            }
        }
    }

    public void init() {
        if (LOGFILENAME == null) {
            LOGFILENAME = getLogFileName();
            try {
                if (printWriter == null) {
                    printWriter = new PrintWriter(new FileWriter(LOGFILENAME, true), true);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private String getLogFileName() {
        try {
            if (properties == null) {
                properties = new Properties();
                printWriter = getClass().getResourceAsStream("log.properties");
                properties.load(printWriter);
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Clone method access changed with public
     *
     * @throws {@link CloneNotSupportedException}
     * @retun the exception
     * */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Resolve the problem of serializing broken singleton pattern.
     * @return  the unique instance.
     * */
    protected Object readResolve() {
        return loggerManger;
    }
}
