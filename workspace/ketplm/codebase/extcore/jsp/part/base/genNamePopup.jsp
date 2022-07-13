<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld" %>
<%


%>
<script type="text/javascript">
<!--

$(document).ready(function() {

    <c:if test="${not empty result.lev1NumberCodeType}">
    $('#lev1NumberCodeType').attr("esse","true").attr("esseLabel","<c:out value="${result.lev1NumberCodeTypeName}" escapeXml="true"/>");
    CommonUtil.singleSelect('lev1NumberCodeType', 100);
    </c:if>
    
    <c:if test="${not empty result.lev2NumberCodeType}">
    $('#lev2NumberCodeType').attr("esse","true").attr("esseLabel","<c:out value="${result.lev2NumberCodeTypeName}" escapeXml="true"/>");
    CommonUtil.singleSelect('lev2NumberCodeType', 100);
    </c:if>
    
    <c:if test="${not empty result.lev3NumberCodeType}">
    $('#lev3NumberCodeType').attr("esse","true").attr("esseLabel","<c:out value="${result.lev3NumberCodeTypeName}" escapeXml="true"/>");
    CommonUtil.singleSelect('lev3NumberCodeType', 100);
    </c:if>
    
    <c:if test="${not empty result.lev4NumberCodeType}">
    $('#lev4NumberCodeType').attr("esse","true").attr("esseLabel","<c:out value="${result.lev4NumberCodeTypeName}" escapeXml="true"/>");
    CommonUtil.singleSelect('lev4NumberCodeType', 100);
    </c:if>
    
    <c:if test="${not empty result.lev5NumberCodeType}">
    $('#lev5NumberCodeType').attr("esse","true").attr("esseLabel","<c:out value="${result.lev5NumberCodeTypeName}" escapeXml="true"/>");
    CommonUtil.singleSelect('lev5NumberCodeType', 100);
    </c:if>
    
    
    // 선택시 Opner 값 세팅해 주기
    if(opener){
    	
    	var form = document.forms[0];
    	
    	try{
    		
    		opener.$("[numberCodeTypeUse=Y]").each(function(){
    			var numberCode = this.getAttribute('numberCodeType');
    			//alert(numberCode);
    			try{
	    			if(form.lev1NumberCodeType.getAttribute('numberCodeType') == numberCode){
	    				var opnerObj = eval("opener.document.forms[0]."+this.name);
	    				form.lev1NumberCodeType.selectedIndex = opnerObj.selectedIndex;
	    				$("#lev1NumberCodeType").multiselect('refresh');
	    			}
    			}catch(e){}
    			
    			try{
	    			if(form.lev2NumberCodeType.getAttribute('numberCodeType') == numberCode){
	    				var opnerObj = eval("opener.document.forms[0]."+this.name);
	                    form.lev2NumberCodeType.selectedIndex = opnerObj.selectedIndex;
	                    $("#lev2NumberCodeType").multiselect('refresh');
	    			}
    			}catch(e){}
    			
    			try{
	    			if(form.lev3NumberCodeType.getAttribute('numberCodeType') == numberCode){
	    				var opnerObj = eval("opener.document.forms[0]."+this.name);
	                    form.lev3NumberCodeType.selectedIndex = opnerObj.selectedIndex;
	                    $("#lev3NumberCodeType").multiselect('refresh');
	    			}
    			}catch(e){}
    			
    			try{
	    			if(form.lev4NumberCodeType.getAttribute('numberCodeType') == numberCode){
	    				
	    				var opnerObj = eval("opener.document.forms[0]."+this.name);
	                    form.lev4NumberCodeType.selectedIndex = opnerObj.selectedIndex;
	                    $("#lev4NumberCodeType").multiselect('refresh');
	    			}
    			}catch(e){}

  				try{
	    			if(form.lev5NumberCodeType.getAttribute('numberCodeType') == numberCode){
	    				var opnerObj = eval("opener.document.forms[0]."+this.name);
	                    form.lev5NumberCodeType.selectedIndex = opnerObj.selectedIndex;
	                    $("#lev5NumberCodeType").multiselect('refresh');
	    			}
                }catch(e){}
    	    });
    		
        }catch(e){
        	//alert(e.message);
        }
    }
    
    // 선택시 Opner 값 세팅해 주기
    $("#lev1NumberCodeType").change(function(){
        if($(this).val() == ""){
            
        }else{
            var selValObj = $(this).val();
            var selVal = selValObj + "";
            selVal = selVal.substring(selVal.indexOf('↕')+1);
            var numberCode = this.getAttribute('numberCodeType');
            //alert(selVal);
            //alert(numberCode);
            if(opener){
            	try{
            	opener.setNumberCodeValue(numberCode, selVal);
            	}catch(e){}
            }
        }
    });
    
    $("#lev2NumberCodeType").change(function(){
        if($(this).val() == ""){
            
        }else{
            var selValObj = $(this).val();
            var selVal = selValObj + "";
            selVal = selVal.substring(selVal.indexOf('↕')+1);
            var numberCode = this.getAttribute('numberCodeType');
            //alert(selVal);
            //alert(numberCode);
            if(opener){
                try{
                opener.setNumberCodeValue(numberCode, selVal);
                }catch(e){}
            }
        }
    });
    
    $("#lev3NumberCodeType").change(function(){
        if($(this).val() == ""){
            
        }else{
            var selValObj = $(this).val();
            var selVal = selValObj + "";
            selVal = selVal.substring(selVal.indexOf('↕')+1);
            var numberCode = this.getAttribute('numberCodeType');
            //alert(selVal);
            //alert(numberCode);
            if(opener){
                try{
                opener.setNumberCodeValue(numberCode, selVal);
                }catch(e){}
            }
        }
    });
    
    $("#lev4NumberCodeType").change(function(){
        if($(this).val() == ""){
            
        }else{
            var selValObj = $(this).val();
            var selVal = selValObj + "";
            selVal = selVal.substring(selVal.indexOf('↕')+1);
            var numberCode = this.getAttribute('numberCodeType');
            //alert(selVal);
            //alert(numberCode);
            if(opener){
                try{
                opener.setNumberCodeValue(numberCode, selVal);
                }catch(e){}
            }
        }
    });
    
    $("#lev5NumberCodeType").change(function(){
        if($(this).val() == ""){
            
        }else{
            var selValObj = $(this).val();
            var selVal = selValObj + "";
            selVal = selVal.substring(selVal.indexOf('↕')+1);
            var numberCode = this.getAttribute('numberCodeType');
            //alert(selVal);
            //alert(numberCode);
            if(opener){
                try{
                opener.setNumberCodeValue(numberCode, selVal);
                }catch(e){}
            }
        }
    });
    
});

