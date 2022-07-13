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
		
	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/code/saveCostMaterial",
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
				DATATYPE : "COSTMaterial",
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
					id : "costMaterialPopup",
					Style : gridStyle,
					SuppressCfg : cookiesType,
					IdPrefix : "T",
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
				        {Name : 'partNo', Width:100, Align : 'left', CanSort : '1', CanEdit : '1', Spanned : '1'},
				        {Name : 'partName', Width:230, Align : 'left', CanSort : '1', CanEdit : '0', Type : "Text", Spanned : '1'},
				        {Name : 'grade', Width:100, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Text", Spanned : '1'},
				        {Name : 'color', Width:100, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Text", Spanned : '1'},
				        {Name : 'materialPrice', Width:100, Align : 'center', CanSort : '1', CanEdit : '0', Type : "Int", Format : "###,##0", Spanned : '1'},
				        {Name : 'rate', Width:70, Align : 'center', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
				        {Name : 'sapExist', CanEdit : 0, Visible : 0},
			           ],
				Head : [{
						Kind : "Topbar",
						Cells : "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print,Columns",
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
                        partNo : '자재번호',
                        partName : '원재료명',
                        grade : 'Grade',
                        color : 'Color',
                        materialPrice : '원재료단가',
                        rate  : '비율',
					}
				],
				Foot : [{
                    id : "foot1",
                    Spanned : '1',
                    CanDelete : '0', 
                    CanEdit : '0',
                    partNoAlign : 'Center',
                    partNo : '비율 합계',
                    partNoSpan : '5',
                    rateAlign : 'Center',
                    rateFormula : "sum()",
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
	        var rowNum = 0;
	        for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
	        	
	        	if ( r.deleteFlag != delFlag ) {//삭제되지 않은 행에 대해 필수입력 값 체크
	        		if ( r.partNo == null || (r.partNo + "").trim() == "" ) {
	                    alert("자재번호를 입력하십시오.");
	                    setTimeout( function() { G.Focus(r, "partNo", null, true); }, 10);
	                    return true;
	                }else if ( r.partName == null || (r.partName + "").trim() == "" ) {
                        alert("원재료명을 입력하십시오.");
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
	        	    rowNum++;
	        	}
	        	

	        }
	        
	        if(rowNum > 0 && G.Foot.lastChild.rate != 1){
	        	alert("비율 합계는 100%이어야 합니다.");
	        	return true;
	        }
	        
	        var arr = $('[name=costMaterialForm]').serializeArray();
            var paramObj = {};
            if (arr) {
                $.each(arr, function() {
                    paramObj[this.name] = this.value;
                });
            }
            G.Data.Upload.Param.formdata = JSON.stringify(paramObj);
	        
	     }catch(e){ alert(e.message); setTimeout( function() { G.Focus(r, "partTypeName", null, true); }, 10); return true;};
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
                changeAttr();
				if(opener){
					var row = G.GetFirst();
					opener.setRowPopupProperty("RAWMATERIAL", row);
				}
                 
             }else{
                 
                 alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
                   //window.location.reload();
               }

           } catch (e) {
               alert(e.message);
           };
   }
    
    Grids.OnSuggest = function(grid, row, col, val, suggest, formula){
    	
    	if(col == "partNo"){
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
	                    wtPartNoLike : "R2"
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
    
    Grids.OnValueChanged = function(grid, row, col, val, oldVal, errors){
    	
    	window.console.log(val);

    	if(col == "partNo"){
    		grid.SetValue(row, "partNameCanEdit", 1, 1);
    		grid.SetValue(row, "materialPriceCanEdit", 1, 1);
    		
    		val = checkNull(val);
        	if(val != ""){
        		ajaxCallServer("/plm/ext/part/base/getLastestPart", {partNo : val.toUpperCase() },function(data){
            		window.console.log(data);
            		
            		grid.SetValue(row, "partName", checkNull(data.spMaterTypeName), 1);
            		grid.SetValue(row, "grade", data.spMaterialInfoName, 1);
            		grid.SetValue(row, "color", data.spColorName, 1);
            		grid.SetValue(row, "materialPrice", "0", 1);
            		grid.SetValue(row, "erpCheck", "1", 0);
        			grid.SetValue(row, "partNameCanEdit", 0, 1);
        			grid.SetValue(row, "materialPriceCanEdit", 0, 1);
            	},false);
        	}
        	
    	}
    	
    	return val;
    }
    
   Grids.OnRowAdded = function(grid, row){
	   
	   grid.SetValue(row, "partNoIcon", searchIcon, 1);
       grid.SetValue(row, "partNoIconAlign", "Right", 1);
       grid.SetValue(row, "partNoOnClickSideIcon", "javascript:openGridPopup(Row);", 1);
       grid.SetValue(row, "partNoButton", deleteIcon, 1);
       grid.SetValue(row, "partNoOnClickSideButton", "javasciprt:deletePopupValue(Row);", 1);
	   grid.SetValue(row, "partNameCanEdit", 1, 1);
	   grid.SetValue(row, "materialPriceCanEdit", 1, 1);
	   grid.SetValue(row, "partNoSuggestType", "Start", 1);
	   grid.SetValue(row, "partNoSuggestMin", 4, 1);
       
       grid.SetValue(row, "rate", 1, 1);
       grid.SetValue(row, "materialPrice", 0, 1);
       grid.Focus(row, "partNo", null, true);
   }
   
   window.openGridPopup = function(row){
	   mappingRow = row;
	   //showProcessing();
	   SearchUtil.selectOnePart('selectPartAfterFunc','pType=R');
	   //SearchUtil.selectCostPartType(loadCostPartType,'onlyLeaf=Y&openAll=N');
   }
   
   window.selectPartAfterFunc = function(objArr ){
	   //hideProcessing();
	   var trArr;
       if(objArr.length == 0) {
    	   return;
       }
       
       for(var i = 0; i < objArr.length; i++){
    	   window.console.log(objArr[i]);
    	   trArr = objArr[i];
       }
       Grids[0].SetValue(mappingRow, "partNo", trArr[1], 1);
       var partName = "";
       
       
       if(trArr[17] != null){
    	   partName = trArr[17];
       }
       
       /* if(trArr[16] != null && trArr[18] != null){
    	   partName = trArr[16]+'('+trArr[18]+')';
       }else{
    	   if(trArr[16]  != null){
    		   partName = trArr[16];
    	   }
    	   if(trArr[18]  != null){
    		   partName = trArr[18];
           }
       } */
       
       Grids[0].SetValue(mappingRow, "partName", partName, 1);
       Grids[0].SetValue(mappingRow, "grade", trArr[19], 1);
       Grids[0].SetValue(mappingRow, "color", trArr[18], 1);
       Grids[0].SetValue(mappingRow, "materialPrice", "0", 1);
       Grids[0].SetValue(mappingRow, "erpCheck", "1", 0);
   }
   
   window.deletePopupValue = function(row){
	   Grids[0].SetValue(row, "partNo", "", 1);
       Grids[0].SetValue(row, "partName", "", 1);
       Grids[0].SetValue(row, "grade", "", 1);
       Grids[0].SetValue(row, "materialPrice", "0", 1);
       Grids[0].SetValue(mappingRow, "erpCheck", "1", 0);
   }
   

});

Grids.OnRenderFinish = function(grid, row){
	changeAttr();
}

window.changeAttr = function(){
	var tempRows = Grids[0].Rows;
    var rowKeys = Object.keys(tempRows);
    
    for(var i = 0; i < rowKeys.length; i++){
        var id = rowKeys[i];
        var tempRow = tempRows[id];
        if("Data" == tempRow.Kind){
            
            if(tempRow.Deleted == 1){
                continue;
            }
            
            if(tempRow.id != 'foot1'){
                //alert(tempRow.materialPrice); 
                
                /* if(!eval(tempRow.erpCheck)){//erp에 자재가 존재하지 않을때 수기입력가능하도록
                	Grids[0].SetAttribute(tempRow, "materialPrice", "CanEdit", "1", 1); 
                } */
                //모든 수지원재료 단가 변경가능하도록 수정 요청 2018.12.28 이경무 과장 요청
            	Grids[0].SetAttribute(tempRow, "materialPrice", "CanEdit", "1", 1);
            }
        }
    }
}

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
									<td class="font_01">수지 원재료</td>
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
	
	<form name="costMaterialForm">
	<input type="hidden" name="partOid" id="partOid" value="${partOid}">
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