<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,
                  e3ps.common.jdf.config.*,
                  e3ps.project.beans.*,
                  e3ps.project.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String inputPN = request.getParameter("inputPN");
    String initType = request.getParameter("initType");

    if(inputPN == null){
        inputPN = "";
    }

    if(initType == null){
        initType = "";
    }

    String invokeMethod = request.getParameter("invokeMethod");
    if(invokeMethod == null)
        invokeMethod = "";
%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<html>
<head>
<base target="_self">
<TITLE><%=messageService.getString("e3ps.message.ket_message", "00223") %><%--ERP 프로젝트 목록--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
<!--
function isCheckedCheckBox() {
    form = document.forms[0];

    if(form.oid == null) {
        return false;
    }

    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                //if( (form.oid[i].value.length != 10) || (form.oid[i].value.substring(0,1) != "J") || (form.oid[i].value.substring(0,1)!="R") ){
                    //alert("프로젝트 번호는 10자리만 선택 가능하며  첫 문자의 시작은 'J','R'로    시작되는 프로젝트만 선택 가능 합니다.")
                    //return true;
                //}
                return true;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            //if( (form.oid.value.length != 10) || (form.oid.value.substring(0,1)!="J") || (form.oid.value.substring(0,1)!="R") ){
                //alert("프로젝트 번호는 10자리만 선택 가능하면 첫 문자 시작은 'J','R'로   시작되는 프로젝트만 선택 가능 합니다.")
                //return true;
            //}
            return true;
        }
    }
    return false;
}

function checkList() {
    form = document.forms[0];

    var arr = new Array();
    var subarr = new Array();//0:oid
    if(!isCheckedCheckBox()) {
        return arr;
    }

    len = form.oid.length;

    var idx = 0;

    if(len) {
        for(var i = 0; i < len; i++) {
            if(form.oid[i].checked == true) {
                subarr = new Array();
                subarr[0] = (form.oid[i].value).replace("\\", "\"");          //oid
                subarr[1] = (form.oid[i].tempProjectNo).replace("\\", "\"");      //ProjectNo
                subarr[2] = (form.oid[i].projectName).replace("\\", "\"");        //projectName
                subarr[3] = (form.oid[i].projectType).replace("\\", "\"");        //projectType
                subarr[4] = (form.oid[i].projectAcceptanceDate).replace("\\", "\"");  //projectAcceptanceDate
                subarr[5] = (form.oid[i].projectDeliveredDate).replace("\\", "\"");    //projectDeliveredDate
                subarr[6] = (form.oid[i].projectFabName).replace("\\", "\"");      //projectFabName
                subarr[7] = (form.oid[i].projectProduct).replace("\\", "\"");      //projectProduct
                subarr[8] = (form.oid[i].projectCustomer).replace("\\", "\"");      //projectCustomer
                subarr[9] = (form.oid[i].projectConsignment).replace("\\", "\"");    //projectConsignment
                subarr[10] = (form.oid[i].projectSite).replace("\\", "\"");        //projectSite
                subarr[11] = (form.oid[i].projectSiteOid).replace("\\", "\"");      //projectSiteOid
                arr[idx++] = subarr;
            }
        }
    }else {
        if(form.oid.checked == true) {
                subarr = new Array();
                subarr[0] = (form.oid.value).replace("\\", "\"");            //oid
                subarr[1] = (form.oid.tempProjectNo).replace("\\", "\"");        //ProjectNo
                subarr[2] = (form.oid.projectName).replace("\\", "\"");          //projectName
                subarr[3] = (form.oid.projectType).replace("\\", "\"");          //projectType
                subarr[4] = (form.oid.projectAcceptanceDate).replace("\\", "\"");    //projectAcceptanceDate
                subarr[5] = (form.oid.projectDeliveredDate).replace("\\", "\"");    //projectDeliveredDate
                subarr[6] = (form.oid.projectFabName).replace("\\", "\"");        //projectFabName
                subarr[7] = (form.oid.projectProduct).replace("\\", "\"");        //projectProduct
                subarr[8] = (form.oid.projectCustomer).replace("\\", "\"");        //projectCustomer
                subarr[9] = (form.oid.projectConsignment).replace("\\", "\"");      //projectConsignment
                subarr[10] = (form.oid.projectSite).replace("\\", "\"");        //projectSite
                subarr[11] = (form.oid.projectSiteOid).replace("\\", "\"");        //projectSiteOid
                arr[idx++] = subarr;
        }
    }
    return arr;
}

function onSelect() {
    form = document.forms[0];

    if(document.forms[0].oid == null) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>");
        return;
    }

    var arr = checkList();
    if(arr.length == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01812") %><%--선택된 프로젝트가 없습니다--%>");
        return;
    }

<%  if(invokeMethod.length() == 0) {  %>
    //modal dialog
    selectModalDialog(arr);
<%  } else {  %>
    //open window
    selectOpenWindow(arr);
<%  }  %>

}

function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
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
    self.close();
}

<%  }  %>

function reload(){
    document.forms[0].
    document.forms[0].submit();
}

//-->
</script>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
    margin-right:10px;

    overflow-x:auto;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}
