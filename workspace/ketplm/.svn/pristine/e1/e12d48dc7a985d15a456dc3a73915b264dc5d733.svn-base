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
<link rel="stylesheet" type="text/css" href="/plm/extcore/bootstrap-3.3.2-dist/css/bootstrap.min.css" />

<script src='/plm/portal/js/treegrid/GridE.js'></script>
<script type="text/javascript" src="/plm/extcore/js/cost/costCommon.js"></script>
<!-- EJS TreeGrid End --> 
<title>Insert title here</title>

<script type="text/javascript" charset="utf-8">
var searchIcon          = "/plm/portal/images/icon_5.png";
var deleteIcon          = "/plm/portal/images/icon_delete.gif";
var mappingRow = null;
var delFlag = "1";

$(document).ready(function(){
	
	var EditConfig = 1;
    var CellConfig ="Save,ExpandAll,CollapseAll,Reload";
    
    var isCostAdmin = ${ket:isCostAdmin() };
    
    if(!isCostAdmin){
        EditConfig = 0;
        CellConfig = "";
    }

    
	var mftFactoryList = eval('${mftFactoryList}');
	
	//window.console.log(mftFactoryList);
	
	var mftFactory2_column = { Name : "mftFactory2", CanEdit : 0, Width : "60", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	var mftFactory3_column = { Name : "mftFactory3", CanEdit : 0, Width : "60", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	var mftFactory4_column = { Name : "mftFactory4", CanEdit : 0, Width : "60", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	var mftFactory5_column = { Name : "mftFactory5", CanEdit : 0, Width : "60", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	var mftFactory6_column = { Name : "mftFactory6", CanEdit : 0, Width : "60", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	
	var mftFactory7_column = { Name : "mftFactory7", CanEdit : 0, Width : "60", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	var mftFactory8_column = { Name : "mftFactory8", CanEdit : 0, Width : "60", Align : "left", Type : "Enum", Related : "mftFactory1",  CanEmpty: 1, Range : 1 };
	
	
	
	
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
				
				mftFactory3_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
				mftFactory3_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
				
				mftFactory4_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
				mftFactory4_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
				
				
				mftFactory5_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
				mftFactory5_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
				
				mftFactory6_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
				mftFactory6_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
				
				mftFactory7_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
				mftFactory7_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
				
				mftFactory8_column["Enum" + pCode] = childEnum + "|" + mftFactory.name;
				mftFactory8_column["EnumKeys" + pCode] = childEnumKeys + "|" + mftFactory.code;
				
				
			}else{
				mftFactory2_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory2_column["EnumKeys" + pCode] = "|" + mftFactory.code;
				
				mftFactory3_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory3_column["EnumKeys" + pCode] = "|" + mftFactory.code;
			
				mftFactory4_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory4_column["EnumKeys" + pCode] = "|" + mftFactory.code;
				
				mftFactory5_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory5_column["EnumKeys" + pCode] = "|" + mftFactory.code;
				
				mftFactory6_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory6_column["EnumKeys" + pCode] = "|" + mftFactory.code;
				
				mftFactory7_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory7_column["EnumKeys" + pCode] = "|" + mftFactory.code;
				
				mftFactory8_column["Enum" + pCode] = "|" + mftFactory.name;
				mftFactory8_column["EnumKeys" + pCode] = "|" + mftFactory.code;
				
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
			Url : '/plm/ext/cost/costPartTypeLinkList',
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
					//Undo : 1,
					Sorting : 0,
					//Editing : 1,
					//Deleting : 1,
					ShowDeleted : 0,
					Selecting : 0,
					CanFocus : 0,
					Pasting : 0,
					//CopySelected : 1,
					//CopyPasteTree : 1,
					//Dragging : 1,
					//Dropping : 1,
					//ExportFormat : 1,
					//ExportType : "Filtered,Hidden,Strings,Dates",
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
					Move : 0,
					Copy : 0,
					CanHide : 0,
					Width: 0
				},
				
				LeftCols : [{Name : 'deleteFlag', CanEdit : 0, Visible : 0},
	                        {Name : 'partTypeName', Width:200, Align : 'left', CanSort : '1', CanEdit : '0' ,Visible : 0},
	                        {Name : 'partTypeOid', CanEdit : 0, Visible : 0},
	                        {Name : 'treeFullFath', Width:200, Align : 'left', CanSort : '1', CanEdit : '0'},
	                        {Name : 'mftFactory1', Width:60, CanEdit : '0', Align : 'left', Type : "Enum", Enum : eNum, EnumKeys : eNumKeys, CanEmpty : 1, Range : 0,  Clear : "mftFactory2"},
	                        mftFactory2_column, mftFactory3_column, mftFactory4_column, mftFactory5_column, mftFactory6_column, mftFactory7_column, mftFactory8_column
	                       ],
				Cols : [
                        
				        /* {Name : 'productLossRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'efficientRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'addManHourRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'laborCostRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'laborCostClimbRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'laborCostYear', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Size : "4"},
				        {Name : 'unitManage', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int"},
				        {Name : 'indirectCostRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'moldMaintenance', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : ",0"},
				        {Name : 'tabaryu', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int"},
				        {Name : 'elecCost', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : ",0"},
				        {Name : 'elecCostClimbRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'elecCostYear', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Size : "4"},
				        {Name : 'machineDpcCost', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int", Format : ",0"},
				        {Name : 'assyLossRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'mtrManageRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'coManageRate', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Float", Format : "0.0%"},
				        {Name : 'rnd', Width:200, Align : 'left', CanSort : '1', CanEdit : '1', Type : "Int"}, */
			           ],
			           
				Head : [{
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
                        partTypeName : '부품Type명',
                        treeFullFath : '경로',
                        mftFactory1 : '생산처1',
                        mftFactory2 :  '생산처2',
                        mftFactory3 :  '생산처2',
                        mftFactory4 :  '생산처2',
                        mftFactory5 :  '생산처2',
                        mftFactory6 :  '생산처2',
                        mftFactory7 :  '생산처2',
                        mftFactory8 :  '생산처2',
                        /* 
                        productLossRate          : '생산Loss율',
                        efficientRate            : '생산효율',
                        addManHourRate           : '추가공수효율',
                        laborCostRate            : '표준임율',
                        laborCostClimbRate       : '표준임율상승율',
                        laborCostYear            : '표준임율년도',
                        unitManage               : '관리대수',
                        indirectCostRate         : '간접경비율',
                        moldMaintenance          : '금형유지비',
                        tabaryu                  : '타발유',
                        elecCost                 : '표준전력비',
                        elecCostClimbRate        : '표준전력비상승율',
                        elecCostYear             : '표준전력비년도',
                        machineDpcCost           : '기계감가(사출,프레스)',
                        assyLossRate             : '조립Loss율',
                        mtrManageRate            : '재료관리비율',
                        coManageRate             : '일반관리비율',
                        rnd                      : 'R&D' */
                    }
				],
				Toolbar : { Visible : 0 },
				Body : [ { Pos : 0 }],
				
			}
		}
	}, "treeGrid");
	
	Grids.OnSave = function(G, row, autoupdate) {
		
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
	   
   }
   
   var selectRow ;
   var selectCol ;
   
   Grids.OnClick = function(G, row, col, x, y, event){
	   var typeId = "";
	   
	   if(G.id ==  "costPartTypeLink"){
		   //alert("kk");
		   // alert(col.startsWith("mftFactory"));
		   
		   if(selectRow != null){
			   //alert(selectCol);
			   G.SetValue(selectRow, selectCol + "Color", "", 1);
		   }
		   
		   if(col == "mftFactory2" || col == "mftFactory3" || col == "mftFactory4" || col == "mftFactory5" ||col == "mftFactory6" ||col == "mftFactory7" ||col == "mftFactory8"){
			   typeId = G.GetValue (row, col);
			   
			   var partTypeOid = G.GetValue (row, "partTypeOid");
			   var mftFactory1 = G.GetValue (row, "mftFactory1");
			   
			   var typeId = partTypeOid + "*" + mftFactory1 + "*" + typeId;
			   
			   
			  
			   
			   G.SetValue(row, col + "Color", "#FFFF00", 1);
			   
			   selectRow = row;
			   selectCol = col;
			   //console.log("g",G);// (row, col, {Color:"red", TextColor:"blue"s});
			   
			   
			  
			   
		   }else{
			   alert("생산처2를 선택하세요");
		   }
		   
		   
		   costGrid.Source.Data.Param.typeId = typeId;
           costGrid.Data.Upload.Param.typeId = typeId;
        	//alert(costGrid.Data.Upload.Param.code);
           costGrid.Reload();
		   
		  // G.SetValue (row, col); 
		   
	   }
   }
 

   
   
   var columnList = new Array();
   columnList.push({LABEL : "columKey", KEY : "columKey", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '0' });
   columnList.push({LABEL : "컬럼명", KEY : "displayName", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '0' });
  //columnList.push({LABEL : "description", KEY : "description", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '0' });
   columnList.push({LABEL : "필수여부", KEY : "necessary", Type: "Bool", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '1' });
   columnList.push({LABEL : "disabled", KEY : "disabled", Type: "Bool", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '1' ,Visible : 0});

   
   var columnProp = generateGridColumn(columnList);
   
   console.log("columnProp " , columnProp);
   
   var costGridProp = {
   		Debug : 0,
   		Upload : {
   			Url : "/plm/ext/cost/saveBomAcLForType",
   			Format : "JSON",
   			Flags : "AllCols",
   			Data : "treeData",
   			Type : "Changes,Body",
   			Param : {
   				//partListOid : "${partListOid}",
   				typeId : ""
   			},
   		},
   		Data : {
   			Url : '/plm/ext/cost/getDataFromType', 
   			Method : 'POST',
   			Format : 'JSON',
   			Param : {
   				
   				typeId : ""
   			}
   		},
   		Layout : {
   			Data : {
   				Cfg : {
   					id : "listWorkItemGrid",
   					Style : gridStyle,
   					SuppressCfg : 1,
   					IdPrefix : "T",
   					IdChars : "0123456789",
   					NumberId : 1,
   					Undo : 1,
   					Sorting : 0,
   					Editing : EditConfig,
   					Deleting : 0,
   					ShowDeleted : 0,
   					Selecting : 0,
   					CopySelected : 0,
   					CopyPasteTree : 0,
   					Dragging : 0,
   					Dropping : 0,
   					ExportFormat : 1,
   					ExportType : "Filtered,Hidden,Strings,Dates",
   					SaveSession : 1,
   					UsePrefix : 1,
   					Alternate : 0,
   					MainCol : "partTypeName",
   					SuppressMessage : 1,
   					FastColumns : 1
   				},
   				Toolbar : { Visible : 0 },
   				LeftCols : columnProp.leftCols,
   				Cols : columnProp.cols,
   				Head : [{
   					Kind : "Topbar",
   					Cells : CellConfig,
   					Styles : 2,
   					Calculated : 1,
   					Link : 0,
   				},{
   					Kind : "Header",
   					id : "Header",
   					Visible : 0
   				},
   				columnProp.kHeader1Lv,
   				],
   				Body : [ { Pos : 0 } ],
   			}
   		}
   	}
   	costGrid = TreeGrid(costGridProp, 'treeGrid2');
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
									<td class="font_01">부품 Type별 ACL 관리(DB기준)</td>
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

<%-- 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
                                        href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%>닫기</a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
					</tr>
				</table>
			</td>
		</tr>
	</table> --%>
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
					   <div class="row">
  							<div class="col-md-11">
							    <div class="row">
									<div class="col-xs-6">
										<div id="treeGrid" style="HEIGHT: 600px"></div>
									</div>
									<div class="col-xs-6"> 
										<div id="treeGrid2" style="HEIGHT:600px;width:350"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div> <!-- EJS TreeGrid Start -->
			</td>
		</tr>
	</table>

</body>
</html>