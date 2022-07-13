<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*" %>
<%@page import = "e3ps.wfm.entity.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.common.obj.*" %>
<%@page import = "wt.epm.*" %>
<%@page import = "wt.lifecycle.*" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) CommonUtil.getObject(oid);

    String title = "&nbsp;";
    String reqDate = "&nbsp;";
    String reqUser = "&nbsp;";
    String desc = "&nbsp;";
    String lastApprover = "&nbsp;";
    String multiState = "&nbsp;";

    if (multiReq != null) {
        title = multiReq.getReqName();
        reqUser = multiReq.getCreatorFullName();
        desc =desc = ParamUtil.checkStrParameter(multiReq.getDescription(),"&nbsp;");
        multiState = multiReq.getState().getState().getDisplay();
        if(multiReq.getState().getState().equals(State.toState("APPROVED")))lastApprover = WorkflowSearchHelper.manager.getLastApprover(multiReq).getFullName();
    }

    int perpage = ParamUtil.getIntParameter(request.getParameter("perpage"), 10);
    int cpage = ParamUtil.getIntParameter(request.getParameter("cpage"), 1);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/plm/portal/js/common.js"></script>
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
<script language="javascript">
function doViewEPM(_oid) {
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid="+_oid;
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=820,height=800');
    newWinSPart.focus();
    newWinSPart.location.href = url;
}

function updateMulti(){
    document.forms[0].method="post";
    document.forms[0].action="/plm/jsp/wfm/UpdateMultiApproval.jsp?oid=<%=oid%>";
    document.forms[0].submit();
}

function deleteMulti(){
    document.forms[0].method="post";
    document.forms[0].action="/plm/servlet/e3ps/CommonWorkflowServlet?cmd=deleteMulti&oid=<%=oid%>";
    document.forms[0].submit();


}
function pageSetting(sel){
    document.forms[0].action = "ViewMultiApprovalPopup.jsp";
    document.forms[0].submit();
}

