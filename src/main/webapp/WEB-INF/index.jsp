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
        <jsp:include page="common/include_css.jsp"></jsp:include>
    </head>
    <body>
        <div class="main-content" style="width: 790px; margin: 20px auto;">
            <!-- 导航栏 -->
            <jsp:include page="common/nav.jsp"></jsp:include>

            <!-- 轮播组件 -->
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width:790px;height:300px;">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <a href="#">
                            <img src="<%=path%>/images/c_1.jpg" alt="">
                        </a>
                        <div class="carousel-caption"></div>
                    </div>
                    <div class="item">
                            <img src="<%=path%>/images/c_3.jpg" alt="">
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

            <h1>近期发布的文章</h1>
            <jsp:include page="explore/common/article_list.jsp"></jsp:include>

        </div>

        <jsp:include page="common/include_js.jsp"></jsp:include>
    </body>
</html>