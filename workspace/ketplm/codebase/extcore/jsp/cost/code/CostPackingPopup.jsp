<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- EJS TreeGrid End -->
<c:if test="${isOpen == false}" >
<script>
$(document).ready(function() {
    alert('제품정보가 없습니다.');
    window.close();
});
</script>
</c:if>

<c:if test="${isOpen == true}" >
<script type="text/javascript">
var delFlag = "1";

var ProductMaxQty = 0;
var ProductTotalQty = 0;
var Productus = 0;
var calcObj = {
		        packunit        : '0', 
		        packQuantity    : '0', 
		        collectRate     : '0', 
		        haveMonth       : '0', 
		        month           : '12', 
		        collectOption   : '', 
		        packingPrice    : '0', 
		        haveUnit        : '0', 
		        packquantitySum : '0',
		        currencyCode    : '0',
		        packBoxUnit          : '0',
		        packPalletUnit       : '0',
		        loss : '1'
		      };

$(document).ready(function(){
	var EditConfig = 1;
    var CellConfig = "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    
    if('${editMode}' == 'VIEW' || '${editMode}' == 'VIEW_EXCEL'){
        EditConfig = 0;
        CellConfig = "ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    }
    
    $(document).attr('title','포장비');
    $('#packBoxUnit').number( true );
    $('#packPalletUnit').number( true );
    
    ProductMaxQty = '${ProductMaxQty}';
    ProductTotalQty = '${ProductTotalQty}';
    Productus = '${Productus}';
    
    var grid = TreeGrid({
        Debug : 0,
        Upload : {
            Url : "/plm/ext/cost/code/saveCostPacking",
            Format : "JSON",
            Flags : "AllCols",
            Data : "treeData",
            Type : "Changes,Body",
            Param : {
                //partListOid : "${partListOid}",
                formdata : ""
            }
        },
        Data : {
            Url : '/plm/ext/cost/code/costCodeGridData',
            Method : 'POST',
            Format : 'JSON',
            Param : {
                //partListOid : "${partListOid}",
                DATATYPE : "COSTPacking",
                oid : "${partOid}",
                productOid : "${productOid}",
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
                    id : "costPackingPopup",
                    Style : gridStyle,
                    SuppressCfg : 1,
                    IdPrefix : "T",
                    IdChars : "0123456789",
                    NumberId : 1,
                    Undo : 1,
                    Sorting : 1,
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
                Panel : {
                    Move : 1,
                    Copy : 1,
                    CanHide : 0,
                    CopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd",
                },
                Cols : [    
                            {Name : 'deleteFlag', CanEdit : 0, Visible : 0, Spanned : '1'},
                            {Name : 'currencyNumberCode', CanEdit : 0, Visible : 0, Spanned : '1'},
                            {Name : 'packingCodeName', CanEdit : 0, Visible : 0, Spanned : '1'},
                            {Name : 'packingCode', Width:100, Align : 'center', Type : "Enum", Enum : "${eNumPacking}", EnumKeys : "${eNumKeysPacking}", Range : 0, Spanned : '1'},
                            {Name : 'packingPrice', Width:100, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0", Spanned : '1'},
                            {Name : 'currencyCode',  Width:100, Align : 'center', Type : "Enum", Enum : "${eNumExRate}", EnumKeys : "${eNumKeysExRate}", CanEmpty : 1, Range : 0, Spanned : '1'},
                            {Name : 'packQuantity', Width:80, Align : 'center', CanSort : '0', CanEdit : '1',  Type : "Int", Format : "###,##0" + '" EA"', Spanned : '1'},
                            {Name : 'packunit', Width:80, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text", Spanned : '1'},
                            {Name : 'packquantitySum', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0 원", Spanned : '1', Formula : "((packingPrice*currencyCode)*packQuantity)" },
                            {Name : 'collectOption', Width:80, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Enum", Enum : "|회수|미회수", Spanned : '1' },
                            {Name : 'haveMonth', Width:80, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0", Spanned : '1' },
                            {Name : 'collectRate', Width:80, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
                            {Name : 'haveUnit', Width:90, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0" + '" EA"', Spanned : '1'},
                            {Name : 'packUnitPrice', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Int", Format : "0.00 원", Spanned : '1'},
                            {Name : 'packingNote', Width:250, Align : 'left', CanSort : '0', CanEdit : '1', Type : "Text", Spanned : '1'},
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
                        Visible : 0,
                    }
                    ,{
                        Kind : "Header",
                        id : "Header1",
                        //RowSpan : 0,
                        Spanned : '1',
                        CanDelete : '0', 
                        Align : 'Center',
                        packingCode : '포장재\n사양',
                        packingCodeRowSpan : '2',
                        packingPrice : '포장재 단가/단위',
                        packingPriceSpan : '2',
                        packQuantity : '포장재 수량/단위',
                        packQuantitySpan : '2',
                        packquantitySum : '포장재\n합계',
                        packquantitySumRowSpan : '2',
                        collectOption : '회수 정보',
                        collectOptionSpan : '4',
                        packUnitPrice : '개별\n포장비',
                        packUnitPriceRowSpan : '2',
                        packingNote : '비고',
                        packingNoteRowSpan : '2',
                    }
                    ,{
                        Kind : "Header",
                        id : "Header2",
                        //RowSpan : 0,
                        Spanned : '1',
                        CanDelete : '0', 
                        Align : 'Center',
                        packingPrice : '단가',
                        currencyCode : '단위',
                        packQuantity : '수량',
                        packunit : '단위',
                        collectOption : '회수 유/무',
                        haveMonth : '보유개월',
                        collectRate : '회수율',
                        haveUnit : '보유수량',
                    }
                ],
                Foot : [{
                          id : "foot1",
                          Spanned : '1',
                          CanDelete : '0', 
                          CanEdit : '0',
                          packingCodeAlign : 'Center',
                          packingCode : '포장비 합계',
                          packingCodeSpan : '10',
                          packUnitPriceAlign : 'Center',
                          packUnitPriceSpan : '2',
                          packUnitPriceFormula : "sum()",
                          Calculated : "1",
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
                        CopyRow       : '<%=messageService.getString("e3ps.message.ket_message", "02230") %>'       //위에 아이템 복사
                        CopyRowBelow  : '<%=messageService.getString("e3ps.message.ket_message", "02070") %>'       //아래에 아이템 복사
                        CopyTree      : '<%=messageService.getString("e3ps.message.ket_message", "02231") %>'       //위에 Tree 복사
                        CopyTreeBelow : '<%=messageService.getString("e3ps.message.ket_message", "02071") %>'       //아래에 Tree 복사
                        AddRow        : '<%=messageService.getString("e3ps.message.ket_message", "02232") %>'       //위에 신규 아이템 추가
                        AddRowBelow   : '<%=messageService.getString("e3ps.message.ket_message", "02072") %>'       //아래에 신규 아이템 추가
                        AddChild      : '<%=messageService.getString("e3ps.message.ket_message", "02801") %>'       //첫번째 하위 아이템 추가
                        AddChildEnd   : '<%=messageService.getString("e3ps.message.ket_message", "01356") %>'       //마지막 하위 아이템 추가
                         --%>
                    },
                    Alert : {
                        DelRow : "%d 아이템을 삭제하시겠습니까?"        //아이템을 삭제하시겠습니까?
                        <%-- DelRow : "%d <%=messageService.getString("e3ps.message.ket_message", "00494") %>"      //아이템을 삭제하시겠습니까? --%>
                    }
                }
            }
        }
    }, "treeGrid");
    
    
    
    $(function(){
    	$('#packPalletUnit').keyup(function() {
    		

    			var G = Grids[0];
                for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
                    setCalc(r);
                }
    		

    	});
    });
    
    $(function(){
        $('#packBoxUnit').keyup(function() {


	        	var G = Grids[0];
	            for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
	            	setCalc(r);
	            }
        	
        });
    });
    
    window.setCalc = function(row){
    	
    	if(row.packingCodeName == '라벨'){
    		calcObj.loss = '1.03';
    	}else{
    		calcObj.loss = '1';
    	}
    	
    	calcObj.packunit = row.packunit;
        calcObj.packQuantity = row.packQuantity;
        calcObj.collectRate = row.collectRate;
        calcObj.haveMonth = row.haveMonth;
        
        calcObj.collectOption   = row.collectOption; 
        calcObj.packingPrice    = row.packingPrice; 
        calcObj.haveUnit        = row.haveUnit; 
        calcObj.packquantitySum = row.packquantitySum;
        calcObj.currencyCode    = row.currencyCode;
        
        calcObj.packBoxUnit          = $('#packBoxUnit').val().replace(/,/gi,"");
        calcObj.packPalletUnit       = $('#packPalletUnit').val().replace(/,/gi,"");
        
        var result = formulaHaveUnit(calcObj);
        
        Grids[0].SetValue(row, "haveUnit", result, 1);//보유수량
        
        result = formulaPackUnitPrice(calcObj);
        
        Grids[0].SetValue(row, "packUnitPrice", result, 1);//개별포장비
    }
    
    window.nullCheck = function(val){
        return val != null && (val + "").trim() != "";
    }
    
    window.formulaPackUnitPrice = function(calcObj){//개별포장비 연산
    	
    	var qty = 0;
    	var result = 0;
    	
    	if(calcObj.packunit == '/Pallet'){//포장재 사양이 Pallet일 때
            //qty = calcObj.packPalletUnit; //포장수량(pallet)
            
            //수식 변경 2018.12.28 이경무 과장 요청
            qty = fixed(calcObj.packBoxUnit) * fixed(calcObj.packPalletUnit) //포장수량(box) * 포장수량(pallet)
        }else if(nullCheck(calcObj.packunit)){// 포장재 사양이 Pallet가 아닐때
            qty = calcObj.packBoxUnit; //포장수량(box)
        }
    	
        if(calcObj.collectOption == '미회수'){
        	
        	result = fixed((fixed(calcObj.packquantitySum)/fixed(qty)) * fixed(calcObj.loss) );
        	
        }else if(calcObj.collectOption == '회수'){
        	result = fixed( (((fixed(calcObj.packingPrice) * fixed(calcObj.currencyCode)) * fixed(calcObj.haveUnit)) / fixed(ProductTotalQty)) * fixed(calcObj.loss) );
        }
        
        if(isNaN(result) || !isFinite(result)){
            result = 0;
        }
        
        return result;
    }
    
    window.formulaHaveUnit = function(calcObj){//보유수량 연산
        var qty = 0;
        
        if(calcObj.packunit == '/Pallet'){//포장재 사양이 Pallet일 때
            //qty = calcObj.packPalletUnit; //포장수량(pallet)
            qty = fixed(calcObj.packBoxUnit) * fixed(calcObj.packPalletUnit) //포장수량(box) * 포장수량(pallet)
        }else if(nullCheck(calcObj.packunit)){// 포장재 사양이 Pallet가 아닐때
            qty = calcObj.packBoxUnit; //포장수량(box)
        }
        
        //ProductMaxQty : 제품의 max 물량
        //Productus     : 제품의 u/s
        //qty           : 포장수량(box, pallet)
        //month         : 기준개월수(현재 12개월)
        //packQuantity  : 포장재수량
        //haveMonth     : 보유개월(디폴트 3개월)
        //collectRate   : 회수율(디폴트 80%)
        
        //result = ((제품Max 물량 x 제품 u/s) x 12) / ((포장수량 / 포장재수량) * (보유개월 / 회수율))
        //var result =  ( fixed( fixed((fixed(ProductMaxQty) * fixed(Productus))) / calcObj.month ) / fixed((fixed(qty) / fixed(calcObj.packQuantity))) * fixed((fixed(calcObj.haveMonth) / fixed(calcObj.collectRate))) );
        
        //result = ((제품Max 물량 x 제품 u/s) x 12) / ((포장수량 / 포장재수량) * (보유개월 / 회수율))
        //이미 제품Max 물량에 제품 u/s가 반영되어 있으므로 그냥 1을 곱하는 것으로 처리
        var result =  ( fixed( fixed((fixed(ProductMaxQty) * fixed(1))) / calcObj.month ) / fixed((fixed(qty) / fixed(calcObj.packQuantity))) * fixed((fixed(calcObj.haveMonth) / fixed(calcObj.collectRate))) );
        result = roundUp(result,-1);
        if(isNaN(result) || !isFinite(result)){
            result = 0;
        }
        calcObj.haveUnit = result;
        return result;
    }

    Grids.OnAfterValueChanged = function(G, row, col, val){
        
    	if(col == 'packingCode'){
    		
    		var enumVal = G.Cols.packingCode.Enum.replace('||','').split('|');
    		var EnumKeys = G.Cols.packingCode.EnumKeys.replace('||','').split('|');
    		var Edata = new Array();
    		for(var i=0; i<enumVal.length; i++){
    			obj = {key : EnumKeys[i] , value : enumVal[i]};
    			Edata[i] = obj;
    		}
    		
    		for(var i=0; i<Edata.length; i++){
    			if(Edata[i].key == val){
    				
    				G.SetValue(row, "packingCodeName", Edata[i].value, 1);
    				
    				if(Edata[i].value == 'Pallet'){
    					G.SetValue(row, "packunit", '/Pallet', 1);
    					packingCode = Edata[i].value;
                        break;
    				}else{
    					G.SetValue(row, "packunit", '/Box', 1);
    					packingCode = Edata[i].value;
    					
    					if(Edata[i].value == '라벨'){
    						G.SetValue(row, "packingCodeName", '라벨', 1);
    					}
    					break;
    				}
    			}
    		}
    	}
    	setCalc(row);
    }
    
    window.fixed = function(num){//부동소수점 연산오류 방지
    	return parseFloat(num).toFixed(3);
    }
    
    window.roundUp = function(num, precision) {//1단위 올림
    	precision = Math.pow(10, precision);
    	return Math.ceil(num * precision) / precision;
    }
    
    Grids.OnRowAdded = function(Grid,  TRow){
    	
        /* var packBoxUnit = $('#packBoxUnit').val();
        var packPalletUnit = $('#packPalletUnit').val();
        
        if(!nullCheck(packBoxUnit) || !nullCheck(packPalletUnit)){
        	Grid.DeleteRow(TRow, 2);
        	alert('포장수량을 먼저 입력하시기 바랍니다.');
        	if(!nullCheck(packBoxUnit)){
        		$('#packBoxUnit').focus();	
        	}else if(!nullCheck(packBoxUnit)){
                $('#packPalletUnit').focus();   
            }
        	
        	
        	return true;
        } */
        
        try{
        	Grid.SetString( TRow, "haveMonth", "3", true );
        	Grid.SetString( TRow, "collectRate", "0.8", true );
        	
        }catch(e){ alert(e.message);};
        
    }
    
    Grids.OnSave = function(G, row, autoupdate) {
        try{
        	
        	if($('#packBoxUnit').val() == ''){
        		alert('포장수량(EA/Box)를 입력하세요.');
        		$('#packBoxUnit').focus();
        		return true;
        	}
        	
            if($('#packPalletUnit').val() == ''){
            	alert('포장수량(/Pallet)를 입력하세요.');
            	$('#packPalletUnit').focus();
            	return true;
            }
            
            for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
                
                if ( r.deleteFlag != delFlag ) {//삭제되지 않은 행에 대해 필수입력 값 체크
                    if ( r.packingCode == null || (r.packingCode + "").trim() == "" ) {
                        alert("포장재사양을 입력하십시오.");
                        setTimeout( function() { G.Focus(r, "packingCode", null, true); }, 10);
                        return true;
                    }
                    
                    if ( r.packingPrice <= 0 ) {
                        alert("포장재 단가를 입력하십시오.");
                        setTimeout( function() { G.Focus(r, "packingPrice", null, true); }, 10);
                        return true;
                    }
                    
                    if ( r.currencyCode == null || (r.currencyCode + "").trim() == "" ) {
                        alert("포장재 단위를 선택하세요.");
                        setTimeout( function() { G.Focus(r, "currencyCode", null, true); }, 10);
                        return true;
                    }
                    
                    if ( r.packQuantity < 1 ) {
                        alert("포장재 수량을 입력하세요.");
                        setTimeout( function() { G.Focus(r, "packQuantity", null, true); }, 10);
                        return true;
                    }
                    
                    if ( r.collectOption == null || (r.collectOption + "").trim() == "" ) {
                        alert("회수 유/무를 선택하세요.");
                        setTimeout( function() { G.Focus(r, "packQuantity", null, true); }, 10);
                        return true;
                    } 
                    
/*                     if ( r.haveMonth < 1 ) {
                        alert("보유개월을 입력하세요.");
                        setTimeout( function() { G.Focus(r, "packQuantity", null, true); }, 10);
                        return true;
                    } */
                    
/*                     if ( r.collectRate < 1 ) {
                        alert("회수율을 입력하세요.");
                        setTimeout( function() { G.Focus(r, "packQuantity", null, true); }, 10);
                        return true;
                    } */
                    
/*                     for ( var j = G.GetFirst(); j; j = G.GetNext(j) ) {//삭제되지 않은 행에 대해 1단계,2단계,3단계 체크
                        
                        if(j.deleteFlag != delFlag && r.r0.rowIndex != j.r0.rowIndex){
                            rTemp = r.factory1+"|"+r.factory2+"|"+r.factory3;
                            jTemp = j.factory1+"|"+j.factory2+"|"+j.factory3;
                            if(rTemp == jTemp){
                                alert(j.r0.rowIndex + "행과 " + r.r0.rowIndex + " 행은 중복입니다.");
                                setTimeout( function() { G.Focus(j, "factory1", null, true); }, 10);
                                return true;
                            }
                            
                        }
                    } */
                } 

            }
            
        	var enumVal = G.Cols.currencyCode.Enum.replace('||','').split('|');
            var EnumKeys = G.Cols.currencyCode.EnumKeys.replace('||','').split('|');
            var Edata = new Array();
            for(var i=0; i<enumVal.length; i++){
                obj = {key : EnumKeys[i] , value : enumVal[i]};
                Edata[i] = obj;
            }
            
            for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
            	for(var i=0; i<Edata.length; i++){
            		if(Edata[i].key == r.currencyCode){
            			G.SetValue(r, "currencyNumberCode", Edata[i].value, 1);
            		}
            	}
            }
            
            $('#packUnitPriceSum').val(G.Foot.lastChild.packUnitPrice);
            
            var arr = $('[name=costPackingForm]').serializeArray();
            var paramObj = {};
            if (arr) {
                $.each(arr, function() {
                	paramObj[this.name] = this.value;
                });
            }
            G.Data.Upload.Param.formdata = JSON.stringify(paramObj);
            
            //autoupdate = false;

            
            
         }catch(e){ alert(e.message); setTimeout( function() { G.Focus(r, "factory1", null, true); }, 10); return true;};
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
            
             if( result >= 0 ){
                 
                 alert('저장되었습니다');
                 //G.Reload();
                 //hideProcessing();
                 
                 if(opener){
                	 opener.setRowPopupProperty('QUANTITY', $('#packBoxUnit').val());
                 }
                 
             }else{
                 
                 alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
                   //window.location.reload();
               }

           } catch (e) {
               alert(e.message);
           };
   }
    
    Grids.OnRenderFinish = function(G){//packingCodeName 세팅
    	var enumVal = G.Cols.packingCode.Enum.replace('||','').split('|');
        var EnumKeys = G.Cols.packingCode.EnumKeys.replace('||','').split('|');
        var Edata = new Array();
        for(var i=0; i<enumVal.length; i++){
            obj = {key : EnumKeys[i] , value : enumVal[i]};
            Edata[i] = obj;
        }
        
        for(var i=0; i<Edata.length; i++){
        	
        	for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
        		if(Edata[i].key == r.packingCode){
        			G.SetValue(r, "packingCodeName", Edata[i].value, 1);	
        		}
        	}
        }
    }
   
});

</script>
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
								<td class="font_01">포장비</td>
							</tr>
						</table>
					</td>
					<td width="136" align="right"><img
						src="/plm/portal/images/logo_popup.png"></td>
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
<table border="0" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
					<td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
						<a href="javascript:window.close();" class="btn_blue">닫기</a>
					</td>
					<td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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

<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td align="left">
			<form name="costPackingForm">
				<input type="hidden" name="packUnitPriceSum" id="packUnitPriceSum" value="${packUnitPriceSum}">
				<input type="hidden" name="partOid" id="partOid" value="${partOid}">
				<input type="hidden" name="productOid" id="productOid" value="${productOid}">
				<table width="600px" border="0" cellspacing="0" cellpadding="0">
					<tr>
					    <td width="300px" class="tdgrayMdeep" style="border-top : 1px solid #CCCCCC;">포장수량(EA/Box)</td>
                        <td class="tdwhiteL" style="border-top : 1px solid #CCCCCC; border-right : 1px solid #CCCCCC;"><input type="text" name="packBoxUnit" id="packBoxUnit" class="txt_field" style="width: 60%" value="${packBoxUnit}">&nbsp;&nbsp;EA</td>
					</tr>
					<tr>
                        <td width="300px" class="tdgrayMdeep">포장수량(Box/Pallet)</td>
                        <td class="tdwhiteL" style="border-bottom : 1px solid #CCCCCC; border-right : 1px solid #CCCCCC;"><input type="text" name="packPalletUnit" id="packPalletUnit" class="txt_field" style="width: 60%" value="${packPalletUnit}">&nbsp;&nbsp;Box</td>
                    </tr>
				</table>
			</form>
		</td>
	</tr>
</table>


<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space10"></td>
    </tr>
</table>


<!-- EJS TreeGrid Start -->
<div class="content-main">
	<div class="container-fluid">
		<div class="row-fluid">
			<div id="treeGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
		</div>
	</div>
</div>
<!-- EJS TreeGrid Start -->
</c:if>

