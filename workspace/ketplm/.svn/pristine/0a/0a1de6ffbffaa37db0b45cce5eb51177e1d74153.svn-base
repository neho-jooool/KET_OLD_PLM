<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "e3ps.project.beans.*,e3ps.project.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String inputPN = request.getParameter("inputPN");
    String invokeMethod = request.getParameter("invokeMethod");
    if(invokeMethod == null)
        invokeMethod = "";
%>
<html>
<head>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "03078") %><%--프로젝트 생성--%></TITLE>
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
                return true;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
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

//-->
</script>
<form>

    <table border="0" cellpadding="0" cellspacing="0" class="popBox">
        <tr>
            <td class="boxTLeft"></td>
          <td class="boxTCenter"></td>
            <td class="boxTRight"></td>
        </tr>
        <tr>
            <td class="boxLeft"></td>
            <td>
    <!------------------------------------- 본문 시작 //----------------------------------------->
    <!-- title begin -->
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td class='titleP'><%=messageService.getString("e3ps.message.ket_message", "00223") %><%--ERP 프로젝트 목록--%></td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td class=fixLeft></td>
                                    <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" onClick="Javascript:onSelect();"></td>
                                    <td class=fixRight></td>
                                    <td>&nbsp;</td>
                                    <td class=fixLeft></td>
                                    <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="Javascript:self.close();"></td>
                                    <td class=fixRight></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
    <!-- title end -->
    <!-- list  begin  -->
                <div style="height:640px;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px;">
                    <table border="0" cellpadding="0" cellspacing="0" class="tab_popup03">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                    </table>
                    <table border="0" cellpadding="0" cellspacing="0" class="tab_popup03">
                        <tr>
                            <td class="tab_btm1"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" class="tab_popup03">

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
    ArrayList array = ProjectERPConMgr.getInstance().getERPProjectList(inputPN);
    if(array.size() == 0) {
%>
                        <tr>
                            <td class='tdwhiteM' align='center' colspan='7'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                        </tr>
<%
    }
    else {
        String pjtArr[] = null;
        for(int i = 0; i < array.size(); i++) {
            pjtArr = (String[])array.get(i);
            String type = messageService.getString("e3ps.message.ket_message", "02128")/*연구개발*/;
            if(pjtArr[2]=="AP" || pjtArr[2]=="BJ" || pjtArr[2]=="CD"){
            type = messageService.getString("e3ps.message.ket_message", "00852")/*고객제공*/;
            }

%>

                        <tr>
                            <!-- CheckBOX -->
                            <td class="tdwhiteM" rowspan=2>
                                <input name="oid" type="checkbox" class="Checkbox"  value="<%=pjtArr[0]%>"
                                                                            tempProjectNo="<%=pjtArr[0]%>"
                                                                            projectName="<%=pjtArr[1]%>"
                                                                            projectType="<%=pjtArr[2]%>"
                                                                            projectAcceptanceDate="<%=pjtArr[3]%>"
                                                                            projectDeliveredDate="<%=pjtArr[4]%>"
                                                                            projectFabName="<%=pjtArr[5]%>"
                                                                            projectProduct="<%=pjtArr[6]%>"
                                                                            projectCustomer="<%=pjtArr[7]%>"
                                                                            projectConsignment="<%=pjtArr[8]%>"
                                                                            projectSite="<%=pjtArr[9]%>"
                                                                            onclick="oneClick(this);">
                            </td>
                            <!-- PROJECT_NO -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[0]%></td>
                            <!-- PROJECT_NAME -->
                            <td class="tdwhiteL">&nbsp;<%=pjtArr[1]%></td>
                            <!-- PROJECT_NAM -->
                            <td class="tdwhiteL">&nbsp;<%=type%></td>
                            <!-- PROJECT_SPEC -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[3]%></td>
                            <!-- PROJECT_UNIT -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[4]%></td>
                            <!-- PROJECT_DELIVEREDDATE -->
                            <td class="tdwhiteM0">&nbsp;<%=pjtArr[5]%></td>
                        </tr>
                        <tr>
                            <!-- PM_QTY -->
                            <td class="tdwhiteL">&nbsp;<%=pjtArr[6]%></td>
                            <!-- PRODUCT -->
                            <td class="tdwhiteL">&nbsp;<%=pjtArr[7]%></td>
                            <!-- CUSTOMER -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[8]%></td>
                            <!-- CUSTOMER_NAME -->
                            <td class="tdwhiteM">&nbsp;<%=pjtArr[9]%></td>
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
    <!-- list  end  -->
    <!------------------------------------- 본문 끝 //----------------------------------------->
            </td>
            <td class="boxRight"></td>
        </tr>
        <tr>
            <td class="boxBLeft"></td>
            <td valign="bottom" class="boxBCenter"></td>
            <td class="boxBRight"></td>
        </tr>
    </table>

</form>
</html>
