<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/dms/sgDocument.js"></script>
<script>
$(document).ready(function(){
	
	$("select[name=moduleCode]").val("${dto.moduleCode}");
	
	$("select[name=moduleCode]").data("message", "모듈을 선택하세요.");
	$("input[name=refView]").data("message", "관련화면을 입력하세요.");
	$("input[name=name]").data("message", "문서명을 입력하세요.");
	
	if(${!isModify }){
		$("input[name=primaryFile]").data("message", "<%=messageService.getString("e3ps.message.ket_message", "02685") %>"); //주첨부파일을 첨부하여 주십시오.
	}
});
</script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${dto.oid }"/>
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />시스템 사용설명서
            <c:if test="${isModify }">수정</c:if>
            <c:if test="${!isModify }">등록</c:if>
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:sgDocument.saveSGDocument();" class="btn_blue">
                        <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%>
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <c:if test="${isModify }">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:location.href = '/plm/ext/dms/viewSGDocumentPopup?oid=${dto.oid }';" class="btn_blue">
                        <%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%>
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 float-l b-space5">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>기본정보
        </div>
        <div class="b-space5" >
            <table summary="" class="info">
                <colgroup>
                    <col width="150" />
                    <col width="*" />
                    <col width="150" />
                    <col width="*" />
                </colgroup>
                <tbody>
					<tr>
						<td class="tdblueL">
                            모듈<span class="red">*</span>
						</td>
                        <td class="tdwhiteL" >
                            <select name="moduleCode">
                                <option value="">선택</option>
                                <option value="MODULE001">Workspace</option>
                                <option value="MODULE002">Project</option>
                                <option value="MODULE003">금형/구매품</option>
                                <option value="MODULE004">Document</option>
                                <option value="MODULE005">Drawing</option>
                                <option value="MODULE006">Part</option>
                                <option value="MODULE007">ECM</option>
                                <option value="MODULE008">EWS</option>
                                <option value="MODULE009">HelpDesk</option>
                                <option value="MODULE0010">관리메뉴</option>
                                <option value="MODULE0011">DashBoard</option>
                                <option value="MODULE0012">원가관리</option>
                            </select>
                        </td>
                        <td class="tdblueL">
                            관련화면<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL" >
                            <input type="text" name="refView" class="txt_field WP95" value="${dto.refView }">
                        </td>
					</tr>
					<tr>
                        <td class="tdblueL">
                            문서명<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL" colspan="3" >
                            <input type="text" name="docName" class="txt_field" style="width:98%;" value="${dto.docName }">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            양식설명
                        </td>
                        <td class="tdwhiteL" colspan="3" >
                            <textarea name="description" class='txt_field' style="height:70px;width:98%;">${dto.description }</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667") %><span class="red">*</span><%-- 주 첨부파일 --%></td>
                        <td colspan="3" class="tdwhiteL">
                            <c:if test="${ not empty dto.primaryDTOFile }">
					            <a href='${dto.primaryDTOFile.downURLStr}' download target='download'>
					                ${dto.primaryDTOFile.iconURLStr} ${dto.primaryDTOFile.name}
				                </a>
				                ( ${dto.primaryDTOFile.fileSizeKB} )
				            </c:if>
				            <input name="primaryFile" type="file" style="width:100%;height:25px;">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</form>
<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
</body>