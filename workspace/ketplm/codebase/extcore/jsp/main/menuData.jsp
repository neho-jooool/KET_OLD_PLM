<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.Base64"%>
<%@page import="ext.ket.wfm.service.KETWorkspaceHelper"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.org.*,wt.session.SessionHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

List<Map<String, String>> retire_list = KETWorkspaceHelper.service.getMyRetireUserList("");
boolean isCSAuth = false;
//Department dept = new PeopleData((user)).department;
if( (CommonUtil.isAdmin() || CommonUtil.isMember("자동차사업부_영업") || CommonUtil.isMember("영업CS")) ){
	isCSAuth = true;
}

String kmsLinkId = "";
String kmsId = "";

Cookie[] cookies = request.getCookies();
 if(cookies != null) {
    for( int i = 0; i < cookies.length; i++ ){ 
        Cookie cookie = cookies[i];
        if(cookie.getName().equals("kms_linkID")){
           kmsLinkId =  cookie.getValue();  
        }
    } 
}

kmsLinkId = new String(Base64.decode(kmsLinkId),"UTF-8");

if(StringUtil.checkString(kmsLinkId)){
    String[] kmsLinkArr = kmsLinkId.split("[|]");
    kmsId = kmsLinkArr[0];
}

%>
<c:choose>
    <c:when test="${menuCode == 'dashboard'}">
