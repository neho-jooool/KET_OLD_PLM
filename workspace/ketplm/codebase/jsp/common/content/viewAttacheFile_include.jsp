<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.*,e3ps.common.content.*" %>
<%@page import="wt.content.*" %>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%@include file="/jsp/common/context.jsp"%>
<%
  // command 가 update시에 update 할 ContentHolder OID
  String oid = StringUtil.checkNull(request.getParameter("oid"));

  // type : 첨부파일 타입
  //  p or primary : (Primary)주요문서 파일
  // s or secondary : (Secondary)참조문서 파일
  String type = StringUtil.checkNull(request.getParameter("type"));

  String descCheck = StringUtil.checkNull(request.getParameter("descCheck"));
  Kogger.debug("viewAttacheFile_include", null, null, "descCheck =" +descCheck);
  if ( type.equalsIgnoreCase("p") || type.equalsIgnoreCase("primary")  ) type = "primary";
  else type = "secondary";

  // (Secondary)참조문서 파일일 경우 설명 사용여부
  String desc = StringUtil.checkNull(request.getParameter("desc"));
  boolean canDesc = false;
  if ( desc.equalsIgnoreCase("t") || desc.equalsIgnoreCase("true") ) canDesc = true;

  if ( type.equals("primary") ) {
    Object obj = CommonUtil.getObject(oid);
    if ( obj instanceof FormatContentHolder ) {
      FormatContentHolder holder = (FormatContentHolder)obj;
      ContentInfo info = ContentUtil.getPrimaryContent(holder);
      String iconpath = "";
      String urlpath = "";
      if( info == null) {
        iconpath = "";
        urlpath = "";
      } else {
        iconpath = info.getIconURLStr();
        urlpath = info.getDownURLStr();
      }
%>
<table width="100%" cellspacing="0" cellpadding="1" border="0" class=tb1 align="center">
<%      if ( canDesc ) { %>
  <tr bgcolor="#D6DBE7"  align=center>
    <td id=tb_inner width="50%"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
    <td id=tb_inner width="50%"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td id=tb_gray align="left"><%=iconpath%><%=urlpath%></td>
    <td id=tb_gray align="center">&nbsp;<%= info.getDescription()==null ? "" : info.getDescription()%></td>
  </tr>
<%      } else {  %>
  <tr bgcolor="#D6DBE7"  align=center>
    <td id=tb_inner width="100%"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td id=tb_gray align="left"><%=iconpath%><%=urlpath%></td>
  </tr>
<%
      }
    }
%>
</table>
<%
  } else {
    ContentHolder holder = (ContentHolder)CommonUtil.getObject(oid);
    Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
%>
<table width="100%" cellspacing="0" cellpadding="1" border="0" class=tb1 align="center">
<%    if ( canDesc ) { %>
  <tr bgcolor="#D6DBE7" align=center>
    <td id=tb_inner width="50%"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
    <td id=tb_inner width="50%"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
  </tr>
<%
      for(int i=0; i<secondaryFiles.size(); i++) {
        ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);
        String iconpath = "";
        String urlpath = "";
        if( info == null) {
          iconpath = "";
          urlpath = "";
        } else {
          iconpath = info.getIconURLStr();
          urlpath = info.getDownURLStr();
        }
%>
  <tr bgcolor="#FFFFFF">
    <td id=tb_gray align="left"><%=iconpath%><%=urlpath%></td>
    <td id=tb_gray align="center">&nbsp;<%= info.getDescription()==null ? "" : info.getDescription()%></td>
  </tr>
<%
      }
    } else {
%>
  <tr bgcolor="#D6DBE7"  align=center>
    <td id=tb_inner width="100%"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
  </tr>
<%      boolean secondaryCheck = true;
      for(int i=0; i<secondaryFiles.size(); i++) {
        ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);
        if( descCheck.equals("N") ){
          String Description = info.getDescription()==null?"":info.getDescription();
          if(Description.equals("N")) {
            secondaryCheck = true;
          }else{
            secondaryCheck = false;
          }
        }

        if (secondaryCheck){


            String iconpath = "";
            String urlpath = "";
            if( info == null) {
              iconpath = "";
              urlpath = "";
            } else {
              iconpath = info.getIconURLStr();
              urlpath = info.getDownURLStr();
            }
%>
  <tr bgcolor="#FFFFFF">
    <td id=tb_gray align="left"><%=iconpath%><%=urlpath%></td>
  </tr>
<%        }
      }

    }
%>
</table>
<%  }  %>
<iframe id="download" name="download" frameborder=0 width=0 height=0></iframe>
