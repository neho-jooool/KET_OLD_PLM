<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ page import="e3ps.project.*"%>
<!--
 * [PLM System 1차개선]
 * 파일명 : ProjectScheduleJs.jsp
 * 설명 : Project 일정 관리 JavaScript JSP
 * 작성자 : BoLee
 * 작성일자 : 2013. 6. 19.
-->

<script type="text/javascript">
//<![CDATA[

// Project 일정 변경 화면에서 [완료] 버튼 클릭 시 값 세팅 (일정 저장 후 초기화)
var callMode = "";
var callOid = "";
var callHistoryNoteType = 999;

// Task 책임자 또는 참여자 검색 아이콘 클릭한 Row (검색 아이콘 클릭 시 값 세팅)
var memberRow;

// Task 책임자 또는 참여자 검색 아이콘 클릭한 Task 참여 유형 (검색 아이콘 클릭 시 값 세팅)
var memberType = "";

// Project 상세정보 조회 화면에서 [일정Chart] 버튼 클릭 시 Project 일정 조회 팝업 오픈
// 검토 Project 상세정보 [제품] 탭, 제품 Project 상세정보 [일정] 탭, 금형 Project 상세정보 [제품] 탭
function openViewProjectSchedule(oid) {

    var screenWidth = screen.availWidth;
    var screenHeight = screen.availHeight;
    var url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&cmd=view&width=" + screenWidth + "&height=" + screenHeight;
    openOtherName(url, "ViewProjectSchedule", screenWidth, screenHeight, "status=no,scrollbars=no,resizable=yes");
}

// Project 일정 변경 화면에서 [Project 인원] 버튼 클릭 시 Project 인원 팝업 오픈
function openProjectMemberPopup(oid) {

    var url = "/plm/jsp/project/schedule/ViewProjectMemberPop.jsp?oid=" + oid;
    openOtherName(url, "process", "840", "670", "status=no,scrollbars=yes,resizable=no");
}

// Project 일정 변경 화면에서 [완료] 버튼 클릭 시 처리
function completeChangeSchedule(oid, historyNoteType, pjtNo) {
	
	if(!validateCheck(pjtNo)){
        return;
    }

    var G = Grids[0];

    if ( G != null ) {
    	var count = 0;
    	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
    		 var leaf = G.HasChildren(tRow);
    		 if(leaf == 0){
    		    var master = G.GetString( tRow, 'TaskMaster');
    		    if(master == ""){
    		    	  count++;
    		    }
    		 }
    	}
    	
    	if(count > 0){
    		alert("<%=messageService.getString("e3ps.message.ket_message", "07323") %><%--책임자가 지정되지 않은 TASK가 있습니다.\n최하위 Level TASK에 책임자 지정 후 완료 바랍니다--%>");
    		return;
    		//G.Save();   // Project 일정 저장      
    	}else{
    		callMode = "completeChangeSchedule";
            callOid = oid;
            callHistoryNoteType = historyNoteType;
            G.Save();   // Project 일정 저장    		
    	}
        
    }
    else {
        alert("<%=messageService.getString("e3ps.message.ket_message", "02461") %><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
        return;
    }
}

// Project 상세정보 화면에서 [일정변경 완료] 버튼 클릭 시 처리
function completeChangePjtInfoSchedule(oid, historyNoteType) {

    if ( historyNoteType == 0 ) {   // 최초 버전(일정변경구분이 최초작성(historyNoteType = 0))일 경우 결재요청 팝업 오픈

        openRequestApprovalPopup(oid, "ViewProject");
    }
    else {                          // 최초 버전이 아닐 경우 일정변경사유 입력 팝업 오픈

        var popup = "";
        if ( document.forms[0].popup != null && document.forms[0].popup != undefined && document.forms[0].popup.value != undefined && document.forms[0].popup.value != "undefined" )
            popup = document.forms[0].popup.value;
        var url = "/plm/jsp/project/HistoryChange.jsp?oid=" + oid + "&from=ViewProject&popup=" + popup;
        window.open(url, "HistoryChange", "status=1, menu=no, width=770, height=660, scrollbars=yes, resizable=no");
    }
}

