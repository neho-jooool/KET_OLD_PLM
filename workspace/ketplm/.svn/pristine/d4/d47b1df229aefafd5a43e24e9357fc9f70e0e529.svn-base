<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.content.*,
                wt.fc.PersistenceHelper,
                wt.org.*,
                wt.session.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.common.content.*,
                e3ps.common.content.uploader.*,
                e3ps.common.content.fileuploader.FormUploader" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
public String[] tokenDocType(String value, String param) {
    StringTokenizer st = new StringTokenizer(value, param);
    String[] docTypes = new String[st.countTokens()];
    int i = 0;
    while(st.hasMoreTokens()) {
        docTypes[i] = st.nextToken();
        i++;
    }
    return docTypes;
}
%>

<%
    ProjectIssue issue = null;
    ProjectIssueAnswer answer = null;

    ProjectIssueData data = null;
    ProjectIssueAnswerData answerData = null;

    String contentType = request.getContentType();
    FormUploader fileUploader = FormUploader.newFormUploader(request);
    Hashtable param = fileUploader.getFormParameters();

    String oid       = StringUtil.checkNull( (String) param.get("oid") );
    String command   = StringUtil.checkNull( (String) param.get("command") );
    String answerStr = StringUtil.checkNull( (String) param.get("answerStr") );
    String deleteOID = StringUtil.checkNull( (String) param.get("deleteOID") );
    String planDate  = StringUtil.checkNull( (String) param.get("planDate") );

    Object obj = CommonUtil.getObject(oid);
    if ( obj instanceof ProjectIssue ) {
        issue = (ProjectIssue)obj;

        data = new ProjectIssueData(issue);
        answerData = new ProjectIssueAnswerData(answer);
    } else if ( obj instanceof ProjectIssueAnswer ) {
        answer = (ProjectIssueAnswer)obj;
        issue = ProjectIssueHelper.manager.getIssue(answer);

        data = new ProjectIssueData(issue);
        answerData = new ProjectIssueAnswerData(answer);
    }

    if ( "update".equals(command) ) {

        answer.setQuestion(answerStr);

        if ( StringUtil.checkString(planDate) ) {
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(DateUtil.parseDateStr(planDate));
            answer.setPlanDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
        }

        answer = (ProjectIssueAnswer) PersistenceHelper.manager.modify(answer);

        //첨부파일 관련
        //기존 첨부파일 중 남은 파일 목록
        Vector tVec = new Vector();
        String[] token = tokenDocType(deleteOID, "&");
        for(int i = 0; i < token.length; i++) {
            tVec.add(token[i]);
        }
        //기존 존재하던 모든 파일 목록
        Vector oldFiles = ContentUtil.getSecondaryContents(answer);
        for(int i = 0; i < oldFiles.size(); i++) {
            ContentInfo info = (ContentInfo) oldFiles.elementAt(i);

            if( tVec.contains(info.getContentOid()) ) {
                //Kogger.debug("Delect>>>> " + info.getContentOid());
                answer = (ProjectIssueAnswer)E3PSContentHelper.service.delete(answer, (ApplicationData)CommonUtil.getObject(info.getContentOid()));
            }
        }

        if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 ) {

            Vector files = fileUploader.getFiles();

            if ( files != null ) {
                for ( int i = 0; i < files.size(); i++ ) {
                    answer = (ProjectIssueAnswer)E3PSContentHelper.service.attach(answer, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
                }
            }
        }
    }
%>

<html>
<head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "03175") %><%--해결방안 수정--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript>

$(document).ready(function(){
    
    CalendarUtil.dateInputFormat('planDate');
    
});

// 첨부파일 시작 *****************************************************************************************************************
function insertFileLine() {
    var tBody = document.getElementById("secondaryTable");
    var innerRow = tBody.insertRow();
    //var innerCell = innerRow.insertCell();
    var filePath = "secondaryFile"+tBody.rows.length;

    //innerCell.innerHTML = "<input name='secondarySelect' type='checkbox' class='Checkbox'>";
    //innerCell.innerHTML += "<input name='"+filePath+"' type='file' class='txt_field' style='width:95%;'>";

    newTd = innerRow.insertCell();//delete
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input name='secondarySelect' type='checkbox'>";

    newTd = innerRow.insertCell();//file
    newTd.className = "tdwhiteL0";
    newTd.colSpan = 2;
    newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:95%;'>";
}

