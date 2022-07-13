<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="wt.content.ApplicationData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.groupware.board.NotifyData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.groupware.board.Notify"%>
<%@page import="e3ps.common.web.ParamUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String oid = ParamUtil.checkStrParameter(request.getParameter("oid"));
  long idPart = CommonUtil.getOIDLongValue(oid);
  String from = ParamUtil.checkStrParameter(request.getParameter("from"));
  Notify note = (Notify)CommonUtil.getObject(oid);
  NotifyData data = new NotifyData(note,1);
  data.updateReadCount();
  boolean canManage = false;
  String owner = CommonUtil.getOIDString((wt.org.WTUser)note.getOwner().getPrincipal());
  String cUser = CommonUtil.getOIDString((wt.org.WTUser)wt.session.SessionHelper.manager.getPrincipal());
  if(cUser.equals(owner) || CommonUtil.isAdmin())
      canManage = true;

  ContentHolder holder = ContentHelper.service.getContents(note);
  Vector vec = ContentHelper.getContentList(holder);
%>

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00892") %><%--공지사항 상세조회--%></title>

<link rel="stylesheet" href="/plm/portal/css/e3ps.css">

<script src="/plm/portal/js/script.js"></script>
<script src="/plm/portal/js/viewObject.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
</style>

<script type="text/javascript">
<!--
  function setCookie( name, value, expiredays ) {
      var todayDate = new Date();
    todayDate.setDate( todayDate.getDate() + expiredays );
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
  }

  function checkAndClose() {
    if (document.notifyview.SaleNotice5.checked ) {

      setCookie("renewalPopup<%=idPart %>", "no" , 1);
    }
    window.close();
  }

  function toggleCheck() {
    var checkbox = document.notifyview.SaleNotice5;
    checkbox.checked = ! checkbox.checked;
    setCookie("renewalPopup<%=idPart %>", "no" , 1);
    window.close();
  }

//-->
</script>

<!-- 글 내용을 보여줄 외곽 style 설정 : 사이트 특성에 맞게 수정 가능 및 제거 가능 -->
<!-- 이노디터의 직접적인 설정과는 무관하며, View 영역을 표시하기 위한 Style 설정임 -->
<style type="text/css">
/*<![CDATA[*/
.outline    {border:0px solid #c4cad1;padding:5px;}
/*]]>*/
</style>

<!-- Start : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->
<!--
    이노디터 기본글꼴을 굴림, 기본글크기를 10pt 로 설정하였다면
    View 페이지에서도 작성된 곳의 보여주는 부분을 동일하게 설정
-->
<style type="text/css">
/*<![CDATA[*/
#divView            {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;text-align:left;overflow-x:auto;word-wrap:break-word;}
#divView TD         {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;}

#divView P          {margin-top:2px;margin-bottom:2px;}/* IE 에서 줄바꿈(엔터친 경우) style 설정 */
#divView BLOCKQUOTE {margin-top:2px;margin-bottom:2px;}/* IE 에서 들여쓰기 style 설정 */
/*]]>*/
</style>
<!-- End : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->

<script type="text/javascript">
//<![CDATA[
  function fnSetEditorHTML() {

    var form01 = document.forms[0];
    // 이노디터에서 작성된 내용을 전달
    var strContent = form01.webEditor.value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = form01.hdnBackgroundColor.value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = form01.hdnBackgroundImage.value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = form01.hdnBackgroundRepeat.value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
  }
//]]>
</script>

</head>
<body onload="javascript:fnSetEditorHTML();" scroll="auto" style="overflow-x:hidden">

<form name="notifyview" method="post">
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=data.oid%>">
<input type="hidden" name="from" value="<%=from%>">

<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"><%=note.getWebEditor() %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->

<table style="width:100%; height:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="height:50px;" valign="top"><table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table style="height:28px;" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td style="width:7px;"></td>
            <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00892") %><%--공지사항 상세조회--%></td>
          </tr>
        </table></td>
        <td style="width:136px;" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table style="width:780px; height:100%;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td style="width:10px;">&nbsp;</td>
          <td valign="top">
      <table style="width:770px;" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:checkAndClose();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:770px;">
        <tr>
          <td class="space5"> </td>
        </tr>
      </table>


        <!-- [START] body area -->
        <table style="width: 780px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 780px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
            <td class="tdwhiteL0" colspan="3">
                <%=data.title %>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01337") %><%--등록자--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <%=data.writer %>
            </td>
            <td class="tdblueL"    style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01336") %><%--등록일자--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <%=data.writeDate %>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "00742") %><%--게시 기한일자--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <%=data.deadline == null ? "&nbsp;" : DateUtil.getDateString( data.deadline, "d" )%>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02652") %><%--조회--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <%=data.readCount %>
            </td>
        </tr>
        <%
        if ( data.isAttache ) {
        %>
        <tr>
            <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%> </td>
            <td class="tdwhiteL0" colspan="3" >
    <%
        for ( int i = 0 ; i < vec.size() ; i++ ) {
          ApplicationData appData = (ApplicationData)vec.get(i);
          String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
          //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
          String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
    %>
                <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;
                <a href="<%=url%>" target="download"><%=appData.getFileName()%></a><%if(i<vec.size()){%><br><%} %>
        <%
            }
        %>
            </td>
        </tr>
        <%
        }
        %>
        <tr>
            <td class="tdwhiteL0" colspan="4">
              <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
              <div id="divView" style="width:750px;" class="outline"></div>
              <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
            </td>
        </tr>
        </table>
        <!-- [END] data area -->

        <table style="width: 780px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td style="height: 38px;" align="right" valign="top" >
              <input type="checkbox" name="SaleNotice5" value="checkbox" style="border:0" onclick="javascript:toggleCheck();">&nbsp;<span style="font-size:9pt; cursor:default;"><%=messageService.getString("e3ps.message.ket_message", "02160") %><%--오늘 이창을 다시 열지 않음--%></span>
          </td>
        </tr>
        </table>

        </td>
      </tr>
    </table>
      </td>
    </tr>
     <tr>
        <td style="height:30px;" valign="bottom"><table style="width:780px;" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td style="width:10px;">&nbsp;</td>
              <td style="height:30px;">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width:770px; height:24px;" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe>
              </td>
            </tr>
          </table></td>
      </tr>
</table>
<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px solid #fff;"></iframe>
</form>
</body>
</html>
