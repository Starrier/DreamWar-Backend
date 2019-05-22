package org.starrier.dreamwar.utils.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Starrier
 * @date 2018/11/6.
 */
@Slf4j
@Aspect
@Component
public class HttpAspect {

    /**
     * ThreadLocal variable {@code START_TIME} needs to call the
     * {@link ThreadLocal#remove()} method,
     * when referencing.Otherwise,it will result in Out Of Memory.
     */
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    @Pointcut(value = "execution(public * org.starrier.dreamwar.controller..*(..))")
    public void log() {
    }


    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("Method Start : [{}]", System.currentTimeMillis());
        START_TIME.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Map<String, Object> params = new HashMap<>(6);
        params.put("url --------------", request.getRequestURL());
        params.put("method -----------", request.getMethod());
        params.put("ip ---------------", request.getRemoteAddr());
        params.put("className --------", joinPoint.getSignature().getDeclaringTypeName());
        params.put("classMethod ------", joinPoint.getSignature().getName());
        params.put("args -------------", joinPoint.getArgs());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.info("REQUEST:{}", gson.toJson(params));

    }

    @Around("log()")
    public Object doAround(final ProceedingJoinPoint point) {
        try {
            Object object = point.proceed();
            log.info("doAround");
            return object;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.info("doAround.......");
            return null;
        }
    }

    @After("log()")
    public void doAfter() {
        if (log.isInfoEnabled()) {
            log.info("doAfter ......");
            log.info("SPEND TIME : " + (System.currentTimeMillis() - START_TIME.get()));
        }
        START_TIME.remove();
    }

    /**
     * @param object
     * @throws Throwable running time exception.Maybe it is NPE.
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        // 处理完请求，返回内容
        if (log.isInfoEnabled()) {
            log.info("RESPONSE : " + object);
            log.info("SPEND TIME : " + (System.currentTimeMillis() - START_TIME.get()));
        }
        START_TIME.remove();
    }

    @AfterThrowing(pointcut = "log()")
    public void doAfterThrowing() {
        if (log.isErrorEnabled()) {
            log.error("doAfterThrowing: {}", " 异常情况!");
        }
    }
}
