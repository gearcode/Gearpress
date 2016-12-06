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
        <title>管理栏目 - Gearpress</title>
        <jsp:include page="../common/include_css.jsp"></jsp:include>
    </head>
    <body>
        <div class="main-content" style="width: 790px; margin: 20px auto;">
            <!-- 导航栏 -->
            <jsp:include page="../common/nav.jsp"></jsp:include>

            <h4 style="margin-top: 20px;">共有<span id="columns_count" style="color:#d32; margin: 0 4px;">${fn:length(columns_all)}</span>个栏目</h4>

            <div style="margin: 10px 0;">
                <div class="modal" id="titleDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title" id="myModalLabel">请输入栏目名称</h4>
                            </div>
                            <div class="modal-body">
                                <p>
                                    <input id="name" name="name" class="form-control" type="text" placeholder="在此输入栏目名称" />
                                </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary btn-sm">新增栏目</button>
                                <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#titleDialog">
                    <span class="glyphicon glyphicon-file"></span> 新增栏目
                </button>
            </div>

            <!-- 列表 -->
            <table class="column-list">
                <thead>
                    <tr>
                        <th>栏目名称</th>
                        <th>是否可见</th>
                        <th>排序值</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${columns_all}">
                        <tr id="column_${item.id}" class="alert <c:choose><c:when test="${item.isShow==0}">alert-warning</c:when><c:when test="${item.isShow==1}">alert-success</c:when></c:choose>">
                            <td>
                                <input type="text" placeholder="Empty" class="form-control flat" value="${item.name}"/>
                            </td>
                            <td>
                                <input id="post_status" <c:if test="${item.isShow == 1}">checked</c:if> name="post_status" class="flatui-checkbox" type="checkbox" data-toggle="switch" name="square-switch" data-on-text="可见" data-off-text="隐藏" />
                            </td>
                            <td>
                                <input type="text" placeholder="Empty" class="form-control flat" value="${item.prority}" style="width: 100px;"/>
                            </td>
                            <td>
                                <a href="#" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash"></span> 删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
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
                    url:"<%=path%>/column/"+id,
                    success:function(data){
                        if(data == "success") {
                            $modalDiv.modal('hide').removeClass('loading');
                            $("#column_"+id).fadeOut("slow", function(target){
                                $(this).remove();
                                $("#column_count").html($("#column_count").html()-1);
                            });
                        }
                    }
                });
            });

            // Bind to modal opening to set necessary data properties to be used to make request
            $('#confirm-delete').on('show.bs.modal', function(e) {
                var data = $(e.relatedTarget).data();
                $('.btn-ok', this).data('recordId', data.recordId);
            });

            //新增栏目
            $(function(){
                $("#titleDialog .btn-primary").on("click", function(e) {
                    var $btn = $(this);
                    var $dialog = $btn.closest(".modal-dialog");
                    $btn.button("loading");
                    $.ajax({
                        type: "post",
                        url: "<%=path%>/column",
                        data: {
                            "name": $dialog.find("input[name=name]").val()
                        },
                        success: function(result) {
                            if(result.result == "SUCCESS") {
                                //创建文章成功,重定向
                                window.location = window.location;
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