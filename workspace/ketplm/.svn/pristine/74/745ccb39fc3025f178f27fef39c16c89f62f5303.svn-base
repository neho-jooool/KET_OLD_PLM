<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>
<script type="text/javascript"
    src="/plm/extcore/js/sample/sampleRequest.js"></script>
    
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script>
//Jquery
$(document).ready(function(){
    
});
    
</script>
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">    
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                        <c:if test="${(sampleRequest.sampleRequestStateCode == 'REQUEST' or sampleRequest.sampleRequestStateCode == 'REREQUEST') and sampleRequest.receptionUpdateFlag == true}">
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                       <td class="btn_blue"
                                                            background="/plm/portal/images/btn_bg1.gif"><a
                                                            href="javascript:sampleRequest.goReceptionModify('${sampleRequest.oid}');"
                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                                                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            </c:if>
                        <c:if test="${sampleRequest.sampleRequestStateCode == 'DISPENSATION' and sampleRequest.createUserFlag == true}">
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                       <td class="btn_blue"
                                                            background="/plm/portal/images/btn_bg1.gif"><a
                                                            href="javascript:sampleRequest.goReceptionModify('${sampleRequest.oid}');"
                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                                                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            </c:if>
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
                    <form id="viewForm" name="viewForm">
                    <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%">
	                    <colgroup>
	                        <col width="90">
	                        <col width="*">
	                    </colgroup>
	                    <tr>
	                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09101") %><%--요청부품--%></td>
	                        <td class="tdwhiteL0">
	                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                               <tr>
	                                   <td class="space5"></td>
	                               </tr>
	                           </table>
	                           <div id="div_scroll3"
	                               style="overflow-x: hidden; overflow-y: auto;"
	                               class="table_border">
	                               <table id="partTable" width="100%" cellpadding="0" cellspacing="0">
	                                   <tbody id="partTableBody">
	                                       <colgroup>
	                                         <col width="90"/>
	                                         <col width="*"/>
	                                         <col width="30"/>
	                                         <col width="90"/>
	                                         <col width="70"/>
	                                         <col width="80"/>
	                                         <col width="70"/>
	                                         <col width="70"/>
	                                         <col width="75"/>
	                                         <col width="75"/>
	                                         <col width="75"/>
	                                      </colgroup>
	                                      <tr>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
	                                          <td class="tdgrayM">Rev</td>
	                                          <td class="tdgrayM">Project No</td>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%>(PM)</td>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09084") %><%--요청수량--%></td>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09107") %><%--불출수량--%></td>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09108") %><%--접수일--%></td>
	                                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09109") %><%--불출예정일--%></td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09085") %><%--불출일--%></td>
	                                     </tr>
	                                     <c:forEach items="${samplePartList}" var="samplePart" varStatus="i">
	                                         <tr>
	                                            <td class="tdwhiteM" align="center"><a href="javascript:openViewPart('${samplePart.partNo}');">${samplePart.partNo}</a></td>
	                                            <td class="tdwhiteM" align="center" title="${samplePart.partName}">
	                                               <div style="width:140px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">
	                                                   ${samplePart.partName}
	                                               </div>
	                                            </td>
	                                            <td class="tdwhiteM" align="center">${samplePart.ver}</td>
	                                            <td class="tdwhiteM" align="center"><a href="javascript:openProject('${samplePart.projectNo}');">${samplePart.projectNo}</a></td>
	                                            <td class="tdwhiteM" align="center">${samplePart.developeStageName}</td>
	                                            <td class="tdwhiteM" align="center">${samplePart.pmUserName}</td>
	                                            <td class="tdwhiteM" align="center">${samplePart.requestCount}EA</td>
	                                            <td class="tdwhiteM" align="center">${samplePart.dispensationCount}EA</td>
	                                            <td class="tdwhiteM" align="center">${samplePart.receptionDate}</td>
	                                            <td class="tdwhiteM" align="center">${samplePart.dispensationExpectDate}</td>
	                                            <td class="tdwhiteM" align="center">${samplePart.dispensationDate}</td>
	                                         </tr>
	                                     </c:forEach>
	                                 </tbody>
	                               </table>
	                           </div>
	                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                               <tr>
	                                   <td class="space5"></td>
	                               </tr>
	                           </table>
	                        </td>
	                    </tr>
                    </table>
                    </form>
                 </td>
            </tr>
        </table>