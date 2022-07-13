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
            e3ps.common.obj.*,
            e3ps.groupware.company.*,
            e3ps.project.*,
            e3ps.project.beans.*,
            e3ps.common.web.*,
            e3ps.common.code.*,
            e3ps.common.util.KETStringUtil,
            e3ps.project.historyprocess.HistoryHelper,
            e3ps.project.outputtype.OEMPlan,
            e3ps.project.outputtype.OEMProjectType,
            e3ps.sap.*,
            e3ps.wfm.entity.KETWfmApprovalMaster,
            ext.ket.project.gate.entity.*,
            ext.ket.project.gate.service.*,
            ext.ket.project.task.entity.*,
            ext.ket.project.task.service.*
            " %>
            
            
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>            

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%

	String designType = request.getParameter("designType");
	if(designType==null) {
	    designType = "A";
	}
// String[] requestUri = request.getRequestURI().split("/"); 
// out.println("URI:"+requestUri[requestUri.length-1]);
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


   String linkedAssOid = (String)request.getAttribute("ASS_OID");
   linkedAssOid = StringUtil.checkNull(linkedAssOid);
   String oid = request.getParameter("oid");
   if(StringUtil.isEmpty(oid)) {
       oid = linkedAssOid; 
   }
   
   String pjtStateStr = (String)request.getAttribute("PJT_STATE");
   
   String pjtOid = request.getParameter("pjtOid");
   E3PSProject project = (E3PSProject)CommonUtil.getObject(pjtOid);
   E3PSProjectData projectData = new E3PSProjectData(project);
   String popup = StringUtil.checkNull( request.getParameter("popup") );
   pageContext.setAttribute("projectState", projectData.state);
   
   boolean isPM = ProjectUserHelper.manager.isPM(project);                      //isPM
   
   AssessSheet assSheetObj = AssessSheetHelper.service.findLinkedAssessSheet(pjtOid);
   
   ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();
   
   KETParamMapUtil enumMap = null;
   String targetTypeEnumKey = "";
   String targetTypeEnum = "";

   Vector targetTypeVec = NumberCodeHelper.manager.getNumberCodeForQuery("ASSESSTARGETTYPE");
   if (targetTypeVec != null) {
       for (int i = 0; i < targetTypeVec.size(); i++) {
           NumberCode code = (NumberCode) targetTypeVec.get(i);
           enumMap = KETParamMapUtil.getMap();
           enumMap.put("key",      code.getCode());
           enumMap.put("value",    code.getName());
           enumList.add(enumMap);
           // code.getPersistInfo().getObjectIdentifier().getStringValue()
       }
       targetTypeEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
       targetTypeEnum       = KETGridUtil.getValueEnum(enumList, false);
   }
   
   ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
   boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
   boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
   boolean isCS = CommonUtil.isMember("공정감사");
   
   
   String gateExistG1 = "0";
   String gateExistG2 = "0";
   String gateExistG3 = "0";
   String gateExistG4 = "0";
   String gateExistG5 = "0";
   String gateExistG6 = "0";
   String gateExistG7 = "0";
   String gateExistG8 = "0";
   String gateExistG9 = "0";
   String gateExistG10 = "0";
   String gateExistG11 = "0";
   String gateExistG12 = "0";
   String gateExistG13 = "0";
   String gateExistG14 = "0";
   String gateExistG15 = "0";
   String gateExistG16 = "0";
   String gateExistG17 = "0";
   String gateExistG18 = "0";
   String gateExistG19 = "0";
   String gateExistG20 = "0";
   String gateExistG1Name = "";
   String gateExistG2Name = "";
   String gateExistG3Name = "";
   String gateExistG4Name = "";
   String gateExistG5Name = "";
   String gateExistG6Name = "";
   String gateExistG7Name = "";
   String gateExistG8Name = "";
   String gateExistG9Name = "";
   String gateExistG10Name = "";
   String gateExistG11Name = "";
   String gateExistG12Name = "";
   String gateExistG13Name = "";
   String gateExistG14Name = "";
   String gateExistG15Name = "";
   String gateExistG16Name = "";
   String gateExistG17Name = "";
   String gateExistG18Name = "";
   String gateExistG19Name = "";
   String gateExistG20Name = "";
   String gateCanEditG1 = "1";
   String gateCanEditG2 = "1";
   String gateCanEditG3 = "1";
   String gateCanEditG4 = "1";
   String gateCanEditG5 = "1";
   String gateCanEditG6 = "1";
   String gateCanEditG7 = "1";
   String gateCanEditG8 = "1";
   String gateCanEditG9 = "1";
   String gateCanEditG10 = "1";
   String gateCanEditG11 = "1";
   String gateCanEditG12 = "1";
   String gateCanEditG13 = "1";
   String gateCanEditG14 = "1";
   String gateCanEditG15 = "1";
   String gateCanEditG16 = "1";
   String gateCanEditG17 = "1";
   String gateCanEditG18 = "1";
   String gateCanEditG19 = "1";
   String gateCanEditG20 = "1";
   List<GateTaskOutputDTO> gateStringList = ProjectTaskCompHelper.service.getProjectGateCheckSheetList(pjtOid);
   
