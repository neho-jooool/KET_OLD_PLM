<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/edm/ExceptList.jsp"%>
<% boolean isAdmin = CommonUtil.isAdmin(); %>
<script type="text/javascript"
    src="/plm/extcore/js/edm/drawingDistRequest.js?ver=2"></script>
<script>
<c:set var="bAdmin" value="<%=isAdmin %>"/>
//Jquery
$(document).ready(function(){
	CommonUtil.singleSelect('distType',100);
	SuggestUtil.bind('DEPARTMENT', 'distDeptName', 'drawingDistDeptArray');
	SuggestUtil.bind('ECONO', 'drawingDistEoArray', 'drawingDistEoArrayOid');
	//CommonUtil.multiSelect('drawingDistFileTypeArray',100);   
	var tab = CommonUtil.tabs('tabs');
    tab.tabs({disabled: [1]});
    
    $('[name=etcYn]').click(function (){
        if( this.checked ){ //true
	        $('[name=etcYn]').val("Y");
	        $('[name=distSubcontractor]').attr("class","");
            $('[name=distSubcontractor]').attr("class","txt_field");
            $('[name=distSubcontractor]').attr("readonly",false);
        }else{
        	$('[name=etcYn]').val("N");
            $('[name=distSubcontractor]').attr("class","");
            $('[name=distSubcontractor]').attr("class","txt_fieldRO");
            $('[name=distSubcontractor]').attr("readonly",true);
        }
    });
    

    
    $('[name=direct2Hompage]').click(function (){
    	if ($(this).is(":checked")){
    		$('input[name=drawingDistFileTypeArray]').each(function(i) {
                //$(this).removeAttr("disabled");
                $(this).attr("disabled", true); 
                $(this).prop("checked",false);
            });
    	}else{
    		$('input[name=drawingDistFileTypeArray]').each(function(i) {
                $(this).removeAttr("disabled");
            });
    	}
    });
    
    $('input[name=drawingDistFileTypeArray]').click(function(){drawingDistRequest.fileTypeToggle($(this))});
});

function selectDeptRsltFunc(rsltArray){
	var name = "";
	var oid = "";
	var code = "";
	
	for(var i = 0; i < rsltArray.length ; i++){
		 if(i != 0){
			 oid += ",";
			 name += ",";
			 code += ",";
	     }
        name += rsltArray[i].NAME;
		oid += rsltArray[i].OID;
		code += rsltArray[i].CODE;
	}
	
	$('[name=drawingDistDeptArray]').val(code);
    $('[name=distDeptName]').val(name);
}


function addDrawingAfterFunc(rtnArr){
    for(var i = 0; i < rtnArr.length ; i++){
        var arr = new Array();
        arr = rtnArr[i];
        addDrawing(arr);
        
    }   
}

function addDrawing(arr){
    var paramOid = arr[0];
    
    var cadNo = arr[1];
    
    var rtnFlag = false;
    
    $.each($('[name=exceptNo]'), function(i, val){
        if(val.value == cadNo) {
            alert("선택한 "+arr[1]+" 도면은 정합성 일치작업이 종료될때까지\n설계팀에 오프라인으로 별도 요청바랍니다.\n\n(정합성 일치 작업 기간 : 2016.07.29 까지)\n\n문의 : 커넥터설계1팀 우승우 수석연구원");
            rtnFlag = true;
            return false;
        }
    });
    
    $.each($('[name=drawingDisEpmArray]'), function(i, val){
        if(val.value == paramOid) {
            alert(LocaleUtil.getMessage('09123',arr[1]));//"선택한 "+arr[1]+" 도면은 이미 추가된 도면입니다.");
            rtnFlag = true;
            return;
        }
    });
    
    if(rtnFlag){
        return;
    }
    
    var paramSecurity = arr[11];
    
    if(paramSecurity == 'S2'){
    	alert(LocaleUtil.getMessage('09124',arr[1]));//"선택한 "+arr[1]+" 도면은 대내비 도면이므로 추가하실수 없습니다.");
    	return;
    }
    
    var innerRow = drawingTable.insertRow();
    innerRow.height = "27";
    var innerCell;
    
    for ( var k = 0; k < 10; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();javascript:drawingDistRequest.epmCheck();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = arr[1];
        } else if (k == 2) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = arr[3];
        } else if (k == 3) {
            innerCell.className = "tdwhiteL";
            innerCell.innerHTML = arr[2];
        } else if (k == 4) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = arr[4];
        } else if (k == 5) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = arr[9];
        } else if (k == 6) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = arr[10];
        } else if (k == 7) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = arr[5];
        } else if (k == 8) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<select onBlur='fm_jmp' id='sheetTypeArray' name='sheetTypeArray' style='width:90px' ><option value='Active'>Active</option><option value='All'>All</option></select><input name='drawingDisEpmArray' type='hidden' value='"+arr[0]+"'/>";
        } else if (k == 9) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = arr[12];
        }
        
    }
    
    drawingDistRequest.epmCheck();
}

