package org.starrier.dreamwar.utils.aspect;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.starrier.dreamwar.utils.annotation.RateLimit;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Starrier
 * @date 2019/1/4.
 */
@Aspect
@Component
public class RateLimiterAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterAop.class);

    private Map<String, RateLimiter> rateHashMap = new ConcurrentHashMap<>();

    /**
     * Defining pointcut intercept request.
     * */
    @Pointcut("execution(public * org.starrier.dreamwar.controller.article.ArticleController.*(..))")
    public void rateLimiterAop(){}

    /**
     * 1. Determine whether there is a specified annotation {@link RateLimit} in the method,
     *    if there is ,continue,otherwise return null.
     *
     * 2. Using the Java {@link sun.reflect.Reflection} mechanism to obtain the custom annotation parameters
     *    on the interception method.
     * 3. Call the native {@link RateLimiter} creation token to ensure that each request corresponds
     *    to a singleton RateLimiter.
     * */
    @Around("rateLimiterAop()")
    public Object doBefore(ProceedingJoinPoint point) throws Throwable {
        Method signatureMethod = getSignatureMethod(point);
        if (signatureMethod == null) {
            return null;
        }
        RateLimit rateLimit = signatureMethod.getDeclaredAnnotation(RateLimit.class);
        if (rateLimit == null) {
            return point.proceed();
        }
        double limitNum = rateLimit.limitNum();
        long timeout = rateLimit.timeout();

        String requestURI =getRequestURI();
        RateLimiter rateLimiter;

        if (rateHashMap.containsKey(requestURI)) {
            rateLimiter = rateHashMap.get(requestURI);
        } else {
            rateLimiter = RateLimiter.create(limitNum);
            rateHashMap.put(requestURI, rateLimiter);
        }
        boolean tryAcquire = rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            fallback();
            return null;
        }

        return point.proceed();
    }

    /**
     * @throws IOException Thrown the exception when it  occurs.
     * */
    private void fallback() throws IOException {
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Context-type","application/json");

    }

    private String getRequestURI() {
        return getRequest().getRequestURI();
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * Get the method to intercept AOP.
     *
     * @param joinPoint Pointcut of the event handing.
     * @return the method obtained with AOP interception.
     * */
    private Method getSignatureMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        Method method =signature.getMethod();
        return method;
    }
}

