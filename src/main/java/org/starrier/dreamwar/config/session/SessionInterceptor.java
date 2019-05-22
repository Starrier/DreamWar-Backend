package org.starrier.dreamwar.config.session;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  Starrier
 * @date  2018/6/8.
 */
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (httpServletRequest.getRequestURI().equals("/user/login") || httpServletRequest.getRequestURI().equals("user/login_view")) {
            return true;
        }

        Object object = httpServletRequest.getSession().getAttribute("session_user");
        if (object == null) {
            httpServletResponse.sendRedirect("/user/login_view");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}