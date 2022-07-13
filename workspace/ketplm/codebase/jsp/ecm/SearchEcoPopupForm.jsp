<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.ArrayList,
                        java.util.Vector"%>
<%@page import="wt.lifecycle.LifeCycleHelper,
                        wt.lifecycle.LifeCycleTemplate,
                        wt.lifecycle.State"%>

<%@page import = "wt.fc.QueryResult,
                  java.util.ArrayList,
                  java.util.Hashtable,
                  java.util.Calendar,
                  java.text.SimpleDateFormat,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.PropertiesUtil,
                  e3ps.common.util.KETStringUtil,
                  e3ps.common.web.PageControl"%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%

// EJS TreeGrid Start
response.addHeader("Cache-Control","max-age=1, must-revalidate");

String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
String pagingSize = null;


if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

    pagingSize = searchCondition.get("perPage").toString();
}

if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

    pagingSize = pagingSizeDefault;
}
// EJS TreeGrid End

// ECO의 기본사항탭의 Die No 정보
String from = StringUtil.checkNull(request.getParameter("from"));
String[] pNums = request.getParameterValues("pNums");
String pNumsTemp = org.springframework.util.StringUtils.arrayToCommaDelimitedString(pNums);

String prodMoldCls = StringUtil.checkNull(request.getParameter("prodMoldCls"));
if("".equals(prodMoldCls)) {
    prodMoldCls = "";//(1:Prod 2:Mold)
}

String mode = StringUtil.checkNull(request.getParameter("mode"));
if("".equals(mode)) {
    mode = "single";//(single:한건선택 multi:다건선택)
}
String titleChk = "선택";
if("multi".equals(mode)) {
    titleChk = "<input type=\"checkbox\" name=\"chkAllRelEcr\" onclick=\"javascript:checkAll('forms[0]', 'oid', 'chkAllRelEcr');\">";
}

String sortColumn = StringUtil.checkNull(request.getParameter("sortColumn"));
if("".equals(sortColumn)) {
    sortColumn = "1 ASC";
}

String statusAll = StringUtil.checkNull(request.getParameter("statusAll"));

//[START] [PLM System 1차 고도화] 처음 로딩시 검색조건 '작성일자'를 기본값으로 세팅, 2014-06-13, 김태현
if ( searchCondition.isEmpty() ) {
  Calendar cal = Calendar.getInstance();
  searchCondition.put("createEndDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
  cal.add(Calendar.MONTH, -3);
  searchCondition.put("createStartDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
}
//[START] [PLM System 1차 고도화] 처음 로딩시 검색조건 '작성일자'를 기본값으로 세팅, 2014-06-13, 김태현
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00185") %><%--ECO 검색 팝업--%></title>
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
table th, table td {padding: 0}

img {
    vertical-align: middle;
}

input {
    vertical-align:middle;line-height:22px;
}

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.nameCut{
width:175;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<!-- EJS TreeGrid Start -->
<script src="../../portal/js/treegrid/GridE.js"></script>
<script src="../../portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script language=JavaScript src="/plm/jsp/ecm/SearchEcoPopup.js?ver=0.3"></script>
<script type="text/javascript">
$(document).ready(function(){
    SuggestUtil.bind('ECONO', 'ecoId');
    
    //자동완성, 달력 bind 시작
    CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');

    SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	doSearch();
        }
    });
 
    
    <%
    if(from != null && from.equalsIgnoreCase("MoldEco")) {
    %>
	    // 로딩시 pNums가 있을 경우 바로 검색결과를 보여준다.
	    var pNums = $("input[name='pNums']").val();
	    if(pNums != null && pNums != '' && typeof pNums != 'undefined') {
	        var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
	        try {
	            showProcessing();
	        } catch(e) {}
	        
	        document.forms[0].target = "_self";
	        document.forms[0].action = url;
	        document.forms[0].method = "post";
	
	        document.forms[0].cmd.value = "popupEcoSearchInit";
	        document.forms[0].submit();
	        
	    }
    <%
    }
    %>
    
})
</script>
<script language="javascript">
var mode = "<%=mode%>";
</script>
</head>
<body>
<form name="theForm">
<input type="hidden" name="cmd" value="popupEcoSearch">
<input type="hidden" name="prodMoldCls" value="<%=prodMoldCls%>">

<% if("Y".equals(statusAll)) { %>
    <input type="hidden" name="sancStateFlag" value="">
<% }else{ %>
    <input type="hidden" name="sancStateFlag" value="APPROVED">
<% } %>

<input type="hidden" name="pNums" value="<%=pNumsTemp %>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00184") %><%--ECO 검색--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%">
        <tr>
          <!-- td width="10">&nbsp;</td -->
          <td valign="top"><table width="100%">
            <tr>
              <td>&nbsp;</td>
              <td align="right">
		        <table border="0" cellspacing="0" cellpadding="0">
		          <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doClear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                    <td width="5">&nbsp;</td>		          
		            <td><table>
		                <tr>
		                  <td width="10px"><img src="/plm/portal/images/btn_1.gif"></td>
		                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSearch();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
		                  <td width="10px"><img src="/plm/portal/images/btn_2.gif"></td>
		                </tr>
		              </table></td>
		            <td width="5">&nbsp;</td>
		            <td align="center"><table>
		              <tr>
		                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSelect();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
		                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		              </tr>
		            </table></td>
		          </tr>
		        </table>
			  </td>              
            </tr>
          </table>
            <table width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table width="100%">
              <tr>
                <td width="95px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00199") %><%--ECO번호--%></td>
                <td class="tdwhiteL">
                  <input name="ecoId" type="text" class="txt_field" id="textfield4"  style="width:98%" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecoId") )%>" >
                </td>
                <td width="95px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td class="tdwhiteL0">
                  <input type="hidden" name="creatorOid" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorOid") )%>" >
                  <input type="text" name="creatorName" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorName") )%>" class="txt_field" style="width:80px">
                  <a href="javascript:popupUser();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                  <a href="javascript:clearUser();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>                  
                </td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
                <td colspan="3" class="tdwhiteL0">
                  <input type="text" name="ecoName" id="ecoName" class="txt_field" style="width:99%" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecoName") )%>" ></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td colspan="3" class="tdwhiteL0">
                    <input type="text" id="createStartDate" name="createStartDate" class="txt_field" style="width:70px;" value="<%=StringUtil.checkNull( (String)searchCondition.get("createStartDate") )%>" >
                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createStartDate');" style="cursor: hand;">
                    &nbsp;~&nbsp;
                    <input type="text" readonly id="createEndDate" name="createEndDate" class="txt_field"  style="width:70px;" value="<%=StringUtil.checkNull( (String)searchCondition.get("createEndDate") )%>" >
                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createEndDate');" style="cursor: hand;">
                </td>
              </tr>
            </table>
            <table width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>

                        <div style="WIDTH:100%;HEIGHT:100%">
                            <bdo Debug="1" AlertError="0"
                                Layout_Url="/plm/jsp/ecm/SearchEcoPartPopupGridLayout.jsp"
                                Layout_Param_Pagingsize="<%=pagingSize %>"
                                Data_Url="/plm/jsp/common/searchGridData.jsp"
                                Data_Method="POST"
                                Data_Param_Result="<%=tgData %>"
                                Data_Param_Pagingsize="<%=pagingSize %>"
                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File=""
                            ></bdo>
                        </div>

      </table></td>
  </tr>
</table>
<!-- table width="470px">
  <tr>
    <td width="10">&nbsp;</td>
    <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table -->
</form>
</body>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</html>
