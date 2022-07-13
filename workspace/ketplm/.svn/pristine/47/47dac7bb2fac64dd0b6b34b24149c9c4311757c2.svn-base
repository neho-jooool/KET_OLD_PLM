<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportDetailVO" %>
<%@page import="e3ps.common.web.PageControl" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoReportVO" scope="request" />

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00184") %><%--ECO 검색--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }
-->

.numberCut{
width:95;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

.nameCut{
width:180;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}
</style>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript">
//부품 상세조회 팝업
function viewPart2(v_poid){
    var url = "/plm/jsp/bom/ViewPart_eco.jsp?poid=" + v_poid;
    openWindow(url,"","750","650","scrollbars=no,resizable=no,top=200,left=250");
}
</script>
</head>
<body>
<%
PageControl control = data.getPageControl();
int perPage = 10;
if( control != null )
{
    perPage = control.getInitPerPage();
}

int cols = 11;
int col1 = 0;
int col2 = 0;
String ecoReasonTypeCls = "C";
String prodMoldCls = "1";
if(data.getTotalCount() > 0) {
    ecoReasonTypeCls = data.getEcoReasonTypeCls();
    prodMoldCls = data.getProdMoldCls();
}

int pagerow = 0;
if(data.getTotalCount() > 0) {
    pagerow = data.getResultRows();
}
int tableWidth = 780;
int lastColumn = 55;
if (pagerow > 10) {
    tableWidth = 762;
    lastColumn = 37;
}
%>
<%
if( "C".equals(ecoReasonTypeCls)) {
%>
<div id="div_scroll" style="width:780px; height:271px; overflow:auto;">
<%
}else if("I".equals(ecoReasonTypeCls)) {
%>
<div id="div_scroll" style="width:780px; height:259px; overflow:auto;">
<%
}
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr class="headerLock">
      <td>
          <%
            if( "C".equals(ecoReasonTypeCls) && prodMoldCls.equals("1") ) {
              if (pagerow > 10) {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='762'  style=table-layout:fixed >");
                out.println("<col width='40'><col width='100'><col width='181'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'>");
              } else {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='780'  style=table-layout:fixed >");
              out.println("<col width='40'><col width='100'><col width='181'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'>");
              }
            }else if( "C".equals(ecoReasonTypeCls) && prodMoldCls.equals("2") ){
                if (pagerow > 10) {
                out.println("<table border='0' cellspacing='0' cellpadding='0' width='762'  style=table-layout:fixed >");
                out.println("<col width='40'><col width='45'><col width='95'><col width='154'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='50'><col width='42'><col width='42'>");
              } else {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='780'  style=table-layout:fixed >");
              out.println("<col width='40'><col width='45'><col width='95'><col width='172'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='50'><col width='42'><col width='42'>");
              }
            }else if("I".equals(ecoReasonTypeCls)) {
                if (pagerow > 10) {
                out.println("<table border='0' cellspacing='0' cellpadding='0' width='762'  style=table-layout:fixed >");
                out.println("<col width='40'><col width='40'><col width='85'><col width='82'><col width='35'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'>");
              } else {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='780'  style=table-layout:fixed >");
              out.println("<col width='40'><col width='40'><col width='85'><col width='100'><col width='35'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'>");
              }
            }
            %>
          <%
                if("C".equals(ecoReasonTypeCls) && prodMoldCls.equals("2") ) {
                %>
            <tr>
                  <td height="30" rowspan="2" class="tdblueM">No</td>
                  <td rowspan="2" class="tdblueM">Type</td>
                  <td rowspan="2"  class="tdblueM">Die No</td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                  <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00699") %><%--건수--%></td>
                  <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00850") %><%--고객요청--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03024") %><%--품질--%><br><%=messageService.getString("e3ps.message.ket_message", "01437") %><%--문제--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><br><%=messageService.getString("e3ps.message.ket_message", "01871") %><%--설변--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%><br><%=messageService.getString("e3ps.message.ket_message", "01531") %><%--보완--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01784", "<br>") %><%--생산성{0} 향상--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%><br><%=messageService.getString("e3ps.message.ket_message", "02511") %><%--정리--%></td>
                  <td rowspan="2"  class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%></td>
                </tr>
                <%
                    cols = 14;
                }else if( "C".equals(ecoReasonTypeCls) && prodMoldCls.equals("1") ){
                %>
            <tr>
                  <td height="30" rowspan="2" class="tdblueM">No</td>
                  <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                  <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                  <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00699") %><%--건수--%></td>
                  <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
                  <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00850") %><%--고객요청--%></td>
                  <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01784", "<br>") %><%--생산성{0} 향상--%></td>
                  <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03024") %><%--품질--%><br><%=messageService.getString("e3ps.message.ket_message", "01437") %><%--문제--%></td>
                  <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02099") %><%--양산이관--%></td>
                  <td rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%></td>
                </tr>
                <%
                    cols = 12;
                } else if("I".equals(ecoReasonTypeCls)) {
                %>
            <tr>
                  <td height="30" rowspan="2"  class="tdblueM">No</td>
                  <td rowspan="2" class="tdblueM">Type</td>
                  <td rowspan="2"  class="tdblueM">Die No</td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00699") %><%--건수--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01451") %><%--미성형--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><br><%=messageService.getString("e3ps.message.ket_message", "02902") %><%--치수--%></td>
                  <td rowspan="2"  class="tdblueM">BURR</td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><br><%=messageService.getString("e3ps.message.ket_message", "02295") %><%--이송--%></td>
                  <td rowspan="2"  class="tdblueM">WELD</td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02389") %><%--자국--%><br><%=messageService.getString("e3ps.message.ket_message", "01470") %><%--발생--%></td>
                  <td rowspan="2"  class="tdblueM">GAS</td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01985") %><%=messageService.getString("e3ps.message.ket_message", "01986", "<br>") %><%--스크랩{0} 상승--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><br><%=messageService.getString("e3ps.message.ket_message", "02896") %><%--취출--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01564") %><%--부품--%><br><%=messageService.getString("e3ps.message.ket_message", "02955") %><%--파손--%></td>
                  <td rowspan="2"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%><br><%=messageService.getString("e3ps.message.ket_message", "02637") %><%--조립--%></td>
                  <td rowspan="2"  class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%></td>
                </tr>
                <%
                cols = 17;
                }
                %>
            </tr>
          <tr>
                <%
                if("C".equals(ecoReasonTypeCls))
                {
                %>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00582") %><%--개발--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02078") %><%--양산--%></td>
                    <%
                    if(prodMoldCls.equals("1")){
                    %>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%></td>
                    <td class="tdblueM">BOM</td>
                    <%
                    }
                    %>
                <%
                }
                %>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <%
            if( "C".equals(ecoReasonTypeCls) && prodMoldCls.equals("1") ) {
              if (pagerow > 10) {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='762'  style=table-layout:fixed >");
                out.println("<col width='40'><col width='100'><col width='181'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'><col width='49'>");
              } else {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='780'  style=table-layout:fixed >");
              out.println("<col width='40'><col width='100'><col width='181'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'><col width='51'>");
              }
            }else if( "C".equals(ecoReasonTypeCls) && prodMoldCls.equals("2") ){
                if (pagerow > 10) {
                out.println("<table border='0' cellspacing='0' cellpadding='0' width='762'  style=table-layout:fixed >");
                out.println("<col width='40'><col width='45'><col width='95'><col width='154'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='50'><col width='42'><col width='42'>");
              } else {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='780'  style=table-layout:fixed >");
              out.println("<col width='40'><col width='45'><col width='95'><col width='172'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='42'><col width='50'><col width='42'><col width='42'>");
              }
            }else if("I".equals(ecoReasonTypeCls)) {
                if (pagerow > 10) {
                out.println("<table border='0' cellspacing='0' cellpadding='0' width='762'  style=table-layout:fixed >");
                out.println("<col width='40'><col width='40'><col width='85'><col width='82'><col width='35'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'>");
              } else {
              out.println("<table border='0' cellspacing='0' cellpadding='0' width='780'  style=table-layout:fixed >");
              out.println("<col width='40'><col width='40'><col width='85'><col width='100'><col width='35'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'><col width='40'>");
              }
            }
            %>
                <%
                if(data.getTotalCount() > 0) {
                    int size = data.getResultRows();
                    KETSearchEcoReportDetailVO ketSearchEcoReportDetailVO = null;
                    int rowCount = (control.getCurrentPage() - 1) * control.getInitPerPage();
                    for(int i=0; i<size; i++) {
                        ketSearchEcoReportDetailVO =  data.getKetSearchEcoReportDetailVOList().get(i);
                        rowCount++;

                if("C".equals(ecoReasonTypeCls)) {
                %>
                <tr>
                    <td class="tdwhiteM"><%=rowCount%>&nbsp;</td>
                    <% if(prodMoldCls.equals("2")){
                            if( ketSearchEcoReportDetailVO.getObjectNo().substring(0, 2).equals("29") )
                            {
                    %>
                    <td class="tdwhiteM">Press&nbsp;</td>
                    <%
                            }
                            else
                            {
                    %>
                    <td class="tdwhiteM"><%=StringUtil.checkReplaceStr(ketSearchEcoReportDetailVO.getObjType(),"-")%>&nbsp;</td>
                    <%
                            }
                        }
                    %>
                    <td class="tdwhiteL" title='<%=ketSearchEcoReportDetailVO.getObjectNo() %>' style="text-overflow:ellipsis;overflow:hidden;"><nobr><a href="javascript:viewPart2('<%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getOid())%>');"><%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getObjectNo())%></a>&nbsp;</nobr></td>
                    <td class="tdwhiteL" title='<%=ketSearchEcoReportDetailVO.getObjectName() %>' style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getObjectName())%>&nbsp;</nobr></td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getEcoCount())%>&nbsp;</td>

                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDevEcoCnt())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getProdEcoCnt())%>&nbsp;</td>

                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt1())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt2())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt3())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt4())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt5())%>&nbsp;</td>

                    <%if( prodMoldCls.equals("2")) {%>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt6())%>&nbsp;</td>
                    <td class="tdwhiteR0"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt7())%>&nbsp;</td>
                    <%}else{%>
                        <td class="tdwhiteR0"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt6())%>&nbsp;</td>
                    <%
                        }
                    %>
                </tr>
                <%
                } else if("I".equals(ecoReasonTypeCls)) {
                %>
                <tr>
                    <td class="tdwhiteM"><%=rowCount%></td>
                    <% if(prodMoldCls.equals("2")){ %>
                    <td class="tdwhiteM"><%=StringUtil.checkReplaceStr(ketSearchEcoReportDetailVO.getObjType(),"-")%>&nbsp;</td>
                    <%} %>
                    <td class="tdwhiteL" title='<%=ketSearchEcoReportDetailVO.getObjectNo() %>' style="text-overflow:ellipsis;overflow:hidden;"><nobr><a href="javascript:viewPart2('<%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getOid())%>');"><%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getObjectNo())%></a>&nbsp;</nobr></td>
                    <td class="tdwhiteL" title='<%=ketSearchEcoReportDetailVO.getObjectName() %>' style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getObjectName())%></nobr></td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getEcoCount())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt1())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt2())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt3())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt4())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt5())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt6())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt7())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt8())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt9())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt10())%>&nbsp;</td>
                    <td class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt11())%>&nbsp;</td>
                    <td class="tdwhiteR0"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt12())%>&nbsp;</td>
                </tr>
                <%
                }
                    }
                } else {//자료가 존재하지 않은 경우
                %>
                <tr>
                    <td colspan="<%=cols%>" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
                </tr>
                <%
                }
                %>
            </table>
        </td>
    </tr>
</table>
</div>

<%if(control != null) {%>
<%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
<%}%>
