<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<%--
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
 --%>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- EJS TreeGrid End -->
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">

$(document).ready(function(){
    
});

//////////////////////////////////////////////////////////////
//
//   Grid 관련 전역 변수
// 
//////////////////////////////////////////////////////////////
var delFlag = "1";
var undoFlag = false;

//////////////////////////////////////////////////////////////
//
//   Grid 관련 Function 시작
// 
//////////////////////////////////////////////////////////////
String.prototype.trim = function()
{
  return this.replace(/(^\s*)|(\s*$)/gi, "");
}

String.prototype.replaceAll = function(str1, str2)
{
  var temp_str = "";

  if (this.trim() != "" && str1 != str2)
  {
    temp_str = this.trim();

    while (temp_str.indexOf(str1) > -1)
    {
      temp_str = temp_str.replace(str1, str2);
    }
  }

  return temp_str;
}

Grids.OnDel = function (G){
    alert('Grids.OnDel');
}

//삭제 버튼 클릭시에 삭제이벤트 방지
//1. 분류체계에 연결된 부품이 있는 경우
//2. 분류체계 하위 Child가 있는 경우 - 가능 고려
//3. Level 1인 경우는 원초적으로 불가능하나 한 번 더 체크함
Grids.OnClickPanelDelete = function (Grid, Row, Col, Event){
	
	//alert('Grids.OnClickPanelDelete');
	try{
		var hasPart = Grid.GetString( Row, "hasPart");
	    //alert(hasPart);
		if( hasPart == "1" ){
			alert("<%=messageService.getString("e3ps.message.ket_message", "06101")%><%--해당 부품분류체계를 사용하는 부품이 존재하여 삭제할 수 없습니다.--%>");  
			CancelEvent(Event, 0);
		}else{
			var lev = Grid.GetString( Row, "lev");
			//alert(lev);
	        if( lev == "1" ){
	            alert("<%=messageService.getString("e3ps.message.ket_message", "06102")%><%--부품분류체계'는 시작 위치이므로 삭제할 수 없습니다.--%>'");
	            CancelEvent(Event, 0);
	        }else{
		        // child가 있으면 삭제 불가
				var child = Row.firstChild;
				//alert("child:" + child);
				if(child){
		           alert("<%=messageService.getString("e3ps.message.ket_message", "06103")%><%--하위 부품분류체계가 존재합니다. 하위분류체계를 삭제 및 저장한 후에 진행해 주십시요.--%>");
		           CancelEvent(Event, 0);
		        }
	        }
		}
    }catch(e){ alert(e.message);};
    
}

//행 삭제 시 호출
// 1. 행 삭제 아이콘 클릭 시 Row에 deleteFlag으로 delFlag값["1"] 넣어준다.
// 2. 삭제를 제외한 동일레벨의 sequence를 맞춰준다.
// 3. 삭제한 노드의 하위 Row에도 deleteFlag에 값을 delFlag 즉 ["1"]로 넣어준다.
Grids.OnRowDelete = function(G, row, type) {

	try{
		
	    if ( undoFlag == false ) {
	        G.StartUndo();          // 행 삭제 Undo 범위 시작
	        undoFlag = true;
	    }
	    
	    var id = row.id;
	    var parent = row.parentNode;
	    var sortNo = 0;
	
	    // 1. 행 삭제 아이콘 클릭 시 deleteFlag 값 설정
	    G.SetValue(row, "deleteFlag", delFlag, 1);
	
	    // 2. 동일 Level Seq 설정
	    for ( var r = parent.firstChild; r; r = r.nextSibling ) {
	
	        if ( r.deleteFlag != delFlag ) {        // 삭제된 부품분류가 아닐 경우 Seq 설정
	        	
	        	sortNo = sortNo + 10;
	            G.SetValue(r, "sortNo", sortNo, 1); // 부품분류 Seq
	        }
	    }
	
	    // 3. 하위 부품분류 삭제 처리 - 하위 부품분류 DeleteFlag 값 설정
	    setChildrenDataAfterRowDeleted(G, row);
	
	    for ( var r = row.firstChild; r; r = r.nextSibling ) {
	
	        G.SetValue(r, "deleteFlag", delFlag, 1);
	    }
	
	    if ( undoFlag == true ) {
	        G.EndUndo();            // 행 삭제 Undo 범위 종료
	        undoFlag = false;
	    }
	}catch(e){ alert(e.message);};
}

