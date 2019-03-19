package org.starrier.dreamwar.utils.common;

import lombok.Data;
import org.apache.http.HttpStatus;

import java.util.Date;

/**
 * @Author Starrier
 * @Time 2018/6/16.
 */
@Data
public class ErrorResponse {

    /**
    *  Http Response Status Code
    **/
    private final HttpStatus status;

    /**
     *  General Error message
     **/
    private final Object message;

    /**
    *   Error  Code
    **/
    private final ErrorCode errorCode;

    /**
     * timestamp,the time of response.
     * */
    private final Date timestamp;


    private ErrorResponse(final Object message, final ErrorCode errorCode, HttpStatus status) {
        this.message=message;
        this.errorCode=errorCode;
        this.status=status;
        this.timestamp = new java.util.Date();
    }

    public static ErrorResponse of(final Object message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }


}
