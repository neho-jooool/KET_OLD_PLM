<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<style>
.red {color: #F00;}
.blue {color: #00F;}
.compareBtn {
  width:100%;height:100%;
  background: #CCFFFF;
  text-decoration: none;
  cursor:pointer;
  border:0px;
}
.compareBtn:hover {
  background: #99FFFF;
  text-decoration: none;
}
.headerDiv{text-align:center;line-height:18px;}
</style>
<script>
$(document).ready(function(){
	
	CommonUtil.singleSelect("productNo", 300);
	
	var columnList = [];
	//####################################### 제품정보 ##################################################################
	columnList.push({LABEL : "No", KEY : "RNUM", LEVEL : 1, Align : "Center", Width : 30 });
	//columnList.push({LABEL : "부품번호", KEY : "PARTNO", LEVEL : 1, Align : "Center" });
	columnList.push({LABEL : "산출 정보", KEY : "CALCINFO", LEVEL : 1, COLSPAN : 6 });
	columnList.push({LABEL : "<div class='headerDiv'>산출<p/>단계</div>", KEY : "CALCTYPE", Width : 50, LEVEL : 2, Type : "Text", Align : "Center" });
	columnList.push({LABEL : "<div class='headerDiv'>배포<p/>기준</div>", KEY : "PUBSTD", LEVEL : 2, Width : 50, Type : "Text", Align : "Center"  });
	columnList.push({LABEL : "<div class='headerDiv'>DR<p/>단계</div>", KEY : "DRSTEP", LEVEL : 2, Width : 50, Type : "Text", Align : "Center"  });
	columnList.push({LABEL : "REV.", KEY : "REV", LEVEL : 2, Width : 60, Type : "Text", Align : "Center"  });
	columnList.push({LABEL : "<div class='headerDiv'>산출<p/>일자</div>", KEY : "CALCDATE", LEVEL : 2, Width : 80, Type : "Text", Align : "Center" });
	columnList.push({LABEL : "원가 정보", KEY : "COSTINFO", LEVEL : 1, COLSPAN : 13 });
	columnList.push({LABEL : "KET품", KEY : "KET", LEVEL : 2, COLSPAN : 4 });
	columnList.push({ LABEL : "<div class='headerDiv'>판매<p/>목표가</div>", KEY : "KETSALESTARGET", LEVEL : 3, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "총원가", KEY : "KETTOTALCOST", LEVEL : 3, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "수익률", KEY : "KETPROFITRATE", LEVEL : 3, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({LABEL : "지정품", KEY : "SUB", LEVEL : 2, COLSPAN : 4 });
	columnList.push({ LABEL : "<div class='headerDiv'>판매<p/>목표가</div>", KEY : "SUBSALESTARGET", LEVEL : 3, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "총원가", KEY : "SUBTOTALCOST", LEVEL : 3, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "수익률", KEY : "SUBPROFITRATE", LEVEL : 3, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({LABEL : "합계", KEY : "TOTAL", LEVEL : 2, COLSPAN : 4 });
	columnList.push({ LABEL : "<div class='headerDiv'>판매<p/>목표가</div>", KEY : "SALESTARGET", LEVEL : 3, Color : "#FFFFCC", RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "총원가", KEY : "TOTALCOST", LEVEL : 3, Color : "#FFFFCC", RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "수익률", KEY : "PROFITRATE", LEVEL : 3, Color : "#FFFFCC", RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({LABEL : "투자비 정보", KEY : "INVEST", LEVEL : 1, COLSPAN : 7 });
	columnList.push({ LABEL : "MOLD", KEY : "MOLDINVEST", LEVEL : 2, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "PRESS", KEY : "PRESSINVEST", LEVEL : 2, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "<div class='headerDiv'>조립<p/>설비</div>", KEY : "EQUIPINVEST", LEVEL : 2, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "<div class='headerDiv'>구매<p/>금형비</div>", KEY : "PURMOLDINVEST", LEVEL : 2, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "<div class='headerDiv'>기타<p/>투자비</div>", KEY : "ETCINVEST", LEVEL : 2, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "합계", KEY : "TOTALINVEST", Color : "#FFFFCC", LEVEL : 2, RelWidth : 1, Type : "Float", Format : "###,##0,###.#", Formula : "valueFormula(Row,Col)", CanEmpty : 1, EmptyValue : "-" });
	columnList.push({ LABEL : "<div class='headerDiv'>개발원가<p/>보고서</div>", KEY : "DEVCOSTREPORT", LEVEL : 1, Width : 60, Align : "Center" });
	
	var columnProp = generateGridColumn(columnList);
	
	var costGridProp = {
		Debug : 0,
		  Data : {
			Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
			Method : 'POST',
			Format : 'JSON',
			Timeout : 0
		},
		Layout : {
			Data : {
				Cfg : {
					id : "costHistoryGrid",
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
					Selecting : 1,
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
					SuppressMessage : 1,
					FastColumns : 1,
				},
				Toolbar : { Visible : 0 },
				LeftCols : columnProp.leftCols,
				Cols : columnProp.cols,
				Panel : {
					Width : '20', Visible : '1',CanHide : '0'
				},
				Head : [
		        {
					Kind : "Header",
					id : "Header",
					Visible : 0
				},
				columnProp.kHeader1Lv,
				columnProp.kHeader2Lv,
				columnProp.kHeader3Lv,
				columnProp.kHeader4Lv,
				{
                    Kind : "Filter",
                    id : "Filter",
                    CALCTYPERange : 1,
                    CALCTYPEShowMenu : 0,
                    CALCTYPEButton : "Defaults",
                    CALCTYPEDefaults : "|*FilterOff|*Rows",
                    PUBSTDRange : 1,
                    PUBSTDShowMenu : 0,
                    PUBSTDButton : "Defaults",
                    PUBSTDDefaults : "|*FilterOff|*Rows",
                    DRSTEPRange : 1,
                    DRSTEPShowMenu : 0,
                    DRSTEPButton : "Defaults",
                    DRSTEPDefaults : "|*FilterOff|*Rows",
                    RNUMVisible : 0,
                    REVVisible : 0,
                    CALCDATEVisible : 0,
                    KETSALESTARGETVisible : 0,
                    KETTOTALCOSTVisible : 0,
                    KETPROFITRATEVisible : 0,
                    SUBSALESTARGETVisible : 0,
                    SUBTOTALCOSTVisible : 0,
                    SUBPROFITRATEVisible : 0,
                    SALESTARGETVisible : 0,
                    TOTALCOSTVisible : 0,
                    PROFITRATEVisible : 0,
                    MOLDINVESTVisible : 0,
                    PRESSINVESTVisible : 0,
                    EQUIPINVESTVisible : 0,
                    PURMOLDINVESTVisible : 0,
                    ETCINVESTVisible : 0,
                    TOTALINVESTVisible : 0,
                    DEVCOSTREPORTVisible : 0,
                },
				],
				Body : [ { Pos : 0 } ],
				Foot : [
					{
						Spanned : 1,
						RNUMSpan : 8,
						RNUM : "원가상세비교",
						RNUMOnClick : 'javascript:openCostAnalysis(Grid);',
						RNUMColor : "#CCFFFF",
						DEVCOSTREPORTVisible : 0
					}
		        ]
			}
		}
	}

	costHistoryGrid = TreeGrid(costGridProp, 'costHistoryGrid');
	
	window.search = function(){
		
		var productNo = $("select[name=productNo]").val();
		var isSetTotal = (productNo == "SETTOTAL");
		
		if(isSetTotal){
			productNo = "${allPartNo}";
		}
		
		Grids[0].Source.Data.Url = '/plm/ext/cost/costHistoryData';
        Grids[0].Source.Data.Param.oid = "${oid}";
        Grids[0].Source.Data.Param.isSetTotal = isSetTotal;
        Grids[0].Source.Data.Param.productNo = productNo;
        Grids[0].Reload();
	}
	
	window.valueFormula = function(row, col){
		
		if(row[col] == 0){
			Grids[0].SetAttribute(row, col, "Align", "Center", 1);
			return null;
		}
		
		return row[col];
	}
	
	window.footFormula = function(result){
		
		if(isNaN(result) || !isFinite(result)){
			result = 0;
		}
		if(result == ''){
			result = 0;
		}
		
		return result;
	}
	
	Grids.OnSelect = function(grid, row, deselect, cols){
		
		var rows = grid.GetSelRows();
        if( rows.length > 1 && !deselect ){
            alert('두개의 정보만 선택 가능합니다.');
            return 1;
        }
        
        var bRow = rows[0];
        var aRow = row;
        
        if(deselect) aRow = null;
        
        if(bRow != null && aRow != null && bRow.RNUM > aRow.RNUM){
        	var tempRow = bRow;
        	bRow = aRow;
        	aRow = tempRow;
        }
        
        costCompare(grid, bRow, aRow);
	}
	
	Grids.OnRenderFinish = function(grid){
		
		var rows = grid.Rows;
        var rowIds = Object.keys(rows);
        
        for(var i = 0; i < rowIds.length; i++){
        	
            var row = rows[rowIds[i]];
            
            if(row.Kind == "Data"){
            	
            	var html = "<img src='/plm/portal/images/icon_5.png' style='cursor:pointer;border:0;' " +
           					"onclick='openCostReport(\"" + row.TASKOID+ "\")' />";
            	
            	grid.SetAttribute(row, "DEVCOSTREPORT", "HtmlPrefix", html, 1);
            	
            	var SUBTOTALCOST = row.SUBTOTALCOST;
            	
            	if (null == SUBTOTALCOST || 0 == SUBTOTALCOST) {
            		grid.SetValue(row, "KETSALESTARGET", null, 1);
            		grid.SetValue(row, "KETTOTALCOST", null, 1);
            		grid.SetValue(row, "KETPROFITRATE", null, 1);
            		grid.SetValue(row, "SUBSALESTARGET", null, 1);
            		grid.SetValue(row, "SUBTOTALCOST", null, 1);
            		grid.SetValue(row, "SUBPROFITRATE", null, 1);
            		
            		grid.SetAttribute(row, "KETSALESTARGET", "Changed", "0", 1);
            		grid.SetAttribute(row, "KETTOTALCOST", "Changed", "0", 1);
            		grid.SetAttribute(row, "KETPROFITRATE", "Changed", "0", 1);
            		grid.SetAttribute(row, "SUBSALESTARGET", "Changed", "0", 1);
            		grid.SetAttribute(row, "SUBTOTALCOST", "Changed", "0", 1);
            		grid.SetAttribute(row, "SUBPROFITRATE", "Changed", "0", 1);
            		
            		grid.SetAttribute(row, "KETSALESTARGET", "Align", "Center", 1);
            		grid.SetAttribute(row, "KETTOTALCOST", "Align", "Center", 1);
            		grid.SetAttribute(row, "KETPROFITRATE", "Align", "Center", 1);
            		grid.SetAttribute(row, "SUBSALESTARGET", "Align", "Center", 1);
            		grid.SetAttribute(row, "SUBTOTALCOST", "Align", "Center", 1);
            		grid.SetAttribute(row, "SUBPROFITRATE", "Align", "Center", 1);
            	}
            	
            	if (null == row.KETSALESTARGET || 0 == row.KETSALESTARGET || null == row.KETTOTALCOST || 0 == row.KETTOTALCOST) {
            		grid.SetValue(row, "KETPROFITRATE", null, 1);
					grid.SetAttribute(row, "KETPROFITRATE", "Changed", "0", 1);
            		grid.SetAttribute(row, "KETPROFITRATE", "Align", "Center", 1);
            	}
            	if (null == row.SUBSALESTARGET || 0 == row.SUBSALESTARGET) {
            		grid.SetValue(row, "SUBPROFITRATE", null, 1);
            		grid.SetAttribute(row, "SUBPROFITRATE", "Changed", "0", 1);
            		grid.SetAttribute(row, "SUBPROFITRATE", "Align", "Center", 1);
            	}
            	if (null == row.SALESTARGET || 0 == row.SALESTARGET || null == row.TOTALCOST || 0 == row.TOTALCOST) {
            		grid.SetValue(row, "PROFITRATE", null, 1);
            		grid.SetAttribute(row, "PROFITRATE", "Changed", "0", 1);
            		grid.SetAttribute(row, "PROFITRATE", "Align", "Center", 1);
            	}
            }
        }
	}
	
	window.costCompare = function(grid, bRow, aRow){
		
		var foot = grid.Foot.firstChild;
		
		var colKeys = [
			"KETSALESTARGET",
			"KETTOTALCOST",
			"KETPROFITRATE",
			"SUBSALESTARGET",
			"SUBTOTALCOST",
			"SUBPROFITRATE",
			"SALESTARGET",
			"TOTALCOST",
			"PROFITRATE",
			"MOLDINVEST",
			"PRESSINVEST",
			"EQUIPINVEST",
			"PURMOLDINVEST",
			"ETCINVEST",
			"TOTALINVEST"
        ];
		
		if(aRow != null && bRow != null){
			for(var i = 0; i < colKeys.length; i++){
				var key = colKeys[i];
				var value = aRow[key] - bRow[key];
				
				if(key.indexOf("PROFITRATE") >= 0){
					if(value > 0){
						grid.SetAttribute(foot, key, "HtmlPrefix", "<span class='blue'>▲", 1);
						grid.SetAttribute(foot, key, "HtmlPostfix", "</span>", 1);
					}else if (value < 0){
						grid.SetAttribute(foot, key, "HtmlPrefix", "<span class='red'>▼", 1);
						grid.SetAttribute(foot, key, "HtmlPostfix", "</span>", 1);
					}
				}else{
					if(value > 0){
						grid.SetAttribute(foot, key, "HtmlPrefix", "<span class='red'>▲", 1);
						grid.SetAttribute(foot, key, "HtmlPostfix", "</span>", 1);
					}else if (value < 0){
						grid.SetAttribute(foot, key, "HtmlPrefix", "<span class='blue'>▼", 1);
						grid.SetAttribute(foot, key, "HtmlPostfix", "</span>", 1);
					}
				}
				
				value = Math.abs(value);
				grid.SetValue(foot, key, value, 1);
				grid.SetAttribute(foot, key, "Changed", "0", 1);
				
				if(value == 0){
					grid.SetAttribute(foot, key, "Align", "Center", 1);
				}else{
					grid.SetAttribute(foot, key, "Align", "Right", 1);
				}
			}
		}else{
			for(var i = 0; i < colKeys.length; i++){
				var key = colKeys[i];
				grid.SetValue(foot, key, null, 1);
				grid.SetAttribute(foot, key, "Changed", "0", 1);
	    		grid.SetAttribute(foot, key, "Align", "Center", 1);
	    		grid.SetAttribute(foot, key, "HtmlPrefix", null, 1);
				grid.SetAttribute(foot, key, "HtmlPostfix", null, 1);
			}
		}
	};

	window.openCostAnalysis = function(grid){
		
		if($("#productNo").val() == "SETTOTAL") {
			alert('setTotal일때는 제공되지 않는 기능입니다.\n\r제품을 선택하세요.');
			return;
		}
		
		var rows = grid.GetSelRows();
        
		if( rows.length < 2){
            alert('비교할 정보를 선택하세요.');
            return 1;
        }
        
        var partOid = rows[0].OID;
        var rPartOid = rows[1].OID;
        
		getOpenWindow2("/plm/ext/cost/costProfitAnalysis?partOid=" + partOid + "&rPartOid=" + rPartOid, "ProfitAnalysis", 1280, 720);
	}
	window.openCostReport = function(taskOid){
		getOpenWindow2('/plm/ext/cost/costReportPopup?taskOid=' + taskOid+"&authProjectCheckOid=${oid}", 'COSTREPORT', 1280, 720);
	}
	
	window.excelDownload = function(){
		
		var productNo = $("select[name=productNo]").val();
		
		if(productNo == null){
			alert("제품을 선택하세요.");
			return;
		}else{
			productNo = productNo.join(",");
		}
		
		var param = {
				productNo : productNo,
				oid : "${oid}"
		}
	
		ajaxCallServer("/plm/ext/dms/downloadDocFileZip", param, function(data){
            //location.href = data.downloadLink;
            $("#download")[0].src = data.downloadLink;
        });
	}
	$("#productNo").change(function(){
		search();
		$("#chartFrame").attr("src", "/plm/ext/cost/viewCostChart?oid=${oid}&productNo=" + $(this).val());
	});
	search();
	
});
</script>
<c:if test="${not empty ERROR}">
<script>alert("${ERROR}");self.close();</script>
</c:if>
<body class="popup-background popup-space">
<div class="contents-wrap">
	<div class="sub-title b-space5">
		<img src="/plm/portal/images/icon_3.png" />개발원가이력 분석도구
	</div>
	<div class="b-space5 float-l" >
		<select name="productNo" id="productNo" style="width:300px;" multiple="multiple" class="v-middle">
			<option value="SETTOTAL" selected>SetTotal</option>
		<c:forEach items="${productList }" var="product" >
			<option value="${product.partNo }">${product.partName }</option>
		</c:forEach>
		</select>
		<!-- <img src="/plm/portal/images/iocn_excel.png" style="cursor:pointer;" onclick="excelDownload()" /> -->
	</div>
    <div class="b-space5 float-r" style="text-align: right;">
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></span>
				<span class="pro-cell b-right"></span>
			</span>
		</span>
	</div>
	<br/><br/><br/>
	<iframe id="chartFrame" src="/plm/ext/cost/viewCostChart?oid=${oid}&productNo=SETTOTAL" style="border:0;width:100%;z-index:-1;"></iframe>
	<br/><br/>
	<div id='costHistoryGrid' style='width:100%;'></div>
</div>

<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
</body>
