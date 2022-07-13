<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/project/template/ajaxProgress.html"%>


<style>
    html {font-size:10px;}
    .iframetab {width:100%;height:650px;border:0px; margin:0px;position:relative;top:0px;}
</style>
<script>
//iframe에서 사용함 
var ecoOid = "${eco_oid}";

//test URL
//http://plmapdev.ket.com/plm/ext/part/classify/actualWeightInputForm.do?actualWeight_part_oid=wt.part.WTPart:895014052&actualWeight_part_oid=wt.part.WTPart:894609795&actualWeight_part_oid=wt.part.WTPart:615154004&actualWeight_part_oid=wt.part.WTPart:100000796944

$(document).ready(function(){
	if($("input[name=partRadio]:checked").val() != "" && $("input[name=partRadio]:checked").val() != null){
	    iframeBom.location.href="/plm/ext/part/classify/actualWeightBomIframeInputForm.do?part_oid="+$("input[name=partRadio]:checked").val();
	}
});

function iframeBomChange(){
	if($("input[name=partRadio]:checked").val() != "" && $("input[name=partRadio]:checked").val() != null){
        iframeBom.location.href="/plm/ext/part/classify/actualWeightBomIframeInputForm.do?part_oid="+$("input[name=partRadio]:checked").val();
    }
}

</script>
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
                            <td class="font_01">실중량 입력</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
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
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03">제품</td>
                    <td align="right" width="5">&nbsp;</td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
	            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                    <td width="40" class="tdgrayM">선택</td>
	                    <td width="150" class="tdgrayM">부품분류</td>
	                    <td width="120" class="tdgrayM">부품번호</td>
	                    <td width="*" class="tdgrayM">부품명</td>
	                    <td width="50" class="tdgrayM">Rev</td>
	                    <td width="100" class="tdgrayM">결재상태</td>
	                    <td width="100" class="tdgrayM">작성자</td>
	                </tr>
	                <c:if test="${fn:length(partHashMapList) == 0 }" > 
		                <tr>
		                   <td align="center" colspan="7">부품이 없습니다.</td>
		                </tr>
		            </c:if>
		            <c:forEach items="${partHashMapList}" var="partHashMap" varStatus="i">
		                <c:if test="${i.index == 0 }" > 
			                <tr>
			                   <td class="tdwhiteM" align="center"><input type="radio" id="partRadio" name="partRadio" value="${partHashMap.part_oid}" checked="checked" onclick="iframeBomChange()"></td>
			                   <td class="tdwhiteM" align="center">${partHashMap.part_claz_namekr}</td>
			                   <td class="tdwhiteM" align="center">${partHashMap.part_no}</td>
			                   <td class="tdwhiteL">${partHashMap.part_name}</td>
			                   <td class="tdwhiteM" align="center">${partHashMap.part_ver}</td>
			                   <td class="tdwhiteM" align="center">${partHashMap.part_state}</td>
			                   <td class="tdwhiteM" align="center">${partHashMap.part_creator_name}</td>
			                </tr>
	                   </c:if>
	                   <c:if test="${i.index != 0 }" > 
                            <tr>
                               <td class="tdwhiteM" align="center"><input type="radio" id="partRadio" name="partRadio" value="${partHashMap.part_oid}" onclick="iframeBomChange()"></td>
                               <td class="tdwhiteM" align="center">${partHashMap.part_claz_namekr}</td>
                               <td class="tdwhiteM" align="center">${partHashMap.part_no}</td>
                               <td class="tdwhiteL">${partHashMap.part_name}</td>
                               <td class="tdwhiteM" align="center">${partHashMap.part_ver}</td>
                               <td class="tdwhiteM" align="center">${partHashMap.part_state}</td>
                               <td class="tdwhiteM" align="center">${partHashMap.part_creator_name}</td>
                            </tr>
                       </c:if>
		            </c:forEach>                                         
	            </table>
            </div>
        </td>
    </tr>
</table>
<iframe id="iframeBom" name="iframeBom" class="iframetab" src="">Load Failed?</iframe>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
    