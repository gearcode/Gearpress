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
            <div>作者：${item.user.name} | <fmt:formatDate pattern="yyyy-MM-dd" value="${item.postTime}"/></div>
            <div>
                <span class="icon-group">
                    <span class="glyphicon glyphicon-eye-open"></span>
                    <span class="words">${item.views}</span>
                </span>
                <span class="icon-group" style="margin-left: 10px;">
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