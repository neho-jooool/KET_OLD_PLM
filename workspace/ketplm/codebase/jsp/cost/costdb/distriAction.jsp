<?xml version="1.0" encoding="euc-kr" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=euc-kr" pageEncoding="euc-kr"%>

<%@page import="java.util.*"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.sql.Connection"%>
<stdinfo>
	<results>
		<contents>

		<data_info>
<%

		String code = StringUtil.checkNull(request.getParameter("code"));
		String child = StringUtil.checkNull(request.getParameter("child"));

		//code= java.net.URLDecoder.decode(code==null?"":code,"euc-kr");
		//child= java.net.URLDecoder.decode(child==null?"":child,"euc-kr");

		// 커넥션 생성
		Connection conn = null;
		conn = DBUtil.getConnection();
		ArrayList deptList = new ArrayList();
		try{
			CostComDao dao = new CostComDao(conn);

			deptList = dao.getSearchDest(code,child);

		}catch(Exception e){
			e.printStackTrace();
			e.getMessage();
		}
		if(conn!=null) {conn.close();}
		Hashtable deptHash = null;
		String numCodeName = "";
		String numCodeOid = "";
		String distri_cost = "";
		for(int i = 0 ; i < deptList.size(); i++){
			deptHash = (Hashtable)deptList.get(i);
			if(child.equals("store")){
				numCodeOid = (String)deptHash.get("dest");
				numCodeName = (String)deptHash.get("dest");
			}else{
				distri_cost = (String)deptHash.get("distri_cost");
			}
%>
			<l_name><![CDATA[<%=numCodeName%>]]></l_name>
			<l_oid><![CDATA[<%=numCodeOid%>]]></l_oid>
<%
		}
%>
    <message>
			<l_child><![CDATA[<%=child%>]]></l_child>
			<l_distri><![CDATA[<%=distri_cost%>]]></l_distri>
		</message>
    </data_info>
		</contents>
	</results>
</stdinfo>