HashMap gateHash = new HashMap();
targetTypeVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATELEVELNAME");
if (targetTypeVec != null) {
    for (int i = 0; i < targetTypeVec.size(); i++) {
        NumberCode code = (NumberCode) targetTypeVec.get(i);
        gateHash.put((String)code.getCode() , ((String)code.getName()).replaceAll("Gate", ""));
    }
}
//    Vector gateLevelVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATELEVELNAME");
   if (gateStringList != null) {
       for (int i = 0; i < gateStringList.size(); i++) {
//            NumberCode code = (NumberCode) gateLevelVec.get(i);
           GateTaskOutputDTO gateCategoryDto = (GateTaskOutputDTO)gateStringList.get(i);
           E3PSTask task = (E3PSTask)CommonUtil.getObject("e3ps.project.E3PSTask:"+gateCategoryDto.getTaskOid());
           GateAssessResult gateRslt = null;
           String resultState = "";
           String resultStateName = "";
           QueryResult qr = PersistenceHelper.manager.navigate(task, "assess", GateAssRsltTaskLink.class);
           if (qr.hasMoreElements()) {
	           gateRslt = (GateAssessResult) qr.nextElement();
	           if (gateRslt != null) {
		           resultState = gateRslt.getState().toString();
		           resultStateName = gateRslt.getLifeCycleState().getDisplay(Locale.KOREA);
	           }
           }

           
           String gateCategory = gateCategoryDto.getOutputTaskCategory();
           
           gateCategory = StringUtil.checkNull(gateCategory);
           if(gateCategory.equals((String)gateHash.get("G1"))) {
              gateExistG1 = "1";
              gateExistG1Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG1 = "0";
              else gateCanEditG1 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G2"))) {
              gateExistG2 = "1";
              gateExistG2Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG2 = "0";
              else gateCanEditG2 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G3"))) {
              gateExistG3 = "1";
              gateExistG3Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG3 = "0";
              else gateCanEditG3 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G4"))) {
              gateExistG4 = "1";
              gateExistG4Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG4 = "0";
              else gateCanEditG4 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G5"))) {
              gateExistG5 = "1";
              gateExistG5Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG5 = "0";
              else gateCanEditG5 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G6"))) {
              gateExistG6 = "1";
              gateExistG6Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG6 = "0";
              else gateCanEditG6 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G7"))) {
              gateExistG7 = "1";
              gateExistG7Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG7 = "0";
              else gateCanEditG7 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G8"))) {
              gateExistG8 = "1";
              gateExistG8Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG8 = "0";
              else gateCanEditG8 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G9"))) {
              gateExistG9 = "1";
              gateExistG9Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG9 = "0";
              else gateCanEditG9 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G10"))) {
              gateExistG10 = "1";
              gateExistG10Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG10 = "0";
              else gateCanEditG10 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G11"))) {
              gateExistG11 = "1";
              gateExistG11Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG11 = "0";
              else gateCanEditG11 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G12"))) {
              gateExistG12 = "1";
              gateExistG12Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG12 = "0";
              else gateCanEditG12 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G13"))) {
              gateExistG13 = "1";
              gateExistG13Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG13 = "0";
              else gateCanEditG13 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G14"))) {
              gateExistG14 = "1";
              gateExistG14Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG14 = "0";
              else gateCanEditG14 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G15"))) {
              gateExistG15 = "1";
              gateExistG15Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG15 = "0";
              else gateCanEditG15 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G16"))) {
              gateExistG16 = "1";
              gateExistG16Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG16 = "0";
              else gateCanEditG16 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G17"))) {
              gateExistG17 = "1";
              gateExistG17Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG17 = "0";
              else gateCanEditG17 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G18"))) {
              gateExistG18 = "1";
              gateExistG18Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG18 = "0";
              else gateCanEditG18 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G19"))) {
              gateExistG19 = "1";
              gateExistG19Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG19 = "0";
              else gateCanEditG19 = "1";
           }
           else if(gateCategory.equals((String)gateHash.get("G20"))) {
              gateExistG20 = "1";
              gateExistG20Name = gateCategoryDto.getOutputTaskName();    //
              //Gate 평가결과가 승인완료인 경우 수정 불가 처리하고, 승인완료가 아니면 수정가능(단, 목표항목이 승인완료가 아닌 경우)
              if("APPROVED".equals(resultState)) gateCanEditG20 = "0";
              else gateCanEditG20 = "1";
           }
       }
   }
   
	enumList = new ArrayList<KETParamMapUtil>();
	String creteriaEnumKey = "";
	String creteriaEnum = "";
	
	enumMap = KETParamMapUtil.getMap();
	
	enumMap.put("key",      0);
	enumMap.put("value",    ">=");
	
	enumList.add(enumMap);
	
	enumMap = KETParamMapUtil.getMap();
	
	enumMap.put("key",      1);
	enumMap.put("value",    "=");
	
	enumList.add(enumMap);
	
	enumMap = KETParamMapUtil.getMap();
	
	enumMap.put("key",      2);
	enumMap.put("value",    "<=");
	
	enumList.add(enumMap);
	
	creteriaEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
	creteriaEnum       = KETGridUtil.getValueEnum(enumList, false);
	
	List<E3PSTask> listTask1 =  ProjectTaskCompHelper.service.getTaskSevenBefore();
	//if(listTask1!=null ) out.println("SIZE:"+listTask1.size());
