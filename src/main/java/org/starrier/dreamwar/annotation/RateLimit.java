package org.starrier.dreamwar.annotation;

import java.lang.annotation.*;

/**
 * Customer annotation rateLimiter.
 * More detail in {@link com.google.common.util.concurrent.RateLimiter}
 *
 * @author Starrier
 * @date 2019/1/3.
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * Add a token to the token bucket at a fixed rate per second.
     * */
    double limitNum() default 20;

    /**
     * If the token is not obtained in the specified number,
     * the service demotion process is taken directly.
     * */
    long timeout();
}
