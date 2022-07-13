<%@page import="e3ps.project.beans.ProductProjectHelper"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="ext.ket.invest.entity.KETInvestMaster"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.ProjectTaskScheduleHelper"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.dms.service.KETDmsHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.fc.ReferenceFactory" %>
<%@page import = "wt.org.WTUser" %>
<%@page import = "wt.session.SessionHelper" %>
<%@page import = "e3ps.wfm.entity.KETWfmApprovalMaster" %>
<%@page import = "e3ps.wfm.entity.KETWfmApprovalLine" %>
<%@page import = "e3ps.ecm.entity.KETProdChangeRequest" %>
<%@page import = "e3ps.ecm.entity.KETProdChangeOrder" %>
<%@page import = "e3ps.ecm.entity.KETMoldChangeRequest" %>
<%@page import = "e3ps.ecm.entity.KETMoldChangeOrder" %>
<%@page import = "ext.ket.dqm.entity.KETDqmAction" %>
<%@page import = "ext.ket.arm.entity.KETAnalysisRequestMaster" %>
<%@page import = "ext.ket.arm.entity.KETAnalysisRequestInfo" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "e3ps.dms.entity.*" %>
<%@page import = "e3ps.ecm.entity.*" %>
<%@page import = "e3ps.ecm.beans.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.org.*" %>
<%@page import = "wt.query.QuerySpec" %>
<%@page import = "wt.query.SearchCondition" %>
<%@page import = "e3ps.groupware.company.*" %>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@page import = "org.apache.commons.lang.*" %>
<%@page import = "java.util.*" %>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  String[] pboOids = request.getParameterValues("pboOids");
  String pboOid = StringUtil.checkNull(request.getParameter("pboOid"));
  if(pboOids != null && pboOids.length == 1) pboOid = pboOids[0];
  
  ReferenceFactory ref = new ReferenceFactory();
  Persistable obj = null;
  KETWfmApprovalMaster master = null;
  String oid = StringUtil.checkNull(request.getParameter("oid"));

  String popup = StringUtil.checkNull( request.getParameter("popup") );
    // [START] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
    String mode = StringUtil.checkNull( request.getParameter("mode") );
    // [END] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee

