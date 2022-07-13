<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.DateUtil,
                java.text.SimpleDateFormat" %>

<!--
 * [PLM System 1차개선]
 * 파일명 : ProjectScheduleGridJs.jsp
 * 설명 : Project 일정 관리 Grid JavaScript JSP
 * 작성자 : BoLee
 * 작성일자 : 2013. 6. 19.
-->

<%
    String todayDateStr = DateUtil.getCurrentDateString(new SimpleDateFormat ( "yyyy-MM-dd" ));
%>

<script type="text/javascript">
//<![CDATA[

var delFlag = "D";
var searchIcon          = "/plm/portal/images/icon_5.png";
var deleteIcon          = "/plm/portal/images/icon_delete.gif";
var oemPlanBeforeIcon   = "/plm/portal/images/icon_try1.gif";
var oemPlanAfterIcon    = "/plm/portal/images/icon_try3.gif";
var undoFlag = false;

// Run Box에 Text 렌더링 이전에 호출 - 자동차 일정 표시
Grids.OnGetGanttRunText = function(G, row, col, run, index, text, width) {

    var oemPlanDate = "";
    var oemPlanIcon = "";
    var displayValue = "";

    if ( run[index][4] != undefined && run[index][4].indexOf("]") != -1 && run[index][4].length > 2 ) {

        oemPlanDate = run[index][4].substring(run[index][4].indexOf("]") + 2);
    }

    // 자동차 Event 일정이 오늘 날짜 이전일 경우 회색 아이콘 표시, 오늘 이후일 경우 파랑색 아이콘 표시
    if ( oemPlanDate < "<%= todayDateStr %>" ) {
        oemPlanIcon = oemPlanBeforeIcon;
    }
    else {
        oemPlanIcon = oemPlanAfterIcon;
    }

    // Zoom이 day일 경우 '[Event명] 일정' 표시, week일 경우 'Event명' 표시
    if ( G.Toolbar.Zoom == "day" ) {
        displayValue = run[index][4];
    }
    else if ( G.Toolbar.Zoom == "week" ) {
        displayValue = run[index][4].substr(1, run[index][4].indexOf("]") - 1);
    }

    return "<div style='width: 100%; overflow: hidden;'>"
         + "    <div style='width: 100%; overflow: hidden;'>"
         + "        <div style='float: left;'><img src='" + oemPlanIcon + "'></div>"
         + "        <div style='float: left; padding-left: 3px; font-size: 12px; font-weight: bold;'>" + displayValue + "</div>"
         + "    </div>"
         + "</div>";
}

// Task 계획 Main Bar Chart 렌더링 이전에 호출 - 완료된 Task 색상(#747474) 표시
Grids.OnGetGanttHtml = function(G, row, col, width, comp, crit) {

    if ( row.TaskState == 5 ) {

        return "<div style='width: 100%; overflow: hidden;'>"
             + "    <div style='width: 100%; overflow: hidden;'>"
             + "        <div style='background: #747474; height: 9px;'></div>"
             + "    </div>"
             + "</div>";
    }
}

// Task 실적 Flow Chart 렌더링 이전에 호출 - 완료된 Task 색상(#D4D4D4) 표시
Grids.OnGetGanttFlowHtml = function(G, row, col, index, width, comp, crit) {

    if ( row.TaskState == 5 ) {

        return "<div style='width: 100%; overflow: hidden;'>"
             + "    <div style='width: 100%; overflow: hidden;'>"
             + "        <div style='background: #D4D4D4; height: 3px;'></div>"
             + "    </div>"
             + "</div>";
    }
}

// 행 추가 시 호출 - 행 추가 가능 여부 체크
Grids.OnCanRowAdd = function(G, parent, next) {

    // Project(0 레벨) 선택 후 행 추가 방지
    if ( parent && parent.tagName == "I" ) {

        if ( undoFlag == false ) {
            G.StartUndo();      // 행 추가 Undo 범위 시작
            undoFlag = true;
        }

        return true;
    }
    else {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00413") %><%--Project와 동일한 레벨에는 Task를 추가할 수 없습니다\n다른 Task 선택 후 추가해 주십시오--%>");
    }
}

