<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script type="text/javascript">
//도면 C/O Workspace
function callCOWorkspace( oid ) {
    var path = 'http://plm.ket.com/plm/';
    var uri = path+'servlet/WindchillAuthGW/wt.enterprise.URLProcessor/URLTemplateAction?u8&newpjl=true&ref=project&oid=OR:'+oid+'&action=WFDOWNLOAD&soln=pjl';
    AddToWorkSpaceWin = window.open(uri,'AddToWorkSpaceWin','width=1200,height=700, status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes , left=0, top=0');
    AddToWorkSpaceWin.focus();
}
</script>
</head>
<body onLoad="">
<table border='0' cellspacing='0' cellpadding='0'>
                      <tr>
                        <td><table border='0' cellspacing='0' cellpadding='0'>
                            <tr>
                              <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
                              <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:callCOWorkspace("wt.epm.EPMDocument:961391978");' class='btn_blue' onfocus='this.blur();'>C/O Workspace</a></td>
                              <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
                            </tr>
                          </table></td>
                      </tr>
                   </table>
</body>
</html>