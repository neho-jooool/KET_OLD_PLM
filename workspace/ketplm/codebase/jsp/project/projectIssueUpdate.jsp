<%@page import="ext.ket.shared.mail.service.KETMailHelper"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="ext.ket.shared.content.entity.ContentDTO"%>
<%@page import="ext.ket.shared.content.service.KETContentHelper"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.*" %>
<%@page import="wt.content.*,
                wt.fc.EnumeratedTypeUtil,
                wt.org.WTPrincipal,
                wt.org.WTPrincipalReference,
                wt.org.WTUser"%>
<%@page import="e3ps.common.content.*,
                e3ps.common.content.uploader.*,
                e3ps.common.util.*,
                e3ps.project.*,
                e3ps.project.beans.*"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.fc.PersistenceHelper"%>

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
    while ( st.hasMoreTokens() ) {
        docTypes[i] = st.nextToken();
        i++;
    }
    return docTypes;
}
%>

<%
    IssueType[] issueTypeList = IssueType.getIssueTypeSet();

    String contentType = request.getContentType();

    FormUploader fileUploader = FormUploader.newFormUploader(request);
    Hashtable param = fileUploader.getFormParameters();

    String oid          = (String) param.get("oid");
    String command      = (String) param.get("command");
    String subject      = (String) param.get("subject");
    String question     = (String) param.get("question");
    String comment     = (String) param.get("comment");
    String issueType    = (String) param.get("issueType");
    String planDoneDate = (String) param.get("planDoneDate");
    String ischeck      = (String) param.get("isCHECK");
    String urgency      = (String) param.get("urgency");
    String importance   = (String) param.get("importance");
    String attr2        = (String) param.get("attr2");
    String tempmasterMember = (String) param.get("tempmasterMember");
    String masterMember     = (String) param.get("masterMember");
    String deleteOID        = (String) param.get("deleteOID");
    // mandate를 null 값 비교하기 때문에 아래처럼 처리해야됨
    String mandate      = request.getParameter("mandate");
    String isAegis = request.getParameter("isAegis");
    String primaryDelete = (String) param.get("primaryDelete");

    ProjectIssue issue = (ProjectIssue)CommonUtil.getObject(oid);
    ProjectIssueData data = new ProjectIssueData(issue);
    String pjtLocation = "";
    String taskLocation = "";
    if ( data.project != null ) pjtLocation = data.project.getPjtName();
    if ( data.task != null ) taskLocation = data.task.getTaskName();

    //Kogger.debug("이슈 제기자>>>>> "+data.isQuestionUser);
    //Kogger.debug("이슈 담당자>>>>> "+data.isManagerUser);

    String tStr = "";
    if(data.isQuestionUser && !data.isManagerUser && mandate == null) {  //이슈 제기자
        tStr = "1";
        mandate = null;
    } else if(!data.isQuestionUser && data.isManagerUser && mandate == null) {  //이슈 담당자
        tStr = "2";
        mandate = null;
    } else if(data.isQuestionUser && data.isManagerUser && mandate == null) {  //이슈 제기자 && 이슈 담당자
        tStr = "3";
        mandate = null;
    } else if(data.isManagerUser || mandate!=null) {
        tStr = "5";
    } else{
        tStr = "4";
        mandate = null;
    }
    boolean isAegisEditable = false;
    
    if(data.project instanceof ProductProject){//제품프로젝트이면서 pm이고 aegis이관됐거나 화면에서 aegis 이관버튼 눌러서 들어온 경우
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
        WTUser pm = ProjectUserHelper.manager.getPM(data.project);
        if(user.getName().equals(pm.getName()) && ( "Y".equals(issue.getAegisTrans() ) || "ok".equals(isAegis) ) ){
            isAegisEditable = true;
        }
    }

    if ( "update".equals(command) ) {
        WTUser manager = null;
        if(StringUtil.checkString(masterMember)) {
            manager = (WTUser)CommonUtil.getObject(masterMember);
        }

        if(StringUtil.checkString(subject)) issue.setSubject(subject);
        if(StringUtil.checkString(question)) issue.setAnswer(question);
        if(StringUtil.checkString(comment)){
            issue.setAegisComment(comment);
            issue.setAegisCutOffComment(StringUtil.subByteData(comment, 4000));//AEGIS 이관시 BLOB 데이터 연동에 어려움이 있어 varchar2데이터로 변환(4000가 넘으면 ...  붙여서 4000byte맞춰서 변환)
        }
        isAegis = (String) param.get("isAegis");
        String oldManager = "";
        if("ok".equals(isAegis)){
            Department dept = data.managerUser.department;
            manager = PeopleHelper.manager.getChiefUser(dept);
            tempmasterMember = manager.getFullName();
            mandate = "ok";
            attr2 = "AEGIS 이관";
            issue.setAegisTrans("Y");
            issue.setAegisStatus("DANGER");
            issue.setAegisDate(DateUtil.getCurrentTimestamp());
            issue.setIsAnswerDone(true);
            issue.setAegisCutOffComment("하기 Issue가 AEGIS 에 전송되어 담당자가 "+issue.getManager().getFullName()+" 에서 "+tempmasterMember+" 으로 변경되었습니다.");
        }
        
        WTPrincipalReference oldUser = null;
        if(manager != null) {
            oldManager = issue.getManager().getFullName();
            oldUser = issue.getManager();
            String newManager = WTPrincipalReference.newWTPrincipalReference(manager).getFullName();

            if( !oldManager.equals(newManager) ) {
                issue.setManager(WTPrincipalReference.newWTPrincipalReference(manager));
            }
        }
        
        

        if(StringUtil.checkString(issueType)) {
            IssueType type = (IssueType)EnumeratedTypeUtil.toEnumeratedType(issueType);
            issue.setIssueType(type);
        }

        if(StringUtil.checkString(planDoneDate)) {
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(DateUtil.parseDateStr(planDoneDate));
            issue.setPlanDoneDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
        }

        if(StringUtil.checkString(ischeck)) {
            if("TRUE".equals(ischeck)) issue.setIsDone(true);
            else if("FALSE".equals(ischeck)) issue.setIsDone(false);
        }

        if(StringUtil.checkString(urgency))issue.setUrgency(urgency);
        if(StringUtil.checkString(importance))issue.setImportance(importance);

        //Kogger.debug("ischeck>>> " +issue.isIsDone());

        issue = ProjectIssueHelper.manager.modifyProjectIssue(issue);

        if ( "ok".equals(mandate) ) {
            WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();

            ProjectIssueAnswer issueAnswer = ProjectIssueAnswer.newProjectIssueAnswer();
            //Kogger.debug("issue>>> " +CommonUtil.getOIDString(issue));

            issueAnswer.setOwner(oldUser);

            issueAnswer.setCreateDate(DateUtil.getCurrentTimestamp());

            String history = "";
            history = messageService.getString("e3ps.message.ket_message", "00038", user.getFullName(), tempmasterMember)/*{0}이(가) {1}에게 위임하였습니다*/;
            
            if("ok".equals(isAegis)){
        	   history = "하기 Issue가 AEGIS 에 전송되어 담당자가 "+oldManager+" 에서 "+tempmasterMember+" 으로 변경되었습니다.";
            }
            
            Kogger.debug(history);
            issueAnswer.setHistoryAttr(history);

            //완료 여부
            issueAnswer.setIsCheck(true);
            //위임 사유
            if(attr2 == null) {
                attr2 = "";
            }
            issueAnswer.setAttr2(attr2);
            //해결 방안 : 위임 강제 입력
            issueAnswer.setQuestion("위임");

            issueAnswer = (ProjectIssueAnswer) PersistenceHelper.manager.save(issueAnswer);

            issueAnswer = ProjectIssueHelper.manager.createProjectIssueAnswer(issue, issueAnswer);

            // 위임 메일 공지
            ArrayList<WTUser> to = new ArrayList<WTUser>();
            to.add((WTUser)issue.getManager().getPrincipal());
            KETMailHelper.service.sendFormMail("08136", "NoticeMailLine3.html", issue, (WTUser)user.getPrincipal() , to);
            /* if(true){
                ProjectHelper.projectSendMail(issue, "reAssign");
            } */
        }

        //첨부파일 관련
        //기존 첨부파일 중 남은 파일 목록
        Vector tVec = new Vector();
        String[] token = tokenDocType(deleteOID, "&");
        for(int i = 0; i < token.length; i++) {
            //Kogger.debug("toKEN<<< "+token[i]);
            tVec.add(token[i]);
        }
        //기존 존재하던 모든 파일 목록
        Vector oldFiles = ContentUtil.getSecondaryContents(issue);
        
        for(int i = 0; i < oldFiles.size(); i++) {
            ContentInfo info = (ContentInfo) oldFiles.elementAt(i);

            if( tVec.contains(info.getContentOid()) ) {
                //Kogger.debug("Delect>>>> " + info.getContentOid());
                issue = (ProjectIssue)E3PSContentHelper.service.delete(issue, (ApplicationData)CommonUtil.getObject(info.getContentOid()));
            }
        }
        
        if("ok".equals(primaryDelete) ){
            
            ContentHolder holder = ContentHelper.service.getContents(issue);
            Vector<ContentItem> result = ContentHelper.getContentListAll(holder);
            Iterator<ContentItem> iter = result.iterator();
            while (iter.hasNext()) {
	            ContentItem item = iter.next();
	            if (item.getRole() == ContentRoleType.PRIMARY) {  
	        	    KETContentHelper.service.delete(holder);
	                break;
	            }
	            
            }
        }

        if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 ) {

            Vector files = fileUploader.getFiles();

            if ( files != null ) {
                for ( int i = 0; i < files.size(); i++ ) {
                    
                    WBFile file = (WBFile)files.get(i);
                    if ("iFile0".equalsIgnoreCase(file.getFieldName())) {
                	
                	    ContentHolder holder = ContentHelper.service.getContents(issue);
                	    if(holder != null){
                		   Vector<ContentItem> result = ContentHelper.getContentListAll(holder);
                           Iterator<ContentItem> iter = result.iterator();
                           while (iter.hasNext()) {
                               ContentItem item = iter.next();
                               if (item.getRole() == ContentRoleType.PRIMARY) {  
                        	      KETContentHelper.service.delete(holder);
                                  break;
                               }                                    
                           }
                	    }
                        
                	   
                	   issue = (ProjectIssue)E3PSContentHelper.service.attach(issue, (WBFile)files.get(i), "", ContentRoleType.PRIMARY);
                    }else{
                	   issue = (ProjectIssue)E3PSContentHelper.service.attach(issue, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
                    }
                }
            }
            
        }
        
        
        
        
        
//     java.util.Vector files = uploader.getFiles();
//     for(int i = 0; i < files.size(); i++) {
//       issue = (ProjectIssue)E3PSContentHelper.service.attach(issue, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
//       //Kogger.debug("WBFile ["+i+"]>> "+(WBFile)files.get(i));
//     }
    }
    String appDataOid = "";
    String p_urlpath  = "";
    String p_iconpath = "";
    String p_name = "";
    String p_fileSize = "";
    ContentDTO dto = null;
    if(issue != null){
		dto = KETContentHelper.manager.getPrimaryContent(issue);
		if(dto!=null){
		    p_urlpath = dto.getDownURLStr();
		    p_iconpath = dto.getIconURLStr();
		    p_name = dto.getName();
		    p_fileSize = dto.getFileSizeKB();
		}
	
    }
    
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "02308") %><%--이슈 수정--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
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

    //alert("returnDel>>> "+returnDel);
    document.forms[0].deleteOID.value = returnDel;
}
// 첨부 파일 끝 *****************************************************************************************************************