<%if(!user.getName().startsWith("kplus2")){ %>    
[
<%if(!CommonUtil.isKETSUser()){ %>
{
    panelId : 'dashbard01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "02664") %>',//'종합현황'
    url : '/plm/ext/dashboard/overallStatus',
    leaf : true
},{
    panelId : 'dashbard02',
    text : '<%=messageService.getString("e3ps.message.ket_message", "01103") %>',//'금형제작현황',
    url : '/plm/ext/dashboard/moldMultiColumChart',
    leaf : true
},{
    panelId : 'dashbard03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09241") %>',//금형제작진행현황
    url : '/plm/ext/dashboard/moldGanttChart',
    leaf : true
},
<%}%>
{
    panelId : 'dashbard04',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09242") %>',//팀별업무현황
    url : '/plm/ext/dashboard/teamWorkTimeReort',
    leaf : true
}
,
{
    text : 'Report',
    children : [
<%-- <%if(CommonUtil.isMember("원가_임원") || "Administrator".equals(user.getName())){ %>    
    {
        panelId : 'report01',
        text : '<%=messageService.getString("e3ps.message.ket_message", "09243") %>',//프로젝트 수익율
        url : '/plm/ext/project/cost/listCost.do',
        leaf : true 
    },
<%} %> --%>
    {
        panelId : 'report01',
        text :  '<%=messageService.getString("e3ps.message.ket_message", "09540") %>',//프로젝트 주요일정
        url : '/plm/ext/dashboard/projectMainSchedule',
        leaf : true 
    },
<%if(CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO")){ %>       
    {
        panelId : 'report02',
        text : '<%=messageService.getString("e3ps.message.ket_message", "09244") %>',//프로그램 진행현황
        url : '/plm/ext/dashboard/programProcessReport',
        leaf : true 
    },{
	    panelId : 'report03',
	    text : '<%=messageService.getString("e3ps.message.ket_message", "07233") %>',//공수현황
	    url : '/plm/ext/dashboard/workTimeReport',
	    leaf : true
	},
<%} %>
    {
        panelId : 'report04',
        text :  '<%=messageService.getString("e3ps.message.ket_message", "09541") %>',//팀별 계획공수현황'
        url : '/plm/ext/dashboard/workTimeTeamReport',
        leaf : true 
    },
    {
        panelId : 'report05',
        text :  '<%=messageService.getString("e3ps.message.ket_message", "09542") %>', //기타 Report
        url : '/plm/ext/dashboard/etcReport',
        leaf : true 
    }	
	]    
}

]
<%} %>
    </c:when>
    <c:when test="${menuCode == 'workspace'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    text : '<%=messageService.getString("e3ps.message.ket_message", "00788")%>',//결재함
    expanded : true,
    children : [{
            panelId : 'workspace01',
            text : '<%=messageService.getString("e3ps.message.ket_message", "00760")%>(${listWorkItemCnt })',//결재대기함
            title : '<%=messageService.getString("e3ps.message.ket_message", "00760")%>',//결재대기함
            url : '/plm/ext/wfm/workflow/listWorkItem.do',
            leaf : true
        },{
            panelId : 'workspace02',
            text : '<%=messageService.getString("e3ps.message.ket_message", "00787")%>',//결재진행함
            url : '/plm/ext/wfm/workflow/listInProgressWorkItem.do',
            leaf : true
        },{
            panelId : 'workspace03',
            text : '<%=messageService.getString("e3ps.message.ket_message", "00777")%>',//결재완료함
            url : '/plm/ext/wfm/workflow/listCompletedWorkItem.do',
            leaf : true
        },{
            panelId : 'workspace04',
            text : '<%=messageService.getString("e3ps.message.ket_message", "02375")%>',//임시저장함
            url : '/plm/ext/wfm/workflow/listTempWorkItem.do',
            leaf : true
        },{
            panelId : 'workspace05',
            text : '<%=messageService.getString("e3ps.message.ket_message", "05159")%>',//수신함
            url : '/plm/ext/wfm/workflow/listReceiptWorkItem.do',
            leaf : true
        },{
            panelId : 'workspace06',
            text : '<%=messageService.getString("e3ps.message.ket_message", "05160")%>',//참조함
            url : '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchRefDoc',
            leaf : true
        }]
},{
    text : '<%=messageService.getString("e3ps.message.ket_message", "02435")%>',//작업공간
    expanded : true,
    children : [{
            panelId : 'workspace07',
            text : 'My Todo(${listTodoCnt })',
            title : 'My Todo',
            url : '/plm/ext/wfm/workspace/listMyTodo.do',
            leaf : true
        },
        <%-- {
        	panelId : 'sales06',
        	text : '고객대응관리 Todo (${issueTodoCnt })',
        	url : '/plm/ext/issue/issueToDoList',
        	leaf : true 
    	}, --%>
        {
            panelId : 'workspace08',
            text : 'My Task',
            url : '/plm/ext/wfm/workspace/listMyTask.do',
            leaf : true
        },{
            panelId : 'workspace09',
            text : 'My Project',
            url : '/plm/ext/wfm/workspace/listMyProject.do',
            leaf : true
        },
        {
            panelId : 'workspace10',
            text : 'My CFT요청',
            url : '/plm/servlet/e3ps/IssueServlet&key=command&value=searchMyIssue',
            leaf : true
        },{
            panelId : 'workspace11',
            text : 'My Document',
            url : '/plm/ext/wfm/workspace/listMyDocument.do',
            leaf : true
        },{
            panelId : 'workspace12',
            text : 'My Drawing',
            url : '/plm/servlet/e3ps/EDMServlet&key=command&value=openMySearch',
            leaf : true
        },{
            panelId : 'workspace13',
            text : 'My Part',
            url : '/plm/ext/wfm/workspace/listMyPart.do',
            leaf : true
        },{
            panelId : 'workspace14',
            text : 'My ECM',
            url : '/plm/jsp/ecm/reform/SearchMyEcmForm.jsp',
            leaf : true
        },
        {
            panelId : 'workspace15',
            text : '<%=messageService.getString("e3ps.message.ket_message", "09525") %>',//잔여업무현황,
            url : '',
            leaf : true
        } 
        <%for(Map<String, String>  retire : retire_list ){
        %>
        ,{
            panelId : 'retireWork',
            parameter : '<%=retire.get("RETIRE_ID") %>',
            text : '<%=retire.get("RETIRE_NAME") %>',
            url : '',
            leaf : true
        }
        <%}%>]
}
<%-- {
    panelId : 'workspace15',
    text : '<%=messageService.getString("e3ps.message.ket_message", "02236")%>',//위임설정
    url : '/plm/ptc1/calender/calenderMgmt',
    leaf : true
} --%>
]
<%} %>
</c:when>

    <c:when test="${menuCode == 'helpdesk'}">
