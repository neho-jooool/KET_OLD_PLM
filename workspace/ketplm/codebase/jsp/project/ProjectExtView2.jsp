<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import="
                        wt.fc.*,
                        wt.folder.*,
                        wt.lifecycle.*,
                        wt.org.*,
                        wt.part.*,
                        wt.project.Role,
                        wt.query.*,
                        wt.session.*,
                        wt.team.*,
                        wt.vc.*,
                        wt.vc.wip.*,
                        wt.workflow.engine.WfActivity,
                        wt.workflow.engine.WfProcess,
                        wt.workflow.work.WorkItem"%>
<%@page import="
                        e3ps.common.content.*,
                        e3ps.common.jdf.config.Config,
                        e3ps.common.jdf.config.ConfigImpl,
                        e3ps.common.jdf.log.Logger,
                        e3ps.common.query.*,
                        e3ps.common.util.*,
                        e3ps.ews.dao.PartnerDao,
                        e3ps.groupware.company.*,
                        e3ps.project.*,
                        e3ps.project.beans.*,
                        e3ps.project.historyprocess.HistoryHelper,
                        e3ps.project.outputtype.OEMPlan,
                        e3ps.project.outputtype.OEMProjectType,
                        e3ps.sap.*,
                        e3ps.wfm.entity.KETWfmApprovalMaster
                        " %>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="e3ps.common.mail.MailUtil"%>
<%@page import="e3ps.common.obj.ObjectData"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.project.sap.ProductPrice"%>
<%@page import="ext.ket.part.base.service.*"%>
<%@page import="wt.part.*"%>
<%@page import="ext.ket.part.util.PartSpecEnum"%>
<%@page import="ext.ket.part.util.PartSpecGetter"%>
<%@page import="ext.ket.part.util.PartSpecSetter"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<!-- [START] [PLM System 1차개선] 일정변경 완료 처리 function 호출을 위한 Include, 2013-08-05, BoLee -->
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<!-- [END] [PLM System 1차개선] 일정변경 완료 처리 function 호출을 위한 Include, 2013-08-05, BoLee -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<%!
public String getPjtStateColor(int state){
        String color = "black";
        switch(state){
            case ProjectStateFlag.PROJECT_STATE_COMPLETE : {color = "green"; break;}
            case ProjectStateFlag.PROJECT_STATE_DELAY : {color = "red"; break;}
            case ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS : {color = "orange"; break;}
            case ProjectStateFlag.PROJECT_STATE_PROGRESS : {color = "blue"; break;}
        }

        return color;

    }
