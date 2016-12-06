package com.gearcode.gearpress.controller;

import com.alibaba.fastjson.JSON;
import com.gearcode.gearpress.dao.ArticleMapper;
import com.gearcode.gearpress.dao.ColumnMapper;
import com.gearcode.gearpress.dao.CommentMapper;
import com.gearcode.gearpress.dao.UserMapper;
import com.gearcode.gearpress.domain.*;
import com.gearcode.gearpress.vo.ArticleVO;
import org.apache.ibatis.session.RowBounds;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liteng3 on 2016/11/3.
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ColumnMapper columnMapper;
    @Autowired
    CommentMapper commentMapper;

    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        /*
        查询全部栏目
         */
        ColumnExample columnExample = new ColumnExample();
        columnExample.createCriteria().andIsShowEqualTo((byte)1);
        columnExample.setOrderByClause("prority asc");
        List<Column> columns = columnMapper.selectByExample(columnExample);
        request.setAttribute("columns", columns);

        /*
        查询最近文章
         */
        List<ArticleVO> articleVOs = new ArrayList<ArticleVO>();
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andStatusEqualTo((byte) 1);
        articleExample.setOrderByClause("id desc");
        List<Article> articles = articleMapper.selectByExampleWithBLOBsWithRowbounds(articleExample,  new RowBounds(0, 10));
        for (Article article : articles) {
            articleVOs.add(assembleArticleVO(article));
        }
        request.setAttribute("articles", articleVOs);
        return "index";
    }

    @RequestMapping("articles")
    public String articles(HttpServletRequest request, HttpServletResponse response) {
        logger.info("articles");
        return "articles";
    }


    private ArticleVO assembleArticleVO(Article article) {
        ArticleVO articleVO = JSON.parseObject(JSON.toJSONString(article), ArticleVO.class);

        articleVO.setId(String.valueOf(article.getId()));
        articleVO.setColumnId(String.valueOf(article.getColumnId()));

        /*
        文章内容压缩并截断
         */
        articleVO.setContent(Jsoup.parse(article.getContent()).text());

        //查询用户
        User user = userMapper.selectByPrimaryKey(article.getUserId());
        if(null != user) {
            articleVO.setUser(user);
        }

        //查询栏目
        Column column = columnMapper.selectByPrimaryKey(article.getColumnId());
        if(null != column) {
            articleVO.setColumn(column);
        }

        //查询评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andArticleIdEqualTo(article.getId())
                .andStatusEqualTo((byte) 1);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        articleVO.setComments(comments);

        return articleVO;
    }
}
