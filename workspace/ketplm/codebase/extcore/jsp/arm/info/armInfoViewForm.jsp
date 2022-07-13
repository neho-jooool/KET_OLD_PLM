<%@page import="ext.ket.project.program.entity.KETProgramSchedule"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="ext.ket.project.program.entity.ProgramDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/arm/arm.js"></script>
<script type="text/javascript" src="/plm/extcore/js/arm/armView.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">
$(document).ready(function(){
    //tab binding
	var tab = CommonUtil.tabs('tabs');
	tab.tabs({ heightStyle: "fill"});
    tab.tabs({active: 1});
    
    CommonUtil.multiSelect('analysisDivision',100);
    
    var partOid = "${analysis.partNo}";
    lfn_setInfosRelatedPart(partOid);
    
    var strContent = document.innoditorDataForm["webEditor"].value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    /////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당   //
    /////////////////////////////////////////////////////////////
    var strBackgroundColor = document.innoditorDataForm["hdnBackgroundColor"].value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.innoditorDataForm["hdnBackgroundImage"].value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = document.innoditorDataForm["hdnBackgroundRepeat"].value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
});
</script>

<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${analysisInfo.webEditor}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>
<input type='hidden' name='oid' value='${analysis.analysisRequestOid}'/>
<c:set var="ssUserName" value="<%=user.getFullName()%>"/>
<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">해석의뢰접수</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>    
<div id="tabs">
    <ul>
        <li><a class="tabref" href="#tabs-1">제품검토의뢰</a></li>
        <li><a class="tabref" href="#tabs-2">제품검토접수</a></li>
        <table border="0" cellspacing="0" cellpadding="0" align="right">
            <tr>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <c:if test="${analysisInfo.state == 'APPROVED'}">
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:Arm.goResultCreate()" class="btn_blue">해석보고서 등록</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>                            
                            </c:if>
                            <c:if test="${(analysisInfo.state == 'INWORK' or analysisInfo.state == 'REWORK') and analysis.approvalUserName == ssUserName}">
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goPage('${analysisInfo.analysisInfoOid}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778")%><%--결재요청--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5px"></td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:Arm.goInfoUpdate();" class="btn_blue">수정</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <c:if test="${analysis.state == 'INWORK'}">
                            <td width="5px"></td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:Arm.removeInfo();" class="btn_blue">삭제</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            </c:if>
                            <td width="5px"></td>
                            </c:if>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:window.close();" class="btn_blue"><!-- %=messageService.getString("e3ps.message.ket_message", "02887") %><--!-- 취소 -->닫기</a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </ul>
    <div id="tabs-1" style="height:500px"><!-- 해석의뢰 -->
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
                <td class="tdwhiteL">${analysis.projectTitle}</td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
                <td class="tdwhiteL">${analysis.customerDepartmentName}</td>
                <td class="tdblueL"  ><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%></td>
                <td class="tdwhiteL" >${analysis.carTypeName}</td>
            </tr>
            <tr>
                <td class="tdblueL">고객담당자</td>
                <td width="*" class="tdwhiteL">${analysis.customerChargeName}</td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                <td class="tdwhiteL">${analysis.processName}</td>
            </tr>
            <tr>
                <td class="tdblueL">용도</td>
                <td width="*" class="tdwhiteL">${analysis.analysisUseName}</td>
                <td class="tdblueL">구분</td>
                <td class="tdwhiteL">${analysis.analysisDivisionName}</td>
            </tr>
            <tr>
                <td class="tdblueL">결재상태</td>
                <td width="*" class="tdwhiteL"><a href="javascript:viewHistory('${analysis.analysisRequestOid}')"><font color='#4398BC' style='cursor:hand'>${analysis.stateName}</font></a></td>
                <td class="tdblueL">담당자</td>
                <td class="tdwhiteL">${analysis.approvalUserName}</td>
            </tr>
            <tr>
                <td class="tdblueL">의뢰일</td>
                <td width="*" class="tdwhiteL">${analysis.startDate}</td>
                <td class="tdblueL">완료일</td>
                <td class="tdwhiteL"></td>
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
                   </div>
                </td>
            </tr>
         </table>
    </div>    
    <div id="tabs-2" style="height:500px"><!-- 해석접수 -->
        <form name="ArmInfoForm" method="post">
        <input type="hidden" name="mOid" value="${analysis.oid}">
        <input type="hidden" name="analysisTitle" value="${analysis.analysisTitle}">
        <input type="hidden" name="analysisRequestNo" value="${analysis.analysisRequestNo}">
        <input type="hidden" name="analysisInfoOid" value="${analysisInfo.analysisInfoOid}">
        <table id="createTable" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td class="tdblueL" style="width:20%;">해석의뢰 요청서</td>
                <td valign="top" class="tdwhiteL">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="tdgrayM">용도</td>
                            <td class="tdgrayM">제목</td>
                            <td class="tdgrayM">ProjectNo</td>
                            <td class="tdgrayM">의뢰자</td>
                            <td class="tdgrayM">의뢰일</td>
                            <td class="tdgrayM">접수일</td>
                        </tr>
                        <tr>
                            <td class="tdwhiteM">${analysis.analysisUseName}</td>
                            <td class="tdwhiteM">${analysis.analysisTitle}</td>
                            <td class="tdwhiteM">${analysis.projectNo}</td>
                            <td class="tdwhiteM">${analysis.ketClientUserName}</td>
                            <td class="tdwhiteM">${analysis.startDate}</td>
                            <td class="tdwhiteM">${analysis.endDate}</td>
                        </tr>                        
                    </table>
                </td>
            </tr>
            <tr>
                <td class="tdblueL">해석보고서</td>
                <td class="tdwhiteL">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="tdgrayM">문서번호</td>
                            <td class="tdgrayM">문서명</td>
                        </tr>
                        ${analysisInfo.viewDocTag}                        
                    </table>
                </td>
            </tr>
            <tr>
                <td class="tdblueL">구분</td>
                <td class="tdwhiteL">${analysis.analysisDivisionName}</td>
            </tr>
            <tr>
                <td class="tdblueL">접수담당자</td>
                <td class="tdwhiteL">${analysis.approvalUserName}</td>
            </tr>
            <tr>
                <td class="tdblueL">결재상태</td>
                <td class="tdwhiteL"><a href="javascript:viewHistory('${analysisInfo.analysisInfoOid}')"><font color='#4398BC' style='cursor:hand'>${analysisInfo.stateName}</font></a></td>
            </tr>
            <tr>
                <td class="tdblueL">해석내용</td>
                <td class="tdwhiteL">
	                 <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                     <div id="divView" class="outline"></div>
                     <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                </td>
            </tr>
            <tr>
                <td class="tdblueL">설계목표</td>
                <td class="tdwhiteL">${analysisInfo.designSpec}</td>
            </tr>
            <tr>
                <td class="tdblueL">추가사항</td>
                <td class="tdwhiteL">${analysisInfo.requestedTerm}</td>
            </tr>
        </table>    
        </form>
    </div>
</div>
