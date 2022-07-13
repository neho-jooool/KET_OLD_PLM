<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<style>
html{
	height:100%;
}
.contents-wrap {
	height:100%;
}
.infoTable {
	table-layout:fixed;
}
.infoTable td{
	padding:0;
	overflow:hidden;
	text-align:center;
}
.infoTable .edit{
	width:100% !important;
	height:30px !important;
	border:0 !important;
	margin:0;padding:0;
	padding:5px;
}
.infoTable .editSelect{
	width:100% !important;
	height:30px !important;
	border:0;
	margin:0;padding:0;
	float:right;
}
.addItem{
	cursor:pointer;
}
.deleteItem{
	cursor:pointer;
	margin:5px;
}
</style>
<script>
var SCENARIO = ${SCENARIO};
var RAWMATTHICKNESS = ${RAWMATTHICKNESS};
var RAWMATWIDTH = ${RAWMATWIDTH};
var PLATING = ${PLATING};
var FACTORY = ${FACTORY};
var MONETARYUNIT = ${MONETARYUNIT};

$(document).ready(function(){
	
	onResizeTables();
	
	$(window).resize(function(){
		onResizeTables();
	});
});

window.onResizeTables = function(){
	
	var tdWidthList = new Array();
	
	$("#nMetalStdInfo tr:first td").each(function(i){
		tdWidthList.push($(this).width());
	}); 
	
 	$("#stdInfo1 tr:first td:eq(0)").width(tdWidthList[1]);
	$("#stdInfo1 tr:first td:eq(1)").width(tdWidthList[2]);
	
	var width = tdWidthList[3] + tdWidthList[4];
	var width2 = tdWidthList[5];
	$("#stdInfo1 tr:first td:eq(2)").width(width + 1);
	$("#stdInfo1 tr:first td:eq(3)").width(width2);
	
	$("#stdInfo2 tr:first td:eq(0)").width(tdWidthList[1] + tdWidthList[2]);
	$("#stdInfo2 tr:first td:eq(1)").width(width + 1);
	$("#stdInfo2 tr:first td:eq(2)").width(width2);
	
	$("#stdInfo3 tr:first td:eq(0)").width(tdWidthList[1]);
	$("#stdInfo3 tr:first td:eq(1)").width(tdWidthList[2]);
	$("#stdInfo3 tr:first td:eq(2)").width(width + 1);
	$("#stdInfo3 tr:first td:eq(3)").width(width2);
	
 	$("#stdInfo4 tr:first td:eq(1)").width(tdWidthList[0] - 25);
	$("#stdInfo4 tr:first td:eq(2)").width(tdWidthList[1]);
	$("#stdInfo4 tr:first td:eq(3)").width(tdWidthList[2]);
	$("#stdInfo4 tr:first td:eq(4)").width(width + 1);
	$("#stdInfo4 tr:first td:eq(5)").width(width2);
	
	$("#stdInfo5 tr:first td:eq(1)").width(tdWidthList[0] - 25);
    $("#stdInfo5 tr:first td:eq(2)").width(tdWidthList[1]);
    $("#stdInfo5 tr:first td:eq(3)").width(tdWidthList[2]);
    $("#stdInfo5 tr:first td:eq(4)").width(width + 1);
    $("#stdInfo5 tr:first td:eq(5)").width(width2);
}

