<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style>
<!--
body {
    margin-bottom: 0px;
}
-->
</style>
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/index/indexSearch.js"></script>
<script type="text/javascript">
$(document).ready(function(e) {
	IndexSearch.createGrid();
	treeGridResize("#IndexSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	//IndexSearch.createPaingGrid();
	$('[name=searchKeyword]').bind("keypress", function (e) {
	    if (e.keyCode == 13) {
	        e.preventDefault();
	        IndexSearch.searchDoc();
	    }
	});
	// Multi Combo 생성
    $('#searchCategory').multiselect({
    	multiple : true,
    	minWidth : 150,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "09359") %>'//<!-- 모든유형 -->
    });
    <c:if test="${not empty dto.searchKeyword}">
    IndexSearch.searchDoc();
    </c:if>
});
</script>
<form name="SearchForm">
<div class="contents-wrap" style="margin-bottom: 0px;padding-bottom: 4px">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "09360") %><!-- 통합검색 --></div>    
    <div class="current-location">
        <span><img src="/plm/portal/images/icon_navi.gif" />Home<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "09360") %><!-- 통합검색 --></span>
    </div>
    <div class="inquiry-table box-size" style="text-align:center">
        <span class="r-space20" style="vertical-align:top">
            <select id="searchCategory" name="searchCategory" multiple="multiple">
                <option value="PROJECT"<c:if test="${fn:contains(dto.searchCategory,'PROJECT')}"> selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "03046") %><!-- 프로젝트 --></option>
                <option value="DEVDOC"<c:if test="${fn:contains(dto.searchCategory,'DEVDOC')}"> selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "00638") %><!-- 개발산출문서 --></option>
                <option value="DRAWING"<c:if test="${fn:contains(dto.searchCategory,'DRAWING')}"> selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "01253") %><!-- 도면 --></option>
                <option value="PART"<c:if test="${fn:contains(dto.searchCategory,'PART')}"> selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "01564") %><!-- 부품 --></option>
                <option value="ECO"<c:if test="${fn:contains(dto.searchCategory,'ECO')}"> selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "01844") %><!-- 설계변경 --></option>
                <option value="DQM"<c:if test="${fn:contains(dto.searchCategory,'DQM')}"> selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "07231") %><!-- 개발품질문제 --></option>
                <option value="SAMPLE"<c:if test="${fn:contains(dto.searchCategory,'SAMPLE')}"> selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "08032") %><!-- Sample요청 --></option>
            </select>
        </span>
        <span class="r-space10" style="vertical-align:middle">
            <input id="searchKeyword" name="searchKeyword" type="text" class="search-input box-size" value="${dto.searchKeyword}" />
        </span>
        <span class="b-search" style="vertical-align:middle"><img src="/plm/portal/images/ico_search.png" onclick="IndexSearch.searchDoc()"/></span>
    </div>
    <!-- EJS TreeGrid Start -->
    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"  style="padding-bottom: 10px"></div>
    <!-- EJS TreeGrid Start -->
</div>
</form>