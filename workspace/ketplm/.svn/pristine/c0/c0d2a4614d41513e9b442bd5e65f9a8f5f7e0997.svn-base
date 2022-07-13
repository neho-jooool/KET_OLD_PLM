<!DOCTYPE html>
<html>
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
$(document).ready(function(e) {
	window.numberWithCommas = function(num){
        //var parts = num.replace( /\.0000/gi, ''); 
        var parts = num.toString().split(".");
        return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1] : "");
    }
	var config = {
			id : 'expenseGrid',
			usePaging : false,
            Data : {
                Url : '/plm/ext/project/product/getExpenseListByProject.do',
                Method : 'POST',
                Format : 'Form',
                Params : decodeURIComponent($('[name=searchForm]').serialize())
            },
            Cols : [
                     {Name : 'rnum', Width:40,  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
                     {Name : 'investAcc', Width:130,  Align : 'Left', CanSort : '0', CanEdit : '0', Spanned:'1'},
                     {Name : 'costCenter',  Width:150,  Align : 'Left', CanSort : '0' , CanEdit : '0' ,Spanned:'1'},
                     {Name : 'amt01',     Width:90,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                     {Name : 'amt02',     Width:90,  Align : 'Right', CanSort : '0', CanEdit : '0'  ,Spanned:'1'},
                     {Name : 'amt03',     Width:90,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                     {Name : 'amt04',     Width:90,  Align : 'Right', CanSort : '0', CanEdit : '0'  ,Spanned:'1'},
                     {Name : 'amt05',     Width:90,  Align : 'Right', CanSort : '0', CanEdit : '0'  ,Spanned:'1'},
                     {Name : 'amt06',     Width:100,  Align : 'Right', CanSort : '0', CanEdit : '0'  ,Spanned:'1'},
                     {Name : 'currency',     Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                     {Name : 'bigo',     Width:200,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                     {Name : 'posId',     Width:100,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
            ],Head :[
                     {
                         CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
                         id : "Header",Spanned : '1',
                         rnum : 'No', rnumRowSpan:'2',
                         investAcc : "투자계정", investAccRowSpan:'2',
                         costCenter : "책임 코스트센터", costCenterRowSpan:'2',
                         amt01 : "총예산", amt01RowSpan:'2',
                         amt02: "실적", amt02Span : '4',
                         amt06 : "사용가능\n금액", amt06RowSpan : '2',
                         currency : "거래\n통화", currencyRowSpan : '2',
                         bigo : "비고", bigoRowSpan : '2',
                         posId : "WBS요소", posIdRowSpan : '2',
                     },
                     {
                         CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
                         id:"HeaderTop", 
                         amt02 : "실적",
                         amt03 : "구매오더",
                         amt04 : "구매요청",
                         amt05 : "임시전표",
                     }
             ],
             SelectingSingle : '0'
     };
	
	var grid = TreeGrid(CommonGrid.getGridConfig(config),'listGrid');
	
    Grids.OnRenderPageFinish = function(){
     	var G = Grids[0];
    	G.Toolbar.Formula = '0';
    	var rows = G.Rows;
    	var colNames = G.ColNames[1];
    	for(var key in rows){
    		if(key == 'Header' || key == 'HeaderTop'){
    			gridHeaderFontSetting(colNames,G,rows[key]);	
    		}
    		
     		if(rows[key].Kind == "Data" && key != 'HeaderTop'){
     			
     			colNames.forEach(function(columnKey,index){
     				if(columnKey.indexOf('amt') >= 0){
     					G.SetValue(rows[key],columnKey,numberWithCommas(G.GetValue(rows[key],columnKey)) ,1);
     					G.SetAttribute(rows[key], columnKey, "Changed", 0, 1);
     				}
     				if(columnKey.indexOf('rnum') >= 0){
     					G.SetValue(rows[key],columnKey,rows[key].r1.rowIndex ,1);
                        G.SetAttribute(rows[key], columnKey, "Changed", 0, 1);
     				}
     				
     			});
    		}

        }
    }
	
     window.gridHeaderFontSetting = function(colNames,G,row){
         for(var j = 0; j < colNames.length; j++){
        	 gridHeaderFontSizeChange(G, row, colNames[j], 2);
         }
     },
     
     window.gridHeaderFontSizeChange = function(G, tRow, attr, size){
         
         G.SetAttribute(tRow, attr, "Background", "#E2EDF4", 1);
         G.SetAttribute(tRow, attr, "HtmlPrefix", "<b><font size="+size+ ">", 1);
         G.SetAttribute(tRow, attr, "Postfix", "</font></b>", 1);
         G.SetAttribute(tRow, attr, "Changed", 0, 1);
     },
     

     window.search =  function(perPage){
         
         if(!perPage) perPage = Grids['expenseGrid'].Source.Layout.Data.Cfg.PageLength;
         Grids['expenseGrid'].Source.Data.Url = '/plm/ext/project/purchase/findPagingList.do?sortName=*Sort0';
         Grids['expenseGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
         Grids['expenseGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
         Grids['expenseGrid'].Source.Data.Param.formPage=perPage;
//     Grids['expenseGrid'].ReloadBody();
         
         Grids[0].Reload();
     },
     
	treeGridResize("#expenseGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    
	$(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	search();
        }
    });

});


</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">경비상세 리스트</td>
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
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>                                        
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue">닫기</a></td>
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
                <input type="hidden" name="pjtNo" id="pjtNo" value='${pjtNo}'>
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
</html>