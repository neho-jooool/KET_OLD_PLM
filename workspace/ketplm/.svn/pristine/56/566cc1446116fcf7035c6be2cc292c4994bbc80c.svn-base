
<%--
    /**
     * @(#)  viewpeople.jsp
     * Copyright (c) whois. All rights reserverd
     *
     * @version 1.00
     * @since jdk 1.4.02
     * @author Cho Sung Ok
     */
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="wt.org.*,wt.session.*"%>
<%@page import="java.util.*,e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    int year = Calendar.getInstance().get(Calendar.YEAR);

    String cmd = request.getParameter("cmd");
    String oid = ParamUtil.getStrParameter(request.getParameter("oid"));
    People pp = (People)CommonUtil.getObject(oid);

    String isDisableCheck = request.getParameter("isDisable");

    if(isDisableCheck == null){
        isDisableCheck = String.valueOf(pp.isIsDisable());
    }

    if(cmd == null){
        cmd = "";
    }

    if(cmd.equals("disable")){
        if(isDisableCheck.equals("true")){
            pp.setIsDisable(true);
            pp.setEmail("PLMTFT@ket.com");
        }else{
            pp.setIsDisable(false);
            pp.setEmail(pp.getId()+"@ket.com");
        }
        pp =(People) PersistenceHelper.manager.modify(pp);
        PersistenceHelper.manager.refresh(pp);
        cmd = "";
    }


    PeopleData data = new PeopleData(pp);
    boolean isAdmin = CommonUtil.isAdmin();
    boolean canUpdate = data.wtuser.equals((WTUser)SessionHelper.manager.getPrincipal());
    canUpdate = canUpdate || isAdmin;







%>
<%@page import="wt.fc.PersistenceHelper"%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<title>Untitled Document</title>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<script language=javascript>
<%if(canUpdate){%>
function updateProxy(wtuseroid, peopleoid)
{
    openWindow('UpdateProxy.jsp?wtuseroid='+wtuseroid+'&peopleoid='+peopleoid, 'P', 350, 170);
}
<%}%>
function update() {
    document.viewpeople.action = "/plm/jsp/groupware/company/peopleUpdateForm.jsp";
    document.viewpeople.submit();
}

function passwdUpdate(id) {
    var url = "/plm/jsp/groupware/company/changePasswordForm.jsp?id="+id;
    openSameName(url,"320","230","status=no,scrollbars=no,resizable=no");
}

function viewEducationHistory(userOid){
    var url = "/plm/jsp/manage/edu/educationlistall.jsp?userOid=" + userOid;
    openOtherName(url,"viewEducationHistory","800","640","status=no,scrollbars=yes,resizable=yes");
}

function viewProjectHistory(userOid){
    var url = "/plm/jsp/groupware/company/userProjectList.jsp?userOid=" + userOid;
    openOtherName(url,"viewEducationHistory","800","640","status=no,scrollbars=yes,resizable=no");
}
<!--
function setup()
{
    openSameName("/plm/wt/security/PrivilegeEditor.jsp", "310","200","status=no,scrollbars=no,resizable=no");
}

function doDisableSubmit(){
    document.viewpeople.cmd.value = "disable";
    document.viewpeople.submit();
}


-->
</script>
</head>
<style type="text/css">

body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
    margin-right:5px;

    overflow-x:hidden;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}

</style>
<body>
<form name="viewpeople" method="post">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="cmd" value="<%=cmd%>">

<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
<!-- title제목 시작 //-->
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01675") %><%--사용자 정보--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "02435") %><%--작업공간--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "01675") %><%--사용자 정보--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>

                    <td align="right">
                        <%if(canUpdate){%>
                        <!-- 
                        <a href="javascript:passwdUpdate('<%=data.id%>');">
                        <img src="/plm/portal/images/img_default/button/board_btn_passEdit.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02979") %><%--패스워드수정--%>" width="98" height="20" border="0">
                        </a>
                        &nbsp;
                         -->
                        <a href="javascript:update();">
                        <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
                        </a>
                        <%}%>
                        &nbsp;
                        <a href="javascript:closeWindow();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>

                </tr>
        </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space5"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
          </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02074") %><%--아이디--%></td>
                    <td class="tdwhiteL">&nbsp;<%=data.id%></td>
                    <td width="180" rowspan="6" class="tdwhiteL0" style="margin:2px;">
                        <table width="10%" align=center><tr><td align=center>
                        <img src="<%=data.imgUrl%>" border="0" width="100" height="120">
                        </td></tr></table>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td class="tdwhiteL">&nbsp;<%=data.name%></td>
                </tr>
                <tr>
                    <td class="tdblueL">E-mail</td>
                    <td class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(data.email)%></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%><br></td>
                    <td class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(data.departmentName)%>
                    <% if(StringUtil.checkString(data.chief)) { %>
                    &nbsp;&nbsp;[ <%=StringUtil.checkNull(data.chief)%> <%=messageService.getString("e3ps.message.ket_message", "01562") %><%--부서장--%> ]
                    <% } %>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                    <td class="tdwhiteL">&nbsp;<%= StringUtil.checkNull(data.duty) %></td>
                </tr>
                <%if(isAdmin){%>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02941") %><%--퇴사처리--%></td>
                    <td class="tdwhiteL">
                        [<input type=radio name="isDisable" value="true" <%if(String.valueOf(data.isDiable).equals("true")){out.print("checked");}%> >]
                        &nbsp;<%=messageService.getString("e3ps.message.ket_message", "02939") %><%--퇴사 설정--%>&nbsp;
                        [<input type=radio name="isDisable" value="false" <%if(String.valueOf(data.isDiable).equals("false")){out.print("checked");} %> >]
                        &nbsp;<%=messageService.getString("e3ps.message.ket_message", "02940") %><%--퇴사 해제--%>&nbsp;&nbsp;&nbsp;<input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01491") %><%--변경--%>' onClick="javascript:doDisableSubmit();" class="s_font">
                    </td>
                </tr>
                <%} %>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "01912") %><%--세부정보--%></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space2"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
          </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03260") %><%--휴대전화--%></td>
                    <td class="tdwhiteL">&nbsp;<%=data.cellTel%></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01658") %><%--사내전화--%></td>
                    <td class="tdwhiteL">&nbsp;<%=data.officeTel%></td>
                </tr>

                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02740") %><%--집 전화번호--%></td>
                    <td class="tdwhiteL">&nbsp;<%=data.homeTel%></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02675") %><%--주소--%></td>
                    <td class="tdwhiteL">&nbsp;<%=data.address%></td>
                </tr>

            </table>
<!-- 권한 보기 -->
<%
if(false){//if(isAdmin) {
%>
<jsp:include page="/jsp/auth/ViewMyE3PSAuthTable.jsp">
    <jsp:param name="useroid" value="<%=oid%>"/>
</jsp:include>
<%  }  %>

</form>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space20"> </td>
                </tr>
          </table>
      </td>
    </tr>
</table>

</body>
</html>
