<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<style type="text/css">
<!--
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 5px;
}
-->
</style>

<script language='javascript'>

$(document).ready(function(){
	document.title = '연말정산';
});
	var emp_no = "${emp_no}";
	var name = "${name}";
	var chasu = "${chasu}";

	function valCheck() {
		if (document.forms[0].primaryFile.value != ""
				&& !fileFilter(document.forms[0].primaryFile)) {
			return false;
		}
		return true;
	}

	//pdf 업로드
	function exePdfUpload() {
		showProcessing();
		if (document.forms[0].primaryFile.value == ''
				|| document.forms[0].primaryFile.value == null) {
			alert("파일첨부 후 실행하세요.");
			hideProcessing();
			return;
		}
		if (!valCheck()) {
			alert("PDF 파일만 업로드 가능합니다.");
			hideProcessing();
		} else {
			/*document.forms[0].action = '/plm/servlet/e3ps/PartServlet?cmd=mig';
			document.forms[0].method = "post";
			document.forms[0].submit();*/

			$('input[name=createDto]').each(function(i) {
				if ($(this).is(":checked")) {
					$('[name=createDto]').val("ok");
				}
			});

			$('[name=yesoneForm]').attr('action','/plm/ext/yesone/pdfUpload.do');
			$('[name=yesoneForm]').attr('target', 'download');
			showProcessing();
			disabledAllBtn();
			$('[name=yesoneForm]').submit();

		}

	}

	function fileFilter(obj) {
		var cnt = 0;
		var filterStr = new Array("PDF");
		var str = obj.value.substring(obj.value.lastIndexOf(".") + 1).toUpperCase();
		for (i = 0; i < filterStr.length; i++) {
			if (str == filterStr[i]) {
				return true;
			}
		}
		return false;
	}

	function lfn_feedback_reStart(msg) {
		msg += "\n국세청자료와 사내System 가족내역이 일치하지 않습니다.\n일치하지 않는 인원의 데이터는 자동이관대상에서 제외됩니다.\n그래도 진행하시겠습니까?";
		if (confirm(msg)) {
			$('[name=differentYn]').val("Y");
			exePdfUpload();
		} else {
			try {
				location.reload();
			} catch (e) {
			}
		}
	}
</script>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
</head>
<html>
<body class="body" oncontextmenu='return false'>
	<form name="yesoneForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="empNo" value="9999">
		<input type="hidden" name="name" value="TEST">
		<input type="hidden" name="differentYn" value="N">
		<table width="780" height="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td valign="top">
					<table width="780" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><table width="780" height="28" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td width="20"><img src="../../portal/images/icon_3.png"></td>
										<td class="font_01">연말정산 PDF 업로드</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td class="head_line"></td>
						</tr>
						<tr>
							<td class="space10"></td>
						</tr>
					</table>
					<table border="0" cellspacing="0" cellpadding="0" width="780">
						<tr>
							<td class="space5">${name}님의 PDF 업로드 횟수 : 총 <span
								class="blue">${chasu}</span> 회
							</td>
						</tr>
					</table>


					<table width="780" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>&nbsp;</td>
							<td align="right"><table border="0" cellspacing="0"
									cellpadding="0">
									<tr>
										<td><table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="10"><img src="../../portal/images/btn_1.gif"></td>
													<td background="../../portal/images/btn_bg1.gif"><a href="javascript:exePdfUpload();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02068")%><%--실행--%></a></td>
													<td width="10"><img src="../../portal/images/btn_2.gif"></td>
													
													<td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue">닫기</a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
												</tr>
												
												
											</table></td>
									</tr>
								</table></td>
						</tr>
					</table>
					<table border="0" cellspacing="0" cellpadding="0" width="780">
						<tr>
							<td class="space5"></td>
						</tr>
					</table>

					<table border="0" cellspacing="0" cellpadding="0" width="780">
						<tr>
							<td class="tab_btm2"></td>
						</tr>
					</table>
					<table border="0" cellspacing="0" cellpadding="0" width="780">
						<!--  
        <tr>
          <td width="100" class="tdblueM">Create DTO</td>
          <td colspan="3" class="tdwhiteL0">
            <input name="createDto" type="Checkbox" value='no'>

          </td>
        </tr>
-->
						<tr>
							<td width="50" class="tdblueM">국세청 PDF</td>
							<td class="tdwhiteL">
							<input type="file" accept=".pdf" name="primaryFile" />&nbsp;&nbsp;<img src="/plm/portal/icon/fileicon/pdf.gif" alt="excel down" name="leftbtn_02" border="0">
							<span class="blue">*국세청 연말정산간소화 서비스에서 제공하는 PDF를 업로드하십시오</span></td>
						</tr>
						<tr>
							<td width="50" class="tdblueM">비밀번호</td>
							<td width="290" class="tdwhiteL">
							<input type="password" name="p_pwd" class="txt_field" style="width: 270" id="textfield2">&nbsp; <span class="blue">* PDF에 비밀번호가 설정되어 있을 경우만 입력하십시오</span></td>
						</tr>

					</table>
					<table border="0" cellspacing="0" cellpadding="0" width="780">
						<tr>
							<td class="space20"></td>
						</tr>
					</table>

					<table border="0" cellspacing="0" cellpadding="0" width="780">
						<tr>
							<td class="tdwhiteL0">
								<div id="divView" style="width: 750px;" class="outline">
									<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span
										class="font_03"> 1. [가족등록] 을 입력완료 하신 후 업로드를 실행하세요.</span></b><br>
									<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span
										class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▶</span><span
										class="font_03">&nbsp;&nbsp;PDF파일과 일치하지 않는 인적내역에 해당하는
											데이터는 자동업로드 대상에서 제외됩니다.</span></b><br> <br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span
										class="font_03"> 2. 자동업로드 대상 제외 항목</span></b><br> <br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span
										class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▶</span><span
										class="font_03">&nbsp;&nbsp;[기부금 지급건수] , [월세액],
											[임차차입금원리금상환], [장기주택저당차입금], [출자투자], [세액공제 / 감면]</span></b><br> <br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span
										class="font_03"> 3. PDF 업로드 후 최종적으로 [사용금액등록] 화면을
											입력해야합니다. </span></b><br>
								</div> <br>
							</td>
						</tr>
					</table>
			</tr>
		</table>
	</form>
	<iframe name="download" align="center" width="0" height="0" border="0"
		frameborder="0"></iframe>
</body>
</html>