//  KETProjectDocument pDoc = (KETProjectDocument)CommonUtil.getObject(pboOid);
//  String categoryName = pDoc.getFolderingInfo().getParentFolder().getName());
  boolean protoGate1Check = StringUtil.checkNull( request.getParameter("protoGate1Check") ).equals("OK");
  String protoGate1DiscussGroup = "";
  
  if(protoGate1Check){
      
      Department dept1 = DepartmentHelper.manager.getDepartment("10043");//품질본부
      Department dept2 = DepartmentHelper.manager.getDepartment("11695");//생산기술본부
      
      if(dept1 != null && dept2 != null){
	      WTUser chiefUser1 = PeopleHelper.manager.getChiefUser(dept1);
	      WTUser chiefUser2 = PeopleHelper.manager.getChiefUser(dept2);
	      
	      if(chiefUser1 != null){
		     protoGate1DiscussGroup = chiefUser1.getName();
	      }
	      if(chiefUser2 != null){
	         protoGate1DiscussGroup += ","+chiefUser2.getName();
	      }
      }
      
  }

  if(!pboOid.equals("")){
    obj = ref.getReference(pboOid).getObject();
    master = WorkflowSearchHelper.manager.getMaster(obj);
    if(master!=null) oid = CommonUtil.getOIDString(master);
  }
  WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();


  boolean ecrCo = false;
  if(obj instanceof KETProdChangeRequest
   || obj instanceof KETProdChangeOrder
   || obj instanceof KETMoldChangeRequest
   || obj instanceof KETMoldChangeOrder ){
    ecrCo = true;
  }
  
  boolean isDRR = false;
  if ( obj instanceof KETDevelopmentRequest || obj instanceof KETAnalysisRequestMaster || obj instanceof KETAnalysisRequestInfo){
    isDRR = true;
  }

  HashMap bReview = new HashMap();
  HashMap discuss = new HashMap();
  HashMap aReview = new HashMap();
  HashMap receiver = new HashMap();
  HashMap reference = new HashMap();
  int index1 = 0;
  int index2 = 0;
  String comment = "&nbsp;";
  String list1 = "";
  String list2 = "";
  String list3 = "";
  String list4 = "";
  String list5 = "";
  // Modified by MJOH, 2011-03-24
  // String agreementType = "sequential";
  String agreementType = "noDiscuss";
  String isApprYn = "Y";

  // [START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
  Set<String> discussIds = new HashSet<String>(); // 합의자 id 중복체크용
  Set<String> aReviewIds = new HashSet<String>(); // 승인자 id 중복체크용
  boolean isApprovalForProjectSchedule = false;
  boolean isApprovalForElecEco = false;
  boolean isCompleteCheckDoc = false;
  boolean isCancelProject = false;
  boolean isStopProject = false;
  boolean isInvest = false;
  Set<String> preAddedByRole = new LinkedHashSet<String>(); // 선 지정된 병렬 합의자(historyRole) id list
  String preAddedChief = null; // 선 지정된 승인자(팀장) id list
  String preAddedChief2 = ""; // 선 지정된 팀장의 상위 결재자 id list
  String preAddedChief2Name = "";
  String preAddedChiefName = "";
  boolean preAdded = false;
  String isProjectApproval = "";
  
  //전자사업부 자동병렬합의 지정 체크 Start [2017.09.01 황정태]
  String checkDeptCode = "10080";
  
  WTUser current_user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
  PeopleData current_pdata = new PeopleData(current_user);
  Department parentDept = (Department)current_pdata.department.getParent();
  
  if(obj instanceof KETProjectDocument){
      KETProjectDocument doc = (KETProjectDocument) obj;
      
      QueryResult r = PersistenceHelper.manager.navigate(doc, "documentCategory", KETDocumentCategoryLink.class);

      KETDocumentCategory docCate = null;

      if (r.hasMoreElements()) {
          docCate = (KETDocumentCategory) r.nextElement();
      }
      
      if("PD303148".equals(docCate.getCategoryCode())){
	      isCompleteCheckDoc = true;
	      WTUser chiefUser = PeopleHelper.manager.getPeople("ketcolwj").getUser();
	      
	      if (chiefUser != null) {
		    preAddedChief = chiefUser.getName();
		    PeopleData pdata = new PeopleData(chiefUser);
		    preAddedChiefName = chiefUser.getFullName();
	      }
      }
  }
  
  if(obj instanceof KETInvestMaster){//고객투자비 회수관리의 경우 parent 부서의 parent 부서장 체크
      isInvest = true;
      
      if(parentDept != null){
	  
	      Department UpParentDept = (Department)parentDept.getParent();
	      
	      if("10059".equals(parentDept.getCode())){//글로벌사업부의 경우 parent는 영업본부로 고정
		    UpParentDept = DepartmentHelper.manager.getDepartment("10060");
	      }
	      
	      if(UpParentDept != null){
		      WTUser chiefUser = (WTUser)PeopleHelper.manager.getChiefUser(UpParentDept);
	          if(chiefUser != null){
	              preAddedChief = chiefUser.getName();
	              preAddedChiefName = chiefUser.getFullName();
	          }
	      }
	      
      }
  }
  
  if (obj instanceof E3PSProject) {
    E3PSProject project = (E3PSProject) obj;
    
    if("CANCELING".equals(project.getLifeCycleState().toString())){
	    
        isCancelProject = true;
        
        if(project instanceof MoldProject){//금형의 경우 투자비가 있을 때만 결재라인을 체크한다 - 최종 : 사장
            QueryResult rtCost = ProductProjectHelper.manager.getCostInfo(project.getPjtNo());
            if(rtCost == null || rtCost.size() < 1){
        	   isCancelProject = false;
            }
        }
            
    }
    if("stopStart".equals(mode)){
	    isStopProject = true; 
    }
    
    
    
    //if(!"CANCELING".equals(project.getLifeCycleState().toString()) && !"cancelStart".equals(mode)){
	
	    if (((project.getPjtHistory() == 0 && project.getPjtIteration() == 0) && !project.isWorkingCopy()) && !isStopProject) {  // Project 등록 시에는 일정 변경 범위 체크하지 않음, 2013-08-05, BoLee
	        isApprovalForProjectSchedule = true;
	        
	    }
	    else {
		
		    if(!isStopProject && !isCancelProject){
			    isProjectApproval = ProjectTaskScheduleHelper.isProjectScheduleForApproval(project);
		        isApprovalForProjectSchedule = (isProjectApproval.startsWith("T")); // 일정 변경 범위가 결재 대상인지 여부
		    }
		    
	    }
	    if (isApprovalForProjectSchedule || isCancelProject || isStopProject) {
	      String historyRole = project.getHistoryRole();
	      if (StringUtil.checkString(historyRole)) {
	          QueryResult qr = ProjectUserHelper.manager.getProjectRoleMember(project, historyRole);
	          while(qr.hasMoreElements()) {
	            ProjectMemberLink link = (ProjectMemberLink)qr.nextElement();
	            WTUser user = link.getMember();
	            preAddedByRole.add(user.getName());
	          }
	      }
	
	      WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	      PeopleData pdata = new PeopleData(user);
	      
	      WTUser chiefUser = null;
	      
	      if(parentDept!= null && checkDeptCode.equals(parentDept.getCode())){//전자사업부
		      if(isCancelProject || isStopProject){
			      chiefUser = PeopleHelper.manager.getPeople("jhjun").getUser();
			  }
	      }else{
		      if(isCancelProject){
	              chiefUser = PeopleHelper.manager.getPeople("ketcolwj").getUser();
	          }else if(isStopProject){
	              chiefUser = PeopleHelper.manager.getPeople("ketcolwj").getUser();
	          }else{
	              chiefUser = (WTUser)PeopleHelper.manager.getChiefUser(pdata.department);
	          }
	      }
	      
	      
	      if (chiefUser != null) {
	        preAddedChief = chiefUser.getName();
	        pdata = new PeopleData(chiefUser);
	        preAddedChiefName = chiefUser.getFullName();
	        if(pdata.isDiable){//퇴사자 제외
	            chiefUser = null;
	            preAddedChief = null;
	        }
	      }
	      else {
	        Kogger.debug("RequestApproval.jsp: chiefUser is null! (user=" + user.getName() + ")");
	      }
	      Kogger.debug("isProjectApproval : "+isApprovalForProjectSchedule);
	      if(obj instanceof ProductProject && "T_assess".equals(isProjectApproval)){//gate, dr 타스크의 일정초과시 팀장의 상위 부서장 자동 지정 2017.09.25 적용 연구기획센터장(유승우) 지시
		  
		    
		    pdata = new PeopleData(user);
	        Department myDept = (Department)pdata.department;
	        Department p_dept = null;
	        
	        if(myDept.getCode().equals("11743")){//W/H사업팀의 경우 본부장이 부사장이기 때문에 상위부서장을 팀장으로 지정한다 2021.12.23 부사장 지시
	            p_dept = myDept;
	        }else{
	            p_dept = (Department)myDept.getParent();    
	        }
	        
		    chiefUser = (WTUser)PeopleHelper.manager.getChiefUser(p_dept);
		    
		    if (chiefUser != null) {
			    preAddedChief2 = chiefUser.getName();
			    preAddedChief2Name = chiefUser.getFullName();
		    }else if(p_dept != null){//1레벨 상위부서의 부서장이 없으면 그 위의 부서장을 찾는다
		        
			    p_dept = (Department)p_dept.getParent();
			    chiefUser = (WTUser)PeopleHelper.manager.getChiefUser(p_dept);
			    
			    if (chiefUser != null) {
				    preAddedChief2 = chiefUser.getName();
				    preAddedChief2Name = chiefUser.getFullName();
			    }
		    }
		    
	      }
	    }
    
    //}
  }
  
  
  
  if (parentDept != null && (obj instanceof KETProdChangeOrder || obj instanceof KETMeetingMinutes)) {
      
      if(checkDeptCode.equals(parentDept.getCode())){
	  
	      if(obj instanceof KETProdChangeOrder){
		      KETProdChangeOrder prodEco = (KETProdChangeOrder)obj;
		      
	          String changeReason = StringUtils.stripToEmpty(prodEco.getChangeReason());
	          boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1 || changeReason.lastIndexOf("REASON_13") > -1);
	          boolean isMass = (changeReason.lastIndexOf("REASON_10") > -1);
	          
	          if(!isTheFirst && !isMass){//설변사유가 양산이관아니고 초도배포 아닐때
	            isApprovalForElecEco = true;
	          }
	      }
	      if(obj instanceof KETMeetingMinutes){
		     isApprovalForElecEco = true;
	      }
	      
      }
  }
  
  if (isApprovalForElecEco) {//전자사업부 ECO, ECR 회의록 결재요청시 전자사업부 하위 조직 자동병렬 합의 지정(결재요청자 소속팀 제외) 2017.09.01
      Department elecHeadOffice = DepartmentHelper.manager.getDepartment(checkDeptCode);// 전자사업부
      ArrayList<Department> childDept = DepartmentHelper.manager.getChildList(elecHeadOffice);

      for (Department child : childDept) {
          if(current_pdata.department.getCode().equals(child.getCode()) ){
            continue;
          }
          
          WTUser chiefUser = (WTUser)PeopleHelper.manager.getChiefUser(child);
          
          if(chiefUser != null){
            PeopleData pod = new PeopleData(chiefUser);
            if(pod.isDiable){
        	   continue;
            }
            preAddedByRole.add(chiefUser.getName());
          }
      }    
 
  }
  //전자사업부 자동병렬합의 지정 체크 End
  
  // [END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
  
  // [START] [1차고도화 이후 유지보수] 기술문서 분류체계가 설계가이드 일때 순차합의자 자동지정, 2015-03-26, 황정태
  // 설계가이드 순차합의 제거 2019.07.09 황정태 (다시 지정하려면 아래 주석 풀면됨)
  boolean isTechDocDesign = false;
  Hashtable<String, String> DesignPeopleTab = null;
  ArrayList<Hashtable> list = null;
