<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import = "java.util.ArrayList,
                   java.util.Hashtable,
                   e3ps.common.code.NumberCodeHelper,
                   e3ps.common.util.StringUtil,
                   e3ps.common.web.ParamUtil" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String cmd = ParamUtil.getParameter(request, "cmd");
    String cmdname = messageService.getString("e3ps.message.ket_message", "02485") /*전체*/;
    String year = ParamUtil.getParameter(request, "year");
    String division = ParamUtil.getParameter(request, "division");
    String dept = ParamUtil.getParameter(request, "dept");
    String customerNo = ParamUtil.getParameter(request, "customerNo");
    String devType = ParamUtil.getParameter(request, "devType");
    String devTypename = messageService.getString("e3ps.message.ket_message", "02485") /*전체*/;
    String statestate = ParamUtil.getParameter(request, "statestate");
    String statestatename = messageService.getString("e3ps.message.ket_message", "02485") /*전체*/;
    String deleyState = null;
    String itemType = ParamUtil.getParameter(request, "itemType");
    String itemTypename = messageService.getString("e3ps.message.ket_message", "02485") /*전체*/;
    String making = ParamUtil.getParameter(request, "making");
    String makingname = messageService.getString("e3ps.message.ket_message", "02485") /*전체*/;
    String moldType = ParamUtil.getParameter(request, "moldType");
    String moldTypename = messageService.getString("e3ps.message.ket_message", "02485") /*전체*/;
    String moldType1 = "";
    String moldType2 = "";

    if(cmd != null && (cmd.equals("reportList1") || cmd.equals("reportList2"))){
        cmdname = messageService.getString("e3ps.message.ket_message", "00672")/*개발프로젝트*/;
    }else if(cmd != null && cmd.equals("reportList3")){
        cmdname = messageService.getString("e3ps.message.ket_message", "00734")/*검토프로젝트*/;
    }else{
        cmdname = itemType;
    }

    if(statestate != null && statestate.equals("C")){
        statestate = "'COMPLETED'";
        statestatename = messageService.getString("e3ps.message.ket_message", "02171") /*완료*/;
    }else if(statestate != null && statestate.equals("P")){
          statestate = "'COMPLETED','PMOINWORK','DEVASSIGN','INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'";
          deleyState = "N";
          statestatename = messageService.getString("e3ps.message.ket_message", "02726") /*진행*/;
    }else if(statestate != null && statestate.equals("S")){
          statestate = "'STOPED','WITHDRAWN'";
          statestatename = messageService.getString("e3ps.message.ket_message", "02688") /*중단*/;
    }else if(statestate != null && statestate.equals("D")){
          statestate = "'INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'";
          deleyState = "Y";
          statestatename = messageService.getString("e3ps.message.ket_message", "02703") /*지연*/;
    }else if(statestate != null && statestate.equals("N")){
        statestatename = messageService.getString("e3ps.message.ket_message", "02022") /*신규*/;
    }else if(statestate != null && statestate.equals("O")){
        statestatename = messageService.getString("e3ps.message.ket_message", "02321") /*이월*/;
    }

    if(devType != null && !devType.equals("")){
        devTypename = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", devType).getName();
    }

    if(itemType != null && !itemType.equals("")){
        itemTypename = itemType;
    }

    if(making != null && making.equals("사내")){
        makingname = messageService.getString("e3ps.message.ket_message", "01659") /*사내제작*/;
    }else if(making != null && making.equals("외주")){
        makingname = messageService.getString("e3ps.message.ket_message", "02188") /*외주제작*/;
    }

    if(moldType != null && moldType.equals("MAT001")){
      moldType1 = "MAT001";
        moldTypename = messageService.getString("e3ps.message.ket_message", "02017") /*시작금형*/;
    }else if(moldType != null && moldType.equals("MAT003,MAT005")){
        moldType1 = "MAT003";
        moldType2 = "MAT005";
        moldTypename = messageService.getString("e3ps.message.ket_message", "02016") /*시작Mo/Fa*/;
    }else if(moldType != null && moldType.equals("MAT002")){
        moldType1 = "MAT002";
        moldTypename = messageService.getString("e3ps.message.ket_message", "02086") /*양산금형*/;
    }else if(moldType != null && moldType.equals("MAT004,MAT006")){
        moldType1 = "MAT004";
        moldType2 = "MAT006";
        moldTypename = messageService.getString("e3ps.message.ket_message", "02082") /*양산Mo/Fa*/;
    }

    ArrayList list = (ArrayList)request.getAttribute("list");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style>
 .headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }
</style>

