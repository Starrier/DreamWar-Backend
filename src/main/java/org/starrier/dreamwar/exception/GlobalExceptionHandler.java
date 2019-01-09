/*
package org.starrier.dreamwar.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author Starrier
 * @date 2018/12/11.
 **//*

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handlerException(Exception e) {

        String msg  =e.getMessage();
        if (msg == null || msg.equals("")) {
            msg="服务器出错";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 100);
        map.put("msg", e.getMessage());

        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Exception:[{}]", map);
        }
        return map;

    }


     */
/**
     * <p>Controller<p/>
     *
     **//*

     @ExceptionHandler(RuntimeException.class)
     @ResponseBody
     public ResponseEntity<?> exceptionHandler(RuntimeException e, HttpServletResponse response) {

        return null;

     }
     */
/**
     * @param request {@link HttpServletRequest}
     * @param e {@link Exception}
     * @return return result
     **//*


     public String jsonHandler(HttpServletRequest request, Exception e) {
        LOGGER.error("Request URL:[{}]", request.getRequestURL().toString());
        loggerInformation(request, e);
        return null;
     }

     */
/**
     * <p>loggerInformation</p>
     *
     * @param request {@link Exception}
     * @param e {@link HttpServletRequest}
     **//*

     private void loggerInformation(HttpServletRequest request, Exception e){
        if (LOGGER.isDebugEnabled()) {
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

     */
/**
     * <p>MethodArgumentNotValidException</p>
     *
     * @param request {@link HttpServletRequest}
     * @param e {@link MethodArgumentNotValidException} and {@link HttpMessageNotReadableException}
     * @return return new responseEntity {@link ResponseEntity}
     **//*

     */
/*@ResponseBody
     @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
     public ResponseEntity<?> typeMismatch(HttpServletRequest request,Exception e){
        LOGGER.error("Param Type Error:[{}]", request.getParameterMap());
        return new ResponseEntity<>("Param Valid Error!", HttpStatus.FAILED_DEPENDENCY);
     }*//*


     */
/**
     * <p>HttpRequestMethodNotSupportException</p>
     *
     * @param request get request type
     * @param e exception detail message
     * @return Return new {@link ResponseEntity} to invoker with json format
     *
     **//*

     @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
     @ResponseBody
     @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
     public ResponseEntity<?> httpRequestMethodNotSupport(HttpServletRequest request, Exception e) {
        LOGGER.error("HttpRequestMethodNotSupport:[{}]", request.getMethod());
        return new ResponseEntity<>("HttpRequestMethodNotSupport!", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
*/
