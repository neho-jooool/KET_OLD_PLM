<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge' />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<title>Insert title here</title>

<script>
var delFlag = "1";
var searchIcon          = "/plm/portal/images/icon_5.png";
var deleteIcon          = "/plm/portal/images/icon_delete.gif";
var mappingRow = null;

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
    
    var mftFactory2_column = { Name : "mftFactory2", Width : "70", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1,Spanned : 1 };
    
    var eNum = "";
    var eNumKeys = "";
    
    for(var i = 0; i < mftFactoryList.length; i++){
        
        var mftFactory = mftFactoryList[i];
        
        var pCode = mftFactory.pCode;
        var pName = mftFactory.pName;
        
        if(pCode != null){
            
            var childEnum = mftFactory2_column["Enum" + pCode];
            var childEnumKeys = mftFactory2_column["EnumKeys" + pCode];
            
            if(childEnum != null){
                mftFactory2_column["Enum" + pCode] = childEnum + "|" + pName + " - " + mftFactory.name
                mftFactory2_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
            }else{
                mftFactory2_column["Enum" + pCode] = "|" + pName + " - " + mftFactory.name
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
			Url : "/plm/ext/cost/code/saveCostWorkTime",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
			Param : {
				//partListOid : "${partListOid}"
			},
		},
		Data : {
			Url : '/plm/ext/cost/code/costWorkTimeList',
			Method : 'POST',
			Format : 'JSON',
			Param : {
				//partListOid : "${partListOid}",
				//DATATYPE : "COSTPARTTYPE"
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
					id : "costWorkTimePopup",
					Style : gridStyle,
					SuppressCfg : cookiesType,
					IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : EditConfig ,
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
				
				Cols : [
                        {Name : 'deleteFlag', CanEdit : 0, Visible : 0, Spanned : '1', Spanned : '1'},
                        {Name : 'partTypeName', Width:120, Align : 'left', CanSort : '1', CanEdit : '0', Spanned : '1'},
                        {Name : 'partTypeOid', CanEdit : 0, Visible : 0},
                        {Name : 'treeFullFath', Width:130, Align : 'left', CanSort : '1', CanEdit : '1', Spanned : '1'},
                        {Name : 'mftFactory1', Width:70, Align : 'left', Type : "Enum", Enum : eNum, EnumKeys : eNumKeys, CanEmpty : 1, Range : 1,  Clear : "mftFactory2", Spanned : '1'},
                        mftFactory2_column,
                        {Name : 'workHour', Width:50, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Spanned : '1'},
                        {Name : 'workDay', Width:50, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Spanned : '1'},
                        {Name : 'workYear', Width:50, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Spanned : '1'},
                        {Name : 'capaYear', Width:50, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Spanned : '1'},
/* 				        {Name : 'mftFactory1', Width:200, Align : 'left', CanEdit : '0', Type : 'Text'},
				        {Name : 'mftFactory2', Width:200, Align : 'left', CanEdit : '1', Type : 'Lines', AcceptEnters : '1'},
				        {Name : 'icMoldPDiv', Width:200, Align : 'left', Type : "Enum", Enum : "|금형|설비", Range : 1 } */
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
                        RowSpan : 0,
                        CanDelete : '0', 
                        Align : 'Center',
                        Spanned : '1',
                        partTypeName : '부품Type명',
                        partTypeNameRowSpan : '2',
                        treeFullFath : '경로',
                        treeFullFathRowSpan : '2',
                        mftFactory1 : '조업도',
                        mftFactory1Span : '6',
/*                         mftFactory1 : '생산처1',
                        mftFactory2 :  '생산처2',
                        icMoldPDiv : '제작구분' */
                    },{
                        Kind : "Header",
                        id : "Header2",
                        Spanned : '1',
                        RowSpan : 0,
                        CanDelete : '0', 
                        Align : 'Center',
                        mftFactory1 : '생산처1',
                        mftFactory2 :  '생산처2',
                        workHour    :  'Hr/日',
                        workDay     :  '日/月',
                        workYear    :  '月/年',
                        capaYear    :  '적용년수',
/*                         mftFactory1 : '생산처1',
                        mftFactory2 :  '생산처2',
                        icMoldPDiv : '제작구분' */
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
                        
                        for(var j = 0; j < rowKeys.length; j++){
                            
                            var bId = rowKeys[j];
                            var bRow = rows[bId];
                            
                            if("Data" == bRow.Kind && bRow.deleteFlag != delFlag && aId != bId){//삭제되지 않은 행에 대해 부품Type,생산처 정보 중복 체크
                                
                                aTemp = aRow.partTypeOid + aRow.mftFactory1 + aRow.mftFactory2;
                                
                                bTemp = bRow.partTypeOid + bRow.mftFactory1 + bRow.mftFactory2;
                                
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
 		//alert('aa');
		//return true;
	}
	
	
   
    Grids.OnReload = function(G) {
    	//alert();
    }
    
    Grids.OnAfterSave = function ( G, result, autoupdate){
        
        try{
            
             if( result >=0 ){
                 
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
    
    Grids.OnRowDelete = function(G, row, type) {

        try{
            G.SetValue(row, "deleteFlag", delFlag, 1);
        }catch(e){ alert(e.message);};
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
    }
    
    window.deletePopupValue = function(row){
        Grids[0].SetValue(row, "partTypeName", "", 1);
        Grids[0].SetValue(row, "partTypeOid", "", 1);
        Grids[0].SetValue(row, "treeFullFath", "", 1);
    }

    $('body').addClass('popup-background02 popup-space');

});

</script>
</head>
<body>
	<div class="contents-wrap">
		<div class="popup-title">
			<img src="/plm/portal/images/icon_3.png" />조업도 관리
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td class="space10"></td>
			</tr>
			<tr>
				<td class="space2"></td>
			</tr>
		</table>
		<div id='treeGrid' style='width: 100%;'></div>
	</div>
</body>
</html>