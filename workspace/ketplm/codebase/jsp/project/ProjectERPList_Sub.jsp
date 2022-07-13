<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "e3ps.project.beans.*,
                  e3ps.project.*"%>
                  
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                  
<%
String inputPN = request.getParameter("inputPN");
%>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellspacing="0" cellpadding="0" width="855">
                <tr>
                    <td class="space20"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="855">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="855">
                <tr>
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="855">
                        <tr>
                            <td class="tdblueM" rowspan=2>&nbsp;</td>
                            <td class="tdblueM">PROJECT_NO</td>
                            <td class="tdblueM">PROJECT_NAME</td>
                            <td class="tdblueM">PROJECT_TYPE</td>
                            <td class="tdblueM">ACCEPTANCEDATE</td>
                            <td class="tdblueM">DELIVEREDDATE</td>
                            <td class="tdblueM">PROJECT_FABNAME</td>
                        </tr>
                        <tr>
                            <td class="tdblueM">PRODUCT</td>
                            <td class="tdblueM">CUSTOMER</td>
                            <td class="tdblueM">CONSIGNMENT_TYPE</td>
                            <td class="tdblueM">SITE_TYPE</td>
                            <td class="tdblueM">IS_CREATED</td>
                            <td class="tdblueM">IS_MODIFIED</td>
                        </tr>
<%
ArrayList array = ProjectERPConMgr.getInstance().getERPProjectList(inputPN);
if(array.size() > 0) {
    for(int i = 0; i < array.size(); i++) {
        Object[] obj = (Object[])array.get(i);
        for(int a = 0 ; a < 12 ; a++){
        Kogger.debug(a + " : "+String.valueOf(obj[a]));
        }

%>
                <tr>
                    <!-- CheckBOX -->
                    <td class="tdwhiteM" rowspan=2>
                        <input name="poid" type="checkbox" class="Checkbox" value="<%=String.valueOf(obj[0])%>"
                                                                            tempProjectNo="<%=String.valueOf(obj[0]).trim()%>"
                                                                            projectName="<%=String.valueOf(obj[1]).trim()%>"
                                                                            projectType="<%=String.valueOf(obj[2]).trim()%>"
                                                                            projectAcceptanceDate="<%=String.valueOf(obj[3]).trim()%>"
                                                                            projectDeliveredDate="<%=String.valueOf(obj[4]).trim()%>"
                                                                            projectFabName="<%=String.valueOf(obj[5]).trim()%>"
                                                                            projectProduct="<%=String.valueOf(obj[6]).trim()%>"
                                                                            projectCustomer="<%=String.valueOf(obj[7]).trim()%>"
                                                                            projectConsignment="<%=String.valueOf(obj[8]).trim()%>"
                                                                            projectSite="<%=String.valueOf(obj[9]).trim()%>"
                                                                            onclick="oneClick(this);" >
                    </td>
                    <!-- PROJECT_NO -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[0]) == "null")?"":String.valueOf(obj[0])%></td>
                    <!-- PROJECT_NAME -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[1]) == "null")?"":String.valueOf(obj[1])%></td>
                    <!-- PROJECT_NAM -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[2]) == "null")?"":String.valueOf(obj[2])%></td>
                    <!-- PROJECT_SPEC -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[3]) == "null")?"":String.valueOf(obj[3])%></td>
                    <!-- PROJECT_UNIT -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[4]) == "null")?"":String.valueOf(obj[4])%></td>
                    <!-- PROJECT_DELIVEREDDATE -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[5]) == "null")?"":String.valueOf(obj[5])%></td>
                </tr>
                <tr>
                    <!-- PM_QTY -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[6]) == "null")?"":String.valueOf(obj[6])%></td>
                    <!-- PRODUCT -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[7]) == "null")?"":String.valueOf(obj[7])%></td>
                    <!-- CUSTOMER -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[8]) == "null")?"":String.valueOf(obj[8])%></td>
                    <!-- CUSTOMER_NAME -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[9]) == "null")?"":String.valueOf(obj[9])%></td>
                    <!-- PROJECT_NO_MIN -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[10]) == "null")?"":String.valueOf(obj[10])%></td>
                    <!-- PROJECT_GUBUN -->
                    <td class="tdwhiteM">&nbsp;<%=(String.valueOf(obj[11]) == "null")?"":String.valueOf(obj[11])%></td>
                </tr>
<%
    }
} else {
%>
                <tr>
                    <td class='tdwhiteM' align='center' colspan='7'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>
<%
}
%>
            </table>