%>


<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/gate/CreateAssessSheet.js"></script>


<script type="text/javascript">
<!--

$(document).ready(function(){
<%
    if(assSheetObj!=null)
    {
%>
    CreateAssessSheet.createPaingGrid();
    treeGridResize("#CreateAssessSheetSearchGrid","#listCheckGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
<%
    }
%>
});

function registAssessTarget() {
    var url = "/plm/ext/project/gate/selectTemplateAssessForm.do?oid=<%=pjtOid%>";
    openOtherName(url,"TemplateAssessSheetSelectPopup","900","650","status=no,scrollbars=auto,resizable=no");
    //getOpenWindow2('/plm/extcore/jsp/project/gate/createTemplateAssessForm.jsp','TemplateAssessSheetCreatePopup',900,650);
    //getOpenWindow2('/plm/ext/project/gate/selectTemplateAssessForm.do','TemplateAssessSheetSelectPopup',900,650);
}

function excelDown(){
    location.href = "/plm/jsp/project/CFTRoleExcelDown.jsp?oid=<%=oid%>";
}

function excelUp() {
    var url = "/plm/jsp/project/CFTRoleUp.jsp?oid=<%=oid%>";
    openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");

}

function registProjectAssessSheet(oid) {
	showProcessing(); //Progressbar 보이기
    var pjtOid = '<%=pjtOid%>';
    //alert('pjtOid:'+pjtOid+', tmplAssessOid:'+oid);
    var tmplAssessOid = oid;
    document.CreateAssessSheetUpdateForm.pjtOid.value = pjtOid;
    document.CreateAssessSheetUpdateForm.tmplAssessOid.value = oid;
    document.CreateAssessSheetUpdateForm.action = "/plm/ext/project/gate/registTemplateAssessForm.do";
    document.CreateAssessSheetUpdateForm.submit();
    //getOpenWindow2('/plm/ext/project/gate/updateTemplateAssessForm.do?oid='+rows[0].oid,'TemplateAssessSheetUpdatePopup',900,650);
    hideProcessing(); //Progressbar 감추기
}

function createGateTemplate() 
{
	var linkedAssOid = '<%=linkedAssOid%>';
	if(linkedAssOid=='' || linkedAssOid=='null') {
		alert('<%=messageService.getString("e3ps.message.ket_message", "07153") %><%--우선 평가항목을 등록하시기 바랍니다.--%>');
		return;
	}
    var rntStr = CreateAssessSheet.updateGateCheckSheetList();
    showProcessing(); //Progressbar 보이기
    location.replace('/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=pjtOid%>');
<%--     alert('<%=messageService.getString("e3ps.message.ket_message", "02518") %>정상적으로 처리되었습니다'); --%>
    hideProcessing(); //Progressbar 감추기
}

function reviseAssessSheet() {
    var url = "/plm/ext/project/gate/reviseAssessSheetForm.do?pjtOid=<%=pjtOid%>";
    openOtherName(url,"TemplateAssessSheetRevisePopup","650","450","status=no,scrollbars=auto,resizable=no");
    //getOpenWindow2('/plm/extcore/jsp/project/gate/createTemplateAssessForm.jsp','TemplateAssessSheetCreatePopup',900,650);
    //getOpenWindow2('/plm/ext/project/gate/selectTemplateAssessForm.do','TemplateAssessSheetSelectPopup',900,650);
}
function reviseAssessSheetList() {
    var url = "/plm/ext/project/gate/reviseAssessSheetListForm.do?pjtOid=<%=pjtOid%>";
    openOtherName(url,"TemplateAssessSheetRevisePopup","860","650","status=no,scrollbars=auto,resizable=no");
}

function approvalAction(oid){
<%
    if (project.getLifeCycleState().getDisplay().equals("등록중")) {   //|| project.getLifeCycleState().getDisplay().equals("재작업")
%>
alert('<%=messageService.getString("e3ps.message.ket_message", "07270") %><%--프로젝트 등록중 상태에서는 결재요청을 할 수 없습니다--%>');
return;
<%
    }

AssessSheet latestAssSheet = null;
E3PSProject latestPjt = E3PSProjectHelper.service.getProjectByProjectNo(project.getPjtNo());
QueryResult qr3 = PersistenceHelper.manager.navigate(latestPjt, "assess", ProjectAssSheetLink.class);

String assessOidStr="";
String latestOid = CommonUtil.getOIDString(latestPjt);
while (qr3.hasMoreElements()) {
    latestAssSheet = (AssessSheet) qr3.nextElement();
    if(latestAssSheet!=null) assessOidStr = CommonUtil.getOIDString(latestAssSheet);
}

%>
	oid = '<%=StringUtil.checkNull(assessOidStr)%>';
	
	if(oid=='' || oid == 'null') {
		alert('<%=messageService.getString("e3ps.message.ket_message", "07154") %><%--평가항목을 등록후 결재요청바랍니다.--%>');
		return;
	} 
    var rntStr = CreateAssessSheet.updateGateCheckSheetListNoAlert();

    var url="/plm/jsp/wfm/RequestApproval.jsp?pboOid="+oid;
    openOtherName(url,"approval","800","700","status=no,scrollbars=yes,resizable=no");
}

function openCostHistory(oid){
    var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
    openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
}


//-->
</script>

<form name="CreateAssessSheetUpdateForm" method="post">
<input type='hidden' name='popup' value="<%=popup %>">
<input type='hidden' name='command' value="">
<input type='hidden' name='oid' value="<%=oid%>">
<input type='hidden' name='pjtOid' value="<%=pjtOid%>">
<input type='hidden' name='tmplAssessOid' value="">
<input type="hidden" name="delOids" value=""/>
<input type="hidden" name="targetTypeEnumKey" value="<%=targetTypeEnumKey %>"/>
<input type="hidden" name="targetTypeEnum" value="<%=targetTypeEnum %>"/>
<input type="hidden" name="creteriaEnumKey" value="<%=creteriaEnumKey %>"/>
<input type="hidden" name="creteriaEnum" value="<%=creteriaEnum %>"/>
<input type="hidden" name="gateExistG1" value="<%=gateExistG1 %>"/>
<input type="hidden" name="gateExistG2" value="<%=gateExistG2 %>"/>
<input type="hidden" name="gateExistG3" value="<%=gateExistG3 %>"/>
<input type="hidden" name="gateExistG4" value="<%=gateExistG4 %>"/>
<input type="hidden" name="gateExistG5" value="<%=gateExistG5 %>"/>
<input type="hidden" name="gateExistG6" value="<%=gateExistG6 %>"/>
<input type="hidden" name="gateExistG7" value="<%=gateExistG7 %>"/>
<input type="hidden" name="gateExistG8" value="<%=gateExistG8 %>"/>
<input type="hidden" name="gateExistG9" value="<%=gateExistG9 %>"/>
<input type="hidden" name="gateExistG10" value="<%=gateExistG10 %>"/>
<input type="hidden" name="gateExistG11" value="<%=gateExistG11 %>"/>
<input type="hidden" name="gateExistG12" value="<%=gateExistG12 %>"/>
<input type="hidden" name="gateExistG13" value="<%=gateExistG13 %>"/>
<input type="hidden" name="gateExistG14" value="<%=gateExistG14 %>"/>
<input type="hidden" name="gateExistG15" value="<%=gateExistG15 %>"/>
<input type="hidden" name="gateExistG16" value="<%=gateExistG16 %>"/>
<input type="hidden" name="gateExistG17" value="<%=gateExistG17 %>"/>
<input type="hidden" name="gateExistG18" value="<%=gateExistG18 %>"/>
<input type="hidden" name="gateExistG19" value="<%=gateExistG19 %>"/>
<input type="hidden" name="gateExistG20" value="<%=gateExistG20 %>"/>
<input type="hidden" name="gateExistG1Name" value="<%=gateExistG1Name %>"/>
<input type="hidden" name="gateExistG2Name" value="<%=gateExistG2Name %>"/>
<input type="hidden" name="gateExistG3Name" value="<%=gateExistG3Name %>"/>
<input type="hidden" name="gateExistG4Name" value="<%=gateExistG4Name %>"/>
<input type="hidden" name="gateExistG5Name" value="<%=gateExistG5Name %>"/>
<input type="hidden" name="gateExistG6Name" value="<%=gateExistG6Name %>"/>
<input type="hidden" name="gateExistG7Name" value="<%=gateExistG7Name %>"/>
<input type="hidden" name="gateExistG8Name" value="<%=gateExistG8Name %>"/>
<input type="hidden" name="gateExistG9Name" value="<%=gateExistG9Name %>"/>
<input type="hidden" name="gateExistG10Name" value="<%=gateExistG10Name %>"/>
<input type="hidden" name="gateExistG11Name" value="<%=gateExistG11Name %>"/>
<input type="hidden" name="gateExistG12Name" value="<%=gateExistG12Name %>"/>
<input type="hidden" name="gateExistG13Name" value="<%=gateExistG13Name %>"/>
<input type="hidden" name="gateExistG14Name" value="<%=gateExistG14Name %>"/>
<input type="hidden" name="gateExistG15Name" value="<%=gateExistG15Name %>"/>
<input type="hidden" name="gateExistG16Name" value="<%=gateExistG16Name %>"/>
<input type="hidden" name="gateExistG17Name" value="<%=gateExistG17Name %>"/>
<input type="hidden" name="gateExistG18Name" value="<%=gateExistG18Name %>"/>
<input type="hidden" name="gateExistG19Name" value="<%=gateExistG19Name %>"/>
<input type="hidden" name="gateExistG20Name" value="<%=gateExistG20Name %>"/>
<input type="hidden" name="GATE1_CNT" value="${GATE1_CNT}"/>
<input type="hidden" name="GATE2_CNT" value="${GATE2_CNT}"/>
<input type="hidden" name="GATE3_CNT" value="${GATE3_CNT}"/>
<input type="hidden" name="GATE4_CNT" value="${GATE4_CNT}"/>
<input type="hidden" name="GATE5_CNT" value="${GATE5_CNT}"/>
<input type="hidden" name="GATE6_CNT" value="${GATE6_CNT}"/>
<input type="hidden" name="GATE7_CNT" value="${GATE7_CNT}"/>
<input type="hidden" name="GATE8_CNT" value="${GATE8_CNT}"/>
<input type="hidden" name="GATE9_CNT" value="${GATE9_CNT}"/>
<input type="hidden" name="GATE10_CNT" value="${GATE10_CNT}"/>
<input type="hidden" name="GATE11_CNT" value="${GATE11_CNT}"/>
<input type="hidden" name="GATE12_CNT" value="${GATE12_CNT}"/>
<input type="hidden" name="GATE13_CNT" value="${GATE13_CNT}"/>
<input type="hidden" name="GATE14_CNT" value="${GATE14_CNT}"/>
<input type="hidden" name="GATE15_CNT" value="${GATE15_CNT}"/>
<input type="hidden" name="GATE16_CNT" value="${GATE16_CNT}"/>
<input type="hidden" name="GATE17_CNT" value="${GATE17_CNT}"/>
<input type="hidden" name="GATE18_CNT" value="${GATE18_CNT}"/>
<input type="hidden" name="GATE19_CNT" value="${GATE19_CNT}"/>
<input type="hidden" name="GATE20_CNT" value="${GATE20_CNT}"/>
<%
    if("A".equals(designType)) {
        if(!StringUtil.isEmpty(linkedAssOid) && ("INWORK".equals(pjtStateStr) || "REWORK".equals(pjtStateStr))) {
%>


	<input type="hidden" name="canEdit" value="1"/>
	<input type="hidden" name="gateCanEditG1" value="<%=gateCanEditG1 %>"/>
	<input type="hidden" name="gateCanEditG2" value="<%=gateCanEditG2 %>"/>
	<input type="hidden" name="gateCanEditG3" value="<%=gateCanEditG3 %>"/>
	<input type="hidden" name="gateCanEditG4" value="<%=gateCanEditG4 %>"/>
	<input type="hidden" name="gateCanEditG5" value="<%=gateCanEditG5 %>"/>
	<input type="hidden" name="gateCanEditG6" value="<%=gateCanEditG6 %>"/>
	<input type="hidden" name="gateCanEditG7" value="<%=gateCanEditG7 %>"/>
	<input type="hidden" name="gateCanEditG8" value="<%=gateCanEditG8 %>"/>
	<input type="hidden" name="gateCanEditG9" value="<%=gateCanEditG9 %>"/>
	<input type="hidden" name="gateCanEditG10" value="<%=gateCanEditG10 %>"/>
	<input type="hidden" name="gateCanEditG11" value="<%=gateCanEditG11 %>"/>
	<input type="hidden" name="gateCanEditG12" value="<%=gateCanEditG12 %>"/>
	<input type="hidden" name="gateCanEditG13" value="<%=gateCanEditG13 %>"/>
	<input type="hidden" name="gateCanEditG14" value="<%=gateCanEditG14 %>"/>
	<input type="hidden" name="gateCanEditG15" value="<%=gateCanEditG15 %>"/>
	<input type="hidden" name="gateCanEditG16" value="<%=gateCanEditG16 %>"/>
	<input type="hidden" name="gateCanEditG17" value="<%=gateCanEditG17 %>"/>
	<input type="hidden" name="gateCanEditG18" value="<%=gateCanEditG18 %>"/>
	<input type="hidden" name="gateCanEditG19" value="<%=gateCanEditG19 %>"/>
	<input type="hidden" name="gateCanEditG20" value="<%=gateCanEditG20 %>"/>
<%
        }else {
%>
	<input type="hidden" name="canEdit" value="0"/>
	<input type="hidden" name="gateCanEditG1" value="0"/>
	<input type="hidden" name="gateCanEditG2" value="0"/>
	<input type="hidden" name="gateCanEditG3" value="0"/>
	<input type="hidden" name="gateCanEditG4" value="0"/>
	<input type="hidden" name="gateCanEditG5" value="0"/>
	<input type="hidden" name="gateCanEditG6" value="0"/>
	<input type="hidden" name="gateCanEditG7" value="0"/>
	<input type="hidden" name="gateCanEditG8" value="0"/>
	<input type="hidden" name="gateCanEditG9" value="0"/>
	<input type="hidden" name="gateCanEditG10" value="0"/>
	<input type="hidden" name="gateCanEditG11" value="0"/>
	<input type="hidden" name="gateCanEditG12" value="0"/>
	<input type="hidden" name="gateCanEditG13" value="0"/>
	<input type="hidden" name="gateCanEditG14" value="0"/>
	<input type="hidden" name="gateCanEditG15" value="0"/>
	<input type="hidden" name="gateCanEditG16" value="0"/>
	<input type="hidden" name="gateCanEditG17" value="0"/>
	<input type="hidden" name="gateCanEditG18" value="0"/>
	<input type="hidden" name="gateCanEditG19" value="0"/>
	<input type="hidden" name="gateCanEditG20" value="0"/>
<%
        }
    }else {
%>
    <input type="hidden" name="canEdit" value="0"/>
    <input type="hidden" name="gateCanEditG1" value="0"/>
    <input type="hidden" name="gateCanEditG2" value="0"/>
    <input type="hidden" name="gateCanEditG3" value="0"/>
    <input type="hidden" name="gateCanEditG4" value="0"/>
    <input type="hidden" name="gateCanEditG5" value="0"/>
    <input type="hidden" name="gateCanEditG6" value="0"/>
    <input type="hidden" name="gateCanEditG7" value="0"/>
    <input type="hidden" name="gateCanEditG8" value="0"/>
    <input type="hidden" name="gateCanEditG9" value="0"/>
    <input type="hidden" name="gateCanEditG10" value="0"/>
    <input type="hidden" name="gateCanEditG11" value="0"/>
    <input type="hidden" name="gateCanEditG12" value="0"/>
    <input type="hidden" name="gateCanEditG13" value="0"/>
    <input type="hidden" name="gateCanEditG14" value="0"/>
    <input type="hidden" name="gateCanEditG15" value="0"/>
    <input type="hidden" name="gateCanEditG16" value="0"/>
    <input type="hidden" name="gateCanEditG17" value="0"/>
    <input type="hidden" name="gateCanEditG18" value="0"/>
    <input type="hidden" name="gateCanEditG19" value="0"/>
    <input type="hidden" name="gateCanEditG20" value="0"/>
<%
    }
%>
<div style="<%=("B".equals(designType))?"":"margin:0 10px;" %>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=("B".equals(designType))?"display:none":"" %>">
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
<%
    if("A".equals(designType)) {
%>

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="5"></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png"></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView2.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView3.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02327") %><%--인원--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <%if(!isCS){ %>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView4.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <%} %>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><img src="/plm/portal/images/tab_4.png"></td>
                        <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                            href="/plm/jsp/project/ProjectExtView10.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">Issue</a></td>
                        <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><img src="/plm/portal/images/tab_4.png"></td>
                        <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                            href="/plm/jsp/project/ProjectExtView5.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">CFT요청</a></td>
                        <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_1.png"></td>
                      <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
                      <td><img src="/plm/portal/images/tab_2.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                      <a href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "09500") %><%--평가관리--%></a>
                      </td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView7.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "03034") %><%--프로그램--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
