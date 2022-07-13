<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%@page import="e3ps.project.outputtype.OEMType"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
    String oid = request.getParameter("oid");
    String oemtype = request.getParameter("oemtype");
    String Level = request.getParameter("Level");
    Object type = CommonUtil.getObject(oid);

    if(type instanceof NumberCode){
        Kogger.debug("aa");
        NumberCode code = (NumberCode)CommonUtil.getObject(oid);
    }else{
        Kogger.debug("else");
        OEMProjectType code = (OEMProjectType) CommonUtil.getObject(oid);
    }
%>

<%@page import="e3ps.common.code.NumberCode"%>
<html>
<head>
<title><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "00942") %><%--관리(등록)--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
<!--
function createType() {
    if (!check()) return;
    else {
        document.CreateOEMType.action = '/plm/servlet/e3ps/ProjectOutPutTypeServlet';
        document.CreateOEMType.submit();
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
    var url = "/plm/jsp/groupware/folder/SelectFolder.jsp?folderpath=/Default/Document&target="+target;
    openWindow(url, "SelectFolder", 300, 300);
}

function cancel(oid)
{
    document.location.href="/plm/jsp/doc/doctype/ViewDocType.jsp?oid="+oid;
}
function cancel1(oid, oemtype)
{
    document.location.href="/plm/jsp/project/projectType/ViewOEMType.jsp?oid="+oid+"&oemtype="+oemtype;
}

    function SetNum(obj){
        val=obj.value;
        re=/[^0-9]/gi;
        obj.value=val.replace(re,"");
    }

function selectCustomerEvent(inputObj, inputLabel) {
    var url = "/plm/jsp/common/code/SelectNumberCode.jsp?oemtype=CUSTOMEREVENT";

    attache = window.showModalDialog(url,window,"help=no; scroll=yes; resizable=yes; dialogWidth=500px; dialogHeight:450px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }

    addList(attache, inputObj, inputLabel);
}

function selectCustomerEvent1(inputObj, inputLabel) {
    var url = "/plm/jsp/common/code/SelectNumberCode.jsp?oemtype=SUBCONTRACTOR";

    attache = window.showModalDialog(url,window,"help=no; scroll=yes; resizable=yes; dialogWidth=500px; dialogHeight:450px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }
    addList(attache, inputObj, inputLabel);
}

function addList(arrObj, inputObj, inputLabel) {
    if(arrObj.length == 0) {
        return;
    }

    var CustomerOid;//
    var CustomerName;//
    var CustomerCode

    for(var i = 0; i < arrObj.length; i++) {
        subarr = arrObj[i];
        CustomerOid = subarr[0];//
        CustomerCode = subarr[1];//
        CustomerName = subarr[2];

        inputObj.value = CustomerOid;
        inputLabel.value = CustomerName;
    }
}



function clearText(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
}

//parent.tree.location.reload();
//-->
</script>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
</head>
<body>
<form name = "CreateOEMType" method="post">
<input type="hidden" name="cmd" value="createOEM">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="oemtype" value="<%=oemtype%>">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00949") %><%--관리자 화면--%><img src="/plm/portal/images/icon_navi.gif"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "00941") %><%--관리--%></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20">&nbsp;</td>
          <td class="font_03">&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:createType()"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:cancel1('<%=oid%>', '<%=oemtype%>')"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
        <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
          <td width="630" class="tdwhiteL0">
            <input type=text  name='NAME' id=i>
          </td>

        </tr>
        <tr>
          <td width="150" class="tdblueL">친환경여부</td>
          <td width="630" class="tdwhiteL0">
          [  <label>Yes <input type=radio size=40 name="isEcoCar" id="isEcoCar" value="true" style="position:relative;top:2px;"></label>
             <label>No    <input type=radio size=40 name="isEcoCar" id=isEcoCar value="false" style="position:relative;top:2px;" checked></label> ]
          </td>
        </tr>
        <tr>
          <td width="150" class="tdblueL">Running Change 여부</td>
          <td width="630" class="tdwhiteL0">
          [  <label>Yes <input type=radio size=40 name="isRunningChange" value="true" style="position:relative;top:2px;"></label>
             <label>No    <input type=radio size=40 name="isRunningChange" value="false" style="position:relative;top:2px;" checked></label> ]
          </td>
        </tr>
        <!-- tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
          <td width="630" class="tdwhiteL0"><input type="text" name="CODE" class="txt_field"  style="width:400" id="textfield"></td>
        </tr -->
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