<%if(!user.getName().startsWith("kplus2")){ %>    
[{
    panelId : 'helpdesk01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "00889") %>',//공지사항
    url : '/plm/servlet/e3ps/ManageNotifyServlet',
    leaf : true        
},

<%-- {
    panelId : 'helpdesk02',
    text : '<%=messageService.getString("e3ps.message.ket_message", "00423") %>',//Q & A
    url : '/plm/servlet/e3ps/ManageQnaBoardServlet',
    leaf : true        
}, --%>

{
    panelId : 'helpdesk03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "00238") %>',//FAQ
    url : '/plm/jsp/groupware/help/faq/SearchBoard.jsp',
    leaf : true        
},{
    panelId : 'helpdesk04',
    text : '<%=messageService.getString("e3ps.message.ket_message", "03464") %>',//PLM 사용가이드
    url : '/plm/servlet/e3ps/ManageManualBoardServlet',
    leaf : true        
},{
    panelId : 'helpdesk05',
    text : '<%=messageService.getString("e3ps.message.ket_message", "00423") %>',//Q & A
    url : '/plm/servlet/e3ps/ManageImproveBoardServlet',
    leaf : true    
}]
<%} %>
    </c:when>
    <c:when test="${menuCode == 'project'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[
<%if(!CommonUtil.isKETSUser() && !CommonUtil.isMember("SQ인증감사")){ %>
{
    panelId : 'project01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "00599") %>',//검토(검토)의뢰
    url : '/plm/jsp/dms/SearchDevRequest.jsp',
    leaf : true
},
<%}%>
<%if(!CommonUtil.isMember("SQ인증감사")){ %>
{
    panelId : 'project02',
    text : '<%=messageService.getString("e3ps.message.ket_message", "03034") %>',//프로그램
    url : '/plm/ext/project/program/listProgram.do',
    leaf : true
},
<%}%>
{
    panelId : 'project03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "03046") %>',//프로젝트
    url : '/plm/jsp/project/ProjectSearch.jsp&key=radio&value=2',
    leaf : true
}

