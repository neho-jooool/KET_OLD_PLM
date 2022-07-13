<%@ page language='java' contentType='text/html; charset=UTF-8'
	pageEncoding='UTF-8'%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<!DOCTYPE >
<c:if test="${isOpen == false}" >
<script>
$(document).ready(function() {
    alert('부품 정보가 없습니다.');
    window.close();
});
</script>
</c:if>
<c:if test="${isOpen == true}" >
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge' />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<title>Insert title here</title>
<script type="text/javascript" charset="utf-8">
var searchIcon          = "/plm/portal/images/icon_5.png";
var deleteIcon          = "/plm/portal/images/icon_delete.gif";
var mappingRow = null;
var delFlag = "1";
var startYear;
var EditConfig;
$(document).ready(function(){
	
	EditConfig = 1;
    var CellConfig = "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    
    if('${editMode}' == 'VIEW' || '${editMode}' == 'VIEW_EXCEL'){
        EditConfig = 0;
        CellConfig = "ExpandAll,CollapseAll,Reload,Export,Print,Columns";
        $('#btnCtr').hide();
    }
    
	CommonUtil.singleSelect('payPlace', 110); //완제품입고
	CommonUtil.singleSelect('sopYear', 110); //완제품입고
	
	CommonUtil.singleSelect('salesTargetGb', 110);
	//CalendarUtil.dateInputFormat('sopYear'); //sop
	$('#prodUS').number( true );
	$('#salesTargetCostExpr').number( true ,2 );
	$('#disposableCr').number( true ,1 );
	
	/* $('#sopYear').keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
        
        if($(this).val().substr(0,1) == '0'){
        	$(this).val($(this).val().substr(1,$(this).val().length));
        }
    }); */
	
	$('#disposableApplyYear').keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
        
        if($(this).val().substr(0,1) == '0'){
            $(this).val($(this).val().substr(1,$(this).val().length));
        }
    });
    
    
    var start = 5;
    var dt = new Date();
    
    var com_year = dt.getFullYear();
    startYear = com_year-start;
    
    window.setSopYear = function(end){
    	
    	$("#sopYear").empty().data('options');
        for(var y = startYear; y <= (com_year+end); y++){
            $("#sopYear").append("<option value='"+ y +"'>"+ y + " 년" +"</option>");
        }
        
        $("#sopYear").multiselect('refresh');
    }
	
    setSopYear(10);
    
    window.footFormula = function(result){
    	
    	if(isNaN(result) || !isFinite(result)){
            result = 0;
        }
    	if(result == ''){
    		result = 0;
    	}
    	return result;
    }
	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/code/saveCostQty",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
 			Param : {
 				formdata : ""
			},
		},
		Data : {
			Url : '/plm/ext/cost/code/costCodeGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				//partListOid : "${partListOid}",
				DATATYPE : "COSTQty",
				oid : "${productOid}",
				version : "${qty.version}",
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
					id : "costQuantityPopup",
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
					Dragging : 0,
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
					Move : 1,
					Copy : 1,
					CanHide : 0,
					CopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd",
				},
				Cols : [
                        {Name : 'deleteFlag', CanEdit : 0, Visible : 0},
                        {Name : 'salesTargetCostExpr', CanEdit : 0, Visible : 0},
				        {Name : 'year', Width:150, Align : 'left', CanSort : '1', CanEdit : '0', Spanned : '1', Type : "Int", Format : "###,##0 년차"},
				        //{Name : 'quantity', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "0, .### 천개"},
				        {Name : 'quantity', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : "###,##0' EA'"},
				        {Name : 'quantitySum', Width:100, Align : 'center', CanSort : '1', CanEdit : '0', Type : "Int", Format : "###,##0' EA'", Spanned : '1', Formula : "(quantity * $('#prodUS').val())"},
				        {Name : 'lastest',CanEdit : 0, Visible : 0},
				        {Name : 'version',CanEdit : 0, Visible : 0},
				        {Name : 'tempFlag',CanEdit : 0, Visible : 0},
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
						RowSpan : 0,
						Visible : 0
					},{
                        Kind : "Header",
                        id : "Header1",
                        Spanned : '1',
                        RowSpan : 0,
                        CanDelete : '0', 
                        Align : 'Center',
                        year : '년차',
                        quantity : "예상수량(EA/년)",
                        quantitySum : '합계',
					}
				],
				Foot : [
				    {
				        id : "foot1",
				        Spanned : '1',
				        CanDelete : '0', 
				        CanEdit : '0',
				        yearAlign : 'Center',
				        year : '예상물량 합계',
				        yearType : 'String',
				        yearSpan : '2',
				        quantitySumFormula : "footFormula(sum())",
				        Calculated : "1",
                    },
                    {
                        id : "foot2",
                        Spanned : '1',
                        CanDelete : '0', 
                        CanEdit : '0',
                        yearAlign : 'Center',
                        year : '예상물량 최대',
                        yearType : 'String',
                        yearSpan : '2',
                        quantitySumFormula : "footFormula(max())",
                        Calculated : "1",
                        },
				    {
	                     id : "foot3",
	                     Spanned : '1',
	                     CanDelete : '0', 
	                     CanEdit : '0',
	                     yearAlign : 'Center',
                         year : '예상물량 평균',
                         yearType : 'String',
                         yearSpan : '2',
	                     quantitySumFormula : "footFormula(sum()/count())",
	                     Calculated : "1",
	                    },
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
	
	$(function(){
        $('#prodUS').keyup(function() {
           
                var G = Grids[0];
                for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
                	Grids[0].SetValue(r, "quantitySum", r.quantity*$('#prodUS').val(), 1);
                }
            

        });
    });
	
	
	gridSave = function(){
		var G = Grids[0];
		G.Save();
	} 
	
	
	Grids.OnSave = function(G, row, autoupdate) {
		try{
	        //autoupdate = false;

	        /* for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
	        	
	        	if ( r.deleteFlag != delFlag ) {//삭제되지 않은 행에 대해 필수입력 값 체크
	        		if ( r.partNo == null || (r.partNo + "").trim() == "" ) {
	                    alert("자재번호를 입력하십시오.");
	                    setTimeout( function() { G.Focus(r, "partNo", null, true); }, 10);
	                    return true;
	                }else{
	                	if(r.partNo.substring(2,1) == '1'){
	                		alert("수지원재료 자재만 입력가능합니다.");
	                		setTimeout( function() { G.Focus(r, "partNo", null, true); }, 10);
	                        return true;
	                	}
	                	
	                }
	        	    
	        	    for ( var j = G.GetFirst(); j; j = G.GetNext(j) ) {//삭제되지 않은 행에 대해 부품Type,생산처 정보 중복 체크
	                    
	                    if(j.deleteFlag != delFlag && r.r0.rowIndex != j.r0.rowIndex){
	                        rTemp = r.partNo;
	                        jTemp = j.partNo;
	                        
	                        if(rTemp == jTemp){
	                            alert(j.r0.rowIndex + "행과 " + r.r0.rowIndex + " 행은 중복입니다.");
	                            setTimeout( function() { G.Focus(j, "partNo", null, true); }, 10);
	                            return true;
	                        }
	                        
	                    }
	                }
	        	}

	        }
	        
	        if(G.Foot.lastChild.rate != 1){
	        	alert("비율 합계는 100%이어야 합니다.");
	        	return true;
	        } */
	        
	        if(G.GetFirst() == null){
	        	alert("년차를 추가하세요.");
	        	return true;
	        }
	        
	        if($("#payPlace").val() == null || $("#payPlace").val() == ''){
	            alert("완제품입고를 선택하세요.");
	            
	            $('[name="payPlace"]').focus();
	            return true;
	            
	        }
	        
	        if($("#sopYear").val() == null || $("#sopYear").val() == ''){
                alert("SOP를 입력하세요.");
                
                $('[name="sopYear"]').focus();
                return true;
                
            }
	        
	        if($("#prodUS").val() == null || $("#prodUS").val() == ''){
                alert("U/S 를 입력하세요.");
                
                $('[name="sopYear"]').focus();
                return true;
                
            }
	        
	        if($("#salesTargetCostExpr").val() > 0 && ($("#salesTargetGb").val() == null || $("#salesTargetGb").val() == '')){
                alert("판매목표가 구분(목표/확정)을 선택하세요.");
                
                $('[name="salesTargetGb"]').focus();

                return true;                
            }
	        
	        
	        if($("#lastest").is(":checked") ){
	        	$('#lastest').val('1');
	        }else{
	        	$('#lastest').val('0');
	        	
	        	if($("#case option").size() == 1){
	        		$('#lastest').val('1');
	        		$("#lastest").prop('checked', true) ;
	        	}
	        }
	        
	        $('#quantityTotal').val(grid.Foot.firstChild.quantitySum);          //예상물량 합계
	        $('#quantityMax').val(grid.Foot.firstChild.nextSibling.quantitySum);          //예상물량 최대
	        
	        var avg = grid.Foot.lastChild.quantitySum;
	        var num_check=/^([0-9]*)[\.]?([0-9])?$/; //정수여부 체크
	        if(!num_check.test(avg)){
	        	avg = parseFloat(avg).toFixed(3);
	        }

	        
	        $('#quantityAvg').val(avg);          //예상물량 평균
	        
	        
            
            G.Data.Upload.Param.formdata = formParamSet();
	        
	     }catch(e){ alert(e.message); setTimeout( function() { G.Focus(r, "quantity", null, true); }, 10); return true;};
	}
	
	window.formParamSet = function(){
		var arr = $('[name=costForm]').serializeArray();
        var paramObj = {};
        if (arr) {
            $.each(arr, function() {
                paramObj[this.name] = this.value;
            });
        }
        return(JSON.stringify(paramObj));
	}
	
	Grids.OnRowDelete = function(G, row, type) {

	    try{
	    	G.SetValue(row, "deleteFlag", delFlag, 1);
	    	gridYearSet(G);
	    }catch(e){ alert(e.message);};
	}
	
	Grids.OnRenderFinish = function(grid, row){
		
		if(Grids[0].GetFirst() != null){
			var check = Grids[0].GetFirst().lastest;
	        
	        if(check == '1'){
	            $("#lastest").prop('checked', true) ;
	        }	
	        $("#case_create").css("display", "");
		}
		
		
		if(Grids[0].GetFirst() != null){
			$("#version").val(Grids[0].GetFirst().version);
			$("#salesTargetCostExpr").val(Grids[0].GetFirst().salesTargetCostExpr);
			
			$("#payPlace").val(Grids[0].GetFirst().payPlace);
            $("#payPlace").multiselect('refresh');
            
            if(startYear > Grids[0].GetFirst().sopYear){
            	startYear = Grids[0].GetFirst().sopYear;
            	setSopYear(10);
            }
			$("#sopYear").val(Grids[0].GetFirst().sopYear);
			$("#sopYear").multiselect('refresh');
			
			$("#salesTargetGb").val(Grids[0].GetFirst().salesTargetGb);
            $("#salesTargetGb").multiselect('refresh');
            
			
			$("#deliverName").val(Grids[0].GetFirst().deliverName);
			$("#disposableCr").val(Grids[0].GetFirst().disposableCr);
			$("#disposableApplyYear").val(Grids[0].GetFirst().disposableApplyYear);
		}
		
		
		if('${qty.maxVersion}' == $("#version").val()){
			$("#case_delete").css("display", "");
		}
	}
	
	$(function(){
        $('#lastest').change(function() {
        	    var lastest = '0'
        	    if($("#lastest").is(":checked") ){
        	    	lastest = '1';
        	    }
                var G = Grids[0];
                for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
                    Grids[0].SetValue(r, "lastest", lastest, 1);
                }

        });
    });
	
   
    Grids.OnReload = function(G) {
    	
    }
    
    Grids.OnAfterSave = function ( G, result, autoupdate){
        
        try{
            
             if( result == 0 ){
                 
                 alert('저장되었습니다');
                 //G.Reload();
                 //hideProcessing();
                 $("#case_create").css("display", "");
                 if(opener){
                	 opener.setRowPopupProperty('ESTIMATED', $('#quantityTotal').val());
                 }
                 
             }else{
                 
                 alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
                   //window.location.reload();
               }

           } catch (e) {
               alert(e.message);
           };
   }
    
   Grids.OnRowAdded = function(grid, row){
	   
       grid.Focus(row, "itemName", null, true);
       gridYearSet(grid);

   }
   
   window.gridYearSet = function(grid){
	   var yearRowIndex = 1;
	   var ver = '${qty.version}';
	   if(ver == ''){
		   ver = 0;
	   }
	   
	    try{
           
           for ( var r = grid.GetFirst(); r; r = grid.GetNext(r) ) {
               
               if ( r.deleteFlag != delFlag ) {
                   grid.SetValue(r, "year", yearRowIndex, 1);
                   grid.SetValue(r, "tempFlag", yearRowIndex, 1);
                   grid.SetValue(r, "version", ver, 1);
                   yearRowIndex++;
               }
           }
        }catch(e){ alert(e.message);};
   }
   
   window.caseCreate = function(){
	   if(confirm("CASE를 복사 생성하시겠습니까?")){
		   $('[name=costForm]').attr('action', '/plm/ext/cost/code/costQtyCaseCreate.do');
	       $('[name=costForm]').attr('target', 'download');
	       
	       $('[name=costForm]').submit();
	       showProcessing();   
	   }
	   
   }
   
   window.caseDelete = function(){
	   var curVer = '${qty.version}';
	   
	   if(confirm("CASE"+curVer+" 을 삭제하시겠습니까?")){
		   $('[name=costForm]').attr('action', '/plm/ext/cost/code/costQtyCaseDelete.do');
	       $('[name=costForm]').attr('target', 'download');
	       
	       $('[name=costForm]').submit();
	       showProcessing();
	   }
	   
   }
   
   
   $("#case").empty().data('options');
   
   var maxVer = '${qty.maxVersion}';
   for(var i=0; i<= parseInt(maxVer); i++){
       $("#case").append("<option value='"+i+"'>"+ i +"</option>") ; 
   }
   var curVer = '${qty.version}';
   if('${qty.version}' == ''){
	   curVer = '${qty.maxVersion}';
   }
   $("#case").val(curVer).attr("selected", "selected");
   
   CommonUtil.singleSelect('case', 60); //완제품입고
   
   $(function(){
       $('#case').change(function() {
    	   location.href='/plm/ext/cost/code/CostQuantityPopup.do?oid=' + '${productOid}' + '&version=' + $("#case").val();
           showProcessing();
       });
   });
});


