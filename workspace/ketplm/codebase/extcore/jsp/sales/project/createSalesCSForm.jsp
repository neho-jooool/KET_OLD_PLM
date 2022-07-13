<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript"
	src="/plm/extcore/js/sales/project/salesCSProject.js"></script>
<script type="text/javascript">
var year;
var mon;

<!--
	$(document).ready(function() {
		$(document).attr('title', "CS회의차수 등록");//'금형 Try 조건표 [MOLD] 등록'
		CalendarUtil.dateInputFormat('csStartDate'); //csStartDate
		CalendarUtil.dateInputFormat('csEndDate'); //csEndDate
		var today = new Date();
		year = today.getFullYear();
		
		mon = (today.getMonth()+1) > 9 ?  (today.getMonth()+1) : "0" + (today.getMonth()+1);
		
		
		$('#title').append(year+"년 "+mon+"월 차수 생성");
	});
//-->
</script>
<form name="SalesCSForm" method="post" enctype="multipart/form-data">
	<div class="contents-wrap">
		<table style="width: 100%;">
			<tr>
				<td background="/plm/portal/images/logo_popupbg.png">
					<table style="height: 28px;">
						<tr>
							<td width="7"></td>
							<td width="20"><img src="/plm/portal/images/icon_3.png"></td>
							<td class="font_01">CS회의차수 등록</td>
						</tr>
					</table>
				</td>
				<td width="136" align="right"><img
					src="/plm/portal/images/logo_popup.png"></td>
			</tr>
		</table>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td class="space5"></td>
			</tr>
		</table>
		<div class="float-r" style="text-align: right">
			<span class="in-block v-middle">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"><a href="javascript:sales.CSDegreeCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><!-- 저장 --></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<span class="in-block v-middle">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><!-- 취소 --></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<div id="title" class="sub-title-02 b-space15 clear">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>
		</div>
		<div class="b-space30">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td class="tab_btm2"></td>
				</tr>
			</table>
			<table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed" border="1">
				<colgroup>
					<col width="20%" />
					<col width="80%" />
					<col width="20%" />
                    <col width="80%" />
				</colgroup>
				<tbody>
					<tr>
						<td class="tdblueL">CS기간<span class="red">*</span></td>
                        <td class="tdwhiteL">
                          <input type="text" class="txt_field" name="csStartDate" style="width: 30%" esse="true" esseLabel="CS기간시작일"  />
                          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('csStartDate');" style="cursor:pointer;cursor:hand;"> ~
                          <input type="text" class="txt_field" name="csEndDate" style="width: 30%" esse="true" esseLabel="CS기간종료일"  />
                          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('csEndDate');" style="cursor:pointer;cursor:hand;">
                        </td>
					</tr>
					<tr>
			          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667") %><%--주 첨부파일--%></td>
			          <td class="tdwhiteL">
			              <input name="primaryFile" type="file" class="txt_fieldRO" style="width: 81%" esse="true" esseLabel="첨부파일"">
			          </td>
			        </tr>
				</tbody>
			</table>
		</div>
	</div>
	<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>