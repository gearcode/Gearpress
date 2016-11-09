package com.gearcode.gearpress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liteng3 on 2016/11/9.
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @RequestMapping(value="", method = RequestMethod.POST)
    public String comment_post(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("message", "评论成功！");
        return "success";
    }
}
