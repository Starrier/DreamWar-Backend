package org.starrier.dreamwar.utils.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.starrier.dreamwar.utils.common.enums.ResultCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Api Response Code Wrapper</p>
 * Custom return type classes.
 *
 * @see Result is the enhanced and custom version of  {@link org.springframework.http.ResponseEntity}
 *
 * @author  Starrier
 * @date  2018/11/11.
 */
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -1709587390161841001L;

    private Integer code ;

    private String message;

    private String url;

    private Object data;

    public Result() { }

    ThreadLocal<Result> response;

    public Result(Integer errorCode, String errMessage) {
        this.code = errorCode;
        this.message = errMessage;
    }
    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    public Map<String, Object> simple() {
        Map<String, Object> simple = new HashMap<>();
        this.data = simple;
        return simple;
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * Success.
     * @param data Object
     * @return {@link Result}
     * */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result error(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result error(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

}
