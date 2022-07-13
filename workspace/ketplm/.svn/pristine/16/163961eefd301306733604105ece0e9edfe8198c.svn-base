<%@page import="e3ps.project.cancel.CancelProject"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="e3ps.project.cancel.ProjectCancelLink"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="wt.query.QuerySpec"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.Vector" %>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.Performance"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import="e3ps.project.beans.PerformanceHelper"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.beans.ProjectTreeNode"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
// [START] [PLM System 1차개선] Project WBS로 Vector 구성, 2013-08-15, BoLee
public void makeVector(ProjectTreeNode node, Vector vector){

    vector.add(node);

    for ( int i = 0; i < node.getChildCount(); i++ ) {
        makeVector((ProjectTreeNode)node.getChildAt(i), vector);
    }
}
// [END] [PLM System 1차개선] Project WBS로 Vector 구성, 2013-08-15, BoLee
%>

<%
    String oid = request.getParameter("oid");

    String popup = "";
    if(request.getParameter("popup") != null && request.getParameter("popup").length() > 0){
        popup = request.getParameter("popup");
    }
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");

    String tempPopup = "";
    if("popup".equals(popup)){
        tempPopup = "&popup=popup";
    }

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData mmData = new E3PSProjectData(project);
    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
    MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);

    // [START] [PLM System 1차개선] 일정변경구분 가져오기, 2013-08-05, BoLee
    int historyNoteType = project.getHistoryNoteType();
    // [END] [PLM System 1차개선] 일정변경구분 가져오기, 2013-08-05, BoLee

    MoldItemInfo moldInfo = moldProject.getMoldInfo();
    if(moldInfo == null){
        moldInfo = MoldItemInfo.newMoldItemInfo();
    }

    ProductProject productProject = null;
    E3PSProjectData ppdata = null;
    if(moldInfo != null){
        productProject = moldInfo.getProject();
        if(productProject != null){
            ppdata = new E3PSProjectData(productProject);
        }
        
    }
    boolean isCheckPlan = false;
    String proudctPlanEnd = "";
    if(ppdata != null && (mmData.pjtPlanEndDate != null && ppdata.pjtPlanEndDate != null) ){
        if( ppdata.pjtPlanEndDate.compareTo(mmData.pjtPlanEndDate) == -1 ){
            proudctPlanEnd = DateUtil.getDateString(ppdata.pjtPlanEndDate,"D");
            isCheckPlan = true;
        }
    }

    // [START] [PLM System 1차개선] [일정변경 완료] 버튼 표시 조건 추가(금형 Project WBS 생성 이후에만 표시), 2013-08-15, BoLee
    boolean isChild = false;
    ProjectTreeNode root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot((MoldProject)project, false);
    Vector list = new Vector();
    makeVector(root, list);

    if ( mmData.pjtPlanEndDate != null && list.size() > 1 ) {
        isChild = true;
    }
    // [END] [PLM System 1차개선] [일정변경 완료] 버튼 표시 조건 추가(금형 Project WBS 생성 이후에만 표시), 2013-08-15, BoLee

    ProjectViewButtonAuth productAuth =  null;
    
    if(productProject != null){
	   productAuth = new ProjectViewButtonAuth(productProject);
    }
    

    //##################################### 성과 관리
    Performance pf = null;
    QueryResult qrtest = PerformanceHelper.manager.searchPerformance(moldProject.getPjtNo());
    Object[] pobj = null;
    if(qrtest.hasMoreElements()){
        pobj = (Object[])qrtest.nextElement();
        pf = (Performance)pobj[0];
    }
    //##################################### 성과 관리
    
    boolean isPurchase = false;
    if ("구매품".equals(moldInfo.getItemType())) {
		isPurchase = true;
    }
    
     //프로젝트 취소 관련 소스 추가
    boolean isCanApp = true;
    String wfmApprovalMasterOid = "";
    QuerySpec spec = new QuerySpec();
    int masterIdx = spec.addClassList(KETWfmApprovalMaster.class, true);
    if(spec.getConditionCount()>0) spec.appendAnd();
    spec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class,
            "pboReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(project)), new int[] {masterIdx});
    spec.appendOrderBy(new OrderBy(new ClassAttribute(KETWfmApprovalMaster.class, "thePersistInfo.createStamp"),true), new int[]{masterIdx});
    QueryResult masterQr = PersistenceHelper.manager.find(spec);
    String test = String.valueOf(CommonUtil.getOIDLongValue(project));
    while(masterQr.hasMoreElements()){
        Object[] master = (Object[])masterQr.nextElement();
        KETWfmApprovalMaster appMaster = (KETWfmApprovalMaster)master[0];
        wfmApprovalMasterOid = CommonUtil.getOIDString(appMaster);


        //##### 프로젝트 취소 결재요청버튼 확인 시작
        QuerySpec cSpec = new QuerySpec(KETWfmApprovalHistory.class);

           cSpec.appendWhere(new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(appMaster)), new int[] {0});
           QueryResult historyQr = PersistenceHelper.manager.find(cSpec);
           KETWfmApprovalHistory history = null;

        while(historyQr.hasMoreElements()){
            history = (KETWfmApprovalHistory)historyQr.nextElement();
            if(history.getCompletedDate() == null){
                   //isCanApp = false;
            }
        }
        QueryResult CancelQr = ProjectHelper.getCancelProject(project);
        Object[] ProjectObj = null;
        CancelProject cpProject = null;
        
        while(CancelQr.hasMoreElements()){
            ProjectObj = (Object[]) CancelQr.nextElement();
            ProjectCancelLink ps = (ProjectCancelLink) ProjectObj[0];
            cpProject = ps.getCancle();
            if("취소중".equals(cpProject.getCancelType())){
                isCanApp = false;
            }
        }
        
        
        
      //##### 프로젝트 취소 결재요청버튼 확인 끝

    }
