<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="wt.vc.VersionControlHelper"%>
<%@page import="wt.fc.WTObject"%>
<%@page import="wt.enterprise.Master"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.dms.entity.KETStandardTemplate"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.obj.ObjectUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
   function temp(oid){
	   document.getElementById(oid).style.height = "";
	   document.getElementById(oid).style.width = "";
	   document.getElementById(oid).style.display = "block";
	   document.getElementById(oid).style.position = "absolute";
	   document.getElementById(oid).style.zIndex = "1002";
	   document.getElementById(oid).style.backgroundColor = "white";
	   document.getElementById(oid).style.border = "1px solid black";
	   document.getElementById(oid).style.left = window.event.clientX-200;
	   document.getElementById(oid).style.top = window.event.clientY;
   }
   
   function closeDivPopup(oid){
	   document.getElementById(oid).style.display = "none";
   }
</script>
</head>
<body class="popup-background popup-space">
<table style="width: 100%; ">
    <tr>
        <td valign="top">
            <table style="width:100%;">
                <tr>
                    <td style="background: url(/plm/portal/images/logo_popupbg.png) repeat-x 0 0;">
                        <table style="width:100%;height: 43px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">이력조회</td>
                          </tr>
                        </table>
                    </td>
                    <td style="
    width: 283px;background:url(/plm/portal/images/logo_popup.png) no-repeat 0 0"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" align="right">
	 <tr>
	   <td width="10"><img src="../../portal/images/btn_1.gif"></td>
	   <%-- <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %>목록</a></td> --%>
	   <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue">닫기<%--닫기--%></a></td>
	    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
	  </tr>
</table>
<table class="table-style-01" style="width:100%;margin-top:30px">
<tr>
    <td class="fontb center bgcol">Rev.</td>
    <td class="fontb center bgcol">문서명</td>
    <td class="fontb center bgcol">작성자</td>
    <td class="fontb center bgcol">배포일</td>
    <td class="fontb center bgcol">표준양식</td>
</tr>

<%
 
String oid = ParamUtil.getParameter(request, "oid");
KETStandardTemplate ketStandardTemplate = (KETStandardTemplate)CommonUtil.getObject(oid);
QueryResult qr = VersionControlHelper.service.allVersionsOf(ketStandardTemplate.getMaster());
Object opObj = null;
KETStandardTemplate historyInfo = null;

while(qr.hasMoreElements()){
    opObj = qr.nextElement(); 
    historyInfo = (KETStandardTemplate)opObj;
    String historyOid =  historyInfo.toString();
    ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(historyOid));
    Vector files = ContentHelper.getContentList(holder);
%>
<tr>
    <td class="center"><%=historyInfo.getVersionInfo().getIdentifier().getValue() %></td>
    <td class="center"><%=historyInfo.getTitle() %></td>
    <td class="center"><%=historyInfo.getCreatorFullName() %></td>
    <td class="center"><fmt:formatDate pattern="YYYY-MM-dd" value="<%=historyInfo.getPersistInfo().getCreateStamp()%>" /></td>
    <td class="center"><a href="javascript:void(0);" onclick="javascript:temp('<%=historyOid%>')"><%=files.size()%></a></td>
<div id="<%=historyOid%>" style="display: none;font-size:11px;Font-family: NanumGothic, "나눔고딕", Nanumgo, NanumBold, Dotum;">
<div style="background-color:#e2edf4;padding:15px;color:#266e8c">
<%
  if (files != null && files.size() > 0){
      for (int inx = 0 ; inx < files.size() ; inx++){
          ApplicationData appData = (ApplicationData)files.get(inx);
          String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
          //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
          String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
%>
   
    <a href="<%=url%>"><%=appData.getFileName()%></a>&nbsp<%=appData.getFileSizeKB()%>KB<br>
<%  }
 }else if(files == null || files.size() < 1){
%>
     &nbsp;
<%}%>
<br>
    
    <p class="b-small2"><a href="javascript:void(0);" onclick="closeDivPopup('<%=historyOid%>')"><span style="color:#444">close</span></a></p>
</div>
</div>
</tr>
<%
}
%>
</table>
</body>
</html>