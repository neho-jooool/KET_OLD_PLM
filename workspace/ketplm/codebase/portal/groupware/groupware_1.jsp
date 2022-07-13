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
            <p><img src="../images/img_groupware/sub_work2.gif" width="180" height="41" border="0"></p>
            </td>
            </tr>
            </table>
            <!--left_title//-->
            <!--left_menu//-->
            <table width=179  border="0" cellpadding="0" cellspacing="1" bgcolor=ffffff >
                <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr6' style="mouse:default,backgraoundColor='#ffffff'">
                    <td  height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/groupware/webmail/sendMail.jsp','tr6','편지쓰기')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "02980") %><%--편지쓰기--%></p>
                    </td>
                </tr>
                <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr7' style="mouse:default,backgraoundColor='#ffffff'">
                    <td  height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet&key=command&value=getmail','tr7','받은편지함')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "01469") %><%--받은편지함--%></p>
                    </td>
                </tr>
                <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr8' style="mouse:default,backgraoundColor='#ffffff'">
                    <td  height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet&key=mailboxid&value='+<%=WebMailStateFlag.TEMP_MAILBOX_INDEX %>,'tr8','임시보관함')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "02373") %><%--임시보관함--%></p>
                    </td>
                </tr>
                <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr9' style="mouse:default,backgraoundColor='#ffffff'">
                    <td  height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet&key=mailboxid&value='+<%=WebMailStateFlag.SEND_MAILBOX_INDEX %>,'tr9','보낸편지함')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "01528") %><%--보낸편지함--%></p>
                    </td>
                </tr>
                <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr15' style="mouse:default,backgraoundColor='#ffffff'">
                    <td  height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.webmail.servlet.ReceiveCheckServlet','tr15','수신확인')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "01931") %><%--수신확인--%></p>
                    </td>
                </tr>
                <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr10' style="mouse:default,backgraoundColor='#ffffff'">
                    <td  height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet&key=mailboxid&value='+<%=WebMailStateFlag.WASTE_MAILBOX_INDEX %>,'tr10','휴지통')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "03261") %><%--휴지통--%></p>
                    </td>
                </tr>
                <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr1' style="mouse:default,backgroundColor='#ffffff'">
                    <td height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/groupware/address/personalAddressFrm.jsp','tr1','개인주소록 목록')" onFocus="this.blur()" id=sub><%=messageService.getString("e3ps.message.ket_message", "00683") %><%--개인주소록 목록--%></p>
                    </td>
                </tr>
                <tr bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr2' style="mouse:default,backgroundColor='#ffffff'">
                    <td height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/groupware/address/personalAddressCategoryFrm.jsp','tr2','개인주소록 관리')" onFocus="this.blur()" id=sub><%=messageService.getString("e3ps.message.ket_message", "00682") %><%--개인주소록 관리--%></p>
                    </td>
                </tr>
                <tr  bgcolor="#ffffff" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr16' style="mouse:default,backgraoundColor='#ffffff'">
                    <td  height="24">
                        <p><img src="../icon/sub_arr.gif" width="22" height="9" border="0">
                        <a href="javascript:trcolor('/plm/jsp/common/loading.jsp?url=/plm/jsp/groupware/webmail/mailEnvMain.jsp','tr16','환경설정')"  onFocus="this.blur()"  id=sub><%=messageService.getString("e3ps.message.ket_message", "03232") %><%--환경설정--%></p>
                    </td>
                </tr>
            </table>
                    <!--menu//-->

<%--
    String portalOid = request.getParameter("portalOid");
    String portalAction = request.getParameter("portalAction");

    String nextPage = "/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet&key=command&value=getmail";
    if(portalOid != null)
    {
        nextPage = "/plm/servlet/WindchillAuthGW/wt.workflow.work.WorkItemURLProcessor/URLTemplateAction?u8&oid=OR:"+portalOid+"&action="+portalAction;
    }
--%>
</td>
<td width="100%">

                <!--우측//-->
                    <table border=0 width=100% height=100%>
                        <tr>
                            <td width="100%" height="20" background="../images/img_default/indexing.gif">
                        <p>&nbsp;<span id=position dataformatas=HTML datafld=position datasrc=#DDR_DSO>
                            <font color=gray><%=messageService.getString("e3ps.message.ket_message", "02372") %><%--읽는중--%></font></span></p>
                    </td>
                        </tr>
                        <tr>
                            <td>
                                <p><iframe frameborder=0 width=100% height=100% src="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.webmail.servlet.ManageMailServlet&key=command&value=getmail" name=iframe></iframe></p>
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
<input type=hidden name=myname value="우편함">
<script>setPosition(null);</script>

