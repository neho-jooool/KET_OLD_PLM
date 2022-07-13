<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="ext.ket.shared.content.service.KETContentHelper"%>
<%@page import="ext.ket.shared.content.entity.ContentDTO"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.Vector"%>
<%@page import="wt.content.*"%>
<%@page import="e3ps.project.*,
                        e3ps.project.beans.*,
                        e3ps.common.util.*,
                        e3ps.common.content.*" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");

    Object obj = CommonUtil.getObject(oid);

    String pjtLocation = "";
    String taskLocation = "";
    String taskOid = "";
    String command = request.getParameter("command");
    if(command == null){
        command = "";
    }
    String complateDetail = StringUtil.checkNull( request.getParameter("complateDetail") );
    String detail = "";
    String endDate = "";
    String etcDetail = "";
    String reassign = StringUtil.checkNull( request.getParameter("reassign") );
    String attr2 =  StringUtil.checkNull( request.getParameter("attr2") );

    ProjectIssue issue = null;
    ProjectIssueAnswer answer = null;

    ProjectIssueData data = null;
    ProjectIssueAnswerData answerData = null;

    IssueType[] issueTypeList = IssueType.getIssueTypeSet();

    boolean isAnswer = false;
    boolean isAssignCheck = false;
    boolean isAdmin = CommonUtil.isAdmin();
    boolean isAnswerCheck = true;
    boolean isAegisTarget = false;
    boolean isPm = false;
    String p_urlpath  = "";
    String p_iconpath = "";
    String p_name = "";
    String p_fileSize = "";
    ContentDTO dto = null;
    String aegisStauts = "";
    if ( obj instanceof ProjectIssue ) {
        issue = (ProjectIssue)obj;

        data = new ProjectIssueData(issue);
        aegisStauts = data.aegisStatus;
        pjtLocation = data.project.getPjtName();
        if(issue.getIssueAttr1() != null){
            detail = issue.getIssueAttr1();
        }
        if(issue.getIssueAttr2() != null){
            etcDetail = issue.getIssueAttr2();
        }
        if(data.task!=null){
            taskLocation = data.task.getTaskName();
            taskOid = data.task.getPersistInfo().getObjectIdentifier().toString();
        }
        if(data.planDoneDate != null){
            endDate = DateUtil.getDateString(data.planDoneDate, "d");
        }
        boolean isCheck = false;

        Vector vec2 = ProjectIssueHelper.manager.getIssueAnswer(issue);
        if(vec2.size() != 0){
            for(int i = 0; i < vec2.size(); i++ ) {
                ProjectIssueAnswerData aData = (ProjectIssueAnswerData) vec2.get(i);
                ProjectIssueAnswer pa = aData.answer;
                isCheck = pa.isIsCheck();
                if(!isCheck){
                    break;
                }
            }
        }
        // 해결방안 등록 여부 false && 이슈 미완료 && 해결방안이 모두 완료 여부 true
        isAssignCheck = !issue.isIsAnswerDone() && !issue.isIsDone() && isCheck;
        
        if(data.project instanceof ProductProject){
	        WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	        WTUser pm = ProjectUserHelper.manager.getPM(data.project);
	        if(user.getName().equals(pm.getName())){
	            isPm = true;
	        }
	        
	        if(isPm && !"Y".equals(issue.getAegisTrans()) && !data.isCheck){
	            isAegisTarget = true;
	        }
	        
	        
	        if(issue != null){
	            dto = KETContentHelper.manager.getPrimaryContent(issue);
	            if(dto!=null){
	                p_urlpath = dto.getDownURLStr();
	                p_iconpath = dto.getIconURLStr();
	                p_name = dto.getName();
	                p_fileSize = dto.getFileSizeKB();
	            }
	        
	        }
        }
        
    } else if ( obj instanceof ProjectIssueAnswer ) {
        answer = (ProjectIssueAnswer)obj;
        //해결 방안 완료 여부
        isAnswerCheck = answer.isIsCheck();
        issue = ProjectIssueHelper.manager.getIssue(answer);
        if(issue.getIssueAttr1() != null){
            detail = issue.getIssueAttr1();
        }
        if(issue.getIssueAttr2() != null){
            etcDetail = issue.getIssueAttr2();
        }

        isAnswer = true;
        data = new ProjectIssueData(issue);
        pjtLocation = data.project.getPjtName();

        if(data.task!=null){
            taskLocation = data.task.getTaskName();
            taskOid = data.task.getPersistInfo().getObjectIdentifier().toString();
        }

        if(data.planDoneDate != null){
            endDate = DateUtil.getDateString(data.planDoneDate, "d");
        }

        answerData = new ProjectIssueAnswerData(answer);
        isAegisTarget = false;
    }

