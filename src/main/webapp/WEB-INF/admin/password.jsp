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
        <title>修改密码 - Gearpress</title>

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
            <form class="form-horizontal" role="form" action="<%=path%>/user/password" method="post" onsubmit="return check()">
                <input name="_method" type="hidden" value="put" />
                <h2>修改密码</h2>
                <div class="form-group">
                    <label for="old_password" class="col-sm-2 control-label">原始密码</label>
                    <div class="col-sm-10">
                        <input name="old_password" type="password" class="form-control" id="old_password" placeholder="原始密码" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="new_password" class="col-sm-2 control-label">新的密码</label>
                    <div class="col-sm-10">
                        <input name="new_password" type="password" class="form-control" id="new_password" placeholder="新的密码" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="new_password2" class="col-sm-2 control-label">确认输入</label>
                    <div class="col-sm-10">
                        <input name="new_password2" type="password" class="form-control" id="new_password2" placeholder="确认输入" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">修改密码</button>
                    </div>
                </div>
            </form>

        </div>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="<%=path %>/plugin/jquery/jquery-1.11.1.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=path %>/plugin/bootstrap3/js/bootstrap.min.js"></script>
        <!-- javascript md5 impl -->
        <script src="<%=path %>/plugin/md5/md5.js"></script>

        <script>
            function check() {
                var $old = $('#old_password'), $new = $('#new_password'), $new2 = $('#new_password2');

                if($new.val() != $new2.val()) {
                    alert("两次密码输入不一致！");
                    return false;
                }

                $old.val(md5($old.val()));
                $new.val(md5($new.val()));
                $new2.val('');

                return true;
            }
        </script>
    </body>
</html>