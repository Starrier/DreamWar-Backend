package org.starrier.dreamwar.config.mvc;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.starrier.dreamwar.config.mvc.BaseInterceptor;

import javax.annotation.Resource;

/**
 * @author Starrier
 * @date 2019/3/18.
 * <p>
 * Description :
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BaseInterceptor baseInterceptor;
}
