<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
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
    src="/plm/extcore/js/dqm/dqm.js"></script>

<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%
boolean isAdmin = CommonUtil.isAdmin();
%>

<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script type="text/javascript">
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
});

function insertFileLine() {
    var innerRow = fileTable.insertRow();
    innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>"
                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteM0";
            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' size='100%'>";
        }
    }
}
    
    function addDepartment(targetId) {
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
        attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
        if(typeof attacheDept == "undefined" || attacheDept == null) {
            return;
        }

        if ( typeof(attacheDept) != "object" ) {
            var deptSplit = attacheDept.split(",");
            for ( var i=0; i<deptSplit.length-1; i++ ) {
                var param = "command=deptInfo&deptOid=" + deptSplit[i];
                var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;

                if ( targetId == "actionDeptName" ) {
                    callServer(url, acceptDept1);
                }
                else if ( targetId == "devUserDept2" ) {
                    callServer(url, acceptDept2);
                }
                else if ( targetId == "devUserDept3" ) {
                    callServer(url, acceptDept3);
                }
            }
        }
        else {
            var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;

            if ( targetId == "actionDeptName" ) {
                callServer(url, acceptDept1);
            }
            else if ( targetId == "devUserDept2" ) {
                callServer(url, acceptDept2);
            }
            else if ( targetId == "devUserDept3" ) {
                callServer(url, acceptDept3);
            }
        }
    }
    
    function acceptDept1(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg != 'true') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
            return;
        }

        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");
        var l_name = showElements[0].getElementsByTagName("l_name");
        var l_code = showElements[0].getElementsByTagName("l_code");
        var l_chiefOid = showElements[0].getElementsByTagName("l_chiefOid");
        var l_chiefName = showElements[0].getElementsByTagName("l_chiefName");

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "actionDeptCode", "actionDeptName");
    }
    
    function deptMerge(deptOid, deptName, targetId, targetName) {
        if ( $("#"+targetId).val() == "" ) {
            $("#"+targetId).val( deptOid );
            $("#"+targetName).val( deptName );
        }
        else {
            var deptOidArr = $("#"+targetId).val().split(", ");
            var deptNameArr = $("#"+targetName).val().split(", ");
            // 선택된 부서 push
            deptOidArr.push(deptOid);
            deptNameArr.push(deptName);
            // 중복 부서 정리
            deptOidArr = $.unique(deptOidArr.sort());
            deptNameArr = $.unique(deptNameArr.sort());

            $("#"+targetId).val( deptOidArr.join(", ") );
            $("#"+targetName).val( deptNameArr.join(", ") );
        }
    }
    
    function delDepartment(targetId, targetName) {
        $("#" + targetId).val("");
        $("#" + targetName).val("");
    }


    function deleteFileLine() {
        var body = document.getElementById("fileTable");
        if (body.rows.length == 0)
            return;
        var file_checks = document.forms[0].fileSelect;
        if (body.rows.length == 1) {
            body.deleteRow(0);
        } else {
            for ( var i = body.rows.length; i > 0; i--) {
                if (file_checks[i - 1].checked)
                    body.deleteRow(i - 1);
            }
        }
    }

    function allCheck(checkboxName, isChecked) {
        var checkedList = document.getElementsByName(checkboxName);

        for ( var i = 0; i < checkedList.length; i++) {
            checkedList[i].checked = isChecked;
        }
    }
    
    function changeDate(param){
        var startDate;
        startDate = $("[name="+param+"]").val();
              
        if (startDate != "" ) {
            if ( !dateCheck(startDate,"-") ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>");
                $("[name="+param+"]").val("");
            }
        }
    }
    
    function clearDate(param){
    	$("[name="+param+"]").val("");
    }
    
    function reReview(){
    	if( confirm('<%=messageService.getString("e3ps.message.ket_message", "09059") %><%--재검토하시겠습니까?--%>') ){
            showProcessing();
	        $.ajax({
	            type       : "POST",
	            url        : "/plm/ext/dqm/reReview.do",
	            data       :{
	            	oid : "${dqm.oid}"
	            },
	            dataType   : "json",
	            success    : function(data){
	            	alert('<%=messageService.getString("e3ps.message.ket_message", "09060") %><%--재검토되었습니다.--%>');
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
	                
	                parent.location.href = '/plm/ext/dqm/dqmMainForm.do?type=actionToGrid&oid=${dqm.oid}';
	            },
	            error    : function(xhr, status, error){
	                         alert(xhr+"  "+status+"  "+error);
	                         
	            }
	        });
    	}
    	
    }
    
    function close(){
    	var closeOid = "${dqm.closeOid}";
    	if(closeOid == "" || closeOid == null){
    		alert('<%=messageService.getString("e3ps.message.ket_message", "09061") %><%--종결 기본정보를 입력해야 작업완료 가능합니다.--%>');
    		return;
    	}
    	
    	if( confirm('<%=messageService.getString("e3ps.message.ket_message", "09062") %><%--종결하시겠습니까?--%>') ){
    		showProcessing();
	    	$.ajax({
	            type       : "POST",
	            url        : "/plm/ext/dqm/close.do",
	            data       :{
	                oid : "${dqm.oid}"
	            },
	            dataType   : "json",
	            success    : function(data){
	            	alert('<%=messageService.getString("e3ps.message.ket_message", "09063") %><%--종결되었습니다.--%>');
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
	            },
	            error    : function(xhr, status, error){
	                         alert(xhr+"  "+status+"  "+error);
	                         
	            }
	        });
    	}
    }
    </script>
    <form name="innoditorDataForm" method="post">
	    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
	    <textarea name="webEditor" rows="0" cols="0" style="display: none">${dqm.reviewWebEditor}</textarea>
	
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
                        <%if(isAdmin){ %>
                        <td  align="right">
                              <table border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                      <td class="btn_blue"
                                          background="/plm/portal/images/btn_bg1.gif"><a
                                          href="javascript:dqm.goCloseModify('${dqm.oid}');"
                                          class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                  </tr>
                              </table>
                          </td>
                        <%} else{ %>
                        <c:if test="${dqm.dqmStateCode == 'ACTIONAPPROVED' and (dqm.pmUserFlag == true or todoUserFlag == true or dqm.closeUserFlag == true)}" >
	                        <c:if test="${not empty dqm.closeOid}" >
	                        <td align="right">
	                            <table border="0" cellspacing="0" cellpadding="0">
	                                <tr>
	                                    <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <td>
				                                    <table border="0" cellspacing="0" cellpadding="0">
				                                        <tr>
				                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
				                                            <td class="btn_blue"
				                                                background="/plm/portal/images/btn_bg1.gif"><a
				                                                href="javascript:dqm.goCloseModify('${dqm.oid}');"
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
		                                                       href="javascript:close();"
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
	                                                            href="javascript:reReview();"
                                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09064") %><%--재검토--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
	                                                </table>
	                                            </td>
	                                           </table>
	                                        </td>
	                                    </tr>
	                                </table>
	                            </td>
	                            </c:if>
	                            <c:if test="${empty dqm.closeOid}" >
	                           <td align="right">
	                            <table border="0" cellspacing="0" cellpadding="0">
	                                <tr>
	                                    <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <td>
	                                                <table border="0" cellspacing="0" cellpadding="0">
	                                                    <tr>
	                                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                        <td class="btn_blue"
	                                                            background="/plm/portal/images/btn_bg1.gif"><a
	                                                            href="javascript:dqm.goCloseCreate('${dqm.oid}');"
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
	                                                           href="javascript:close();"
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
	                                                           href="javascript:reReview();"
                                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09064") %><%--재검토--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
	                                                </table>
	                                            </td>
	                                           </table>
	                                        </td>
	                                    </tr>
	                                </table>
	                            </td>
	                            </c:if>
                            </c:if>
                            <%} %>
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
                    <form name="createForm" method="post" enctype="multipart/form-data"> 
			            <input type="hidden" name="oid" value="${dqm.oid}">            
			                <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%">
			                    <col width="12.5%">
			                    <col width="37.5%">
			                    <col width="12.5%">
			                    <col width="37.5%">
			                    <tr>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09065") %><%--검토결과--%></td>
                                    <td class="tdwhiteL">
                                        <ket:codeValueByCode codeType="DQMREVIEWRESULT" code="${dqm.reviewRsltCode}"/> 
                                    </td>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09066") %><%--적용예정일--%></td>
                                    <td class="tdwhiteL">${dqm.applyExpectDate}</td>
                                </tr>
                                <tr>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09067") %><%--유효성검증 확인--%></td>
                                    <td class="tdwhiteL" colspan="3">
                                        ${dqm.validationCheck}
                                    </td>
                                </tr>
			                    <tr>
			                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09068") %><%--검토의견--%></td>
			                        <td class="tdwhiteL" colspan="3">
			                            <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
			                                <div id="divView" class="outline"></div>
			                                <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
		                            </td>
			                    </tr>
			                    <tr>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09033") %><%--검토자--%></td>
                                    <td class="tdwhiteL">${dqm.reviewer}</td>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09047") %><%--검토일--%></td>
                                    <td class="tdwhiteL">${dqm.reviewDate}</td>
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
		            <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
            </td>
        </tr>
   </table>
             