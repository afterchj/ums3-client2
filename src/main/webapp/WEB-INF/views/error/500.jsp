<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.utils.Constants"%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	response.setStatus(200);
%>

<%
	Throwable ex = exception;
	if (request.getAttribute("exception") != null) {
		ex = (Throwable) request.getAttribute("exception");
	} else if (request.getAttribute("javax.servlet.error.exception") != null) {
		ex = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
	}

	//记录日志
	if (ex != null) {
		Logger logger = Logger.getLogger("500.jsp");
		logger.error(ex.getMessage(), ex);
	}
%>
{"result" : "200"}
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>天天锁屏</title>
<base href="${tomcat }" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="${resources}/static/bootstrap/2.1.1/css/bootstraped.min.css"
	type="text/css" rel="stylesheet" />
<script src="${tomcat }/js/global.js"></script>
</head>

<body>
	<div style="text-align: center">
		<i style="font-size: 280px;" class="icon-wrench"></i>
		<h1>服务器开小差了!&nbsp;&nbsp;<a href="javascript: jumpByGender('home')">返回</a></h1>
	</div>
</body>
</html> --%>
