<%@page import="ext.ket.project.program.entity.KETProgramSchedule"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="ext.ket.project.program.entity.ProgramDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
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

});
</script>
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
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:Arm.createInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><!-- 저장 --></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            <td width="5px"></td>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><!-- 취소 --></a>
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
                <td width="*" class="tdwhiteL"><a href="javascript:viewHistory('${analysis.analysisRequestOid}')">${analysis.stateName}</a></td>
                <td class="tdblueL">담당자</td>
                <td class="tdwhiteL"></td>
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
                       </br>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%" >
                              <tr>
                                  <td class="tdwhiteL" colspan="3">비고 : ${analysis.analysisComment}</td>
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
        <table id="createTable" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td class="tdblueL" style="width:20%;">해석의뢰 요청서</td>
                <td colspan="3" valign="top" class="tdwhiteL">
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
                <td class="tdblueL">구분</td>
                <td class="tdwhiteL"><ket:select id="analysisDivision" name="analysisDivision" className="fm_jmp" style="width: 70px;" codeType="ANALYSISDIVISION" multiple="multiple" value="${analysis.analysisDivision}"/></td>
                <td class="tdblueL" style="width: 100px;">담당자</td>
                <td class="tdwhiteL">
	                <input type="text" name="approvalUserName" class="txt_field" style="width: 70%" value=${analysis.approvalUserName}>
	                <input type="hidden" name="approvalUser"> 
	                <input type="hidden" name="HisapprovalUserName" value=${analysis.approvalUserName}> 
	                <a href="javascript:SearchUtil.selectOneUser('approvalUser','approvalUserName');">
	                <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
	                <a href="javascript:CommonUtil.deleteValue('approvalUser','approvalUserName');">
	                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                </td>
            </tr>
            <tr>
                <td class="tdblueL">해석내용</td>
                <td colspan="3" class="tdwhiteL">
	                <!-- 이노디터 JS Include Start --> 
	                <script type="text/javascript">
	                    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
	                    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
	                    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
	                    var g_arrSetEditorArea = new Array();
	                    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
	                </script>
	                <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
	                <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>
	                <script type="text/javascript">
	                    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
	                    // Skin 재정의
	                    //g_nSkinNumber = 0;
	                    // 크기, 높이 재정의
	                    g_nEditorWidth = $("#createTable").width()-150;
	                    g_nEditorHeight = 400;
	                </script>
	                <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
	                <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
	                <input type="hidden" name="hdnBackgroundColor" value="" />
	                <input type="hidden" name="hdnBackgroundImage" value="" />
	                <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
	                <!-- 이노디터에서 작성된 내용을 보낼 Form End -->
	                <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
	                <textarea name="webEditorText" rows="0" cols="0" style="display: none"></textarea> <!-- Editor Area Container : Start -->
	                <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
                </td>
            </tr>
            <tr>
                <td class="tdblueL">설계목표</td>
                <td colspan="3" class="tdwhiteL"><textarea name="designSpec" id="designSpec" style="width:100%; height:5em"></textarea></td>
            </tr>
            <tr>
                <td class="tdblueL">추가사항</td>
                <td colspan="3" class="tdwhiteL"><textarea name="requestedTerm" id="requestedTerm" style="width:100%; height:3em"></textarea></td>
            </tr>
        </table>    
        </form>
    </div>
</div>