%>

<script language="JavaScript">
<!--
function debug(){
    var url="/plm/jsp/project/Debug.jsp?oid=<%=oid%>&making=<%=moldInfo.getMaking() %><%=tempPopup%>";
    openOtherName(url, "debug", 600, 550, "status=yes,scrollbars=no,resizable=yes");
}

// [START] [PLM System 1차개선] [일정변경] 버튼 클릭 시 confirm message 표시 후 일정변경(PLANCHANGE) 상태 변경, 2013-07-15, BoLee
function historyChange(oid) {

    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03281") %><%--Project 일정을 변경하시겠습니까?\n확인 시 Project 상태가 [일정변경] 상태로 변경됩니다.--%>") ) {

        showProcessing();   // 진행 상태바 표시 (processing.html)
        disabledAllBtn();   // 버튼 비활성화

        document.forms[0].action = "/plm/servlet/e3ps/ProjectScheduleServlet";
        document.forms[0].cmd.value = "change";
        document.forms[0].target = "_self";
        document.forms[0].submit();
    }
}
// [END] [PLM System 1차개선] [일정변경] 버튼 클릭 시 confirm message 표시 후 일정변경(PLANCHANGE) 상태 변경, 2013-07-15, BoLee

// [START] [PLM System 1차개선] 일정 변경 이력 팝업 사이즈 변경, 2013-07-07, BoLee
function projectHistory(){
    var url="/plm/jsp/project/ProjectHistoryList.jsp?oid=<%=oid%>";
    openOtherName(url,"process","950","550","status=no,scrollbars=no,resizable=no");
}
// [END] [PLM System 1차개선] 일정 변경 이력 팝업 사이즈 변경, 2013-07-07, BoLee

<%if(pf == null){%>
function viewPerformance(oid){
        var url = "/plm/jsp/project/performance/CreatePerformance.jsp?oid="+oid+"&pOid=<%=(pf!= null)?CommonUtil.getOIDString(pf):""%>";
        openSameName(url,"820","690","status=no,scrollbars=no,resizable=no");
}
<%}else{%>
function viewPerformance(oid){
        var url = "/plm/jsp/project/performance/ViewPerformance.jsp?oid="+oid+"&pOid=<%=(pf!= null)?CommonUtil.getOIDString(pf):""%>";
        openSameName(url,"850","730","status=no,scrollbars=no,resizable=no");
}
<%}%>

    function stateChangeStop(oid){
        var url = "/plm/jsp/project/StopHistory.jsp?oid="+oid;
        openOtherName(url,"ChangeStop","780","730","status=no,scrollbars=yes,resizable=no");
    }
    function stateChangeStopView(oid){
        var url = "/plm/jsp/project/StopHistoryList.jsp?oid="+oid;
        openSameName(url,"620","500","status=no,scrollbars=yes,resizable=yes");
    }

    function stateChangeReStart(oid){
        var url = "/plm/jsp/project/ReStartHistory.jsp?oid="+oid;
        openOtherName(url,"ChangeStop","780","730","status=no,scrollbars=yes,resizable=no");
    }
    
    function cancelProject(oid){
    	var url = "/plm/jsp/project/CancelProject.jsp?oid=" + oid;
        openOtherName(url,"프로젝트취소","800","700","status=no,scrollbars=yes,resizable=no");
    }


