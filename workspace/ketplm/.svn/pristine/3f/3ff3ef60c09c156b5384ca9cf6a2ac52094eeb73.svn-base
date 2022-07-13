<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />
<jsp:useBean id="currentPageNo" class="java.lang.String" scope="request" />
<jsp:useBean id="pageSize" class="java.lang.String" scope="request" />
<%

    response.addHeader("Cache-Control","max-age=1, must-revalidate");

%>
<Grid><Body><B Count="<%=pageSize%>" Pos="<%=currentPageNo%>" ><%=tgData%></B></Body></Grid>