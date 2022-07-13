<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<style>
.GOIconVAlign{background-position-y : center;}
.GOIconAlign{background-position : center;}
.showPartTypeNames table{min-width:300px;}
.showPartTypeNames table td{border:1px solid #DBDBDB;padding:5px;}
</style>
<script>
//javascript Array 타입 conains function 추가

var delFlag = "D";
var searchIcon          = "/plm/portal/images/icon_5.png";
var deleteIcon          = "/plm/portal/images/icon_delete.gif";
var addIcon        		= "/plm/portal/images/iconPlus.gif";
var removeIcon          = "/plm/portal/images/iconMinus.gif";
var oemPlanBeforeIcon   = "/plm/portal/images/icon_try1.gif";
var oemPlanAfterIcon    = "/plm/portal/images/icon_try3.gif";
var undoFlag = false;

var mappingRow = null;
var formulaRow = null;

$(document).ready(function(){
	
	var CellConfig = "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print,Columns";
	
	if(!${auth}) {
		CellConfig = "ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    }
	
	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/saveCostFormula",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
			Param : {
				deptType : "AUTO",
				DATATYPE : "COSTFORMULA",
				version : "${version}"
			},
			Timeout : 0
		},
		Data : {
			Url : '/plm/ext/cost/costTreeGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				deptType : "AUTO",
				DATATYPE : "COSTFORMULA",
				version : "${version}"
			}
		},
		Export : {
            Url : "/plm/jsp/common/treegrid/ExcelExport.jsp",
            Data : "TGData",
            Param : {
                File : "KPLUS_Grid_list"
            }
        },
		Layout : {
			Data : {
				Cfg : {
					id : "costFormulaEditor",
					Style : gridStyle,
					//Style : "extJS",
					SuppressCfg : cookiesType,
					IdPrefix : "TX",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : 1,
					Deleting : 1,
					ShowDeleted : 0,
					Selecting : 0,
					CopySelected : 1,
                    CopyPasteTree : 1,
                    Pasting : 0,
					Dragging : 1,
					Dropping : 1,
					ExportFormat : 1,
					ExportType : "Filtered,Hidden,Strings,Dates",
					SaveSession : 1,
					UsePrefix : 1,
					Alternate : 0,
					SuppressMessage : '1',
					MainCol : "name"
				},
				Toolbar : {
					Visible : 0
				},
				Panel : {
					Move : 1,
					Copy : 1,
					CanHide : 0,
					CopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd",
				},
				Cols : [
					{ Name : "name", Spanned : 1},
					{ Name : "mappingName", CanEdit : 0, Spanned : 1, Class : "IconVAlign"},
					{ Name : "mappingCode", CanEdit : 0, Visible : 0},
					{ Name : "partTypeName", CanEdit : 0, Type : "Html"},
					{ Name : "partType", CanEdit : 0, Visible : 0},
					{ Name : "mftFactory1Name", CanEdit : 0, Type : "Html"},
					{ Name : "mftFactory1", CanEdit : 0, Visible : 0},
					{ Name : "mftFactory2Name", CanEdit : 0, Type : "Html"},
					{ Name : "mftFactory2", CanEdit : 0, Visible : 0},
					{ Name : "calculationStd", Type : "Enum", Enum : "${calcStdEnum}", EnumKeys : "${calcStdEnumKeys}", Spanned : 1},
					{ Name : "formula", CanEdit : 0, Spanned : 1, Class : "IconVAlign"},
					{ Name : "formulaKeys", CanEdit : 0, Visible : 0},
					{ Name : "status", CanEdit : 0, Visible : 0},
				],
				Head : [{
					Kind : "Topbar",
					Cells : CellConfig,
					AddCopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow",
					AddChildCopyMenu : "AddChild,AddChildEnd",
					Styles : 2,
					Calculated : 1,
					Link : 0,
				},{
					Kind : "Header",
					id : "Header",
					Visible : 0
				},{
					Kind : "Header",
					Align : "center",
					name : "산출속성명",
					mappingName : "매핑속성",
					typeAdd : "",
					partTypeName : "구분",
					mftFactory1Name : "생산국(입고처)",
					mftFactory2Name : "생산지(유/무상)",
					calculationStd : "산출기준",
					formula : "산출식 (연산자 + 피연산자 + 참조수식)",
				}],
				Body : [ { Pos : 0 } ],
				Lang : {
					MenuCopy : {
						CopyRow       : '위에 아이템 복사'
						,CopyRowBelow  : '아래에 아이템 복사'
						,CopyTree      : '위에 Tree 복사'
						,CopyTreeBelow : '아래에 Tree 복사'
						,AddRow        : '위에 신규 아이템 추가'
						,AddRowBelow   : '아래에 신규 아이템 추가'
						,AddChild      : '첫번째 하위 아이템 추가'
						,AddChildEnd   : '마지막 하위 아이템 추가'
						<%-- 
						CopyRow       : '<%=messageService.getString("e3ps.message.ket_message", "02230") %>'		//위에 아이템 복사
						CopyRowBelow  : '<%=messageService.getString("e3ps.message.ket_message", "02070") %>'		//아래에 아이템 복사
						CopyTree      : '<%=messageService.getString("e3ps.message.ket_message", "02231") %>'		//위에 Tree 복사
						CopyTreeBelow : '<%=messageService.getString("e3ps.message.ket_message", "02071") %>'		//아래에 Tree 복사
						AddRow        : '<%=messageService.getString("e3ps.message.ket_message", "02232") %>'		//위에 신규 아이템 추가
						AddRowBelow   : '<%=messageService.getString("e3ps.message.ket_message", "02072") %>'		//아래에 신규 아이템 추가
						AddChild      : '<%=messageService.getString("e3ps.message.ket_message", "02801") %>'		//첫번째 하위 아이템 추가
						AddChildEnd   : '<%=messageService.getString("e3ps.message.ket_message", "01356") %>'		//마지막 하위 아이템 추가
						 --%>
					},
					Alert : {
						DelRow : "%d 아이템을 삭제하시겠습니까?"		//아이템을 삭제하시겠습니까?
						<%-- DelRow : "%d <%=messageService.getString("e3ps.message.ket_message", "00494") %>"		//Task를 삭제하시겠습니까? --%>
					}
				}
			}
		}
	},'treeGrid');
	
	
	Grids.OnRenderFinish = function(grid){
		settingReviseBtn();
	}
		
    Grids.OnClick = function(grid, row, col, e){
        if(row.Kind == "Data"){
        	if(row.partTypeName != ""){
        		ShowPartTypeNames(grid,row,col);
        	}
        }
    }
    
    window.settingReviseBtn = function(){
    	
    	var rows = Grids["costFormulaEditor"].Rows;
    	window.console.log(Grids["costFormulaEditor"]);
        var rowKeys = Object.keys(rows);
        var formulaStatus = "";
        var formulaVersion = $("#formulaVersion").val();
        window.console.log(rowKeys);
        for(var i = 0; i < rowKeys.length; i++){
            
            var id = rowKeys[i];
            var row = rows[id];
            
            if("Data" == row.Kind){
            	window.console.log(row);
            	formulaStatus = row.status;
                break;
            }
        }
        
        if(($("#formulaVersion option").length-1) == formulaVersion){
            if(formulaStatus == "COMPLETED"){
            	$("#reviseBtn").addClass("in-block");
                $("#reviseBtn").show();
                $("#formulaDist").removeClass("in-block");
                $("#formulaDist").hide();
                $("#reviseCancelBtn").removeClass("in-block");
                $("#reviseCancelBtn").hide();
            }else{
            	$("#reviseBtn").removeClass("in-block");
                $("#reviseBtn").hide();
            	$("#formulaDist").addClass("in-block");
                $("#formulaDist").show();
                $("#reviseCancelBtn").addClass("in-block");
                $("#reviseCancelBtn").show();
            }
        }else{
        	
        	$(".reviseFnBtn").removeClass("in-block");
            $(".reviseFnBtn").hide();
        }
    }
	
	window.ShowPartTypeNames = function(G,row,col){
		
		var partTypeHtml = "<div class='showPartTypeNames'><table><tr>";
		partTypeHtml += "<td>" + row.partTypeName + "</td>";
		partTypeHtml += "<td>" + row.mftFactory1Name + "</td>";
		partTypeHtml += "<td>" + row.mftFactory2Name + "</td>";
		partTypeHtml += "</tr></div>";
		
		var M = { Body : partTypeHtml };
		
		M = G.ShowDialog(row, "partTypeName",M,1);
		
		return true;
	}
	
	// 행 추가 시 호출 - 행 추가 가능 여부 체크
	Grids.OnCanRowAdd = function(grid, parent, next) {
		
	    // 0 레벨 행 추가 방지
	    if ( parent.Level >= 0) {

	        if ( undoFlag == false ) {
	        	grid.StartUndo();      // 행 추가 Undo 범위 시작
	            undoFlag = true;
	        }

	        return true;
	    }
	    else {
	        alert("총원가 이외의 항목은 추가할 수 없습니다.");
	    }
	}
	
	// 행 이동 시 호출 - 행 Drop 가능 여부 체크 ([type] 0 – cannot drop, 1 – above torow, 2 – to the end of children of torow, 3 – below torow)
	Grids.OnCanDrop = function(grid, row, togrid, torow, type, copy) {

	    // Project(0레벨)와 동일한 레벨로 행 Drop 방지
	    if ( torow.Level == 0) {
	        return true;
	    }
	}
	
	Grids.OnRowAdded = function(grid, row){

		grid.SetValue(row, "status", row.parentNode.status, 1);
		grid.SetValue(row, "mappingNameIcon", searchIcon, 1);
	    grid.SetValue(row, "mappingNameIconAlign", "Right", 1);
	    grid.SetValue(row, "mappingNameOnClickSideIcon", "javascript:openGridPopup(Row, 'mapping');", 1);
	    grid.SetValue(row, "mappingNameButton", deleteIcon, 1);
	    grid.SetValue(row, "mappingNameOnClickSideButton", "javasciprt:deletePopupValue(Row, 'mapping');", 1);
	    
	    grid.SetValue(row, "formulaClassInnerIcon", 1, 1);
	    grid.SetValue(row, "formulaIcon", searchIcon, 1);
	    grid.SetValue(row, "formulaIconAlign", "Right", 1);
	    grid.SetValue(row, "formulaOnClickSideIcon", "javascript:openGridPopup(Row, 'formula');", 1);
	    grid.SetValue(row, "formulaButton", deleteIcon, 1);
	    grid.SetValue(row, "formulaOnClickSideButton", "javasciprt:deletePopupValue(Row, 'formula');", 1);
	    
	    grid.RefreshRow(row);
	    
	    window.console.log(row);
	}

	
	//삭제되는 수식의 하위 수식 체크
	window.childDeleteCheck = function (grid, row) {
        
		var parentId = row.id;
		var rows = grid.Rows;
        var rowKeys = Object.keys(rows);
        var isChild = false;
        var linkRows = new Array();
        var childIds = new Array();
        
        //하위 수식 전체 OID 가져오기
        for(var i = 0; i < rowKeys.length; i++){
            var id = rowKeys[i];
            var gridRow = rows[id];
            
            if("Data" == gridRow.Kind){
            	
                grid.SetAttribute(gridRow, "formula", "Color", "", 1);
            	
            	if(row.nextSibling == gridRow) isChild = false;
            	
            	if(isChild) childIds.push(id);
            	
            	if(gridRow == row) isChild = true;
            }
        }
        
    	for(var i = 0; i < childIds.length; i++){
    		
            var childId = childIds[i];
            
            for(var j = 0; j < rowKeys.length; j++){
                var id = rowKeys[j];
                var gridRow = rows[id];
                
                if("Data" == gridRow.Kind){
                    var formulaKeys = gridRow.formulaKeys;
                    
                   //수식 내에 하위 OID 가 있을 경우 추가 (삭제할 수식 및 삭제할 수식 하위 제외)
                   if(formulaKeys != null && formulaKeys.indexOf(childId) >= 0 && parentId != id && !childIds.contains(id)){
                	   grid.SetAttribute(gridRow, "formula", "Color", "#FCD", 1);
                       linkRows.push(gridRow);
                    }
                }
            }
        }
        
        if(linkRows.length > 0){
        	
            alert("하위 수식에 연계된 수식이 존재합니다. 변경 후 삭제하세요.");
            
            return false;
        }
        
        return true;
    }
	
	//삭제시 연계 수식 체크
    Grids.OnCanRowDelete = function(grid, row, type){
		
		var rows = grid.Rows;
        var rowKeys = Object.keys(rows);
        var rowId = row.id;
		
    	if(type == 1){ 
            var linkRows = new Array();
            
            if(grid.HasChildren(row)){
            	if(!childDeleteCheck(grid, row)) return false;
            }
            
            for(var i = 0; i < rowKeys.length; i++){
                var id = rowKeys[i];
                var gridRow = rows[id];
                
                grid.SetAttribute(gridRow, "formula", "Color", "", 1);
                
                if("Data" == gridRow.Kind){
                    
                	var formulaKeys = gridRow.formulaKeys;
                    
                    if(formulaKeys != null && formulaKeys.indexOf(rowId) >= 0){
                    	grid.SetAttribute(gridRow, "formula", "Color", "#FCD", 1);
                    	linkRows.push(gridRow);
                    }
                }
            }
            
            if(linkRows.length > 0){
            	alert("연계된 수식이 존재합니다. 변경 후 삭제하세요.");
            	
            	return false;
            }
            
            return true;
    	}
    }
	
	Grids.OnEndEdit = function(grid, row, col, save, val, raw){
		
		var rows = grid.Rows;
		var rowKeys = Object.keys(rows);
		
		if(col == "name"){
			
			var name = row.name;
			var newName = val;
			
			if(newName != null) {
				for(var i = 0; i < rowKeys.length; i++){
	                var id = rowKeys[i];
	                var gridRow = rows[id];
	                
	                if("Data" == gridRow.Kind){
	                    
	                    var formulaKeys = gridRow.formulaKeys;
	                    var formula = gridRow.formula;
	                    
	                    if(formulaKeys != null && formulaKeys.indexOf(row.id) >= 0){
	                        
	                        formula = formula.replaceAll("[" + name + "]", "[" + newName + "]");
	                        
	                        grid.SetValue(gridRow, "formula", formula, 1);
	                    }
	                }
	            }
			}
		}
	}
	
    Grids.OnSave = function(grid){
        
        //if(${isAdmin}) return false; //true 면 save가 먹지 않음 false 일때는 체크로직 안타고 save실행됨
        var isError = false;
        var rows = grid.Rows;
        var rowKeys = Object.keys(rows);
        
        var checkCodes = new Array();
        var dupCode = "";
        
        for(var i = 0; i < rowKeys.length; i++){
            
            var id = rowKeys[i];
            var row = rows[id];
            
            if("Data" == row.Kind){
                grid.SetAttribute(row, "mappingName", "Color", "", 1);
                
                if(!checkCodes.contains(row.mappingCode)){
                	checkCodes.push(row.mappingCode);
                }else{
                    dupCode = row.mappingCode;
                    break;
                }
            }
        }
        
        for(var i = 0; i < rowKeys.length; i++){
            
            var id = rowKeys[i];
            var row = rows[id];
            
            if("Data" == row.Kind){
            	if(row.mappingCode != null && row.mappingCode.trim() != ""){
            		if(dupCode == row.mappingCode){
                        message = "매핑속성 [" + row.mappingName + "]가 중복됩니다.";
                        grid.SetAttribute(row, "mappingName", "Color", "#FCD", 1);
                        isError = true;
                    }
            	}
            }
        }
        
        if(isError){
            alert(message);
        }
        
        return isError;
        
    }
	
	Grids.OnAfterSave = function (G, result, autoupdate){
		if( result >=0 ){
			if(window.isFormulaDist){
				
				alert("수식배포되었습니다.");
                window.isFormulaDist = false;
                setTimeout(settingReviseBtn, 1000);
			}else{
				alert('저장되었습니다.');
			}
		}else{
			alert("저장에 실패하였습니다.\n관리자에게 문의하시기 바랍니다.");
			
			if(window.isFormulaDist){
                window.isFormulaDist = false;
                
                var rows = Grids[0].Rows;
                var rowKeys = Object.keys(rows);
                
                for(var i = 0; i < rowKeys.length; i++){
                    
                    var id = rowKeys[i];
                    var row = rows[id];
                    
                    if("Data" == row.Kind){
                        Grids[0].SetValue(row, "status", "INWORK", 1);
                    }
                }
            }
		}
	}
	
	$("#treeGrid").height($(window).height()-150);
	$(window).resize(function(){
		$("#treeGrid").height($(window).height()-150);
	});
	
	$("#formulaVersion").change(function(){
		Grids[0].Source.Data.Param.version = $(this).val();
		Grids[0].Source.Upload.Param.version = $(this).val();
        Grids[0].ReloadBody(settingReviseBtn);
	});
	$("#formulaVersion").val("${data.version}");
});

