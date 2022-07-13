<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.Weights"%>
<%@page import="e3ps.project.beans.PerformanceHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String oid = StringUtil.checkNull( request.getParameter("oid"));

    Weights wg = null;
    Weights wg1 = null;
    Weights wg2 = null;
    Weights wg3 = null;
    Weights wg4 = null;
    Weights wg5 = null;
    String oid1 = "";
    String oid2 = "";
    String oid3 = "";
    String oid4 = "";
    String oid5 = "";
    if(oid.length() > 0) {

            wg =  (Weights)CommonUtil.getObject(oid);
            //자동차 사업부 커텍터
            if(wg.getWType() == 1){
                wg1 = wg;
                oid1 = PersistenceHelper.getObjectIdentifier ( wg ).getStringValue ();
            }
            //자동차 사업부 모듈
            if(wg.getWType() == 2){
                wg2 = wg;
                oid2 = PersistenceHelper.getObjectIdentifier ( wg ).getStringValue ();
            }
            //금형
            if(wg.getWType() == 3){
                wg3 = wg;
                oid3 = PersistenceHelper.getObjectIdentifier ( wg ).getStringValue ();
            }
            //전자 사업부 커텍터
            if(wg.getWType() == 4){
                wg4 = wg;
                oid4 = PersistenceHelper.getObjectIdentifier ( wg ).getStringValue ();
            }
            //전자 사업부 모듈
            if(wg.getWType() == 5){
                wg5 = wg;
                oid5 = PersistenceHelper.getObjectIdentifier ( wg ).getStringValue ();
            }
    }

%>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>

<style type="text/css">
<!--
body {
    margin-left: 12px;
    margin-top: 15px;
    margin-right: 5px;
    margin-bottom: 5px;

    overflow-x:;
    overflow-y:;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}
-->
</style>
<script language="JavaScript">

    function onSavePopup(oid, wtype ){
        var url = "/plm/jsp/project/performance/CreateValuation.jsp?oid="+oid+"&wType="+wtype;
        openSameName(url,"800","500","status=no,scrollbars=no,resizable=yes");
    }

</script>
</head>



<body>
<form>
<!-- hidden begin -->
<input type="hidden" name="cmd" value="">
<!-- hidden end -->

<!-- ############################################################################################################ START -->
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
            <table width="780" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/logo_popupbg.png">
                      <table height="28" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02985") %><%--평가 기준 보기--%></td>
                            <td width="10"></td>
                          </tr>
                      </table>
                      </td>
                      <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                  </table>
                </td>
            </tr>
            <tr>
              <td class="space10"></td>
            </tr>
              </table>
         </td>
     </tr>

<!-- ############################################################################################################ START -->
     <tr>
         <td>
