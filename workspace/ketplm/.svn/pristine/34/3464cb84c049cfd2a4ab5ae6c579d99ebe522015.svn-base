<%@ page language='java' contentType='text/html; charset=UTF-8'
	pageEncoding='UTF-8'%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<!DOCTYPE >
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
    
    var isCostAdmin = ${ket:isCostAdmin() };
    
    if(!isCostAdmin){
        EditConfig = 0;
        CellConfig = "ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    }
    
    
	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/code/saveCostReduceCode",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
			Param : {
                //partListOid : "${partListOid}",
                //DATATYPE : "COSTREDUCE"
            }
		},
		Data : {
			Url : '/plm/ext/cost/code/costCodeGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				//partListOid : "${partListOid}",
				DATATYPE : "COSTREDUCE"
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
					id : "costReducePopup",
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
					CopySelected : 0,
					CopyPasteTree : 0,
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
				
				LeftCols : [{Name : 'deleteFlag', CanEdit : 0, Visible : 0, Spanned : '1'},
				            {Name : 'costMaking', Width:100, Align : 'left', Type : "Enum", Enum : "${eNumMaking}", EnumKeys : "${eNumKeysMaking}", CanEmpty : 1, Range : 0, Spanned : '1'},
				            {Name : 'factory', Width:100, Align : 'left', Type : "Enum", Enum : "${eNumFty}", EnumKeys : "${eNumKeysFty}", CanEmpty : 1, Range : 0, Spanned : '1'},
	                       ],
				Cols : [    {Name : 'moldTariffPrice', Width:130, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
                            {Name : 'moldMtnExpence', Width:130, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
                            {Name : 'facTariffPrice', Width:130, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
                            {Name : 'facMtnExpence', Width:130, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
                            {Name : 'etcTariffPrice', Width:130, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
                            {Name : 'etcMtnExpence', Width:130, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
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
					},{
                        Kind : "Header",
                        id : "Header1",
                        Align : 'Center',
                        Spanned : '1',
                        costMaking : '제작처',
                        costMakingRowSpan:'2',
                        factory : '생산국',
                        factoryRowSpan:'2',
                        moldTariffPrice : '금형',
                        moldTariffPriceSpan : '2',
                        facTariffPrice : '설비',
                        facTariffPriceSpan : '2',
                        etcTariffPrice  : '기타',
                        etcTariffPriceSpan  : '2',
                    }
					,{
                        Kind : "Header",
                        id : "Header2",
                        //RowSpan : 0,
                        Spanned : '1',
                        CanDelete : '0', 
                        Align : 'Center',
                        moldTariffPrice : '관세&물류비율',
                        moldMtnExpence : '관리비율',
                        facTariffPrice : '관세&물류비율',
                        facMtnExpence :  '관리비율',
                        etcTariffPrice  : '관세&물류비율',
                        etcMtnExpence  : '관리비율',
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
	        	
	        	if ( r.deleteFlag != delFlag ) {//삭제되지 않은 행에 대해 필수입력 값 체크
	        		if ( r.costMaking == null || (r.costMaking + "").trim() == "" ) {
	                    alert("제작처를 입력하십시오.");
	                    setTimeout( function() { G.Focus(r, "costMaking", null, true); }, 10);
	                    return true;
	                }
	        		
	        	    if ( r.factory == null || (r.factory + "").trim() == "" ) {
                        
	        	    	alert("생산국을 입력하십시오");
                        setTimeout( function() { G.Focus(r, "factory", null, true); }, 10);
                        return true;
                    }
	        	    
	        	    for ( var j = G.GetFirst(); j; j = G.GetNext(j) ) {//삭제되지 않은 행에 대해 제작처, 생산국 중복 체크
	                    
	                    if(j.deleteFlag != delFlag && r.r0.rowIndex != j.r0.rowIndex){
	                        rTemp = r.costMaking + r.factory;
	                        jTemp = j.costMaking + j.factory;
	                        
	                        if(rTemp == jTemp){
	                            alert(j.r0.rowIndex + "행과 " + r.r0.rowIndex + " 행은 중복입니다.");
	                            setTimeout( function() { G.Focus(j, "costMaking", null, true); }, 10);
	                            return true;
	                        }
	                        
	                    }
	                }
	        	}

	        }
	        
	     }catch(e){ alert(e.message); setTimeout( function() { G.Focus(r, "costMaking", null, true); }, 10); return true;};
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
									<td class="font_01">감가비 기준정보 관리</td>
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