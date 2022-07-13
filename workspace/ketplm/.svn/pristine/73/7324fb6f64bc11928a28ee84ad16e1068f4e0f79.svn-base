<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/cost/costCommon.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>

<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript">

/* {Name : 'columnKey', Align : 'Center', CanSort : '0', CanEdit : '0'},
{Name : 'displayName', Align : 'Center', CanSort : '0', CanEdit : '0'},
{Name : 'description', RelWidth : 100, Align : 'Left', CanSort : '0', CanEdit : '0'},
{Name : 'view', Align : 'Center', CanSort : '0', CanEdit : '1'},
{Name : 'editor', Align : 'Center', CanSort : '0', CanEdit : '1'},
 */

    $(document).ready(function(e) {
    	
    	var EditConfig = 1;
        var CellConfig = "Save,ExpandAll,CollapseAll,Reload";
        
        var isCostAdmin = ${ket:isCostAdmin() };
        
        if(!isCostAdmin){
            EditConfig = 0;
            CellConfig = "";
        }
    	
      // WorkItem.createPaingGrid();

        $(function(){
            $("#filterClass").multiselect({
                minWidth : 200,
                multiple: false,
                header: false,
                noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %>',
                checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %>',
                uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %>' 
            });
            
           
        }); 
        
      //  RADIOEnum="|A|B|C|D|E|F|G"
       
        var columnList = new Array();
        columnList.push({LABEL : "columKey", KEY : "columKey", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '0' });
        columnList.push({LABEL : "컬럼명", KEY : "displayName", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '0' });
        columnList.push({LABEL : "description", KEY : "description", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '0' });
        columnList.push({LABEL : "view", KEY : "view", Type: "Bool", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '1' });
        columnList.push({LABEL : "editor", KEY : "editor", Type: "Bool", LEVEL : 1, Align : 'Center', CanSort : '0', CanEdit : '1' });
    
        
        var columnProp = generateGridColumn(columnList);
        
        console.log("columnProp " , columnProp);
        
        var costGridProp = {
        		Debug : 0,
        		Upload : {
        			Url : "/plm/ext/cost/saveBomAcL",
        			Format : "JSON",
        			Flags : "AllCols",
        			Data : "treeData",
        			Type : "Changes,Body",
        			Param : {
        				//partListOid : "${partListOid}",
        				code : $("#filterClass").val() 
        			},
        		},
        		Data : {
        			Url : '/plm/ext/cost/getData',
        			Method : 'POST',
        			Format : 'JSON',
        			Param : {
        				
        				code : $("#filterClass").val()
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
        					Pasting : 0,
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
        	costGrid = TreeGrid(costGridProp, 'treeGrid');
        	
        $("#filterClass").change(function(){
        	//alert( $("#filterClass").val());
        	console.log(" costGrid.Data.Upload.Param", costGrid.Data.Upload.Param);
        	costGrid.Source.Data.Param.code = $("#filterClass").val();
        	costGrid.Data.Upload.Param.code = $("#filterClass").val();
        	//alert(costGrid.Data.Upload.Param.code);
        	costGrid.Reload();
        });
        Grids.OnAfterValueChanged  = function(G, row, col, val){
        	if(col == "view"){
        		if(val == 0){
        			
        			//G.SetBool(row, "editor", null, 0); 
        			//G.SetChecked(row, "editor", 0); 
        			//G.ResetChecked (row, "editor") ;
        			G.SetValue (row, "editor", 0, 1); 
        			//TRow row, string col, type value = null, bool test = 0) 
        			//G.SetString(row,'customerEventName','',1);
        			
        		}
        	}else if(col == "editor"){
        		if(val == 1){
        			G.SetValue (row, "view", 1, 1); 
        			
        			
        			//alert("kkkk");
        		}
        	}
        	
        }
        
        Grids.OnAfterSave = function (grid, result, autoupdate){
    		try{
    			if( result >=0 ){
    				alert('저장되었습니다');
    			}else{
    				alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
    			}
    		} catch (e) {
    			alert(e.message);
    		};
       }
        
        $(window).resize(function(){
        	$("#treeGrid").height($(window).height() - 150);
        });
        $("#treeGrid").height($(window).height() - 150);
    });
    
</script>
<script>
$(document).ready(function(){
	$("body").removeClass("body-space");
	$("body").addClass("popup-background");
	$("body").addClass("popup-space");
});
</script>
<div class="contents-wrap">
	<div class="sub-title b-space5">
		<img src="/plm/portal/images/icon_3.png" />Task유형별 ACL 관리(그리드 기준)
	</div>
	<div class="b-space5 float-r" style="text-align: right">
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></span>
				<span class="pro-cell b-right"></span>
			</span>
		</span>
	</div>
	<div style="width:100%;overflow:auto;">
		<table summary="" class="table-style-01">
			<colgroup>
				<col width="40%" />
				<col width="60%" />
			</colgroup>
			<tbody>
				<tr>
					<td class="center bgcol fontb" >
					</td>
					<td>
						<select name="filterClass" id="filterClass" class="fm_jmp" multiple="multiple">
				            <c:forEach items="${typeList}" var="entry">
				              <option value="<c:out value="${entry.key}"/>"><c:out value="${entry.value}" /></option>
				            </c:forEach>
				        </select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div id='treeGrid' style='width:100%;height:100%;'></div>