<%if((CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO")) && !CommonUtil.isMember("SQ인증감사")){ %>
,{
    panelId : 'project06',
    text : '<%=messageService.getString("e3ps.message.ket_message", "07104") %>',//표준 WBS
    url : '/plm/servlet/e3ps/SearchProjectTemplateServlet',
    leaf : true
},{
    panelId : 'project07',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09239") %>',//Gate 평가시트 관리
    url : '/plm/ext/project/gate/listTemplateAssess.do',
    leaf : true
}
<%}%>
<%if(!CommonUtil.isMember("SQ인증감사")){ %>
,{
            panelId : 'part02',
            text : 'Issue',
            url : '/plm/ext/dqm/dqmList.do',
            leaf : true
}
,{
    panelId : 'project05',
    text : 'CFT 요청',
    url : '/plm/jsp/project/ProjectIssueTotalList.jsp',
    leaf : true
},
<%if(CommonUtil.isAdmin() || CommonUtil.isMember("국내차종관리") || CommonUtil.isMember("국외차종관리")){ %>
{
    panelId : 'project08',
    text : '<%=messageService.getString("e3ps.message.ket_message", "02749") %>',//차종관리
    url : '',
    leaf : true
},
<%} %>
{
    panelId : 'project04',
    text : '<%=messageService.getString("e3ps.message.ket_message", "02395") %>',//자동차 일정
    url : '/plm/jsp/project/ListProgram.jsp',
    leaf : true
},
{
    panelId : 'sales05',
    text : '고객/사내 요구사항',
    url : '/plm/ext/issue/issueMasterList',
    leaf : true 
},
{
    panelId : 'project10',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09527") %>',//고객투자비 회수관리
    url : '/plm/ext/invest/investMasterList',
    leaf : true 
}
<%}%>

,
<% if(isCSAuth){ %>
{
    text : '<%=messageService.getString("e3ps.message.ket_message", "09528") %>',//영업
    children : [  
    {
        panelId : 'sales01',
        text : '<%=messageService.getString("e3ps.message.ket_message", "09529") %>',//영업프로젝트'
        url : '/plm/ext/sales/project/salesProjectList.do',
        leaf : true 
    },
    {
        panelId : 'sales02',
        text : '<%=messageService.getString("e3ps.message.ket_message", "09530") %>',//CS차수관리
        url : '/plm/ext/sales/project/salesCSProjectList.do',
        leaf : true 
    },
    {
        panelId : 'sales03',
        text : '<%=messageService.getString("e3ps.message.ket_message", "09531") %>',//CS회의자료생성
        url : '',
        leaf : true 
    },
    {
        panelId : 'sales04',
        text : '<%=messageService.getString("e3ps.message.ket_message", "09532") %>',//CS회의시작
        url : '',
        leaf : true 
    },
    
    {
        panelId : 'sales05',
        text : '고객/사내 요구사항',
        url : '/plm/ext/issue/issueMasterList',
        leaf : true 
    }
    ]    
},
<%} %>
{
    
    text : '<%=messageService.getString("e3ps.message.ket_message", "09533") %>',//업무
    children : [  
    {
        panelId : 'project09',
        text : '<%=messageService.getString("e3ps.message.ket_message", "03046") %>',//'프로젝트
        url : '/plm/jsp/project/ProjectSearch.jsp&key=radio&value=4',
        leaf : true 
    }
    ]
}
]
<%} %>
    </c:when>
    <c:when test="${menuCode == 'mold'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    panelId : 'mold01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "01024") %>',//금형 프로젝트
    url : '/plm/jsp/project/ProjectSearch.jsp&key=radio&value=3',
    leaf : true
}
<%if(!CommonUtil.isMember("SQ인증감사")){ %>
,{
    panelId : 'mold02',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09240") %>',//금형 Try 일정 관리
    url : '/plm/jsp/project/trySchdule/CalendarMain.jsp',<%--/plm/jsp/project/trySchdule/TryCalendar.jsp --%>
    leaf : true
}
,
{
    panelId : 'mold03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09245") %>',//금형 Try 조건표
    url : '/plm/ext/project/trycondition/listTryCondition.do',
    leaf : true
}
<%}%>
<%if(!CommonUtil.isMember("SQ인증감사") && (!CommonUtil.isKETSUser() || CommonUtil.isMember("KETS_PMO"))){ %>
,{
    panelId : 'mold04',
    text : '<%=messageService.getString("e3ps.message.ket_message", "01068") %>',//금형 부품 관리
    url : '/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/MoldPartSearchServlet?command=openSearch',
    leaf : true
},{
    panelId : 'mold05',
    text : '<%=messageService.getString("e3ps.message.ket_message", "02089") %>',//양산금형 일정관리
    url : '/plm/jsp/ecm/SearchMoldPlan.jsp',
    leaf : true
},{
    panelId : 'mold06',
    //text : '<%=messageService.getString("e3ps.message.ket_message", "09535") %>',//구매품 프로젝트
    text : '외주개발품/구매품 개발현황',
    url : '/plm/ext/project/purchase/purchaseProjectList.do?menu=ok',
    leaf : true
}
<%}%>
]    
<%} %>
    </c:when>
    <c:when test="${menuCode == 'document'}">
