<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.*,
                e3ps.common.web.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                java.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@include file="/jsp/common/context.jsp"%>
<%
    e3ps.groupware.company.PeopleData pdata = new e3ps.groupware.company.PeopleData();
    String oid = ParamUtil.getStrParameter(request.getParameter("oid"));          //ProjectOutput OID
    ProjectOutput output = (ProjectOutput)CommonUtil.getObject(oid);
    ProjectOutputData outputData = new ProjectOutputData(output);
%>
<html>
<head>
<script language="javascript">
<!--
//-->
</script>
<title><%=messageService.getString("e3ps.message.ket_message", "01717") %><%--산출물 LINK--%> - <%=pdata.name+" ["+pdata.departmentName+"]" %></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript>
<!--
function doSubmit(oid)
{
    if(validateCheck()) {
        var url = "/plm/servlet/e3ps/ManageProjectOutputServlet?cmd=createLink&oid="+oid
        //document.forms[0].action = '/plm/jsp/project/ProjectOutputDocRegistryLinkForm.jsp?cmd=update&oid='+oid;
        document.forms[0].action = url;
        document.forms[0].cmd = "createLink";
        document.forms[0].method = "post";
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

function validateCheck() {
    var docOidObj = document.getElementsByName("docOid");
    if( !docOidObj.length ) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00931") %><%--관련문서를 선택해 주십시요--%>");
        return false;
    }
    if( docOidObj.length > 1 ) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00048") %><%--1개 이상을 선택하셨습니다 \n꼭 1개에 관련문서만 선택해 주십시요--%>");
        return false;
    }
    return true;
}

function deleteRow(objectName, delOid)
{
    var obj = document.getElementsByName(objectName+"Oid");
    var tableObj = document.getElementById(objectName+"Table");

    if ( obj.length ) {
        for (var i=obj.length-1 ; i>=0 ; i-- )
        {
            if(obj[i] == null ) continue;
            if(obj[i].value == delOid)
            {
                tableObj.deleteRow(i);
                break;
            }
        }
    } else {
                tableObj.deleteRow(0);
    }
}

//관련 문서 검색
function addDocInfo(mpArr) {
    var checkExist = false;

    for(var i = 0; i < mpArr.length; i++) {
        var arr = mpArr[i];
        docoid = arr[0];            //문서 OID
        docnumber = arr[1];            //문서 번호
        docname = arr[2];            //문서 제목
        docversion = arr[3];            //문서 버젼

        checkExist = true;
        var oidExist = false;
        var obj = document.all.docOid;

        //데이터가 이미 추가 되었는지 확인
        if ( obj )
        {
            if ( obj.length )
            {
                for(j = 0; j <  obj.length; j++){
                    if( document.all.docOid[j].value == docoid){
                        oidExist = true;
                        break;
                    }
                }
            } else {
                if( document.all.docOid.value == docoid){
                    oidExist = true;
                }
            }
        }

        if(!oidExist){
            var objBody = document.getElementById("docTable");
            var objRow = objBody.insertRow();
            objRow.style.height="25px";

            var docobjCell = objRow.insertCell();
            docobjCell.className = "tdwhiteM";
            docobjCell.style.textAlign="center";
            docobjCell.innerHTML = "<input type='hidden' name='docOid' value='"+docoid+"'>" +
                                        "<img src=/plm/portal/icon/delete_x.gif border=0 title='delete' onClick=\"deleteRow('doc', '"+docoid+"')\" style='cursor:hand'>" ;

            var docobjCel2 = objRow.insertCell();
            docobjCel2.className = "tdwhiteM";
            docobjCel2.style.textAlign="left";
            var url = "COMMON_openPopup(1050,700).location.href='/plm/jsp/doc/docView.jsp?oid="+docoid+"'";
            docobjCel2.innerHTML = "&nbsp;&nbsp;&nbsp;<a href=javascript:// onclick=\""+url+"\">"+docnumber+"</a>";

            var docobjCel3 = objRow.insertCell();
            docobjCel3.className = "tdwhiteM";
            docobjCel3.innerHTML = docname;

            var docobjCel4 = objRow.insertCell();
            docobjCel4.className = "tdwhiteM";
            docobjCel4.innerHTML = docversion;

        }

    }

}
//-->
</SCRIPT>
</head>
<body leftmargin="0" topmargin="0">
<%@include file="/jsp/common/processing.html"%>
<form method="post">
<%
String cmd = request.getParameter("cmd");
if("update".equals(cmd))
{
    Kogger.debug(request.getParameter("docOid"));
    wt.doc.WTDocument doc = (wt.doc.WTDocument)CommonUtil.getObject(request.getParameter("docOid"));
    Kogger.debug("doc>>> "+CommonUtil.getOIDString(doc));
    if(doc != null)
    {
        //e3ps.doc.E3PSDocumentHelper.manager.createDocLink((e3ps.doc.E3PSDocument)doc, "", linkoid);
        //if("APPROVED".equals(doc.getLifeCycleState().toString()))
            //TaskHelper.manager.autoCompleteTask((e3ps.doc.E3PSDocument)doc);
    }
%>
    <SCRIPT LANGUAGE="JavaScript">
        opener.document.location.reload();
        self.close();
    </SCRIPT>
<%
    return;
}
%>
<!-- Hidden Value -->
<input type="hidden" name="oid" value="<%=oid%>"> <!-- ProjectOutput OID -->

<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01717") %><%--산출물 LINK--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button id=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%>' onclick="javascript:doSubmit('<%=oid%>')">
            &nbsp;<input type=button class="btnTras" id=button value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onclick="javascript:closeWindow()">
        </td>
    </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
    <tr bgcolor="#D8E0E7" align=center height=23>
        <td width="100%" bgcolor="#D8E0E7" colspan=5 id=title nowrap><%=messageService.getString("e3ps.message.ket_message", "01737") %><%--산출물 첨부--%></td>
    </tr>
    <TR align=left height=23>
        <TD bgcolor="#EBEFF3" width=20%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></TD>
        <TD bgcolor=white colspan=4>&nbsp;<%=outputData.locationStr%></TD>
    </TR>
    <TR align=left height=23>
        <TD bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01736") %><%--산출물 제목--%></TD>
        <TD bgcolor=white colspan=4>&nbsp;<%=outputData.name%></TD>
    </TR>
    <tr>
        <td bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00929") %><%--관련문서--%></td>
        <td colspan="4" bgcolor=white>
            <input name="Button25222222" type="button" class="s_font" value="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" onClick="COMMON_openPopup(850,750).location.href='/plm/jsp/common/loading.jsp?url=/plm/jsp/doc/docSearch_Multi.jsp&key=command&value=one&key=mode&value=one&key=invokeMethod&value=addDocInfo'">
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
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="tdblueM">&nbsp;</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                </tr>
                <tBody id="docTable">
                <tBody>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