/*   if (obj instanceof KETTechnicalDocument) {
	  KETTechnicalDocument techDoc = (KETTechnicalDocument) obj;
	  if("Y".equals(techDoc.getAttribute1()) && !CommonUtil.isMember("설계가이드관리") ){
		  isTechDocDesign = true;
		  list = CommonUtil.findUser("설계가이드관리","userId");
		  for(int i=0; i<list.size(); i++){
			  DesignPeopleTab = (Hashtable) list.get(i);
			  preAddedByRole.add((String)DesignPeopleTab.get("userId"));
		  }
	  }
  } */
  
 //[END] [1차고도화 이후 유지보수] 기술문서 분류체계가 설계가이드 일때 순차합의자 자동지정, 2015-03-26, 황정태

  if(master!=null){
    agreementType = master.getAgreementType();
    Vector vec = WorkflowSearchHelper.manager.getApprovalLine(master);
    for(int i=0; i<vec.size(); i++){
      KETWfmApprovalLine line = (KETWfmApprovalLine)vec.get(i);
      People user = PeopleHelper.manager.getPeople(line.getApproverID());
      if(line.getApprovalType().equals("beforeReview")) bReview.put(line.getApprovalOrder(),user);
      else if(line.getApprovalType().equals("discuss")) {
        discuss.put(line.getApprovalOrder(),user);
        discussIds.add(line.getApproverID());
      }
      else if(line.getApprovalType().equals("afterReview")) {
        aReview.put(line.getApprovalOrder(),user);
        aReviewIds.add(line.getApproverID());
      }
      else if(line.getApprovalType().equals("receiver")) receiver.put(line.getApprovalOrder(),user);
      else reference.put(line.getApprovalOrder(),user);
    }
    comment = (master.getComments() != null && master.getComments().trim().length() > 0) ? master.getComments().trim() : "";
  }

  // [START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
  if (isApprovalForProjectSchedule || isTechDocDesign || isApprovalForElecEco || isCancelProject || isStopProject || isCompleteCheckDoc) {
    // 승인자(팀장) 선 지정
    if (StringUtil.checkString(preAddedChief)) {
      // 중복체크
      if (aReviewIds.contains(preAddedChief) == false &&
          preAddedChief.equals(sessionUser.getName()) == false) { // 본인을 승인자로 지정 불가
        People chiefPeople = PeopleHelper.manager.getPeople(preAddedChief);
        aReview.put(aReview.size(), chiefPeople);
        aReviewIds.add(preAddedChief);
        if (preAdded == false) preAdded = true;
      }
    }
    
/*     if (StringUtil.checkString(preAddedChief2)) {
	      // 중복체크
	      if (aReviewIds.contains(preAddedChief2) == false &&
		      preAddedChief2.equals(sessionUser.getName()) == false) { // 본인을 승인자로 지정 불가
	        People chiefPeople = PeopleHelper.manager.getPeople(preAddedChief2);
	        aReview.put(aReview.size(), chiefPeople);
	        aReviewIds.add(preAddedChief2);
	        if (preAdded == false) preAdded = true;
	      }
	    } */

    // 병렬 합의자(historyRole) 선 지정
    if (preAddedByRole.size() > 0) {
	
      if(isTechDocDesign){//설계가이드 문서일때 순차합의지정
    	  agreementType = "sequential";
      }else{
    	// 기존 합의자는 삭제
    	if(!isApprovalForElecEco){
    	   discuss.clear();
    	}
    	
    	agreementType = "parallel";
      }
    }
    int idx = 0;
    for (String id : preAddedByRole) {
      // 중복체크
      if (aReviewIds.contains(id) == false && discussIds.contains(id) == false) {
        People people = PeopleHelper.manager.getPeople(id);
        discuss.put(idx++, people);
        discussIds.add(id);
        if (preAdded == false) preAdded = true;
      }
    }
  }

  index1 = bReview.size()+discuss.size()+aReview.size();
  index2 = receiver.size()+reference.size();
  // [END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준

  boolean isSecureBudget = true;
  String divisionCode = "";
  WFMProperties wfmConfig = WFMProperties.getInstance();
  String boardMemberID = "";
  String boardMemberName = "";

  if((obj instanceof KETProdChangeOrder)||(obj instanceof KETMoldChangeOrder)){
      
    // 초도인지 아닌지
    boolean isTheFirst = false;
    if(obj instanceof KETProdChangeOrder){
	    KETProdChangeOrder eco = (KETProdChangeOrder) obj;
	    String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
	    isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);

    } else if(obj instanceof KETMoldChangeOrder){
	    KETMoldChangeOrder eco = (KETMoldChangeOrder) obj;
        String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
        isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

    }
    
    // 비용확보 체크
    // 초도가 아닐 경우에만    
    if (!isTheFirst) {
        isSecureBudget = EcmSearchHelper.manager.isSecureBudget(pboOid);
    }     
    
    String exeGroupName = "";
    if(!isSecureBudget)  {
      if(obj instanceof KETProdChangeOrder){
        KETProdChangeOrder prodCO = (KETProdChangeOrder)obj;
        divisionCode = prodCO.getDivisionFlag();

      }else{
        KETMoldChangeOrder moldCO = (KETMoldChangeOrder)obj;
        divisionCode = moldCO.getDivisionFlag();
      }
      if(divisionCode.equals("C"))exeGroupName = wfmConfig.getString("wfm.car.group");
      else exeGroupName = wfmConfig.getString("wfm.electronic.group");
      WTGroup group = OrganizationServicesHelper.manager.getGroup(exeGroupName);
      Enumeration enumer = group.members();
      while(enumer.hasMoreElements()){
        WTUser member = (WTUser)enumer.nextElement();
        if(boardMemberID.equals("")) {
          boardMemberID = member.getName();
          boardMemberName = member.getFullName();
        }else {
          boardMemberID += "," + member.getName();
          boardMemberName += " / " + member.getFullName();
        }
      }
    }
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- [PLM System 1차개선] 버튼 비활성화, 2013-08-14, BoLee -->
<script src="/plm/portal/js/common.js"></script>

