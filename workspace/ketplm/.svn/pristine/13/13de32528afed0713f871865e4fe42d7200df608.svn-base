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
<%@include file="/jsp/project/template/ajaxProgress.html"%>

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

  function onErpSite() {
    var param = "?command=erpSite";

    //onProgressBar();

    var url = "/plm/jsp/common/code/CodeAjaxAction.jsp" + param;
    callServer(url, onMessage);
  }

  function onMessage(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'true') {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00217") %><%--ERP 동기화 완료 했습니다--%>");
    } else {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
    }
    document.forms[0].submit();
  }

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

  }
//-->
</SCRIPT>
<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>
<input type='hidden' name='mode' value='<%=mode%>'>
<input type='hidden' name='elementsName' value='<%=elementsName%>'>
<input type='hidden' name='id' value='<%=id%>'>

<!-----------상단타이틀 및 버튼//-------------------->
<table width="98%" height=30 align=center border=0>
  <tr>
    <td width="296" align="left">
      <SCRIPT>printTitle('<%=e3ps.common.code.NumberCodeType.toNumberCodeType(codetype).getDisplay(messageService.getLocale())%>')</SCRIPT>
    </td>
    <td align=right>
      <%if(isSelect){ %>
        <input type=button id=button2 onclick="addSelectedItem()" value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>'>
        <input type=button id=button2 onclick="closeWindow()" value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>'>
      <%}else{ %>
        <%  if("SITE".equals(codetype)) {  %>
        <input name="button22" type=button  value="<%=messageService.getString("e3ps.message.ket_message", "00216") %><%--ERP 동기화--%>" onclick="javascript:onErpSite();">
        <%  } %>
        <input type=button id=button2 onclick="javascript:create()" value='등록'>
        <%if(CommonUtil.isAdmin()){ %>
        <input type=button id=button2 onclick="javascript:modify()" value='<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>'>
        <input type=button id=button2 onclick="javascript:remove()" value='삭제'>
        <%}%>
      <%}//if(isSelect) end %>
    </td>
  </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="98%" bgcolor="#AABDC6" align="center">
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
