<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="e3ps.project.*,e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02938") %><%--템플릿 프로젝트 생성--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language="JavaScript">

function createTemplate1(obj) {
  if(checkValidate()) {
    document.forms[0].action = "/plm/servlet/e3ps/TemplateProjectServlet";
    document.forms[0].method = "post";
    document.forms[0].IMSI.value = "IMSI";
    disabledAllBtn();
    obj.value = '처리중';
    showProcessing();
    document.forms[0].submit();
  }
}

function createTemplate2(obj) {
  if(checkValidate()) {
    document.forms[0].action = "/plm/servlet/e3ps/TemplateProjectServlet";
    document.forms[0].method = "post";
    disabledAllBtn();
    obj.value = '처리중';
    showProcessing();
    document.forms[0].submit();
  }
}



function checkValidate() {
  if(checkField(document.forms[0].name, "Template 명")) {
    return false;
  }

  return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<form method="post">
<!-- Hidden Value -->
<input type="hidden" name="command" value="create">
<input type="hidden" name="IMSI">

<!-- //Hidden Value -->
<!-- title제목 시작 //-->
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="400" valign="top" background="/plm/portal/images/img_drawing/drawtitle_bg01.gif">
      <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="40">&nbsp;</td>
          <td>
           <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="250" height="26" align="middle">
            <param name="allowScriptAccess" value="sameDomain" />
            <param name="movie" value="/plm/portal/flash/sub_title.swf?title=Template 등록"/>
            <param name="quality" value="high" />
            <param name="wmode" value="transparent" />
            <param name="bgcolor" value="#ffffff" />
            <embed src="/plm/portal/flash/sub_title.swf?title=Template 등록" quality="high" wmode="transparent" bgcolor="#ffffff" width="250" height="26" name="sub_title" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
            </object>
          </td>
        </tr>
      </table>
    </td>
    <td background="/plm/portal/images/img_drawing/drawtitle_bg02.gif">
      <!-- 현재위치 시작 //-->
      <table  class="tab_w03" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="position">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00507") %><%--Template 등록--%> </td>
              </tr>
            </table>
      <!-- 현재위치 끝 //-->
    </td>
  </tr>
</table>
<!-- title제목 끝 //-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding-left:12">
<!-------------------------------------- 상단버튼 시작 //-------------------------------------->
      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "02374") %><%--임시저장--%>' onClick="Javascript:createTemplate1(this)"></td>
                <td class=fixRight></td>
                <td>&nbsp;</td>
                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "02424") %><%--작성및결재--%>' onClick="Javascript:createTemplate2(this)"></td>
                <td class=fixRight></td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
<!-------------------------------------- 상단버튼 끝 //-------------------------------------->
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
<!------------------------------ 본문 시작 //------------------------------>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false">
        <jsp:param name="tb_class" value="tab_w01"/>
        <jsp:param name="td_class" value="tab_btm2"/>
      </jsp:include>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false">
        <jsp:param name="tb_class" value="tab_w01"/>
        <jsp:param name="td_class" value="tab_btm1"/>
      </jsp:include>
      <table class="tab_w01" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00509") %><%--Template 명--%><span class="style1"> *</span></td>
          <td class="tdwhiteL"><input name="name" class="txt_field" style="width:99%"></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00506") %><%--Template 기간--%></td>
          <td class="tdwhiteL"><input name="duration" class="txt_field" size=10 onkeyup ="SetNum(this)" style='IME-MODE: disabled'>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%></td>
        </tr>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02761") %><%--참조 Template--%></td>
          <td class="tdwhiteL">
            <jsp:include page="/jsp/project/selectTemplateUseCreate_include.jsp" flush="false"/>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdwhiteL">
            <textarea name="description" cols="110" rows="3" class="fm_area"onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"></textarea>
          </td>
        </tr>
        <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
<!------------------------------ 본문 끝 //------------------------------>
    </td>
  </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