<%
        if(wg1!= null){
%>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <%if(false){ %>
            <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:onSavePopup('<%=oid1%>','1');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                      </table>
                </td>
              </tr>
            </table>
            <%} %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>

            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02394") %><%--자동차 사업부 제품(커넥터)--%></td>
              </tr>
            </table>

            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="28%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdwhiteM">&nbsp;100~<%=wg1.getTotalS()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg1.getTotalS()-1%>~<%=wg1.getTotalA()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg1.getTotalA()-1%>~<%=wg1.getTotalB()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg1.getTotalB()-1%>~<%=wg1.getTotalC()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg1.getTotalD()%>~</td>
              </tr>
              <tr>
                <td class="tdblueM">Quality </td>
                <td class="tdwhiteM">&nbsp;100~<%=(wg1.getQS()==null)?"0": wg1.getQS()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg1.getQS()==null)?"0": Integer.parseInt(wg1.getQS())-1%>~<%=(wg1.getQA()==null)?"0": wg1.getQA()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg1.getQA()==null)?"0": Integer.parseInt(wg1.getQA())-1%>~<%=(wg1.getQB()==null)?"0": wg1.getQB()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg1.getQB()==null)?"0": Integer.parseInt(wg1.getQB())-1%>~<%=(wg1.getQC()==null)?"0": wg1.getQC()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg1.getQD()==null)?"0": wg1.getQD()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                  <td class="tdwhiteM">&nbsp;<%=wg1.getQuality()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Cost </td>
                <td class="tdwhiteM"> &nbsp;~ <%=(wg1.getCS()==null)?"0":wg1.getCS()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getCS()==null)?"0":Integer.parseInt(wg1.getCS())-1%>~<%=(wg1.getCA()==null)?"0":wg1.getCA()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getCA()==null)?"0":Integer.parseInt(wg1.getCA())-1%>~<%=(wg1.getCB()==null)?"0":wg1.getCB()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getCB()==null)?"0":Integer.parseInt(wg1.getCB())-1%>~<%=(wg1.getCC()==null)?"0":wg1.getCC()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getCD()==null)?"0":wg1.getCD()%>%~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg1.getCost()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Delivery </td>
                <td class="tdwhiteM"> &nbsp;~<%=(wg1.getDOneS()==null)?"":wg1.getDOneS()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getDOneS()==null)?"":Integer.parseInt(wg1.getDOneS())+1%>~<%=(wg1.getDOneA()==null)?"":wg1.getDOneA()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getDOneA()==null)?"":Integer.parseInt(wg1.getDOneA())+1%>~<%=(wg1.getDOneB()==null)?"":wg1.getDOneB()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getDOneB()==null)?"":Integer.parseInt(wg1.getDOneB())+1%>~<%=(wg1.getDOneC()==null)?"":wg1.getDOneC()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getDOneD()==null)?"":wg1.getDOneD()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg1.getDeliveryOne()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Technical </td>
                <td class="tdwhiteM"> &nbsp;100~<%=(wg1.getTS()==null)?"":wg1.getTS()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getTS()==null)?"":Integer.parseInt(wg1.getTS())-1%>~<%=(wg1.getTA()==null)?"":wg1.getTA()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getTA()==null)?"":Integer.parseInt(wg1.getTA())-1%>~<%=(wg1.getTB()==null)?"":wg1.getTB()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getTB()==null)?"":Integer.parseInt(wg1.getTB())-1%>~<%=(wg1.getTC()==null)?"":wg1.getTC()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg1.getTD()==null)?"":wg1.getTD()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg1.getTechnical()%>%</td>
              </tr>
            </table>
        <%} %>
