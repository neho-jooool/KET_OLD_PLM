<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.session.*,wt.util.*"%>
<%@page import="e3ps.common.jdf.config.Config,e3ps.common.jdf.config.ConfigImpl"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>
<%
    String id = "";
    boolean isdrm = true;//ConfigImpl.getInstance().getBoolean("DRM");
    String language = messageService.getLocale().getLanguage();     // [PLM System 1차개선] 다국어 적용, 2013-08-29, BoLee

    WTProperties wtproperties = WTProperties.getLocalProperties();
    String serverName = wtproperties.getProperty("java.rmi.server.hostname");
    String webAppName = wtproperties.getProperty("wt.webapp.name");
    String serverCodebase = wtproperties.getProperty("wt.server.codebase");

    //id = "wcadmin";
    WTUser wtuser = (WTUser)SessionHelper.getPrincipal();
    id = wtuser.getName();

    EncodingConverter ec = new EncodingConverter();
    String url_jsp = serverCodebase + "/servlet/JNLPGeneratorServlet/" + "EPMLoadContainer.jnlp";
    //String url_jsp = serverCodebase + "/servlet/JNLPGeneratorServlet/" + "ptc_pdm_ProductStructureExplorer.jnlp";
    //String url_jsp = "http://" + serverName + "/"+webAppName+"/servlet/JNLPGeneratorServlet/" + "mpmLoader" + ".jnlp";
    //String url_jsp = url_factory.getHREF("/servlet/JNLPGeneratorServlet/" + app_id.replace(".","_") + ".jnlp");



    String host_name = request.getRemoteHost();
    if (host_name != null)
    {
       host_name = request.getRemoteAddr();
    }

    String description = "";
    String short_description = "";

    ///////////////////////////////////
    // find the right icon
    ///////////////////////////////

    String jwsRuntimeParameters = null;
    String url_icon = null;
    String mainClass = "com.ptc.jws.JNLPApplicationLauncher";
    String appClass = "e3ps.edm.clients.batch.EPMLoadContainer";
    String charset = "UTF-8";

    String sid = session.getId();


    String manageType = request.getParameter("manageType");
    if(manageType==null) {
        manageType = "MOLD_DRAWING";
    }
    
    Kogger.debug("edmBatchCnt", null, null, "# edmBatch start ...");
    Kogger.debug("edmBatchCnt", null, null, "# id : " + id);
    Kogger.debug("edmBatchCnt", null, null, "# type : " + manageType);


%>
<% /*** URLFactory designation ***/ %>
<jsp:useBean id="url_factory" class="wt.httpgw.URLFactory" scope="request" >
    <% url_factory.setRequestURL(request.getScheme(), request.getHeader("HOST"), request.getRequestURI()); //$NON-NLS-1$ %>
