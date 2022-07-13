<%@page import="e3ps.common.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = "";

    if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
        oid = request.getParameter("oid");
    }
    
    String isModal = request.getParameter("isModal");
    String invokeMethod = StringUtil.checkNull(request.getParameter("invokeMethod"));


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02594") %><%--제품변경사유--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
function onSave(){
	
    var reasonValue = document.forms[0].reason;
    if(reasonValue.value == ""){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02596") %><%--제품변경사유를 입력하세요--%>');
        return;
    }

    /* document.forms[0].action = "/plm/jsp/project/ProductChangeAction.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit(); */
    $.ajax( {
        url : "/plm/jsp/project/ProductChangeAction.jsp",
        type : "POST",
        data : $('[name=Changeform]').serialize(),
        async : false,
        success: function(data) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02460")%><%--저장되었습니다. --%>');
            
            <%  if ( invokeMethod.length() > 0 ) {  %>
                //open window
                selectOpenWindow("OK");
            <%  } else if("Y".equals(StringUtil.checkNull(isModal))) {  %>
            
                //modal dialog
                window.returnValue = 'OK';
                self.close();
            <%  }  %>
            
            
        },
        fail : function(){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장 실패 하였습니다. --%>');
            hideProcessing();
        }
    });
    
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

    /*
    var reasonValue = document.forms[0].reason;
    if(reasonValue.value == ""){
        window.returnValue = null;
    }else{
        window.returnValue = reasonValue.value;
    }
    window.close();*/
}

function closePopup(){
	if('Y' == '<%=StringUtil.checkNull(isModal)%>'){
        window.returnValue = null;	
	}
    self.close();
}
</script>
</head>
<body>
    <form name="Changeform">
        <input type="hidden" name="oid" value="<%=oid%>"></input>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02594")%><%--제품변경사유--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="70%"></td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <td><table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onclick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table></td>
                                            </table></td>
                                        <td>&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <td><table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table></td>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="460">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="460">
                                    <tr>
                                        <textarea name="reason" style="width: 100%" rows="4" class="fm_area"></textarea>
                                    </tr>

                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="460">
                                    <tr>
                                        <td class="space15"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>
