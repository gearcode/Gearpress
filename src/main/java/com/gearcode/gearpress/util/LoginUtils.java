package com.gearcode.gearpress.util;

import com.gearcode.gearpress.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by jason on 16/12/6.
 */
public class LoginUtils {
    public static User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute(Constants.LOGIN_USER_SESSION_KEY);

        if(null != user && user instanceof User) {
            return (User) user;
        } else {
            return null;
        }
    }
}
