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
                        e3ps.common.content.uploader.*" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String command = "";
    String oid = "";

    String pjtLocation = "";
    String taskLocation = "";

    ProjectIssue issue = null;
    ProjectIssueData issueData = null;

    ProjectIssueAnswer answer = null;
    ProjectIssueAnswerData answerData = null;

    String answerStr = "";
    String planDate = "";
    String cCheck = "";

    Timestamp plan  ;

    String contentType = request.getContentType();
    FileUploader uploader = null;

    if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 ) {
        uploader = FileUploader.newFileUploader(null, request, response);
        command = uploader.getFormParameter("command");
        oid = uploader.getFormParameter("oid");

        answerStr = uploader.getFormParameter("answerStr");
        planDate = uploader.getFormParameter("planDate");
        cCheck = uploader.getFormParameter("cCheck");
    } else {
        command = CharUtil.E2K(request.getParameter("command"));
        oid = CharUtil.E2K(request.getParameter("oid"));
        cCheck = CharUtil.E2K(request.getParameter("cCheck"));
    }

    oid = request.getParameter("oid");
    Object obj = CommonUtil.getObject(oid);
    if ( obj instanceof ProjectIssue ) {
        issue = (ProjectIssue)obj;

        issueData = new ProjectIssueData(issue);
    } else if ( obj instanceof ProjectIssueAnswer ) {
        answer = (ProjectIssueAnswer)obj;
        //issue = (ProjectIssue)answer.getQuestion();
        issue = ProjectIssueHelper.manager.getIssue(answer);

        issueData = new ProjectIssueData(issue);
        answerData = new ProjectIssueAnswerData(answer);
    }


    if(cCheck == null){ cCheck = "false";}
    if ( issueData.project != null ) pjtLocation = issueData.project.getPjtName();
    if ( issueData.task != null ) taskLocation = issueData.task.getTaskName();

    if("answer".equals(command)){
        WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();

        ProjectIssueAnswer issueAnswer = ProjectIssueAnswer.newProjectIssueAnswer();
        ProjectIssueAnswer parentRef = (ProjectIssueAnswer)obj;
        issueAnswer.setQuestion(answerStr);
        issueAnswer.setOwner(user);
        issueAnswer.setCreateDate(DateUtil.getCurrentTimestamp());
        issueAnswer.setDependencyAnswer(parentRef);

        if("true".equals(cCheck)){
            issueAnswer.setIsCheck(true);
        }

        issueAnswer = (ProjectIssueAnswer) PersistenceHelper.manager.save(issueAnswer);

        java.util.Vector files = uploader.getFiles();
        for(int i = 0; i < files.size(); i++) {
            issueAnswer = (ProjectIssueAnswer)E3PSContentHelper.service.attach(issueAnswer, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
        }
    }
%>

<%@page import="java.sql.Timestamp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript>
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
    if (body.rows.length == 1) {
        body.deleteRow(0);
    } else {
        for (var i = body.rows.length; i > 0; i--) {
            if (file_checks[i-1].checked) body.deleteRow(i - 1);
        }
    }
}
// 첨부 파일 끝 *****************************************************************************************************************

function answerIssue(){
    if(isNullData(document.forms[0].answerStr.value)){
        alert('<%=messageService.getString("e3ps.message.ket_message", "03169") %><%--해결방안 참조를 입력해 주십시오--%>');
        document.forms[0].answerStr.focus();
        return;
    }
    document.forms[0].command.value = "answer";
    document.forms[0].action = "/plm/jsp/project/projectIssueAnswerReference.jsp?oid="+document.forms[0].oid.value;
    document.forms[0].submit();
}

function cancel(){
    location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
}

