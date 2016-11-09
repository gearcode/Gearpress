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
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liteng3 on 2016/11/9.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ColumnMapper columnMapper;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public String article_get(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

        /*
        查询当前文章
         */
        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null) {
            request.setAttribute("message", "找不到此文章！");
            return "error";
        }
        request.setAttribute("article", assembleArticleVO(article));

        /*
        文章浏览量+1
         */
        Article articleAddView = new Article();
        articleAddView.setId(id);
        articleAddView.setViews(article.getViews()+1);
        articleMapper.updateByPrimaryKeySelective(articleAddView);

        /*
        当前浏览栏目
         */
        ColumnExample columnExample = new ColumnExample();
        Column column = columnMapper.selectByPrimaryKey(article.getColumnId());
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
        articleExample.createCriteria().andColumnIdEqualTo(column.getId());
        articleExample.setOrderByClause("id desc");
        List<Article> articles = articleMapper.selectByExampleWithBLOBs(articleExample);
        for (Article article1 : articles) {
            articleVOs.add(assembleArticleVO(article1));
        }
        request.setAttribute("articles", articleVOs);

        return "explore/article";
    }

    private ArticleVO assembleArticleVO(Article article) {
        ArticleVO articleVO = JSON.parseObject(JSON.toJSONString(article), ArticleVO.class);

        //查询用户
        User user = userMapper.selectByPrimaryKey(article.getUserId());
        if(null != user) {
            articleVO.setUser(user);
        }

        //查询评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andArticleIdEqualTo(article.getId());
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        articleVO.setComments(comments);

        return articleVO;
    }
}