%>
<%
	//********************** Department 가져오기************************/
	e3ps.groupware.company.Department department = null;
	String departmentName = "";
	String gateDrTabName = "";
	//로그인 사용자 정보 (OID)
	try {
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    
	    QueryResult qr = PersistenceHelper.manager.navigate(wtuser, "people", e3ps.groupware.company.WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
	        e3ps.groupware.company.People people = (e3ps.groupware.company.People) qr.nextElement();
	        department = people.getDepartment();
	        departmentName = department.getName();
	    }   
	}catch(Exception e){
	    Kogger.error(e);
	}
	
	gateDrTabName = "평가관리";
	//********************** Department 가져오기************************/


    WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
    PeopleData pd = new PeopleData(sessionUser);

    String oid = request.getParameter("oid");
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);
    String pjtNo = project.getPjtNo();

    String pjtStartDate = "";
    if(projectData.pjtExecStartDate != null){
        pjtStartDate = DateUtil.getDateString(projectData.pjtExecStartDate,"D");
    }else{
        pjtStartDate = DateUtil.getDateString(projectData.pjtPlanStartDate,"D");
    }
    boolean Isproductinfo = false;
    if(CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("KETS_PMO")){
        Isproductinfo = true;
    }else{

        if(CommonUtil.isMember("자동차사업부_제품설계") ||CommonUtil.isMember("자동차사업부_영업") ){
            Isproductinfo = true;
        }

        if(CommonUtil.isMember("전자사업부_제품설계") ||CommonUtil.isMember("전자사업부_영업") ){
            Isproductinfo = true;
        }
    }
    String popup = StringUtil.checkNull( request.getParameter("popup") );

    String goApp = "";
    boolean isCanApp = true;
    String printApp = StringUtil.checkNull( request.getParameter("add") );
    Kogger.debug("############ app === " + printApp);
    // [START] [PLM System 1차개선] 일정변경구분 가져오기, 2013-08-05, BoLee
    int historyNoteType = project.getHistoryNoteType();
    // [END] [PLM System 1차개선] 일정변경구분 가져오기, 2013-08-05, BoLee

    //##################################### KETWfmApprovalMaster
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
        		isCanApp = false;
        	}
        }
      //##### 프로젝트 취소 결재요청버튼 확인 끝

    }
    //##################################### KETWfmApprovalMaster END
    //##################################### 성과 관리
    Performance pf = null;
    QueryResult qrtest = PerformanceHelper.manager.searchPerformance(project.getPjtNo());
    Object[] pobj = null;
    if(qrtest.hasMoreElements()){
        pobj = (Object[])qrtest.nextElement();
        pf = (Performance)pobj[0];
    }
    //##################################### 성과 관리

    //######################################금형 프로젝트 등록시 금형개발(mold/press)가 있는지 체크

    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isCS = CommonUtil.isMember("공정감사");
    boolean isPM = ((ProductProject)project).isIsPM();
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
    boolean canDelete = ProjectUserHelper.manager.isPM(project) || isAdmin;    //Delete(isPM || isAdmin)
    boolean isProjectPM = projectData.pjtPm != null;

    Hashtable userH = TrySchduleHelper.getProjectRoleMember(project);

    boolean isMoldPM = userH.get(MoldProjectHelper.MOLDDPM) != null;
    boolean isPressPM = userH.get(MoldProjectHelper.PRESSPM) != null;
    boolean isPURCHASEPM = false;
    
    if(project.getPjtType() == 4){
        isMoldPM = userH.get("Team_ELECTRON04") != null;
        isPressPM = userH.get("Team_ELECTRON05") != null;
    }else{
	    isPURCHASEPM  = userH.get("Team_PRODUCT20") != null;
    }
    //######################################

    //프로젝트 중지
    String command = request.getParameter("command");
    String devName = "";
    if(project.getDevelopentType() != null){
    	devName = project.getDevelopentType().getName();
    }
    
    if(command == null){
        command = "";
    }
    if ("stateChange".equals(command)) {
        String state = request.getParameter("state");
        boolean isChangeState = true;

        if("COMPLETED".equals(state)){
            if(pf == null){
                isChangeState = false;
            }else{
                if(!pf.getState().toString().equals("APPROVED")){
                    isChangeState = false;
                }
            }
        }

        if("CANCELING".equals(state)){
        	Kogger.debug("################### CANCELING");
        	goApp = "isApp";
        }

        // 연구개발 및 추가금형 성과관리를 하지 않는다.. 2010 - 05 - 24
        if("연구개발".equals(devName) || "추가금형".equals(devName)){
            isChangeState = true;
        }else if( project.getProcess().getName().equals("Proto") ){
            isChangeState = true;
        }else{}



        if(isChangeState){
            ProjectHelper.changeProjectState(project, state);
            //out.println("여기 실행 되며 오케");
        }else{
    %>
            <script language="JavaScript">
                alert("<%=messageService.getString("e3ps.message.ket_message", "01897") %><%--성과관리 업무가 완료 되지 않았습니다 업무가 완료되어야 프로젝트를 종료 할 수 있습니다--%>");
            </script>
    <%

        }

//    if("PROGRESS".equals(state))
//      ProjectHelper.manager.projectSendMail(project, "restart");
%>
        <script language="JavaScript">
            <%if("popup".equals(popup)){%>
                parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=<%=popup%>";
            <%}else{%>
            <%
            	if("isApp".equals(goApp)){
            %>
            	alert("결재를 진행해야 프로젝트가 취소 됩니다.");

            	var addr = "/plm/jsp/wfm/RequestApproval.jsp?pboOid=<%=oid%>";
            	  var topener = window.open(addr,"approval","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
            	  topener.focus();
            <%
            	}else{
            %>
                	parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>');
            <%
            	}
            }
            %>
        </script>
<%
    }



    WTUser wtuser = null;
    String wtUserOid = "";
    if(command.equals("dev")){
        String coMsg = "";
        Department dp = null;
        if(projectData.departmentOid.length() > 0 ){
            dp = (Department)CommonUtil.getObject(projectData.departmentOid);
        }
        try {
            if(dp != null){
                if("전장모듈개발1팀".equals( projectData.department )){
                    wtUserOid = "wt.org.WTUser:28088";
                    wtuser = (WTUser)CommonUtil.getObject(wtUserOid);
                }else{
                wtuser = (WTUser)PeopleHelper.manager.getChiefUser(dp);
                }
            }
            if(wtuser != null){
                WTPrincipal wtuserPrin = (WTPrincipal)wtuser;
                e3ps.project.workprocess.ExpressionUtil.addPrincialToRole(project, "ASSIGNEE", wtuserPrin);
                project = (E3PSProject)LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)project, State.toState("DEVASSIGN"), true);
                PersistenceHelper.manager.refresh(project);
                ProjectHelper.manager.projectSendMailAssign(project, "assign", wtuser, true);

            }
            //PersistenceHelper.manager.refresh(project);
        }
        catch(Exception e) {
            Kogger.error(e);
            coMsg = e.getMessage();
        }

        if(wtuser == null){
        %>
        <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "01202") %><%--담당부서 팀장을 지정 하십시오--%>');
            document.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>";
        </script>
    <%
        }else{
        //String copyOid = CommonUtil.getOIDString(workCopyOid.getWorkingCopy());
    %>
        <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "00670") %><%--개발팀 이관 되었습니다.--%>');
            parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=CommonUtil.getOIDString(project)%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=CommonUtil.getOIDString(project)%>&command=')

        </script>
    <%  }
    }

    if("pmDev".equals(command)){

        String coMsg = "";
        Department dp = null;
        if(projectData.departmentOid.length() > 0 ){
            dp = (Department)CommonUtil.getObject(projectData.departmentOid);
        }

        WTUser pmUser = null;

        try {
            wtuser = null;
            if(dp != null){
                wtuser = (WTUser)PeopleHelper.manager.getChiefUser(dp);
            }

            pmUser = projectData.pjtPm;

            if(pmUser != null){
                //WTPrincipal wtuserPrin = (WTPrincipal)wtuser;
                //e3ps.project.workprocess.ExpressionUtil.addPrincialToRole(project, "ASSIGNEE", wtuserPrin);
                project = (E3PSProject)LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)project, State.toState("PLANCHANGE"), true);
                PersistenceHelper.manager.refresh(project);
                //ProjectHelper.manager.projectSendMailPM(project, wtuser, pmUser, false);

            }

            //PersistenceHelper.manager.refresh(project);
        }
        catch(Exception e) {
            Kogger.error(e);
            coMsg = e.getMessage();
        }


        if(false && wtuser == null){
        %>
        <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "01202") %><%--담당부서 팀장을 지정 하십시오--%>');
            document.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>";
        </script>
    <%
        }else if(pmUser == null){
            %>
            <script>
                alert("<%=messageService.getString("e3ps.message.ket_message", "00383") %><%--PM을 지정 하십시오--%>");
                document.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>";
            </script>
        <%
        }else{
        //String copyOid = CommonUtil.getOIDString(workCopyOid.getWorkingCopy());
    %>
        <script>
            alert("<%=messageService.getString("e3ps.message.ket_message", "00377") %><%--PM 이관 되었습니다--%>");
            parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=CommonUtil.getOIDString(project)%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=CommonUtil.getOIDString(project)%>&command=')

        </script>
    <%  }
    }


    if("reassign".equals(command)) {
        String lifecycle = "";
        wt.lifecycle.LifeCycleManaged lcm = (wt.lifecycle.LifeCycleManaged)project;
        LifeCycleTemplate lct2 = (LifeCycleTemplate)lcm.getLifeCycleTemplate().getObject();
        lifecycle = lct2.getName();
        try {
            LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef());
            LifeCycleHelper.service.reassign((LifeCycleManaged)project, lct.getLifeCycleTemplateReference(),WCUtil.getWTContainerRef());
        }
        catch(Exception e) {
            Kogger.error(e);
        }
