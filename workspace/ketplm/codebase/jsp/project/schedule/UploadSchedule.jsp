<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script type="text/javascript">
//<![CDATA[

    function save() {
        try
        {
            var objFrmUpload = document.frmUpload;
            var objFile = objFrmUpload.fileUpload;

            var strImageFilePath = objFile.value;
            strImageFilePath = strImageFilePath.toLowerCase();

            if ( 0 == strImageFilePath.length || "" == strImageFilePath ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02962") %><%--파일을 선택해 주십시오--%>');
                return;
            }

            if ( ( -1==strImageFilePath.indexOf(".mpp")) ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "03227") %><%--확장자가 mpp 인 파일을 선택해 주십시오--%>');
                return;
            }


            // 파일 확장자 추가검사 - Start
            var nFindIndex = -1;
            var nFilePathLength = strImageFilePath.length;
            if(-1 != strImageFilePath.indexOf(".mpp")) {
                nFindIndex = strImageFilePath.indexOf(".mpp");
                if ( nFilePathLength != (nFindIndex+4) ) {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "03227") %><%--확장자가 mpp 인 파일을 선택해 주십시오--%>');
                    return;
                }
            }
            // 파일 확장자 추가검사 - End

            if ( confirm ("<%=messageService.getString("e3ps.message.ket_message", "03277") %><%--mpp 파일을 업로드 하시겠습니까?\n업로드시 현재 일정이 삭제 후 업로드 된 데이터로 재 생성됩니다.--%>") ) {
                objFrmUpload.action = '/plm/jsp/project/schedule/UploadMppFile.jsp';
                objFrmUpload.target = "ifrmUpload";
                objFrmUpload.submit();
            }
        }
        catch(e) {
        }
    }

    function fnUploadResult(filePath) {
        if ( fnUploadResult != "" ) {
            selectModalDialog(filePath);
        }
    }

    function selectModalDialog(filePath) {
        window.returnValue= filePath;
        window.close();
    }
//]]>
</script>
</head>

<body>
<form name="frmUpload" method="post" action="" enctype="multipart/form-data" onsubmit="return false">

<table style="width: 560px;">
<tr>
    <td valign="top" style="padding: 0px 0px 0px 0px">
        <table style="width: 560px;">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01">Project Schedule Upload</td>
                    <td width="10"></td>
                </tr>
                </table>
            </td>
            <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        </table>
        <table style="width: 560px;">
        <tr>
            <td class="space10"></td>
        </tr>
        </table>
        <table style="width: 560px;">
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <table>
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                        <a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02958") %><%--파일 업로드--%></a>
                    </td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <table style="width: 560px;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <table style="width: 560px;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 560px;">
        <tr>
            <td class="tdblueL"   style="width: 120px;">MS Project File</td>
            <td class="tdwhiteL0" style="width: 440px;">
                <input type="file" id="fileUpload" name="fileUpload" style="width: 440px;">
            </td>
        </tr>
        </table>
        <table style="width: 560px;">
        <tr>
            <td class="tab_btm1"></td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td height="30" valign="bottom">
        <table style="width: 560px;">
        <tr>
            <td width="10">&nbsp;</td>
            <td height="30">
                <iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" style="width: 560px; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr>
</table>
</form>

<!-- Upload Form Action Area : Start -->
<iframe name="ifrmUpload" src="" style="display:none" width="0" height="0" frameborder="0" marginheight="0" marginwidth="0"></iframe>
<!-- Upload Form Action Area : End -->

</body>
</html>
