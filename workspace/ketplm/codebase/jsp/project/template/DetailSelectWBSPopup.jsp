<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/common/processing.html"%>   
<%
   String originOid = request.getParameter("origin");
   String copyOid = request.getParameter("oid");
   String moldType = request.getParameter("moldType");
   String invokeMethod = request.getParameter("invokeMethod");
   if ( invokeMethod == null || invokeMethod.length() == 0 )
       invokeMethod = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE>표준 WBS 유형 상세선택</TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<!-- jQuery -->
<script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<script type="text/javascript">
function reSetting(){
	//form data를 json으로 변환
	<%if(!"wbsReSetting".equals(request.getParameter("callBackFn"))){%>
	var jsonArry = $("#reSetting").serializeArray();
	<%}else{%>
	var jsonArry = $("#reSetting").serialize();
	<%}%>
	
	<%  if ( invokeMethod.length() == 0 ) {  %>
    //modal dialog
	    selectModalDialog(jsonArry);
	<%  } else {  %>
	    //open window
	    selectOpenWindow(jsonArry);
	<%  }  %>
	
	//return value에 json객체를 전달한다.
	//window.returnValue= jsonArry;
    //window.close();	
    //showProcessing();
     /* $.ajax({
        type       : "POST",
        url        : "/plm/servlet/e3ps/SearchProjectTemplateServlet",
        data       : $("#reSetting").serialize(),
        dataType   : "json",
        success    : function(data){
            
                     window.opener.opener.location.reload();
                     window.close();
                     opener.window.close();
                
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  */
    
    /* $("#reSetting").attr({action:"/plm/servlet/e3ps/SearchProjectTemplateServlet",method:"post"}).submit();  
    window.opener.opener.location.reload();
    window.close();
    opener.window.close(); */
}

function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
    window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function selectOpenWindow(arrObj) {
    //...이하 원하는 스크립트를 만들어서 작업...
    if(opener) {
        if(opener.<%=invokeMethod%>) {
            opener.<%=invokeMethod%>(arrObj);
        }
    }else if(parent.opener) {
        if(parent.opener.<%=invokeMethod%>) {
            parent.opener.<%=invokeMethod%>(arrObj);
        }
    }
    self.close();
}

<%  }  %>
</script>
</head>
<body>
<form id="reSetting">
<input type="hidden" name="originOid" value="<%=originOid%>">
<input type="hidden" name="copyOid" value="<%=copyOid%>">
<input type="hidden" name="mode" value="reSetting">
<table style="width: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">표준 WBS 유형 상세선택</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="space10"></td>
    </tr>
    <tr>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="5">&nbsp;</td>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                    href="javascript:reSetting();" class="btn_blue">확인</a></td>
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
                                    href="javascript:window.close();" class="btn_blue">닫기</a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="space10"></td>
    </tr>
    <tr>
        <td>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
    
<% if(moldType.equals("product")){ %>
                <tr>
                    <td width="90" height="130" class="tdblueL">구분</td>
                    <td class="tdwhiteL0">
                        <input type="radio" name="productType" value="newType" checked/> 신규
                        <input type="radio" name="productType" value="modifyType" /> Modify
                    </td>
                </tr>
<% } %>    
    
<% if(moldType.equals("review") || moldType.equals("product")  || moldType.equals("work")){ %>
               <tr>
                    <td width="90" height="130" class="tdblueL">Category</td>
                    <td class="tdwhiteL0">
                       <input type="checkbox" name="category" value="common" checked />공통
                       <input type="checkbox" name="category" value="mdraw"/>기구
                       <input type="checkbox" name="category" value="hw"/>HW
                       <input type="checkbox" name="category" value="sw"/>SW
                       <input type="checkbox" name="category" value="m"/>M
                       <input type="checkbox" name="category" value="p"/>P
                       <input type="checkbox" name="category" value="buy"/>구매
                       <input type="checkbox" name="category" value="system"/>설비
                    </td>
                </tr>
<% } %>
            </table>
        </td>
     </tr>
</table>
</form>
</body>
</html>