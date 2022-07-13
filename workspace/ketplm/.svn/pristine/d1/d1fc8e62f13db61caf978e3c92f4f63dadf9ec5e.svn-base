<%--
 /**
 * @(#)  table.jsp
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.1
 * @author Seung-hwan Choi, skyprda@e3ps.com
 * @desc dhtml 테이블
 */
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="java.util.StringTokenizer"%>

<%
  String tableName = request.getParameter("tablename");  // 테이블명
  String fieldName = request.getParameter("fieldname");  // 테이블에 출력될 필드명 ( var1$var2$var3 )
  String idxField = request.getParameter("idxfield");  // 테이블에 출력될 필드 인덱스 ( 1$3$4 )
  String addType = request.getParameter("addtype");    // 추가방법 one | multi
  String searchPage = request.getParameter("searchpage");  // 검색 페이지
  String submit = request.getParameter("submit");      // 페이지 reload 여부

  String extraTitle = request.getParameter("extratitle");        // 추가 필드의 제목
  String extra = request.getParameter("extra");        // 추가 필드의 내용

  if(submit==null || submit.length() == 0) submit = "false";
  if(searchPage.indexOf("cmd")<0) searchPage += searchPage.indexOf("?")>0 ? "&cmd=select" : "?cmd=select";
  searchPage += "&tablename="+tableName;

  String viewMode = request.getParameter("viewmode");

  StringTokenizer st = null;
  int countToken = 0;
  if(!"text".equals(addType))
  {
    st = new StringTokenizer(fieldName, "$");
    countToken = st.countTokens();

    if(idxField == null)
    {
        idxField = "";
        for(int i=0 ; i<countToken ; i++)
      {
          idxField += ""+(i+1);
          if(i != countToken-1) idxField += "$";
      }
    }
  }
%>
<input type='hidden' value='<%=addType%>' id='addtype<%=tableName%>'>
<input type='hidden' value='<%=viewMode%>' id='viewmode<%=tableName%>'>
<input type='hidden' value='<%=idxField%>' id='idxfield<%=tableName%>'>
<input type='hidden' value='<%=tableName%>' id='ACTIVE_E3PS_TABLE'>
<%--<input type='button' value='삭제' onClick='deleteLine("<%=tableName%>")' id=innerbutton>--%>

<%
  if("text".equals(addType))
  {
%>
    <input type=text name='<%=tableName%>' id=i readonly <%="view".equals(viewMode)?"style='border:0'":"" %>><input type=hidden name='oid<%=tableName%>'>
<%  if( !"view".equals(viewMode) ) {%>
    <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick='searchWindow("<%=tableName%>", "<%=searchPage%>")' id=innerbutton>
    <input type='button' value='삭제' onClick='document.getElementById("<%=tableName%>").value="";document.getElementById("oid<%=tableName%>").value="";' id=innerbutton>
<%  }
  }
  else
  {
      if( !"view".equals(viewMode) )
      {  %>
      <input type='hidden' value='<%=submit%>' id='submit<%=tableName%>'>
      <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick='searchWindow("<%=tableName%>", "<%=searchPage%>")' id=innerbutton class="s_font">
<%  }   %>
  <TABLE id="<%=tableName%>" width="100%" border="0" cellspacing="0" cellpadding="1" class=tb1>
  <TR align="center">
    <TD width="50" id=tb_blue class="tdblueM2" nowarp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
<%
    while (st.hasMoreTokens())
    {
        String token = st.nextToken();
%>  <TD id=tb_blue width='<%=100/countToken%>%' class="tdblueM2"><%=fieldName == null?"":token%></TD>
<%
    }

    // 추가 필드 제목
    if(extraTitle != null)
    {
        st = new StringTokenizer(extraTitle, "$");
      while (st.hasMoreTokens())
          out.println("<TD id=tb_blue>"+st.nextToken()+"</TD>");
    }
%>
  </TR>
  <TR align="center" bgcolor="#FFFFFF" style="display:none" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'" >
<%
    for (int i=-1 ; i<countToken ; i++ )
        out.println("<TD id=tb_gray "+(i!=-1&&i<2?"align=left" : "")+"></TD>");

    // 추가 필드 내용
    if(extraTitle != null)
    {
      st = new StringTokenizer(extra, "$");
      while (st.hasMoreTokens())
          out.println("<TD id=tb_gray align=center>"+st.nextToken()+"</TD>");
    }
%>
  </TR>
  </TABLE>
<%
  } // end of else
%>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
var dataArray<%=tableName%> = new Array();
//-->
</SCRIPT>
