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

	var samplePartList = ${sampleRequest.partListSize};
	var receptionButtonFlag = ${sampleRequest.receptionButtonFlag};
	
	if(receptionButtonFlag == true){
	    CalendarUtil.dateInputFormat('dispensationExpectDateArr');
	}
	//CalendarUtil.dateInputFormat('receptionDateArr');
    //CalendarUtil.dateInputFormat('dispensationDateArr');
    /*
	for(var i = 1; i <= samplePartList; i++){
		
		CalendarUtil.dateInputFormatById('receptionDateArr'+i);
        CalendarUtil.dateInputFormatById('dispensationExpectDateArr'+i);
        CalendarUtil.dateInputFormatById('dispensationDateArr'+i);
	}
    */
    
	$("#check_all").click(function() {
		var chcStatus = $("input[name=check_all]:checkbox").is(":checked");
		
		if(chcStatus){
	        $("input[name=partCheck]:checkbox").prop("checked", true);
		}
		else {
	        $("input[name=partCheck]:checkbox").prop("checked", false);
		}
    });
    
    try{
	    var strHTMLCode = document.forms[0].webEditor.value;
	    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
	    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
	    // 세번째 매개변수 => 이노디터 번호
	    fnSetEditorHTMLCode(strHTMLCode, false, 0);
	
	    ////////////////////////////////////////////////////////////////////////////////////////////////////
	    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
	    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor.value, 0);// 배경색, Value, 이노디터 번호
	    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage.value, 0);// 배경이미지, Value, 이노디터 번호
	    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat.value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
	    ////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    catch (e) {
	}
});
    