// 행 추가 후 호출
Grids.OnRowAdded = function(G, row) {

    var parent;
    var rowLevel = row.Level;
    var taskSeq = 0;

    // 부모 Task
    parent = row.parentNode;

    if ( rowLevel > 1 ) {

        // 부모 Task 정의
        G.ChangeDef(parent, "Summary");
    }

    G.SetValue(row, "TaskOid", "", 1);              // Task OID
    G.SetValue(row, "TaskNameButton", "", 1);       // Task 상세정보 Link 아이콘
    G.SetValue(row, "TaskLevel", rowLevel, 1);      // Task Level
    G.SetValue(row, "TaskComplete", "0", 1);        // 진척률
    G.SetValue(row, "TaskPreferComp", "0", 1);      // 진행률
    G.SetValue(row, "MilestoneType", "0", 1);       // Milestone 여부
    G.SetValue(row, "OptionType", "0", 1);          // 필수 여부
    G.SetValue(row, "TaskType", "일반", 1);         // Task 종류
    G.SetValue(row, "TaskTypeCategory", "", 1);         // Task 종류
    G.SetValue(row, "DRValue", "", 1);              // DR값
    G.SetValue(row, "DRValueCondition", "", 1);              // 조건부 승인 DR값

    var oid = G.GetFirst().TaskOid;

    // Task 책임자 수정(Project 참여자 목록 선택) 화면 Link
    var taskMasterSearchIconLink = "javascript:openSelectMemberPopup('" + oid + "', Row, 'Master');";
    // Task 책임자 삭제 처리
    var taskMasterDeleteIconLink = "javascript:deleteTaskMember(Row, 'Master');";

    // Task 참여자 수정(Project 전체 인원 목록 선택) 화면 Link
    var taskMemberSearchIconLink = "javascript:openSelectMemberPopup('" + oid + "', Row, 'Member');";
    // Task 참여자 삭제 처리
    var taskMemberDeleteIconLink = "javascript:deleteTaskMember(Row, 'Member');";

    // Task 책임자 수정 모드 설정

    G.SetValue(row, "TaskMasterIcon", searchIcon, 1);
    G.SetValue(row, "TaskMasterIconAlign", "Right", 1);
    G.SetValue(row, "TaskMasterOnClickSideIcon", taskMasterSearchIconLink, 1);
    G.SetValue(row, "TaskMasterButton", deleteIcon, 1);
    G.SetValue(row, "TaskMasterOnClickSideButton", taskMasterDeleteIconLink, 1);

    // Task 참여자 수정 모드 설정

    G.SetValue(row, "TaskMemberIcon", searchIcon, 1);
    G.SetValue(row, "TaskMemberIconAlign", "Right", 1);
    G.SetValue(row, "TaskMemberOnClickSideIcon", taskMemberSearchIconLink, 1);
    G.SetValue(row, "TaskMemberButton", deleteIcon, 1);
    G.SetValue(row, "TaskMemberOnClickSideButton", taskMemberDeleteIconLink, 1);

    // 동일 Level Task Seq 설정
    for ( var r = parent.firstChild; r; r = r.nextSibling ) {

        if ( r.DeleteFlag != delFlag ) {        // 삭제된 Task가 아닐 경우 Seq 설정

            taskSeq++;

            G.SetValue(r, "TaskSeq", taskSeq, 1);       // Task Seq
        }
    }

    // 부모 Task 설정
    if ( row.TaskLevel > 1 ) {

        G.SetValue(row, "ParentHierarchy", parent.ParentHierarchy + "/" + parent.id, 1);
        G.SetValue(row, "ParentTaskOid", parent.TaskOid, 1);
    }
    else {
        G.SetValue(row, "ParentHierarchy", 0, 1);
        G.SetValue(row, "ParentTaskOid", "", 1);
    }

    // Task 정의
    if ( G.HasChildren(row) == 0 ) {

        G.ChangeDef(row, "Task");
    }
    else {
        G.ChangeDef(row, "Summary");
    }

    // 부모 Task가 Debug Task일 경우 Task명 Enumeration Type으로 구성
    if ( parent.DebugN == 1 ) {

        G.SetValue(row, "DebugSub", "1", 1);
        G.SetValue(row, "Ncha", parent.Ncha, 1);
        G.SetAttribute(row, "TaskName", "Type", "Enum", 1);
        debugSubTaskAjax(G, row);
    }

    // 하위 Task 정보 설정
    setChildrenDataAfterRowAdded(G, row);

    if ( undoFlag == true ) {
        G.EndUndo();            // 행 추가 Undo 범위 종료
        undoFlag = false;
    }
}

// 행 복사 붙여넣기(Ctrl+V) 후 호출
Grids.OnPasteRowFinish = function(G, row, cols, values, added) {

    var parent;
    var rowLevel = row.Level;

    // 부모 Task 정의 설정
    if ( rowLevel > 1 ) {

        parent = row.parentNode;
        G.ChangeDef(parent, "Summary");
    }

    G.SetValue(row, "TaskOid", "", 1);              // Task OID
    G.SetValue(row, "TaskNameButton", "", 1);       // Task 상세정보 Link 아이콘
    G.SetValue(row, "TaskLevel", rowLevel, 1);      // Task Level
    G.SetValue(row, "TaskComplete", "0", 1);        // 진척률
    G.SetValue(row, "TaskPreferComp", "0", 1);      // 진행률
    G.SetValue(row, "OptionType", "0", 1);          // 필수 여부
    G.SetValue(row, "DRValue", "", 1);              // DR값
    G.SetValue(row, "DRValueCondition", "", 1);              // 조건부 승인 DR값
    
    // Task 편집 모드 설정
    G.SetValue(row, "TaskNameCanEdit", "1", 1);
    G.SetValue(row, "PlanStartDateCanEdit", "1", 1);
    G.SetValue(row, "PlanEndDateCanEdit", "1", 1);
    G.SetValue(row, "PlanManHourCanEdit", "1", 1);
    G.SetValue(row, "PersonRoleCanEdit", "1", 1);
    G.SetValue(row, "MilestoneTypeCanEdit", "1", 1);
    G.SetValue(row, "TaskTypeCanEdit", "1", 1);
    G.SetValue(row, "DRValueCanEdit", "1", 1);
    G.SetValue(row, "DRValueConditionCanEdit", "1", 1);

    // 부모 Task 설정
    if ( rowLevel > 1 ) {

        G.SetValue(row, "ParentHierarchy", parent.ParentHierarchy + "/" + parent.id, 1);
        G.SetValue(row, "ParentTaskOid", parent.TaskOid, 1);

        // 부모 Task
        if (G.HasChildren(parent) == 1) {

            G.SetValue(parent, "TaskAncestors", "", 1);
            G.SetValue(parent, "TaskDescendants", "", 1);
        }
    }
    else {
        G.SetValue(row, "ParentHierarchy", 0, 1);
        G.SetValue(row, "ParentTaskOid", "", 1);
    }

    // Task 정의 설정
    if (G.HasChildren(row) == 0) {

        G.ChangeDef(row, "Task");
    }
    else {
        G.ChangeDef(row, "Summary");
    }

    // 하위 Task 정보 설정
    setChildrenDataAfterRowPasted(G, row);
}

