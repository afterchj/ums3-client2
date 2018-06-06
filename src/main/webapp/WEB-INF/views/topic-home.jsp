<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<base href="${tomcat }">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="壁纸 主题 手机主题 主题下载 锁屏 天天锁屏 天天锁屏商店 手机美化 解锁" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1, maximum-scale=1">
<title>专题区</title>
<link rel="stylesheet" href="css/front.css">
<script type="text/javascript" src="js/zepto.min.js"></script>
<script type="text/javascript" src="js/zeptogame.js"></script>
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
	<div class="ads ads_t">
		<div class="ads_bars">
			<div id="dk_game_slider" class="ui-slider">
				<div class="ui-slider-wheel">
					<div class="ui-slider-group"></div>
				</div>
				<p class="ui-slider-items-dots"></p>
			</div>
		</div>
	</div>
	<div style="height: 5%; width: 100%" id="empty"></div>
</body>

<script>
	var cw;
	var bars = [];
	var data = {
		"topics" : ${topics},
		"tbars" : ${tbars}
	};

	$(document).ready(function(e) {
		cw = $(window).width();
		var ad = eval(data.tbars);
		for (i = 0; i < ad.length; i++) {
			var obj = new Object;
			obj.pic = ad[i].pic;
			obj.href = ad[i].href;
			obj.ext = ad[i].ext;
			bars[i] = obj;
		}
		$.ui.slider('#dk_game_slider', {
			index : bars.rand(),
			showArr : false,
			autoPlayTime : 2500,
			content : bars
		});
		addContainerList();
	});

	function addContainerList() {
		var apps = null;
		apps = eval(data.topics);
		for (var i = 0; i < apps.length; i++) {
			var vAds = document.createElement("div");
			var vLink = document.createElement("a");
			var vAds_bar = document.createElement("div");
			var vTitle = document.createElement("div");
			var vName = document.createElement("div");
			var vDate = document.createElement("div");
			var vImg = document.createElement("img");
			var vWordBar = document.createElement("p");

			vAds.setAttribute("class", "ads");
			var linkLog = "app/f/topic/tid"
					+ apps[i].id;
			$(vLink).attr("href", linkLog);

			vAds_bar.setAttribute("class", "ads_bar");

			vTitle.setAttribute("class", "title");
			vName.setAttribute("class", "name");
			vDate.setAttribute("class", "date");

			vName.innerHTML = apps[i].name;
			vDate.innerHTML = apps[i].editDate;
			vImg.src = "${resources}/files/"
					+ apps[i].icon;

			vWordBar.innerHTML = apps[i].description;
			vWordBar.innerHTML = apps[i].description.substring(0,46);
			vWordBar.innerHTML = vWordBar.innerHTML +'......'

			vTitle.appendChild(vName);
			vTitle.appendChild(vDate);

			vAds_bar.appendChild(vTitle);
			vAds_bar.appendChild(vImg);
			vAds_bar.appendChild(vWordBar);

			vLink.appendChild(vAds_bar);

			vAds.appendChild(vLink);

			var vAddContainers = document.getElementById("empty");
			$(vAddContainers).before(vAds);
		}
		$(".ads").css("height", cw * 250 / 480);
		$(".ads").css("padding-top", cw * 10 / 480);
		$(".ads").css("padding-left", cw * 10 / 480);
		$(".ads").css("padding-right", cw * 10 / 480);
		$(".ads_bar p").css("margin-top", cw * 10 / 480);
		$(".ads_bar p").css("margin-left", cw * 10 / 480);
		$(".ads_bar p").css("font-size", cw * 17 / 480);
		$(".ads_bar p").css("line-height", cw * 22 / 480 + 'px');
		$(".ads_t").css("height", cw * 120 / 480);
		$(".ads_t").css("padding-bottom", '0px');
		$(".ads_bars").css("padding-top", cw * 6 / 480);
		$(".ads_bars").css("padding-bottom", cw * 6 / 480 + 2);
		$(".ads_bars").css("height", cw * 105 / 480);
		$(".title").css("height", cw * 40 / 480);
		$(".title").css("line-height", cw * 40 / 480 + 'px');
		$(".title").css("margin-top", cw * 5 / 480);
		$(".name").css("font-size", cw * 20 / 480);
		$(".date").css("font-size", cw * 14 / 480);

	}
</script>
</html>