%>

<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%@page import="java.util.Collections"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00272") %><%--Issue 상세정보--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript>
    function issueUpdate(){
        location.href="/plm/jsp/project/projectIssueUpdate.jsp?oid=<%=oid%>";
    }

    function issueAnswerUpdate(){
        location.href="/plm/jsp/project/projectIssueAnswerUpdate.jsp?oid=<%=oid%>";
    }
    function issueAnswerUpdateOne(oid){
        location.href="/plm/jsp/project/projectIssueAnswerUpdate.jsp?oid="+oid;
    }
    function issueReference(oid){
        location.href="/plm/jsp/project/projectIssueAnswerReference.jsp?oid="+oid;
    }


    function answer(){
        location.href="/plm/jsp/project/projectIssueAnswer.jsp?oid=<%=oid%>";
    }

    function completeDetail(){
        location.href="/plm/jsp/project/projectIssueView.jsp?command=complateDetail&oid=<%=oid%>";
    }
    function etcDetail(){
        location.href="/plm/jsp/project/projectIssueView.jsp?command=etc&oid=<%=oid%>";
    }


    //Move Function
    function onMovePage(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg == 'false') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00281") %><%--Issue완료를 실패하였습니다 다시 시도해 주십시요--%>");
            return;
        }

        alert("<%=messageService.getString("e3ps.message.ket_message", "00280") %><%--Issue를 완료 했습니다--%>");
        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");

        location.href="/plm/jsp/project/projectIssueView.jsp?command=&oid=<%=oid%>";
    }

    function onMovePageA(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg == 'false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03168") %><%--해결방안 완료를 실패하였습니다 다시 시도해 주십시요--%>');
            return;
        }

        alert('<%=messageService.getString("e3ps.message.ket_message", "03171") %><%--해결방안을 완료 했습니다--%>');
        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");

        location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
        //document.location.reload();
        //opener.document.location.reload();
    }

    function onMovePageAssign(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg == 'false') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01455") %><%--미완료 업무 지정이  실패하였습니다 다시 시도해 주십시요--%>");
            return;
        }

        alert("<%=messageService.getString("e3ps.message.ket_message", "01456") %><%--미완료 업무 지정이  완료 했습니다--%>");
        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");

        location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
        //document.location.reload();
        //opener.document.location.reload();
    }
    function onMovePageEtc(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg == 'false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01141") %><%--기타 사항 입력이 실패하였습니다 다시 시도해 주십시요--%>');
            return;
        }

        alert('<%=messageService.getString("e3ps.message.ket_message", "01140") %><%--기타 사항 입력을 완료 했습니다--%>');
        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");

        location.href="/plm/jsp/project/projectIssueView.jsp?oid=<%=oid%>";
    }

    function complete() {
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03321") %><%--완료를 하시겠습니까?--%>")) {
            return;
        }

        if(isNullData(document.forms[0].complateDetail.value)) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02309") %><%--이슈 완료 내용를 입력해 주십시오--%>');
            return;
        }
        var url = "/plm/jsp/project/ProjectIssueCompleteAjaxAction.jsp?command=complete&oid=<%=oid%>&complateDetail="+encodeURI(document.forms[0].complateDetail.value);
        callServer(url, onMovePage);
    }
    function issueAnswerAssign(){
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03299") %><%--다시 업무를 지정 하시겠습니까?--%>")) {
            return;
        }
        if(isNullData(document.forms[0].reassign.value)) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01459") %><%--미완료지정사유를 입력해 주십시오--%>");
            return;
        }
        var url = "/plm/jsp/project/ProjectIssueCompleteAjaxAction.jsp?command=assign&oid=<%=oid%>&reassign="+encodeURI(document.forms[0].reassign.value);
        callServer(url, onMovePageAssign);
    }
    function issueAnswerComplate(obj){
        var url = "/plm/jsp/project/ProjectIssueCompleteAjaxAction.jsp?command=complateA&oid="+obj;
        callServer(url, onMovePageA);
    }

    function etcAction(){
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03298") %><%--기타 사항을 등록 하시겠습니까?--%>")) {
            return;
        }
        if(isNullData(document.forms[0].attr2.value)) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01142") %><%--기타 사항을 입력해 주십시오--%>');
            return;
        }
        var url = "/plm/jsp/project/ProjectIssueCompleteAjaxAction.jsp?command=etc&oid=<%=oid%>&etcDetail="+encodeURI(document.forms[0].attr2.value);
        callServer(url, onMovePageEtc);
    }


    function viewTask(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
    }

    function AnswerAssign(){
        location.href="/plm/jsp/project/projectIssueView.jsp?command=assign&oid=<%=oid%>";
    }

    function mandate(){
        location.href="/plm/jsp/project/projectIssueUpdate.jsp?oid=<%=oid%>&mandate=ok";
    }
    
    function aegisTrans(){
    	location.href="/plm/jsp/project/projectIssueUpdate.jsp?oid=<%=oid%>&isAegis=ok";
    }
    
    function aegisComplete(){
    	if(!confirm("최종 완료 처리 하시겠습니까?")) {
            return;
        }
        var url = "/plm/jsp/project/ProjectIssueCompleteAjaxAction.jsp?command=completeAegis&oid=<%=oid%>";
        callServer(url, onMovePage);
    }

