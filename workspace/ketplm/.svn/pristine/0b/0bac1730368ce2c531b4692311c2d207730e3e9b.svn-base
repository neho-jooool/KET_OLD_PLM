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
.deleteItem{
	cursor:pointer;
	margin:5px;
}
</style>
<script> 
$(document).ready(function(){
	
	var validate = false;
	var formulaKeys = new Array();
	var numKey = "0123456789";
	var calcKey = ["Divide","Multiply","Subtract","Add","Decimal", ".", "+", "-", "/", "*", "^", "(", ")"];
	var delKey = ["Backspace","Del","Delete"];
	var arrowKey = ["ArrowLeft","ArrowRight","ArrowUp","ArrowDown","Left","Right","Up","Down"];
	
	//입력 가능한 문자인지 체크
	window.isCalcCheck = function(str){
		return (numKey.indexOf(str) >= 0) 
				|| calcKey.contains(str) 
				|| delKey.contains(str)
				|| arrowKey.contains(str);	
	}
	
	//입력된 전체 수식 유효성 체크
	window.fomulaValidateCheck = function(){
		
		var formulaText = $("#formula").val();
		
		try{
			var variableCalc = "";
			var tempCalc = "";
			var cnt = 0;
			
			for(var i = 0; i < formulaText.length; i++){
				var formulaChar = formulaText.charAt(i);
				
				//입력 가능한 문자일 경우 temp 에 추가
				if(isCalcCheck(formulaChar)){
					tempCalc += formulaChar;
					variableCalc += formulaChar;
				//변수일 경우 숫자로 치환 후 temp 에 추가
				}else if(formulaChar == "["){
					tempCalc += "0";
					variableCalc += formulaKeys[cnt];
					cnt++;
				}
			}
			//수식이 오류가 나는지 체크 (오류 발생시 catch로 이동하여 처리)
			var result = eval(tempCalc);
			
			//window.console.log("tempCalc ========= ", tempCalc);
			//window.console.log("result ============ ", result);
			//window.console.log(variableCalc);
			
			$('#message').html('');
			return true;
		}catch(e){
			$('#message').html('올바른 수식이 아닙니다.');
			return false;
		}
	}
	
	//입력 유효성 체크
	$("#formula").keydown(function(e){
		
		//window.console.log(e);
		var formulaText = $("#formula").val();
		var sPosition = $(this).prop("selectionStart");
		var ePosition = $(this).prop("selectionEnd");
		var chkStrB = formulaText.charAt(sPosition - 1);		//현재 포커스의 앞쪽 문자 가져오기
		var chkStrA = formulaText.charAt(sPosition);			//현재 포커스의 뒤쪽 문자 가져오기
		var isPrevent = false;
		
		
		//입력 가능한 문자, 블럭상태인지 체크
		if(!isCalcCheck(e.key) || sPosition != ePosition){
			isPrevent = true;
		//삭제 키입력 체크
		}else if(delKey.contains(e.key)){
			
			var delChar = formulaText.charAt(sPosition);				//삭제될 문자 가져오기
			var formulaS = formulaText.substring(0, sPosition);
			
			if("Backspace" == e.key) {
				delChar = formulaText.charAt(sPosition - 1);			//삭제될 문자 가져오기
				formulaS = formulaText.substring(0, sPosition);
			}
			var formulaE = formulaText.substring(formulaS.length, formulaText.length);
			
			//삭제될 문자가 숫자 또는 연산자가 아닌 변수인 경우 잘라내기 처리
			if(!(numKey.indexOf(delChar) >= 0 || calcKey.contains(delChar))){
				
				var matchCnt = 0;
				for(var i = 0; i < formulaS.length; i++){
					var formulaChar = formulaS.charAt(i);
					if(formulaChar == "["){
						matchCnt++;
					}
				}
				
				if(matchCnt == 0){
					formulaKeys.splice(matchCnt, 1);								//formulaKeys에 담겨있는 변수 키를 index로 구별하여 삭제
				}else{
					formulaKeys.splice(matchCnt - 1, 1);							//formulaKeys에 담겨있는 변수 키를 index로 구별하여 삭제
				}
				
				//Backspace key로 삭제시 substring 처리
				if("Backspace" == e.key) {
					formulaS = formulaS.substring(0, formulaS.lastIndexOf("["));
					
					if((formulaE.indexOf("[") >= 0 && (formulaE.indexOf("[") > formulaE.indexOf("]"))) ||
							(formulaE.indexOf("[") < 0 && formulaE.indexOf("]") >= 0)){
						formulaE = formulaE.substring(formulaE.indexOf("]") + 1, formulaE.length);
					}
					
				//DELETE Key로 삭제시 substring 처리
				}else{
					if(formulaS.lastIndexOf("[") > formulaS.lastIndexOf("]")){
						formulaS = formulaS.substring(0, formulaS.lastIndexOf("["));
					}
					if(formulaE.indexOf("[") <= 0 || formulaE.indexOf("]") == formulaE.length || (formulaE.indexOf("[") > formulaE.indexOf("]"))){
						formulaE = formulaE.substring(formulaE.indexOf("]") + 1, formulaE.length);
					}
				}
				formulaText = formulaS + formulaE;
				$(this).val(formulaText);
				isPrevent = true;
			}
		//괄호입력
		}else if((e.key == "(" && chkStrB != ")") || (e.key == ")" && chkStrB != "(")){
			
		//연산자 반복입력 체크
		}else if(calcKey.contains(e.key) && calcKey.contains(chkStrB) && chkStrB != ")"){
		
			isPrevent = true;
			
		//숫자입력 체크
		}else if(numKey.indexOf(e.key) >= 0){
			//현재 위치의 앞, 뒤 문자가 입력할 수 없는 문자인 경우 입력 불가 처리
			if(!isCalcCheck(chkStrB) || !isCalcCheck(chkStrA)){
				isPrevent = true;
			}
		}else if(calcKey.contains(e.key)){
			
			//현재 위치의 앞, 뒤 문자가 입력할 수 없는 문자인 경우 입력 불가 처리
			if(!isCalcCheck(chkStrB) || !isCalcCheck(chkStrA)){
				isPrevent = true;
			}
			
			//현재 위치의 앞, 뒤 문자가 변수인 경우 연산자 입력 가능 처리
			if(chkStrB == "]" || chkStrA == "["){
				isPrevent = false;
			}
		}
		
		if(isPrevent){
			e.preventDefault();
		}
	});
	
	$("#formula").keyup(function(e){
		fomulaValidateCheck();
		e.preventDefault();
	});
	
	window.addNewFormulaAttribute = function(label, key){
		var formulaText = $('#formula').val();
		var sPosition = $("#formula").prop("selectionStart");
		var ePosition = $("#formula").prop("selectionEnd");
		
		var chkStrB = formulaText.charAt(sPosition - 1);		//현재 포커스의 앞쪽 문자 가져오기
		var chkStrA = formulaText.charAt(sPosition);			//현재 포커스의 뒤쪽 문자 가져오기
		
		if(sPosition != ePosition){
			
		}else if(chkStrB != "" && !calcKey.contains(chkStrB)){
			
		}else if(chkStrA != "" && !calcKey.contains(chkStrA)){
			
		}else{
			var formulaS = formulaText.substring(0, sPosition);
			var formulaE = formulaText.substring(sPosition, formulaText.length);
			
			$('#formula').val(formulaS + label + formulaE);
			
			var results = formulaS.match(/\[/g);
			
			if(results != null && results.length > 0){
				formulaKeys.splice(results.length, 0, key);
			}else{
				formulaKeys.unshift(key);
			}
		}
		
		$('#formula').focus();
		fomulaValidateCheck();
	}
	
	$("#attribute").load("/plm/ext/cost/selectCostAttribute?useType=CALCULATOR", function(){
		
		$("button[data-code]").click(function(e){
			
			var label = "[" + $(this).text() + "]";
			var key = $(this).data("code");
			
			addNewFormulaAttribute(label, key);
			e.preventDefault();
		});
		
		if(opener){
			var row = opener.formulaRow;
			
			if(row != null){
				$("#formula").val(row.formula);
				if(row.formulaKeys != null) formulaKeys = row.formulaKeys.split(",");
				
				setPartTypeList(row);
			}
		}
		
		fomulaValidateCheck();
	});
	
	//드래그 & 드롭 편집 막기
	$('#formula').on('drop', function(e) {
    	e.preventDefault();
	});
	$('#save').click(function(e){
		
		var partType = "";
		var mftFactory1 = "";
		var mftFactory2 = "";
		var partTypeKeys = "";
		var mftFactory1Keys = "";
		var mftFactory2Keys = "";
		
		var isMFTNull = false;
		
		$("#productType tbody tr").each(function(idx){
			var var1 = $(this).find("input[name='partTypeOid']").val();
			var var2 = $(this).find("select[name='mftFactory1']").val();
			var var3 = $(this).find("select[name='mftFactory2']").val();
			/* 
			if(var2 == "" || var3 == null) {
				isMFTNull = true;
				return false;
			}
			 */
			var productType = productTypeData[var1];
			var mftFactoryList = mftFactoryData[var1];
			if(idx != 0){
				partType += "<br>";
				mftFactory1 += "<br>";
				mftFactory2 += "<br>";
				partTypeKeys += "|";
				mftFactory1Keys += "|";
				mftFactory2Keys += "|";
			}
			
			var isMFT1 = false;
			var isMFT2 = false;
			
			for(var i = 0; i < mftFactoryList.length; i++){
				var factory = mftFactoryList[i];
				
				if(var2 != null && var2.indexOf(factory.numberOidLong) >= 0){
					
					if(!isMFT1){
						isMFT1 = true;
					}else{
						mftFactory1 += ",";
						mftFactory1Keys += ",";
					}
					
					mftFactory1 += factory.numberCodeName;
					mftFactory1Keys += factory.numberOidLong;
					
				}
				
				if(var3 != null && var3.indexOf(factory.numberOidLong) >= 0){
					
					if(!isMFT2){
						isMFT2 = true;
					}else{
						mftFactory2 += ",";
						mftFactory2Keys += ",";
					}
					mftFactory2 += factory.numberCodeName;
					mftFactory2Keys += factory.numberOidLong;
				}
			}
			
			partTypeKeys += productType.oid;
			partType += productType.name;
			
			if(idx != 0){
				partType += "&nbsp;";
				mftFactory1 += "&nbsp;";
				mftFactory2 += "&nbsp;";
			}
		});
	    /* 
		if(isMFTNull){
		    alert("생산처를 입력하세요.");
		    return;
		}
		 */
		var typeObject = {
				partTypeName : partType,
				mftFactory1Name : mftFactory1,
				mftFactory2Name : mftFactory2,
				partType : partTypeKeys,
				mftFactory1 : mftFactory1Keys,
				mftFactory2 : mftFactory2Keys
		}
		
		if(fomulaValidateCheck()){
			
			var param = {
					formula : $('#formula').val(),
					formulaKeys : formulaKeys
			}
			
			ajaxCallServer("/plm/ext/cost/checkFormula", param, function(data){
	            
				var isModify = false;
				
	            if(opener){
	                isModify = opener.setPartType(typeObject);
	                if(isModify) opener.addPopupValue($('#formula').val(), data.formulaKeys, 'formula');
	            }
	            
	            if(isModify) self.close();
	        });
			
		}else{
			alert("올바른 수식을 입력하세요.");
		}
	});
	
	//사이즈 조절
	$(window).resize(function(){
		$('body').height($('html').height() - 80);
		$('#attribute').height($('body').height() - 330);
	});
	
	$('body').height($('html').height() - 80);
	$('#attribute').height($('body').height() - 330);
	$('#productTypeBody').sortable({items : 'tr'});
	
});
var productTypeData = new Object();
var mftFactoryData = new Object();
window.addItem = function(productType){
	
	var mftFactory = SearchUtil.selectArrayCostCodeByPartType(productType);
	
	$.each(productType, function(idx){
		
		var partTypeOid = this.oid;
		var tr = "<tr>";
		tr += "<td style='width:25px;'><img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/>";
		tr += "<input type='hidden' name='partTypeOid' value='" + partTypeOid + "' /></td>";
		tr += "<td>" + this.name + "</td>";
		tr += "<td style='padding:0;'><select name='mftFactory1' onchange='onChangeMftFactory(this);' data-typeid='" + partTypeOid + "' class='fm_jmp' style='width:100%;height:100%;' >";
		tr += "<option value=''>선택</option>";
		
		var factoryFilter = new Array();
		for(var i = 0; i < mftFactory.length; i++){
			
			var factory = mftFactory[i];
			if(partTypeOid == factory.partTypeOid){
				
				factoryFilter.push(factory);
				
				if(factory.level == 1){
					tr += "<option value='" + factory.numberOidLong + "'>";
					tr += factory.numberCodeName;
					tr += "</option>";
				}
			}
		}
		
		productTypeData[partTypeOid] = this;
		mftFactoryData[partTypeOid] = factoryFilter;
		
		tr += "</select></td>";
		tr += "<td><select name='mftFactory2' class='fm_jmp' multiple='multiple' style='display:none;'></select></td>";
		tr += "</tr>";
		
		var el = $("#productType tbody").append(tr);
		
		$(el).find("tr:last select[name='mftFactory2']").multiselect({
	    	minWidth : 170,
	        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	    });
	});
	
}

window.onChangeMftFactory = function(obj){
	
	var partTypeOid = $(obj).data("typeid");
	
	var mftFactory1 = $(obj).val();
	var tr = $(obj).parents("tr:first")
	var options = "";
	
	if(mftFactory1 != null && mftFactory1 != ""){
		
		var childFactory = mftFactoryData[partTypeOid];
		
		for(var i = 0; i < childFactory.length; i++){
			var factory = childFactory[i];
			
			if(mftFactory1 == factory.numberParentOid){
				options += "<option value='" + factory.numberOidLong + "'>";
				options += factory.numberCodeName;
				options += "</option>";
			}
		}
	}
	
	$(tr).find("select[name='mftFactory2']").html(options);
	$(tr).find("select[name='mftFactory2']").multiselect('refresh');
}

window.deleteItem = function(obj){
	$(obj).parents("tr:first").remove();
}

window.setPartTypeList = function(row){
	
	var partType = checkNull(row.partType);
	var partTypeName = checkNull(row.partTypeName);
	var mftFactory1 = checkNull(row.mftFactory1);
	var mftFactory1Name = checkNull(row.mftFactory1Name);
	var mftFactory2 = checkNull(row.mftFactory2);
	var mftFactory2Name = checkNull(row.mftFactory2Name);
	
	if(partType){
		var partTypeList = partType.split("|");
		var partTypeNameList = partTypeName.split("<br>");
		var mftFactory1List = mftFactory1.split("|");
		var mftFactory2List = mftFactory2.split("|");

		for(var i = 0; i < partTypeList.length; i++){
			var id = partTypeList[i];
			var name = partTypeNameList.length > i ? partTypeNameList[i] : "";
			var mft1 = mftFactory1List[i].split(",");
			var mft2 = mftFactory2List[i].split(",");
			
			var partTypeObj = new Object();
			partTypeObj.id = id;
			partTypeObj.oid = id;
			partTypeObj.name = name;
			
			addItem([partTypeObj]);
			
			$("#productType tbody tr:last select[name=mftFactory1] option").each(function(){
				var value = $(this).val();
				
				if(mft1.indexOf(value) >= 0){
					$(this).prop("selected", true);
				}
			});
			
			$("#productType tbody tr:last select[name=mftFactory1]").change();
			$("#productType tbody tr:last select[name=mftFactory2] option").each(function(){
				var value = $(this).val();
				
				if(mft2.indexOf(value) >= 0){
					$(this).prop("selected", true);
				}
			});
			
			$("#productType tbody tr:last select[name=mftFactory2]").multiselect("refresh");
		}
	}
}

</script>
<body class="popup-background popup-space">
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />산출수식계산기
		</div>
		<div class="b-space5 float-r" style="text-align: right">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="#" id="save" class="btn_blue">저장</a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<div class="sub-title-02 float-l b-space5">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>부품분류 
			<img src="/plm/portal/images/icon_5.png" style="cursor:pointer;" onclick="SearchUtil.selectCostPartType('addItem','onlyLeaf=Y&openAll=N');" />
		</div>
		<div class="b-space5" >
			<table id="productType" summary="" class="table-style-01" >
				<colgroup>
					<col width="25px">
					<col width="25%">
					<col width="30%">
					<col width="*">
				</colgroup>
				<thead>
					<tr>
						<td class="center bgcol fontb" colspan="2">제품 TYPE</td>
						<td class="center bgcol fontb">생산처1<!-- <span class="red">*</span> --></td>
						<td class="center bgcol fontb">생산처2<!-- <span class="red">*</span> --></td>
					</tr>
				</thead>
				<tbody id="productTypeBody"></tbody>
			</table>
			<!-- <img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/> -->
		</div>
		<div class="sub-title-02 b-space5 float-l">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>산출수식
		</div>
		<div class="float-r">
			<span class="r-space7" id="message" style="color:#FF0000;font-weight:bold;"></span>
		</div>
		<div class="b-space5" >
			<table summary="" class="table-style-01">
				<tbody>
					<tr>
						<td class="center"><textarea name="formula" id="formula"></textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="sub-title-02 float-l b-space5">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>부품속성
		</div>
		<div class="b-space5" >
			<table summary="" style="width:100%;">
				<tbody>
					<tr>
						<td class="center"><div id="attribute"></div></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
