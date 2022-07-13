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
				sb.append("		<td class='tdwhiteL' width='150'>"+planGubunArray[0].toString()+"</td> \n");
				sb.append("		<td class='tdwhiteL' width='150'>"+planDateArray[0].toString()+"</td> \n");
				sb.append("	</tr> \n");
			}else{
				sb.append("	<tr id='firstItem' class='firstItem'> \n");
				sb.append("		<td class='tdwhiteL' width='150'>"+planGubunArray[i].toString()+"</td> \n");
				sb.append("		<td class='tdwhiteL' width='150'>"+planDateArray[i].toString()+"</td> \n");
				sb.append("	</tr> \n");
			}
		}
	}else{
		sb.append("	<tr id='firstItem' class='firstItem'> \n");
		sb.append("		<td class='tdwhiteL' width='150'></td> \n");
		sb.append("		<td class='tdwhiteL' width='150'></td> \n");
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