-->
</style>
</head>
<form>
<input type="hidden" name=initType value=<%=initType%>>
    <table border="0" cellpadding="0" cellspacing="0" class="popBox" width="100%">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                  <tr>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00223") %><%--ERP 프로젝트 목록--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00223") %><%--ERP 프로젝트 목록--%> </td>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
                  </tr>
                </table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <a href="javascript:reload();">
                            <img src="/plm/portal/images/img_default/button/board_btn_ref.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01768") %><%--새로고침--%>" width="76" height="20" border="0">
                            </a>
                            &nbsp;&nbsp;
                            <a href="javascript:onSelect();">
                            <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
                            </a>
                            &nbsp;&nbsp;
                            <a href="javascript:self.close();">
                            <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                            </a>
                        </td>
                    </tr>
                </table>
                <div style="height:600px;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px;">
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                    </table>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="tab_btm1"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tdblueM" rowspan=2>&nbsp;</td>
                            <td class="tdblueM">PROJECT_NO</td>
                            <td class="tdblueM">PROJECT_NAME</td>
                            <td class="tdblueM">PROJECT_TYPE</td>
                            <td class="tdblueM">ACCEPTANCEDATE</td>
                            <td class="tdblueM">DELIVEREDDATE</td>
                            <td class="tdblueM">PROJECT_FABNAME</td>
                        </tr>
                        <tr>
                            <td class="tdblueM">PRODUCT</td>
                            <td class="tdblueM">CUSTOMER</td>
                            <td class="tdblueM">CONSIGNMENT_TYPE</td>
                            <td class="tdblueM">SITE_TYPE</td>
                            <td class="tdblueM">IS_CREATED</td>
                            <td class="tdblueM">IS_MODIFIED</td>
                        </tr>
<%
    ArrayList array = ProjectERPConMgr.getInstance().getERPProjectList(inputPN,initType);

    if(array == null || array.size() == 0) {
%>
                        <tr>
                            <td class='tdwhiteM' align='center' colspan='7'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                        </tr>
<%
    }
    else {
        String pjtArr[] = null;
        boolean isSite = false;
        for(int i = 0; i < array.size(); i++) {
            pjtArr = (String[])array.get(i);


            String type = messageService.getString("e3ps.message.ket_message", "02919")/*타입이 없습니다*/;
            String typeCode = "";

            HashMap prodMap = new HashMap();
            String pjtTypeProduceArr[] = ConfigImpl.getInstance().getArray("project.type.produceproject");
            for(int j = 0; j < pjtTypeProduceArr.length; j++) {
                prodMap.put(pjtTypeProduceArr[j], pjtTypeProduceArr[j]);
            }

            HashMap devMap = new HashMap();
            String pjtTypeDevArr[] = ConfigImpl.getInstance().getArray("project.type.devproject");
            for(int j = 0; j < pjtTypeDevArr.length; j++) {
                devMap.put(pjtTypeDevArr[j], pjtTypeDevArr[j]);
            }

            if(prodMap.get(pjtArr[2].toString()) != null){
                type = messageService.getString("e3ps.message.ket_message", "00832")/*고객공급*/;
                typeCode = pjtArr[2];
            }else if(devMap.get(pjtArr[2].toString()) != null){
                type = messageService.getString("e3ps.message.ket_message", "02128")/*연구개발*/;
                typeCode = pjtArr[2];
            }else {
                typeCode ="Return";
            }
            String siteOid = "";
            String siteName = "";
            String siteValue = "";
            if(!pjtArr[9].trim().equals("")){

                //SITE 코드는 10자리에서 7자리로 변경.
                NumberCode nc =(NumberCode)NumberCodeHelper.manager.getNumberCode("SITE", pjtArr[9].trim());
                //NumberCode nc =(NumberCode)NumberCodeHelper.manager.getNumberCode("SITE", "000"+pjtArr[9].trim());
                if(nc != null){
                    siteOid = CommonUtil.getOIDString(nc);
                    siteValue = nc.getName();
                    siteName = nc.getName();
                    isSite = true;

                }else{
                    siteName = messageService.getString("e3ps.message.ket_message", "00466")/*Site 코드 값이 없습니다*/ + "("+pjtArr[9]+")";
                }

            }
%>
                        <tr>
                            <!-- CheckBOX -->
                            <td class="tdwhiteM" rowspan=2>
                                <input name="oid" type="checkbox" class="Checkbox"  value="<%=pjtArr[0]%>"
                                                                            tempProjectNo="<%=pjtArr[0]%>"
                                                                            projectName="<%=pjtArr[1]%>"
                                                                            projectType="<%=typeCode%>"
                                                                            projectAcceptanceDate="<%=pjtArr[3].trim()%>"
                                                                            projectDeliveredDate="<%=pjtArr[4].trim()%>"
                                                                            projectFabName="<%=pjtArr[5]%>"
                                                                            projectProduct="<%=pjtArr[6]%>"
                                                                            projectCustomer="<%=pjtArr[7]%>"
                                                                            projectConsignment="<%=pjtArr[8]%>"
                                                                            projectSite="<%=siteValue%>"
                                                                            projectSiteOid="<%=siteOid%>"
                                                                            onclick="oneClick(this);">
                            </td>
                            <!-- PROJECT_NO -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[0]%></td>
                            <!-- PROJECT_NAME -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[1]%></td>
                            <!-- PROJECT_NAM -->
                            <td class="tdwhiteM">&nbsp;<%=type%></td>
                            <!-- PROJECT_SPEC -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[3]%></td>
                            <!-- PROJECT_UNIT -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[4]%></td>
                            <!-- PROJECT_DELIVEREDDATE -->
                            <td class="tdwhiteM0">&nbsp;<%=pjtArr[5]%></td>
                        </tr>
                        <tr>
                            <!-- PM_QTY -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[6]%></td>
                            <!-- PRODUCT -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[7]%></td>
                            <!-- CUSTOMER -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[8]%></td>
                            <!-- CUSTOMER_NAME -->
                            <td class="tdwhiteM">&nbsp;<%=siteName%></td>
                            <!-- PROJECT_NO_MIN -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[10]%></td>
                            <!-- PROJECT_GUBUN -->
                            <td class="tdwhiteM0">&nbsp;<%=pjtArr[11]%></td>
                        </tr>
<%
        }
    }
%>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</form>
</html>
