<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.fc.*"%>
<%@page import = "wt.org.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    ProjectOutput output = null;

	String returnTargetUrl = request.getParameter("returnTargetUrl");
    String oid = request.getParameter("oid");
    if(oid == null) {
        oid = "";
    }

    ReferenceFactory rf = new ReferenceFactory();
    if(oid.length() > 0) {
        output = (ProjectOutput)rf.getReference(oid).getObject();
    }

    ProjectOutputData outputData = new ProjectOutputData(output);
    E3PSProject project = (E3PSProject)outputData.project;
    E3PSTask task = (E3PSTask)outputData.task;
    String taskType = task.getTaskType();
    String taskName = task.getTaskName().trim();
    String dwgNo = "";
    if(project != null){
        if(project instanceof MoldProject){
	        dwgNo = project.getPjtNo()+"*";
	        if("DWG".equals(output.getObjType()) && taskName.indexOf("제품도") >= 0) {
	            //2014-09-26
	            //금형프로젝트
			    //Task중 ‘제품도’ 명칭이 들어가는 Task(ex. 제품도출도, 제품도 등록 등)
	            //산출물 타입이 ‘도면’인 경우
		        //‘링크등록’ Popup의 ‘도면번호’ Field에, 금형프로젝트 ‘제품’ Tab의 Part No 를 기본 Setting 해줌 ( 기존에는 Die No.로 Setting 되어있음)
			    MoldProject moldProject = (MoldProject)project;
			    MoldItemInfo moldInfo = moldProject.getMoldInfo();
			    String partNoStr = moldInfo.getPartNo();
			    if(partNoStr!=null && partNoStr.startsWith("H")) partNoStr = "*"+partNoStr.substring(1);
			    dwgNo = partNoStr+"_2D*";
	        }
        }
    }

%>
<html>
<head>
<base target="_self">
<%@include file="/jsp/common/top_include.jsp" %>
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
    form = document.forms[0];
    form.method = "post";
    form.action= "/plm/jsp/project/ProjectOutputDwgResultLinkList.jsp";
    form.target = "list";
    form.submit();
}

function onSave(docid) {
    onProgressBar();

    var param = "command=inputOutputLink";
    param += "&oid=<%=oid%>";
    param += "&docOid=" + docid;
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp?" + param;
    callServer(url, onMessage);

<% if(returnTargetUrl==null || "".equals(returnTargetUrl)) { 
%>
    opener.document.location.reload();
<% }else { %>
//예,returnTargetUrl=/plm/jsp/project/ProjectViewFrm.jsp?oid=Oid:e3ps.project.E3PSTask:100000646355"
    opener.parent.document.location.href='<%=returnTargetUrl%>&popup=popup'; 
<% } %>
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
    margin-top: 0px;
    margin-left:12px;
    margin-right:10px;

    overflow-x:auto;
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
<form method="post">
<!-- hidden begin -->
<input type='hidden' name='oid' value='<%=oid%>'>

<!-- hidden end -->
    <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="700" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table width="700" border="0" cellspacing="0" cellpadding="0">
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
             <table width="700" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
            <table border="0" cellpadding="0" cellspacing="0" width="700">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="700">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
                <col width='20%'><col width='30%'><col width='20%'><col width='30%'>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                    <td class="tdwhiteL0" colspan=3><input name="name" class="txt_field" style="width:100%;" value=""></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                    <td class="tdwhiteL0" >
                    <input name="number" class="txt_field" style="width:100%;" value="<%=dwgNo%>">
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td class="tdwhiteL0" ><input name="creator" class="txt_field" style="width:98%;" value=""></td>

                </tr>
                <tr>
                    <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                    <td class="tdwhiteL0" colspan=3>

                      <input name="modify_start" value="" datemsg="" class="txt_field" size="12" engnum="engnum" maxlength=15 />
                    <a href="#" onclick="javascript:showCal('modify_start');"><img src="/plm/portal/images/icon_6.png" border=0></a>
                    <a href="JavaScript:clearDate('modify_start')"><img src="/plm/portal/images/icon_delete.gif" border=0></a>&nbsp;~&nbsp;
                    <input name="modify_end" value="" datemsg="" class="txt_field" size="12" engnum="engnum" maxlength=15/>
                    <a href="#" onclick="javascript:showCal('modify_end');"><img src="/plm/portal/images/icon_6.png" border=0></a>
                    <a href="JavaScript:clearDate('modify_end')"><img src="/plm/portal/images/icon_delete.gif" border=0></a>&nbsp;&nbsp;(yyyy-mm-dd)

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
                        <iframe src="/plm/jsp/project/ProjectOutputDwgResultLinkList.jsp?name=<%=outputData.name%>" id="list" name="list" frameborder="0" width="700" height="600" leftmargin="0" topmargin="0" scrolling="no"></iframe>
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