<!-- [PLM System 1차개선] 진행 상태바 표시, 2013-08-14, BoLee -->
<%@include file="/jsp/common/processing.html" %>

<script type="text/javascript">
//<![CDATA[

var tld;
var winObj1;
var winObj2;
var stop = false;
var isDRR = '<%= isDRR%>';

function popup(type){
  var ptable = document.getElementById("sApproval");

  if(type == 'setApproval'){
    var url = '/plm/jsp/wfm/SetApprovalParticipant.jsp?isDRR=<%= isDRR%>&pboOid=<%=pboOid%>';
    <%-- if(isDRR=='true') url = '/plm/jsp/wfm/SetApprovalParticipantDrr.jsp?isDRR=<%= isDRR%>'; --%>
    winObj1 = window.open(url,'결재선지정',"status=1, menu=no, width=950, height=700");
    //tld = setInterval('loadval1()', 20);
  }
  else{
    if(ptable.rows.length<1){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00772") %><%--결재선 지정 후에 선택하여 주십시요--%>');
      return;
    }
    var url = '/plm/jsp/wfm/SetDistributeParticipant.jsp';
    winObj2 = window.open(url,'배포처지정',"status=1, menu=no, width=950, height=700");
    //tld = setInterval('loadval2()', 20);
  }
}

function loadval1(){
  var obj = document.requestApproval;
  if(winObj1.document.readyState=="complete"){
    if(isDRR!='true'){
      winObj1.addUser(obj.bReview.value);
      winObj1.beforeDiscuss();
      if(obj.discussType.value=='noDiscuss'){
        winObj1.document.searchPeople.radiob[0].checked = true;
        winObj1.addUser(obj.discuss.value);
        winObj1.discuss();
      }
      else if(obj.discussType.value=='sequential'){
        winObj1.document.searchPeople.radiob[1].checked = true;
        winObj1.addUser(obj.discuss.value);
        winObj1.discuss();
      }
      else if(obj.discussType.value=='parallel'){
        winObj1.document.searchPeople.radiob[2].checked = true;
        winObj1.addUser(obj.discuss.value);
        winObj1.discuss();
      }else {
        winObj1.document.searchPeople.radiob[0].checked = true;
        winObj1.addUser(obj.discuss.value);
        winObj1.discuss();
      }
    }
    winObj1.addUser(obj.aReview.value);
    winObj1.afterDiscuss();

    clearInterval(tld);
    winObj1.endUserLoading();

// [START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
<%
    if (isApprovalForProjectSchedule || isTechDocDesign || isApprovalForElecEco || isCancelProject || isStopProject || isCompleteCheckDoc) {
%>
      winObj1.setPreAdded(obj.preAddedByRole.value, obj.preAddedChief.value);
<%
    }
%>
// [END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준

  }
}

function loadval2(){
  var obj = document.requestApproval;
  if(winObj2.document.readyState=="complete"){
    winObj2.addUser(obj.receiver.value);
    winObj2.receiver();
    winObj2.addUser(obj.reference.value);
    winObj2.reference();

    clearInterval(tld);
    winObj2.endUserLoading();
  }
}

function delapproval(){
  var obj = document.requestApproval;

  var tableObj1 = document.getElementById('sApproval');
    for(var j=tableObj1.rows.length-1; j>=0; j--){
      tableObj1.deleteRow(j);
    }
    obj.bReview.value = '';
    obj.discuss.value = '';
    obj.aReview.value = '';
}

function deldistribute(){
  var obj = document.requestApproval;

  var tableObj2 = document.getElementById('sDistribute');
    for(j=tableObj2.rows.length-1; j>=0; j--){
      tableObj2.deleteRow(j);
    }
    obj.receiver.value = '';
    obj.reference.value = '';
}

function addTR(totalArray , type){
  var cnt;
  var ptable;

  if(type=="approval"){
    ptable = document.getElementById("sApproval");
    if(ptable.rows.length>0)cnt = ptable.rows.length+1;
    else cnt = 1;
    for(var i=0; i<totalArray.length; i++){
      var newTR = ptable.insertRow(0);
      for(var j=0; j<totalArray[i].length; j++){
        var newTD = document.createElement('TD');
        if(j==0){
          newTD.innerText = cnt;
          newTD.width = "59";
        }
        else if(j==1){
          if( i == (totalArray.length-1) )
            newTD.innerText = "<%=messageService.getString("e3ps.message.ket_message", "01987") %><%--승인--%>";
          else
            newTD.innerText = totalArray[i][j];
          newTD.width = "100";
        }
        else if(j==2){
          newTD.innerText = totalArray[i][j];
          newTD.width = "170";
        }
        else if(j==3){
          newTD.innerText = totalArray[i][j];
          newTD.width = "90";
        }
        else if(j==4){
          newTD.innerText = totalArray[i][j];
          newTD.width = "159";
        }
        newTD.className = "tdwhiteM";
        newTR.appendChild(newTD);
      }
      cnt++;
    }
  }else{
    ptable = document.getElementById("sDistribute");
    if(ptable.rows.length>0)cnt = ptable.rows.length+1;
    else cnt = 1;
    for(var i=0; i<totalArray.length; i++){
      var newTR = ptable.insertRow(0);
      for(var j=0; j<totalArray[i].length; j++){
        var newTD = document.createElement('TD');
        if(j==0){
          newTD.innerText = cnt;
          newTD.width = "59";
        }
        else if(j==1){
          newTD.innerText = totalArray[i][j];
          newTD.width = "100";
        }
        else if(j==2){
          newTD.innerText = totalArray[i][j];
          newTD.width = "170";
        }
        else if(j==3){
          newTD.innerText = totalArray[i][j];
          newTD.width = "90";
        }
        else if(j==4){
          newTD.innerText = totalArray[i][j];
          newTD.width = "159";
        }
        newTD.className = "tdwhiteM";
        newTR.appendChild(newTD);
      }
      cnt++;
    }
  }
}

