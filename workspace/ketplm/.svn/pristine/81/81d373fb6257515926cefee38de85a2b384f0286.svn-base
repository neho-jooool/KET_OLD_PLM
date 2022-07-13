<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>
<%@page import ="wt.query.*"%>
<%@page import="wt.fc.*" %>
<%@page import="e3ps.common.util.*" %>
<%@page import="e3ps.project.historyprocess.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<html>
<head>
<%
  String oid = request.getParameter("oid");
  Object historyOid = CommonUtil.getObject(oid);
  TemplateProject toid =(TemplateProject)historyOid;
  QueryResult qr = null;
  qr = HistoryHelper.getHistory(historyOid);
%>

<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/viewObject.js"></script>
<script language=JavaScript>
<!--

  function checkCbox(cbox)
  {

    if(cbox==null)
      len=0;
    else
    {
      len=cbox.length
      if(''+len == 'undefined') len = 1;
    }
    return len;
  }

  function comparison()
  {
    var cbox = document.forms[0].checkboxEach;
    var selectedList = "";
    var len = checkCbox(cbox);
    var vlen = 0;
    disabledAllBtn();

    if(cbox != null)
    {
      if(len > 1)
      {
        for(var i=0 ; i<len ; i++)
        {
          if (cbox[i].checked==true){
            selectedList += "oid="+cbox[i].value+"&";
            vlen++;
          }
        }

      }else{
          selectedList += "oid="+cbox.value;
          vlen++;
      }
    }
    enabledAllBtn();
    if(vlen == 0){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02803") %><%--체크 박스를 선택 해주세요--%>');
      return;
    }
    if(vlen == 1){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01636") %><%--비교할 이력 정보가 없습니다--%>');
      //if (cbox.checked==true) selectedList += "oid="+cbox.value;
      return;
    }
    if(vlen > 2){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01635") %><%--비교 대상은 2개만 가능 합니다--%>');
      return;
    }

    if(selectedList!=""){
    compareView(selectedList);
    }

  }

  function compareView(oids){
    var url="/plm/jsp/project/template/TemplateCompareFrm.jsp?"+oids;
    openOtherName(url,"TemplateConpareFrm","1200","800","status=no,scrollbars=yes,resizable=no");
  }

//-->
</script>
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
        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00512") %><%--Template 이력비교--%></td>
        <td align="right" style="padding:8px 0px 0px 0px"></td>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <a href="javascript:comparison();">
            <img src="/plm/portal/images/img_default/button/board_btn_comparison.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01634") %><%--비교--%>" width="62" height="20" border="0">
            </a>
            &nbsp;&nbsp;
            <a href="javascript:self.close();">
            <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
            </a>
          </td>
        </tr>
      </table>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td  class="tab_btm1"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="tdblueM">&nbsp;</td>
          <td class="tdblueM">NO</td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00509") %><%--Template 명--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00506") %><%--Template 기간--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02857") %><%--최초--%><br><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02843") %><%--최종--%><br><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00251") %><%--History 버전--%></td>
        </tr>
<%
  int listCount = 1;
  int count = 1;
  while ( qr.hasMoreElements() ) {
    Object[] obj = (Object[])qr.nextElement();
    TemplateProject template = (TemplateProject)obj[0];
    TemplateProjectData data = new TemplateProjectData((TemplateProject)obj[0]);
%>
        <tr>
          <td class="tdwhiteM">&nbsp;<input type="checkbox" name="checkboxEach" value="<%=data.tempProjectOID%>"></td>
          <td class="tdwhiteM">&nbsp;<%=count++%></td>
          <td class="tdwhiteL">&nbsp;<a href="javascript:openView('<%=data.tempProjectOID%>','','','')"><%=data.tempName%></td>
          <td class="tdwhiteM">&nbsp;<%=data.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
          <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(data.tempCreateDate,"D")%></td>
          <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(data.tempModifyDate,"D")%></td>
          <td class="tdwhiteM">&nbsp;<%=template.getPjtHistory()%><%= (template.isWorkingCopy()) ? "(" + messageService.getString("e3ps.message.ket_message", "02441")/*작업중*/ + ")" : "" %></td>
        </tr>
<%
  }
    if(qr.size() == 0) {
%>
        <tr>
          <td class='tdwhiteM0' align='center' colspan='6'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
        </tr>
<%  }%>
      </table>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td>
            <jsp:include page="/jsp/common/inc_page.jsp" flush="false">
              <jsp:param name="href" value="/plm/servlet/e3ps/SearchProjectTemplateServlet"/>
              <jsp:param name="ispost" value="true"/>
            </jsp:include>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
