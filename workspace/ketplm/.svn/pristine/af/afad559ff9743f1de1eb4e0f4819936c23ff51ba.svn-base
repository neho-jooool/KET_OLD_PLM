<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<script src='/plm/extcore/js/cost/costCommon.js?ver=0.1'></script>
<!-- <script src='/plm/extcore/js/cost/xlsx.core.min.js'></script>
<script src='/plm/extcore/js/cost/FileSaver.min.js'></script>
<script src='/plm/extcore/js/cost/Blob.js'></script>
<script src='/plm/extcore/js/cost/tableexport.js'></script> -->
<style>
* {margin:0; padding:0;}

#d1{
 width: 200px;
 height: 200px;
 margin: 0 auto;
}

h1{
    text-align:center;
    line-height: 200px;
}
</style>
<script> 
$(document).ready(function(){
    $(document).attr("title","Excel Export"); 
    
    $(document).bind("contextmenu", function(e){
        return false;
    });
    
	$(".num").each(function(){
		
		var text = $(this).text();
		
		if(text.indexOf("%") >= 0){
			text = text.substring(0, text.indexOf("%"));
		}
		
		if(text != "") {
			if($(this).hasClass("THOUSAND")){
	            text = calculateValue(text, 1000, "/");
	        }else if($(this).hasClass("MILLION")){
	            text = calculateValue(text, 1000000, "/");
	        }
			
	        if($(this).hasClass("nonFixed")){
	        	text = text.commaFormat();
	        }else{
	        	text = text.commaFormat(1);
	        }
		}
		
		$(this).text(text);
	});
	

	window.calcResultTotal = function(){
		

        
        $(".compare").each(function(){
        	
        	var value = $(this).text();
        	if(value.indexOf("%") >= 0){
        		value = value.substring(0, value.indexOf("%"));
            }
        	value = value.commaRemove();
        	if(value != null && value.length > 0){
        		
        		var compare = eval("0<=" + value);
        		
                if(compare){
                	$(this).parent("td:first").css("color", "#0000FF");
                }else{
                	$(this).parent("td:first").css("color", "#FF0000");
                }
                $(this).parent("td:first").css("font-weight", "bold");
        	}
        });
	}
	
	calcResultTotal();
	
/* 	var DefaultTable = $("#default-table").tableExport({
        headers: true,                              // (Boolean), display table headers (th or td elements) in the <thead>, (default: true)
        footers: true,                              // (Boolean), display table footers (th or td elements) in the <tfoot>, (default: false)
        formats: ['xlsx'],            // (String[]), filetype(s) for the export, (default: ['xlsx', 'csv', 'txt'])
        filename: 'id',                             // (id, String), filename for the downloaded file, (default: 'id')
        bootstrap: false,                           // (Boolean), style buttons using bootstrap, (default: false)
        position: 'bottom',                         // (top, bottom), position of the caption element relative to table, (default: 'bottom')
        ignoreRows: null,                           // (Number, Number[]), row indices to exclude from the exported file(s) (default: null)
        ignoreCols: null,                           // (Number, Number[]), column indices to exclude from the exported file(s) (default: null)
        ignoreCSS: '.tableexport-ignore',           // (selector, selector[]), selector(s) to exclude cells from the exported file(s) (default: '.tableexport-ignore')
        emptyCSS: '.tableexport-empty',             // (selector, selector[]), selector(s) to replace cells with an empty string in the exported file(s) (default: '.tableexport-empty')
        trimWhitespace: true,                       // (Boolean), remove all leading/trailing newlines, spaces, and tabs from cell text in the exported file(s) (default: true)
        RTL: false,                                 // (Boolean), set direction of the worksheet to right-to-left (default: false)
        sheetname: 'id'                             // (id, String), sheet name for the exported spreadsheet, (default: 'id')
    });
 */
/* 	var ExportButtons = document.getElementById('COSTTOTALTABLE');

    var instance = new TableExport(ExportButtons, {
        formats: ['xlsx'],
        exportButtons: false
    });

    var exportData = instance.getExportData()['COSTTOTALTABLE']['xlsx'];

    var XLSbutton = document.getElementById('customXLSButton');

    XLSbutton.addEventListener('click', function (e) {
        instance.export2file(exportData.data, exportData.mimeType, exportData.filename, exportData.fileExtension);
    });
    
    var binary = instance.getRawData(exportData.data, exportData.fileExtension);
     */
    
    fnExcelReport();

});

