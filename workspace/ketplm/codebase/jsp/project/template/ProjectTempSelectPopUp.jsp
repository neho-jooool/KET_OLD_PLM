<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
  String tmpName = request.getParameter("tmpName");
  String tmpCode = request.getParameter("tmpCode");
  String mode = request.getParameter("mode");
  String invokeMethod = request.getParameter("invokeMethod");
  String type = request.getParameter("type");
  String wType = request.getParameter("wType");
  String selectReview = request.getParameter("selectReview");
  if(tmpName == null) {
    tmpName = "";
  }
  if(tmpCode == null) {
    tmpCode = "";
  }

  if(mode == null) {
    mode = "one";
  }
  if(invokeMethod == null) {
    invokeMethod = "";
  }
  if(type == null){
    type = "";
  }
  if(wType == null){
    wType = "";
  }
  if(selectReview == null){
    selectReview = "";
  }

%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00557")%><%--WBS 목록--%></title>
<base target="_self">
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {
    color: #FF0000
}
-->
</style>
<script language='javascript'>
<!--
function onSearch() {
  form = document.forms[0];

  var wname = form.tmpName.value;
  var wbstypeV = form.wType.value;

  form.method = "post";
  form.action = "/plm/jsp/project/template/ProjectTempListTable.jsp?name=" + wname + "&code=&type=<%=type%>" + "&wType=<%=wType%>&selectReview=<%=selectReview%>";
  form.target = "list";
  form.submit();
}


function onSelect() {
  form = document.forms[0];

  var arr = list.checkList();
  if(arr.length == 0) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00565") %><%--WBS가 선택되지 않았습니다--%>");
    return;
  }

<%  if(invokeMethod.length() == 0) {  %>
  //modal dialog
  selectModalDialog(arr);
<%  } else {  %>
  //open window
  selectOpenWindow(arr);
<%  }  %>

}

function selectModalDialog(arrObj) {
  window.returnValue= arrObj;
  window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function selectOpenWindow(arrObj) {
  //...이하 원하는 스크립트를 만들어서 작업...
  if(opener) {
    if(opener.<%=invokeMethod%>) {
      opener.<%=invokeMethod%>(arrObj);
    }
  }

  if(parent.opener) {
    if(parent.opener.<%=invokeMethod%>) {
      parent.opener.<%=invokeMethod%>(arrObj);
    }
  }
  self.close();
}

<%  }  %>

    function onKeyPress() {
      var keycode;
      if (window.event) {
        keycode = window.event.keyCode;
      }else{
        return true;
      }

      if (keycode == 13) {    //엔터키를 눌렀을때
        onSearch();
        return false
      }
      return true
    }
    document.onkeypress = onKeyPress;
//-->
</script>
<style type="text/css">
<!--
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

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
-->
</style>
</head>
<body>
<form method="post">
<!-- hidden begin -->
<input type="hidden" name="command" value="select">
<input type="hidden" name="mode" value="<%=mode%>">
<!-- hidden end -->
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td valign="top" style="padding: 0px 0px 0px 0px">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table height="28" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00557")%><%--WBS 목록--%></td>
                                        <td width="10"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td><input type="hidden" name=wType value="<%=wType%>"> <b>Name</b> : <input
                                            name="tmpName" class="txt_field" width="50px" value="<%=tmpName%>"></td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:onSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:onSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td><iframe
                                    src="/plm/jsp/project/template/ProjectTempListTable.jsp?command=select&mode=<%=mode%>&wType=<%=wType%>&selectReview=<%=selectReview%>"
                                    id="list" name="list" frameborder="0" width="100%" height="300" leftmargin="0" topmargin="0"
                                    scrolling="no"></iframe></td>
                        </tr>
                    </table>
                </td>
            </tr>
    </form>
</body>
</html>
