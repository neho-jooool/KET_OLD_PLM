<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript"
    src="/plm/extcore/js/sample/sampleRequest.js"></script>
<%@include file="/jsp/project/template/ajaxProgress.html"%>


<style>
    html {font-size:10px;}
    .iframetab {width:100%;height:575px;border:0px; margin:0px;position:relative;top:0px;}
</style>
<script>

$(document).ready(function(){
    var tab = CommonUtil.tabs('tabs') 
    var sampleRequestStateCode = "${sampleRequest.sampleRequestStateCode}";
    var viewType = "${sampleRequest.viewType}";
    
    if(!(sampleRequestStateCode == "REQUEST" || sampleRequestStateCode == "REREQUEST" || sampleRequestStateCode == "DISPENSATION" || sampleRequestStateCode == "COMPLETE")){
        tab.tabs({disabled: [1]});
    }
    
    if(viewType == "partTODO"){
    	changeActiveTab(1);
    }
});

function changeActiveTab(index){
    $("#tabs").tabs({active : index });
}

//Jquery
/*
var dqmHeaderOid = "${sampleRequest.oid}";
var type = "${type}";

$(document).ready(function(){
    var tab = CommonUtil.tabs('tabs') 
    if(dqmHeaderOid == ""){
        tab.tabs({disabled: [1,2]});
    }
    
    if(type == 'action' || type == 'actionToGrid'){
        changeActiveTab(1);
    } else if(type == 'close' || type == 'closeToGrid'){
        changeActiveTab(2);
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
*/


</script>

<%
String isIframe = request.getParameter("isIframe");
%>

<c:set var="isIframe" value="<%=isIframe%>" />

<c:if test="${isIframe != true}">
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09247") %><%--Sample 요청--%></td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table> 
</c:if>
<div id="tabs">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="left">
                <ul>
                    <c:if test="${empty sampleRequest.oid}" >
                        <li><a class="tabref" href="#tabs-1" rel="/plm/ext/sample/sampleRequestCreateForm.do">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02190") %><%--요청--%>&nbsp;&nbsp;</a></li>
                    </c:if>
                    <c:if test="${not empty sampleRequest.oid}" >
                        <li><a class="tabref" href="#tabs-1" rel="/plm/ext/sample/sampleRequestViewForm.do?oid=${sampleRequest.oid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02190") %><%--요청--%>&nbsp;&nbsp;</a></li>
                    </c:if>
                    <c:if test="${sampleRequest.viewType == 'partTODO'}" >
                        <li><a class="tabref" href="#tabs-2" rel="/plm/ext/sample/sampleReceptionUpdateForm.do?oid=${sampleRequest.oid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02505") %><%--접수--%>/<%=messageService.getString("e3ps.message.ket_message", "09103") %><%--불출--%>&nbsp;&nbsp;</a></li>
                    </c:if>
                    <c:if test="${sampleRequest.viewType != 'partTODO'}" >
                        <li><a class="tabref" href="#tabs-2" rel="/plm/ext/sample/sampleReceptionViewForm.do?oid=${sampleRequest.oid}">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02505") %><%--접수--%>/<%=messageService.getString("e3ps.message.ket_message", "09103") %><%--불출--%>&nbsp;&nbsp;</a></li>
                    </c:if>
                </ul>
            </td>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <c:if test="${sampleRequest.sampleRequestStateCode == 'COMPLETE'}">
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
	                            <tr>
	                               <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                               <td class="btn_blue"
	                                    background="/plm/portal/images/btn_bg1.gif"><a
	                                    href="javascript:sampleRequest.goPrint('${sampleRequest.oid}');"
	                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03126") %><%--프린트--%></a></td>
	                               <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                            </tr>
                            </table>
                        </td>
                        </c:if>
                        <td width="5">&nbsp;</td>
                        <td id="deleteButtonTd" style="display: none">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:sampleRequest.remove();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <c:if test="${isIframe != true}">
                        <td width="5">&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
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
                                </tr>
                           </table>
                        </td>
                        </c:if>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <div id="tabs-1"> 
     <div class="tabIframeWrapper">
        <c:if test="${empty sampleRequest.oid}" >
            <iframe class="iframetab" src="/plm/ext/sample/sampleRequestCreateForm.do">Load Failed?</iframe>
        </c:if>
        <c:if test="${not empty sampleRequest.oid}" >
            <iframe class="iframetab" src="/plm/ext/sample/sampleRequestViewForm.do?oid=${sampleRequest.oid}">Load Failed?</iframe>
        </c:if>
     </div>
    </div> 
    <div id="tabs-2"> 
    </div>
    <div id="tabs-3"> 
    </div>
</div>
<form name="updateForm" method="post">
    <input type="hidden" name="oid" value="">       
    <input type="hidden" name="raiseOid" value="">  
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
    