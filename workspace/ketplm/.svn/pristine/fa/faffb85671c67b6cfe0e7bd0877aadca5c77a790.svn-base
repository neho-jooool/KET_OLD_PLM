<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>
<script>
//Jquery
$(document).ready(function(){
	$('#eco_oid').val(parent.ecoOid); 
	/*
	$('[name=part_sp_net_weight]').keypress(function (e) {
	    var verified = (e.which == 8 || e.which == undefined || e.which == 0) ? null : String.fromCharCode(e.which).match(/[^\.0-9]/);
	    if (verified) {e.preventDefault();}
	});
	
	$('[name=part_sp_total_weight]').keypress(function (e) {
	    var verified = (e.which == 8 || e.which == undefined || e.which == 0) ? null : String.fromCharCode(e.which).match(/[^\.0-9]/);
	    if (verified) {e.preventDefault();}
	});
	*/
});

function lfn_Keychk(obj){
	var val = obj.value;
	var re = /[^0-9|.]/gi;
	obj.value = val.replace(re,'');
	
	var split = val.split(".");
	if(split.length > 2){
		obj.value = val.substr(0,val.length-1);
	}
	if(split[1] != null){
		if(split[1].length > 3){
	        obj.value = val.substr(0,val.length-1);
		}
	}
}

function valCheck(obj){
    var keyinId = obj.id;
    
    var keyIndex = keyinId.substr(5,keyinId.length);
    var keyId = keyinId.substr(0,5);
    
    var spnetVal = parseFloat($('#spnet'+keyIndex).val()) || 0;;
    var totalVal = parseFloat($('#total'+keyIndex).val()) || 0;;
    var spscrabVal = parseFloat($('#spscrab'+keyIndex).val()) || 0;;
    
    if(keyId == "spnet"){
    	if( typeof spscrabVal  == "number" ){
    		$('#total'+keyIndex).val(spnetVal+spscrabVal); 
    	}
    	else{
    		$('#total'+keyIndex).val(spnetVal); 
    	}

    }
    else{
        if( typeof spscrabVal  == "number" ){
        	$('#spnet'+keyIndex).val(totalVal-spscrabVal); 
        }
        else{
            $('#spnet'+keyIndex).val(totalVal); 
        }
    }
    
    /*
    if(parseFloat(totalVal) < parseFloat(spnetVal)){
        obj.value = 0;
        $('#'+keyinId).focus();
        alert("부품중량은 총중량보다 클수 없습니다.");
    }
    
    if ( parseFloat(spscrabVal)) 

    
    $('[name=part_sp_scrab_weight]').val();
    */
}

function save(){
	$('[name=saveForm]').attr('action', '/plm/ext/part/classify/actualWeightBomSave.do');
    $('[name=saveForm]').attr('target', 'download');
    $('[name=saveForm]').submit();
    //showProcessing();
}

function close(){
	if(confirm("창을 닫으시겠습니까?")){
		parent.window.close();
	}
}
</script>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space10"></td>
    </tr>
</table>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03">BOM</td>
                    <td align="right" width="5">&nbsp;</td>
                    <td width="160" align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                    href="javascript:save();" class="btn_blue">저장</a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                <td width="5">&nbsp;</td>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                    href="javascript:close();" class="btn_blue">취소</a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
	            <form id="saveForm" name="saveForm" method="post">
	            <input type="hidden" id="eco_oid" name="eco_oid" value=""/>
	            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                    <td width="40" class="tdgrayM">레벨</td>
	                    <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
	                    <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
	                    <td width="50" class="tdgrayM">Rev</td>
	                    <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04137") %><%--총중량--%></td>
                        <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04138") %><%--부품중량--%></td>
                        <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04139") %><%--스크랩중량--%></td>
	                </tr>
	                <c:if test="${fn:length(partHashMapList) == 0 }" > 
                        <tr>
                           <td align="center" colspan="7">Assy, 단품, 구매품인 부품이 없습니다.</td>
                        </tr>
                    </c:if>
	                <c:forEach items="${partHashMapList}" var="partHashMap" varStatus="i">
                        <tr>
                           <td class="tdwhiteM" align="center">${partHashMap.part_lvl}</td>
                           <td class="tdwhiteM" align="center">${partHashMap.part_no}</td>
                           <td class="tdwhiteL" align="center">${partHashMap.part_name}</td>
                           <td class="tdwhiteM" align="center">${partHashMap.part_ver}</td>
                           <td class="tdwhiteM" align="center"><input id="total${i.index}" name='part_sp_total_weight' type='text' class='txt_field' style='width:98%' onkeyup="lfn_Keychk(this);" onkeypress="lfn_Keychk(this);" onblur="valCheck(this);" onpaste="javascript:return false;" maxlength="18" value='${partHashMap.part_sp_total_weight}'/></td>
                           <td class="tdwhiteM" align="center"><input id="spnet${i.index}" name='part_sp_net_weight' type='text' class='txt_field' style='width:98%'  onkeyup="lfn_Keychk(this);" onkeypress="lfn_Keychk(this);" onblur="valCheck(this);" onpaste="javascript:return false;" maxlength="18" value='${partHashMap.part_sp_net_weight}'/></td>
                           <td class="tdwhiteM" align="center"><input id="spscrab${i.index}" name="part_sp_scrab_weight" type="text" class='txt_fieldRO' style='width:98%' readonly="readonly" value="${partHashMap.part_sp_scrab_weight}"/></td>
                           <input type="hidden" name="part_no" value="${partHashMap.part_no}"/>
                           <input type="hidden" name="part_oid" value="${partHashMap.part_oid}"/>
                        </tr>
                    </c:forEach>                                          
	            </table>
	            </form>
            </div>
        </td>
    </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
    