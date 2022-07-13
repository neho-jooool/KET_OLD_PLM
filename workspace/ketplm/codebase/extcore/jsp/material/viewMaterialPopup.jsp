<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script>
$(document).ready(function(){
});
</script>
<body class="popup-background popup-space">
<form>
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />원재료정보 상세
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            
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
                    <col width="150" />
                    <col width="*" />
                </colgroup>
                <tbody>
                     <tr>
                        <td class="tdblueL">Spec_no</td>
                        <td class="tdwhiteL">${dto.spec_no }</td>
                        <td class="tdblueL">Type</td>
                        <td class="tdwhiteL">${dto.type }</td>
                        <td class="tdblueL">maker</td>
                        <td class="tdwhiteL">${dto.maker }</td>
                    </tr>

                    <tr>
                        <td class="tdblueL">grade</td>
                        <td class="tdwhiteL">${dto.grade }</td>
                        <td class="tdblueL">r_date</td>
                        <td class="tdwhiteL">${dto.createDate }</td>
                        <td class="tdblueL">Up_date</td>
                        <td class="tdwhiteL">${dto.modifyDate }</td>
                    </tr>         
                </tbody>
            </table>
        </div>
        
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr><td class="space20"></td></tr>
        </table>
        <div class="sub-title-02 float-l b-space5">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>관련부품
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="30" />
                    <col width="200" />
                    <col width="400" />
                    <col width="200" />
                    <col width="*" />
                </colgroup>
                <thead>
                    <tr>
                        <td>순서</td>
                        <td>Part No</td>
                        <td>Part Name</td>
                        <td>부품유형</td>
                        <!-- <td>단가(원)</td> -->
                    </tr>
                </thead>
                <tbody id="issuePartList">
                <c:forEach items="${partList }" var="list" varStatus="status" >
                <tr>
                <td class='td top center'>${status.count }</td>
                <td class='td top left'>
                <a href="javascript:openViewPart('${list.partNo }')">${list.partNo }</a>
                </td>
                <td class='td top left'>${list.partName }</td>
                <td class='td top left'>${list.partType }</td>
                <%-- <td class='td top center'><fmt:formatNumber>${list.price }</fmt:formatNumber></td> --%>
                </tr>
                </c:forEach>
                <c:if test="${partList.size() < 1}" >
                <tr><td colspan=5 style="text-align:center; font-family: NanumGothic, '나눔고딕', Nanumgo, Dotum; font-size: 12px;"><br><b>관련 부품이 없습니다.</b><br><br></td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