function addDocumentAfterFunc(rtnArr) {
    for(var i = 0; i < rtnArr.length ; i++){
        var obj = new Object();
        obj = rtnArr[i];
        addDocument(obj);
    }   
}

function addDocument(rsltObject){
    /*
    rsltObject.INDEX = 0;
    rsltObject.oid = R[i].oid;
    rsltObject.createDate = R[i].createDate;
    rsltObject.creator = R[i].creator; 
    rsltObject.state = R[i].state;
    rsltObject.version = R[i].version;
    rsltObject.documentNo = R[i].documentNo;
    rsltObject.documentCategory = R[i].documentCategory;
    rsltObject.documentCategoryTxt = R[i].documentCategoryTxt;
    rsltObject.documentName = R[i].documentName;
    rsltObject.primaryConentDownUrl = R[i].primaryConentDownUrl;
    rsltObject.buyerSummit = R[i].buyerSummit;
    rsltObject.pjtNo = R[i].pjtNo;
    rsltObject.pjtType = R[i].pjtType;
    */
    
    var paramOid = rsltObject.oid;
    
    var rtnFlag = false;
    
    $.each($('[name=drawingDistDocArray]'), function(i, val){
        if(val.value == paramOid) {
            alert(LocaleUtil.getMessage('09125',rsltObject.documentNo));//"선택한 "+rsltObject.documentNo+" 문서는 이미 추가된 문서입니다.");
            rtnFlag = true;
            return;
        }
    });
    
    if(rtnFlag){
        return;
    }
    
    var paramSecurity = rsltObject.security;
    
    if(paramSecurity == 'S2'){
    	alert(LocaleUtil.getMessage('09126',rsltObject.documentNo));//"선택한 "+rsltObject.documentNo+" 문서는 대내비 문서로 추가하실수 없습니다.");
        return;
    }
    
    if(rsltObject.pjtNo == 'null' || rsltObject.pjtNo == null){
        rsltObject.pjtNo = "";
    }
    var innerRow = documentTable.insertRow();
    innerRow.height = "27";
    var innerCell;
    
    for ( var k = 0; k < 9; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();javascript:drawingDistRequest.docCheck();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = rsltObject.documentNo;
        } else if (k == 2) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = rsltObject.version;
        } else if (k == 3) {
            innerCell.className = "tdwhiteL";
            innerCell.innerHTML = rsltObject.documentName;
        } else if (k == 4) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = rsltObject.documentCategory;
        } else if (k == 5) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = rsltObject.pjtNo;
        } else if (k == 6) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = rsltObject.creator;
        } else if (k == 7) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = rsltObject.createDate;
        } else if (k == 8) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = rsltObject.state+"<input type='hidden' name='drawingDistDocArray' value='"+rsltObject.oid+"'/>";
        }
        
    }
    
    drawingDistRequest.docCheck();
}


