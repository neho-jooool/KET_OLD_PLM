<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<script>
var moldGrid = null;
var equipGrid = null;
var etcGrid = null;
$(document).ready(function() {
	
	var topbarProp = new Object();
	var panelProp = new Object();
	
	panelProp.Width = 0;
	topbarProp.Kind = "Topbar";
	topbarProp.Cells = "Save,Reload,Export";
	topbarProp.Styles = 2;
	topbarProp.Calculated = 1;
	topbarProp.Link = 0;
	
	var editing = 1;
	if(${isBasePart}) {
        topbarProp.Cells = "Reload,Export";
        editing = 0;
    }
	
	var columnList = new Array();
	columnList.push({LABEL : "품명", KEY : "partName", LEVEL : 1, CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "구분", KEY : "구분", LEVEL : 1, COLSPAN : 3});
	columnList.push({LABEL : "양산구분", KEY : "costTypeDisplay", LEVEL : 2, CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "감가유형", KEY : "reduceCode", LEVEL : 2, Type : "Enum" });
	columnList.push({LABEL : "금형투자비", KEY : "금형투자비", LEVEL : 1, COLSPAN : 4});
	columnList.push({LABEL : "금형투자비", KEY : "investCost", LEVEL : 2, Type: "Float", Format : "###,##0.0" , CanEdit : 0, CanSelect : 0});
		
	columnList.push({LABEL : "지급액", KEY : "investPay", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "감가액", KEY : "investReduceCost", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	
	columnList.push({LABEL : "생산 Capa", KEY : "생산 Capa", LEVEL : 1, COLSPAN : 3});
	columnList.push({LABEL : "생산 Capa", KEY : "capaRate", LEVEL : 2, Type: "Float", Format : "0%", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "벌", KEY : "investUnit", LEVEL : 2, Type: "Float", Format : "###,##0.0벌", CanEdit : 0, CanSelect : 0 });
	
	columnList.push({LABEL : "판매감가수량", KEY : "판매감가수량", LEVEL : 1, COLSPAN : 5});
	columnList.push({LABEL : "판매수량", KEY : "salesQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "양산수량", KEY : "massQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "추가수량", KEY : "addQty", LEVEL : 2, Type: "Float", Format : "###,##0.0" });
	
	columnList.push({LABEL : "판매감가수량", KEY : "salesReduceQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0, Formula : "salesQty+massQty+addQty" });
	columnList.push({LABEL : "일반감가수량", KEY : "normalReduceQty", LEVEL : 1, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "개별 감가비", KEY : "eachReduceCost", LEVEL : 1, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "비고", KEY : "investNote", LEVEL : 1 });
	columnList.push({LABEL : "PARTOID", KEY : "cpOid", LEVEL : 1, Visible : 0 });
	
	var columnProp = generateGridColumn(columnList);
	
	moldGrid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/saveCostInvestInfo",
			Format : "JSON",
			Flags : "AllCols",
			Data : "gridData",
			Type : "Changes,Body",
			Param : {
				oid : "${oid}"
			},
		},
		Data : {
			Url : '/plm/ext/cost/costReductionGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				oid : "${oid}",
				REDUCTIONTYPE : "mold",
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
					id : "moldCostReductionGrid",
					Style : gridStyle,
					SuppressCfg : cookiesType,
					IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : editing,
					Deleting : 0,
					ShowDeleted : 0,
					Selecting : 0,
					CopySelected : 0,
					CopyPasteTree : 0,
					Pasting : 0,
					Dragging : 0,
					Dropping : 0,
					ExportFormat : 1,
					ExportType : "Filtered,Hidden,Strings,Dates",
					SaveSession : 1,
					UsePrefix : 1,
					Alternate : 0,
					SuppressMessage : '1',
				},
				Toolbar : { Visible : 0 },
				Panel : panelProp,
				Cols : columnProp.cols,
				Head : [
					topbarProp, {
						Kind : "Header",
						id : "Header",
						RowSpan : 0,
						Visible : 0
					},
					columnProp.kHeader1Lv, columnProp.kHeader2Lv
				],
				Body : [ { Pos : 0 }],
				Foot : [{
					id : "foot1",
					Spanned : '1',
					CanDelete : '0', 
					CanEdit : '0',
					구분Align : 'Center',
					구분 : '금형 감가비 합계',
					구분Span : '16',
					eachReduceCostSpan : '2',
					eachReduceCostAlign : 'Center',
					eachReduceCostFormula : "footFormula(sum())",
					Calculated : "1"
				}]
			}
		}
	}, "MOLD");
	
	columnList = new Array();
	if(!${isBasePart}) {
		columnList.push({LABEL : "", KEY : "PANEL", Type : "Panel", Width : 40, LEVEL : 1, CanEdit : 0, CanSelect : 0,
			OnClickPanelCopy : "addGridRow(equipGrid, Row)"
		});
	}
	columnList.push({LABEL : "품명", KEY : "partName", LEVEL : 1, CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "양산구분", KEY : "costType", LEVEL : 1, Visible : 0  });
	columnList.push({LABEL : "구분", KEY : "구분", LEVEL : 1, COLSPAN : 3});
	columnList.push({LABEL : "양산구분", KEY : "costTypeDisplay", LEVEL : 2, CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "감가유형", KEY : "reduceCode", LEVEL : 2, Type : "Enum" });
	columnList.push({LABEL : "설비투자비", KEY : "설비투자비", LEVEL : 1, COLSPAN : 4});
	columnList.push({LABEL : "설비투자비", KEY : "investCost", LEVEL : 2, Type: "Float", Format : "###,##0.0" , CanEdit : 0, CanSelect : 0});
		
	columnList.push({LABEL : "지급액", KEY : "investPay", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "감가액", KEY : "investReduceCost", LEVEL : 2, Type: "Float", Format : "###,##0.0" });
	
	columnList.push({LABEL : "생산 Capa", KEY : "생산 Capa", LEVEL : 1, COLSPAN : 3});
	columnList.push({LABEL : "생산 Capa", KEY : "capaRate", LEVEL : 2, Type: "Float", Format : "0%", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "Line", KEY : "investUnit", LEVEL : 2, Type: "Float", Format : "###,##0.0Line", CanEdit : 0, CanSelect : 0 });
	
	columnList.push({LABEL : "판매감가수량", KEY : "판매감가수량", LEVEL : 1, COLSPAN : 5});
	columnList.push({LABEL : "판매수량", KEY : "salesQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "양산수량", KEY : "massQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "추가수량", KEY : "addQty", LEVEL : 2, Type: "Float", Format : "###,##0.0" });
	
	columnList.push({LABEL : "판매감가수량", KEY : "salesReduceQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0, Formula : "salesQty+massQty+addQty" });
	columnList.push({LABEL : "일반감가수량", KEY : "normalReduceQty", LEVEL : 1, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "개별 감가비", KEY : "eachReduceCost", LEVEL : 1, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "비고", KEY : "investNote", LEVEL : 1 });
	columnList.push({LABEL : "PARTOID", KEY : "cpOid", LEVEL : 1, Visible : 0 });
	
	var columnProp = generateGridColumn(columnList);
	
	equipGrid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/saveCostInvestInfo",
			Format : "JSON",
			Flags : "AllCols",
			Data : "gridData",
			Type : "Changes,Body",
			Param : {
				oid : "${oid}"
			},
		},
		Data : {
			Url : '/plm/ext/cost/costReductionGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				oid : "${oid}",
				REDUCTIONTYPE : "equip",
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
					id : "equipCostReductionGrid",
					Style : gridStyle,
					SuppressCfg : cookiesType,
					IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : editing,
					Deleting : 1,
					ShowDeleted : 0,
					Selecting : 0,
					CopySelected : 0,
                    CopyPasteTree : 0,
                    Pasting : 0,
					Dragging : 0,
					Dropping : 0,
					ExportFormat : 1,
					ExportType : "Filtered,Hidden,Strings,Dates",
					SaveSession : 1,
					UsePrefix : 1,
					Alternate : 0,
					SuppressMessage : '1',
					MainCol : "costType"
				},
				Toolbar : { Visible : 0 },
				Panel : panelProp,
				Cols : columnProp.cols,
				Head : [
					topbarProp, {
						Kind : "Header",
						id : "Header",
						RowSpan : 0,
						Visible : 0
					},
					columnProp.kHeader1Lv, columnProp.kHeader2Lv
				],
				Body : [ { Pos : 0 }],
				Foot : [{
					id : "foot1",
					Spanned : '1',
					CanDelete : '0', 
					CanEdit : '0',
					구분Align : 'Center',
					구분 : '설비 감가비 합계',
					구분Span : '16',
					eachReduceCostSpan : '2',
					eachReduceCostAlign : 'Center',
					eachReduceCostFormula : "footFormulaEquip()",
					Calculated : "1"
				}],
				Lang : {
					Alert : {
						DelRow : "%d 아이템을 삭제하시겠습니까?"		//아이템을 삭제하시겠습니까?
						<%-- DelRow : "%d <%=messageService.getString("e3ps.message.ket_message", "00494") %>"		//아이템을 삭제하시겠습니까? --%>
					}
				}
			}
		}
	}, "FAC");
	
	var columnList = new Array();
	columnList.push({LABEL : "품명", KEY : "partName", LEVEL : 1, CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "구분", KEY : "구분", LEVEL : 1, COLSPAN : 4});
	columnList.push({LABEL : "항목명", KEY : "itemName", LEVEL : 2, CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "양산구분", KEY : "costTypeDisplay", LEVEL : 2, CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "감가유형", KEY : "reduceCode", LEVEL : 2, Type : "Enum" });
	columnList.push({LABEL : "기타투자비", KEY : "기타투자비", LEVEL : 1, COLSPAN : 4});
	columnList.push({LABEL : "기타투자비", KEY : "investCost", LEVEL : 2, Type: "Float", Format : "###,##0.0" , CanEdit : 0, CanSelect : 0});
		
	columnList.push({LABEL : "지급액", KEY : "investPay", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "감가액", KEY : "investReduceCost", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	
	columnList.push({LABEL : "생산 Capa", KEY : "생산 Capa", LEVEL : 1, COLSPAN : 3});
	columnList.push({LABEL : "생산 Capa", KEY : "capaRate", LEVEL : 2, Type: "Float", Format : "0%", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "Set", KEY : "investUnit", LEVEL : 2, Type: "Float", Format : "###,##0.0Set", CanEdit : 0, CanSelect : 0 });
	
	columnList.push({LABEL : "판매감가수량", KEY : "판매감가수량", LEVEL : 1, COLSPAN : 5});
	columnList.push({LABEL : "판매수량", KEY : "salesQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "양산수량", KEY : "massQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0});
	columnList.push({LABEL : "추가수량", KEY : "addQty", LEVEL : 2, Type: "Float", Format : "###,##0.0" });
	
	columnList.push({LABEL : "판매감가수량", KEY : "salesReduceQty", LEVEL : 2, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0, Formula : "salesQty+massQty+addQty" });
	columnList.push({LABEL : "개별 감가비", KEY : "eachReduceCost", LEVEL : 1, Type: "Float", Format : "###,##0.0", CanEdit : 0, CanSelect : 0 });
	columnList.push({LABEL : "비고", KEY : "investNote", LEVEL : 1 });
	columnList.push({LABEL : "PARTOID", KEY : "cpOid", LEVEL : 1, Visible : 0 });
	
	var columnProp = generateGridColumn(columnList);
	
	etcGrid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/saveCostInvestInfo",
			Format : "JSON",
			Flags : "AllCols",
			Data : "gridData",
			Type : "Changes",
			Param : {
				oid : "${oid}"
			},
		},
		Export : {
            Url : "/plm/jsp/common/treegrid/ExcelExport.jsp",
            Data : "TGData",
            Param : {
                File : "KPLUS_Grid_list"
            }
        },
		Data : {
			Url : '/plm/ext/cost/costReductionGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				oid : "${oid}",
				REDUCTIONTYPE : "etc",
			}
		},
		Layout : {
			Data : {
				Cfg : {
					id : "etcCostReductionGrid",
					Style : gridStyle,
					SuppressCfg : cookiesType,
					 IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Sorting : 0,
					Editing : editing,
					Deleting : 1,
					ShowDeleted : 0,
					Selecting : 0,
					CopySelected : 0,
                    CopyPasteTree : 0,
                    Pasting : 0,
					Dragging : 0,
					Dropping : 0,
					ExportFormat : 1,
					ExportType : "Filtered,Hidden,Strings,Dates",
					Validate : "Added,Changed",
					SaveSession : 1,
					UsePrefix : 1,
					Alternate : 0,
					SuppressMessage : '1',
					MainCol : "costType"
				},
				Toolbar : { Visible : 0 },
				Panel : panelProp,
				Cols : columnProp.cols,
				Head : [
					topbarProp, {
						Kind : "Header",
						id : "Header",
						RowSpan : 0,
						Visible : 0
					},
					columnProp.kHeader1Lv, columnProp.kHeader2Lv
				],
				Body : [ { Pos : 0 }],
				Foot : [{
					id : "foot1",
					Spanned : '1',
					CanDelete : '0', 
					CanEdit : '0',
					구분Align : 'Center',
					구분 : '기타 감가비 합계',
					구분Span : '16',
					eachReduceCostSpan : '2',
					eachReduceCostAlign : 'Center',
					eachReduceCostFormula : "footFormula(sum())",
					Calculated : "1"
				}],
			}
		}
	}, "ETC");
	
	window.footFormula = function(result){
		
		if(isNaN(result) || !isFinite(result)){
			result = 0;
		}
		if(result == ''){
			result = 0;
		}
		
		return result;
    }
	
	footFormulaEquip = function(){
        var result = 0;
        
        var grid = Grids[1];
        var rows = grid.Rows;
        var rowKeys = Object.keys(rows);
        var parentRows = new Object();
        
        
        for(var i = 0; i < rowKeys.length; i++){
        	var id = rowKeys[i];
            var row = rows[id];
            
        	if(row.Deleted == 1){
                continue;
            }
        	
        	if("Data" == row.Kind && row.id != "foot1"){
        		result = result+row.eachReduceCost;
            }
        }
        
        if(isNaN(result) || !isFinite(result)){
            result = 0;
        }
        if(result == ''){
            result = 0;
        }
        
        return result;
    }
	
	Grids.OnAfterSave = function (grid, result, autoupdate){
		try{
			if( result >=0 ){
				
				alert('저장되었습니다');
				settingRowSpan(grid);
				
				if(opener){
					opener.initializePage();
				}
				
			}else{
				alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
			}
		} catch (e) {
			alert(e.message);
		};
	}
	
	Grids.OnSave = function(grid){
		
		var isError = false;
		var isAlert = true;
		
		var rows = grid.Rows;
		var rowKeys = Object.keys(rows);
		var parentRows = new Object();
		var rcErrorRows = new Array();
		var ircErrorRows = new Array();
		
		try{
			for(var i = 0; i < rowKeys.length; i++){
				
				var id = rowKeys[i];
				var row = rows[id];
				
				if(row.Deleted == 1){
					continue;
				}
				
				if("Data" == row.Kind && row.id != "foot1"){
					grid.SetAttribute(row, "reduceCode", "Color", "", 1);
					grid.SetAttribute(row, "investReduceCost", "Color", "", 1);
	
					if(row.reduceCode != ""){

						var isNormal = row.salesReduceQty > row.normalReduceQty;
						
						if(row.normalReduceQty == 0){//일반감가수량이 0 일 경우 체크로직 걸지 않도록 변경 2018.11.06 이경무 요청
							isNormal = false;
						}
						
/* 						if(isNormal && row.reduceCode == "REDU001" && row.investType != "etc"){ //마이그레이션 때문에 임시적으로 주석처리한다 2020.01.09 이경무 요청
							
							if(isAlert){
								alert("판매를 선택할 수 없습니다. (판매감가수량 > 일반감가수량)");
								isAlert = false;
							}
							
							isError = true;
							rcErrorRows.push(row);
						} */
						
					}else{
						
						if(isAlert){
							alert("감가유형을 입력하세요.");
							isAlert = false;
						}
						isError = true;
						rcErrorRows.push(row);
					}
	
					if(row.Level == 1){
						
						if(parentRows[row.parentNode.id] == null){
							parentRows[row.parentNode.id] = new Array();
							parentRows[row.parentNode.id].push(row);
						}else{
							parentRows[row.parentNode.id].push(row);
						}
					}else{
						parentRows[row.id] = new Array();
					}
				}
			}
			
			var parentKeys = Object.keys(parentRows);
		
			for(var i = 0; i < parentKeys.length; i++){
				
				var pid = parentKeys[i];
				var pRow = rows[pid];
				var childRows = parentRows[pid];
				
				var irCost = calculateValue(pRow.investCost, pRow.investPay, "-");
				var irSumCost = pRow.investReduceCost;
				
				for(var j = 0; j < childRows.length; j++){
					var cRow = childRows[j];
					irSumCost = calculateValue(irSumCost, cRow.investReduceCost, "+");
				}
				
				if(irCost != irSumCost){
					if(isAlert){
						alert("감가액이 일치하지 않습니다.");
						isAlert = false;
					}
					isError = true;
					ircErrorRows.push(pRow);
					ircErrorRows = ircErrorRows.concat(childRows);
				}
			}
			for(var i = 0; i < ircErrorRows.length; i++){
				
				var row = ircErrorRows[i];
				grid.SetAttribute(row, "investReduceCost", "Color", "#FCD", 1);
			}
			
			for(var i = 0; i < rcErrorRows.length; i++){
				var row = rcErrorRows[i];
				grid.SetAttribute(row, "reduceCode", "Color", "#FCD", 1);
			}
			
		}catch(e){
			window.console.log(e);
		}
		
		return isError;
	}
	
	Grids.OnRenderFinish = function(grid){
		settingRowSpan(grid);
	}
	
	window.settingRowSpan = function(grid){
		var rows = grid.Rows;
		var rowKeys = Object.keys(rows);
		
		for(var i = 0; i < rowKeys.length; i++){
			
			var id = rowKeys[i];
			var row = rows[id];
			
			if("Data" == row.Kind && row.parent == null && row.id != "foot1"){
				if(row.lastChild != null){
					grid.SpanRange(row, "costTypeDisplay", row.lastChild, "costTypeDisplay");
					
					if("equipCostReductionGrid" == grid.id){
						grid.SpanRange(row, "investCost", row.lastChild, "investCost");
						grid.SpanRange(row, "investPay", row.lastChild, "investPay");
					}
				}
				
				if(row.costType == "M"){
					if(row.lastChild != null){
						if("etcCostReductionGrid" == grid.id){
							grid.SpanRange(row.previousSibling, "itemName", row.lastChild, "itemName");
						}
						grid.SpanRange(row.previousSibling, "partName", row.lastChild, "partName");
						grid.SpanRange(row.previousSibling, "capaRate", row.lastChild, "capaRate");
						grid.SpanRange(row.previousSibling, "investUnit", row.lastChild, "investUnit");
					}else if(row.previousSibling != null){
						grid.SpanRange(row.previousSibling, "partName", row, "partName");
						if("etcCostReductionGrid" == grid.id){
							grid.SpanRange(row.previousSibling, "itemName", row, "itemName");
						}
						grid.SpanRange(row.previousSibling, "capaRate", row, "capaRate");
						grid.SpanRange(row.previousSibling, "investUnit", row, "investUnit");
					}
				}
			}
		}
	}
	
	window.addGridRow = function(grid, row){
		
		var nRow = null;
		
		nRow = grid.AddRow(row, null, true);
		
		grid.SetValue(nRow, "PANEL", "Delete", 1);
		
		grid.SetValue(nRow, "partName", row.partName, 1);
		grid.SetValue(nRow, "costType", row.costType, 1);
		grid.SetValue(nRow, "salesQty", row.salesQty, 1);
		grid.SetValue(nRow, "massQty", row.massQty, 1);
		grid.SetValue(nRow, "addQty", row.addQty, 1);
		grid.SetValue(nRow, "salesReduceQty", row.salesReduceQty, 1);
		grid.SetValue(nRow, "normalReduceQty", row.normalReduceQty, 1);
		grid.SetValue(nRow, "cpOid", row.cpOid, 1);
		
		grid.SetValue(nRow, "itemNameFormat", "-", 1);
		grid.SetValue(nRow, "investCostFormat", "-", 1);
		grid.SetValue(nRow, "investPayFormat", "-", 1);
		grid.SetValue(nRow, "capaRateFormat", "-", 1);
		grid.SetValue(nRow, "investUnitFormat", "-", 1);
		grid.SetValue(nRow, "reduceCodeEnum", "${reduceCodeEnumNames}", 1);
		grid.SetValue(nRow, "reduceCodeEnumKeys", "${reduceCodeEnumKeys}", 1);
		
		grid.SpanRange(row, "costTypeDisplay", nRow, "costTypeDisplay");
		/* grid.SpanRange(row, "investCost", nRow, "investCost");
		grid.SpanRange(row, "investPay", nRow, "investPay"); */
		var spanRow = row;
		if(row.costType == "M") spanRow = row.previousSibling;
		grid.SpanRange(spanRow, "itemName", nRow, "itemName");
		grid.SpanRange(spanRow, "partName", nRow, "partName");
		
		grid.RefreshRow(nRow);
	}
	
	
	window.reduceReCalc = function(){
		if(confirm("현재 설정된 감가비 내역을 모두 삭제 후 감가비 재계산 및\r\n원가 재산출을 진행합니다.\r\n계속하시겠습니까?")){
			
			param = {
				    oid : "${oid}"
		    };

		    ajaxCallServer("/plm/ext/cost/reCalcReduceCost", param, function(data){
		    	if(opener){
                    opener.initializePage();
                }
		    	
		    	var G = Grids[0];
		        G.Reload();
		    });
		}
	}
	
	var tab = CommonUtil.tabs('tabs');
	$('.tabContent').hide();
	$('.tabContent :first').show();
	
	$(".tabref").click(function(){
		$(".tabContent").hide();
		var ref = $(this).attr("href");
		$(ref).show();
	});
	
	$(".tabContent").height($(window).height() - 150);
	$(window).resize(function(){
		$(".tabContent").height($(window).height() - 150);
	});
});
</script>
<body class="popup-background popup-space">
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />원가 산출 기준 (감가비)
		</div>
		
		<div class="b-space5 float-r" style="text-align: right;">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<c:if test="${!isBasePart}">
        <div class="b-space5 float-r" style="text-align: right;">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:reduceReCalc();" class="btn_blue">감가비 재계산</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        </c:if>
	</div>
	<div id="tabs">
			<ul>
				<li><a class="tabref" href="#MOLD">금형</a></li>
				<li><a class="tabref" href="#FAC">설비</a></li>
				<li><a class="tabref" href="#ETC">기타</a></li>
			</ul>
			<div id="MOLD" class="tabContent"></div> 
			<div id="FAC" class="tabContent"> </div>
			<div id="ETC" class="tabContent"></div>
		</div>
</body>