/* function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
  } */

// Project 일정 저장 후 로직 (ProjectScheduleGridJs.jsp Grids.OnAfterSave에서 호출)
function callAfterSaveProjectSchedule() {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %>');
    if ( callMode == "completeChangeSchedule" ) {   // Project 일정 변경 화면에서 [완료] 버튼 클릭한 경우
        if ( callHistoryNoteType == 0 ) {               // 최초 버전(일정변경구분이 최초작성(historyNoteType = 0))일 경우 결재요청 팝업 오픈
            if ( callOid.indexOf("MoldProject") > 0 ) {
                checkMoldProjectScheduleAjax(callOid, "ChangeSchedule");   // 금형 Project 일정 Validation Check
            }
            else {
                openRequestApprovalPopup(callOid, "ChangeSchedule");
            }
        }
        else {                                      // 최초 버전이 아닐 경우 일정변경사유 입력 팝업 오픈
            var popup = "";
            if ( document.forms[0].popup != null && document.forms[0].popup != undefined && document.forms[0].popup.value != undefined && document.forms[0].popup.value != "undefined" )
                popup = document.forms[0].popup.value;
            var url = "/plm/jsp/project/HistoryChange.jsp?oid=" + callOid + "&from=ChangeSchedule&popup=" + popup;
            setTimeout( function() { window.open(url, "HistoryChange", "status=1, menu=no, width=770, height=660, scrollbars=yes, resizable=no"); }, 20);
        }
    }else if(callType != null && callType != ""){
    	var taskUrl = "/plm/servlet/e3ps/ProjectScheduleServlet?oid="+callOid;
        var wbsContentUrl = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+callOid+"&type="+callType;
       
        //search(taskUrl,wbsContentUrl);
        //openOtherName(encodeURI(wbsContentUrl+"&taskUrl="+taskUrl), "StandardWBSSearch", 1335, 768, "status=no,scrollbars=yes,resizable=no");
    }
    
   //저장후 그리드 재로딩
    var G = Grids[0];
    G.Reload();
    
   
    callMode = "";
    callOid = "";
    callHistoryNoteType = 999;
    //self.close();
}
<%-- 
function callAfterSaveProjectSchedule() {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %>저장되었습니다');
    //hideProcessing();
    showProcessing();
    var taskUrl = "/plm/servlet/e3ps/ProjectScheduleServlet?oid="+callOid;
    var wbsContentUrl = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+callOid+"&type="+callType;
  
    //searchPopup(taskUrl, wbsContentUrl);
    if(callType){
        search(taskUrl,wbsContentUrl);
    }else{
    	var target;
        var viewUrl = "";

        if ( opener.parent.parent.document.frames['contName'] != null ) {
            target = eval("opener.parent.parent.document.frames['contName']");
        }
        else {
            target = eval("opener.parent.parent.body");
        }

        if ( callOid.indexOf("ReviewProject") > 0 ) {
            viewUrl = "/plm/jsp/project/ReviewProjectView.jsp?oid=" + callOid;
        }
        else if ( callOid.indexOf("ProductProject") > 0 ) {
            viewUrl = "/plm/jsp/project/ProjectView.jsp?oid=" + callOid;
        }
        else if ( callOid.indexOf("MoldProject") > 0 ) {
            viewUrl = "/plm/jsp/project/MoldProjectView.jsp?oid=" + callOid;
        }
        else if ( callOid.indexOf("TemplateProject") > 0 ) {
            viewUrl = "/plm/jsp/project/template/TemplateProjectView.jsp?oid=" + callOid;
        }
        
        // Project 상세정보 조회 화면 재로딩
        target.document.location.href = viewUrl;
    
        // Project 일정 조회 (저장된 데이터로 재로딩하기 위함)
        doSubmit();
    
        if ( callMode == "completeChangeSchedule" ) {   // Project 일정 변경 화면에서 [완료] 버튼 클릭한 경우
    
            if ( callHistoryNoteType == 0 ) {               // 최초 버전(일정변경구분이 최초작성(historyNoteType = 0))일 경우 결재요청 팝업 오픈
    
                if ( callOid.indexOf("MoldProject") > 0 ) {
                    checkMoldProjectScheduleAjax(callOid, "ChangeSchedule");   // 금형 Project 일정 Validation Check
                }
                else {
                    openRequestApprovalPopup(callOid, "ChangeSchedule");
                }
            }
            else {                                      // 최초 버전이 아닐 경우 일정변경사유 입력 팝업 오픈
    
                var popup = "";
                if ( document.forms[0].popup != null && document.forms[0].popup != undefined && document.forms[0].popup.value != undefined && document.forms[0].popup.value != "undefined" )
                    popup = document.forms[0].popup.value;
                var url = "/plm/jsp/project/HistoryChange.jsp?oid=" + callOid + "&from=ChangeSchedule&popup=" + popup;
                setTimeout( function() { window.open(url, "HistoryChange", "status=1, menu=no, width=770, height=660, scrollbars=yes, resizable=no"); }, 20);
            }
        }
    
        callMode = "";
        callOid = "";
        callHistoryNoteType = 999;
    }
}
 --%>
