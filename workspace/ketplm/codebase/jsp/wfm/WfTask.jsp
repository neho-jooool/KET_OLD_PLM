<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language='javascript'>
    function onLoad(){
        var url = "/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workflow/doPerformApprovalPopup.do&key=oid&value=<%=request.getParameter("oid")%>";
        document.location.href = url;
    }
</script>
</head>
<body onload="onLoad();"></body>
</html>