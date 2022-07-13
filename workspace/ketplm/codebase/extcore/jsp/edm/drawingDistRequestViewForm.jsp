<%@page import="e3ps.common.util.CommonUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/edm/drawingDistRequest.js"></script>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/edm/ExceptList.jsp"%>
<% boolean isAdmin = CommonUtil.isAdmin(); %>
<script>
var downFlag = "${ketDrawingDistRequest.downFlag}";
var state = "${ketDrawingDistRequest.state}"
var createFlag = "${ketDrawingDistRequest.createUserFlag}";
var isTotalDownload = false;
var publicDownLoadUrl = "";
<c:set var="bAdmin" value="<%=isAdmin %>"/>
//Jquery
$(document).ready(function(){
    // 결재대상화면인 경우 body의 css를 초기화한다.
    if(${isIframe}){
        $('body').removeClass(function(index, css){
            $('body').removeClass(css);
        });
    }
    
	var tab = CommonUtil.tabs('tabs');
    //tab.tabs({disabled: [1]});
	var epmFlag = drawingDistRequest.epmCheckYn();
	var docFlag = drawingDistRequest.docCheckYn();
	
	var str = "";
	if(epmFlag == "Y"){
		str += LocaleUtil.getMessage('01253');//"도면";
	}
	
	if(docFlag == "Y"){
		if(str != ""){
			str += ", ";
		}
        str += LocaleUtil.getMessage('01394');//"문서";
    }
	
	$("#drawingDistDiv").html(str);
	
});


function requestApprove(requestOid)
{
	var goFlag = true;
    $.each($('[name=exceptNo]'), function(i, val){
        exceptno = val.value;
        
        $.each($('[name=drawingDisEpmNoArray]'), function(i, epmval){
            if(epmval.value == exceptno){
            	alert(exceptno+" 도면은 정합성 일치작업이 종료될때까지\n설계팀에 오프라인으로 별도 요청바랍니다.\n\n(정합성 일치 작업 기간 : 2016.07.29 까지)\n\n문의 : 커넥터설계1팀 우승우 수석연구원");
            	goFlag = false;
                return false;
                
            }
        })
        if(!goFlag){
        	return false;
        }
    });
    if(goFlag){
    	goPage(requestOid);
    }
    
}

function downLoadCallBack(rtnVal){
    if(rtnVal == "OK"){
        
    	if(isTotalDownload){
    		drawingDistRequest.totalDownLoad();
    	}else{
    		if(publicDownLoadUrl != ''){
                //window.open(downUrl,"download","width=0, height=0");
                window.open('/plm/ext/pathDownLoad.do?path='+encodeURIComponent(publicDownLoadUrl),"download","width=0, height=0");
            }
            
            //리프레쉬
            setTimeout(function(){location.href='/plm/ext/edm/drawingDistRequestViewForm.do?oid=${ketDrawingDistRequest.oid}';}, 1000);
    	}
        
        
    }
}


function downloadReason(fileName, downUrl){
	if(!(downFlag == "Y" && state == 'APPROVED' &&  createFlag == "true" )){
		alert(LocaleUtil.getMessage('09132'));//"다운로드 권한이 없습니다.");
		return;
	}
	isTotalDownload = false;
	publicDownLoadUrl = downUrl;
	var url = "/plm/ext/edm/downloadReasonForm.do?drawingDistRequestOid=${ketDrawingDistRequest.oid}&fileName="+encodeURIComponent(fileName);
	
	//var rtnVal = window.showModalDialog(url, "downloadReasonPoup", "status=no; scroll=no; dialogWidth=650px; dialogHeight:300px; center:yes");	
    
	getOpenWindow2(url,'downloadReasonPopup', 650, 250);
	
	
}

function totalDownloadReason(){
    if(!(downFlag == "Y" && state == 'APPROVED' &&  createFlag == "true" )){
        alert(LocaleUtil.getMessage('09132'));//"다운로드 권한이 없습니다.");
        return;
    }
    isTotalDownload = true;
    publicDownLoadUrl = "";
	var filename = encodeURIComponent("일괄 다운로드");
    var url = "/plm/ext/edm/downloadReasonForm.do?drawingDistRequestOid=${ketDrawingDistRequest.oid}&fileName="+filename;
    
    //var rtnVal = window.showModalDialog(url, "downloadReasonPoup", "status=no; scroll=no; dialogWidth=650px; dialogHeight:300px; center:yes");  
    
    getOpenWindow2(url,'downloadReasonPopup', 650, 250);
    
}