function saveNaming(){
	 
	if(!CommonUtil.checkEsseOk()){ // => 해당 시점에 alert("'부품명' 항목을 입력해 주십시요.");
	   return;
	}
	
	var lev1Abbr = "";
	var lev2Abbr = "";
	var lev3Abbr = "";
	var lev4Abbr = "";
	var lev5Abbr = "";
    
	<c:if test="${not empty result.lev1NumberCodeType}">
	lev1Abbr = $('#lev1NumberCodeType option:selected').val();
	lev1Abbr = (lev1Abbr && lev1Abbr.length > 0 && lev1Abbr.substring(0,1) != "↕") ?  ""  + lev1Abbr.substring(0,lev1Abbr.indexOf('↕')) : "";
	</c:if>
	
	<c:if test="${not empty result.lev2NumberCodeType}">
	lev2Abbr = $('#lev2NumberCodeType option:selected').val();
	lev2Abbr = (lev2Abbr && lev2Abbr.length > 0 && lev2Abbr.substring(0,1) != "↕") ?  " "  + lev2Abbr.substring(0,lev2Abbr.indexOf('↕')) : "";
	</c:if>
	
	<c:if test="${not empty result.lev3NumberCodeType}">
	lev3Abbr = $('#lev3NumberCodeType option:selected').val();
	lev3Abbr = (lev3Abbr && lev3Abbr.length > 0 && lev3Abbr.substring(0,1) != "↕") ? " "  + lev3Abbr.substring(0,lev3Abbr.indexOf('↕')) : "";
	</c:if>
	
	<c:if test="${not empty result.lev4NumberCodeType}">
	lev4Abbr = $('#lev4NumberCodeType option:selected').val();
	lev4Abbr = (lev4Abbr && lev4Abbr.length > 0 && lev4Abbr.substring(0,1) != "↕") ? " "  + lev4Abbr.substring(0,lev4Abbr.indexOf('↕')) : "";
	</c:if>
	
	<c:if test="${not empty result.lev5NumberCodeType}">
	lev5Abbr = $('#lev5NumberCodeType option:selected').val();
	lev5Abbr = (lev5Abbr && lev5Abbr.length > 0 && lev5Abbr.substring(0,1) != "↕") ? " "  + lev5Abbr.substring(0,lev5Abbr.indexOf('↕')) : "";
	</c:if>
	
	/*
	alert(lev1Abbr);
	alert(lev2Abbr);
	alert(lev3Abbr);
	alert(lev4Abbr);
	alert(lev5Abbr);
	*/

	var returnVal = lev1Abbr + lev2Abbr + lev3Abbr + lev4Abbr + lev5Abbr;
	
	if(opener && opener.setPartName){
		opener.setPartName(returnVal);
		self.close();
	}else{
	    window.returnValue = returnVal;
	    window.close();
	}
}

	-->
