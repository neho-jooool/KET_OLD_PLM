<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="java.text.*,
				 wt.fc.*"%>
<%@page import="e3ps.project.*,
				e3ps.project.beans.*,
				e3ps.common.util.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%!private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	%>
<%
	String oid = request.getParameter("oid");
	TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);


	String name = StringUtil.checkNull(request.getParameter("name"));
	String description = StringUtil.checkNull(request.getParameter("description"));
	Kogger.debug("name : " + name);
	Kogger.debug("name : " + description);

	String command = StringUtil.checkNull(request.getParameter("command"));
	try{
	if ( command.equals("update") ) {
		ProjectMaster master = project.getMaster();
		master.setPjtName(name);
		//project.setPjtName(name);
		project.setPjtDesc(description);
		PersistenceHelper.manager.modify(master);
		project =(TemplateProject) PersistenceHelper.manager.modify(project);

	}
	}catch(Exception e)
		{Kogger.error(e);}
%>

<HTML>
<HEAD>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "00560") %><%--WBS 정보수정--%></TITLE>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript">
<!--
function update() {
	if(isNullData(document.forms[0].name.value)){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02279") %><%--이름이 정의되지 않았습니다--%>');
		return;
	}
	document.forms[0].submit();
}


<%	if ( command.equals("update") ) {	%>
opener.parent.document.location.reload();
self.close();
<%	}	%>
//-->
</script>
<style type="text/css">
<!--
body {

}
-->
</style>
</HEAD>
<body>
<form name="projectUpdate" method="post">
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=command value='update'>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00560") %><%--WBS 정보수정--%></td>
                <td width="10"></td>
              </tr>
            </table></td>
          <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="460">
				<tr>
					<td class="tdblueL" width="25%" >&nbsp;WBS</td>
					<td class="tdwhiteL" align=left width="75%">&nbsp;<input type=text name="name" value='<%=project.getPjtName()%>' style="width:98%" ></td>
				</tr>
				<tr>
					<td class="tdblueL" width="25%" ><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
					<td class="tdwhiteL" width="75%">&nbsp;<textarea name="description" rows="10" style="width:98%" ><%=project.getPjtDesc()==null?"":project.getPjtDesc()%></textarea></td>
				</tr>
			</table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space15"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10">&nbsp;</td>
        <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
      </tr>
    </table></td>
  </tr>
</table>


</form>
</BODY>
</HTML>

