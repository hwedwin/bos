<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
 <!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>取派标准</title>
        <!-- 导入jquery核心类库 -->
        <!-- 导入jquery核心类库 -->
<script type="text/javascript"
        src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/outOfBounds.js"
        type="text/javascript"></script>
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
		<script type="text/javascript">
			$(function () {
                // 先将body隐藏，再显示，不会出现页面刷新效果
                $("body").css({visibility: "visible"});

                // 收派标准信息表格
                $('#grid').datagrid({
                    iconCls: 'icon-forward',
                    fit: true,
                    border: false,
                    rownumbers: true,
                    striped: true,
                    pageList: [1, 3],
                    pagination: true,
                    toolbar: toolbar,
                    url: "standardAction_pageQuery.action",
                    idField: 'id',
                    columns: columns,
                    onDblClickRow: function (rowIndex, rowData) {
                        //alert(rowData.name);

                        $("#editStandardWindow").window("open");

                        $("#editStandardForm").form("load", rowData);

                    }
                });
                // 添加取派员窗口
                $('#addStandardWindow').window({
                    title: '取派标准操作',
                    width: 600,
                    modal: true,
                    shadow: true,
                    closed: true,
                    height: 400,
                    resizable: false,
                });

                // 添加取派员窗口
                $('#editStandardWindow').window({
                    title: '取派标准操作',
                    width: 600,
                    modal: true,
                    shadow: true,
                    closed: true,
                    height: 400,
                    resizable: false,
                });
            });

            //工具栏
            var toolbar = [{
                id: 'button-add',
                text: '增加',
                iconCls: 'icon-add',
                handler: function () {
                    $('#addStandardWindow').window("open");
                }
            },
//				{
//                id: 'button-edit',
//                text: '修改',
//                iconCls: 'icon-edit',
//                handler: function () {
//                        $('#editStandardWindow').window("open");
//                        var rows = $("#grid").datagrid("getSelections");
//                }
//            },
                {
                    id: 'button-delete',
                    text: '作废',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        var rows = $("#grid").datagrid("getSelections");
                        var ids = new Array();
                        if (rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                ids.push(rows[i].id);
                            }
                            ids = ids.join(",");
                        }
                        $.post("standardAction_delBatch", {"ids": ids}, function (data) {
//                            if (data) {
//                                $.messager.alert("恭喜!", "作废成功", "info");
//                            } else {
//                                $.messager.alert("遗憾!", "作废失败", "error");
//                            }
                            //立即不刷新重加载
                            $("#grid").datagrid("reload");
                        });


                    }
                }, {
                    id: 'button-restore',
                    text: '还原',
                    iconCls: 'icon-save',
                    handler: function () {
                        var rows = $("#grid").datagrid("getSelections");
                        var ids = new Array();
                        if (rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                ids.push(rows[i].id);
                            }
                            ids = ids.join(",");
                        }
                        $.post("standardAction_backBatch", {"ids": ids}, function (data) {
//                            if (data) {
//                                $.messager.alert("恭喜!", "还原成功", "info");
//                            } else {
//                                $.messager.alert("遗憾!", "还原失败", "error");
//                            }
                            //立即不刷新重加载
                            $("#grid").datagrid("reload");
                        });
                    }
                }];

            // 定义列
            var columns = [[{
                field: 'id',
                checkbox: true
            }, {
                field: 'name',
                title: '标准名称',
                width: 120,
                align: 'center'
            }, {
                field: 'minWeight',
                title: '最小重量',
                width: 120,
                align: 'center'
            }, {
                field: 'maxWeight',
                title: '最大重量',
                width: 120,
                align: 'center'
            }, {
                field: 'minLength',
                title: '最小长度',
                width: 120,
                align: 'center'
            }, {
                field: 'maxLength',
                title: '最大长度',
                width: 120,
                align: 'center'
            }, {
                field: 'operator',
                title: '操作人',
                width: 120,
                align: 'center'
            }, {
                field: 'operationTime',
                title: '操作时间',
                width: 120,
                align: 'center'
            }, {
                field: 'operatorCompany',
                title: '操作单位',
                width: 120,
                align: 'center'
            }, {
                field: 'deltag',
                title: '是否作废',
                width: 120,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return "有效";
                    } else if (value == 0) {
                        return "作废";
                    }
                }
            }]];
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
	<div class="easyui-window" title="对收派标准进行添加" id="addStandardWindow" collapsible="false"
         minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>
		

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStandardForm" method="post"
                  action="${pageContext.request.contextPath }/standardAction_save.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派标准信息</td>
					</tr>
					<tr>
						<td>收派名称</td>
						<td>
						<input type="text" name="name" class="easyui-validatebox"
                               data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>最小重量</td>
						<td>
						   <input type="text" name="minWeight"
                                  class="easyui-numberbox"
                                  data-options="min:1,precision:0,suffix:'kg',required:true"
                           />
						   </td>
					</tr>
					<tr>
						<td>最大重量</td>
						<td><input type="text" name="maxWeight"
                                   class="easyui-numberbox"
                                   data-options="max:500,precision:0,suffix:'kg',required:true"
                        /></td>
					</tr>
					<tr>
						<td>最小长度</td>
						<td><input type="text" name="minLength"
                                   class="easyui-numberbox"
                                   data-options="min:10,precision:0,suffix:'cm',required:true"
                        /></td>
					</tr>
					<tr>
						<td>最大长度</td>
						<td><input type="text" name="maxLength"
                                   class="easyui-numberbox"
                                   data-options="max:1000,precision:0,suffix:'cm',required:true"
                        /></td>
					</tr>
					</table>
			</form>
		</div>

	</div>
	</body>


	<div class="easyui-window" title="对收派标准进行修改" id="editStandardWindow" collapsible="false"
         minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">修改
				</a>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStandardForm" method="post"
                  action="${pageContext.request.contextPath }/standardAction_edit.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派标准信息</td>
					</tr>
					<tr>
						<td>收派名称</td>
						<td>
						<input type="hidden" name="id"/>
						<input type="hidden" name="operator"/>
						<input type="hidden" name="operationTime"/>
						<input type="hidden" name="operatorCompany"/>
						<input type="hidden" name="deltag"/>
						<input type="text" name="name" class="easyui-validatebox"
                               data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>最小重量</td>
						<td>
						   <input type="text" name="minWeight"
                                  class="easyui-numberbox"
                                  data-options="min:1,precision:0,suffix:'kg',required:true"
                           />
						   </td>
					</tr>
					<tr>
						<td>最大重量</td>
						<td><input type="text" name="maxWeight"
                                   class="easyui-numberbox"
                                   data-options="max:500,precision:0,suffix:'kg',required:true"
                        /></td>
					</tr>
					<tr>
						<td>最小长度</td>
						<td><input type="text" name="minLength"
                                   class="easyui-numberbox"
                                   data-options="min:10,precision:0,suffix:'cm',required:true"
                        /></td>
					</tr>
					<tr>
						<td>最大长度</td>
						<td><input type="text" name="maxLength"
                                   class="easyui-numberbox"
                                   data-options="max:1000,precision:0,suffix:'cm',required:true"
                        /></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
    </body>
    <script type="application/javascript">
     $(function () {
         $("#save").click(function () {
             var r = $("#addStandardForm").form("validate");
             if (r) {
                 $("#addStandardForm").submit();
                 $("#addStandardWindow").window("close");
             }
         });


         $("#edit").click(function () {
             var r = $("#editStandardForm").form("validate");
             if (r) {
                 $("#editStandardForm").submit();
                 $("#editStandardWindow").window("close");
             }
         });

     });


</script>
</html>