%>
    <script language="javascript">
        alert("<%=messageService.getString("e3ps.message.ket_message", "00441") %><%--Reassign 완료--%>");
        document.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>";
    </script>
<%
    }
%>

<%
//프로젝트 취소 관련 소스 추가
String curState = project.getLifeCycleState().toString();
boolean ckeckPjt = false;  //true일 경우에만 취소결재가 가능한 프로젝트임.

try {
    //LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_ECA");
    LifeCycleTemplate lct = (LifeCycleTemplate)project.getLifeCycleTemplate().getObject();
    Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
    PhaseTemplate pt = null;
    wt.lifecycle.State lcState = null;
    for(int i = 0; i < states.size(); i++) {
        pt = (PhaseTemplate)states.get(i);
        lcState = pt.getPhaseState();
        Kogger.debug("###    true==  "+lcState.toString());
        if("CANCELING".equals(lcState.toString())){
            ckeckPjt = true;
            Kogger.debug("###    true");
        }

    }
} catch(Exception e) {
    Kogger.error(e);
}

%>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
-->
</style>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/common/processing.html"%>
<script type="text/javascript">
<!--

	$(document).ready(function() {
	    $("#produtInfoTb").colResizable({resizeMode:'overflow'});
	    $("#produtInfoTb2").colResizable({resizeMode:'overflow'});
	
	});
    function onReassign(oid){
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03276") %><%--LC ReAssign 하시겠습니까?--%>")) {
            return;
        }

        disabledAllBtn();
        onProgressBar();
        document.forms[0].command.value = "reassign";
        document.forms[0].oid.value = oid;
        document.forms[0].submit();

    }


