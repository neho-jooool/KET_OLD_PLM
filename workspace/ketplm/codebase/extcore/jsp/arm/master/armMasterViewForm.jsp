<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/arm/arm.js"></script>
<script type="text/javascript" src="/plm/extcore/js/arm/armView.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var partOid = "${analysis.partNo}";
	lfn_setInfosRelatedPart(partOid);
	
	var projectNo = "${analysis.projectNo}";
	lfn_setInfosRelatedProject(projectNo);

	var Usech = "${analysis.analysisUseName}";
	if ( Usech != "기타" )
		$("#analysisCommentTR").hide();
	
});
</script>
<input type='hidden' name='oid' value='${analysis.analysisRequestOid}'/>
<table style="width: 100%; height: 100%; <c:if test="${param.isIframe == true}">display:none;</c:if>">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">해석의뢰 요청서</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>  
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table style="width: 780; border: 0; cellspacing : 0; cellpadding : 0; <c:if test="${param.isIframe == true}">display:none;</c:if>" align="right">
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <c:if test="${(analysis.state == 'INWORK' or analysis.state == 'REWORK') and analysis.createUserFlag == true}">
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goPage('${analysis.analysisRequestOid}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778")%><%--결재요청--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Arm.goUpdate();" class="btn_blue">수정</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <c:if test="${analysis.state == 'INWORK'}">
                                <td width="5">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Arm.remove();" class="btn_blue">삭제</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                </c:if>
                                <td width="5">&nbsp;</td>
                                </c:if>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:window.close();" class="btn_blue">닫기</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <form name="ArmViewForm" method="post">
            <input type="hidden" name="oid" value="${analysis.oid}">
            <input type="hidden" name="analysisRequestNo" value="${analysis.analysisRequestNo}">            
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="tdblueL" width="125">의뢰NO</td>
                        <td width="265" class="tdwhiteL">${analysis.analysisRequestNo}</td>
                        <td class="tdblueL" width="125"><%=messageService.getString("e3ps.message.ket_message", "03104") %><%-- 프로젝트NO --%></td>
                        <td width="265" class="tdwhiteL">${analysis.projectNo}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">의뢰명</td>
                        <td width="*" class="tdwhiteL">${analysis.analysisTitle}</td>
                        <td class="tdblueL" style="width: 110px;">프로젝트명</td>
                        <td class="tdwhiteL" >${analysis.projectTitle}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
                        <td class="tdwhiteL">${analysis.customerDepartmentName}</td>
                        <td class="tdblueL"  ><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%></td>
                        <td class="tdwhiteL" >${analysis.carTypeName}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                        <td class="tdwhiteL">${analysis.processName}</td>
                        <td class="tdblueL">제품구분</td>
                        <td class="tdwhiteL" >${analysis.className}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">용도</td>
                        <td width="*" class="tdwhiteL">${analysis.analysisUseName}</td>
                        <td class="tdblueL">구분</td>
                        <td class="tdwhiteL">${analysis.analysisDivisionName}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">의뢰부서</td>
                        <td class="tdwhiteL">${analysis.ketClientDepartmentName}</td>
                        <td class="tdblueL">의뢰자</td>
                        <td width="*" class="tdwhiteL">${analysis.ketClientUserName}</a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">결재상태</td>
                        <td width="*" class="tdwhiteL"><a href="javascript:viewHistory('${analysis.analysisRequestOid}')">${analysis.stateName}</a></td>
                        <td class="tdblueL">담당자</td>
                        <td class="tdwhiteL">${analysis.approvalUserName}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">의뢰일</td>
                        <td width="*" class="tdwhiteL">${analysis.startDate}</td>
                        <td class="tdblueL">완료일</td>
                        <td class="tdwhiteL">${analysis.endDate}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">해석의뢰 목적</td>
                        <td colspan="3" class="tdwhiteL">${analysis.analysisObjectComment}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">설계목표&설계기준</td>
                        <td colspan="3" class="tdwhiteL">${analysis.analysisTargetComment}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">의뢰부품</td>
                        <td colspan="3">
                           <div style="width: 100%; height: 230px; overflow: auto; overflow:hidden">
                               <table border="0" cellspacing="0" cellpadding="0" width="100%" id="productInfo">
                               <tr>
                                   <td width="20%" class="tdgrayM">Part No</td>
                                   <td width="65%" class="tdgrayM">Part Name</td>
                                   <td width="15%" class="tdgrayM0">Rev</td>
                                </tr>
                               </table>
                               </br>
                               <table border="0" cellspacing="0" cellpadding="0" width="100%" >
                                    <tr>
                                        <td class="tdwhiteL" colspan="3">비고 : ${analysis.analysisComment}</td>
                                     </tr>
                               </table>
                           </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">첨부파일</td>
                        <td colspan="3" class="tdwhiteL0">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                <table width="100%" cellpadding="0" cellspacing="0">
                                    <tr class="headerLock3">
                                        <td>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                                                <tr>
                                                    <td width="265" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>                                    
                                <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                    <tbody id="fileTable">
                                    <c:forEach var="content" items="${secondaryFiles}">
                                        <tr>
	                                        <td width="265" class="tdwhiteL">
	                                            <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
	                                            <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
	                                            <a href='${content.downURLStr}' target='_blank'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
	                                        </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                 </table>
            </form>
        </td>
    </tr>
</table>
