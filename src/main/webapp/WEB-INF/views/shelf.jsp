<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%
	response.setHeader("remember", "true");
%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<base href="${tomcat }" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<title>天天锁屏-货架</title>
<link rel="stylesheet" href="${resources }/static/styles/siteV2.0.css" />
<style>
.nav_second_level {
	background: url(${resources}/static/images/2.0/game-selected.png)
		no-repeat;
	background-size: 100% 100%;
}

.nav_second_level a {
	padding-top: 4px;
}

.app {
	background: url(${resources}/static/images/2.0/app-selected.png)
		no-repeat;
	background-size: 100% 100%;
}

.v-list .down-area {
	right: 80px;
}
</style>
<script src="${tomcat }/js/global.js"></script>
</head>
<body>

	<form action="home.action" method="get">
		<%@include file="shelf-nav.jsp"%>
		<div id="container">
			<div class="content ajax-wrap">
				<ul class="v-list J_ajaxWrap">
					<c:forEach items="${fileStoreInfos }" var="fileStoreInfo">
						<%@include file="home-list.jsp"%>
					</c:forEach>
				</ul>
				<div class="footer">
					<div class="J_scrollLoadMore load-btn click-state"
						data-api="app/${gender }/shelf/p"></div>
					<a href="javascript:void(0)" class="go-top"></a>
				</div>
				<%@include file="home-banner.jsp"%>
			</div>
		</div>

	</form>
	<script src="${resources }/static/zepto/zepto.min.js"></script>
	<%-- <script src="${resources }/static/zepto/android.js"></script> --%>
	<script src="${tomcat }/static/zepto/android.js"></script>
	<script>
		$(function() {

			var reg = function(name) {
				var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)",
						"i");
				if (reg.test(location.href))
					return decodeURIComponent(RegExp.$2.replace(/\+/g, " "));
				return "";
			};

			if (reg("sf") == 'game' || location.href.indexOf("game") > 0) {
				$("#nav_second_level").addClass("nav_second_level");
				$("#banner")
						.attr("href",
								"${ums2}/log/redirect?url=http://ytao.cn/?id=141061tp_tpchannel=1236619");
				$("#banner > img").attr("src",
						"${resources}/static/images/2.0/ad/ytMi2p.jpg");
			} else {
				$("#nav_second_level").addClass("app");
				$("#banner")
						.attr("href",
								"${ums2}/log/redirect?url=http://ytao.cn/index.php?id=66457tp_tpchannel=1236615");
				$("#banner > img").attr("src",
						"${resources}/static/images/2.0/ad/ytxjlyq.jpg");
			}
			$("#content1").live("click", function() {
				$(this).css("backgroundColor", "#e7e6c8");
			});

		});
	</script>
</body>
</html>