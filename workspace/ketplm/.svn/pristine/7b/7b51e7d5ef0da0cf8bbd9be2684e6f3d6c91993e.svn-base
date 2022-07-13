<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/issue/issue.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script>
$(document).ready(function(){
	
	issue.createPaingGrid("CS");
    
	Grids.OnRenderFinish = function(grid){
        issue.search();
    }
});
</script>
<body>
<form name="searchForm">
<input type="hidden" name="pboOid" value="${oid }" />
<input type="hidden" name="issueState" value="IST002" />
</form>
<div class="contents-wrap">
	<div class="sub-title-02 b-space15 clear">
	    <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>진행현황
		<div class="b-space20" style="text-align:right">
		    <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span>
		    <span class="pro-cell b-center">
		    <a href="javascript:self.close();" class="btn_blue">닫기</a>
		    </span><span class="pro-cell b-right"></span></span></span>
		</div>
		<!-- EJS TreeGrid Start -->
		<div class="content-main">
		    <div class="container-fluid">
		        <div class="row-fluid">
		            <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
		        </div>
		    </div>
		    <!-- EJS TreeGrid Start -->
		</div>
	</div>
</div>
</body>