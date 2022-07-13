<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,
                  wt.fc.*,
                  wt.org.*,
                  wt.vc.*,
                  wt.part.*,
                  wt.vc.wip.*"%>
<%@page import="e3ps.groupware.company.*,
                e3ps.common.util.*,
                e3ps.common.content.*,
                e3ps.common.jdf.log.Logger,
                e3ps.auth.beans.E3PSAuthHelper,
                e3ps.project.*,
                e3ps.project.beans.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<html>
<%

    String oid = request.getParameter("oid"); // 기존 OID
    String newOid = "";
    String initType = request.getParameter("initType");
    if(initType == null){
        initType = "produceproject";
    }

    if(oid == null && oid.length() == 0 )
            oid = "";




    String command = "";
    FormUploader uploader = FormUploader.newFormUploader(request);
    Hashtable param = uploader.getFormParameters();


    command = (String)param.get("command");

    param.put("projectNo", param.get("tempProjectNo"));
    param.put("projectName", param.get("projectName"));
    param.put("projectType", param.get("projectType"));
    param.put("projectAcceptanceDate", param.get("projectAcceptanceDate"));
    param.put("projectDeliveredDate", param.get("projectDeliveredDate"));

    param.put("projectFabName", param.get("projectFabName"));
    param.put("projectProduct", param.get("projectProduct"));
    param.put("projectConsignment", param.get("projectConsignment"));


    if(command == null)
        command = "";
    E3PSProject jelOid = null;
    E3PSProject newJel = null;
    if("saveas".equals(command)) {
        try{
        if(!oid.equals(""))
            jelOid   = (E3PSProject)CommonUtil.getObject(oid);

        newJel =  (E3PSProject)ProjectSaveAs.saveAs(param,jelOid);
        newOid = CommonUtil.getOIDString(newJel);
        }catch(Exception e){Kogger.error(e);}

        if(newJel == null){
    %>
            <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "02462") %><%--저장을 실패했습니다\n 다시 시도하시기 바랍니다--%>');
            opener.parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=jelOid%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=jelOid%>');
            window.close();
            </script>

    <%   }else{
    %>
        <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "03106") %><%--프로젝트가 복사 되었습니다 새 프로젝트로 이동 합니다--%>');
            opener.parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=newOid%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=newOid%>');
            window.close();
        </script>

<%    }
    }

%>

<script language="JavaScript">

    function erpProjectNoSearch() {
        //if(checkField(document.forms[0].projectNo, "프로젝트 NO")) {
            //return;
        //}

        var url = "/plm/jsp/project/ProjectERPSelectPage.jsp?inputPN="+document.forms[0].projectNo.value +"&initType=<%=initType%>";
        attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=900px; dialogHeight:700px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        setProjectInfo(attache);
    }

    function setProjectInfo(infoArr) {

        var projectNo = (infoArr[0][1]).replace("\\", "\"");
        var projectName = (infoArr[0][2]).replace("\\", "\"");
        var projectType = (infoArr[0][3]).replace("\\", "\"");
        var projectAcceptanceDate = (infoArr[0][4]).replace("\\", "\"");
        var projectDeliveredDate = (infoArr[0][5]).replace("\\", "\"");
        var projectFabName = (infoArr[0][6]).replace("\\", "\"");
        var projectProduct = (infoArr[0][7]).replace("\\", "\"");
        var projectCustomer = (infoArr[0][8]).replace("\\", "\"");
        var projectConsignment = (infoArr[0][9]).replace("\\", "\"");
        var projectSite = (infoArr[0][10]).replace("\\", "\"");
        var projectSiteOid = (infoArr[0][11]).replace("\\", "\"");
        var form = document.forms[0];

        var projectDeliveredDateValue = ' ';
        var projectAcceptanceDateValue = ' ';

        if(projectDeliveredDate.length != 0){
            projectDeliveredDateValue = projectDeliveredDate.substring(0,4)+ "/" + projectDeliveredDate.substring(4,6) + "/" + projectDeliveredDate.substring(6,8);
        }

        if(projectAcceptanceDate.length != 0){
            projectAcceptanceDateValue = projectAcceptanceDate.substring(0,4)+ "/" + projectAcceptanceDate.substring(4,6) + "/" + projectAcceptanceDate.substring(6,8);
        }

        if(projectName.length == 0) {
            projectName = ' ';
        }

        if(projectProduct.length == 0) {
            projectProduct = ' ';
        }

        if(projectCustomer.length == 0) {
            projectCustomer = ' ';
        }

        if(projectDeliveredDate.length == 0) {
            projectDeliveredDate = ' ';
        }

        if(projectAcceptanceDate.length == 0) {
            projectAcceptanceDate = ' ';
        }

        if(projectType == 'AP' || projectType == 'BJ' || projectType == 'CD'  ){
            projectType = '고객제공';
            form.projectType.value = '1';
        }
        else if(projectType =='CZ'){
            projectType = '연구개발';
            form.projectType.value = '2';
        }else {
            projectType = '고객제공';
            form.projectType.value = '1';
        }

        if(projectFabName.length == 0 ) {
            projectFabName = ' ';
        }

        if(projectConsignment.length == 0 ){
            projectConsignment = ' ';
        }
        if(projectSite.length == 0 ){
            projectSite = ' ';
        }
        if(projectSiteOid.length == 0 ){
            projectSiteOid = ' ';
        }



        form.tempProjectNo.value = projectNo;
        form.projectName.value = projectName;
        form.projectProduct.value = projectProduct;
        form.projectCustomer.value = projectCustomer;
        form.projectDeliveredDate.value = projectDeliveredDate;
        form.projectAcceptanceDate.value = projectAcceptanceDate;
        form.projectNo.value = projectNo;
        form.projectConsignment.value = projectConsignment;
        form.projectFabName.value = projectFabName;
        form.projectSite.value = projectSite;
        form.projectSiteOid.value = projectSiteOid;



        document.getElementById("projectProductTD").innerText = projectProduct;
        document.getElementById("projectNameTD").innerText = projectName;
        document.getElementById("projectCustomerTD").innerText = projectCustomer;
        document.getElementById("projectFabNameTD").innerText = projectFabName;
        document.getElementById("projectDeliveredDateTD").innerText = projectDeliveredDateValue;
        document.getElementById("projectAcceptanceDateTD").innerText = projectAcceptanceDateValue;
        document.getElementById("projectTypeTD").innerText = projectType;
        document.getElementById("projectConsignmentTD").innerText = projectConsignment;
        document.getElementById("projectSiteTD").innerText = projectSite;

    }




    function saveAs(obj){

        form = document.forms[0];
        if(form.projectNo.value == ""){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03112") %><%--프로젝트를 입력해 주십시오--%>');
            return;
        }
        if(form.tempProjectNo.value == ""){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03112") %><%--프로젝트를 입력해 주십시오--%>');
            return;
        }

        form.command.value='saveas';
        disabledAllBtn();
        showProcessing();
        form.submit();

    }

    function onKeyPress() {
    if (window.event) {
        if (window.event.keyCode == 13) erpProjectNoSearch();
    } else return;
    }

    document.onkeypress = onKeyPress;

