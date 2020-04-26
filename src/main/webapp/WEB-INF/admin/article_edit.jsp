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
        <title>撰写文章 - Gearpress</title>
        <jsp:include page="../common/include_css.jsp"></jsp:include>
    </head>
    <body>

    <div class="row" style="margin: 20px 0;">
        <div class="main-content col-md-offset-2 col-md-8">
            <!-- 导航栏 -->
            <jsp:include page="../common/nav.jsp"></jsp:include>

            <form id="form" action="<%=path%>/article" method="post">
                <input name="_method" type="hidden" value="put" />
                <input name="id" type="hidden" value="${article.id}" />
                <div style="position: relative;">
                    <h2 style="margin: 15px 0;"><span class="glyphicon glyphicon-edit"></span> 撰写文章</h2>
                    <div style="position: absolute; right: 0; top: -2px;">
                        <select name="columnId" class="form-control select select-primary select-block mbl">
                            <option value="0">选择一个栏目</option>
                            <c:forEach var="item" items="${columns_all}">
                                <option value="${item.id}" <c:if test="${item.id == article.columnId}">selected="selected"</c:if>>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <input type="text" name="title" class="form-control" placeholder="在此输入标题" value="${article.title}" style="margin-bottom: 14px;"/>

                <!-- 编辑器 -->
                <script id="ue_editor" name="ue_content" type="text/plain">${article.content}</script>
                <input type="hidden" name="content" value="" />

                <div class="btn_bar" style="margin: 15px 0;">
                    <p class="bootstrap-switch-square">
                        <input id="post_status" <c:if test="${article.status == 1}">checked</c:if> name="post_status" class="flatui-checkbox" type="checkbox" data-toggle="switch" name="square-switch" data-on-text="发布" data-off-text="草稿" />
                    </p>
                    <button type="submit" class="btn btn-embossed btn-primary">
                        <span class="glyphicon glyphicon-floppy-save"></span> 保存文章
                    </button>
                </div>
            </form>
        </div>

        <jsp:include page="../common/include_js.jsp"></jsp:include>
    </div>
        <!-- ueditor -->
        <script type="text/javascript" charset="utf-8" src="<%=path %>/plugin/ueditor/ueditor.config.js"></script>
        <script type="text/javascript">
            UEDITOR_CONFIG.serverUrl = "<%=path%>/ueUpload";
        </script>
        <script type="text/javascript" charset="utf-8" src="<%=path %>/plugin/ueditor/ueditor.all.min.js"> </script>
        <script type="text/javascript" charset="utf-8" src="<%=path %>/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>

        <script>
            $(function(){
                //初始化编辑器
                var ue_desc = UE.getEditor('ue_editor', {
                    initialFrameHeight: 460
                });

                ue_desc.ready(function() {
                    UE.dom.domUtils.on(ue_desc.body,"keydown",function(oEvent){
                        var oEvent = oEvent || window.oEvent;
                        var nKeyCode = oEvent.keyCode || oEvent.which || oEvent.charCode;
                        var bCtrlKeyCode = oEvent.ctrlKey || oEvent.metaKey;
                        if( nKeyCode == 83 && bCtrlKeyCode  ) {
                            save();
                            //阻止触发默认的ctrl+s事件
                            oEvent.returnValue = false;
                        }
                    });
                });

                $(document).bind('keydown', function(e) {
                  if(e.ctrlKey && (e.which == 83)) {
                    e.preventDefault();
                    save();
                    return false;
                  }
                });
            });

            jQuery(function($) {
                $("#form").on("submit", function(e) {
                    save();
                    return false;
                });
            });

            function save() {
                $("#form .btn_bar button").button("loading");
                $("#form input[name=content]").val(UE.getEditor('ue_editor').getContent());

                $("#form").ajaxSubmit({
                    success: function(response, status, xhr, $form) {
                        console.log(response);

                        if(response.result == 'success') {
                            $.notify({
                                icon: 'glyphicon glyphicon-ok',
                                message: "保存成功！"
                            },{
                                type: "success",
                                allow_dismiss: false,
                                delay: 1000,
                                z_index: 10000
                            });
                        } else if (response.result == 'noAuth') {
                            $.notify({
                                icon: 'glyphicon glyphicon-warning-sign',
                                message: "登录失效！"
                            },{
                                type: "danger",
                                allow_dismiss: false,
                                delay: 1000,
                                z_index: 10000
                            });
                        } else {
                            $.notify({
                                icon: 'glyphicon glyphicon-warning-sign',
                                message: "保存失败！"
                            },{
                                type: "danger",
                                allow_dismiss: false,
                                delay: 1000,
                                z_index: 10000
                            });
                        }
                        $("#form .btn_bar button").button("reset");
                    }
                });
            }

            setInterval(save, 60000);
        </script>
    </body>
</html>