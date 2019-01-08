package org.starrier.dreamwar.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author  Starrier
 * @date  2018/6/5.
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private static final Logger LOGGER = Logger.getLogger(WebLogAspect.class);

    /**
     * ThreadLocal variable {@code START_TIME} needs to call the
     * {@link ThreadLocal#remove()} method,
     * when referencing.Otherwise,it will result in Out Of Memory.
     * */
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    @Pointcut(value = "execution(public * org.starrier.dreamwar.controller.*.*(..))")
    public void webLog(){}

    @Before(value = "webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        START_TIME.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 记录下请求内容
        LOGGER.info("URL : " + request.getRequestURL().toString());
        LOGGER.info("HTTP_METHOD : " + request.getMethod());
        LOGGER.info("IP : " + request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    /**
     * @param ret
     * @throws Throwable running time exception.Maybe it is NPE.
     * */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE : " + ret);
        LOGGER.info("SPEND TIME : " + (System.currentTimeMillis() - START_TIME.get()));

        START_TIME.remove();
    }


}

