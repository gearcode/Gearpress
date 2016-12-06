<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
%>
<!-- 文章列表 -->
<div class="article_wrap">
    <c:forEach var="item" items="${articles}">
        <div class="bs-callout bs-callout-info">
            <h4><a href="<%=path%>/article/${item.id}">${item.title}</a></h4>
            <div class="article-status">
                <span class="icon-group">
                    <span class="glyphicon glyphicon-calendar"></span>
                    <span class="words"><fmt:formatDate pattern="yyyy年MM月dd日" value="${item.createTime}"/></span>
                </span>
                <span class="icon-group">
                    <span class="glyphicon glyphicon-user"></span>
                    <span class="words">${item.user.name}</span>
                </span>
                <span class="icon-group">
                    <span class="glyphicon glyphicon-eye-open"></span>
                    <span class="words">${item.views}</span>
                </span>
                <span class="icon-group">
                    <span class="glyphicon glyphicon-comment"></span>
                    <span class="words">${fn:length(item.comments)}</span>
                </span>
            </div>
            <div class="article-abstract">
                ${fn:substring(item.content, 0, 500)}
                ......
            </div>
        </div>

    </c:forEach>

</div>