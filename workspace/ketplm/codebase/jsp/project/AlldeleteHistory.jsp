<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String oid[] = request.getParameterValues("oid");

    for(int i = 0 ; i  < oid.length ; i++){
        Kogger.debug("oid ["+i+"] :" + oid[i]);
        E3PSProject project = (E3PSProject)CommonUtil.getObject(oid[i]);
           PersistenceHelper.manager.delete(project);
    }

%>
    <script>
        alert("<%=messageService.getString("e3ps.message.ket_message", "01692") %><%--삭제 되었습니다--%>");
        opener.document.location.reload();
        window.close();
    </script>
