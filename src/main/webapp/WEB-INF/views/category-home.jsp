<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%
	response.setHeader("remember", "true");
%>
<html>
<head>
<base href="${tomcat }">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="壁纸 主题 手机主题 主题下载 锁屏 天天锁屏 天天锁屏商店 手机美化 解锁" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1, maximum-scale=1">
<title>天天锁屏</title>
<link rel="stylesheet" href="css/front.css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
</head>

<body class="BorderImage1">
	<div class="wrapper" id="wrapper"></div>
	<div style="height: 5%; width: 100%; clear: both"></div>
</body>


<script>
	var cw;
	var data = {
		"categories" : ${categories}
	};

	$(document).ready(function(e) {
		cw = $(window).width();
		//cw = 480;

		addContainerList();
	});

	function addContainerList() {

		var apps = null;
		apps = eval(data.categories);
		console.log(apps.length);
		for (i = 0; i < apps.length; i++) {
			var vThumb = document.createElement("div");
			var vLink = document.createElement("a");
			var vImg = document.createElement("img");
			vThumb.setAttribute("class", "thumb1");
			var linkLog = "app/f/category/cid"
					+ apps[i].id + "/newest";
			$(vLink).attr("href", linkLog);

			vImg.src = "${resources}/files/"
					+ apps[i].icon;

			vLink.appendChild(vImg);

			vThumb.appendChild(vLink);

			var vAddContainers = document.getElementById("wrapper");
			vAddContainers.appendChild(vThumb);

			$(".wrapper").css("padding-left", cw * 4 / 480);
			$(".wrapper").css("padding-right", cw * 4 / 480);
			$(".wrapper").css("padding-top", cw * 10 / 480);
			$(".thumb1").css("height", cw * 153 / 480);
		}
	};
</script>
</html>
