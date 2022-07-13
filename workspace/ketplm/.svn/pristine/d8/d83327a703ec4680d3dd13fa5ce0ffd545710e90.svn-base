<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>

<%
boolean isAdmin = CommonUtil.isAdmin();
%>
<script type="text/javascript"
    src="/plm/extcore/js/dqm/dqm.js?ver=0.2"></script>

<script type="text/javascript">
$(document).ready(function(){
	// 이노디터에서 작성된 내용을 전달
	var curState = "${curState}";
	if(curState != 'RAISEINWORK'){
	    parent.$('#deleteButtonTd').attr('style', 'display:none');
	}
	else{
		parent.$('#deleteButtonTd').attr('style', 'display:block');
	}
    var strContent = document.innoditorDataForm["webEditor"].value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    //var tab = CommonUtil.tabs('tabs');
    //tab.tabs({enable: [2]});
})

var selDownUrl = "";
var oid = "${dqm.oid}";
var raiseOid = "${dqm.raiseOid}";

function requestApprove(requestOid)
{
	if($('[name=closerName]').val() == ""){
		alert("종결담당자가 지정되지 않았습니다.\nRefresh 버튼을 눌러 담당자를 지정하세요.\n\n자동차사업부 : Project의 선행품질보증Role\n전자사업부 : Project의 선행품질보증Role 또는 전자품질관리Role");
		$('#refresh').focus();
		return;
	}
    goPage(requestOid);
}


function allCheck(checkboxName, isChecked) {
    var checkedList = document.getElementsByName(checkboxName);

    for ( var i = 0; i < checkedList.length; i++) {
        checkedList[i].checked = isChecked;
    }
}

function selDownload(){
    //get 방식
    /*
    selDownUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet/selected?oid="+oid;
    var checkedList = document.getElementsByName('fileSelect');

    var checkFlag = false;
    
    for ( var i = 0; i < checkedList.length; i++) {
        if(checkedList[i].checked){
            checkFlag = true;
            selDownUrl += "&soid=OR:" + $('[name=secondaryFileOids]')[i].value;
        }
    }
    if(!checkFlag){
        alert("선택해");
        return;
    }
    openWindow(selDownUrl,"","200","200","scrollbars=no,resizable=no");
    */
    
    //post 방식
    document.getElementById("selDownloadForm").innerHTML = "";
    selDownUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet/selected";
    var checkedList = document.getElementsByName('fileSelect');

    var checkFlag = false;
    var str = "";
    var subStr = "";
    for ( var i = 0; i < checkedList.length; i++) {
        if(checkedList[i].checked){
            checkFlag = true;
            subStr += "<input type='hidden' name='soid' value=\'"+"OR:" + $('[name=secondaryFileOids]')[i].value+"\'/>";
        }
    }
    if(!checkFlag){
        alert("선택해");
        return;
    }
    str +=  "<input type='hidden' name='oid' value=\'"+raiseOid+"\'/>";
    str +=  subStr;
    
    alert(str);
    document.getElementById("selDownloadForm").innerHTML = str;
    
    var multiDownForm = document.selDownloadForm;
    window.open("" ,"selDownloadForm", 
          "toolbar=no, width=200, height=200, directories=no, status=no,    scrollorbars=no, resizable=no"); 
    multiDownForm.action =selDownUrl; 
    multiDownForm.method="post";
    multiDownForm.target="selDownloadForm";
    multiDownForm.submit();
}

