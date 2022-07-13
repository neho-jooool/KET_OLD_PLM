<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="e3ps.common.util.DateUtil"%>
<%@ page import="e3ps.common.web.PageControl"%>
<%@ page import="e3ps.common.util.PropertiesUtil"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    response.addHeader("Cache-Control", "max-age=1, must-revalidate");   

    String pagingList = PropertiesUtil.getSearchPagingSizeList();
    String pagingNameList = PropertiesUtil.getSearchPagingSizeNameList();
    String gridStyle = PropertiesUtil.getSearchGridStyle();
    String cookiesType = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth = PropertiesUtil.getSearchColMinWidth();
    String pageSize = PropertiesUtil.getSearchPagingSizeDefault();
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -3);
    String postdate = DateUtil.getCurrentDateString("d");
    String predate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
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
//전역변수 설정
var pagingList = '<%=pagingList%>';
var pagingNameList = '<%=pagingNameList%>';
var gridStyle = '<%=gridStyle%>';
var cookiesType = '<%=cookiesType%>';
var alternateType = '<%=alternateType%>';
var maxSort = '<%=maxSort%>';
var sortIcons = '<%=sortIcons%>';
var colMinWidth = '<%=colMinWidth%>';
var pageSize = '<%=pageSize%>';
var locale = '<%=messageService.getLocale()%>';
var predate = '<%=predate%>';
var postdate = '<%=postdate%>';
</script>
<%@include file="/jsp/common/multicombo.jsp" %>
</head>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<body>
<%@include file="/jsp/common/processing.html" %>    
    <tiles:insertAttribute name="body" />
</body>
</html>