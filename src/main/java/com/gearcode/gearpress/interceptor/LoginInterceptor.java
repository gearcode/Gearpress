package com.gearcode.gearpress.interceptor;

import com.gearcode.gearpress.annotation.LoginRequired;
import com.gearcode.gearpress.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            LoginRequired loginRequired = ((HandlerMethod) handler).getMethodAnnotation(LoginRequired.class);

            //无需登录
            if(loginRequired == null) {
                return true;
            } else {
                //需要登录, 验证session
                HttpSession session = request.getSession();
                Object user = session.getAttribute(Constants.LOGIN_USER_SESSION_KEY);
                logger.info("user:{}", user);
                if(user == null) {
                    //未登录, 重定向回首页
                    response.sendRedirect("/");
                    return false;
                } else {
                    //已登录
                    return true;
                }
            }
        }

		return true;
	}
}