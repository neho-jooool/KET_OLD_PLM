<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;

    if( searchCondition != null && !searchCondition.isEmpty() ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End
%>


<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->
</head>

<body>
<div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/dms/SearchDocumentGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_TechDocument_List"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
</body>
</html>
