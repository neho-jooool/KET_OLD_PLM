<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />


<%@page import="jxl.Workbook"%>
<%@page import="jxl.Sheet"%>
<%@page import="java.util.Hashtable"%>
<%@page import="jxl.Cell"%>
<%@page import="java.io.File"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/common/processing.html" %>
<title>Excel Load</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script>
function onSubmit(){
    if(document.forms[0].roleUpFile.value == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00234") %><%--Excel 양식 파일을 선택 해주십시오--%>");
        return;
    }
   /*  document.forms[0].action = "/plm/jsp/project/TaskExcelUpLoad.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit();
     */
    
    showProcessing();
    
    $.ajax({
        type : 'post',
        url : '/plm/jsp/project/TaskExcelUpLoad.jsp',
        data : $("[name=excelForm]").serializefiles(),
        processData : false,
        contentType : false,
        success : function(data) {
        	hideProcessing();
        	if(data.trim() == ""){
        		alert("<%=messageService.getString("e3ps.message.ket_message", "00303") %>");	
        	}else{
        		alert(data.trim());
        	}
            opener.gridReload();
            window.close();
        	
        	
        },
        error : function() {
            alert('등록에 실패하였습니다.');
            // 프로그레스바 숨기기
            hideProcessing();
        }
    });
    
}
</script>
</head>
<%
    String oid = request.getParameter("oid");
%>
<body>
<form method=POST enctype="multipart/form-data" name="excelForm">
<input type="hidden" name="oid" value="<%=oid%>"></input>


<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01">Task Load</td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>

                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onSubmit();" class="btn_blue">Load</a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
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
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <input type="file" name="roleUpFile" style="width:100%;"></input>

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
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