<script language='javascript'>
<!--

    //Project Report List 검색
    function search(){
        document.forms[0].sort.value = "2 DESC";

        document.forms[0].cmd.value = "<%=cmd%>";
        document.forms[0].year.value = "<%=year%>";
        document.forms[0].division.value = "<%=division%>";
        document.forms[0].dept.value = "<%=dept%>";
        document.forms[0].customerNo.value = "<%=customerNo%>";
        document.forms[0].statestate.value = "<%=statestate%>";
        <% if( cmd != null && cmd.equals("reportList1") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
        <% }else if( cmd != null && cmd.equals("reportList2") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
                document.forms[0].deleyState.value = "<%=deleyState%>";
        <% }else if( cmd != null && cmd.equals("reportList3") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
        <% }else if( cmd != null && cmd.equals("reportList4") ){ %>
                document.forms[0].itemType.value = "<%=itemType%>";
                document.forms[0].making.value = "<%=making%>";
                document.forms[0].moldType1.value = "<%=moldType1%>";
                document.forms[0].moldType2.value = "<%=moldType2%>";
                document.forms[0].deleyState.value = "<%=deleyState%>";
        <% }else if( cmd != null && cmd.equals("reportList5") ){ %>
                document.forms[0].itemType.value = "<%=itemType%>";
                document.forms[0].making.value = "<%=making%>";
                document.forms[0].moldType1.value = "<%=moldType1%>";
                document.forms[0].moldType2.value = "<%=moldType2%>";
        <% } %>

        document.forms[0].target = "list";
        document.forms[0].action = "/plm/servlet/e3ps/ProjectReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    // Project Report Excel
    function searchExcel(){
        document.forms[0].sort.value = "2 DESC";

        document.forms[0].cmd.value = "<%=cmd%>"+"Excel";
        document.forms[0].year.value = "<%=year%>";
        document.forms[0].division.value = "<%=division%>";
        document.forms[0].dept.value = "<%=dept%>";
        document.forms[0].customerNo.value = "<%=customerNo%>";
        document.forms[0].statestate.value = "<%=statestate%>";
        <% if( cmd != null && cmd.equals("reportList1") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
        <% }else if( cmd != null && cmd.equals("reportList2") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
                document.forms[0].deleyState.value = "<%=deleyState%>";
        <% }else if( cmd != null && cmd.equals("reportList3") ){ %>
                document.forms[0].devType.value = "<%=devType%>";
        <% }else if( cmd != null && cmd.equals("reportList4") ){ %>
                document.forms[0].itemType.value = "<%=itemType%>";
                document.forms[0].making.value = "<%=making%>";
                document.forms[0].moldType1.value = "<%=moldType1%>";
                document.forms[0].moldType2.value = "<%=moldType2%>";
                document.forms[0].deleyState.value = "<%=deleyState%>";
        <% }else if( cmd != null && cmd.equals("reportList5") ){ %>
                document.forms[0].itemType.value = "<%=itemType%>";
                document.forms[0].making.value = "<%=making%>";
                document.forms[0].moldType1.value = "<%=moldType1%>";
                document.forms[0].moldType2.value = "<%=moldType2%>";
        <% } %>

        document.forms[0].target = "download";
        document.forms[0].action = "/plm/servlet/e3ps/ProjectReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

//-->
</script>
</head>
<body onload="search();">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='cmd' value="">
<input type='hidden' name='year' value="">
<input type='hidden' name='division' value="">
<input type='hidden' name='dept' value="">
<input type='hidden' name='customerNo' value="">
<input type='hidden' name='devType' value="">
<input type='hidden' name='statestate' value="">
<input type='hidden' name='deleyState' value="">
<input type='hidden' name='itemType' value="">
<input type='hidden' name='making' value="">
<input type='hidden' name='moldType' value="">
<input type='hidden' name='moldType1' value="">
<input type='hidden' name='moldType2' value="">
<input type='hidden' name='sort' value="">
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="../../portal/images/logo_popupbg.png">
            <table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <% if( cmd != null && (cmd.equals("reportList1") || cmd.equals("reportList2") || cmd.equals("reportList3")) ){ %>
                <td class="font_01">Project Report (<%=cmdname%>_<%=statestatename%>)</td>
                <% }else { %>
                <%   if (itemTypename.equals("전체") && statestatename.equals("전체")){ %>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00399") %><%--Project Report (전체)--%></td>
                <%   }else{ %>
                <td class="font_01">Project Report (<%=itemTypename%>_<%=statestatename%>)</td>
                <%   } %>
                <% } %>
              </tr>
            </table>
          </td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top">
        <table width="880" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="860" height=20" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="left">
                          <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <% if( cmd != null && (cmd.equals("reportList1") || cmd.equals("reportList2") || cmd.equals("reportList3")) ){ %>
                            <td align="left" class="font_01"><%=devTypename%></td>
                            <% }else { %>
                            <%   if (itemTypename.equals("전체") && statestatename.equals("전체") && makingname.equals("전체") && moldTypename.equals("전체")){ %>
                            <td align="left" class="font_01">&nbsp;</td>
                            <%   }else if (makingname.equals("전체") && moldTypename.equals("전체")){ %>
                            <td align="left" class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
                            <%   }else{ %>
                                <td align="left" class="font_01"><%=makingname%>_<%=moldTypename%></td>
                                <%   } %>
                                <% } %>
                          </tr>
                        </table>
                      </td>
                      <td align="right">
                          <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><a href="javascript:searchExcel();"><img src="../../portal/images/iocn_excel.png" border="0"></a></td>
                            </tr>
                          </table>
                      </td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="860">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
            <table border="0" cellspacing="0" cellpadding="0" width="860">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <iframe name="list" width="860" height="570" src="" border="0" cellspacing="0" cellpadding="0" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            <table border="0" cellspacing="0" cellpadding="0" width="790">
            <tr>
              <td class="space15"></td>
            </tr>
              </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
        <table width="880" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="860" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</form>
</body>
</html>
