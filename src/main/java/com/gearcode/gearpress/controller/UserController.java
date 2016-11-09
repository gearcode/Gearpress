package com.gearcode.gearpress.controller;

import com.alibaba.fastjson.JSON;
import com.gearcode.gearpress.annotation.LoginRequired;
import com.gearcode.gearpress.dao.UserMapper;
import com.gearcode.gearpress.domain.User;
import com.gearcode.gearpress.domain.UserExample;
import com.gearcode.gearpress.util.Constants;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by liteng3 on 2016/11/7.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String username = ServletRequestUtils.getStringParameter(request, "username", "");
        String password = ServletRequestUtils.getStringParameter(request, "password", "");
        String password_encode = ServletRequestUtils.getStringParameter(request, "password_encode", "");
        logger.info("login request, username:{}, password:{}, password_encode:{}", username, password, password_encode);

        /*
        查询是否存在用户
         */
        User user = findUser(username, password_encode);
        if(user == null) {
            request.setAttribute("message", "用户名或密码错误！");
            return "error";
        } else {
            /*
            登录用户存入session
             */
            request.getSession().setAttribute(Constants.LOGIN_USER_SESSION_KEY, user);
            return "redirect:/";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_SESSION_KEY);
        logger.info("logout request, login user:{}", JSON.toJSONString(loginUser));

        session.removeAttribute(Constants.LOGIN_USER_SESSION_KEY);
        logger.info("logout success");

        return "redirect:/";
    }

    @LoginRequired
    @RequestMapping(value="/password", method = RequestMethod.GET)
    public String password_get(HttpServletRequest request, HttpServletResponse response) {
        return "admin/password";
    }

    @LoginRequired
    @RequestMapping(value="/password", method = RequestMethod.PUT)
    public String password_put(HttpServletRequest request, HttpServletResponse response) {
        User loginUser = (User) request.getSession().getAttribute(Constants.LOGIN_USER_SESSION_KEY);

        String old_password = ServletRequestUtils.getStringParameter(request, "old_password", "");
        String new_password = ServletRequestUtils.getStringParameter(request, "new_password", "");
        logger.info("change password, old:{}, new:{}", old_password, new_password);

        if(Strings.isNullOrEmpty(old_password) || Strings.isNullOrEmpty(new_password)) {
            request.setAttribute("message", "密码不能为空！");
            return "error";
        }

        /*
        判断旧密码
         */
        User user = findUser(loginUser.getUsername(), old_password);
        if(user == null) {
            request.setAttribute("message", "原始密码输入错误！");
            return "error";
        } else {
            User record = new User();
            record.setPassword(new_password);
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(loginUser.getId());
            userMapper.updateByExampleSelective(record, example);
            request.setAttribute("message", "密码修改成功！");
            return "success";
        }

    }

    private User findUser(String username, String password_encode) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password_encode);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }
}
