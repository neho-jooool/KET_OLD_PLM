<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.project.cancel.ProjectCancelLink"%>
<%@page import="e3ps.project.cancel.CancelProject"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.ProjectChangeStop"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String oid = request.getParameter("oid");
  String command = request.getParameter("command");

  String pjtType = "";
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);

  QuerySpec qs = null;
  QueryResult qr = null;
  try{
    long idLong = CommonUtil.getOIDLongValue(project.getMaster());
    qs = new QuerySpec();
    int idx = qs.addClassList(ProjectCancelLink.class, true);
    qs.appendWhere(new SearchCondition(ProjectCancelLink.class, "roleBObjectRef.key.id", "=", idLong), new int[] {idx});
    SearchUtil.setOrderBy(qs, ProjectCancelLink.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
    qr = PersistenceHelper.manager.find(qs);
  }catch(Exception e){
      Kogger.error(e);
  }


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script language=JavaScript>
  function stateChangeStopView(oid, pcsOid){
    var url = "/plm/jsp/project/CancelHistoryView.jsp?oid="+oid+"&pcsOid="+pcsOid;
    openSameName(url,"1100","800","status=no,scrollbars=yes,resizable=1");
  }
</script>

<style type="text/css">
<!--
.style1 {color: #FF0000}
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}

-->
</style>
<title><%=messageService.getString("e3ps.message.ket_message", "01492") %><%--변경 사유--%></title>
</head>
<body>
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form method=POST enctype="multipart/form-data">
        <input type=hidden name=oid value="<%=oid%>"> 
        <input type=hidden name=command>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td valign="top">
                    <!-------------------------------------- 상단 제목 버튼 시작 //--------------------------------------> 
                    <%--      <table border="0" cellpadding="0" cellspacing="0" width="540">
                      <tr>
                        <td>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td background="/plm/portal/images/logo_popupbg.png">
                                <table height="28" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01493") %>변경 사유 보기</td>
                                      <td width="10"></td>
                                    </tr>
                                </table>
                                </td>
                                <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                              </tr>
                              </table>
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                              <td class="space10"> </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                  </table> --%> 
                  <!-------------------------------------- 상단 제목 버튼 끝 //-------------------------------------->
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td>
                                <!------------------------------ 본문 Start //------------------------------>
                                <!-- <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td class="space15"></td>
                                    </tr>
                                </table> -->
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tdblueM" style="width: 100px;"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                        <td class="tdblueM" style="width: 100px;"><%=messageService.getString("e3ps.message.ket_message", "01505")%><%--변경구분--%></td>
                                        <td class="tdblueM" style="width: 210px;"><%=messageService.getString("e3ps.message.ket_message", "01682")%><%--사유--%></td>
                                        <td class="tdblueM" style="width: 100px;"><%=messageService.getString("e3ps.message.ket_message", "02020")%><%--시행일--%></td>
                                        <td class="tdblueM" style="width: 50px;"><%=messageService.getString("e3ps.message.ket_message", "02794")%><%--첨부--%></td>
                                    </tr>
                                    <%
                                        if (qr == null || qr.size() == 0) {
                                    %>
                                    <tr>
                                        <td class='tdwhiteM0' align='center' colspan='5'><%=messageService.getString("e3ps.message.ket_message", "00708")%><%--검색결과가 없습니다--%>
                                        </td>
                                    </tr>
                                    <%
                                        } else {
                                    		Object[] obj = null;
                                    		while (qr.hasMoreElements()) {
                                    		    obj = (Object[]) qr.nextElement();
                                    		    ProjectCancelLink ps = (ProjectCancelLink) obj[0];
                                    		    CancelProject cp = ps.getCancle();
                                    		    String pcsOid = CommonUtil.getOIDString(cp);
                                    		    Vector contents = new Vector();
                                    		    contents = ContentUtil.getSecondaryContents(cp);

                                    		    String webEditor = "";
                                    		    String webEditorText = "";
                                    		    if (cp.getWebEditor() != null) {
                                    			webEditor = (String) cp.getWebEditor();
                                    		    }
                                    		    if (cp.getWebEditorText() != null) {
                                    			webEditorText = (String) cp.getWebEditorText();
                                    		    }

                                    		    String fileImg = "";
                                    		    if (contents.size() > 0) {
                                    			fileImg = "<img src='/plm/portal/images/icon_file.gif' border='0'>";
                                    		    }

                                    		    String stopChageType = "";
                                    		    if (cp.getCancelReasonType() != null) {
                                    			Map<String, Object> numCodeValue = new HashMap<String, Object>();
                                    			String change = "PROJECTCANCELTYPE";

                                    			numCodeValue = NumberCodeUtil.getNumberCodeValueMap(change, cp.getCancelReasonType().toString(), messageService.getLocale().toString());

                                    			stopChageType = numCodeValue.get("value").toString();
                                    		    }
                                    %>
                                    <tr>
                                        <td width="100" class="tdwhiteM"><%=cp.getCancelType()%></td>
                                        <td width="100" class="tdwhiteM"><%=stopChageType%></td>
                                        <td class="tdwhiteL" style="width: 210px; cursor: pointer;"
                                            onclick="javascript:stateChangeStopView('<%=oid%>','<%=pcsOid%>');">
                                            <div class="changeDetail"
                                                style="width: 240px; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
                                                <%=webEditorText%>
                                            </div>
                                        </td>
                                        <td width="100" class="tdwhiteM"><%=cp.getCancelDate().toString().substring(0, 10)%></td>
                                        <td width="50" class="tdwhiteM"><%=fileImg%>&nbsp;</td>
                                    </tr>
                                    <%  }
                      }
                      %>

                                </table>
                            </td>
                        </tr>
                    </table>

                </td>
            </tr>
        </table>
    </form>
    <script>

  function doCreateJsp(){
    if(!checkValidate()){
      return;
    }
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03346") %><%--프로젝트를 중지 하시겠습니까?--%>")) {
      return;
    }
    document.forms[0].command.value = "create";
    document.forms[0].method = "post";
    document.forms[0].submit();

  }
  function checkValidate(){
    if(document.forms[0].stopDetil.value == ""){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02699") %><%--중지 사유를 입력 하십시오--%>');
      return false;
    }

    return true;
  }



</script>


</body>
</HTML>