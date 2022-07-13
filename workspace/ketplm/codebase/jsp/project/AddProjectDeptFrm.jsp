<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String isModal = request.getParameter("isModal");
    String isOpen = request.getParameter("isOpen");
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");
    if ( isModal == null || isModal.length() == 0 )
        isModal = "false";
    if ( isOpen == null || isOpen.length() == 0 )
        isOpen = "false";
    if ( mode == null || mode.length() == 0 )
        mode = "s";
    if ( invokeMethod == null || invokeMethod.length() == 0 )
        invokeMethod = "";

    String param = "command=dept&mode=" + mode + "&invokeMethod=" + invokeMethod;
    String treeURL = "/plm/jsp/project/AddProjectOnlyDeptTree.jsp?" + param;
%>


<html>
<head>
<title>PLM Portal</title>
<base target="_self">

<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/top_include.jsp" %>

<script src="/plm/portal/js/checkbox2.js"></script>
<script src="/plm/portal/js/tableScriptChild.js"></script>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
</style>

<script type="text/javascript">
//<![CDATA[
    function seletDept(deptid) {
        var arr = new Array();
        var subArr = new Array();
        subArr[0] = deptid;//선택된 부서 OID
        arr[0] = subArr;

        <%
        if ( "true".equals(isModal) )  {
            if("true".equals(isOpen)) {
        %>
                var modalopener = window.dialogArguments;
                modalopener.<%=invokeMethod%>(arr);
        <%
            }
            else { %>
                //modal dialog
                selectModalDialog(arr);
        <%
            }
        }
        else {
            if ( invokeMethod.length() == 0 ) {
        %>
                //modal dialog
                selectModalDialog(arr);
        <%
            }
            else { %>
                //open window
                selectOpenWindow(arr);
        <%
            }
        }
        %>
    }

    function selectModalDialog(arrObj) {
        window.returnValue = arrObj;
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
        }
    <%
    }
    %>

    function addDept(selectDept){
        document.searchDept.deptList.value = selectDept;
    }

    function selectDeptList() {
        var arr = document.searchDept.deptList.value;
        <%
        if ( "true".equals(isModal) )   {
            if("true".equals(isOpen)) {
        %>
                var modalopener = window.dialogArguments;
                modalopener.<%=invokeMethod%>(arr);
        <%
            }
            else { %>
                //modal dialog
                selectModalDialog(arr);
        <%
            }
        }
        else {
            if ( invokeMethod.length() == 0 ) {
        %>
                //modal dialog
                selectModalDialog(arr);
        <%
            }
            else { %>
                //open window
                selectOpenWindow(arr);
        <%
            }
        }
        %>
    }
//]]>
</script>


</head>

<body topmargin="0" leftmargin="0">
<form name="searchDept" method="post">
<input type="hidden" name="deptList" value="">

<table style="width: 100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
        <table style="width: 100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <table style="width: 100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01544") %><%--부서 선택--%></td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                    <td style="width: 136px"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <table style="width: 100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <table>
                <tr>
                    <%
                    if ( mode.equals("m") ) {%>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:selectDeptList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%} %>
                    <td>&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <table style="width: 100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <table style="width: 100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">
                <div style="width:100%;height:370px;overflow-x:hidden;overflow-y:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px;">
                <iframe src="<%=treeURL%>" id="tree" name="tree" frameborder="0" style="width: 100%; height: 370px" leftmargin="0" topmargin="0" scrolling="auto"></iframe>
                </div>
            </td>
        </tr>
        </table>
    </td>
</tr>
</table>
</form>
</body>
</html>
