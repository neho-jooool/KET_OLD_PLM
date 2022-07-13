<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.ecm.beans.EcmUtil
                            ,e3ps.ecm.beans.ProdEcoBeans" %>
<%@page import="java.util.Vector
                            ,java.util.Hashtable
                            ,java.util.ArrayList
                            ,wt.content.ContentHolder
                            ,wt.content.ContentHelper
                            ,wt.content.ApplicationData" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.KETObjectUtil" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.entity.KETProdChangeRequest"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEco" class="e3ps.ecm.entity.KETProdChangeOrder" scope="request"/>
<jsp:useBean id="ecoHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script type="text/javascript">
<!--
//처리중 화면 전환
function clickTabBtn(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabActivity = document.getElementById("tabActivity");
    if(tabId == 1) {
        tabBasic.style.visibility = "visible";
        tabActivity.style.visibility = "hidden";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
    } else {
        tabBasic.style.visibility = "hidden";
        tabActivity.style.visibility = "visible";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "block";
    }
}

function init() {
    clickTabBtn(1);
}

function viewRelDoc(oid)
{
    var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid="+oid;
    openWindow(url,"","800","640","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","750","650","scrollbars=no,resizable=no,top=200,left=250");
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
    var url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid=" + oid;
    openWindow(url,"","820","700","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
}

function viewEpmDocPopup(oid)
{
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + oid + "&isRefresh=N";
    openWindow2(url,"","820","800","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}

//-->
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%
ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)prodEco );
Vector attachFileList = ContentUtil.getSecondaryContents(holder);

ArrayList<Hashtable<String, String>> bomList = (ArrayList)ecoHash.get("bomList");
ArrayList<Hashtable<String, String>> parentItemList = (ArrayList)ecoHash.get("parentItemList");
ArrayList<Hashtable<String, String>> epmDocList = (ArrayList)ecoHash.get("epmDocList");
ArrayList<Hashtable<String, String>> docList = (ArrayList)ecoHash.get("docList");

boolean isAddableActivity = false;
%>
</head>
<body onload="init();">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02564") %><%--제품ECO상세조회--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="790" height="500" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td width="780" valign="top"><table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20">&nbsp;</td>
              <td class="font_03">

                <table id="infoShow" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03235") %><%--활동계획--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
                <table id="infoHide" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03235") %><%--활동계획--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>


              </td>
              <td align="right">&nbsp;</td>
            </tr>
          </table>
            <table width="780" height="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                <td height="10" background="/plm/portal/images/box_14.gif"></td>
                <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
              </tr>
              <tr>
                <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                <td valign="top"><!--내용-->
                  <table width="760" height="610" border="0" cellspacing="0" cellpadding="10">
                    <tr>
                      <td valign="top">&nbsp;</td>
                    </tr>
                  </table>
                  <div id="tabBasic" style="position:absolute; width:740px; height:550px; z-index:1; top: 90px; left: 30px">
                  <%@include file="/jsp/ecm/ViewProdEcoBasicForm.jsp" %>
                </div>
                <div id="tabActivity" style="position:absolute; width:740px; height:550px; z-index:1; top: 90px; left: 30px">
                <%@include file="/jsp/ecm/ViewProdEcoActivityForm.jsp" %>
                </div>
                </td>
                <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
              </tr>
              <tr>
                <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
                <td height="10" background="/plm/portal/images/box_16.gif"></td>
                <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
              </tr>
          </table></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="790" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
