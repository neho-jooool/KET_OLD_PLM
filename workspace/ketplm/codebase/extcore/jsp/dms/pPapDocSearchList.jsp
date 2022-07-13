<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<style>
.table-info {
  border-collapse: collapse;
  table-layout: fixed;
}
.panel1{
	overflow:hidden;
}
.panel2 {
  overflow: auto;
}
.dataRows, .leftCols{
	border-top:0px;
}

.categoryCol{
	background:#FFFFEF !important;
}
.fileCell{
overflow:hidden; text-overflow:ellipsis; white-space:nowrap;vertical-align:top;
}

</style>
<script>
(function($) {
    $.fn.hasVerticalScrollbar = function() {
        // This will return true, when the div has vertical scrollbar
        return this.get(0).scrollHeight > this.height();
    }
    $.fn.hasHorizontalScrollbar = function() {
        // This will return true, when the div has horizontal scrollbar
        return this.get(0).scrollWidth > this.width();
    }
})(jQuery);

$(document).ready(function(){
	
	window.getDownloadItemList = function(){
		
		var list = new Array();
		
		$("input[name=downloadItem]:checked").each(function(){
			
			var downloadItem = {
				contentOid : $(this).val(),
				holderOid : $(this).data("holderoid"),
				categoryName : $(this).parent("td").data("categoryname"),
				partNo : $(this).parents("tr").data("partno"),
				parentPartNo : $(this).parents("tr").data("parentpartno"),
				rootPartNo : $(this).parents("tr").data("rootpartno"),
				filePath : $(this).data("filepath"),
				name : $(this).data("name")
			}
			list.push(downloadItem);
		});
		
		return list;
		
	}
	
	$("tr[data-level=1] td").css("background","#DFE");
	$("tr[data-level=1] td").css("font-weight","bold");
	$(".totalCnt").text("${fn:length(bomList)}");
	
	$(".panel2").scroll(function(){
		$(".panel1").scrollTop($(this).scrollTop());
		$(".header2").scrollLeft($(this).scrollLeft());
	});
	
	$(".dataRows tr").each(function(idx){
		$(".leftCols tr:eq(" + idx + ")").height($(this).height());
	});
	
	window.resizePanel = function(){
		if($(".panel2").hasHorizontalScrollbar()){
			$(".blankTD").hide();
			$(".panel1").height($(".content-main").height()-55);
		}else{
			$(".panel1").height($(".content-main").height()-35);
			$(".blankTD").show();
		}
		$(".panel2").height($(".content-main").height()-35);
	}
    
	$(window).resize(function(){
		resizePanel();
	});
	
	resizePanel();
	
	window.closeTempFileWindow = function() {  
	    setTimeout(function() {
	        window.FILE_TEMP_POPUP.close();
	    }, 800);  
	}  
	
	window.getExtensionOfFilename = function(filename){
        var _fileLen = filename.length;
         
        var _lastDot = filename.lastIndexOf('.');
     
        // 확장자 명만 추출한 후 소문자로 변경
        var _fileExt = filename.substring(_lastDot, _fileLen).toLowerCase();
     
        return _fileExt;
    }
    

	window.pPopFileDownload = function(url,fileName){
	    
	    var isIE = false;
	    
	    var agent = navigator.userAgent.toLowerCase();

	    if( navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1 || (agent.indexOf("msie") != -1)) {
	        isIE = true;
	    }
	    
	    if(isIE || getExtensionOfFilename(fileName) != '.pdf'){
	        var winObj = null;
	        var popName = "download";
	        var ot = "width=0 height=0 menubar=no status=no";
	        winObj = window.open(url, popName, ot); 
	    }else{
	        window.FILE_TEMP_POPUP = window.open(url, "_blank",'scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width=0,height=0,left=0,top=0');
	        closeTempFileWindow();  
	    }
	}
	
	document.getElementsByClassName("fileCell")[0].style.paddingTop = '3px';


});
</script>
<div class="header1" style="float:left;width:601px;">
<table class="table-info">
	<colgroup>
		<col width="30" />
		<col width="130" />
		<col width="230" />
		<col width="90" />
	</colgroup>
	<thead>
		<tr>
	    	<td>Lev</td>
	        <td><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
	        <td><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
	        <td style="overflow:hidden; text-overflow:ellipsis; white-space:nowrap;"><%=messageService.getString("e3ps.message.ket_message", "02208") %><%--원재료Maker--%></td>
	    </tr>
	</thead>
