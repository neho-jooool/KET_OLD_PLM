<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript"
    src="/plm/extcore/js/dqm/dqm.js"></script>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%
String pjtoid = request.getParameter("pjtoid");
if(pjtoid==null) pjtoid = ""; 
%>

<style>
    html {font-size:10px;}
    .iframetab {width:100%;height:650px;border:0px; margin:0px;position:relative;top:0px;}
</style>
<c:if test="${authCheckFlag == false}" >
<script>
$(document).ready(function(){
    alert('<%=messageService.getString("e3ps.message.ket_message", "00990") %>');
    window.close();
});
</script>
</c:if>

<c:if test="${authCheckFlag == true}" >
<script>
//Jquery
var dqmHeaderOid = "${dqm.oid}";
var type = "${type}";
var isIframe = "${isIframe}";
var outputOid = "${outputOid}";

$(document).ready(function(){
    var tab = CommonUtil.tabs('tabs') 
    if(dqmHeaderOid == ""){
    	tab.tabs({disabled: [1,2]});
    }
    
    var curState = "${curState}";
    
    if(type == 'action' || type == 'actionToGrid' || type == 'REVIEW_USER_CHOISE' || type == 'DQM_ACTION' || curState == 'RAISEAPPROVED' || curState == 'ACTIONUNDERREVIEW' || curState == 'ACTIONREJECTED'){
    	changeActiveTab(1);
    	if(!(curState == 'ACTIONAPPROVED' || curState == 'END')){
            tab.tabs({disabled: [2]});
    	}
    } else if(type == 'close' || type == 'closeToGrid' || type == 'DQM_CLOSE' || curState == 'ACTIONAPPROVED' || curState == 'END'){
    	if(curState != 'END'){
    	    changeActiveTab(2);
    	}
    } else{
    	tab.tabs({disabled: [1,2]});
    }
    
    // 결재대상화면에서 제어부분
    if(isIframe == 'true'){
        if(type == 'raise'){
            tab.tabs({disabled: [1,2]});
        }else if(type == 'action'){
            tab.tabs({disabled: [2]});
        }
    }
 
});

//탭 활설화 및 selected
function changeActiveTab(index){
    $("#tabs").tabs({active : index });
}

//탭 disable 시 enable
function changeTabState(index){
    $("#tabs").tabs('enable', index)
}



</script>

<table style="width: 100%; height: 100%; <c:if test="${isIframe == 'true'}" >display: none;</c:if>">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">ISSUE</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table> 
<div id="tabs">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
            <td align="left">
			    <ul>
			        <c:if test="${empty dqm.oid}" >
			          <li><a class="tabref" href="#tabs-1" rel="/plm/ext/dqm/createForm.do?outputOid=${outputOid}&copyOid=${copyOid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "09049") %><%--제기--%>&nbsp;&nbsp;</a></li>
			        </c:if>
			        <c:if test="${not empty dqm.oid}" >
			          <li><a class="tabref" href="#tabs-1" rel="/plm/ext/dqm/viewForm.do?oid=${dqm.oid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "09049") %><%--제기--%>&nbsp;&nbsp;</a></li>
			        </c:if>
			          <c:if test="${type == 'REVIEW_USER_CHOISE'}" >
			             <li><a class="tabref" href="#tabs-2" rel="/plm/ext/dqm/actionCreateForm.do?oid=${dqm.oid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "09050") %><%--검토--%>/<%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%>&nbsp;&nbsp;</a></li>
			          </c:if>
			          <c:if test="${type != 'REVIEW_USER_CHOISE'}" >
                      <li><a class="tabref" href="#tabs-2" rel="/plm/ext/dqm/actionViewForm.do?oid=${dqm.oid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "09050") %><%--검토--%>/<%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%>&nbsp;&nbsp;</a></li>
                      </c:if>
			          <li><a class="tabref" href="#tabs-3" rel="/plm/ext/dqm/closeViewForm.do?oid=${dqm.oid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "09038") %><%--종결--%>&nbsp;&nbsp;</a></li>
			    </ul>
            </td>
            <td align="right">
	            <table border="0" cellspacing="0" cellpadding="0" <c:if test="${isIframe == 'true'}" >style="display: none;"</c:if>>
	                <tr>
	                    <c:if test="${not empty dqm.oid}">
	                    <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                   <td class="btn_blue"
                                       background="/plm/portal/images/btn_bg1.gif"><a
                                       href="javascript:dqm.openTotalPrintView('${dqm.oid}');"
                                       class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09072") %><%--종합프린트--%></a></td>
                                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        </c:if>
	                    <c:if test="${dqm.createUserFlag == true}">
                        <td id="deleteButtonTd" style="display: none">
		                    <table border="0" cellspacing="0" cellpadding="0">
		                        <tr>
		                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
		                                href="javascript:dqm.remove();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
		                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		                        </tr>
		                    </table>
		                </td>
		                <td width="5">&nbsp;</td>
		                </c:if>
		                <td>
	                        <table border="0" cellspacing="0" cellpadding="0">
	                            <td>
	                                <table border="0" cellspacing="0" cellpadding="0">
	                                    <tr>
	                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
	                                            href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
	                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                    </tr>
	                                </table>
	                            </td>
	                       </table>
	                    </td>
	                </tr>
	            </table>
	        </td>
        </tr>
    </table>
    <div id="tabs-1"> 
     <div class="tabIframeWrapper">
            <c:if test="${empty dqm.oid}" >
	          <iframe class="iframetab" id="iframetab1" src="/plm/ext/dqm/createForm.do?pjtoid=<%=pjtoid%>&outputOid=${outputOid}&copyOid=${copyOid}">Load Failed?</iframe>
	        </c:if>
	        <c:if test="${not empty dqm.oid}" >
	          <iframe class="iframetab" id="iframetab1" src="/plm/ext/dqm/viewForm.do?oid=${dqm.oid}">Load Failed?</iframe>
	        </c:if>
    </div>
    </div> 
    <div id="tabs-2"> 
    </div>
    <div id="tabs-3"> 
    </div>
</div>
<form name="updateForm" method="post">
	<input type="hidden" name="oid" value="${dqm.oid}">       
	<input type="hidden" name="raiseOid" value="${dqm.raiseOid}">  
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</c:if>
    