// 금형 Project 일정 Validation Check Ajax - 금형 Project일 경우 Project 계획완료일 체크(제품 Project 계획완료일 내에서 일정 수립)
function checkMoldProjectScheduleAjax(oid, mode) {

    $.ajax( {
        url : "/plm/servlet/e3ps/MoldProjectScheduleCheckAjax",
        type : "POST",
        data : {oid:oid},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.resultData, function() {
                if ( this.result != null && this.result == "T" ) {
                    alert(this.message.replace("\\n", "\n").replace("\\n", "\n"));
                    return;
                }
                else {
                    openRequestApprovalPopup(oid, mode);
                }
            });
        }
    });
}

var isCost13 = false;
var isCost15 = false;
var isCost16 = false;

function validateCheck(type){
	
	isCost13 = false;
    isCost15 = false;
    isCost16 = false;
    
	 var G = Grids[0];
	    var pjtNo = type;
	    
	    var gateTypeCheck = ("E" == pjtNo.substring(0,1) || "4" == pjtNo.substring(0,1)); //전자사업부는 Gate형식을 DR타스크에서 활용하므로 체크하지 않는다
	    
	    var gateObj = {//객체 변수명을 . 이나, -를 쓸수없기 때문에 gate4.1 -> gate41 이런식으로 표시함
	            gate1 : 1,
	            gatep2 : 2,
	            gate2 : 3,
	            gatep3 : 4,
	            gate3 : 5,
	            gatep41 : 6,
	            gatep42 : 7,
	            gate4 : 8,
	            gate5 : 9,
	            gate6 : 10
	    };
	    
	    var checkRow = true;
	    var gateSortCheckCnt = 0;
	    var gateTypeCheckName = ""; 
	    var costCheckRow = true;
	    
	    for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
	        if(tRow.DeleteFlag != delFlag){
	            
	            
	            if(tRow.TaskType == 'Gate'){
	                var tRowTaskType = String(tRow.TaskTypeCategory);
	                tRowTaskType = String(tRowTaskType).replace('.', '');
	                tRowTaskType = String(tRowTaskType).replace('-', '');
	            
	                if(gateSortCheckCnt > 0){
	                    if(gateSortCheckCnt > gateObj["gate"+tRowTaskType]){
	                        checkRow = false;
	                    }
	                }
	            
	                gateSortCheckCnt = gateObj["gate"+tRowTaskType];
	            }else{
	                var taskName = String(tRow.TaskName).toUpperCase();
	                if(taskName.indexOf('GATE') != -1){
	                    gateTypeCheckName = tRow.TaskName;
	                }
	                
	            }
	            
	        }
	    }
	    
	    if(!checkRow){
	        alert("Gate순서가 맞지 않습니다.");
	        return false;
	    }
	    
	    if(!gateTypeCheck && gateTypeCheckName != ''){
	        alert("["+gateTypeCheckName + "] 의 TaskType이 Gate가 아닙니다.");
	        return false;
	    }
	 
	     var rows = G.Rows;
	     var rowKeys = Object.keys(rows);
	     
	     //var gateSortCheckCnt = 0;
	      //전체 ROWS 체크
	     F1 : for(var i = 0; i < rowKeys.length; i++){
	         
	         var aId = rowKeys[i];
	         var aRow = rows[aId];
	         
	         if("Data" == aRow.Kind){
	             
	             if(aRow.DeleteFlag != delFlag){//삭제되지 않은 행에 대해 gate순서, 중복 유무 체크
	                 
	                 
	/*               if(aRow.TaskType == 'Gate'){
	                     var aRowTaskType = String(aRow.TaskTypeCategory);
	                     aRowTaskType = String(aRowTaskType).replace('.', '');
	                     aRowTaskType = String(aRowTaskType).replace('-', '');
	                     
	                     if(gateSortCheckCnt > 0){
	                         if(gateSortCheckCnt > gateObj["gate"+aRowTaskType]){
	                             checkRow = false;
	                             break F1;
	                         }
	                     }
	                     
	                     gateSortCheckCnt = gateObj["gate"+aRowTaskType];
	                 } */
	                 
	                 
	                 if(aRow.TaskType == 'COST' && (aRow.TaskTypeCategory == 'COST013' || aRow.TaskTypeCategory == 'COST015' || aRow.TaskTypeCategory == 'COST016') ){
	                     
	                     costCheckRow = costTypeCheck(aRow.parentNode);
	                     if(!costCheckRow){
	                         break;
	                     }
	                 }
	                 
	                 for(var j = 0; j < rowKeys.length; j++){
	                     
	                     var bId = rowKeys[j];
	                     var bRow = rows[bId];

	                     if("Data" == bRow.Kind && bRow.DeleteFlag != delFlag && aId != bId){
	                         
	                         if(aRow.TaskType == 'Gate' && bRow.TaskType == 'Gate' ){
	                             
	                         }else{
	                             continue;
	                         }
	                         
	                         aTemp = aRow.TaskTypeCategory;
	                         bTemp = bRow.TaskTypeCategory;
	                         
	                         if(aRow.TaskType == 'Gate'){
	                             
	                             if(aTemp === bTemp && bRow.TaskType === 'Gate'){//중복체크
	                                 checkRow = false;
	                                 break F1;
	                             }
	                             
	/*                               if(aRow.TaskSeq > bRow.TaskSeq && bRow.TaskType == 'Gate'){//순서체크
	                                 var aRowTaskType = String(aRow.TaskTypeCategory);
	                                 aRowTaskType = String(aRowTaskType).replace('.', '');
	                                 aRowTaskType = String(aRowTaskType).replace('-', '');
	                                 var aRowGateSort = gateObj["gate"+aRowTaskType];
	                                 
	                                 var bRowTaskType = String(bRow.TaskTypeCategory);
	                                 bRowTaskType = String(bRowTaskType).replace('.', '');
	                                 bRowTaskType = String(bRowTaskType).replace('-', '');
	                                 var bRowGateSort = gateObj["gate"+bRowTaskType];
	                                 
	                                 if(aRowGateSort > bRowGateSort){
	                                     checkRow = false;
	                                     break F1;
	                                 }
	                                 
	                             } */
	                         }
	                     }
	                 }
	             }
	         }
	     }
	 
	     if(!checkRow){
	         alert("Gate유형이 중복되었습니다.");
	         return false;
	     }
	     
	     if(!costCheckRow){
	         alert("Task종류가 [원가] 인 Task가 있을 경우\r\nTask 유형이 [원가산출 요청], [원가산출], [보고서 등록] 인 Task가 모두 존재해야합니다.");
	         return false;
	     }
	     
	     return true;
}

