package org.starrier.dreamwar.util.aspect;

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
import java.util.Objects;

/**
 * @author Starrier
 * @date  2018/11/6.
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * ThreadLocal variable {@code START_TIME} needs to call the
     * {@link ThreadLocal#remove()} method,
     * when referencing.Otherwise,it will result in Out Of Memory.
     * */
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    @Pointcut(value = "execution(public * org.starrier.dreamwar.controller..*(..))")
    public void log() {
    }


    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint) {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Method Start : [{}]", System.currentTimeMillis());
        }
        START_TIME.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        Map<String, Object> params = new HashMap<>(6);

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
    public Object doAround(final ProceedingJoinPoint point) {
        try {
            Object object = point.proceed();
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
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("doAfter ......");
            LOGGER.info("SPEND TIME : " + (System.currentTimeMillis() - START_TIME.get()));
        }
        START_TIME.remove();
    }


    /**
     * @param object
     * @throws Throwable running time exception.Maybe it is NPE.
     * */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object){
        // 处理完请求，返回内容
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("RESPONSE : " + object);
            LOGGER.info("SPEND TIME : " + (System.currentTimeMillis() - START_TIME.get()));
        }
        START_TIME.remove();
    }


    @AfterThrowing(pointcut = "log()")
    public void doAfterThrowing() {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("doAfterThrowing: {}", " 异常情况!");
        }
    }




















}
