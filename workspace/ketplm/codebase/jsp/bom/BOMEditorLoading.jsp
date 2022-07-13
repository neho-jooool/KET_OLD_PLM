<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="http://www.ptc.com/windchill/taglib/util" prefix="util" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<% response.setContentType("text/html; charset=UTF-8"); %>

<%@ page import ="java.util.*"%>
<%@ page import ="java.net.URL" %>
<%@ page import ="wt.access.AccessControlHelper" %>
<%@ page import ="wt.access.AccessPermission" %>
<%@ page import ="wt.admin.AdminDomainRef" %>
<%@ page import ="wt.admin.AdministrativeDomain" %>
<%@ page import ="wt.folder.*" %>
<%@ page import ="wt.inf.container.WTContainerHelper" %>
<%@ page import ="wt.inf.container.WTContainerRef" %>
<%@ page import ="wt.lifecycle.LifeCycleHelper" %>
<%@ page import ="wt.lifecycle.LifeCycleTemplate" %>
<%@ page import ="wt.lifecycle.State" %>
<%@ page import ="wt.org.OrganizationServicesHelper" %>
<%@ page import ="wt.org.WTOrganization" %>
<%@ page import ="wt.org.WTGroup" %>
<%@ page import ="wt.org.WTPrincipal" %>
<%@ page import ="wt.org.WTUser" %>
<%@ page import ="wt.session.SessionHelper" %>
<%@ page import ="wt.util.WTProperties" %>
<%@ page import ="e3ps.common.util.KETStringUtil" %>
<%@ page import ="e3ps.bom.entity.KETBomHeader" %>
<%@ page import ="e3ps.groupware.company.*" %>

<%
WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
System.out.println( "@@@@@@@ user : " + user );

People people = PeopleHelper.manager.getPeople(user.getName());
System.out.println( "@@@@@@@ people : " + people );

String department = (people.getDepartment()!=null)?people.getDepartment().getName():"";
System.out.println( "@@@@@@@ department : " + department );

String strOid = request.getParameter("oid");      // initBomOid
String strEcoType = request.getParameter("ecoType");  // ecoType
String strChildItem = request.getParameter("child");  // childItemCode
String strEcoNo = request.getParameter("ecoNo");    // ecoNumber
String strParentItem = request.getParameter("parent");  // parentItemCodes

System.out.println( "@@@@@@@ initBomOid : " + strOid );
System.out.println( "@@@@@@@ ecoType : " + strEcoType );
System.out.println( "@@@@@@@ childItemCode : " + strChildItem );
System.out.println( "@@@@@@@ ecoNumber : " + strEcoNo );
System.out.println( "@@@@@@@ parentItemCodes : " + strParentItem );

String language = messageService.getLocale().getLanguage();
System.out.println( "@@@@@@@ language : " + language );

System.out.println( "BOM Editor Loading Start!!!" );

  URL wcUrl = WTProperties.getServerCodebase();


  WTGroup wtSysGroup = OrganizationServicesHelper.manager.getGroup("Administrators", WTContainerHelper.service.getExchangeContainer().getContextProvider());
  boolean isSysAdmin = OrganizationServicesHelper.manager.isMember(wtSysGroup, SessionHelper.manager.getPrincipal());

  //WTGroup wtBizGroup = OrganizationServicesHelper.manager.getGroup("Business Administrator", WTContainerHelper.service.getExchangeContainer().getContextProvider());
  //boolean isBizAdmin = OrganizationServicesHelper.manager.isMember(wtBizGroup, SessionHelper.manager.getPrincipal());

  // 로그인한 사용자가  Admin Group 에 속해 있는지 체크
  String adminFlag = "";
  if(isSysAdmin)
  {
    adminFlag = "System Administrator";
  }
  //else if(isBizAdmin)
  //{
  //  adminFlag = "Business Administrator";
  //}

  WTOrganization wtOrg = OrganizationServicesHelper.manager.getOrganization(SessionHelper.manager.getPrincipal());
  WTContainerRef containerRef = WTContainerHelper.service.getByPath("/wt.inf.container.OrgContainer=" + wtOrg.getName() + "/wt.pdmlink.PDMLinkProduct=KET");

  // 로그인한 사용자의 ACL 정보 가져오기
  String bomAccessFlag = "";
  try
  {
    boolean isCreate = false;

    AdministrativeDomain domain = wt.admin.AdministrativeDomainHelper.manager.getDomain( "[/wt.inf.container.OrgContainer="+wtOrg.getName()+"/wt.pdmlink.PDMLinkProduct=KET]/Default");
    AdminDomainRef domainRef = wt.admin.AdminDomainRef.newAdminDomainRef( domain );

    isCreate = AccessControlHelper.manager.hasAccess(SessionHelper.manager.getPrincipal(), "e3ps.bom.entity.KETBomHeader", domainRef, null, AccessPermission.CREATE);

    if(isCreate)
    {
      bomAccessFlag = "OWNER";
    }
  }
  catch(Exception ex)
  {
    ex.printStackTrace();
  }

  // 신규  BOM LifeCycle 상태
  LifeCycleTemplate lifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", containerRef);

  Vector stateVec = LifeCycleHelper.service.findStates(lifeCycle);
  // BOM ECO LifeCycle 상태
  LifeCycleTemplate bomEcoLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_ECO_LC", containerRef);
  Vector bomEcoStateVec = LifeCycleHelper.service.findStates(bomEcoLifeCycle);

  State state = null;
  String statusStr = "";
  String bomEcoStatusStr = "";

  for(int i=0; i<stateVec.size(); i++)
  {
    String temp = "";
    state = (State) stateVec.elementAt(i);

    temp = state.toString()+","+state.getDisplay();
    statusStr = statusStr + ":" + temp;
  }

  for(int i=0; i<bomEcoStateVec.size(); i++)
  {
    String temp = "";
    state = (State) bomEcoStateVec.elementAt(i);

    temp = state.toString()+","+state.getDisplay();
    bomEcoStatusStr = bomEcoStatusStr + ":" + temp;
  }

  System.out.println("====>> statusStr : " + statusStr);
  System.out.println("====>> bomEcoStatusStr : " + bomEcoStatusStr);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>BOM Editor</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  overflow:hidden;
}
-->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
<!--
  function winClose()
  {
    window.close();
  }

    function editorLoading()
    {
      showProcessing();
    }

    function winRefresh()
    {
      //parent.location.reload();
      opener.document.location.reload();

    }
