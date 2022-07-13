<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Vector,
                java.util.List,
                java.util.HashMap,
                java.util.Map,
                java.util.Hashtable"%>

<%@page import="wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.State,
                wt.fc.QueryResult"%>

<%@page import="e3ps.ews.beans.EWSUtil,
                e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.web.PageControl"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<%

    //EJS TreeGrid Start
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

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 진행상태
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "EWSSTEP");
    List<Map<String, Object>> ewsStepNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 생산처 구분-사내/외주
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "VENDORFLAG");
    List<Map<String, Object>> vendorFlagNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    ArrayList inoutCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdinOut")), "," );   //  생산처 구분
    ArrayList stepCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("Hdstep")), "," );     // 진행상태
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "02809") %>BOM편집</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/GridC.js"></script>

<script type="text/javascript">

</script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/jsp/ews/EWSCommon.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

</head>

<body>
<form name="frm" method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" />
<input type="hidden" name="HdinOut" value="" />
<input type="hidden" name="Hdstep" value="" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<!-- hidden end -->

<table style="width: 780px; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 780px;">
        <tr>
            <td>
                <table  style="width: 780px; height: 28px;">
                <tr>
                    <td width="20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01">BOM편집</td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif">BOM
                        <img src="/plm/portal/images/icon_navi.gif">BOM편집
                    </td>
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
        <!-- [END] title & position -->

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/bom/EJSTreeGridTestLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/bom/EJSTreeGridTestData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid Start -->
    </td>

</tr>

<tr>
  <td><a href="http://www.tgantt.com/treegrid/www/Gantt.html#..*Examples*Html*BasicAjax*First.html" target="_blank">Show example</a></td>
</tr>
<tr>
  <td>
    
    <table style="width: 780px; border: 1px;">
      <tr>
        <td>  
		    <div style="WIDTH:100%;HEIGHT:100%">
		        <bdo Debug="1" AlertError="0"
		            Data_Url="/plm/jsp/bom/EJSTreeGridTestData.jsp"
		            
		        ></bdo>
		    </div>
        </td>
      </tr> 
      <!-- tr>
        <td>      		    
		    <div style="width:100%;height:100%;" id='GridTutorialReplace'> 
		        <script>
		        document.write(!location.hash 
		                       ? 'Run me with the name of XML data source to show<br>For example: <pre>ShowTutorial.html#Basic1 Empty grid.xml</pre>' 
		                       : '<bdo Data_Url="/plm/jsp/bom/EJSTreeGridTestData.jsp"> </bdo>'
		                      );
		        </script>
		    </div>                    
		</td>
	  </tr -->    
	</table>
		  
  </td>
</tr>

        
<!-- [START] copyright -->
<tr>
    <td style="height: 30px" valign="bottom">
        <table style="width: 780px;">
        <tr>
            <td style="width: 10px">&nbsp;</td>
            <td style="height: 30px">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 780px; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr>
<!-- [END] copyright -->

</table>
</form>
</body>
</html>
