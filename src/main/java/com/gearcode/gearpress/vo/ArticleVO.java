package com.gearcode.gearpress.vo;

import com.gearcode.gearpress.domain.Article;
import com.gearcode.gearpress.domain.Column;
import com.gearcode.gearpress.domain.Comment;
import com.gearcode.gearpress.domain.User;

import java.util.Date;
import java.util.List;

public class ArticleVO extends Article{
    private User user;
    private Column column;
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}