<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    MoldMachine machine = (MoldMachine)CommonUtil.getObject(oid);

    String mType = "";
    boolean isCall = false;

    if(request.getParameter("call") != null){
        isCall = true;
        mType = request.getParameter("moldType");
    }else{
        mType = machine.getMoldType();
    }

    String makerOid = CommonUtil.getOIDString(machine.getMachineMaker());
    String typeOid = CommonUtil.getOIDString(machine.getMachineType());
    String tonOid = CommonUtil.getOIDString(machine.getTon());

    String maker = machine.getMachineMaker().getName();
    String type = machine.getMachineType().getName();
    String ton = machine.getTon().getName();
    String model = "";
    if(machine.getModel() != null && machine.getModel().length() >0){
        model = machine.getModel();
    }

    Vector tMap = null;
%>

<%@page import="e3ps.common.util.CommonUtil"%>

<%@page import="e3ps.project.machine.MoldMachine"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%><html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02216") %><%--원재료 상세정보--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript">

function modify(){
    document.forms[0].action="/plm/jsp/project/machine/MachineAction.jsp";
    document.forms[0].submit();

}

function view(){
    document.forms[0].action="/plm/jsp/project/machine/MachineView.jsp";
    document.forms[0].submit();

}

function select(){
    document.forms[0].method = "post";
    document.forms[0].action = "/plm/jsp/project/machine/MachineModify.jsp";
    document.forms[0].submit();
}
</script>
</head>
<body>
<form>
<input type="hidden" name="mode" value="modify"></input>
<input type="hidden" name="oid" value="<%=oid %>"></input>
<input type="hidden" name="call" value="true"></input>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00312") %><%--Machine 상세정보--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>

                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:modify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    <td width="5">&nbsp;</td>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:view();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>

                  </tr>
                </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                <td width="120" class="tdwhiteL"><select name="moldType" class="fm_jmp" style="width:120" onchange="javascript:select();">
                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                    <option value="Mold" <%if(mType.equals("Mold")){ %>selected<%} %>>Mold</option>
                    <option value="Press" <%if(mType.equals("Press")){ %>selected<%} %>>Press</option>
                </select></td>
                <td width="100" class="tdblueL">Type</td>
                <td width="130" class="tdwhiteL0"><select name="type" class="fm_jmp" style="width:120">
                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                    <%
                        if(mType != null && mType.length() > 0) {
                            tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MACHINETYPE", mType);
                            for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);
                                String tValue = CommonUtil.getOIDString(tNum);
                                String selected = "";
                                  if(tValue.equals(typeOid) && !isCall){
                                      selected = "selected";
                                  }
                    %>
                        <option value="<%=tValue%>" <%=selected %>><%=tNum.getName()%></option>
                    <%
                            }
                        }
                    %>
                </select></td>
              </tr>
              <tr>
                <td width="110" class="tdblueL">Maker</td>
                <td width="120" class="tdwhiteL"><select name="maker" class="fm_jmp" style="width:120">
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                  <%
                    if(mType != null && mType.length() > 0) {
                        tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MACHINEMAKER", mType);
                        for(int i = 0; i < tMap.size(); i++) {
                            NumberCode tNum = (NumberCode)tMap.get(i);
                            String tValue = CommonUtil.getOIDString(tNum);
                            String selected = "";
                              if(tValue.equals(makerOid) && !isCall){
                                  selected = "selected";
                              }
                    %>
                    <option value="<%=tValue%>" <%=selected %>><%=tNum.getName()%></option>
                    <%
                        }
                    }
                    %>
                </select></td>
                <td width="100" class="tdblueL">Ton</td>
                <td width="130" class="tdwhiteL0"><select name="ton" class="fm_jmp" style="width:120">
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                  <%
                    if(mType != null && mType.length() > 0) {
                        tMap = NumberCodeHelper.manager.getNumberCodeLevel("MACHINETON", "1");
                        for(int i = 0; i < tMap.size(); i++) {
                            NumberCode tNum = (NumberCode)tMap.get(i);
                            String tValue = CommonUtil.getOIDString(tNum);
                            String selected = "";
                              if(tValue.equals(tonOid) && !isCall){
                                  selected = "selected";
                              }
                    %>
                    <option value="<%=tValue%>" <%=selected %>><%=tNum.getName()%></option>
                    <%
                        }
                    }
                    %>
                   </select></td>
              </tr>
              <tr>
                <td width="110" class="tdblueL">Model</td>
                <td width="130" colspan="3" class="tdwhiteL0"><input type="text" name="model"class="txt_field"  style="width:100" id="model" value="<%=model %>"></input></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
