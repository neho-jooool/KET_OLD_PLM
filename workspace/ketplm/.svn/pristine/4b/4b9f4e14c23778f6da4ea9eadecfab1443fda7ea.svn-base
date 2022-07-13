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
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.People"%>
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

    E3PSTask task = (E3PSTask) issue.getTask();
    E3PSProject E3PSProject =(E3PSProject) task.getProject();
    String jelOid= CommonUtil.getOIDString(E3PSProject);

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


    function ModifyIssue(){
        var cmd = document.forms[0].cmd;
        if(cmd.value == ""){

            if( checkField(document.forms[0].title, "이슈 제목") ){
                document.forms[0].title.focus()
                return;
            }
            if( document.getElementById("disposertable").rows.length < 2 ){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02781") %><%--처리 요청자을(를) 입력하세요--%>');
                addMember('disposertable', 'disposer')
                return;
            }

            if( checkField(document.forms[0].planCompleteDate, "처리 요청일") ){
                openCal('planCompleteDate')
                return;
            }

            if( checkField(document.forms[0].description, "상세 내용") ){
                document.forms[0].description.focus();
                return;
            }
            document.forms[0].cmd.value = "modifyIssue";
        }

        disabledAllBtn();

        ajax.requestFile = "/plm/jsp/project/ProjectIssueAjaxAction.jsp";
        ajax.URLString = getPostData(document.forms[0]);
        ajax.onCompletion = reload;
        ajax.runAJAX();

    }

    //사용자 가져오기 시작 ........................................................................................
    //............................................................................................................
    function addMember(tableid, membertag) {
        var url = "/plm/jsp/project/AddMember.jsp?projectOid=<%=jelOid%>&memberType=4";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=850px; dialogHeight:600px; center:yes");
        if(typeof attacheMembers == "undefined" || attacheMembers == null) {
            return;
        }
        acceptMember(tableid, membertag, attacheMembers);
    }

    function acceptMember(tableid, membertag, objArr) {
        if(objArr.length == 0) {
            return;
        }

        /*
            subArr[0] = chkobj[i].value;//wtuser oid
            subArr[1] = chkobj[i].poid;//people oid
            subArr[2] = chkobj[i].doid;//dept oid
            subArr[3] = chkobj[i].uid;//uid
            subArr[4] = chkobj[i].sname;//name
            subArr[5] = chkobj[i].dname;//dept name
            subArr[6] = chkobj[i].duty;//duty
            subArr[7] = chkobj[i].dutycode;//duty code
            subArr[8] = chkobj[i].email;//email
        */

        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            if(isDuplicateCheckBox(membertag, infoArr[0])) {
                continue;
            }

            nonUserArr[nonUserArr.length] = infoArr;
        }

        if(nonUserArr.length == 0) {
            return;
        }
        if(nonUserArr.length == 1) {

            var objBody = document.getElementById("disposertable");
            var intseq = objBody.rows.length;
            if(intseq == 2){
            objBody.deleteRow(1);
            }
        }
        var targetTable = document.getElementById(tableid);

        for(var i = 0; i < nonUserArr.length; i++) {
            tableRows = targetTable.rows.length;

            infoArr = nonUserArr[i];
            newTr = targetTable.insertRow(tableRows);

            //이름
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = infoArr[4];

            //직위
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = infoArr[6];

            //부서
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = infoArr[5];

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+tableid+"','"+membertag+"','" + infoArr[0] + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+membertag+"' value='" + infoArr[0] + "'>";
        }

    }
     //사용자 가져오기 끝 ........................................................................................

    function reload()
    {
        //var objBody = document.getElementById("taskTable");
        eval(ajax.response);
        enabledAllBtn();
    }



    function viewIssue(oid) {
        var url = "/plm/jsp/project/IssueView.jsp?oid="+oid;
        openSameName(url,"800","500","status=no,scrollbars=no,resizable=no");
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


<title><%=messageService.getString("e3ps.message.ket_message", "02308") %><%--이슈 수정--%></title>
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
<input type="hidden" name = "cmd" >
<input type="hidden" name = "issueOid" value="<%=CommonUtil.getOIDString(issue)%>">
<input type="hidden" name = "taskOid" value="<%=CommonUtil.getOIDString(task)%>">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00273") %><%--Issue 수정--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00273") %><%--Issue 수정--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <% if(DateUtil.getDateString(issue.getCompleteDate(), "D").equals("")) { %>
                        <a href="javascript:ModifyIssue();">
                        <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                    <% } %>
                        <a href="javascript:history.back();">
                        <img src="/plm/portal/images/img_default/button/board_btn_back.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01309") %><%--뒤로--%>' width="62" height="20" border="0">
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
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02311") %><%--이슈 제목--%> <span class="style1"> *</span></td>
                    <td class="tdwhiteL0" colspan = 3>
                        <input name="title" class="txt_field" style="width:70%" value="<%=issue.getTitle()%>" engnum="engnum" onKeyUp="common_CheckStrLength(this, 60)" onChange="common_CheckStrLength(this, 60)"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02314") %><%--이슈 처리자--%> <span class="style1"> *</span></td>
                    <td class="tdwhiteL0" colspan="3">
                        <a href="javascript:addMember('disposertable', 'disposer');">
                        <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" width="62" height="20" border="0">
                        </a>
                        <!--input type='button' value='추가' onClick="javascript:addMember('disposertable', 'disposer');" class="s_font"-->
                        <div style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="disposertable">
                                <tr>
                                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                    <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                                </tr>
