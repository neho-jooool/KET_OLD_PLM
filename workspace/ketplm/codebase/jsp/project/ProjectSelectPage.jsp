<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.fc.*,wt.clients.folder.FolderTaskLogic,wt.lifecycle.*,wt.part.*,wt.query.*"%>
<%@ page import="e3ps.common.code.*,
                 e3ps.common.jdf.config.*,
                 e3ps.common.util.WCUtil,e3ps.project.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
    String command = request.getParameter("command");
    if(command == null)
        command = "select";

    String fixedPjtType = request.getParameter("fixedPjtType");
    if( (fixedPjtType == null) || (fixedPjtType.trim().length() == 0) ) {
        fixedPjtType = "";
    }

    String isModal = request.getParameter("isModal");
    String isOpen = request.getParameter("isOpen");
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");
    if( (isModal == null) || (isModal.trim().length() == 0) ) {
        isModal = "false";
    }
    if( (isOpen == null) || (isOpen.trim().length() == 0) ) {
        isOpen = "false";
    }
    if( (mode == null) || (mode.trim().length() == 0) ) {
        mode = "s";
    }
    if( (invokeMethod == null) || (invokeMethod.trim().length() == 0) ) {
        invokeMethod = "";
    }

%>
<html>
<head>
<base target="_self">
<title>Project Search Page</title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<title>Untitled Document</title>
<style type="text/css">
<!--
.style1 {color: #FF0000}

#compareDiv
{
    border:0;
    padding:2px;
    margin:0;
    overflow-x:hidden;
    overflow-y:hidden;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}
-->
</style>
<script language="javascript">
function doSearch() {
    form = document.forms[0];

    form.pjtNo.value = (form.pjtNo.value).toUpperCase();
    form.method= 'post';
    form.target = 'list';
    form.action = "/plm/jsp/project/ProjectSelectList.jsp";
    form.submit();
}


function doSelect() {
    form = document.forms[0];
    if(list.document.forms[0] == null) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01361") %><%--먼저 프로젝트를 검색하시기 바랍니다--%>');
        return;
    }

    if(list.document.forms[0].oid == null) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>");
        return;
    }

    var arr = list.checkList();
    if(arr == null || arr.length == 0) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03111") %><%--프로젝트를 선택해 주십시오--%>');
        return;
    }

    <%  if("true".equals(isModal) || (invokeMethod.length() == 0)) { %>
            //modal dialog
            selectModalDialog(arr);
    <%  } else {  %>
            //open window
            selectOpenWindow(arr);
    <%  }  %>
}

function selectModalDialog(arrObj) {
    <%if("true".equals(isOpen) && (invokeMethod.length() > 0)){%>
        var opener = window.dialogArguments;
        opener.<%=invokeMethod%>(arrObj);
    <%}else{%>
        window.returnValue= arrObj;
        window.close();
    <%}%>
    //window.returnValue= arrObj;
    //window.close();
}

<%  if(invokeMethod.length() > 0) {  %>
function selectOpenWindow(arrObj) {
    //...이하 원하는 스크립트를 만들어서 작업...
    if(opener) {
        if(opener.<%=invokeMethod%>) {
            opener.<%=invokeMethod%>(arrObj);
        }
    } else if(parent.opener) {
        if(parent.opener.<%=invokeMethod%>) {
            parent.opener.<%=invokeMethod%>(arrObj);
        }
    }

    <%if(!"true".equals(isOpen)){%>
        thiscolse();
    <%}%>
}
<%  }  %>

function thiscolse(){
    self.close();
}

function onTextKeyUp(tobj) {
    if (window.event &&
            (tobj.name=="pjtNo" || tobj.name=="pjtName") ) {

        if (window.event.keyCode == 13) {
            doSearch();
        }
    }
}
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
    scrollbar-arrow-color:#607ddb;
}
-->
</style>
</head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<body>

