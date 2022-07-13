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
                e3ps.common.jdf.config.ConfigImpl,
                e3ps.groupware.webmail.beans.WebMailStateFlag"%>
<%@page session="true"%>
<jsp:useBean id="foid" class="java.lang.String" scope="request" />
<jsp:useBean id="expanded" class="java.util.Hashtable" scope="request" />
<jsp:useBean id="state" class="java.lang.String" scope="request" />
<jsp:useBean id="error" class="java.lang.String" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>

<%
        WTPrincipal principal = SessionHelper.manager.getPrincipal();
        String cabinet = principal.getIdentity();
        if ( foid == null ) foid="";

        //Add(2005.05.10)
        WTUser user=(WTUser)SessionHelper.manager.getPrincipal();
        String oid = CommonUtil.getOIDString(user);
        PeopleData data = new PeopleData(CommonUtil.getObject(oid));
        String datapeople = CommonUtil.getOIDString(data.people);

        Config conf = ConfigImpl.getInstance();
    String company = conf.getString("company").trim();
    String company_Name = null;
    if(company.equalsIgnoreCase("auto"))
    {
        company_Name = "대기오토모티브";
    }
    else
    {
        company_Name = "대기산업";
    }
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
<script src='../js/menu.js'></script>
<form>
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
   <tr>
        <td width="150" bgcolor="#CCCCCC" valign="top" background="../images/img_default/dk_side_back.gif" height="" valign=top>
<!--left//-->
            <p><img src="../images/img_office/office_sub2-1.gif" width="160" height="20" border="0"></p>
