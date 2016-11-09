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
        <title>${column.name} - Gearpress</title>

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

            <h1>文章列表</h1>
            <jsp:include page="common/article_list.jsp"></jsp:include>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="<%=path %>/plugin/jquery/jquery-1.11.1.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=path %>/plugin/bootstrap3/js/bootstrap.min.js"></script>
    </body>
</html>