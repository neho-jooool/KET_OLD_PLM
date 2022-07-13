<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<STYLE>
.resFormula{
	font-weight:bold;
	font-size:14px;
	color:#FF0000;
	padding-right:20px;
	position:absolute;
	right:0;
	z-index:99999;
}
.GOnoBorder{
	border:0;
}
.headerDiv{text-align:center;line-height:18px;}
</STYLE>
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<script>
var ${COSTTYPE}costGrid = null;

$(document).ready(function(){
	var columnList = new Array();
	//####################################### 제품정보 ##################################################################
	columnList.push({LABEL : "제품정보", KEY : "PROD", LEVEL : 1, COLSPAN : 6, LEFTCOLS : true });
	columnList.push({LABEL : "구분", KEY : "partTypeName", LEVEL : 2, CanEdit : 0, CanSelect : 0, LEFTCOLS : true});
	columnList.push({LABEL : "partType", KEY : "partType", LEVEL : 2, Visible : 0, LEFTCOLS : true });
	columnList.push({LABEL : "레벨", KEY : "Level", Align : "Center", LEVEL : 2, CanEdit : 0, CanSelect : 0, LEFTCOLS : true});
	columnList.push({ LABEL : "품번", KEY : "partNo", LEVEL : 2, CanEdit : 0, CanSelect : 0, LEFTCOLS : true });
	columnList.push({ LABEL : "품명", KEY : "partName", LEVEL : 2, LEFTCOLS : true, CanEdit : 0, CanSelect : 0 });
	columnList.push({ LABEL : "<div class='headerDiv'>U/S<p/>(EA)<p/>(mm)</div> ", KEY : "us", LEVEL : 1, Type : "Int", Format : "###,##0", LEFTCOLS : true,  CanEdit : 0, CanSelect : 0 });
	
    //####################################### 생산정보 ##################################################################
	
	if("${COSTTYPE}" == "CAPA"){
		columnList.push({ LABEL : "생산정보", KEY : "MFT", LEVEL : 1, COLSPAN : 7, LEFTCOLS : true });
		columnList.push({ LABEL : "C/V", KEY : "CV", LEVEL : 2, COLSPAN : 3, LEFTCOLS : true });
		columnList.push({ LABEL : "금형", KEY : "cvMold", LEVEL : 3, LEFTCOLS : true, Width : 90, Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "설비", KEY : "cvAssemble", LEVEL : 3, LEFTCOLS : true, Width : 90, Type : "Float", Format : "###,##0.00"  });
		columnList.push({ LABEL : "C/T SPM", KEY : "CTSPM", LEVEL : 2, LEFTCOLS : true, COLSPAN : 3 });
		columnList.push({ LABEL : "금형", KEY : "ctSPMMold", LEVEL : 3, LEFTCOLS : true, Width : 90, Type : "Float", Format : "###,##0.00"  });
		columnList.push({ LABEL : "설비", KEY : "ctSPMAssemble", LEVEL : 3, LEFTCOLS : true, Width : 90, Type : "Float", Format : "###,##0.00" });
	}else{
	}

	if("${COSTTYPE}" == "CAPA"){
		columnList.push({ LABEL : "생산량 [/HR]", KEY : "outputExpr", LEVEL : 1, LEFTCOLS : true, CanEdit : 0, CanSelect : 0, Width : 90, Type : "Float", Format : "###,##0.00"});
	}else{
		var calcStd = "";
		var calcOption = "";
		switch("${COSTTYPE}"){
			case "MATERIAL_COST" :
				calcStd = "calcStdMaterial";
				calcOption = "calcOptionMaterial";
				break;
			case "LABOR_COST" :
				calcStd = "calcStdLabor";
				calcOption = "calcOptionLabor";
				break;
			case "EXPENSE" :
				calcStd = "calcStdExpense";
				break;
			case "MAINTENENCE_COST" :
				calcStd = "calcStdManage";
				calcOption = "calcOptionManage";
				break;
			default :
				break;
		}
		
		columnList.push({ LABEL : "산출기준", KEY : calcStd, LEVEL : 1, LEFTCOLS : true, Type : "Enum", CanEmpty : 1, Width : 90, EnumKeys : "${calcStdKeys}", Enum : "${calcStdNames}", Clear : calcOption });
		
		if("${COSTTYPE}" == "MATERIAL_COST" || "${COSTTYPE}" == "MAINTENENCE_COST" || "${COSTTYPE}" == "LABOR_COST"){
			var calcOptionColumn = { LABEL : "산출옵션", KEY : calcOption, LEVEL : 1, LEFTCOLS : true, Range : 1, Type : "Enum", Width : 90, CanEmpty : 1, Related : calcStd };
	        
	       var calcOptionProp = ${calcOptionProp};
	        var keys = Object.keys(calcOptionProp);
	        
	        for(var i = 0; i < keys.length; i++){
	            var key = keys[i];
	            var value = calcOptionProp[key];
	            
	            calcOptionColumn[key] = value;
	        }
	        
	        columnList.push(calcOptionColumn);
		}else if("${COSTTYPE}" == "EXPENSE"){
			var calcOptionColumn = { LABEL : "<div class='headerDiv'>재료비<p/>산출옵션</div>", KEY : "coMaterialNames", LEVEL : 1, Width : 90, LEFTCOLS : true,  CanEdit : 0, CanSelect : 0 };
			columnList.push(calcOptionColumn);
		}
	}
	 
	//####################################### 재료비 ##################################################################
	if("${COSTTYPE}" == "MATERIAL_COST"){
		columnList.push({ LABEL : "재료비", KEY : "재료비", LEVEL : 1, COLSPAN : 11 });
		
		columnList.push({ LABEL : "기준정보", KEY : "기준_정보", LEVEL : 2,COLSPAN : 3 });
		
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='productLossRateLocked'/>생산Loss율", KEY : "productLossRate", LEVEL : 3,  Type : "Float", Format : "0.0%"});
        columnList.push({ LABEL : "TEMP", KEY : "TEMP", LEVEL : 3, Width : 0 });
		
		columnList.push({ LABEL : "재료비 산출", KEY : "재료비_산출", LEVEL : 2,COLSPAN : 7 });
		
		/* 
		columnList.push({ LABEL : "원재료관세율", KEY : "mtrLtRate", LEVEL : 2, Width : 90, Type : "Float", Format : "0.00%" });
		columnList.push({ LABEL : "원재료물류비용", KEY : "mtrLtCost", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00" });
		 */
		columnList.push({ LABEL : "원재료비", KEY : "원재료비", LEVEL : 3,COLSPAN : 3 });
	    columnList.push({ LABEL : "<div class='headerDiv'>후도금비<p/>(옵션)</div>", KEY : "apUnitCostOption", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
	    columnList.push({ LABEL : "<div class='headerDiv'>포장비<p/>(옵션)</div>", KEY : "packUnitPriceOption", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "원재료비", KEY : "rawMaterialCostExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "생산 Loss", KEY : "productLossExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "스크랩판매비", KEY : "scrapSalesCostExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		
		
		
		columnList.push({ LABEL : "<div class='headerDiv'>재료비<p/>합계</div>", KEY : "materialCostExpr", LEVEL : 1, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
	}

	//####################################### 노무비 ##################################################################
	if("${COSTTYPE}" == "LABOR_COST"){
		
		columnList.push({ LABEL : "금형준비공수", KEY : "금형준비공수", LEVEL : 1, COLSPAN : 3 });
		columnList.push({ LABEL : "생산보유개월", KEY : "productHaveMonth", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0});
		columnList.push({ LABEL : "금형준비시간", KEY : "moldReadyTime", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		
		columnList.push({ LABEL : "추가공수1", KEY : "추가공수1", LEVEL : 1, COLSPAN : 6 });
		columnList.push({ LABEL : "CT", KEY : "addManHourCt_1", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "효율", KEY : "addManHourEff_1", LEVEL : 2, Width : 90, Type : "Float", Format : "0.00%", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "임율", KEY : "addManHourLb_1", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "임율상승율", KEY : "addManHourClb_1", LEVEL : 2, Width : 90, Type : "Float", Format : "0.00%", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "임율년도", KEY : "addManHourYear_1", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0", CanEdit : 0, CanSelect : 0 });
		
		columnList.push({ LABEL : "추가공수2", KEY : "추가공수2", LEVEL : 1, COLSPAN : 6 });
		columnList.push({ LABEL : "CT", KEY : "addManHourCt_2", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
        columnList.push({ LABEL : "효율", KEY : "addManHourEff_2", LEVEL : 2, Width : 90, Type : "Float", Format : "0.00%", CanEdit : 0, CanSelect : 0 });
        columnList.push({ LABEL : "임율", KEY : "addManHourLb_2", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
        columnList.push({ LABEL : "임율상승율", KEY : "addManHourClb_2", LEVEL : 2, Width : 90, Type : "Float", Format : "0.00%", CanEdit : 0, CanSelect : 0 });
        columnList.push({ LABEL : "임율년도", KEY : "addManHourYear_2", LEVEL : 2, Type : "Int", Format : "###,##0", CanEdit : 0, CanSelect : 0});
        
		columnList.push({ LABEL : "노무비", KEY : "노무비", LEVEL : 1, COLSPAN : 12 });
		columnList.push({ LABEL : "기준 정보", KEY : "기준_정보", LEVEL : 2, COLSPAN : 6 });
		//생산효율, 관리대수는 변경가능한 항목이 아니나 과거기준 산출이 필요함에 따라 일단 블러킹 대상 항목으로 추가함 2019.08.28 by 황정태 (이경무 요청)
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='efficientRateLocked'/>생산효율", KEY : "efficientRate", LEVEL : 3,  Type : "Float", Format : "###,##0.0%" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='laborCostRateLocked'/>표준임율", KEY : "laborCostRate", LEVEL : 3,  Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='laborCostClimbRateLocked'/>표준임율상승율", KEY : "laborCostClimbRate", LEVEL : 3, Type : "Float", Format : "0.00%" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='laborCostYearLocked'/>표준임율년도", KEY : "laborCostYear", LEVEL : 3,  Type : "Int", Format : "###,##0" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='unitManageLocked'/>관리대수", KEY : "unitManage", LEVEL : 3,  Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "노무비 산출", KEY : "노무비_산출", LEVEL : 2, COLSPAN : 5 });
		columnList.push({ LABEL : "생산/조립", KEY : "moldProductAssyExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "금형준비", KEY : "moldPrepareExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "추가공수1", KEY : "laborExpenseAdd1Expr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "추가공수2", KEY : "laborExpenseAdd2Expr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		
		columnList.push({ LABEL : "<div class='headerDiv'>노무비<p/>합계</div>", KEY : "laborCostExpr", LEVEL : 1, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
	}
	
	
	//####################################### 경비 ##################################################################
	if("${COSTTYPE}" == "EXPENSE"){
		
		columnList.push({ LABEL : "경비", KEY : "경비", LEVEL : 1, COLSPAN : 23 });
		columnList.push({ LABEL : "기준 정보", KEY : "기준_정보", LEVEL : 2, COLSPAN : 10 });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='elecCostLocked'/>표준전력비", KEY : "elecCost", LEVEL : 3, Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "<div class='headerDiv'><input type='checkbox' name='attrLocked' value='elecCostClimbRateLocked'/>표준전력비<p/>(상승율)</div>", KEY : "elecCostClimbRate", LEVEL : 3, Type : "Float", Format : "0.00%" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='elecCostYearLocked'/>표준전력비년도", KEY : "elecCostYear", LEVEL : 3, Type : "Int", Format : "###,##0" });
		
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='tabaryuLocked'/>타발유", KEY : "tabaryu", LEVEL : 3, Type : "Int", Format : "###,##0" });
		
		columnList.push({ LABEL : "<div class='headerDiv'><input type='checkbox' name='attrLocked' value='machineDpcCostLocked'/>기계감가<p/>(사출,프레스)</div>", KEY : "machineDpcCost", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00"});
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='moldMaintenanceLocked'/>금형유지비", KEY : "moldMaintenance", LEVEL : 3, Type : "Float", Format : "###,##0.00" });
		//간접경비비용 기준 정보 추가 이경무 요청 2019.08.28 by 황정태
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='inDirectCostLocked'/>간접경비비용", KEY : "inDirectCost", LEVEL : 3, Type : "Int", Format : ",0" });
		//간접경비율은 변경가능한 항목이 아니나 과거기준 산출이 필요함에 따라 일단 블러킹 대상 항목으로 추가함 2019.08.28 by 황정태 (이경무 요청)
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='indirectCostRateLocked'/>간접경비율", KEY : "indirectCostRate", LEVEL : 3, Type : "Float", Format : "###,##0.0%" });
		//신규항목 추가 Buyback 간접경비율(본사) 이경무 요청 2019.09.03 by 황정태
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='buyBackIndirectCostRateLocked'/>Buyback<p/>간접경비율(본사)", KEY : "buyBackIndirectCostRate", LEVEL : 3, Type : "Float", Format : "###,##0.0%" });
		columnList.push({ LABEL : "경비 산출", KEY : "경비_산출", LEVEL : 2, COLSPAN : 12 });
		columnList.push({ LABEL : "직접경비", KEY : "직접경비", LEVEL : 3, COLSPAN : 9 });
		columnList.push({ LABEL : "전력비", KEY : "elecCostExpr", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "기계감가", KEY : "machineDpcCostExpr", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "<div class='headerDiv'>외주단가<p/>(환율적용)</div>", KEY : "outUnitCostVal", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "타발유", KEY : "tabaryuExpr", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		//그리드에 두개의 동일 컬럼을 쓸 수 없어  moldMaintenance -> moldMaintenanceTemp 로 변경함. 금형유지비는 변경가능한 항목이 아니나 과거기준 산출이 필요함에 따라 일단 블러킹 대상 항목으로 추가함 2019.08.26 by 황정태 (이경무 요청)
		columnList.push({ LABEL : "금형유지비", KEY : "moldMaintenanceTemp", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "<div class='headerDiv'>후도금단가<p/>(환율적용)</div>", KEY : "apUnitCostVal", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "포장비", KEY : "packUnitPriceSum", LEVEL : 4, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "직접경비", KEY : "directCostExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "간접경비(1)", KEY : "inDirectCostExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "간접경비(2)", KEY : "inDirectCost2Expr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "<div class='headerDiv'>경비<p/>합계</div>", KEY : "expenseExpr", LEVEL : 1, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		
	}
	
	//####################################### 관리비 ##################################################################
	if("${COSTTYPE}" == "MAINTENENCE_COST"){
		columnList.push({ LABEL : "관리비", KEY : "관리비", LEVEL : 1, COLSPAN : 28 });
		columnList.push({ LABEL : "기준 정보", KEY : "기준_정보", LEVEL : 2, COLSPAN : 14 });
		
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='assyLossRateLocked'/>조립Los율", KEY : "assyLossRate", LEVEL : 3, Type : "Float", Format : "0.0%" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='mtrManageRateLocked'/>재료관리비율", KEY : "mtrManageRate", LEVEL : 3, Type : "Float", Format : "0.0%" });
		//일반관리비용 기준 정보 추가 이경무 요청 2019.08.28 by 황정태
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='coManageCostLocked'/>일반관리비용", KEY : "coManageCost", LEVEL : 3, Type : "Int", Format : ",0" });
		//일반관리비율은 변경가능한 항목이 아니나 과거기준 산출이 필요함에 따라 일단 블러킹 대상 항목으로 추가함 2019.08.28 by 황정태 (이경무 요청)
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='coManageRateLocked'/>일반관리비율", KEY : "coManageRate", LEVEL : 3,  Type : "Float", Format : "0.0%" });
		//R&D율은 변경가능한 항목이 아니나 과거기준 산출이 필요함에 따라 일단 블러킹 대상 항목으로 추가함 2019.08.30 by 황정태 (이경무 요청)
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='rndLocked'/>R&D비율", KEY : "rnd", LEVEL : 3,  Type : "Float", Format : "0.0%" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='mtrLtCostLocked'/>원재료물류비용", KEY : "mtrLtCost", LEVEL : 3,  Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='mtrLtRateLocked'/>원재료관세율", KEY : "mtrLtRate", LEVEL : 3,  Type : "Float", Format : "0.00%" });
		
		columnList.push({ LABEL : "불량율", KEY : "defectRate", LEVEL : 3,  Type : "Float", Format : "0.00%" });
		
		columnList.push({ LABEL : "법인간마진율", KEY : "corporateMarginRate", LEVEL : 3, Type : "Float", Format : "0.00%"});
		
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='partLtCostLocked'/>공정물류비용", KEY : "partLtCost", LEVEL : 3,  Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='partLtRateLocked'/>공정관세율", KEY : "partLtRate", LEVEL : 3,  Type : "Float", Format : "0.00%" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='payLtCostLocked'/>납입물류비용", KEY : "payLtCost", LEVEL : 3,  Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "<input type='checkbox' name='attrLocked' value='payRateLtLocked'/>납입관세율", KEY : "payRateLt", LEVEL : 3,  Type : "Float", Format : "0.00%" });
		
		columnList.push({ LABEL : "관리비 산출", KEY : "관리비_산출", LEVEL : 2, COLSPAN : 13 });
		columnList.push({ LABEL : "조립Loss", KEY : "assyLossExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "재료관리비", KEY : "mtrManageExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "일반관리비", KEY : "coManageExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "R&D", KEY : "rndExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		
		columnList.push({ LABEL : "원재료물류비", KEY : "manageMtrLogisExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
        columnList.push({ LABEL : "원재료관세", KEY : "manageMtrdutieExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
        columnList.push({ LABEL : "불량비용", KEY : "defectCostExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
        columnList.push({ LABEL : "법인간마진비용", KEY : "corporateMarginCostExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "공정물류비", KEY : "mtrLtCostExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "공정관세", KEY : "mtrLtRateExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "납입물류비", KEY : "payCostLtExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		columnList.push({ LABEL : "납입관세", KEY : "payRateLtExpr", LEVEL : 3, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
		
		columnList.push({ LABEL : "<div class='headerDiv'>관리비<p/>합계</div>", KEY : "manageCostExpr", LEVEL : 1, Width : 90, Type : "Float", Format : "###,##0.00", CanEdit : 0, CanSelect : 0 });
	}
	
	//####################################### Capa ##################################################################
	if("${COSTTYPE}" == "CAPA"){
		columnList.push({ LABEL : "적용조업도", KEY : "적용조업도", LEVEL : 1, COLSPAN : 5 });
		columnList.push({ LABEL : "Hr/日", KEY : "workHour", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00"});
		columnList.push({ LABEL : "日/月", KEY : "workDay", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00" }); 
		columnList.push({ LABEL : "月/年", KEY : "workYear", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "적용년수", KEY : "capaYear", LEVEL : 2, Width : 90, Type : "Float", Format : "###,##0.00"});
		columnList.push({ LABEL : "생산량 (EA/年)", KEY : "eaOutput", LEVEL : 1, CanEdit : 0, CanSelect : 0, Formula : "outputExpr*workHour*workDay*workYear", Width : 90, Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "판매량 (EA/年)", KEY : "quantityMax", LEVEL : 1, CanEdit : 0, CanSelect : 0, Width : 90, Type : "Float", Format : "###,##0.00" });
		columnList.push({ LABEL : "Capa", KEY : "capa", LEVEL : 1, CanEdit : 0, CanSelect : 0, Width : 90, Type : "Float", Formula : "columnFormula(quantityMax/(outputExpr*workHour*workDay*workYear))", Format : "0.00%", Width : 100 });
		columnList.push({ LABEL : "비고", KEY : "capaNote", LEVEL : 1 });
	}
	
	window.columnFormula = function(result){
    	
    	if(isNaN(result) || !isFinite(result)){
            result = 0;
        }
    	if(result == ''){
    		result = 0;
    	}
    	return result;
    }
	
	var columnProp = generateGridColumn(columnList);
	var headCells = "Save,ExpandAll,CollapseAll,Reload,Export";
	var editing = 1;
    if(${isBasePart}) {
    	editing = 0;
    	headCells = "ExpandAll,CollapseAll,Reload,Export";
    }
	
	var costGridProp = {
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/saveCostPart",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
			Param : {
				taskOid : "${taskOid}",
				oid : "${oid}"
			},
			Timeout : 0
		},
		Data : {
			Url : '/plm/ext/cost/costTreeGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				oid : "${oid}",
				taskOid : "${taskOid}",
				EDITMODE : "NONPOPUP",
				DATATYPE : "COSTPART"
			},
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
					id : "${COSTTYPE}",
					Style : gridStyle,
					SuppressCfg : 1,
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
					MainCol : "partTypeName",
					SuppressMessage : 1,
					FastColumns : 1,
				},
				Toolbar : { Visible : 0 },
				LeftCols : columnProp.leftCols,
				Cols : columnProp.cols,
				Head : [{
					Kind : "Topbar",
					Cells : headCells,
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
				columnProp.kHeader3Lv,
				columnProp.kHeader4Lv
				],
				Body : [ { Pos : 0 } ],
			}
		}
	}
	costGrid = TreeGrid(costGridProp, '${COSTTYPE}costGrid');
	
	Grids.OnClickCell = function(grid, row, col, e){
		
		if(row.Kind == "Header"){
			
			$("input[name='attrLocked']").each(function(){
				var value = $(this).val();
				if(value == col + "Locked"){
					var chk = $(this).is(":checked");
					$(this).prop("checked", !chk);
				}
			});
			
		}else if(row.Kind == "Data"){
			
			var param = {
				    oid : row.id,
				    code : col
			}
			
			var data = ajaxCallServer("/plm/ext/cost/getAttrCaluateFormula", param, null, false);
            
			//기계감가, 포장비 가 아닐때 디버깅 표시
            if(data.keyFormula != null && col != "machineDpcCostExpr" && col != "packUnitPriceSum"){
                var resultHTML = checkNull(data.keyFormula) + "<br/>" 
                 + "실계산식 : " + checkNull(data.resultExp);
                $("." + grid.id + "resFormula").html(resultHTML);
            }else{
                $("." + grid.id + "resFormula").html("");
            }
		}
	}
	
	Grids.OnEndEdit = function(grid, row, col, save, val, raw){
		
		if(val == null) val = "";
		
	    if(grid.id == "LABOR_COST" && col == "calcOptionLabor" ){
           	//금형준비공수 활성화
           	if(val.indexOf("CALCSTD016") >= 0){
           		grid.SetAttribute(row, "productHaveMonth", "CanEdit", 1, 1);
           		grid.SetAttribute(row, "moldReadyTime", "CanEdit", 1, 1);
           	}else{
           		grid.SetAttribute(row, "productHaveMonth", "CanEdit", 0, 1);
                grid.SetAttribute(row, "moldReadyTime", "CanEdit", 0, 1);
                grid.SetValue(row, "productHaveMonth", "0", 1);
                grid.SetValue(row, "moldReadyTime", "0", 1);
           	}
           	
           	
            //추가공수1 활성화
            if(val.indexOf("CALCSTD017") >= 0){
                grid.SetAttribute(row, "addManHourCt_1", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourEff_1", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourLb_1", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourClb_1", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourYear_1", "CanEdit", 1, 1);
            }else{
            	grid.SetAttribute(row, "addManHourCt_1", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourEff_1", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourLb_1", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourClb_1", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourYear_1", "CanEdit", 0, 1);
                grid.SetValue(row, "addManHourCt_1", "0", 1);
                grid.SetValue(row, "addManHourEff_1", "0", 1);
                grid.SetValue(row, "addManHourLb_1", "0", 1);
                grid.SetValue(row, "addManHourClb_1", "0", 1);
                grid.SetValue(row, "addManHourYear_1", "0", 1);
            }
          
            //추가공수2 활성화
            if(val.indexOf("CALCSTD018") >= 0){
                grid.SetAttribute(row, "addManHourCt_2", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourEff_2", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourLb_2", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourClb_2", "CanEdit", 1, 1);
                grid.SetAttribute(row, "addManHourYear_2", "CanEdit", 1, 1);
            }else{
                grid.SetAttribute(row, "addManHourCt_2", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourEff_2", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourLb_2", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourClb_2", "CanEdit", 0, 1);
                grid.SetAttribute(row, "addManHourYear_2", "CanEdit", 0, 1);
                grid.SetValue(row, "addManHourCt_2", "0", 1);
                grid.SetValue(row, "addManHourEff_2", "0", 1);
                grid.SetValue(row, "addManHourLb_2", "0", 1);
                grid.SetValue(row, "addManHourClb_2", "0", 1);
                grid.SetValue(row, "addManHourYear_2", "0", 1);
            }
        }
    }
	
	Grids.OnRenderFinish = function(grid){
		if(typeof(parent.iframeCalcHeight) == "function") {
            $(parent.iframeCalcHeight("${COSTTYPE}", window.document.body.scrollHeight+40));
        }
		setAttrLocked();
	}
	
	Grids.OnAfterColResize = function(){
		setAttrLocked();
	}
	
	Grids.OnSave = function(grid){
		var attrLocked = $('input[name=attrLocked]:checked').map(function() { return this.value;}).get().join(',');
		var attrUnLocked = $('input[name=attrLocked]').not(":checked").map(function() { return this.value;}).get().join(',');
		grid.Source.Upload.Param.attrLocked=attrLocked;
		grid.Source.Upload.Param.attrUnLocked=attrUnLocked;
	}
	
	Grids.OnAfterSave = function (grid, result, autoupdate){
		try{
			if( result >=0 ){
				if(typeof(parent.initializePage) == "function") {
					alert('저장되었습니다');
					parent.initializePage();
				}
			}else{
				alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
			}
		} catch (e) {
			alert(e.message);
		}
	}
	
	window.gridReload = function(){
		setTimeout(function(){
			Grids[0].ReloadBody(function(){
				setAttrLocked();
			});
		},1000);
	}
	
	window.setAttrLocked = function(){
		
		var rows = Grids[0].Rows;
		var rowKeys = Object.keys(rows);
		var attrLocked = "";
		for(var i = 0; i < rowKeys.length; i++){
			
			var key = rowKeys[i];
			var row = rows[key];
			
			if("Data" == row.Kind){
				attrLocked = checkNull(row.attrLocked);
				break;
			}
		}

		$("input[name=attrLocked]").each(function(){
			if(attrLocked.indexOf($(this).val()) >= 0){
				$(this).prop("checked", true);				
			}
		});
		
		$("input[name=attrLocked]").click(function(e){
			$(this).prop("checked", !$(this).is(":checked"));
		});
	}
});
</script>
<html>
<body style="margin:0 !important;">
<span class="${COSTTYPE}resFormula resFormula"></span>
<div id='${COSTTYPE}costGrid' style='width:100%;height:100%;position:relative;top:20px;'></div>
</body>
</html>