</script>
<body>
<form>
<input type="hidden" name="cpage" id="cpage" value="<%=cpage%>">
<input type="hidden" name="oid" id="oid" value="<%=oid%>">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02338") %><%--일괄결재요청 상세정보--%></td>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02338") %><%--일괄결재요청 상세정보--%></td>
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
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02197") %><%--요청제목--%></td>
          <td colspan="3" class="tdwhiteL0"><%=title%></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02196") %><%--요청자--%></td>
          <td width="290" class="tdwhiteL"><%=reqUser%></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02195") %><%--요청일자--%></td>
          <td width="290" class="tdwhiteL0"><%=reqDate%></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
          <td width="290" class="tdwhiteL"><%=lastApprover %></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td width="290" class="tdwhiteL0"><%=multiState %></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td colspan="3" class="tdwhiteL0" style="height:100"><textarea name="textfield10" class="txt_fieldRO" id="textfield12" style="width:100%; height:96%" readonly><%=desc%></textarea></td>
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
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right"><select name="perpage" class="fm_jmp" style="width:80" onChange="pageSetting(this)">
                    <option value="10" <%=(perpage == 10) ? "selected" : ""%>>10</option>
                    <option value="30" <%=(perpage == 30) ? "selected" : ""%>>30</option>
                    <option value="50" <%=(perpage == 50) ? "selected" : ""%>>50</option>
                    <option value="100" <%=(perpage == 100) ? "selected" : ""%>>100</option>
                  </select></td>
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
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
          <td width="280" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="120" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
        </tr>
        <%
            Vector vec = null;
            vec = WorkflowSearchHelper.manager.getEPMList(multiReq);
            if (vec != null) {
                vec = WorkflowSearchHelper.manager.getEPMList(multiReq);
                int size = vec.size();
                int index = 0;
                int maxValue = 0;
                if ((cpage == 1) && (perpage < vec.size())) {
                    maxValue = size - perpage;
                } else if ((cpage > 1) && (cpage * perpage < vec.size())) {
                    maxValue = size - (cpage * perpage);
                } else {
                    maxValue = 0;
                }
                for (index = size - ((cpage - 1) * perpage); index >= maxValue + 1; index--) {
                    EPMDocument epmDoc = (EPMDocument) vec.get(index - 1);
                    epmDoc = (EPMDocument)ObjectUtil.getLatestVersion(epmDoc);
                    String createDate = DateUtil.getDateString(epmDoc.getPersistInfo().getCreateStamp(), "d");

        %>
        <tr>
        <td class="tdwhiteM"><%=index%></td>
        <td class="tdwhiteM" title="<%=epmDoc.getNumber() %>"><a href="javascript:doViewEPM('<%=epmDoc.toString()%>');"><nobr style="text-overflow:ellipsis;overflow:hidden;width:130;cursor:hand;"><%=epmDoc.getNumber()%></nobr></a></td>
        <td class="tdwhiteL" title="<%=epmDoc.getName() %>"><nobr style="text-overflow:ellipsis;overflow:hidden;width:280;"><%=epmDoc.getName()%></nobr></td>
        <td class="tdwhiteM"><%=epmDoc.getCreatorFullName()%></td>
        <td class="tdwhiteM"><%=KETObjectUtil.getVersion(epmDoc)%></td>
        <td class="tdwhiteM0"><%=epmDoc.getState().getState().getDisplay(messageService.getLocale())%></td>
        </tr>
        <%
                }
            }
        %>
        </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
        <%
            if (vec != null) {
                int totalSize = vec.size();
                int totalPage = totalSize / perpage;
                int x = totalSize % perpage;
                if (x > 0)
                    totalPage = totalPage + 1;
                int pageCount = 5;
                int temp = cpage / pageCount;
                if ((cpage % pageCount) > 0)
                    temp++;
                int start = (temp - 1) * pageCount + 1;
                int end = start + pageCount - 1;
                if (end > totalPage) {
                    end = totalPage;
                }
        %>
        <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="small" align="left">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> : <%=totalPage%>)</td>
          <td><table border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <!-- 처음 -->
                        <td width="20" align="center">
                        <%
                            if (start > 1) {
                        %><a href="ViewMultiApproval.jsp?oid=<%=oid%>&cpage=1&perpage=<%=perpage %>"
                            class='small'><img src='../../portal/images/btn_arrow4.gif'
                            style='border: 0'></a>
                        <%
                            }
                        %>
                        </td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <!-- 이전 -->
                        <%
                            if (start > 1) {
                        %>
                        <td width="20" align="center"><a href="ViewMultiApproval.jsp?oid=<%=oid%>&cpage=<%=start - 1%>&perpage=<%=perpage %>" class='small'><img
                            src='../../portal/images/btn_arrow3.gif' style='border: 0'></a></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%
                            }
                        %>
                        <!-- 페이지 -->
                        <%
                            for (int i = start; i <= end; i++) {
                        %>
                        <td style="padding: 2 8 0 7; cursor: hand"
                            onMouseOver="this.style.background='#ECECEC'"
                            OnMouseOut="this.style.background=''" class="nav_on"><font
                            color=777777>
                        <%
                            if (i == cpage) {
                        %><b><font color=006699><%=i%></font></b>
                        <%
                            } else {
                        %><a href="ViewMultiApproval.jsp?oid=<%=oid%>&cpage=<%=i%>&perpage=<%=perpage %>"><%=i%></a>
                        <%
                            }
                        %>
                        </font></td>
                        <%
                            }
                        %>
                        <!-- 다음 -->
                        <%
                            if (end < totalPage) {
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="20" align="center"><a href="ViewMultiApproval.jsp?oid=<%=oid%>&cpage=<%=end + 1%>&perpage=<%=perpage %>"  class='small'><img
                            src='../../portal/images/btn_arrow1.gif' style='border: 0'></a></td>
                        <%
                            }
                        %>
                        <!-- 마지막 -->
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="20" align="center">
                        <%
                            if (end < totalPage) {
                        %><a href="ViewMultiApproval.jsp?oid=<%=oid%>&cpage=<%=totalPage %>&perpage=<%=perpage %>"  class='small'><img
                            src='../../portal/images/btn_arrow2.gif' style='border: 0'></a></td>
                        <%
                            }
                        %>
                    </tr>
                </table></td>
          <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%> : <%=totalSize%>)</td>
        </tr>
      </table>
      <%
          }
      %>
      </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
