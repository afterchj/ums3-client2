<%@page import="com.utils.Constants"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<title>天天锁屏</title>
	<base href="<%=Constants.TOMCAT_SHOW %>" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${resources}/static/bootstrap/2.1.1/css/bootstraped.min.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<div style="text-align: center">
		<i style="font-size: 280px;" class="icon-exclamation-sign"></i>
		<h1>访问链接错误.</h1>
		<div><a href="javascript: jumpByGender('home')">返回首页</a></div>
	</div>
</body>
</html>