</jsp:useBean>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/util" prefix="util" %>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01267") %><%--도면등록(일괄)--%></TITLE>
<LINK rel="stylesheet" type="text/css" href="/<%=webAppName%>/portal/css/e3ps.css">
<style type="text/css">
<!--
body {
    overflow:hidden;
}
-->
</style>
</head>
<body>
<table width="100%" height="100%" cellspacing="2" cellpadding="0">
    <tr>
        <td valign="middle">
            <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center" valign="middle">
                        <%=messageService.getString("e3ps.message.ket_message", "01268") %><%--도면등록(일괄)을 마칠때 까지--%> <br><br><b><%=messageService.getString("e3ps.message.ket_message", "02266") %><%--이 창을 닫지 마십시오--%></b><br>
                        <%
                            if(!e3ps.edm.util.EDMProperties.getInstance().isJavaWebStart()) {
                        %>
                                <util:plugin code="<%=e3ps.edm.clients.batch.EPMLoaderApplet.class.getName()%>"
                                    codebase="<%= url_factory.getBaseHREF() %>"
                                    archive="wt/security/security.jar" width="180" height="75">
                                    <util:params>
                                       <util:param name="cabinets" value="wt/security/security.cab" />
                                       <util:param name="cache_option" value="Plugin" />
                                       <util:param name="cache_archive" value="edmBatch.jar,signjar/swingx-1.0.jar,signjar/swingx-beaninfo-1.0.jar,signjar/log4j.jar,signjar/commons-collections.jar,signjar/vecmath.jar,signjar/jxl.jar,signjar/boot.jar,wtClientChecker.jar" />
                                       <util:param name="hostname" value="<%=host_name%>" />
                                       <util:param name="application" value="EPMLoaderApplet" />
                                       <util:param name="sid" value="<%=sid%>" />
                                       <util:param name="hide" value="true" />
                                       <util:param name="forceSameClassLoader" value="true" />
                                       <util:param name="MAYSCRIPT" value="true" />
                                       <util:param name="userId" value="<%=id%>" />
                                       <util:param name="manageType" value="<%=manageType%>" />
                                       <util:param name="drm" value="<%=String.valueOf(isdrm)%>" />
                                       <util:param name="language" value="<%=language%>" />
                                    </util:params>
                                </util:plugin>
                        <%
                            } else {
                                ////////////////////////////////
                                // create URL for the JNLP generator
                                ////////////////////////////////
                                StringBuffer url_b = new StringBuffer();
                                url_b = url_b.append(url_jsp);
                                url_b = url_b.append("?");
                                url_b = url_b.append("title=" + ec.encode("KET", charset));
                                if(description!=null) url_b = url_b.append("&description=" + ec.encode(description,charset));
                                if(short_description!=null) url_b = url_b.append("&short_description=" + ec.encode(short_description,charset));
                                if(url_icon!=null) url_b=url_b.append("&icon=" + url_icon);
                                if(jwsRuntimeParameters!=null) url_b = url_b.append("&vm_args=" + ec.encode(jwsRuntimeParameters,charset));
                                url_b = url_b.append("&mainclass=" + mainClass);

                                //&params=mainClass=com.ptc.windchill.explorer.struct
                                url_b = url_b.append("&params=" + "mainClass=" + appClass + ",userId=" + id + ",manageType=" + manageType + ",drm=" + String.valueOf(isdrm) + ",language=" + language);
                                url_b = url_b.append("&jars=" + "edmBatch.jar,signjar/swingx-1.0.jar,signjar/swingx-beaninfo-1.0.jar,signjar/log4j.jar,signjar/commons-collections.jar,signjar/vecmath.jar,signjar/jxl.jar");
                                //url_b = url_b.append("&exts=" + "wtAppletFCS.jar");
                                //url_b = url_b.append("&exts=" + "lib/JWSUtil.jar,lib/WtHttpClientAddOns.jar");
                                //url_b = url_b.append("&exts_sec=" + "wt/security/security.jar,install/boot.jar");
                                url_b = url_b.append("&sid=" + sid);

                                url_b = url_b.append("&allperm=1");
                                url_b = url_b.append("&width=1");
                                url_b = url_b.append("&height=1");


                                ////////////////////////////////
                                // create URL for the JNLP generator
                                ////////////////////////////////
                                /*
                                StringBuffer url_b = new StringBuffer();
                                    url_b = url_b.append(url_jsp);
                                    url_b = url_b.append("?");
                                    url_b = url_b.append("title=" + ec.encode(explorer_name,charset));
                                    if(description!=null) url_b = url_b.append("&description=" + ec.encode(description,charset));
                                    if(short_description!=null) url_b = url_b.append("&short_description=" + ec.encode(short_description,charset));
                                    if(url_icon!=null) url_b=url_b.append("&icon=" + url_icon);
                                    if(jwsRuntimeParameters!=null) url_b = url_b.append("&vm_args=" + ec.encode(jwsRuntimeParameters,charset));
                                    url_b = url_b.append("&mainclass=com.ptc.jws.JNLPApplicationLauncher");
                                    url_b = url_b.append("&params=mainClass=" + applet);
                                        url_b = url_b.append(",application=" + app_id);
                                        url_b = url_b.append(",setoid=" + oid);
                                        url_b = url_b.append(",container_id=" + container_id);
                                        url_b = url_b.append(",hide=true");
                                        url_b = url_b.append(",singleton=true");
                                        url_b = url_b.append(",wt.context.locale=" + locale);
                                        url_b = url_b.append(",principal_name=" + user.getName());
                                    url_b = url_b.append("&sid=" + sid);
                                    url_b = url_b.append("&jars=" + "lib/JWSUtil.jar,lib/WtHttpClientAddOns.jar");
                                    url_b = url_b.append("&exts=" + jars);
                                    url_b = url_b.append("&exts_sec=" + "wt/security/security.jar,install/boot.jar");
                                    url_b = url_b.append("&allperm=1");
                                    url_b = url_b.append("&documentbase=" + "netmarkets/jsp/explorer/");
                                    url_b = url_b.append("&width=1");
                                    url_b = url_b.append("&height=1");
                                    // In IE, a GET URL cannot contain more than 2048 characters. We will just issue a warning on the console for debugging purpose
                                    // eventually we could use a proper logger and verify the User-Agent for IE
                                    if(url_b.length() >= 2048) System.out.println("WARNING: The url to launch PSE via JWS is longer than 2048 characters. This might cause issues with IE");
                                    */
                        %>
                                <SCRIPT language="JAVASCRIPT">
                                    <!--
                                    open('<%=url_b%>','_self', '');
                                    window.resizeBy(1,0);
                                    // -->
                                </SCRIPT>
                        <%
                            }
                        %>
                        <br><br>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