//행 삭제 후 하위 분류체계 deleteFlag (삭제 표시) 값 처리 (Recursive)
function setChildrenDataAfterRowDeleted(G, row) {

	try{	
	    for ( var r = row.firstChild; r; r = r.nextSibling ) {
	
	        G.SetValue(r, "deleteFlag", delFlag, 1);
	
	        if ( G.HasChildren(r) ) {
	
	            setChildrenDataAfterRowDeleted(G, r);
	        }
	    }
	}catch(e){ alert(e.message);};
	
}

//행 추가 시 호출
//1. hasPart 값 0으로 주어, 삭제 가능하도록 한다. 
//2. Default 값들을 넣어 둔다.
Grids.OnRowAdd = function (Grid, Row){
	
	try{
		
        if ( undoFlag == false ) {
             Grid.StartUndo();          // 행 삭제 Undo 범위 시작
             undoFlag = true;
        }
	        
		//alert('Grids.OnRowAdd');
		//Grid.SetString( Row, "hasPart", "0", true );
		//Grid.SetString( Row, "useClaz", "0", true );
		
    }catch(e){ alert(e.message);
    
	    if ( undoFlag == true ) {
	        Grid.EndUndo();
	        undoFlag = false;
	    }
    };
}

// 행을 추가후에 호출 : 
// 1. 부품에서 연결되어 사용중인 분류체계는 하위 분류체계를 가지지 못함 (동일레벨이 아닌 자식으로 넣을 경우에 Validation)
// 2. 부모의 hasPart를 체크해서 1인 경우 삭제
Grids.OnRowAdded = function(Grid,  TRow){
	
    try{
    	
    	if ( undoFlag == false ) {
            Grid.StartUndo();          // 행 삭제 Undo 범위 시작
            undoFlag = true;
        }
    	
    	//alert('Grids.OnRowAdded :');
    	var TRowParent = TRow.parentNode;
    	var hasPart = Grid.GetString( TRowParent, "hasPart");
        //alert('Parent hasPart :' + hasPart);
        
        if( hasPart == "1" ){
        	
            alert('<%=messageService.getString("e3ps.message.ket_message", "06104")%><%--부품에서 연결되어 사용중인 분류체계는 하위 분류체계를 가질 수 없습니다.--%>');
            Grid.DeleteRow(TRow, 2);
            
        }else{
        
	        Grid.SetString( TRow, "hasPart", "0", true );
	        Grid.SetString( TRow, "useClaz", "0", true );
	        
	        var sortNo = 0;
	        var parent = TRow.parentNode;
	        // 동일 Level Task Seq 설정
	        for ( var r = parent.firstChild; r; r = r.nextSibling ) {
	
	            if ( r.deleteFlag != delFlag ) {        
	            	sortNo = sortNo + 10;
	                Grid.SetValue(r, "sortNo", sortNo, 1);      
	            }
	        }
        }
        
        if ( undoFlag == true ) {

        	Grid.EndUndo();            // 행 삭제 Undo 범위 종료
            undoFlag = false;
        }

    }catch(e){ alert(e.message);
    
	    if ( undoFlag == true ) {
	        Grid.EndUndo();
	        undoFlag = false;
	    }
	    
    };
    
}

/**
 Called before changes are uploaded or submitted to server.
 It is called from Save method that is called when a user clicks Save button. It is called after validation (see <Cfg Validate>).
 It is also called whenever are changes uploaded by AutoUpdate (with autoupdate parameter set to 1)
 Return true to suppress saving.
 The row is set if only one row was changed, this is the row to update, only with autoupdate = 1.
 */
 // Validation
