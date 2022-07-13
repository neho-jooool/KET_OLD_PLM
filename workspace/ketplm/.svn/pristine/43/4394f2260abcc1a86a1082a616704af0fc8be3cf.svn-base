<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<script language="javascript">
$(document).ready(function(){
CommonUtil.multiSelect('distDeptName',120);
});

var drawingDistRequestOid = "${drawingDistRequestOid}";
var fileName = "${fileName}";
function saveReason(){
	/* 요구사항 변경 삭제
	if($('[name="distSubcontractor"]').val() == "" || $('[name="distSubcontractor"]').val() == null){
        if($('[name="distDeptName"]').val() == "" || $('[name="distDeptName"]').val() == null){
            alert("사내배포처와 배포처중 하나는 입력하셔야 합니다.");
            return;
        }
    }
	*/
	
	if($('[name="downloadReason"]').val() == "" || $('[name="downloadReason"]').val() == null){
		alert(LocaleUtil.getMessage('09137'));//"다운로드 사유를 입력하세요.");
        return;
    }
	
	
	
	$.ajax({
	       type       : "POST",
	       url        : "/plm/ext/edm/saveReason.do",
	       data       : $("#reasonForm").serialize(),
	       dataType   : "json",
	       success    : function(data){
	    	   opener.downLoadCallBack(data);
	    	   window.close();  	   
	       },
	       error    : function(xhr, status, error){
	                    alert(xhr+"  "+status+"  "+error);
	                    
	       }
	   });
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09136") %><%--다운로드 사유--%></td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table> 
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	    <td valign="top">    
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
                    <td class="space5"></td>
                </tr>
	            <tr>
	                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
	                <td align="right">
	                    <table border="0" cellspacing="0" cellpadding="0">
	                        <tr>
	                            <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:saveReason();"
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
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
                                                href="javascript:self.close();"
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
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
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	            <tr>
	                <td  class="tab_btm2"></td>
	            </tr>
	        </table>
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <form id="reasonForm" name="reasonForm">
                <input type="hidden" name="drawingDistRequestOid" value="${drawingDistRequestOid}"> 
                <input type="hidden" name="fileName" value="${fileName}"> 
                <col width="120">
                <col width="*">
                <col width="120">
                <col width="*">
                <!-- 요구사항 변경 삭제
                <tr>
                    <td class="tdblueL">사내배포처</td>
                    <td class="tdwhiteL0">
	                    <select onBlur="fm_jmp" id="distDeptName" name="distDeptName" style="width:130px" multiple="multiple">
	                    <c:set var="deptNameArr" value="${fn:split(distDeptName, ',')}" />
	                    <c:forEach var="deptName" items="${deptNameArr}" >
	                        <option value="<c:out value="${deptName}" />"><c:out value="${deptName}" /></option>
	                    </c:forEach>
	                    </select>
                    </td>
                    <td class="tdblueL">배포처</td>
                    <td class="tdwhiteL0"><input type="text" name="distSubcontractor" class="txt_field" style="width:100%" value=""></td>
                </tr>
                 -->
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09136") %><%--다운로드 사유--%><span class="red">*</span></td>
                    <td class="tdwhiteL0" colspan="3"><textarea rows="11" style="width:100%" name="downloadReason"></textarea></td>
                </tr>
              </form>
            </table>
        </td>
    </tr>
</table>
