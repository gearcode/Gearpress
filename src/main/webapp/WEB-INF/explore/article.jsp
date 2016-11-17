<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${article.title} - Gearpress</title>

        <!-- Bootstrap -->
        <link href="<%=path %>/plugin/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%=path %>/plugin/bootstrap3/css/docs.min.css" rel="stylesheet">

        <!-- main.css -->
        <link href="<%=path %>/css/main.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%=path %>/plugin/html5/html5shiv.min.js"></script>
            <script src="<%=path %>/plugin/html5/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="main-content" style="width: 790px; margin: 20px auto;">
            <!-- 导航栏 -->
            <jsp:include page="common/nav.jsp"></jsp:include>

            <h2>${article.title}</h2>
            <div class="article-status">
                <span class="icon-group">
                    <span class="glyphicon glyphicon-calendar"></span>
                    <span class="words"><fmt:formatDate pattern="yyyy年MM月dd日" value="${article.postTime}"/></span>
                </span>
                <span class="icon-group">
                    <span class="glyphicon glyphicon-user"></span>
                    <span class="words">${article.user.name}</span>
                </span>
                <span class="icon-group">
                    <span class="glyphicon glyphicon-eye-open"></span>
                    <span class="words">${article.views}</span>
                </span>
                <span class="icon-group">
                    <a href="#comments">
                        <span class="glyphicon glyphicon-comment"></span>
                        <span class="words">${fn:length(article.comments)}</span>
                    </a>
                </span>
            </div>
            <div style="margin: 10px 0;">
                ${article.content}
            </div>

            <a name="comments"></a>
            <h4 style="margin-top: 20px;">共有<span style="color:#d32; margin: 0 4px;">${fn:length(article.comments)}</span>条评论</h4>
            <!-- 评论列表 -->
            <ul class="comments-list">
                <c:forEach var="item" items="${article.comments}">
                    <li>
                        <div class="comment-wrap">
                            <a href="javascript:void(0)" class="avatar">
                                <img src="<%=path%>/icon/noavatar_default.png" />
                            </a>
                            <div class="text-wrap">
                                <div>${item.name}</div>
                                <p>${item.content}</p>
                                <div class="date"><fmt:formatDate pattern="MM月dd日" value="${item.createTime}"/></div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>

            <h4>发表评论</h4>
            <div class="comments-commit">
                <a href="javascript:void(0)" class="avatar">
                    <img src="<%=path%>/icon/guest.jpg">
                </a>
                <form action="<%=path%>/comment" method="post">
                    <div class="form-group">
                        <label for="comment_name">您的姓名</label>
                        <input type="text" name="comment_name" class="form-control" id="comment_name" placeholder="您的姓名" maxlength="15" required>
                    </div>
                    <div class="form-group">
                        <label for="comment_contact">联系方式</label>
                        <input type="text" name="comment_contact" class="form-control" id="comment_contact" placeholder="联系方式" maxlength="50">
                    </div>
                    <div class="form-group">
                        <label for="comment_content">评论内容</label>
                        <div class="text-wrap">
                            <textarea id="comment_content" name="comment_content" placeholder="说点什么吧..." maxlength="800" required></textarea>
                            <div class="comments-bottom">
                                <input type="hidden" name="article_id" value="${article.id}" />
                                <button type="submit">发表评论</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="<%=path %>/plugin/jquery/jquery-1.11.1.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=path %>/plugin/bootstrap3/js/bootstrap.min.js"></script>
    </body>
</html>