// 행 이동 시 호출 - 행 Drop 가능 여부 체크 ([type] 0 – cannot drop, 1 – above torow, 2 – to the end of children of torow, 3 – below torow)
Grids.OnCanDrop = function(G, row, togrid, torow, type, copy) {

    // Project(0레벨)와 동일한 레벨로 행 Drop 방지
    // 단계(1레벨), 필수 Task, Debug Task, Debug Sub Task는 동일 레벨에서만 이동 가능
    if ( torow.Level == 0
            || ((row.Level == 1 || row.OptionType == "1" || row.DebugN == "1" || row.DebugSub == "1") && (row.parentNode.id != torow.parentNode.id || type == 2)) ) {

        return true;
    }
}

// 행 이동 후 호출
Grids.OnRowMove = function(G, row, oldparent, oldnext) {

    var parent;
    var rowLevel = 1;
    var taskSeq = 0;

    // 부모 Task 정의 설정

    parent = row.parentNode;

    if (parent.id != undefined) {

        rowLevel = parent.TaskLevel + 1;

        if ( parent.TaskLevel > 0 ) {   // 부모가 Root(Project)가 아닐 경우에만 부모 Task 정의 설정
            G.ChangeDef(parent, "Summary");
        }
    }
    else {
        G.SetValue(row, "Moved", 0, 1);
        return;
    }

    G.SetValue(row, "TaskLevel", rowLevel, 1);

    // 동일 Level Task Seq 설정
    for ( var r = parent.firstChild; r; r = r.nextSibling ) {

        if ( r.DeleteFlag != delFlag ) {        // 삭제된 Task가 아닐 경우 Seq 설정

            taskSeq++;

            G.SetValue(r, "TaskSeq", taskSeq, 1);       // Task Seq
        }
    }

    // 부모 Task 정보 설정
    if (rowLevel > 1) {

        G.SetValue(row, "ParentHierarchy", parent.ParentHierarchy + "/" + parent.id, 1);
        G.SetValue(row, "ParentTaskOid", parent.TaskOid, 1);

        // 부모 Task
        if (G.HasChildren(parent) == 1) {

            G.SetValue(parent, "TaskAncestors", "", 1);
            G.SetValue(parent, "TaskDescendants", "", 1);
        }
    }
    else {
        G.SetValue(row, "ParentHierarchy", 0, 1);
        G.SetValue(row, "ParentTaskOid", "", 1);
    }

    // Task 정의 설정
    if (G.HasChildren(row) == 0) {

        G.ChangeDef(row, "Task");
    }
    else {
        G.ChangeDef(row, "Summary");
        G.SetValue(row, "TaskAncestors", "", 1);
        G.SetValue(row, "TaskDescendants", "", 1);
    }

    // 이동하기 이전 부모 Task 정의 설정 - 이전 부모가 Root(Project)가 아닐 경우에만 설정
    if ( oldparent.TaskLevel > 0 ) {

        if (G.HasChildren(oldparent) == 0) {

            G.ChangeDef(oldparent, "Task");
        }
        else {
            G.ChangeDef(oldparent, "Summary");

            taskSeq = 0;

            // 이동하기 이전 부모 하위 Task Seq 설정
            for ( var r = oldparent.firstChild; r; r = r.nextSibling ) {

                if ( r.DeleteFlag != delFlag ) {        // 삭제된 Task가 아닐 경우 Seq 설정

                    taskSeq++;

                    G.SetValue(r, "TaskSeq", taskSeq, 1);       // Task Seq
                }
            }
        }
    }

    // 하위 Task 정보 설정
    setChildrenDataAfterRowMoved(G, row);
}

//행 삭제 시 하위 Task에 원가유형이 있는지 체크 (Recursive)
window.checkChildrenDataBeforeRowDeleted = function(G, row) {

    for ( var r = row.firstChild; r; r = r.nextSibling ) {

        var taskType = G.GetString(r, 'TaskType');
        
         if(taskType == '원가'){
        	 alert("하위 Task 중 원가유형의 Task가 존재합니다.\n\r먼저 해당 Task를 삭제하십시오."); 
        	return false;
        }

        if ( G.HasChildren(r) ) {
        	checkChildrenDataBeforeRowDeleted(G, r);
        }
        
    }
    return true;
    
}

Grids.OnCanRowDelete = function(grid, row, type){
	
	if(type == 1){ 
		return checkChildrenDataBeforeRowDeleted(grid,row);
	}
	
}

