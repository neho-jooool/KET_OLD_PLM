<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.issue.Issue"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.issue.IssueEcrLink"%>
<%@page import="e3ps.change.E3PSEChangeRequest"%>
<%@page import="e3ps.change.beans.ECRData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleData"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<%

    String oid = request.getParameter("oid");
    Issue issue = (Issue)CommonUtil.getObject(oid);
    E3PSTask task = (E3PSTask)issue.getTask();
    E3PSProject E3PSProject = (E3PSProject) task.getProject();
    WTUser user=(WTUser)SessionHelper.manager.getPrincipal();
    PeopleData peoData = new PeopleData(user);
%>

<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<Script>
    var ajax = new sack();

    function viewIssue(oid) {
        var url = "/plm/jsp/project/IssueView.jsp?oid="+oid;
        openSameName(url,"800","500","status=no,scrollbars=no,resizable=no");
    }

    function ModifyIssue(oid) {
        location.href  = "/plm/jsp/project/IssueModify.jsp?oid="+oid;
    }

    function reload()
    {
        eval(ajax.response);
        enabledAllBtn();
    }

    // ECR 가져오기 시작

    function addECR() {
        var url = "/plm/jsp/project/AddIssueECR.jsp?mode=multi";
        attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=840px; dialogHeight:460px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }
        acceptECR(attache);
    }

    function acceptECR(objArr) {
        if(objArr.length == 0) {
            return;
        }


        var targetTable = document.getElementById("ecrtable");

        if(targetTable.rows.length > 1) {
            targetTable.deleteRow(1);
        }

        var trArr;
        for(var i = 0; i < objArr.length; i++) {
            tableRows = targetTable.rows.length;

            trArr = objArr[i];
            newTr = targetTable.insertRow(tableRows);


            //ECR NO
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = trArr[1];

            //ECR 제목
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = trArr[2];

            //작성자
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = trArr[3];

            //작성일자
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = trArr[4];

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('ecrtable','EcrLinkOid','" + trArr[0] + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='EcrLinkOid' value='" + trArr[0] + "'>";
        }

    }
    // ECR 끝

    function onDeleteTableRow(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    targetTable.deleteRow(i+1);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                targetTable.deleteRow(1);
                return;
            }
        }
    }


    function isDuplicateCheckBox(chk_name, chk_value) {
        var chkTag = document.all.item(chk_name);//eval("document.forms[0]." + membertag);
        if(chkTag == null || chkTag == 'undefined') {
            return false;
        }

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    return true;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                return true;
            }
        }

        return false;
    }

    function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
    }

</Script>
<title><%=messageService.getString("e3ps.message.ket_message", "02307") %><%--이슈 보기--%></title>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00272") %><%--Issue 상세정보--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00272") %><%--Issue 상세정보--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <% if(CommonUtil.isAdmin()||DateUtil.getDateString(issue.getCompleteDate(), "D").equals("")&&(issue.getReqeuster().getFullName().equals(peoData.name))) { %>
                        <a href="javascript:ModifyIssue('<%=oid%>');">
                        <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                    <% } %>
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="35%"><COL width="15%"><COL width="35%">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03085") %><%--프로젝트 이름--%> </td>
                    <td class="tdwhiteL">
                        &nbsp;<%=E3PSProject.getPjtName()%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%> </td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=task.getTaskName()%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02311") %><%--이슈 제목--%></td>
                    <td class="tdwhiteL0" colspan = 3>
                        &nbsp;<%=issue.getTitle()%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02319") %><%--이슈요청자--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=issue.getReqeuster().getFullName() %>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02789") %><%--처리요청일자--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=DateUtil.getDateString(issue.getPlanCompleteDate(), "D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01746") %><%--상세내용--%>  </td>
                    <td class="tdwhiteL0" colspan="3">
                        <textarea name="description" cols="108" rows="5" class="fm_area" READONLY style="width:100%"> <%=StringUtil.checkNull(issue.getDescription())%> </textarea><br>
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <COL width="15%"><COL width="35%"><COL width="15%"><COL width="35%">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02314") %><%--이슈 처리자--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=issue.getDisposer().getFullName() %>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02790") %><%--처리일자--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=DateUtil.getDateString(issue.getCompleteDate(), "D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02777") %><%--처리 내용--%></td>
                    <td class="tdwhiteL0" colspan="3">
                        <textarea name="description" cols="108" rows="5" class="fm_area" READONLY style="width:100%"> <%=StringUtil.checkNull(issue.getHandlingcontents())%> </textarea><br>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02472") %><%--적용할 ECR--%> </td>
                    <td class="tdwhiteL0" colspan="3">
                        <!--  <input type='button' value='추가' onClick="javascript:addECR();" class="s_font"> -->
                        <div style="width=99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="ecrtable">
                                <tr>
                                    <td class="tdblueM" width="80">ECR NO</td>
                                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                                    <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                    <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                                    <!-- <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>  -->
                                </tr>
<%
        try {
        QueryResult qr = PersistenceHelper.manager.navigate(issue,IssueEcrLink.ECR_ROLE,IssueEcrLink.class);
            while(qr.hasMoreElements()){
                Object obj = (Object)qr.nextElement();
                E3PSEChangeRequest ecr = (E3PSEChangeRequest)obj;
                ECRData data = new ECRData(ecr);
%>
                                <tr>
                                    <td class="tdwhiteM" width="80">&nbsp;<%=data.number%></td>
                                    <td class="tdwhiteM" > &nbsp;<%=data.title%></td>
                                    <td class="tdwhiteM" width="80">&nbsp;<%=ecr.getCreatorFullName()%></td>
                                    <td class="tdwhiteM" width="80">&nbsp;<%=DateUtil.getDateString(ecr.getPersistInfo().getCreateStamp(), "d")%></td>
                                </tr>
<%      }

%>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
<%

        }catch(Exception e){
            Kogger.error(e);
        }
%>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            </td>
            <td class="boxRight"></td>
        </tr>
        <tr>
            <td class="boxBLeft"></td>
            <td valign="bottom" class="boxBCenter"></td>
            <td class="boxBRight"></td>
        </tr>
    </table>
</form>
</body>
</html>
