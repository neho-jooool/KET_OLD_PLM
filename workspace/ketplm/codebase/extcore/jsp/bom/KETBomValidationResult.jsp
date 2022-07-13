<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script type="text/javascript">
function alertMsg(msg)
{
	alert(msg);
}
</script>
</head>
<body oncontextmenu="return false">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top" align="right">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01">BOM <%=messageService.getString("e3ps.message.ket_message", "09419") %><%--검증--%></td>
            </table>
          </td>
          <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <tr>
             <td height="10"></td>
        </tr>
      </table>
    </td>
</tr>
<tr>
    <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td id="rsltCnt">(<%=messageService.getString("e3ps.message.ket_message", "09408") %><%--체크항목--%> : 0 <%=messageService.getString("e3ps.message.ket_message", "09409") %><%--건--%>)</td>
          <td align="right" width="70">
			      <table border="0" cellspacing="0" cellpadding="0">
			        <tr>
				        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
				        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:opener.bomValidation();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02068") %><%--실행--%></a></td>
				        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			        </tr>
			     </table> 
		</td>
		<td align="right" width="70">	     
			     <table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window,close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
			            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			        </tr>
			     </table> 
		</td>
		</tr>
		</table>	     
    </td>
</tr> 
<tr>
    <td height="10"></td>
</tr>   
<tr>
    <td id="result" name="result">
     <table width="100%" height="100%" border="1" cellspacing="2" cellpadding="2" style="table-layout:fixed;word-break:break-all;" oncontextmenu="return false">
	  <tr>
	       <td height="20" width="25"><input type="checkbox" name="check1" disabled></td>
	       <td>1. <%=messageService.getString("e3ps.message.ket_message", "09420") %><%--모부품의 동일한 레벨에 같은 부품이 사용되었는지 체크--%></td>
	       <td width="150" id="checkdata1"></td>
	  </tr>
	  <tr>
           <td height="20" width="25"><input type="checkbox" name="check2" disabled></td>
           <td>2. <%=messageService.getString("e3ps.message.ket_message", "09421") %><%--모부품이 하위 자부품으로 재사용되었는지 체크--%></td>
           <td width="150" id="checkdata2"></td>
      </tr>
      <tr>
           <td height="20" width="25"><input type="checkbox" name="check3" disabled></td>
           <td>3. <%=messageService.getString("e3ps.message.ket_message", "09422") %><%--동일한 모부품이 서로 다른 구조의 자부품으로 사용되었는지 체크--%></td>
           <td width="150" id="checkdata3"></td>
      </tr>
      <tr>
           <td height="20" width="25"><input type="checkbox" name="check4" disabled></td>
           <td>4. <%=messageService.getString("e3ps.message.ket_message", "09423") %><%--부품에 연결된 비철/수지 원재료 코드 밑에 올바른 스크랩 코드가 존재하는지 체크--%></td>
           <td width="150" id="checkdata4"></td>
      </tr>
      <tr>
           <td height="20" width="25"><input type="checkbox" name="check5" disabled></td>
           <td>5. <%=messageService.getString("e3ps.message.ket_message", "09424") %><%--삭제된 부품이 존재하는지 체크--%></td>
           <td width="150" id="checkdata5"></td>
      </tr>
      <tr>
           <td height="20" width="25"><input type="checkbox" name="check6" disabled></td>
           <td>6. <%=messageService.getString("e3ps.message.ket_message", "09425") %><%--터미널 반제품의 경우 자부품에 후도금 부품코드가 존재하는지 체크 (품번 도금사양이 -2 일 경우)--%></td>
           <td width="150" id="checkdata6"></td>
      </tr>
      <tr>
           <td height="20" width="25"><input type="checkbox" name="check7" disabled></td>
           <td>7. <%=messageService.getString("e3ps.message.ket_message", "09426") %><%--도금전 터미널 반제품 자부품으로 원재료, 스크랩 코드 존재하는지 체크--%></td>
           <td width="150" id="checkdata7"></td>
      </tr>
       <tr>
           <td height="20" width="25"><input type="checkbox" name="check8" disabled></td>
           <td>8. <%=messageService.getString("e3ps.message.ket_message", "09427") %><%--총중량이 부품중량보다 큰 지 체크--%>, 스크랩 중량이 입력되었는지 체크</td>
           <td width="150" id="checkdata8"></td>
      </tr>
      <tr>
           <td height="20" width="25"><input type="checkbox" name="check9" disabled></td>
           <td>9. 상품 하위에 자부품 존재여부 체크(하위부품 구성 불가)</td>
           <td width="150" id="checkdata9"></td>
      </tr>
	</table>
    </td>
</tr>
</table>
</body>
</html>