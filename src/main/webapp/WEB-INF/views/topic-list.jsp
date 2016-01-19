<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<base href="${tomcat }">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="壁纸 主题 手机主题 主题下载 锁屏 天天锁屏 天天锁屏商店 手机美化 解锁" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1, maximum-scale=1">
<title>${title }</title>
<link rel="stylesheet" href="css/front.css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<style>
.ads_bar {
	text-align: center;
	border: #e2e2e2 solid 1px;
}

.ads_bar img {
	width: 95%;
}
</style>
</head>

<body class="BorderImage">
	<div class="ads">
		<div class="ads_bar">
			<img id="topicImg" src="">
			<p id="word"></p>
		</div>
	</div>
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
	var tid = 0;//当前页面cid
	var data = {
		"topic" : ${topic},
		"fileStoreInfos" : ${fileStoreInfos}
	};

	$(document)
			.ready(
					function(e) {
						cw = $(window).width();
						//cw = 480;
						$(".ads").css("height", cw * 220 / 480);
						$(".ads").css("padding-top", cw * 10 / 480);
						$(".ads").css("padding-bottom", cw * 10 / 480 + 2);
						$(".ads").css("padding-left", cw * 10 / 480);
						$(".ads").css("padding-right", cw * 10 / 480);
						$(".ads_bar img").css("margin-top", cw * 12 / 480);
						$(".ads_bar p").css("margin-top", cw * 8 / 480);
						$(".ads_bar p").css("margin-left", cw * 8 / 480);
						$(".ads_bar p").css("font-size", cw * 16 / 480);
						$(".ads_bar p")
								.css("line-height", cw * 20 / 480 + 'px');
						$(".tab").css("height", cw * 62 / 480);
						$(".tab p").css("font-size", cw * 20 / 480);
						$(".tab p").css("line-height",
								$(".tab").height() + 'px');
						var topic = eval(data);
						document.getElementById('topicImg').src = "${resources}/files/"
								+ topic.topic.icon;
						document.getElementById('word').innerHTML = topic.topic.description;
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