// Project 일정 변경 화면, WBS 변경 화면에서 [저장] 버튼 클릭 시 Grid 저장 (Grid Toolbar 저장 아이콘 기능과 동일)
function saveGrid(oid, type) {
	if(!validateCheck(type)){
		return;
	}
	var G = Grids[0];
    if ( G != null ) {

    	var count = 0;
        for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
             var leaf = G.HasChildren(tRow);
             if(leaf == 0){
                var master = G.GetString( tRow, 'PersonRole');
                if(master == ""){
                      count++;
                }
             }
        }
        
        if(count > 0){
            alert("Role이 지정되지 않은 TASK가 있습니다.\n최하위 Level TASK에 Role 지정 후 완료 바랍니다");
            return;
            //G.Save();   // Project 일정 저장      
        
        }else{
    	
        callOid = oid;
        
        callType = type;

        G.Save();
        }
    }
}

function costTypeCheck(row){
	
	var G = Grids[0];
	
	for ( var r = row.firstChild; r; r = r.nextSibling ) {
		
		if ( r.DeleteFlag != delFlag ) {
			
			if(G.HasChildren(r)){
				costTypeCheck(r);
			}
			
			var taskCategory = r.TaskTypeCategory;
	        
	        if(taskCategory == 'COST013'){
	        	isCost13 = true;
	        }
	        if(taskCategory == 'COST015'){
	        	isCost15 = true;
            }
	        if(taskCategory == 'COST016'){
	        	isCost16 = true;
            }

		}
	}
	
	return isCost13 && isCost15 && isCost16;
}


