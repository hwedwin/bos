<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>跳转到主页</title>
</head>
<body>
<!-- 这里的index.jsp并不是真正的主页， 系统的所有页面都放在 WEB-INF/pages下，这样用户就无法对这些页面直接访问
 访问WEB-INF下页面，必须通过Action转发后访问，详见 struts.xml配置，也方便后期通过struts2拦截器 进行权限控制 -->
<jsp:forward page="/page_common_index.action"></jsp:forward>
</body>
</html>