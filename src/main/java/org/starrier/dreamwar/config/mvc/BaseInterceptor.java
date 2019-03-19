package org.starrier.dreamwar.config.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.starrier.dreamwar.utils.Constants.USER_AGENT;


/**
 * @author Starrier
 * @date 2019/3/18.
 * <p>
 * Description :
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {

        String uri = request.getRequestURI();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("UserAgent : [{}]",request.getHeader(USER_AGENT));
        }
    }

}