function clearDate(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
}

function addRoleMember(rname) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    acceptRoleMember(rname,attacheMembers);
}

function acceptRoleMember(rname, objArr) {
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

function update(){
	
	<%if(isAegisEditable){%>
	var comment = document.forms[0].comment.value;
	
	if(isNullData(comment)){
		alert("AEGIS 내용을 입력하세요.");
		return;
	}
	<%}%>
	
    <% if(data.isQuestionUser || data.isManagerUser) { %>
    //이슈 타입
    if(document.forms[0].issueType != null) {
        var itLen = document.forms[0].issueType.length;
        for(var i = 0; i < itLen; i++) {
            if(isNullData(document.forms[0].issueType[i].value) && document.forms[0].issueType[i].selected) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02315") %><%--이슈 타입을 선택해 주십시오--%>');
                return;
            }
        }
    }

    //이슈 담당자
    if(document.forms[0].masterMember != null) {
        if(isNullData(document.forms[0].masterMember.value)) {
            <%if("5".equals(tStr) ){%>
            alert('<%=messageService.getString("e3ps.message.ket_message", "02238") %><%--위임자를 선택해 주십시오--%>');
            <%}else{%>
            alert('<%=messageService.getString("e3ps.message.ket_message", "02303") %><%--이슈 담당자를 입력해 주십시오--%>');
            <%}%>
            return;
        }
    }

    //이슈 제목
    if(document.forms[0].subject != null) {
        if(isNullData(document.forms[0].subject.value)){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02312") %><%--이슈 제목을 입력해 주십시오--%>');
            document.forms[0].subject.focus();
            return;
        }
    }

    //이슈 개요
    if(document.forms[0].question != null) {
        if(isNullData(document.forms[0].question.value)){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02298") %><%--이슈 개요를 입력해 주십시오--%>');
            document.forms[0].question.focus();
            return;
        }
    }
    <% } %>

    <% if(data.isManagerUser && mandate == null && false) { %>
    //이슈 완료 예정일자
    if(isNullData(document.forms[0].planDoneDate.value)) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02310") %><%--이슈 완료 예정일자를 입력해 주십시오--%>');
        return;
    }
    <%}%>

    <%if(mandate != null){ %>
    if(isNullData(document.forms[0].attr2.value)) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02234") %><%--위임 사유를 입력해 주십시오--%>');
        return;
    }

