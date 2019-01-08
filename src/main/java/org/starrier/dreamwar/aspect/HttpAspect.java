package org.starrier.dreamwar.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Starrier
 * @Time 2018/11/6.
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * org.starrier.dreamwar.controller..*(..))")
    public void log() {
    }


    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();

        Map params = new HashMap();

        params.put("url", request.getRequestURL());
        params.put("method", request.getMethod());
        params.put("ip", request.getRemoteAddr());
        params.put("className", joinPoint.getSignature().getDeclaringTypeName());
        params.put("classMethod", joinPoint.getSignature().getName());
        params.put("args", joinPoint.getArgs());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        LOGGER.info("REQUEST:{}", gson.toJson(params));

    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint point) {
        try {
            Object object=point.proceed();
            LOGGER.info("doAround");
            return object;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOGGER.info("doAround.......");
            return null;
        }
    }

    @After("log()")
    public void doAfter() {
        LOGGER.info("doAfter ......");
    }


    /**
     * Fetch return value
     *
     * @param object
     * @return
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {

        LOGGER.info("RESPONSE : {}", object.toString());
    }


    @AfterThrowing(pointcut = "log()")
    public void doAfterThrowing() {
        LOGGER.error("doAfterThrowing: {}", " 异常情况!");
    }




















}
