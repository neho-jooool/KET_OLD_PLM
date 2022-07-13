<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01408") %><%--문서관리 관리(생성)--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function createNode() {
    if (!check()) return;
    else {
        document.forms[0].action = '/plm/servlet/e3ps.doc.servlet.DocCodeTypeServlet';
        document.forms[0].submit();
    }
}

function check() {
    var doc = document.forms[0];
    if( isNullData(doc.oid.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01756") %><%--상위 노드를 선택해주세요--%>');
        return false;
    }
    if ( isNullData(doc.NAME.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02515") %><%--정보를 입력하십시오--%>');
        return false;
    }
    return true;
}

function selectFolder(target)
{
    var url = "/plm/jsp/groupware/folder/SelectFolder.jsp?folderpath=/Document&target="+target;
    openWindow(url, "SelectFolder", 300, 300);
}

//parent.tree.location.reload();
//-->
</script>
</head>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" onLoad="MM_preloadImages('/plm/portal/icon/bbs_admin1on.gif','/plm/portal/icon/bbs_admin2on.gif','/plm/portal/icon/bbs_admin3on.gif');">
<form name="create" method="post">
<input type="hidden" name="cmd" value="create">

<table width="95%" height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title3_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif>문서타입 관리 <b>[<%=messageService.getString("e3ps.message.ket_message", "01795") %><%--생성--%>]</b></td>
                    <td><img src=/plm/portal/images/title3_right.gif></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<br>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="95%">
    <tr>
        <td>
            <!--생성,삭제,수정 img-->
            <table border="0" cellpadding="0" cellspacing="0" width="189">
                <tr>
                    <td width="63"><p><a href="/plm/jsp/doc/doctype/ManageDocCreate.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','/plm/portal/icon/bbs2_admin1on.gif',1)"><img name="Image4" border="0" src="/plm/portal/icon/bbs2_admin1on.gif" width="63" height="26"></a></p></td>
                    <td width="63"><p><a href="/plm/jsp/doc/doctype/ManageDocDelete.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','/plm/portal/icon/bbs2_admin2on.gif',1)"><img name="Image5" border="0" src="/plm/portal/icon/bbs_admin2.gif" width="63" height="26"></a></p></td>
                    <td width="63"><p><a href="/plm/jsp/doc/doctype/ManageDocUpdate.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image6','','/plm/portal/icon/bbs2_admin3on.gif',1)"><img name="Image6" border="0" src="/plm/portal/icon/bbs_admin3.gif" width="61" height="26"></a></p></td>
                </tr>
            </table>
            <!--//-->
        </td>
    </tr>
    <tr>
        <td width="">
            <table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor=AABCC7 align=center>
                <tr height=23 bgcolor="#D9E2E7" align=center>
                    <td id=textblue colspan=2>
                        <input type="hidden" name="oid">
                        <table width=100% border="0" cellpadding="2" cellspacing="1">
                            <tr>
                                <td id=title>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00040", "<input type='text' name='parentname' id='i' onfocus='this.blur()' size='40'>&nbsp;") %><%--{0}의 하위에--%></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<input type=text size=40 name="NAME" id=i></td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02997") %><%--폴더--%></td>
                    <td width="80%" bgcolor="white" height="10">
                        &nbsp;<input type=text size=40 name="FOLDERName" id=i onfocus='blur()'>
                        &nbsp;<input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick="selectFolder('FOLDER')" id=innerbutton>
                        <input type='hidden' name='FOLDER'>
                    </td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<input type=text size=40 name="CODE" id=i></td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01922") %><%--소트넘버--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<input type=text size=40 name="SORT" value='0' id=i></td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<TEXTAREA NAME="DESCRIPTION" ROWS="3" COLS="60" id=input></TEXTAREA></td>
                </tr>
                <tr>
                    <td width="" bgcolor="#EEEEEE" height="40" colspan=2>
                        <p align="center"><input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "01795") %><%--생성--%>' onclick="createNode()"></p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
