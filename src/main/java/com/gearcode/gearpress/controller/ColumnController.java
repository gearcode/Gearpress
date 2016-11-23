package com.gearcode.gearpress.controller;

import com.alibaba.fastjson.JSON;
import com.gearcode.gearpress.dao.ArticleMapper;
import com.gearcode.gearpress.dao.ColumnMapper;
import com.gearcode.gearpress.dao.CommentMapper;
import com.gearcode.gearpress.dao.UserMapper;
import com.gearcode.gearpress.domain.*;
import com.gearcode.gearpress.vo.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liteng3 on 2016/11/7.
 */
@Controller
@RequestMapping(value = "/column")
public class ColumnController {
    private static final Logger logger = LoggerFactory.getLogger(ColumnController.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ColumnMapper columnMapper;

    @RequestMapping("/{columnId}")
    public String column(@PathVariable("columnId") Long columnId,
            HttpServletRequest request, HttpServletResponse response) {
        /*
        当前浏览栏目
         */
        ColumnExample columnExample = new ColumnExample();
        Column column = columnMapper.selectByPrimaryKey(columnId);
        request.setAttribute("column", column);

        /*
        查询全部栏目
         */
        ColumnExample showColumnExample = new ColumnExample();
        showColumnExample.createCriteria().andIsShowEqualTo((byte)1);
        showColumnExample.setOrderByClause("prority asc");
        List<Column> columns = columnMapper.selectByExample(showColumnExample);
        request.setAttribute("columns", columns);

        /*
        查询栏目下文章
         */
        List<ArticleVO> articleVOs = new ArrayList<ArticleVO>();
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria()
                .andColumnIdEqualTo(columnId)
                .andStatusEqualTo((byte) 1);
        articleExample.setOrderByClause("id desc");
        List<Article> articles = articleMapper.selectByExampleWithBLOBs(articleExample);
        for (Article article : articles) {
            articleVOs.add(assembleArticleVO(article));
        }
        request.setAttribute("articles", articleVOs);

        return "explore/column";
    }

    private ArticleVO assembleArticleVO(Article article) {
        ArticleVO articleVO = JSON.parseObject(JSON.toJSONString(article), ArticleVO.class);

        articleVO.setId(String.valueOf(article.getId()));
        articleVO.setColumnId(String.valueOf(article.getColumnId()));

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
        commentExample.createCriteria().andArticleIdEqualTo(article.getId());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        articleVO.setComments(comments);

        return articleVO;
    }
}
