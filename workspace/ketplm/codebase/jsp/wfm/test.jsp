<%@page import="wt.vc.VersionReference"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.org.*" %>
<%@page import = "java.util.*" %>
<%@page import = "java.sql.Timestamp" %>
<%@page import = "wt.workflow.engine.*" %>
<%@page import = "wt.workflow.work.*" %>
<%@page import = "wt.workflow.definer.*" %>
<%@page import = "wt.lifecycle.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.session.*" %>
<%@page import = "wt.vc.*" %>
<%@page import = "wt.folder.*" %>
<%@page import = "wt.team.*" %>
<%@page import = "wt.part.*" %>
<%@page import = "wt.doc.*" %>
<%@page import = "wt.lifecycle.*" %>
<%@page import = "wt.query.*" %>
<%@page import = "e3ps.wfm.entity.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "e3ps.wfm.service.*" %>
<%@page import = "wt.inf.container.*" %>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.common.obj.*" %>
<%@page import = "e3ps.common.folder.*" %>
<%@page import = "e3ps.common.mail.*" %>
<%@page import = "e3ps.groupware.company.*" %>
<%@page import = "wt.util.WTProperties" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
Hashtable data = new Hashtable();
HashMap toMap = new HashMap();
HashMap fromMap = new HashMap();
fromMap.put("EMAIL","slgmoney@ket.com");
fromMap.put("NAME","Administrator");
toMap.put("slgmoney@ket.com","이영주");
data.put("TO",toMap);
data.put("FROM",fromMap);
String oid = "wt.workflow.work.WorkItem:13432758";
WorkItem item = (WorkItem)CommonUtil.getObject(oid);
Kogger.debug(item.toString());
Hashtable contentHash = MailUtil.getMailContent("approval", item, "ost");
MailHtmlContentTemplate contentTemplate = MailHtmlContentTemplate.getInstance();
String htmlmsg = contentTemplate.htmlContent(contentHash, "ApprovalNoticeMail.html");
data.put("CONTENT",htmlmsg);

MailUtil.sendMail2(data);

%>
<body>
Success!!<br>
<br>
</body>