function viewECO(ecoId){
	var url = "";
	var name = "ViewECO";
    if(ecoId.indexOf('KETProdChangeOrder') > 0) {
		url = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid="+ecoId;
		name += 'PROD';
    } else {
    	url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid="+ecoId;
    	name += 'MOLD';
    }
	
    var opt = launchCenter(1024, 768);
    opt += ", resizable=yes";
    var windowWin = window.open(url,name,opt);
    
    windowWin.focus();
}

//문서 상세조회 팝업
function viewDocPop(docOid){
    var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid=" + docOid;
    openWindow(url,"","820","640","status=yes,scrollbars=no,resizable=no");
}

// 도면 상세조회 팝업
function viewDwgPop(dwgOid){
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + dwgOid;
    openWindow(url,"","820","800","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}
    
</script>
<table style="width: 100%; height: 100%;">
  <tr>
    <td valign="top">
      <table style="width: 100%; <c:if test="${isIframe == 'true'}" >display: none;</c:if>">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table style="height: 28px;">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03457") %><%--배포요청서--%></td>
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
    <li><a class="tabref" href="#tabs-1"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></li>
    <li><a class="tabref" href="#tabs-2"><%=messageService.getString("e3ps.message.ket_message", "09127") %><%--다운로드 이력--%></a></li>
  </ul>
  <div id="tabs-1">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
              <td align="right">
                <table border="0" cellspacing="0" cellpadding="0" style="<c:if test="${isIframe == 'true'}" >display: none;</c:if>">
                  <tr>
                    <c:if test="${((ketDrawingDistRequest.state == 'INWORK' or ketDrawingDistRequest.state == 'REWORK') and ketDrawingDistRequest.createUserFlag == true) or bAdmin == true}">
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:drawingDistRequest.goModify('${ketDrawingDistRequest.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:requestApprove('${ketDrawingDistRequest.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08003") %><%--결재요청--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:viewHistory('${ketDrawingDistRequest.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00785") %><%--결재이력--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                    </c:if>
                    <c:if test="${ketDrawingDistRequest.state == 'APPROVED' and ketDrawingDistRequest.createUserFlag == true }">
                      <c:if test="${ketDrawingDistRequest.downFlag == 'Y'}">
                        <!-- 
                        <td>
                          <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:drawingDistRequest.reApproved();" class="btn_blue">재승인(TEST용)</a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table>
                        </td>
                        <td width="5">&nbsp;</td>
                         -->
                        <td>
                          <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:totalDownloadReason();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09133") %><%--일괄다운로드--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table>
                        </td>
                        <td width="5">&nbsp;</td>
                      </c:if>
                    </c:if>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
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
          <form name="viewForm" method="post">
            <input type="hidden" name="oid" value="${ketDrawingDistRequest.oid}">
            <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;f">
              <col width="110">
              <col width="*">
              <col width="110">
              <col width="*">
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td class="tdwhiteL0" colspan="3">${ketDrawingDistRequest.title}</td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09113") %><%--사내배포처--%></td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.distDeptName}</td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09114") %><%--외주배포처--%></td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.distSubcontractor}</td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09112") %><%--배포유형--%></td>
                <td class="tdwhiteL0"><ket:codeValueByCode codeType="DISTTYPE" code="${ketDrawingDistRequest.distType}" /></td>
                <td class="tdblueL">EO No</td>
                <td class="tdwhiteL0"><c:forEach var="drawingDistEoInfo" items="${ketDrawingDistRequest.drawingDistEoInfoList}">
                    <a style="color: #4398bc;" href="#" onclick="viewECO('${drawingDistEoInfo.drawingDistEoOid}');">${drawingDistEoInfo.drawingDistEoNo}</a>&nbsp
                                    </c:forEach></td>
              </tr>
              <tr>
                <td class="tdblueL">작성부서</td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.createDeptName}</td>
                <td class="tdblueL">다운로드 기한</td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.downloadExpireDate}</td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09128") %><%--배포포멧--%></td>
                <td class="tdwhiteL0"><ket:codeValueByCode codeType="DRAWINGDISTFILETYPE" code="${ketDrawingDistRequest.drawingDistFileTypeArray}" /></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09129") %><%--배포구분--%></td>
                <td class="tdwhiteL0" id="drawingDistDiv"></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.creator}</td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.createStamp}</td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.approver}</td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01998") %><%--승인일--%></td>
                <td class="tdwhiteL0">${ketDrawingDistRequest.approveStamp}</td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09130") %><%--배포도면--%></td>
                <td class="tdwhiteL0" colspan="3">
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
                              <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                              <td width="30" class="tdgrayM">Rev</td>
                              <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                              <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></td>
                              <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                              <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                              <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                              <td width="90" class="tdgrayM">Sheet</td>
                              <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02957") %><%--파일--%></td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                        <tbody id="drawingTable">
                          <c:forEach items="${distReqEpmDocList}" var="distReqEpmDoc" varStatus="i">
                            <tr>
                              <td width="130" class="tdwhiteM" align="center"><a style="color: #4398bc;" href="javascript:viewDwgPop('${distReqEpmDoc.drawingOid}');">${distReqEpmDoc.drawingNo}</a></td>
                              <td width="30" class="tdwhiteM" align="center">${distReqEpmDoc.ver}</td>
                              <td width="*" class="tdwhiteM" style="text-align: left;">&nbsp;${distReqEpmDoc.drawingName}</td>
                              <td width="110" class="tdwhiteM" align="center">${distReqEpmDoc.cadType}</td>
                              <td width="60" class="tdwhiteM" align="center">${distReqEpmDoc.creator}</td>
                              <td width="80" class="tdwhiteM" align="center">${distReqEpmDoc.createDate}</td>
                              <td width="80" class="tdwhiteM" align="center">${distReqEpmDoc.status}<input type='hidden' name='drawingDisEpmArray' value='${distReqEpmDoc.drawingOid}' /></td>
                              <input type='hidden' name='drawingDisEpmNoArray' value='${distReqEpmDoc.drawingNo}' />
                              <td width="90" class="tdwhiteM" align="center">${distReqEpmDoc.sheetType}</td>
                              <c:if test="${ketDrawingDistRequest.downFlag == 'N'}">
                                <td width="100" class="tdwhiteM" align="center"></td>
                              </c:if>
                              <c:if test="${ketDrawingDistRequest.downFlag == 'Y'}">
                                <td width="100" class="tdwhiteM" align="center">
                                    <c:forEach items="${distReqEpmDoc.epmDocumentFileList}" var="epmDocumentFile" varStatus="j">
                                        ${epmDocumentFile.iconURLStr}
                                    </c:forEach>
                                    <!-- 
                                        <a style="color: #4398bc;" href="javascript:downloadReason('${distReqWtDoc.fileName}');">${distReqWtDoc.extension}</a>
                                     -->
                                </td>
                              </c:if>
                              <!-- 
                              <td width="100" class="tdwhiteM" align="center">${distReqEpmDoc.drawingNo}<input type='hidden' name='drawingDisEpmArray' value='${distReqEpmDoc.drawingOid}' /></td>
                              -->
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
                      <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09131") %><%--배포문서--%></td>
                        <td class="tdwhiteL0" colspan="3">
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
                                      <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                                      <td width="30" class="tdgrayM">Rev</td>
                                      <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                                      <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                                      <td width="100" class="tdgrayM">Project No</td>
                                      <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                      <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                                      <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                                      <td width="100" class="tdgrayM">파일</td>
                                    </tr>
                                  </table>
                                </td>
                              </tr>
                              <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                <tbody id="documentTable">
                                  <c:forEach items="${distReqWtDocList}" var="distReqWtDoc" varStatus="i">
                                    <tr>
                                      <td width="100" class="tdwhiteM" align="center"><a style="color: #4398bc;" href="javascript:viewDocPop('${distReqWtDoc.oid}');">${distReqWtDoc.documentNo}</a></td>
                                      <td width="30" class="tdwhiteM" align="center">${distReqWtDoc.ver}</td>
                                      <td width="*" class="tdwhiteM" style="text-align: left;">&nbsp;${distReqWtDoc.title}</td>
                                      <td width="110" class="tdwhiteM" align="center">${distReqWtDoc.categoryName}</td>
                                      <td width="100" class="tdwhiteM" align="center">
                                        <c:set var="pjt_array" value="${fn:split(distReqWtDoc.pjtNo,', ')}"/>
                                        <c:forEach items="${pjt_array}" var="pjt" varStatus="j">
                                            <a style="color: #4398bc;" href="javascript:openProject('${pjt}');">${pjt}</a>
                                            <c:if test="${j.index+1 != fn:length(pjt_array)}">
                                            ,&nbsp;
                                            </c:if>
                                        </c:forEach>
                                      </td>
                                      <td width="60" class="tdwhiteM" align="center">${distReqWtDoc.creatorName}</td>
                                      <td width="80" class="tdwhiteM" align="center">${distReqWtDoc.createDate}</td>
                                      <td width="80" class="tdwhiteM" align="center">${distReqWtDoc.state}<input type='hidden' name='drawingDistDocArray' value='${distReqWtDoc.oid}' /></td>
                                      <c:if test="${ketDrawingDistRequest.downFlag == 'N'}">
                                        <td width="100" class="tdwhiteM" align="center"></td>
                                      </c:if>
                                      <c:if test="${ketDrawingDistRequest.downFlag == 'Y'}">
                                        <td width="100" class="tdwhiteM" align="center">
	                                        <c:forEach items="${distReqWtDoc.docDocumentFileList}" var="docDocumentFile" varStatus="j">
		                                        ${docDocumentFile.iconURLStr}
		                                    </c:forEach>
                                        </td>
                                      </c:if>
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
                              <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09115") %><%--배포사유--%></td>
                                <td class="tdwhiteL0" colspan="3">${ketDrawingDistRequest.distReason}</td>
                              </tr>
                            </table>
                            </form>
                        </td>
                      </tr>
                    </table>
                  <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
                  </div>
                  <div id="tabs-2">
                    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td valign="top">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
	                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
	                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "09127") %><%--다운로드 이력--%></td>
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
                          <table id="drawingDownHisTable" border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border">
                            <tr>
                              <td width="30" class="tdgrayM">NO</td>
                              <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01673") %><%--사용자--%></td>
                              <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09134") %><%--일시--%></td>
                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                              <!-- 요구사항 변경 삭제
                              <td class="tdgrayM">배포처</td>
                               -->
                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09136") %><%--다운로드 사유--%></td>
                            </tr>
                            <c:if test="${fn:length(drawingDownHisList) == 0 }">
                              <tr>
                                <td align="center" colspan="5"><%=messageService.getString("e3ps.message.ket_message", "09135") %><%--다운로드 이력이 없습니다.--%></td>
                              </tr>
                            </c:if>
                            <c:forEach items="${drawingDownHisList}" var="drawingDownHis" varStatus="i">
                              <tr>
                                <td class="tdwhiteM" align="center">${i.index+1}</td>
                                <td class="tdwhiteM" align="center">${drawingDownHis.creator}</td>
                                <td class="tdwhiteM" align="center">${drawingDownHis.downloadDate}</td>
                                <td class="tdwhiteM" align="center">${drawingDownHis.downloadFileName}</td>
                                <!-- 요구사항 변경 삭제
                                <td class="tdwhiteM" align="center">${drawingDownHis.distSubContractor}</td>
                                -->
                                <td class="tdwhiteM" align="center">${drawingDownHis.downloadReason}</td>
                              </tr>
                            </c:forEach>
                          </table>
                          <c:forEach items="${exceptList}" var="except" varStatus="i">
                          <input type="hidden" name="exceptNo" value="${except}">
                          </c:forEach>
                        </td>
                      </tr>
                    </table>
                  </div>
                  </div>