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
    src="/plm/extcore/js/dqm/dqm.js?ver=0.4"></script>
<script type="text/javascript">
$(document).ready(function(){
    // 이노디터에서 작성된 내용을 전달
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
    
    
    
    var strContent2 = document.innoditorDataForm2["webEditor2"].value;

    var objView2 = document.getElementById("divView2");
    objView2.innerHTML = strContent2;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor2 = document.innoditorDataForm2["hdnBackgroundColor2"].value;
    if("" != strBackgroundColor2)
    {
        objView2.style.backgroundColor = strBackgroundColor2;
    }

    var strBackgroundImage2 = document.innoditorDataForm2["hdnBackgroundImage2"].value;
    if("" != strBackgroundImage2)
    {
        var strCopyBackgroundImage2 = strBackgroundImage2.toUpperCase();

        if("none" == strCopyBackgroundImage2)
        {
            objView2.style.backgroundImage = strBackgroundImage2;
        }
        else
        {
            objView2.style.backgroundImage = "url(" + strBackgroundImage2 + ")";
        }
    }

    var strBackgroundRepeat2 = document.innoditorDataForm2["hdnBackgroundRepeat2"].value;
    if("" != strBackgroundRepeat2)
    {
        objView2.style.backgroundRepeat = strBackgroundRepeat2;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
})

var selDownUrl = "";
var oid = "${dqm.oid}";

function goModify(){
    location.href="/plm/ext/dqm/updateForm.do?oid=${dqm.oid}&raiseOid=${dqm.raiseOid}";
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
    str +=  "<input type='hidden' name='oid' value=\'"+oid+"\'/>";
    str +=  subStr;
    
    document.getElementById("selDownloadForm").innerHTML = str;
    
    var multiDownForm = document.selDownloadForm;
    window.open("" ,"selDownloadForm", 
          "toolbar=no, width=200, height=200, directories=no, status=no,    scrollorbars=no, resizable=no"); 
    multiDownForm.action =selDownUrl; 
    multiDownForm.method="post";
    multiDownForm.target="selDownloadForm";
    multiDownForm.submit();
}


function rejectReasonPopup(oid){
    var url = "/plm/ext/dqm/rejectReasonForm.do?oid="+oid;
    
    var rtnVal = window.showModalDialog(url, "rejectReasonPoup", "status=no; scroll=no; dialogWidth=650px; dialogHeight:390px; center:yes");  
    
    //var rtnVal = getOpenWindow2(url,'downloadReasonPopup', 650, 250);
    
    if(rtnVal == "OK"){
        //리프레쉬
        alert('<%=messageService.getString("e3ps.message.ket_message", "09058") %><%--기각 처리되었습니다.--%>');
        var flag = false;
        try {
            parent.opener.dqm.search();
            flag = true;
        }
        catch (e) {
            // TODO: handle exception
        }
        
        try { 
            if(!flag){
                parent.opener.location.reload();
            }
       } catch(error) {
           
       }
        parent.location.href = '/plm/ext/dqm/dqmMainForm.do?type=closeToGrid&oid=${dqm.oid}';
    }
}

function requestApprove(requestOid)
{
    goPage(requestOid);
}


</script>
<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${dqm.causeWebEditor}</textarea>

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

<form name="innoditorDataForm2" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor2" rows="0" cols="0" style="display: none">${dqm.improveWebEditor}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat2" value="" />
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
	                           <!-- 
	                            <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif">
                                                    <a href="javascript:dqm.openPrintActionViewPopup('${dqm.oid}');" class="btn_blue">프린트</a>
                                                   </td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="5">&nbsp;</td>  
	                            -->
	                            <c:if test="${dqm.actionOid != '' and dqm.actionOid != null}" >
	                                   <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:viewHistory('${dqm.actionOid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00785") %><%--결재이력--%></a>
                                                           </td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
	                            </c:if>
	                            <% if(isAdmin) { %>
	                            <td>
                                  <table border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                          <td class="btn_blue"
                                              background="/plm/portal/images/btn_bg1.gif">
                                                  <a href="javascript:dqm.goActionModify('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a>
                                                 </td>
                                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                      </tr>
                                  </table>
                                 </td>
	                            <%} else { %>
			                    <c:if test="${dqm.dqmStateCode == 'RAISEAPPROVED' || dqm.dqmStateCode == 'ACTIONINWORK' || dqm.dqmStateCode == 'ACTIONPROGRESS'}" >
				                    <c:if test="${empty dqm.actionOid and (dqm.actionUserFlag == true or todoUserFlag == true)}" >
<%-- 		                                <c:if test="${dqm.pmUserFlag == true}" >
		                                <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:rejectReasonPopup('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04101") %>기각</a>
                                                           </td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
                                        </c:if> --%>
		                                <td>
		                                    <table border="0" cellspacing="0" cellpadding="0">
		                                        <tr>
		                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                                            <td class="btn_blue"
		                                                background="/plm/portal/images/btn_bg1.gif">
		                                                    <a href="javascript:dqm.goActionCreate('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a>
		                                                   </td>
		                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		                                        </tr>
		                                    </table>
		                                </td>
	                                </c:if>
                                    <c:if test="${not empty dqm.actionOid and (dqm.actionUserFlag == true or todoUserFlag == true)}" >
<%--                                         <c:if test="${dqm.pmUserFlag == true}" >
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:rejectReasonPopup('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04101") %>기각</a>
                                                           </td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
                                        </c:if> --%>
                                        <c:if test="${dqm.dqmStateCode == 'ACTIONPROGRESS'}" >
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:dqm.actionComplete();" class="btn_blue">진행완료</a>
                                                           </td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
                                        </c:if>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                           <a href="javascript:dqm.goActionModify('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a>
                                                           </td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
                                        <!-- 
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:dqm.openCreateECR('${dqm.oid}');" class="btn_blue">ECR 등록</a>
                                                           </td>
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
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:dqm.openCreateECO('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09069") %><%--ECO 등록--%></a>
                                                           </td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <c:if test="${dqm.dqmStateCode != 'ACTIONPROGRESS'}" >
                                        <td width="5">&nbsp;</td>  
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:requestApprove('${dqm.actionOid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08003") %><%--결재요청--%></a>
                                                           </td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <%} %>
                                <c:if test="${dqm.dqmStateCode == 'END' || dqm.dqmStateCode == 'ACTIONAPPROVED'}" >
                                     <c:if test="${(dqm.actionUserFlag == true or todoUserFlag == true)}" >
                                     <!-- 
                                     <td>
	                                     <table border="0" cellspacing="0" cellpadding="0">
	                                         <tr>
	                                             <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                             <td class="btn_blue"
	                                                 background="/plm/portal/images/btn_bg1.gif">
	                                                     <a href="javascript:dqm.openCreateECR('${dqm.oid}');" class="btn_blue">ECR 등록</a>
	                                                    </td>
	                                             <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                         </tr>
	                                     </table>
	                                 </td>
	                                  -->
                                     <td>
                                         <table border="0" cellspacing="0" cellpadding="0">
                                             <tr>
                                                 <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                 <td class="btn_blue"
                                                     background="/plm/portal/images/btn_bg1.gif">
                                                         <a href="javascript:dqm.openCreateECO('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09069") %><%--ECO 등록--%></a>
                                                        </td>
                                                 <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                             </tr>
                                         </table>
                                     </td>
	                                 </c:if>
                                </c:if>
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
                <input type="hidden" name="raiseOid" value="${dqm.raiseOid}">
                <input type="hidden" name="actionOid" value="${dqm.actionOid}">
                <input type="hidden" name="execEndDate" value="${dqm.execEndDate}">
                <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed">
                    <colgroup>
                    <col width="12.5%">
                    <col width="12.5%">
                    <col width="12.5%">
                    <col width="12.5%">
                    <col width="12.5%">
                    <col width="12.5%">
                    <col width="12.5%">
                    <col width="12.5%">
                    </colgroup>
                    <tr>
                        <td colspan="4" class="tdblueL">
                            <%=messageService.getString("e3ps.message.ket_message", "09032") %><%--원인--%> (${dqm.causeCodeName})
                        <!-- 
                        <c:forTokens var = "token" items = "${dqm.causeCode}" delims = ",">
                          <c:choose>
                            <c:when test="${token=='PT01'}">
							 <c:out value="설계 "/>
							</c:when>
							<c:when test="${token=='PT02'}">
                             <c:out value="금형 "/>
                            </c:when>
                            <c:when test="${token=='PT03'}">
                             <c:out value="생기 "/>
                            </c:when>
                            <c:when test="${token=='PT04'}">
                             <c:out value="생산 "/>
                            </c:when>
                            <c:when test="${token=='PT05'}">
                             <c:out value="구매 "/>
                            </c:when>
                            <c:when test="${token=='PT06'}">
                             <c:out value="품질 "/>
                            </c:when>
					       </c:choose>
                          </c:forTokens>
                        )
						-->
                        </td>
                        </td>
                        <td colspan="4" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03433") %><%--개선 대책--%></td>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" class="tdwhiteL" valign="top">
                            <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                                <div id="divView" class="outline" style="overflow-x:auto; overflow-y:hidden; width: 100%"></div>
                            <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                        </td>
                        <td colspan="4" class="tdwhiteL" valign="top">
                            <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                                <div id="divView2" class="outline" style="overflow-x:auto; overflow-y:hidden; width: 100%"></div>
                            <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09039") %><%--중복--%></td>
                        <td class="tdwhiteL" colspan="3">${dqm.duplicateYn}</td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09040") %><%--도면출도--%></td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09041") %><%--금형수정--%></td>
                        <td class="tdblueL">T/O</td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09042") %><%--신뢰성시험--%></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09043") %><%--중복문제보고--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <a href="#" onclick="javascript:dqm.openDqmPopup('${dqm.duplicateReportCode}');">${dqm.duplicateReportName}</a>
                        </td>
                        <td class="text-center-border">
                            ${dqm.drawingOutDate}
                        </td>
                        <td class="text-center-border">
                            ${dqm.moldModifyDate}
                        </td>
                        <td class="text-center-border">
                            ${dqm.toDate}
                        </td>
                        <td class="text-center-border">
                            ${dqm.trustTestDate}
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00907") %><%--관련ECO--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <c:forEach var = "relatedEcrInfo" items = "${dqm.relatedEcrInfoList}">
                                <a href="#" onclick="javascript:openViewEco('${relatedEcrInfo.relatedEcrNo}');">${relatedEcrInfo.relatedEcrNo}</a>&nbsp
                            </c:forEach>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09141") %><%--설계변경반영--%></td>
                        <td class="tdwhiteL" colspan="4">
                            ${dqm.designChangeYn}
                        </td>
                    </tr>
                    <tr>
	                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09044") %><%--과거차문제점반영--%></td>
	                    <td class="tdwhiteL" colspan="3">
	                        ${dqm.problemReflectYn}
	                    </td>
	                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09045") %><%--유효성검증 예정--%></td>
	                    <td class="tdwhiteL">
	                        ${dqm.validationDate}
	                    </td>
	                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02062") %><%--실제완료일--%></td>
                        <td class="tdwhiteL">
                            ${dqm.execEndDate}
                        </td>
	                </tr>
	                <tr>
	                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09046") %><%--설계반영여부--%></td>
	                    <td class="tdwhiteL" colspan="7">
	                       ${dqm.designReflectName}
	                    </td>
	                </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09033") %><%--검토자--%></td>
                        <td class="tdwhiteL" colspan="3">
                            ${dqm.actionUserName}
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09047") %><%--검토일--%></td>
                        <td class="tdwhiteL" colspan="3">
                            ${dqm.actionCreateStamp}
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                        <td class="tdwhiteL" colspan="3">
                            ${dqm.actionApprovName}
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01998") %><%--승인일--%></td>
                        <td class="tdwhiteL" colspan="3">
                            ${dqm.actionApprovDate}
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">진행사항</td>
                        <td class="tdwhiteL" colspan="7">
                        ${dqm.mainSubjectHtml}
                        </td>
                    </tr> 
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                        <td colspan="7" class="tdwhiteL">
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
                                                    <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
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