window.openGridPopup = function(row, flag){
	
	Grids[0].EndEdit(true);
	
	if("mapping" == flag){
		mappingRow = row;
		getOpenWindow2("/plm/ext/cost/selectCostAttrPopup", "mapping", 800,400);
		
	}else if("formula" == flag){
 		
		var gridRows = Grids[0].Rows;
		var rowKey = Object.keys(gridRows);
		
		for(var i = 0; i < rowKey.length; i++){
			
			var id = rowKey[i];
			var gridRow = gridRows[id];
			var name = gridRow.name;
			if("Data" == gridRow.Kind){
				if(name == null){
					alert("모든 구분을 입력한 후 입력이 가능합니다.");
					return;
				}
			}
		}
		
		formulaRow = row;
		getOpenWindow2("/plm/ext/cost/costCalculatorPopup", "costCalculator", 800, 720);
	}
}

window.setPartType = function(typeObject){
	
	var row = formulaRow;
	var keys = Object.keys(typeObject);
	
	if(row.mappingCode != null && row.mappingCode.trim() != "" && typeObject.partType.length > 0){
		alert("매핑속성 수식에는 부품 구분이 들어갈 수 없습니다. (하위수식추가)");
        return false;
	}
	
	for(var i = 0; i < keys.length; i++){
        var key = keys[i];
        var value = typeObject[key];
        if(value == null) value = "";
        Grids[0].SetValue(row, key, value, 1);
    }
	
	return true;
}