function fnExcelReport() {

	var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
    tab_text = tab_text + '<head><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>';
    tab_text = tab_text + '<x:Name>Sheet1</x:Name>';
    tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
    tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';
    tab_text = tab_text + "<table border='1px'>";
    var exportTable = $('#COSTTOTALTABLE').clone();
    exportTable.find('input').each(function (index, elem) { $(elem).remove(); });
    tab_text = tab_text + exportTable.html();
    tab_text = tab_text + '</table></body></html>';
    
    var d = new Date();
    var curr_hour = d.getHours();
    var curr_min = d.getMinutes();
    var curr_sec = d.getSeconds();
    
    var fileName = "costReport_"+curr_hour + curr_min + curr_sec  + '.xls';
    // ie 10+
    try{        
        
    	//단순 엑셀 다운로드는 아래와 같이하면 됨. 하지만 암호화를 위해 바이너리 문자열을 자바단으로 넘겨 서버에서 암호화 후 내려받음
    	//var blob = new Blob([tab_text], { type: "application/vnd.ms-excel;charset=euc-kr" })
        //window.navigator.msSaveOrOpenBlob(blob, fileName);
        
        //window.saveAs(blob, fileName);
        $("#costExcel").val(tab_text);
        
        var url = '/plm/ext/cost/costExcelCreate.do';
        
        $('[name=excelDataForm]').attr('action', url);
        $('[name=excelDataForm]').attr('target', 'download');
        $('[name=excelDataForm]').submit();
        
    }
    // ie 9이하
    catch(e){
        var data = document.getElementById('COSTTOTALTABLE');
        var csvData = [];
        var tmpArr = [];
        var tmpStr = '';
        for (var i = 0; i < data.rows[0].cells.length; i++)
        {
        tmpArr.push((data.rows[0].cells[i].innerText || data.rows[0].cells[i].textContent));
        }
        csvData.push(tmpArr.join('\t'));
        for (var i = 1; i < data.rows.length; i++)
        {
        tmpArr = [];
        for (var j = 0; j < data.rows[0].cells.length; j++)
        {
            tmpArr.push(data.rows[i].cells[j].innerHTML);
        }
        csvData.push(tmpArr.join('\t'));
        }
        var output = csvData.join('\n');
        SaveContents(output);
    }
}

