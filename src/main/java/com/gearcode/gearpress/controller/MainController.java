package com.gearcode.gearpress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liteng3 on 2016/11/3.
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        logger.info("SUCCESS");
        return "index";
    }

    @RequestMapping("articles")
    public String articles(HttpServletRequest request, HttpServletResponse response) {
        logger.info("articles");
        return "articles";
    }


}
