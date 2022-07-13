<%@page contentType="text/xml"%><%@page pageEncoding="UTF-8"%><jsp:useBean id="rootCount" class="java.lang.String" scope="request" /><jsp:useBean id="pagingLength" class="java.lang.String" scope="request" /><%

    response.addHeader("Cache-Control","max-age=1, must-revalidate");
    int pagingSize = Integer.parseInt(pagingLength);
    if(pagingSize == 0) pagingSize = 1;

    
%><?xml version="1.0"?>
<Grid>
    <Cfg RootCount='<%=rootCount%>' />
    <Body>
       <%
         for( int z=0; z < pagingSize; z++ ){
       %>
        <B></B>
       <%
         }
       %>
    </Body>
</Grid>