</script>
<c:if test="${not empty ERROR}">
<script>alert("${ERROR}");self.close();</script>
</c:if>
<body>
<form name="excelDataForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="costExcel" id="costExcel" />
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
<div id="d1">
<h1>Excel Export</h1>
</div>

				<table id="COSTTOTALTABLE" style="display: none">
				<!-- <div>
				<button id= "customXLSButton">Export</button>
				</div> -->
				    <colgroup>
				        <col width="200"/>
				    </colgroup>
				    <thead>
                    <tr >
                        <th rowspan="2" style="background: #C0C0C0";>품명</th>
                        <th rowspan="2" style="background: #C0C0C0";>US</th>
                        <th colspan="4" style="border-bottom:0; background: #C0C0C0">제조원가</th>
                        <th rowspan="2" style="border-left:1px; background: #C0C0C0">관리비</th>
                        <th rowspan="2" style="background: #C0C0C0";>감가비</th>
                        <th rowspan="2" style="background: #C0C0C0";>총원가</th>
                        <th rowspan="2" style="background: #C0C0C0";>판매 목표가</th>
                        <th rowspan="2" style="background: #C0C0C0";>수익율</th>
                        <th rowspan="2" style="background: #C0C0C0";>원가비중</th>
                    </tr>
                    <tr >
                        <th style="background: #C0C0C0";>재료비</th>
                        <th style="background: #C0C0C0";>노무비</th>
                        <th style="background: #C0C0C0";>경비</th>
                        <th style="border-top:0; background: #C0C0C0"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${reportData.cpList }" var="cp" varStatus="stat">
                    <c:if test="${not empty cp.masterReference }">
                        <c:if test="${stat.index ne 0 }">
                            <tr>
                                <th style="background: #C0C0C0";" colspan="2" title="${product.partName }">${product.partName }</th>
		                        <td class="right"><span class="num">${product.materialCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.laborCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.expenseExpr }</span></td>
		                        <td class="right"><span class="num bold">${product.mfcCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.manageCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.reduceCostExpr }</span></td>
		                        <td class="right"><span class="num compare">${product.totalCostExpr }</span></td>
		                        <td class="right"><span class="num bold">${product.salesTargetCostExpr }</span></td>
		                        <td class="right"><span class="num compare">${product.profitRateExpr * 100}</span>%</td>
		                        <td class="center" ><span class="num compare">100</span>%</td>
                            </tr>
                            <tr>
                                <th style="background: #C0C0C0";" colspan="2">(총원가 대비 구성비)</th>
                                <td class="right"><span class="num compare">${(product.materialCostExpr / product.totalCostExpr) * 100 }</span>%</td>
                                <td class="right"><span class="num compare">${(product.laborCostExpr / product.totalCostExpr) * 100 }</span>%</td>
                                <td class="right"><span class="num compare">${(product.expenseExpr / product.totalCostExpr) * 100 }</span>%</td>
                                <td class="right"><span class="num compare">${(product.mfcCostExpr / product.totalCostExpr) * 100 }</span>%</td>
                                <td class="right"><span class="num compare">${(product.manageCostExpr / product.totalCostExpr) * 100 }</span>%</td>
                                <td class="right"><span class="num compare">${(product.reduceCostExpr / product.totalCostExpr) * 100 }</span>%</td>
                                <td class="right"><span class="num compare">100</span>%</td>
                                <td ></td>
                            </tr>
                            <tr>
                                <td  colspan="12"></td>
                            </tr>
                            <script>
                                $("tr[data-productoid='${productOid}']").each(function(){
                                	var totalCostExpr = $(this).find(".totalCostExpr").text().commaRemove();
                                	var ptotalCostExpr = "${product.totalCostExpr}";
                                	var percent = calculateValue(totalCostExpr, ptotalCostExpr, "/");
                                	percent = calculateValue(percent, 100, "*");
                                	if(isNaN(percent)) percent = "0"; 
                                	$(this).find("td:last").html("<span class='num compare'>" + percent +"</span>%");
                                });
                            </script>
                    </c:if>
                        <c:set value="${cp.masterReference }" var="productOid"></c:set>
                        <c:set value="${cp }" var="product"></c:set>
                        
                        <c:set target="${product}" property="materialCostExpr"  value="${product.materialCostExpr * product.us }" />
                        <c:set target="${product}" property="laborCostExpr"     value="${product.laborCostExpr * product.us }" />
                        <c:set target="${product}" property="expenseExpr"       value="${product.expenseExpr * product.us }" />
                        <c:set target="${product}" property="mfcCostExpr"       value="${product.mfcCostExpr * product.us }" />
                        <c:set target="${product}" property="manageCostExpr"    value="${product.manageCostExpr * product.us }" />
                        <c:set target="${product}" property="reduceCostExpr"    value="${product.reduceCostExpr * product.us }" />
                        <c:set target="${product}" property="totalCostExpr"     value="${product.totalCostExpr * product.us }" />
                </c:if>
	                    <tr data-id="${cp.oid }" data-parent="${cp.pOid }" data-level="${cp.bomLevel + 1 }" data-productoid="${productOid }">
	                        <td data-column="name"  title="${cp.partName }">${cp.partName }</td>
	                        <td class="center"><span class="num nonFixed">${cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.materialCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.laborCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.expenseExpr * cp.us }</span></td>
	                        <td class="right"><span class="num bold">${cp.mfcCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.manageCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.reduceCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num compare totalCostExpr">${cp.totalCostExpr * cp.us }</span></td>
	                        <td class="center" colspan="2">${cp.mftFactory2Display }<c:if test="${ not empty cp.company }"> / </c:if>${cp.company }</td>
	                        <td class="center" ></td>
	                    </tr>
	                    <c:if test="${empty cp.masterReference }">
	                        <c:set target="${product}" property="materialCostExpr"  value="${product.materialCostExpr + (cp.materialCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="laborCostExpr"     value="${product.laborCostExpr + (cp.laborCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="expenseExpr"       value="${product.expenseExpr + (cp.expenseExpr * cp.us) }" />
	                        <c:set target="${product}" property="mfcCostExpr"       value="${product.mfcCostExpr + (cp.mfcCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="manageCostExpr"    value="${product.manageCostExpr + (cp.manageCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="reduceCostExpr"    value="${product.reduceCostExpr + (cp.reduceCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="totalCostExpr"     value="${product.totalCostExpr + (cp.totalCostExpr * cp.us) }" />
	                    </c:if>
	                    </c:forEach>
	                    <tr>
	                        <th style="background: #C0C0C0";" colspan="2" title="${product.partName }">${product.partName }</th>
	                        <td class="right"><span class="num">${product.materialCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.laborCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.expenseExpr }</span></td>
	                        <td class="right"><span class="num bold">${product.mfcCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.manageCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.reduceCostExpr }</span></td>
	                        <td class="right"><span class="num compare">${product.totalCostExpr }</span></td>
	                        <td class="right"><span class="num bold">${product.salesTargetCostExpr }</span></td>
	                        <td class="right"><span class="num compare">${product.profitRateExpr * 100}</span>%</td>
	                        <td class="center" ><span class="num compare">100</span>%</td>
	                    </tr>
	                    <tr>
	                        <th style="background: #C0C0C0";" colspan="2">(총원가 대비 구성비)</th>
	                        <td class="right"><span class="num compare">${(product.materialCostExpr / product.totalCostExpr) * 100 }</span>%</td>
	                        <td class="right"><span class="num compare">${(product.laborCostExpr / product.totalCostExpr) * 100 }</span>%</td>
	                        <td class="right"><span class="num compare">${(product.expenseExpr / product.totalCostExpr) * 100 }</span>%</td>
	                        <td class="right"><span class="num compare">${(product.mfcCostExpr / product.totalCostExpr) * 100 }</span>%</td>
	                        <td class="right"><span class="num compare">${(product.manageCostExpr / product.totalCostExpr) * 100 }</span>%</td>
	                        <td class="right"><span class="num compare">${(product.reduceCostExpr / product.totalCostExpr) * 100 }</span>%</td>
	                        <td class="right"><span class="num compare">100</span>%</td>
	                        <td ></td>
	                    </tr>
	                    <tr>
                            <td colspan="12">
                            <script>
                                $("tr[data-productoid='${productOid}']").each(function(){
                                    var totalCostExpr = $(this).find(".totalCostExpr").text().commaRemove();
                                    var ptotalCostExpr = "${product.totalCostExpr}";
                                    
                                    var percent = calculateValue(totalCostExpr, ptotalCostExpr, "/");
                                    percent = calculateValue(percent, 100, "*");
                                    if(isNaN(percent)) percent = "0"; 
                                    $(this).find("td:last").html("<span class='num compare'>" + percent +"</span>%");
                                });
                            </script>
                            </td>
                        </tr>
                    </tbody>
                </table>
</form>
</body>