</table>
</div>
<div class="header2" style="overflow:hidden;position:relative;">
<table class="table-info" style="width:0px;">
	<colgroup>
		<c:forEach items="${categoryList }" var="categoryName">
		<col width="200" />
        </c:forEach>
        <col width="17" />
	</colgroup>
	<thead>
		<tr>
	        <c:forEach items="${categoryList }" var="categoryName" varStatus="catStat">
	        <c:if test="${catStat.last }"><td class="categoryCol" style="border-right:1px;">${categoryName }</td></c:if>
	        <c:if test="${!catStat.last }"><td class="categoryCol">${categoryName }</td></c:if>
	        </c:forEach>
	        <td class="categoryCol">&nbsp;</td>
	    </tr>
	</thead>
</table>
</div>
<div class="panel1" style="float:left;width:601px;">
<table class="table-info leftCols">
	<colgroup>
		<col width="30" />
        <col width="130" />
        <col width="230" />
        <col width="90" />
	</colgroup>
	<tbody>
		<c:forEach items="${bomList }" var="part" varStatus="partStat">
		<tr data-level="${part.LEV }" data-partno="${part.PARTNO }" data-parentpartno="${part.PARENTPARTNO }" data-rootpartno="${part.ROOTPARTNO }">
			<td class="td center">${part.LEV }</td>
			<td class="td" style="overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
				<c:forEach begin="2" end="${part.LEV }">
				&nbsp;&nbsp;&nbsp;&nbsp;
				</c:forEach>
				<a href="javascript:openView('${part.PARTOID }');" title="${part.PARTNO}">${part.PARTNO }</a>
			</td>
			<td class="td" style="overflow:hidden; text-overflow:ellipsis; white-space:nowrap;" title="${part.PARTNAME}"><a href="javascript:openView('${part.PARTOID }');">${part.PARTNAME }</a></td>
			<td class="td" style="overflow:hidden; text-overflow:ellipsis; white-space:nowrap;" title="${part.MATERIALMAKER}">${part.MATERIALMAKER }</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<div class="panel2">
	<table class="table-info dataRows" style="width:0px;" >
		<colgroup>
			<c:forEach items="${categoryList }" var="categoryName">
			<col width="200" />
	        </c:forEach>
	        <col width="17" />
		</colgroup>
		<tbody>
			<c:forEach items="${bomList }" var="part" varStatus="partStat">
			<tr data-level="${part.LEV }" data-partno="${part.PARTNO }" data-parentpartno="${part.PARENTPARTNO }" data-rootpartno="${part.ROOTPARTNO }">
				<c:forEach items="${categoryList }" var="categoryName" varStatus="catStat">
				<c:if test="${catStat.last }">
				<td class="td fileCell" style="border-right:0px;" data-categoryname="${categoryName }">
				</c:if>
				<c:if test="${!catStat.last }">
				<td class="td fileCell" data-categoryname="${categoryName }">
				</c:if>
				<c:forEach items="${part.catDocMap[categoryName] }" var="docFile" varStatus="fileStat">
				
					<c:if test="${fileStat.index eq 0 || categoryName == '재질성적서' || categoryName == '중금속성적서'}">
						<input type="checkbox" name="downloadItem" value="${docFile.contentoid }" data-holderoid="${docFile.holderoid }" data-name="${docFile.name }" data-filepath="${docFile.filePath }" checked />
					</c:if>
					<c:if test="${fileStat.index ne 0 && categoryName != '재질성적서' && categoryName != '중금속성적서'}">
						<input type="checkbox" name="downloadItem" value="${docFile.contentoid }" data-holderoid="${docFile.holderoid }" data-name="${docFile.name }" data-filepath="${docFile.filePath }"/>
					</c:if>

					<c:if test="${!empty docFile.docNo}">
					<span style="cursor:pointer;" title="[${docFile.docNo }]&nbsp;${docFile.docName }"
						onclick="javascript:getOpenWindow2('/plm/jsp/dms/ViewDocument.jsp?oid=${docFile.holderoid }','',1000,800);">
						<img src="/plm/portal/images/icon_5.png">
					</span>
					</c:if>
					<%-- <a href='${docFile.downURLStr}' download target='download' title="${docFile.name}">${docFile.iconURLStr}</a>&nbsp; --%>
					<%-- <a href='${docFile.downURLStr}' target='download' title="${docFile.name}">${docFile.name}</a>&nbsp;<br/> --%>
					<a href="javascript:pPopFileDownload('${docFile.downURLStr}','${fn:replace(docFile.name, "'", "\\'") }');" title="${docFile.name}">${docFile.name}</a>&nbsp;<br/>
				</c:forEach>
				</td>
				</c:forEach>
				<td class="td blankTD">&nbsp;</td>
			</tr>
		</c:forEach>
		<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
		</tbody>
	</table>
</div>