// 행 삭제 시 호출
Grids.OnRowDelete = function(G, row, type) {
	
    if ( undoFlag == false ) {
        G.StartUndo();          // 행 삭제 Undo 범위 시작
        undoFlag = true;
    }

    var id = row.id;
    var parent = row.parentNode;
    var taskSeq = 0;

    // 1. 행 삭제 아이콘 클릭 시 DeleteFlag 값 설정
    G.SetValue(row, "DeleteFlag", delFlag, 1);

    // 2. 동일 Level Task Seq 설정
    for ( var r = parent.firstChild; r; r = r.nextSibling ) {

        if ( r.DeleteFlag != delFlag ) {        // 삭제된 Task가 아닐 경우 Seq 설정

            taskSeq++;

            G.SetValue(r, "TaskSeq", taskSeq, 1);       // Task Seq
        }
    }

    // 3. 삭제한 행의 부모 Task 정의 설정
    if ( taskSeq > 0 ) {      // 하위 Task가 존재하는 경우

        if (parent.parentNode.id != undefined) {
            G.ChangeDef(parent, "Summary");     // 부모 Task에 부모 Task가 존재하는 경우 'Summary'로 설정
        }
        else {
            G.ChangeDef(parent, "Root");        // 부모 Task에 부모 Task가 존재하지 않는 경우 'Root'로 설정
        }
    }
    else {                    // 하위 Task가 존재하지 않는 경우 'Task'로 설정
        G.ChangeDef(parent, "Task");
    }

    // 4. 하위 Task 삭제 처리 - 하위 Task DeleteFlag 값 설정
    setChildrenDataAfterRowDeleted(G, row);

    for ( var r = row.firstChild; r; r = r.nextSibling ) {

        G.SetValue(r, "DeleteFlag", delFlag, 1);
    }

    if ( undoFlag == true ) {
        G.EndUndo();            // 행 삭제 Undo 범위 종료
        undoFlag = false;
    }
}

// 값 변경 후 호출
Grids.OnAfterValueChanged = function(G, row, col, val) {

    if ( G.id == "ChangeProjectScheduleGrid" && col == "PersonRole" ) {

        // Project 일정 변경 화면에서 Role 변경 시 Task 책임자 설정
        taskMemberAjax(G, row, val);
    }
    else if ( col == "PlanStartDate" || col == "PlanEndDate" ) {

        // 선후행 관계 변경(SS, FS)
        changeDependencyLink(G, row, row.PlanStartDate, row.PlanEndDate, true);
    }
}

// Gantt Chart Object 변경 시 호출
Grids.OnGanttChange = function(G, row, col, item, newtype, new2type, oldtype, old2type, action) {

    var descendantRow;
    var idx = 0;

    // 최하위 Task가 아니거나 완료된 Task일 경우 Main Bar 편집 불가
    if ( item == "Main" && ((row.Def != null && row.Def.Name != "Task") || row.TaskState == "5") ) {
        return true;
    }

    // 최하위 Task가 아닐 경우 선후행 관계 지정 불가

    if ( item == "Dependency" && newtype != null && newtype != "" ) {

        if ( oldtype != null && oldtype != "" ) {
            idx = newtype.lastIndexOf(";") + 1;
        }

        descendantRow = G.GetRowById( newtype.substring(idx) );
    }

    if ( item == "Dependency" && ((row.Def != null && row.Def.Name != "Task") || (descendantRow != null && descendantRow.Def != null && descendantRow.Def.Name != "Task")) ) {
        return true;
    }

    if ( undoFlag == false ) {
        G.StartUndo();          // Gantt Chart Object 변경 Undo 범위 시작
        undoFlag = true;
    }
}

// Gantt Chart Object 변경 후 호출
Grids.OnGanttChanged = function(G, row, col, item, newtype, new2type, oldtype, old2type, action) {

    var descendantRow;
    var idx = 0;

    // 후행 Task 계획시작일이 선행 Task 계획완료일보다 이른 선후행 관계 지정 시 SS(Start - Start)로 설정

    if ( item == "Dependency" && newtype != null && newtype != "" ) {

        if ( oldtype != null && oldtype != "" ) {
            idx = newtype.lastIndexOf(";") + 1;
        }

        descendantRow = G.GetRowById( newtype.substring(idx) );
    }

    if ( item == "Dependency" && descendantRow != null && row.PlanEndDate != "" && descendantRow.PlanStartDate != "" && descendantRow.PlanStartDate <= row.PlanEndDate ) {

            G.SetValue(row, "TaskDescendants", newtype + "ss", 1);
            G.SetValue(descendantRow, "TaskAncestors", descendantRow.TaskAncestors + "ss", 1);
    }

    if ( undoFlag == true ) {
        G.EndUndo();          // Gantt Chart Object 변경 Undo 범위 종료
        undoFlag = false;
    }
}

// Gantt Chart Object Drag 시작 시 호출
Grids.OnStartDragGantt = function(G, row, col, name, start, end, dir, XY, keyprefix, clientX, clientY) {

    if ( undoFlag == false ) {
        G.StartUndo();          // Gantt Chart Object Drag Undo 범위 시작 (Undo 범위 종료는 Gantt Chart Object 변경 Undo 범위 종료)
        undoFlag = true;
    }
}

// Gantt Chart Object Drag 후 호출
Grids.OnEndDragGantt = function(G, row, col, name, start, end, oldstart, oldend, dir, XY, keyprefix, clientX, clientY) {

    // 선후행 관계 변경(SS, FS)
    changeDependencyLink(G, row, start, end - 24*60*60*1000, false);
}