function saveSelectBox(type, selBox, page){
  var obj = document.requestApproval;

  if(page=='approval'){
    for(var i=0; i<selBox.length; i++){
      if(type=='type1'){
        obj.bReview.value +=selBox.options[i].value + ",";
      }else if(type=='type2'){
        obj.discuss.value +=selBox.options[i].value + ",";
      }else if(type=='type3'){
        obj.aReview.value +=selBox.options[i].value + ",";
      }
    }
  }else if(page=='distribute'){
    for(i=0; i<selBox.length; i++){
      if(type=='type4'){
        obj.receiver.value +=selBox.options[i].value + ",";
      }else{
        obj.reference.value +=selBox.options[i].value + ",";
      }
    }
  }

}

function doSave(){
    <% if(StringUtil.checkString(oid)){ %>
    update();
    <%}else{%>
    save();
    <%}%>
}

function save()  {
  var ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>');
  var ptable = document.getElementById("sApproval");
  if(ptable.rows.length<1){
    alert('<%=messageService.getString("e3ps.message.ket_message", "02712") %><%--지정된 사용자가 없습니다--%>');
    return;
  }
  if(ok){
    document.requestApproval.action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=create&startflag=false&pbo=<%= pboOid%>&mode=<%= mode %>";
    document.requestApproval.submit();
  }
}

function update()  {
  var ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>');
  <%-- var ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "01954") %>수정하시겠습니까?'); --%>
  var ptable = document.getElementById("sApproval");
  if(ptable.rows.length<1){
    alert('<%=messageService.getString("e3ps.message.ket_message", "02712") %><%--지정된 사용자가 없습니다--%>');
    return;
  }
  if(ok){
    document.requestApproval.action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=update&startflag=false&oid=<%=oid%>&pbo=<%=pboOid%>&mode=<%= mode %>";
    document.requestApproval.submit();
  }
}