Grids.OnSave = function ( G, TRow , autoupdate){
	
	 try{
		 
		//autoupdate = false;

		for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
	
	        if ( r.deleteFlag != delFlag ) {        // 삭제된 Task가 아닐 경우 Validation Check
	
	            lastRow = r;
	
	            // 분류체계명 필수 입력 체크
	            if ( r.classKrName == null || (r.classKrName + "").trim() == "" ) {
	
	                alert("<%=messageService.getString("e3ps.message.ket_message", "06105")%><%--분류 체계 명을 입력해 주십시오.--%>");
	                setTimeout( function() { G.Focus(r, "classKrName", null, true); }, 10);
	                return true;
	            }
	            
	            // 분류코드 필수 입력 체크
                if ( r.classKrName == null || (r.classKrName + "").trim() == "" ) {
    
                    alert("분류 체계 코드를 입력해 주십시오.");
                    setTimeout( function() { G.Focus(r, "classCode", null, true); }, 10);
                    return true;
                }
                
                // 사용여부 필수 입력 체크
                var useClaz = G.GetString( r, "useClaz");
                if ( useClaz == null || useClaz.trim() == "" ) {

                	alert("<%=messageService.getString("e3ps.message.ket_message", "06106")%><%--사용 여부을 선택해 주십시오.--%>");
                    setTimeout( function() { G.Focus(r, "useClaz", null, true); }, 10);
                    return true;
                }
                
                if ( r.sortNo == null || (r.sortNo + "").trim() == "" ) {
                    
                    alert("<%=messageService.getString("e3ps.message.ket_message", "06107")%><%--정렬순서를 입력해 주세요.--%>");
                    setTimeout( function() { G.Focus(r, "sortNo", null, true); }, 10);
                    return true;
                }
                
	        }
	    }
	   	
	    //showProcessing();   // 진행 상태바 표시 (processing.html)
	    //disabledAllBtn();   // 버튼 비활성화
		 
	 }catch(e){ alert(e.message);};

} 

/**
 Called after Save or AutoUpdate is done, the server response was received and changes accepted (if succeeded). 
 The result is the <IO Result/>, <0 error, >=0 success.
 It is called only for AJAX communication.
 */
Grids.OnAfterSave = function ( G, result, autoupdate){
	 
	 try{
		 
		  if( result >=0 ){
			  
			  alert('<%=messageService.getString("e3ps.message.ket_message", "02460")%><%--저장되었습니다--%>');
			  G.Reload();
			  //hideProcessing();
			  
		  }else{
			  
			  alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
				//window.location.reload();
			}

		} catch (e) {
			alert(e.message);
	    };
}

	//////////////////////////////////////////////////////////////
	//
	//   Grid 관련 Function 끝
	// 
	//////////////////////////////////////////////////////////////

function openUpdatePartAttrPopup(Grid, Row, Col, Event, oid) {
    /*
	alert("call Grid:" + Grid);
	alert("call Row:" + Row);
	alert("call Col:" + Col);
	alert("call Event:" + Event);
	alert("call oid:" + oid);
	*/
	
	//var partAttrPop = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=1024px; dialogHeight:510px; center:yes");
	
	showProcessing();
	
	_callBackGrid = Grid;
	_callBackGridRow = Row;
    var url = "/plm/ext/part/spec/searchSpecPopup.do?oid="+oid;
    var name = "partAttr";
    var defaultWidth = 1034;
    var defaultHeight = 510;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
	
}

var _callBackGrid;
var _callBackGridRow;
function setPartAttributeListName(partAttrPop){
	
	if ( typeof partAttrPop == "undefined" || partAttrPop == null ) {
        return;
    }
    
	_callBackGrid.SetValue(_callBackGridRow, "partAttributeName", partAttrPop, 1);
    
}
	
function moveRowUp(){
	
	try{
		
		G = Grids[0];
        var rows = G.GetSelRows(); //선택한 Row
        //alert(rows);
        
        if( rows == '' ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "06108")%><%--좌측을 체크박스 클릭하여 이동할 Row를 선택해 주세요.--%>');
            return;
        }
		
        if( rows.length > 1 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "06109")%><%--하나의 Row를 선택해 주세요.--%>');
            return;
        }
        
		var selectedRow = rows[0];
		var parent = selectedRow.parentNode;
		if(parent == null){
            alert('<%=messageService.getString("e3ps.message.ket_message", "06110")%><%--최상위는 이동할 수 없습니다.--%>');
            return;
        }
		
		var prev = null; 
		var now = false;		
		for ( var r = parent.firstChild; r; r = r.nextSibling ) {
			
	        if ( r.id == selectedRow.id ) {
	        	now = true;
	        	//alert("now:" + selectedRow.id + " next:" + r.id);
	        	break;
	        }
	        
	        prev = r;
	    }
		
		if(prev == null){
            alert('<%=messageService.getString("e3ps.message.ket_message", "06111")%><%--최상위는 더 이상 이동할 수 없습니다.--%>');
            return;
        }
		
        if ( undoFlag == false ) {
            G.StartUndo();          // 정렬 Undo 범위 시작
            undoFlag = true;
        }
        
		var show = true;
		// 정렬
	    G.MoveRow(prev, parent, selectedRow, show);
	    G.MoveRow(selectedRow, parent, prev, show);
	    
	    var sortNo = 0;
	    // 동일 Level Seq 설정
	    for ( var r = parent.firstChild; r; r = r.nextSibling ) {

	        if ( r.deleteFlag != delFlag ) {        // 삭제된 부품분류가 아닐 경우 Seq 설정
	            
	            sortNo = sortNo + 10;
	            G.SetValue(r, "sortNo", sortNo, 1); // 부품분류 Seq
	        }
	    }
	    
	    if ( undoFlag == true ) {
            G.EndUndo();            // 정렬 Undo 범위 종료
            undoFlag = false;
        }
	    
	} catch (e) {
        alert(e.message);
    };
}

