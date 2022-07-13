<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.project.beans.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<jsp:setProperty name="control" property="href" value="/plm/servlet/e3ps/SearchProjectTemplateServlet" />
<%
    String mode = ParamUtil.getStrParameter(request.getParameter("mode"),"s");
    boolean isMultiSelect = false;
    if ( mode.equalsIgnoreCase("m") ) isMultiSelect = true;
    String function = ParamUtil.getStrParameter(request.getParameter("function"),"thiscolse");
    control.setParam("mode="+mode+"&function="+function);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00517") %><%--Template 프로젝트 리스트--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
<!--
function selectAll() {
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
        for( var i = 0 ; i < len ; i++ ) {
            if ( document.selectProjectTemplateList.checkboxAll.checked == true ) document.selectProjectTemplateList.check[i].checked=true;
            else document.selectProjectTemplateList.check[i].checked=false;
        }
    } else if ( len == 1 ) {
        if ( document.selectProjectTemplateList.checkboxAll.checked == true ) document.selectProjectTemplateList.check.checked=true;
        else document.selectProjectTemplateList.check.checked=false;
    }
}

function clickThis(param) {
    if ( !param.checked ) return;

    var len = <%=control.getResult().size()%>;
    var checkStr = param.value;

    var objArr = document.forms[0];
    if (len > 1) {
        for ( var i = 0 ; i < objArr.length ; i++ ) {
            if ( objArr[i].type == "checkbox" ) {
                if ( checkStr != objArr[i].value ) {
                    objArr[i].checked = false;
                }
            }
        }
    }
}

function fcheck() {
    var count = 0;
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
        for( i=0;i<len ;i++) {
            if( document.selectProjectTemplateList.check[i].checked == true) {
                count++;
            }
        }
    } else if ( len == 1 ) {
        if( document.selectProjectTemplateList.check.checked == true) count++;
    }

    if(count==0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01813") %><%--선택된 항목이 없습니다--%>");
        return false;
    }
    return true;
}

function thiscolse(){
    self.close();
}

function addTempUseTemplateCreate() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=control.getResult().size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++) {
                if( document.selectProjectTemplateList.check[i].checked == true) {
                    paramStr = paramStr + document.selectProjectTemplateList.check[i].value + "&" + document.selectProjectTemplateList.check[i].tname + "&" +  document.selectProjectTemplateList.check[i].dur + "&" + document.selectProjectTemplateList.check[i].createdate + "&" + document.selectProjectTemplateList.check[i].modifydate + "?";
                }
            }
        } else if ( len == 1 ) {
            if( document.selectProjectTemplateList.check.checked == true) {
                paramStr = document.selectProjectTemplateList.check.value + "&" + document.selectProjectTemplateList.check.tname + "&" +  document.selectProjectTemplateList.check.dur + "&" + document.selectProjectTemplateList.check.createdate + "&" + document.selectProjectTemplateList.check.modifydate + "?";
            }
        }
        opener.addTemp(paramStr);
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addTempUseCreate() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=control.getResult().size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectTemplateList.check[i].checked == true) paramStr = paramStr + "tempid=" + document.selectProjectTemplateList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectTemplateList.check.checked == true) paramStr = "tempid=" + document.selectProjectTemplateList.check.value;
        }

        opener.document.projectCreate.method="post";
        opener.document.projectCreate.action = "/plm/jsp/project/ProjectCreateForm.jsp?"+paramStr;
        opener.document.projectCreate.submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}
-->
</script>
</head>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="selectProjectTemplateList">
<table width=95% height=40 align=center border=0 >
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "00508") %><%--Template 리스트--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button class="btnTras" id=button value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' onclick='JavaScript:<%=function%>()'>
            <input type=button class="btnTras" id=button value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onclick='JavaScript:thiscolse()'>
        </td>
    </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
    <tr  bgcolor="#D8E0E7" align=center height=20>
        <td width="5%" bgcolor="#D8E0E7" id=title><%if(isMultiSelect){%><input type="checkbox" name="checkboxAll" value="all"  onClick="javascript:selectAll();"><%}%>&nbsp;</td>
        <td width="55%" id=title>Template</td>
        <td width="10%" id=title><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
        <td width="15%" id=title><%=messageService.getString("e3ps.message.ket_message", "02859") %><%--최초등록일--%></td>
        <td width="15%" id=title><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
    </tr>
<%
    PagingQueryResult result = control.getResult();
    while ( result.hasMoreElements() ) {
        Object[] obj = (Object[])result.nextElement();
        TemplateProjectData data = new TemplateProjectData((TemplateProject)obj[0]);
%>
    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#f5f5f5'" onMouseout="this.style.backgroundColor='#ffffff'">
        <td align="center"><input type="checkbox" name="check" value="<%=data.tempProjectOID%>" tname='<%=data.tempName%>' createdate='<%=DateUtil.getDateString(data.tempCreateDate,"d")%>' modifydate='<%=DateUtil.getDateString(data.tempModifyDate,"d")%>' dur='<%=data.tempDuration%>' <%if(!isMultiSelect)out.print("onclick='javascript:clickThis(this)'");%>></td>
        <td align="left">&nbsp;<%=StringUtil.getLeft(data.tempName,30)%></td>
        <td align="center"><%=data.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
        <td align="center"><%=DateUtil.getDateString(data.tempCreateDate,"d")%></td>
        <td align="center"><%=DateUtil.getDateString(data.tempModifyDate,"d")%></td>
    </tr>
<%  }  %>
<%  if(control.getTotalCount() == 0) {  %>
    <tr bgcolor="#FFFFFF">
        <td colspan="5" height="25" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
    </tr>
<%  }  %>
</table>
<%@include file="/jsp/common/page_include.jsp" %>
</form>
</body>
</html>
