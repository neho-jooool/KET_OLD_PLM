<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,wt.query.*"%>
<%@page import="e3ps.common.code.*,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                wt.fc.PagingQueryResult"%>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>
<%
    e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigEx.getInstance("message");

    String codetype = request.getParameter("codetype");
    String numberCode = request.getParameter("numberCode");

    String command = request.getParameter("command");
    String mode =  request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");


    if(codetype==null)
        codetype = "";

    if(numberCode==null)
        numberCode = "";


    if(command==null)
        command = "";

    if(mode==null)
        mode = "";

    if(invokeMethod==null)
        invokeMethod = "";


    int psize = 15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;

    String sessionidstring = request.getParameter("sessionid");
    if(sessionidstring==null)sessionidstring = "0";
    long sessionid = Long.parseLong(sessionidstring);
    String pagestring = request.getParameter("tpage");
    if(pagestring!=null && pagestring.length()>0)cpage = Integer.parseInt(pagestring);

    PagingQueryResult result = null;

    if(sessionid <= 0){
        HashMap map = new HashMap();
        map.put("type", codetype);
        QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);

        result = PagingSessionHelper.openPagingSession(0, psize, qs);
    }
    else {
        result = PagingSessionHelper.fetchPagingSession((cpage-1) * psize, psize, sessionid);
    }


    if(result!=null){
        total = result.getTotalSize();
        sessionid = result.getSessionId();
    }
%>
<html>
<head>
<base target="_self">
<title><%=messageService.getString("e3ps.message.ket_message", "01135") %><%--기준정보 목록--%></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
.a52Btn {margin:0;padding:0;border:0;width:52;text-decoration: none;}
.a62Btn {margin:0;padding:0;border:0;width:62;text-decoration: none;}
-->
</style>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script language="javascript">
function gotoPage(p){
    //document.forms[0].command.value='search';
    document.forms[0].sessionid.value='<%=sessionid%>';
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
}

