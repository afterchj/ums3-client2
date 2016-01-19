function getQueryString(name){
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
  var r = window.location.search.substr(1).match(reg);
  if (r != null)
  {
   return unescape(r[2]);
  }
  return null;
}

function getQueryStringUrl() {
	var url = location.search.split("?");
	return url[1];
}

function jump(href){
	var qs = getQueryStringUrl();
	if(qs != undefined){
		href += "?" + qs;
	}
	window.location.href = href;
}

function appendQueryString(href){
	var qs = getQueryStringUrl();
	if(qs != undefined){
		href += "?" + qs;
	}
	return href;
}

function jumpByGender(href){
	jump('app/' + getGender() + '/' + href);
}

function getGender(){
	var url = window.location.href;
	if(url.indexOf("\/m\/") > 0){
		return "m";
	}else if(url.indexOf("\/f\/") > 0){
		return "f";
	}else if(getQueryString('g') === 'male'){
		return "m";
	}else {
		return "f";
	}
}

function details(id){
	jumpByGender("details/id" + id + ".json?usenative=true");
}

function pageMoreAjax(){
	$.ajax({
		type : 'get',
		url : window.location.href + '/p' + page + '.json',
		dataType : 'text',
		success : function(json) {//success				 
			if (typeof json != 'undefined') {
				var result = JSON.parse(json);
				if (result.code != 200) {
					loadingMore = 1;
					$("#Sicon").removeClass();
					$("#Sicon").addClass('ui-refresh-icon');
					$("#Stitle").html("已经到底");
				} else {
					number = 0;
					page++;
					data["fileStoreInfos"] = result.data;
					addContainerList();
				}
			}
		},
		error : function() {
			$("#Sicon").removeClass();
			$("#Sicon").addClass('ui-refresh-icon');
			$("#Stitle").html("加载失败");
		},
	});
}

function initList(resources){
	if(page === 1){
		page = 2;
	}
	var apps = null;
	var dataF = 9 * (number + 1);
	apps = eval(data);
	if (apps.fileStoreInfos.length > 9 * (number + 1)) {
		dataF = 9 * (number + 1);
	} else {
		dataF = parseInt(apps.fileStoreInfos.length / 3) * 3;
		pageMoreAjax();
	}
	for (var i = number * 9; i < dataF; i++) {
		var vThumb = document.createElement("div");
		var vLink = document.createElement("a");
		var vImg = document.createElement("img");
		var vWordBar = document.createElement("p");
		vThumb.setAttribute("class", "thumb");
		$(vLink).attr("onclick",
				"details(" + apps.fileStoreInfos[i].theme.id + ")");

		vImg.src = resources + "/files/" + apps.fileStoreInfos[i].theme.iconPath;
		$(vImg).bind("error", function() {
			this.src = "static/images/jp/default.jpg";
		});
		vWordBar.innerHTML = apps.fileStoreInfos[i].theme.title;

		vLink.appendChild(vImg);
		vLink.appendChild(vWordBar);

		vThumb.appendChild(vLink);

		var vAddContainers = document.getElementById("start");
		$(vAddContainers).before(vThumb);
	}
}