</script>
</head>
<form name="namingForm">
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" />부품명 자동생성</div>    
    <div class="b-space10" style="text-align:right">
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:saveNaming();" class="btn_blue">저장</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:self.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <div class="b-space30">
        <table class="table-style-01" cellpadding="0" summary="">
            <colgroup>
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
            </colgroup>
            <tbody>
                <tr>
                    <td colspan="5" class="title">${result.namingName}</td>
                </tr>
                <tr>
                    <td class="title02">${result.lev1NumberCodeTypeName}</td>
                    <td class="title02">${result.lev2NumberCodeTypeName}</td>
                    <td class="title02">${result.lev3NumberCodeTypeName}</td>
                    <td class="title02">${result.lev4NumberCodeTypeName}</td>
                    <td class="title02">${result.lev5NumberCodeTypeName}</td>
                </tr>
                <tr>
                    <td class="center">
                      <c:if test="${not empty result.lev1NumberCodeType}">
                        <ket:select id="lev1NumberCodeType" name="lev1NumberCodeType" className="fm_jmp" style="width: 170px;" useNamingAbbr="true" codeType="${result.lev1NumberCodeType}" multiple="multiple" otherHtml="ketMultiSelect='N' numberCodeType='${result.lev1NumberCodeType}' " />
                      </c:if>
                    </td>
                    <td class="center">
                      <c:if test="${not empty result.lev2NumberCodeType}">
                        <ket:select id="lev2NumberCodeType" name="lev2NumberCodeType" className="fm_jmp" style="width: 170px;" useNamingAbbr="true" codeType="${result.lev2NumberCodeType}" multiple="multiple" otherHtml="ketMultiSelect='N' numberCodeType='${result.lev2NumberCodeType}' " />
                      </c:if>
                    </td>
                    <td class="center">
                      <c:if test="${not empty result.lev3NumberCodeType}">
                        <ket:select id="lev3NumberCodeType" name="lev3NumberCodeType" className="fm_jmp" style="width: 170px;" useNamingAbbr="true" codeType="${result.lev3NumberCodeType}" multiple="multiple" otherHtml="ketMultiSelect='N' numberCodeType='${result.lev3NumberCodeType}' " />
                      </c:if>
                    </td>
                    <td class="center">
                      <c:if test="${not empty result.lev4NumberCodeType}">
                        <ket:select id="lev4NumberCodeType" name="lev4NumberCodeType" className="fm_jmp" style="width: 170px;" useNamingAbbr="true" codeType="${result.lev4NumberCodeType}" multiple="multiple" otherHtml="ketMultiSelect='N' numberCodeType='${result.lev4NumberCodeType}' " />
                      </c:if>
                    </td>
                    <td class="center">
                      <c:if test="${not empty result.lev5NumberCodeType}">
                        <ket:select id="lev5NumberCodeType" name="lev5NumberCodeType" className="fm_jmp" style="width: 170px;" useNamingAbbr="true" codeType="${result.lev5NumberCodeType}" multiple="multiple" otherHtml="ketMultiSelect='N' numberCodeType='${result.lev5NumberCodeType}' " />
                      </c:if>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    
</div>
</form>