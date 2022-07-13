<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>
<style type="text/css">
<!--
.downloadLayer {
	position: absolute;
	background: #C0C0C0;
	width: 100%;
	height: 100%;
	border: 0;
	padding: 0;
	margin: 0;
	filter: Alpha(style=0,opacity=70);
	display: none;
	z-index: 1001;
}
.innerDivToLayer {
	position: absolute;
	background: #ffffff;
	border: 0;
	padding: 0;
	margin: 0;
	display: none;
	z-index: 1002;
}
.loadingID {
	padding: 0;
	margin: 0;
	border: 3px;
	border-style: solid;
	border-color: #5F9EA0;
}
-->
</style>
<script language='javascript'>
<!--
	var def_scrollX = document.body.style.overflowX;
	var def_scrollY = document.body.style.overflowY;

	function doOnOffLayer(disFlag) {
		var disTarget = document.getElementById("downloadLayer");
		var innerTarget = document.getElementById("innerDivToLayer");

		if(disFlag != false) {
			disTarget.style.display = "block";
			innerTarget.style.display = "block";

			onLayoutForLayer();
		} else {
			disTarget.style.display = "none";
			innerTarget.style.display = "none";

			document.body.style.overflowX=def_scrollX;
			document.body.style.overflowY=def_scrollY;
			//document.body.style.overflowY='auto';
		}
	}

	function onLayoutForLayer() {
		var layerObj = document.getElementById("downloadLayer");
		if(layerObj.style.display == 'none') {
			return;
		}

		document.body.style.overflowX=def_scrollX;
		document.body.style.overflowY=def_scrollY;

		layerObj.style.left = 0;
		layerObj.style.top = 0;
		layerObj.style.width = document.body.offsetWidth + 17;//clientWidth;
		layerObj.style.height = document.body.offsetHeight + 17;//clientHeight;

		var innerObj = document.getElementById("innerDivToLayer");
		var loadingObj = document.getElementById("loadingID");
		var loadingWidth = loadingObj.clientWidth;
		var loadingHeight = loadingObj.clientHeight;

		var locLeft = document.body.clientWidth/2;
		var locTop = document.body.clientHeight/2;
		locLeft = locLeft - loadingWidth/2;
		locTop = locTop - loadingHeight/2;

		innerObj.style.left = locLeft;
		innerObj.style.top = locTop;
		/*
			layerObj.innerHTML = "top : " + layerObj.style.top
								+ "<br>left : " + layerObj.style.left
								+ "<br>width : " + layerObj.style.width
								+ "<br>height : " + layerObj.style.height
								+ "<br>clientTop : " + document.body.clientTop
								+ "<br>clientLeft : " + document.body.clientLeft
								+ "<br>clientWidth : " + document.body.clientWidth
								+ "<br>clientHeight : " + document.body.clientHeight
								+ "<br>offsetWidth : " + document.body.offsetWidth
								+ "<br>offsetHeight : " + document.body.offsetHeight
								+ "<br>scrollHeight : " + document.body.scrollHeight;
		*/
	}
// -->
</script>
<div class='downloadLayer' id='downloadLayer'></div>
<div class='innerDivToLayer' id='innerDivToLayer'>
	<table class='loadingID' id='loadingID' align=center valign=center>
		<tr><td align=center>&nbsp;</td></tr>
		<tr><td id='loadingContent' align=center>다운로드가 완료되면<br> 창을 닫아 주십시오.<br>(<%=messageService.getString("e3ps.message.ket_message", "00050") %><%--3~5분의 시간이 소요될 수 있습니다--%>)</td></tr>
		<tr><td align=center><img src='/plm/portal/icon/loading.gif' border=0></td></tr>
		<tr><td id='btnClose' align=center><input type=button value='Close' onclick="javascript:doOnOffLayer(false);" id=button></td></tr>
		<tr><td align=center>&nbsp;</td></tr>
	</table>
</div>
