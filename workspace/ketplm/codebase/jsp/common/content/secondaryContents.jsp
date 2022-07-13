<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String color = request.getParameter("color");
  if(color == null) color="bbdddd";
  String colspan = request.getParameter("colspan");
  if(color == null) colspan="";
  String addtype = request.getParameter("addtype");

  // div로 구성된 페이지인 경우
  String isDiv = request.getParameter("isdiv");

  String isRequired = e3ps.common.util.StringUtil.checkReplaceStr(request.getParameter("isRequired"), "false");

  if(isDiv == null)
  {
%>
    <TD bgcolor='<%=color%>' width=20% class="tdblueM">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%><%=("true".equals(isRequired))?"<span class='style1'> *</span>":""%><BR>&nbsp;(<%=messageService.getString("e3ps.message.ket_message", "00011", 30) %><%--{0}MB이하만--%>)</TD>
    <TD bgcolor=white colspan='<%=colspan%>'class="tdwhiteL">
<%
  }
  else
  {
%>
    <div class=div_body_row>
      <div class=div_row_label style='background:<%=color%>'><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%><%=("true".equals(isRequired))?"<span class='style1'> *</span>":""%><BR>(<%=messageService.getString("e3ps.message.ket_message", "00011", 30) %><%--{0}MB이하만--%>)</div>
      <div class=div_row_long>
<%} %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"  id="fileTable">
          <% if(!e3ps.common.util.StringUtil.checkString(addtype)){ %>
          <tr>
            <td width="20"></TD>
            <td>
              <a href="javascript:insertSecondaryFile();">
              <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="추가" width="62" height="20" border="0">
              </a>
              &nbsp;
              <a href="javascript:deleteSecondaryFile();">
              <img src="/plm/portal/images/img_default/button/board_btn_delete.gif" alt="삭제" width="62" height="20" border="0">
              </a>
              <!--input type=button onclick="javascript:insertSecondaryFile()" value="추가" class="s_font">
              <input type=button onclick="javascript:deleteSecondaryFile()" value="삭제" class="s_font"-->
            </td>
          </tr>
          <% } else {%>
          <tr>
            <td></td>
            <td></td>
          </tr>
          <%} %>
          <tr align="left" id="fileTableRow" style="display:none">
            <td align="left" width="20" height="22">
          <% if(!e3ps.common.util.StringUtil.checkString(addtype)){ %>
              <input type="checkbox" name="fileDelete">
          <%} %>
            </td>
            <td>
              <input type="file" name="filePath" id=input onchange='isValidSecondarySize(this)' onKeyDown="this.blur()" style="ime-mode:disabled" class="txt_field" size="80">
            </td>
          </tr>
        </table>
<%=(isDiv==null) ? "</TD>" : "</div></div>" %>

<script language='javascript' src='/plm/jsp/common/content/content.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
insertSecondaryFile()
//-->
</SCRIPT>
