<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.beans.ProjectIssueHelper"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.web.PageControl"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
    response.addHeader("Cache-Control", "max-age=1, must-revalidate");   

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

    String pagingList = PropertiesUtil.getSearchPagingSizeList();
    String pagingNameList = PropertiesUtil.getSearchPagingSizeNameList();
    String gridStyle = PropertiesUtil.getSearchGridStyle();
    String cookiesType = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth = PropertiesUtil.getSearchColMinWidth();
    String pageSize = PropertiesUtil.getSearchPagingSizeDefault();
    String oid = request.getParameter("oid");
    
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);
    String popup = StringUtil.checkNull( request.getParameter("popup") );

    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
    boolean isCS = CommonUtil.isMember("공정감사");
    boolean isProgram = CommonUtil.isMember("프로그램관리");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
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
<script type="text/javascript">
var pagingList = '<%=pagingList%>';
var pagingNameList = '<%=pagingNameList%>';
var gridStyle = '<%=gridStyle%>';
var cookiesType = '<%=cookiesType%>';
var alternateType = '<%=alternateType%>';
var maxSort = '<%=maxSort%>';
var sortIcons = '<%=sortIcons%>';
var colMinWidth = '<%=colMinWidth%>';
var pageSize = '<%=pageSize%>';
var locale = '<%=messageService.getLocale()%>';
</script>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">
    var removedProgramOids = [];
    /**
     * server paging grid  
     */
    function  createProgramGrid(){
        this.grid = TreeGrid(CommonGrid.getGridConfig({
            id : 'ProgramGrid',
            Sort : 'programNo',
            useToolbar : false,//grid toolbar hide
            SelectingSingle : '0',
            Data : {
                Url : '/plm/ext/project/program/findProgramByProject.do?sortName=*Sort0',
                Method : 'POST',
                Format : 'Form',
                Param : {
                	projectOid : '<%=oid%>'
                }
            },
            Cols : [
                {Name : 'programNo',        Width:90, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'programName',      Width:100, RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'},
                {Name : 'lastUsingBuyer',   Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'subContractor',    Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'programAdmin',     Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'carType',          Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'projectsCount',    Align : 'Center', CanSort : '0', CanEdit : '0'},
                {Name : 'checkedEvent',   Width:50, Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'},
                {Name : 'baseProgram',           Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'}
            ],Header :{
                CanDelete : '0', CanEdit : '0', Align : 'Center',
                programNo : LocaleUtil.getMessage('03104'),//'Project No'
                programName : LocaleUtil.getMessage('00395'),//'Project Name'
                lastUsingBuyer : LocaleUtil.getMessage('02847')/*'최종사용처'*/,
                subContractor : LocaleUtil.getMessage('00859')/*'고객처'*/,
                programAdmin : LocaleUtil.getMessage('07294')/*'관리자'*/,
                carType : LocaleUtil.getMessage('01760')/*'상태'*/,
                projectsCount : LocaleUtil.getMessage('03046')/*'프로젝트'*/,
                checkedEvent : LocaleUtil.getMessage('02348'),//'일정',
                baseProgram : LocaleUtil.getMessage('09250')//'기준 프로그램'
            },
            Panel : {
                Width : '21', Visible : '1',CanHide : '0'
            }
        }),'listGrid');
        
        //row click event
        Grids.OnClick = goView;
        
        Grids.OnRenderFinish = function(){
        	var G = Grids[0];
        	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		if(tRow.checkedEvent == '1'){
        			   G.SetString(tRow,'checkedEvent', LocaleUtil.getMessage('03226')+"<input type=hidden name=checkedEvent value=1>",1);//확인        			
        		}else if(tRow.checkedEvent == '0'){
        			   G.SetString(tRow,'checkedEvent',"<span class='b-small box-size' onclick='changeCheckedEvent(\""+tRow.projectLinkOid+"\",\""+tRow.oid+"\")'>"+LocaleUtil.getMessage('09215')+"<input type=hidden name=checkedEvent value=0></span>",1);
        		}
        		if(tRow.baseProgram == '1'){
        			   G.SetString(tRow,'baseProgram',"<input type='radio' name='baseProgram' value='"+tRow.projectLinkOid+"' checked onclick='changeBaseProgram(\""+tRow.projectLinkOid+ "\")'>",1);        			
        		}else if(tRow.baseProgram == '0'){
        			   G.SetString(tRow,'baseProgram',"<input type='radio' name='baseProgram' value='"+tRow.projectLinkOid+"' onclick='changeBaseProgram(\""+tRow.projectLinkOid+ "\")'>",1);
        		}
        	}
        }
    }
    
    /**
     * server paging grid  
     */
    function  createProjectGrid(){
    	this.grid = TreeGrid(CommonGrid.getGridConfig({
            id : 'ProjectLinkGrid',
            Data : {
                Url : '/plm/ext/project/program/findProjectByProject.do',
                Param : {
                	projectOid : '<%=oid%>',
                }
            },
            Sort : 'projectNo',
            useToolbar : false,//grid toolbar hide
            SelectingSingle : '0',
            Cols : [
                {Name : 'projectNo', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'projectName', Width:50, RelWidth : 50, Align : 'Left', CanSort : '1', CanEdit : '0'},
                {Name : 'partNo', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'pm', Width:50, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'customer', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'}
            ],Header :{
                CanDelete : '0', Align : 'Center',
                projectNo : LocaleUtil.getMessage('03104'),//'Project No'
                projectName : LocaleUtil.getMessage('00395'),//'Project Name'
                partNo : 'PartNo',//완료일
                pm : 'PM',
                customer : LocaleUtil.getMessage('00859')/*'고객처'*/
            }
        }),'listGrid2');
    	
    	//row click event
        Grids.OnClick = goView;
    }
    
    function goView(grid,row,col,x,y){
        if(row.oid && col == 'programNo'){
            getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid='+row.oid,'ProgramPopup',800,620);
        }else if(row.oid && col == 'programName'){
            getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid='+row.oid,'ProgramPopup',800,620);
        }else if(row.oid && col == 'projectsCount'){
            getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid='+row.oid+'&selectTab=PROJECT','ProgramPopup',800,620);
        }else if(row.oid && col == 'projectNo'){
            openProject(row.projectNo);
        }
    }
    
    //프로그램 추가
    function addProgram(rows){
    	var G = Grids[0]; 
        //중복체크
        var hasDuplicated = false;
        for(var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        	for(var i=0;i<rows.length;i++){
        		if(rows[i].oid == tRow.oid){
        			alert(LocaleUtil.getMessage('09251'));//'이미 등록된 프로그램이 존재합니다.'
        			hasDuplicated = true;
        			return;
        		}
        	}
        }
        if(hasDuplicated){
        	return;
        }
        for(var i=0;i<rows.length;i++){
        	var addedRow = G.AddRow(null, null, 7);
            addedRow.NoColor = 2;
        	G.SetString(addedRow,'oid',rows[i].oid,1);
        	G.SetString(addedRow,'programNo',rows[i].programNo,1);
        	G.SetString(addedRow,'programName',rows[i].carType + ' '+ rows[i].subContractor + ' ' +rows[i].programName,1);
        	G.SetString(addedRow,'lastUsingBuyer',rows[i].lastUsingBuyer,1);
        	G.SetString(addedRow,'subContractor',rows[i].subContractor,1);
        	G.SetString(addedRow,'programAdmin',rows[i].programAdmin,1);
        	G.SetString(addedRow,'carType',rows[i].carType,1);
        	G.SetString(addedRow,'projectsCount',rows[i].projectsCount,1);
        	G.SetString(addedRow,'checkedEvent',rows[i].checkedEvent,1);
        	G.SetString(addedRow,'baseProgram',rows[i].baseProgram,1);
            if(addedRow.checkedEvent == '1'){
                   G.SetString(addedRow,'checkedEvent',LocaleUtil.getMessage('03226')+"<input type=hidden name=checkedEvent value=1>",1);//확인                    
            }else{
                   G.SetString(addedRow,'checkedEvent',"<span class='b-small box-size'>"+LocaleUtil.getMessage('09215')+"<input type=hidden name=checkedEvent value=0></span>",1);
            }
            if(addedRow.baseProgram == '1'){
                   G.SetString(addedRow,'baseProgram',"<input type='radio' name='baseProgram' value='"+addedRow.oid+"' checked>",1);                  
            }else{
                   G.SetString(addedRow,'baseProgram',"<input type='radio' name='baseProgram' value='"+addedRow.oid+"' >",1);
            }
        	G.RefreshRow(addedRow);
        }
        saveProjectLinkByProject();
    }
    
    //삭제
    function removeRow(){
    	if(!confirm(LocaleUtil.getMessage('01697'))){//'삭제 하시겠습니까?'
            return ;
        }
    	
        var G = Grids[0];
        if(G.RowCount<=0) {
            G.AddRow(null, null, 7);
            return;
        }
        var rows = G.GetSelRows(); //선택한 Row
        var nextRow = G.GetNext(rows[0]); //선택한 Row + 다음 Row
        if(rows=='' || rows.length == 0) {
            alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
            return;
        }
        for(var i=0;i<rows.length;i++){
            //삭제한 oid 배열에 추가
           	removedProgramOids[removedProgramOids.length] = rows[i].oid;
            G.RemoveRow(rows[i]);    //선택한 Row 삭제        	
        }
        saveProjectLinkByProject();
    }
    
    //수정
    function saveProjectLinkByProject(){
    	var G = Grids[0];
    	var programOids = '';
    	var checkedEvents = '';
    	var basePrograms = '';
    	var removedProgram = '';
        var i = 0;
        for (var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        	programOids += '&programOids['+i+']='+ tRow.oid;
        	basePrograms += '&basePrograms['+i+']='+($(':radio[name=baseProgram]:checked').val() == tRow.oid?'1':'0');
        	checkedEvents += '&checkedEvents['+i+']='+$('[name=checkedEvent]')[i].value;
            i++;
        }
        for(i=0;i<removedProgramOids.length;i++){
        	removedProgram += '&removedProgramOids['+i+']='+removedProgramOids[i];
        }
        
        //프로그래스바 보이기
        showProcessing();
        $.ajax({
            url: '/plm/ext/project/program/saveProjectLinkByProject.do',
            type: 'post',
            data: $('[name=ProgramListForm]').serialize()+programOids+basePrograms+checkedEvents+removedProgram,
            success: function (data) {
            	if(data.success == 'OK'){
            		 alert('저장되었습니다.');
            		 Grids[0].Reload();
            		 Grids[1].Reload();
            		 removedProgramOids = [];
            	}else{
            		alert(data.msg);
            	}
            	//프로그레스바 숨기기
                hideProcessing();
            },
            error : function(){
            	//프로그레스바 숨기기
                hideProcessing();
            }
        });
    }
    
    //일정확인 버튼 클릭
    function changeCheckedEvent(oid,programOid){
    	if(!confirm(LocaleUtil.getMessage('09252'))){//'일정 확인 하시겠습니까?'
    		return;
    	}
    	//프로그래스바 보이기
        showProcessing();
    	$.ajax({
            url: '/plm/ext/project/program/changeCheckedEvent.do',
            type: 'post',
            data: {
            	projectOid : $('[name=projectOid]').val(),
            	projectLinkOid : oid
            },
            success: function (data) {
                if(data.success == 'OK'){
                    getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid='+programOid,'ProgramPopup',800,620);
                    alert(LocaleUtil.getMessage('01496'));//'변경 되었습니다.'
                    Grids[0].Reload();
                }else{
                    alert(data.msg);
                }
                //프로그레스바 숨기기
                hideProcessing();
            },
            error : function(){
                //프로그레스바 숨기기
                hideProcessing();
            }
        });
    }
    
    //기준 프로그램 라디오 클릭
    function changeBaseProgram(oid){
        if(!confirm(LocaleUtil.getMessage('09253'))){//'기준 프로그램으로 설정 하시겠습니까?'
            return;
        }
        //프로그래스바 보이기
        showProcessing();
        $.ajax({
            url: '/plm/ext/project/program/changeBaseProgram.do',
            type: 'post',
            data: {
                projectOid : $('[name=projectOid]').val(),
                projectLinkOid : oid
            },
            success: function (data) {
                if(data.success == 'OK'){
                    alert(LocaleUtil.getMessage('09254'));//'기준 프로그램으로 설정 되었습니다.'
                    Grids[0].Reload();
                }else{
                    alert(data.msg);
                }
                //프로그레스바 숨기기
                hideProcessing();
            },
            error : function(){
                //프로그레스바 숨기기
                hideProcessing();
            }
        });
    }
    
    function openCostHistory(oid){
    	var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
        //openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
        
        getOpenWindow2(url, oid, 'full', 10);
    }
    
    $(document).ready(function(){
    	createProgramGrid();
    	createProjectGrid();
    });
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form name="ProgramListForm">
    <input type="hidden" name="projectOid" value="<%=oid%>"> 
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551")%><%--제품 프로젝트 상세정보--%></td>
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
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isCS) {
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640")%><%--비용--%></a></td>
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
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>&popup=<%=popup%>"
                                                        class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01381")%><%--목표--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09500") %><%--평가관리--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03034")%><%--프로그램--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png""></td>
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
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                            <td height="10" background="/plm/portal/images/box_14.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <!--내용-->
                                <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                    <tr>
                                        <td valign="top">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "09255") %><!-- 관련 프로그램 --></td>
                                                    <td align="right">
                                                        <%
                                                        if(!auth.isCompleted && (isProgram || isAdmin)){
                                                        %>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10">&nbsp;</td>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                                    <a href="#" onClick="javascript:SearchUtil.selectProgram('addProgram','<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                <td width="5">&nbsp;</td>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                                    <a href="#" onClick="javascript:removeRow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                        <%
                                                        }
                                                        %>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td>
                                                        <!-- EJS TreeGrid Start -->
                                                        <div class="content-main">
                                                            <div class="container-fluid">
                                                                <div class="row-fluid">
                                                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 300px"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- EJS TreeGrid End -->
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                    <tr>
                                        <td valign="top">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00920") %><!-- 관련 프로젝트 --></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td>
                                                        <!-- EJS TreeGrid Start -->
                                                        <div class="content-main">
                                                            <div class="container-fluid">
                                                                <div class="row-fluid">
                                                                    <div id="listGrid2" style="WIDTH: 100%; HEIGHT: 300px"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- EJS TreeGrid End -->
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
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
    </form>
</body>
</html>