<!-- title 시작 //-->
<form method=post>
<!-- hidden begin -->
<input type="hidden" name="command" value="<%=command%>">
<input type="hidden" name="invokeMethod" value="<%=invokeMethod%>">
<input type="hidden" name="mode" value="<%=mode%>">
<!-- hidden end -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">&nbsp;</td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a class='a52Btn' onclick="javascript:doSearch();">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a class='a62Btn' onclick="javascript:doSelect();">
                        <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a class='a62Btn' onclick="javascript:self.close();">
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
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <col width='15%'><col width='35%'><col width='15%'><col width='35%'>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL">
                        <input name="pjtNo" class="txt_field" style="width:50%;text-transform:'uppercase'" value="" onkeyup="javascript:onTextKeyUp(this);" />
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03098") %><%--프로젝트 종류--%></td>
                    <td class="tdwhiteL0">
                    <%  if(fixedPjtType.trim().length() == 0) {%>
                        <select name="pjtType" style="width:50%">
                            <option value="2"><%=messageService.getString("e3ps.message.ket_message", "01962") %><%--수주/견적--%></option>
                            <option value="1"><%=messageService.getString("e3ps.message.ket_message", "02083") %><%--양산개발--%></option>
                            <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01832") %><%--선행개발--%></option>
                        </select>
                    <%  }else{%>
                        <%if("0".equals(fixedPjtType)){%><%=messageService.getString("e3ps.message.ket_message", "01857") %><%--선행--%><%}else if("1".equals(fixedPjtType)){%><%=messageService.getString("e3ps.message.ket_message", "00582") %><%--개발--%><%}else if("2".equals(fixedPjtType)){%><%=messageService.getString("e3ps.message.ket_message", "01962") %><%--수주/견적--%><%}else{%>&nbsp;<%}%>
                        <input type='hidden' name='pjtType' value='<%=fixedPjtType%>'>
                    <%  } %>
                    </td>

                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                    <td class="tdwhiteL0" colspan="3">
                        <input name="pjtName" class="txt_field" style="width:100%" value="" onkeyup="javascript:onTextKeyUp(this);" />
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
                    <td class="tdwhiteL">
                        <select name="modelcode" style="width:50%">
                            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                Vector tMap = null;
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("MODELCODE", "parent");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                            %>
                                    <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
                            <%  } %>
                        </select>
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL0">
                        <select name="pjtState" style="width:50%">
                            <OPTION VALUE="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</OPTION>
                            <%  String cState = request.getParameter("pjtState");
                                if(cState == null)
                                    cState = "";

                                if(true) {//if(CommonUtil.isAdmin()) {
                                    try {
                                        LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_Project",WCUtil.getWTContainerRef());
                                        Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
                                        PhaseTemplate pt = null;
                                        wt.lifecycle.State lcState = null;
                                        for (int i = 0; i < states.size(); i++) {
                                            pt = (PhaseTemplate) states.get(i);
                                            lcState = pt.getPhaseState();
                            %>
                                            <OPTION VALUE="<%=lcState.toString()%>" <%if(cState.equals(lcState.toString())){%>selected<%}%>><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
                            <%
                                        }

                                    } catch(Exception e) {
                                	Kogger.error(e);
                                    }
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
                    <td class="tdwhiteL">
                        <select name="divisioncode" style="width:50%">
                            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("DIVISIONCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                            %>
                                    <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
                            <%  } %>
                        </select>
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01162") %><%--난이도--%>
</td>
                    <td class="tdwhiteL0">
                        <select name="levelcode" style="width:50%">
                            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("LEVELCODE", "parent");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                            %>
                                    <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
                            <%  } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%></td>
                    <td class="tdwhiteL">
                        <select name="productcode" style="width:50%">
                            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("PRODUCTCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                            %>
                                    <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
                            <%  } %>
                        </select>
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01474") %><%--발주처--%></td>
                    <td class="tdwhiteL0">
                        <select name="customercode" style="width:50%">
                            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("CUSTOMERCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                            %>
                                    <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
                            <%  } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%></td>
                    <td class="tdwhiteL">
                        <select name="devcompanycode" style="width:50%">
                            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("DEVCOMPANYCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                            %>
                                    <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
                            <%  } %>
                        </select>
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01788") %><%--생산조직--%></td>
                    <td class="tdwhiteL0">
                        <select name="makecompanycode" style="width:50%">
                            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("MAKECOMPANYCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                            %>
                                    <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
                            <%  } %>
                        </select>
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
                    <td class="space5"></td>
                </tr>
            </table>
            <div id='compareDiv' style="height:440;border-style:solid;border-color:#5F9EA0;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                        <iframe src="" id="list" name="list" frameborder="0"  width="100%" height="500" leftmargin="0" topmargin="0" scrolling="no"></iframe>
                        <!-- <iframe src="/plm/jsp/project/ProjectSelectList.jsp?command=<%=command%>&mode=<%=mode%>" id="list" name="list" frameborder="0"  width="100%" height="428" leftmargin="0" topmargin="0" scrolling="no"></iframe> -->
                    </td>
                </tr>
            </table>
            </div>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
        </td>
        <td class="boxRight"></td>
    </tr>
    </tr>
</table>
</form>
</body>
</html>