</script>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>

<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="tempProjectNo" value="">
<input type="hidden" name="projectProduct" value="">
<input type="hidden" name="projectName" value="">
<input type="hidden" name="projectCustomer" value="">
<input type="hidden" name="projectDeliveredDate" value="">
<input type="hidden" name="projectAcceptanceDate" value="">
<input type="hidden" name="projectState" value="">
<input type="hidden" name="lifecycle" value="Default">
<input type="hidden" name="projectType" value="">
<input type="hidden" name="projectFabName" value="">
<input type="hidden" name="projectConsignment" value="">
<input type="hidden" name="projectSite" value="">
<input type="hidden" name="projectSiteOid" value="">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00457") %><%--Save As 저장--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00457") %><%--Save As 저장--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:saveAs(this);">
                        <img src="/plm/portal/images/img_default/button/board_btn_saveas.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00458") %><%--SAVE AS등록--%>" width="76" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"> </td>
                </tr>
            </table>
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
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="35%"><COL width="15%"><COL width="35%">
                <!-- ERP Interface 항목 들 -->
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%><span class="style1"> *</span></td>
                    <td class="tdwhiteL">
                        <input name="projectNo" class="txt_field" value="" style="width:65%"/>&nbsp;&nbsp;
                        <a href="javascript:erpProjectNoSearch();">
                        <img src="/plm/portal/images/img_default/button/board_btn_erpSearch.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00222") %><%--ERP 조회--%>" width="76" height="20" border="0">
                        </a>
                    </td>
                    <td class="tdblueL">PRODUCT</td>
                    <td class="tdwhiteL0" id='projectProductTD'>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03098") %><%--프로젝트 종류--%></td>
                    <td class="tdwhiteL" id='projectTypeTD'>
                        &nbsp;
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                    <td class="tdwhiteL0" id='projectNameTD'>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00696") %><%--거래처--%></td>
                    <td class="tdwhiteL" id='projectCustomerTD'>
                        &nbsp;
                    </td>
                    <td class="tdblueL">FAB Name</td>
                    <td class="tdwhiteL0" id='projectFabNameTD'>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <!-- PLM 항목 들 -->
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01966") %><%--수주일자--%></td>
                    <td class="tdwhiteL" id='projectAcceptanceDateTD'>
                        &nbsp;
                    </td>
                    <!-- //PLM 항목 들 -->
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02884") %><%--출하일자--%></td>
                    <td class="tdwhiteL0" id='projectDeliveredDateTD'>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02886") %><%--출하조건--%></td>
                    <td class="tdwhiteL" id='projectConsignmentTD'>
                        &nbsp;
                    </td>
                    <td class="tdblueL">SITE</td>
                    <td class="tdwhiteL0" id='projectSiteTD'>
                        &nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