//  //이슈 완료 체크여부
//  if(document.forms[0].checkDone.checked) {
//    //alert(document.forms[0].checkDone.checked);
//    document.forms[0].isCHECK.value = "TRUE";
//  } else {
//    document.forms[0].isCHECK.value = "FALSE";
//  }

    //alert("ISCHECK<< " +document.forms[0].isCHECK.value);

    <% } %>
    var primaryDelete = document.getElementById('isPrimaryFileDelete').getAttribute("checked");
    if(primaryDelete){
    	document.forms[0].primaryDelete.value = "ok";
    }
    
    <%if(mandate != null){ %>
        document.forms[0].command.value = "update";
        document.forms[0].action = "/plm/jsp/project/projectIssueUpdate.jsp?mandate=ok";
        document.forms[0].submit();
    <%}else{ %>
        document.forms[0].command.value = "update";
        document.forms[0].action = "/plm/jsp/project/projectIssueUpdate.jsp";
        document.forms[0].submit();
    <%}%>

}

function cancel(){
    location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
}
<%if("update".equals(command)){%>
    location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
<%}%>

    function deleteCal(rname) {
        document.getElementById(rname).value = "";
    }
    
function copy() {
	alert("Issue 제목과 내용을 복사합니다.");
	
	var subject = '<%=data.subject%>'+'\r\n\r\n';
	
	var question = document.getElementById('aegisQuestion').value;
	
	document.getElementById('comment').value = subject+question;
}