</script>
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">    
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                        <c:if test="${sampleRequest.sampleRequestStateCode == 'DISPENSATION' and sampleRequest.createUserFlag == true}">
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
                                                            href="javascript:sampleRequest.complete();"
                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></a></td>
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
                                                            href="javascript:sampleRequest.reRequest();"
                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09106") %><%--재요청--%></a></td>
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
                                                            href="javascript:sampleRequest.goReceptionView('${sampleRequest.oid}');"
                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
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
                        <c:if test="${(sampleRequest.sampleRequestStateCode == 'REQUEST' or sampleRequest.sampleRequestStateCode == 'REREQUEST') and sampleRequest.receptionUpdateFlag == true}">
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <c:if test="${sampleRequest.receptionButtonFlag == true}">
	                                                <td>
	                                                    <table border="0" cellspacing="0" cellpadding="0">
	                                                    <tr>
	                                                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                       <td class="btn_blue"
	                                                            background="/plm/portal/images/btn_bg1.gif"><a
	                                                            href="javascript:sampleRequest.reception('${sampleRequest.oid}');"
	                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02505") %><%--접수--%></a></td>
	                                                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                                    </tr>
	                                                    </table>
	                                                </td>
                                                </c:if>
                                                <c:if test="${sampleRequest.receptionButtonFlag != true}">
	                                                <td>
	                                                    <table border="0" cellspacing="0" cellpadding="0">
	                                                    <tr>
	                                                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                       <td class="btn_blue"
	                                                            background="/plm/portal/images/btn_bg1.gif"><a
	                                                            href="javascript:sampleRequest.workComplete('${sampleRequest.oid}');"
	                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
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
	                                                            href="javascript:sampleRequest.receptionUpdate('${sampleRequest.oid}');"
	                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
	                                                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                                    </tr>
	                                                    </table>
	                                                </td>
                                                </c:if>
                                                <td width="5">&nbsp;</td>  
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                       <td class="btn_blue"
                                                            background="/plm/portal/images/btn_bg1.gif"><a
                                                            href="javascript:sampleRequest.goReceptionView('${sampleRequest.oid}');"
                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
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
                    <form id="updateForm" name="updateForm" method="post">
                    <input type="hidden" name="oid" value="${sampleRequest.oid}">  
                    <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%">
                        <colgroup>
                            <col width="90">
                            <col width="*">
                        </colgroup>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09101") %><%--요청부품--%><span class="red">*</span></td>
                            <td class="tdwhiteL0">
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
                                             <c:if test="${sampleRequest.sampleRequestStateCode == 'DISPENSATION' and sampleRequest.createUserFlag == true}">
                                                <col width="20"/>
                                             </c:if>
                                             <col width="65"/>
                                             <col width="*"/>
                                             <col width="30"/>
                                             <col width="65"/>
                                             <col width="70"/>
                                             <col width="70"/>
                                             <col width="80"/>
                                             <col width="80"/>
                                             <col width="75"/>
                                             <c:if test="${sampleRequest.receptionButtonFlag != true}">
                                                <col width="75"/>
                                             </c:if>
                                             <c:if test="${sampleRequest.receptionButtonFlag == true}">
                                                <col width="120"/>
                                             </c:if>
                                             <col width="75"/>
                                          </colgroup>
                                          <tr>
                                              <c:if test="${sampleRequest.sampleRequestStateCode == 'DISPENSATION' and sampleRequest.createUserFlag == true}">
                                              <td class="tdgrayM"><input type="checkbox" id="check_all" name="check_all"></td>
                                              </c:if>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
                                              <td class="tdgrayM">Rev</td>
                                              <td class="tdgrayM">Project No</td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%>(PM)</td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09084") %><%--요청수량--%></td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09107") %><%--불출수량--%></td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09108") %><%--접수일--%></td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09109") %><%--불출예정일--%></td>
                                              <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09085") %><%--불출일--%></td>
                                         </tr>
                                         <c:forEach items="${samplePartList}" var="samplePart" varStatus="i">
                                             <tr>
                                                <c:if test="${sampleRequest.sampleRequestStateCode == 'DISPENSATION' and sampleRequest.createUserFlag == true}">
		                                          <td class="tdwhiteM" align="center"><input type="checkbox" id="partCheck" name="partCheck"  value="${samplePart.samplePartOid}"></td>
		                                        </c:if>
                                                <td class="tdwhiteM" align="center">${samplePart.partNo}</td>
                                                <td class="tdwhiteM" align="center" title="${samplePart.partName}">
                                                   <div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">
                                                       ${samplePart.partName}
                                                   </div>
                                                </td>
                                                <td class="tdwhiteM" align="center">${samplePart.ver}</td>
                                                <td class="tdwhiteM" align="center">${samplePart.projectNo}</td>
                                                <td class="tdwhiteM" align="center">${samplePart.developeStageName}</td>
                                                <td class="tdwhiteM" align="center">${samplePart.pmUserName}</td>
                                                <td class="tdwhiteM" align="center">${samplePart.requestCount}EA</td>
                                                <input name='samplePartOidArr' type='hidden' value='${samplePart.samplePartOid}'/>
                                                <c:if test="${sampleRequest.receptionButtonFlag != true and (sampleRequest.sampleRequestStateCode == 'REQUEST' or sampleRequest.sampleRequestStateCode == 'REREQUEST')}">
	                                                <td class="tdwhiteM" align="center">
	                                                    <input name='dispensationCountArr' type='text' class='txt_field' style='width:70%' value='${samplePart.dispensationCount}'/>EA
	                                                </td>
	                                            </c:if>
	                                            <c:if test="${sampleRequest.receptionButtonFlag != true and !(sampleRequest.sampleRequestStateCode == 'REQUEST' or sampleRequest.sampleRequestStateCode == 'REREQUEST')}">
	                                               <td class="tdwhiteM" align="center">${samplePart.dispensationCount}EA</td>
	                                            </c:if>
                                                <c:if test="${sampleRequest.receptionButtonFlag == true}">
                                                    <td class="tdwhiteM" align="center">${samplePart.dispensationCount}EA</td>
                                                </c:if>
                                                <td class="tdwhiteM" align="center">${samplePart.receptionDate}</td>
                                                
                                                <!-- 
                                                <td class="tdwhiteM">
													<input type="text" id='receptionDateArr${i.index+1}' name="receptionDateArr" class="txt_field" style="width: 70px" value="${samplePart.receptionDate}">
                                                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValueById('receptionDateArr${i.index+1}');" style="cursor: hand;"></td>
												</td>
												 -->
												 
												 <c:if test="${sampleRequest.receptionButtonFlag == true}">
												 	<td class="tdwhiteM">
	                                                    <input type="text" id='dispensationExpectDateArr${i.index+1}' name="dispensationExpectDateArr" class="txt_field" style="width: 70px" value="${samplePart.dispensationExpectDate}">
	                                                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValueById('dispensationExpectDateArr${i.index+1}');" style="cursor: hand;"></td>
	                                                </td>
                                                </c:if>
                                                <c:if test="${sampleRequest.receptionButtonFlag != true}">
                                                    <td class="tdwhiteM" align="center">${samplePart.dispensationExpectDate}</td>
                                                </c:if>
                                                <td class="tdwhiteM" align="center">${samplePart.dispensationDate}</td>
                                                <!-- 
                                                <td class="tdwhiteM">
                                                    <input type="text" id='dispensationDateArr${i.index+1}' name="dispensationDateArr" class="txt_field" style="width: 70px" value="${samplePart.dispensationDate}">
                                                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValueById('dispensationDateArr${i.index+1}');" style="cursor: hand;"></td>
                                                </td>
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
                        <c:if test="${sampleRequest.sampleRequestStateCode == 'DISPENSATION' and sampleRequest.createUserFlag == true}">
                        <tr>
	                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09110") %><%--요청사유 (재요청시)--%></span></td>
	                        <td class="tdwhiteL0" id="enoediter">
	                            <!-- 이노디터 JS Include Start --> <script type="text/javascript">
	                                // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
	                                // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
	                                // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
	                                var g_arrSetEditorArea = new Array();
	                                g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
	                            </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script> <script type="text/javascript"
	                                src="/plm/portal/innoditor_u/js/customize_ui.js"></script> <script type="text/javascript">
	                                // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
	                                // Skin 재정의
	                                //g_nSkinNumber = 0;
	                                // 크기, 높이 재정의
	                                g_nEditorWidth = $("#enoediter").width();
	                                g_nEditorHeight = 300;
	                            </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
	                            <textarea name="webEditor" rows="0" cols="0" style="display: none">${sampleRequest.webEditor}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
	                            <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
	                            value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
	                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
	                            <textarea name="webEditorText" rows="0" cols="0" style="display: none">${sampleRequest.webEditorText}</textarea> <!-- Editor Area Container : Start -->
	                            <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
	                        </td>
	                    </tr>
	                    </c:if>
                    </table>
                    </form>
                    <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
                 </td>
            </tr>
        </table>