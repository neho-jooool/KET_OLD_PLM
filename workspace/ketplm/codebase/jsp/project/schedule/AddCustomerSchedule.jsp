<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.HashMap,
                e3ps.common.util.CommonUtil,
                e3ps.project.customerPlan.CustomerPlan,
                e3ps.project.customerPlan.beans.CustomerPlanHelper"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

	String poid = request.getParameter("poid")!=null?request.getParameter("poid"):"";
	String coid = request.getParameter("coid")!=null?request.getParameter("coid"):"";
	String oid = "";

	StringBuffer sb = new StringBuffer();

	CustomerPlan customerPlan = null;
	customerPlan = CustomerPlanHelper.getCustomerPlan(poid, coid);
	if(customerPlan != null){
		oid = CommonUtil.getOIDString(customerPlan);
		String[] planGubunArray = customerPlan.getIni_Sample().split("\\|");
		String[] planDateArray = customerPlan.getIni_Date().split("\\|");

		for(int i=0;i<planGubunArray.length;i++){
			if(i==0){
				sb.append("	<tr id='firstItem' class='firstItem'> \n");
				sb.append("		<td class='tdwhiteL' width='150'><img src='/plm/portal/images/iconPlus.gif' align='absmiddle' class='inputButton'/><img src='/plm/portal/images/iconMinus.gif' align='absmiddle' class='delButton'/><input id='planName' name='planName' value='"+planGubunArray[0].toString()+"' class='txt_field' style='width: 80px;' maxlength='10'/></td> \n");
				sb.append("		<td class='tdwhiteL' width='150'><input id='customerPlan' name='customerPlan' class='txt_field' value='"+planDateArray[0].toString()+"' style='width: 80px;' maxlength='10'/><a href='#' onclick='javascript:showCal(customerPlan);'><img src='/plm/portal/images/icon_6.png'></a>&nbsp;<a href=\"JavaScript:clearDate(\'customerPlan\');\"><img src='/plm/portal/images/icon_delete.gif'></a></td> \n");
				sb.append("	</tr> \n");
			}else{
				sb.append("	<tr id='firstItem' class='firstItem'> \n");
				sb.append("		<td class='tdwhiteL' width='150'><img src='/plm/portal/images/iconPlus.gif' align='absmiddle' class='inputButton'/><img src='/plm/portal/images/iconMinus.gif' align='absmiddle' class='delButton'/><input id='planName' name='planName' value='"+planGubunArray[i].toString()+"' class='txt_field' style='width: 80px;' maxlength='10'/></td> \n");
				sb.append("		<td class='tdwhiteL' width='150'><input id='customerPlan"+i+"' name='customerPlan"+i+"' class='txt_field' value='"+planDateArray[i].toString()+"' style='width: 80px;' maxlength='10'/><a href='#' onclick='javascript:showCal(customerPlan"+i+");'><img src='/plm/portal/images/icon_6.png'></a>&nbsp;<a href=\"JavaScript:clearDate(\'customerPlan"+i+"\');\"><img src='/plm/portal/images/icon_delete.gif'></a></td> \n");
				sb.append("	</tr> \n");
			}
		}
	}else{
		sb.append("	<tr id='firstItem' class='firstItem'> \n");
		sb.append("		<td class='tdwhiteL' width='150'><img src='/plm/portal/images/iconPlus.gif' align='absmiddle' class='inputButton'/><img src='/plm/portal/images/iconMinus.gif' align='absmiddle' class='delButton'/><input id='planName' name='planName' class='txt_field' style='width: 80px;' maxlength='10'/></td> \n");
		sb.append("		<td class='tdwhiteL' width='150'><input id='customerPlan' name='customerPlan' class='txt_field' style='width: 80px;' maxlength='10'/><a href='#' onclick='javascript:showCal(customerPlan);'><img src='/plm/portal/images/icon_6.png'></a>&nbsp;<a href='JavaScript:clearDate('customerPlan');'><img src='/plm/portal/images/icon_delete.gif'></a></td> \n");
		sb.append("	</tr> \n");
	}

	String errorMsg = messageService.getString("e3ps.message.ket_message", "02384");/*입력된 데이터가 없습니다*/
%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<!-- title><%=messageService.getString("e3ps.message.ket_message", "00403") %><%--Project 인원--%></title -->
<title><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%> <%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%><%=messageService.getString("e3ps.message.ket_message", "00941") %><%--관리--%></title>

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<base target="_self">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/jquery/jquery-1.7.1.min.js"></script>
<script src="/plm/portal/js/customerSchedule.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script language="javascript">
<!--
function updatePlan(){
	var poid = document.forms[0].poid.value;
	var coid = document.forms[0].coid.value;

	var rows = $(".firstItem").length;
	var plan_gubun = "";
	var plan_date = "";
	for(i=0;i<rows;i++){
		if(document.getElementsByName("planName").item(i).value != ""){
			if(i==0){
				plan_gubun = document.getElementsByName("planName").item(i).value;
			}else{
				plan_gubun = plan_gubun + "|" + document.getElementsByName("planName").item(i).value;
			}
		}else{
			alert((i+1) + " <%=errorMsg%>");
			return;
		}
		if(i==0){
			if(document.getElementsByName("customerPlan").item(0).value != ""){
				plan_date = document.getElementsByName("customerPlan").item(0).value;
			}else{
				alert((i+1) + " <%=errorMsg%>");
				return;
			}
		}else{
			if(document.getElementsByName("customerPlan"+i).item(0).value != ""){
				plan_date = plan_date + "|" + document.getElementsByName("customerPlan"+i).item(0).value;
			}else{
				alert((i+1) + " <%=errorMsg%>");
				return;
			}
		}
	}
	document.forms[0].planGubun.value = plan_gubun;
	document.forms[0].planDate.value = plan_date;
	document.forms[0].command.value = "add";
	document.forms[0].method = "post";
	document.forms[0].action = "/plm/jsp/project/schedule/CustomerAction.jsp";
	document.forms[0].submit();


}

function deletePlan(){
	var oid = document.forms[0].oid.value;

	if(oid.length > 0){
		document.forms[0].command.value = "del";
		document.forms[0].method = "post";
		document.forms[0].action = "/plm/jsp/project/schedule/CustomerAction.jsp";
		document.forms[0].submit();
	}else{
		alert("error");
	}
}
// -->
</script>
<style type="text/css">
<!--
body {
  margin-top: 15px;
  margin-left: 10px;
  margin-right: 15px;
  margin-bottom: 10px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
-->
</style>
</head>

<body>

<form>
<input type="hidden" name="command" value="">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="poid" value="<%=poid%>">
<input type="hidden" name="coid" value="<%=coid%>">
<input type="hidden" name="planGubun" value="">
<input type="hidden" name="planDate" value="">
<table style="width: 300px; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                            <tr>
                                <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                <!-- td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00403") %><%--Project 인원--%></td-->
                                <td class="font_01">고객사 일정관리<%-- 고객사 일정관리 --%></td>
                                <td style="width: 10px;"></td>
                            </tr>
                        </table>
                    </td>
                    <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <!-- [END] title & position -->
	</td>
</tr>
</table>
<!-- [START] button -->
        <table style="width: 300px;" >
        <tr>
            <td align="center">
                <table>
                <tr>
                    <td>
                       <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:updatePlan();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td width="20">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:deletePlan();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
<table style="width: 300px;" border="0" cellspacing="0" cellpadding="0" id='planTable'>
<tr>
	<td class="tdwhiteM" width="150"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
	<td class="tdwhiteM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%></td>
</tr>
<%=sb.toString() %>
</table>
</form>
</body>
</html>
