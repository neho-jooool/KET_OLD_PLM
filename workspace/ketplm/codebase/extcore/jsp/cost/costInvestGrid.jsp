<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.GOboldCell{
	font-weight:bold;
}
</style>
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<script>
var costInvestGrid = null;
$(document).ready(function(){
	var columnList = new Array();
	//####################################### 제품정보 ##################################################################
	columnList.push({LABEL : "구분", KEY : "investTypeDisplay", Spanned : 1, LEVEL : 1, Align : "Center", Width : 100 });
	columnList.push({LABEL : "품명", KEY : "partName", Spanned : 1, LEVEL : 1, RelWidth : 1 });
	columnList.push({LABEL : "투자비 정보", KEY : "INVESTINFO", LEVEL : 1, COLSPAN : 4 });
	columnList.push({LABEL : "투자비", KEY : "investCost", LEVEL : 2, RelWidth : 1, Type : "Int", Format : "###,##0,.# 천원"});
	columnList.push({LABEL : "수량", KEY : "investUnit", LEVEL : 2, RelWidth : 1, Type : "Int", Format : "0," });
	columnList.push({ LABEL : "투자금액", KEY : "investPrice", LEVEL : 2, RelWidth : 1, Type : "Int", Format : "###,##0,.# 천원" });
	columnList.push({ LABEL : "감가조건", KEY : "reduceCode", Enum : "${reduceCodeEnum}", EnumKeys : "${reduceCodeEnumKeys}", Type : "Enum", ENUMRange : 1, LEVEL : 1, Width : 50, Align : "Center" });
	columnList.push({ LABEL : "비고", KEY : "investNote", LEVEL : 1, MinWidth : 200, RelWidth : 1 });
	
	var columnProp = generateGridColumn(columnList);
	var topbarFunc = "";
	if("${lastest}" != "true"){
		topbarFunc = "Reload";
	}
	var editMode = "${EDITMODE}";
	
	if(editMode == 'VIEW_EXCEL' || editMode == 'EDIT'){
		topbarFunc = "Reload,Export";
	}
	var costGridProp = {
		Debug : 0,
		Data : {
			Url : '/plm/ext/cost/costInvestGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				oid : "${oid}",
				taskOid : "${taskOid}",
				caseOrder : "${caseOrder}"
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
					id : "INVESTMENT_COST",
					Style : gridStyle,
					SuppressCfg : 1,
					IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : 0,
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
					MainCol : "partTypeName",
					SuppressMessage : 1,
					FastColumns : 1
				},
				Toolbar : { Visible : 0 },
				LeftCols : columnProp.leftCols,
				Cols : columnProp.cols,
				Head : [{
					Kind : "Topbar",
					Cells : topbarFunc,
					Styles : 2,
					Calculated : 1,
					Link : 0,
				},{
					Kind : "Header",
					id : "Header",
					Visible : 0
				},
				columnProp.kHeader1Lv,
				columnProp.kHeader2Lv,
				],
				Body : [ { Pos : 0 } ],
			}
		}
	}

	costInvestGrid = TreeGrid(costGridProp, 'costInvestGrid');
	
	window.footFormula = function(result){
		
		if(isNaN(result) || !isFinite(result)){
			result = 0;
		}
		if(result == ''){
			result = 0;
		}
		
		return result;
    }
	
	Grids.OnRenderFinish = function(grid){
		if(typeof(parent.iframeCalcHeight) == "function") {
            $(parent.iframeCalcHeight("INVESTMENT_COST", window.document.body.scrollHeight));
        }
	}
});
</script>
<html>
<body style="margin:0 !important;">
<div id='costInvestGrid' style='width:100%;height:100%;'></div>
</body>
</html>
