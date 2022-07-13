<!-- 팝업 Size = 760px * 260px -->

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head><title><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></title>
<%
    String oid = request.getParameter("oid");
    String developentType = request.getParameter("developentType");
    String pjtNo = request.getParameter("pjtNo");
    String pjtName = request.getParameter("pjtName");
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");
    String pjtType = request.getParameter("pjtType");
    if(oid == null){
        oid = "";
    }

    if(developentType == null) {
        developentType = "";
    }

    if(pjtNo == null) {
        pjtNo = "";
    }
    if(pjtName == null) {
        pjtName = "";
    }

    if(mode == null) {
        mode = "one";
    }

    if(invokeMethod == null) {
        invokeMethod = "";
    }

    if(pjtType == null){
        pjtType = "";
    }

%>

<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {
        color: #FF0000
}
-->
</style>
<script language='javascript'>
<!--
function onSearch() {
    form = document.forms[0];
    form.method = "post";
    form.action= "/plm/jsp/project/ProjectListTable.jsp";
    form.target = "list";
    form.submit();
}


function onSelect() {
    form = document.forms[0];

    var arr = list.checkList();
    if(arr.length == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01816") %><%--선택한 Project가 없습니다--%>");
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
</head>
<body>
<form method="post">
<!-- hidden begin -->
<input type="hidden" name="cmd" value="select">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name=pjtType value="<%=pjtType%>">
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="10">&nbsp;</td>
                 <td valign="top">


                    <table width="700" border="0" cellspacing="0" cellpadding="0" >
                      <tr>
                        <td>&nbsp;</td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="700">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="700">
                      <tr>
                        <td  class="tab_btm2"></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="700">
                      <tr>
                        <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
                        <td width="200" class="tdwhiteL0" colspan=3><select name="developentType" class="fm_jmp" style="width:180">
                            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                            <%
                            Map<String, Object> parameter = new HashMap<String, Object>();
                            parameter.clear();
                            parameter.put("locale",   messageService.getLocale());
                            parameter.put("codeType", "DEVELOPENTTYPE");
                            List<Map<String, Object>> numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                            for ( int i=0; i<numCode.size(); i++ ) {
                            %>
                            <option value="<%=numCode.get(i).get("code") %>"><%=numCode.get(i).get("value")%></option>
                            <%
                            }
                            %>
                            </select></td>
                      </tr>
                      <tr>
                        <td width="150" class="tdblueM">Project No</td>
                        <td width="200" class="tdwhiteL"><input type="text" name="pjtNo" class="txt_field"  style="width:180" id="textfield"></td>
                        <td width="150" class="tdblueM">Project Name</td>
                        <td width="200" class="tdwhiteL0"><input type="text" name="pjtName" class="txt_field"  style="width:180" id="textfield"></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="700">
                      <tr>
                        <td class="space15"></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="700">
                      <tr>
                        <td  class="tab_btm2"></td>
                      </tr>
                    </table>
                    <table border="0" cellpadding="0" cellspacing="0" width="700">
                        <tr>
                            <td>
                                <iframe src="/plm/jsp/project/ProjectListTable.jsp?oid=<%=oid%>&cmd=select&mode=<%=mode%>&pjtType=<%=pjtType%>" id="list" name="list" frameborder="0" width="100%" height="400" leftmargin="0" topmargin="0" scrolling="no"></iframe>
                            </td>
                        </tr>
                    </table>

    <!------------------------------------- 본문 끝 //----------------------------------------->
                <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSelect()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                  </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="700" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
<!-- 산출물 정의 등록 layer 끝 -->
<!-- ############################################################################################################################## -->
</form>
</body>
</html>
