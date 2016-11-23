package com.gearcode.gearpress.controller;

import com.alibaba.fastjson.JSON;
import com.gearcode.gearpress.annotation.LoginRequired;
import com.gearcode.gearpress.dao.ArticleMapper;
import com.gearcode.gearpress.dao.CommentMapper;
import com.gearcode.gearpress.domain.Article;
import com.gearcode.gearpress.domain.Comment;
import com.gearcode.gearpress.domain.CommentExample;
import com.gearcode.gearpress.util.IdWorker;
import com.gearcode.gearpress.vo.CommentVO;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liteng3 on 2016/11/9.
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    IdWorker idWorker;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleMapper articleMapper;

    @RequestMapping(value="", method = RequestMethod.POST)
    public String comment_post(HttpServletRequest request, HttpServletResponse response) {
        String input_articleId = ServletRequestUtils.getStringParameter(request, "article_id", "");
        String input_name = ServletRequestUtils.getStringParameter(request, "comment_name", "");
        String input_contact = ServletRequestUtils.getStringParameter(request, "comment_contact", "");
        String input_content = ServletRequestUtils.getStringParameter(request, "comment_content", "");

        logger.info("comment_post, name:{}, contact:{}, content:{}", input_name, input_contact, input_content);

        long articleId = 0;
        try {
            articleId = Long.parseLong(input_articleId);
            if(articleId <= 0) {
                throw new Exception("article_id不能小于等于0");
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            request.setAttribute("message", "系统错误，请重试！");
            return "error";
        }

        if(input_name.length() == 0 || input_content.length() == 0) {
            request.setAttribute("message", "姓名和评论内容不能为空！");
            return "error";
        }

        /*
        过滤html标签
         */
        String name_text = Jsoup.parse(input_name).text();
        String contact_text = Jsoup.parse(input_contact).text();
        String content_text = Jsoup.parse(input_content.replaceAll("[\\r]?[\\n]", "br2n")).text();

        /*
        输入长度限制
         */
        if(name_text.length() > 15) {
            request.setAttribute("message", "姓名长度不能超过15个字符！");
            return "error";
        }
        if(contact_text.length() > 50) {
            request.setAttribute("message", "联系方式不能超过50个字符！");
            return "error";
        }
        if(content_text.length() > 800) {
            request.setAttribute("message", "评论内容不能超过800个字符！");
            return "error";
        }

        /*
        保存评论
         */
        Comment record = new Comment();
        record.setId(idWorker.nextId());
        record.setArticleId(articleId);
        record.setName(name_text);
        record.setContact(contact_text);
        record.setContent(content_text.replaceAll("br2n", "<br />"));
        record.setStatus((byte) 0);
        record.setCreateTime(new Date());
        commentMapper.insert(record);

        request.setAttribute("message", "评论成功！");
        return "success";
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Object comment_delete(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        commentMapper.deleteByPrimaryKey(id);
        return "success";
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Object comment_put(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus((byte) 1);
        commentMapper.updateByPrimaryKeySelective(comment);
        return "success";
    }

    @LoginRequired
    @RequestMapping("list")
    public String comment_list(HttpServletRequest request, HttpServletResponse response) {

        /*
        查询全部评论
        TODO 分页、查询条件、排序字段
         */
        List<CommentVO> commentVOs = new LinkedList<CommentVO>();
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("id DESC");
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        for (Comment comment : comments) {
            CommentVO commentVO = assembleCommentVO(comment);
            commentVOs.add(commentVO);
        }

        request.setAttribute("comments", commentVOs);

        return "admin/comment_list";
    }

    private CommentVO assembleCommentVO(Comment comment) {
        CommentVO commentVO = JSON.parseObject(JSON.toJSONString(comment), CommentVO.class);

        //查询文章
        Article article = articleMapper.selectByPrimaryKey(comment.getArticleId());
        if(null != article) {
            commentVO.setArticle(article);
        }

        return commentVO;
    }

}