<%--                   <%if(CommonUtil.isAdmin() || CommonUtil.isMember("원가_임원")) {%>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                href="/plm/jsp/project/ProjectExtView8.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">원가</a></td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                    </table></td>
                  <%} %> --%>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                            <a href="/plm/jsp/project/ProjectExtView9.jsp?oid=<%=pjtOid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">ATFT</a></td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr></table>
                  </td>
                  <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png"
                                                    class="btn_tab"><a
                                                    href="javascript:openCostHistory('<%=oid%>')"
                                                    class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09518") %><%--개발원가--%></a></td>
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
<%
	}
%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top"><!--내용-->

    <div class="clearfix b-space15">
        <div class="sub-title-02 float-l">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>
            <%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%>
                        /<%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%>
        </div>
        <div style="text-align:right" class="float-r">
<%
    if("A".equals(designType) && !"COMPLETED".equals(projectData.state)) {
%>

        <% if(CommonUtil.isAdmin() || isPM ||  (CommonUtil.isMember("자동차PMO") && projectData.teamType.equals("자동차 사업부"))
            || (CommonUtil.isMember("전자PMO") && projectData.teamType.equals("전자 사업부"))|| (CommonUtil.isMember("KETS_PMO") && projectData.teamType.equals("KETS")) )
           { 
            
            //if(latestAssSheet==null) {
        %>
            <c:if test="${PJT_VERSION eq 0 && PJT_STATE eq 'INWORK' || PJT_STATE eq 'REWORK' || ASS_OID eq null || ASS_OID eq ''}">
            <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span>
            <span class="pro-cell b-center">
              <a href="#" onclick="javascript:registAssessTarget();" class="btn_blue">
              <%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%>&nbsp;
              <%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>
              </a>
            </span><span class="pro-cell b-right"></span></span></span>
            </c:if>
        <%
            //}
        %>
            <c:if test="${PJT_STATE eq 'INWORK' || PJT_STATE eq 'REWORK'}">
            <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
                <a href="#" onclick="javascript:approvalAction('<%=oid %>');" class="btn_blue">
                <%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%>
                </a>
            </span><span class="pro-cell b-right"></span></span></span>
            </c:if>
            
<%
String pjtOidComp = (pjtOid.indexOf(":")>=0)?pjtOid.substring(pjtOid.indexOf(":")+1):pjtOid;
if(latestOid.equals(pjtOid)) { %>
            <c:if test="${PJT_STATE eq 'APPROVED' && projectState != 'PLANCHANGE'}">
            <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
                <a href="#" onClick="javascript:reviseAssessSheet();" class="btn_blue">
                <%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%>
                <%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a>
            </span><span class="pro-cell b-right"></span></span></span>
            </c:if>
<%} %>
		<% } %>
<%
    }
