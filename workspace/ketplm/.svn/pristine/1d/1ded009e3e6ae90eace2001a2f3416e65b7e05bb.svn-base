<html>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.code.NumberCode,
        e3ps.common.util.StringUtil,
        e3ps.common.util.CommonUtil,
        wt.fc.PagingQueryResult"%>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01135") %><%--기준정보 목록--%></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<%
  e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigEx.getInstance("message");
  String codetype = request.getParameter("codetype");
  String mode = request.getParameter("mode");
  String elementsName = request.getParameter("elementsName");
  String id = request.getParameter("id");
  //Kogger.debug("mode : " + mode);
  //Kogger.debug("elementsName : " + elementsName);
  //Kogger.debug("id : " + id);
  boolean isSelect = StringUtil.checkString(mode);
%>
<SCRIPT type="text/javascript">
<!--
  function addSelectedItem(){
    if(document.forms[0].oid == null) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>");
      return;
    }
    var sresult = new Array();
    var count = 0;
    if(document.forms[0].oid.length==null){
      if(document.forms[0].oid.checked){
        sresult[count++] = document.forms[0].oid.value;
      }else{
        alert("<%=messageService.getString("e3ps.message.ket_message", "01811") %><%--선택된 코드가 없습니다--%>");
        return;
      }
    }
    for(var i=0; i< document.forms[0].oid.length; i++){
      if(document.forms[0].oid[i].checked){
        sresult[count++] = document.forms[0].oid[i].value;
      }
    }
    if(sresult.length==0){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01811") %><%--선택된 코드가 없습니다--%>");
      return;
    }

<%
  if(elementsName != null && elementsName.length() > 0) {
%>
    if(opener) {
      <%if(!"multi".equals(mode)){%>
      opener.document.forms[0].<%=elementsName%>.value=sresult[0];
      opener.document.forms[0].submit();
      self.close();
      <%}else{%>
      var addhtml = "";
      for(var i=0; i< sresult.length; i++){
        addhtml += "<input type=hidden name=<%=elementsName%> value='"+sresult[i]+"'>";
      }
      opener.document.all.<%=id%>.innerHTML = addhtml;
      opener.document.forms[0].submit();
      self.close();
      <%} %>
    }
<%
  }
%>
  }
//-->
</SCRIPT>
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
<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>
<input type='hidden' name='mode' value='<%=mode%>'>
<input type='hidden' name='elementsName' value='<%=elementsName%>'>
<input type='hidden' name='id' value='<%=id%>'>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
        <tr>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
        <td class="font_01"><%=e3ps.common.code.NumberCodeType.toNumberCodeType(codetype).getDisplay(messageService.getLocale())%></td>
        <td align="right" style="padding:8px 0px 0px 0px"></td>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td align=right>
          <%if(isSelect){ %>
            <a href="javascript:addSelectedItem();">
            <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
            </a>
            &nbsp;&nbsp;
            <a href="javascript:closeWindow();">
            <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
            </a>
          <%}else{ %>
            <a href="javascript:create();">
            <img src="/plm/portal/images/img_default/board_btn_ok.gif" alt="등록" width="62" height="20" border="0">
            </a>
          <%if(CommonUtil.isAdmin()){ %>
            <a href="javascript:modify();">
            <img src="/plm/portal/images/img_default/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
            </a>
            &nbsp;&nbsp;
            <a href="javascript:create();">
            <img src="/plm/portal/images/img_default/board_btn_delete.gif" alt="삭제" width="62" height="20" border="0">
            </a>
          <%} %>
          <%}//if(isSelect) end %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="100%" bgcolor="#AABDC6" align="center">
  <TR bgcolor="#D8E0E7" align=center height=23>
      <TD width="1%" bgcolor="#D8E0E7" id=title></td>
    <TD width=20% id=title>이름</TD>
    <TD width=10% id=title><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></TD>
    <TD width=25% id=title><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></TD>
  </TR>

<%
  PagingQueryResult pagingResult = control.getResult();
  NumberCode nCode = null;
  Object[] oo = null;
  while(pagingResult.hasMoreElements())
  {
    oo = (Object[])pagingResult.nextElement();
    nCode = (NumberCode)oo[0];
%>
  <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
    <td><input type="checkbox" value="<%=CommonUtil.getOIDString(nCode)%>" name="oid" <%if(!isSelect && !"multi".equalsIgnoreCase(mode)){ %>onClick="oneClick(this)"<%} %>></td>
    <TD>&nbsp;<%=nCode.getName()%></TD>
    <TD align=center><%=nCode.getCode()%></TD>
    <TD>&nbsp;<%=StringUtil.checkNull(nCode.getDescription())%></TD>
  </TR>
<%
  } // end of while(pr.hasMoreElements())
%>
</TABLE>
<table width=98% align=center>
  <tr>
    <td width=100%>
    <%
      control.setHref("/plm/servlet/e3ps/NumberCodeServlet");
      control.setPostMethod();
    %>
    <%@include file="/jsp/common/page_include.jsp"%>
    </td>
  </tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
<!--
function create()
{
  var codetype = document.forms[0].codetype.value;
  var param = "?cmd=create&codetype="+codetype;
  openWindow("/plm/jsp/common/code/CreateNumberCode.jsp"+param, "create", "400", "250");
}

function modify()
{
  if(!isSelected(document.forms[0].oid)) return;

  var oid = getSelectedList(document.forms[0].oid);
  var codetype = document.forms[0].codetype.value;
  var param = "?cmd=modify&"+oid+"&codetype="+codetype;
  openWindow("/plm/jsp/common/code/ModifyNumberCode.jsp"+param, "modify", "400", "200");
}

function remove()
{
  if(!isSelected(document.forms[0].oid)) return;

  if(!confirm("<%=conf.getString("confirm.delete")%>")) return;
  showProcessing();
  disabledAllBtn();

  var oid = getSelectedList(document.forms[0].oid);
  var codetype = document.forms[0].codetype.value;
  var param = "?cmd=delete&"+oid+"&codetype="+codetype;
  document.forms[0].action ="/plm/servlet/e3ps/NumberCodeServlet"+param;
  document.forms[0].method ="post";
  document.forms[0].submit();
}
//-->
</script>