function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

    function updateCostInfo(){
        width = 890;
        height = 400;
        var screenWidth = (screen.availWidth-width)/2;
        var screenHeight = (screen.availHeight-height)/2;
        var url = "/plm/jsp/project/UpdateCostInfo.jsp?oid=<%=oid%>";
        newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=no,top="+screenHeight+",left="+screenWidth);
        newWin.resizeTo(890,400);
        newWin.focus();
    }

    function updateProject(){
        width = 860;
        height = 700;
        var screenWidth = (screen.availWidth-width)/2;
        var screenHeight = (screen.availHeight-height)/2;
        var url = "/plm/jsp/project/UpdateProductProject.jsp?oid=<%=oid%>";
        newWin = window.open(url,"window", "scrollbars=yes,status=yes,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=yes,top="+screenHeight+",left="+screenWidth);
        newWin.resizeTo(860,700);
        newWin.focus();
    }

    <%
            String deleteMsg = "";
            if(project.isCheckOut() == true) {
                deleteMsg = messageService.getString("e3ps.message.ket_message", "00789")/*경고!!\n일정변경을 취소하시겠습니까?*/;
            }
            else {
                deleteMsg = messageService.getString("e3ps.message.ket_message", "00791")/*경고!!\n프로젝트를 삭제하시겠습니까?*/;
            }
    %>
    function deleteProject(){
        if ( confirm('<%=deleteMsg%>') ) {
            if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03293") %><%--경고!!\n일정변경 중에 변경된 프로파일 정보는 복구할 수 없습니다.--%>') ) {
                document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
                document.forms[0].method = "post";
                document.forms[0].command.value = "delete";
                //disabledAllBtn();
                //showProcessing();
                document.forms[0].submit();
            }
        }
    }


    // [START] [PLM System 1차개선] [일정변경] 버튼 클릭 시 confirm message 표시 후 일정변경(PLANCHANGE) 상태 변경, 2013-06-24, BoLee
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
    // [END] [PLM System 1차개선] [일정변경] 버튼 클릭 시 confirm message 표시 후 일정변경(PLANCHANGE) 상태 변경, 2013-06-24, BoLee

    // [START] [PLM System 1차개선] 일정 변경 이력 팝업 사이즈 변경, 2013-07-07, BoLee
    function projectHistory(){
        var url="/plm/jsp/project/ProjectHistoryList.jsp?oid=<%=oid%>";
        openOtherName(url,"process","950","550","status=no,scrollbars=no,resizable=no");
    }
    // [END] [PLM System 1차개선] 일정 변경 이력 팝업 사이즈 변경, 2013-07-07, BoLee

    function viewHistory(pboOid){
        var url = '/plm/jsp/wfm/ApprovalHistory.jsp?pboOid='+pboOid;
        window.open(url,'결재이력',"status=1, menu=no, width=730, height=500");
    }

    <%if(pf == null){%>
    function viewPerformance(oid){
            var url = "/plm/jsp/project/performance/CreatePerformance.jsp?oid="+oid+"&pOid=<%=(pf!= null)?CommonUtil.getOIDString(pf):""%>";
            openSameName(url,"850","690","status=no,scrollbars=no,resizable=no");
    }
    <%}else{%>
    function viewPerformance(oid){
            var url = "/plm/jsp/project/performance/ViewPerformance.jsp?oid="+oid+"&pOid=<%=(pf!= null)?CommonUtil.getOIDString(pf):""%>";
            openSameName(url,"850","690","status=no,scrollbars=no,resizable=no");
    }
    <%}%>

    function devAction(oid){
        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "00671") %><%--개발팀 이관을 하시겠습니까?--%>')) {
            return;
        }

        //disabledAllBtn();
        //onProgressBar();
        document.forms[0].command.value = "dev";
        document.forms[0].oid.value = oid;
        document.forms[0].submit();
    }

    function pmAction(oid){
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03279") %><%--PM 이관을 하시겠습니까??--%>")) {
            return;
        }

        //disabledAllBtn();
        //onProgressBar();
        document.forms[0].command.value = "pmDev";
        document.forms[0].oid.value = oid;
        document.forms[0].submit();
    }

    function approvalAction(oid){
        var isProjectPM = <%=isProjectPM%>;
        if(!isProjectPM) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00587") %><%--개발 담당자(PM)가 지정되어 있지 않습니다--%>");
            return;
        }

        var url="/plm/jsp/wfm/RequestApproval.jsp?pboOid="+oid+"&oid=<%=wfmApprovalMasterOid%>" + "&popup=<%=popup%>";
        openOtherName(url,"approval","800","700","status=no,scrollbars=yes,resizable=no");
    }

    function stateChange(statevalue, message){
        if ( confirm("프로젝트를 " + message + "상태로 변경합니다.\n변경하시겠습니까?") ) {
                document.forms[0].action = "/plm/jsp/project/ProjectView.jsp";
                document.forms[0].command.value = "stateChange";
                document.forms[0].state.value = statevalue;
                document.forms[0].method = "post";

                document.forms[0].submit();
                return;
        }
    }

    function createMoldProject(oid, type){
        var isMoldPM = <%=isMoldPM%>;
        var isPressPM = <%=isPressPM%>;
        var isPURCHASEPM = <%=isPURCHASEPM%>;
        
        if(type == "Mold" && !isMoldPM){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01045") %><%--금형개발(M) 담당자가 지정이 되어 있지 않습니다--%>');
            return;
        }

        if(type == "Press" && !isPressPM){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01047") %><%--금형개발(P) 담당자가 지정이 되어 있지 않습니다--%>');
            return;
        }
        
        if(type == "구매품" && !isPURCHASEPM){
            alert('구매PM 담당자가 지정이 되어 있지 않습니다.');
            return;
        }
        /*
        var url="/plm/jsp/project/CreateMoldProject.jsp?oid=" + oid;
        openOtherName(url, "popup", 730, 680, "status=yes,scrollbars=auto,resizable=yes");
        */
        //var url="/plm/jsp/project/MoldProjectAction.jsp?mode=save&moid=" + oid + "&date=<%//=pjtStartDate%>";
        //openOtherName(url, "popup", 730, 680, "status=yes,scrollbars=auto,resizable=yes");
        var msg = "<%=messageService.getString("e3ps.message.ket_message", "03297") %><%--금형프로젝트를 등록하시겠습니까?--%>";
        
        if(type == "구매품"){
            msg = "구매프로젝트를 등록하시겠습니까?";        	
        }
        		
        if(confirm(msg)){
            location.href = "/plm/jsp/project/MoldProjectAction.jsp?mode=save&moid=" + oid + "&date=<%=pjtStartDate%>&poid=<%=oid%>";
        }
    }

    function goCustomerPlan(project_oid, customer_oid, view){
    	if(view == 'add'){
    		openWindow('/plm/jsp/project/schedule/AddCustomerSchedule.jsp?poid='+project_oid+'&coid='+customer_oid+'&popup=popup', '',300,500);
    	}else{
    		openWindow('/plm/jsp/project/schedule/ViewCustomerSchedule.jsp?poid='+project_oid+'&coid='+customer_oid+'&popup=popup', '',300,500);
    	}
    	//alert(project_oid+"====="+customer_oid);
    }

    function viewMoldProject(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
    }

    function stateChangeStop(oid){
        var url = "/plm/jsp/project/StopHistory.jsp?oid="+oid;
        openOtherName(url,"ChangeStop","780","730","status=no,scrollbars=yes,resizable=no");
    }

    function stateChangeCancel(oid){
        var url = "/plm/jsp/project/CancelHistory.jsp?oid="+oid;
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
    function fileCreate(oid){
        var url = "/plm/jsp/project/FileHistory.jsp?oid="+oid;
        openOtherName(url,"","620","300","status=no,scrollbars=yes,resizable=yes");
    }

    function openCostHistory(oid){
    	var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
        //openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
        
        getOpenWindow2(url, oid, 'full', 10);
    }

    //상태 변경 ...................................................................... begin
    var ajaxType;
    function onStateChange(s_obj, lcm_oid) {
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03314") %><%--상태를 변경하시겠습니까?--%>")) {
            return;
        }

        if(s_obj.value == '') {
            return;
        }

        var param = "?command=stateChange&oid=" + lcm_oid + "&state=" + s_obj.value;


        ajaxType = "stateChange";

        onProgressBar();

        var url = "/plm/jsp/project/WorkProcessAjaxAction.jsp" + param;
        callServer(url, onStateMessage);
    }



    function onStateMessage(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;

        var alertMsg = "";
        if(msg == 'true') {
            if(ajaxType == "stateChange") {
                alertMsg = "<%=messageService.getString("e3ps.message.ket_message", "01764") %><%--상태변경을 완료했습니다\n화면을 다시 로드하겠습니다--%>";
            }
        } else {
            if(ajaxType == "stateChange") {
                alertMsg = "<%=messageService.getString("e3ps.message.ket_message", "01763") %><%--상태변경을 완료하지 못했습니다\n다시 시도하시기 바랍니다\n화면을 다시 로드하겠습니다--%>";
            }
        }


        alert(alertMsg);

        document.forms[0].submit();
    }
    //상태 변경 ...................................................................... end

    function requestPop(oid){
        var url = '/plm/jsp/dms/ViewDevRequestPop.jsp?oid='+oid;
        window.open(url,'의뢰서',"status=1, menu=no, width=830, height=800");
    }

    function viewProjectPop(oid){
        var url="/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
        openWindow(url,"","1050","640","status=1,scrollbars=no,resizable=no");
    }

    function linkUrlOpen(linkUrl){
        if(linkUrl.length > 0) {
        window.open(linkUrl,'',"status=1, menu=no, width=1010, height=900");
        }
    }