%>
            <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
                <a href="#" onClick="javascript:reviseAssessSheetList();" class="btn_blue">
                <%=messageService.getString("e3ps.message.ket_message", "00690") %><%--개정이력--%></a>
            </span><span class="pro-cell b-right"></span></span></span>
        </div>

    </div>
    <div class="b-space10">
        <table width="100%"  cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="13%" />
                <col width="12%" />
                <col width="13%" />
                <col width="12%" />
                <col width="13%" />
                <col width="12%" />
                <col width="13%" />
                <col width="12%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title">Rev.</td>
                    <td>${PJT_VERSION}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                    <td>${CREATE_DATE}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td colspan="3">
<%
    if(assSheetObj!=null)
    {
%>
                    <a href="javascript:viewHistory('<%=(CommonUtil.getOIDString(assSheetObj))%>')">${PJT_STATE_NAME}</a>
<%
    }else {
%>
                    ${PJT_STATE_NAME}
<%
    }
%>
                    
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="clearfix b-space10">
        <div class="float-l">
<%
    Vector vecRst = (Vector)request.getAttribute("VEC_CNT");


if(vecRst!=null) {
    HashMap hMap = new HashMap();
    for(int i=0;i<vecRst.size();i++) {
        hMap = (HashMap)vecRst.get(i);
%>
    <B><%=StringUtil.checkNull((String)hMap.get("name")) %> : <%=StringUtil.checkNull((String)hMap.get("count")) %> 건 &nbsp;&nbsp;&nbsp;</B> 
<%
    }
}
%>
        </div>

