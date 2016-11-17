<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">首页</a>
        </div>
        <div class="navbar-collapse">
            <ul class="nav navbar-nav">
                <c:forEach var="item" items="${columns}">
                    <li ${item.id eq column.id ? "class='active'" : ""}><a href="<%=path%>/column/${item.id}">${item.name}</a></li>
                </c:forEach>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${sessionScope.loginUser == null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>登录</b> <span class="caret"></span></a>
                        <ul class="dropdown-menu" style="min-width: 250px; padding: 14px 14px 0; overflow: hidden; background-color: rgba(255,255,255,.8);">
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        <!-- javascript md5 impl -->
                                        <script src="<%=path %>/plugin/md5/md5.js"></script>
                                        <form class="form" role="form" method="post" action="<%=path%>/user/login" onsubmit="$('#input_password_encode').val(md5($('#input_password').val()));$('#input_password').val('')">
                                            <div class="form-group">
                                                 <label class="sr-only" for="input_username">用户名</label>
                                                 <input name="username" type="text" class="form-control" id="input_username" placeholder="用户名" required>
                                            </div>
                                            <div class="form-group">
                                                 <label class="sr-only" for="input_password">密码</label>
                                                 <input name="password" type="password" class="form-control" id="input_password" placeholder="密码" required>
                                                 <input name="password_encode" type="hidden" class="form-control" id="input_password_encode" />
                                            </div>
                                            <div class="form-group">
                                                 <button type="submit" class="btn btn-primary btn-block">登录</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.loginUser != null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.loginUser.name } <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">文章管理</a></li>
                            <li><a href="#">栏目管理</a></li>
                            <li><a href="<%=path%>/comment/list">审核评论</a></li>
                            <li><a href="#">系统设置</a></li>
                            <li class="divider"></li>
                            <li><a href="<%=path%>/user/password">修改密码</a></li>
                            <li><a href="<%=path%>/user/logout">退出登录</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>