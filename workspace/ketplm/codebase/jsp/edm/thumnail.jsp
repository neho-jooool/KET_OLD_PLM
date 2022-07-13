<%@page import="wt.util.WTProperties"%>
<%@page import="java.util.Locale"%>
<%@page import="com.ptc.wvs.common.ui.VisualizationHelper"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="wt.part.WTPart"%>
<%@page import="e3ps.edm.beans.EDMHelper"%>
<%@page contentType="text/html; charset=EUC-KR"%>
<%
/******************************************************************************
    @Filename    : viewDGCadPopup.jsp
    @Description : 도면 상세 화면
    @Modification Log
    @2011.05.11   hsJo initialize
******************************************************************************/
%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%= request %>" />
    <!-- OOTB JS 처리를 위한 BaseHref 설정 필수!! -->
    <base id='basehref' href="<%=(new wt.httpgw.URLFactory()).getBaseHREF()%>"/>
       
    <!-- 축소한 한글설정... -->
    <script type="text/javascript">
        var is_Wildfire=false;
        var pjlLocal='ko';
        var USER_AGENT='Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.94 Safari/534.13';
    </script>

    <!-- OOTB JS 처리를 위한 BaseHref 설정 필수!! -->
    <base id='basehref' href="<%=(new wt.httpgw.URLFactory()).getBaseHREF()%>"/>
       
    <!-- thumbail windchill_10 start -->
    <LINK REL=stylesheet HREF="/plm/netmarkets/css/windchill-base.css" TYPE="text/css">
    <LINK REL=stylesheet id="theme0" HREF="/plm/netmarkets/themes/windchill/xtheme-windchill.css" TYPE="text/css">
    
    <script type="text/javascript" src="/plm/netmarkets/javascript/util/windchill-libs.js"></script>
    <script type="text/javascript" src="/plm/netmarkets/javascript/ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="/plm/netmarkets/javascript/util/ext-and-extensions.js"></script>
    <script type="text/javascript" src="/plm/netmarkets/javascript/util/jstable-all.js"></script>
    <script type="text/javascript" src="/plm/netmarkets/javascript/util/windchill-all.js"></script>
    <!-- thumbail windchill_10 end -->
<%
    String oid = request.getParameter("oid");
    String userAgent = request.getHeader("user-agent");
    String browser = "";

if(userAgent.indexOf("Edg") > -1 ) {
    browser = "edge";
}else if(userAgent.indexOf("Trident") > -1 || userAgent.indexOf("MSIE") > -1) { //IE

    if(userAgent.indexOf("Trident/7") > -1) {
        browser = "IE";
    }else if(userAgent.indexOf("Trident/6") > -1) {
        browser = "IE";
    }else if(userAgent.indexOf("Trident/5") > -1) {
        browser = "IE";
    }else if(userAgent.indexOf("Trident/4") > -1) {
        browser = "IE";
    }else if(userAgent.indexOf("edge") > -1) {
        browser = "edge";
    }

}else if(userAgent.indexOf("Whale") > -1){ //네이버 WHALE
    browser = "WHALE " + userAgent.split("Whale/")[1].toString().split(" ")[0].toString();
}else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1){ //오페라
    if(userAgent.indexOf("Opera") > -1) {
        browser = "OPERA " + userAgent.split("Opera/")[1].toString().split(" ")[0].toString();
    }else if(userAgent.indexOf("OPR") > -1) {
        browser = "OPERA " + userAgent.split("OPR/")[1].toString().split(" ")[0].toString();
    }
}else if(userAgent.indexOf("Firefox") > -1){ //파이어폭스
    browser = "FIREFOX " + userAgent.split("Firefox/")[1].toString().split(" ")[0].toString();
}else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1 ){ //사파리
    browser = "SAFARI " + userAgent.split("Safari/")[1].toString().split(" ")[0].toString();
}else if(userAgent.indexOf("Chrome") > -1){ //크롬
    browser = "CHROME " + userAgent.split("Chrome/")[1].toString().split(" ")[0].toString();
}

