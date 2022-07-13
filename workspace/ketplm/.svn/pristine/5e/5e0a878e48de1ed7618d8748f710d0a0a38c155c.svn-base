<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function selectFrame(str, oid) {
        var srcc;

        if (str == "list") {
            srcc = "/plm/ext/common/dashboard/viewMailReceiveDetailListForm.do?oid=";
        } else {
            srcc = "/plm/ext/common/dashboard/viewMailReceiveDetailListForm.do?oid="+oid;
        }
        
        document.all.iframe.src = srcc;
    }
</SCRIPT>
</head>
    <form name="drawingmanager">
        <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
             <tr >
                <td bgcolor=EDE9DD valign="top" height="100%" valign="top">
                    <table width=200 border="0" cellpadding="0" cellspacing="1">
	                    <tr>
	                       <td valign="top" height="5px" style="line-height:5px" align="left" bgcolor=white>&nbsp;</td>
	                    </tr>
	                    <tr>
	                        <td valign="top" height="25px" align="left" bgcolor=white>&nbsp;<input style="line-height:15px" type=button value='<%=messageService.getString("e3ps.message.ket_message", "01768")%><%--새로고침--%>' onclick='location.reload()' id='innerbutton'>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td height="50" bgcolor=white>&nbsp;&nbsp;<a style="font-size: small;" href="javascript:selectFrame('list','')" id="sub" onFocus="this.blur()">종합현황 메일 수신자</td>
	                    </tr>
                    </table>
                    <table width=200 border="0" cellpadding="0" cellspacing="1">
                        <c:forEach var="result" items="${resultList}" varStatus="status">
                            <tr>
                                <td height="25" bgcolor=white><img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0> <a href="javascript:selectFrame('one','${result.receiveOid}')" id="sub" onFocus="this.blur()">${result.targetPeopleName}</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td width="100%" height="100%" valign="top"><iframe frameborder=0 width="100%" height="100%" style="min-height:800px" src="/plm/ext/common/dashboard/viewMailReceiveDetailListForm.do?oid=" name="iframe" scrolling="no"></iframe></td>
            </tr>
        </table>
        <script src='/plm/portal/js/menu.js'></script>
        <input type="hidden" name="myname" value="종합현황 메일 수신자">
        <script>
            setPosition(null);
        </script>
    </form>