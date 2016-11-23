<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
%>
<script src="<%=path%>/plugin/jquery/jquery-1.11.1.min.js"></script>
<script src="<%=path%>/plugin/jquery/jquery.form.js"></script>
<script src="<%=path%>/plugin/flatui/js/flat-ui.min.js"></script>
<script src="<%=path%>/plugin/bootstrap-notify-3.1.3/bootstrap-notify.min.js"></script>

<script>
    $("select").select2({dropdownCssClass: 'dropdown-inverse'});
    $('input.flatui-checkbox').bootstrapSwitch();
</script>