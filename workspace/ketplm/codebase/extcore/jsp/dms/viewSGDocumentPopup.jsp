<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/dms/sgDocument.js"></script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${dto.oid }"/>
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />시스템 사용설명서 상세화면
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <c:if test="${dto.lastest }">
	            <span class="in-block v-middle r-space7">
	                <span class="pro-table">
	                    <span class="pro-cell b-left"></span>
	                    <span class="pro-cell b-center">
	                        <a href="javascript:sgDocument.reviseSGDocument('${dto.oid }');" class="btn_blue">
	                        <%=messageService.getString("e3ps.message.ket_message", "00684")%><%--개정--%>
	                        </a>
	                    </span>
	                    <span class="pro-cell b-right"></span>
	                </span>
	            </span>
	            <span class="in-block v-middle r-space7">
	                <span class="pro-table">
	                    <span class="pro-cell b-left"></span>
	                    <span class="pro-cell b-center">
	                        <a href="javascript:location.href='/plm/ext/dms/saveSGDocumentPopup?oid=${dto.oid }';" class="btn_blue">
	                        <%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%>
	                        </a>
	                    </span>
	                    <span class="pro-cell b-right"></span>
	                </span>
	            </span>
	            <span class="in-block v-middle r-space7">
	                <span class="pro-table">
	                    <span class="pro-cell b-left"></span>
	                    <span class="pro-cell b-center">
	                        <a href="javascript:sgDocument.deleteSGDocument('${dto.oid }');" class="btn_blue">
	                        <%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%>
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
                            모듈
						</td>
                        <td class="tdwhiteL" >
                            ${dto.moduleDisplay }
                        </td>
                        <td class="tdblueL">
                            관련화면
                        </td>
                        <td class="tdwhiteL" >
                            ${dto.refView }
                        </td>
					</tr>
					<tr>
                        <td class="tdblueL">
                            Rev
                        </td>
                        <td class="tdwhiteL">
                            <a href="javascript:sgDocument.openHistory('${dto.oid }');">
                                ${dto.version }
                            </a>
                        </td>
                        <td class="tdblueL">
                            최종 배포일
                            
                        </td>
                        <td class="tdwhiteL">
                            ${dto.modifyDate }
                        </td>
                    </tr>
					<tr>
					    <td class="tdblueL">
                            문서번호
                        </td>
                        <td class="tdwhiteL" colspan="3">
                            ${dto.docNo }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            문서명
                        </td>
                        <td class="tdwhiteL" colspan="3">
                            ${dto.docName }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            양식설명
                        </td>
                        <td class="tdwhiteL" colspan="3" >
                            <textarea readonly="readonly" class='txt_fieldRO' style="height:70px;width:98%;">${dto.description }</textarea>
                            
                        </td>
                    </tr>
                    <tr>
                    	<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667") %><span class="red">*</span><%-- 주 첨부파일 --%></td>
                        <td colspan="3" class="tdwhiteL">
                            <c:if test="${ not empty dto.primaryDTOFile }">
                            	<a href='${dto.primaryDTOFile.downURLStr}' target='download'>
					                ${dto.primaryDTOFile.iconURLStr} ${dto.primaryDTOFile.name}
				                </a>
				                ( ${dto.primaryDTOFile.fileSizeKB} )
				            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            첨부파일
                        </td>
                        <td colspan="3" class="tdwhiteL">
                            <div id="attachFileDiv"></div>
                            <script>
                            $("#attachFileDiv").load("/plm/ext/common/attachFilesForm?oid=${dto.oid}&mode=view");
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
