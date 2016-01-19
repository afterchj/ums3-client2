<%@ page contentType="text/html;charset=UTF-8"%>
<li>
    <div class="icon">
        <img src="${resources}/files/${fileStoreInfo.theme.iconPath}" onerror="this.src='${resources}/static/images/default.png'">
    </div>
    <div class="y-split"></div>
    <div class="info">
        <p class="title">${fileStoreInfo.title}</p>
        <p class="txt">${fileStoreInfo.shortDescription}</p>
        <div class="y-split right"></div>
        <div class="down-btn">
            <a href="javascript:void(0)" onclick="goDownload('${fileStoreInfo.theme.id}','${fileStoreInfo.theme.downloadURL}');">
                <img src="${resources}/static/images/2.0/down.png">
                <span>下载</s:text></span>
            </a>
        </div>
    </div>
    <c:choose>
		<c:when test="${fileStoreInfo.theme.ishot == 1 }">
			<span class="icon_n"></span>
		</c:when>
		<c:when test="${fileStoreInfo.theme.isnew == 1 }">
			<span class="icon_n icon_n_new"></span>
		</c:when>
	</c:choose> 
    <a onclick="jumpByGender('details/id${fileStoreInfo.theme.id}')" href="javascript:void(0)" class="down-area"></a>
</li>