<%if(wg2 != null){ %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <%if(false){ %>
            <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:onSavePopup('<%=oid2%>','2');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                      </table>
                </td>
              </tr>
            </table>
            <%} %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02393") %><%--자동차 사업부 제품(모듈)--%></td>
              </tr>
            </table>

            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="28%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdwhiteM">&nbsp;100~<%=wg2.getTotalS()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg2.getTotalS()-1%>~<%=wg2.getTotalA()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg2.getTotalA()-1%>~<%=wg2.getTotalB()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg2.getTotalB()-1%>~<%=wg2.getTotalC()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg2.getTotalD()%>~</td>
              </tr>
              <tr>
                <td class="tdblueM">Quality </td>
                <td class="tdwhiteM">&nbsp;100~<%=(wg2.getQS()==null)?"0": wg2.getQS()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg2.getQS()==null)?"0": Integer.parseInt(wg2.getQS())-1%>~<%=(wg2.getQA()==null)?"0": wg2.getQA()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg2.getQA()==null)?"0": Integer.parseInt(wg2.getQA())-1%>~<%=(wg2.getQB()==null)?"0": wg2.getQB()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg2.getQB()==null)?"0": Integer.parseInt(wg2.getQB())-1%>~<%=(wg2.getQC()==null)?"0": wg2.getQC()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg2.getQD()==null)?"0": wg2.getQD()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                  <td class="tdwhiteM">&nbsp;<%=wg2.getQuality()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Cost </td>
                <td class="tdwhiteM"> &nbsp;~ <%=(wg2.getCS()==null)?"0":wg2.getCS()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getCS()==null)?"0":Integer.parseInt(wg2.getCS())-1%>~<%=(wg2.getCA()==null)?"0":wg2.getCA()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getCA()==null)?"0":Integer.parseInt(wg2.getCA())-1%>~<%=(wg2.getCB()==null)?"0":wg2.getCB()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getCB()==null)?"0":Integer.parseInt(wg2.getCB())-1%>~<%=(wg2.getCC()==null)?"0":wg2.getCC()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getCD()==null)?"0":wg2.getCD()%>%~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg2.getCost()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Delivery </td>
                <td class="tdwhiteM"> &nbsp;~<%=(wg2.getDOneS()==null)?"":wg2.getDOneS()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getDOneS()==null)?"":Integer.parseInt(wg2.getDOneS())+1%>~<%=(wg2.getDOneA()==null)?"":wg2.getDOneA()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getDOneA()==null)?"":Integer.parseInt(wg2.getDOneA())+1%>~<%=(wg2.getDOneB()==null)?"":wg2.getDOneB()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getDOneB()==null)?"":Integer.parseInt(wg2.getDOneB())+1%>~<%=(wg2.getDOneC()==null)?"":wg2.getDOneC()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getDOneD()==null)?"":wg2.getDOneD()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg2.getDeliveryOne()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Technical </td>
                <td class="tdwhiteM"> &nbsp;100~<%=(wg2.getTS()==null)?"":wg2.getTS()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getTS()==null)?"":Integer.parseInt(wg2.getTS())-1%>~<%=(wg2.getTA()==null)?"":wg2.getTA()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getTA()==null)?"":Integer.parseInt(wg2.getTA())-1%>~<%=(wg2.getTB()==null)?"":wg2.getTB()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getTB()==null)?"":Integer.parseInt(wg2.getTB())-1%>~<%=(wg2.getTC()==null)?"":wg2.getTC()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg2.getTD()==null)?"":wg2.getTD()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg2.getTechnical()%>%</td>
              </tr>

            </table>
        <%} %>
<%if(wg3 != null){  %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <%if(false){ %>
            <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:onSavePopup('<%=oid3%>','3');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                      </table>
                </td>
              </tr>
            </table>
            <%} %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02392") %><%--자동차 사업부 금형--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="14%"><COL width="14%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" colspan=2 rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdwhiteM">&nbsp;100~<%=wg3.getTotalS()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg3.getTotalS()-1%>~<%=wg3.getTotalA()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg3.getTotalA()-1%>~<%=wg3.getTotalB()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg3.getTotalB()-1%>~<%=wg3.getTotalC()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg3.getTotalD()%>~</td>
              </tr>
              <tr>
                <td class="tdblueM" colspan=2 >Cost </td>
                <td class="tdwhiteM"> &nbsp;~ <%=(wg3.getCS()==null)?"0":wg3.getCS()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getCS()==null)?"0":Integer.parseInt(wg3.getCS())-1%>~<%=(wg3.getCA()==null)?"0":wg3.getCA()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getCA()==null)?"0":Integer.parseInt(wg3.getCA())-1%>~<%=(wg3.getCB()==null)?"0":wg3.getCB()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getCB()==null)?"0":Integer.parseInt(wg3.getCB())-1%>~<%=(wg3.getCC()==null)?"0":wg3.getCC()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getCD()==null)?"0":wg3.getCD()%>%~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg3.getCost()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM" rowspan=3>Delivery </td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01023") %><%--금형 제작--%></td>
                <td class="tdwhiteM"> &nbsp;~<%=(wg3.getDOneS()==null)?"":wg3.getDOneS()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDOneS()==null)?"":Integer.parseInt(wg3.getDOneS())+1%>~<%=(wg3.getDOneA()==null)?"":wg3.getDOneA()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDOneA()==null)?"":Integer.parseInt(wg3.getDOneA())+1%>~<%=(wg3.getDOneB()==null)?"":wg3.getDOneB()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDOneB()==null)?"":Integer.parseInt(wg3.getDOneB())+1%>~<%=(wg3.getDOneC()==null)?"":wg3.getDOneC()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDOneD()==null)?"":wg3.getDOneD()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg3.getDeliveryOne()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01339") %><%--디버깅--%> </td>
                <td class="tdwhiteM"> &nbsp;~<%=(wg3.getDTwoS()==null)?"":wg3.getDTwoS()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDTwoS()==null)?"":Integer.parseInt(wg3.getDTwoS())+1%>~<%=(wg3.getDTwoA()==null)?"":wg3.getDTwoA()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDTwoA()==null)?"":Integer.parseInt(wg3.getDTwoA())+1%>~<%=(wg3.getDTwoB()==null)?"":wg3.getDTwoB()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDTwoB()==null)?"":Integer.parseInt(wg3.getDTwoB())+1%>~<%=(wg3.getDTwoC()==null)?"":wg3.getDTwoC()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDTwoD()==null)?"":wg3.getDTwoD()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg3.getDeliveryTwo()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01095") %><%--금형이관--%></td>
                <td class="tdwhiteM"> &nbsp;~<%=(wg3.getDThreeS()==null)?"":wg3.getDThreeS()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDThreeS()==null)?"":Integer.parseInt(wg3.getDThreeS())+1%>~<%=(wg3.getDThreeA()==null)?"":wg3.getDThreeA()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDThreeA()==null)?"":Integer.parseInt(wg3.getDThreeA())+1%>~<%=(wg3.getDThreeB()==null)?"":wg3.getDThreeB()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDThreeB()==null)?"":Integer.parseInt(wg3.getDThreeB())+1%>~<%=(wg3.getDThreeC()==null)?"":wg3.getDThreeC()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg3.getDThreeD()==null)?"":wg3.getDThreeD()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg3.getDeliveryThree()%>%</td>
              </tr>
            </table>

        <%} %>
