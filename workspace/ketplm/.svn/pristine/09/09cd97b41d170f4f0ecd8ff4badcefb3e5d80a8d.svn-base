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

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script language="JavaScript">
<!--
$(document).ready(function() {
	$(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "06114")%><%--Attribute 지정--%>');
    // 체크 박스 모두 해제
    if(document.forms[0].partNamingOid.value == ""){
	    $("input[name=useNaming]").each(function() {
	        $(this).attr("checked", false);
	    });
    }
});

// 저장 버튼 처리
function partNamingReg(){
	
	var form = document.forms[0];
	
	if(form.namingName.value == ""){
		alert("<%=messageService.getString("e3ps.message.ket_message", "06152")%><%--'이름'을 입력해 주십시요.--%>");
		return;
	}

	var checkUseNaming = true;
	$("input[name=useNaming]").each(function() {
       if(this.checked){
    	    checkUseNaming = false;
    	    return false;
       }
    });
	
	if(checkUseNaming){
		alert("<%=messageService.getString("e3ps.message.ket_message", "06153")%><%--'사용여부'를 선택해 주십시요.--%>");
		return;
	}
	
	if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "02463")%><%--저장하시겠습니까?--%>")){
		return;
	}
	
    form.target = "_self";
    form.action =  "/plm/ext/part/naming/savePartNaming.do";
    form.method = "post";
    //form.encoding = 'multipart/form-data';
    disabledAllBtn();
    showProcessing();
    form.submit();

}

// 취소 버튼 처리
function partNamingCancel(){
	
	if(document.forms[0].partNamingOid.value == ""){
		parent.document.all.iframe.src = "/plm/ext/part/naming/viewPartNamingDetailListForm.do";
	}else{
		parent.document.all.iframe.src = "/plm/ext/part/naming/viewPartNamingForm.do?partNamingOid=${result.partNamingOid}";
	}
}
    -->
