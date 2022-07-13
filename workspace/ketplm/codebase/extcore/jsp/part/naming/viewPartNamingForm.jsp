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
                
<%@page import="e3ps.common.code.NumberCodeType,
                ext.ket.part.entity.dto.*" %>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    
%>
<script language="JavaScript">
<!--
$(document).ready(function() {
	$(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "06114")%><%--Attribute 지정--%>');
    // 체크 박스 모두 해제
    $("input[name=useNaming]:checkbox").each(function() {
        $(this).attr("checked", false);
    });
    
});

// 수정 화면 호출
function partNamingMod(){
	parent.document.all.iframe.src = "/plm/ext/part/naming/createPartNamingForm.do?partNamingOid=${result.partNamingOid}";
}

// 삭제 호출 - 서버에서도 체크함.
function partNamingDel(){
	
	<c:if test="${result.existClazNamingLink}">
    alert("<%=messageService.getString("e3ps.message.ket_message", "06154")%><%--분류체계에서 품명코드를 사용하고 있습니다.\n분류체계에 연결된 품명코드를 제거하여 주십시요.--%>");
    return;
    </c:if>
	
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01707")%><%--삭제하시겠습니까?--%>")){
    	return;
    }
	parent.document.all.iframe.src = "/plm/ext/part/naming/deletePartNamingForm.do?partNamingOid=${result.partNamingOid}";
}
    -->
</script>
</head>

<body class="body-space" onload="">
<form name="form">
<input type="hidden" name="partNamingOid" value="${result.partNamingOid}" />
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "06146") %><%--품명코드 조회--%></div>
    <div class="current-location">
        <span><img src="/plm/portal/images/icon_navi.gif" />Admin<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "06144") %><%--부품명관리--%><img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "06142") %><%--품명코드--%></span>
    </div>
    <div style="text-align:right">
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:partNamingMod();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%-- 수정 --%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:partNamingDel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%-- 삭제 --%></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <p class="b-space7"><span class="red-star"><!-- * --></span>&nbsp; 
    <div class="b-space30">
        <table id="partSpecTbl" class="table-style-01" cellpadding="0" summary="">
            <colgroup>
                <col width="20%" />
                <col width="80%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06147") %><%--이름--%></td>
                    <td class="left">${result.namingName}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06148") %><%--코드--%></td>
                    <td class="left">${result.namingCode}</td>
                </tr>
                <tr>
                    <td class="title">Level1</td>
                    <td class="left">${result.lev1NumberCodeTypeName}</td>
                </tr>
                <tr>
                    <td class="title">Level2</td>
                    <td class="left">${result.lev2NumberCodeTypeName}</td>
                </tr>
                <tr>
                    <td class="title">Level3</td>
                    <td class="left">${result.lev3NumberCodeTypeName}</td>
                </tr>
                <tr>
                    <td class="title">Level4</td>
                    <td class="left">${result.lev4NumberCodeTypeName}</td>
                </tr>
                <tr>
                    <td class="title">Level5</td>
                    <td class="left">${result.lev5NumberCodeTypeName}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06149") %><%--사용여부--%></td>
                    <td class="left"><input type="radio" name="useNaming" value="true" disabled 
                            <c:if test="${result.useNaming=='true'}" >checked="checked"</c:if> ><%=messageService.getString("e3ps.message.ket_message", "06150") %><%--사용--%></input>
                            <input type="radio" name="useNaming" value="false" disabled 
                            <c:if test="${result.useNaming=='false'}">checked="checked"</c:if> ><%=messageService.getString("e3ps.message.ket_message", "06151") %><%--사용안함--%></input></td>
                </tr>
            </tbody>
        </table>
    </div>
    
</div>
</form>
</body>
