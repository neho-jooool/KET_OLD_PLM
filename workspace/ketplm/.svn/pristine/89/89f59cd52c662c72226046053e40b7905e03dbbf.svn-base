<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.Vector"%>
<%@page import="e3ps.sap.PJTInfoERPInterface"%>
<%@page import="java.util.Hashtable"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00958") %><%--구매 정보--%> </title>
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
                <td class="space20"></td>
            </tr>
        </table>
        <table width=100% height=40 align=center border=0>
            <tr>
                <td valign="top" style="padding:0px 0px 0px 0px">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                      <tr>
                        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00958") %><%--구매 정보--%></td>
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



<%

//LTXA1  작업내역
//MATNR  자재 번호
//MAKTX  자재내역
//RSNUM  예약 번호
//BDTER  요청일
//BDMNG  소요량
//MEINS  기본 단위
//ERDAT  도면 출도일
//PETRF  출도완료 예상종료일
//EBELN  구매 문서 번호
//BEDAT  발주일
//EINDT  PO 납기일
//BUDAT  입고일
//ERFMG  입고수량

    String projectNo = request.getParameter("projectNo");
    Vector vt = new Vector();
%>


        <table width="100%" cellspacing="1" cellpadding="1" border="0" align=center bgcolor=AABDC6>
            <tr bgcolor="#D6DBE7" align=center>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02434") %><%--작업 내역--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02421") %><%--자재번호--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02420") %><%--자재내역--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02158") %><%--예약 번호--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02194") %><%--요청일--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01919") %><%--소요량--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01260") %><%--도면 출도일--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02879") %><%--출도완료 예상종료일--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "00957") %><%--구매 문서 번호--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01473") %><%--발주일--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "00384") %><%--PO 납기일--%> </td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02379") %><%--입고일--%></td>
                <td id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02378") %><%--입고수량--%></td>
            </tr>
        <%

            vt = PJTInfoERPInterface.getPurchaseData(projectNo);
            Hashtable hash = null;
            for(int i = 0; i < vt.size(); i++){
                hash = (Hashtable) vt.get(i);

        %>
            <tr bgcolor=white>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("LTXA1")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("MATNR")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("MAKTX")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("RSNUM")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("BDTER")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("BDMNG")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("MEINS")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("ERDAT")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("PETRF")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("EBELN")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("BEDAT")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("EINDT")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("BUDAT")%></td>
                <td id=tb_gray align=center>&nbsp;<%=hash.get("ERFMG")%></td>


            </tr>
        <%}

            if(vt.size() == 0 ){
        %>
            <tr bgcolor=white>
                <td id=tb_gray align=center colspan="14" ><font color="red">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
            </tr>
        <%} %>
        </table>
    </form>

</body>
</html>