window.addPopupValue = function(text, key, flag){
 	
	var row = formulaRow;
		
	if("mapping" == flag){
		row = mappingRow;
	}
	
	if("mapping" == flag){
		
		if(row.partType != null && row.partType.trim() != "") {
			alert("부품구분이 있는 수식에는 매핑속성이 들어갈 수 없습니다. (상위수식추가)");
			return false;
		}
		Grids[0].SetValue(row, "mappingName", text, 1);
		Grids[0].SetValue(row, "mappingCode", key, 1);
	
	}else if("formula" == flag){

		var keyStr = key.toString();
		if(keyStr != ""){
			var checkLast = keyStr.substring(keyStr.length - 1,keyStr.length);
			if(checkLast == ","){
				keyStr = keyStr.substring(0,keyStr.length - 1);
			}
		}
		Grids[0].SetValue(row, "formula", text, 1);
		Grids[0].SetValue(row, "formulaKeys", keyStr, 1);
	}
	
	return true;
}
window.deletePopupValue = function(row, flag){

	if("mapping" == flag){
		Grids[0].SetValue(row, "mappingName", "", 1);
		Grids[0].SetValue(row, "mappingCode", "", 1);
	}else if("formula" == flag){
		Grids[0].SetValue(row, "formula", "", 1);
		Grids[0].SetValue(row, "formulaKeys", "", 1);
	}
}

