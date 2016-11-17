package com.gearcode.gearpress.vo;

import com.gearcode.gearpress.domain.Article;
import com.gearcode.gearpress.domain.Comment;

public class CommentVO extends Comment {
    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}