<%
	if("A".equals(designType)) {
		if(!StringUtil.isEmpty(linkedAssOid) && ("INWORK".equals(pjtStateStr) || "REWORK".equals(pjtStateStr))) {
%>

        <% if(CommonUtil.isAdmin() || isPM || (CommonUtil.isMember("자동차PMO") && projectData.teamType.equals("자동차 사업부"))
            || (CommonUtil.isMember("전자PMO") && projectData.teamType.equals("전자 사업부"))|| (CommonUtil.isMember("KETS_PMO") && projectData.teamType.equals("KETS")) )
           { 
        %>
    <div style="text-align:right" class="float-r">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
               <td align="right">
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:CreateAssessSheet.addRowAbove();" class="btn_blue">상위추가</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:CreateAssessSheet.addRowBelow();" class="btn_blue">하위추가</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:createGateTemplate();" class="btn_blue">저장</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:CreateAssessSheet.removeRow();" class="btn_blue">제거</a></span><span class="pro-cell b-right"></span></span></span>
               </td>
            </tr>
        </table>
    </div>
        <% } %>
<%
		}
    }
%>
</div>

<%
    if(assSheetObj!=null)
    {
%>

                <div class="b-space15" style="text-align:right">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="right">
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                          <div class="container-fluid">
                            <div class="row-fluid">
<%
    if("A".equals(designType)) {
        
%>
                              <div id="listCheckGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
<%
    }else {
%>
                              <div id="listCheckGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
<%
    }
%>
                            </div>
                          </div>
                        </div>
                        <!-- EJS TreeGrid Start -->
                      </td>
                    </tr>
                  </table>
              </div>
<%
    }
%>
          </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
</form>