function stateChange(statevalue, message){

    if ( confirm("프로젝트를 " + message + "상태로 변경합니다.\n변경하시겠습니까?") ) {
            document.forms[0].action = "/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>";
            document.forms[0].command.value = "stateChange";
            document.forms[0].state.value = statevalue;
            document.forms[0].method = "post";
            document.forms[0].submit();
            //return;
    }
}

function deleteProject(){
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "03326") %><%--일정변경을 취소 하시겠습니까?--%>")){
        document.forms[0].mode.value="delete";
        document.forms[0].action="/plm/jsp/project/MoldProjectAction.jsp";
        document.forms[0].submit();
    }
}

function deleteProject2(){
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "03262") %><%--(관리자기능) 프로젝트를 삭제 하시겠습니까? (복구 불가)--%>")){
        document.forms[0].mode.value="delete2";
        document.forms[0].action="/plm/jsp/project/MoldProjectAction.jsp";
        document.forms[0].submit();
    }
}

function approvalAction(oid){

    <%if(isCheckPlan){%>
    alert('<%=messageService.getString("e3ps.message.ket_message", "02554", proudctPlanEnd) %><%--제품 프로젝트의 계획 종료일({0}) 일정 안에 \n금형 일정을 수립해야 합니다 \n금형 프로젝트 일정을 조정 하시기 바랍니다--%>');
    return;
    <%}%>
    var url="/plm/jsp/wfm/RequestApproval.jsp?pboOid="+oid;
    openOtherName(url,"approval","800","700","status=no,scrollbars=yes,resizable=no");
}

-->
</script>
<form name="frm">
<input type="hidden" name="command"></input>
<input type="hidden" name="state"></input>
<input type="hidden" name="popup" value="<%=popup %>"></input>
<input type="hidden" name="mode"></input>
<!-- [START] [PLM System 1차개선] Project 일정 변경 상태 변경 (PLANCHANGE), 2013-07-15, BoLee -->
<input type="hidden" name="oid" value="<%= oid %>">
<input type="hidden" name="cmd" value="">
<!-- [END] [PLM System 1차개선] Project 일정 변경 상태 변경 (PLANCHANGE), 2013-07-15, BoLee -->
</form>
<table border="0" cellspacing="0" cellpadding="0">
    <tr>

        <!-- 버튼 -->
        <%
            if (project.getLifeCycleState().toString().equals("PLANCHANGE") || project.getLifeCycleState().toString().equals("REWORK")) {
        %>
        <%
            if ((auth.isWorkingCopy && auth.isLatest && (auth.isPM || isbizAdmin)) || CommonUtil.isAdmin()) {
        %>
        <%
            //=project.getLifeCycleState()
        %>

        <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:deleteProject();"
                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02357")%><%--일정변경 취소--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>
        <%
            }
            }
        %>
        <%
            if (auth.isLatest && (CommonUtil.isAdmin() || isbizAdmin || auth.isPM) && auth.isPMOInWork ) {
        %>
        <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:deleteProject2();"
                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>
        <%
            }
        %>
        <%
            if (!auth.isCheckOut && auth.isLatest && (auth.isPM || isbizAdmin) && auth.isProgress) {
        %>
        <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:debug();"
                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01342")%><%--디버깅생성--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>
        <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                        onclick="javascript:historyChange('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02356")%><%--일정변경--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>
        <%
            }
        %>

        <%
            if (auth.isLatest && (auth.isPM || isbizAdmin) && auth.isProgress && auth.is100Complection) {
        %>
        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                        onclick="javascript:stateChange('COMPLETED', '완료 ');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171")%><%--완료--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>
        <%
            }
        %>

        <%
            if (auth.isLatest && (auth.isPM || (productAuth != null && productAuth.isPM) || isbizAdmin) && auth.isProgress) {
        %>
        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                        onclick="javascript:stateChangeStop('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02695")%><%--중지--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>
        <%
            }
        %>
        <%
        if (isCanApp && auth.isLatest && (auth.isPM || CommonUtil.isAdmin()) && (auth.isProgress || auth.isCanceling)) {
        %>
		<td>
		   <table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
					<td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
						href="#" onClick="javascript:cancelProject('<%=oid%>');"
						class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소(프로젝트취소)--%></a></td>
					<td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
				</tr>
			</table>
		</td>
		<td width="5">&nbsp;</td>
		<%} %>	
		<%
            // Kogger.debug("auth.isLatest && (auth.isPM) && auth.isSTOPED= " + (auth.isLatest && (auth.isPM) && auth.isSTOPED)  + " kkkk = " + !productAuth.isSTOPED);
            if (auth.isLatest && (auth.isPM || (productAuth != null && productAuth.isPM) || isbizAdmin) && auth.isSTOPED && productAuth != null && !productAuth.isSTOPED) {
        %>
        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                        onclick="javascript:stateChangeReStart('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02446")%><%--재시작--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>
        <%
            }
        %>
        <%
            //if (auth.isSTOPED || true) {
        %>
