<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
	$(document).ready(function() {
		$(document).attr('title', "회수일정 변경이력");
	});
//-->
</script>
<form>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     <tr><td class="space20"></td>
     </tr>
     </table>
	 <div class="sub-title-02 b-space15 clear">
	   <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>회수일정 변경이력
     </div>

     <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;" border="1">
                <colgroup>
                    <col width="15%" />
                    <col width="15%" />
                    <col width="50%" />
                    <col width="10%" />
                    <col width="10%" />
                </colgroup>
                <tbody>
                <tr>
                    <td class="tdblueM">변경전 일자</td>
                    <td class="tdblueM">변경후 일자</td>
                    <td class="tdblueM">변경사유</td>
                    <td class="tdblueM">등록자</td>
                    <td class="tdblueM">등록일</td>
                </tr>
                <c:forEach items="${imDTO.dateHistoryList}" var="list" varStatus="status">
                    <tr>
                    <td class="tdwhiteM">${list.orgRequestDate}</td>
                    <td class="tdwhiteM">${list.changeRequestDate}</td>
                    <td class="tdwhiteL">${list.changeHistory}</td>
                    <td class="tdwhiteM">${list.worker}</td>
                    <td class="tdwhiteM">${list.createDate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>   
    </div>
	<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>