System.out.println("userAgent 확인 ["+userAgent+"]");
System.out.println("브라우저/버전 확인 ["+browser+"]");
    // added by tklee 
    ReferenceFactory rf = new ReferenceFactory();

    Object o = rf.getReference(oid).getObject();
    if (o instanceof WTPart) {
		ArrayList refDocs = new ArrayList();

		//관련 모델 조회.
		refDocs = EDMHelper.getReferenceDocs((WTPart) o, EDMHelper.REFERENCE_TYPE_MODEL, -1);

		//대표 연관 도면
		if (refDocs.size() == 0) {
		    refDocs = EDMHelper.getReferenceDocs((WTPart) o, EDMHelper.REFERENCE_TYPE_RELATED, EDMHelper.REQUIRED_STANDARD);
		}

		//관련 연관 도면
		if (refDocs.size() == 0) {
		    refDocs = EDMHelper.getReferenceDocs((WTPart) o, EDMHelper.REFERENCE_TYPE_RELATED, EDMHelper.REQUIRED_RELATED);
		}

		if (refDocs.size() > 0) {
		    oid = rf.getReferenceString((EPMDocument) ((Object[]) refDocs.get(0))[1]);
		}
    }

    boolean isSupport = true;

    o = rf.getReference(oid).getObject();
    if (o instanceof EPMDocument) {
		EPMDocument oo = (EPMDocument) o;
		String authoringApp = oo.getAuthoringApplication().toString();
		if (!("ACAD".equals(authoringApp) || "CATIAV5".equals(authoringApp) || "PROE".equals(authoringApp) || "UG".equals(authoringApp))) {
		    isSupport = false;
		}
    }

    String width = request.getParameter("width");
    String height = request.getParameter("height");
    if (width == null || "".equals(width)) {
		width = "100";
    }
    if (height == null || "".equals(height)) {
		height = "100";
    }

    String oldWidth = ",\"192\",\"128\",";
    String changeWidth = ",\"" + width + "\",\"" + height + "\",";

    // K100263-00_3D  http://plm.ket.com/plm/jsp/edm/ViewEPMDocument.jsp?oid=wt.epm.EPMDocument:860011320 
    Locale locale = request.getLocale();
    VisualizationHelper visHelper = new VisualizationHelper();
    String thumnail = "";
    if (visHelper.isWVSEnabled()) {
		out.println(visHelper.getCreateDialogWindow());
		String visData[] = null;
		visData = visHelper.getDefaultVisualizationData(oid, new Boolean(false), locale);

		int thumbnailIndex = visHelper.getIndexOfVisLink(VisualizationHelper.THUMBNAILS_PROP_PAGE_PREF_KEY, VisualizationHelper.DEFAULT_THUMBNAILS_PROP_PAGE_PREF_VALUE);
		int clipboardIndex = visHelper.clipboardLinkIndex();
		int printIndex = visHelper.printLinkIndex();
		int repsOrMarkupsIndex = visHelper.viewRepsLinkIndex();

		thumnail = visData[thumbnailIndex];
		if(!(browser.equals("edge") || browser.equals("IE"))){
		    WTProperties properties = WTProperties.getLocalProperties();
		    
		    String hostName = properties.getProperty("wt.server.codebase");
		    thumnail = thumnail.replaceAll("/plm/wtcore/jsp/wvs/edrview.jsp", "microsoft-edge:"+hostName+"/wtcore/jsp/wvs/edrview.jsp");    
		}
		
    }

    thumnail = thumnail.replace(oldWidth, changeWidth);
%>
<html>
	<head>
		<title>Thumnail</title>
		 
		 <base  href="<%=(new wt.httpgw.URLFactory()).getBaseHREF()%>"/>
	</head>
	<body style="border:0 solid #FFFFFF;">
	<div id="wvs_pview_div" style="position:relative; width:300px; height:200px; margin:0 0 15px 0;"><%=thumnail %></div>
	</body>
</html>