function reload(){
	var G = Grids[0];
	G.Reload();
}

// Project 일정 변경 화면에서 [일정변경 취소] 버튼 클릭 시 Project 삭제
function cancelChangeSchedule() {

    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03292") %><%--경고!!\n일정 변경을 취소하시겠습니까?\nProject 상태가 [진행중] 상태로 변경됩니다.--%>") ) {
        if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03291") %><%--경고!!\n일정 변경 사항 및 일정 변경 중에 변경된 프로파일 정보가 삭제되며 복구할 수 없습니다.--%>") ) {

            document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
            document.forms[0].method = "post";
            document.forms[0].command.value = "delete";
            document.forms[0].submit();
        }
    }
}

// 일정변경사유 입력 팝업 화면에서 [결재요청] 버튼 클릭한 경우 일정변경사유 저장 후 결재요청 팝업 오픈, 2013-06-24, BoLee
function openRequestApprovalPopup(pboOid, from) {

    var popup = "";
    if ( document.forms[0].popup != null && document.forms[0].popup != undefined && document.forms[0].popup.value != undefined && document.forms[0].popup.value != "undefined" )
        popup = document.forms[0].popup.value;
    var url = "/plm/jsp/wfm/RequestApproval.jsp?pboOid=" + pboOid + "&mode=" + from;// + "&popup=" + popup;
    //openOtherName(url, "approval", "740", "500", "status=no,scrollbars=yes,resizable=no");
    var topener = window.open(url, "approval", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
    topener.focus();
}

// 일정변경사유 입력 팝업 화면에서 [완료] 버튼 클릭한 경우 일정변경사유 저장 후 일정 변경 완료 처리, 2013-07-05, BoLee
function completeChangeProjectSchedule(from) {

    showProcessing();   // 진행 상태바 표시 (processing.html)
    disabledAllBtn();   // 버튼 비활성화

    document.forms[0].action = "/plm/servlet/e3ps/ProjectScheduleServlet?from=" + from;
    document.forms[0].method = "post";
    document.forms[0].cmd.value = "complete";
    document.forms[0].submit();
}

// Project 일정 조회/변경 화면에서 Version 링크 클릭 시 일정변경이력 팝업 오픈
function openProjectChangeHistoryPopup(oid) {

    var url = "/plm/jsp/project/ProjectHistoryList.jsp?oid=" + oid;
    openOtherName(url, "process", "950", "550", "status=no,scrollbars=no,resizable=no");
}

// Project 일정 변경 화면에서 0레벨 Task Update/Information 아이콘 클릭 시 Project 수정 팝업 오픈
function openUpdateProjectPopup(oid) {

    var width   = 860;
    var height  = 700;
    var screenWidth     = (screen.availWidth - width) / 2;
    var screenHeight    = (screen.availHeight - height) / 2;
    var url;
    var pjtType;

    // Project OID로부터 Class Name 추출
    pjtType = oid.substr(0, oid.indexOf(":"));

    if ( pjtType == "e3ps.project.ReviewProject" ) {        // 검토 Project

        url = "/plm/jsp/project/UpdateReviewProject.jsp?oid=" + oid;
    }
    else if ( pjtType == "e3ps.project.ProductProject" ) {  // 제품 Project

        url = "/plm/jsp/project/UpdateProductProject.jsp?oid=" + oid;
    }
    else if ( pjtType == "e3ps.project.MoldProject" ) {     // 금형 Project

        url = "/plm/jsp/project/ModifyMoldProject.jsp?oid=" + oid;
    }

    var newWin = window.open(url,"window", "scrollbars=yes,status=yes,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=yes,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(860,700);
    newWin.focus();
}

// Project 일정 변경 화면에서 Task Update 아이콘 클릭 시 Task 수정 팝업 오픈
function openUpdateTaskPopup(oid) {

    var url="/plm/jsp/project/TaskUpdate.jsp?oid=" + oid + "&isScheduleOpen=T";
    setTimeout( function() { openOtherName(url,"UpdateTask","500","520","status=no,scrollbars=no,resizable=yes"); }, 10);
}

// Project 일정 조회 화면에서 Task Information 아이콘 클릭 시 Task 정보 조회 팝업 오픈
function openViewTaskPopup(oid) {

    var url = "/plm/jsp/project/TaskView.jsp?oid=" + oid;
    setTimeout( function() { openOtherName(url, "TaskView", "850", "700", "status=no,scrollbars=yes,resizable=yes"); }, 10);
}

// WBS 일정 변경 화면에서 0레벨 Task Update/Information 아이콘 클릭 시 WBS 수정 팝업 오픈
function openUpdateWBSPopup(oid) {

    var url="/plm/jsp/project/template/TemplateProjectUpdate.jsp?oid=" + oid;
    setTimeout( function() { openOtherName(url,"process","500","500","status=no,scrollbars=no,resizable=no"); }, 10);
}

// WBS 일정 변경 화면에서 Task Information 아이콘 클릭 시 WBS Task 정보 조회 팝업 오픈
function openViewWBSTaskPopup(oid) {

    var url = "/plm/jsp/project/template/TemplateTaskView.jsp?oid=" + oid;
    setTimeout( function() { openOtherName(url, "TaskView", "850", "700", "status=no,scrollbars=yes,resizable=yes"); }, 10);
}

// Project 일정 변경 화면에서 Debug Sub Task일 경우 Task명 Enumeration Type 구성 Ajax (ProjectScheduleGridJs.jsp 에서 호출)
function debugSubTaskAjax(G, row) {

    $.ajax( {
        url : "/plm/servlet/e3ps/DebugSubTaskAjax",
        type : "POST",
        data : {oid:$("#oid").val()},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.taskNameEnum, function() {
                G.SetAttribute(row, "TaskName", "EnumKeys", this.name, 1);
                G.SetAttribute(row, "TaskName", "Enum", this.name, 1);
            });
        }
    });
}