function pageClose() {
    if(opener || parent.opener) {
        parent.pageClose();
    }
    else {
        window.close();
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
    margin-right:15px;
}
-->
</style>
</head>
<body>

<form>
<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>
<input type='hidden' name='command' value='<%=command%>'>
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<input type='hidden' name='mode' value="<%=mode%>">
<input type='hidden' name='invokeMethod' value="<%=invokeMethod%>">

<!-- 본문외관테두리 시작 //-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=NumberCodeType.toNumberCodeType(codetype).getDisplay(messageService.getLocale())%></td>
                <td align="right" style="padding:8px 0px 0px 0px"></td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td align="right">
                        <a class="a62Btn" href="#" onclick="javascript:addSelectCode();">
                        <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a class="a62Btn" href="#" onclick="javascript:pageClose();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="1" cellspacing="1" width="100%" bgcolor="#AABDC6" align="center">
                <TR bgcolor="#D8E0E7" align=center height=23>
                <%if("select".equals(command) || CommonUtil.isAdmin()) {%>
                    <TD width="1%" bgcolor="#D8E0E7" id=title>
                        <%if("multi".equals(mode)){%><input name="chkAll" type="checkbox" class="Checkbox" onClick="javascript:checkAll();"><%}else{%>&nbsp;<%}%>
                    </td>
                <%}%>
                    <TD width=20% id=title>이름</TD>
                    <TD width=10% id=title><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></TD>
                    <TD width=25% id=title><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></TD>
                </TR>
            <%
                if(result == null || result.size() == 0) {
            %>
                <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                    <TD align=center colspan='4'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></TD>
                </TR>
            <%
                }
                else {
                    NumberCode nCode = null;
                    String nCodeOid = "";
                    String checked = "";
                    Object obj[] = null;
                    while(result.hasMoreElements()) {
                        obj = (Object[])result.nextElement();
                        nCode = (NumberCode)obj[0];
                        nCodeOid = nCode.getPersistInfo().getObjectIdentifier().getStringValue();
                        checked = "";
                        if(numberCode.equals(nCode.getCode())) {
                            checked = "checked";
                        }
            %>
                    <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                        <%if("select".equals(command) || CommonUtil.isAdmin()) {%>
                        <td>
                            <input type="checkbox" value="<%=nCodeOid%>" name="oid" codename='<%=nCode.getName()%>' codecode='<%=nCode.getCode()%>' codedesc='<%=StringUtil.checkNull(nCode.getDescription())%>'  <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%> <%=checked%>>
                        </td>
                        <%}%>
                        <TD title="<%=nCode.getName()%>">
                            <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                <nobr>&nbsp;<%=nCode.getName()%></nobr>
                            </div>
                        </TD>
                        <TD align=center><%=nCode.getCode()%></TD>
                        <TD>&nbsp;<%=StringUtil.checkNull(nCode.getDescription())%></TD>
                    </TR>
            <%
                    } // end of while(pr.hasMoreElements())
                }
            %>
            </TABLE>
            <%
                int ksize = total/psize;
                int x = total%psize;
                if(x>0) ksize++;
                int temp = cpage/pageCount;
                if( (cpage%pageCount)>0)temp++;
                int start = (temp-1)*pageCount+1;
                int end = start + pageCount-1;
                if(end> ksize){
                    end = ksize;
                }
            %>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
                <tr>
                  <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
                  <td>
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="40" align="center"><%if(start>1){%><a href="#" onclick="JavaScript:gotoPage(1)" class="small">처음</a><%}%></td>
                                <td width="1" bgcolor="#dddddd"></td>
                                <%if(start>1){%>
                                <td width="60" class="quick" align="center"><a href="#" onclick="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a></td>
                                <td width="1" bgcolor="#dddddd"></td>
                                <%}
                                for(int i=start; i<= end; i++){
                                %>
                                <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
                                    <%if(i==cpage){%><b><%=i%><%}else{%><a href="#" onclick="JavaScript:gotoPage(<%=i%>)"><%=i%></a><%}%></td>
                                <%}
                                if(end < ksize){
                                %>
                                <td width="1" bgcolor="#dddddd"></td>
                                <td width="60" align="center"><a href="#" onclick="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "01186") %><%--다음--%></a></td>
                                <%}%>
                                <td width="1" bgcolor="#dddddd"></td>
                                <td width="45"align="center"><%if(end<ksize){%><a href="#" onclick="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a><%}%></td>
                            </tr>
                        </table>
                  </td>
                    <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
<!--
function addSelectCode() {
    if(!checkedCheck()) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "02912") %><%--코드를 선택하십시오--%>");
        return;
    }


    form = document.forms[0];

    var arr = new Array();
    var subArr = new Array();
    var idx = 0;
    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                subArr = new Array();
                subArr[0] = form.oid[i].value;
                subArr[1] = form.oid[i].codecode;
                subArr[2] = form.oid[i].codename;
                subArr[3] = form.oid[i].codedesc;

                arr[idx++] = subArr;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            subArr = new Array();
            subArr[0] = form.oid.value;
            subArr[1] = form.oid.codecode;
            subArr[2] = form.oid.codename;
            subArr[3] = form.oid.codedesc;

            arr[idx++] = subArr;
        }
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

    if(opener || parent) {
        parent.pageClose();
    }
    else {
        window.close();
    }
}

<%  }  %>

function checkAll() {
    form = document.forms[0];
    if(form.oid == null) {
        return;
    }

    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            form.oid[i].checked = form.chkAll.checked;
        }
    }
    else {
        form.oid.checked = form.chkAll.checked;
    }
}

function checkedCheck() {
    form = document.forms[0];
    if(form.oid == null) {
        return false;
    }

    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                return true;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            return true;
        }
    }

    return false;

}

function selectedCode() {
    var arr = new Array();

    form = document.forms[0];
    if(form.oid == null) {
        return arr;
    }

    var idx = 0;
    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                arr[idx++] = form.oid[i].value;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            arr[idx++] = form.oid.value;
        }
    }

    return arr;
}
//-->
</script>