<%if("answer".equals(command)){%>
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

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00275") %><%--Issue 참조 등록--%></td>
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

          <table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>

                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>

                  <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:answerIssue();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02765") %><%--참조등록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                     <td>&nbsp;</td>
                  <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif">
                            <a href="javascript:;" onClick="history.back();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%>
                            </a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>


                </tr>
                </table>
                </td>

              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                <td class="tdwhiteL" colspan="5" >&nbsp;<%=pjtLocation%></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                <td class="tdwhiteL">&nbsp;<%=issueData.questionUser.name%></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02522") %><%--제기일자--%></td>
                <td colspan="3" class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(issueData.questionDate,"date")%></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
                <td width="250" class="tdwhiteL">&nbsp;<%=issueData.issueType%></td>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
                <td width="250" colspan="3" class="tdwhiteL0">&nbsp;<%=issueData.isCheck? messageService.getString("e3ps.message.ket_message", "02171")/*완료*/ : messageService.getString("e3ps.message.ket_message", "01454")/*미완료*/ %></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                <td width="250" class="tdwhiteL">&nbsp;<%=issueData.managerUser.people.getName() %></td>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01157") %><%--긴급 여부--%></td>
                <td width="90" class="tdwhiteL"><%=messageService.getString("e3ps.message.ket_message", "03359", 1) %><%--{0,choice,1#상|2#중|3#하}--%></td>
                <td width="70" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
                <td width="90" class="tdwhiteL0"><%=messageService.getString("e3ps.message.ket_message", "03359", 1) %><%--{0,choice,1#상|2#중|3#하}--%></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td width="250" class="tdwhiteL0" colspan="5">&nbsp;<%=issueData.subject%></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00677") %><%--개요--%></td>
                <td colspan="5" class="tdwhiteL0">
                    <textarea name="detail" style="width:97%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" readOnly><%=(issueData.question=="")?"":issueData.question%></textarea>
                </td>
              </tr>
              <% if ( issueData.isQuestionAttache ) { %>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                <td colspan="5" class="tdwhiteL0">
                <%
                for ( int i = 0 ; i < issueData.questionAttacheVec.size() ; i++ ) {
                    ContentInfo info = (ContentInfo)issueData.questionAttacheVec.get(i);
                %>
                    <%=info.getDownURLStr()%><%if(i<(issueData.questionAttacheVec.size()-1))out.print("<br>");%>
                <%
                }
                %>
                </td>
              </tr>
            <% } %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="20"><img src="../../portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03177") %><%--해결방안 참조 등록--%></td>
                <td align="right">&nbsp;</td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td width="100" style="height:100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03178") %><%--해결방안 참조--%></td>
                <td width="600" class="tdwhiteL0" style="height:100"><textarea name="answerStr" class="txt_field" id="textfield12" style="width:100%; height:96%"></textarea></td>
              </tr>
              <tr>
                  <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
                <td width="600" class="tdwhiteL0"><input type="radio" name=cCheck value="false" disabled> <%=messageService.getString("e3ps.message.ket_message", "01454") %><%--미완료--%>  <input type="radio" name=cCheck value="true" checked disabled> <%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%> </td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                <td width="600" class="tdwhiteM0">
                    <table width="580" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                      </table>
                    <table width="580" border="0" cellspacing="0" cellpadding="0" >
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
                      <table border="0" cellspacing="0" cellpadding="0" width="580">
                        <tr>
                          <td class="space5"></td>
                        </tr>
                      </table>
                      <table width="580" border="0" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="40"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                      <td colspan="2"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                    </tr>
                    <tbody id="secondaryTable"/>
                    <%
                        if(answer != null && false) {
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
                      <td width="40" class="tdwhiteM"><input type="checkbox" name="deleteSecondaryFile" id="checkbox" value="<%=info.getContentOid()%>"></td>
                      <td width="450" class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                      <td width="90" class="tdwhiteL">&nbsp;</td>
                    </tr>
                    <%
                                }
                            }
                        }
                    %>
                  </table>
                  <table width="580" border="0" cellpadding="0" cellspacing="0">
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
