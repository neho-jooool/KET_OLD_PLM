<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.part.WTPart"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="wt.content.ContentRoleType"%>
<%@page import="wt.fc.QueryResult"%>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<!--  주석 처리 변경해봅니다. -->
<%

boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

%>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

/* 재정의 Iframe에 들어가므로 */
.body-space {
    margin-left: 0px !important;
    margin-top: 0px !important;
    margin-right: 0px !important;
    min-width: 0px;   
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0;
    word-break:break-all;
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}

/* 재정의 frame height 재정의함 */
.tabIframeWrapper {width:100%;height:430px;border:0px; margin:0px;position:relative;top:0px;}

</style>

<%

    String poid = request.getParameter("partOid"); 
    
%>

<script type="text/javascript">
<!--

$(document).ready(function(){
    CommonUtil.tabs('tabs');
})

function deletePart(oid){
	
	showProcessing();
    var checkStr = checkPartBom();
    if("E" == checkStr){
        hideProcessing();
        return;
    }else if("N" == checkStr){
    }else if("Y" == checkStr){
        alert("'${baseDto.partNumber}'부품은 BOM에서 사용중입니다.\n BOM에서 삭제한 후에 삭제가 가능합니다.");
        hideProcessing();
        return;
    }
    
    var checkStr = checkPartDieHalbLink();
    if("E" == checkStr){
        hideProcessing();
        return;
    }else if("N" == checkStr){
    }else if("Y" == checkStr){
        alert("'${baseDto.partNumber}'부품은 제품-금형 관계에서 사용중입니다.\n 제품-금형 관계에서 삭제한 후에 삭제가 가능합니다.");
        hideProcessing();
        return;
    }
    
    var ecoid = EcoUsePart(oid);
    if(ecoid != ""){
    	alert("${baseDto.partNumber} 부품은 관련 ECO가 존재합니다("+ecoid+")\nECO에서 삭제한 후에 삭제가 가능합니다.");
    	hideProcessing();
        return;
    }
    
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01707")%><%--삭제하시겠습니까?--%>")){
    	hideProcessing();
        return;
    }
    
    $.ajax({
    	
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/deletePart.do?partOid='+oid,
        //data: $('[name=partForm]').serialize(),
        success: function (data) {
            if(data != 'Fail'){
                alert('삭제되었습니다.');
                if(parent){
                	if(parent.opener){
                		try{
                			parent.opener.PartList.reload(); // parent.opener.location.reload();
                		}catch(e){}
                	}
                    parent.window.close();
                }
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            hideProcessing();
        }
        
    });
    
    hideProcessing();

}

 function checkPartBom(){
    var ret = "N";
    $.ajax({
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/existPartBom.do?partNumber=${baseDto.partNumber}',
        success: function (data) {
            if(data != 'Fail'){
                try{
                    if(data=='Y'){
                        ret = 'Y';
                    }else{
                        ret = 'N';
                    }
                }catch(e){alert(e.message); ret = "E"; }
            }else{
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
 }

 function checkPartDieHalbLink(){
    var ret = "N";
    $.ajax({
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/existPartDieHalbLink.do?partNumber=${baseDto.partNumber}',
        success: function (data) {
            if(data != 'Fail'){
                try{
                    if(data=='Y'){
                        ret = 'Y';
                    }else{
                        ret = 'N';
                    }
                }catch(e){alert(e.message); ret = "E"; }
            }else{
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
 }

function viewPartList(oid){
	
	var url = "/plm/ext/part/base/viewPartListForm.do?partOid="+oid;
    //var name = "partList:" + oid;
    //var name = "viewPartListFormPopup";
    var name = oid;
    defaultWidth = 1024; // 1150;
    defaultHeight = 768; // 800
    var opts = ",scrollbars=1,resizable=1"; // "toolbar=0,location=0,directory=0,status=1,menubar=0
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function modifyPart(oid){
	
	var url = "/plm/jsp/bom/UpdatePart.jsp?oid="+oid;
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "modify:" + oid;
    //var name = "UpdatePartPopup";
    var name = oid;
    defaultWidth = 1024; // 1150;
    defaultHeight = 768; // 800
    var opts = ",scrollbars=1,resizable=1"; // "toolbar=0,location=0,directory=0,status=1,menubar=0
    //getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    //parent.location.replace=url;
    parent.lfn_goPage(oid);
}

function modifyPartAfterApp(oid){
	
	var url = "/plm/jsp/bom/UpdatePartAfterApp.jsp?oid="+oid;
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "modify:" + oid;
    //var name = "UpdatePartAfterAppPopup";
    var name = oid;
    defaultWidth = 1024; // 1150;
    defaultHeight = 768; // 800
    var opts = ",scrollbars=1,resizable=1"; // toolbar=0,location=0,directory=0,status=1,menubar=0
    //getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    //parent.location.replace=url;
    parent.location.href = url;
}

function registCopyPart(oid){
	
	var url = "/plm/ext/part/base/registCopyPartForm.do?partOid="+oid;
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "copy:" + oid;
    //var name = "registCopyPartFormPopup";
    var name = oid;
    var defaultWidth = 1150;
    var defaultHeight = 800;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function registHalbPart(oid,numberingCode){
	
	var url = "/plm/ext/part/base/registHalbPartForm.do?partOid="+oid+"&numberingCode="+numberingCode;
	
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "halb:" + oid;
    //var name = "registHalbPartFormPopup";
    var name = oid;
    var defaultWidth = 1150;
    var defaultHeight = 800;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function registHawaPart(oid,numberingCode){
    
    var url = "/plm/ext/part/base/registHalbPartForm.do?partOid="+oid+"&numberingCode="+numberingCode+"&isHawaRegist=Y";
    
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "halb:" + oid;
    //var name = "registHalbPartFormPopup";
    var name = oid;
    var defaultWidth = 1150;
    var defaultHeight = 800;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function registDieSetPart(oid){
	
	var url = "/plm/ext/part/base/registPartForm.do?partProdOid="+oid;
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "halb:" + oid;
    //var name = "registPartFormPopup";
    var name = oid;
    var defaultWidth = 1150;
    var defaultHeight = 800;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function KetnPartSend(oid){    
    showProcessing();

    if(!confirm("부품 ERP에 전송하시겠습니까?\nERP에 자재마스터를 생성합니다.")){
        hideProcessing();
        return;
    } 
    
     $.ajax({
    	type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/ketnPartSend.do?partOid='+oid,
        
        //dataType: "jsonp",
        //http://blog.naver.com/kangminser88/220118477181
        //data: $('[name=partForm]').serialize(),
        success: function (data) {
            if(data != 'Fail'){
                
                try{
                    if(data=='Y'){
                         
                        alert("ERP에 자재마스터가 생성되었습니다.");
                         
                         if(parent){
                             if(parent.opener){
                                 try{
                                     parent.opener.PartList.reload(); // parent.opener.location.reload();
                                 }catch(e){}
                             }
                             parent.window.close();
                         }
                         
                    }else if(data=='S'){
                        alert("ERP에 이미 해당 부품이 존재합니다.");
                    }else if(data=='X'){
                        alert("해당부품의 사업부는 [KETN] 이 아닙니다.");
                    }else if(data=='E'){
                        alert("ERP에 부품 생성 중 예상치 못한 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
                    }
                }catch(e){alert(e.message); }
                
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%>저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%>저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
            hideProcessing();
        }
        
    });
    
    hideProcessing();

}


function notUsePart(oid){
    
    showProcessing();

    if(!confirm("부품 '사용안함' 상태로 변경하시겠습니까?\nERP의 부품정보도 '사용안함'으로 동시에 변경됩니다.")){
        hideProcessing();
        return;
    }
    
    $.ajax({
        
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/notUsePart.do?partOid='+oid,
        //data: $('[name=partForm]').serialize(),
        success: function (data) {
        	
            if(data != 'Fail'){
            	
                try{
                    if(data=='Y'){
                    	 
                    	alert("부품 '사용안함' 상태로 변경되었습니다.");
                    	 
                    	 if(parent){
                             if(parent.opener){
                                 try{
                                     parent.opener.PartList.reload(); // parent.opener.location.reload();
                                 }catch(e){}
                             }
                             parent.window.close();
                         }
                    	 
                    }else if(data=='N'){
                    	alert("부품 '사용안함' 상태로 변경 중 ERP 인터페이스 결과가 실패입니다.");
                    }else if(data=='W'){
                    	alert("ERP에 존재하지 않는 부품이어서 '사용안함' 상태로 변경할 수 없습니다.");
                    }else if(data=='O'){
                    	alert("이미 '사용안함' 상태로 변경된 부품입니다.");
                    }else if(data=='E'){
                    	alert("부품 '사용안함' 상태로 변경 중 예상치 못한 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
                    }
                }catch(e){alert(e.message); }
                
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            hideProcessing();
        }
        
    });
    
    hideProcessing();

}

function plm2hompageMulti(){
    
    showProcessing();

    if(!confirm("정말로 해당 부품을 홈페이지로 이관하시겠습니까?\n전송된 정보는 홈페이지에 공개되므로 각별히 주의하여야합니다.")){
        hideProcessing();
        return;
    }
    
    $.ajax({
        
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/plm2hompageMulti.do',
        //data: $('[name=partForm]').serialize(),
        success: function (data) {
            
            if(data != 'Fail'){
                
                try{
                    if(data=='Y'){
                         
                        alert("해당 부품을 홈페이지로 이관하였습니다.");
                         
                    }else if(data=='N'){
                        alert("부품 홈페이지 인터페이스 결과가 실패입니다.");
                    }else if(data=='E'){
                        alert("부품 홈페이지 인터페이스 중 예상치 못한 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
                    }
                }catch(e){alert(e.message); }
                
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            hideProcessing();
        }
        
    });
    
    hideProcessing();

}

function plm2hompage(oid){
    
    showProcessing();

    if(!confirm("정말로 해당 부품을 홈페이지로 이관하시겠습니까?\n전송된 정보는 홈페이지에 공개되므로 각별히 주의하여야합니다.")){
        hideProcessing();
        return;
    }
    
    $.ajax({
        
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/plm2hompage.do?partOid='+oid,
        //data: $('[name=partForm]').serialize(),
        success: function (data) {
            
            if(data != 'Fail'){
                
                try{
                    if(data=='Y'){
                         
                        alert("해당 부품을 홈페이지로 이관하였습니다.");
                         
                    }else if(data=='N'){
                        alert("부품 홈페이지 인터페이스 결과가 실패입니다.");
                    }else if(data=='E'){
                        alert("부품 홈페이지 인터페이스 중 예상치 못한 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
                    }
                }catch(e){alert(e.message); }
                
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            hideProcessing();
        }
        
    });
    
    hideProcessing();

}

function EcoUsePart(oid){
	var ecoid = "";
	$.ajax({
        
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/ecoUsePart.do?partOid='+oid,
        //data: $('[name=partForm]').serialize(),
        success: function (data) {
        	ecoid = data;
        }
    });
    
    return ecoid; 
}

// 버전이력 팝업
function viewVerHistory(poid){

    viewVersionHistory(poid);
    //var url ="/plm/servlet/e3ps/VersionHistoryServlet?oid=" + poid;
    //openWindow3(url,"버전이력보기","750","400");
}

// 결재이력 팝업
function viewWFHistory(ecoId){
    //viewHistory(poid);
    // 결재내역 확인
    var addr = "/plm/ext/part/base/viewWFHistory.do?ecoId=" + ecoId;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    var topener = getOpenWindow2(addr, "history", 1024, 800, opts);
    // var topener = window.open(addr, "history" ,"toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=1024,height=800");
    topener.focus();
}

//viewable 재생성
function regenerateViewable(oid){

    $.ajax({
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/regeneateVW.do?oid=' + oid,
        success: function (data) {
            if(data != 'Fail'){
                alert('썸네일을 재생성을 시작했습니다. 경우에 따라서 재생성하는 데 몇 분이 걸릴 수 있습니다.');
            }else{
                alert("썸네일을 재생성 에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("썸네일을 재생성 에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
            hideProcessing();
        }
    });
}


function scrapAutoGen(oid){
    
    showProcessing();

    if(!confirm("스크랩을 생성하시겠습니까?")){
        hideProcessing();
        return;
    }
    
    $.ajax({
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/scrapAutoGen.do?oid='+oid,
        data: $('[name=partForm]').serialize(),
        success: function (data) {
            if(data != 'Fail'){
            	try{
                    if(data=='Y'){
                         
                        alert("스크랩이 생성되었습니다.");
                         
                    }else if(data=='N'){
                        alert("스크랩이 이미 존재합니다.");
                    }else if(data=='E'){
                        alert("스크랩 생성 중 예상치 못한 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
                    }
                }catch(e){alert(e.message); }
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            hideProcessing();
        }
    });
    
    
    hideProcessing();

}

-->
</script>

</script>
<body class="body">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td valign="top">    
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	                <td align="left">* 기본정보</td>
	                <td align="right">
	                    <table border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none":""%>">
	                        <tr>
	                            <td>
	                                <table border="0" cellspacing="0" cellpadding="0">
	                                    <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
	                                                    href="javascript:viewPartList('${baseDto.partOid}');" class="btn_blue">Part List</a></td>
	                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                            </tr>
	                                        </table>
	                                    </td>
	                                    <td width="5">&nbsp;</td>
                                        <%-- viewable 생성 --%>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:regenerateViewable('${baseDto.partOid}');" class="btn_blue">썸네일 재생성</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
	                                    <%-- bizAdmin, 승인완료된 것만 --%>
	                                    <c:if test="${ fn:endsWith(baseDto.partStateCode, 'APPROVED') && isSpecAdmin}" >
	                                    <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
	                                                    href="javascript:registCopyPart('${baseDto.partOid}');" class="btn_blue">부품복사생성</a></td>
	                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                            </tr>
	                                        </table>
	                                    </td>
	                                    </c:if>
	                                    <c:choose>  
	                                      <%-- bizAdmin, 상태:INWORK --%>
					                      <c:when test="${fn:endsWith( baseDto.partStateCode, 'INWORK') && (isSpecAdmin || isEcoOwner) }"><%-- 작업중 수정 --%>
		                                        <td width="5">&nbsp;</td>
		                                        <td>
		                                            <table border="0" cellspacing="0" cellpadding="0">
		                                                <tr>
		                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
		                                                        href="javascript:modifyPart('${baseDto.partOid}');" class="btn_blue">수정</a></td>
		                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		                                                </tr>
		                                            </table>
		                                        </td>
	                                      </c:when>
	                                      <%-- isAdmin, 상태:APPROVED --%>
					                      <c:when test="${fn:endsWith(baseDto.partStateCode, 'APPROVED') && (isAdmin || isPartModify)}"><%-- 결재완료후 수정 --%>
		                                        <td width="5">&nbsp;</td>
		                                        <td>
		                                            <table border="0" cellspacing="0" cellpadding="0">
		                                                <tr>
		                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
		                                                        href="javascript:modifyPartAfterApp('${baseDto.partOid}');" class="btn_blue">수정(Admin)</a></td>
		                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		                                                </tr>
		                                            </table>
		                                        </td>					                      
	                                      </c:when>
					                      <c:otherwise>
					                      </c:otherwise>
					                    </c:choose>
	                                    <td width="5">&nbsp;</td>
	                                    <%-- isAdmin, 0 리비전 상태:INWORK, 최신 --%>
	                                    <c:if test="${baseDto.partLatestVersion && baseDto.partRevision=='0' && fn:endsWith( baseDto.partStateCode, 'INWORK') && isAdmin }">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:deletePart('${baseDto.partOid}');" class="btn_blue">삭제</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        </c:if>
                                        <%-- isAdmin, 상태:APPROVED, 최신 --%>
                                        <c:if test="${baseDto.partLatestVersion && fn:endsWith( baseDto.partStateCode, 'APPROVED') && isAdmin }">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:notUsePart('${baseDto.partOid}');" class="btn_blue">사용안함(Admin)</a></td>
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
                                                        href="javascript:plm2hompage('${baseDto.partOid}');" class="btn_blue">홈페이지 이관(S)</a></td>
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
                                                        href="javascript:plm2hompageMulti();" class="btn_blue">홈페이지 이관(M)</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        </c:if>
                                        <%--isAdmin,isKetn 상태:not APPROVED, 최신 --%>
                                        <c:if test="${baseDto.partLatestVersion && !fn:endsWith( baseDto.partStateCode, 'APPROVED') && isAdmin && isKetn}">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:KetnPartSend('${baseDto.partOid}');" class="btn_blue">ERP전송(KETN)</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        </c:if>
                                        <%-- bizAdmin, 상태무관 --%>
                                        <c:if test="${(baseDto.spPartType=='F' || baseDto.numberingCode == 'RK30') && isSpecAdmin && !hasHalbPartOfFert }">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:registHalbPart('${baseDto.partOid}','${baseDto.numberingCode}');" class="btn_blue">반제품등록</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        </c:if>
                                        <%-- bizAdmin, 상태무관 --%>
                                        <c:if test="${baseDto.spPartType=='F' && fn:startsWith(baseDto.classCode,'Z10') && isSpecAdmin  && !hasHawaPartOfFert}">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:registHawaPart('${baseDto.partOid}','${baseDto.numberingCode}');" class="btn_blue">상품등록</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        </c:if>
                                        <%-- (bizAdmin, 연구개발*팀|커넥터*팀|센터), 상태무관 --%>
                                        <c:if test="${baseDto.spPartType=='H' && ( isAdmin || isSpecAdmin ) }">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:registDieSetPart('${baseDto.partMastOid}');" class="btn_blue">금형(SET)등록</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        </c:if>
                                        
                                        <c:if test="${baseDto.numberingCode == 'R20' && baseDto.partLatestVersion && isAdmin }">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:scrapAutoGen('${baseDto.partOid}');" class="btn_blue">스크랩생성</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        </c:if>
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
                <table cellpadding="0" class="table-style-01" summary="" style="table-layout: fixed;">
                    <colgroup>
                        <col width="13%" />
                        <col width="20%" />
                        <col width="13%" />
                        <col width="21%" />
                        <col width="13%" />
                        <col width="21%" />
                    </colgroup>
                    <tbody id="partSpecPropTbody" >
                        <tr>
                            <td class="title">부품번호</td>
                            <td>${baseDto.partNumber}</td>
                            <td class="title">Rev</td>
                            <td><a href="javascript:viewVerHistory('${baseDto.partOid}');">${baseDto.spPartRevision}</a></td>
                            <!--  img src="images/sample_component.png" /-->
                            <td colspan="2" rowspan="6" class="center" valign="middle">
                               <%--
                               <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
                                    <jsp:param name="oid" value="${baseDto.partOid}"/>
                                </jsp:include>
                                <td width="25%" rowspan="5">
				                    <iframe marginwidth="0" marginheight="10" scrolling="no"  width="300" height="200"  src="/Windchill/extcore/jsp/caddoc/thumnail.jsp?oid=< broker.getOid() >" frameborder="0"></iframe>
				                </td>
                                --%>
                                <iframe marginwidth="0" marginheight="10" scrolling="no"  width="300" height="200"  src="/plm/jsp/edm/thumnail.jsp?oid=${baseDto.partOid}" frameborder="0"></iframe>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">부품명</td>
                            <td colspan="3">${baseDto.partName}</td>
                        </tr>
                        <tr>
                            <td class="title">프로젝트 번호</td>
                            <td><a href="javascript:openProject('${baseDto.projectNo}');">${baseDto.projectNo}</a></td>
                            <td class="title">개발단계</td>
                            <td>
                            <c:choose>  
                            <c:when test="${baseDto.spPartDevLevel=='PC003'}">개발</c:when>
                            <c:when test="${baseDto.spPartDevLevel=='PC004'}">양산</c:when>
                            <c:otherwise></c:otherwise>
                            </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">프로젝트 명</td>
                            <td colspan="3">${baseDto.projectName}</td>
                        </tr>
                        <tr>
                            <td class="title">기본단위</td>
                            <c:set var="partDefaultUnit" value="${fn:replace(baseDto.partDefaultUnit, 'KET_', '')}" />
                            <td><c:out value="${partDefaultUnit}" /></td>
                            <td class="title">상태</td>
                            <c:set var="ecoNo" value="${fn:replace(baseDto.ecoNo, 'ECO-', '')}" />
                            <td><a href="javascript:viewWFHistory('<c:out value="${ecoNo}" />');">${baseDto.partState}</a></td>
                        </tr>                        
                        <tr>
                            <td class="title">부품분류</td>
                            <td>${baseDto.partClazNameKr}</td>
                            <td class="title">부품유형</td>
                            <td><ket:codeValueByCode codeType="SPECPARTTYPE" code="${baseDto.spPartType}"/></td>
                        </tr>
                        <tr>
                            <td class="title">경로</td>
                            <td colspan="5">${baseDto.partClazRoute}</td>
                        </tr>
                        <tr> 
                            <td class="title">홈페이지 등록</td>
                            <td>${baseDto.homepageIF}</td>
                            <td class="title">홈페이지(2D)<br/>등록</td>
                            <td>${baseDto.homepage2DIF}</td>
                            <td class="title">홈페이지(3D)<br/>등록</td>
                            <td>${baseDto.hompage3DIF}</td>
                        </tr>
                        <tr> 
	                        <td class="title">총중량</td>
	                        <td>${baseDto.spTotalWeight}</td>
	                        <td class="title">부품중량</td>
	                        <td>${baseDto.spNetWeight}</td>
	                        <td class="title">스크랩중량</td>
	                        <td>${baseDto.spScrabWeight}</td>
	                    </tr>
	                    <tr>
                            <td class="title">작성부서</td>
                            <td>${baseDto.writeDeptKrName}</td>
                            <td class="title">작성자</td>
                            <td>${baseDto.creatorName}</td>
                            <td class="title">작성일</td>
                            <td>${baseDto.createDate}</td>
                        </tr>
                        <tr>
                            <td class="title">EO No</td>
                            <td><a href="javascript:PartOpenViewEco('${baseDto.ecoNo}')" ><c:out value="${ecoNo}" /></a></td>
                            <td class="title">수정자</td>
                            <td>${baseDto.modifierName}</td>
                            <td class="title">수정일</td>
                            <td>${baseDto.modifyDate}</td>
                        </tr>
                <c:forEach var="result" items="${baseDto.partClassAttrLinkDTOList}" varStatus="status">
                 <c:choose> 
                  <c:when test="${result.partAttributeDTO.attrCode != 'spDevSpec' && result.partAttributeDTO.attrCode != 'hompageImgIF'}">
                    
                   <c:if test="${status.index%3==0}" ><tr id="partSpecPropTr"></c:if>
                            <td class="title">
                                ${fn:replace(result.partAttributeDTO.attrName, '(', '<br/>(')}
                                <c:if test="${result.hpYn}"><span class="red-star">*</span></c:if>
                            </td>
                        <c:choose>  
                         <c:when test="${result.partAttributeDTO.attrInputType=='TEXT'}">
                              <c:choose>  
                              <c:when test="${result.partAttributeDTO.attrCode == 'spRepresentM' || result.partAttributeDTO.attrCode == 'spScrabCode' 
                                    ||result.partAttributeDTO.attrCode == 'spMTerminal' || result.partAttributeDTO.attrCode == 'spMConnector' 
                                    ||result.partAttributeDTO.attrCode == 'spMClip'     || result.partAttributeDTO.attrCode == 'spMRH'
                                    || result.partAttributeDTO.attrCode == 'spMCover'
                                    ||result.partAttributeDTO.attrCode == 'spMWireSeal' || result.partAttributeDTO.attrCode == 'spMatchBulb'}">
                                    <td><ket:partNoMulti code="${result.partStandardValue}" /></td>
                              </c:when>
                              <c:when test="${result.partAttributeDTO.attrCode == 'spManufacPlace' || result.partAttributeDTO.attrCode == 'spDieWhere'}">
                                   <td><ket:manufPlace code="${result.partStandardValue}" /></td>
                              </c:when>                            
                              <c:otherwise>
                                   <td>${result.partStandardValue}</td>
                              </c:otherwise>
		                      </c:choose>
                         </c:when>
                         <c:when test="${result.partAttributeDTO.attrInputType=='SELECT'}">
                             <td>
                             <c:choose>  
                              <%-- 재질 수지 /  재질 비철 --%>
                             <c:when test="${result.partAttributeDTO.attrCode == 'spMaterialInfo' || result.partAttributeDTO.attrCode == 'spMaterNotFe'}">
		                         <ket:materialInfo id="${result.partStandardValue}" />
		                     </c:when>
                             <c:when test="${result.partAttributeDTO.attrMultiSelect}">
                               <c:if test="${not empty result.partStandardValue}">
                               <ket:codeValueByCode codeType="${result.partAttributeDTO.attrCodeType}" code="${result.partStandardValue}"/>
                               </c:if>
                             </c:when>
                             <c:otherwise>
                              <c:if test="${not empty result.partStandardValue}">
                              <ket:codeValueByCode codeType="${result.partAttributeDTO.attrCodeType}" code="${result.partStandardValue}"/>
                              </c:if>
                             </c:otherwise>
                             </c:choose>
                             </td>
                         </c:when>
                         <c:otherwise>
                             <td></td>
                         </c:otherwise>
                      </c:choose>
                      <c:if test="${status.index%3==2}" ></tr></c:if>

         </c:when>
             <c:when test="${result.partAttributeDTO.attrCode == 'spDevSpec'}">
             </tbody><tbody>
           <tr id="partSpecPropTrDevSpec">
                <td class="title">${fn:replace(result.partAttributeDTO.attrName, '(', '<br/>(')}
                    <c:choose>  
                        <c:when test="${result.essential}"><span class="red-star">*</span></c:when>
                        <c:when test="${result.checked}"><span class="blue-star">*</span></c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
                <td colspan="5" class="tdwhiteL0">
                    <textarea id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="textline" class="txt_field" value="${result.partStandardValue}" style="width: 95%" value="" readonly="true">${result.partStandardValue}</textarea>
                </td>
            </tr>  
         </c:when>
         <c:when test="${result.partAttributeDTO.attrCode == 'hompageImgIF'}">
          </tbody>
           <tr id="partSpecPropTrHPImg">
                <td class="title">제품 이미지</td>
                <td colspan="5" class="tdwhiteL0">
                <c:choose> 
                 <c:when test="${primaryFile.contentoid != null}">
                 <input name='primaryFileOid' type='hidden' value='${primaryFile.contentoid}'>
                      <a target='download' href='${primaryFile.downURLStr}'>${primaryFile.iconURLStr}</a>&nbsp;
                      <a href='${primaryFile.downURLStr}' target='_blank'>${primaryFile.name}</a>&nbsp;(&nbsp;${primaryFile.fileSizeKB}&nbsp;)
                 </c:when>
                 <c:otherwise></c:otherwise>
                </c:choose>
                </td>
            </tr>

         </c:when>
          <c:otherwise></c:otherwise>
         </c:choose>
        </c:forEach>  
        
                   </tbody>
             
                  
                  <c:if test="${status.index%3==2}" >
                  </c:if>
                   <c:if test="${status.index%3==1}" >
                        
                         <%-- 끝에 colspan 추가 및 사이즈 조절 --%>
                         <script lang="javascript">
                             $('#partSpecPropTbody tr#partSpecPropTr:last').each(function(){
                                 $(this).find('td:last').attr('colspan',3);
                                
                                 var hasImageTd = false;
                                 $(this).find("td:last img").each(function(){
                                     hasImageTd = true;
                                 });
                                 
                                 $(this).find("td:last [type='text']").each(function(){
                                    //alert($(this).width());
                                     if( !hasImageTd ){ // 95%
                                         $(this).width("21.4%");
                                     }else if( hasImageTd ){ // 70%
                                         $(this).width("15.7%");
                                     }
                                 });
                             });
                         </script>
                   </c:if>
                   <c:if test="${status.index%3==0}" >
                        
                         <%-- 끝에 colspan 추가 --%>
                         <script lang="javascript">
                             $('#partSpecPropTbody tr#partSpecPropTr:last').each(function(){
                                 $(this).find('td:last').attr('colspan',5);
                                 
                                 var hasImageTd = false;
                                 $(this).find("td:last img").each(function(){
                                     hasImageTd = true;
                                 });
                                 
                                 $(this).find("td:last [type='text']").each(function(){
                                     //alert($(this).width());
                                     if( !hasImageTd ){ // 95%
                                         $(this).width("34.5%"); 
                                     }else if( hasImageTd){ // 70%
                                         $(this).width("25.4%"); 
                                     }
                                 });
                             });
                         </script>
                   </c:if>
                   
                   
                </table>
            </td>
       </tr>
</table>


<div id="tabs">
    <ul>
        <li><a class="tabref" href="#tabs-1" rel="/plm/jsp/part/reform/drawingList.jsp?oid=<%=poid%>">도면</a></li>
        <li><a class="tabref" href="#tabs-2" rel="/plm/jsp/part/reform/documentList.jsp?oid=<%=poid%>">문서</a></li>
        <li><a class="tabref" href="#tabs-3" rel="/plm/jsp/part/reform/projectList.jsp?oid=<%=poid%>">프로젝트</a></li>
        <li><a class="tabref" href="#tabs-4" rel="/plm/jsp/part/reform/ecoList.jsp?oid=<%=poid%>">설계변경</a></li>
        <c:choose>  
        <c:when test="${baseDto.spPartType=='D' || baseDto.spPartType=='M'}">
        <li><a class="tabref" href="#tabs-5" rel="/plm/jsp/part/reform/partList.jsp?oid=<%=poid%>">부품</a></li>
        </c:when>
        <c:otherwise>
        <li><a class="tabref" href="#tabs-6" rel="/plm/jsp/part/reform/moldList.jsp?oid=<%=poid%>">금형</a></li>
        </c:otherwise>
        </c:choose>
        <li><a class="tabref" href="#tabs-7" rel="/plm/jsp/part/reform/dqmList.jsp?oid=<%=poid%>">개발품질문제</a></li>
        <li><a class="tabref" href="#tabs-8" rel="/plm/jsp/part/reform/sampleHistoryList.jsp?oid=<%=poid%>">Sample 이력</a></li>
        <c:choose>
        <c:when test="${baseDto.spPartType=='D' || baseDto.spPartType=='M'}">
        <li><a class="tabref" href="#tabs-9" rel="/plm/ext/project/trycondition/listTryCondtionByDieNo.do?dieNo=${baseDto.partNumber}">금형 TRY</a></li>
        </c:when>
        <c:otherwise>
        </c:otherwise>
        </c:choose>
    </ul>
	<!-- 첫번째 tab 화면 -->
	<div id="tabs-1" class="tabMain">
	    <div class="tabIframeWrapper">
	        <iframe class="iframetab" src="/plm/jsp/part/reform/drawingList.jsp?oid=<%=poid%>">Load Failed?</iframe>
	    </div>
	</div>
	<!-- 2번째 tab 화면부터는 rel속성에 정의된 url을 호출합니다. -->   
	<div id="tabs-2">
	</div>
	<div id="tabs-3">
    </div>
    <div id="tabs-4">
    </div>
    <div id="tabs-5">
    </div>
    <div id="tabs-6">
    </div>
    <div id="tabs-7">
    </div>
    <div id="tabs-8">
    </div>
    <div id="tabs-9">
    </div>
</div>