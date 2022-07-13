<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrder" %>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrderVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLink" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECALinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldEcoEcrLinkVO" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.ArrayList" %>

<%@page import="wt.content.ContentHolder
                            ,wt.content.ContentHelper" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.ecm.servlet.MoldEcoServlet" %>
<%@page import="e3ps.common.util.CommonUtil" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<jsp:useBean id="ketMoldChangeOrderVO" class="e3ps.ecm.entity.KETMoldChangeOrderVO" scope="request" />
<%
String fromPage = StringUtil.checkNull(request.getParameter("fromPage"));
Kogger.debug("fromPage:" + fromPage);
int size = 0;
String chkChangeReason[] = new String[7];
String chkIncreaseProdType[] = new String[12];
String codeChangeReason[] = new String[7];
String codeIncreaseProdType[] = new String[12];

boolean isViewableState = true;

if(ketMoldChangeOrderVO.getTotalCount() > 0) {
    ArrayList arrChangeReason = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getChangeReason(), "|");
    ArrayList arrIncreaseProdType = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getIncreaseProdType(), "|");
    int i = 0;
    int idx = 0;
    for(i=0; i<7; i++) {
        chkChangeReason[i] = "";
        codeChangeReason[i] = "REASON_" + (i+1);
    }
    for(i=0; i<12; i++) {
        chkIncreaseProdType[i] = "";
        codeIncreaseProdType[i] ="INCR_" + (i+1);
    }
    size = arrChangeReason.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrChangeReason.get(i), codeChangeReason);
        if(idx >= 0) {
            chkChangeReason[idx] = "checked";
        }
    }
    size = arrIncreaseProdType.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrIncreaseProdType.get(i), codeIncreaseProdType);
        if(idx >= 0) {
            chkIncreaseProdType[idx] = "checked";
        }
    }
}
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01032") %><%--금형ECO 상세조회--%></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 3px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>

<!-- script language=JavaScript src="/plm/jsp/ecm/ViewMoldEco.js"></script -->
<script language="javascript">

</script>

</head>
<body onload="javascript:clickTabBtn(1);">
<form method="post" action="/plm/servlet/e3ps/MoldEcoServlet">
<input type="hidden" name="cmd" value="view">
<input type="hidden" name="oid" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()));}%>">
<input type="hidden" name="ecoId" value="">
<input type="hidden" name="devYn" value="">
<input type="hidden" name="divisionFlag" value="">
<input type="hidden" name="changeReason" value="">
<input type="hidden" name="increaseProdType" value="">
<!--table width="780" height="750" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top"
    ><table width="780" border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td
          ><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01">금형 ECO 상세조회</td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif">설계변경 관리<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%><img src="/plm/portal/images/icon_navi.gif">금형 ECO 상세조회</td>
              </tr>
            </table
          ></td>
      </tr>
      <tr>
          <td  class="head_line"></td>
      </tr>
      <tr>
          <td class="space10"></td>
        </tr>
      </table-->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="../../portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01003") %><%--금형 ECO 상세조회--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
    <td valign="top">
      <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="20">&nbsp;</td>
          <td class="font_03">

                <table id="infoShow" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03235") %><%--활동계획--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
                <table id="infoHide" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03235") %><%--활동계획--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
      </table>
      <table width="780" height="700" border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
      </tr>
      <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top"<%//내용%>
            ><table width="760" height="700" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">&nbsp;</td>
              </tr>
            </table>
            <div id="tabBasic" style="position:absolute; visibility:visible; width:747px; z-index:1; top: 80px; left: 30px">
            <%@include file="/jsp/ecm/ViewMoldEcoBasicForm.jsp" %>
            </div>
            <div id="tabActivity" style="position:absolute; visibility:visible; width:747px; z-index:1; top: 80px; left: 30px">
            <%@include file="/jsp/ecm/ViewMoldEcoActivityForm.jsp" %>
            </div>
          </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table></td>
</tr>
<!--tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
</tr>
</table-->
          </table></td>
      </tr>
      </table></td>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>

</form>
</body>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</html>