<!--left end//-->
<%
    String needMenu = "true";
    if (request.getParameter("needMenu") !=null )needMenu = request.getParameter("needMenu");
    if (needMenu.equals("true")){
%>

                    <td width="100%" height="20" background="../images/img_default/indexing.gif">
                        <p>&nbsp;<span id=position dataformatas=HTML datafld=position datasrc=#DDR_DSO>
                            <font color=gray><%=messageService.getString("e3ps.message.ket_message", "02372") %><%--읽는중--%></font></span></p>
                    </td>
                </tr>
<% }%>
                <tr>
                        <td valign=top  background="../images/img_default/dk_side_back.gif" >
                        <!-- menu// -->
                    <table cellpadding=0 cellspacing=0 border=0><tr><td>
                                <p><img src="../images/img_office/office_sub2-2.gif" width="160" height="20" border="0"></p>
                                </td></tr></table>

            <table width=160  border="0" cellpadding="0" cellspacing="1" bgcolor=e4e4e4 >
            <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr0' style="mouse:default,backgraoundColor='#ffffff'">
                <td height="24">
                    <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                    <a href="javascript:trcolor('/plm/servlet/e3ps/ManageNotifyServlet','tr0','공지사항')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "00889") %><%--공지사항--%></p>
                </td>
            </tr>
            <tr>
                <td bgcolor=#ffffff>
                <span id="processmenu" onClick="showhide(processout)" style="cursor:hand;">
                    <table cellpadding=0 cellspacing=0 border=0 width=100%>
                        <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)"  style="mouse:default,backgraoundColor='#ffffff'">
                            <td  height="24">  <img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0"><%=messageService.getString("e3ps.message.ket_message", "00788") %><%--결재함--%></td>
                        </tr>
                    </table>
                </span>
                <span id="processout" style="display:none">
                    <table cellpadding=0 cellspacing=0 border=0 width="100%"><tr><td height=1 bgcolor=e4e4e4 width="100%"><td></tr></table>
                    <table width=140 align=right border=0 cellpadding=0 cellspacing=0>
                        <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr1' style="mouse:default,backgraoundColor='#ffffff'">
                            <td height="24">
                                <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.workprocess.servlet.WorklistServlet','tr1')" id=sub  onFocus="this.blur()" ><%=messageService.getString("e3ps.message.ket_message", "00775") %><%--결재수신함--%></a></p>
                            </td>
                        </tr>
                        <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr2' style="mouse:default,backgraoundColor='#ffffff'">
                            <td height="24">
                                <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.workprocess.servlet.WFItemServlet?processtype=own','tr2')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00770") %><%--결재발신함--%></a></p>
                            </td>
                        </tr>
                        <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr3' style="mouse:default,backgraoundColor='#ffffff'">
                            <td height="24">
                                <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.workprocess.servlet.WFItemServlet?processtype=ing','tr3')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00787") %><%--결재진행함--%></a></p>
                            </td>
                        </tr>
                        <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr4' style="mouse:default,backgraoundColor='#ffffff'">
                            <td height="24">
                            <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                            <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.workprocess.servlet.WFItemServlet?processtype=complete','tr4')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "02181") %><%--완료함--%></a></p>
                            </td>
                        </tr>
                        <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr5' style="mouse:default,backgraoundColor='#ffffff'">
                            <td  height="24">
                                <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                <a href="javascript:trcolor('/plm/jsp/office/workprocess/CreateAsmProcess.jsp','tr5')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "02334") %><%--일괄결재--%></p>
                            </td>
                        </tr>
                    </table>
                </span>
                </td>
            </tr>
            <tr>
                <td bgcolor=#ffffff>
                <span id="mailmenu" onClick="showhide(mailout)" style="cursor:hand;">
                    <table cellpadding=0 cellspacing=0 border=0 width=100%>
                        <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)"  style="mouse:default,backgraoundColor='#ffffff'">
                            <td  height="24">  <img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0"><%=messageService.getString("e3ps.message.ket_message", "02199") %><%--우편함--%></td>
                        </tr>
                    </table>
                </span>
                <span id="mailout" style="display:none">
                    <table cellpadding=0 cellspacing=0 border=0 width="100%"><tr><td height=1 bgcolor=e4e4e4 width="100%"><td></tr></table>
                        <table width=140 align=right border=0 cellpadding=0 cellspacing=0>
                            <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr6' style="mouse:default,backgraoundColor='#ffffff'">
                                <td  height="24">
                                    <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                    <a href="javascript:trcolor('/plm/jsp/groupware/webmail/sendMail.jsp','tr6')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "02980") %><%--편지쓰기--%></p>
                                </td>
                            </tr>
                            <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr7' style="mouse:default,backgraoundColor='#ffffff'">
                                <td  height="24">
                                    <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                    <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet?command=getmail','tr7')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "01469") %><%--받은편지함--%></p>
                                </td>
                            </tr>
                            <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr8' style="mouse:default,backgraoundColor='#ffffff'">
                                <td  height="24">
                                    <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                    <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet?mailboxid='+<%=WebMailStateFlag.TEMP_MAILBOX_INDEX %>,'tr8')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "02373") %><%--임시보관함--%></p>
                                </td>
                            </tr>
                            <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr9' style="mouse:default,backgraoundColor='#ffffff'">
                                <td  height="24">
                                    <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                    <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet?mailboxid='+<%=WebMailStateFlag.SEND_MAILBOX_INDEX %>,'tr9')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "01528") %><%--보낸편지함--%></p>
                                </td>
                            </tr>
                            <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr10' style="mouse:default,backgraoundColor='#ffffff'">
                                <td  height="24">
                                    <p><img src="/plm/portal/icon/sub_arr3.gif" width="22" height="9" border="0">
                                    <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet?mailboxid='+<%=WebMailStateFlag.WASTE_MAILBOX_INDEX %>,'tr10')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "03261") %><%--휴지통--%></p>
                                </td>
                            </tr>
                        </table>
                </span>
                </td>
            </tr>
            <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr11' style="mouse:default,backgraoundColor='#ffffff'">
                <td height="24">
                    <p><img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0">
                    <a href="javascript:trcolor('/plm/servlet/e3ps.groupware.workprocess.servlet.SearchMyFolderServlet','tr11')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00680") %><%--개인문서함--%></a>
                    </p>
                </td>
            </tr>
            <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr12' style="mouse:default,backgraoundColor='#ffffff'">
                <td height="24">
                    <p><img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0">
                    <a href="javascript:trcolor('/plm/jsp/groupware/company/peopleView.jsp?oid=<%=datapeople%>','tr12')" id=sub  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00681") %><%--개인정보수정--%></a>
                    </p>
                </td>
            </tr>
                            <!-- 2005/03/07(시스템관리) -->
    <%
    boolean isAdmin = CommonUtil.isAdmin();
    if(isAdmin)
    {
    %>
                            <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr13' style="mouse:default,backgraoundColor='#ffffff'">
                                <td  height="24"><img src="/plm/portal/icon/sub_arr.gif" width="22" height="9" border="0">
                                                <a href="javascript:trcolor('/plm/jsp/groupware/company/companyfrm.jsp','tr13','<%=company_Name%> 조직도')" onFocus="this.blur();" id=sub><%=company_Name%> <%=messageService.getString("e3ps.message.ket_message", "02648") %><%--조직도--%></a></td>
                            </tr>
<%} %>
                        </table>
                        <!--menu//-->
<%
    String portalOid = request.getParameter("portalOid");
    String portalAction = request.getParameter("portalAction");

    String nextPage = "/plm/servlet/e3ps/ManageNotifyServlet";
    if(portalOid != null)
    {
        nextPage = "/plm/servlet/WindchillAuthGW/wt.workflow.work.WorkItemURLProcessor/URLTemplateAction?u8&oid=OR:"+portalOid+"&action="+portalAction;
    }
%>
                        </td>
                    <td width="100%">
                        <p><iframe frameborder=0 width=100% height=100% src="<%=nextPage%>" name=iframe></iframe></p>
                    </td>
                </tr>
            </table>
</form>
<script src='../js/menu.js'></script>
</html>
<input type=hidden name=myname value=작업장>
<script>setPosition(null);</script>
