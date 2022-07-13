<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.code.GenNumberCode,
                 e3ps.common.web.HtmlTagUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<%@page import="e3ps.common.jdf.config.Config"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>
<%@page import="wt.lifecycle.LifeCycleTemplate"%>
<%@page import="wt.lifecycle.LifeCycleHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.lifecycle.PhaseTemplate"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<html>
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Form</title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<%
    Config conf = ConfigImpl.getInstance();
    String produceproject = conf.getString("produceproject.value");
    String devproject = conf.getString("devproject.value");
    String command = request.getParameter("command");

    String mode =  request.getParameter("mode");//multi
    String invokeMethod = request.getParameter("invokeMethod");
    if(mode == null || mode.length() == 0) mode = "none";
    if(invokeMethod == null || invokeMethod.length() == 0) invokeMethod = "";

    boolean isProject = false;
    GenNumberCode genCode = new GenNumberCode();


    boolean isOnLoad = false;
    String pjtNo = request.getParameter("pjtNo");
    String fromPage = request.getParameter("fromPage");

    if(pjtNo == null)
        pjtNo = "";

    if(fromPage == null)
        fromPage = "";


    if(pjtNo.length() > 0 && "errorCreate".equals(fromPage)) {
        isOnLoad = true;
    }

%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/plm/portal/js/tableScriptChild2.js"></SCRIPT>

<script language=JavaScript>
<!--
function searchProjectMulti() {
    if(checkDate())return;
    document.forms[0].action= "/plm/servlet/e3ps/ProjectServlet?command=selectdoc&mode=<%=mode%>";
    //document.forms[0].action= "/plm/jsp/project/ListE3PSProject_AdvancedQuery.jsp?command=selectdoc&mode=<%=mode%>";

    document.forms[0].target = "list";
    document.forms[0].submit();
}

function openCal(param) {
    var str="/plm/jsp/common/calendar.jsp?obj="+param;
    newWin = window.open(str,"CalWin", "scrollbars=no,status=yes,menubar=no,toolbar=no,location=no,directories=no,width=200,height=200,resizable=yes,left=590,top=412");
    newWin.focus();
}

function selectPM(target) {
    var url = "/plm/jsp/groupware/company/selectPeopleFrm.jsp?function=addUserToTarget&mode=s&target="+target;
    newWin = window.open(url,"selectUser","status=no,scrollbars=no,resizable=no,location=no,width=600,height=500");
    newWin.focus();
}

function checkDate() {
    var planStartStartDate = document.forms[0].planStartStartDate.value;
    var planStartEndDate = document.forms[0].planStartEndDate.value;
    if(planStartStartDate.length>0 && planStartEndDate.length>0) {
        if(planStartStartDate>planStartEndDate) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00800") %><%--계획 시작일자 검색기간이 잘못입력되었습니다--%>");
            document.forms[0].planStartStartDate.value = "";
            document.forms[0].planStartEndDate.value = "";
            return true;
        } else {
            return false;
        }
    }

    var planEndStartDate = document.forms[0].planEndStartDate.value;
    var planEndEndDate = document.forms[0].planEndEndDate.value;
    if(planEndStartDate.length>0 && planEndEndDate.length>0) {
        if(planEndStartDate>planEndEndDate) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00805") %><%--계획 종료일자 검색기간이 잘못입력되었습니다--%>");
            document.forms[0].planEndStartDate.value = "";
            document.forms[0].planEndEndDate.value = "";
            return true;
        } else {
            return false;
        }
    }

    var acceptanceStartDate = document.forms[0].acceptanceStartDate.value;
    var acceptanceEndDate = document.forms[0].acceptanceEndDate.value;
    if(acceptanceStartDate.length>0 && acceptanceEndDate.length>0) {
        if(acceptanceStartDate>acceptanceEndDate) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01967") %><%--수주일자 검색기간이 잘못입력되었습니다--%>");
            document.forms[0].acceptanceStartDate.value = "";
            document.forms[0].acceptanceEndDate.value = "";
            return true;
        } else {
            return false;
        }
    }

    var deliveredStartDate = document.forms[0].deliveredStartDate.value;
    var deliveredEndDate = document.forms[0].deliveredEndDate.value;
    if(deliveredStartDate.length>0 && deliveredEndDate.length>0) {
        if(deliveredStartDate>deliveredEndDate) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02885") %><%--출하일자 검색기간이 잘못입력되었습니다--%>');
            document.forms[0].deliveredStartDate.value = "";
            document.forms[0].deliveredEndDate.value = "";
            return true;
        } else {
            return false;
        }
    }
}

function onSelectBtn() {
    if(list.document.forms[0] == null || !list.checkedCheck()) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01806") %><%--선택된 결과가 없습니다--%>");
        return;
    }
    resultArr = list.getCheckedData();

    //리턴 값 : Array
    //sub : Array :[0] : oid, [1]:number, [2]:name, [3]:version
    //alert(arr.length + "\n" + arr);
