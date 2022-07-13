<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="ext.ket.shared.code.service.CodeHelper"%>
<%@page import="e3ps.project.cancel.ProjectCancelLink"%>
<%@page import="e3ps.project.cancel.CancelProject"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.elecSop.ProjectSopLink"%>
<%@page import="e3ps.project.elecSop.CustomerSopHistory"%>
<%@page import="ext.ket.project.program.entity.ProgramDTO"%>
<%@page import="ext.ket.part.entity.KETPartClassification"%>
<%@page import="ext.ket.project.program.service.ProgramHelper"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

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
                        e3ps.wfm.entity.KETWfmApprovalMaster,
                        e3ps.common.code.NumberCodeHelper
                        " %>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="e3ps.common.mail.MailUtil"%>
<%@page import="e3ps.common.obj.ObjectData"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.project.sap.ProductPrice"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

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
    boolean hasCheckedEvent = false;
    boolean hasCheckedSopEvent = false;

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
        //e.printStackTrace();
    }
    
    /* if(departmentName!=null && "전자개발팀".equals(departmentName)) {
        gateDrTabName = "DR";
    }else {
        gateDrTabName = "Gate/DR";
    } */
    
    gateDrTabName = "평가관리";
    //********************** Department 가져오기************************/




    WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
    PeopleData pd = new PeopleData(sessionUser);

    String oid = request.getParameter("oid");
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    
    String devProductSaleFirstYear = "";
    
    if (project.getDevRequest() != null) {
	   devProductSaleFirstYear = project.getDevRequest().getProductSaleFirstYear();
    }
    
    if(project.getPjtType() == 4){//전자사업부일때 고객 sop 관리 로직 추가 2017.02.17 by 황정태 
	    //Main Query 시작
        ProjectMaster pjtMaster = project.getMaster();
        QuerySpec mainSpec = new QuerySpec();
        
        int idxA = mainSpec.appendClassList(CustomerSopHistory.class, true);
        int idxB = mainSpec.appendClassList(ProjectSopLink.class, true);
        ClassAttribute attrA1 = new ClassAttribute(CustomerSopHistory.class,"thePersistInfo.theObjectIdentifier.id");
        ClassAttribute attrA2 = new ClassAttribute(ProjectSopLink.class,"roleAObjectRef.key.id");
        ClassAttribute rev = new ClassAttribute(CustomerSopHistory.class,"rev");
        
        mainSpec.appendWhere(new SearchCondition(attrA1,"=",attrA2), new int[]{idxA,idxB});
        mainSpec.appendAnd();
        mainSpec.appendWhere(new SearchCondition(ProjectSopLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(pjtMaster) ), new int[] { idxB });
        
        //Sub Query 시작 - 가장 마지막에 등록된 고객 sop를 가지고 온다
        
        QuerySpec subQuery = new QuerySpec();
        
        int subidxA = subQuery.appendClassList(CustomerSopHistory.class, false);
        int subidxB = subQuery.appendClassList(ProjectSopLink.class, false);
        ClassAttribute subattrA1 = new ClassAttribute(CustomerSopHistory.class,"thePersistInfo.theObjectIdentifier.id");
        ClassAttribute subattrA2 = new ClassAttribute(ProjectSopLink.class,"roleAObjectRef.key.id");
            
        subQuery.appendWhere(new SearchCondition(subattrA1,"=",subattrA2), new int[]{subidxA,subidxB});
        subQuery.appendAnd();
        subQuery.appendWhere(new SearchCondition(ProjectSopLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(pjtMaster) ), new int[] { idxB });
        
        SQLFunction toNumber = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(CustomerSopHistory.class, "rev"));
            
        SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, toNumber);
        subQuery.appendSelect(max, false);
        SearchCondition sc = new SearchCondition(rev, "=", new SubSelectExpression(subQuery));
        
        mainSpec.appendAnd();
        mainSpec.appendWhere(sc, new int[]{0});
        
        mainSpec.appendSelect(new ClassAttribute(CustomerSopHistory.class,"sopDate"),false);
        mainSpec.setAdvancedQueryEnabled(true);
        
        QueryResult sopQr = PersistenceHelper.manager.find(mainSpec);
        String elecSopDate = "";
        String createDate = null;
        while(sopQr.hasMoreElements()){
            
            Object[] obj = (Object[]) sopQr.nextElement();
            CustomerSopHistory history = (CustomerSopHistory)obj[0];
            ProjectSopLink link = (ProjectSopLink)obj[1];
            
            elecSopDate = DateUtil.getDateString(history.getSopDate(), "date");
            
            createDate = DateUtil.getDateString(link.getCreateTimestamp(), "date");
        }
        if(!"".equals(elecSopDate)){//고객 sop 가 등록되고 일주일간 프로젝트 기본정보에 알람 띄우기위함
            devProductSaleFirstYear = elecSopDate;

            createDate = DateUtil.addDate(createDate, 7);
            
            Date date1 = DateUtil.getTimestampFormat(createDate, "yyyy/MM/dd");
            Date date2 = DateUtil.getCurrentTimestamp();
            
            long oneDayMillis = 24*60*60*1000;
            int duration = (int)((date1.getTime() - date2.getTime())/oneDayMillis);

            if(duration > 0){
        	   hasCheckedSopEvent = true;
            }
        }
    }
    
    String develop_typed_check = StringUtil.checkNull(project.getDevelopedType());
    String[] develop_typed_temp = null;
    String develop_typed = "";
    
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;
    if(!"".equals(develop_typed_check)){
        develop_typed_temp = develop_typed_check.split("\\|");
        NumberCode nCode =null;
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "DEVELOPPATTERN");
        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    
        for(int i=0; i<develop_typed_temp.length; i++ ){
            for ( int j=0; j < numCode.size(); j++ ) {
                if(develop_typed_temp[i].equals(numCode.get(j).get("code"))){
                    if(i == develop_typed_temp.length-1){
                        develop_typed += numCode.get(j).get("value");
                    }else{
                        develop_typed += numCode.get(j).get("value")+",";
                    }
                }
            }
        }
    }
    
    ProductProject productProject = (ProductProject)project;
    E3PSProjectData projectData = new E3PSProjectData(project);

    String pjtStartDate = "";
    if(projectData.pjtExecStartDate != null){
        pjtStartDate = DateUtil.getDateString(projectData.pjtExecStartDate,"D");
    }else{
        pjtStartDate = DateUtil.getDateString(projectData.pjtPlanStartDate,"D");
    }
    boolean Isproductinfo = false;
    if(CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO")){
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
    //##################################### KETWfmApprovalMaster END

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

    //######################################
    //###################################### 비용
    String titleCost = "";
    boolean isCostCheck = false;
    
    //예산부족 타이틀 찍어주는 로직 제거 2021.05.07 경영혁신실 박상수 수석 요청
    /* long targetCost1 = 0;
    long targetCost2 = 0;
    long targetCost3 = 0;
    long targetCost4 = 0;
    long budget1 = 0;
    long budget2 = 0;
    long budget3 = 0;
    long budget4 = 0;
    long resultsCost1 = 0;
    long resultsCost2 = 0;
    long resultsCost3 = 0;
    long resultsCost4 = 0;
    long balanceCost1 = 0;
    long balanceCost2 = 0;
    long balanceCost3 = 0;
    long balanceCost4 = 0;

    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
    int count =0;
    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject)project);
    while(rtCost.hasMoreElements()){
         Object[] obj = (Object[])rtCost.nextElement();
        CostInfo costInfo = (CostInfo)obj[0];

        long budget = 0;       //예산
        long executionCost = 0;  //초기집행가
        long editCost = 0;      //추가집행가
        long totalExpense = 0;  //총집행가
        long balanceCost = 0;  //잔액


        if(costInfo.getOrderInvest() != null){
            Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
            Long totalPriceObj = (Long)datas.get(ProductPrice.TOTALPRICE);
            Long initExpenseObj = (Long)datas.get(ProductPrice.INITEXPENSE);
            Long addExpenseObj = (Long)datas.get(ProductPrice.ADDEXPENSE);
            Long totalExpenseObj = (Long)datas.get(ProductPrice.TOTALEXPENSE);

            if(totalPriceObj != null) budget = totalPriceObj.longValue();            //예산
            if(initExpenseObj != null) executionCost = initExpenseObj.longValue();    //초기집행가
            if(addExpenseObj != null) editCost = addExpenseObj.longValue();      //추가집행가
            if(totalExpenseObj != null) totalExpense = totalExpenseObj.longValue();    //총집행가
            balanceCost = budget - totalExpense;                      //잔액
        }else {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                budget = Long.parseLong(costInfo.getTargetCost().replaceAll(",", ""));        //예산

            if(costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
                executionCost = Long.parseLong(costInfo.getExecutionCost().replaceAll(",", ""));  //초기집행가
            else if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                executionCost = Long.parseLong(costInfo.getTargetCost().replaceAll(",", ""));    //초기집행가

            if(costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
                editCost = Long.parseLong(costInfo.getEditCost().replaceAll(",", ""));          //추가집행가

            totalExpense = executionCost + editCost;                          //총집행가
            balanceCost = budget - totalExpense;                            //잔액
        }

        if("금형".equals(costInfo.getCostType())) {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
            budget1 = budget1 + budget;
            resultsCost1 = resultsCost1 + executionCost + editCost;
            balanceCost1 = balanceCost1 + balanceCost;
        }else if("설비".equals(costInfo.getCostType())) {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
            budget2 = budget2 +budget;
            resultsCost2 = resultsCost2 + executionCost + editCost;
            balanceCost2 = balanceCost2 + balanceCost;
        }else if("JIG".equals(costInfo.getCostType())) {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost4 = targetCost4 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
            budget4 = budget4 +budget;
            resultsCost4 = resultsCost4 + executionCost + editCost;
            balanceCost4 = balanceCost4 + balanceCost;
        }else {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
            budget3 = budget3 +budget;
            resultsCost3 = resultsCost3 + executionCost + editCost;
            balanceCost3 = balanceCost3 + balanceCost;
        }
    } */
     //if(balanceCost1+balanceCost2+balanceCost3+balanceCost4 < 0) {
     //    isCostCheck = true;
     //    titleCost = "(" + messageService.getString("e3ps.message.ket_message", "02144")/*예산 부족*/ + ")";
     //}

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
    
  //프로젝트 취소 관련 소스 추가
    boolean ckeckPjt = false;  //false 일 경우에만 취소결재가 가능한 프로젝트임.

    try {
        //LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_ECA");
/*         if("CANCELING".equals(project.getLifeCycleState().toString())){
            ckeckPjt = true;
        } */
        /*LifeCycleTemplate lct = (LifeCycleTemplate)project.getLifeCycleTemplate().getObject();
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

        }*/
    } catch(Exception e) {
        //e.printStackTrace();
    }
    
    
    if("cancelStart".equals(command) && !ckeckPjt){
%>
<script language="JavaScript">
alert("결재를 진행해야 프로젝트가 취소 됩니다.");
var addr = "/plm/jsp/wfm/RequestApproval.jsp?pboOid=<%=oid%>"+"&popup=popup&mode=cancelStart";
var topener = window.open(addr,"approval","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
topener.focus();
</script>
<%
    }
    if ("stateChange".equals(command)) {
        String state = request.getParameter("state");
        boolean isChangeState = true;

/*         if("CANCELING".equals(state)){
            Kogger.debug("################### CANCELING");
            goApp = "isApp";
        } */

        // 연구개발 및 추가금형 성과관리를 하지 않는다.. 2010 - 05 - 24
        if("연구개발".equals(devName) || "추가금형".equals(devName)){
            isChangeState = true;
        }else if( project.getProcess().getName().equals("Proto") ){
            isChangeState = true;
        }else{}

        ProjectHelper.changeProjectState(project, state);
        //out.println("여기 실행 되며 오케");
%>
<script language="JavaScript">
        <%if("popup".equals(popup)){%>
            parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=<%=popup%>";
        <%}else{%>
          parent.document.location.href = '/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=<%=popup%>';
        <%
          }
        %>
</script>
<%
    }
    
    if ("stopStart".equals(command)) {
%>
<script language="JavaScript">
alert("결재를 진행해야 프로젝트가 중지 됩니다.");
var addr = "/plm/jsp/wfm/RequestApproval.jsp?pboOid=<%=oid%>"+"&popup=popup&mode=stopStart";
var topener = window.open(addr,"approval","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
topener.focus();
</script>
<%}


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
            //e.printStackTrace();
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
            document.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=CommonUtil.getOIDString(project)%>";

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
                project = (E3PSProject)LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)project, State.toState("PMOINWORK"), true);
                PersistenceHelper.manager.refresh(project);
                //ProjectHelper.manager.projectSendMailPM(project, wtuser, pmUser, false);

            }

            //PersistenceHelper.manager.refresh(project);
        }
        catch(Exception e) {
            //e.printStackTrace();
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
            document.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=CommonUtil.getOIDString(project)%>";

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
            //e.printStackTrace();
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
String salesOid = projectData.salesOid;

WTUser session_user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
String session_userOid = CommonUtil.getOIDString(session_user);

boolean isSalesRole = session_userOid.equals(salesOid);

if(CommonUtil.isAdmin()){
    isSalesRole = true;
}

String manufacPlaceTemp = StringUtil.checkNull(project.getManufacPlace());
String mfacPlace[] = manufacPlaceTemp.split("\\|");
String manufacPlace = "";

for(String fac : mfacPlace){
    manufacPlace += CodeHelper.manager.getCodeValue("PRODUCTIONDEPT", fac)+",";
}

manufacPlace = StringUtils.removeEnd(manufacPlace, ",");

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
<script type="text/javascript">
<!--

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
        height = 600;
        var screenWidth = (screen.availWidth-width)/2;
        var screenHeight = (screen.availHeight-height)/2;
        var url = "/plm/jsp/project/UpdateProductProject.jsp&key=oid&value=<%=oid%>";
        //newWin = window.open(url,"window", "scrollbars=yes,status=yes,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=yes,top="+screenHeight+",left="+screenWidth);
        newWin = openWindow('/plm/jsp/common/loading2.jsp?url='+url, '',800,550);
        //newWin.resizeTo(860,700);
        //newWin.focus();
        
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
                showProcessing();
                document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
                document.forms[0].method = "post";
                document.forms[0].command.value = "delete";
                //disabledAllBtn();
                //showProcessing();
                document.forms[0].submit();
            }
        }
    }
    
    function checkPartPlace(oid){
        var msg = '';

        $.ajax({
            type: 'post',
            url : '/plm/ext/project/product/checkPartPlace.do',
            async : false,
            cache:false,
            data : {
                oid : '<%=oid%>'
            },
            success : function(result){
                if(result.msg != ''){
                    msg = result.msg;
                }
            }
        });
        
        return msg;
        
    }


    // [START] [PLM System 1차개선] [일정변경] 버튼 클릭 시 confirm message 표시 후 일정변경(PLANCHANGE) 상태 변경, 2013-06-24, BoLee
    function historyChange(oid) {

    	var msg = checkPartPlace(oid);
    	if(msg != ''){
    		alert(msg);
    		return;
    	}
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
    
    function checkCostReport(oid){
        var msg = '';

        $.ajax({
            type: 'post',
            url : '/plm/ext/cost/checkProjectReport.do?projectOid='+oid,
            async : false,
            cache:false,
            success : function(result){
                if(result.msg != ''){
                    msg = result.msg;
                }
            }
        });
        
        return msg;
        
    }
    
    function stateChange(statevalue, message){
        var oid = '<%=oid%>';
        var msg = '';
        if(statevalue == 'COMPLETED'){
        	
            $.ajax({
                url : '/plm/ext/project/product/projectComleteCheck.do',
                async : false,
                cache:false,
                data : {
                    oid : oid
                },
                success : function(result){
                    if(result.msg != ''){
                        msg = result.msg;
                    }
                    
                }
            });
            
            if(msg != ''){
                alert(msg);
                return;
            }
        }
        
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

        if(type == "Mold" && !isMoldPM){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01045") %><%--금형개발(M) 담당자가 지정이 되어 있지 않습니다--%>');
            return;
        }

        if(type == "Press" && !isPressPM){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01047") %><%--금형개발(P) 담당자가 지정이 되어 있지 않습니다--%>');
            return;
        }
        /*
        var url="/plm/jsp/project/CreateMoldProject.jsp?oid=" + oid;
        openOtherName(url, "popup", 730, 680, "status=yes,scrollbars=auto,resizable=yes");
        */
        //var url="/plm/jsp/project/MoldProjectAction.jsp?mode=save&moid=" + oid + "&date=<%//=pjtStartDate%>";
        //openOtherName(url, "popup", 730, 680, "status=yes,scrollbars=auto,resizable=yes");
        if(confirm("<%=messageService.getString("e3ps.message.ket_message", "03297") %><%--금형프로젝트를 등록하시겠습니까?--%>")){
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
    
    function openSopView(pjtno){
    	
    	var url = "/plm/jsp/project/ElecSopHistory.jsp?pjtno="+pjtno;
    	  
        openOtherName(url,"ElecSopHistory","780","730","status=no,scrollbars=yes,resizable=no");
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
    if( ckeckPjt ){
    %>
    alert('<%=messageService.getString("e3ps.message.ket_message", "03188") %><%--해당 프로젝트는 취소를 진행할 수 없습니다\n관리자에게 문의하세요--%>');
    return;
    <%
    }
    %>
    var url = "/plm/jsp/project/CancelProject.jsp?oid=" + oid;
    openOtherName(url,"프로젝트취소","800","700","status=no,scrollbars=yes,resizable=no");
}

function viewCancelList(oid)
{
    var url = "/plm/jsp/project/CancelProjectList.jsp?oid=" + oid;
    openOtherName(url,"","620","500","status=no,scrollbars=yes,resizable=no");
}


function moveTabPage(oid)
{
    document.forms[0].action = "../../ext/project/gate/updateProjectAssessForm.do?oid="+oid;
    document.forms[0].submit();
}

window.setPjtStrList = function(){
    var param = new Object();
    param.pjtNo = '<%=projectData.ViewpjtNo%>';
    
    ajaxCallServer("/plm/ext/project/product/getProjectStructureList.do", param, function(data){
        
        var list = data.pjtStrList;
        
        if(list.length > 0){
            for(var i = 0; i < list.length; i++){
                addPjtStrList(list[i], param.pjtNo);
            }  
        }
    });
}


window.publishHtml = function(targetPjtNo, ortherPjtNo){
	
	var hrefHtml = "<td class='tdwhiteM'>"+ ortherPjtNo +"</a></td>";
	
	if(targetPjtNo != ortherPjtNo){
		hrefHtml = "<td class='tdwhiteM'><a href=# onclick=javascript:openProject('"+ortherPjtNo+"');>"+ ortherPjtNo +"</a></td>";
	}
	
	return hrefHtml; 
}


window.addPjtStrList = function(data, targetPjtNo){
	var row = "<tr>" + publishHtml(targetPjtNo, data.parentNo);
	row += "<td class='tdwhiteL'>"+data.parentName+"</td>";
	row += publishHtml(targetPjtNo, data.childNo);
	row += "<td class='tdwhiteL'>"+data.childName+"</td></tr>";
	
	$("#pjtStrList").append(row);
}

$(document).ready(function(){
	setPjtStrList();                        
});

//-->
</script>
</head>
<%@include file="/jsp/common/context.jsp" %>
<!-- [START] [PLM System 1차개선] 일정변경 완료 처리 function 호출을 위한 Include, 2013-08-05, BoLee -->
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<!-- [END] [PLM System 1차개선] 일정변경 완료 처리 function 호출을 위한 Include, 2013-08-05, BoLee -->
<style type="text/css">
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
</style>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/processing.html"%>
<body>
    <form>
        <input type='hidden' name='popup' value="<%=popup %>"> <input type='hidden' name='command' value="<%=command%>"> <input
            type='hidden' name='oid' value="<%=oid%>"> <input type="hidden" name="state" value=""></input>
        <!-- [START] [PLM System 1차개선] Project 일정 변경 상태 변경 (PLANCHANGE), 2013-06-24, BoLee -->
        <input type='hidden' name='cmd' value="">
        <!-- [END] [PLM System 1차개선] Project 일정 변경 상태 변경 (PLANCHANGE), 2013-06-24, BoLee -->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551")%><%--제품 프로젝트 상세정보--%></td>
                                        <td align="right">
                                        <%
                                        List<ProgramDTO> programList = ProgramHelper.service.findProgramByProject(oid);
                                        
                                        for(ProgramDTO program : programList){
                                            if("0".equals(program.getCheckedEvent())){
                                               hasCheckedEvent = true;
                                               break;
                                            }
                                        }
                                        if(hasCheckedEvent || hasCheckedSopEvent){
                                        %>
                                            <script>
                                            $(document).ready(function(){
                                                var x;
                                                setInterval(function() {
                                                    if(x == 0) {
                                                        $('#checkedEventDiv').hide();
                                                        x = 1;
                                                    } else  {
                                                        $('#checkedEventDiv').show();
                                                        x = 0;
                                                    }
                                                }, 500); // change the time if you want
                                                                                            
                                            });
                                            </script>
                                            <%if(hasCheckedEvent){%>
                                            <div id="checkedEventDiv"><font color="red">※<%=messageService.getString("e3ps.message.ket_message", "09256") %><!-- 관련 프로그램 이벤트 일정이 변경되었습니다. --></font></div>
                                            <%} %>
                                            
                                            <%if(hasCheckedSopEvent){%>
                                            <div id="checkedEventDiv"><font color="red">※고객 SOP 일정이 변경되었습니다.</font></div>
                                            <%} %>
                                            
                                        <%
                                        }
                                        %>
                                        </td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <!-- Tab start -->
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="5"></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isCS) {
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01640")%><%--비용--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
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
                                                    <!-- /plm/extcore/jsp/project/gate/updateProjectAssessForm.jsp?oid= -->
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01381")%><%--목표--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                    <!-- /plm/ext/project/gate/updateProjectAssessForm.do -->
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "09500")%><%--평가관리--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "03034")%><%--프로그램--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
<%--                                         <%if(CommonUtil.isAdmin() || CommonUtil.isMember("원가_임원")){%>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView8.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">원가</a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%} %> --%>
                                        <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td><img src="/plm/portal/images/tab_4.png"></td>
	                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
	                                                    href="ProjectExtView9.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">ATFT</a></td>
	                                                <td><img src="/plm/portal/images/tab_5.png""></td>
	                                            </tr>
	                                        </table>
	                                    </td>
                                        <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td><img src="/plm/portal/images/tab_4.png"></td>
	                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab">
	                                                <a href="javascript:openCostHistory('<%=oid %>')" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09518")%><%--개발원가--%></a></td>
	                                                <td><img src="/plm/portal/images/tab_5.png""></td>
	                                            </tr>
	                                        </table>
	                                    </td>
                                    </tr>
                                </table>
                            </td>
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
                    <!-- Tab end -->
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                            <td height="10" background="/plm/portal/images/box_14.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <div style="width:100%; height:100%; overflow-x:auto; overflow-y:auto;">
                                        <!--내용-->
                                        <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                            <tr>
                                                <td valign="top">
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                            <td class="font_03">
                                                                <%
                                                                    if (isCostCheck) {
                                                                %><span class="red"> <%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%><%=titleCost%></span>
                                                                <%
                                                                    } else {
                                                                %><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%>
                                                                <%
                                                                    }
                                                                %>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td align="right" colspan="2">
                                                                <table border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:SearchUtil.projectCard('<%=oid%>','<%=projectData.ViewpjtNo%>');"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00415")%><%--프로젝트카드--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                </tr>
                                                                            </table></td>
                                                                        <%
                                                                        WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
                                                                        String id = user.getName();//일시적으로 구본경 대리에게 권한부여
                                                                        
                                                                            if ("gubk".equals(id) || ( (auth.isLatest && !auth.isCompleted && (auth.isPM || auth.isPMO) && (auth.isPMOInWork || auth.isProgress || auth.isReWork || auth.isPlanChange || auth.isSTOPED)) || isAdmin) ) {
                                                                        %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:updateProject();"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정(프로젝트 수정)--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                </tr>
                                                                            </table></td>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%
                                                                            if (isCanApp && auth.isLatest && (auth.isPM || isAdmin) && (auth.isProgress || auth.isCanceling)) {
                                                                        %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:cancelProject('<%=oid%>');"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소(프로젝트취소)--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                </tr>
                                                                            </table></td>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%//if(auth.isLatest && (auth.isPM) && auth.isProgress){ 
                                                                            if (auth.isLatest && (auth.isPM || isAdmin) && (auth.isProgress||auth.isSTOPINWORK)){
                                                                        %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                            <tr>
                                                                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:stateChangeStop('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02695") %><%--중지--%></a></td>
                                                                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                              </tr>
                                                                            </table>
                                                                        </td>
                                                                      <%}%>
                                                                        <%
                                                                            if (!auth.isCheckOut && (auth.isPM && auth.isFirst && auth.isPMOInWork)) {
                                                                        %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:deleteProject('<%=oid%>');"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제(지우기)--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                </tr>
                                                                            </table></td>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%
                                                                            if (!auth.isCheckOut && auth.isLatest && (auth.isPM || auth.isPMO || isAdmin) && auth.isProgress) {
                                                                        %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:historyChange('<%=oid%>');"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02356")%><%--일정변경--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                </tr>
                                                                            </table></td>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%
                                                                            if (project.getLifeCycleState().toString().equals("PLANCHANGE") || project.getLifeCycleState().toString().equals("REWORK")) {
                                                                        %>
                                                                        <%
                                                                                 if ((auth.isWorkingCopy && auth.isLatest && (isAdmin || auth.isPM) && auth.isPlanChange) || CommonUtil.isAdmin()) {
                                                                        %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:deleteProject('<%=oid%>');"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02357")%><%--일정변경 취소--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                </tr>
                                                                            </table></td>
                                                                        <%
                                                                                }
                                                                            }
                                                                        %>
                                                                        <%if(auth.isLatest && (auth.isPM) && auth.isSTOPED){ %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                            <tr>
                                                                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:stateChangeReStart('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02446") %><%--재시작--%></a></td>
                                                                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                              </tr>
                                                                            </table></td>                                                
                                                                        <%}%>
                                                                        <%
                                                                        boolean modeComplectionCheck = true;
                                                                        QueryResult rt2 = ProductProjectHelper.manager.getMoldItemInfo((ProductProject)project);
                                                                            while(rt2.hasMoreElements()){
                                                                                 Object[] obj = (Object[])rt2.nextElement();
                                                                                MoldItemInfo miInfo = (MoldItemInfo)obj[0];
                                                                                MoldProject mProject = MoldProjectHelper.getMoldProject(miInfo);
                                                                               if(mProject != null){
                                                                                   E3PSProjectData data = new E3PSProjectData(mProject);
                                                                                   String state = data.state;
                                                                                   //out.println(state);
                                                                                   //out.println(mProject.getName());
                                                                                   if(!(state.equals("COMPLETED") || state.equals("WITHDRAWN"))){
                                                                                       modeComplectionCheck = false;
                                                                                   }
                                                                               }else{
                                                                                   //out.println(" null");
                                                                               }

                                                                            }
                                                                        if(auth.isLatest && (auth.isPM) && auth.isProgress && auth.is100Complection && modeComplectionCheck){  
                                                                        %>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                    <tr>
                                                                                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:stateChange('COMPLETED', '완료 ');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></a></td>
                                                                                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                    </tr>
                                                                                  </table></td>
                                                                        <%}%>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:projectHistory();"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01518")%><%--변경이력--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                                </tr>
                                                                            </table></td>
                                                                        <td width="5">&nbsp;</td>
                                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                        href="#" onClick="javascript:viewHistory('<%=oid%>');"
                                                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00785")%><%--결재이력--%></a></td>
                                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
                                                    <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                                        <tr>
                                                            <td width="150px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03104")%><%-- 프로젝트 No --%></td>
                                                            <td width="30%" class="tdwhiteL"><%=projectData.ViewpjtNo%></td>
                                                            <td width="150px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%></td>
                                                            <td width="30%" class="tdwhiteL0"><%=project.getLifeCycleState().getDisplay(messageService.getLocale())%><!--font color="red"><%=(project.isCheckOut() == true) ? messageService.getString("e3ps.message.ket_message", "00120")/*CheckOut됨*/: ""%></font-->
                                                                <%-- <%
                                                                    if (isAdmin) {
                                                                %> <select name="state0" class="fm_jmp" style="width: 90"
                                                                onchange="javascript:onStateChange(this, '<%=project.getPersistInfo().getObjectIdentifier().getStringValue()%>');">
                                                                    <%
                                                                        //String curState = project.getLifeCycleState().toString();
                                                                            try {
                                                                                //LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_ECA");
                                                                                LifeCycleTemplate lct = (LifeCycleTemplate) project.getLifeCycleTemplate().getObject();
                                                                                Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
                                                                                PhaseTemplate pt = null;
                                                                                wt.lifecycle.State lcState = null;
                                                                                for (int i = 0; i < states.size(); i++) {
                                                                                pt = (PhaseTemplate) states.get(i);
                                                                                lcState = pt.getPhaseState();
                                                                    %>
                                                                    <OPTION VALUE="<%=lcState.toString()%>"
                                                                        <%if (curState.equals(lcState.toString())) {%> selected <%}%>><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
                                                                    <%
                                                                        }
                                                                            } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                    %>
                                                                    </select> 
                                                                    <%
                                                                        }
                                                                    %>
                                                            </td> --%>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00659")%><%--개발의뢰번호--%></td>
                                                            <td class="tdwhiteL" colspan='3'> <%
                                                                     String devROid = "";
                                                                     String devRNumber = "";
                                                                     
                                                                     String devRepCarType ="";
                                                                     String devProductModifyDate = "";
                                                                     String reviewProjectNos[] = null;
                                                                     String reviewOid = "";
                                                                     E3PSProject ReviewProject = null;
                                                                     try {
                                                                        if (project.getDevRequest() != null) {
                                                                            devROid = CommonUtil.getOIDString(project.getDevRequest());
                                                                            devRNumber = project.getDevRequest().getNumber();
                                                                            
                                                                            devRepCarType = project.getDevRequest().getRepCarType();
                                                                           
                                                                            if (project.getReviewPjtNo() != null ) {
                                                                        	
                                                                        	    reviewProjectNos = project.getReviewPjtNo().split(",");
                                                                        	    for(String reviewNo : reviewProjectNos){
                                                                        		    ReviewProject = ProjectHelper.getProject(reviewNo);
                                                                        		    if (ReviewProject != null) {
                                                                        			    reviewOid = CommonUtil.getOIDString(ReviewProject);    
                                                                 %>
                                                                        		  <a href="#" onclick="javascript:viewProjectPop('<%=reviewOid%>');"><%=ReviewProject.getPjtNo()%></a>&nbsp;|&nbsp;
                                                                  
                                                                <%
                                                                        		    }
                                                                                }
        
                                                                            }
        
                                                                        }
                                                                        
                                                                        
                                                                        
                                                                    } catch (Exception e) {
                                                                        //e.printStackTrace();
                                                                    }
                                                                %> <a href="#" onclick="javascript:openView('<%=devROid%>');"><%=devRNumber%> </a>
                                                            </td>
                                                        </tr>
                                                        
                                                        <tr>
                                                            <td class="tdblueL">연계Proto-Pilot</td>
                                                            <td class="tdwhiteL"> 
                                                            <%
                                                                 String linkPjtNos = project.getMaster().getLinkProjectNo();
                                                                 String linkPjtno = "";
                                                                 if(!StringUtil.isEmpty(linkPjtNos)){
                                                                     for(String pjtno : linkPjtNos.split(",")){
                                                                	     linkPjtno = pjtno; 
                                                            %>
                                                            <a href="#" onclick="javascript:openProject('<%=linkPjtno%>');"><%=linkPjtno%> </a><span>&nbsp;&nbsp;&nbsp;</span>
                                                            <%
                                                                     }
                                                                 }
                                                                     
                                                                     
                                                            %> 
                                                            </td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%><%--개발단계--%></td>
                                                            <td class="tdwhiteL"><%=project.getProcess() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("PROCESS", project.getProcess().getCode(), messageService.getLocale().toString()))%></td>
                                                        </tr>
                                                        
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09483")%><%--개발목적--%></td>
                                                            <td class="tdwhiteL">
                                                            <%=project.getMainGoalType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPANDREVIEWGOAL", project.getMainGoalType().getCode(), messageService.getLocale().toString()))  + " / "%>
                                                            <%=project.getSubGoalType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPANDREVIEWGOAL", project.getSubGoalType().getCode(), messageService.getLocale().toString()))%>
                                                            </td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09482")%><%--관리 제품군--%></td>
                                                            <td class="tdwhiteL0">
                                                            <%=project.getManageProductType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("MANAGEPRODUCTTYPE", project.getManageProductType().getCode(), messageService.getLocale().toString()))%>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
                                                            <td class="tdwhiteL"><%=productProject.getTeamType()%></td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02139")%><%--영업담당자--%></td>
                                                            <td class="tdwhiteL0"><%=projectData.salesName%></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578")%><%--제품구분--%></td>
                                                            <%
                                                            if ("전자 사업부".equals(projectData.teamType)) {
                                                            
                                                            %>
                                                            <td class="tdwhiteL">
                                                            <%}else{ %>
                                                            <td class="tdwhiteL0" colspan="3">
                                                            <%
                                                            }
                                                            %>
                                                            <%
                                                            String className = "";;
                                                            if(StringUtil.checkString(project.getProductTypeLevel2()) && project.getProductTypeLevel2().indexOf("KETPartClassification") > 0){
                                                                KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(project.getProductTypeLevel2());
                                                                if(partClaz!=null){
                                                                   className = partClaz.getParent().getClassKrName() + "/" +partClaz.getClassKrName();
                                                                }
                                                            }
                                                            %>
                                                            <%=className%>
                                                            </td>
                                                            <%
                                                            if ("전자 사업부".equals(projectData.teamType)) {
                                                            %>
                                                            <td class="tdblueL">전자개발구분</td>
                                                            <td class="tdwhiteL0"><%="it".equals(productProject.getItDivision())?"전장IT개발":"전자개발"%></td>
                                                            <%
                                                            }
                                                            %>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01201")%><%--담당부서--%></td>
                                                            <td class="tdwhiteL"><%=projectData.department%></td>
                                                            <td class="tdblueL">PM</td>
                                                            <td class="tdwhiteL0"><%=projectData.pjtPmName%></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL">Part No</td>
                                                            <td class="tdwhiteL">
                                                                <div style="text-align:left;width: 90%; border: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                                    <%=projectData.partNo%>
                                                                </div>
                                                            </td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09488")%><%--수주관리--%></td>
                                                            <td class="tdwhiteL0"><%=project.getObtainType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("OBTAINORDERTYPE", project.getObtainType().getCode(), messageService.getLocale().toString()))%></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로젝트명 --%></td>
                                                            <td colspan=3 class="tdwhiteL0"><%=projectData.pjtName%> 
                                                            <%
                                                                 String fileUrl = "";
                                                                 String icon = "";
                                                            
                                                                 Vector contents = new Vector();
                                                                 contents = ContentUtil.getSecondaryContents(project, null);
                                                                 if (contents.size() > 0) {
                                                             %> <%
                                                                 for (int j = 0; j < contents.size(); j++) {
                                                                        ContentInfo info = (ContentInfo) contents.get(j);
                                                                        fileUrl = info.getDownURL().toString();
                                                                        icon = info.getIconURLStr();
                                                                        icon = icon.substring(icon.indexOf("<img"));
                                                             %> <a href="<%=fileUrl%>" title="<%=info.getName()%>"><%=icon%></a> <%
                                                                 }
                                                             %> <%
                                                                 }
                                                             %>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL">Rank</td>
                                                            <td class="tdwhiteL" <%--"자동차 사업부".equals(projectData.teamType) || "it".equals(productProject.getItDivision())?"":"colspan=3"--%>><%=project.getRank() == null ? "" : StringUtil.checkNull(project.getRank().getName())%></td>
                                                                <%--
                                                                
                                                                if ( "자동차 사업부".equals(projectData.teamType) || "it".equals(productProject.getItDivision()) ) {
                                                                --%>
                                                            <td class="tdblueL"><% if ( "전자 사업부".equals(projectData.teamType) || "it".equals(productProject.getItDivision())) {
                                                                                    out.print(messageService.getString("e3ps.message.ket_message", "02466")); //적용기기
                                                                                }else{ 
                                                                                    out.print(messageService.getString("e3ps.message.ket_message", "01248")); //대표차종
                                                                                } %>
                                                            </td>
                                                            <td class="tdwhiteL0"><% if ( "전자 사업부".equals(projectData.teamType) || "it".equals(productProject.getItDivision())) {
                                                                                    out.print(devRepCarType); //적용기기
                                                                                }else{ 
                                                                                    out.print(projectData.representModel); //대표차종
                                                                                } %>                                                               
                                                                <%--
                                                                }
                                                                --%>
                                                             </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%-- 최종사용처 --%></td>
                                                            <td class="tdwhiteL">
                                                                <%
                                                                    if (projectData.customereventVec != null && projectData.customereventVec.size() > 0) {
                                                                        
                                                                        for (int i = 0; i < projectData.customereventVec.size(); i++) {
                                                                            NumberCode nc = (NumberCode) projectData.customereventVec.get(i);
                                                                            String masterName = NumberCodeUtil.getNumberCodeValue("CUSTOMEREVENT", nc.getCode(), messageService.getLocale().toString());
                                                                            if(i!=0){
                                                                               out.print(",");   
                                                                            }
                                                                            out.print(masterName);
                                                                        }
                                                                    }
                                                                %>
                                                            </td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                                                            <td class="tdwhiteL0">
                                                                <%
                                                                    if (projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
                                                                        for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
                                                                            NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
                                                                            String masterName = NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode(), messageService.getLocale().toString());
                                                                            if(i!=0){
                                                                                out.print(",");   
                                                                            }
                                                                            out.print(masterName);
                                                                        }
                                                                    }
                                                                %>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07234")%><%--개발구분--%></td>
                                                            <td class="tdwhiteL"><%=project.getDevelopentType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", project.getDevelopentType().getCode(), messageService.getLocale().toString()))%></td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01843")%><%--설계구분--%></td>
                                                            <td class="tdwhiteL0"><%=project.getDesignType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DESIGNTYPE", project.getDesignType().getCode(), messageService.getLocale().toString()))%></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07165","")%><%--계획시작일--%></td>
                                                            <td class="tdwhiteL"><%=DateUtil.getDateString(projectData.pjtPlanStartDate, "D")%></td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07166","")%><%--계획완료일--%></td>
                                                            <td class="tdwhiteL0"><%=DateUtil.getDateString(projectData.pjtPlanEndDate, "D")%></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02064")%><%--실제시작일--%></td>
                                                            <td class="tdwhiteL"><%=DateUtil.getDateString(projectData.pjtExecStartDate, "D")%></td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02062")%><%--실제 완료일--%></td>
                                                            <td class="tdwhiteL0"><%=DateUtil.getDateString(projectData.pjtExecEndDate, "D")%></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07121")%><%--예상작업시간(hr)--%></td>
                                                            <td class="tdwhiteL"><%=projectData.planWorkTime %></td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07122")%><%--실제작업시간--%></td>
                                                            <td class="tdwhiteL0"><%=projectData.execWorkTime %></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653","")%><%--개발유형--%></td>
                                                            <td class="tdwhiteL"><%=develop_typed%></td>
                                                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791","")%><%--생산처--%></td>
                                                            <td class="tdwhiteL"><%=manufacPlace%></td>
                                                        </tr>
                                                        
                                                        <% if ( "전자 사업부".equals(projectData.teamType) || "it".equals(productProject.getItDivision()) ) {%>
                                                        <tr>
                                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00831") %><%--고객SOP--%></td>
                                                            
                                                            <td class="tdwhiteL0" colspan="3">
                                                            <% if (!"".equals(devProductSaleFirstYear)) {%>
                                                            <a href="#" onclick="javascript:openSopView('<%=projectData.ViewpjtNo%>');"><%=devProductSaleFirstYear%> </a>
                                                            <%}else if(isSalesRole){ %>
                                                                <table border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                            href="javascript:openSopView('<%=projectData.ViewpjtNo%>');" class="btn_blue">SOP등록</a></td>
                                                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                    </tr>
                                                                </table>
                                                            <%} %>
                                                            </td>
                                                        </tr>
                                                        <%} %>
                                                        
                                                        <tr>
                                                            <td class="tdblueL">모-자 프로젝트</td>
                                                            <td colspan="3" class="tdwhiteM0">
											                <table border="0" cellpadding="0" cellspacing="0">
											                  <tr>
											                    <td class="space5"></td>
											                  </tr>
											                </table>
											                <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
											                    <colgroup>
											                      <col width="10%">
											                      <col width="40%">
											                      <col width="10%">
											                      <col width="40%">
											                    </colgroup>
											                    <thead>
											                     <tr>
											                         <td class="tdgrayM" style="font-family: NanumBold !important;">모 PJT No</td>
											                         <td class="tdgrayM" style="font-family: NanumBold !important;">모 Project 명</td>
											                         <td class="tdgrayM" style="font-family: NanumBold !important;">자 PJT No</td>
											                         <td class="tdgrayM" style="font-family: NanumBold !important;">자 Project 명</td>
											                     </tr>
											                   </thead>
											                <tbody id="pjtStrList">
											                </tbody>
											                </table>
											                <table width="100%" border="0" cellpadding="0" cellspacing="0">
											                  <tr>
											                    <td class="space5"></td>
											                  </tr>
											                </table>
											                </td>
                                                        </tr>
                                                        
														<%-- <tr>
															<td class="tdblueL">
														        <%=messageService.getString("e3ps.message.ket_message", "09487","")%>파생차종
															</td>
															<td <% if ( !"전자 사업부".equals(projectData.teamType)) { %> colspan="3" class="tdwhiteL0" <%}else{ %> class="tdwhiteL" <%} %>>
															    <div id="OEMTYPELIST">
					                                            <%
					                                            List<OEMProjectType> oemTypes = projectData.oemTypes;
					                                            for(int i = 0; i < oemTypes.size(); i++){
					                                                OEMProjectType oemType = oemTypes.get(i);
					                                                String oemName = oemType.getName();
					                                                
					                                                if(i != (oemTypes.size() - 1)) oemName += ",";
					                                            %>
					                                                <div style='float:left;width:100px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;' title='<%=oemName%>'>
					                                                <%=oemName%></div>
					                                            <%}%>
					                                            </div>
															</td> 
															
														</tr> --%>
                                                    </table>
                                                </td>
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