function popupRelEco() {
    var url = "/plm/jsp/ecm/SearchEcoPopupForm.jsp?prodMoldCls=0&mode=multi";
    
    var defaultWidth = 740;
    var defaultHeight = 550;
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'', defaultWidth, defaultHeight, opts);
}

function selectModalDialog(objArr) {
    if(objArr.length == 0) {
        return;
    }
    
    var ecoNoStr = "";
    var ecoOidStr = "";
    
    for(var i = 0; i < objArr.length; i++){
        var obj = objArr[i];
        if(i != 0){
            ecoNoStr += ", ";
            ecoOidStr += ", ";
        }
        ecoNoStr += obj[1];
        ecoOidStr += obj[0];
    }
    
    $("[name='drawingDistEoArray']").val(ecoNoStr);
    $("[name='drawingDistEoArrayOid']").val(ecoOidStr);
}

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
				                                                href="javascript:drawingDistRequest.create();"
				                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
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
                    <form name="createForm" method="post">         
		                <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%">
		                    <col width="110">
		                    <col width="40%">
		                    <col width="110">
		                    <col width="*">
		                    <tr>
		                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
		                        <td class="tdwhiteL0" colspan="3"><input type="text" name="title" class="txt_field" style="width:100%" value=""></td>
		                    </tr>
		                    <tr>
		                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09113") %><%--사내배포처--%></td>
		                        <td class="tdwhiteL0">
		                            <input type="text" name="distDeptName" class="txt_field" style="width:70%" value="">
		                            <input type="hidden" name="drawingDistDeptArray" value="">
		                            <a href="javascript:SearchUtil.addDepartmentAfterFunc(true, 'selectDeptRsltFunc');">
		                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
		                            <a href="javascript:CommonUtil.deleteValue('drawingDistDeptArray', 'distDeptName');">
		                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		                        </td>
		                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09114") %><%--외주배포처--%></td>
		                        <td class="tdwhiteL0">
									<input type="checkbox" name="etcYn" value="N">기타
									<input type="text" name="distSubcontractor" class="txt_fieldRO" style="width:70%" value="" readonly="readonly">
									<a href="javascript:drawingDistRequest.selectPartner();">
									<img src="/plm/portal/images/icon_5.png" border="0"></a> 
									<a href="javascript:CommonUtil.deleteValue('distSubcontractorArray', 'distSubcontractor');">
									<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09112") %><%--배포유형--%><span class="red">*</span></td>
		                        <td class="tdwhiteL0">
		                            <ket:select id="distType" name="distType" className="fm_jmp" style="width: 130px;" codeType="DISTTYPE" multiple="multiple" useCodeValue="true"/>
		                            <!-- 
		                            <select onBlur="fm_jmp" id="distType" name="distType" style="width:130px" >
		                                <option value="REVIEW">검토</option>
		                                <option value="APPROVED">승인</option>
		                            </select>
		                             -->
		                        </td>
		                        <td class="tdblueL">EO No</td>
		                        <td class="tdwhiteL0">
		                          <input type="text" name="drawingDistEoArray" class="txt_field" style="width:70%" value="">
		                          <input type="hidden" name="drawingDistEoArrayOid" value="">
		                          <a href="javascript:popupRelEco();">
                                  <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                  <a href="javascript:CommonUtil.deleteValue('drawingDistEoArray', 'drawingDistEoArrayOid');">
                                  <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09128") %><%--배포포멧--%><span class="red">*</span></td>
		                        <td class="tdwhiteL0">
		                            <input type='checkbox' name='drawingDistFileTypeArray' value='PDF' null>PDF
		                            <input type='checkbox' name='drawingDistFileTypeArray' value='DWG' null>DWG
		                            <!-- <input type='checkbox' name='drawingDistFileTypeArray' value='ONEDO' null>원도  -->
		                            <input type='checkbox' name='drawingDistFileTypeArray' value='STEP,IGS,CAT' null>STEP / IGS / CAT
		                            <!--<input type='checkbox' name='drawingDistFileTypeArray' value='IGS' null>IGS-->
		                            <!--<input type='checkbox' name='drawingDistFileTypeArray' value='JPG' null>JPG  -->
		                            <!--<input type='checkbox' name='drawingDistFileTypeArray' value='THREEPDF' null>3D PDF -->
		                            <c:if test="${bAdmin == true}">
		                            <input type='checkbox' name='direct2Hompage' value='Y' null>홈페이지전송용
		                            </c:if>
		                            <!-- <select onBlur="fm_jmp" id="drawingDistFileTypeArray" name="drawingDistFileTypeArray" multiple="multiple" style="width:130px" >
		                                <option value="PDF">PDF</option>
		                                <option value="DWG">DWG</option>
		                                <option value="ONEDO">원도</option>
		                                <option value="STEP">STEP</option>
		                            </select> -->
		                        </td>
		                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09129") %><%--배포구분--%></td>
                                <td class="tdwhiteL0">
                                    <input type='checkbox' name='drawingDistDiv' value='DWG' disabled="disabled" onclick="return false;" null><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%>
                                    <input type='checkbox' name='drawingDistDiv' value='DOC' disabled="disabled" onclick="return false;" null><%=messageService.getString("e3ps.message.ket_message", "01394") %><%--문서--%>
                                </td>
		                    </tr>
		                    <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09130") %><%--배포도면--%></td>
                                <td class="tdwhiteL0" colspan="3">
                                   <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                       <tr>
                                           <td class="space5"></td>
                                       </tr>
                                   </table>
                                   <div id="div_scroll3"
                                       style="overflow-x: hidden; overflow-y: auto;"
                                       class="table_border">
                                       <table id="drawingTable" width="100%" cellpadding="0" cellspacing="0">
                                           <tbody id="drawinTableBody">
                                               <colgroup>
                                                 <col width="30"/>
                                                 <col width="130"/>
                                                 <col width="30"/>
                                                 <col width="*"/>
                                                 <col width="110"/>
                                                 <col width="60"/>
                                                 <col width="80"/>
                                                 <col width="80"/>
                                                 <col width="90"/>
                                              </colgroup>
                                              <tr>
                                                  <td class="tdgrayM"><a href="#" onclick="javascript:SearchUtil.selectDrawing('addDrawingAfterFunc');"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                                                  <td class="tdgrayM">Rev</td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                                                  <td class="tdgrayM">Sheet</td>
                                                  <td class="tdgrayM">유형</td>
                                             </tr>
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
                                   <div id="div_scroll3"
                                       style="overflow-x: hidden; overflow-y: auto;"
                                       class="table_border">
                                       <table id="documentTable" width="100%" cellpadding="0" cellspacing="0">
                                           <tbody id="documentTableBody">
                                               <colgroup>
                                                  <col width="30"/>
                                                  <col width="100"/>
                                                  <col width="30"/>
                                                  <col width="*"/>
                                                  <col width="110"/>
                                                  <col width="100"/>
                                                  <col width="80"/>
                                                  <col width="80"/>
                                                  <col width="90"/>
                                              </colgroup>
                                              <tr>
                                                  <td class="tdgrayM"><a href="#" onclick="javascript:SearchUtil.selectTotalDocument('addDocumentAfterFunc');"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                                                  <td class="tdgrayM">Rev</td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                                                  <td class="tdgrayM">Project No</td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                                             </tr>
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
		                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09115") %><%--배포사유--%><span class="red">*</span></td>
		                        <td class="tdwhiteL0" colspan="3"><textarea rows="3" style="width:100%" name="distReason"></textarea></td>
		                    </tr>
		                </table>
		                <c:forEach items="${exceptList}" var="except" varStatus="i">
		                <input type="hidden" name="exceptNo" value="${except}">
                        </c:forEach>
		            </form> 
		            <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
                </td>
            </tr>
        </table>
    </div>
    <div id="tabs-2">
    </div>
</div>

