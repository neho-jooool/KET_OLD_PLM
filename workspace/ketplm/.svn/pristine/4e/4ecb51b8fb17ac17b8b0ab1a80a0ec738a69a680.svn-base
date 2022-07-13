<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.session.*"%>
<%@page import = "wt.query.*"%>
<%@page import = "wt.fc.*"%>
<%@page import = "wt.fc.ObjectIdentifier"%>
<%@page import = "wt.org.WTPrincipal, wt.org.*"%>
<%@page import = "wt.session.SessionHelper"%>
<%@page import="wt.doc.*,java.util.*,wt.folder.*" %>
<%@page import="wt.fc.*,wt.query.*"%>
<%@page import="wt.util.*"%>
<%@page import="java.io.*"%>
<%@page import="wt.clients.folder.FolderTaskLogic"%>
<%@page import="e3ps.common.util.CommonUtil,e3ps.groupware.company.*"%>
<%@page import = "e3ps.common.jdf.config.Config,
                e3ps.common.jdf.config.ConfigImpl
                "%>
<%@page session="true"%>
<jsp:useBean id="foid" class="java.lang.String" scope="request" />
<jsp:useBean id="expanded" class="java.util.Hashtable" scope="request" />
<jsp:useBean id="state" class="java.lang.String" scope="request" />
<jsp:useBean id="error" class="java.lang.String" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>

<%
    if ( foid == null ) foid="";

    //Add(2005.05.10)
    PeopleData data = new PeopleData();
    String datapeople = CommonUtil.getOIDString(data.people);

    Config conf = ConfigImpl.getInstance();
    String company_Name = conf.getString("people.company.name").trim();
%>

<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>

<LINK rel="stylesheet" type="text/css" href="../css/e3ps.css">
<script src="../js/newtree.js"></script>
<script src="../js/trcolor.js"></script>
<script language=javaScript>
<!--
function showhide(what)
{
    if (what.style.display=='none')
    {
        what.style.display='';
    //what2.src=Open.src
    }
    else
    {
    what.style.display='none'
    //what2.src=Closed.src
    }
}
-->
</script>
</head>
<body  marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" >
<form>
<script src='../js/menu.js'></script>

<%
    String needMenu = "true";
    if (request.getParameter("needMenu") !=null )needMenu = request.getParameter("needMenu");
    if (needMenu.equals("true")){
%>


<% }%>


<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td valign=top  background="../images/img_common/ds_sub.gif" bgcolor=EDE9DD>
			<!-- left_title// -->
			<table cellpadding=0 cellspacing=0 border=0>
			<tr>
			<td>
			<p><img src="../images/img_groupware/sub_work1.gif" width="180" height="41" border="0"></p>
			</td>
			</tr>
			</table>
			<!--left_title//-->
			<!--left_menu//-->
			<table width=179  border="0" cellpadding="0" cellspacing="1" bgcolor=white>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr0' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
					<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/servlet/e3ps.groupware.board.servlet.ManageNotifyServlet','tr0','공지사항')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "00889") %><%--공지사항--%></p>
					</td>
				</tr>
				<!-- 임시 메뉴 자료실 -->
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr90' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
					<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/servlet/e3ps.groupware.board.servlet.ManageBoardServlet?category=게시판','tr90','게시판')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "00743") %><%--게시판--%></p>
					</td>
				</tr>
				<!-- 임시 메뉴 끝 -->
				<%--
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr00' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/groupware/workprocess/form/CreateFormProcess.jsp','tr00','결재작성')" id=sub  onFocus="this.blur()" >결재작성</a></p>
					</td>
				</tr>
				--%>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr1' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.WorklistServlet','tr1','결재대기함')" id=sub  onFocus="this.blur()" ><%=messageService.getString("e3ps.message.ket_message", "00760") %><%--결재대기함--%></a></p>
					</td>
				</tr>
				<%if(CommonUtil.isAdmin()){ %>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr5' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.WorklistServlet&key=status&value=COMPLETED&key=order&value=workComplete','tr5','작업목록 완료함')" id=sub  onFocus="this.blur()" ><%=messageService.getString("e3ps.message.ket_message", "02437") %><%--작업목록 완료함--%></a></p>
					</td>
				</tr>
				<%} %>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr2' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.WFItemServlet&key=processtype&value=own','tr2','결재발신함')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00770") %><%--결재발신함--%></a></p>
				</td>
				</tr>
					<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr3' style="mouse:default,backgraoundColor='#ffffff'">
						<td height="24">
						<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.WFItemServlet&key=processtype&value=ing','tr3','결재진행함')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00787") %><%--결재진행함--%></a></p>
						</td>
				</tr>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr4' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.WFItemServlet&key=processtype&value=complete','tr4','결재완료함')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00777") %><%--결재완료함--%></a></p>
					</td>
				</tr>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr11' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.SearchMyFolderServlet','tr11','개인문서함')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00680") %><%--개인문서함--%></a>
						</p>
					</td>
				</tr>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr12' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/doc/PersonalCreateDoc.jsp','tr12','개인문서생성')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00678") %><%--개인문서등록--%></a>
						</p>
					</td>
				</tr>
				<tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr13' style="mouse:default,backgraoundColor='#ffffff'">
					<td height="24">
						<p><img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/groupware/company/peopleView.jsp&key=oid&value=<%=datapeople%>','tr13','개인정보수정')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00681") %><%--개인정보수정--%></a>
						</p>
					</td>
				</tr>
				<%if(CommonUtil.isAdmin()){ %>
				<tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr14' style="mouse:default,backgraoundColor='#ffffff'">
					<td  height="24">
						<img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0">
						<a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/groupware/company/companyfrm.jsp','tr14','<%=company_Name%>조직도')" onFocus="this.blur();" id=sub><%=company_Name%> <%=messageService.getString("e3ps.message.ket_message", "02648") %><%--조직도--%></a>
					</td>
				</tr>
				<%} %>

            </table>
                    <!--left_menu//-->

<%
    String portalOid = request.getParameter("portalOid");
    String portalAction = request.getParameter("portalAction");

    String nextPage = "/plm/servlet/e3ps.groupware.workprocess.servlet.WorklistServlet";
    if(portalOid != null)
    {
        nextPage = "/plm/servlet/WindchillAuthGW/wt.workflow.work.WorkItemURLProcessor/URLTemplateAction?u8&oid=OR:"+portalOid+"&action="+portalAction;
    }
%>

</td>
<td width="100%">
                    <!--우측//-->
                    <table border=0 width=100% height=100%>
                        <tr>
                            <td width="100%" height="20" background="../images/img_default/indexing.gif">
                                <p>&nbsp;<span id=position dataformatas=HTML datafld=position datasrc=#DDR_DSO>
                                <font color=gray><%=messageService.getString("e3ps.message.ket_message", "02372") %><%--읽는중--%></font></span></p><!--인덱싱//-->
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p><iframe frameborder=0 width=100% height=100% src="<%=nextPage%>" name=iframe></iframe></p>
                            </td>
                        </tr>
                    </table>
                    <!--우측//-->
</td>
</tr>
</table>

<form>
<script src='../js/menu.js'></script>
</html>
<input type=hidden name=myname value=결재함>
<script>setPosition(null);</script>
