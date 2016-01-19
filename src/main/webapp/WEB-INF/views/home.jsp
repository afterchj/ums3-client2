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
<script type="text/javascript" src="${tomcat }/js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" href="${tomcat }/css/front.css">
<script src="${tomcat }/js/global.js"></script>
</head>

<body class="BorderImage">
	<div id="containers">
		<div class="ads">
			<div class="ads_bar">
				<div class="ads_item ads_item_1" id="ads_t1">
					<a href="#"></a>
				</div>
				<div class="ads_item ads_item_2" id="ads_t2">
					<a href="#"></a>
				</div>
				<div class="ads_item ads_item_3" id="ads_t3">
					<a href="#"></a>
				</div>
				<div class="ads_item ads_item_4" id="ads_t4">
					<a href="#"></a>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="tab">
				<c:choose>
					<c:when test="${gender == 'm' }">
						<div class="normal boy_s">
							<a href="app/m/home"><p class="boy">男生专区</p></a>
						</div>
						<div class="normal girl_n">
							<a href="app/f/home"><p class="girl">女生专区</p></a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="normal boy_n">
							<a href="app/m/home"><p class="boy">男生专区</p></a>
						</div>
						<div class="normal girl_s">
							<a href="app/f/home"><p class="girl">女生专区</p></a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="wrapper">
				<div id="start" class="ui-refresh-up">
					<span id="Sicon" class="ui-refresh-icon"></span> <span id="Stitle"
						class="ui-refresh-label">加载更多</span>
				</div>
			</div>
		</div>
		<div class="ads" id="bottomBar" style="display: none;border-top:#e2e2e2 solid 1px;">
			<div class="ads_bar">
				<div class="ads_item ads_item_1" id="ads_b1">
					<a href="#"></a>
				</div>
				<div class="ads_item ads_item_2" id="ads_b2">
					<a href="#"></a>
				</div>
				<div class="ads_item ads_item_3" id="ads_b3">
					<a href="#"></a>
				</div>
				<div class="ads_item ads_item_4" id="ads_b4">
					<a href="#"></a>
				</div>
			</div>
		</div>
		<div id="more" class="more" style="display: none;">
			<a href="javascript:jumpByGender('topic')">更多专题</a>
		</div>
		<div style="height: 5%; width: 100%"></div>
	</div>
</body>


<script>
var cw;
var loading = 0;
var number = 0;
var loadingMore = 0;
var data={'bbars':'${bbars}', "fileStoreInfos" : ${fileStoreInfos}, 'tbars':'${tbars}'};
$(document).ready(function(e) {
	cw = $(window).width();
	//cw = 480;
	$(".ads").css("height",cw*200/480);
	$(".ads").css("padding-top",cw*7/480);
	$(".ads").css("padding-bottom",cw*7/480+2);
	$(".ads").css("padding-left",cw*12/480);
	$(".ads").css("padding-right",cw*12/480);
	$(".tab").css("height",cw*72/480);
	$(".tab").css("width",cw*450/480);
	$(".tab").css("left",cw*12/480);
	$(".girl_n").css("left",cw*233/480);
	$(".girl_s").css("left",cw*233/480);
	$(".normal").css("width",cw*224/480);
	$(".tab p").css("font-size",cw*22/480);
	$(".tab p").css("line-height",$(".tab").height()+2+'px');
	$(".more").css("padding-left",cw*10/480);
	$(".more").css("padding-right",cw*10/480);
	$(".more").css("height",cw*57/480);
	$(".more a").css("font-size",cw*25/480);
	$(".more a").css("line-height",cw*57/480+'px');
	touch('more');
	topBar();
	addContainerList();
});