// Project 일정 변경 화면에서 Role 변경 시 Task 책임자 Setting Ajax (ProjectScheduleGridJs.jsp 에서 호출)
function taskMemberAjax(G, row, personRole) {

    $.ajax( {
        url : "/plm/servlet/e3ps/TaskMemberAjax",
        type : "POST",
        data : {oid:$("#oid").val(), personRole:personRole},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.taskMaster, function() {
                G.SetValue(row, "TaskMaster", this.name, 1);
                G.SetValue(row, "TaskMasterId", this.id, 1);
            });
        }
    });
}

// Project 일정 변경 화면에서 Task 책임자, 참여자 검색 아이콘 클릭 시 Project 인원 선택 팝업 오픈
function openSelectMemberPopup(oid, row, type) {

    memberRow = row;
    memberType = type;

    var url = "/plm/jsp/project/SelectProjectPeopleList.jsp?oid=" + oid;
    var taskMaster = "";

    if ( type == "Master" ) {       // Task 책임자일 경우 Project 전체 인원 목록에서 선택 (단일 선택)

        url = url + "&listType=all&mode=s&function=addMemberToTaskOnScheduling";
    }
    else {                          // Task 참여자일 경우 Project 전체 인원 목록에서 선택 (다중 선택)

        // Task 참여자 설정 시 책임자 제외 처리를 위한 parameter 설정
        if ( memberRow.TaskMasterId != undefined && memberRow.TaskMasterId != "" ) {
            taskMaster = memberRow.TaskMasterId;
        }

        url = url + "&listType=all&mode=m&function=addMemberToTaskOnScheduling&param=" + taskMaster;
    }

    openOtherName(url,"TaskMember","500","500","status=no,scrollbars=yes,resizable=no");
}

