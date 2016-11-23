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
        <title>出错了 - Gearpress</title>
        <jsp:include page="common/include_css.jsp"></jsp:include>
    </head>
    <body>
        <div class="main-content" style="width: 790px; margin: 20px auto;">
            <div class="jumbotron" style="padding: 60px; color: #c7254e;">
                <h1><span class="glyphicon glyphicon-remove-sign red" style="top: 8px;"></span>出错了</h1>
                <p>${message}</p>
                <p><a class="btn btn-danger btn-lg" href="javascript:history.go(-1)" role="button">返回上一页</a></p>
            </div>
        </div>

        <jsp:include page="common/include_js.jsp"></jsp:include>
    </body>
</html>