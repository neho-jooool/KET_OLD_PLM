<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>

<script language='javascript'>
$(document).ready(function(){
    //------------ start suggest binding ------------//
    SuggestUtil.bind('PARTNO', 'number');
});
function valCheck(){
	if(document.forms[0].BomUpFile.value!="" && !fileFilter(document.forms[0].BomUpFile)){
		return false;
	}
	return true;
}

//저장
function exeMigration(){
	showProcessing();
	if(!valCheck()){
		alert("지정된 템플릿파일의 확장자가 아닙니다.");
	}else{
       /*document.forms[0].action = '/plm/servlet/e3ps/PartServlet?cmd=mig';
       document.forms[0].method = "post";
       document.forms[0].submit();*/
       $.ajax({
    	   url : "/plm/servlet/e3ps/PartServlet?cmd=mig",
    	   data : $("[name=BomUploadForm]").serializefiles(),
    	   processData : false,
    	   contentType : false,
	       type : "POST",
	       dataType : 'json',
	       //async : false,
	       success: function(data) {
	    	   $.each(data.returnObj, function() {
	    		   alert(this.msg);
	    	   });
	    	   hideProcessing();
	       },
	       fail : function(){
	    	   alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
	           hideProcessing();
	       }
	   });
       
	}
}

function fileFilter(obj){
	var cnt = 0;
	var filterStr = new Array("XLS");
	var str = obj.value.substring(obj.value.lastIndexOf(".")+1).toUpperCase();
	for(i=0; i<filterStr.length; i++){
		if(str==filterStr[i]){
			return true;
	    }
    }
	return false;
}
</script>
</head>
<body class="body">
<form name="BomUploadForm" method="post" enctype="multipart/form-data">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01">BOM I/F</td>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif">BOM<img src="../../portal/images/icon_navi.gif">BOM I/F</td>
              </tr>
              </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <!-- START :: 제품 Migration  -->
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td background="../../portal/images/btn_bg1.gif"><a href="javascript:exeMigration();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02068") %><%--실행--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
              </table>
              </td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td colspan="3" class="tdwhiteL0">
            <input name="partType" type="radio" class="Checkbox" value="P" checked><%=messageService.getString("e3ps.message.ket_message", "02537") %><%--제품 BOM--%>&nbsp;&nbsp;
            <input name="partType" type="radio" class="Checkbox" value="M"><%=messageService.getString("e3ps.message.ket_message", "00998") %><%--금형 BOM--%>

          </td>
        </tr>

        <tr>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="290" class="tdwhiteL"><input type="text" name="number" class="txt_field"  style="width:270" id="textfield2"></td>
        </tr>
        <tr>
          <td width="70" class="tdblueM">Excel</td>
          <td class="tdwhiteL"><input type="file" accept="application/vnd.ms-excel" name="BomUpFile"/>&nbsp;&nbsp;<a href="/plm/extcore/jsp/bom/KETExcelTemplateDownload.jsp?filepath=BomMigTemplate.xls"><img src="/plm/portal/images/iocn_excel.png" alt="excel down" name="leftbtn_02" border="0"></a></td>
        </tr>

      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <!-- END :: 제품 Migration  -->
  </tr>
</table>
</form>
</body>
</html>
