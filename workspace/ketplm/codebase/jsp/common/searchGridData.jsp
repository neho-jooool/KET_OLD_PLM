<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%
    /*-----------------------------------------------------------------------------------------------------------------
      ! Support file only, run Grid.html instead !
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/

    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String resultStr  = request.getParameter("Result");
%>

<?xml version="1.0"?>
<Grid>
    <Body>
        <B><%=resultStr%></B>
    </Body>
</Grid>
