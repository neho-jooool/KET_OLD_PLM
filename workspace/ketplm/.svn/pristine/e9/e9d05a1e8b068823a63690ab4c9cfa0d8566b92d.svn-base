<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.fc.*"%>
<%@page import = "wt.org.*"%>
<%@page import = "e3ps.access.*,e3ps.access.service.*"%>
<%@page import = "e3ps.common.util.*"%>
<%@page import = "e3ps.groupware.company.*"%>
<%@page import = "e3ps.doc.*,e3ps.doc.beans.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String ecrNo = request.getParameter("ecrNo");
    String creator = request.getParameter("creator");
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");


    if(ecrNo == null) {
        ecrNo = "";
    }

    if(creator == null) {
        creator = "";
    }

    if(mode == null) {
        mode = "multi";
    }

    if(invokeMethod == null) {
        invokeMethod = "";
    }

%>
<html>
<head>
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

    var ecrNo = form.ecrNo.value;
    var creator = form.creator.value;

    form.method = "post";
    form.action= "/plm/jsp/project/ECRListTable.jsp?ecrNo=" + ecrNo + "&creator=" + creator;
    form.target = "list";
    form.submit();
}


function onSelect() {
    form = document.forms[0];

    var arr = list.checkList();
    if(arr.length == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01817") %><%--선택한 Template이 없습니다--%>");
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
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form method="post">
<!-- hidden begin -->
<input type="hidden" name="command" value="select">
<input type="hidden" name="mode" value="<%=mode%>">
<!-- hidden end -->
<!--  layer 시작 -->
    <table border="0" cellpadding="0" cellspacing="0" class="popBox">
        <tr>
            <td class="boxTLeft"></td>
          <td class="boxTCenter"></td>
            <td class="boxTRight"></td>
        </tr>
        <tr>
            <td class="boxLeft"></td>
            <td>
<!------------------------------------- 본문 시작 //----------------------------------------->
                <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
                    <tr>
                        <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "00271") %><%--Issue 목록--%></td>
                        <td align="right" width='70%'>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><b>ECR NO</b> : <input name="ecrNo" class="txt_field" width="50px" value='<%=ecrNo%>'></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td>&nbsp;</td>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><b><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></b> : <input name="creator" class="txt_field" size=10 maxlength='10'  value='<%=creator%>'></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td>
                                        &nbsp;&nbsp;
                                        <a href="#" onclick="#" onclick="javascript:onSearch();">
                                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="52" height="20" border="0">
                                        </a>
                                        &nbsp;&nbsp;
                                        <a href="#" onclick="javascript:onSelect();">
                                        <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
                                        </a>
                                        &nbsp;&nbsp;
                                        <a href="#" onclick="javascript:self.close();">
                                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tab_w01">
                    <tr>
                        <td>
                            <iframe src="/plm/jsp/project/ECRListTable.jsp?command=select&mode=<%=mode%>" id="list" name="list" frameborder="0" width="100%" height="400" leftmargin="0" topmargin="0" scrolling="no"></iframe>
                        </td>
                    </tr>
                </table>

<!------------------------------------- 본문 끝 //----------------------------------------->
            </td>
            <td class="boxRight"></td>
        </tr>
        <tr>
            <td class="boxBLeft"></td>
            <td valign="bottom" class="boxBCenter"></td>
            <td class="boxBRight"></td>
        </tr>
    </table>
<!--  layer 끝 -->
<!-- ############################################################################################################################## -->
</form>
</body>
</html>