</script>
</head>

<body class="popup-background02 popup-space" style="margin-left: 5px!important;margin-right: 5px!important">
    <form>
        <input type=hidden name=oid value=<%=oid%>>

        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <table style="width: 100%; height: 28px;" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">CFT요청</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <%if("Y".equals(issue.getAegisTrans()) && issue.isIsDone() && isPm && !"NORMAL".equals(issue.getAegisStatus())){ %>
                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:aegisComplete()" class="btn_blue">최종완료</a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <%} %>
                                                    <%if("complateDetail".equals(command) || "assign".equals(command)|| "etc".equals(command)){ %>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <%if("assign".equals(command)){ %>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:issueAnswerAssign()"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02711") %><%--지정--%></a></td>
                                                                <%}else if("complateDetail".equals(command)){ %>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:complete()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></a></td>
                                                                <%}else if("etc".equals(command)){ %>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:etcAction()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%></a></td>
                                                                <%} %>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="history.back();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <%}else{ %>
                                                    <%
                  if( (data.isQuestionUser) && !issue.isIsDone() && obj instanceof ProjectIssue && isAssignCheck) { %>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:etcDetail()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01150") %><%--기타사항--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:completeDetail()"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00274") %><%--Issue 완료--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:AnswerAssign();"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01457") %><%--미완료 업무 할당--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>

                                                    <% } %>
                                                    <% if((((isPm && "Y".equals(issue.getAegisTrans())) || ( data.isQuestionUser ) )&& !issue.isIsDone()) && obj instanceof ProjectIssue ) { %>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:issueUpdate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00273") %><%--Issue 수정--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <% } %>
                                                    <% if(!isAnswer && ( data.isManagerUser) && data.issue.isIsAnswerDone() ) { %>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:answer();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03173") %><%--해결방안 등록--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:mandate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02233") %><%--위임--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <% } %>
                                                    <% if( isAnswer && ( answerData.isAnswerUser) && !issue.isIsDone() && data.issue.isIsAnswerDone() && !isAnswerCheck) {%>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:issueAnswerUpdate();"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03175") %><%--해결방안 수정--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <% } %>
                                                    <% } %>
                                                    <td width="5">&nbsp;</td>
                                                    <%if(isAegisTarget){ %>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                           <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                           <td class="btn_blue" background="../../portal/images/btn_bg1.gif">
                                                               <a href="javascript:;" onClick="javascript:aegisTrans();" class="btn_blue">AEGIS 이관</a></td>
                                                           <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                        </table>
                                                     </td>
                                                     <%} %>

                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onClick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <%
              if(data.task!=null){
              %>
                                    <tr>
                                        <td width="100" class="tdblueL">Project Name</td>
                                        <td width="250" class="tdwhiteL">&nbsp;<%=pjtLocation%></td>
                                        <td width="100" class="tdblueL">Task</td>
                                        <td colspan="3" class="tdwhiteL0">&nbsp;<a href="javascript:;"
                                            onclick="javascript:viewTask('<%=taskOid%>');"><%=taskLocation%></a></td>
                                    </tr>

                                    <%
              }else{
              %>
                                    <tr>
                                        <td width="100" class="tdblueL">Project Name</td>
                                        <td width="250" class="tdwhiteL0" colspan="5">&nbsp;<%=pjtLocation%></td>
                                    </tr>
                                    <%
              }
            %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                                        <td width="250" class="tdwhiteL">&nbsp;<b><%=data.questionUser.name%></b></td>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02522") %><%--제기일자--%></td>
                                        <td colspan="3" class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(data.questionDate,"date")%></td>
                                    </tr>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
                                        <td width="250" class="tdwhiteL">&nbsp;<%=data.issueType%></td>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%>
                                        </td>
                                        <td width="250" colspan="3" class="tdwhiteL0">&nbsp;<%=data.isCheck?messageService.getString("e3ps.message.ket_message", "02171")/*완료*/ : messageService.getString("e3ps.message.ket_message", "01454")/*미완료*/ %></td>
                                    </tr>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                                        <td width="250" class="tdwhiteL">&nbsp;<b><%=data.managerUser.people.getName() %></b></td>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01157") %><%--긴급 여부--%></td>
                                        <td width="50" class="tdwhiteL">&nbsp;<%=data.urgency%></td>
                                        <td width="50" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
                                        <td width="50" class="tdwhiteL0">&nbsp;<%=data.importance%></td>
                                    </tr>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                                        <td colspan="5" class="tdwhiteL0">&nbsp;<%=data.subject%></td>
                                    </tr>

                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
                                        <td colspan="5" class="tdwhiteL0"><%=(data.question=="")?"":data.question.toString().replaceAll(System.getProperty("line.separator") , "<br>")%></td>
                                    </tr>
                                    <%if("complateDetail".equals(command)){ %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02174") %><%--완료내용--%>
                                            <span class="red">*</span></td>
                                        <td colspan="5" class="tdwhiteL0"><input type="text" class='txt_field' name="complateDetail"
                                            onKeyUp="common_CheckStrLength(this, 66)" onKeyDown="common_CheckStrLength(this, 100)"
                                            style="width: 100%;"></td>
                                    </tr>
                                    <%} %>

                                    <%if("etc".equals(command)){ %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01150") %><%--기타사항--%>
                                            <span class="red">*</span></td>
                                        <td colspan="5" class="tdwhiteL0"><input type="text" name="attr2" class='txt_field'
                                            onKeyUp="common_CheckStrLength(this, 66)" value="<%=etcDetail%>"
                                            onKeyDown="common_CheckStrLength(this, 100)" style="width: 100%;"></td>
                                    </tr>
                                    <%} %>


                                    <%if(issue.isIsDone()){ %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02174") %><%--완료내용--%></td>
                                        <td colspan="5" class="tdwhiteL0">&nbsp;<%=detail%></td>
                                    </tr>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02855") %><%--최종완료일--%></td>
                                        <td colspan="5" class="tdwhiteL0">&nbsp;<%=endDate%></td>
                                    </tr>
                                    <%} %>
                                    <%if(!"".equals(etcDetail)){ %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01150") %><%--기타사항--%></td>
                                        <td colspan="5" class="tdwhiteL0">&nbsp;<%=etcDetail%></td>
                                    </tr>
                                    <%} %>
                                    <% if ( data.isQuestionAttache ) { %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                        <td colspan="5" class="tdwhiteL0">
                                            <%
                    for ( int i = 0 ; i < data.questionAttacheVec.size() ; i++ ) {
                        ContentInfo info = (ContentInfo)data.questionAttacheVec.get(i);
                        String urlpath="";
                        String iconpath="";
                        if( info == null) {
                            iconpath = "";
                            urlpath = "";
                        } else {
                            ContentItem ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());
                            String appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                            //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+oid+"&adOid="+appDataOid;
                            urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + oid + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                             urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                            iconpath = info.getIconURLStr();
                            iconpath = iconpath.substring(iconpath.indexOf("<img"));
                        }
                    %> <%=iconpath%>&nbsp;<%=urlpath%><br> <%
                    }
                    %>
                                        </td>
                                    </tr>
                                    <% } %>
                                    <%if("Y".equals(issue.getAegisTrans())){ %>
                                    
                                    <tr>
                                        <td width="100" class="tdblueL">AEGIS<br>상태</td>
                                        <td colspan="5" class="tdwhiteL0"><%=aegisStauts %></td>
                                    </tr>
                                    
                                    <tr>
                                        <td width="100" class="tdblueL">AEGIS<br>내용</td>
                                        <%if(issue.getAegisComment() != null && StringUtils.isNotEmpty(issue.getAegisComment().toString())){ %>
                                        <td colspan="5" class="tdwhiteL0"><%=issue.getAegisComment().toString().replaceAll(System.getProperty("line.separator") , "<br>") %></td>
                                        <%}else{ %>
                                        <td colspan="5" class="tdwhiteL0"></td>
                                        <%} %>
                                    </tr>
                                    
                                    <tr>
	                                    <td width="100" class="tdblueL">AEGIS<br>파일</td>
	                                    <td colspan="5" class="tdwhiteL0">
		                                    <table border="0" cellpadding="0" cellspacing="0">
			                                    <tr>
				                                    <td>
					                                    <table border="0" cellspacing="0" cellpadding="0">
						                                    <tr>
							                                    <td>
							                                    <%if(dto != null){ %>
								                                    <a target='download' href=<%=p_urlpath%>><%=p_iconpath %></a>&nbsp;
								                                    <a href='<%=p_urlpath%>' target='download'><%=p_name %></a>&nbsp;(&nbsp;<%=p_fileSize%>&nbsp;)
							                                    <%} %>
							                                    </td>
						                                    </tr>                  
										                </table>
									                </td>
								                </tr>
						                    </table>
						               </td>
                                   </tr>
                                   <%} %>
                                </table> 
                                
                                <% if( answer!=null ) { %>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space15"></td>
                                    </tr>
                                </table>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03174") %><%--해결방안 보기--%></td>
                                        <td align="right">&nbsp;</td>
                                    </tr>
                                </table> 
                                <%
                                WTUser sessionWTUser = (WTUser)wt.session.SessionHelper.manager.getPrincipal();
                                boolean isUser = false;
                                if( answer.getOwner().getName().equals(sessionWTUser.getName())   ){
                                    isUser = true;
                                }
                                %>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td align="right">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td align="right">&nbsp;</td>
                                                    <% if(  !answer.isIsCheck() && isUser ) { %>
                                                    <td align="right">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;"
                                                                    onClick="javascript:issueAnswerUpdateOne('<%=CommonUtil.getOIDString(answer)%>');"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03175") %><%--해결방안 수정--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td align="right">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;"
                                                                    onClick="javascript:issueAnswerComplate('<%=CommonUtil.getOIDString(answer)%>');"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03176") %><%--해결방안 완료--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <%} %>
                                                    <td width="5">&nbsp;</td>
                                                    <td align="right">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;"
                                                                    onClick="javascript:issueReference('<%=CommonUtil.getOIDString(answer)%>');"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02765") %><%--참조등록--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space10"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03172") %><%--해결방안--%></td>
                                        <td colspan="3" class="tdwhiteL">
                                        <%="".equals(answerData.HistoryAttr)?answerData.answerStr:answerData.HistoryAttr.replaceAll(System.getProperty("line.separator") , "<br>")%>
                                        
                                        <%-- <textarea name="detail" style="width: 100%" rows="4"
                                                class="fm_area" readOnly><%="".equals(answerData.HistoryAttr)?answerData.answerStr:answerData.HistoryAttr%></textarea> --%>
                                        </td>
                                    </tr>
                                    <%if(!"".equals(answerData.HistoryAttr)){
                   String getAttr2 = "";
              if(answerData.answer.getAttr2() != null ){
                  getAttr2 = "";
              }
              if(!"".equals(answerData.answer.getAttr2()) ){
                  getAttr2 = answerData.answer.getAttr2();
              %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02235") %><%--위임사유--%></td>
                                        <td colspan="3" class="tdwhiteL"><textarea name="detail" style="width: 100%" rows="3"
                                                class="fm_area" readOnly><%=getAttr2%></textarea></td>
                                    </tr>
                                    <%}
              }
              %>

                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02177") %><%--완료예정일자--%></td>
                                        <td class="tdwhiteL">&nbsp;<%=answerData.answer.getPlanDate() == null ? "" :DateUtil.getDateString(answerData.answer.getPlanDate(), "date")%></td>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
                                        <td class="tdwhiteL0">
                                            <%if(!"".equals(answerData.HistoryAttr)){
                      out.print(messageService.getString("e3ps.message.ket_message", "02233")/*위임*/);
                  }else if(answerData.answer.isIsCheck()){
                      out.print(messageService.getString("e3ps.message.ket_message", "02171")/*완료*/);
                  }else{out.print(messageService.getString("e3ps.message.ket_message", "01454")/*미완료*/);}
                %>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                                        <td width="250" class="tdwhiteL">&nbsp;<b><%=answerData.answerUser.name%></b></td>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                                        <td width="250" class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(answerData.answerDate,"date")%></td>
                                    </tr>
                                    <%  if( answerData.isAnswerAttache ) { %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                        <td colspan="3" class="tdwhiteL0">
                                            <%

                        for ( int i = 0 ; i < answerData.answerAttacheVec.size() ; i++ ) {
                            ContentInfo info = (ContentInfo)answerData.answerAttacheVec.get(i);
                            String iconpath = "";
                            iconpath = info.getIconURLStr();
                            iconpath = iconpath.substring(iconpath.indexOf("<img"));
                %> <%=iconpath%>
                                            <%if(i<(answerData.answerAttacheVec.size()-1))out.print("<br>");%> <%  } %>
                                        </td>
                                    </tr>
                                    <%  } %>
                                </table> <%
            Vector vecref = ProjectIssueHelper.manager.getIssueAnswerReference(answerData.answer);
            Collections.sort(vecref, new IssueAnswerComparator(false));
            for(int r = 0; r < vecref.size(); r++ ) {
                ProjectIssueAnswerData arefData = (ProjectIssueAnswerData) vecref.get(r);

            %>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td width="80"><img src="/plm/portal/images/icon_re.gif" border="0"></td>
                                        <td width="620">
                                            <table border="0" cellspacing="0" cellpadding="0" width="620">
                                                <tr>
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="620">
                                                <tr>
                                                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03178") %><%--해결방안 참조--%></td>
                                                    <td colspan="3" class="tdwhiteL"><textarea name="refdetail" style="width: 100%"
                                                            rows="3" class="fm_area" readOnly><%="".equals(arefData.HistoryAttr)?arefData.answerStr:arefData.HistoryAttr%></textarea></td>
                                                </tr>
                                                <tr>
                                                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                                    <td width="210" class="tdwhiteL">&nbsp;<b><%=arefData.answerUser != null ? arefData.answerUser.name : ""%></b></td>
                                                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                                                    <td width="210" class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(arefData.answerDate,"date")%></td>
                                                </tr>

                                                <%if ( arefData.isAnswerAttache ) {  %>
                                                <tr>
                                                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                                    <td colspan="3" class="tdwhiteL0">
                                                        <%
                                    for ( int j = 0 ; j < arefData.answerAttacheVec.size() ; j++ ) {
                                        ContentInfo info = (ContentInfo)arefData.answerAttacheVec.get(j);
                        %> <%=info.getDownURLStr()%>
                                                        <%if(j<(arefData.answerAttacheVec.size()-1))out.print("<br>");%> <%    }  %>
                                                    </td>
                                                </tr>
                                                <%} %>
                                            </table>

                                        </td>
                                    </tr>
                                </table> <%} %></td>
                        </tr>
                    </table> <% } else {
                    Vector vec = ProjectIssueHelper.manager.getIssueAnswer(issue);
                    Collections.sort(vec, new IssueAnswerComparator(false));
                    for(int i = 0; i < vec.size(); i++ ) {
                        ProjectIssueAnswerData aData = (ProjectIssueAnswerData) vec.get(i);


                        if(i == 0) {
            %>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="../../portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03174") %><%--해결방안 보기--%></td>
                            <td align="right">&nbsp;</td>
                        </tr>
                    </table> <%if("assign".equals(command)){ %>

                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <td width="97" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01458") %><%--미완료사유--%>
                            <span class="red">*</span></td>
                        <td colspan="3" class="tdwhiteL0"><input type="text" name="reassign" onKeyUp="common_CheckStrLength(this, 66)"
                            onKeyDown="common_CheckStrLength(this, 100)" style="width: 99%;"></td>
                    </table> 
                    <%} %> 
                    <%} %> 
                    <%
                    WTUser sessionWTUser = (WTUser)wt.session.SessionHelper.manager.getPrincipal();
                    boolean isUser = false;
                    if( aData.answer.getOwner().getName().equals(sessionWTUser.getName())   ){
                        isUser = true;
                    }
                    %>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td align="right">&nbsp;</td>
                                        <% if(  !aData.answer.isIsCheck() && isUser ) { %>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;"
                                                        onClick="javascript:issueAnswerUpdateOne('<%=aData.oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03175") %><%--해결방안 수정--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;"
                                                        onClick="javascript:issueAnswerComplate('<%=aData.oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03176") %><%--해결방안 완료--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%} %>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0" style="float: right">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;"
                                                        onClick="javascript:issueReference('<%=aData.oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02765") %><%--참조등록--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                       </tr>        
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03172") %><%--해결방안--%></td>
                            <td colspan="3" class="tdwhiteL">
                            <%="".equals(aData.HistoryAttr)?aData.answerStr:aData.HistoryAttr.replaceAll(System.getProperty("line.separator") , "<br>")%>
                            <%-- <textarea name="detail" style="width: 100%" rows="4" class="fm_area"
                                    readOnly><%="".equals(aData.HistoryAttr)?aData.answerStr:aData.HistoryAttr%></textarea> --%>
                                    
                                    </td>
                        </tr>
                        <%if(!"".equals(aData.HistoryAttr)){
                          String getAttr2 = "";
                          if(aData.answer.getAttr2() != null ){
                              getAttr2 = "";
                          }
                          if(!"".equals(aData.answer.getAttr2()) ){
                              getAttr2 = aData.answer.getAttr2();
                          %>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02235") %><%--위임사유--%></td>
                            <td colspan="3" class="tdwhiteL"><textarea name="detail" style="width: 100%" rows="3" class="fm_area"
                                    readOnly><%=getAttr2%></textarea></td>
                        </tr>
                        <%}
                          }%>
                        <%if(aData.answer.getAttr1() != null){
                            String reAssignDetail = aData.answer.getAttr1();
                        %>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01458") %><%--미완료사유--%></td>
                            <td colspan="3" class="tdwhiteL0">&nbsp;<%=reAssignDetail%></td>
                        </tr>
                        <%} %>

                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02177") %><%--완료예정일자--%></td>
                            <td class="tdwhiteL">&nbsp;<%=aData.answer.getPlanDate() == null ? "" :DateUtil.getDateString(aData.answer.getPlanDate(), "date")%></td>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
                            <td class="tdwhiteL0">
                                <%if(!"".equals(aData.HistoryAttr)){
                                  out.print(messageService.getString("e3ps.message.ket_message", "02233")/*위임*/);
                              }else if(aData.answer.isIsCheck()){
                                  out.print(messageService.getString("e3ps.message.ket_message", "02171")/*완료*/);
                              }else{out.print(messageService.getString("e3ps.message.ket_message", "01454")/*미완료*/);}
                            %>
                            </td>
                        </tr>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                            <td width="250" class="tdwhiteL">&nbsp;<b><%=aData.answerUser != null ? aData.answerUser.name : ""%></b></td>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                            <td width="250" class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(aData.answerDate,"date")%></td>
                        </tr>

                        <%    if ( aData.isAnswerAttache ) {  %>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                            <td colspan="3" class="tdwhiteL0">
                            <%
                            for ( int j = 0 ; j < aData.answerAttacheVec.size() ; j++ ) {
                                ContentInfo info = (ContentInfo)aData.answerAttacheVec.get(j);

                                String iconpath = "";
                                String urlpath = "";
                                if( info == null) {
                                    iconpath = "";
                                    urlpath = "";
                                } else {
                                    iconpath = info.getIconURLStr();
                                    urlpath = info.getDownURLStr();
                                }
                            %> <%=iconpath%><%=urlpath%>
                                <%if(j<(aData.answerAttacheVec.size()-1))out.print("<br>");%> <%    }  %>
                            </td>
                        </tr>
                        <%     } %>
                    </table> 
                    <%
                    Vector vecref = ProjectIssueHelper.manager.getIssueAnswerReference(aData.answer);
                    Collections.sort(vecref, new IssueAnswerComparator(false));
        
                    for(int r = 0; r < vecref.size(); r++ ) {
                        ProjectIssueAnswerData arefData = (ProjectIssueAnswerData) vecref.get(r);
        
                    %>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="80"><img src="/plm/portal/images/icon_re.gif" border="0"></td>
                            <td width="100%">
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03178") %><%--해결방안 참조--%></td>
                                        <td colspan="3" class="tdwhiteL"><textarea name="refdetail" style="width: 100%" rows="3"
                                                class="fm_area" readOnly><%="".equals(arefData.HistoryAttr)?arefData.answerStr:arefData.HistoryAttr%></textarea></td>
                                    </tr>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                        <td width="210" class="tdwhiteL">&nbsp;<b><%=arefData.answerUser != null ? arefData.answerUser.name : ""%></b></td>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                                        <td width="210" class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(arefData.answerDate,"date")%></td>
                                    </tr>

                                    <%if ( arefData.isAnswerAttache ) {  %>
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                        <td colspan="3" class="tdwhiteL0">
                                            <%
                                            for ( int j = 0 ; j < arefData.answerAttacheVec.size() ; j++ ) {
                                                ContentInfo info = (ContentInfo)arefData.answerAttacheVec.get(j);
                                                String iconpath = info.getIconURLStr();
                                                iconpath = iconpath.substring(iconpath.indexOf("<img"));
        
                                            %> <%=info.getDownURLStr()%>
                                            <%if(j<(arefData.answerAttacheVec.size()-1))out.print("<br>");%> <%}%>
                                        </td>
                                    </tr>
                                    <%} %>
                                </table>

                            </td>
                        </tr>
                    </table> <%} %> <%} %></td>
            </tr>
        </table>
        <%}%>
        </td>
    </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
