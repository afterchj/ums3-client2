<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<base href="${tomcat }">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1, maximum-scale=1">
<title>${title }</title>
<link rel="stylesheet" href="css/front.css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<style>
.tab {
	width: 100%;
	background-image: url(static/images/fl/hot.png);
	background-repeat: no-repeat;
	background-size: 100% 100%;
}
</style>
</head>

<body>
	<div class="container_hot">
		<div class="tab">
			<div class="tab_item" onclick="javascrpit: jumpByGender('category/cid${categoryId}/newest')">最新</div>
			<div class="tab_item" onclick="javascrpit: jumpByGender('category/cid${categoryId}/hottest')">最热</div>
		</div>
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
	var cid = ${categoryId};//当前页面cid
	var data = {
			"bbars" : '${bbars}',
			"fileStoreInfos" : ${fileStoreInfos},
			"tbars" : '${tbars}'
	};

	$(document).ready(function(e) {
		cw = $(window).width();
		//cw = 480;
		$(".tab").css("height", cw * 46 / 480);
		$(".tab").css("margin-bottom", cw * 10 / 480);
		$(".tab_item").css("font-size", cw * 18 / 480);
		$(".tab_item").css("line-height", cw * 46 / 480 + 'px');
		addContainerList();
	});

	function addContainerList() {
		initList('${resources}');
		$(".wrapper").css("padding-left", cw * 6 / 480);
		$(".wrapper").css("padding-right", cw * 6 / 480);
		$(".thumb").css("height", cw * 280 / 480);
		$(".thumb p").css("font-size", cw * 18 / 480);
		$(".thumb p").css("line-height", cw * 40 / 480 + 'px');
	}

	$(document).scroll(
			function() {
				//console.log($("#containers").height()-$(window).height());
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

