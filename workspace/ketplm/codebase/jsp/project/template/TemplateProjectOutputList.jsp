<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,
        e3ps.common.util.*,
        e3ps.project.beans.*,
        e3ps.groupware.company.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<jsp:setProperty name="control" property="href" value="/plm/servlet/e3ps/SearchProjectOutputServlet" />

<%
  String oid = CharUtil.E2K(request.getParameter("oid"));
//  TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);
  String category = CharUtil.E2K(request.getParameter("category"));
  control.setParam("type=nomal&oid="+oid);
%>

<%@page import="e3ps.common.web.ParamUtil"%><html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<script language=JavaScript>
<!--

function viewTask(oid) {
  parent.movePaage('/plm/jsp/project/template/TemplateProjectViewLeftFrm.jsp?oid=' + oid, '/plm/jsp/project/template/TemplateTaskView.jsp?oid=' + oid);
  //location.href = "/plm/jsp/project/template/TemplateTaskView.jsp?oid="+oid;

}

function outputList(type) {
  var url = "/plm/jsp/project/ProjectOutputListView.jsp?oid=<%=oid%>&projectType="+type
  openSameName(url,"800","600","status=no,scrollbars=yes,resizable=no");
}

function search() {
//  onProgressBar();
//  document.forms[0].command.value='search';
//  document.forms[0].sessionid.value ="0";
//  document.forms[0].tpage.value = "";

  if(document.forms[0].sessionid != null){
      document.forms[0].sessionid.value = "";
    }

    if(document.forms[0].page != null){
      document.forms[0].page.value = "";
    }

  document.forms[0].method = "post";
//  document.forms[0].command = "search";
  document.forms[0].action = "/plm/servlet/e3ps/SearchProjectOutputServlet";
  document.forms[0].submit();
}

function sorting(key){

  document.forms[0].method = "post";
  document.forms[0].action = "/plm/servlet/e3ps/SearchProjectOutputServlet";
  document.forms[0].sessionid.value = "";
  document.forms[0].page.value = "";

  if(key == document.forms[0].sortKey.value){
    if(document.forms[0].dsc.value == "false"){

      document.forms[0].dsc.value = "true";

    }else{
      document.forms[0].dsc.value = "false";
    }
  }
  else{
    document.forms[0].dsc.value = "false";
  }

  document.forms[0].sortKey.value = key;
  document.forms[0].submit();
}

function checkAll(){
  form = document.forms[0];
  if(form.chkAll.checked == ""){
    form.isAPQP.checked = "checked";
    form.isPSO10.checked = "checked";
    form.isESIR.checked = "checked";
    form.isISIR.checked = "checked";
  }else{
    form.isAPQP.checked = "";
    form.isPSO10.checked = "";
    form.isESIR.checked = "";
    form.isISIR.checked = "";
  }
}
function selected(){
  form = document.forms[0];
  if(form.isAPQP.checked == "" && form.isPSO10.checked == "" && form.isESIR.checked == "" && form.isISIR.checked == "" ){
    form.chkAll.checked = "checked";
  }else{
    form.chkAll.checked = "";
  }
}

//-->
</script>
<style type="text/css">
<!--
body {

}
-->
</style>
</head>
<body>
<%
  String perPage = String.valueOf(control.getInitPerPage());
  String sortKey = ParamUtil.getStrParameter(request.getParameter("sortKey"));
  String dsc = ParamUtil.getStrParameter(request.getParameter("dsc"));

  String chkAll = StringUtil.checkNull(request.getParameter("chkAll"));
  String isAPQP = StringUtil.checkNull(request.getParameter("isAPQP"));
  String isPSO10 = StringUtil.checkNull(request.getParameter("isPSO10"));
  String isESIR = StringUtil.checkNull(request.getParameter("isESIR"));
  String isISIR = StringUtil.checkNull(request.getParameter("isISIR"));


  if(isAPQP.length() == 0 && isPSO10.length() == 0 && isESIR.length() == 0 && isISIR.length() == 0){
    chkAll = "on";
  }
  if(dsc.length() == 0){
    dsc = "true";
  }

  String imgDsc = "(▲)";
  if(dsc.equals("false")){
    imgDsc = "(▼)";
  }

  if(sortKey.length() == 0){
    sortKey = "thePersistInfo.createStamp";
  }

%>

<form>
<input type = "hidden" name = "sortKey" value="<%=sortKey%>">
<input type = "hidden" name = "dsc" value="<%=dsc%>">
<input type = "hidden" name = "command" value="">
<input type = "hidden" name = "projectType" value="temp">
<input type = "hidden" name = "type" value="normal">
<input type = "hidden" name = "subAll" value="All">
<input type="hidden" name="oid" value="<%= oid %>">
<%
//oid=e3ps.project.MoldTemplateProject:220221&projectType=temp&type=normal&subAll=all
%>
<!-- Hidden Value -->

<!-- //Hidden Value -->
<!-- title제목 시작 //-->