<%
        if(wg4!= null){
%>

            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <%if(false){ %>
            <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:onSavePopup('<%=oid4%>','4');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                      </table>
                </td>
              </tr>
            </table>
            <%} %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02480") %><%--전자 사업부 제품(커넥터)--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="28%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdwhiteM">&nbsp;100~<%=wg4.getTotalS()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg4.getTotalS()-1%>~<%=wg4.getTotalA()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg4.getTotalA()-1%>~<%=wg4.getTotalB()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg4.getTotalB()-1%>~<%=wg4.getTotalC()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg4.getTotalD()%>~</td>
              </tr>
              <tr>
                <td class="tdblueM">Quality </td>
                <td class="tdwhiteM">&nbsp;100~<%=(wg4.getQS()==null)?"0": wg4.getQS()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg4.getQS()==null)?"0": Integer.parseInt(wg4.getQS())-1%>~<%=(wg4.getQA()==null)?"0": wg4.getQA()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg4.getQA()==null)?"0": Integer.parseInt(wg4.getQA())-1%>~<%=(wg4.getQB()==null)?"0": wg4.getQB()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg4.getQB()==null)?"0": Integer.parseInt(wg4.getQB())-1%>~<%=(wg4.getQC()==null)?"0": wg4.getQC()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg4.getQD()==null)?"0": wg4.getQD()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                  <td class="tdwhiteM">&nbsp;<%=wg4.getQuality()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Cost </td>
                <td class="tdwhiteM"> &nbsp;~ <%=(wg4.getCS()==null)?"0":wg4.getCS()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getCS()==null)?"0":Integer.parseInt(wg4.getCS())-1%>~<%=(wg4.getCA()==null)?"0":wg4.getCA()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getCA()==null)?"0":Integer.parseInt(wg4.getCA())-1%>~<%=(wg4.getCB()==null)?"0":wg4.getCB()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getCB()==null)?"0":Integer.parseInt(wg4.getCB())-1%>~<%=(wg4.getCC()==null)?"0":wg4.getCC()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getCD()==null)?"0":wg4.getCD()%>%~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg4.getCost()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Delivery </td>
                <td class="tdwhiteM"> &nbsp;~<%=(wg4.getDOneS()==null)?"":wg4.getDOneS()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getDOneS()==null)?"":Integer.parseInt(wg4.getDOneS())+1%>~<%=(wg4.getDOneA()==null)?"":wg4.getDOneA()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getDOneA()==null)?"":Integer.parseInt(wg4.getDOneA())+1%>~<%=(wg4.getDOneB()==null)?"":wg4.getDOneB()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getDOneB()==null)?"":Integer.parseInt(wg4.getDOneB())+1%>~<%=(wg4.getDOneC()==null)?"":wg4.getDOneC()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg4.getDOneD()==null)?"":wg4.getDOneD()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg4.getDeliveryOne()%>%</td>
              </tr>

            </table>
        <%} %>