// 저장 버튼 클릭 시 호출
Grids.OnSave = function(G, row, autoupdate) {

    var taskName = "";
    var taskName2 = "";
    var taskDR6;
    var ancestorsList;
    var ancestors;
    var ancestorRow;
    var lastRow;
    var dns = location.href;
    
    if(dns.search("ProductTemplateProject") == -1){//wbs 편집이 아닐때만
    	var isMold  = "";
    	try{
    		isMold = document.form01.isMold.value;	
    	}catch(e){
    		isMold = "";
    	}
	    
	    
	    if(isMold == "MOLD"){
		    var moldPlanEndDate = DateToString(G.GetFirst().PlanEndDate, "yyyyMMdd");//금형 프로젝트 계획완료일자
		    var productPlanEndDate = document.form01.pjtPlanEndDate.value;//제품 프로젝트 계획완료일자
		    /* alert(productPlanEndDate);
		    if(moldPlanEndDate == '' || productPlanEndDate == ''){
		    	alert("계획완료일자를 확인 중 오류!");
	            setTimeout( function() { G.Focus(G.GetFirst(), "PlanEndDate", null, true); }, 10);
	            return true;
		    } */
		    
/* 		    if(productPlanEndDate != '' && productPlanEndDate!=null && !checkPeriod(productPlanEndDate, moldPlanEndDate)){ 2017.03.16 연구기획팀 요청으로 주석처리함
		    	alert("제품프로젝트의 Gate4 계획완료일 또는 DR6 계획완료일\n보다 일정이 지연될 수 없습니다.");
		    	setTimeout( function() { G.Focus(G.GetFirst(), "PlanEndDate", null, true); }, 10);
		    	return true;
		    } */
	    }
	    
	    var CostBomTaskCnt = 0;  
	    
/*         for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
        	
        	var taskType = G.GetString(r, 'TaskType');
            var taskTypeCategory = r.TaskTypeCategory;
            var taskName = G.GetString(r, 'TaskName');
            var taskState = G.GetString(r, 'TaskState');
            
            if(taskType != '일반' && taskType != 'Try' && taskType != '' && taskType != '디버깅' && taskName != '제품합격' && taskName != '제품합격(종합)' && taskName != 'Full-Tool 공정점검(M)' && taskName != 'Full-Tool 공정점검(조립)' && taskName != 'Full-Tool 공정점검(P)' ){
            	if(taskTypeCategory == '' && G.GetString(r, 'TaskTypeCategory') == ''){
                    alert('유형이 지정되지 않은 Task가 있습니다.');
                    //G.GetString(tRow, 'TaskState')
                    return true;
                }
            }
            
            if(taskTypeCategory == 'COST001'){
            	CostBomTaskCnt ++;
            }
        } */
        
        var CostBomTaskCnt = 0;
        
        for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
        	
        	if(r.TaskLevel == 0){
        		continue;
        	}
            
            var taskType = G.GetString(r, 'TaskType');
            var taskTypeCategory = G.GetString(r, 'TaskTypeCategory').replace('-','');
            var taskName = G.GetString(r, 'TaskName');
            var taskState = G.GetString(r, 'TaskState');
            var mainScheduleCode = G.GetString(r, 'MainScheduleCode');
            var taskState = G.GetString('TaskState');
            
            if( taskState != '5' && ( (taskType == 'DR' && mainScheduleCode.indexOf('DR') != -1) || taskType == 'Gate' || taskType == '원가' ) && taskTypeCategory == '' ){
            	alert('유형이 지정되지 않은 Task가 있습니다. [ '+taskName + ' ]');
                return true;
            }
            
            if(taskTypeCategory == 'COST001'){
                CostBomTaskCnt ++;
            }
        }
        
        
        if(CostBomTaskCnt > 1){
        	alert('[ 개발검토BOM ] Task는 프로젝트에 중복으로 존재할 수 없습니다.');
        	return true;
        }
        
        var rows = G.Rows;
        var rowKeys = Object.keys(rows);
        var checkRow = true;
        
         //전체 ROWS 체크
        F1 : for(var i = 0; i < rowKeys.length; i++){
            
            var aId = rowKeys[i];
            var aRow = rows[aId];
            
            if("Data" == aRow.Kind){
                
            	
                
                if(aRow.DeleteFlag != delFlag){//삭제되지 않은 행에 대해 필수입력 값 체크

                    for(var j = 0; j < rowKeys.length; j++){
                        
                        var bId = rowKeys[j];
                        var bRow = rows[bId];
                        
                        
                        
                        if("Data" == bRow.Kind && bRow.DeleteFlag != delFlag && aId != bId){//삭제되지 않은 행에 대해 부품Type,생산처 정보 중복 체크
                            
                            aTemp = aRow.TaskTypeCategory;
                            
                            bTemp = bRow.TaskTypeCategory;
                            
                            if(aTemp == '-' || bTemp == '-' ){
                            	continue;
                            }
                            
                            
                            if(aRow.TaskType == 'COST' || aRow.TaskType == '원가'){
                            	var b_TaskState = bRow.TaskState;
                                var a_TaskState = aRow.TaskState;
                                if(aTemp == bTemp && !(a_TaskState == 5 || b_TaskState == 5)){
                                    //dupRows.aRow = aRow;
                                    //dupRows.bRow = bRow;
                                    checkRow = false;
                                    break F1;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if(!checkRow){
        	alert('완료되지 않은 원가산출Task 목록 중 유형이 중복되는 행이 있습니다.');
        	return true;
        }
	    
	    
    }
    for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {

        if ( r.DeleteFlag != delFlag ) {        // 삭제된 Task가 아닐 경우 Validation Check

            lastRow = r;

            // Task명 필수 입력 체크
            if ( r.TaskName == null || (r.TaskName + "").replace(/^\s*|\s*$/g,"") == "" ) {

                alert("<%=messageService.getString("e3ps.message.ket_message", "00499") %><%--Task명을 입력해 주십시오--%>");
                setTimeout( function() { G.Focus(r, "TaskName", null, true); }, 10);
                return true;
            }

            if ( G.id == "ChangeProjectScheduleGrid" ) {    // Project 일정 변경 화면에서 계획시작일, 계획완료일 필수 입력

                // 계획시작일 필수 입력 체크
                if ( r.PlanStartDate == null || r.PlanStartDate == "" ) {

                    alert("<%=messageService.getString("e3ps.message.ket_message", "00818") %><%--계획시작일을 입력해 주십시오--%>");
                    setTimeout( function() { G.Focus(r, "PlanStartDate", null, true); }, 10);
                    return true;
                }

                // 계획완료일 필수 입력 체크
                if ( r.PlanEndDate == null || r.PlanEndDate == "" ) {

                    alert("<%=messageService.getString("e3ps.message.ket_message", "00821") %><%--계획완료일을 입력해 주십시오--%>");
                    setTimeout( function() { G.Focus(r, "PlanEndDate", null, true); }, 10);
                    return true;
                }
            }
            else if ( G.id == "ChangeWBSScheduleGrid" ) {   // WBS 일정 변경 화면에서 기간 필수 입력

                if ( r.PlanDuration == null || r.PlanDuration == 0 ) {

                    alert('<%=messageService.getString("e3ps.message.ket_message", "01113") %><%--기간을 입력해 주십시오--%>');
                    setTimeout( function() { G.Focus(r, "PlanDuration", null, true); }, 10);
                    return true;
                }
            }

            // 하위 Task에 중복 Task명 존재 여부 체크 - 삭제된 Task가 아닐 경우 체크
            for ( var cr = r.firstChild; cr; cr = cr.nextSibling ) {

                if ( cr.DeleteFlag != delFlag ) {

                    taskName = (cr.TaskName + "").replace(/^\s*|\s*$/g,"");

                    for ( var cr2 = cr.nextSibling; cr2; cr2 = cr2.nextSibling ) {

                        if ( cr2.DeleteFlag != delFlag ) {

                            taskName2 = (cr2.TaskName + "").replace(/^\s*|\s*$/g,"");

                            if ( taskName == taskName2 ) {

                                alert("[" + taskName2 + "] <%=messageService.getString("e3ps.message.ket_message", "00003") %><%--Task명이 동일한 Task가 존재합니다--%>");
                                setTimeout( function() { G.Focus(cr2, "TaskName", null, true); }, 10);
                                return true;
                            }
                        }
                    }
                }
            }

            // DR6 Task 존재 여부 체크
            if ( r.TaskName == "DR6" ) {
                taskDR6 = r;
            }

            // Task보다 계획시작일이 늦은 선행 Task 존재 여부 체크
            if ( r.TaskAncestors != null && r.TaskAncestors != undefined && r.TaskAncestors != "" ) {

                ancestorsList = r.TaskAncestors + "";
                ancestors = ancestorsList.split(";");

                for ( var x = 0; x < ancestors.length; x++ ) {

                    if ( ancestors[x] != "" ) {

                        ancestorRow = G.GetRowById(ancestors[x].replace("ss", ""));

                        if ( r.PlanStartDate != "" && ancestorRow.PlanStartDate != "" && r.PlanStartDate < ancestorRow.PlanStartDate ) {

                            alert("[" + r.TaskName + "(" + DateToString(r.PlanStartDate, "yyyy-MM-dd") + ")] Task는 선행 Task[" + ancestorRow.TaskName + "(" + DateToString(ancestorRow.PlanStartDate, "yyyy-MM-dd") + ")]보다 일찍 시작할 수 없습니다.");
                            setTimeout( function() { G.Focus(r, "PlanStartDate", null, true); }, 10);
                            return true;
                        }
                    }
                }
            }
        }
    }

    if ( taskDR6 != null ) {

        if ( taskDR6 != lastRow ) {

            alert("<%=messageService.getString("e3ps.message.ket_message", "00049") %><%--DR6는 Project의 마지막 Task이어야 합니다.--%>");
            setTimeout( function() { G.Focus(taskDR6, "TaskName", null, true); }, 10);
            return true;
        }
        else if ( taskDR6.PlanEndDate != G.GetFirst().PlanEndDate ) {

            alert("DR6 계획완료일은 Project 계획완료일(" + DateToString(Get(G.GetFirst(), "PlanEndDate"), "yyyy-MM-dd") + ")과 같아야 합니다.");
            setTimeout( function() { G.Focus(taskDR6, "TaskName", null, true); }, 10);
            return true;
        }
    }

    showProcessing();   // 진행 상태바 표시 (processing.html)
    //disabledAllBtn();   // 버튼 비활성화
}

// 저장 완료 후 호출
Grids.OnAfterSave = function(G, result, autoupdate) {

    if ( result >= 0 ) {        // result >= 0 : success

        callAfterSaveProjectSchedule();
    }
    else {                      // result < 0 : error
        alert("<%=messageService.getString("e3ps.message.ket_message", "02461") %><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
    }
    hideProcessing();
}

// Reload 버튼 클릭 시 호출 - Project 일정 조회
Grids.OnReload = function(G) {

    doSubmit();
    return true;
}

// 전체 접기 버튼 클릭 시 호출 - 1레벨(단계)까지만 표시
Grids.OnClickButtonCollapseAll = function(G) {

    for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {

        if ( r == G.GetFirst() ) {
            G.Expand(r);
        }
        else if ( r.Level == 1 ) {
            G.Collapse(r);
        }
    }

    return true;
}

Grids.OnEndEdit = function(grid, row, col, save, val, raw){
	
	if(col != "New" && col != "Modify" && col != "Common" && col != "Mdraw" && col != "HW" && col != "SW" && col != "M" && col != "P" && col != "Buy" && col != "System"){
		
	}else{
		
		var parent =  row.parentNode;
        var parentValue = grid.GetValue(parent, col);
        
        grid.SetValue(row,col,val,1);
        
		while(parent.Level > 0){
			
			var childValueArray = new Array();
		
			var index = 0;
			for ( var r = parent.firstChild; r; r = r.nextSibling ) {
				   var childValue = grid.GetValue(r,col);
				   childValueArray[index] = childValue;
				   index++;
		    } 
			  
			var resultValue = Math.max.apply(this,childValueArray)
		    
			if(parent.Level > 0){
				  grid.SetValue(parent,col,resultValue,1);
			}
			
			parent = parent.parentNode;
		}
	}
   
};

function checkPeriod(startdate, enddate){
    
    var startCal = new Date(startdate.substring(0,4), Number(startdate.substring(4,6)), Number(startdate.substring(6,8)));

    var endCal = new Date(enddate.substring(0,4), Number(enddate.substring(4,6)), Number(enddate.substring(6,8)));

    var interval = startCal - endCal;
    
    var value = interval/(24 * 3600 * 1000);

    if(value < 0){
        return false;
    }else{
        return true;
    }
}

// 행 추가 후 하위 Task 정보 설정 (Recursive)
function setChildrenDataAfterRowAdded(G, row) {

    var taskSeq = 0;

    for ( var r = row.firstChild; r; r = r.nextSibling ) {

        taskSeq++;

        G.SetValue(r, "TaskOid", "", 1);            // Task OID
        G.SetValue(r, "TaskNameButton", "", 1);     // Task 상세정보 Link 아이콘
        G.SetValue(r, "TaskLevel", r.Level, 1);     // Task Level
        G.SetValue(r, "TaskComplete", "0", 1);      // 진척률
        G.SetValue(r, "TaskPreferComp", "0", 1);    // 진행률
        G.SetValue(r, "MilestoneType", "0", 1);     // Milestone 여부
        G.SetValue(r, "OptionType", "0", 1);        // 필수 여부
        G.SetValue(r, "TaskType", "일반", 1);       // Task 종류
        G.SetValue(r, "TaskTypeCategory", "", 1);         // Task 종류
        G.SetValue(r, "TaskSeq", taskSeq, 1);       // Task Seq
        G.SetValue(r, "DRValue", "", 1);            // DR값
        G.SetValue(r, "DRValueCondition", "", 1);              // 조건부 승인 DR값

        // Parent Task
        G.SetValue(r, "ParentHierarchy", r.parentNode.ParentHierarchy + "/" + r.parentNode.id, 1);
        G.SetValue(r, "ParentTaskOid", r.parentNode.TaskOid, 1);

        // Task 정의
        if ( G.HasChildren(r) ) {

            G.ChangeDef(r, "Summary");

            // 하위 Task가 존재할 경우 Recursive Call
            setChildrenDataAfterRowAdded(G, r);
        }
        else {
            G.ChangeDef(r, "Task");
        }
    }
}

// 행 복사 붙여넣기(Ctrl+V) 후 하위 Task 정보 설정 (Recursive)
function setChildrenDataAfterRowPasted(G, row) {

    for (var r = row.firstChild; r; r = r.nextSibling) {

        G.SetValue(r, "TaskOid", "", 1);            // Task OID
        G.SetValue(r, "TaskNameButton", "", 1);     // Task 상세정보 Link 아이콘
        G.SetValue(r, "TaskLevel", r.Level, 1);     // Task Level
        G.SetValue(r, "TaskComplete", "0", 1);      // 진척률
        G.SetValue(r, "TaskPreferComp", "0", 1);    // 진행률
        G.SetValue(r, "ParentHierarchy", r.parentNode.ParentHierarchy + "/" + r.parentNode.id, 1);
        G.SetValue(r, "ParentTaskOid", r.parentNode.TaskOid, 1);
        G.SetValue(r, "DRValue", "", 1);            // DR값
        G.SetValue(r, "DRValueCondition", "", 1);              // 조건부 승인 DR값

        if ( G.HasChildren(r) ) {

            setChildrenDataAfterRowPasted(G, r);
        }
    }
}

// 행 이동 후 하위 Task 정보 설정 (Recursive)
function setChildrenDataAfterRowMoved(G, row) {

    for (var r = row.firstChild; r; r = r.nextSibling) {

        G.SetValue(r, "ParentHierarchy", r.parentNode.ParentHierarchy + "/" + r.parentNode.id, 1);
        G.SetValue(r, "ParentTaskOid", r.parentNode.TaskOid, 1);
        G.SetValue(r, "TaskLevel", (r.parentNode.TaskLevel + 1), 1);
        G.SetValue(r, "TaskAncestors", "", 1);
        G.SetValue(r, "TaskDescendants", "", 1);

        if ( G.HasChildren(r) ) {

            setChildrenDataAfterRowMoved(G, r);
        }
    }
}

// 행 삭제 후 하위 Task 삭제 처리 (Recursive)
function setChildrenDataAfterRowDeleted(G, row) {

    for ( var r = row.firstChild; r; r = r.nextSibling ) {

        G.SetValue(r, "DeleteFlag", delFlag, 1);

        if ( G.HasChildren(r) ) {

            setChildrenDataAfterRowDeleted(G, r);
        }
    }
}

// 선후행 관계 변경(SS, FS)
function changeDependencyLink(G, row, start, end, endFlag) {

    var ancestorsList;
    var ancestors;
    var ancestorRow;
    var ancestorRowId = "";
    var taskDescendantsList = "";
    var descendantsList;
    var descendants;
    var descendantRow;
    var descendantRowId = "";
    var taskAncestorsList = "";
    var newValue = "";
    var compareFlag = false;

    // 날짜 변경 시 선행 Task 선후행 관계 유형(SS, FS) 변경
    if ( row.TaskAncestors != null && row.TaskAncestors != undefined && row.TaskAncestors != "" ) {

        ancestorsList = row.TaskAncestors + "";
        ancestors = ancestorsList.split(";");

        for ( var x = 0; x < ancestors.length; x++ ) {

            if ( ancestors[x] != "" ) {

                ancestorRowId = ancestors[x].replace("ss", "");

                ancestorRow = G.GetRowById(ancestorRowId);

                if ( ancestorRow != null && ancestorRow.TaskDescendants != null && ancestorRow.TaskDescendants != undefined ) {
                    taskDescendantsList = ancestorRow.TaskDescendants + "";
                }

                compareFlag = start != "" && start <= ancestorRow.PlanEndDate;

                if ( ancestorRow.PlanEndDate != "" && compareFlag ) {           // 계획시작일이 선행 Task 계획완료일보다 이른 경우

                    if ( newValue != "" ) {
                        newValue = newValue + ";";
                    }
                    newValue = newValue + ancestorRowId + "ss";

                    // 선행 Task의 후행 Task List에서 선후행 관계 SS 지정
                    if ( taskDescendantsList.indexOf(row.id) > -1 && taskDescendantsList.indexOf(row.id + "ss") == -1 ) {
                        G.SetValue(ancestorRow, "TaskDescendants", taskDescendantsList.replace(row.id, row.id + "ss"), 1);
                    }
                }
                else if ( ancestorRow.PlanEndDate != "" && !compareFlag ) {     // 계획시작일이 선행 Task 계획완료일보다 늦은 경우

                    if ( newValue != "" ) {
                        newValue = newValue + ";";
                    }
                    newValue = newValue + ancestorRowId;

                    // 선행 Task의 후행 Task List에서 선후행 관계 FS 지정
                    if ( taskDescendantsList.indexOf(row.id + "ss") > -1 ) {
                        G.SetValue(ancestorRow, "TaskDescendants", taskDescendantsList.replace(row.id + "ss", row.id), 1);
                    }
                }
            }
        }

        G.SetValue(row, "TaskAncestors", newValue, 1);
    }

    // 날짜 변경 시 후행 Task 선후행 관계 유형(SS, FS) 변경
    if ( row.TaskDescendants != null && row.TaskDescendants != undefined && row.TaskDescendants != "" ) {

        descendantsList = row.TaskDescendants + "";
        descendants = descendantsList.split(";");
        newValue = "";

        for ( var x = 0; x < descendants.length; x++ ) {

            if ( descendants[x] != "" ) {

                descendantRowId = descendants[x].replace("ss", "");

                descendantRow = G.GetRowById(descendantRowId);

                if ( descendantRow != null && descendantRow.TaskAncestors != null && descendantRow.TaskAncestors != undefined ) {
                    taskAncestorsList = descendantRow.TaskAncestors + "";
                }

                if ( endFlag == true ) {        // TreeGrid에서 날짜 변경
                    compareFlag = descendantRow.PlanStartDate != "" && end >= descendantRow.PlanStartDate;
                }
                else {                          // Gantt Chart에서 Chart Object 변경
                    compareFlag = descendantRow.PlanStartDate != "" && end >= descendantRow.PlanStartDate;
                }

                if ( end != "" && compareFlag ) {       // 계획완료일이 후행 Task 계획시작일보다 늦은 경우

                    if ( newValue != "" ) {
                        newValue = newValue + ";";
                    }
                    newValue = newValue + descendantRowId + "ss";

                    // 후행 Task의 선행 Task List에서 선후행 관계 SS 지정
                    if ( taskAncestorsList.indexOf(row.id) > -1 && taskAncestorsList.indexOf(row.id + "ss") == -1 ) {
                        G.SetValue(descendantRow, "TaskAncestors", taskAncestorsList.replace(row.id, row.id + "ss"), 1);
                    }
                }
                else if ( end != "" && !compareFlag ) {   // 계획완료일이 후행 Task 계획시작일보다 이른 경우

                    if ( newValue != "" ) {
                        newValue = newValue + ";";
                    }
                    newValue = newValue + descendantRowId;

                    // 후행 Task의 선행 Task List에서 선후행 관계 FS 지정
                    if ( taskAncestorsList.indexOf(row.id + "ss") > -1 ) {
                        G.SetValue(descendantRow, "TaskAncestors", taskAncestorsList.replace(row.id + "ss", row.id), 1);
                    }
                }
            }
        }

        G.SetValue(row, "TaskDescendants", newValue, 1);
    }
}

//]]>
</script>
