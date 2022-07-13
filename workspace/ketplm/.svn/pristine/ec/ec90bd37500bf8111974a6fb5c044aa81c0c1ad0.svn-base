<%@ page language='java' contentType='text/html; charset=UTF-8'
	pageEncoding='UTF-8'%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge' />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<!-- EJS TreeGrid End -->
<title>Insert title here</title>

<script type="text/javascript" charset="utf-8">
var searchIcon          = "/plm/portal/images/icon_5.png";
var deleteIcon          = "/plm/portal/images/icon_delete.gif";
var mappingRow = null;
var delFlag = "1";

$(document).ready(function(){
	var EditConfig = 1;
    var CellConfig = "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    
    var isCostAdmin = ${ket:isCostAdmin() };
    
    if(!isCostAdmin){
        EditConfig = 0;
        CellConfig = "ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    }
	var mftFactoryList = eval('${mftFactoryList}');
	
	//window.console.log(mftFactoryList);
	
	var mftFactory2_column = { Name : "mftFactory2", Width : "70", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1, Spanned : '1' };
	
	var eNum = "";
	var eNumKeys = "";
	
	for(var i = 0; i < mftFactoryList.length; i++){
		
		var mftFactory = mftFactoryList[i];
		
		var pCode = mftFactory.pCode;
		
		if(pCode != null){
			
			var childEnum = mftFactory2_column["Enum" + pCode];
			var childEnumKeys = mftFactory2_column["EnumKeys" + pCode];
			
			if(childEnum != null){
				mftFactory2_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
				mftFactory2_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
			}else{
				mftFactory2_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory2_column["EnumKeys" + pCode] = "|" + mftFactory.code;
			}
			
            window.console.log("test2[Enum + code] : "+mftFactory2_column["Enum" + pCode]);
            window.console.log("test2[EnumKeys + code] : "+mftFactory2_column["EnumKeys" + pCode]);
			
		}else{
			eNum += "|" + mftFactory.name;
			eNumKeys += "|" +  mftFactory.code;
		}
	}

	
	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/code/saveCostPartTypeLink",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
/* 			Param : {
				partListOid : "${partListOid}"
			}, */
		},
		Data : {
			Url : '/plm/ext/cost/code/costPartTypeLinkList',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				//partListOid : "${partListOid}",
				DATATYPE : "COSTPARTTYPE"
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
					id : "costPartTypeLink",
					Style : gridStyle,
					SuppressCfg : cookiesType,
					IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : EditConfig,
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
					//MainCol : "name",
					SuppressMessage : '1',
					/* 
					ConstHeight : '1',ConstWidth : '1',
					MaxHeight : '1', MaxWidth : '1',
					MinHeight : '100', MinTagHeight : '100'
					 */
				},
				Toolbar : { Visible : 0 }, //하단 도구 비표시 처리
				Panel : {
					Visible : EditConfig,
					Move : 1,
					Copy : 1,
					CanHide : 0,
					CopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd",
				},
				
				LeftCols : [{Name : 'deleteFlag', CanEdit : 0, Visible : 0},
	                        {Name : 'partTypeName', Width:120, Align : 'left', CanSort : '1', CanEdit : '0', Spanned : '1'},
	                        {Name : 'partTypeOid', CanEdit : 0, Visible : 0},
	                        {Name : 'treeFullFath', Width:130, Align : 'left', CanSort : '1', CanEdit : '1', Spanned : '1'},
	                        {Name : 'mftFactory1', Width:70, Align : 'left', Type : "Enum", Enum : eNum, EnumKeys : eNumKeys, CanEmpty : 1, Range : 0,  Clear : "mftFactory2", Spanned : '1'},
	                        mftFactory2_column,
	                        {Name : 'cv', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
	                        {Name : 'cv_min', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'moldFromVal', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "###,##0"},
                            {Name : 'moldFromSign', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', CanEmpty : '1', Type : "Enum", Enum : "||==|>|<|>=|<=", Range : 0 },
                            {Name : 'cv_max', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'moldToSign', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', CanEmpty : '1', Type : "Enum", Enum : "||==|>|<|>=|<=", Range : 0 },
                            {Name : 'moldToVal', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "###,##0"},
                            {Name : 'ton', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'ton_min', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'tonFromVal', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "###,##0"},
                            {Name : 'tonFromSign', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', CanEmpty : '1', Type : "Enum", Enum : "||==|>|<|>=|<=", Range : 0 },
                            {Name : 'ton_max', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'tonToSign', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', CanEmpty : '1', Type : "Enum", Enum : "||==|>|<|>=|<=", Range : 0 },
                            {Name : 'tonToVal', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "###,##0"},
                            {Name : 'partMax', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'partMax_min', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'quantityMaxFromVal', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "###,##0"},
                            {Name : 'quantityMaxFromSign', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', CanEmpty : '1', Type : "Enum", Enum : "||==|>|<|>=|<=", Range : 0 },
                            {Name : 'partMax_max', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                            {Name : 'quantityMaxToSign', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', CanEmpty : '1', Type : "Enum", Enum : "||==|>|<|>=|<=", Range : 0 },
                            {Name : 'quantityMaxToVal', Width:60, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "###,##0"},
	                       ],
				Cols : [
                        
				        {Name : 'productLossRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'efficientRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        //{Name : 'addManHourRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'laborCostRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.00", Spanned : '1'},
				        {Name : 'laborCostClimbRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'laborCostYear', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Size : "4", Spanned : '1'},
				        {Name : 'unitManage', Width:100, Align : 'left', CanSort : '1', CanEdit : '1',Type : "Float", Format : "0.00", Spanned : '1'},
				        {Name : 'elecCost', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : ",0", Spanned : '1'},
				        {Name : 'elecCostClimbRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'elecCostYear', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Size : "4", Spanned : '1'},
				        {Name : 'machineDpcCost', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : ",0", Spanned : '1'},
				        {Name : 'tabaryu', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Spanned : '1'},
				        {Name : 'moldMaintenance', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.00", Spanned : '1'},
				        {Name : 'inDirectCost', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : ",0", Spanned : '1'},
				        {Name : 'indirectCostRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'buyBackIndirectCostRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'assyLossRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'mtrManageRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'coManageCost', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : ",0", Spanned : '1'},
				        {Name : 'coManageRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'rnd', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'indirectLabourRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'indirectRndRate', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
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
                        id : "Header1",
                        CanDelete : '0', 
                        Align : 'Center',
                        Spanned : '1',
                        partTypeName : '부품Type명',
                        treeFullFath : '경로',
                        mftFactory1 : '생산처1',
                        mftFactory2 :  '생산처2',
                        cv : 'C/V',
                        ton : '설비Ton',
                        partMax : '부품별MAX물량',
                        productLossRate          : '생산Loss율',
                        efficientRate            : '생산효율',
                        //addManHourRate           : '추가공수효율',
                        laborCostRate            : '표준임율',
                        laborCostClimbRate       : '표준임율상승율',
                        laborCostYear            : '표준임율년도',
                        unitManage               : '관리대수',
                        elecCost                 : '표준전력비',
                        elecCostClimbRate        : '표준전력비상승율',
                        elecCostYear             : '표준전력비년도',
                        machineDpcCost           : '기계감가(사출,프레스)',
                        tabaryu                  : '타발유',
                        moldMaintenance          : '금형유지비',
                        inDirectCost             : '간접경비비용',
                        indirectCostRate         : '간접경비율',
                        buyBackIndirectCostRate  : 'Buyback\n간접경비율\n(본사)',
                        assyLossRate             : '조립Loss율',
                        mtrManageRate            : '재료관리비율',
                        coManageCost             : '일반관리비용',
                        coManageRate             : '일반관리비율',
                        rnd                            : 'R&D',
                        indirectLabourRate        : '간접인건비비율\r\n(노무비)',
                        indirectRndRate            : '간접인건비비율\r\n(R&D)',
                        cvSpan : '7',
                        tonSpan : '7',
                        partMaxSpan : '7',
                        partTypeNameRowSpan             : '3',
                        treeFullFathRowSpan             : '3',
                        mftFactory1RowSpan              : '3',
                        mftFactory2RowSpan              : '3',
                        productLossRateRowSpan          : '3',
                        efficientRateRowSpan            : '3',
                        //addManHourRateRowSpan           : '3',
                        laborCostRateRowSpan            : '3',
                        laborCostClimbRateRowSpan       : '3',
                        laborCostYearRowSpan            : '3',
                        unitManageRowSpan               : '3',
                        elecCostRowSpan                 : '3',
                        elecCostClimbRateRowSpan        : '3',
                        elecCostYearRowSpan             : '3',
                        machineDpcCostRowSpan           : '3',
                        tabaryuRowSpan                  : '3',
                        moldMaintenanceRowSpan          : '3',
                        inDirectCostRowSpan             : '3',
                        indirectCostRateRowSpan         : '3',
                        buyBackIndirectCostRateRowSpan  : '3',
                        assyLossRateRowSpan             : '3',
                        mtrManageRateRowSpan            : '3',
                        coManageCostRowSpan             : '3',
                        coManageRateRowSpan             : '3',
                        rndRowSpan                      : '3',
                        indirectLabourRateRowSpan                      : '3',
                        indirectRndRateRowSpan                      : '3',
                    },
                    {
                        Kind : "Header",
                        id : "Header2",
                        Align : 'Center',
                        Spanned : '1',
                        cv_min :  '최소',
                        cv_max :  '최대',
                        ton_min :  '최소',
                        ton_max :  '최대',
                        partMax_min :  '최소',
                        partMax_max :  '최대',
                        cv_minSpan : '3',
                        cv_maxSpan : '3',
                        ton_minSpan : '3',
                        ton_maxSpan : '3',
                        partMax_minSpan : '3',
                        partMax_maxSpan : '3',
                    },
                    {
                    	Kind : "Header",
                    	id : "Header3",
                    	Align : 'Center',
                        moldFromVal :  '기준',
                        moldFromSign :  '부등호',
                        moldToVal :  '기준',
                        moldToSign :  '부등호',
                        tonFromVal :  '기준',
                        tonFromSign :  '부등호',
                        tonToVal :  '기준',
                        tonToSign :  '부등호',
                        quantityMaxFromVal :  '기준',
                        quantityMaxFromSign :  '부등호',
                        quantityMaxToVal :  '기준',
                        quantityMaxToSign :  '부등호',
                    },
                    {
                        Kind : "Filter",
                        id : "Filter",
                    }
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
	
	Grids.OnSave = function(G, row, autoupdate) {
		
		var rows = grid.Rows;
        var rowKeys = Object.keys(rows);
        
		try{
	         
	        //autoupdate = false;
	        
	        var message = "";
	        var dupRows = {};
	        
	        //색상 초기화
	        for(var i = 0; i < rowKeys.length; i++){
	        	var aId = rowKeys[i];
                var aRow = rows[aId];
                if("Data" == aRow.Kind) G.SetAttribute(aRow, "partTypeName", "Color", "", 1);
	        }
	        
            //전체 ROWS 체크
            F1 : for(var i = 0; i < rowKeys.length; i++){
                
                var aId = rowKeys[i];
                var aRow = rows[aId];
                
                if("Data" == aRow.Kind){
                	
                    if(aRow.deleteFlag != delFlag){//삭제되지 않은 행에 대해 필수입력 값 체크
                        
                        if ( aRow.partTypeName == null || (aRow.partTypeName + "").trim() == "" ) {
                            message = "부품Type명을 입력하십시오.";
                            dupRows.aRow = aRow;
                            break;
                        }
                        
                        if ( aRow.mftFactory1 == null || (aRow.mftFactory1 + "").trim() == "" ) {
                        	message = "생산처1을 입력하십시오.";
                            dupRows.aRow = aRow;
                            break;
                        }
                        
                        if ( aRow.mftFactory2 == null || (aRow.mftFactory2 + "").trim() == "" ) {
                        	message = "생산처2을 입력하십시오.";
                            dupRows.aRow = aRow;
                            break;
                        }
                        
                        for(var j = 0; j < rowKeys.length; j++){
                            
                            var bId = rowKeys[j];
                            var bRow = rows[bId];
                            
                            if("Data" == bRow.Kind && bRow.deleteFlag != delFlag && aId != bId){//삭제되지 않은 행에 대해 부품Type,생산처 정보 중복 체크
                            	
                            	aTemp = aRow.partTypeOid + aRow.mftFactory1 + 
                                aRow.mftFactory2 + aRow.moldFromVal + 
                                aRow.moldFromSign + aRow.moldToVal + 
                                aRow.moldToSign + aRow.tonFromVal + 
                                aRow.tonFromSign + aRow.tonToVal + 
                                aRow.tonToSign + aRow.quantityMaxFromVal + aRow.quantityMaxFromSign + aRow.quantityMaxToVal + aRow.quantityMaxToSign;
                                
                                bTemp = bRow.partTypeOid + bRow.mftFactory1 + 
                                        bRow.mftFactory2 + bRow.moldFromVal + 
                                        bRow.moldFromSign + bRow.moldToVal + 
                                        bRow.moldToSign + bRow.tonFromVal + 
                                        bRow.tonFromSign + bRow.tonToVal + 
                                        bRow.tonToSign + bRow.quantityMaxFromVal + bRow.quantityMaxFromSign + bRow.quantityMaxToVal + bRow.quantityMaxToSign;
                                
                                if(aTemp == bTemp){
                                	dupRows.aRow = aRow;
                                	dupRows.bRow = bRow;
                                	message = "중복되는 행이 있습니다.";
                                	break F1;
                                }
                            }
                        }
                    }
                }
            }
	        
	        //오류 발생시 색상 표기 및 경고창 표시
	        if(message != ""){
	        	G.SetAttribute(dupRows.aRow, "", "Color", "#FCD", 1);
	        	if(dupRows.bRow != null) G.SetAttribute(dupRows.bRow, "", "Color", "#FCD", 1);
	        	alert(message);
	            return true;
	        }
	        
            /* 
	        for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
	        	
	        	if ( r.deleteFlag != delFlag ) {//삭제되지 않은 행에 대해 필수입력 값 체크
	        		if ( r.partTypeName == null || (r.partTypeName + "").trim() == "" ) {
	                    alert("부품Type명을 입력하십시오.");
	                    setTimeout( function() { G.Focus(r, "partTypeName", null, true); }, 10);
	                    return true;
	                }
	        		
	        	    if ( r.mftFactory1 == null || (r.mftFactory1 + "").trim() == "" ) {
                        
	        	    	alert("생산처1을 입력하십시오.");
                        setTimeout( function() { G.Focus(r, "mftFactory1", null, true); }, 10);
                        return true;
                    }
	        	    
	        	    if ( r.mftFactory2 == null || (r.mftFactory2 + "").trim() == "" ) {
                        
	        	    	alert("생산처2을 입력하십시오.");
                        setTimeout( function() { G.Focus(r, "mftFactory2", null, true); }, 10);
                        return true;
                    }
                    
	        	    for ( var j = G.GetFirst(); j; j = G.GetNext(j) ) {//삭제되지 않은 행에 대해 부품Type,생산처 정보 중복 체크
	                    
	                    if(j.deleteFlag != delFlag && r.r0.rowIndex != j.r0.rowIndex){
	                        rTemp = r.partTypeOid + r.mftFactory1 + r.mftFactory2 + r.moldFromVal + r.moldFromSign + r.moldToVal + r.moldToSign + r.tonFromVal + r.tonFromSign + r.tonToVal + r.tonToSign;
	                        jTemp = j.partTypeOid + j.mftFactory1 + j.mftFactory2 + j.moldFromVal + j.moldFromSign + j.moldToVal + j.moldToSign + j.tonFromVal + j.tonFromSign + j.tonToVal + r.tonToSign;
	                        
	                        if(rTemp == jTemp){
	                            alert(j.r0.rowIndex + "행과 " + r.r0.rowIndex + " 행은 중복입니다.");
	                            setTimeout( function() { G.Focus(j, "partTypeName", null, true); }, 10);
	                            return true;
	                        }
	                        
	                    }
	                }
	        	}
	        }
	         */
	        
	     }catch(e){ 
	    	 alert(e.message); 
	    	//색상 초기화
            for(var i = 0; i < rowKeys.length; i++){
                var aId = rowKeys[i];
                var aRow = rows[aId];
                if("Data" == aRow.Kind) G.SetAttribute(aRow, "partTypeName", "Color", "", 1);
            }
            return true;
         };
	}
	
	Grids.OnRowDelete = function(G, row, type) {

	    try{
	    	G.SetValue(row, "deleteFlag", delFlag, 1);
	    }catch(e){ alert(e.message);};
	}
	
   
    Grids.OnReload = function(G) {
    	
    }
    
    Grids.OnAfterSave = function ( G, result, autoupdate){
        
        try{
            
             if( result == 0 ){
                 
                 alert('저장되었습니다');
                 //G.Reload();
                 //hideProcessing();
                 
             }else{
                 
                 alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
                   //window.location.reload();
               }

           } catch (e) {
               alert(e.message);
           };
   }
    
   Grids.OnRowAdded = function(grid, row){

	   grid.SetValue(row, "partTypeNameIcon", searchIcon, 1);
       grid.SetValue(row, "partTypeNameIconAlign", "Right", 1);
       grid.SetValue(row, "partTypeNameOnClickSideIcon", "javascript:openGridPopup(Row);", 1);
       grid.SetValue(row, "partTypeNameButton", deleteIcon, 1);
       grid.SetValue(row, "partTypeNameOnClickSideButton", "javasciprt:deletePopupValue(Row);", 1);
       
       grid.Focus(row, "partTypeName", null, true);

   }
   
   window.openGridPopup = function(row){
	   mappingRow = row;
	   //showProcessing();
	   SearchUtil.selectOneCostPartTypeWithCallBack('loadCostPartType','onlyLeaf=Y&openAll=N');
	   //SearchUtil.selectCostPartType(loadCostPartType,'onlyLeaf=Y&openAll=N');
   }
   
   window.loadCostPartType = function(returnValue){
	   //hideProcessing();
       Grids[0].SetValue(mappingRow, "partTypeName", returnValue[0].name, 1);
	   Grids[0].SetValue(mappingRow, "partTypeOid", returnValue[0].id, 1);
	   Grids[0].SetValue(mappingRow, "treeFullFath", returnValue[0].url, 1);
	  /*  var testList = SearchUtil.selectArrayCostCodeByPartType(returnValue);
	   
	   var test2 = new Object();
	   var eNum2 = "";
	   var eNumKeys2 = "";
	  $.each(testList, function(i) {
		  
 		  
          
          var pCode = this.numberParentOid;
          var code = this.numberCodeOid;

          if(this.parentOid != '0'){
              
              var childEnum = test2["Enum" + code];
              
              var childEnumKeys = test2["EnumKeys" + code];
              
              if(childEnum != null){
            	  test2["Enum" + pCode] = childEnum + "|" + this.numberCodeName;
            	  test2["EnumKeys" + pCode] = childEnumKeys + "|" + code;
              }else{
            	  test2["Enum" + pCode] = "|" + this.numberCodeName;
            	  test2["EnumKeys" + pCode] = "|" + code;
              }
              window.console.log("test2[Enum + code] : "+test2["Enum" + pCode]);
              window.console.log("test2[EnumKeys + code] : "+test2["EnumKeys" + pCode]);
              
              
          }else{
        	  
        	  eNum2 += "|" + this.numberCodeName;
        	  eNumKeys2 += "|" +  this.numberCodeOid;
          }
          
          
          
	      
	  });
	  
	  alert(eNum2);
      alert(eNumKeys2); */
	  
	  
   }
   
   window.deletePopupValue = function(row){
	   Grids[0].SetValue(row, "partTypeName", "", 1);
       Grids[0].SetValue(row, "partTypeOid", "", 1);
       Grids[0].SetValue(row, "treeFullFath", "", 1);
   }
   
   
   window.priceInterface = function(){
	   
	   showProcessing();
	   alert("SAP 인터페이스를 시작합니다.");
	   $.ajax({
           type: 'get',
           async: false,
           cache: false,
           url: '/plm/ext/cost/code/purchasePriceSapInterface.do',
           success: function (data) {
               try{
                   if(data == 'S'){
                       alert("동기화되었습니다.");
                   }else if(data == 'F'){
                       alert("실패하였습니다.");
                   }else{
                       alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다.");
                   }
                   
               }catch(e){alert(e.message);}
           },
           fail : function(){
               alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
           }
       });
	   
	   hideProcessing();
   }
   
   $("#treeGrid").height($(window).height()-150);
   $(window).resize(function(){
       $("#treeGrid").height($(window).height()-150);
   });
});


</script>
</head>
<body>
	<table style="width: 100%; height: 100%;">
		<tr>
			<td valign="top">
				<table style="width: 100%;">
					<tr>
						<td background="/plm/portal/images/logo_popupbg.png">
							<table style="height: 28px;">
								<tr>
									<td width="7"></td>
									<td width="20"><img src="/plm/portal/images/icon_3.png"></td>
									<td class="font_01">부품Type별 기준정보 관리</td>
								</tr>
							</table>
						</td>
						<td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
					</tr>
				</table>
			</td>
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
			<td>&nbsp;</td>
			<td align="right">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>

						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="5">&nbsp;</td>
									<td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
									<td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
									<a href="#" onclick="javascript:getOpenWindow2('/plm/ext/cost/code/costPartTypeEditor.do','createSalesProjectFormPopup',850,800);" class="btn_blue">부품Type설정</a></td>
									<td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
								</tr>
							</table>
						</td>
						<td width="5">&nbsp;</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
									<td class="btn_blue"
										background="/plm/portal/images/btn_bg1.gif"><a href="#"
										onclick="javascript:priceInterface();"
										class="btn_blue">수지원재료단가 동기화</a></td>
									<td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
								</tr>
							</table>
						</td>
						<td width="5">&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue"
                                        background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
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
							<div id="treeGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
						</div>
					</div>
				</div> <!-- EJS TreeGrid Start -->
			</td>
		</tr>
	</table>

</body>
</html>