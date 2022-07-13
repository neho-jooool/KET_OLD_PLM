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

$(document).ready(function(){
	var EditConfig = 1;
    var CellConfig = "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,Reload,Export,Print,Columns";
    
	var isCostAdmin = ${ket:isCostAdmin() };
	
	if(!isCostAdmin){
		EditConfig = 0;
		CellConfig = "ExpandAll,CollapseAll,Reload,Export,Print,Columns";
	}
/* 	var mftFactoryList = eval('${mftFactoryList}');
	
	window.console.log(mftFactoryList);
	
	var mftFactory2_column = { Name : "mftFactory2", Width : "200", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	
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
			
		}else{
			eNum += "|" + mftFactory.name;
			eNumKeys += "|" +  mftFactory.code;
		}
	} */
	
	var codeEnumKey = '||MOLD|PRESS|조립W/H|소모품|양산품|지정품|구매';
	
	var grid = TreeGrid({
		Debug : 0,
		Upload : {
			Url : "/plm/ext/cost/saveCostPartType",
			Format : "JSON",
			Flags : "AllCols",
			Data : "treeData",
			Type : "Changes,Body",
			Param : {
				partListOid : "${partListOid}"
			},
		},
		Data : {
			Url : '/plm/ext/cost/costTreeGridData',
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
					id : "costPartTypeEditor",
					Style : gridStyle,
					SuppressCfg : cookiesType,
					IdPrefix : "T",
					IdChars : "0123456789",
					NumberId : 1,
					Undo : 1,
					Sorting : 0,
					Editing : EditConfig,
					Deleting : 0,
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
					MainCol : "name",
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
                        {Name : 'deleteFlag', CanEdit : 0, Visible : 0},
                        {Name : 'name', Width:150, Align : 'left', CanSort : '1', CanEdit : '1'},
				        {Name : 'code', Width:80, Align : 'left', Type : "Enum", Enum : codeEnumKey, Range : 0 },
				        {Name : 'lvlOption', Width:100, Align : 'left', Type : "Enum", Enum : "||==|>|<|>=|<=", Range : 0 },
                        {Name : 'lvl', Width:50, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int"},
                        {Name : 'parentCfg', Width:125, Align : 'left',  Type : "Enum",  Enum : codeEnumKey, Range : 0 },
                        {Name : 'childCfg', Width:80, Align : 'left', Type : "Enum", Enum : "||필수|불가", Range : 0 },
                        {Name : 'capaCfg', Width:110, Align : 'left', Type : "Enum", Enum : "||설정", EnumKeys : "|false|true", Range : 0},
                        {Name : 'taskCode', Width:125, Align : 'left', Type : "Enum", Enum : "${eNumTaskType}", EnumKeys : "${eNumKeysTaskType}", Range : 1},
                        {Name : 'costRatioCode', Width:117, Align : 'left', Type : "Enum", Enum : "||Mold|Press|조립|구매품|W/H품|지정품|양산품", Range : 0 },
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
                        
                        name : '부품Type명',
                        code : '부품Type코드',
                        lvlOption : '레벨설정',
                        lvl : '레벨',
                        parentCfg : '모부품설정(모부품코드)',
                        childCfg : '자부품생성여부',
                        capaCfg : 'capa 설정(감가비)',
                        taskCode : 'Task유형',
                        costRatioCode : '총원가 비율 분석 코드',
/*                         mftFactory1 : '생산처1',
                        mftFactory2 :  '생산처2',
                        icMoldPDiv : '제작구분' */
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

    $('body').addClass('popup-background02 popup-space');

});

</script>
</head>
<body>
	<div class="contents-wrap">
		<div class="popup-title">
			<img src="/plm/portal/images/icon_3.png" />부품Type관리
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