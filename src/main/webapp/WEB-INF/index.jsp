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
        <title>首页 - Gearpress</title>

        <!-- Bootstrap -->
        <link href="<%=path %>/plugin/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%=path %>/plugin/bootstrap3/css/docs.min.css" rel="stylesheet">

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
            <nav class="navbar navbar-default" role="navigation">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">首页</a>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="#">技术笔记</a></li>
                            <li><a href="#">问题解决</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#">未登录</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">liteng <span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#">修改密码</a></li>
                                    <li><a href="#">文章管理</a></li>
                                    <li><a href="#">栏目管理</a></li>
                                    <li><a href="#">审核评论</a></li>
                                    <li><a href="#">系统设置</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#">退出登录</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>

            <!-- 轮播组件 -->
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width:790px;height:340px;">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <a href="http://www.jd.com/" target="_blank">
                            <img src="http://img1.360buyimg.com/da/jfs/t3757/181/984089896/119779/f3764b8e/5819c0f1N68e59263.jpg" alt="">
                        </a>
                        <div class="carousel-caption"></div>
                    </div>
                    <div class="item">
                        <img src="http://img14.360buyimg.com/da/jfs/t3643/31/961808759/214308/57ad496f/581b20c8Nc81eea8c.jpg" alt="">
                        <div class="carousel-caption"></div>
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

            <h1 style="font-weight: bold;">近期发布的文章</h1>
            <div class="article_wrap">
                <c:forEach var="item" items="${articles}">
                    <div class="bs-callout bs-callout-info">
                        <h4>${item.title}</h4>
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


        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="<%=path %>/plugin/jquery/jquery-1.11.1.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=path %>/plugin/bootstrap3/js/bootstrap.min.js"></script>
    </body>
</html>