function deleteFileLine() {
    var body = document.getElementById("secondaryTable");
    if (body.rows.length == 0) return;
    var file_checks = document.forms[0].secondarySelect;
    var returnDel = document.forms[0].deleteOID.value;

    if (body.rows.length == 1) {
        returnDel = document.forms[0].secondarySelect.value + "&";
        body.deleteRow(0);
    } else {
        for (var i = body.rows.length; i > 0; i--) {
            if (file_checks[i-1].checked) {
                returnDel = returnDel + document.forms[0].secondarySelect[i-1].value + "&";
                body.deleteRow(i - 1);
            }
        }
    }

    document.forms[0].deleteOID.value = returnDel;
}
// 첨부 파일 끝 *****************************************************************************************************************

function update(){
    if(isNullData(document.forms[0].answerStr.value)){
        alert('<%=messageService.getString("e3ps.message.ket_message", "03170") %><%--해결방안을 입력해 주십시오--%>');
        document.forms[0].answerStr.focus();
        return;
    }
    document.forms[0].command.value = "update";
    document.forms[0].action = "/plm/jsp/project/projectIssueAnswerUpdate.jsp";
    document.forms[0].submit();
}

function cancel(){
    location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
}

<%if("update".equals(command)){%>
    location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
    //opener.document.location.reload();
    //self.close();
<%}%>

</script>
</head>

<body>
<form name="projectIssueAnswer" method=POST enctype="multipart/form-data">
<input type=hidden name=oid value=<%=oid%>>
<input type=hidden name=command>
<input type=hidden name=deleteOID>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03175") %><%--해결방안 수정--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="710" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="202">&nbsp;</td>
          <td valign="top">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td>&nbsp;</td>
              <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                 <tr>
                 <td>
                     <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                     <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03175") %><%--해결방안 수정--%></a></td>
                     <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                     </tr>
                     </table>
                </td>
                <td width="5">&nbsp;</td>
                <td >
                     <table border="0" cellspacing="0" cellpadding="0" >
                     <tr>
                     <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="history.back();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                     <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                     </tr>
                     </table>
                </td>

                </tr>
                   </table>
               </td>
               </tr>
           </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" >
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" >
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" >
              <tr>
                <td width="100"style="height:100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03172") %><%--해결방안--%> <font color="red">*</font></td>
                <td class="tdwhiteL0" style="height:100">
                <textarea name="answerStr" class="txt_field" id="i" style="word-break:break-all;width:97%;height:96%"><%=answerData.answerStr%></textarea></td>
              </tr>
              <tr>
                
                  
                  <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02177")%><%--완료예정일자--%></td>
                  <td class="tdwhiteL">
                  <input type="text" name="planDate" class="txt_field" style="width: 80px" value="<%=answerData.answer.getPlanDate() == null ? "" :DateUtil.getDateString(answerData.answer.getPlanDate(), "date")%>">
                  <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('planDate');" style="cursor: hand;">
                  </td>
                  
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                <td class="tdwhiteM0"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:insertFileLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:deleteFileLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                      <td align="right">&nbsp;</td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="40"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                      <td colspan="2"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                    </tr>
                    <tbody id="secondaryTable"/>
                    <%
                        if(answer != null) {
                            ContentHolder contentHolder = ContentHelper.service.getContents(answer);
                            Vector secondaryFiles = ContentUtil.getSecondaryContents(contentHolder);

                            if(secondaryFiles.size() > 0) {
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
                    <tr>
                      <td width="40" class="tdwhiteM"><input type="checkbox" name="secondarySelect" value="<%=info.getContentOid()%>"></td>
                      <td width="450" class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                      <td width="90" class="tdwhiteL">&nbsp;</td>
                    </tr>
                    <%
                                }
                            }
                        }
                    %>
                  </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
