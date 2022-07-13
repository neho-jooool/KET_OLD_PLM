<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%
String partNumber  = (String)request.getParameter("partNo");
String seq  = (String)request.getParameter("seq");
//if(partNumber==null || partNumber.equals(""))
    //partNumber = "S1";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품검색--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<%@include file="/jsp/common/multicombo.jsp" %>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/bom/bom.js"></script>
<script type="text/javascript">
<%
if(seq.equals("9999"))
    out.println("window.close();");
%>
$(document.partList).ready(function(){
	//부품 suggest
	//alert('1');
    SuggestUtil.bind('PARTNO', 'partNo');
    //alert('2');
});
</script>
<script type="text/javascript">
function doSearch()
{
	var partNumber = document.search.partNo.value;
	document.partList.location.href = "./searchScrapPart.jsp?partNo="+partNumber;
}

function doSelect()
{
	
	var result = document.partList.checkedData();	
	var seq = <%=seq%>;	
	
	opener.scrapR10addFn(result, seq);
	
}

function searchPart(){
	var partNumber = document.search.partNo.value;
    document.partList.location.href = "./searchScrapPart.jsp?partNo="+partNumber;
}

</script>
</head>
<body onLoad="">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품검색--%></td>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <tr>
             <td height="10"></td>
        </tr>
      </table>
    </td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                 </table> 
                 </td>
              </tr>
            </table></td>
        </tr>
      </table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      
            
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>     
       
       <form name="search">
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%><span class="red">*</span></td>
          <td width="80%" class="tdwhiteL"><input type="hidden" name="seq" value="<%=seq%>"><input type="text" id="partNo" name="partNo" class="txt_field" value="<%=partNumber%>" style="width: 70%"> 
                            
                            
          </td>
        </tr>
      </table>
      
        </form>
        
      </td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
<table width="100%" height="400" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>      
       <table width="100%" height="400" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td height="400"><iframe id="partList" name="partList" src="searchScrapPart.jsp?partNo=<%=partNumber %>" style="width:100%; height:400px;border:0px;" frameborder="0"></iframe></td>
        </tr>
      </table>
      </td>
</tr>
</table>
<iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
</body>
</html>