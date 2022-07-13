<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<script>

window.insertFileLine = function(tid) {
    var fileHtml = "<tr style='height:30px;'><td class='tdwhiteM'><a href='#' onclick='javascript:$(this).parent().parent().remove();return false;'><img src='/plm/portal/images/b-minus.png'></a>";
    fileHtml += "<div style='display:none;'><input name='fileSelect' id='fileSelect' type='checkbox' class='chkbox'></div></td>";
    fileHtml += "<td class='tdwhiteL0'><input name='secondaryFiles' id='secondaryFiles' type='file' style='width: 100%;'></td> <input name='fileDescriptions' type='hidden' value='"+tid+"'></tr>";
    var tBodyId = "#iFileTableOld"+tid;
    
    if ( typeof tid == "undefined" || tid == null ) {
        tBodyId = "#iFileTableOld";
    }
    
    $(tBodyId).append(fileHtml);
}


</script>
<style>
.table_border{overflow-x: hidden;width:100%; overflow-y: auto;}
.table_border table{border-collapse:collapse;padding:0;width:100%;border:0;}
</style>
<c:if test="${fn:length(contentList) > 0 or mode ne 'view'}">
<div class="table_border" id="fileDiv${tid}">
	<table>
		<c:if test="${mode ne 'view' }">
		<tr class="headerLock3">
			<td>
				<table>
					<tr>
						<td width="20" class="tdgrayM">
							<a href="#" onclick="javascript:insertFileLine(${tid});return false;">
							<img src="/plm/portal/images/b-plus.png"></a>
						</td>
						
						<td width="" class="tdgrayM0">
							<%=messageService.getString("e3ps.message.ket_message", "02961") %><!-- 파일명 -->
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</c:if>
		<tr>
			<td>
				<table style="width:100%;table-layout:fixed;">
				    <colgroup>
						<c:if test="${mode ne 'view' }">
						   <col width="20">
						</c:if>
						<col width="*">
					</colgroup>
					<tbody id="iFileTableOld${tid}">
					<c:forEach var="content" items="${contentList}">
						<tr style='height:30px;'>
						    <c:if test="${mode ne 'view' }">
							<td class='tdwhiteM'>
								<a href="#" onclick="javascript:$(this).parent().parent().remove();return false;"><img src="/plm/portal/images/b-minus.png"></a>
                            </td>
                            </c:if>
							<td class="tdwhiteL0">
								<input id='secondaryFileOids' name='secondaryFileOids' type='hidden' value='${content.contentoid}' />
								<a href="javascript:PLM_FILE_DOWNLOAD('${content.downURLStr}')">${content.iconURLStr}</a>&nbsp;
								<a href="javascript:PLM_FILE_DOWNLOAD('${content.downURLStr}')">${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</div>
</c:if>
<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>