<%--         <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#"
                        onClick="javascript:stateChangeStopView('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01498")%>변경 사유</a></td>
                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td> --%>
        <%
            //}
        %>

        <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:projectHistory();"
                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01518")%><%--변경이력--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>
        <td width="5">&nbsp;</td>


        <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                        onclick="Javascript:viewHistory('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02270")%><%--이력--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td>

        <!-- 버튼 끝 -->

        <%
            // [PLM System 1차개선] 일정변경 완료 버튼 표시 조건(isChild) 추가(금형 Project WBS가 구성되었을 경우에만 [일정변경 완료] 버튼 표시), 2013-08-15, BoLee
            if (auth.isLatest && (auth.isPM || isbizAdmin) && (auth.isPlanChange || auth.isReWork) && isChild) {
        %>
        <%-- <td width="5">&nbsp;</td>
        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                        <!-- [START] [PLM System 1차개선] 일정변경 완료 버튼 추가(결재요청 버튼 주석처리), 2013-08-05, BoLee --> <a href="#" onclick="javascript:approvalAction('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청
                        </a> <%
     if (isCheckPlan) { // 금형 Project 계획완료일이 제품 Project 계획완료일보다 늦을 경우 alert message 표시
 %> <a href="#"
                        onclick="javascript:alert('<%=messageService.getString("e3ps.message.ket_message", "02554", proudctPlanEnd)%>제품 프로젝트의 계획 종료일({0}) 일정 안에 \n금형 일정을 수립해야 합니다 \n금형 프로젝트 일정을 조정 하시기 바랍니다');"
                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03361")%>일정변경 완료</a> <%
     } else { // 금형 Project 계획완료일이 제품 Project 계획완료일보다 이른 경우 일정변경 완료 처리
 %> <a href="#" onclick="javascript:completeChangePjtInfoSchedule('<%=oid%>', '<%=historyNoteType%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03361")%>일정변경 완료</a>
                        <%
                            }
                        %> <!-- [END] [PLM System 1차개선] 일정변경 완료 버튼 추가(결재요청 버튼 주석처리), 2013-08-05, BoLee -->
                    </td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table></td> --%>
        <%
            }
        %>

        <%
            boolean isPilotCheck = false;
            if (productProject != null && productProject.getProcess() != null) {
        		if (productProject.getProcess().getName().equals("Pilot")) {
        		    isPilotCheck = true;
        		}
            }

            if (auth.isLatest && (auth.isPM || CommonUtil.isAdmin() || true) && pf != null && isPilotCheck && auth.isCompleted) {
        %>
        <%-- <td width="5">&nbsp;</td>
        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:viewPerformance('<%=oid%>');"
                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01900") %>성과관리 보기</a></td>
                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
            </table></td> --%>
        <%}%>
        <td width="5">&nbsp;</td>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <%
                        if ((auth.isPM ||  auth.isPMO || isbizAdmin) && !auth.isCompleted && !isPurchase) {
                    %>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                        href="javascript:modify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03125")%><%--프로파일 수정--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    <%
                        }
                    %>
                </tr>
            </table>
         </td>
    </tr>
</table>