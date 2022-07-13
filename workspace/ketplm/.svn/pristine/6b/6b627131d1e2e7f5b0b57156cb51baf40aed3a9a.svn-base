<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<jsp:setProperty name="control" property="href" value="/plm/servlet/e3ps/MachineListServlet" />

<%
    String mType = "";
    if(request.getParameter("moldType") != null && request.getParameter("moldType").length() > 0){
        mType = request.getParameter("moldType");
    }

    Vector tMap = null;
%>

<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.PagingQueryResult"%>

<%@page import="e3ps.project.material.MoldMaterial"%>
<%@page import="e3ps.project.machine.MoldMachine"%>

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
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<script>
function select(){
    document.forms[0].method = "post";
    document.forms[0].action = "/plm/jsp/project/machine/ListMachine.jsp";
    document.forms[0].submit();
}

    function search() {
//    onProgressBar();
//    document.forms[0].command.value='search';
//    document.forms[0].sessionid.value ="0";
//    document.forms[0].tpage.value = "";
        document.forms[0].method = "post";
        document.forms[0].action = "/plm/servlet/e3ps/MachineListServlet";
        document.forms[0].submit();
    }

    function goViewPage(oid){
        var url="/plm/jsp/project/machine/MachineView.jsp?oid=" + oid;
        openOtherName(url, "popup", 500, 300, "status=yes,scrollbars=no,resizable=yes");
    }

    function onClear(){
        document.forms[0].moldType.value = "";
        document.forms[0].type.value = "";
        document.forms[0].ton.value = "";
        document.forms[0].maker.value = "";
        document.forms[0].model.value = "";
        document.forms[0].perPage.value = "10";
    }
</script>
</head>
<body>

<%
    String moldType  = ParamUtil.getStrParameter(request.getParameter("moldType"));
    String maker = ParamUtil.getStrParameter(request.getParameter("maker"));
    String type  = ParamUtil.getStrParameter(request.getParameter("type"));
    String ton  = ParamUtil.getStrParameter(request.getParameter("ton"));
    String model = ParamUtil.getStrParameter(request.getParameter("model"));
    String perPage = String.valueOf(control.getInitPerPage());
%>
<form>
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00308") %><%--Machine 검색--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Admin<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00309") %><%--Machine 관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00308") %><%--Machine 검색--%></td>
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
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01019") %><%--금형 구분--%></td>
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
                        String mMakerOid = CommonUtil.getOIDString(tNum);

                        String selected = "";
                        if(mMakerOid.equals(type)){
                            selected = "selected";
                        }

            %>
                <option value="<%=mMakerOid%>" <%=selected%>><%=tNum.getName()%></option>
            <%
                    }
                }
            %>
          </select></td>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00523") %><%--Ton(형체력)--%></td>
          <td width="130" class="tdwhiteL0"><select name="ton" class="fm_jmp" style="width:120">
            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
            <%
                if(mType != null && mType.length() > 0) {
                    tMap = NumberCodeHelper.manager.getNumberCodeLevel("MACHINETON", "1");

                    for(int i = 0; i < tMap.size(); i++) {
                        NumberCode tNum = (NumberCode)tMap.get(i);

                        String mTypeOid = CommonUtil.getOIDString(tNum);
                        String selected = "";
                        if(mTypeOid.equals(ton)){
                            selected = "selected";
                        }
                %>
                <option value="<%=mTypeOid%>" <%=selected%>><%=tNum.getName()%></option>
                <%
                    }
                }
            %>
          </select></td>
        </tr>
        <tr>
          <td width="130" class="tdblueM">Maker</td>
          <td width="130" class="tdwhiteL"><select name="maker" class="fm_jmp" style="width:120">
            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
            <%
                if(mType != null && mType.length() > 0) {
                    tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MACHINEMAKER", mType);

                    for(int i = 0; i < tMap.size(); i++) {
                        NumberCode tNum = (NumberCode)tMap.get(i);

                        String mTypeOid = CommonUtil.getOIDString(tNum);
                        String selected = "";
                        if(mTypeOid.equals(maker)){
                            selected = "selected";
                        }
                %>
                <option value="<%=mTypeOid%>" <%=selected%>><%=tNum.getName()%></option>
                <%
                    }
                }
            %>
          </select></td>
          <td width="130" class="tdblueM">Model</td>
          <td colspan="3" class="tdwhiteL0"><input type="text" name="model" class="txt_field"  style="width:200" id="model"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>

      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>

        </tr>
      </table>

      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <!-- td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><a href="javaScript:excelDown()"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                      </tr>
                    </table></td>
                    <td width="10"></td-->
                    <td align="right"><select name="perPage" class="fm_jmp" style="width:80" onChange="javaScript:search();" >
                      <option value="10" <%="10".equals(perPage) ? "selected" : ""%>>10</option>
                      <option value="30" <%="30".equals(perPage) ? "selected" : ""%>>30</option>
                      <option value="50" <%="50".equals(perPage) ? "selected" : ""%>>50</option>
                      <option value="100" <%="100".equals(perPage) ? "selected" : ""%>>100</option>
                    </select></td>
                  </tr>
                </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onClear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
                <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                <td width="140" class="tdblueM">Type</td>
                <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03383") %><%--형 체력--%></td>
                <td width="140" class="tdblueM">Maker</td>
                <td width="140" class="tdblueM0">Model</td>
              </tr>

   <%

        int listCount = control.getTopListCount();
           int cpage = control.getCurrentPage();
        int count = (Integer.parseInt(perPage) * (cpage - 1)) + 1;
        PagingQueryResult result = control.getResult();

        while ( result != null && result.hasMoreElements() ) {
            Object[] obj = (Object[])result.nextElement();
            MoldMachine data = (MoldMachine)obj[0];
            String mtype = data.getMoldType();

            String makerName = data.getMachineMaker().getName();
            String typeName = data.getMachineType().getName();
            String tonName = data.getTon().getName();
            String modelName = data.getModel();

            String oid = CommonUtil.getOIDString(data);

    %>
              <tr>
                <td width="80" class="tdwhiteM"><%=count++ %></td>
                <td width="140" class="tdwhiteM"><%=mtype%></td>
                <td width="140" class="tdwhiteM"><%=makerName%></td>
                <td width="140" class="tdwhiteM"><%=typeName%></td>
                <td width="140" class="tdwhiteM"><%=tonName%></td>
                <td width="140" class="tdwhiteM0"><a href ="javaScript:goViewPage('<%=oid%>');"><%=modelName%></a></td>
              </tr>
   <%}%>

   <%
    if(control.getTotalCount() == 0) {
%>
                <tr>
                    <td class='tdwhiteM0' align='center' colspan='6'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>
<%  }  %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                    <td>
                        <%@include file="/jsp/common/page_include.jsp" %>
                    </td>
                </tr>
             </table>

            </td>
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