function startProcess(){
  var totalArray = new Array();
  var totalcnt = 0;
  var ptable = document.getElementById("sApproval");
  var isApprYn = document.requestApproval.isApprYn.value;
<%-- <%
    QueryResult qre = new QueryResult();
    try{
      QuerySpec qse = new QuerySpec();
      Class clsAppr = KETWfmApprovalMaster.class;

      int idxAppr = qse.appendClassList(clsAppr, true);
      qse.appendWhere(new SearchCondition(clsAppr, "pboReference.key.id", "=",  CommonUtil.getOIDLongValue(pboOid)), new int[]{idxAppr});
      qse.appendAnd();
      qse.appendWhere(new SearchCondition(KETWfmApprovalMaster.class, KETWfmApprovalMaster.START_FLAG, SearchCondition.IS_TRUE));
      qre = PersistenceHelper.manager.find((QuerySpec)qse);

      String statestate =ClassifyPBOUtil.extractPBOInfo(obj).get("state").toString();

      if(qre.size() >= 1 && !statestate.equals("재작업") && !statestate.equals("일정변경")  && !statestate.equals("활동완료") && !statestate.equals("취소중")){
        //out.println("alert('"+statestate+"');");
        if(!(obj instanceof KETDqmAction)){
            out.println("alert('" + messageService.getString("e3ps.message.ket_message", "02281")/*이미 결재가 진행중입니다*/ + "');return;");
        }
        
      }

    }catch(Exception appEx){
    }
%> --%>
  if(isApprYn != 'Y'){
    alert('<%=messageService.getString("e3ps.message.ket_message", "02516") %><%--정보없는 사용자 또는 퇴사자가 지정되어있습니다 \n 결재선 또는 배포처를 재지정하십시오--%>');
    return;
  }
  if(ptable.rows.length<1){
    alert('<%=messageService.getString("e3ps.message.ket_message", "02712") %><%--지정된 사용자가 없습니다--%>');
    return;
  }
  // 승인자 확인
  var hasApprUser = false;
  var rows = ptable.rows;
  for(var i=0; i < rows.length; i++){
      var cells = rows[i].cells;
      var gubun = cells[1].textContent;
      if('<%=messageService.getString("e3ps.message.ket_message", "01987") %>' == gubun) hasApprUser = true;
  }
  if(!hasApprUser){
      alert('<%=messageService.getString("e3ps.message.ket_message", "05163") %><%--승인자가 없습니다--%>');
      return;
  }
<%-- 
//  var ecrCo = <%//=ecrCo%>;
//  if(ecrCo){
//    for(i=ptable.rows.length-1; i>=0; i--){
//      var targetArray = new Array(5);
//      for(j=0; j<ptable.rows[i].childNodes.length; j++){
//        if(ptable.rows[i].childNodes[j].tagName=='TD'){
//          targetArray[j]=ptable.rows[i].childNodes[j].innerText;
//          if( targetArray[j] == "<%//=sessionUser.getFullName()%>" ){
//            alert("지정된 사용자는 결재를 할 수 없습니다.");
//            return;
//          }
//        }
//      }
//      totalArray[totalcnt] = targetArray;
//      totalcnt++;
//    }
//  } --%>
  var isSecureBudget = "<%= isSecureBudget%>";
  var isInBoardMember = false;
  var boardMemberName = "<%= boardMemberName%>";
  if(isSecureBudget=='false'){
    var userArray1 = document.requestApproval.aReview.value.split(',');
    var userArray2 = document.requestApproval.bReview.value.split(',');
    var boardMember = "<%= boardMemberID%>";
    var boardMemberArray = boardMember.split(',');

    var array1Length = userArray1.length;
    var array2Length = userArray2.length;

    if(boardMember!=''){
      if(userArray1.length>2){
        for(var i=0; i<userArray1.length; i++){
          if(boardMemberArray.length>1){
            for(var j=0; j<boardMemberArray.length; j++){
              if(userArray1[array1Length-2]==boardMemberArray[j]){
                isInBoardMember = true;
                break;
              }
            }
          }else if(boardMemberArray.length==1){
            if(userArray1[array1Length-2]==boardMemberArray){
              isInBoardMember = true;
              break;
            }
          }
        }
      }else if(userArray1.length==2){
        if(boardMemberArray.length>1){
          for(var j=0; j<boardMemberArray.length; j++){
            if(userArray1[0]==boardMemberArray[j]){
              isInBoardMember = true;
              break;
            }
          }
        }else if(boardMemberArray.length==1){
          if(userArray1[0]==boardMemberArray){
            isInBoardMember = true;
          }
        }
      }else if(userArray2.length>2){
        for(var i=0; i<userArray2.length; i++){
          if(boardMemberArray.length>1){
            for(var j=0; j<boardMemberArray.length; j++){
              if(userArray2[array2Length-2]==boardMemberArray[j]){
                isInBoardMember = true;
                break;
              }
            }
          }else if(boardMemberArray.length==1){
            if(userArray2[array2Length-2]==boardMemberArray){
              isInBoardMember = true;
              break;
            }
          }
        }
      }else if(userArray2.length==2){
        if(boardMemberArray.length>1){
          for(var j=0; j<boardMemberArray.length; j++){
            if(userArray2[0]==boardMemberArray[j]){
              isInBoardMember = true;
              break;
            }
          }
        }else if(boardMemberArray.length==1){
          if(userArray2[0]==boardMemberArray){
            isInBoardMember = true;
          }
        }
      }
    }
    
    
    if((!isInBoardMember)&& (boardMemberArray.length>1)){
      alert('예산이 미확보된 ECO입니다\n'+boardMemberName+' 중\n1명을 최종승인자로 지정하여 결재를 요청하여 주십시요');
      return;
    }else if((!isInBoardMember)&& (boardMemberArray.length==1)){
      alert('예산이 미확보된 ECO입니다\n'+boardMemberName+' 본부장님을 최종승인자로 지정하여\n결재를 요청하여 주십시요');
      return;
    }
  }
  var preAddedChief2 = "<%= preAddedChief2 %>";
  var isProjectApproval = "<%= isProjectApproval %>";
  var preAddedChief2Name = "<%= preAddedChief2Name %>";
  var isProjectScheduleApproval = false;
  var protoGate1Check = "<%= protoGate1Check %>";
  protoGate1Check =  (protoGate1Check==='true');
  
  if(preAddedChief2 != '' && isProjectApproval == "T_assess"){//gate, dr 타스크의 일정초과시 팀장의 상위 부서장 자동 지정
	  var userArray1 = document.requestApproval.aReview.value.split(',');//검토 및 승인
      
      for(var j=0; j<userArray1.length; j++){
          if(userArray1[j]==preAddedChief2){
        	  isProjectScheduleApproval = true;
            break;
          }
      }
      
	  if(!isProjectScheduleApproval){
		  userArray1 = document.requestApproval.bReview.value.split(',');//합의 전 검토
	      
	      for(var j=0; j<userArray1.length; j++){
	          if(userArray1[j]==preAddedChief2){
	              isProjectScheduleApproval = true;
	            break;
	          }
	      }
	  }
      
  }else{
	  isProjectScheduleApproval = true;
  }
  if(!isProjectScheduleApproval){
	  alert("평가관리 또는 중점관리 Task의 일정이 지연된 경우\r\n상위 부서장인 "+preAddedChief2Name+" 님을 결재라인에 포함하여야합니다.");
	  return;
  }
  
  
  var preAddedChief = "<%= preAddedChief %>";
  var isCancelProject = "<%= isCancelProject %>";
  var isStopProject = "<%= isStopProject %>";
  var isCompleteCheckDoc = "<%=isCompleteCheckDoc%>";
  var preAddedChiefName = "<%= preAddedChiefName %>";
  var isCancelApproveCheck = false;
  var isStopApproveCheck = false;
  var isdocApproveCheck = false;
  var isInvestApproveCheck = false;
  
  if((isStopProject || isCancelProject || isCompleteCheckDoc || isInvest) && preAddedChief != ''){
	  var str = document.requestApproval.aReview.value; //검토 및 승인
	  str = str.substr(0, str.length -1);
      var userArray2 = str.split(',');
      
      for(var j=0; j<userArray2.length; j++){
          if(userArray2[userArray2.length-1]==preAddedChief){
        	  isCancelApproveCheck = true;
        	  isStopApproveCheck = true;
        	  isdocApproveCheck = true;
        	  isInvestApproveCheck = true;
              break;
          }

      }
      <%if(isCompleteCheckDoc){%>
      if(!isdocApproveCheck){
          alert(preAddedChiefName+" 님을 최종결재라인에 지정하여야합니다.");
          return;
      }
      <%}%>
      
      <%if(isCancelProject){%>
      if(!isCancelApproveCheck){
    	  alert(preAddedChiefName+" 님을 최종결재라인에 지정하여야합니다.");
          return;
      }
      <%}%>
      
      <%if(isStopProject){%>
      if(!isStopApproveCheck){
          alert(preAddedChiefName+" 님을 최종결재라인에 지정하여야합니다.");
          return;
      }
      <%}%>
      
      <%if(isInvest){%>
      if(!isInvestApproveCheck){
          alert(preAddedChiefName+" 님을 최종결재라인에 지정하여야합니다.");
          return;
      }
      <%}%>
  }
  if(protoGate1Check){
	  var protoCheck = true;
	  var cnt = 0;
	  var str = document.requestApproval.discuss.value; //합의
	  str = str.substr(0, str.length -1);
	  protoCheck = str.length > 1;
	  var protoGate1DiscussGroup =  "<%= protoGate1DiscussGroup %>";
	  var discussList = protoGate1DiscussGroup.split(",");
	  protoCheck = discussList.length == 2;
	  
	  if(protoCheck){
		  var userArray = str.split(',');
		  for(var j=0; j<userArray.length; j++){
	          if(userArray[j]==discussList[0]){
	        	  cnt ++;
	          }
	          if(userArray[j]==discussList[1]){
                  cnt ++;
              }
	      }
	  }
	  protoCheck = (cnt == 2);
	  
	  if(!protoCheck){
		  alert("Gate1 미완료 상태입니다.\r\n품질본부장, 생산기술부본부장 합의결재 추가 후 결재 진행하시기 바랍니다.");
		  return;
	  }
  }
  
  
      
  var ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "00769") %><%--결재를 요청하시겠습니까?--%>');
  if(ok){
      // [PLM System 1차개선] 진행 상태바 표시 및 버튼 비활성화, 2013-08-14, BoLee
      showProcessing();   // 진행 상태바 표시 (processing.html)
      disabledAllBtn();   // 버튼 비활성화
//alert("oid=<%=oid%>&pbo=<%= pboOid%>&popup=<%=popup%>&mode=<%= mode %>");
      document.requestApproval.action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=start&startflag=true&oid=<%=oid%>&pbo=<%= pboOid%>&popup=<%=popup%>&mode=<%= mode %>";
      document.requestApproval.submit();
  }
}

