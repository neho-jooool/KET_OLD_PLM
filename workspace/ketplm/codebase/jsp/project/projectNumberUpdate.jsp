<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.project.*,
                 e3ps.project.beans.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.groupware.company.*,wt.fc.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%@page import="e3ps.common.jdf.config.Config"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>

<%
String oid = request.getParameter("oid");

E3PSProject E3PSProject = (E3PSProject)CommonUtil.getObject(oid);

String divisionCode = E3PSProject.getDivision().getCode();

String tempDate = DateUtil.getDateString(new Date(), "all");
String projectNo = divisionCode + "-" + tempDate.substring(2, 4);

projectNo = projectNo + ManageSequence.getSeqNo(projectNo, "000", "E3PSProjectMaster", E3PSProjectMaster.PJT_NO);;

ProjectMaster master = E3PSProject.getMaster();

master.setPjtNo(projectNo);
PersistenceHelper.manager.save(master);
%>

<script>
alert("<%=messageService.getString("e3ps.message.ket_message", "01500") %><%--변경 완료 했습니다--%>");
</script>
