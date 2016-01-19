<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%
	response.setHeader("remember", "true");
%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<base href="${tomcat }" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<title>天天锁屏-diy</title>

<link rel="stylesheet" href="${resources }/static/styles/siteV2.0.css" />
<style>
.diy-top {
	padding-top: 20px;
	width: 100%;
}
</style>
<script src="${tomcat }/js/global.js"></script>
</head>
<body>

	<form  method="get">
		<img src="${resources }/static/images/2.0/DIY.png" class="diy-top">
		<div id="container">
			<div id="dk_game_slider"></div>

			<div class="content ajax-wrap">
				<ul class="v-list J_ajaxWrap">
					<c:forEach items="${fileStoreInfos }" var="fileStoreInfo">
						<%@include file="home-list.jsp"%>
					</c:forEach>
				</ul>
				<div class="footer">
					<div class="J_scrollLoadMore load-btn click-state"
						data-api="app/g/diy/p"></div>
					<a href="javascript:void(0)" class="go-top"></a>
				</div>
				<%@include file="home-banner.jsp"%>
			</div>


		</div>

	</form>
	<script src="${resources }/static/zepto/zepto.min.js"></script>
	<script src="${resources }/static/zepto/gameall.min.js"></script>
	<%-- <script src="${resources }/static/zepto/android.js"></script> --%>
	<script src="${tomcat }/static/zepto/android.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							$("#banner")
									.attr("href",
											"${ums2}/log/redirect?url=http://54631.mmb.cn/wap/touch/channel/clothes/index.jsp");
							$("#banner > img")
									.attr("src",
											"${resources}/static/images/2.0/ad/tpqz.jpg");
							var bars = ${bars}
							;
							$.ui.slider('#dk_game_slider', {
								index : bars.rand(),
								showArr : false,
								autoPlayTime : 2500,
								content : bars
							});
						});
	</script>
</body>
</html>