// Project 인원 선택 팝업에서 Task 책임자, 참여자 선택 시 Task 책임자, 참여자로 추가
function addMemberToTask(member, memberId) {

    var G = Grids[0];
    var taskMember = "";
    var taskMemberId = "";
    var taskMembers;
    var taskMemberIds;
    var changedTaskMember = "";
    var changedTaskMemberId = "";

    if ( G != null ) {

        if ( memberType == "Master" ) {     // Task 책임자

            // Task 책임자 설정
            G.SetValue(memberRow, "PersonRole", "", 1);
            G.SetValue(memberRow, "TaskMaster", member, 1);
            G.SetValue(memberRow, "TaskMasterId", memberId, 1);

            // Task 참여자에서 해당 인원 제외

            taskMember = memberRow.TaskMember;
            taskMemberId = memberRow.TaskMemberId;

            taskMembers = taskMember.split(",");
            taskMemberIds = taskMemberId.split(",");

            for (var i = 0; i < taskMemberIds.length; i++ ) {

                if ( memberId != taskMemberIds[i] ) {

                    if ( changedTaskMemberId == "" ) {
                        changedTaskMember = taskMembers[i];
                        changedTaskMemberId = taskMemberIds[i];
                    }
                    else {
                        changedTaskMember = changedTaskMember + "," + taskMembers[i];
                        changedTaskMemberId = changedTaskMemberId + "," + taskMemberIds[i];
                    }
                }
            }

            G.SetValue(memberRow, "TaskMember", changedTaskMember, 1);
            G.SetValue(memberRow, "TaskMemberId", changedTaskMemberId, 1);
        }
        else {                              // Task 참여자
            // Task 참여자 설정
            G.SetValue(memberRow, "TaskMember", member, 1);
            G.SetValue(memberRow, "TaskMemberId", memberId, 1);
        }

        memberType = "";
    }
}

// Project 일정 변경 화면에서 Task 책임자, 참여자 삭제 아이콘 클릭 시 Task Role, 책임자 또는 참여자 삭제
function deleteTaskMember(row, type) {

    var G = Grids[0];

    if ( G != null ) {

        if ( type == "Master" ) {

            G.SetValue(row, "PersonRole", "", 1);
            G.SetValue(row, "TaskMaster", "", 1);
            G.SetValue(row, "TaskMasterId", "", 1);
        }
        else {
            G.SetValue(row, "TaskMember", "", 1);
            G.SetValue(row, "TaskMemberId", "", 1);
        }
    }
}

//]]>
</script>