<%if(wg5 != null){ %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <%if(false){ %>
            <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:onSavePopup('<%=oid5%>','5');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                      </table>
                </td>
              </tr>
            </table>
            <%} %>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02479") %><%--전자 사업부 제품(모듈)--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="28%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdwhiteM">&nbsp;100~<%=wg5.getTotalS()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg5.getTotalS()-1%>~<%=wg5.getTotalA()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg5.getTotalA()-1%>~<%=wg5.getTotalB()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg5.getTotalB()-1%>~<%=wg5.getTotalC()%> </td>
                <td class="tdwhiteM">&nbsp;<%=wg5.getTotalD()%>~</td>
              </tr>
              <tr>
                <td class="tdblueM">Quality </td>
                <td class="tdwhiteM">&nbsp;100~<%=(wg5.getQS()==null)?"0": wg5.getQS()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg5.getQS()==null)?"0": Integer.parseInt(wg5.getQS())-1%>~<%=(wg5.getQA()==null)?"0": wg5.getQA()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg5.getQA()==null)?"0": Integer.parseInt(wg5.getQA())-1%>~<%=(wg5.getQB()==null)?"0": wg5.getQB()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg5.getQB()==null)?"0": Integer.parseInt(wg5.getQB())-1%>~<%=(wg5.getQC()==null)?"0": wg5.getQC()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdwhiteM">&nbsp;<%=(wg5.getQD()==null)?"0": wg5.getQD()%><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                  <td class="tdwhiteM">&nbsp;<%=wg5.getQuality()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Cost </td>
                <td class="tdwhiteM"> &nbsp;~ <%=(wg5.getCS()==null)?"0":wg5.getCS()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getCS()==null)?"0":Integer.parseInt(wg5.getCS())-1%>~<%=(wg5.getCA()==null)?"0":wg5.getCA()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getCA()==null)?"0":Integer.parseInt(wg5.getCA())-1%>~<%=(wg5.getCB()==null)?"0":wg5.getCB()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getCB()==null)?"0":Integer.parseInt(wg5.getCB())-1%>~<%=(wg5.getCC()==null)?"0":wg5.getCC()%>%</td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getCD()==null)?"0":wg5.getCD()%>%~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg5.getCost()%>%</td>
              </tr>
              <tr>
                <td class="tdblueM">Delivery </td>
                <td class="tdwhiteM"> &nbsp;~<%=(wg5.getDOneS()==null)?"":wg5.getDOneS()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getDOneS()==null)?"":Integer.parseInt(wg5.getDOneS())+1%>~<%=(wg5.getDOneA()==null)?"":wg5.getDOneA()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getDOneA()==null)?"":Integer.parseInt(wg5.getDOneA())+1%>~<%=(wg5.getDOneB()==null)?"":wg5.getDOneB()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getDOneB()==null)?"":Integer.parseInt(wg5.getDOneB())+1%>~<%=(wg5.getDOneC()==null)?"":wg5.getDOneC()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdwhiteM"> &nbsp;<%=(wg5.getDOneD()==null)?"":wg5.getDOneD()%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdwhiteM"> &nbsp;<%=wg5.getDeliveryOne()%>%</td>
              </tr>

            </table>
        <%} %>
        </td>
    </tr>
<!-- ############################################################################################################ END -->
    <tr>
      <td height="30" valign="bottom">
          <iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
      </td>
    </tr>
</table>
<!-- ############################################################################################################ END -->
</form>
</body>

</html>
