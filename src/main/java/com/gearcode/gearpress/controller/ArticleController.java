package com.gearcode.gearpress.controller;

import com.alibaba.fastjson.JSON;
import com.gearcode.gearpress.annotation.LoginRequired;
import com.gearcode.gearpress.dao.ArticleMapper;
import com.gearcode.gearpress.dao.ColumnMapper;
import com.gearcode.gearpress.dao.CommentMapper;
import com.gearcode.gearpress.dao.UserMapper;
import com.gearcode.gearpress.domain.*;
import com.gearcode.gearpress.util.IdWorker;
import com.gearcode.gearpress.vo.AjaxResult;
import com.gearcode.gearpress.vo.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.gearcode.gearpress.vo.AjaxResult.Result.FAILURE;
import static com.gearcode.gearpress.vo.AjaxResult.Result.SUCCESS;

/**
 * Created by liteng3 on 2016/11/9.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    IdWorker idWorker;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ColumnMapper columnMapper;

    /*
    文章页
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public String article_get(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

        /*
        查询当前文章
         */
        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null || !article.getStatus().equals((byte)1)) {
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

    /*
    文章管理
     */
    @LoginRequired
    @RequestMapping("list")
    public String article_list(HttpServletRequest request, HttpServletResponse response) {

        /*
        查询全部文章
        TODO 分页、排序、查询条件
         */
        List<ArticleVO> articleVOs = new ArrayList<ArticleVO>();
        ArticleExample articleExample = new ArticleExample();
        articleExample.setOrderByClause("id desc");
        List<Article> articles = articleMapper.selectByExample(articleExample);
        for (Article article : articles) {
            articleVOs.add(assembleArticleVO(article));
        }
        request.setAttribute("articles", articleVOs);

        /*
        查询全部栏目
         */
        ColumnExample columnExample = new ColumnExample();
        List<Column> columns = columnMapper.selectByExample(columnExample);
        request.setAttribute("columns_all", columns);

        return "admin/article_list";
    }

    @LoginRequired
    @RequestMapping("edit/{id}")
    public String article_edit(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

        /*
        查询文章
         */
        Article article = articleMapper.selectByPrimaryKey(id);
        if(null == article) {
            request.setAttribute("message", "找不到此文章！");
            return "error";
        }
        request.setAttribute("article", article);

        /*
        查询栏目列表
         */
        List<Column> columns = columnMapper.selectByExample(new ColumnExample());
        request.setAttribute("columns_all", columns);

        return "admin/article_edit";
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.PUT)
    public Object article_save(@ModelAttribute("article") Article article, HttpServletRequest request, HttpServletResponse response) {
        Date now = new Date();

        if(null == article.getId()) {
            return "error";
        } else {
            logger.info("save article: {}", JSON.toJSONString(article));

            String postStatus = ServletRequestUtils.getStringParameter(request, "post_status", "000");
            if(postStatus.equals("on")) {
                article.setStatus((byte) 1);
            } else {
                article.setStatus((byte) 0);
            }

            if(null == article.getTitle()) {
                article.setTitle("");
            }
            if(null == article.getContent()) {
                article.setContent("");
            }
            if(null == article.getColumnId()) {
                article.setColumnId(0L);
            }

            /*
            保存文章
             */
            articleMapper.updateByPrimaryKeySelective(article);
        }

        Map<String, Object> ajaxResult = new HashMap<String, Object>();
        ajaxResult.put("id", String.valueOf(article.getId()));
        return ajaxResult;
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.POST)
    public Object article_new(HttpServletRequest request, HttpServletResponse response) {
        Date now = new Date();
        AjaxResult result = new AjaxResult();

        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        Long columnId = ServletRequestUtils.getLongParameter(request, "columnId", 0);

        if(title.length() == 0) {
            result.setResult(FAILURE);
            result.setMessage("标题不能为空！");
            return result;
        }

        /*
        新增文章
         */
        Article article = new Article();
        article.setId(idWorker.nextId());
        article.setColumnId(columnId);
        article.setUserId(0L);
        article.setViews(0L);
        article.setTitle(title);
        article.setContent("");
        article.setStatus((byte) 0);
        article.setCreateTime(now);
        articleMapper.insert(article);

        result.setResult(SUCCESS);
        result.setMessage("文章创建成功");
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId(String.valueOf(article.getId()));
        result.setData(articleVO);
        return result;
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Object article_delete(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        articleMapper.deleteByPrimaryKey(id);
        return "success";
    }

    /**
     * 组装文章类的VO
     *
     * @param article 文章PO
     * @return 组装好的VO对象
     */
    private ArticleVO assembleArticleVO(Article article) {
        ArticleVO articleVO = JSON.parseObject(JSON.toJSONString(article), ArticleVO.class);

        articleVO.setId(String.valueOf(article.getId()));
        articleVO.setColumnId(String.valueOf(article.getColumnId()));

        //查询用户
        User user = userMapper.selectByPrimaryKey(article.getUserId());
        if(null != user) {
            articleVO.setUser(user);
        }

        //查询评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andArticleIdEqualTo(article.getId())
                .andStatusEqualTo((byte)1);
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        articleVO.setComments(comments);

        //查询栏目
        Column column = columnMapper.selectByPrimaryKey(article.getColumnId());
        if(null != column) {
            articleVO.setColumn(column);
        }

        return articleVO;
    }
}
