<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@ page import="ext.ket.part.base.service.internal.*"%>
<%@ page import="ext.ket.part.util.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
String partOid = (String)request.getParameter("partOid");
String partListType = (String)request.getParameter("partListType");

//String createExcelPartList(String partOid, String partListType)
PartListExcelDao dao = new PartListExcelDao();

String downUrl = dao.createExcelPartList(partOid, partListType);
%>
<script type="text/javascript">
<!--
var durl = "<%=downUrl%>";

if(durl=="")
{
    alert("엑셀을 생성하는 중에 오류가 발생하였습니다.\n관리자에게 문의 바랍니다.");	
}else
{
	document.location.href='/plm/extcore/jsp/bom/KETExcelDownLoad.jsp?filepath='+durl;
	
}
-->
</script>