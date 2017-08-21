<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <!-- 导入jquery核心类库 -->
<script type="text/javascript"
        src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
    <!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
        src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
        src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
        type="text/javascript"></script>
    <!-- 导入ztree类库 -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
      type="text/css"/>
<script
        src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
        type="text/javascript"></script>
<script type="text/javascript">
	$(function () {

        <%--// 授权树初始化--%>
        <%--var setting = {--%>
        <%--data : {--%>
        <%--key : {--%>
        <%--title : "t"--%>
        <%--},--%>
        <%--simpleData : {--%>
        <%--enable : true--%>
        <%--}--%>
        <%--},--%>
        <%--check : {--%>
        <%--enable : true,--%>
        <%--}--%>
        <%--};--%>
        <%----%>
        <%--$.ajax({--%>
        <%--url : '${pageContext.request.contextPath}/menuAction_ajaxList',--%>
        <%--type : 'POST',--%>
        <%--dataType : 'text',--%>
        <%--success : function(data) {--%>
        <%--var zNodes = eval("(" + data + ")");--%>
        <%--$.fn.zTree.init($("#functionTree"), setting, zNodes);--%>
        <%--},--%>
        <%--error : function(msg) {--%>
        <%--alert('树加载异常!');--%>
        <%--}--%>
        <%--});--%>

        //  权限数据checkbox生成
        $.post("functionAction_listFunctionAjax.action", function (data) {
            $(data).each(function () {
                //  List<Function>
				// 下面这个格式化会乱码
               //$("#functionIds").append("<input name='functionIds' type='checkbox' value='"+this.id+"'>"+this.name+"</input>&nbsp;&nbsp;");
               $("#functionIds").append("<input name='functionIds' type='checkbox' value='"+this.id+"'>"+this.name+"</input>&nbsp;&nbsp;");
            });
        });

    });
</script>
</head>
<body class="easyui-layout">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="roleForm" action="roleAction_add.action"
                  method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td width="200">角色关键字</td>
						<td>
						 <!-- 菜单的ids -->
						   <input type="hidden" id="menuIds" name="menuIds">
							<input type="text" name="code" class="easyui-validatebox"
                                   data-options=""/>
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td><input type="text" name="name" class="easyui-validatebox"
                                   data-options=""/></td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
					<tr>
						<td>权限选择</td>
						<td id="functionIds">
							<!-- 动态生成checkbox -->
						</td>
					</tr>
					<tr>
						<td>菜单树</td>
						<td>
							<ul id="functionTree" class="ztree">
								<select id="cc" class="easyui-combotree" style="width:200px;"
                                        data-options="url:'menuAction_listMenuAjax.action',multiple:true"></select>
							</ul>
						</td>
					</tr>
					</table>
			</form>
		</div>
</body>

	<script type="application/javascript">
        $(function () {
            $("#save").click(function () {

                var arr = $("#cc").combotree("getValues");
                $("#menuIds").val(arr);

                $("#roleForm").submit();
            });
        });

	</script>

</html>