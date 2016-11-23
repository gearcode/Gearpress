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
        <title>管理文章 - Gearpress</title>
        <jsp:include page="../common/include_css.jsp"></jsp:include>
    </head>
    <body>
        <div class="main-content" style="width: 790px; margin: 20px auto;">
            <!-- 导航栏 -->
            <jsp:include page="../common/nav.jsp"></jsp:include>

            <h4 style="margin-top: 20px;">共有<span id="articles_count" style="color:#d32; margin: 0 4px;">${fn:length(articles)}</span>篇文章</h4>

            <div style="margin: 10px 0;">
                <div class="modal" id="titleDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title" id="myModalLabel">请输入文章标题</h4>
                            </div>
                            <div class="modal-body">
                                <select name="columnId" style="width: 100%" class="form-control select select-primary select-block mbl">
                                    <option value="" selected="selected">选择一个栏目</option>
                                    <c:forEach var="item" items="${columns_all}">
                                        <option value="${item.id}" <c:if test="${item.id == article.columnId}">selected="selected"</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                                <p>
                                    <input id="title" name="title" class="form-control" type="text" placeholder="在此输入文章标题" />
                                </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary btn-sm">创建新文章</button>
                                <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#titleDialog">
                    <span class="glyphicon glyphicon-file"></span> 创建新文章
                </button>
            </div>

            <!-- 文章列表 -->
            <ul class="article-list">
                <c:forEach var="item" items="${articles}">
                    <li id="article_${item.id}" class="alert <c:choose><c:when test="${item.status==0}">alert-warning</c:when><c:when test="${item.status==1}">alert-success</c:when></c:choose>">
                        <h5 class="article-title">
                            <a target="_blank" href="<%=path%>/article/${item.id}">${item.title}</a>
                        </h5>
                        <div class="article-admin">
                            <a href="<%=path%>/article/edit/${item.id}" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-edit"></span> 编辑
                            </a>
                            &nbsp;
                            <a class="btn btn-danger btn-sm" data-record-id="${item.id}" data-toggle="modal" data-target="#confirm-delete">
                                <span class="glyphicon glyphicon-trash"></span> 删除
                            </a>
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
                        确定删除此篇文章？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <a class="btn btn-danger btn-ok">删除</a>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../common/include_js.jsp"></jsp:include>

        <script>

            // Bind click to OK button within popup
            $('#confirm-delete').on('click', '.btn-ok', function(e) {

                var $modalDiv = $(e.delegateTarget);
                var id = $(this).data('recordId');

                $modalDiv.addClass('loading');

                $.ajax({
                    type:"DELETE",
                    url:"<%=path%>/article/"+id,
                    success:function(data){
                        if(data == "success") {
                            $modalDiv.modal('hide').removeClass('loading');
                            $("#article_"+id).fadeOut("slow", function(target){
                                $(this).remove();
                                $("#articles_count").html($("#articles_count").html()-1);
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

            //新建文章
            $(function(){
                $("#titleDialog .btn-primary").on("click", function(e) {
                    var $btn = $(this);
                    var $dialog = $btn.closest(".modal-dialog");
                    $btn.button("loading");
                    $.ajax({
                        type: "post",
                        url: "<%=path%>/article",
                        data: {
                            "title": $dialog.find("input[name=title]").val(),
                            "columnId": $dialog.find("select[name=columnId]").val()
                        },
                        success: function(result) {
                            if(result.result == "SUCCESS") {
                                //创建文章成功,重定向
                                window.location = "<%=path%>/article/edit/" + result.data.id;
                            } else {

                                $.notify({
                                    icon: 'glyphicon glyphicon-exclamation-sign',
                                    message: result.message
                                },{
                                    element: $dialog,
                                    type: "danger",
                                    allow_dismiss: false,
                                    delay: 1000
                                });

                                $btn.button("reset");
                            }
                        }
                    });
                });
            });

        </script>
    </body>
</html>