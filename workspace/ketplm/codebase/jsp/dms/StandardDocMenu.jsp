<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 5px;
    margin-right: 0px;
    margin-bottom: 0px;
}
-->
</style>

<script language='javascript'>
<!--

    //표준양식검색화면 이동
    function goSearch(){
        parent.main.document.location.href="/plm/jsp/dms/SearchStandardDoc.jsp?";
    }

    //표준양식등록화면 이동
    function goCreate(){
        parent.main.document.location.href="/plm/jsp/dms/CreateStandardDoc.jsp?";
    }

//-->
</script>

</head>
<body>
<tr>
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" height="100%" valign="top">
          <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal//images/icon_2.gif"
                  ><a href="javascript:goSearch();" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03009") %><%--표준양식 검색--%></a></td>
                </tr>
                <tr height="20">&nbsp;</tr>
              </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10">&nbsp;</td>
                    <td><img src="/plm/portal/images/icon_2.gif"
                    ><a href="javascript:goCreate();" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03012") %><%--표준양식 등록--%></a></td>
                  </tr>
                </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>