function cancelProject(oid) {

    <%
    if( !ckeckPjt ){
    %>
    alert('<%=messageService.getString("e3ps.message.ket_message", "03188") %><%--해당 프로젝트는 취소를 진행할 수 없습니다\n관리자에게 문의하세요--%>');
    return;
    <%
    }
    %>
    var url = "/plm/jsp/project/CancelProject.jsp?oid=" + oid;
    openOtherName(url,"프로젝트취소","800","700","status=no,scrollbars=yes,resizable=no");
}

function viewCancelList(oid){
    var url = "/plm/jsp/project/CancelProjectList.jsp?oid=" + oid;
    openOtherName(url,"","620","500","status=no,scrollbars=yes,resizable=no");
}

function updateProductItemInfo(){
	var url = "/plm/jsp/project/UpdateProductItemInfo.jsp?oid=" + $('[name=oid]').val();
    //openOtherName(url,"","800","400","status=no,scrollbars=yes,resizable=no");
    openWindow(url, '',800,620);
}

function openBomCheckList(){
    
    openWindow("/plm/ext/project/bom/bomCheckList?pjtNo=" + $('[name=pjtNo]').val(), '',1350,600);
}

function openPurchaseDocList(){
	var pjtNo = $('[name=pjtNo]').val();
	getOpenWindow2("/plm/ext/project/purchase/purchaseDocList.do?pjtNo="+pjtNo, pjtNo , 1500, 800);
}

function openPurchasePartList(){
	
	var url = "/plm/ext/project/purchase/purchaseProjectList?pjtNo=" + $('[name=pjtNo]').val();
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

    var rest = "width=" + (screen.availWidth * 0.9) + ",height="
            + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
            + toppos;

    window.open(url, '', (opts + rest));
}

