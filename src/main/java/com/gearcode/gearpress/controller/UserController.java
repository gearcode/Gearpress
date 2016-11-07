package com.gearcode.gearpress.controller;

import com.alibaba.fastjson.JSON;
import com.gearcode.gearpress.dao.UserMapper;
import com.gearcode.gearpress.domain.User;
import com.gearcode.gearpress.domain.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by liteng3 on 2016/11/7.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static final String LoginUserSessionKey = "loginUser";

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String username = ServletRequestUtils.getStringParameter(request, "username", "");
        String password = ServletRequestUtils.getStringParameter(request, "password", "");
        String password_encode = ServletRequestUtils.getStringParameter(request, "password_encode", "");
        logger.info("login request, username:{}, password:{}, password_encode:{}", username, password, password_encode);

//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] digest = md5.digest(password_encode.getBytes("UTF-8"));
//            String result = new String(digest);

            /*
            查询是否存在用户
             */
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password_encode);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()) {
            request.setAttribute("message", "用户名或密码错误！");
            return "explore/error";
        } else {
                /*
                登录用户存入session
                 */
            request.getSession().setAttribute(LoginUserSessionKey, users.get(0));
            return "redirect:/";
        }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute(LoginUserSessionKey);
        logger.info("logout request, login user:{}", JSON.toJSONString(loginUser));

        session.removeAttribute(LoginUserSessionKey);
        logger.info("logout success");

        return "redirect:/";
    }

    @RequestMapping(value="/password", method = RequestMethod.GET)
    public String password_get(HttpServletRequest request, HttpServletResponse response) {
        return "admin/password";
    }

    @RequestMapping(value="/password", method = RequestMethod.PUT)
    public String password_put(HttpServletRequest request, HttpServletResponse response) {
        return "admin/error";
    }
}
