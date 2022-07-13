<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.dao.TaskReportDao"%>
<%@page import="e3ps.common.util.PlmDBUtil"%>
<%@page import="e3ps.common.web.ParamUtil"%>



<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@include file="/jsp/common/context.jsp"%>
<stdinfo>
    <results>
        <contents>
        <data_info>
<%
    String deptCodeValue = request.getParameter("deptCodeValue");
    String child = ParamUtil.getParameter(request, "child");

// 	deptCodeValue= java.net.URLDecoder.decode(deptCodeValue==null?"":deptCodeValue,"euc-kr");
// 	child= java.net.URLDecoder.decode(child==null?"":child,"euc-kr");

    // 커넥션 생성
    Connection conn = PlmDBUtil.getConnection();
    ArrayList deptList = new ArrayList();
    try {
        TaskReportDao taskReportDao = new TaskReportDao(conn);
        deptList = taskReportDao.getDeptLevel1(deptCodeValue);
    }catch(Exception ex){
	Kogger.error(ex);
    }finally{
        conn.close();
    }

    Hashtable taskDepartment = null;
    String numCodeName = "";
    String numCodeOid = "";
    for ( int i = 0 ; i < deptList.size(); i++ ) {
        taskDepartment = (Hashtable)deptList.get(i);
        if ( !taskDepartment.get("name").equals("선행해석팀") ){
              numCodeOid  = (String)taskDepartment.get("code");
              numCodeName = (String)taskDepartment.get("name");
%>
            <l_name><![CDATA[<%=numCodeName%>]]></l_name>
            <l_oid><![CDATA[<%=numCodeOid%>]]></l_oid>
<%
        }
    }
%>
        <message>
            <l_child><![CDATA[<%=child%>]]></l_child>
        </message>
        </data_info>
        </contents>
    </results>
</stdinfo>