function cutStr(str, limit){

    

    var strLength = 0;

    var strTitle = "";

    var strPiece = "";

    

    for (i = 0; i < str.length; i++){

        var code = str.charCodeAt(i);

        var ch = str.substr(i,1).toUpperCase();

        //체크 하는 문자를 저장

        strPiece = str.substr(i,1)

        code = parseInt(code);

        if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){

            strLength = strLength + 3; //UTF-8 3byte 로 계산

        }else{

            strLength = strLength + 1;

        }

        if(strLength>limit){ //제한 길이 확인
            break;
        }else{
            strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
        }

    }

    alert(strTitle);

}


</script>
</head>

<body>
<form name="projectIssueUpdate" method=POST enctype="multipart/form-data">
<input type=hidden name=oid value=<%=oid%>>
<input type=hidden name=mandate value=<%=mandate%>>
<input type=hidden name=isAegis value=<%=isAegis%>>
<input type=hidden name=command>
<input type=hidden name=deleteOID>
<input type=hidden name=isCHECK>
<input type=hidden name=primaryDelete value="no">
<textarea name="aegisQuestion" style="display: none" rows="5" class="fm_area" style="width:100%;"><%=data.question.toString().replaceAll(System.getProperty("line.separator") , "\r\n")%></textarea>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
               <%if("ok".equals(isAegis) ){ %>
               <td class="font_01">AEGIS 이관</td>
               <%}else{ %> 
               <%if("5".equals(tStr)){ %>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02237") %><%--위임자 등록--%></td>
              <%}else{ %>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00273") %><%--Issue 수정--%></td>
              <%}}%>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="710" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="9">&nbsp;</td>
          <td valign="top">
                <table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                  <td>&nbsp;</td>
                  <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>

                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif">
                            <a href="javascript:;" onClick="javascript:update();" class="btn_blue">
                            <%
                            if("ok".equals(isAegis)){
                        	%>
                        	이관
                        	<%}else if("5".equals(tStr)) {
                            %>
                            <%=messageService.getString("e3ps.message.ket_message", "02233") %><%--위임--%>
                            <%}
                            else{ %><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%} %>
                            </a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                            <%
                            if("ok".equals(isAegis)){
                            %>
                            <td>&nbsp;</td>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif">
                            <a href="javascript:;" onClick="javascript:copy();" class="btn_blue">
                                 내용 복사
                            </a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                            <%} %>
                            
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
                <td width="100" class="tdblueL">Project Name</td>
                <td width="250" class="tdwhiteL" colspan="5">&nbsp;<%=pjtLocation%></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                <td width="250" class="tdwhiteL"><%=data.questionUser.name%></td>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02522") %><%--제기일자--%></td>
                <td colspan="3" class="tdwhiteL0"><%=DateUtil.getDateString(data.questionDate,"date")%></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%><%=tStr.equals("1")||tStr.equals("3")?"<font color='red'>*</font>":"" %></td>
                <td width="240" class="tdwhiteL">
                    <% if(tStr.equals("1")||tStr.equals("3")) { %>
                    <select name="issueType" class="fm_jmp" style="width:150">
                        <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                        <%
                        for( int i=0; i<issueTypeList.length; i++ ) {
                            String selected = "";

                            if( !issueTypeList[i].isSelectable() ) continue;

                            if( (issueTypeList[i].getStringValue()).equals("e3ps.project.IssueType."+issue.getIssueType()) ) {
                                selected = "selected";
                            }
                        %>
                        <option value="<%=issueTypeList[i].getStringValue()%>" <%=selected %>><%=issueTypeList[i].getDisplay(messageService.getLocale())%></option>
                        <%
                        }
                        %>
                    </select>
                    <% } %>
                    <% if(tStr.equals("2") ||tStr.equals("5") || isAegisEditable) { %>
                    <%=data.issueType %>
                    <% } %>
                </td>
                <td width="100" class="tdblueL"><%if(tStr.equals("5")){ %><%=messageService.getString("e3ps.message.ket_message", "02189") %><%--위임자--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%><%}%><span class="red">*</span></td>
                <td width="240" colspan="3" class="tdwhiteL0">
                    <% if(tStr.equals("5")) { %>
                    <input type='text' name='tempmasterMember' value='' class='txt_field' style="width:60" readonly>
                    <input type='hidden' name='masterMember' value=''>
                    &nbsp;<a href="#" onClick="javascript:addRoleMember('masterMember');"><img src="../../portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;
                    <a href="#" onClick="javascript:deleteRoleMember('masterMember');"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                    <% } %>
                    <% if(tStr.equals("2")||tStr.equals("1")||tStr.equals("3") || isAegisEditable) { %>
                    <%=data.managerUser.people.getName() %>
                    <% } %>
                </td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><%=tStr.equals("1")||tStr.equals("3")?"<font color='red'>*</font>":"" %></td>
                <td width="240" class="tdwhiteL">
                    <% if(tStr.equals("1")||tStr.equals("3")) { %>
                    <input type="text" name="subject" class='txt_field' style="width:98%" id=i value='<%=data.subject%>' <%if(!data.isQuestionUser)out.print("readonly");%>>
                    <% } %>
                    <% if(tStr.equals("2") ||tStr.equals("5") || isAegisEditable) { %>
                    <%=data.subject %>
                    <% } %>
                </td>
                <% if(tStr.equals("2")||tStr.equals("3")||tStr.equals("5")) { %>


                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01157") %><%--긴급 여부--%></td>
                <td width="50" class="tdwhiteL"><%=issue.getUrgency() %></td>
                <td width="50" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
                <td width="50" class="tdwhiteL0"><%=issue.getImportance() %></td>

                <% }else  if(tStr.equals("1") || tStr.equals("3")) { %>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01157") %><%--긴급 여부--%></td>
                <td width="50" class="tdwhiteL"><select name="urgency" class="fm_jmp" style="width:50">
                    <option value="상" <%if("상".equals(issue.getUrgency())){ %> selected <%} %>>상</option>
                    <option value="중" <%if("중".equals(issue.getUrgency())){ %> selected <%} %>><%=messageService.getString("e3ps.message.ket_message", "02686") %><%--중--%></option>
                    <option value="하" <%if("하".equals(issue.getUrgency())){ %> selected <%} %>>하</option>
                  </select></td>
                <td width="50" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
                <td width="50" class="tdwhiteL0"><select name="importance" class="fm_jmp" style="width:50">
                    <option value="상" <%if("상".equals(issue.getImportance())){ %> selected <%} %>>상</option>
                    <option value="중" <%if("중".equals(issue.getImportance())){ %> selected <%} %>><%=messageService.getString("e3ps.message.ket_message", "02686") %><%--중--%></option>
                    <option value="하" <%if("하".equals(issue.getImportance())){ %> selected <%} %>>하</option>
                  </select></td>
            <%} %>

              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%><%=tStr.equals("1")?"<font color='red'>*</font>":"" %></td>
                <td colspan="5" class="tdwhiteL0">
                    <% if(tStr.equals("1") || tStr.equals("3")) { %>
                    <textarea name="question" rows="5" class="fm_area" style="width:100%;"><%=data.question%></textarea>
                    <% } else { %>
                    <textarea name="question" rows="5" class="fm_area" style="width:100%;" readOnly><%=data.question%></textarea>
                    <% } %>
                </td>
              </tr>
              <%if("ok".equals(mandate)){ %>
               <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02235") %><%--위임사유--%><span class="red">*</span></td>
                <td colspan="5" class="tdwhiteL0">
                    <input type="text" class='txt_field' name="attr2" onKeyUp="common_CheckStrLength(this, 66)" onKeyDown="common_CheckStrLength(this, 100)" style="width:99%" >
                </td>
              </tr>

              <%} %>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%><br>
                  </td>
                <td colspan="5" class="tdwhiteL0">&nbsp;<%=issue.isIsDone()?messageService.getString("e3ps.message.ket_message", "02171")/*완료*/ : messageService.getString("e3ps.message.ket_message", "01454")/*미완료*/ %></td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                <td colspan="5" class="tdwhiteM0"><table width="99%" border="0" cellpadding="0" cellspacing="0">
                <% if(tStr.equals("1")||tStr.equals("3")) { %>
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="99%" border="0" cellspacing="0" cellpadding="0" >
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
                  <table border="0" cellspacing="0" cellpadding="0" width="600">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="600" border="0" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="40"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                      <td colspan="2"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                    </tr>
                    <tbody id="secondaryTable"/>
                    <%
                        if(issue != null) {
                            ContentHolder contentHolder = ContentHelper.service.getContents(issue);
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
                      <td width="40" class="tdwhiteM"><input type="checkbox" name="secondarySelect" id="checkbox" value="<%=info.getContentOid()%>"></td>
                      <td width="450" class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                      <td width="90" class="tdwhiteL">&nbsp;</td>
                    </tr>
                    <%
                                }
                            }
                        }
                    %>
                  </table>
                <% } %>
                <% if(tStr.equals("2")||tStr.equals("5")) { %>
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="99% border="0" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td colspan="3"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                    </tr>
                    <%
                        if(issue != null) {
                            ContentHolder contentHolder = ContentHelper.service.getContents(issue);
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
                                        <td class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                                    </tr>
                    <%
                                }
                            }
                        }
                    %>
                  </table>
                <% } %>
                  <table width="99% border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table></td>
              </tr>
              
              <tr>
                <td width="100" class="tdblueL">AEGIS<br>내용<font color='red'>*</font></td>
                <td colspan="5" class="tdwhiteL0">
                    <textarea name="comment" rows="5" class="fm_area" style="width:100%;" <%if(!isAegisEditable){ %>readonly<%} %>><%=StringUtil.checkNull((String)issue.getAegisComment()) %></textarea>
                </td>

              </tr>
              <tr>
          <td width="105" class="tdblueL">AEGIS<br>파일</td>
          <td colspan="5" class="tdwhiteL0"><table border="0" cellpadding="0" cellspacing="0">
              <tr>

                <td><table border="0" cellspacing="0" cellpadding="0">
                  <%if(dto != null){ %>
                  <tr>
                  <td><%if(isAegisEditable){ %>삭제<%} %> <input type="checkBox" id="isPrimaryFileDelete">
                                                          <a target='download' href=<%=p_urlpath%>><%=p_iconpath %></a>&nbsp;
                                                          <a href='<%=p_urlpath%>' target='download'><%=p_name %></a>&nbsp;(&nbsp;<%=p_fileSize%>&nbsp;)
                                                          </td>
                  </tr>
                  <%}else{ %>
                  <td><input type="hidden" id="isPrimaryFileDelete" ></td>
                  <%} %>
                  <%if(isAegisEditable){ %>
                  <tr>
                    <input name="iFile0" type="file" class="txt_fieldRO" style="width:600;">
                  </tr>
                  <%} %>
                  
                </table></td>
              </tr>
          </table></td>
        </tr>
              
          </table></td>
        </tr>
      </table></td>
  </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
