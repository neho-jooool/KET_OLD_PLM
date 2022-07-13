<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.fc.*"%>
<%@page import = "wt.org.*"%>
<%@page import = "e3ps.common.util.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>
<%@page import="wt.session.SessionHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    ProjectOutput output = null;

    String oid = request.getParameter("oid");
    if(oid == null) {
        oid = "";
    }

    ReferenceFactory rf = new ReferenceFactory();
    if(oid.length() > 0) {
        output = (ProjectOutput)rf.getReference(oid).getObject();
    }

    ProjectOutputData outputData = new ProjectOutputData(output);

    String sType = StringUtil.checkNull(request.getParameter("stype"));
    if(sType != null) sType = "PLM";



%>
<html>
<head>
<base target="_self">

<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="JavaScript" src="/plm/portal/js/Calendar.js"></script>
<style type="text/css">
<!--
.style1 {
    color: #FF0000
}
-->
</style>
<script language='javascript'>
<!--
function clearDate(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
}

function onSearch() {
    form = document.docLink;
    form.method = "post";
    form.action = "/plm/jsp/project/ProjectOutputResultLinkList.jsp?docTypeOid=<%=output.getOutputKeyType()%>";
    form.target = "list";
    form.submit();
}



function wincenter2(siz,opt) {
    if (opt == 1) {
        return (screen.availWidth - siz) / 2 ;
    }
    else if (opt == 2) {
        return (screen.availHeight - siz) / 2 ;
    }
    else {
        return false;
    }
}
function onSave(docid) {
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03303") %><%--링크등록을 하시겠습니까?--%>")) {
            return;
    }

    onProgressBar();

    var param = "command=inputOutputLink";
    param += "&oid=<%=oid%>";
    param += "&docOid=" + docid;
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp?" + param;
    callServer(url, onMessage);
}

function onMessage(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
    } else {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    window.returnValue = true;
    window.close();
}

//-->
</script>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
    margin-right:10px;

    overflow-x:hidden;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
}
-->
</style>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form method="post" name="docLink">
<!-- hidden begin -->
<input type='hidden' name='oid' value='<%=oid%>'>
<input type='hidden' name='docTypeOid' value='<%=output.getOutputKeyType()%>'>

<!-- hidden end -->
    <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="680" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table width="680" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/logo_popupbg.png">
                      <table height="28" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01719") %><%--산출물 등록--%></td>
                            <td width="10"></td>
                          </tr>
                      </table>
                      </td>
                      <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                  </table>
                </td>
            </tr>
            <tr>
              <td class="space10"></td>
            </tr>
              </table>
                <table border="0" cellspacing="0" cellpadding="0" width="680">
                <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:onSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="680">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="680">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="680">
                <col width='20%'><col width='30%'><col width='20%'><col width='30%'>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                    <td class="tdwhiteL0" colspan=3>&nbsp;<%=outputData.location%></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                    <td class="tdwhiteL0" colspan=3><input name="name" class="txt_field" style="width:99%;" value="<%=outputData.name%>"></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                    <td class="tdwhiteL" ><input name="number" class="txt_field" style="width:98%;" value=""></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td class="tdwhiteL0" ><input name="creator" class="txt_field" style="width:98%;" value=""></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                    <td class="tdwhiteL0" colspan=3>

              <input name="predate" value="" datemsg="" class="txt_field" size="12" engnum="engnum" maxlength=15 />
            <a href="#" onclick="javascript:showCal('predate');"><img src="/plm/portal/images/icon_6.png" border=0></a>
            <a href="JavaScript:clearDate('predate')"><img src="/plm/portal/images/icon_delete.gif" border=0></a>&nbsp;~&nbsp;
            <input name="postdate" value="" datemsg="" class="txt_field" size="12" engnum="engnum" maxlength=15/>
            <a href="#" onclick="javascript:showCal('postdate');"><img src="/plm/portal/images/icon_6.png" border=0></a>
            <a href="JavaScript:clearDate('postdate')"><img src="/plm/portal/images/icon_delete.gif" border=0></a>&nbsp;&nbsp;(yyyy-mm-dd)


                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="700">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="700">
                <tr>
                    <td>
                        <iframe src="/plm/jsp/project/ProjectOutputResultLinkList.jsp?name=<%=outputData.name%>&docTypeOid=<%=output.getOutputKeyType()%>" name="list" frameborder="0" width="680" height="500" leftmargin="0" topmargin="0" scrolling="no"></iframe>
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="700">
                <tr>
                    <td class="space20"></td>
                </tr>
            </table>
<!------------------------------------- 본문 끝 //----------------------------------------->
        </td>
    </tr>
</table>
<!-- ############################################################################################################################## -->
</form>
</body>
</html>