//-->
</script>

<%@include file="/jsp/common/processing.html"%>

</head>

<body onload="javascript:editorLoading()">
<table width="100%" height="100%" cellspacing="2" cellpadding="0">
  <tr>
    <td valign="middle">
      <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center" valign="middle">
            <font color="orange">
            <b><%=messageService.getString("e3ps.message.ket_message", "00085") %><%--BOM 작업을 모두 마칠때까지 이 창을 닫지 마십시오--%></b>
            <br><br><br><br><br><br><br><br><br><br><br><br><br><br>
            </font>

<%
  String bomJar = "lib/SBOMRegister.jar";
%>
      <!--"CONVERTED_APPLET"-->
      <!-- HTML CONVERTER -->
       <!-- [PLM System 1차고도화] 내용 : 1.7 버전업, 2014. 06. 17, 김근우 -->
      <OBJECT classid = "clsid:CAFEEFAC-0017-0000-FFFF-ABCDEFFEDCBA"
              codebase = "http://java.sun.com/update/1.7.0/jinstall-7-windows-i586.cab#Version=1,7,0" WIDTH = "1" HEIGHT = "1" >
      <PARAM NAME = "CODE" VALUE = "e3ps/bom/BOMRegisterApplet.class" >
      <PARAM NAME = "CODEBASE" VALUE = "/plm">
      <PARAM NAME = "cache_archive" VALUE = "<%=bomJar%>">
      <PARAM NAME = "cache_archive_ex" VALUE = "<%=bomJar%>;preload">
      <PARAM NAME = "cache_option" VALUE = "Plugin">
      <PARAM NAME = "MAYSCRIPT" VALUE = "true">
      <PARAM NAME = "wc_server_codebase" VALUE = "<%=wcUrl%>">
      <PARAM NAME = "statusStr" VALUE = "<%=statusStr%>" >
      <PARAM NAME = "bomEcoStatusStr" VALUE = "<%=bomEcoStatusStr%>" >
      <PARAM NAME = "adminFlag" VALUE = "<%=adminFlag%>" >
      <PARAM NAME = "bomAccessFlag" VALUE = "<%=bomAccessFlag%>" >
      <PARAM NAME = "orgCode" VALUE = "" >
      <PARAM NAME = "orgName" VALUE = "<%=wtOrg.getName()%>" >
      <PARAM NAME = "userId" VALUE = "<%=user.getName()%>" >
      <PARAM NAME = "userName" VALUE = "<%=user.getFullName()%>" >
      <PARAM NAME = "userDept" VALUE = "<%=department%>" >
      <PARAM NAME = "userEMail" VALUE = "<%=user.getEMail()%>" >
      <PARAM NAME = "type" VALUE = "application/x-java-applet;version=1.7">
      <PARAM NAME = "scriptable" VALUE = "false">

      <PARAM NAME = "oid" VALUE = "<%=strOid%>" >
      <PARAM NAME = "ecoType" VALUE = "<%=strEcoType%>" >
      <PARAM NAME = "childItemCode" VALUE = "<%=strChildItem%>" >
      <PARAM NAME = "ecoNumber" VALUE = "<%=strEcoNo%>" >
      <PARAM NAME = "parentItemCodes" VALUE = "<%=strParentItem%>" >

      <!-- [PLM System 1차개선] 내용 : 다국어 처리, 2013. 08. 13, 김무준 -->
      <PARAM NAME = "language" VALUE = "<%=language%>" >

      <COMMENT>
      <EMBED TYPE = "application/x-java-applet;version=1.7"  CODE = "e3ps/bom/BOMRegisterApplet.class" JAVA_CODEBASE = "/plm" ARCHIVE = "<%=bomJar%>" WIDTH = 10 HEIGHT = 10  scriptable = false pluginspage = "http://java.sun.com/products/plugin/index.html#download">
      <NOEMBED>
      </NOEMBED>
      </EMBED>
      </COMMENT>
      </OBJECT>
      <!--"END_CONVERTED_APPLET"-->
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
