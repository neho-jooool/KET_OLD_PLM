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
    
    var mftFactoryList = eval('${mftFactoryList}');
    
    //window.console.log(mftFactoryList);
    
    var mft3_column = { Name : "mft3", Width : "100", Align : "center", Type : "Enum", Related : "mft2",  CanEmpty: 1, Range : 1, Spanned : '1',CanSort : '0' };
    
    var eNum = "";
    var eNumKeys = "";
    
    for(var i = 0; i < mftFactoryList.length; i++){
        
        var mftFactory = mftFactoryList[i];
        
        var pCode = mftFactory.pCode;
        
        if(pCode != null){
            
            var childEnum = mft3_column["Enum" + pCode];
            var childEnumKeys = mft3_column["EnumKeys" + pCode];
            
            if(childEnum != null){
                mft3_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
                mft3_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
            }else{
                mft3_column["Enum" + pCode] = "|" + mftFactory.name;
                mft3_column["EnumKeys" + pCode] = "|" + mftFactory.code;
            }

            
        }else{
            eNum += "|" + mftFactory.name;
            eNumKeys += "|" +  mftFactory.code;
        }
    }

	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/code/saveCostLogiticsCode",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
			Param : {
                //partListOid : "${partListOid}",
                DATATYPE : "M"
            }
		},
		Data : {
			Url : '/plm/ext/cost/code/costCodeGridData',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				//partListOid : "${partListOid}",
				DATATYPE : "COSTLogitics",
				SEARCHTYPE : "M"
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
					Visible : EditConfig,
					Move : 1,
					Copy : 1,
					CanHide : 0,
					CopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd",
				},
				Cols : [    
                        {Name : 'deleteFlag', CanEdit : 0, Visible : 0, Spanned : '1'},
                        {Name : 'tempCol1', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'tempCol2', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'mft1', Width:70, Align : 'center', Type : "Enum", Enum : "${eNumFty}", EnumKeys : "${eNumKeysFty}", CanEmpty : 1, Range : 0, Spanned : '1',CanSort : '0' },
                        {Name : 'mft2', Width:70, Align : 'center', Type : "Enum", Enum : eNum, EnumKeys : eNumKeys, CanEmpty : 1, Range : 0,  Clear : "mft3", Spanned : '1',CanSort : '0' },
                        {Name : 'tempCol3', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        mft3_column,
                        {Name : 'tempCol4', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'tempCol5', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'inLandCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Int", Format : "###,##0", Spanned : '1' },
                        {Name : 'inLandCurrency', Width:70, Align : 'center', Type : "Enum", Enum : "${eNumExRate}", EnumKeys : "${eNumKeysExRate}", CanEmpty : 1, Range : 0, Spanned : '1' ,CanSort : '0' },
                        {Name : 'tempCol6', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'inHarborCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Int", Format : "###,##0", Spanned : '1' },
                        {Name : 'inHarborCurrency', Width:70, Align : 'center', Type : "Enum", Enum : "${eNumExRate}", EnumKeys : "${eNumKeysExRate}", CanEmpty : 1, Range : 0, Spanned : '1' ,CanSort : '0' },
                        {Name : 'tempCol7', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'seaCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Int", Format : "###,##0", Spanned : '1' },
                        {Name : 'seaCurrency', Width:70, Align : 'center', Type : "Enum", Enum : "${eNumExRate}", EnumKeys : "${eNumKeysExRate}", CanEmpty : 1, Range : 0, Spanned : '1' ,CanSort : '0' },
                        {Name : 'tempCol8', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'outHarborCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Int", Format : "###,##0", Spanned : '1' },
                        {Name : 'harborCurrency', Width:70, Align : 'center', Type : "Enum", Enum : "${eNumExRate}", EnumKeys : "${eNumKeysExRate}", CanEmpty : 1, Range : 0, Spanned : '1' ,CanSort : '0' },
                        {Name : 'tempCol9', CanSort : '0', Visible : '0', CanHide : '0', CanExport : '0'},
                        {Name : 'outLandCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '1', Type : "Int", Format : "###,##0", Spanned : '1' },
                        {Name : 'landCurrency', Width:70, Align : 'center', Type : "Enum", Enum : "${eNumExRate}", EnumKeys : "${eNumKeysExRate}", CanEmpty : 1, Range : 0, Spanned : '1' ,CanSort : '0' },
                        {Name : 'cost', Width:90, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.00원", Spanned : '1', Formula : "(inLandCost*inLandCurrency)+(inHarborCost*inHarborCurrency)+(seaCost*seaCurrency)+(outHarborCost*harborCurrency)+(outLandCost*landCurrency)" },
                        {Name : 'p_container', Width:70, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Int", Format : "###,##0Pallet", Spanned : '1' },
                        {Name : 'rate', Width:70, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Float", Format : "0.0%", Spanned : '1'},
                        {Name : 'note', Width:300, Align : 'left', CanSort : '0', CanEdit : '1', Type : "Text", Spanned : '1'},
                        
                        
                        
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
                    tempCol1 : '구분',
                    tempCol1Span : '6',
                    tempCol4 : '원재료물류비',
                    tempCol4Span : '16',
                    cost : '원재료\n물류비',
                    costRowSpan : '3',
                    p_container : 'Pallet/\nContainer',
                    p_containerRowSpan : '3',
                    rate :  '원재료\n관세율',
                    rateRowSpan : '3',
                    note : '비고',
                    noteRowSpan : '3',
                }
                ,{
                    Kind : "Header",
                    id : "Header2",
                    //RowSpan : 0,
                    Spanned : '1',
                    CanDelete : '0', 
                    Align : 'Center',
                    mft1 : '원재료',
                    
                    mft2 : '부품',
                    mft2Span: '3',
                    tempCol5 : '출하 내륙 운송비',
                    tempCol5Span : '3',
                    tempCol6 : '출하 항구 부대비용',
                    tempCol6Span : '3',
                    tempCol7 : '해상 운송비',
                    tempCol7Span : '3',
                    tempCol8 : '수입 항구 부대비용',
                    tempCol8Span : '3',
                    tempCol9 : '수입 내륙 운송비',
                    tempCol9Span : '3',
                }
                ,{
                    Kind : "Header",
                    id : "Header3",
                    //RowSpan : 0,
                    CanDelete : '0', 
                    Align : 'Center',
                    mft1 : '입고처',
                    mft2 : '생산국',
                    mft3 : '생산지',
                    inLandCost : '금액',
                    inLandCurrency : '화폐단위',
                    inHarborCost : '금액',
                    inHarborCurrency : '화폐단위',
                    seaCost : '금액',
                    seaCurrency : '화폐단위',
                    outHarborCost : '금액',
                    harborCurrency : '화폐단위',
                    outLandCost : '금액',
                    landCurrency : '화폐단위',
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
                    if ( r.mft1 == null || (r.mft1 + "").trim() == "" ) {
                        alert("원재료의 입고처를 입력하십시오.");
                        setTimeout( function() { G.Focus(r, "mft1", null, true); }, 10);
                        return true;
                    }
                    
                    if ( r.mft2 == null || (r.mft2 + "").trim() == "" ) {
                        alert("부품의 생산국을 입력하십시오.");
                        setTimeout( function() { G.Focus(r, "mft2", null, true); }, 10);
                        return true;
                    }
                    
                    if ( r.mft3 == null || (r.mft3 + "").trim() == "" ) {
                        alert("부품의 생산지를 입력하십시오.");
                        setTimeout( function() { G.Focus(r, "mft3", null, true); }, 10);
                        return true;
                    }
                    
                    for ( var j = G.GetFirst(); j; j = G.GetNext(j) ) {//삭제되지 않은 행에 대해 1단계,2단계,3단계 체크
                        
                        if(j.deleteFlag != delFlag && r.r0.rowIndex != j.r0.rowIndex){
                            rTemp = r.mft1+"|"+r.mft2+"|"+r.mft3;
                            jTemp = j.mft1+"|"+j.mft2+"|"+j.mft3;
                            if(rTemp == jTemp){
                                alert(j.r0.rowIndex + "행과 " + r.r0.rowIndex + " 행은 중복입니다.");
                                setTimeout( function() { G.Focus(j, "mft1", null, true); }, 10);
                                return true;
                            }
                            
                        }
                    }
                }

            }
            
         }catch(e){ alert(e.message); setTimeout( function() { G.Focus(r, "mft1", null, true); }, 10); return true;};
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
									<td class="font_01">원재료 관세/물류비 관리</td>
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