function openWareHousingList(){
    var pjtNo = $('[name=pjtNo]').val();
    getOpenWindow2("/plm/ext/project/product/wareHousingList?pjtNo="+pjtNo, "ware"+pjtNo , 1500, 800);
}
//-->
</script>
</head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<!-- [START] [PLM System 1차개선] 진행 상태 바 표시, 2013-07-13, BoLee -->
<%@include file="/jsp/common/processing.html" %>
<!-- [END] [PLM System 1차개선] 진행 상태 바 표시, 2013-07-13, BoLee -->
<body>
<form>
<input type='hidden' name='popup' value="<%=popup %>">
<input type='hidden' name='command' value="<%=command%>">
<input type='hidden' name='oid' value="<%=oid%>">
<input type='hidden' name='pjtNo' value="<%=pjtNo%>">
<input type="hidden" name="state" value=""></input>
<!-- [START] [PLM System 1차개선] Project 일정 변경 상태 변경 (PLANCHANGE), 2013-06-24, BoLee -->
<input type='hidden' name='cmd' value="">
<!-- [END] [PLM System 1차개선] Project 일정 변경 상태 변경 (PLANCHANGE), 2013-06-24, BoLee -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551") %><%--제품 프로젝트 상세정보--%></td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="5"></td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></a></td>
                                            <td><img src="/plm/portal/images/tab_5.png"></td>
                                        </tr>
                                    </table></td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_1.png"></td>
                                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></td>
                                            <td><img src="/plm/portal/images/tab_2.png""></td>
                                        </tr>
                                    </table></td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02327") %><%--인원--%></a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
                                <%if(!isCS){ %>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
                                <%} %>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="ProjectExtView10.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue--%></a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="ProjectExtView5.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">CFT요청</a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
                                    
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "09500") %><%--평가관리--%></a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "03034") %><%--프로그램--%></a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
<%--                                 <%if(CommonUtil.isAdmin() || CommonUtil.isMember("원가_임원")){%>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/plm/portal/images/tab_4.png"></td>
                                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                href="ProjectExtView8.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">원가</a></td>
                                            <td><img src="/plm/portal/images/tab_5.png""></td>
                                        </tr>
                                    </table></td>
                                <%} %> --%>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                    href="ProjectExtView9.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">ATFT</a></td>
                                                <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                        </table></td>
                                        <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td><img src="/plm/portal/images/tab_4.png"></td>
	                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab">
	                                                <a href="javascript:openCostHistory('<%=oid %>')" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09518") %><%--개발원가--%></a></td>
	                                                <td><img src="/plm/portal/images/tab_5.png""></td>
	                                            </tr>
	                                        </table>
	                                    </td>
                            </tr>
                        </table>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                    href="#" onClick="javascript:top.close();"
                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>    
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                    <td height="10" background="/plm/portal/images/box_14.gif"></td>
                    <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
                </tr>
                <tr>
                    <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                    <td valign="top">
                        <div style="position:; width:100%; height:580px; overflow-x:auto; overflow-y:auto;">
                            <!--내용-->
                            <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                <tr>
                                    <td valign="top">
                                     <%
                                        QuerySpec qs = new QuerySpec();
                                        
                                        int idxpi = qs.appendClassList(ProductInfo.class, true);
                                        
                                        SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id" , "=", CommonUtil.getOIDLongValue(oid));
                                        qs.appendWhere(cs, new int[] { idxpi } );
                                        
                                        QueryResult qrpi = PersistenceHelper.manager.find(qs);
                                        
                                        %> <%if(!isCS){ %>
    
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                            <tr>
                                                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></td>
                                                <td align="right">
                                                    
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onClick="javascript:openWareHousingList();"
                                                                class="btn_blue">입고처 조회</a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            
                                                            <td width="10">&nbsp;</td>
                                                            
                                                            <%
                                                            if (auth.isLatest && !auth.isCompleted && (auth.isPM || auth.isPMO || isAdmin || CommonUtil.isMember("사양관리"))) {
                                                            %>
                                                            
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onClick="javascript:updateProductItemInfo();"
                                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정(프로젝트 수정)--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            
		                                                    <%
		                                                    }
		                                                    %>        
                                                        </tr>
                                                    </table>
                                                    
                                                </td>
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
    
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;" id="produtInfoTb">
                                            <tr>
                                                <td width="30px" class="tdblueM">No</td>
                                                <td width="80px" class="tdblueM">Part No</td>
                                                <td class="tdblueM">Part Name</td>
                                                <td width="90px" class="tdblueM">
                                                    <%
                                                      if(projectData.isCarDivisionMode){%><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%>
                                                    <%}else{ %><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%>
                                                    <%} %>
                                                </td>
                                                <td width="130px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07293") %><%--조립처(생산처)--%></td>
                                                <td width="100px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02640") %><%--조립구분--%></td>
                                                <td width="60px" class="tdblueM">U/S</td>
                                            </tr>
                                            <%
                                            int no = 1;
                                            PartnerDao partnerDao = new PartnerDao();
                                            while(qrpi.hasMoreElements()){
                                                Object o[] = (Object[])qrpi.nextElement();
                                                ProductInfo pi = (ProductInfo)o[0];
                                    
                                                int  totalYield = 0;
                                                int y1 = Integer.parseInt(pi.getYear1()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear1());
                                                int y2 = Integer.parseInt(pi.getYear2()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear2());
                                                int y3 = Integer.parseInt(pi.getYear3()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear3());
                                                int y4 = Integer.parseInt(pi.getYear4()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear4());
                                                int y5 = Integer.parseInt(pi.getYear5()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear5());
                                                int y6 = Integer.parseInt(pi.getYear6()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear6());
                                                /* int y7 = Integer.parseInt(pi.getYear7()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear7());
                                                int y8 = Integer.parseInt(pi.getYear8()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear8());
                                                int y9 = Integer.parseInt(pi.getYear9()==null||"undefined".equals(pi.getYear1())?"0":pi.getYear9());
                                                int y10 = Integer.parseInt(pi.getYear10()==null?"0":pi.getYear10()); */
                                                totalYield += y1 + y2 + y3 + y4 + y5 + y6;// + y7 + y8 + y9 + y10;
                                    
                                                String carName = ProductHelper.getCarName(pi.getPersistInfo().getObjectIdentifier().toString());
                                                String carName2 = "";
                                                String assemblyPlaceName = "";
                                                String assemblyPlace = "";
                                                try{
                                            	   if(pi.getAssemblyPlaceType() != null){
                                                        if(pi.getAssemblyPlace() != null){
                                                            assemblyPlaceName = pi.getAssemblyPlace().getName();
                                                            assemblyPlace = CommonUtil.getOIDString(pi.getAssemblyPlace());
                                                        }
                                                        if(pi.getAssemblyPartnerNo() != null){
                                                            assemblyPlaceName = partnerDao.ViewPartnerName(pi.getAssemblyPartnerNo());
                                                            assemblyPlace = pi.getAssemblyPartnerNo();
                                                        }
                                                    }
                                                    carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
                                                }catch(Exception e){
                                                    carName2 = "";
                                                    Kogger.error(e);
                                                }
                                            Hashtable hash = ProductHelper.getCostIFLinkUrl(pi);
                                            String linkUrl = (String)hash.get("linkUrl");
                                    
                                            if(linkUrl == null) linkUrl = "";
                                            
                                            %>  
                                            <tr>
                                                <td class="tdwhiteM"><%=no++ %></td>
                                                <td class="tdwhiteM"><%=pi.getPNum()==null?"":"<a href=javascript:openViewPart('"+pi.getPNum()+"')>"+pi.getPNum()+"</a>" %></td>
                                                <td class="tdwhiteM" style="text-align:left"><%=pi.getPName()==null?"":pi.getPName() %></td>
                                                <td class="tdwhiteM"><%=carName==""?carName2:carName %></td>
                                                <td class="tdwhiteM" style="text-align:left"><%=pi.getAssemblyPlaceType()!=null?pi.getAssemblyPlaceType()+"/":""%><%=assemblyPlaceName%></td>
                                                <td class="tdwhiteM"><%=pi.getAssembledType()!=null?pi.getAssembledType().getName():""%></td>
                                                <td class="tdwhiteM"><%=pi.getUsage()==null?"":pi.getUsage() %></td>
                                            </tr>
    
                                            <%
                                            }
                                            %>
                                        </table> <%} %>
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td class="space15"></td>
                                            </tr>
                                        </table>
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00283") %><%--Item 현황--%></td>
                                                <td align="right">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onClick="javascript:openPurchaseDocList();"
                                                                class="btn_blue">산출물 조회</a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            <td width="10">&nbsp;</td>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onClick="javascript:openPurchasePartList();"
                                                                class="btn_blue">구매품 개발일정</a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            <td width="10">&nbsp;</td>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onClick="javascript:openBomCheckList();"
                                                                class="btn_blue">BOM정합성 검증</a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
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
                                            <tr>
                                                <td class="space5">
                                                    <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;" id="produtInfoTb2">
                                                        <tr>
                                                            <td width="20px" rowspan="2" class="tdblueM">No</td>
                                                            <td width="80px" rowspan="2" class="tdblueM">Part No</td>
                                                            <td rowspan="2" class="tdblueM">Part Name</td>
                                                            <td width="70px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "06112") %><%--부품분류--%></td>
                                                            <td width="50px" rowspan="2" class="tdblueM" style="word-wrap: break-word;"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>
                                                            <td width="80px" rowspan="2" class="tdblueM" style="word-wrap: break-word;"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                                                            <td width="60px" rowspan="2" class="tdblueM">Die No</td>
                                                            <td width="250px" <%if(CommonUtil.isMember("SQ인증감사")){ %> colspan="3" <%}else{%> colspan="4" <%}%>class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01090") %><%--금형속성--%></td>
                                                            <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%>
                                                                <br>(<%=messageService.getString("e3ps.message.ket_message", "00965") %><%--구매처--%>)
                                                            </td>
                                                            <td width="60px" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="45px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                                                            <td width="30px" class="tdblueM">C/V</td>
                                                            <td width="30px" class="tdblueM">C/T</td>
                                                            <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                                                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
                                                            <%} %>
                                                        </tr>
                                                        <%
                                                        ArrayList moldItemArr = new ArrayList();
                                                        /*
                                                        QuerySpec specMoldItem = new QuerySpec();
                                                        int idx = specMoldItem.addClassList(MoldItemInfo.class, true);
                                                        SearchCondition sc = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(project) );
                                                        specMoldItem.appendWhere(sc, new int[] { idx });
                                                        //SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, "dieNo", false);
                                                        SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, "thePersistInfo.createStamp", false);
                                            
                                            
                                                        //QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject)project);
                                                        QueryResult rt = PersistenceHelper.manager.find(specMoldItem);
                                                        */
                                                        QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject)project);
                                            
                                                        while(rt.hasMoreElements()){
                                                             Object[] obj = (Object[])rt.nextElement();
                                                            MoldItemInfo miInfo = (MoldItemInfo)obj[0];
                                                            moldItemArr.add(miInfo);
                                                        }
                                            
                                                        int plusNum = 1;
                                                        String crateDate = "";
                                                        for ( int i = 0 ; i < moldItemArr.size(); i++ ) {
                                                            MoldItemInfo miInfo = (MoldItemInfo)moldItemArr.get(i);
                                                            boolean isMoldProject = false;
                                                            MoldProject mProject = null;
                                                            if(miInfo.getDieNo() != null){
                                                        	    mProject = MoldProjectHelper.getMoldProject(miInfo.getDieNo());
                                                            }
                                                            String mProjectOid = "";
                                                            String state = "&nbsp;";
                                                            String color = "black";
                                            
                                                            if ( mProject != null ) {
                                                                isMoldProject = true;
                                                                mProjectOid = CommonUtil.getOIDString(mProject);
                                                                E3PSProjectData data = new E3PSProjectData(mProject);
                                                                state = data.state;
                                                                color = getPjtStateColor(data.getPjtState());
                                            
                                                            }
                                                            crateDate = "" +miInfo.getPersistInfo().getCreateStamp();
                                                        %>
                                                        <tr>
                                                            <td class="tdwhiteM"><%=i+1%><%//=crateDate%></td>
                                                            <td class="tdwhiteM"><%=miInfo.getPartNo() != null ? "<a href=javascript:openViewPart('"+miInfo.getPartNo()+"')>"+miInfo.getPartNo()+"</a>" : "&nbsp;"%></td>
                                                            <td class="tdwhiteM" title="<%=miInfo.getPartName() != null ? miInfo.getPartName() : "&nbsp;"%>">
                                                                <div style="text-align:left;width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                                    <nobr><%=miInfo.getPartName() != null ? miInfo.getPartName() : "&nbsp;"%></nobr>
                                                                </div>
                                                            </td>
                                                            <td class="tdwhiteM">
                                                                <%try{ %>
                                                                <%=miInfo.getProductType()!=null ? miInfo.getProductType().getName() : "&nbsp;"%>
                                                                <%}catch(Exception e){}%>
                                                            </td>
                                                            <td class="tdwhiteM"><%=miInfo.getItemType()!=null? miInfo.getItemType():"&nbsp;"%></td>
                                                            <td class="tdwhiteM"><%=miInfo.getShrinkage() != null ? miInfo.getShrinkage() : "&nbsp;" %></td>
                                                            <td class="tdwhiteM">
                                                                <%if(StringUtil.checkString(mProjectOid)){%>
                                                                <a href="javascript:;" onClick="javascript:viewMoldProject('<%=mProjectOid%>')">
                                                                <%} %><%=miInfo.getDieNo() != null ? !miInfo.getDieNo().equals("미입력") ? miInfo.getDieNo() : "-" : "&nbsp;"%>
                                                                    <%if(StringUtil.checkString(mProjectOid)){%>
                                                                </a>
                                                                <%} %>
                                                            </td>
                                                            <td class="tdwhiteM"><%=miInfo.getMoldType()!=null ? NumberCodeUtil.getNumberCodeValue("MOLDTYPE", miInfo.getMoldType().getCode(), messageService.getLocale().toString()) : "&nbsp;"%></td>
                                                            <td class="tdwhiteM"><%=miInfo.getCVPitch() != null ? miInfo.getCVPitch() : "&nbsp;"%></td>
                                                            <td class="tdwhiteM"><%=miInfo.getCTSPM() != null ? miInfo.getCTSPM() : "&nbsp;"%></td>
                                                            <%
                                                                String inoutName2 = "&nbsp;";
                                                                String purchaseStr = "";
                                                                PartnerDao partnerDao2 = new PartnerDao();
                                                                if ( miInfo.getMakingPlace() != null ) {
                                                                    purchaseStr = NumberCodeUtil.getNumberCodeValue("PRODUCTIONDEPT", miInfo.getMakingPlace().getCode(), messageService.getLocale().toString());
                                                                }
                                                
                                                                if ( "사내".equals(miInfo.getMaking())) {
                                                                    inoutName2 = messageService.getString("e3ps.message.ket_message", "01655")/*사내*/ + "   /" + purchaseStr;
                                                                }
                                                                else if ( miInfo.getMakingPlacePartnerNo() != null && miInfo.getMakingPlacePartnerNo().length() > 0 ) {
                                                                    String partnerName = partnerDao2.ViewPartnerName(miInfo.getMakingPlacePartnerNo());
                                                                    if ( partnerName == null || partnerName.length() == 0 ){
                                                                        partnerName = "&nbsp;";
                                                                    }
                                                                    inoutName2 = messageService.getString("e3ps.message.ket_message", "02184")/*외주*/ + "   /" + partnerName;
                                                                }
                                                            %>
                                                            <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                                                            <td class="tdwhiteM" title="<%=inoutName2%>"><div
                                                                style="width: 90%; text-align:left; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                                <nobr><%=inoutName2%></nobr>
                                                            </div></td>
                                                            <%} %>
                                                            <%
                                                                inoutName2 = "&nbsp;";
                                                                purchaseStr = "";
                                                                if ( miInfo.getPurchasePlace() != null ) {
                                                                    purchaseStr = NumberCodeUtil.getNumberCodeValue("PRODUCTIONDEPT", miInfo.getPurchasePlace().getCode(), messageService.getLocale().toString());
                                                                }
                                                
                                                                if ( "사내".equals(miInfo.getProductionPlace()) ) {
                                                                    inoutName2 = messageService.getString("e3ps.message.ket_message", "01655")/*사내*/ + "   /" + purchaseStr;
                                                                }
                                                                else if ( miInfo.getPartnerNo() != null && miInfo.getPartnerNo().length() > 0 ) {
                                                                    String partnerName = partnerDao2.ViewPartnerName(miInfo.getPartnerNo());
                                                                    if ( partnerName == null || partnerName.length() == 0 ){
                                                                        partnerName = "&nbsp;";
                                                                    }
                                                                    inoutName2 = messageService.getString("e3ps.message.ket_message", "02184")/*외주*/ + "   /" + partnerName;
                                                                }
                                                            %>
                                                            <td class="tdwhiteM" title="<%=inoutName2%>"><div
                                                                    style="width: 90%; text-align:left; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                                    <nobr><%=inoutName2%></nobr>
                                                                </div></td>
                                                            <!-- <td width="80" class="tdwhiteM"><%=miInfo.getMaterial() !=null ? miInfo.getMaterial().getGrade() : "&nbsp;"%></td> -->
                                                            <%
                                                                if ( "Press".equals(miInfo.getItemType()) || "Mold".equals(miInfo.getItemType()) ) {
                                                            %>
                                                            <td class="tdwhiteM0 txt-center" align="center">
                                                                <table border="0" cellspacing="0" cellpadding="0" align="center">
                                                                    <tr>
                                                                        <%if(isMoldProject){%>
                                                                        <td></td>
                                                                        <td class="btn_blue">
                                                                            <a href="javascript:;" onClick="javascript:viewMoldProject('<%=mProjectOid%>')" class="btn_blue">
                                                                            <font color="<%=color %>"><%=State.toState( state ).getDisplay(messageService.getLocale()) %></font></a>
                                                                        </td>
                                                                        <td></td>
                                                                        <%
                                                                        }else if(("신규".equals(miInfo.getShrinkage())) && !auth.isSTOPED && !("폐기".equals(project.getLifeCycleState().getDisplay(Locale.KOREA))) && (auth.isPM || auth.isPMO|| isMoldPM || isPressPM  || isAdmin) ){%>
                                                                        <%
                                                                            boolean isMoldProjectoverCheck = false;
                                                                            String SpPartType = "";
                                                                            WTPart lastPart = null;
                                                                            if(miInfo.getPartNo() != null && !"".equals(miInfo.getPartNo())){
                                                                        	   lastPart = PartBaseHelper.service.getLatestPart(miInfo.getPartNo());
                                                                        	   SpPartType = PartSpecGetter.getPartSpec(lastPart, PartSpecEnum.SpPartType);
                                                                            }
                                                                            
                                                                            if ( miInfo.getDieNo() != null ) {
                                                                                if(MoldProjectHelper.getMoldProject(miInfo.getDieNo()) == null){
                                                                                    isMoldProjectoverCheck = true;
                                                                                }
                                                                            }
                                                                            if (( ("H".equals(SpPartType) && isMoldProjectoverCheck && !miInfo.getDieNo().equals("미입력")) || ("구매품".equals(miInfo.getItemType()) && isMoldProjectoverCheck) ) && !auth.isCompleted && (auth.isPM || auth.isPMO || isAdmin)){
                                                                        %>
                                                                        <td><span class='b-small box-size' onClick="javascript:createMoldProject('<%=CommonUtil.getOIDString(miInfo)%>' , '<%=miInfo.getItemType()%>')"><%=messageService.getString("e3ps.message.ket_message", "01795") %><%--생성--%></span></td>
                                                                        <%
                                                                            }
                                                                            else if ( isMoldProjectoverCheck == false && !miInfo.getDieNo().equals("미입력") ){
                                                                        %>
                                                                        <td></td>
                                                                        <td title="<%=messageService.getString("e3ps.message.ket_message", "00148") %><%--Die사용중--%>"><%=messageService.getString("e3ps.message.ket_message", "00149") %><%--Die중복--%></td>
                                                                        <td></td>
                                                                        <%
                                                                            }
                                                                            else {
                                                                        %>
                                                                        <td></td>
                                                                        <td>&nbsp;</td>
                                                                        <td></td>
                                                                        <%
                                                                            }
                                                                        }%>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                            <%
                                                                }else {
                                                            %>
                                                            <td class="tdwhiteM0">&nbsp;</td>
                                                            <%}%>
                                                        </tr>
                                                        <%
                                                            }
                                                        %>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table></td>
                                </tr>
                            </table>
                         </div>
                    </td>
                    <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
                    <td height="10" background="/plm/portal/images/box_16.gif"></td>
                    <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
                </tr>
            </table></td>
    </tr>
</table>
</form>
</body>
</html>