</script>
</head>
<body>
<form name="costForm">
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
									<td class="font_01">예상 판매량</td>
								</tr>
							</table>
						</td>
						<td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
					</tr>
				</table>
			</td>
		</tr>
        <tr>
          <td class="space10"></td>
        </tr>
	</table>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
	
	
	<input type="hidden" name="productOid" id="productOid" value="${productOid}">
	<input type="hidden" name="oid" id="oid" value="${oid}">
	<input type="hidden" name="quantityTotal" id="quantityTotal" value="${quantityTotal}">
	<input type="hidden" name="quantityAvg" id="quantityAvg" value="${quantityAvg}">
	<input type="hidden" name="quantityMax" id="quantityMax" value="${quantityMax}">
	<input type="hidden" name="version" id="version" value="${qty.version}">
	
		<tr>
			<td>&nbsp;</td>
			<td align="right">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr id="btnCtr">
					    <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue"
                                        background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:gridSave();" class="btn_blue">저장</a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td>&nbsp;</td>
					    <td id='case_create' style="display:none;">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue"
                                        background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:caseCreate();" class="btn_blue">CASE생성</a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td>&nbsp;</td>
                        <td id='case_delete' style="display:none;">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:caseDelete();" class="btn_blue">CASE삭제</a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td>&nbsp;</td>
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

	<table width="100%" height="100%" border="0" cellspacing="1"
		cellpadding="1">
		<tr>
			<td align="left">
				<form name="costPackingForm">
					<input type="hidden" name="productOid" id="productOid" value="${productOid}">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					    <colgroup>
                        <col width="12%">
                        <col width="38%">
                        <col width="12%">
                        <col width="38%">
                        </colgroup>
						<tr>
							<td class="tdgrayMdeep" style="border-top: 1px solid #CCCCCC;">완제품입고</td>
							<td class="tdwhiteL" style="border-top: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;">
							<ket:select id="payPlace" name="payPlace" value="" className="fm_jmp" style="width: 40px;" codeType="MFTFACTORY" useOidValue="true" multiple='multiple' />
							</td>
						    <td class="tdgrayMdeep" style="border-top: 1px solid #CCCCCC;">SOP</td>
						    <td class="tdwhiteL" style="border-top: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;">
						    <select name="sopYear" id="sopYear" class="fm_jmp" multiple="multiple" style="width:70px"></select>    
						    </td>
						</tr>
						<tr>
							<td class="tdgrayMdeep">U/S</td>
							<td class="tdwhiteL" style="border-bottom: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;"><input type="text" name="prodUS" id="prodUS" class="txt_fieldRO" style="width: 60%" readonly value="${qty.prodUS}"></td>
						    <td class="tdgrayMdeep" style="border-top: 1px solid #CCCCCC;">CASE</td>
						    <td class="tdwhiteL" style="border-top: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;">
						    <select id="case" name="case" class="fm_jmp" multiple="multiple" style="width:70px">
						    </select>
						    최종안 : <input type="checkBox" id="lastest" name ="lastest"/>
						    </td>
						</tr>
						<tr>
						    <td class="tdgrayMdeep" style="border-top: 1px solid #CCCCCC;">납입처</td>
                            <td class="tdwhiteL" style="border-top: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;">
                            <input type="text" class="txt_field" name="deliverName" id="deliverName" style="width: 180px" value="" />
                            </td>
                            <td class="tdgrayMdeep" style="border-top: 1px solid #CCCCCC;">판매목표가</td>
                            <td class="tdwhiteL" style="border-top: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;" colspan='2'>
                            <input type="text" class="txt_field" name="salesTargetCostExpr" id="salesTargetCostExpr" style="width: 150px" value="" />
                            <select name="salesTargetGb" id="salesTargetGb" class="fm_jmp" multiple="multiple" style="width:70px">
                            <option value='선택'>선택</option>
                            <option value='목표'>목표</option>
                            <option value='확정'>확정</option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdgrayMdeep" style="border-top: 1px solid #CCCCCC;">C/R적용율</td>
                            <td class="tdwhiteL" style="border-top: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;">
                            <input type="text" class="txt_field" name="disposableCr" id="disposableCr" style="width: 180px" value="" />%
                            </td>
                            <td class="tdgrayMdeep" style="border-top: 1px solid #CCCCCC;">C/R적용년수</td>
                            <td class="tdwhiteL" style="border-top: 1px solid #CCCCCC; border-right: 1px solid #CCCCCC;" colspan='2'>
                            <input type="text" class="txt_field" name="disposableApplyYear" id="disposableApplyYear" style="width: 70px" value="" maxlength="2" />
                            
                            
                            </td>
                        </tr>
					</table>
				</form>
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
	<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>
</body>
</html>
</c:if>