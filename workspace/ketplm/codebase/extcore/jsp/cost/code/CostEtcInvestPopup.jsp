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

$(document).ready(function(){
	
	var EditConfig = 1;
    var CellConfig = "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    
    if('${editMode}' == 'VIEW' || '${editMode}' == 'VIEW_EXCEL'){
        EditConfig = 0;
        CellConfig = "ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    }
		
	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/code/saveCostEtcInvest",
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
				DATATYPE : "COSTEtcInvest",
				oid : "${partOid}"
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
					id : "costEtcInvestPopup",
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
				Panel : {
					Move : 1,
					Copy : 1,
					CanHide : 0,
					CopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd",
				},
				Cols : [
                        {Name : 'deleteFlag', CanEdit : 0, Visible : 0},
				        {Name : 'itemName', Width:150, Align : 'left', CanSort : '1', CanEdit : '1', Spanned : '1', Type : "Text"},
				        {Name : 'etcNfactory', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Enum", Enum : "|${eNumMaking}", EnumKeys : "|${eNumKeysMaking}", Spanned : '1'},
				        {Name : 'etcNcost', Width:100, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0,.### 천원", Spanned : '1'},
				        {Name : 'etcNunit_1', Width:90, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Enum", Enum : "|${eNumUnit}", EnumKeys : "|${eNumKeysUnit}", Spanned : '1'},
				        {Name : 'etcNpay', Width:100, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0,.### 천원", Spanned : '1'},
				        {Name : 'etcNunit_2', Width:80, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Enum", Enum : "|${eNumUnit}", EnumKeys : "|${eNumKeysUnit}", Spanned : '1'},
				        {Name : 'etcPfactory', Width:100, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Enum", Enum : "|${eNumMaking}", EnumKeys : "|${eNumKeysMaking}", Spanned : '1'},
				        {Name : 'etcPcost', Width:100, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0,.### 천원", Spanned : '1'},
				        {Name : 'etcPunit', Width:80, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Enum", Enum : "|${eNumUnit}", EnumKeys : "|${eNumKeysUnit}", Spanned : '1'},
				        {Name : 'etcTotalQty', Width:80, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Int", Format : "0,.### 천개", Spanned : '1'},
				        {Name : 'etcMaxQty', Width:80, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Int", Format : "0,.### 천개", Spanned : '1'},
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
                        itemName : '항목',
                        itemNameRowSpan : "2",
                        etcNfactory : '신규(천원)',
                        etcNfactorySpan : "5",
                        etcPfactory : '양산(천원)',
                        etcPfactorySpan : "3",
                        etcTotalQty : '양산수량(천개)',
                        etcTotalQtySpan : "2",
					},{
                        Kind : "Header",
                        id : "Header2",
                        Spanned : '1',
                        RowSpan : 0,
                        CanDelete : '0', 
                        Align : 'Center',
                        etcNfactory : '제작처',
                        etcNcost : '투자비',
                        etcNunit_1 : '화폐단위',
                        etcNpay : '지급액',
                        etcNunit_2 : '화폐단위',
                        etcPfactory : '제작처',
                        etcPcost : '투자비',
                        etcPunit : '화폐단위',
                        etcTotalQty : 'TOTAL',
                        etcMaxQty : 'MAX',
                    }
				],
				Foot : [
				    {
                    id : "foot1",
                    Spanned : '1',
                    CanDelete : '0', 
                    CanEdit : '0',
                    itemNameAlign : 'Center',
                    itemName : '신규투자비 합계',
                    itemNameSpan : '2',
                    etcNcostFormula : "sum()",
                    etcNunit_1Align : 'Center',
                    etcNunit_1 : '신규지급액 합계',           
                    etcNpayFormula : "sum()",
                    etcNunit_2Span : '2',
                    etcNunit_2Align : 'Center',
                    etcNunit_2 : '양산투자비 합계',
                    etcPcostFormula : "sum()",
                    etcPunitAlign : 'Center',
                    etcPunit : '양산수량 합계',           
                    etcTotalQtyFormula : "sum()",
                    etcMaxQtyFormula : "sum()",
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
		try{
	         
	        //autoupdate = false;

	        for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
	        	
	        	if ( r.deleteFlag != delFlag ) { //삭제되지 않은 행에 대해 필수입력 값 체크
	        		
	        		var etcNfactory    = checkNull(r.etcNfactory).trim();    //제작처(신규)
	        		var etcPfactory    = checkNull(r.etcPfactory).trim();    //제작처(양산)
	                var etcNcost       = r.etcNcost;                         //투자비(신규)
	                var etcNunit_1     = checkNull(r.etcNunit_1).trim();     //화폐단위(신규)
	                var etcNpay        = r.etcNpay;                          //지급액
	                var etcNunit_2     = checkNull(r.etcNunit_2).trim();     //화폐단위
	                var etcPcost       = r.etcPcost;                         //투자비(양산)
	                var etcPunit       = checkNull(r.etcPunit);              //화폐단위(양산)
	        		
	                var colName = "";
	                var colKey = "";
	                
	                if(etcNfactory == "" && etcNcost != 0){
	                	colKey = "etcNfactory";
	                	colName = "제작처(신규)";
	                }else if(etcPfactory == "" && etcPcost != 0){
	                	colKey = "etcPfactory";
                        colName = "제작처(양산)";
	                }else if(etcNcost != 0 && etcNunit_1 == ""){
                        colKey = "etcNunit_1";
                        colName = "투자비(신규) 화폐단위";
	                }else if(etcNpay != 0 && etcNunit_2 == ""){
                        colKey = "etcNunit_2";
                        colName = "지급액 화폐단위";
	                }else if(etcPcost != 0 && etcPunit == ""){
                        colKey = "etcPunit";
                        colName = "투자비(양산) 화폐단위";
	                }
	                
	                if(colKey != ""){
	                	alert(colName + "를 입력하십시오.");
                        setTimeout( function() { G.Focus(r, colKey, null, true); }, 10);
                        return true;
	                }
	        	}
	        	    
/* 	        	    for ( var j = G.GetFirst(); j; j = G.GetNext(j) ) {//삭제되지 않은 행에 대해 부품Type,생산처 정보 중복 체크
	                    
	                    if(j.deleteFlag != delFlag && r.r0.rowIndex != j.r0.rowIndex){
	                        rTemp = r.partNo;
	                        jTemp = j.partNo;
	                        
	                        if(rTemp == jTemp){
	                            alert(j.r0.rowIndex + "행과 " + r.r0.rowIndex + " 행은 중복입니다.");
	                            setTimeout( function() { G.Focus(j, "partNo", null, true); }, 10);
	                            return true;
	                        }
	                        
	                    }
	                } */

	        }
	        $('#etcNIC').val(G.Foot.lastChild.etcNcost);          //기타 투자비 신규 합계
            $('#etcNPay').val(G.Foot.lastChild.etcNpay);        //기타 지급액 신규 합계
            $('#etcMPIC').val(G.Foot.lastChild.etcPcost);        //기타 투자비 양산 합계
            $('#etcMPQTotal').val(G.Foot.lastChild.etcTotalQty);    //기타 양산수량 total 합계
            $('#etcMPQMax').val(G.Foot.lastChild.etcMaxQty);        //기타 양산수량 max 합계
	        
	        var arr = $('[name=costForm]').serializeArray();
            var paramObj = {};
            if (arr) {
                $.each(arr, function() {
                    paramObj[this.name] = this.value;
                });
            }
            
            
            G.Data.Upload.Param.formdata = JSON.stringify(paramObj);
	        
	     }catch(e){ alert(e.message); setTimeout( function() { G.Focus(r, "itemName", null, true); }, 10); return true;};
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
                 
                 if(opener){
                     var partData = ajaxCallServer("/plm/ext/common/getObjectData", {oid : "${partOid}"}, null, false);
               		 opener.setRowPopupProperty("ETCIC", partData);
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

   }

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
									<td class="font_01">기타 투자비</td>
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
	
	<form name="costForm">
	
	<input type="hidden" name="partOid" id="partOid" value="${partOid}">
	<input type="hidden" name="etcNIC" id="etcNIC" value="">
	<input type="hidden" name="etcNPay" id="etcNPay" value="">
	<input type="hidden" name="etcMPIC" id="etcMPIC" value="">
	<input type="hidden" name="etcMPQTotal" id="etcMPQTotal" value="">
	<input type="hidden" name="etcMPQMax" id="etcMPQMax" value="">
	</form>
		<tr>
			<td>&nbsp;</td>
			<td align="right">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
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
</c:if>