<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00558") %><%--WBS 산출물 목록--%></td>
                <td  align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="/plm/portal/images/icon_navi.gif">WBS<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00558") %><%--WBS 산출물 목록--%></td>
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
          <td class="space15"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><!--  <a href="javaScript:excelDown()"><img src="/plm/portal/images/iocn_excel.png" border="0"></a>--></td>
                </tr>
              </table></td>
              <td width="10"></td>
              <td align="right">
                <input type="checkbox" name="chkAll" onclick="javascript:checkAll();" <%if(chkAll.length() > 0){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></input>
                <input type="checkbox" name="isAPQP" onclick="javascript:selected();" <%if(isAPQP.length() > 0){ %>checked<%} %>>APQP</input>
                <input type="checkbox" name="isPSO10" onclick="javascript:selected();" <%if(isPSO10.length() > 0){ %>checked<%} %>>PSO10단계</input>
                <input type="checkbox" name="isESIR" onclick="javascript:selected();" <%if(isESIR.length() > 0){ %>checked<%} %>>ESIR</input>
                <input type="checkbox" name="isISIR" onclick="javascript:selected();" <%if(isISIR.length() > 0){ %>checked<%} %>>ISIR</input>
              </td>
              <td width="10"></td>
              <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table></td>
              <td width="10"></td>
              <td align="right"><select name="perPage" class="fm_jmp" style="width:80" onChange="javaScript:search();" >
                <option value="10" <%="10".equals(perPage) ? "selected" : ""%>>10</option>
                <option value="30" <%="30".equals(perPage) ? "selected" : ""%>>30</option>
                <option value="50" <%="50".equals(perPage) ? "selected" : ""%>>50</option>
                <option value="100" <%="100".equals(perPage) ? "selected" : ""%>>100</option>
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
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
          <td width="190" class="tdblueM"><a href = "javaScript:sorting('<%=TemplateTask.TASK_NAME%>');"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%><%=TemplateTask.TASK_NAME.equals(sortKey) ? imgDsc : "" %></a></td>
          <td width="300" class="tdblueM"><a href = "javaScript:sorting('<%=ProjectOutput.OUTPUT_NAME%>');"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><%=ProjectOutput.OUTPUT_NAME.equals(sortKey) ? imgDsc : "" %></a></td>
          <td width="50" class="tdblueM"><a href = "javaScript:sorting('<%=ProjectOutput.OBJ_TYPE%>');"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%><%=ProjectOutput.OBJ_TYPE.equals(sortKey) ? imgDsc : "" %></a></td>
          <td width="70" class="tdblueM"><a href = "javaScript:sorting('<%=ProjectOutput.IS_PRIMARY%>');"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%><%=ProjectOutput.IS_PRIMARY.equals(sortKey) ? imgDsc : "" %></a></td>
          <!-- <td width="200" class="tdblueM"><a href = "javaScript:sorting('<%=ProjectOutput.OUTPUT_LOCATION%>');"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%><%=ProjectOutput.OUTPUT_LOCATION.equals(sortKey) ? imgDsc : "" %></a></td> -->
          <td width="90" class="tdblueM0"><a href = "javaScript:sorting('<%="thePersistInfo.createStamp"%>');"><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%><%="thePersistInfo.createStamp".equals(sortKey) ? imgDsc : "" %></a></td>
        </tr>
<%
  int count = 1;
  PagingQueryResult result = control.getResult();
  while ( result != null && result.hasMoreElements() ) {
    Object[] obj = (Object[])result.nextElement();
    ProjectOutputData data = new ProjectOutputData((ProjectOutput)obj[0]);

%>
        <tr>
          <td class="tdwhiteM" >&nbsp;<%=count++ %></td>
          <% if(data.task!=null) { %>
          <td class="tdwhiteL">
            <img src="/plm/portal/icon/task.gif">&nbsp;
            <a href="javascript:viewTask('<%=CommonUtil.getOIDString(data.task)%>')">&nbsp;<%=data.task.getTaskName()%></a>
          </td>
          <% } %>
          <td class="tdwhiteL">&nbsp;<%=data.name%></td>
          <td class="tdwhiteM">&nbsp;<%=data.objType%></td>
          <td class="tdwhiteM">&nbsp;<%=data.isPrimary%></td>
          <!-- <td class="tdwhiteL">&nbsp;<%=data.locationStr%></td> -->
          <!--<td class="tdwhiteL">&nbsp;<%=data.role_ko%></td>-->
          <td class="tdwhiteM0">&nbsp;<%=data.createDate%></td>
        </tr>
<%
  }

  if(control.getTotalCount() == 0) {
%>
        <tr>
          <td class='tdwhiteM0' align='center' colspan='10'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
        </tr>
<%  }  %>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width=98% align=center>
    <tr>
      <td width=100%>
      <%control.setPostMethod();%>
      <%@include file="/jsp/common/page_include.jsp" %>
      </td>
    </tr>
    </table>

    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>

<!-- 본문외관테두리 끝 //-->
</form>
</center>
</body>
</html>