[
<%if(!user.getName().startsWith("kplus2")){ %>
{
    panelId : 'document01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "00638") %>',//개발산출문서
    url : '/plm/ext/dms/listProjectDocument.do',
    leaf : true
}
<%if(!CommonUtil.isMember("SQ인증감사")){ %>
,{
    panelId : 'document02',
    text : '<%=messageService.getString("e3ps.message.ket_message", "01123") %>',//기술문서
    url : '/plm/jsp/dms/SearchTechDocument.jsp',
    leaf : true
},{
    panelId : 'document03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "03008") %>',//표준양식
    url : '/plm/jsp/dms/SearchStandardDoc.jsp',
    leaf : true
}
,{
    panelId : 'document04',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09536") %>',//해석의뢰
    url : '/plm/ext/arm/master/armList.do',
    leaf : true
},{
    panelId : 'document05',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09537") %>',//시스템 사용설명서
    url : '/plm/ext/dms/sgDocumentList.do',
    leaf : true
},{
    panelId : 'document06',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09501") %>',//PPAP자료 검색
    url : '/plm/ext/dms/pPapDocSearchView.do',
    leaf : true
}
<%}}%>
<%--{
    panelId : 'document05',
    text : '품질 표준문서',
    url : '/plm/ext/dms/qualityListDocument.do',
    leaf : true
}--%>

]    
    </c:when>
    <c:when test="${menuCode == 'drawings'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    panelId : 'drawings01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "01253") %>',//도면
    url : '/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/EDMServlet?command=openSearch', // 도면검색
    leaf : true
}
<%if(!CommonUtil.isMember("SQ인증감사")){ %>
,{
    panelId : 'drawings04',
    text : '<%=messageService.getString("e3ps.message.ket_message", "03457") %><%--배포요청서--%>',
    url : '/plm/ext/edm/drawingDistRequestSearchList.do', // 배포요청서
    leaf : true
}
<%}%>
]
<%} %> 
    </c:when>
    <c:when test="${menuCode == 'part'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    panelId : 'part01',
    text : 'Part',
    url : '/plm/ext/part/base/listPart.do',
    leaf : true
}
<%if(!CommonUtil.isKETSUser()){ %>
,{
    panelId : 'part03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09247") %>',//Sample 요청
    url : '/plm/ext/sample/sampleRequestList.do',
    leaf : true
},{
    panelId : 'part04',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09543") %>',//'원재료DB관리'
    url : '/plm/ext/material/materialList.do',
    leaf : true
},{
    panelId : 'part05',
    text : '자재양산시작일 관리',//'자재양산시작일관리'
    url : '/plm/ext/part/base/partMassPlanList.do',
    leaf : true
}
<%}%>
]
<%} %>
    </c:when>
    <c:when test="${menuCode == 'ecm'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    panelId : 'ecm01',
    text : 'ECR',
    url : '/plm/jsp/ecm/SearchEcrForm.jsp',
    leaf : true
},{
    panelId : 'ecm02',
    text : 'ECO',
    url : '/plm/jsp/ecm/SearchEcoForm.jsp',
    leaf : true
},{
    panelId : 'ecm03',
    text : 'ECN',
    url : '/plm/jsp/ecm/reform/SearchEcnForm.jsp',
    leaf : true
}]
<%} %>  
    </c:when>
    <c:when test="${menuCode == 'ews'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    panelId : 'ews01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "02809") %>',//초기유동관리
    url : '/plm/jsp/ews/SearchEarlyWarning.jsp',
    leaf : true
}
<%-- ,{
    panelId : 'ews02',
    text : '<%=messageService.getString("e3ps.message.ket_message", "03452") %>',//습득교훈관리
    url : '/plm/jsp/lesson/SearchLessonLearn.jsp',
    leaf : true
} --%>
]
<%} %>
    </c:when>
    <c:when test="${menuCode == 'admin'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    panelId : 'admin01',
    text : 'Admin',
    url : '',
    leaf : true
}
<%if("Administrator".equals(user.getName())) { %>
,
{
    panelId : 'admin02',
    text : '자재 I/F',
    url : '/plm/jsp/bom/sapInterfaceTest.jsp', // 자재 I/F 테스트
    leaf : true    
},{
    panelId : 'admin03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "02329") %>',//인터페이스 테스트
    url : '/plm/jsp/ews/interfaceTest.jsp',
    leaf : true
},{
    panelId : 'admin04',
    text : 'BOM I/F',//Migration테스트
    url : '/plm/jsp/common/loading.jsp?url=/plm/jsp/bom/MigrationTest.jsp',
    leaf : true
}
<%} %>
,{
    panelId : 'admin05',
    text : 'Sample',
    url : '/plm/ext/sample/list.do',
    leaf : true
}
<%if("Administrator".equals(user.getName())) { %>
,{
    panelId : 'admin06',
    text : '객체정보',
    url : '/plm/jsp/project/ManageMainPage.jsp',
    leaf : true
}
<%} %>
,{
    panelId : 'admin07',
    text : 'ECO 모니터링',
    url : '/plm/ext/klog/listLog.do',
    leaf : true
},{
    panelId : 'drawings05',
    text : 'Interface 이력관리',
    url : '/plm/ext/edm/plmHpIfList.do',  
    leaf : true
}
]
<%} %> 
    </c:when>
    <c:when test="${menuCode == 'home'}">