function moveRowDown(){
	
    try{
        
    	G = Grids[0];
        var rows = G.GetSelRows(); //선택한 Row
        //alert(rows);
        
        if( rows == '' ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "06108")%><%--좌측을 체크박스 클릭하여 이동할 Row를 선택해 주세요.--%>');
            return;
        }
        
        if( rows.length > 1 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "06109")%><%--하나의 Row를 선택해 주세요.--%>');
            return;
        }
        
        var selectedRow = rows[0];
        var parent = selectedRow.parentNode;
        if(parent == null){
        	alert('<%=messageService.getString("e3ps.message.ket_message", "06110")%><%--최상위는 이동할 수 없습니다.--%>');
        	return;
        }
        
        var next = null; 
        var now = false;
        for ( var r = parent.firstChild; r; r = r.nextSibling ) {
            
            if(now){
                next = r;
                //alert("now:" + selectedRow.id + " next:" + r.id);
                break;
            }
            
            if ( r.id == selectedRow.id ) {
                next = r.nextSibling;
                now = true;
            }
        }
        
        if(next == null){
        	alert('<%=messageService.getString("e3ps.message.ket_message", "06111")%><%--최하위는 더 이상 이동할 수 없습니다.--%>');
        	return;
        }
        
        if ( undoFlag == false ) {
            G.StartUndo();          // 정렬 Undo 범위 시작
            undoFlag = true;
        }
        
        var show = true;
        // 정렬
        G.MoveRow(selectedRow, parent, next, show);
        G.MoveRow(next, parent, selectedRow, show);
        
        var sortNo = 0;
        // 동일 Level Seq 설정
        for ( var r = parent.firstChild; r; r = r.nextSibling ) {

            if ( r.deleteFlag != delFlag ) {        // 삭제된 부품분류가 아닐 경우 Seq 설정
                
                sortNo = sortNo + 10;
                G.SetValue(r, "sortNo", sortNo, 1); // 부품분류 Seq
            }
        }
        
        if ( undoFlag == true ) {

            G.EndUndo();            // 정렬 Undo 범위 종료
            undoFlag = false;
        }
        
    } catch (e) {
        alert(e.message);
    };
}
	
</script>
<table width="1280" height="100%" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<td valign="top">
			<table width="1280" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><table width="1280" height="28" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td width="20"><img src="/plm/portal/images/icon_3.png"></td>
								<td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "06112")%><%--부품분류--%></td>
								<td align="right"><img
									src="/plm/portal/images/icon_navi.gif">Admin<img
									src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "06112")%><%--부품분류--%><img
									src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "06113")%><%--부품분류 관리--%></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td class="head_line"></td>
				</tr>
				<tr>
					<td class="space10"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="right">
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td class="space5"></td>
							</tr>
						</table> <!-- EJS TreeGrid Start -->
						<div class="content-main">
							<div class="container-fluid">
								<div class="row-fluid">
									<div id="listGrid" style="WIDTH: 100%; HEIGHT: 500px">
										<bdo
											<%--
                                            Debug="1"
                                            Debug='3'
											AlertError="0"
                                            --%>
											Layout_Url="/plm/extcore/jsp/part/classify/viewClazListLayout.jsp"
											Layout_Method="POST"
											Data_Url="/plm/ext/part/classify/listClazDataXml.do"
				                            
				                            Upload_Url="/plm/ext/part/classify/saveClazDataXml.do"
											Upload_Method="POST" Upload_Format="Internal"
											Upload_Data="TGData" Upload_Flags="AllCols" Upload_Type="All"
											Upload_Xml="1"
				                            
				                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
											Export_Data="TGData" Export_Param_File="Search_Claz_List"></bdo>
									</div>
								</div>
							</div>
						</div> <!-- EJS TreeGrid End -->

					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>