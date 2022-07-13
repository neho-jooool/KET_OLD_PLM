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
/* 크롬일 경우 */
@media screen and (-webkit-min-device-pixel-ratio:0) { 
.GOHeadLeft .GOHeaderRow {height:22px !important;}
.GOHeadLeft .GOHeaderRow td{height:22px !important;}
.GOHeadMid .GOHeaderRow {height:25px !important;}
.GOHeadMid .GOHeaderRow td{height:25px !important;}
}

.headerDiv{text-align:center;line-height:18px;}
.legend{
    font-weight:bold;
    font-size:12px;
    /* color:#FF0000; */
    margin-right:25px;
    position:absolute;
    right:0;
    font-family:NanumBold !important;
}
</style>
<script>
var searchIcon          = "/plm/portal/images/icon_5.png";
var deleteIcon          = "/plm/portal/images/icon_delete.gif";
var costBomGrid = null;
var bomACL = ${bomACL};
var typeACL = ${typeACL};
var isPurchaseDept = eval('${isPurchaseDept}');
var costGridHeight;
$(document).ready(function(){
	
	var levelColKey = "Level";
	
	if(${FILTERMODE}) levelColKey = "bomLevel";
	
	var isPurchaseColumns = true;
	if("${EDITMODE}" == "VIEW_COMMON" && !isPurchaseDept){//프로젝트탭 중 개발원가 클릭 -> 공통조회권한으로 접속시 구매본부가 아닌 인원은 구매관련 항목을 숨긴다 excel내려받기에서도 클릭이 안되도록 하기 위함 
		isPurchaseColumns = false;
	}
	
	var columnList = new Array();
	
	//####################################### 제품정보 ##################################################################
	columnList.push({LABEL : "제품정보", KEY : "PROD", LEVEL : 1, COLSPAN : 13, LEFTCOLS : true });
	columnList.push({LABEL : "구분", KEY : "partTypeName", LEVEL : 2, MinWidth : 200, CanEdit : 0, CanSelect : 0, LEFTCOLS : true });
	columnList.push({LABEL : "", KEY : "partType", LEVEL : 2, Visible : 0, LEFTCOLS : true  });
	columnList.push({LABEL : "레벨", KEY : levelColKey, Align : "Center", LEVEL : 2, CanEdit : 0, CanSelect : 0, LEFTCOLS : true });
	//columnList.push({LABEL : "형상", KEY : "partForm", LEVEL : 2, CanEdit : 0, CanSelect : 0, Type : "Html", LEFTCOLS : true });
	columnList.push({LABEL : "제품 SIZE", KEY : "PROD_SIZE", LEVEL : 2, COLSPAN : 4, LEFTCOLS : true  });
	columnList.push({ LABEL : "<div class='headerDiv'>W<p/>(mm)</div>", KEY : "sizeW", LEVEL : 3, Type : "Float", Format : "###,##0.0", LEFTCOLS : true  });
	columnList.push({ LABEL : "<div class='headerDiv'>L<p/>(mm)</div>", KEY : "sizeL", LEVEL : 3, Type : "Float", Format : "###,##0.0", LEFTCOLS : true  });
	columnList.push({ LABEL : "<div class='headerDiv'>H<p/>(mm)</div>", KEY : "sizeH", LEVEL : 3, Type : "Float", Format : "###,##0.0", LEFTCOLS : true  });
	columnList.push({ LABEL : "임시품번", KEY : "partNo", LEVEL : 2, CanEdit : 0, CanSelect : 0, LEFTCOLS : true  });
	columnList.push({ LABEL : "품번", KEY : "realPartNo", LEVEL : 2, CanEdit : 0, CanSelect : 0, LEFTCOLS : true  });
	columnList.push({ LABEL : "품명", KEY : "partName", LEVEL : 2, LEFTCOLS : true  });
	columnList.push({ LABEL : "<div class='headerDiv'>U/S<p/>(EA)<p/>(mm)</div> ", KEY : "us", LEVEL : 2, Type : "Int", Format : "###,###", LEFTCOLS : true  });
	
	columnList.push({ LABEL : "<div class='headerDiv'>예상판매량<p/></div>", KEY : "estimated", Format : "###,##0", Type : "Int", LEVEL : 1, CanEdit : 0, CanSelect : 0 });
	//####################################### 생산정보 ##################################################################
	columnList.push({ LABEL : "생산정보", KEY : "MFT", LEVEL : 1, COLSPAN : 12 });
	columnList.push({ LABEL : "C/V", KEY : "CV", LEVEL : 2, COLSPAN : 3 });
	columnList.push({ LABEL : "금형", KEY : "cvMold", LEVEL : 3, Type : "Int", Format : "###,##0" });
	columnList.push({ LABEL : "설비", KEY : "cvAssemble", LEVEL : 3, Type : "Int", Format : "###,##0" });
	columnList.push({ LABEL : "C/T, SPM", KEY : "CTSPM", LEVEL : 2, COLSPAN : 3 });
	columnList.push({ LABEL : "금형", KEY : "ctSPMMold", LEVEL : 3, Type : "Float", Format : "###,##0.0" });
	columnList.push({ LABEL : "설비", KEY : "ctSPMAssemble", LEVEL : 3, Type : "Float", Format : "###,##0.0" });
	
	columnList.push({ LABEL : "<div class='headerDiv'>생산국<p/>(입고처)</div>", KEY : "mftFactory1", LEVEL : 2, Type : "Enum", Clear : "mftFactory2", CanEmpty : 1 });
	columnList.push({ LABEL : "<div class='headerDiv'>생산지<p/>(유/무상)</div>", KEY : "mftFactory2", LEVEL : 2, Type : "Enum", Related : "mftFactory1", CanEmpty : 1 });
	
	columnList.push({ LABEL : "업체명", KEY : "company", LEVEL : 2 });
	columnList.push({ LABEL : "<div class='headerDiv'>적용설비<p/>(Ton)</div>", KEY : "facilities", LEVEL : 2, Type : "Int", Format : "###,##0" });
	columnList.push({ LABEL : "<div class='headerDiv'>작업인원<p/>(명)</div>", KEY : "worker", LEVEL : 2, Type : "Float", Format : "###,##0.0" });
	
	//####################################### 구매/도금/외주 ##################################################################
	if(isPurchaseColumns){
		columnList.push({ LABEL : "구매/도금/외주", KEY : "PO", LEVEL : 1, COLSPAN : 12 });
	    columnList.push({ LABEL : "선적조건", KEY : "tos", LEVEL : 2 });
	    columnList.push({ LABEL : "<div class='headerDiv'>관세<p/>(%)</div>", KEY : "duty", LEVEL : 2, Type : "Float", Format : "0.0%" });
	    columnList.push({ LABEL : "<div class='headerDiv'>물류비<p/>(%)</div>", KEY : "distributionCost", LEVEL : 2, Type : "Float", Format : "0.0%" });
	    columnList.push({ LABEL : "<div class='headerDiv'>구매단가<p/>(/EA)</div>", KEY : "pUnitCost", LEVEL : 2, Type : "Float", Format : "###,##0.0" });
	    columnList.push({ LABEL : "화폐단위", KEY : "pMonetaryUnit", LEVEL : 2, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}"});
	    columnList.push({ LABEL : "<div class='headerDiv'>선도금단가<p/>(/kg)</div>", KEY : "prePlatingCost", LEVEL : 2, Type : "Float", Format : "###,##0.0" }); 
	    columnList.push({ LABEL : "화폐단위", KEY : "prePlatingUnit", LEVEL : 2, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}"});
	    columnList.push({ LABEL : "<div class='headerDiv'>후도금단가<p/>(/EA)</div>", KEY : "apUnitCost", LEVEL : 2, Type : "Float", Format : "###,##0.0" });
	    columnList.push({ LABEL : "화폐단위", KEY : "apMonetaryUnit", LEVEL : 2, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}"});
	    columnList.push({ LABEL : "<div class='headerDiv'>외주단가<p/>(/EA)</div>", KEY : "outUnitCost", LEVEL : 2, Type : "Float", Format : "###,##0.0" });
	    columnList.push({ LABEL : "화폐단위", KEY : "outMonetaryUnit", LEVEL : 2, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}"});
	}
	
	
	//####################################### 제품중량 ##################################################################
	columnList.push({ LABEL : "중량", KEY : "PRODW", LEVEL : 1, COLSPAN : 4  });
	columnList.push({ LABEL : "<div class='headerDiv'>제품중량<p/>(g)</div>", KEY : "productWeight", LEVEL : 2, Type : "Float", Format : "###,##0.0" });
	columnList.push({ LABEL : "<div class='headerDiv'>스크랩중량<p/>(g)</div>", KEY : "scrapWeight", LEVEL : 2, Type : "Float", Formula : "weightFormula(Row,Col)", Format : "###,##0.0" });
	columnList.push({ LABEL : "<div class='headerDiv'>총중량<p/>(g)</div>", KEY : "totalWeight", LEVEL : 2, CanEdit : 0, CanSelect : 0, Formula : "weightFormula(Row,Col)", Type : "Float", Format : "###,##0.0" });
	
	//####################################### 포장수량 ##################################################################
	columnList.push({ LABEL : "<div class='headerDiv'>포장수량<p/>(EA/Box)</div>", KEY : "quantity", LEVEL : 1, CanEdit : 0, CanSelect : 0 });
	
	//####################################### 금형 투자비 ##################################################################
	columnList.push({ LABEL : "금형 투자비", KEY : "MOLD_IC", LEVEL : 1, COLSPAN : 18 }); 
	columnList.push({ LABEL : "투자오더", KEY : "moldOrder", LEVEL : 2, Type : "Int" });
	columnList.push({ LABEL : "Die No", KEY : "moldDieNo", LEVEL : 2 });
	columnList.push({ LABEL : "금형구조", KEY : "moldStructure", LEVEL : 2 });
	columnList.push({ LABEL : "제작구분", KEY : "moldMftDivision", LEVEL : 2, Type : "Enum", EnumKeys : "${mDivisionKeys}", Enum : "${mDivisionNames}"});
	
	columnList.push({ LABEL : "신규투자비 (천원)", KEY : "MOLD_IC_NEW", LEVEL : 2, COLSPAN : 6 });
	columnList.push({ LABEL : "제작처", KEY : "moldNFactory", LEVEL : 3, Type : "Enum", EnumKeys : "${makingKeys}", Enum : "${makingNames}" });
	columnList.push({ LABEL : "투자비", KEY : "moldNIC", LEVEL : 3, Type : "Float", Format : "###,##0,.0" });
	columnList.push({ LABEL : "화폐단위", KEY : "moldNICMUnit", LEVEL : 3, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}" });
	columnList.push({ LABEL : "지급액", KEY : "moldNPay", LEVEL : 3, Type : "Float", Format : "###,##0,.0" });
	columnList.push({ LABEL : "화폐단위", KEY : "moldNPayMUnit", LEVEL : 3, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}" });
	
	columnList.push({ LABEL : "양산투자비 (천원)", KEY : "MOLD_IC_MP", LEVEL : 2, COLSPAN : 4 });
	columnList.push({ LABEL : "제작처", KEY : "moldMPFactory", LEVEL : 3, Type : "Enum", EnumKeys : "${makingKeys}", Enum : "${makingNames}"  });
	columnList.push({ LABEL : "투자비", KEY : "moldMPIC", LEVEL : 3, Type : "Float", Format : "###,##0,.0" });
	columnList.push({ LABEL : "화폐단위", KEY : "moldMPICMUnit", LEVEL : 3, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}" });
	
	columnList.push({ LABEL : "양산수량 (천개)", KEY : "MOLD_IC_MPQ", LEVEL : 2, COLSPAN : 3 });
	columnList.push({ LABEL : "Total", KEY : "moldMPQTotal", LEVEL : 3, Type : "Int", Format : "###,##0,.0" });
	columnList.push({ LABEL : "MAX", KEY : "moldMPQMax", LEVEL : 3, Type : "Int", Format : "###,##0,.0" });
	
	//####################################### 설비 투자비 ##################################################################
	columnList.push({ LABEL : "설비 투자비", KEY : "FAC_IC", LEVEL : 1, COLSPAN : 16 }); 
	columnList.push({ LABEL : "투자오더", KEY : "facOrder", LEVEL : 2 });
	columnList.push({ LABEL : "제작구분", KEY : "facMftDivision", LEVEL : 2, Type : "Enum", EnumKeys : "${fDivisionKeys}", Enum : "${fDivisionNames}"});
	
	columnList.push({ LABEL : "신규투자비 (천원)", KEY : "FAC_IC_NEW", LEVEL : 2, COLSPAN : 6 });
	columnList.push({ LABEL : "제작처", KEY : "facNFactory", LEVEL : 3, Type : "Enum", EnumKeys : "${makingKeys}", Enum : "${makingNames}" });
	columnList.push({ LABEL : "투자비", KEY : "facNIC", LEVEL : 3, Type : "Float", Format : "###,##0,.0" });
	columnList.push({ LABEL : "화폐단위", KEY : "facNICMUnit", LEVEL : 3, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}" });
	columnList.push({ LABEL : "지급액", KEY : "facNPay", LEVEL : 3, Type : "Float", Format : "###,##0,.0" });
	columnList.push({ LABEL : "화폐단위", KEY : "facNPayMUnit", LEVEL : 3, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}" });
	
	columnList.push({ LABEL : "양산투자비 (천원)", KEY : "FAC_IC_MP", LEVEL : 2, COLSPAN : 4 });
	columnList.push({ LABEL : "제작처", KEY : "facMPFactory", LEVEL : 3, Type : "Enum", EnumKeys : "${makingKeys}", Enum : "${makingNames}" });
	columnList.push({ LABEL : "투자비", KEY : "facMPIC", LEVEL : 3, Type : "Float", Format : "###,##0,.0" });
	columnList.push({ LABEL : "화폐단위", KEY : "facMPICMUnit", LEVEL : 3, Type : "Enum", EnumKeys : "${monetaryKeys}", Enum : "${monetaryNames}" });
	
	columnList.push({ LABEL : "양산수량 (천개)", KEY : "FAC_IC_MPQ", LEVEL : 2, COLSPAN : 3 });
	columnList.push({ LABEL : "Total", KEY : "facMPQTotal", LEVEL : 3, Type : "Int", Format : "###,##0,.0" });
	columnList.push({ LABEL : "MAX", KEY : "facMPQMax", LEVEL : 3, Type : "Int", Format : "###,##0,.0" });
	
	//####################################### 기타 투자비 ##################################################################
	columnList.push({ LABEL : "기타 투자비", KEY : "ETC_IC", LEVEL : 1, COLSPAN : 12 }); 
	columnList.push({ LABEL : "항목", KEY : "itemName", LEVEL : 2, CanEdit : 0 });
	
	columnList.push({ LABEL : "신규투자비 (천원)", KEY : "ETC_IC_NEW", LEVEL : 2, COLSPAN : 4 });
	columnList.push({ LABEL : "제작처", KEY : "etcNPFactory", LEVEL : 3, CanEdit : 0, CanSelect : 0 });
	columnList.push({ LABEL : "투자비", KEY : "etcNIC", LEVEL : 3, CanEdit : 0, CanSelect : 0, Type : "Float", Format : "###,##0,.0" });
	//columnList.push({ LABEL : "화폐단위", KEY : "etcNICMUnit", LEVEL : 3, CanEdit : 0 });
	columnList.push({ LABEL : "지급액", KEY : "etcNPay", LEVEL : 3, CanEdit : 0, CanSelect : 0, Type : "Float", Format : "###,##0,.0" });
	//columnList.push({ LABEL : "화폐단위", KEY : "etcNPayMUnit", LEVEL : 3, CanEdit : 0 });
	
	columnList.push({ LABEL : "양산투자비 (천원)", KEY : "ETC_IC_MP", LEVEL : 2, COLSPAN : 3 });
	columnList.push({ LABEL : "제작처", KEY : "etcMPFactory", LEVEL : 3, CanEdit : 0, CanSelect : 0 });
	columnList.push({ LABEL : "투자비", KEY : "etcMPIC", LEVEL : 3, CanEdit : 0, CanSelect : 0, Type : "Float", Format : "###,##0,.0" });
	//columnList.push({ LABEL : "화폐단위", KEY : "etcMPICMUnit", LEVEL : 3, CanEdit : 0 });
	
	columnList.push({ LABEL : "양산수량 (천개)", KEY : "ETC_IC_MPQ", LEVEL : 2, COLSPAN : 3 });
	columnList.push({ LABEL : "Total", KEY : "etcMPQTotal", LEVEL : 3, CanEdit : 0, CanSelect : 0, Type : "Float", Format : "###,##0" });
	columnList.push({ LABEL : "MAX", KEY : "etcMPQMax", LEVEL : 3, CanEdit : 0, CanSelect : 0, Type : "Float", Format : "###,##0" });

	//####################################### 원재료정보 ##################################################################
	columnList.push({ LABEL : "원재료정보", KEY : "RMAT", LEVEL : 1, COLSPAN : 15 }); 
	columnList.push({ LABEL : "입고처", KEY : "costDeliver", LEVEL : 2, Type : "Enum", EnumKeys : "${costDeliverKeys}", Enum : "${costDeliverNames}" }); 
	columnList.push({ LABEL : "수지원재료", KEY : "RMAT_PROFIT", LEVEL : 2, COLSPAN : 5 }); 
	columnList.push({ LABEL : "자재번호", KEY : "sujiPartNo", LEVEL : 3, CanEdit : 0, CanSelect : 0 }); 
	columnList.push({ LABEL : "원재료명", KEY : "sujiPartName", LEVEL : 3, CanEdit : 0, CanSelect : 0 });
	columnList.push({ LABEL : "Grade", KEY : "sujiGrade", LEVEL : 3, CanEdit : 0, CanSelect : 0 });
	columnList.push({ LABEL : "Color", KEY : "sujiColor", LEVEL : 3, CanEdit : 0, CanSelect : 0 });
	
	
	columnList.push({ LABEL : "비철원재료", KEY : "RMAT_NMETAL", LEVEL : 2, COLSPAN : 8, MinWidth : 150 });
	columnList.push({ LABEL : "자재번호", KEY : "metalPartNo", LEVEL : 3, MinWidth : 120 });
	columnList.push({ LABEL : "원재료명", KEY : "rMatNMetalName", LEVEL : 3, Type : "Enum", EnumKeys : "${rMaterialKeys}", Enum : "${rMaterialNames}" }); 
	columnList.push({ LABEL : "선도금사양", KEY : "prePlatingSpec", LEVEL : 3, Type : "Enum", EnumKeys : "${prePlatingKeys}", Enum : "${prePlatingNames}" }); 
	columnList.push({ LABEL : "후도금사양", KEY : "apPlatingSpec", LEVEL : 3, Type : "Enum", EnumKeys : "${apPlatingKeys}", Enum : "${apPlatingNames}" }); 
	
	columnList.push({ LABEL : "<div class='headerDiv'>두께<p/>(mm)</div>", KEY : "rMatNMetalT", LEVEL : 3, Type : "Float", Format : "###,##0.0" }); 
	columnList.push({ LABEL : "<div class='headerDiv'>폭<p/>(mm)</div>", KEY : "rMatNMetalW", LEVEL : 3, Type : "Float", Format : "###,##0.0" }); 
	columnList.push({ LABEL : "<div class='headerDiv'>피치<p/>(mm)</div>", KEY : "rMatNMetalP", LEVEL : 3, Type : "Float", Format : "###,##0.0" });
	columnList.push({ LABEL : "비철-비중", KEY : "metalImportance", LEVEL : 1, Visible : 0 });
	columnList.push({ LABEL : "제품구분코드", KEY : "partTypeCode", LEVEL : 1, Visible : 0 });
	
	var columnProp = generateGridColumn(columnList);
	var topbarProp = new Object();
	var panelProp = new Object();
	var isDeleted = 0;
	var isDragging = 0;
	var isDropping = 0;
	
	topbarProp.Kind = "Topbar";
	
	if("${EDITMODE}" == "VIEW" || "${EDITMODE}" == "VIEW_COMMON" || "${EDITMODE}" == "VIEW_EXCEL"){
		topbarProp.Cells = "ExpandAll,CollapseAll,Reload,Export,Print";
	}else{
		topbarProp.Cells = "Save,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print";
	}
	
	topbarProp.Styles = 2;
	topbarProp.Calculated = 1;
	topbarProp.Link = 0;
	
	if("${EDITMODE}" == "TREEEDIT"){
		panelProp.Move = 1;
		panelProp.Copy = 1;
		panelProp.CanHide = 0;
		panelProp.CopyMenu = "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd";
		isDragging = 1;
		isDropping = 1;
		isDeleted = 1;
		topbarProp.Cells = "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload";
		topbarProp.AddCopyMenu = "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow";
		topbarProp.AddChildCopyMenu = "AddChild,AddChildEnd";
	}
	
	var mainCol = "partTypeName";
	
    if(${FILTERMODE}){
    	mainCol = null;
    }

    costBomGrid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/saveCostPart",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
			Param : {
				taskOid : "${taskOid}",
				EDITMODE : "${EDITMODE}",
				subVersion : "${subVersion}",
				oid : "${oid}",
				EDITTYPE : "${EDITTYPE}"
			},
			Timeout : 0
		},
		Data : {
			Url : '/plm/ext/cost/costTreeGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				taskOid : "${taskOid}",
				oid : "${oid}",
				version : "${version}",
				subVersion : "${subVersion}",
				lastest : "${lastest}",
				FILTERMODE : "${FILTERMODE}",
				EDITMODE : "${EDITMODE}",
				DATATYPE : "COSTPART"
			},
			Timeout : 0
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
					id : "costBomEditor",
					Style : gridStyle,
					SuppressCfg : 1,
					IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : 1,
					Deleting : isDeleted,
					ShowDeleted : 0,
					Selecting : 0,
					CopySelected : 1,
					CopyPasteTree : 1,
					Pasting : 0,
					Dragging : isDragging,
					Dropping : isDropping,
					ExportFormat : 1,
					ExportType : "Filtered,Hidden,Strings,Dates",
					SaveSession : 1,
					UsePrefix : 1,
					Alternate : 0,
					MainCol : mainCol,
					SuppressMessage : '1',
					FastColumns : 1
				},
				Toolbar : { Visible : 0 },
				Panel : panelProp,
				LeftCols : columnProp.leftCols,
				Cols : columnProp.cols,
				Head : [
					topbarProp, {
						Kind : "Header",
						id : "Header",
						RowSpan : 0,
						Visible : 0
					},
					columnProp.kHeader1Lv, columnProp.kHeader2Lv, columnProp.kHeader3Lv, columnProp.kHeader4Lv
				],
				Body : [ { Pos : 0 }],
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
						<%-- DelRow : "%d <%=messageService.getString("e3ps.message.ket_message", "00494") %>"		//아이템을 삭제하시겠습니까? --%>
					}
				}
			}
		}
	}, "treeGrid");
    
    Grids.OnSuggest = function(grid, row, col, val, suggest, formula){
        
        if(col == "metalPartNo"){
        	if(val.length >= 4){
        		$.ajax({
                    url : '/plm/ext/suggest/find.do',
                    dataType : 'json',
                    async : false,
                    data : {
                        suggestType : "PARTNO",
                        inputParam : val.toUpperCase(),
                        wtPartColumn : "spPartType",
                        wtPartColumnValue : "R",
                        wtPartNoLike : "R1"
                    },
                    success : function(data) {
                        suggest = checkNull(suggest);
                        for(var i = 0; i < data.length; i++){
                            suggest += "|" + data[i].label;
                        }
                    }
                });	
        	}
            
        }
        
        return suggest;
    }
	
	Grids.OnSave = function(grid){
		
		//if(${isAdmin}) return false; //true 면 save가 먹지 않음 false 일때는 체크로직 안타고 save실행됨
		
		var tempRows = grid.Rows;
		var rowKeys = Object.keys(tempRows);
		var rows = {};
		
		for(var i = 0; i < rowKeys.length; i++){
			
			var id = rowKeys[i];
			var tempRow = tempRows[id];
			var parentNode = tempRow.parentNode;
			
			if("Data" == tempRow.Kind && tempRow.Visible){
				
				grid.SetAttribute(tempRow, "partTypeName", "Color", "", 1);
				grid.SetAttribute(tempRow, "mftFactory1", "Color", "", 1);
				grid.SetAttribute(tempRow, "mftFactory2", "Color", "", 1);
				
				var row = new Object();
				row.partType = checkNull(tempRow.partType + "");
				row.level = checkNull(tempRow.Level + "");
				row.hasChild = (tempRow.childNodes.length > 0 ) + "";
				row.typeChanged = (tempRow.partTypeChanged != null) + "";
				
				if(parentNode != null){
					row.parent = parentNode.id;
				}
				rows[id] = row;
			}
		}
		
		var param = {
				rows : rows
		}
		
		var isError = false;
		
		if(!${FILTERMODE}){
			
			var data = ajaxCallServer("/plm/ext/cost/treePartTypeCheck", param, null, false);
	        var errorList = data.errorList;
	        
	        for(var i = 0; i < errorList.length; i++){
	            grid.SetAttribute(tempRows[errorList[i]], "partTypeName", "Color", "#FCD", 1);
	        }
	        
	        if(data.isError) return true;
		}
		
		var message = "";
		var isMFTError = false;
		for(var i = 0; i < rowKeys.length; i++){
			
			var id = rowKeys[i];
			var tempRow = tempRows[id];
			try{
				
	 			if("Data" == tempRow.Kind && tempRow.Visible){
					if(tempRow.mftFactory1 == null){
						grid.SetAttribute(tempRows[id], "mftFactory1", "Color", "#FCD", 1);
						isMFTError = true;
						if(message == ""){
							message = "생산국(입고처)을 입력하세요.";
						}
						
					}
					if(tempRow.mftFactory2 == null){
						grid.SetAttribute(tempRows[id], "mftFactory2", "Color", "#FCD", 1);
						isMFTError = true;
						if(message == ""){
							message = "생산지(유/무상)을 입력하세요.";
						}
					}
				}
	 			
			}catch(e){
				alert(e);
			}
		}
		
		if(isError || isMFTError){
			alert(message);
			isError = true;
		}

		return isError;
	}

	Grids.OnRenderFinish = function(grid){
		
		var hideCols = [];
		if("CALCULATE" != "${EDITMODE}"){
			
			var columnKeys = Object.keys(bomACL);
			
			if(columnKeys.length == 0){
				
				$("#treeGrid").hide();
				alert("권한을 설정해 주세요.");
				self.close();
				
			}else{
				for(var i = 0; i < columnKeys.length; i++){
					
					var columnKey = columnKeys[i];
					var acl = bomACL[columnKey];
					if(!eval(acl.viewable)){
						hideCols.push(columnKey);
					}
				}
			}
		}
		grid.ChangeColsVisibility(null, hideCols, 1);
		
		if(grid.id == "costBomEditor"){
			var rows = grid.Rows;
            var rowIds = Object.keys(rows);
            
            var isCalcResult = false;
            
            for(var i = 0; i < rowIds.length; i++){
            	
                var row = rows[rowIds[i]];
                
                if(row.Kind == "Data"){
                	if(!isCalcResult && ${costCalcResult}){
                        isCalcResult = true;
                    }
                	
                    if("${EDITMODE}" != "VIEW" && "${EDITMODE}" != "VIEW_COMMON") setNeedColumn(grid, row);
                }
            }
		}
		
		if(typeof(parent.iframeCalcHeight) == "function") {
            $(parent.iframeCalcHeight("BOMEDITOR", window.document.body.scrollHeight));
        }
	}
	
	Grids.OnAfterSave = function (grid, result, autoupdate){
		
		try{
			
			if("${EDITMODE}" == "TREEEDIT" && opener != null){
                opener.location.reload();
            }
			
			if( result >=0 ){
				
				alert('저장되었습니다');
				
				
			    if(typeof(parent.initializePage) == "function") {
			    	$(parent.initializePage());
			    }
			    grid.Reload();
			    
			    var editType = '${EDITTYPE}';
		        var editMode = '${EDITMODE}';
		        var category = '${task.taskTypeCategory}';
		        if(editType == 'NEWPART' && editMode == 'TREEEDIT' && (category == 'COST016' ||category == 'COST015')){
		            //window.close();
		        }
			    
			}else{
				alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
			}
			
		} catch (e) {
			alert(e.message);
		};
   }
	
	Grids.OnEndEdit = function(grid, row, col, save, val, raw){
		
		
		//선도금 사양이 [Ag도금, Au도금, 기타도금] & 선도금단가 에디터 일 경우 선도금화폐단위, 선도금단가를 파랑색으로 표시한다.
		if("prePlatingSpec" == col && val != null){
			var aclData = bomACL["prePlatingCost"];
            if(eval(aclData.editor) && ("PTG003" == val || "PTG004" == val || "PTG005" == val)) {
                grid.SetValue(row, "prePlatingCostCanEdit", "1", 1);
                grid.SetValue(row, "prePlatingCostColor", "#B4EEF2", 1);
                grid.SetValue(row, "prePlatingUnitCanEdit", "1", 1);
                grid.SetValue(row, "prePlatingUnitColor", "#B4EEF2", 1);
            }else{
                grid.SetValue(row, "prePlatingCostCanEdit", "0", 1);
                grid.SetValue(row, "prePlatingCostColor", "", 1);
                grid.SetValue(row, "prePlatingCost", "", 1);
                grid.SetValue(row, "prePlatingUnitCanEdit", "0", 1);
                grid.SetValue(row, "prePlatingUnitColor", "", 1);
                grid.SetValue(row, "prePlatingUnit", "", 1);
            }
        }
		
		//후도금 사양이 있음 & 후도금단가 에디터 일 경우 후도금화폐단위, 후도금단가를 파랑색으로 표시한다.
		if("apPlatingSpec" == col){
			var aclData = bomACL["apUnitCost"];
			
			if(eval(aclData.editor) || val != null){
	            grid.SetValue(row, "apUnitCostCanEdit", "1", 1);
	            grid.SetValue(row, "apUnitCostColor", "#B4EEF2", 1);
	            grid.SetValue(row, "apMonetaryUnitCanEdit", "1", 1);
                grid.SetValue(row, "apMonetaryUnitColor", "#B4EEF2", 1);
	            
            }else{
            	grid.SetValue(row, "apUnitCostCanEdit", "0", 1);
                grid.SetValue(row, "apUnitCostColor", "", 1);
                grid.SetValue(row, "apUnitCost", "", 1);
                grid.SetValue(row, "apMonetaryUnitCanEdit", "0", 1);
                grid.SetValue(row, "apMonetaryUnitColor", "", 1);
                grid.SetValue(row, "apMonetaryUnit", "", 1);
            }
        }
	}
	
	Grids.OnRowAdded = function(grid, row){

		grid.SetValue(row, "partTypeNameIcon", searchIcon, 1);
	    grid.SetValue(row, "partTypeNameIconAlign", "Right", 1);
	    grid.SetValue(row, "partTypeNameOnClickSideIcon", "javascript:partTypeRow=Row;SearchUtil.selectOneCostPartTypeWithCallBack('setPartType','onlyLeaf=Y&openAll=N');", 1);
	    grid.SetValue(row, "partTypeNameButton", deleteIcon, 1);
	    grid.SetValue(row, "partTypeNameOnClickSideButton", "javasciprt:deletePartType(Row);", 1);
	    
	    var rows = grid.Rows;
        var rowIds = Object.keys(rows);
        
        for(var i = 0; i < rowIds.length; i++){
            
            var row = rows[rowIds[i]];
            
            if(row.Kind == "Data"){
            	if(row.id.indexOf("T") != -1){//아직 저장되지 않은 부품에 대한 원자재 정보 초기화
            		grid.SetValue(row, "sujiPartNo", "", 1);
                    grid.SetValue(row, "sujiPartName", "", 1);
                    grid.SetValue(row, "sujiGrade", "", 1);
                    grid.SetValue(row, "sujiColor", "", 1);
            	}
            }
        }

		grid.RefreshRow(row);
		$("#treeGrid").height(costGridHeight + (grid.RowCount * 20));
		
		
		
	}
	
	Grids.OnAfterValueChanged = function(grid, row, col, val){
		
		if(col == "partTypeName" || col == "mftFactory1" || col == "mftFactory2"){
			setNeedColumn(grid, row);
		}
		if(col == "moldMftDivision" && val == "MDV006"){//금형투자비 제작구분이 [해당없음] 일 경우 관련 필드 초기화
			Grids[0].SetValue(row, "moldNFactory", "", 1);
			Grids[0].SetValue(row, "moldNIC", 0, 1);
			Grids[0].SetValue(row, "moldNPay", 0, 1);
			Grids[0].SetValue(row, "moldNPayMUnit", "", 1);
			Grids[0].SetValue(row, "moldMPFactory", "", 1);
			Grids[0].SetValue(row, "moldMPIC", 0, 1);
			Grids[0].SetValue(row, "moldMPICMUnit", "", 1);
			Grids[0].SetValue(row, "moldMPQTotal", 0, 1);
			Grids[0].SetValue(row, "moldMPQMax", 0, 1);
			Grids[0].SetValue(row, "moldNICMUnit", "", 1);
		}
		if(col == "facMftDivision" && val == "FDV006"){//설비투자비 제작구분이 [해당없음] 일 경우 관련 필드 초기화
			Grids[0].SetValue(row, "facNFactory", "", 1);
			Grids[0].SetValue(row, "facNIC", 0, 1);
			Grids[0].SetValue(row, "facNICMUnit", "", 1);
			Grids[0].SetValue(row, "facNPay", 0, 1);
			Grids[0].SetValue(row, "facMPIC", 0, 1);
			Grids[0].SetValue(row, "facMPICMUnit", "", 1);
			Grids[0].SetValue(row, "facMPQTotal", 0, 1);
			Grids[0].SetValue(row, "facMPQMax", 0, 1);
			Grids[0].SetValue(row, "facNPayMUnit", "", 1);
			Grids[0].SetValue(row, "facMPFactory", "", 1);
		}
	}
	
    // 행 이동 시 호출 - 행 Drop 가능 여부 체크 ([type] 0 – cannot drop, 1 – above torow, 2 – to the end of children of torow, 3 – below torow)
    Grids.OnCanDrop = function(grid, row, togrid, torow, type, copy) {
    	if("${EDITMODE}" == "TREEEDIT"){
    		// (0레벨)와 동일한 레벨로 행 Drop 방지
            if ( torow.Level == 0) {
                return true;
            }
    	}
    }
    
    
    //해당 구문이 없으면 리사이즈가 안먹어서 특정 높이로 한번 리사이즈함
    $("#treeGrid").height($(window).height()-150);
    $(window).resize(function(){
          $("#treeGrid").height($(window).height()-150);
    });
    
    var timerId = null;
    
    var gridHeight = 0;
    
    window.editorResize = function(){
        
        var windowHeight = $(window).height();
        var gridHeight = $("#costBomEditor").height();
        
        window.console.log("gridHeight : "+gridHeight);
        window.console.log("windowHeight : "+windowHeight);
         
        //그리드가 로딩되어 높이가 측정되고 스크롤이 필요없을만큼 갯수가 적을 때 (그리드 높이가 낮을 때) 그리드 사이즈만큼 리사이즈 해준다 간이산출과 간격이 벌어지지 않기 위해    
        if(gridHeight != null && gridHeight < windowHeight){
             window.console.log($("#costBomEditor").height());
             if(Grids[0].RowCount > 0){//row가 없으면 로딩오류 나서..
            
            	 if(!${costCalcResult} || Grids[0].RowCount == 1){
                     gridHeight = gridHeight + 10;
                 }
             
            	 $("#treeGrid").height(gridHeight);
                 $(window).resize(function(){
                	   
                       $("#treeGrid").height(gridHeight);                   
                 });	 
             }     
             
            
             window.console.log($("#treeGrid").height());
             
        }
        if($("#costBomEditor").height() != null){
        	costGridHeight = $("#treeGrid").height();
            clearInterval(timerId); 
        }
    }
    
    timerId = setInterval(editorResize, 500);

    //$("#treeGrid").height($(window).height()-150);
    

});

window.setNeedColumn = function(grid, row){
	
	var colNames = grid.ColNames[1];
    
    for(var i = 0; i < colNames.length;i++){
        var column = colNames[i];
        
        //JAVA에서 하드코딩으로 처리
        if("prePlatingCost" == column || "apUnitCost" == column || "prePlatingUnit" == column || "apMonetaryUnit" == column
        		|| "moldNFactory" == column || "moldNIC" == column || "moldNICMUnit" == column || "moldNPay" == column    
                || "moldNPayMUnit" == column || "moldMPFactory" == column || "moldMPIC" == column || "moldMPICMUnit" == column
                || "moldMPQTotal" == column || "moldMPQMax" == column || "facNFactory" == column || "facNIC" == column       
                || "facNICMUnit" == column || "facNPay" == column || "facNPayMUnit" == column || "facMPFactory" == column 
                || "facMPIC" == column || "facMPICMUnit" == column || "facMPQTotal" == column || "facMPQMax" == column    ) continue;
        
        
        grid.SetAttribute(row, column, "Color", "", 1);
    }
    
    var aclKey = "ext.ket.cost.entity.CostPartType:" + row.partType + "*" + row.mftFactory1 + "*" + row.mftFactory2;
    var TACL = typeACL[aclKey];
    
    if(TACL != null){
    	var aclColumns = Object.keys(TACL);
        
        for(var i = 0; i < aclColumns.length; i++){
            
            var column = aclColumns[i];
            var aclData = bomACL[column];
            
            //JAVA에서 하드코딩으로 처리
            if("prePlatingCost" == column || "apUnitCost" == column || "prePlatingUnit" == column || "apMonetaryUnit" == column
            		|| "moldNFactory" == column || "moldNIC" == column || "moldNICMUnit" == column || "moldNPay" == column    
            		|| "moldNPayMUnit" == column || "moldMPFactory" == column || "moldMPIC" == column || "moldMPICMUnit" == column
            		|| "moldMPQTotal" == column || "moldMPQMax" == column || "facNFactory" == column || "facNIC" == column       
            		|| "facNICMUnit" == column || "facNPay" == column || "facNPayMUnit" == column || "facMPFactory" == column 
            		|| "facMPIC" == column || "facMPICMUnit" == column || "facMPQTotal" == column || "facMPQMax" == column    ) continue;
            
            if(aclData != null && eval(aclData.editor)){
            	grid.SetAttribute(row, column, "Color", "#B4EEF2", 1);
            }else{
            	grid.SetAttribute(row, column, "Color", "#DFE", 1);
            }
        }
    }
	
	grid.SetAttribute(row, "partTypeName", "Color", "#DFE", 1);
    grid.SetAttribute(row, "mftFactory1", "Color", "#DFE", 1);
    grid.SetAttribute(row, "mftFactory2", "Color", "#DFE", 1);
}

var bomRowData = new Object();
window.openBomAttrPopup = function(flag, row){
	var editMode = "${EDITMODE}";
	bomRowData[flag] = row;
	if("QUANTITY" == flag){
		getOpenWindow2("/plm/ext/cost/code/CostPackingPopup.do?oid=" + row.id+"&editMode="+editMode, flag, 800, 400);
	}else if("RAWMATERIAL" == flag){
		getOpenWindow2("/plm/ext/cost/code/CostMaterialPopup.do?oid=" + row.id, flag, 800, 400);
	//기타 투자비
	}else if("ETCIC" == flag){
		getOpenWindow2("/plm/ext/cost/code/CostEtcInvestPopup.do?oid=" + row.id+"&editMode="+editMode, flag, 800, 400);
	//예상 판매량
	}else if("ESTIMATED" == flag){
		getOpenWindow2("/plm/ext/cost/code/CostQuantityPopup.do?oid=" + row.id + "&us=" + row.us + "&editMode=" + editMode, flag, 800, 400);
	}else if("CALCULATEPART" == flag){
		getOpenWindow2("/plm/ext/cost/costPartCalculatePopup.do?&taskOid=${taskOid}&oid=" + row.id, flag, 1280, 720);
	}else if("REALPARTNO" == flag){
		SearchUtil.selectOnePart('selectPartAfterFunc','pType=FHWR');
	}
}

window.selectPartAfterFunc = function(objArr){
	
	var trArr;
    if(objArr.length == 0) {
 	   return;
    }
    
    for(var i = 0; i < objArr.length; i++){
 	   trArr = objArr[i];
    }
    Grids[0].SetValue(bomRowData["REALPARTNO"], "realPartNo", trArr[1], 1);
    Grids[0].SetValue(bomRowData["REALPARTNO"], "partName", trArr[2], 1);
}

window.setRowPopupProperty = function(flag, data){
	
	var row = bomRowData[flag];
	if("QUANTITY" == flag){
		Grids[0].SetValue(row, "quantity", data, 1);
	}else if("RAWMATERIAL" == flag){
		if(data == null){
			Grids[0].SetValue(row, "sujiPartNo", "", 1);
	        Grids[0].SetValue(row, "sujiPartName", "", 1);
	        Grids[0].SetValue(row, "sujiGrade", "", 1);
	        Grids[0].SetValue(row, "sujiColor", "", 1);
		}else{
			Grids[0].SetValue(row, "sujiPartNo", data.partNo, 1);
	        Grids[0].SetValue(row, "sujiPartName", data.partName, 1);
	        Grids[0].SetValue(row, "sujiGrade", data.grade, 1);
	        Grids[0].SetValue(row, "sujiColor", data.color, 1);
		}
		
		//기타 투자비
	}else if("ETCIC" == flag){
	
        Grids[0].SetValue(row, "itemName", data.itemName, 1);
        Grids[0].SetValue(row, "etcNPFactory", data.etcNPFactory, 1);
        Grids[0].SetValue(row, "etcNIC", data.etcNIC, 1);
        Grids[0].SetValue(row, "etcNPay", data.etcNPay, 1);
        Grids[0].SetValue(row, "etcMPFactory", data.etcMPFactory, 1);
        Grids[0].SetValue(row, "etcMPIC", data.etcMPIC, 1);
        Grids[0].SetValue(row, "etcMPQTotal", data.etcMPQTotal, 1);
        Grids[0].SetValue(row, "etcMPQMax", data.etcMPQMax, 1);
	        
	//예상 판매량
	}else if("ESTIMATED" == flag){
		Grids[0].SetValue(row, "estimated", data, 1);
	}
}

var partTypeRow = null;
window.setPartType = function(productType){
	
	var partTypeCode = productType[0].code;
	
	deletePartType(partTypeRow);
	Grids[0].SetValue(partTypeRow, "partTypeCode", partTypeCode, 1);
	
	if("MOLD" == partTypeCode) {
		Grids[0].SetValue(partTypeRow, "productWeightCanEdit", eval(bomACL["productWeight"].editor), 1);
		Grids[0].SetValue(partTypeRow, "scrapWeightCanEdit", eval(bomACL["scrapWeight"].editor), 1);
	}else if("PRESS" == partTypeCode) {
		Grids[0].SetValue(partTypeRow, "scrapWeightCanEdit", false, 1);
		Grids[0].SetValue(partTypeRow, "productWeightCanEdit", eval(bomACL["productWeight"].editor), 1);
	}else {
		Grids[0].SetValue(partTypeRow, "productWeightCanEdit", false, 1);
		Grids[0].SetValue(partTypeRow, "scrapWeightCanEdit", false, 1);
	}
	
	weightFormula(partTypeRow, "productWeight");
    weightFormula(partTypeRow, "scrapWeight");
    
	var mftFactoryList = SearchUtil.selectArrayCostCodeByPartType(productType);
	
	var eNum = "";
	var eNumKeys = "";
	var childEnumProp = new Object();
	
	for(var i = 0; i < mftFactoryList.length; i++){
		
		var mftFactory = mftFactoryList[i];
		
		var pOid = mftFactory.numberParentOid;
		
		if(pOid != null && pOid != ""){
			
			var childEnum = childEnumProp["mftFactory2Enum" + pOid];
			var childEnumKeys = childEnumProp["mftFactory2EnumKeys" + pOid];
			
			if(childEnum != null ){
				childEnumProp["mftFactory2Enum" + pOid] = childEnum + "|" + mftFactory.numberCodeName;
				childEnumProp["mftFactory2EnumKeys" + pOid] = childEnumKeys + "|" + mftFactory.numberOidLong;
			}else{
				childEnumProp["mftFactory2Enum" + pOid] = "|" + mftFactory.numberCodeName;
				childEnumProp["mftFactory2EnumKeys" + pOid] = "|" + mftFactory.numberOidLong;
			}

		}else{
			eNum += "|" + mftFactory.numberCodeName;
			eNumKeys += "|" +  mftFactory.numberOidLong;
		}
	}
	
	Grids[0].SetValue(partTypeRow, "partTypeName", productType[0].name, 1);
	Grids[0].SetValue(partTypeRow, "partType", productType[0].oid, 1);
	Grids[0].SetValue(partTypeRow, "mftFactory1Enum", eNum, 1);
	Grids[0].SetValue(partTypeRow, "mftFactory1EnumKeys", eNumKeys, 1);
	
	var enumPropNames = Object.keys(childEnumProp);
	
	for(var i = 0; i < enumPropNames.length; i++){
		var name = enumPropNames[i];
		Grids[0].SetValue(partTypeRow, name, childEnumProp[name], 1);
	}
	
	setNeedColumn(Grids[0], partTypeRow);
}

window.deleteRealPartNo = function(row){
	Grids[0].SetValue(row, "realPartNo", null, 1);
	Grids[0].SetValue(row, "partName", null, 1);
}

window.deletePartType = function(row){
	Grids[0].SetValue(row, "partTypeName", null, 1);
	Grids[0].SetValue(row, "partType", null, 1);
	Grids[0].SetValue(row, "partTypeCode", null, 1);
	Grids[0].SetValue(row, "mftFactory1", null, 1);
	Grids[0].SetValue(row, "mftFactory1Enum", null, 1);
	Grids[0].SetValue(row, "mftFactory1EnumKeys", null, 1);
	Grids[0].SetValue(row, "productWeight", 0, 1);
	Grids[0].SetValue(row, "scrapWeight", 0, 1);
	
	var rowKey = Object.keys(row);
	
	for(var i = 0; i < rowKey.length; i++){
		var key = rowKey[i];
		
		if(key.indexOf("mftFactory2") >= 0){
			Grids[0].SetValue(row, key, null, 1);
		}
	}
	
	setNeedColumn(Grids[0], row);
}
window.weightFormula = function(row, col){
	
	var partTypeCode = row.partTypeCode;
	
	var result = 0;
	if("MOLD" == partTypeCode) {
		//제품중량 + 스크랩중량
		result = calculateValue(row.productWeight, row.scrapWeight, "+");
		
		if("scrapWeight" == col){
			result = row.scrapWeight;
		}
	}else if("PRESS" == partTypeCode) {
		
		//(원재료 두께 x 폭 x 피치  x 비중 (비중은 선택된 원재료 코드의 비철원재료 기준정보로 설정)  ) / 1000
		result = calculateValue(row.rMatNMetalT, row.rMatNMetalW, "*");
		result = calculateValue(result, row.rMatNMetalP, "*");
		result = calculateValue(result, row.metalImportance, "*");
		result = calculateValue(result, 1000, "/");
		
		if(isNaN(result) || !isFinite(result)){
			result = 0;
		}

		if("scrapWeight" == col){
			//총중량 - 제품중량
			result = calculateValue(result, row.productWeight, "-");
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
</script>
<c:if test="${not empty ERROR}">
<script>alert("${ERROR}");self.close();parent.close();</script>
</c:if>
<c:if test="${isPopup }">
<script>
$(document).ready(function(){
	$("body").removeClass("body-space");
	$("body").addClass("popup-background");
	$("body").addClass("popup-space");
	
	window.bomCopyAdd = function(){
		if($("input[name='bomCopyRPNo']").val().trim() == ""){
            alert("Copy 대상 프로젝트 번호를 입력하세요.");
            return;
        }
		
		var param = {
				taskOid : "${taskOid}",
				rpNo : $("input[name='bomCopyRPNo']").val()
		}
		
		if(confirm("BOM을 Copy 추가 하시겠습니까?")){
			ajaxCallServer("/plm/ext/cost/bomCopyAdd", param, function(data){
				/* $("input[name='bomCopyRPNo']").val("");
				Grids[0].Reload(); */
				location.href = '/plm/ext/cost/costBomEditor?EDITMODE=TREEEDIT&isPopup=true&taskOid=${taskOid}';
			});
		}
	}
	
	window.costBomUpload = function(){
		
		if($("input[name='uploadFile']").val().trim() == ""){
            alert("업로드할 파일을 추가하세요.");
            return;
        }
		
		var param = $("[name=uploadForm]").serializefiles();
		
		if(confirm("BOM UPLOAD시 기존 BOM은 제거됩니다. 계속하시겠습니까?")){
			ajaxCallServer("/plm/ext/cost/bomExcelUpload", param, function(data){
				location.href = '/plm/ext/cost/costBomEditor?EDITMODE=TREEEDIT&isPopup=true&taskOid=${taskOid}';
			});
		}
	}
	
	Grids.OnClickCell = function(grid, row, col, e){
 		if(grid.id == "costBomEditor" && row.Kind == "Data" && col != 'partName' && ${costCalcResult}){
			
			var param = new Object();
			var productPart = getParentRow(row);
			
			getMyCostCalcResult(productPart.id);
		}
	}
	
	
	window.getParentRow = function(child){
		if(child.Level != 0 && child.Kind == "Data"){
			return getParentRow(child.parentNode);
		}else{
			return child;
		}
	}
	
	
	var tempOid = null;
	window.getMyCostCalcResult = function(oid, isInit){
		
		if(tempOid != oid) tempOid = oid;
		else if(!isInit)   return;
		
		if(oid.indexOf("ext.ket.cost.entity.CostPart") >= 0){
			
			var param = {
	                taskOid : "${taskOid}",
	                partListOid : "${partListOid}",
	                oid : oid,
	                DATATYPE : "COSTPART"
	        }
			
			var data = ajaxCallServer("/plm/ext/cost/getCostCalcResult", param, null, false);
			
			var dataList = data.dataList;
            var caList = data.caList;
            
            $("#costCalcResult tbody tr").remove();
            
            for( var i = 0; i < dataList.length; i++ ){
                
                var part = dataList[i];
                
                var partName = checkNull(part.partName);
                var materialCostExpr = checkNull(part.materialCostExpr).commaFormat(1);
                var laborCostExpr = checkNull(part.laborCostExpr).commaFormat(1);
                var expenseExpr = checkNull(part.expenseExpr).commaFormat(1);
                var manageCostExpr = checkNull(part.manageCostExpr).commaFormat(1);
                var mfcCostExpr = checkNull(part.mfcCostExpr).commaFormat(1);
                var reduceCostExpr = checkNull(part.reduceCostExpr).commaFormat(1);
                var totalCostExpr = checkNull(part.totalCostExpr).commaFormat(1);
                var salesTargetCostExpr = checkNull(part.salesTargetCostExpr).commaFormat(1);
                
                var tr = "<tr>" +
                            "<td >" + partName + "</td>" +
                            "<td class='right' >" + materialCostExpr + "</td>" +
                            "<td class='right' >" + laborCostExpr + "</td>" +
                            "<td class='right' >" + expenseExpr + "</td>" +
                            "<td class='right' >" + mfcCostExpr + "</td>" +
                            "<td class='right' >" + manageCostExpr + "</td>" +
                            "<td class='right' >" + reduceCostExpr + "</td>" +
                            "<td class='right' >" + totalCostExpr + "</td>";
                if(i == 0){
                	
                	var profitRate = data["profitRateExpr"];
                	profitRate = calculateValue(profitRate, 100, "*").commaFormat(1);
                	
                	
                    tr += "<td class='center' rowspan='" + (dataList.length + 1) +"'>" + salesTargetCostExpr + "</td>" +
                    "<td class='center' rowspan='" + (dataList.length + 1) +"'>" + profitRate + "%</td>";
                    
                    $("input[name='salesTargetCostTotal']").val(salesTargetCostExpr);
                }
                
                $("#costCalcResult tbody").append(tr);
                
            }

            var tr = "<tr>" +
                        "<td class='center bgcol fontb' >합계</td>" +
                        "<td class='right' >" + data["materialCostTotal"].commaFormat(1) + "</td>" +
                        "<td class='right' >" + data["laborCostTotal"].commaFormat(1) + "</td>" +
                        "<td class='right' >" + data["expenseTotal"].commaFormat(1) + "</td>" +
                        "<td class='right' >" + data["mfcCostTotal"].commaFormat(1) + "</td>" +
                        "<td class='right' >" + data["manageCostTotal"].commaFormat(1) + "</td>" +
                        "<td class='right' >" + data["reduceCostTotal"].commaFormat(1) + "</td>" +
                        "<td class='right' >" + data["productCostTotal"].commaFormat(1) + "</td>" +
                     "</tr>";

            $("#costCalcResult tbody").append(tr);
		}else{
			$("#costCalcResult tbody").html("");
		}
	}
	
	window.downloadExcelForm = function(cmd){
	     
	     var param = new Object();
	     param.taskOid = "${taskOid}";
	     var url = "/plm/ext/cost/createRealPartNoFormExcel";
	     
	     if(cmd == "BOMUPLOADFORM"){
	    	 url = "/plm/ext/cost/createBomUploadExcel";
	     }
	     
	     ajaxCallServer(url, param, function(data){
	    	 $("#download")[0].src = data.downloadLink;
	     });
	}
	
	window.uploadExcelForm = function(){
		
		if($("input[name='uploadFilePartNo']").val().trim() == ""){
			alert("업로드할 파일을 추가하세요.");
			return;
		}
	    var param = $("[name=uploadForm]").serializefiles();
	    
	    ajaxCallServer("/plm/ext/cost/saveRealPartNo", param, function(data){
	    	Grids[0].Reload();
	    });
	}
	//$("#treeGrid").height($(window).height()+200);
	
	/* var editMode = '${EDITMODE}';
	var category = '${task.taskTypeCategory}';
	if(editMode == 'TREEEDIT' && (category == 'COST016' ||category == 'COST015')){
		alert("※0 레벨 제품을 신규추가시 반드시 확인하세요.※\r\n\r\n1.신규로 추가된 0레벨 제품은 삭제가 불가능합니다.\r\n\r\n2.저장 후 해당 제품은 본 화면에서 조회되지 않습니다.\r\n원가산출대상 팝업에서 신규로 추가된 0버전의 제품을\r\n확인하시기 바랍니다.");
	} */
	
	// 행 추가 시 호출 - 행 추가 가능 여부 체크
    Grids.OnCanRowAdd = function(grid, parent, next) {
		
		var canRootAdd = true;
		var editType = '${EDITTYPE}';
		var editMode = '${EDITMODE}';
	    var category = '${task.taskTypeCategory}';
	    if(editType != 'NEWPART' && editMode == 'TREEEDIT' && (category == 'COST016' ||category == 'COST015')){
	    	canRootAdd = false;
	    }
        
	    if(!canRootAdd){//신규 제품추가는 (rootPart) costCaseListPopup.jsp 에서 진행할 수 있다
	    	// 0 레벨 행 추가 방지
	        if ( parent.Level >= 0) {
	            return true;
	        }else {
	            return false;
	        }	
	    }
	    
	    if(editType == 'NEWPART' &&Grids[0].RowCount > 0){//제품추가는 하나만 가능하도록 한다.
	    	if(parent.Level >= 0){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }
	    		
	    return true;
    }
	
	
});
	
</script>
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="projectOid" value="${projectOid }" />
<input type="hidden" name="taskOid" value="${taskOid }" />
<div class="contents-wrap">
	<div class="sub-title b-space5">
		<img src="/plm/portal/images/icon_3.png" />원가요청서
	</div>
    <div class="b-space5 float-l" style="text-align: right;">
        <c:if test="${fn:indexOf(task.getProject().toString(),'ProductProject') > 0 and task.taskTypeCategory eq 'COST013'}">
        <img src="/plm/portal/images/iocn_excel.png" onclick="downloadExcelForm('PARTNOCONNFORM')" style="cursor:pointer;"/>
        <input type="file" name="uploadFilePartNo" />
        <span class="in-block v-middle r-space7">
            <span class="pro-table">
                <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center"> <a href="javascript:uploadExcelForm()" class="btn_blue">품번 연계</a></span>
                <span class="pro-cell b-right"></span>
            </span>
        </span>
        </c:if>
    </div>
    <div class="b-space5 float-r" style="text-align: right;">
    	<%--<c:if test="${fn:indexOf(task.getProject().toString(),'ProductProject') > 0}">  --%>
    	<c:if test="${EDITMODE eq 'TREEEDIT' and (task.taskTypeCategory eq 'COST013' or task.taskTypeCategory eq 'COST001' )}">
		<input type="text" name="bomCopyRPNo" class="txt_field" value ="" style="width:200px;" />
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center"> <a href="javascript:bomCopyAdd()" class="btn_blue">기존 Bom Copy</a></span>
				<span class="pro-cell b-right"></span>
			</span>
		</span>
		</c:if>
		<%-- </c:if> --%>
        <c:if test="${task.taskTypeCategory eq 'COST016' and EDITTYPE ne 'NEWPART'}">
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
			    <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center">
	                <a href="#top" onclick="javascript:getOpenWindow2('/plm/ext/cost/costReportPopup?taskOid=${taskOid}', 'COSTREPORT', 1280, 720);" class="btn_blue">
	                    REPORT
				    </a>
			    </span>
			    <span class="pro-cell b-right"></span>
			</span>
		</span>
		</c:if>
		<c:if test="${EDITMODE eq 'TREEEDIT' and (task.taskTypeCategory eq 'COST013' or task.taskTypeCategory eq 'COST001' )}">
		<img src="/plm/portal/images/iocn_excel.png" onclick="downloadExcelForm('BOMUPLOADFORM')" style="cursor:pointer;"/>
		<input type="file" name="uploadFile" />
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center"> <a href="javascript:costBomUpload()" class="btn_blue">BOM UPLOAD</a></span>
				<span class="pro-cell b-right"></span>
			</span>
		</span>
		</c:if>
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></span>
				<span class="pro-cell b-right"></span>
			</span>
		</span>
	</div>
	<br>
</div>
</form>
<span class="legend">
<span style="background:#DFE;padding:5px;";>기본입력</span>
<span style="background:#B4EEF2;padding:5px;">필수입력</span>
<span style="background:#FCD;padding:5px;">입력오류</span>
</span>
<div id='treeGrid' style='width:100%;'></div>

<c:if test="${costCalcResult }">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td class="space20"></td></tr></table>
<!-- <div class="sub-title-02 float-l b-space5" style="width:100%;margin-top:50px;"> -->
<div class="sub-title-02 float-l b-space5" style="width:100%;">
	<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>간이산출결과
	
	<span class="float-r" style="color:#FF0000;">납입 물류비/관세 제외 기준</span>
</div>
<div class="b-space5">
	<table summary="" id="costCalcResult" class="table-style-01" style="table-layout:fixed;margin-bottom:50px;">
	    <colgroup>
	       <col width="15%" />
	    </colgroup>
		<thead>
			<tr>
				<td class="center bgcol fontb" rowspan="2">제품명</td>
				<td class="center bgcol fontb" colspan="4">제조원가</td>
				<td class="center bgcol fontb" rowspan="2">관리비</td>
				<td class="center bgcol fontb" rowspan="2">감가비</td>
				<td class="center bgcol fontb" rowspan="2">총원가</td>
				<td class="center bgcol fontb" rowspan="2">판매 목표가</td>
				<td class="center bgcol fontb" rowspan="2">수익률</td>
			</tr>
			<tr>
				<td class="center bgcol fontb">재료비</td>
				<td class="center bgcol fontb">노무비</td>
				<td class="center bgcol fontb">경비</td>
				<td class="center bgcol fontb">제조원가</td>
			</tr>
		</thead>
		<tbody>
		<tr><td colspan=10 style="text-align:center; font-family: NanumGothic, '나눔고딕', Nanumgo, Dotum; font-size: 12px;"><b>간이산출대상을 클릭하세요.</b></td></tr>
		</tbody>
	</table>
</div>
</c:if>

</c:if>
<c:if test="${!isPopup }">
<html>
<body style="margin:0 !important;">
<span class="legend">
<span style="background:#DFE;padding:5px;";>기본입력</span>
<span style="background:#B4EEF2;padding:5px;">필수입력</span>
<span style="background:#FCD;padding:5px;">입력오류</span>
</span>
<div id='treeGrid' style='width:100%;'></div>
</body>
</html>
</c:if>
<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
