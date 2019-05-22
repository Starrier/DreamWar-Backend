package org.starrier.dreamwar.utils.aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.starrier.dreamwar.utils.annotation.DuplicateSubmitToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Starrier
 * @date 2018/12/9.
 */
@Slf4j
@Aspect
@Component
public class DuplicateSubmitAspect {

    public static final String DUPLICATE_TOKEN_KEY = "duplicate_token_key";

    @Pointcut("execution(public * org.starrier.dreamwar.controller..*(..))")
    public void webLog() { }

    @SneakyThrows(Exception.class)
    @Before("webLog() && @annotation(token)")
    public void before(final JoinPoint joinPoint, DuplicateSubmitToken token)  {
        if (token!=null){
            Object[]args=joinPoint.getArgs();
            HttpServletRequest request=null;
            HttpServletResponse response=null;
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    request = (HttpServletRequest) arg;
                }
                if (arg instanceof HttpServletResponse) {
                    response = (HttpServletResponse) arg;
                }
            }

            boolean isSaveSession=token.save();
            if (isSaveSession){
                Object t = request.getSession().getAttribute(DUPLICATE_TOKEN_KEY);
                if (null==t){
                    String uuid= UUID.randomUUID().toString();
                    request.getSession().setAttribute(DUPLICATE_TOKEN_KEY,uuid);
                    log.info("进入方法：token="+uuid);
                }else {
                    throw new Exception("请不要重复请求！");
                }
            }

        }
    }

    @AfterReturning("webLog() && @annotation(token)")
    public void doAfterReturning(JoinPoint joinPoint, DuplicateSubmitToken token) {
        // 处理完请求，返回内容
        log.info("出来方法：");
        if (token!=null){
            Object[]args=joinPoint.getArgs();
            HttpServletRequest request=null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest){
                    request= (HttpServletRequest) args[i];
                }
            }
            boolean isSaveSession=token.save();
            if (isSaveSession){
                Object t = request.getSession().getAttribute(DUPLICATE_TOKEN_KEY);
                if (null!=t){
                    //方法执行完毕移除请求重复标记
                    request.getSession(false).removeAttribute(DUPLICATE_TOKEN_KEY);
                    log.info("方法执行完毕移除请求重复标记！");
                }
            }
        }
    }
}

