<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
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
<script type="text/javascript" src="js/global.js"></script>
</head>

<body class="BorderImage">
	<div class="container_hot">
		<div class="wrapper">
			<div id="start" class="ui-refresh-up">
				<span id="Sicon" class="ui-refresh-icon"></span> <span id="Stitle"
					class="ui-refresh-label">加载更多</span>
			</div>
		</div>

	</div>
	<div style="height: 5%; width: 100%"></div>
</body>


<script>
	var cw;
	var loading = 0;
	var number = 0;
	var loadingMore = 0;
	var page = 1;
	var cid = 0;//当前页面cid
	var data = {
		"bbars" : "${bbars}",
		"fileStoreInfos" : ${fileStoreInfos},
		"tbars" : "${tbars}"
	};

	$(document).ready(function(e) {
		cw = $(window).width();
		addContainerList();
	});

	function addContainerList() {
		initList('${resources}');
		$(".wrapper").css("padding-left", cw * 6 / 480);
		$(".wrapper").css("padding-right", cw * 6 / 480);
		$(".wrapper").css("padding-top", cw * 12 / 480);
		$(".thumb").css("height", cw * 280 / 480);
		$(".thumb p").css("font-size", cw * 18 / 480);
		$(".thumb p").css("line-height", cw * 40 / 480 + 'px');
	}

	$(document).scroll(
			function() {
				if ($(document).scrollTop() >= $("#containers").height()
						- $(window).height() - 50
						&& loading == 0 && loadingMore == 0) {
					loading = 1;
					$("#Sicon").removeClass();
					$("#Sicon").addClass('ui-loading');
					$("#Stitle").html("正在加载");
					setTimeout(function() {
						number++;
						addContainerList();
						loading = 0;
						$("#Sicon").removeClass();
						$("#Sicon").addClass('ui-refresh-icon');
						$("#Stitle").html("加载更多");
						if (loadingMore == 1) {
							$("#Sicon").removeClass();
							$("#Sicon").addClass('ui-refresh-icon');
							$("#Stitle").html("已经到底");
						}
					}, 1000);
				}
			});
</script>
</html>
