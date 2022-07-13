<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    String oid = ParamUtil.getStrParameter(request.getParameter("oid"));
     String viewtype = ParamUtil.getStrParameter(request.getParameter("viewtype"));
     PeopleData data = new PeopleData(CommonUtil.getObject(oid));
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01680") %><%--사원 정보--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<script language=javascript>
function update() {
    document.viewpeople.action = "/plm/page/groupware/company/peopleUpdate.jsp";
    document.viewpeople.submit();
}

function closeForm(){
    self.close();
}

function cancelForm(){
    history.back();
}
function viewEducationHistory(userOid){
    var url = "/plm/jsp/manage/edu/educationlistall.jsp?userOid=" + userOid;
    openOtherName(url,"viewEducationHistory","800","640","status=no,scrollbars=yes,resizable=yes");
}
</script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</HEAD>
<%@include file="/jsp/common/processing.html"%>
<body>
<form name="projectUpdate" method="post">
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=command >

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01681") %><%--사원 정보 보기--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top">
      <table width="520" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="520" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td rowspan="2">
                  <table width="520" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20">
                        <img src="/plm/portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
		              <td align="right">
		                <table border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:<%=(viewtype.equals("open"))?"closeForm()":"cancelForm()"%>;" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
		                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		                  </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="520">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="520">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="520">
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02074") %><%--아이디--%></td>
                <td width="280" class="tdwhiteL0">
                <%=data.id%>
                </td>
                <td rowspan="5" align="right" valign="top">
                    <img src="<%=data.imgUrl%>" border="0" width="130" height="152">
                </td>
              </tr>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                <td width="280" class="tdwhiteL0">
                  <%=data.name%>
                </td>
              </tr>
              <tr>
                <td width="100" class="tdblueL">e-mail</td>
                <td width="280" class="tdwhiteL0"><%=data.email%>
                </td>
              </tr>
<%

    String departmentStr = "";
    if ( data.department != null ) departmentStr = data.departmentName;
    else departmentStr = "지정안됨";
%>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                <td width="280" class="tdwhiteL0">
                  &nbsp;<%=departmentStr%>
                    <% if(StringUtil.checkString(data.chief)) { %>
                    &nbsp;&nbsp;[ <%=StringUtil.checkNull(data.chief)%> <%=messageService.getString("e3ps.message.ket_message", "01562") %><%--부서장--%> ]
                    <% } %>
                </td>
              </tr>
<%
    String dutyStr = "";
    if( data.duty == null || data.duty.trim().length() < 1 ) dutyStr = "지정안됨";
    else dutyStr = data.duty;
%>
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                <td width="280" class="tdwhiteL0"><%= dutyStr %>
                </td>
              </tr>
              
		    </table>
		    
		  </td>
		</tr>
        <tr>
          <td class="space10"></td>
        </tr>
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="520" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td rowspan="2">
                  <table width="520" border="0" cellspacing="0" cellpadding="0" >
		            <tr>
		              <td width="20">
		                <img src="/plm/portal/images/icon_4.png"></td>
		              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01912") %><%--세부정보--%></td>
		            </tr>
		          </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="520">
              <tr>
                <td rowspan="2" class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="520">
              <tr>
                <td rowspan="2" class="tab_btm2"></td>
              </tr>
            </table>
              
            <table width="520" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03260") %><%--휴대전화--%></td>
                <td width="420" class="tdwhiteL0"><%=data.cellTel%>
                </td>
              </tr>
            </table>
            <table width="520" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01657") %><%--사내번호--%></td>
                <td width="420" class="tdwhiteL0"><%=data.officeTel%>
                </td>
              </tr>
            </table>
              
            <table width="520" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02740") %><%--집 전화번호--%></td>
                <td width="420" class="tdwhiteL0"><%=data.homeTel%>
                </td>
              </tr>
            </table>
            <table width="520" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02675") %><%--주소--%></td>
                <td width="420" class="tdwhiteL0"><%=data.address%>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="520" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</BODY>
</HTML>



