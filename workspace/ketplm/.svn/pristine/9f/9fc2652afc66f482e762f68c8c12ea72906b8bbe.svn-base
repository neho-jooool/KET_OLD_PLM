<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/trycondition/tryCondition.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
    $(document).attr('title',LocaleUtil.getMessage('09262'));//'금형 Try 조건표 복사'
    //server paging
    TryCondition.createTryCopyGrid(${tryConditionList});
});
//-->
</script>
<div class="contents-wrap">
    <table style="width: 100%;">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;">
                    <tr>
                        <td width="7"></td>
                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09262") %><!-- 금형 Try 조건표 복사 --></td>
                    </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td class="space5"></td>
        </tr>
    </table>
	<div class="b-space10" style="text-align:right">
	<span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.copyTryCondition();" class="btn_blue">선택</a></span><span class="pro-cell b-right"></span></span></span>
	<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:self.close()" class="btn_blue">닫기</a></span><span class="pro-cell b-right"></span></span></span>
	</div>
	<div class="b-space5">
		<table cellpadding="0" class="table-style-01" summary="">
			<colgroup>
				<col width="20%" />
				<col width="80%" />
			</colgroup>
			<tbody>
				<tr>
					<td class="title">Die No</td>
					<td>${moldProject.moldInfo.dieNo}</td>
				</tr>
				<tr>
					<td class="title">Part No</td>
					<td>${moldProject.moldInfo.partNo}</td>
				</tr>
				<tr>
					<td class="title">Part Name</td>
					<td>${moldProject.moldInfo.partName}</td>
				</tr>
			</tbody>
		</table>
	</div>
    <!-- EJS TreeGrid Start -->
    <div class="content-main">
        <div class="container-fluid">
            <div class="row-fluid">
                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
            </div>
        </div>
    </div>
    <!-- EJS TreeGrid End -->
</div>