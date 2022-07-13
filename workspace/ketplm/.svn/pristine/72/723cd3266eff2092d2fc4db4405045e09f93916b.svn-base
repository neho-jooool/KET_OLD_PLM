<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,wt.org.*,
                wt.team.*,
                wt.project.Role"%>
<%@page import="e3ps.common.util.*, e3ps.project.*, e3ps.project.beans.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
    String invokeMethod = request.getParameter("invokeMethod");
    if(invokeMethod == null || invokeMethod.length() == 0)
        invokeMethod = "";

    HashMap roleUser = new HashMap();
    ProjectMemberLink mlink = null;

    ReferenceFactory rf = new ReferenceFactory();
    TemplateProject project = (TemplateProject)rf.getReference(oid).getObject();
    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.ROLEMEMBER);

    Object obj[] = null;
    while(qr.hasMoreElements()) {
        obj = (Object[])qr.nextElement();
        mlink = (ProjectMemberLink)obj[0];
        if(mlink.getPjtRole() != null && mlink.getPjtRole().length() > 0) {
            roleUser.put(mlink.getPjtRole(), mlink.getMember());
        }
    }

    Vector vecTeamStd = null;
    // 기준 Team
    Object pobj = CommonUtil.getObject(oid);

    TeamTemplate tempTeam = null;
    if(pobj instanceof ReviewProject){
        tempTeam =	TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
    }else if(pobj instanceof ProductProject){
        if("자동차 사업부".equals( ((ProductProject)pobj).getTeamType() )){

            tempTeam =	TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");

        }else if("KETS".equals( ((ProductProject)pobj).getTeamType() )) {

            tempTeam =	TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");

        }else{

            tempTeam =	TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");

        }
    }else if(pobj instanceof MoldProject){
        tempTeam =	TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
    }else if(pobj instanceof WorkProject){
        tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Work Project");
    }


    if(tempTeam != null) {
        vecTeamStd = tempTeam.getRoles();
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00445") %><%--Role 담당자 수정--%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<base target="_self">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<script language="javascript">
<!--
function pageClose() {
<%
    if(invokeMethod.length() == 0) {
%>
        window.returnValue = true;
<%
    }
%>
    window.close();
}

function onSave() {
<%
    if(invokeMethod.length() == 0) {
%>
                var param = "command=addRoleMember";
                param += "&oid=<%=oid%>";
<%
        if(vecTeamStd != null) {
            Role role = null;
            for (int i = 0; i < vecTeamStd.size();i++) {
                role = (Role) vecTeamStd.get(i);
%>
                if(param.length > 0) {
                    param += "&";
                }
                param += getParamValue("<%=role.toString()%>");
<%
            }
        }
%>
        showProcessing();
        var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
        postCallServer(url, param, onMessage, true);
<%
    }
    else {
%>
    form = document.forms[0];
    form.target = "roleAssignFrame";
    form.action = "/plm/jsp/project/projectMemberAssign.jsp";
    form.submit();
<%
    }
%>
}

function getParamValue(name_str) {
    var paramObj = document.all.item(name_str);

    if(paramObj == null || paramObj == 'undefined') {
        return "";
    }

    var rtnParam = "";
    var paramLen = paramObj.length;
    if(paramLen) {
        for(var i = 0; i < paramLen; i++) {
            if(rtnParam.length > 0) {
                rtnParam += "&";
            }
            rtnParam += name_str + "=" + paramObj[i].value;
        }
    }else {
        rtnParam += name_str + "=" + paramObj.value;
    }

    return rtnParam;
}

window.getTagText = function(xmlDoc){
    return xmlDoc.textContent || xmlDoc.text || '';
}

function onMessage(req) {
    var xmlDoc = req.responseXML;
    var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
    if(msg == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        hideProcessing();
        return;
    }

    alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
    hideProcessing();
    
    if(opener) {
    	opener.location.reload();
    }else if(parent.opener) {
        parent.opener.location.reload();
    }
    self.close();
    
}

function returnMethod() {
    //alert(parent.document.location.href);
    if(opener) {
        form = opener.document.forms[0];
        form.target = "_self";
        form.action = "/plm/jsp/project/ProjectView.jsp";
        form.submit();
    }

    window.close();
}
var targetUserId = "";
function addRoleMember(rname) {
	targetUserId = rname;
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=acceptRoleMember";
    
    window.open(url,"roleMember"+rname,"top=100px, left=100px, height=800px, width=710px");
    
}

function acceptRoleMember(objArr) {
	var rname = targetUserId;
    if(objArr.length == 0) {
        return;
    }

    var displayName = document.getElementById("temp" + rname);
    var keyName = document.getElementById(rname);

    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
        infoArr = objArr[i];
        document.getElementById("temp" + rname).value = infoArr[4];
        document.getElementById(rname).value = infoArr[0];

    }
}