</script>
<form name="form">
<input type="hidden" name="partNamingOid" value="${result.partNamingOid}" />
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "06142") %><%--품명코드--%> <c:if test="${empty result.partNamingOid}"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%-- 등록 --%></c:if><c:if test="${!empty result.partNamingOid}"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%-- 수정 --%></c:if></div>
    <div class="current-location">
        <span><img src="/plm/portal/images/icon_navi.gif" />Admin<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "06144") %><%--부품명관리--%><img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "06142") %><%--품명코드--%></span>
    </div>
    <div class="b-space10" style="text-align:right">
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:partNamingReg();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:partNamingCancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <div class="b-space30">
        <table class="table-style-01" cellpadding="0" summary="">
            <colgroup>
                <col width="20%" />
                <col width="80%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06147") %><%--이름--%></td>
                    <td class="left"><input type="text" name="namingName" value="${result.namingName}" /></td>
                </tr>
                <c:if test="${!empty result.partNamingOid}">
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06148") %><%--코드--%></td>
                    <td class="left"><input type="hidden" name="namingCode" value="${result.namingCode}" />${result.namingCode}</td>
                </tr>
                </c:if>
                <tr>
                   <td class="title">Level 1</td>
                   <td class="left">
                   <select name="lev1NumberCodeType" >
                       <option value="" ></option>
                       <%
                       PartNamingDTO partNamingDTO = (PartNamingDTO)request.getAttribute("result");
                       String lev1NumberCodeType = partNamingDTO.getLev1NumberCodeType();
                       
                       Kogger.debug("createPartNamingForm", null, null, lev1NumberCodeType);
                       for(int i = 0; i < NumberCodeType.getNumberCodeTypeSet().length; i++) {
                           NumberCodeType numCode = NumberCodeType.getNumberCodeTypeSet()[i];
                           String codType = (numCode.getStringValue()).substring(32);
                       %>
                           <option value="<%=codType%>"
                           <%
                             if( lev1NumberCodeType != null){
                               if(codType.equals(lev1NumberCodeType)){
                                 %> selected<%
                               }
                             }
                           %>
                           ><%=numCode.getComment()%></option>
                       <%
                       }
                       %>
                   </select>
                   </td>
               </tr>
               <tr>
                   <td class="title">Level 2</td>
                   <td class="left">
                   <select name="lev2NumberCodeType" >
                       <option value="" ></option>
                       <%
                       String lev2NumberCodeType = partNamingDTO.getLev2NumberCodeType();
                       Kogger.debug("createPartNamingForm", null, null, lev2NumberCodeType);
                       for(int i = 0; i < NumberCodeType.getNumberCodeTypeSet().length; i++) {
                           NumberCodeType numCode = NumberCodeType.getNumberCodeTypeSet()[i];
                           String codType = (numCode.getStringValue()).substring(32);
                       %>
                           <option value="<%=codType%>"
                           <%
                             if( lev1NumberCodeType != null){
                               if(codType.equals(lev2NumberCodeType)){
                                 %> selected<%
                               }
                             }
                           %>
                           ><%=numCode.getComment()%></option>
                       <%
                       }
                       %>
                   </select>
                   </td>
               </tr>
               <tr>
                   <td class="title">Level 3</td>
                   <td class="left">
                      <select name="lev3NumberCodeType" >
                          <option value="" ></option>
                       <%
                       String lev3NumberCodeType = partNamingDTO.getLev3NumberCodeType();
                       Kogger.debug("createPartNamingForm", null, null, lev3NumberCodeType);
                       for(int i = 0; i < NumberCodeType.getNumberCodeTypeSet().length; i++) {
                           NumberCodeType numCode = NumberCodeType.getNumberCodeTypeSet()[i];
                           String codType = (numCode.getStringValue()).substring(32);
                       %>
                           <option value="<%=codType%>"
                           <%
                             if( lev1NumberCodeType != null){
                               if(codType.equals(lev3NumberCodeType)){
                                 %> selected<%
                               }
                             }
                           %>
                           ><%=numCode.getComment()%></option>
                       <%
                       }
                       %>
                   </select>
                   </td>
               </tr>
               <tr>
                   <td class="title">Level 4</td>
                   <td class="left">
                       <select name="lev4NumberCodeType" >
                           <option value="" ></option>
                       <%
                       String lev4NumberCodeType = partNamingDTO.getLev4NumberCodeType();
                       Kogger.debug("createPartNamingForm", null, null, lev4NumberCodeType);
                       for(int i = 0; i < NumberCodeType.getNumberCodeTypeSet().length; i++) {
                           NumberCodeType numCode = NumberCodeType.getNumberCodeTypeSet()[i];
                           String codType = (numCode.getStringValue()).substring(32);
                       %>
                           <option value="<%=codType%>"
                           <%
                             if( lev1NumberCodeType != null){
                               if(codType.equals(lev4NumberCodeType)){
                                 %> selected<%
                               }
                             }
                           %>
                           ><%=numCode.getComment()%></option>
                       <%
                       }
                       %>
                   </select>
                   </td>
               </tr>
               <tr>
                   <td class="title">Level 5</td>
                   <td class="left">
                       <select name="lev5NumberCodeType" >
                           <option value="" ></option>
                       <%
                       String lev5NumberCodeType = partNamingDTO.getLev5NumberCodeType();
                       Kogger.debug("createPartNamingForm", null, null, lev5NumberCodeType);
                       Kogger.debug("createPartNamingForm", null, null, NumberCodeType.getNumberCodeTypeSet().length);
                       for(int i = 0; i < NumberCodeType.getNumberCodeTypeSet().length; i++) {
                           NumberCodeType numCode = NumberCodeType.getNumberCodeTypeSet()[i];
                           String codType = (numCode.getStringValue()).substring(32);
                       %>
                           <option value="<%=codType%>"
                           <%
                             if( lev1NumberCodeType != null){
                               if(codType.equals(lev5NumberCodeType)){
                                 %> selected<%
                               }
                             }
                           %>
                           ><%=numCode.getComment()%></option>
                       <%
                       }
                       %>
                   </select>
                   </td>
               </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06149") %><%--사용여부--%></td>
                    <td class="left"><input type="radio" name="useNaming" value="true" 
                            <c:if test="${result.useNaming=='true'}">checked="checked"</c:if> ><%=messageService.getString("e3ps.message.ket_message", "06150") %><%--사용--%></input>
                            <input type="radio" name="useNaming" value="false" 
                            <c:if test="${result.useNaming=='false'}">checked="checked"</c:if> ><%=messageService.getString("e3ps.message.ket_message", "06151") %><%--사용안함--%></input></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
</form>
</body>
</html>