function addContainerList(){
	var apps=null;
	var dataF = 9*(number+1);
	apps = eval(data);
	if(apps.fileStoreInfos.length>9*(number+1)){
		dataF = 9*(number+1);
	}else{
		dataF = parseInt(apps.fileStoreInfos.length/3)*3;
		loadingMore = 1;
	}
	for(var i=number*9;i<dataF;i++){
		var vThumb=document.createElement("div");
		var vLink = document.createElement("div");
		var vImg=document.createElement("img");
		var vWordBar=document.createElement("p");
		vThumb.setAttribute("class","thumb");
		$(vLink).attr("onclick","details("+apps.fileStoreInfos[i].theme.id+")");
		vImg.src="${resources}/files/"+apps.fileStoreInfos[i].theme.iconPath;
		
		$(vImg).bind("error",function(){ 
			this.src="${tomcat}/static/images/jp/default.jpg"; 
		}); 
		vWordBar.innerHTML=apps.fileStoreInfos[i].theme.title;
		vLink.appendChild(vImg);
		vLink.appendChild(vWordBar);
		vThumb.appendChild(vLink);
		var vAddContainers=document.getElementById("start");
		$(vAddContainers).before(vThumb);
	}
	$(".wrapper").css("padding-left",cw*6/480);
	$(".wrapper").css("padding-right",cw*6/480);
	$(".thumb").css("height",cw*280/480);
	$(".thumb p").css("font-size",cw*18/480);
	$(".thumb p").css("line-height",cw*40/480+'px');
	if(apps.fileStoreInfos.length<=9*(number+1)){
		$("#bottomBar").css("display","block");
		$("#more").css("display","block");
	}
}

$(document).scroll(function(){
	if($(document).scrollTop()>=$("#containers").height()-$(window).height()-50 && loading==0 && loadingMore == 0){
		loading = 1;
		$("#Sicon").removeClass();
		$("#Sicon").addClass('ui-loading');
		 $("#Stitle").html("正在加载");
		setTimeout(function(){
			number++;
			addContainerList();
			loading=0;
			$("#Sicon").removeClass();
			$("#Sicon").addClass('ui-refresh-icon');
		 	$("#Stitle").html("加载更多");
			if(loadingMore == 1){
				$("#Sicon").removeClass();
				$("#Stitle").html("");
			}
		},1000);
	}
});


function topBar(){
	for(var i=1;i<=4;i++){
		if(JSON.parse(data.tbars)[i-1] !=undefined){
			$("#ads_t"+i).css("background-image","url("+JSON.parse(data.tbars)[i-1].pic+")");
			$("#ads_t"+i+" a").attr("href",JSON.parse(data.tbars)[i-1].href);
		}
		if(JSON.parse(data.bbars)[i-1] !=undefined){
			$("#ads_b"+i).css("background-image","url("+JSON.parse(data.bbars)[i-1].pic+")");
			$("#ads_b"+i+" a").attr("href",JSON.parse(data.bbars)[i-1].href);
		}
	}
}

function touch(id){
	var started=false;
	var startEvent=null;
	function handlePointerEventStart(event){
		startEvent=event;
		if(typeof event.preventDefault!='undefined'){
			//event.preventDefault();
		}
		if(typeof event.touches!='undefined'){
			lastX=event.touches[0].pageX;
			lastY=event.touches[0].pageY;
		    
		}else if(typeof event.x!='undefined'){
			lastX=event.x;
			lastY=event.y;
		} 
		started=true;
		if(id=='more'){
			$(".more a").css("background-image","url(${tomcat}/static/images/jp/more_1.png)");
		}
		
	}
	
	function hanldePointerEventMove(event){	
		if(!started){
			return;
		}
		if(typeof event.preventDefault!='undefined'){
		//	event.preventDefault();
		}

		if(typeof event.touches=='undefined' && typeof event.x=='undefined'){
			return;
		}
		var dx,dy;
		if(typeof event.touches!='undefined'){
		  dx= event.touches[0].pageX-lastX;
		  dy= event.touches[0].pageY-lastY;
		}else if(typeof event.x!='undefined'){
		  dx = event.x-lastX;
		  dy = event.y-lastY;
		}else{
			return;	
						
		}
		if(dx>=30 || dx<=-30 || dy>=30 || dy<=-30)
		{
			if(id=='more'){
				$(".more a").css("background-image","url(${tomcat}/static/images/jp/more.png)");
			}
		}

	}
	
		
	function handlePointerEventEnd(event){
		started=false;
		if(typeof event.preventDefault!='undefined'){
			//event.preventDefault();
		}
		$(".more a").css("background-image","url(${tomcat}/static/images/jp/more.png)");
	}
			/*document.getElementById(id).addEventListener('mouseup',handlePointerEventEnd);
			document.getElementById(id).addEventListener('mousemove',hanldePointerEventMove);
			document.getElementById(id).addEventListener('mousedown',handlePointerEventStart);/**/
			document.getElementById(id).addEventListener('touchstart',handlePointerEventStart);
			document.getElementById(id).addEventListener('touchmove',hanldePointerEventMove);
			document.getElementById(id).addEventListener('touchend',handlePointerEventEnd);

}
</script>
</html>