<%
    WTUser user =(WTUser)issue.getDisposer().getPrincipal();
    Object object = (Object)user;
    PeopleData peopleData = new PeopleData(object);

%>
                                <tr>
                                    <td class="tdwhiteM" width="150"><%=peopleData.name%></td>
                                    <td class="tdwhiteM" width="100">&nbsp;<%=peopleData.duty%></td>
                                    <td class="tdwhiteM" >&nbsp;<%=peopleData.departmentName%></td>
                                    <td class="tdwhiteM0" width="30">
                                    <a href="#" onClick="javascript:onDeleteTableRow('disposertable','disposer','<%=CommonUtil.getOIDString(user)%>');">
                                    <img src="/plm/portal/icon/del.gif" width='13' height='12' border='0'>
                                    </a>
                                    <input type='hidden' name="disposer" value="<%=CommonUtil.getOIDString(user)%>">
                                    </td>
                                </tr>

                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02789") %><%--처리요청일자--%> <span class="style1"> *</span></td>
                    <td class="tdwhiteL">
                        <input name="planCompleteDate" value="<%=DateUtil.getDateString(issue.getPlanCompleteDate(), "D")%>" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('planCompleteDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('planCompleteDate');"/>
                        <a href="JavaScript:clearDate('planCompleteDate')"><img src="/plm/portal/images/img_common/x.gif" border=0></a>
                    </td>
                </tr>

                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01746") %><%--상세내용--%> <span class="style1"> *</span></td>
                    <td class="tdwhiteL0" colspan="3">
                        <textarea name="description" cols="108" rows="5" class="fm_area"onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"><%=StringUtil.checkNull(issue.getDescription())%></textarea><br>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02472") %><%--적용할 ECR--%> </td>
                    <td class="tdwhiteL0" colspan="3">
                        <a href="javascript:addECR();">
                        <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" width="62" height="20" border="0">
                        </a>
                        <!--input type='button' value='추가' onClick="javascript:addECR();" class="s_font"-->
                        <div style="width=99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="ecrtable">
                                <tr>
                                    <td class="tdblueM" width="80">ECR NO</td>
                                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                                    <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                    <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                                    <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
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
                                    <td class="tdwhiteM0" width="30">
                                        <a href="JavaScript:onDeleteTableRow('ecrtable','EcrLinkOid','<%=data.oid%>')">
                                        <img src="/plm/portal/icon/del.gif" width='13' height='12' border='0'>
                                        </a>
                                        <input type='hidden' name='EcrLinkOid' value="<%=data.oid%>">
                                    </td>
                                </tr>
<%      }

%>
                            </table>
                        </div>
                    </td>
                </tr>
<%

        }catch(Exception e){
            Kogger.error(e);
        }
%>
            </table>
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
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