window.reviseCostFormula = function(){
	
	if(confirm("개정하시겠습니까?")){
		var param = {
			deptType : "AUTO",
			DATATYPE : "COSTFORMULA"
		}
		 
	    ajaxCallServer("/plm/ext/cost/reviseCostFormula", param, function(data){
	    	
	    	$("#formulaVersion option:first").before("<option value='" + data.version + "'>" + data.version + "</option>").ready(function(){
	    		$("#formulaVersion").val(data.version);
	    	});
	    	
	    	Grids[0].Source.Data.Param.version = data.version;
	    	Grids[0].Source.Upload.Param.version = data.version;
	    	Grids[0].ReloadBody(settingReviseBtn);
	    });
	}
}
window.formulaDist = function(){
	
	if(confirm("수식배포하시겠습니까?")){
		
	    var rows = Grids[0].Rows;
	    var rowKeys = Object.keys(rows);
	    
	    for(var i = 0; i < rowKeys.length; i++){
	        
	        var id = rowKeys[i];
	        var row = rows[id];
	        
	        if("Data" == row.Kind){
	            Grids[0].SetValue(row, "status", "COMPLETED", 1);
	        }
	    }
	    
	    window.isFormulaDist = true;
	    Grids[0].Save();
	}
}

window.reviseCancel = function(){
    if(confirm("개정취소하시겠습니까?")){
    	
    	var param = {}
        ajaxCallServer("/plm/ext/cost/reviseCancel", param, function(data){
        	
        	$("#formulaVersion option:first").remove();
        	$("#formulaVersion").val(data.version);
        	
        	Grids[0].Source.Data.Param.version = data.version;
            Grids[0].Source.Upload.Param.version = data.version;
            Grids[0].ReloadBody(settingReviseBtn);
        });
    }
}
</script>
<body class="popup-background popup-space">
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />수식관리
		</div>
		<div class="b-space5 float-r" style="text-align: right">
			<select name="formulaVersion" id="formulaVersion" style="float:left;position:relative;left:-10px;top:2px;">
			<c:forEach begin="0" end="${version }" varStatus="stat" var="i">
			  <c:set var="idx" value="${stat.end - stat.index}" scope="page"></c:set>
              <option value="${idx }">${idx }</option>
			</c:forEach>
			</select>
			<c:if test="${auth}">
            <span class="reviseFnBtn in-block v-middle r-space7" id="reviseBtn" style="float:left;">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:reviseCostFormula();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684")%><%--개정--%></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="reviseFnBtn in-block v-middle r-space7" id="formulaDist" style="float:left;">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:formulaDist();" class="btn_blue">수식배포</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="reviseFnBtn in-block v-middle r-space7" id="reviseCancelBtn" style="float:left;">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:reviseCancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00691")%><%--개정취소--%></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			
		</div>
		<div id='treeGrid' style='width:100%;'></div>
	</div>
</body>