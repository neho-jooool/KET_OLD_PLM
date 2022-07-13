<%@page import="ext.ket.shared.mail.service.KETMailHelper"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.org.*,
                wt.session.*,
                wt.fc.*,
                wt.content.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.common.content.*,
                e3ps.common.content.uploader.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    IssueType[] issueTypeList = IssueType.getIssueTypeSet();
    WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();
    String today = DateUtil.getDateString(Calendar.getInstance().getTime(),"date");

    String contentType = request.getContentType();
    ProjectIssue issue = null;

    FormUploader fileUploader = FormUploader.newFormUploader(request);
    Hashtable param = fileUploader.getFormParameters();

    String command   = (String) param.get("command");
    String oid       = (String) param.get("oid");
    String subject   = (String) param.get("subject");
    String question  = (String) param.get("question");
    String issueType = (String) param.get("issueType");
    String tempmasterMember = (String) param.get("tempmasterMember");
    String masterMember     = (String) param.get("masterMember");
    String review           = (String) param.get("review");
    String urgency          = (String) param.get("urgency");
    String importance       = (String) param.get("importance");

    if ( "save".equals(command) ) {
        Object obj = CommonUtil.getObject(oid);
        IssueType type = (IssueType)EnumeratedTypeUtil.toEnumeratedType(issueType);

        WTUser manager = (WTUser)CommonUtil.getObject(masterMember);

        issue = ProjectIssue.newProjectIssue();
        issue.setSubject(subject);
        issue.setAnswer(question);
        issue.setIsAnswerDone(true);
        issue.setOwner(user);
        issue.setManager(WTPrincipalReference.newWTPrincipalReference(manager));
        issue.setIssueType(type);
        if ( obj instanceof E3PSProject ) {
            issue.setProject((E3PSProject)obj);
        } else if ( obj instanceof E3PSTask ) {
            issue.setTask((E3PSTask)obj);
            issue.setProject((E3PSProject)((E3PSTask)obj).getProject());
        }

        issue.setCreateDate(DateUtil.getCurrentTimestamp());

        issue.setUrgency(urgency);
        issue.setImportance(importance);

        issue = ProjectIssueHelper.manager.createProjectIssue(issue);
        // 이슈 등록 메일 공지 발송
        WTUser from = (WTUser) issue.getOwner().getPrincipal();
        List<WTUser> to = ProjectIssueHelper.manager.getIssueMailingList(issue);
        KETMailHelper.service.sendFormMail("08017", "NoticeMailLine3.html", issue, from, to);
        /* if ( true ) {
            ProjectHelper.projectSendMail(issue, "issue");
        } */

        if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 ) {

            Vector files = fileUploader.getFiles();

            if ( files != null ) {
                for ( int i = 0; i < files.size(); i++ ) {
                    issue = (ProjectIssue)E3PSContentHelper.service.attach(issue, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
                }
            }
        }
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "02304") %><%--이슈 등록--%></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<%@include file="/jsp/common/multicombo.jsp"%>
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
    newTd.innerHTML = "<a href='#' onclick='$(this).parent().parent().remove();'><img src=\"/plm/portal/images/b-minus.png\"></a>";

    newTd = innerRow.insertCell();//file
    newTd.className = "tdwhiteL0";
    newTd.colSpan = 2;
    newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:95%;'>";
}

function deleteFileLine(obj) {
	$(this).parent().parent().remove();
}
// 첨부 파일 끝 *****************************************************************************************************************

//function clearDate(str) {
    //var tartxt = document.getElementById(str);
    //tartxt.value = "";
//}

var memberFormId = "";
function addRoleMember(rname) {
	memberFormId = rname;
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=acceptRoleMember";
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,rname, 800, 710, opts);
}

function acceptRoleMember(objArr) {
	var rname = memberFormId;
	alert("temp" + rname);
    if(objArr.length == 0) {
        return;
    }

    var displayName = document.getElementById("temp" + rname);
    var keyName = document.getElementById(rname);

    /*
        subArr[0] = chkobj[i].value;//wtuser oid
        subArr[1] = chkobj[i].poid;//people oid
        subArr[2] = chkobj[i].doid;//dept oid
        subArr[3] = chkobj[i].uid;//uid
        subArr[4] = chkobj[i].sname;//name
        subArr[5] = chkobj[i].dname;//dept name
        subArr[6] = chkobj[i].duty;//duty
        subArr[7] = chkobj[i].dutycode;//duty code
        subArr[8] = chkobj[i].email;//email
    */

    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
        infoArr = objArr[i];
        displayName.value = infoArr[4];
        keyName.value = infoArr[0];
    }
}

