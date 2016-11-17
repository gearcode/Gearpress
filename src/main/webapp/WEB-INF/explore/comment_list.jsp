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
        <title>审核评论 - Gearpress</title>

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

            <h4 style="margin-top: 20px;">共有<span id="comments_count" style="color:#d32; margin: 0 4px;">${fn:length(comments)}</span>条评论</h4>
            <!-- 评论列表 -->
            <ul class="comments-list">
                <c:forEach var="item" items="${comments}">
                    <li id="comment_${item.id}" class="alert <c:choose><c:when test="${item.status==0}">alert-warning</c:when><c:when test="${item.status==1}">alert-success</c:when></c:choose>">
                        <div class="comment-article">
                            <a target="_blank" href="<%=path%>/article/${item.article.id}">${item.article.title}</a>
                        </div>
                        <div class="comment-wrap">
                            <a href="javascript:void(0)" class="avatar">
                                <img src="<%=path%>/icon/noavatar_default.png" />
                            </a>
                            <div class="text-wrap">
                                <div>${item.name}(${item.contact})</div>
                                <p>${item.content}</p>
                                <div class="date"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${item.createTime}"/></div>
                            </div>
                        </div>
                        <div class="comment-admin">
                            <button class="btn btn-danger btn-sm" data-record-id="${item.id}" data-toggle="modal" data-target="#confirm-delete">
                                <span class="glyphicon glyphicon-trash"></span> 删除
                            </button>
                            <c:if test="${item.status != 1}">
                                &nbsp;
                                <button class="btn btn-success btn-sm" data-record-id="${item.id}">
                                    <span class="glyphicon glyphicon-ok"></span> 通过
                                </button>
                            </c:if>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        确认操作
                    </div>
                    <div class="modal-body">
                        确定删除此条评论？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <a class="btn btn-danger btn-ok">删除</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="<%=path %>/plugin/jquery/jquery-1.11.1.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=path %>/plugin/bootstrap3/js/bootstrap.min.js"></script>


        <script>

            // Bind click to OK button within popup
            $('#confirm-delete').on('click', '.btn-ok', function(e) {

                var $modalDiv = $(e.delegateTarget);
                var id = $(this).data('recordId');

                $modalDiv.addClass('loading');

                $.ajax({
                    type:"DELETE",
                    url:"http://localhost:10080/comment/"+id,
                    success:function(data){
                        if(data == "success") {
                            $modalDiv.modal('hide').removeClass('loading');
                            $("#comment_"+id).fadeOut("slow", function(target){
                                $(this).remove();
                                $("#comments_count").html($("#comments_count").html()-1);
                            });
                        }
                    }
                });
            });

            // Bind to modal opening to set necessary data properties to be used to make request
            $('#confirm-delete').on('show.bs.modal', function(e) {
                var data = $(e.relatedTarget).data();
                //$('.title', this).text(data.recordTitle);
                $('.btn-ok', this).data('recordId', data.recordId);
            });

            $(".comment-admin .btn-success").on("click", function(e) {
                var data = $(e.target).data();
                $.ajax({
                    type:"PUT",
                    url:"http://localhost:10080/comment/"+data.recordId,
                    success:function(data){
                        if(data == "success") {
                            $(e.target).remove();
                            $("li.alert").removeClass("alert-warning").addClass("alert-success");
                        }
                    }
                });
            });

        </script>
    </body>
</html>