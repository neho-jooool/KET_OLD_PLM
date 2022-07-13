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
<form name='test'>
<%
  e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigEx.getInstance("message");
  String codetype = request.getParameter("codetype");
%>
<%
  PagingQueryResult pagingResult = control.getResult();
  NumberCode nCode = null;
  Object[] oo = null;
  int rs = pagingResult.size();
%>
<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>

<!-----------상단타이틀 및 버튼//-------------------->
<table width="98%" height=30 align=center border=0>
  <tr>
    <td width="296" align="left">
      <SCRIPT>printTitle('<%=e3ps.common.code.NumberCodeType.toNumberCodeType(codetype).getDisplay(messageService.getLocale())%>')</SCRIPT>
    </td>
    <td align=right>
      <input type=button id=button2 onclick="javascript:selectOid(<%=rs%>)" value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>'>
      <input type=button id=button2 onclick="javascript:self.close()" value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>'>
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
  while(pagingResult.hasMoreElements())
  {
    oo = (Object[])pagingResult.nextElement();
    nCode = (NumberCode)oo[0];
%>
  <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
    <td><input type="checkbox" value="<%=CommonUtil.getOIDString(nCode)%>"
                    test="<%=nCode.getName()%>"
                      name="check" onClick="oneClick(this)"></td>
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

  function selectOid(count)
  {
    var testoid = "";
    var test = "";

    if(!isSelected(document.test.check)) return;

      var len =count;
      if (len > 1) {
        for( i=0;i<len ;i++){
          if( document.test.check[i].checked == true)
          {
            testoid  =document.test.check[i].value;
            test =document.test.check[i].test;
          }
        }
      } else if ( len == 1 ) {

        if( document.test.check.checked == true)
        {
            testoid  =document.test.check.value;
            test = document.test.check.test;
        }
      }

      parent.opener.document.forms[0].testoid.value = testoid;
      parent.opener.document.forms[0].test.value = test;

      document.forms[0].submit();
      window.close();
  }

//-->
</script>