function deleteRoleMember(rname) {
    document.getElementById("temp" + rname).value = "";
    document.getElementById(rname).value = "";
}

function save1(){

    //이슈타입
    var itLen = document.projectProjectIssueCreate.issueType.length;
    for(var i = 0; i < itLen; i++) {
        if(isNullData(document.projectProjectIssueCreate.issueType[i].value) && document.projectProjectIssueCreate.issueType[i].selected) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02316") %><%--이슈구분을 선택해 주십시오--%>');
            return;
        }
    }

    //이슈 담당자
    if(isNullData(document.projectProjectIssueCreate.masterMember.value)) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02303") %><%--이슈 담당자를 입력해 주십시오--%>');
        return;
    }

    //완료 예정일자
    //if(isNullData(document.projectProjectIssueCreate.planDoneDate.value)) {
        //alert("완료 예정일자를 입력해 주십시오");
        //return;
    //}

    //이슈 제목
    if(isNullData(document.projectProjectIssueCreate.subject.value)){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02312") %><%--이슈 제목을 입력해 주십시오--%>');
        document.projectProjectIssueCreate.subject.focus();
        return;
    }

    //이슈 개요
    if(isNullData(document.projectProjectIssueCreate.question.value)){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02301") %><%--이슈 내용을 입력해 주십시오--%>');
        document.projectProjectIssueCreate.question.focus();
        return;
    }

    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03322") %><%--이슈를 등록 하시겠습니까?--%>")) {
            return;
    }
    showProcessing();
    document.projectProjectIssueCreate.command.value = "save";
    document.projectProjectIssueCreate.submit();
}
function close1(){
    self.close();
}
function saveClose(){
    opener.location.reload();
    self.close();
}
function save2Close(){
    opener.document.location.href = "/plm/jsp/project/ReviewProjectView4.jsp?oid=<%=oid%>&radioValue=3&popup=popup";
    self.close();
}
<%if("save".equals(command)){
Kogger.debug("review ==>"+review);
    if("".equals(review)){
%>
saveClose();
<%
}else{
%>
save2Close();
<%
}
}%>

$(document).ready(function(){
	//사용자 suggest
    SuggestUtil.bind('USER', 'tempmasterMember', 'masterMember');
});
</script>
</HEAD>
<body>
<%@include file="/jsp/common/processing.html"%>
<form name="projectProjectIssueCreate" method="POST" enctype="multipart/form-data">
<input type="hidden" name="oid" value="<%=oid%>" >
<input type="hidden" name="command">
<input type="hidden" name="review" value="<%=review%>" >


