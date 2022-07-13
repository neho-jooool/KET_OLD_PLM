<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
html{width:100%;height:100%;}
</style>
<script type="text/javascript">
var treeGrid_ = {
        createPaingGrid : function(){
            
            this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
                id : 'searchGrid',
                Sort : '-pjtNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
                perPageOnChange : 'javascript:treeGrid_search.search(Value);',
                Sync : 1,
                usePaging : false,
                //useToolbar : false,//grid toolbar hide
                Data : {
                    Url : "/plm/ext/project/product/findWareHousingList",
                    Method : 'POST',
                    Format : 'Form',
                    Param : {
                        formPage : (Grids['searchGrid'])?Grids['searchGrid'].PageLength:CommonGrid.pageSize
                    },
                    Params : decodeURIComponent($('[name=searchForm]').serialize())
                },
                Cols : [
                        {Name : 'rnum', Realwidth:30,   Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
                        {Name : 'pjtNo',     Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1', Type : 'Html'},
                        {Name : 'partNo',       Width:150,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1', Type : 'Html'},
                        {Name : 'partName',       Width:300,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                        {Name : 'whPlantName',    Width:150,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                        {Name : 'type',    Width:90,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                        ],
                        Head :[
                                {
                                    CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
                                    id : "Header",Spanned : '1',
                                    rnum : 'No', rnumBackground : '#E2EDF4',
                                    pjtNo : "Project No", pjtNoBackground : '#E2EDF4',
                                    partNo : "Part No", partNoBackground : '#E2EDF4',
                                    partName : "Part Name", partNameBackground : '#E2EDF4',
                                    whPlantName : "입고처", whPlantNameBackground : '#9FF781',
                                    type: "조달유형", typeBackground : '#9FF781'
                                }
                                ]
                }),'listGrid');
            
            
          Grids.OnDownloadPage = function(grid,row){
              
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/project/product/findWareHousingList';
                
                var param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.Source.Layout.Data.Cfg.PageLength
                    }

                $('input,select').each(function(){
                    var name = $(this).attr('name');
                    var value = $(this).val();
                    param[name] = value;
                });
                grid.Data.Page.Param = param;
            }

            
            //row click event
            
            //Grids.OnClick = purchase.goView;
            
            
            Grids.OnRenderPageFinish = function(){
                
                var pageLength = Grids[0].RowCount>0?Grids[0].Body.childNodes.length:0;
                var gridRowCount = 4;
                //alert(Grids[0].RootCount);
                gridRowCount = Grids[0].RootCount;
                //$('#testid').html("전체페이지 : <b>"+pageLength+"</b>  /  전체개수: <b>"+ gridRowCount +"</b>");
                var rowCount = 0;
                var G = Grids[0];
                G.Toolbar.Formula = '0';
                var rows = G.Rows;
                var rowIds = Object.keys(rows);
                var colNames = G.ColNames[1];
                
                //colNames.push('outSourcing');
                //colNames.push('outSourcingGubun');
                
                for(var i = 0; i < rowIds.length; i++){
                    var row = rows[rowIds[i]];
                    if(row.Kind == 'Header' || row.Kind == 'HeaderTop'){
                        
                    	treeGrid_.gridHeaderFontSetting(colNames,G,row);
                        rowCount++;
                    }else{
                        if(rowCount > 1){
                        	treeGrid_.gridHeaderFontSetting(colNames,G,row);
                            break;
                        }
                        
                    }
                    
                }
            }
            
            
        },
        
        gridHeaderFontSetting : function(colNames,G,row){
            for(var j = 0; j < colNames.length; j++){
            	treeGrid_.gridHeaderFontSizeChange(G, row, colNames[j], 2);
            }
            
        },
        
        gridHeaderFontSizeChange : function(G, tRow, attr, size){
            //G.SetAttribute(tRow, attr, "Background", "#E2EDF4", 1);
            G.SetAttribute(tRow, attr, "HtmlPrefix", "<b><font size="+size+ ">", 1);
            G.SetAttribute(tRow, attr, "Postfix", "</font></b>", 1);
            G.SetAttribute(tRow, attr, "Changed", 0, 1);
        },
        
        
        trimPjtNo : function(){
            var pjtNo = $('#pjtNo').val();
            
            console.log("params before === "+"["+pjtNo+"]");
            
            $("#pjtNo").val((pjtNo.replace(/ /g,"")).toUpperCase());
            
            console.log("params after === "+"["+$("#pjtNo").val()+"]");
        },

        search : function(perPage){
            if(!perPage) perPage = Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength;
            Grids['searchGrid'].Source.Data.Url = '/plm/ext/project/product/findWareHousingList';
            Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
            Grids['searchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
            Grids['searchGrid'].Source.Data.Param.formPage=perPage;
            Grids['searchGrid'].ReloadBody();
            
            //Grids[0].Reload();
        },
        
        doViewDoc : function(oid){
            
            url = "/plm/jsp/dms/ViewDocument.jsp?oid="+oid;
            purchase.openDocLink(url);
        }
}

$(document).ready(function(e) {
	SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
     
    treeGrid_.createPaingGrid();
	treeGridResize("#searchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	
	treeGrid_.trimPjtNo();
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	treeGrid_.search();
        }
    });
    
    $('#pjtNo').focus();
});

window.treeGridSearch = function(){
	treeGrid_.trimPjtNo();
	treeGrid_.search();
}

</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">입고처/조달유형 조회
                                </td>
                                <td align="right"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:treeGridSearch();" class="btn_blue">
                                                   검색
                                            </a>
                                        </td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
            </table>
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            
            <form name="searchForm">
                <input type="hidden" name="searchParam" id="searchParam" value="main">
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <colgroup>
                        <col width="10%" />
                        <col width="90%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL0"><input type="text" name="pjtNo" id="pjtNo" class="txt_field" style="width: 80%" value="${pjtNo}"></td>
                    </tr>
                </table>
            </form>
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
		                            <div class="tabContent" style="border: 0; border-radius: 0;padding-top:0 !important;">
		                                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
		                            </div>
		                        </div>
		                    </div>
		                <!-- EJS TreeGrid Start -->
		                </div>
		            </td>
		        </tr>
		    </table>
            
        </td>
    </tr>
</table>