</script>
<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${dqm.webEditor}</textarea>

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
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <!-- 
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                   <td class="btn_blue"
                                                       background="/plm/portal/images/btn_bg1.gif"><a
                                                       href="javascript:dqm.openPrintViewPopup('${dqm.oid}');"
                                                       class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03126") %><%--프린트--%></a></td>
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
                                                   <td class="btn_blue"
                                                       background="/plm/portal/images/btn_bg1.gif"><a
                                                       href="javascript:dqm.goCopy('${dqm.oid}');"
                                                       class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00128") %><%--Copy등록--%></a></td>
                                                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        <c:if test="${dqm.raiseOid != '' and dqm.raiseOid != null}">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                   <td class="btn_blue"
                                                       background="/plm/portal/images/btn_bg1.gif"><a
                                                       href="javascript:viewHistory('${dqm.raiseOid}');"
                                                       class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00785") %><%--결재이력--%></a></td>
                                                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
                                        </c:if>
                                        <%if(isAdmin) {%>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                   <td class="btn_blue"
                                                       background="/plm/portal/images/btn_bg1.gif"><a
                                                       href="javascript:dqm.goModify('${dqm.oid}');"
                                                       class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                                                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%} else{ %>
                                        <c:if test="${dqm.dqmStateCode == 'RAISEINWORK' and dqm.createUserFlag == true}">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
		                                           <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                                           <td class="btn_blue"
		                                               background="/plm/portal/images/btn_bg1.gif"><a
		                                               href="javascript:dqm.goModify('${dqm.oid}');"
		                                               class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
		                                           <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                   <td class="btn_blue"
                                                       background="/plm/portal/images/btn_bg1.gif"><a
                                                       href="javascript:requestApprove('${dqm.raiseOid}');"
                                                       class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08003") %><%--결재요청--%></a></td>
                                                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                               </tr>
                                            </table>
                                        </td>
                                        </c:if>
                                        <%} %>
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
                      <td  class="tab_btm2"></td>
                  </tr>
              </table>
              <form name="updateForm" method="post">
              <input type="hidden" name="oid" value="${dqm.oid}">
              <input type="hidden" name="closerName" value="${dqm.closerName}">
              <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed">
	             <colgroup>
	                 <col width="120">
	                 <col width="37%">
	                 <col width="120">
	                 <col width="*">
	              </colgroup>
	              <tr>
	                 <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09001") %><%--품질문제 No--%></td>
	                 <td class="tdwhiteL">${dqm.problemNo}</td>
	                 <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
	                 <td class="tdwhiteL">${dqm.dqmStateName}</td>
	                 <!-- 
	                 <td class="tdblueM">작성</td>
	                 <td class="tdblueM">승인</td>
	                  -->
	             </tr>
	             <tr>
	                 <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09002") %><%--문제내용--%></td>
	                 <td class="tdwhiteL" colspan="3">${dqm.problem}</td>
	                 <!--
	                 <td class="text-center-border">${dqm.raiseCreateUserName}</td>
	                 <td class="text-center-border">${dqm.raiseApprovName}</td>
	                  -->
	             </tr>
	             <tr>
	                 <td class="tdblueL">Project No</td>
	                 <td class="tdwhiteL">
	                 <a href="javascript:openProject('${dqm.pjtno}');">${dqm.pjtno}</a>
	                 <input type="hidden" name="pjtno" value="${dqm.pjtno}">
	                 <input type="hidden" name="pjtoid" value="${dqm.pjtoid}">
	                 </td>
	                 <td class="tdblueL">Project Name</td>
	                 <td class="tdwhiteL">${dqm.pjtname}</td>
	                 <!--
	                 <td class="text-center-border">${dqm.raiseCreateStamp}</td>
	                 <td class="text-center-border">${dqm.raiseApprovDate}</td>
	                  -->
	             </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
                      <td class="tdwhiteL">${dqm.customerName}</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                      <td class="tdwhiteL">${dqm.cartypeName}</td>
                  </tr>
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04630") %><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--문제점구분--%></td>
                    <td class="tdwhiteL">${dqm.issueName}</td>
                    <td class="tdblueL">발생시점</td>
                    <td class="tdwhiteL">${dqm.occurPointName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09003") %><%--불량구분--%></td>
                      <td class="tdwhiteL">${dqm.defectDivName}</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09004") %><%--불량유형--%></td>
                      <td class="tdwhiteL">${dqm.defectTypeName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04126") %>/<%=messageService.getString("e3ps.message.ket_message", "02693") %><%--긴급도/중요도--%></td>
                      <td class="tdwhiteL">${dqm.urgencyName}/${dqm.importanceName}</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09025") %><%--적용부위 1--%></td>
                      <td class="tdwhiteL">${dqm.applyArea1}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09026") %><%--적용부위 2--%></td>
                      <td class="tdwhiteL">${dqm.applyArea2}</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09027") %><%--적용부위 3--%></td>
                      <td class="tdwhiteL">${dqm.applyArea3}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%></td>
                      <td class="tdwhiteL"><a href="javascript:openView('${dqm.relatedPartOid}');">${dqm.relatedPart}</a></td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "06112") %><%--부품분류--%></td>
                      <td class="tdwhiteL">${dqm.partCategoryName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02004") %><%--시리즈--%></td>
                      <td class="tdwhiteL" colspan="3">${dqm.seriesName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07139") %><%--발생구분--%></td>
                      <td class="tdwhiteL">${dqm.occurDivName}</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09028") %><%--발생단계--%></td>
                      <td class="tdwhiteL">${dqm.occurStepName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07137") %><%--발생처--%></td>
                      <td class="tdwhiteL">${dqm.occurName}</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07138") %><%--발생일--%></td>
                      <td class="tdwhiteL">${dqm.occurDate}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09029") %><%--발생장소--%></td>
                      <td class="tdwhiteL">${dqm.occurPlaceName}</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                      <td class="tdwhiteL">${dqm.actionDeptName}</td>
                  </tr>
                  
                  <tr>
                      <td class="tdblueL">완료목표일</td>
                      <td class="tdwhiteL">${dqm.requestDate}</td>
                      <td class="tdblueL">PM</td>
                      <td class="tdwhiteL">${dqm.pmUserName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">작성자</td>
                      <td class="tdwhiteL">${dqm.raiseCreateUserName}</td>
                      <td class="tdblueL">작성일</td>
                      <td class="tdwhiteL">${dqm.raiseCreateStamp}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">승인자</td>
                      <td class="tdwhiteL">${dqm.raiseApprovName}</td>
                      <td class="tdblueL">승인일</td>
                      <td class="tdwhiteL">${dqm.raiseApprovDate}</td>
                  </tr>
                  
                  <tr>
                      <td class="tdblueL">검토자</td>
                      <td class="tdwhiteL">${dqm.actionUserName}</td>
                      <td class="tdblueL">종결담당자</td>
                      <td class="tdwhiteL">${dqm.closerName}</td>
                  </tr>
                  
<%--                   <tbody id="closeTbody">
                  <tr>
                      <td class="tdblueL">종결담당자</td>
                      <c:choose>
                      <c:when test="${dqm.closerName != ''}"><td colspan="3" class="tdwhiteL">${dqm.closerName}</td></c:when>
                      <c:when test="${dqm.closerName == ''}">
                      <td colspan="3" class="tdwhiteL">
                      <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:dqm.setCloser('${dqm.oid}');" class="btn_blue" id="refresh">Refresh</a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                      </table>
                      </c:when>
                      <c:otherwise></c:otherwise>
                      </c:choose>
                      </td>
                  </tr>
                  </tbody> --%>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01746") %><%--상세내용--%></td>
                      <td colspan="3" class="tdwhiteL">
                           <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                              <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                              <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                      </td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                      <td colspan="3" class="tdwhiteL">
                          <!-- 
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                              <tr>
                                  <td class="space5"></td>
                              </tr>
                          </table>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                  <td>&nbsp;</td>
                                  <td align="right"><table border="0" cellspacing="0"
                                          cellpadding="0">
                                          <tr>
                                              <td><table border="0" cellspacing="0" cellpadding="0">
                                                      <tr>
                                                          <td width="10"><img
                                                              src="/plm/portal/images/btn_1.gif"></td>
                                                          <td class="btn_blue"
                                                              background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:selDownload();" class="btn_blue">다운로드</a></td>
                                                          <td width="10"><img
                                                              src="/plm/portal/images/btn_2.gif"></td>
                                                      </tr>
                                                  </table></td>
                                          </tr>
                                      </table></td>
                              </tr>
                          </table>
                          -->
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                              <tr>
                                  <td class="space5"></td>
                              </tr>
                          </table>
                          <div id="div_scroll3"
                              style="overflow-x: hidden; overflow-y: auto;"
                              class="table_border">
                              <table width="100%" cellpadding="0" cellspacing="0">
                                  <tr class="headerLock3">
                                      <td>
                                          <table border="0" cellpadding="0" cellspacing="0"
                                              width="100%" style="table-layout: fixed">
                                              <tr>
                                                  <!-- 
                                                  <td width="40" class="tdgrayM"><input type="checkbox"
                                                      name="chkFileAll" id="checkbox14"
                                                      onclick="javascript:allCheck( 'fileSelect', this.checked);"></td>
                                                  -->
                                                  <td class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                              </tr>
                                          </table>
                                      </td>
                                  </tr>
                                  <table width="100%" cellpadding="0" cellspacing="0"
                                      style="table-layout: fixed">
                                      <tbody id="fileTable">
                                      <c:forEach var="content" items="${secondaryFiles}">
                                                  <tr>
                                                      <!-- 
                                                      <td width="40" class="tdwhiteM">
                                                          <input name='fileSelect' type='checkbox' class='chkbox'>
                                                      </td>
                                                      -->
                                                      <td class="tdwhiteL">
                                                          <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
                                                          <a href="javascript:PLM_FILE_DOWNLOAD2('${content.downURLStr}');">${content.iconURLStr}</a>&nbsp;
                                                          <a href="javascript:PLM_FILE_DOWNLOAD2('${content.downURLStr}');">${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
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
                              </table>
                      </td>
                  </tr>  
              </table>
          </form>
          <form id="selDownloadForm" name="selDownloadForm" method="post">
           </form>
            </td>
        </tr>
    </table>