<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td height="50" valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01">CFT요청 등록</td>
                </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td valign="top">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="10">&nbsp;</td>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                        <a href="#" onClick="javascript:save1();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a>
                                    </td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    <td width="10">&nbsp;</td>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    <td width="10">&nbsp;</td>
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
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                    <td width="250" class="tdwhiteL"><%=user.getFullName()%></td>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02522") %><%--제기일자--%></td>
                    <td width="250" colspan="3" class="tdwhiteL0"><%=today%></td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%> <font color="red">*</font></td>
                    <td width="250" class="tdwhiteL">
                        <select name="issueType" class="fm_jmp" style="width:180">
                        <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                        <%
                        for( int i=0; i<issueTypeList.length; i++ ) {
                            if( !issueTypeList[i].isSelectable() ) continue;
                        %>
                        <option value="<%=issueTypeList[i].getStringValue()%>" ><%=issueTypeList[i].getDisplay(messageService.getLocale())%></option>
                        <%
                        }
                        %>
                        </select>
                    </td>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%><font color="red">*</font></td>
                    <td width="250" colspan="3" class="tdwhiteL0">
                        <input type="text" name="tempmasterMember" class="txt_field"  style="width:170px" id="tempmasterMember"> 
                        <input type='hidden' name='masterMember' id="masterMember" value=''> 
                        <a href="#" onclick="javascript:addRoleMember('masterMember');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="#" onclick="javascript:deleteRoleMember('masterMember');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01159") %><%--긴급여부--%></td>
                    <td width="230" class="tdwhiteL">
                        <select name="urgency" class="fm_jmp" style="width:50">
                        <option value="상"><%=messageService.getString("e3ps.message.ket_message", "03359", 1) %><%--{0,choice,1#상|2#중|3#하}--%></option>
                        <option value="중"><%=messageService.getString("e3ps.message.ket_message", "03359", 2) %><%--{0,choice,1#상|2#중|3#하}--%></option>
                        <option value="하" selected><%=messageService.getString("e3ps.message.ket_message", "03359", 3) %><%--{0,choice,1#상|2#중|3#하}--%></option>
                        </select>
                    </td>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
                    <td width="230" class="tdwhiteL0">
                        <select name="importance" class="fm_jmp" style="width:50">
                            <option value="상"><%=messageService.getString("e3ps.message.ket_message", "03359", 1) %><%--{0,choice,1#상|2#중|3#하}--%></option>
                            <option value="중"><%=messageService.getString("e3ps.message.ket_message", "03359", 2) %><%--{0,choice,1#상|2#중|3#하}--%></option>
                            <option value="하" selected><%=messageService.getString("e3ps.message.ket_message", "03359", 3) %><%--{0,choice,1#상|2#중|3#하}--%></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%> <font color="red">*</font></td>
                    <td colspan="3" class="tdwhiteL0">
                        <input type="text" name="subject" style="width:100%;" class="txt_field">
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%> <font color="red">*</font></td>
                    <td colspan="3" class="tdwhiteL0">
                        <textarea name="question" rows=7 style="width:100%;height:70px" class="txt_field"></textarea>
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                    <td colspan="3" class="tdwhiteM0">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_border">
                        <tr>
                            <td width="40"  class="tdgrayM"><a href="javascript:insertFileLine()"><img src="/plm/portal/images/b-plus.png"></a></td>
                            <td colspan="2"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                        </tr>
                        <tbody id="secondaryTable"/>
                        <%
                        if ( issue != null ) {
                            ContentHolder contentHolder = ContentHelper.service.getContents(issue);
                            Vector secondaryFiles = ContentUtil.getSecondaryContents(contentHolder);

                            if ( secondaryFiles.size() > 0 ) {
                                for(int i=0; i<secondaryFiles.size(); i++) {
                                    ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);

                                    String iconpath = "";
                                    String urlpath = "";
                                    if ( info == null ) {
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
                    </td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="700">
                <tr>
                    <td class="space5"></td>
                </tr>
                </table>
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00268") %><%--Issue 긴급도 및 중요도에 따른 메일 공지--%></td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="700">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="700">
                <tr>
                    <td width="100" class="tdblueM" rowspan=2 colspan=2><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                    <td width="300" class="tdblueM" colspan=3><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
                    <td width="200" class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
                </tr>
                <tr>
                    <td width="100" class="tdblueM" rowspan=1><%=messageService.getString("e3ps.message.ket_message", "03359", 1) %><%--{0,choice,1#상|2#중|3#하}--%></td>
                    <td width="100" class="tdblueM" rowspan=1><%=messageService.getString("e3ps.message.ket_message", "03359", 2) %><%--{0,choice,1#상|2#중|3#하}--%></td>
                    <td width="100" class="tdblueM" rowspan=1><%=messageService.getString("e3ps.message.ket_message", "03359", 3) %><%--{0,choice,1#상|2#중|3#하}--%></td>
                </tr>
                <tr>
                    <td width="50" class="tdblueM" rowspan=3><%=messageService.getString("e3ps.message.ket_message", "01158", "<br>", "<br>") %><%--긴{0}급{1}도--%>
                    </td>
                    <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03359", 1) %><%--{0,choice,1#상|2#중|3#하}--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02376") %><%--임원--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02376") %><%--임원--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02376") %><%--임원--%></td>
                    <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00373") %><%--PM 및 CFT 포함--%></td>
                </tr>
                <tr>
                    <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03359", 2) %><%--{0,choice,1#상|2#중|3#하}--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02376") %><%--임원--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02954") %><%--팀장--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02954") %><%--팀장--%></td>
                    <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00373") %><%--PM 및 CFT 포함--%></td>
                </tr>
                <tr>
                    <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03359", 3) %><%--{0,choice,1#상|2#중|3#하}--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02376") %><%--임원--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02954") %><%--팀장--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02953") %><%--팀원--%></td>
                    <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00373") %><%--PM 및 CFT 포함--%></td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
    </td>
</tr>
</table>
</form>
</body>
</html>
