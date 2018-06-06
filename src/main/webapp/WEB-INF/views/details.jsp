<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<base href="${tomcat }" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<title>天天锁屏-商店详细</title>
<link rel="stylesheet" href="${resources}/static/styles/siteV2.0.css" />
<script src="${tomcat }/js/global.js"></script>
<style>
body {
	background: #fff;
}

.more {
	padding: 0;
}

footer {
	background: #e1e1e1;
	padding-bottom: 10px;
}

footer a {
	display: block;
}

footer img {
	width: 92%;
	height: auto;
}

footer li {
	text-align: center;
}

footer li span {
	display: block;
	margin: 10px 0;
}

footer ul:first-child span {
	border-right: 1px solid #a1a1a1;
}
</style>
</head>
<body>
	<nav class="details">
		<a href="javascript:void(0)" onclick="history.back();return false;">
			<img src="${resources }/static/images/2.0/back.png"> </a> 
		<a>${current.title}</a>
		<a onclick="jumpByGender('home')" href="javascript:void(0)">
			<img src="${resources }/static/images/2.0/home.png"> </a>
	</nav>
	<section id="container">
		<div class="preview">

			<img src="${resources }/files/${current.theme.preWebPath}">
			<c:if test="${current.theme.preClientPath!=null }">
				<img src="${resources}/files/${current.theme.preClientPath}">
			</c:if>

		</div>
		<div class="description soft-info">
			<div class="size introduce J_info">
				<span>大小: 
				<c:choose>
					<c:when test="current.theme.apkSize!=null">
						  ${fn:substring(current.theme.apkSize/1024/1024,0,4)}
					</c:when>
					<c:otherwise>
						${fn:substring(current.theme.uxSize/1024/1024,0,4)}
					</c:otherwise>
				</c:choose>
				 M
				</span>
				<c:if test="${current.theme.dtype!=2 }">
				
					<!-- 需要通过ajax获得 -->
					<span id="down" style="text-align: right">
					</span>
				</c:if>
				<p>
					设计师
					: ${current.author}
				</p>

				<p>
					简介
					: ${current.longDescription}
				</p>
			</div>
			<span class="toggle-btn"></span>
		</div>
		<div class="btns">
			<a onclick="jumpByGender('details/id${current.theme.id}/o${current.offset - 1 }')" href="javascript:void(0)">
				<img class="offset-r"
				src="${resources }/static/images/2.0/offset-left.png">
			</a> 
			<a id="download" href="javascript:void(0)"> <span 
				class="more-btn"><img
					src="${resources }/static/images/2.0/down-split.png"> <span
					style="display: block; margin-left: 15px;">免费下载</span> </span>
			</a> 
				<a onclick="jumpByGender('details/id${current.theme.id}/o${current.offset + 1 }')" href="javascript:void(0)">
				<img class="offset-r"
				src="${resources }/static/images/2.0/offset-right.png">
			</a>
		</div>
	</section>
	<section class="description more">
		<h3>猜你喜欢</h3>

		<div class="content ajax-wrap">

			<ul class="v-list J_ajaxWrap">

				<li>
					<div class="icon">
						<img src="${resources }/files/${appStoreInfo.theme.iconPath}" onerror="this.src='${resources}/static/images/default.png'">
					</div>
					<div class="y-split"></div>
					<div class="info">
						<p class="title">${appStoreInfo.title}</p>

						<p class="txt">${appStoreInfo.shortDescription}</p>

						<div class="down-btn">

							<img src="${resources }/static/images/2.0/down.png"> <span>
							下载 </span>

						</div>
					</div> 
					<c:choose>
						<c:when test="${appStoreInfo.theme.ishot == 1 }">
							<span class="icon_n"></span>
						</c:when>
						<c:when test="${appStoreInfo.theme.isnew == 1 }">
							<span class="icon_n icon_n_new"></span>
						</c:when>
					</c:choose>
					<a href="javascript:void(0)"
					onclick="goDownload('${appStoreInfo.theme.id}','${appStoreInfo.theme.downloadURL}');"
					class="down-area"></a>
				</li>

				<li>
					<div class="icon">
						<img src="${resources }/files/${gameStoreInfo.theme.iconPath}" onerror="this.src='${resources}/static/images/default.png'">
					</div>
					<div class="y-split"></div>
					<div class="info">
						<p class="title">${gameStoreInfo.title}</p>
						<p class="txt">${gameStoreInfo.shortDescription}</p>
						<div class="down-btn">
							<img src="${resources }/static/images/2.0/down.png"> <span>
							下载 </span>
						</div>
					</div> 
					<c:choose>
						<c:when test="${gameStoreInfo.theme.ishot == 1 }">
							<span class="icon_n"></span>
						</c:when>
						<c:when test="${gameStoreInfo.theme.isnew == 1 }">
							<span class="icon_n icon_n_new"></span>
						</c:when>
					</c:choose>
					
					<a href="javascript: void(0)"
					onclick="goDownload('${gameStoreInfo.theme.id}','${gameStoreInfo.theme.downloadURL}');"
					class="down-area"></a>
				</li>

			</ul>
		</div>
		<div class="webkit-box" style="margin: 10px auto;">
			<span></span> <span class="more-btn"> 
				<a onclick="jumpByGender('store/game')" href="javascript:void(0)">更多精彩</a>
				<div class="more-split"></div>
			</span>
		</div>
	</section>
	<footer class="webkit-box">
		<ul id="ad1" class="webkit-box1">
			<a style="color: #19b400"
				href="${ums2}/log/redirect?url=http://ytao.cn/index.php?c=Firsttp_tpa=Indextp_tpid=143361tp_tpfid=9826tp_tpchannel=1317287">
				<li><img style="border: 4px solid #ffffff; margin: 10px 0 0;"
					src="${resources }/static/images/2.0/ad/detail/nvytlyq.jpg">
			</li>
				<li><span>时髦女郎低至四折</span></li>
			</a>
		</ul>
		<ul id="ad2" class="webkit-box1">
			<a style="color: #ff0000"
				href="${ums2}/log/redirect?url=http://ytao.cn/index.php?c=Goodstp_tpa=Indextp_tpgid=9967tp_tpchannel=1236619">
				<li><img style="border: 4px solid #ffffff; margin: 10px 0 0;"
					src="${resources }/static/images/2.0/ad/detail/yt4s.jpg"></li>
				<li><span>iphone手机599元</span></li>
			</a>
		</ul>
	</footer>
	<script src="${resources }/static/zepto/zepto.min.js"></script>
	<script src="${resources }/static/zepto/android.js"></script>
	<script>
		$(function() {
			$("#download")
					.click(
							function() {
								$("#download > span").addClass(
										"more-btn hasDown");
								$
										.ajax({
											type : "POST",
											url : appendQueryString("info/${gender}/appDownload.json"),
											dataType : "text",
											data : {
												id: '${current.theme.id}',
												queryString : '${current.theme.downloadURL}',
												cs : getQueryStringUrl(),
												name:'${current.theme.name}'
											},
											complete : function() {
												location.href = '${current.theme.downloadURL}';
											}
										});
							});
			$
			.ajax({
				type : "POST",
				url : appendQueryString("info/${gender}/details.json"),
				dataType : "json",
				data : {
					id: '${current.theme.id}',
					queryString : '${current.theme.downloadURL}',
					name:'${current.theme.name}'
				},
				success: function(msg){
					if(msg.totalDownload != undefined){
						$("#down").html('下载 :&nbsp;' + msg.totalDownload);
					}
				}
			});
			var app = getGender();
			if (app === 'f') {
				$("#ad1 a")
						.attr("href",
								"${ums2}/log/redirect?url=http://54623.mmb.cn/wap/touch/channel/clothes/index.jsp");
				$("#ad1  img")
						.attr("src",
								"${resources}/static/images/2.0/ad/detail/nvzhuang.jpg");
				$("#ad1 span").text("秋装热卖开抢啦");
				$("#ad2 a")
						.attr("href",
								"${ums2}/log/redirect?url=http://54624.mmb.cn/wap/touch/groupRate.do?type=2");
				$("#ad2  img").attr("src",
						"${resources}/static/images/2.0/ad/detail/xsqg.jpg");
				$("#ad2 span").text("限时抢购全场包邮");
			} else if (app === 'm') {
				$("#ad1 a")
						.attr(
								"href",
								"${ums2}/log/redirect?url=http://ytao.cn/index.php?c=Goodstp_tpa=Indextp_tpgid=9967tp_tpchannel=1236619");
				$("#ad1  img").attr("src",
						"${resources}/static/images/2.0/ad/detail/yt4s.jpg");
				$("#ad1 span").text("iphone手机599元");
				$("#ad2 a")
						.attr("href",
								"${ums2}/log/redirect?url=http://ytao.cn/?id=141061tp_tpchannel=1236619");
				$("#ad2  img").attr("src",
						"${resources}/static/images/2.0/ad/detail/nvytmi2.jpg");
				$("#ad2 span").text("发烧机小Mi2走起");
			}
			var fm = getQueryString('fm');
			if (fm == 'cn.goapk.market') {
				$(".description.more").hide();
				$("footer").hide();
			}
		});
	</script>
</body>
</html>