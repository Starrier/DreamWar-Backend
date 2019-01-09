package org.starrier.dreamwar.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Custom return type classes.
 * @see ResponseCode is the enhanced and custom version of  {@link org.springframework.http.ResponseEntity}
 *
 * @author  Starrier
 * @Time 2018/11/11.
 */
@AllArgsConstructor
@Builder
@Data
public class ResponseCode<T> implements Serializable {

    private static final long serialVersionUID = -1709587390161841001L;

    private HttpStatus code ;

    private String message;

    private String url;

    private T result;

   ThreadLocal<ResponseCode<T>> response;



    public ResponseCode(HttpStatus errorCode, String errMessage) {
        this.code = errorCode;
        this.message = errMessage;
    }

    public ResponseCode() {

    }


    /**
     * Success.
     * @param result T
     * @return {@link ResponseCode}
     * */
    public static <T> ResponseCode<T> success(T result) {
        ResponseCode<T> response = new ResponseCode<T>(HttpStatus.OK, null);
        response.result = result;
        return response;
    }

    public static <T> ResponseCode<T> success(final String msg) {
        return new ResponseCode<T>(HttpStatus.OK, msg);
    }

    public static <T> ResponseCode<T> success() {
        return new ResponseCode<T>(HttpStatus.OK, null);
    }

    public static <T> ResponseCode<T> error(final HttpStatus code, final String msg) {
        ResponseCode<T> response = new ResponseCode<>();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

}
