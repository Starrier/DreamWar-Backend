package org.starrier.dreamwar.utils.common;



/**
 *
 * Enumeration of REST Error types;
 *
 * @Author Starrier
 * @Time 2018/6/16.
 */
public enum ErrorCode {

    /**
     *
     * */
    GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);

    private int errorCode;
    public static final int SERVER_INTERNAL_ERROR = 1000;
    public static final int PARAMETER_MISSING_ERROR = 1001;
    public static final int PARAMETER_ILLEGAL_ERROR = 1002;
    public static final int RESOURCE_NOT_FOUND_ERROR = 1003;

    /**
     * Prevent instantiation
     */
    private ErrorCode() {
    }


    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }
}