window.addItem = function(id, isChild){
	
	var tr = "<tr>";
	
	if("stdInfo1" == id){
		tr += "<td style='width:25px;'><img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/></td>";
		tr += "<td><select name='scenario' class='editSelect'><option value=''>선택</option>";
		for(var i = 0; i < SCENARIO.length; i++){
			var code = SCENARIO[i];
			tr += "<option value='" + code.code + "'>" + code.name +"</option>";
		}
		tr += "</select></td>";
		tr += "<td><input type='text' name='lmeStd' class='edit'/></td>";
		tr += "<td><input type='text' name='nMetalPrice' class='edit'/></td>";
		tr += "<td><select name='nMetalMUnit' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < MONETARYUNIT.length; i++){
            var code = MONETARYUNIT[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
		tr += "</tr>";
		$("#" + id + " tbody").append(tr);
	}else if("stdInfo2" == id){
		
		tr += "<td style='width:25px;'><img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/></td>";
		tr += "<td><select name='platingThick' class='editSelect'><option value=''>선택</option>";
		for(var i = 0; i < RAWMATTHICKNESS.length; i++){
			var code = RAWMATTHICKNESS[i];
			tr += "<option value='" + code.code + "'>" + code.name +"</option>";
		}
		tr += "</select></td>";
		tr += "<td><input type='text' name='platingPrice' class='edit'/></td>";
		tr += "<td><select name='platingMUnit' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < MONETARYUNIT.length; i++){
            var code = MONETARYUNIT[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
		tr += "</tr>";
		$("#" + id + " tbody").append(tr);
		
	}else if("stdInfo3" == id){
		if(!isChild){
			tr += "<td rowspan='1' style='width:45px;'>" +
			"<input type='radio' name='stdInfoCheck' style='position:relative;top:4px;' />" +
			"<img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/></td>";
			
			tr += "<td><select name='cuttingWidth' class='editSelect'><option value=''>선택</option>";
			for(var i = 0; i < RAWMATWIDTH.length; i++){
				var code = RAWMATWIDTH[i];
				tr += "<option value='" + code.code + "'>" + code.name +"</option>";
			}
			tr += "</select></td>";
		}
		
		tr += "<td style='width:25px;'><img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/></td>";
		tr += "<td><select name='cuttingThick' class='editSelect'><option value=''>선택</option>";
		for(var i = 0; i < RAWMATTHICKNESS.length; i++){
			var code = RAWMATTHICKNESS[i];
			tr += "<option value='" + code.code + "'>" + code.name +"</option>";
		}
		tr += "</select></td>";
		tr += "<td><input type='text' name='cuttingPrice' class='edit'/></td>";
		tr += "<td><select name='cuttingMUnit' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < MONETARYUNIT.length; i++){
            var code = MONETARYUNIT[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
		
		if(isChild){
			var rowIdx = $("input[name='stdInfoCheck']:checked").parents("tr:first")[0].sectionRowIndex;
			var rowspan = $("input[name='stdInfoCheck']:checked").parents("tr").children("td:first").attr("rowspan");
			rowIdx += parseInt(rowspan) - 1;
			$("#" + id + " tbody tr:eq(" + rowIdx + ")").after(tr);
			
			rowspan = parseInt(rowspan) + 1;
			
			$("input[name='stdInfoCheck']:checked").parents("tr").children("td:eq(0)").attr("rowspan", rowspan);
			$("input[name='stdInfoCheck']:checked").parents("tr").children("td:eq(1)").attr("rowspan", rowspan);
		}else{
			$("#" + id + " tbody").append(tr);
			$("input[name='stdInfoCheck']:last").prop("checked", true);
		}
		
	}else if("stdInfo4" == id){
		
		tr += "<td style='width:25px'><img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/></td>";
		
		tr += "<td><select name='factoryType2' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < FACTORY.length; i++){
            var code = FACTORY[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
		tr += "<td><select name='platingType' class='editSelect'><option value=''>선택</option>";
		for(var i = 0; i < PLATING.length; i++){
			var code = PLATING[i];
			tr += "<option value='" + code.code + "'>" + code.name +"</option>";
		}
		tr += "</select></td>";
		tr += "<td><input type='text' name='deTin' class='edit'/></td>";
		tr += "<td><input type='text' name='scrapRecycleCost' class='edit'/></td>";
		tr += "<td><select name='metalSRMUnit' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < MONETARYUNIT.length; i++){
            var code = MONETARYUNIT[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
		tr += "</tr>";
		$("#" + id + " tbody").append(tr);
	}else if("stdInfo5" == id){
        
        tr += "<td style='width:25px'><img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/></td>";
        tr += "<td><select name='scenario' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < SCENARIO.length; i++){
            var code = SCENARIO[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
        tr += "<td><select name='factoryType' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < FACTORY.length; i++){
            var code = FACTORY[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
        tr += "<td><select name='platingType2' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < PLATING.length; i++){
            var code = PLATING[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
        tr += "<td><input type='text' name='metalScrapCost' class='edit'/></td>";
        tr += "<td><select name='metalSSMUnit' class='editSelect'><option value=''>선택</option>";
        for(var i = 0; i < MONETARYUNIT.length; i++){
            var code = MONETARYUNIT[i];
            tr += "<option value='" + code.code + "'>" + code.name +"</option>";
        }
        tr += "</select></td>";
        tr += "</tr>";
        $("#" + id + " tbody").append(tr);
    }
	
	onResizeTables();
}
window.deleteItem = function(obj){
	
	var rowspan = $(obj).parents("td:first").attr("rowspan");
	
	if(rowspan){
		
		var idx = 0;
		if($(obj).parents("tr:first").length > 0){
			idx = $(obj).parents("tr:first")[0].sectionRowIndex - 1;
		}
		
		if(idx >= 0){
			$(obj).parents("table tbody:first").children("tr:gt(" + idx + ")").each(function(i){
				if(rowspan > i){
					$(this).remove();
				}else{
					return false;
				}
			});
		}else{
			$(obj).parents("table tbody:first").children("tr").each(function(i){
				if(rowspan > i){
					$(this).remove();
				}else{
					return false;
				}
			});
		}
	}else{   
		
		var rowspan = $(obj).parents("tr").children("td:first").attr("rowspan");
		
		if(rowspan){
			var td1 = $(obj).parents("tr").children("td:eq(0)");
			var td2 = $(obj).parents("tr").children("td:eq(1)");
			$(td1).attr("rowspan", rowspan-1);
			$(td2).attr("rowspan", rowspan-1);
			
			var inputTd = $(obj).parents("tr").next("tr").children("td:eq(0)");
			
			if(!$(inputTd).hasClass("bgcol") && $(inputTd).children("input[type='radio']").length == 0){
				$(inputTd).before(td1);
				$(inputTd).before(td2);
			}
			
		}else{
			var idx = $(obj).parents("tr")[0].sectionRowIndex + 1;
			var tr = null;
			$(obj).parents("table tbody:first").children("tr:lt(" + idx + ")").each(function(i){
				var spanValue = $(this).children("td:first").attr("rowspan");
				if(spanValue){
					rowspan = spanValue;
					tr = this;
				}
			});
			$(tr).children("td:eq(0)").attr("rowspan", rowspan - 1);
			$(tr).children("td:eq(1)").attr("rowspan", rowspan - 1);
		}
		
		$(obj).parents("tr:first").remove();
	}
}

//입력정보 가져오기
window.getCSInfoItems = function(){
	
	var stdInfo1 = new Array();
	var stdInfo2 = new Array();
	var stdInfo3 = new Array();
	var stdInfo4 = new Array();
	var stdInfo5 = new Array();
	
	$("#stdInfo1 tbody tr").each(function(){
		var var1 = $(this).find("select[name='scenario']").val();
		var var2 = $(this).find("input[name='lmeStd']").val();
		var var3 = $(this).find("input[name='nMetalPrice']").val();
		var var4 = $(this).find("select[name='nMetalMUnit']").val();
		stdInfo1.push({ value1 : var1, value2 : var2, value3 : var3, mUnit : var4, itemType : "NMETALCOST" });
	});
	
	$("#stdInfo2 tbody tr").each(function(){
		var var1 = $(this).find("select[name='platingThick']").val();
		var var2 = $(this).find("input[name='platingPrice']").val();
		var var3 = $(this).find("select[name='platingMUnit']").val();
		stdInfo2.push({ value1 : var1, value2 : var2, mUnit : var3, itemType : "PLATINGCOST" });
	});
	
	var cuttingWidth = null;
	$("#stdInfo3 tbody tr").each(function(){
		window.console.log(this);
		var var1 = $(this).find("select[name='cuttingWidth']").val();
		var var2 = $(this).find("select[name='cuttingThick']").val();
		var var3 = $(this).find("input[name='cuttingPrice']").val();
		var var4 = $(this).find("select[name='cuttingMUnit']").val();
		
		if(var1 == null){
			var1 = cuttingWidth;
		}else{
			cuttingWidth = var1;
		}
		
		stdInfo3.push({ value1 : var1, value2 : var2, value3 : var3, mUnit : var4, itemType : "CUTTINGCOST" });
	});
	
	$("#stdInfo4 tbody tr").each(function(){
		var var1 = $(this).find("select[name='platingType']").val();
		var var2 = $(this).find("input[name='deTin']").val();
		var var3 = $(this).find("input[name='scrapRecycleCost']").val();
		var var4 = $(this).find("select[name='factoryType2']").val();
		var var5 = $(this).find("select[name='metalSRMUnit']").val();
		
		stdInfo4.push({ value1 : var1, value2 : var2, value3 : var3, value4 : var4, mUnit : var5, itemType : "SCRAPRECYCLECOST" });
	});
	
	$("#stdInfo5 tbody tr").each(function(){
        var var1 = $(this).find("select[name='factoryType']").val();
        var var2 = $(this).find("select[name='platingType2']").val();
        var var3 = $(this).find("input[name='metalScrapCost']").val();
        var var4 = $(this).find("select[name='metalSSMUnit']").val();
        var var5 = $(this).find("select[name='scenario']").val();
        
        stdInfo4.push({ value1 : var1, value2 : var2, value3 : var3, mUnit : var4, value5 : var5, itemType : "SCRAPSALESCOST" });
    });

	var data = {
			rMatCode : $("select[name='rMatCode']").val(),
			importance : $("input[name='importance']").val(),
			scrapInternal : $("input[name='scrapInternal']").val(),
			scrapChina : $("input[name='scrapChina']").val(),
			stdInfo1 : stdInfo1,
			stdInfo2 : stdInfo2,
			stdInfo3 : stdInfo3,
			stdInfo4 : stdInfo4,
	}
	/* 
	window.console.log(stdInfo1);
	window.console.log(stdInfo2);
	window.console.log(stdInfo3);
	window.console.log(stdInfo4);
	window.console.log(data);
	 */
	return data;
}

window.saveCSInfo = function(){
	
	if(!checkDuplicateCSInfo() && confirm("저장하시겠습니까?")){
		
		var rMatCode = $("select[name='rMatCode']").val();
	    var param = new Object();
	    param = getCSInfoItems();
	    param.mode = "SAVE";
	    param.infoType = "NMETAL" + rMatCode;
	    
	    ajaxCallServer("/plm/ext/cost/saveCSInfo", param, function(res){
	        location.href="/plm/ext/cost/viewNMetalCSIPopup?rMatCode=" + rMatCode;
	    });
	}
}

window.checkDuplicateCSInfo = function(){
	
    var isDuplicate = false;
    
    var checkList = new Array();
    $("#stdInfo1 tbody tr").each(function(){
        var var1 = $(this).find("select[name=scenario]").val();
        var text = $(this).find('select[name=scenario] option:selected').text();
        
        if(checkList.contains(var1)) {
            alert("비철단가 시나리오 [" + text + "] 값이 중복됩니다."); 
            isDuplicate = true;
            return false;
        }
        checkList.push(var1);
    });
    
    if(!isDuplicate){
        
        checkList = new Array();
        
        $("#stdInfo2 tbody tr").each(function(){
            var var1 = $(this).find("select[name=platingThick]").val();
            var text = $(this).find('select[name=platingThick] option:selected').text();
            
            if(checkList.contains(var1)) {
                alert("도금단가 두께 [" + text + "] 값이 중복됩니다."); 
                isDuplicate = true;
                return false;
            }
            checkList.push(var1);
        });
    }
    
    if(!isDuplicate){
        
        checkList = new Array();
        var cuttingWidth = null;
        var cuttingWidthText = null;
        $("#stdInfo3 tbody tr").each(function(){
        	
        	var var1 = $(this).find("select[name=cuttingWidth]").val();
            var var2 = $(this).find("select[name=cuttingThick]").val();
            var text1 = $(this).find('select[name=cuttingWidth] option:selected').text();
            var text2 = $(this).find('select[name=cuttingThick] option:selected').text();
            
            
            if(var1 == null){
                var1 = cuttingWidth;
                text1 = cuttingWidthText;
            }else{
            	
            	if(checkList.contains(var1)) {
                    alert("절단비 폭 [" + text1 + "] 값이 중복됩니다."); 
                    isDuplicate = true;
                    return false;
                }
                checkList.push(var1);
            	
                cuttingWidth = var1;
                cuttingWidthText = text1;
            }
            
            if(checkList.contains(var1+var2)) {
                alert("절단비 폭 - 두께 [" + text1 + " - " + text2 + "] 값이 중복됩니다."); 
                isDuplicate = true;
                return false;
            }
            checkList.push(var1+var2);
        });
    }
    
    if(!isDuplicate){
        
        checkList = new Array();
        $("#stdInfo4 tbody tr").each(function(){
            
            var var1 = $(this).find("select[name=factoryType2]").val();
            var var2 = $(this).find("select[name=platingType]").val();
            var text1 = $(this).find('select[name=factoryType2] option:selected').text();
            var text2 = $(this).find('select[name=platingType] option:selected').text();
            
            if(checkList.contains(var1+var2)) {
                alert("스크랩 재생비 생산국 - 선도금사양 [" + text1 + " - " + text2 + "] 값이 중복됩니다."); 
                isDuplicate = true;
                return false;
            }
            checkList.push(var1+var2);
            
        });
    }
    
    if(!isDuplicate){
        
        checkList = new Array();
        $("#stdInfo5 tbody tr").each(function(){
            
            var var1 = $(this).find("select[name=factoryType]").val();
            var var2 = $(this).find("select[name=platingType2]").val();
            var text1 = $(this).find('select[name=factoryType] option:selected').text();
            var text2 = $(this).find('select[name=platingType2] option:selected').text();
            
            var var5 = $(this).find("select[name=scenario]").val();
            var tex5 = $(this).find('select[name=scenario] option:selected').text();
            
            if(checkList.contains(var5+var1+var2)) {
                alert("스크랩 판매비 시나리오 - 생산국 - 선도금사양 [" + tex5 + " - " + text1 + " - " + text2 + "] 값이 중복됩니다."); 
                isDuplicate = true;
                return false;
            }
            
            checkList.push(var5+var1+var2);
        });
    }
    
    return isDuplicate;
}

window.deleteCSInfo = function(){
	
	if(confirm("삭제하시겠습니까?")){
		var rMatCode = $("select[name='rMatCode']").val();
	    var param = new Object();
	    param.infoType = "NMETAL" + rMatCode;
	    ajaxCallServer("/plm/ext/cost/deleteCSInfo", param, function(res){
	        location.href="/plm/ext/cost/viewNMetalCSIPopup?rMatCode=" + rMatCode;
	    });
	}
}
</script>
<body class="popup-background popup-space">
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />비철 원가기준정보
		</div>
		<div class="b-space5 float-r" style="text-align: right">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:saveCSInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<c:if test="${!empty csInfo}">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:deleteCSInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:location.href='/plm/ext/cost/viewNMetalCSIPopup?rMatCode=${rMatCode}';" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			</c:if>
			<c:if test="${empty csInfo}">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			</c:if>
		</div>
		<div class="sub-title-02 float-l b-space5">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>비철원가기준정보 등록/수정
		</div>
		<div class="b-space5" >
			<table id="nMetalStdInfo" summary="" class="table-style-01 infoTable">
				<colgroup>
					<col width="15%" />
					<col width="25%" />
					<col width="25%" />
					<col width="10%" />
					<col width="10%" />
					<col width="20%" />
				</colgroup>
				<tbody>
					<tr>
						<td class="center bgcol fontb">원재료 코드</td>
						<td style="">
							<select name="rMatCode" class='edit'>
								<c:forEach var="rMatCode" items="${RAWMATERIAL}">
									<option value="${rMatCode.code}">${rMatCode.name }</option>
								</c:forEach>
							</select>
							<script>
								$("select[name='rMatCode']").val("${rMatCode}").prop("selected", true);
								$("select[name='rMatCode']").change(function(){
									location.href="/plm/ext/cost/viewNMetalCSIPopup?rMatCode=" + $(this).val();
								});
							</script>
						</td>
						<td class="center bgcol fontb">비중</td>
						<td>
							<input type="text" name="importance" class="edit" value="${csInfo.importance}"/>
						</td>
						<td class="center bgcol fontb">버전</td>
						<td>
							<c:if test="${empty csInfo}">
								신규
							</c:if>
							${version }
						</td>
					</tr>
					<tr>
						<td class="center bgcol fontb">비철단가</td>
						<td colspan="5" >
							<table id="stdInfo1" class="infoTable">
								<thead>
									<tr>
										<td class="center bgcol fontb" colspan="2">시나리오 <img src="/plm/portal/images/iconPlus.gif" class="addItem" onclick="addItem('stdInfo1')"/></td>
										<td class="center bgcol fontb">LME 기준</td>
										<td class="center bgcol fontb">금액</td>
										<td class="center bgcol fontb">화폐단위</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="csInfoItem" items="${csInfoItemList}">
										<c:if test="${csInfoItem.itemType eq 'NMETALCOST' }">
											<tr>
												<td style='width:25px;'><img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/></td>
												<td>
													<select name='scenario' class='editSelect'><option value=''>선택</option></select>
													<script>
														for(var i = 0; i < SCENARIO.length; i++){
															var item = SCENARIO[i];
															var option = "<option value='" + item.code + "'>" + item.name +"</option>";
															$("select[name='scenario']:last").append(option);
														}
														$("select[name='scenario']:last").val("${csInfoItem.value1}").prop("selected", true);
													</script>
												</td>
												<td><input type='text' name='lmeStd' class='edit' value='${csInfoItem.value2 }' /></td>
												<td><input type='text' name='nMetalPrice' class='edit' value='${csInfoItem.value3 }' /></td>
												<td>
                                                    <select name=nMetalMUnit class='editSelect'><option value=''>선택</option></select>
                                                    <script>
                                                        for(var i = 0; i < MONETARYUNIT.length; i++){
                                                            var item = MONETARYUNIT[i];
                                                            var option = "<option value='" + item.code + "'>" + item.name +"</option>";
                                                            $("select[name='nMetalMUnit']:last").append(option);
                                                        }
                                                        $("select[name='nMetalMUnit']:last").val("${csInfoItem.mUnit}").prop("selected", true);
                                                    </script>
                                                </td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td class="center bgcol fontb">도금단가</td>
						<td colspan="5" >
							<table id="stdInfo2" class="infoTable">
								<thead>
									<tr>
										<td class="center bgcol fontb" colspan="2">두께 <img src="/plm/portal/images/iconPlus.gif" class="addItem" onclick="addItem('stdInfo2')"/></td>
										<td class="center bgcol fontb">금액</td>
										<td class="center bgcol fontb">화폐단위</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="csInfoItem" items="${csInfoItemList}">
										<c:if test="${csInfoItem.itemType eq 'PLATINGCOST' }">
											<tr>
												<td style='width:25px;'><img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/></td>
												<td>
													<select name='platingThick' class='editSelect'><option value=''>선택</option></select>
													<script>
														for(var i = 0; i < RAWMATTHICKNESS.length; i++){
															var item = RAWMATTHICKNESS[i];
															var option = "<option value='" + item.code + "'>" + item.name +"</option>";
															$("select[name='platingThick']:last").append(option);
														}
														$("select[name='platingThick']:last").val("${csInfoItem.value1}").prop("selected", true);
													</script>
												</td>
												<td><input type='text' name='platingPrice' class='edit' value='${csInfoItem.value2 }' /></td>
												<td>
                                                    <select name=platingMUnit class='editSelect'><option value=''>선택</option></select>
                                                    <script>
                                                        for(var i = 0; i < MONETARYUNIT.length; i++){
                                                            var item = MONETARYUNIT[i];
                                                            var option = "<option value='" + item.code + "'>" + item.name +"</option>";
                                                            $("select[name='platingMUnit']:last").append(option);
                                                        }
                                                        $("select[name='platingMUnit']:last").val("${csInfoItem.mUnit}").prop("selected", true);
                                                    </script>
                                                </td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td class="center bgcol fontb">절단비</td>
						<td colspan="5" >
							<table id="stdInfo3" class="infoTable">
								<thead>
									<tr>
										<td class="center bgcol fontb" colspan="2">폭 <img src="/plm/portal/images/iconPlus.gif" style="cursor:pointer;" class="addItem" onclick="addItem('stdInfo3')" /></td>
										<td class="center bgcol fontb" colspan="2">두께 <img src="/plm/portal/images/iconPlus.gif" style="cursor:pointer;" class="addItem" onclick="addItem('stdInfo3', true)"/></td>
										<td class="center bgcol fontb">금액</td>
										<td class="center bgcol fontb">화폐단위</td>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="csInfoItem" items="${csInfoItemList}">
									<c:if test="${csInfoItem.itemType eq 'CUTTINGCOST' }">
									<c:if test="${cuttingWidth eq csInfoItem.value1 }"><tr></c:if>
									<c:if test="${cuttingWidth ne csInfoItem.value1 }">
										<c:set var="cuttingWidth" value="${csInfoItem.value1}" ></c:set>
										<tr data-rowspan="true">
										<td rowspan='0' style='width:45px;'>
											<input type='radio' name='stdInfoCheck' style='position:relative;top:4px;' /><img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/>
										</td>
										<td rowspan='0'>
											<select name='cuttingWidth' class='editSelect'><option value=''>선택</option></select>
											<script>
												for(var i = 0; i < RAWMATWIDTH.length; i++){
													var item = RAWMATWIDTH[i];
													tr = "<option value='" + item.code + "'>" + item.name + "</option>";
													$("select[name='cuttingWidth']:last").append(tr);
												}
												$("select[name='cuttingWidth']:last").val("${csInfoItem.value1}").prop("selected", true);
											</script>
										</td>
									</c:if>
									<script>
									var rowspan = $("#stdInfo3 tr[data-rowspan]:last td:eq(0)").attr("rowspan");
									if(rowspan){
										rowspan = parseInt(rowspan) + 1;
										$("#stdInfo3 tr[data-rowspan]:last td:eq(0)").attr("rowspan", rowspan);
										$("#stdInfo3 tr[data-rowspan]:last td:eq(1)").attr("rowspan", rowspan);
									}
									</script>
									<td style='width:25px;'>
										<img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/>
									</td>
									<td>
										<select name='cuttingThick' class='editSelect'><option value=''>선택</option></select>
										<script>
											for(var i = 0; i < RAWMATTHICKNESS.length; i++){
												var item = RAWMATTHICKNESS[i];
												tr = "<option value='" + item.code + "'>" + item.name +"</option>";
												$("select[name='cuttingThick']:last").append(tr);
											}
											$("select[name='cuttingThick']:last").val("${csInfoItem.value2}").prop("selected", true);
										</script>
									</td>
									<td><input type='text' name='cuttingPrice' class='edit' value='${csInfoItem.value3 }' /></td>
									<td>
                                        <select name=cuttingMUnit class='editSelect'><option value=''>선택</option></select>
                                        <script>
                                            for(var i = 0; i < MONETARYUNIT.length; i++){
                                                var item = MONETARYUNIT[i];
                                                var option = "<option value='" + item.code + "'>" + item.name +"</option>";
                                                $("select[name='cuttingMUnit']:last").append(option);
                                            }
                                            $("select[name='cuttingMUnit']:last").val("${csInfoItem.mUnit}").prop("selected", true);
                                        </script>
                                    </td>
									</tr>
									</c:if>
								</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
<%-- 					<tr>
						<td class="center bgcol fontb">재생단가(국내)</td>
						<td>
							<input type="text" name="scrapInternal" class="edit" value="${csInfo.scrapInternal}"/>
						</td>
						<td class="center bgcol fontb">스크랩(중국)</td>
						<td colspan="3">
							<input type="text" name="scrapChina" class="edit" value="${csInfo.scrapChina}"/>
						</td>
					</tr> --%>
				</tbody>
			</table>
			
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space20"></td>
                </tr>
            </table>
            <div class="sub-title-02 b-space15 clear">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>스크랩 재생비 설정
            </div>
            
			<table id="stdInfo4" summary="" class="table-style-01 infoTable">
				<thead>
					<tr>
						<td class="center bgcol fontb" style="width:25px;"></td>
						<td class="center bgcol fontb">생산국  <img src="/plm/portal/images/iconPlus.gif"  class="addItem" onclick="addItem('stdInfo4')" /></td>
						<td class="center bgcol fontb">선도금사양</td>
						<td class="center bgcol fontb">De-Tin</td>
						<td class="center bgcol fontb">임가공비</td>
						<td class="center bgcol fontb">화폐단위</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="csInfoItem" items="${csInfoItemList}">
						<c:if test="${csInfoItem.itemType eq 'SCRAPRECYCLECOST' }">
						<tr>
						<td style='width:25px'><img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/></td>
						
						<td>
                            <select name='factoryType2' class='editSelect'><option value=''>선택</option></select>
                            <script>
                                for(var i = 0; i < FACTORY.length; i++){
                                    var item = FACTORY[i];
                                    tr = "<option value='" + item.code + "'>" + item.name +"</option>";
                                    $("select[name='factoryType2']:last").append(tr);
                                }
                                $("select[name='factoryType2']:last").val("${csInfoItem.value4}").prop("selected", true);
                            </script>
                        </td>
                        
						<td>
							<select name='platingType' class='editSelect'><option value=''>선택</option></select>
							<script>
								for(var i = 0; i < PLATING.length; i++){
									var item = PLATING[i];
									tr = "<option value='" + item.code + "'>" + item.name +"</option>";
									$("select[name='platingType']:last").append(tr);
								}
								$("select[name='platingType']:last").val("${csInfoItem.value1}").prop("selected", true);
							</script>
						</td>
						<td><input type='text' name='deTin' class='edit' value='${csInfoItem.value2 }' /></td>
						<td><input type='text' name='scrapRecycleCost' class='edit' value='${csInfoItem.value3 }' /></td>
						<td>
                            <select name=metalSRMUnit class='editSelect'><option value=''>선택</option></select>
                            <script>
                                for(var i = 0; i < MONETARYUNIT.length; i++){
                                    var item = MONETARYUNIT[i];
                                    var option = "<option value='" + item.code + "'>" + item.name +"</option>";
                                    $("select[name='metalSRMUnit']:last").append(option);
                                }
                                $("select[name='metalSRMUnit']:last").val("${csInfoItem.mUnit}").prop("selected", true);
                            </script>
                        </td>
						</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
			        <td class="space20"></td>
			    </tr>
		    </table>
			<div class="sub-title-02 b-space15 clear">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>스크랩 판매비 설정
            </div>
			<table id="stdInfo5" summary="" class="table-style-01 infoTable">
                <thead>
                    <tr>
                        <td class="center bgcol fontb" style="width:25px;"></td>
                        <td class="center bgcol fontb">시나리오  <img src="/plm/portal/images/iconPlus.gif"  class="addItem" onclick="addItem('stdInfo5')" /></td>
                        <td class="center bgcol fontb">생산국</td>
                        <td class="center bgcol fontb">선도금사양</td>
                        <td class="center bgcol fontb">스크랩판매비</td>
                        <td class="center bgcol fontb">화폐단위</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="csInfoItem" items="${csInfoItemList}">
                        <c:if test="${csInfoItem.itemType eq 'SCRAPSALESCOST' }">
                        <tr>
                        <td style='width:25px'><img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/></td>
                        <td>
                            <select name='scenario' class='editSelect'><option value=''>선택</option></select>
                            <script>
                                for(var i = 0; i < SCENARIO.length; i++){
                                    var item = SCENARIO[i];
                                    tr = "<option value='" + item.code + "'>" + item.name +"</option>";
                                    $("select[name='scenario']:last").append(tr);
                                }
                                $("select[name='scenario']:last").val("${csInfoItem.value5}").prop("selected", true);
                            </script>
                        </td>
                        <td>
                            <select name='factoryType' class='editSelect'><option value=''>선택</option></select>
                            <script>
                                for(var i = 0; i < FACTORY.length; i++){
                                    var item = FACTORY[i];
                                    tr = "<option value='" + item.code + "'>" + item.name +"</option>";
                                    $("select[name='factoryType']:last").append(tr);
                                }
                                $("select[name='factoryType']:last").val("${csInfoItem.value1}").prop("selected", true);
                            </script>
                        </td>
                        
                        <td>
                            <select name='platingType2' class='editSelect'><option value=''>선택</option></select>
                            <script>
                                for(var i = 0; i < PLATING.length; i++){
                                    var item = PLATING[i];
                                    tr = "<option value='" + item.code + "'>" + item.name +"</option>";
                                    $("select[name='platingType2']:last").append(tr);
                                }
                                $("select[name='platingType2']:last").val("${csInfoItem.value2}").prop("selected", true);
                            </script>
                        </td>
                        <td><input type='text' name='metalScrapCost' class='edit' value='${csInfoItem.value3 }' /></td>
                        <td>
                            <select name=metalSSMUnit class='editSelect'><option value=''>선택</option></select>
                            <script>
                                for(var i = 0; i < MONETARYUNIT.length; i++){
                                    var item = MONETARYUNIT[i];
                                    var option = "<option value='" + item.code + "'>" + item.name +"</option>";
                                    $("select[name='metalSSMUnit']:last").append(option);
                                }
                                $("select[name='metalSSMUnit']:last").val("${csInfoItem.mUnit}").prop("selected", true);
                            </script>
                        </td>
                        </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
		</div>
	</div>
</body>
