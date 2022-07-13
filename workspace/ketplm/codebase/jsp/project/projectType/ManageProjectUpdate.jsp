<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.util.StringUtil,
                e3ps.groupware.company.Department,
                e3ps.doc.DocCodeType,
                e3ps.doc.DocCodeTypeDepartmentLink,
                e3ps.doc.FolderDocCodeTypeLink,
                e3ps.doc.DocCodeTypeParentChild,
                wt.folder.Folder,
                wt.query.QuerySpec,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
    String oid = request.getParameter("oid");
    DocCodeType docType = null;
    String tempName = null;
    String tempCode = null;
    String tempPath = null;
    String tempSort = null;
    String tempDesc = null;
    String tempFolder = "";
    boolean isModify = false;
    boolean isRoot = false;
    QueryResult qr = null;
    if(oid!=null)
    {
        docType = (DocCodeType)CommonUtil.getObject(oid);
        if(docType.getName().equals("ROOT")) isRoot = true;
        tempName = docType.getName();
        tempCode = docType.getCode();
        tempPath = docType.getPath();
        tempSort = "" + docType.getSort();
        tempDesc = docType.getDescription();

        qr = PersistenceHelper.manager.navigate(docType, "folder", FolderDocCodeTypeLink.class);
        while (qr.hasMoreElements())
        {
            Folder folder = (Folder)qr.nextElement();
            tempFolder = folder.getName();
        }

        qr = PersistenceHelper.manager.navigate(docType, "parent", DocCodeTypeParentChild.class);
        while (qr.hasMoreElements())
        {
            DocCodeType tempDct = (DocCodeType)qr.nextElement();
            if(tempDct.getName().equals("ROOT"))
                isModify = true;
        }
    }
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01409") %><%--문서관리 관리(수정)--%></title>
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

function updateData() {
    if (!check()) return;
    else {
        document.forms[0].action = '/plm/servlet/e3ps.doc.servlet.DocCodeTypeServlet';
        document.forms[0].submit();
    }
}

function check() {
    var doc = document.forms[0];
    if( isNullData(doc.oid.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01959") %><%--수정할 카테고리를 선택해주세요--%>');
        return false;
    } else {
        if( doc.oid.value == 'root' ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02838") %><%--최상의 카테고리는 수정할 수 없습니다--%>');
            return false;
        } else {
            return true;
        }
    }
}
function selectFolder(target)
{
    var url = "/plm/jsp/groupware/folder/SelectFolder.jsp?folderpath=/Document&target="+target;
    openWindow(url, "SelectFolder", 300, 300);
}

function deleteFolder()
{
    document.forms[0].FOLDERName.value = '';
    document.forms[0].FOLDER.value = '';
}
//parent.tree.location.reload();
//-->
</script>
</head>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" onLoad="MM_preloadImages('/plm/portal/icon/bbs_admin1on.gif','/plm/portal/icon/bbs_admin2on.gif','/plm/portal/icon/bbs_admin3on.gif');">
<form name="update" method="post">
<input type="hidden" name="cmd" value="modify">
<table width="95%" height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title3_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01435") %><%--문서타입 관리--%> <b>[<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>]</b></td>
                    <td><img src=/plm/portal/images/title3_right.gif></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<br>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="90%">
    <tr>
        <td width="">
            <!--생성,삭제,수정 img-->
            <table border="0" cellpadding="0" cellspacing="0" width="189">
                <tr>
                    <td width="63"><p><a href="/plm/jsp/doc/doctype/ManageDocCreate.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','/plm/portal/icon/bbs2_admin1on.gif',1)"><img name="Image4" border="0" src="/plm/portal/icon/bbs_admin1.gif" width="63" height="26"></a></p></td>
                    <td width="63"><p><a href="/plm/jsp/doc/doctype/ManageDocDelete.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','/plm/portal/icon/bbs2_admin2on.gif',1)"><img name="Image5" border="0" src="/plm/portal/icon/bbs_admin2.gif" width="63" height="26"></a></p></td>
                    <td width="63"><p><a href="/plm/jsp/doc/doctype/ManageDocUpdate.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image6','','/plm/portal/icon/bbs2_admin3on.gif',1)"><img name="Image6" border="0" src="/plm/portal/icon/bbs2_admin3on.gif" width="61" height="26"></a></p></td>
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
                        <table border="0" cellpadding="2" cellspacing="1">
                            <tr><td width=""><p align="center"><%=messageService.getString("e3ps.message.ket_message", "01822") %><%--선택한 카테고리(or 게시판)의 이름 또는설명을 수정합니다--%></p></td></tr>
                            <tr><td width=""><p align="center"><%=messageService.getString("e3ps.message.ket_message", "03368") %><%--입력 후 '수정'을 눌러주세요.--%></p></td></tr>
                        </table>
                    </td>
                </tr>
                <input type="hidden" name="oid" value="<%=oid%>">
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<input type=text size=40 name="NAME" id=i  value="<%=StringUtil.checkNull(tempName)%>" <%if(isRoot) out.print("onfocus='blur()'");%>></td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<input type=text size=40 name="CODE" id=i value="<%=StringUtil.checkNull(tempCode)%>" <%if(isRoot) out.print("onfocus='blur()'");%>></td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00795") %><%--경로--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<input type=text size=40 name="PATH" id=i value="<%=StringUtil.checkNull(tempPath)%>" onfocus='blur()'></td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02997") %><%--폴더--%></td>
                    <td width="80%" bgcolor="white" height="10">
                        &nbsp;<input type=text size=40 name="FOLDERName" id=i onfocus='blur()' value='<%=tempFolder%>'>
                        <%if(!isRoot) {%>
                            <input type='button' id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick="selectFolder('FOLDER')">
                            &nbsp;<input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>' onClick="deleteFolder()" id=innerbutton>
                        <%}%>
                        <input type='hidden' name='FOLDER'>
                    </td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01922") %><%--소트넘버--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<input type=text size=40 name="SORT" id=i value="<%=StringUtil.checkNull(tempSort)%>" <%if(isRoot) out.print("onfocus='blur()'");%>></td>
                </tr>
                <tr>
                    <td width="20%" bgcolor="EBEFF3" height="10">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td width="80%" bgcolor="white" height="10">&nbsp;<TEXTAREA NAME="DESCRIPTION" ROWS="3" COLS="60" <%if(isRoot) out.print("onfocus='blur()'");%> id=input><%=StringUtil.checkNull(tempDesc)%></TEXTAREA></td>
                </tr>
                <tr>
                    <td width="" bgcolor="#EEEEEE" height="40" align=center colspan=2>
                        <input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>' onclick="javascript:updateData()">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
