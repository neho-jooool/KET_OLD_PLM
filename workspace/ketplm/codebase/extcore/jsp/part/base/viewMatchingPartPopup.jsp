<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
$(document).ready(function(){
    $(document).attr('title','매칭 부품');
    //server paging
});
//-->
</script>
<div class="contents-wrap">
    <table style="width: 100%;">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;">
                    <tr>
                        <td width="7"></td>
                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                        <td class="font_01">매칭 부품</td>
                    </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td class="space5"></td>
        </tr>
    </table>
    <div class="b-space5" style="text-align:right">
    <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:self.close()" class="btn_blue">닫기</a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <!-- Content Start -->
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td width="80" class="tdgrayM">부품유형</td>
            <td width="120" class="tdgrayM">부품분류</td>
            <td width="120" class="tdgrayM">부품번호</td>
            <td width="*" class="tdgrayM">부품명</td>
            <td width="60" class="tdgrayM">Rev</td>
            <td width="80" class="tdgrayM">결재상태</td>
        </tr>
        <c:if test="${fn:length(resultList) == 0}">
        <tr>
            <td class="tdwhiteM" colspan="6">관련 부품이 없습니다.</td>
        </tr>
        </c:if>
        <c:forEach var="result" items="${resultList}" varStatus="status">
        <tr>
            <td class="tdwhiteM">${result.matchPartType}</td>
            <td class="tdwhiteL">${result.matchPartClazName}</td>
            <td class="tdwhiteM"><a href="javascript:;" onclick="javascript:openView('${result.matchPartClazOid}');">${result.matchPartNumber}</a></td>
            <td class="tdwhiteL">${result.matchPartName}</td>
            <td class="tdwhiteM">${result.matchPartRev}</td>
            <td class="tdwhiteM">${result.matchPartState}</td>
        </tr>
        </c:forEach>
    </table>
</div>