<%  if(invokeMethod.length() == 0) {  %>
    //modal dialog
    selectModalDialog(resultArr);
<%  } else {  %>
    //open window
    selectOpenWindow(resultArr);
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

function doClear(){
    document.forms[0].reset();
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
}
-->
</style>
</head>
<body <%if(isOnLoad){%>onload="javascript:searchProjectMulti();"<%}%>>
<form method=post >
<input type=hidden name=mode value="<%=mode%>">
<input type=hidden name=fromPage value="iframe">
<!-- title제목 시작 //-->
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%></td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:searchProjectMulti();">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:onSelectBtn();">
                        <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:doClear();">
                        <img src="/plm/portal/images/img_default/button/board_btn_clear.gif" alt="CLEAR" width="76" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="35%"><COL width="15%"><COL width="35%">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL">
                        <input name="pjtNo" class="txt_field" value="<%=pjtNo%>" style="width:85%"/>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                    <td class="tdwhiteL0">
                        <input name="pjtName" class="txt_field" style="width:85%"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03098") %><%--프로젝트 종류--%></td>
                    <td class="tdwhiteL">
                        <select name="pjtType" id=i>
                            <option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                            <option value="1"><%=produceproject%></option>
                            <option value="2"><%=devproject%></option>
                        </select>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL0">
                        <select name="pjtState" class="fm_jmp" style="width:85%">
                            <OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></OPTION>
<%
                try {
                        LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("Project_LC");
                        Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
                        PhaseTemplate pt = null;
                        wt.lifecycle.State lcState = null;
                        for(int i = 0; i < states.size(); i++) {
                            pt = (PhaseTemplate)states.get(i);
                            lcState = pt.getPhaseState();
                            //Kogger.debug(lcState.toString());
                            if(lcState.toString().equals("APPROVING") || lcState.toString().equals("APPROVED")||
                                    lcState.toString().equals("REWORK")|| lcState.toString().equals("ERPSENDERROR")|| lcState.toString().equals("ATCOMPLATED")){

                            }else{
%>
                            <OPTION VALUE="<%=lcState.toString()%>"><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
<%              }
                        }
                } catch(Exception e) {
                    Kogger.error(e);
                }
%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL">FAB Name</td>
                    <td class="tdwhiteL">
                        <input name="pjtFabName" class="txt_field" style="width:85%"/>
                    </td>
                    <td class="tdblueL">PRODUCT</td>
                    <td class="tdwhiteL0">
                        <input name="pjtProduct" class="txt_field" style="width:85%"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00696") %><%--거래처--%></td>
                    <td class="tdwhiteL">
                        <input name="pjtCustomer" class="txt_field" style="width:85%"/>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02886") %><%--출하조건--%></td>
                    <td class="tdwhiteL0">
                        <input name="pjtConsignment" class="txt_field" style="width:85%"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
                    <td class="tdwhiteL">
                        <input name="planStartStartDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('planStartStartDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('planStartStartDate');"/>
                        &nbsp;~&nbsp;
                        <input name="planStartEndDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('planStartEndDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('planStartEndDate');"/>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
                    <td class="tdwhiteL0">
                        <input name="planEndStartDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('planEndStartDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('planEndStartDate');"/>
                        &nbsp;~&nbsp;
                        <input name="planEndEndDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('planEndEndDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('planEndEndDate');"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01966") %><%--수주일자--%></td>
                    <td class="tdwhiteL">
                        <input name="acceptanceStartDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('acceptanceStartDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('acceptanceStartDate');"/>
                        &nbsp;~&nbsp;
                        <input name="acceptanceEndDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('acceptanceEndDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('acceptanceEndDate');"/>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02884") %><%--출하일자--%></td>
                    <td class="tdwhiteL0">
                        <input name="deliveredStartDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('deliveredStartDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('deliveredStartDate');"/>
                        &nbsp;~&nbsp;
                        <input name="deliveredEndDate" class="txt_field" size="12" engnum="engnum" maxlength=15 readonly onclick="javascript:openCal('deliveredEndDate');"/>
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('deliveredEndDate');"/>
                    </td>
                </tr>
            </table>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
<!------------------------------ 본문 끝 //------------------------------>
        </td>
    </tr>
</table>
<!-- 본문외관테두리 끝 //-->

            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space2"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td width="1000" align="right" class="small"><%=messageService.getString("e3ps.message.ket_message", "00710") %><%--검색어는 대소문자를 구분합니다--%> </td>
                </tr>
              </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>

</form>

        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td><iframe src="" name="list" frameborder="0" width="100%" height="900" leftmargin="0" topmargin="0" scrolling="no"></iframe></td>
            </tr>
        </table>
</body>
</html>
