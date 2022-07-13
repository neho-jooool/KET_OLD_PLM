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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    
%>
<script language="JavaScript">
<!--

function addSelectedPartAttr(){
	
	try{
		
		var retArry = new Array();

		$("input:checkbox[name='partAttr']").each(function(){
		    if(this.checked && !this.disabled){
                var retObject = new Object();
                retObject.OID = this.value;
                retObject.NAME = this.getAttribute("partAttrName");
                retArry.push(retObject);
            }
        });
		
		if (retArry.length == 0) {
		    alert('<%=messageService.getString("e3ps.message.ket_message", "06127") %><%--추가로 입력된 데이터가 없습니다.--%>');
		    return;
	    }
		
		//window.returnValue = retArry;
		opener.addPartAttrCallBack(retArry);
	    window.close();
	    
	}catch(e){
		alert(e.message);
	}
}

function init(){
	
	// $('input:checkbox').prop('checked', true);

}
--> 
</script>
    <form name="form1">
     <div class="contents-wrap">
        <div class="sub-title b-space20"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "06128") %><%--Attributes--%></div>
        <div class="b-space20" style="text-align:right">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:addSelectedPartAttr();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
        <div>
            <table cellpadding="0" class="table-style-01" summary="">
                <colgroup>
                    <col width="25%" />
                    <col width="25%" />
                    <col width="25%" />
                    <col width="25%" />
                </colgroup>
                <tbody>
                    <c:forEach var="result" items="${resultList}" varStatus="status">
		                <c:if test="${status.index%4==0}" ><tr></c:if>
		                    <td class="l-padding20 bgcol fontb"><span class="r-space7">
		                    <input type="checkbox" class="v-middle" name="partAttr" value="${result.partAttrOid}" 
		                    partAttrName="<c:out value="${result.attrName}" escapeXml="true"/>" 
		                    <c:if test="${result.selectedPartAttr}">checked disabled</c:if> />
		                    </span>${result.attrName}</td>
		                <c:if test="${status.index > 4 && status.index%4==3}" ></tr></c:if>
		            </c:forEach>
		            <c:if test="${fn:length(resultList)%4==0}" ></c:if>
		            <c:if test="${fn:length(resultList)%4==1}" ><td class="l-padding20 bgcol fontb">&nbsp;</td><td class="l-padding20 bgcol fontb">&nbsp;</td><td class="l-padding20 bgcol fontb">&nbsp;</td></tr></c:if>
		            <c:if test="${fn:length(resultList)%4==2}" ><td class="l-padding20 bgcol fontb">&nbsp;</td><td class="l-padding20 bgcol fontb">&nbsp;</td></tr></c:if>
		            <c:if test="${fn:length(resultList)%4==3}" ><td class="l-padding20 bgcol fontb">&nbsp;</td></tr></c:if>
                </tbody>
            </table>
        </div>
    </div>
    </form>