function lfn_onbeforeunload() {
	try {
		opener.hideProcessing();
		opener.enabledAllBtn();
		
	} catch(e) {}
}
//]]>
</script>
</head>
<form name=requestApproval method="post">
<body onload="javascript:window.focus();" onbeforeunload="javascript:lfn_onbeforeunload();">
<input type="hidden" id="bReview" name="bReview">
<input type="hidden" id="aReview" name="aReview">
<input type="hidden" id="discuss" name="discuss">
<input type="hidden" id="receiver" name="receiver">
<input type="hidden" id="reference" name="reference">
<input type="hidden" id="discussType" name="discussType" value="<%=agreementType %>">
<input type="hidden" id="isApprYn" name="isApprYn" value="<%=isApprYn %>">
<input type="hidden" id="preAddedByRole" name="preAddedByRole">
<!--  [START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준 -->
<input type="hidden" id="preAddedChief" name="preAddedChief">
<!--  [END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준 -->
<%
  if(pboOids != null && pboOids.length > 1){
      for(String poid : pboOids){ 
%>
<input type="hidden" id="pboOids" name="pboOids" value="<%= poid %>">
<%
      }
  }
%>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="720" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="30">&nbsp;</td>
          <td valign="top"><table border="0" cellspacing="0" cellpadding="0" width="720">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="720">
              <tr>
                <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03353") %><%--결재선--%></td>
                <td width="600" class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="580">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="580" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td>&nbsp;</td>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td background="../../portal/images/btn_bg1.gif"><a href="javascript:popup('setApproval')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00774") %><%--결재선지정--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="580">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="580" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01983") %><%--순차--%></td>
                      <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                      <td width="170" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                      <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02714") %><%--직급--%></td>
                      <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    </tr>
                  </table>
                      <div style="width: 580px; height: 100px; overflow-x: hidden; overflow-y: auto;">
                    <table id="sApproval" name="sApproval" width="580" cellpadding="0" cellspacing="0" class="table_border">
                    <%
                    boolean isApprover = true;
                    String unalbe = "";
                    String gubun = "";

                    for( int i = aReview.size() - 1; i >= 0; i-- )
                    {
                      gubun = isApprover ? messageService.getString("e3ps.message.ket_message", "01987")/*승인*/ : messageService.getString("e3ps.message.ket_message", "00716")/*검토*/;

                      People userInfo = (People)aReview.get(i);
            Kogger.debug(userInfo);

            if(userInfo != null)
            {
              if(userInfo.isIsDisable()){
                isApprYn  = "N";
                unalbe      = "(" + messageService.getString("e3ps.message.ket_message", "03342")/*퇴사*/ + ")";
              }else{
                unalbe = "";
              }
                      list1 = userInfo.getId()+","+list1;
                    %>
                      <tr>
                        <td class="tdwhiteM" width="59"><%=index1 %></td>
                        <td class="tdwhiteM" width="100"><%=gubun %></td>
                        <%if(userInfo.getDepartment() != null){ %>
                          <td class="tdwhiteM" width="170"><%=userInfo.getDepartment().getName() %></td>
                        <%}else{
                          isApprYn  = "N";
                        %>
                          <td class="tdwhiteM" width="170"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01536") %><%--부서 정보 없음--%></font></td>
                        <%} %>
                        <td class="tdwhiteM" width="90"><%=userInfo.getDuty() %></td>
                        <td class="tdwhiteM" width=159"><%=userInfo.getName() %><font color = 'red'><%=unalbe %></font> </td>
                      </tr>
                  <%
            }else{
                      isApprYn  = "N";
                %>
                   <tr>
                        <td class="tdwhiteM" width="59"><%=index1 %></td>
                        <td class="tdwhiteM" width="100"><%=gubun %></td>
                        <td class="tdwhiteM" width="419"  colspan="3"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01679") %><%--사용자 정보 없음--%></font></td>
                      </tr>
                <%
            }
                    index1--;
                    isApprover = false;
                  }
                    for( int i = discuss.size() - 1; i >= 0; i-- )
                    {
                      People userInfo = (People)discuss.get(i);

            if(userInfo != null)
            {
              if(userInfo.isIsDisable()){
                isApprYn  = "N";
                unalbe      = "(" + messageService.getString("e3ps.message.ket_message", "03342")/*퇴사*/ + ")";
              }else{
                unalbe = "";
              }
                      list2 = userInfo.getId()+","+list2;

                    %>
                      <tr>
                        <td class="tdwhiteM" width="59"><%=index1 %></td>
                        <td class="tdwhiteM" width="100"><%=messageService.getString("e3ps.message.ket_message", "03156") %><%--합의--%></td>
                        <%if(userInfo.getDepartment() != null){ %>
                          <td class="tdwhiteM" width="170"><%=userInfo.getDepartment().getName() %></td>
                        <%}else{
                          isApprYn  = "N";
                        %>
                          <td class="tdwhiteM" width="170"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01536") %><%--부서 정보 없음--%></font></td>
                        <%} %>
                        <td class="tdwhiteM" width="90"><%=userInfo.getDuty() %></td>
                        <td class="tdwhiteM" width=159"><%=userInfo.getName() %><font color = 'red'><%=unalbe %></font> </td>
                      </tr>
                  <%
            }else{
              isApprYn = "N";
          %>
            <tr>
                        <td class="tdwhiteM" width="59"><%=index1 %></td>
                        <td class="tdwhiteM" width="100"><%=messageService.getString("e3ps.message.ket_message", "03156") %><%--합의--%></td>
                        <td class="tdwhiteM" width="419"  colspan="3"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01679") %><%--사용자 정보 없음--%></font></td>
                      </tr>
          <%
            }
                    index1--;
                  }

                    for( int i = bReview.size() - 1; i >= 0; i-- )
                    {
                      gubun = isApprover ? messageService.getString("e3ps.message.ket_message", "01987")/*승인*/ : messageService.getString("e3ps.message.ket_message", "00716")/*검토*/;

                      People userInfo = (People)bReview.get(i);

            if(userInfo != null)
            {
              if(userInfo.isIsDisable()){
                isApprYn  = "N";
                unalbe      = "(" + messageService.getString("e3ps.message.ket_message", "03342")/*퇴사*/ + ")";
              }else{
                unalbe = "";
              }
                      list3 = userInfo.getId()+","+list3;
                    %>
                      <tr>
                        <td class="tdwhiteM" width="59"><%=index1 %></td>
                        <td class="tdwhiteM" width="100"><%=gubun %></td>
                        <%if(userInfo.getDepartment() != null){ %>
                          <td class="tdwhiteM" width="170"><%=userInfo.getDepartment().getName() %></td>
                        <%}else{
                          isApprYn  = "N";
                        %>
                          <td class="tdwhiteM" width="170"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01536") %><%--부서 정보 없음--%></font></td>
                        <%} %>
                        <td class="tdwhiteM" width="90"><%=userInfo.getDuty() %></td>
                        <td class="tdwhiteM" width=159"><%=userInfo.getName() %><font color = 'red'><%=unalbe %></font> </td>
                      </tr>
                  <%
            }else{
              isApprYn = "N";
          %>
             <tr>
                        <td class="tdwhiteM" width="59"><%=index1 %></td>
                        <td class="tdwhiteM" width="100"><%=gubun %></td>
                        <td class="tdwhiteM" width="419"  colspan="3"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01679") %><%--사용자 정보 없음--%></font></td>
                      </tr>
          <%
            }
                    index1--;
                    isApprover = false;
                  }
                    %>
                    </table>
                  </div>
                  <table border="0" cellspacing="0" cellpadding="0" width="580">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03354") %><%--배포처--%></td>
                <td class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="580">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                  <table width="580" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td>&nbsp;</td>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                              <td background="../../portal/images/btn_bg1.gif"><a href="javascript:popup('setDistribute');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01479") %><%--배포처 지정--%></a></td>
                              <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="580">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="580" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01983") %><%--순차--%></td>
                      <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                      <td width="170" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                      <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02714") %><%--직급--%></td>
                      <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    </tr>
                  </table>
                      <div style="width: 580px; height: 100px; overflow-x: hidden; overflow-y: auto;">
                    <table id="sDistribute" name="sDistribute" width="580" cellpadding="0" cellspacing="0" class="table_border">
                    <%
                    for( int i = receiver.size() - 1; i >= 0; i-- )
                    {
                      People userInfo = (People)receiver.get(i);

            if(userInfo != null)
            {
              if(userInfo.isIsDisable()){
                isApprYn  = "N";
                unalbe      = "(" + messageService.getString("e3ps.message.ket_message", "03342")/*퇴사*/ + ")";
              }else{
                unalbe = "";
              }
                      list5 = userInfo.getId()+","+list5;
                    %>
                      <tr>
                        <td class="tdwhiteM" width="59"><%=index2 %></td>
                        <td class="tdwhiteM" width="100"><%=messageService.getString("e3ps.message.ket_message", "01929") %><%--수신자--%></td>
                        <%if(userInfo.getDepartment() != null){ %>
                          <td class="tdwhiteM" width="170"><%=userInfo.getDepartment().getName() %></td>
                        <%}else{
                          isApprYn  = "N";
                        %>
                          <td class="tdwhiteM" width="170"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01536") %><%--부서 정보 없음--%></font></td>
                        <%} %>
                        <td class="tdwhiteM" width="90"><%=userInfo.getDuty() %></td>
                        <td class="tdwhiteM" width=159"><%=userInfo.getName() %><font color = 'red'><%=unalbe %></font> </td>
                      </tr>
                  <%
            }else{
              isApprYn = "N";
          %>
            <tr>
                        <td class="tdwhiteM" width="59"><%=index2 %></td>
                        <td class="tdwhiteM" width="100"><%=messageService.getString("e3ps.message.ket_message", "01929") %><%--수신자--%></td>
                        <td class="tdwhiteM" width="419"  colspan="3"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01679") %><%--사용자 정보 없음--%></font></td>
                      </tr>
          <%
            }
                    index2--;
                  }

                    for( int i = reference.size() - 1; i >= 0; i-- )
                    {
                      People userInfo = (People)reference.get(i);

            if(userInfo != null)
            {
              if(userInfo.isIsDisable()){
                isApprYn  = "N";
                unalbe      = "(" + messageService.getString("e3ps.message.ket_message", "03342")/*퇴사*/ + ")";
              }else{
                unalbe = "";
              }
                      list4 = userInfo.getId()+","+list4;
                    %>
                      <tr>
                        <td class="tdwhiteM" width="59"><%=index2 %></td>
                        <td class="tdwhiteM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02768") %><%--참조자--%></td>
                        <%if(userInfo.getDepartment() != null){ %>
                          <td class="tdwhiteM" width="170"><%=userInfo.getDepartment().getName() %></td>
                        <%}else{
                          isApprYn  = "N";
                        %>
                          <td class="tdwhiteM" width="170"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01536") %><%--부서 정보 없음--%></font></td>
                        <%} %>
                        <td class="tdwhiteM" width="90"><%=userInfo.getDuty() %></td>
                        <td class="tdwhiteM" width=159"><%=userInfo.getName() %><font color = 'red'><%=unalbe %></font> </td>
                      </tr>
                  <%
            }else{
              isApprYn = "N";
          %>
            <tr>
                        <td class="tdwhiteM" width="59"><%=index2 %></td>
                        <td class="tdwhiteM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02768") %><%--참조자--%></td>
                        <td class="tdwhiteM" width="419"  colspan="3"><font color = "red" ><%=messageService.getString("e3ps.message.ket_message", "01679") %><%--사용자 정보 없음--%></font></td>
                      </tr>
          <%
            }
                    index2--;
                  }
                  %>
                    </table>
                  </div>
                  <table border="0" cellspacing="0" cellpadding="0" width="580">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00780") %><%--결재요청의견--%><br>
                  (<%=messageService.getString("e3ps.message.ket_message", "00045", 600) %><%--{0}자 이내--%>)</td>
                <td class="tdwhiteM0" style="height:100"><textarea name="textfield" class="txt_field" id="textfield" maxlength="600" style="width:580; height:96%"><%=comment %></textarea></td>
              </tr>
            </table>
            <table width="720" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table width="720" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02374") %><%--임시저장--%></a></td>
                            <%-- 
                            <%if(oid==""){ %>
                            <td background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %>저장</a></td>
                            <%} else{ %>
                            <td background="../../portal/images/btn_bg1.gif"><a href="javascript:update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %>수정</a></td>
                            <%} %>
                             --%>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td background="../../portal/images/btn_bg1.gif"><a href="javascript:startProcess()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--01197--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="720">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
  </tr>
  <!-- <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="710" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr> -->
</table>
</body>
</form>
</html>
<script language="javascript">
<%
  if(master!=null){
%>
  document.requestApproval.discussType.value = '<%=agreementType%>';
  document.requestApproval.bReview.value = '<%=list3%>';
  document.requestApproval.discuss.value = '<%=list2%>';
  document.requestApproval.aReview.value = '<%=list1%>';
  document.requestApproval.receiver.value = '<%=list5%>';
  document.requestApproval.reference.value = '<%=list4%>';
<%
  }
  // [START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
  else if (preAdded == true) {
%>
  document.requestApproval.discuss.value = '<%=list2%>';
  document.requestApproval.aReview.value = '<%=list1%>';
<%
  }

  if (isApprovalForProjectSchedule || isTechDocDesign || isApprovalForElecEco || isCancelProject) {
%>
  document.requestApproval.preAddedByRole.value = '<%=KETStringUtil.join(preAddedByRole, ",") %>';
  document.requestApproval.preAddedChief.value = '<%=preAddedChief %>';
<%
  }
  // [END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
%>
<%
if(isApprYn != "Y"){
%>
  document.requestApproval.isApprYn.value = '<%=isApprYn%>';
<%
}
%>
</script>
