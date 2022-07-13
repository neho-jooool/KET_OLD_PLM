<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
String oid = request.getParameter("oid");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02081") %><%--양산 금형변경 일정 검색 팝업--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<script type="text/javascript">

//목록조회
function doSearch(){
    var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "searchMoldPlanPopup";
    form.target = "download";
    form.action = url;
    form.method = "post";
    disabledAllBtn();
    showProcessing();
    form.submit();
}

function onSelect() {
    if(!checkedCheck()) {
        alert("코드를 선택하십시오.");
        return;
    }
    var form = document.forms[0];
    var arr = new Array();
    var subArr = new Array();
    var idx = 0;
    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                subArr = new Array();
                subArr[0] = form.oid[i].value;
                subArr[1] = form.oid[i].codecode;
                subArr[2] = form.oid[i].codename;
                subArr[3] = form.oid[i].codedesc;
                subArr[4] = form.oid[i].codedate;
                arr[idx++] = subArr;
            }
        }
    }else{
        if(form.oid.checked == true) {
            subArr = new Array();
            subArr[0] = form.oid.value;//oid
            subArr[1] = form.oid.codecode;//dieNo
            subArr[2] = form.oid.codename;//scheduleId
            subArr[3] = form.oid.codedesc;//partNo
            subArr[4] = form.oid.codedate;//activity date
            arr[idx++] = subArr;
        }
    }
    parent.selectModalDialog(arr);
}

function checkedCheck() {
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


function deleteAllList() {
    var body = document.getElementById("listTable");
    if (body.rows.length == 0) return;
    for (var i = body.rows.length; i > 0; i--) {
        body.deleteRow(i - 1);
    }
}

function addAllList(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var targetTable = document.getElementById("listTable");
    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];

        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);

        //선택
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "40";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='checkbox' value='" + trArr[0] + "' name='oid' codename='" + trArr[1] + "' codecode='" + trArr[2] + "' codedesc='" + trArr[3] + "' codedate='"+trArr[7]+"' onClick='oneClick(this)'>";

        //일정 번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "110";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[1];

        //Die No
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "90";
        newTd.className = "tdwhiteM";
        str = "&nbsp;" + trArr[2] + "&nbsp;";
        newTd.innerHTML = str;

        //부품번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "100";
        newTd.className = "tdwhiteM";
        str = "&nbsp;" + trArr[3] + "&nbsp;";
        newTd.innerHTML = str;

        //부품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "185";
        newTd.className = "tdwhiteL";
        str ="<font title=\""+trArr[4]+"\">";
        str += "<div class='ellipsis' style='width:185;'><nobr>";
        str += trArr[4] +"</nobr></div></font>";
        newTd.innerHTML = str;

        //계획수립일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "80";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[7];

        //등록자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "80";
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = trArr[6];
    }
}

function addNotFound() {
    var targetTable = document.getElementById("listTable");
    var trArr;
    var str = "";

    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);

    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "100%";
    newTd.colspan = "7";
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<font color='red'>검색된 항목이 없습니다.</font>";
}
</script>

</head>
<body>
<form method="post" action="/plm/servlet/e3ps/SearchMoldEcoServlet">
<input type="hidden" name="cmd" value="searchPartPopup">
<input type="hidden" name="oid" value="<%=oid%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02080") %><%--양산 금형변경 일정 검색--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <!-- td width="10">&nbsp;</td -->
          <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
	            <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td>
	                    <table border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:doSearch();" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                      </tr>
	                    </table>
	                  </td>
	                  <td width="5">&nbsp;</td>
	                  <td>
	                    <table border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:onSelect();" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                      </tr>
	                    </table>
	                  </td>
	                </tr>
	              </table>
	            </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="100" class="tdblueL">Die No</td>
                <td width="240" class="tdwhiteL"><input name="dieNo" type="text" class="txt_field" id="textfield4"  style="width:98%" value=""></td>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="250" class="tdwhiteL0"><input type="text" name="partNumber" class="txt_field"  style="width:98%" id="textfield9"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border">
            <tr>
              <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></td>
              <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02349") %><%--일정 번호--%></td>
              <td width="90" class="tdblueM">Die No</td>
              <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
              <td width="190" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
              <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00816") %><%--계획수립일--%></td>
              <td width="80" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01337") %><%--등록자--%></td>
            </tr>
            </table>
            <div style="height:233;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
            <table width="100%" cellpadding="0" cellspacing="0" id="listTable">
            </table>
            </div>
            
      </table></td>
  </tr>
</table>
<!-- table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10">&nbsp;</td>
    <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="auto"></iframe></td>
  </tr>
</table -->
</form>
</body>
<iframe src="" name="download" width="0" height="0" frameborder="0"></iframe>
</html>
