<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String mType = "";
  if(request.getParameter("moldType") != null && request.getParameter("moldType").length() > 0){
    mType = request.getParameter("moldType");
  }

  Vector tMap = null;
%>


<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<html>
<head>
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
<script>
function select(){
  document.forms[0].method = "post";
  document.forms[0].action = "/plm/jsp/project/machine/CreateMachine.jsp";
  document.forms[0].submit();
}

function onSave(){
  if(!checkValidate()) {
    return;
  }

  document.forms[0].method = "post";
  document.forms[0].action = "/plm/jsp/project/machine/MachineAction.jsp";
  document.forms[0].submit();
}

//Check Function
function checkValidate() {
  var form = document.forms[0];

  if(form.moldType.value =='') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "03295") %><%--구분을 선택하시기 바랍니다.--%>");
    return false;
  }

  if(form.maker.value =='') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00313") %><%--Maker를 입력하시기 바랍니다--%>");
    return false;
  }

  if(form.type.value =='') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00540") %><%--Type을 입력하시기 바랍니다--%>");
    return false;
  }

  if(form.ton.value =='') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00524") %><%--Ton을 입력하시기 바랍니다--%>");
    return false;
  }

  if(form.model.value =='') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00329") %><%--Model를 입력하시기 바랍니다--%>");
    form.model.focus();
    return false;
  }

  return true;
}
</script>
</head>
<body>
<form>
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00310") %><%--Machine 등록--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Admin<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00309") %><%--Machine 관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00310") %><%--Machine 등록--%></td>
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
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td><!--
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td> -->
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
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01051") %><%--금형구분--%></td>
          <td width="130" class="tdwhiteM"><select name="moldType" class="fm_jmp" style="width:120" onchange="javascript:select();">
            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
            <option value="Mold" <%if(mType.equals("Mold")){ %>selected<%} %>>Mold</option>
            <option value="Press" <%if(mType.equals("Press")){ %>selected<%} %>>Press</option>
          </select></td>
          <td width="130" class="tdblueM">Type</td>
          <td width="130" class="tdwhiteL"><select name="type" class="fm_jmp" style="width:120">
            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
            <%
        if(mType != null && mType.length() > 0) {
          tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MACHINETYPE", mType);
          for(int i = 0; i < tMap.size(); i++) {
            NumberCode tNum = (NumberCode)tMap.get(i);
      %>
        <option value="<%=CommonUtil.getOIDString(tNum)%>" ><%=tNum.getName()%></option>
      <%
          }
        }
      %>
          </select></td>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00523") %><%--Ton(형체력)--%></td>
          <td width="130" class="tdwhiteL0">
            <select name="ton" class="fm_jmp" style="width:120">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
              <%
        if(mType != null && mType.length() > 0) {
          tMap = NumberCodeHelper.manager.getNumberCodeLevel("MACHINETON", "1");
          for(int i = 0; i < tMap.size(); i++) {
            NumberCode tNum = (NumberCode)tMap.get(i);
        %>
        <option value="<%=CommonUtil.getOIDString(tNum)%>" ><%=tNum.getName()%></option>
        <%
          }
        }
        %>
            </select>
          </td>
        </tr>
        <tr>
          <td width="130" class="tdblueM">Maker</td>
          <td width="130" class="tdwhiteL">
            <select name="maker" class="fm_jmp" style="width:120">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
              <%
        if(mType != null && mType.length() > 0) {
          tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MACHINEMAKER", mType);
          for(int i = 0; i < tMap.size(); i++) {
            NumberCode tNum = (NumberCode)tMap.get(i);
        %>
        <option value="<%=CommonUtil.getOIDString(tNum)%>" ><%=tNum.getName()%></option>
        <%
          }
        }
        %>
            </select>
          </td>
          <td width="130" class="tdblueM">Model</td>
          <td colspan="3" class="tdwhiteL0">
      <input type="text" name="model" class="txt_field"  style="width:200" id="model"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
