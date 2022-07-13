<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    MoldMaterial material = (MoldMaterial)CommonUtil.getObject(oid);
    String materialGubun = material.getMaterial();
    String maker = material.getMaterialMaker().getName();
    String type = material.getMaterialType().getName();
    String grade = material.getGrade();
%>

<%@page import="e3ps.common.util.CommonUtil"%>

<%@page import="e3ps.project.material.MoldMaterial"%><html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02216") %><%--원재료 상세정보--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript">

function modify(){
    document.forms[0].action="/plm/jsp/project/material/MaterialModify.jsp";
    document.forms[0].submit();

}

function materialDelete(){
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
        document.forms[0].mode.value="delete";
        document.forms[0].action="/plm/jsp/project/material/MaterialAction.jsp";
        document.forms[0].submit();
    }

}
</script>
</head>
<body>
<form>
<input type="hidden" name="mode" value=""></input>
<input type="hidden" name="oid" value="<%=oid %>"></input>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02216") %><%--원재료 상세정보--%></td>
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
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:materialDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
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
                <td width="120" class="tdwhiteL"><%=materialGubun %></td>
                <td width="100" class="tdblueL">Maker</td>
                <td width="130" class="tdwhiteL0"><%=maker %></td>
              </tr>
              <tr>
                <td width="110" class="tdblueL">Type</td>
                <td width="120" class="tdwhiteL"><%=type %></td>
                <td width="100" class="tdblueL">Grade</td>
                <td width="130" class="tdwhiteL0"><%=grade %></td>
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
