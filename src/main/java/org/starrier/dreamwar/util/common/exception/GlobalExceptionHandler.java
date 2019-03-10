package org.starrier.dreamwar.util.common.exception;

import javassist.NotFoundException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.util.common.ParameterInvalidItem;
import org.starrier.dreamwar.util.common.Result;
import org.starrier.dreamwar.util.common.enums.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Starrier
 * @date 2018/12/11.
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * <p>MethodArgumentNotValidException</p>
     *
     * @param request {@link HttpServletRequest}
     * @param e       {@link MethodArgumentNotValidException} and {@link HttpMessageNotReadableException}
     * @return return new responseEntity {@link ResponseEntity}
     **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public Result MethodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Param Type Error:[{}]", request.getParameterMap());
            LOGGER.error("Caused By:", e);
        }

        List<ParameterInvalidItem> parameterInvalidItems = new ArrayList<>();
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            ParameterInvalidItem parameterInvalidItem = new ParameterInvalidItem();
            parameterInvalidItem.setFieldName(fieldError.getField());
            parameterInvalidItem.setMessage(fieldError.getDefaultMessage());
            parameterInvalidItems.add(parameterInvalidItem);
        }
        return Result.error(ResultCode.PARAM_IS_INVALID, parameterInvalidItems);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Result> AuthorizationExceptionHandler(HttpServletRequest request, AuthorizationException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Authorization Error,url:[{}]", request.getRequestURL());
            LOGGER.error("Exception is ", e);
        }
        HttpStatus status = HttpStatus.OK;

        Result result= new Result();
        result.setResultCode(ResultCode.USER_NOT_LOGGED_IN);

        return new ResponseEntity<>(result,status);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> notFoundException(HttpServletRequest request,NotFoundException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Not found:[{}]", e.getMessage());
            loggerInformation(request, e);
        }

        HttpStatus status = HttpStatus.NOT_FOUND;

        Result result = new Result();
        result.setResultCode(ResultCode.PARAM_NOT_COMPLETE);

        return new ResponseEntity<>(result, status);
    }

    @ExceptionHandler(ListenerExecutionFailedException.class)
    public ResponseEntity<?> listenerExecutionFailedException(ListenerExecutionFailedException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ListenerExecutionFailedException :[{}]", e.getMessage());
        }
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler(UserRegisterException.class)
    public Map<String, Object> userRegisterExceptionHandler(UserRegisterException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        result.put("code", e.getCode());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Exception happened:[{}]", result);
        }
        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> ExceptionHandler(HttpServletRequest request, Exception e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Server Internal Error");
            LOGGER.error("URL:[{}]", request.getRequestURL());
            LOGGER.error("Caused By :", e.getMessage());
        }
        HttpStatus status = getStatus(request);

        Result result = new Result();
        result.setResultCode(ResultCode.SYSTEM_INNER_ERROR);
        result.simple().put("Error Details:", e.getMessage());
        return new ResponseEntity<>(result, status);
    }


     /**
     * @param request {@link HttpServletRequest}
     * @param e {@link Exception}
     * @return return result
     **/
     public String jsonHandler(HttpServletRequest request, Exception e) {
        LOGGER.error("Request URL:[{}]", request.getRequestURL().toString());
        loggerInformation(request, e);
        return null;
     }

     /**
     * <p>loggerInformation</p>
     *
     * @param request {@link Exception}
     * @param e {@link HttpServletRequest}
     **/
     private void loggerInformation(HttpServletRequest request, Exception e){
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Exception has happened....");
            LOGGER.error("Exception detail message:[{}]", e);
            LOGGER.error("Request URL:[{}]", request.getRequestURL());
            LOGGER.error("Request Parameters");
            Enumeration enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement().toString();
                LOGGER.error("parameter:[{}]", request.getParameter(name));
            }
            StackTraceElement[] error = e.getStackTrace();
            for (StackTraceElement stackTraceElement : error) {
                LOGGER.error(stackTraceElement.toString());
            }
            LOGGER.error("Exception detail information has ended!");
        }
     }

    /**
     * <p>HttpRequestMethodNotSupportException</p>
     *
     * @param request get request type
     * @param e exception detail message
     * @return Return new {@link ResponseEntity} to invoker with json format
     *
     */
     @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
     @ResponseBody
     @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
     public ResponseEntity<?> httpRequestMethodNotSupport(HttpServletRequest request, Exception e) {
         if (LOGGER.isErrorEnabled()) {
             LOGGER.error("HttpRequestMethodNotSupport:[{}]", request.getMethod());
         }

        return new ResponseEntity<>("HttpRequestMethodNotSupport!", HttpStatus.METHOD_NOT_ALLOWED);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return (statusCode == null) ?
                HttpStatus.INTERNAL_SERVER_ERROR :
                HttpStatus.valueOf(statusCode);
    }
}