<%if(!user.getName().startsWith("kplus2")){ %>
[{
    panelId : 'home01',
    text : 'My Task',
    url : '/plm/ext/wfm/workspace/listMyTask.do',
    leaf : true
},{
    panelId : 'home02',
    text : 'My Project',
    url : '/plm/ext/wfm/workspace/listMyProject.do',
    leaf : true
},{
    panelId : 'home03',
    text : '<%=messageService.getString("e3ps.message.ket_message", "00760")%>',//결재대기함
    url : '/plm/ext/wfm/workflow/listWorkItem.do',
    leaf : true
},{
    panelId : 'home04',
    text : 'My Issue',
    url : '/plm/servlet/e3ps/IssueServlet&key=command&value=searchMyIssue',
    leaf : true
}]
<%} %>
    </c:when>
    <c:when test="${menuCode == 'cost'}">
[
<%-- {
    panelId : 'cost01',
    text : '<%=messageService.getString("e3ps.message.ket_message", "09248") %>',//원가관리 시스템
    url : '',
    leaf : true
}, --%>
{
    panelId : 'erp01',
    text : '양산정보종합조회(SAP)',
    parameter : '<%=kmsId%>',
    url : '',
    leaf : true
}
<%if("Administrator".equals(user.getName()) || CommonUtil.isMember("원가관리") || CommonUtil.isMember("원가조회")){ %>
,
{
    text : '코드관리',
    children : [  
    {
        panelId : 'costCode00',
        text : '부품Type관리',
        url : '',
        leaf : true 
    },
    {
        panelId : 'costCode01',
        text : '부품Type별 기준정보',
        url : '',
        leaf : true 
    },{
        panelId : 'costCode09',
        text : '조업도 설정',
        url : '',
        leaf : true 
    },{
        panelId : 'costAcl01',
        text : 'Task 유형별 ACL 관리',
        url : '',
        leaf : true
    },{
        panelId : 'costAcl02',
        text : '부품 유형별 ACL 관리',
        url : '',
        leaf : true
    },{
        panelId : 'costCode02',
        text : '비철 원가기준정보',//비철 원가기준정보
        url : '',
        leaf : true
    },{
        panelId : 'costCode03',
        text : '수지 원가기준정보',//수지 원가기준정보
        url : '',
        leaf : true
    },{
        panelId : 'costCode04',
        text : '환율정보',//환율정보
        url : '',
        leaf : true
    },{
        panelId : 'costCode05',
        text : '감가비 기준정보',
        url : '',
        leaf : true
    },{
        panelId : 'costCode06',
        text : '공정 관세/물류비 관리',
        url : '',
        leaf : true
    },{
        panelId : 'costCode07',
        text : '원재료 관세/물류비 관리',
        url : '',
        leaf : true
    },{
        panelId : 'costCode08',
        text : '기초코드관리',
        url : '',
        leaf : true
    }
    ]
},
{
    text : '수식관리',
    children : [  
    {
        panelId : 'costFomula01',
        text : '원가수식관리',
        url : '',
        leaf : true 
    }
    ]
},
{
    text : 'DashBoard',
    children : [  
    {
        panelId : 'costDash01',
        text : '수익률추적관리',
        url : '/plm/ext/cost/ProjectCostTrackingList',
        leaf : true 
    }
    ]
},
{
    text : 'ERP',
    children : [  
    {
        panelId : 'costErp',
        text : 'ERP인터페이스',
        url : '/plm/ext/cost/costErpInterfaceList',
        leaf : true 
    }
    ]
}
<%} %>
]

    </c:when>    
</c:choose>