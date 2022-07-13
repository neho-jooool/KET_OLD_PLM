<%@page contentType="text/html; charset=UTF-8"%>
<%@ page
	import="e3ps.common.code.*,
                 e3ps.common.jdf.config.*,
                 e3ps.common.util.WCUtil,
                 e3ps.common.web.HtmlTagUtil,
                 e3ps.part.entity.KETNewPartList,
                 e3ps.common.web.PageControl"%>
<%@page
	import="wt.lifecycle.LifeCycleTemplate,
                wt.fc.*,
                wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.PhaseTemplate,
                java.util.Hashtable"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script language="JavaScript">
<!--
// 
$(document).ready(function() {
    
});

function partNamingReg(){
	parent.document.all.iframe.src = "/plm/ext/part/naming/createPartNamingForm.do?partNamingOid=";
}

function viewDetailNaming(oid){
	parent.document.all.iframe.src = "/plm/ext/part/naming/viewPartNamingForm.do?partNamingOid="+oid;
}

-->
</script>
<form>
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "06142") %><%--품명코드--%></div>
    <div class="current-location">
        <span><img src="/plm/portal/images/icon_navi.gif" />Admin<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "06144") %><%--부품명관리--%><img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "06142") %><%--품명코드--%></span>
    </div>
    <div class="b-space10" style="text-align:right">
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:partNamingReg();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%-- 등록 --%></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <div class="b-space30">
        <table class="table-style-01" cellpadding="0" summary="">
            <colgroup>
                <col width="50%" />
                <col width="50%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="center bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "06147") %><%--이름--%></td>
                    <td class="center bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "06148") %><%--코드<--%></td>
                </tr>
                <c:forEach var="result" items="${resultList}" varStatus="status">
                    <tr>
                        <td class="center"><a href="javascript:viewDetailNaming('${result.partNamingOid}');">${result.namingName}</a></td>
                        <td class="center">${result.namingCode}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</form>