<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Vector"%>
<%@page import="e3ps.sap.PJTInfoERPInterface"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.sap.PJTMoldInterface"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.mrm.util.mrmUtil"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
String oid = request.getParameter("oid");
//Kogger.debug("SAPMold[oid]>>> "+oid);

E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
E3PSProjectData pjtData = new E3PSProjectData(project);

PJTMoldInterface pjtMoldInfo = new PJTMoldInterface();
Vector returnVec = null;
try {
    returnVec = pjtMoldInfo.getMold(pjtData.pjtNo);
} catch(Exception e) {
    Kogger.error(e);
}
%>
<head>
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01098") %><%--금형정보--%></title>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>
<body>
<form>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01098") %><%--금형정보--%></td>
                <td align="right" style="padding:8px 0px 0px 0px"></td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<table border="0" cellpadding="1" cellspacing="1" width="100%">
    <tr>
        <td valign="top" style="padding-left:12">
            <table border="0" cellpadding="0" cellspacing="0" width="50%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="50%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" align=left width="50%">
                <tr bgcolor="#D6DBE7" align=center>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "03379") %><%--품종--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02531") %><%--제작SET--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02493") %><%--전체 진행률--%></td>
                </tr>
                <% if(returnVec.size() > 0) { %>
                <tr bgcolor="#D6DBE7" align=center>
                    <td class=tdwhiteM>&nbsp;<%=pjtData.pjtModelCodeName %></td>
                    <td class=tdwhiteM>&nbsp;<%=pjtData.pjtProductCodeName %></td>
                    <td class=tdwhiteM>&nbsp;<%=returnVec.size()%> SET</td>
                    <%
                    double gressint = 0.0;
                    double tCompleteInt = 0.0;

                    for(int i = 0; i < returnVec.size(); i++) {
                        String[] valueStr = (String[]) returnVec.get(i);

                        String r_date1 = valueStr[8];
                        String r_date2 = valueStr[10];
                        String r_date3 = valueStr[12];
                        String r_date4 = valueStr[14];

                        if(r_date1.equalsIgnoreCase("00000000")) r_date1 = "";
                        if(r_date2.equalsIgnoreCase("00000000")) r_date2 = "";
                        if(r_date3.equalsIgnoreCase("00000000")) r_date3 = "";
                        if(r_date4.equalsIgnoreCase("00000000")) r_date4 = "";

                        if(StringUtil.checkString(r_date1)) {
                            tCompleteInt++;
                        } else if(StringUtil.checkString(r_date2)) {
                            tCompleteInt++;
                        } else if(StringUtil.checkString(r_date3)) {
                            tCompleteInt++;
                        } else if(StringUtil.checkString(r_date4)) {
                            tCompleteInt++;
                        }
                    }
                    gressint = (tCompleteInt/(returnVec.size()*4)) * 100;
                    %>
                    <td class=tdwhiteM>&nbsp;<%=gressint==0.0?"":gressint%>%</td>
                </tr>
                <% } else { %>
                <tr>
                    <td class=tb_gray align=center colspan="4" ><font color="red">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
                </tr>
                <% } %>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="space20"></td>
    </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="100%">
    <tr>
        <td valign="top" style="padding-left:12">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" align=center width="100%">
                <tr bgcolor="#D6DBE7" align=center>
                    <td class=tdblueM rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%><br>/<br><%=messageService.getString("e3ps.message.ket_message", "01345") %><%--라인번호--%></td>
                    <td class=tdblueM rowspan=2>WEB Code</td>
                    <td class=tdblueM rowspan=2><%=messageService.getString("e3ps.message.ket_message", "01299") %><%--도번(도면 명)--%></td>
                    <td class=tdblueM rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
                    <td class=tdblueM rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02110") %><%--업체명--%></td>
                    <td class=tdblueM colspan=5><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
                    <td class=tdblueM rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
                    <td class=tdblueM colspan=2><%=messageService.getString("e3ps.message.ket_message", "01987") %><%--승인--%></td>
                </tr>
                <tr>
                    <td class=tdblueM>&nbsp;</td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01473") %><%--발주일--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01840") %><%--설계--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
                    <td class=tdblueM>TRY<br>/<br><%=messageService.getString("e3ps.message.ket_message", "01891") %><%--설치--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02535") %><%--제출일--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                </tr>
                    <%
                    for(int i = 0; i < returnVec.size(); i++) {
                        String[] valueStr = (String[]) returnVec.get(i);

                        String anln1 = valueStr[0];
                        String w_pspnr = valueStr[1];
                        String usr03 = valueStr[2];
                        String maktx = valueStr[3];
                        String usr02 = valueStr[4];
                        String lifnr = valueStr[5];
                        String name1 = valueStr[6];
                        String p_date1 = valueStr[7];
                        String r_date1 = valueStr[8];
                        String p_date2 = valueStr[9];
                        String r_date2 = valueStr[10];
                        String p_date3 = valueStr[11];
                        String r_date3 = valueStr[12];
                        String p_date4 = valueStr[13];
                        String r_date4 = valueStr[14];

                        if(p_date1.equalsIgnoreCase("00000000")) p_date1 = "";
                        if(r_date1.equalsIgnoreCase("00000000")) r_date1 = "";
                        if(p_date2.equalsIgnoreCase("00000000")) p_date2 = "";
                        if(r_date2.equalsIgnoreCase("00000000")) r_date2 = "";
                        if(p_date3.equalsIgnoreCase("00000000")) p_date3 = "";
                        if(r_date3.equalsIgnoreCase("00000000")) r_date3 = "";
                        if(p_date4.equalsIgnoreCase("00000000")) p_date4 = "";
                        if(r_date4.equalsIgnoreCase("00000000")) r_date4 = "";

                    %>
                <tr bgcolor="#D6DBE7" align=center>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=StringUtil.checkNull(anln1)%></td>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=StringUtil.checkNull(w_pspnr)%></td>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=StringUtil.checkNull(usr03)%><br>[<%=StringUtil.checkNull(maktx)%>]</td>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=StringUtil.checkNull(usr02)%></td>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=StringUtil.checkNull(name1)%></td>
                    <!-- 진행상태 (colspan=5) Start -->
                    <td class=tdwhiteM>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(p_date1)%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(p_date2)%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(p_date3)%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(p_date4)%></td>
                    <!-- 진행상태 (colspan=5) End -->
                    <%
                    int gressInt = 0;
                    double completeInt = 0.0;

                    if(StringUtil.checkString(r_date1)) {
                        completeInt++;
                    } else if(StringUtil.checkString(r_date2)) {
                        completeInt++;
                    } else if(StringUtil.checkString(r_date3)) {
                        completeInt++;
                    } else if(StringUtil.checkString(r_date4)) {
                        completeInt++;
                    }
                    //out.println("completeInt>>> "+completeInt);
                    //out.println("completeInt/4>>> "+(completeInt/4));
                    gressInt = (int) ((completeInt/4) * 100);
                    %>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=gressInt%>%</td>
                    <!-- 승인 (colspan=2) Start -->
                    <%
                    try {
                        //String[] aa = mrmUtil.getMoldComplete("202007665");  //통테 Demo Data
                        String[] aa = mrmUtil.getMoldComplete(StringUtil.checkNull(usr03));
                    %>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=aa!=null?StringUtil.checkNull(aa[0]):""%></td>
                    <td class=tdwhiteM rowspan=3>&nbsp;<%=aa!=null?StringUtil.checkNull(aa[1]):""%></td>
                    <%
                    } catch(Exception e) {
                	Kogger.error(e);
                    }
                    %>
                    <!-- 승인 (colspan=2) End -->
                </tr>
                <tr>
                    <td class=tdwhiteM>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(r_date1)%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(r_date2)%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(r_date3)%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(r_date4)%></td>
                </tr>
                <tr>
                    <td class=tdwhiteM>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkString(r_date1)? messageService.getString("e3ps.message.ket_message", "02171")/*완료*/ : messageService.getString("e3ps.message.ket_message", "02735")/*진행중*/ %></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkString(r_date2)? messageService.getString("e3ps.message.ket_message", "02171")/*완료*/ : messageService.getString("e3ps.message.ket_message", "02735")/*진행중*/ %></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkString(r_date3)? messageService.getString("e3ps.message.ket_message", "02171")/*완료*/ : messageService.getString("e3ps.message.ket_message", "02735")/*진행중*/ %></td>
                    <td class=tdwhiteM>&nbsp;<%=StringUtil.checkString(r_date4)? messageService.getString("e3ps.message.ket_message", "02171")/*완료*/ : messageService.getString("e3ps.message.ket_message", "02735")/*진행중*/ %></td>
                </tr>
                    <%
                    }

                    if(returnVec.size() == 0 ){
                    %>
                <tr bgcolor=white>
                    <td class=tb_gray align=center colspan="13" ><font color="red">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
                </tr>
                    <%
                    }
                    %>
            </table>
        </td>
    </tr>
</table>



</form>

</body>
</html>
