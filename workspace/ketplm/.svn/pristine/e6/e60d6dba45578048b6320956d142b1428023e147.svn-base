<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
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
<script type="text/javascript"
    src="/plm/extcore/js/sample/sampleRequest.js"></script>
    
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script>
//Jquery
$(document).ready(function(){
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
});


function requestApprove(requestOid)
{
    goPage(requestOid);
}
    
</script>

<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${sampleRequest.webEditor}</textarea>

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
                        <c:if test="${(sampleRequest.sampleRequestStateCode == 'INWORK' or sampleRequest.sampleRequestStateCode == 'REWORK') and sampleRequest.createUserFlag == true}">
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
	                                            <td>
	                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                       <td class="btn_blue"
                                                            background="/plm/portal/images/btn_bg1.gif"><a
                                                            href="javascript:sampleRequest.goModify('${sampleRequest.oid}');"
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
		                                                       href="javascript:requestApprove('${sampleRequest.oid}');"
		                                                       class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08003") %><%--결재요청--%></a></td>
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
                                                               href="javascript:sampleRequest.remove('${sampleRequest.oid}');"
                                                               class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                           <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                       </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            </c:if>
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
                    <form name="viewForm" method="post" enctype="multipart/form-data">  
                    <input type="hidden" name="oid" value="${sampleRequest.oid}"/>    
                        <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                            <colgroup>
                                <col width="120">
                                <col width="*">
                                <col width="120">
                                <col width="*">
                            </colgroup>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09078") %><%--요청서 No--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.requestNo}
                                </td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "08102") %><%--요청기한--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.requestDate}
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                                <td class="tdwhiteL0" colspan="3">
                                    ${sampleRequest.requestTitle}
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.customerName}
                                </td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.carTypeName}
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09101") %><%--요청부품--%></td>
                                <td class="tdwhiteL0" colspan="3">
                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                       <tr>
                                           <td class="space5"></td>
                                       </tr>
                                   </table>
                                   <div id="div_scroll3"
                                       style="overflow-x: hidden; overflow-y: auto;"
                                       class="table_border">
                                       <table id="partTable" width="100%" cellpadding="0" cellspacing="0">
                                           <tbody id="partTableBody">
                                               <colgroup>
                                                 <col width="130"/>
                                                 <col width="*"/>
                                                 <col width="30"/>
                                                 <col width="130"/>
                                                 <col width="100"/>
                                                 <col width="120"/>
                                                 <col width="90"/>
                                              </colgroup>
                                              <tr>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
                                                  <td class="tdgrayM">Rev</td>
                                                  <td class="tdgrayM">Project No</td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%>(PM)</td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09084") %><%--요청수량--%></td>
                                             </tr>
                                             <c:forEach items="${samplePartList}" var="samplePart" varStatus="i">
	                                             <tr>
	                                                <td class="tdwhiteM" align="center"><a href="javascript:openViewPart('${samplePart.partNo}');">${samplePart.partNo}</a></td>
	                                                <td class="tdwhiteM" align="center">${samplePart.partName}</td>
	                                                <td class="tdwhiteM" align="center">${samplePart.ver}</td>
	                                                <td class="tdwhiteM" align="center"><a href="javascript:openProject('${samplePart.projectNo}');">${samplePart.projectNo}</a></td>
	                                                <td class="tdwhiteM" align="center">${samplePart.developeStageName}</td>
                                                    <td class="tdwhiteM" align="center">${samplePart.pmUserName}</td>
	                                                <td class="tdwhiteM" align="center">${samplePart.requestCount}EA</td>
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
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09102") %><%--요청부서--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.createUserDeptName}
                                </td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02196") %><%--요청자--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.createUserName}
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02194") %><%--요청일--%></td>
                                <td class="tdwhiteL0"">
                                    ${sampleRequest.createDate}
                                </td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09080") %><%--고객담당자--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.customerContractor}
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09079") %><%--용도--%></td>
                                <td class="tdwhiteL0" colspan="3">
                                    ${sampleRequest.purpose}
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.approvUserName}
                                </td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01998") %><%--승인일--%></td>
                                <td class="tdwhiteL0">
                                    ${sampleRequest.approvDate}
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09105") %><%--요청사유--%></td>
                                <td class="tdwhiteL0" colspan="3">
                                    <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
		                                <div id="divView" class="outline"></div>
		                            <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
		                        </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                                <td colspan="3" class="tdwhiteL0">
                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                            <td class="space5"></td>
                                        </tr>
                                    </table>
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
                    <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
                 </td>
            </tr>
        </table>