function deleteRoleMember(rname) {
    document.getElementById("temp" + rname).value = "";
    document.getElementById(rname).value = "";
}
// -->
</script>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
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
</style>
<%@include file="/jsp/common/multicombo.jsp"%>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form method="post">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="command" value="roleAssign">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/logo_popupbg.png">
                      <table height="28" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00445") %><%--Role 담당자 수정--%></td>
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
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:pageClose();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <!-- 프로젝트 기본정보 -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <COL width="15%"><COL width="18%"><COL width="15%"><COL width="18%"><COL width="15%"><COL width="18%">
                <tr>
                    <td class="tdblueM">Role</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td class="tdblueM">Role</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td class="tdblueM">Role</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                </tr>
<%
    if(vecTeamStd != null && vecTeamStd.size() > 0) {
        Collections.sort(vecTeamStd, new RoleComparator(true,true));
        Role role = null;
        String roleName_en = null;
        String roleName_ko = null;
        WTUser wtuser = null;

        int roleIndex = 0;
        int colspan = 1;
        int tabindex = 100;
        for (int i = vecTeamStd.size() - 1; i > -1; i--) {
            role = (Role) vecTeamStd.get(i);
            roleName_en = role.toString();
            roleName_ko = role.getDisplay(messageService.getLocale());
            wtuser = (WTUser)roleUser.get(roleName_en);
            if(i == 0 && roleIndex%2 == 0){
                //colspan = 3;
            }

            if(roleIndex%3 == 0) {
                out.println("<tr>");
            }
%>
                    <td class="tdwhiteM"><%=roleName_ko%></td>
                    <td class="tdwhiteM"  colspan="<%=colspan%>">
                        <script type="text/javascript">
                        $(document).ready(function(){
                            //사용자 suggest
                            SuggestUtil.bind('USER', 'temp<%=roleName_en%>', '<%=roleName_en%>');
                        });
                        </script>
                        <input type='text' tabindex="<%=tabindex++%>" class="txt_field" style="width:75px" id="temp<%=roleName_en%>" name='temp<%=roleName_en%>' value='<%=wtuser==null?"":wtuser.getFullName()%>'>
                        <input type='hidden' id='<%=roleName_en%>' name='<%=roleName_en%>' value='<%=wtuser==null?"":CommonUtil.getOIDString(wtuser)%>'>
                        <a href="#" onclick="javascript:addRoleMember('<%=roleName_en%>');"><img src="/plm/portal/images/icon_5.png" border=0></a>
                        <a href="javascript:deleteRoleMember('<%=roleName_en%>');"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
                    </td>
<%
            if(i == 0){
                if(roleIndex%3 == 0) { //2명
                    %>
                    <td class="tdwhiteM">&nbsp;</td>
                    <td class="tdwhiteM">&nbsp;</td>
                    <td class="tdwhiteM">&nbsp;</td>
                    <td  class="tdwhiteM">&nbsp;</td>
                    <%
                }else if(roleIndex%3 == 1) { // 1명
                    %>
                    <td class="tdwhiteM">&nbsp;</td>
                    <td class="tdwhiteM">&nbsp;</td>
                    <%
                }
            }

            if(roleIndex%3 == 2 || i == 0) {
                out.println("</tr>");
            }

            roleIndex++;
        }
    }
%>
            </table>
<!------------------------------------- 본문 끝 //----------------------------------------->
        </td>
    </tr>
</table>
<iframe src="" name="roleAssignFrame" scrolling=no frameborder=no marginwidth=0 marginheight=0 style="display:none"></iframe>
</form>
</body>
</html>
