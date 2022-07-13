<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page import="wt.fc.*,wt.lifecycle.*,
        wt.org.*,
        wt.query.*,
        wt.session.*,
        wt.util.*"%>
<%@page import="e3ps.project.*,
        e3ps.project.beans.*,
        e3ps.common.util.*,
        e3ps.common.code.*,
        e3ps.common.jdf.config.*,
        e3ps.common.code.GenNumberCode,
        e3ps.common.web.HtmlTagUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="java.util.Vector"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<html>
<%
  HashMap map = new HashMap();

  String command = request.getParameter("command");
  String isSelect = request.getParameter("isSelect");
  String mode = request.getParameter("mode");
  //String initType = request.getParameter("initType");
  String pjtType = request.getParameter("pjtType");
  String tmpTitle = messageService.getString("e3ps.message.ket_message", "01832")/*선행개발*/;
  if(pjtType.equals("3")){
    tmpTitle = messageService.getString("e3ps.message.ket_message", "02934")/*텔덱*/;
  }


  String setPm = request.getParameter("setPm");

  //pjtType = "0";
  //검색 속성
  String pjtNo = request.getParameter("pjtNo");                 //pjtNo(프로젝트 NO)
  String pjtName = request.getParameter("pjtName");               //pjtName(프로젝트 명)
  String pjtState = request.getParameter("pjtState");             //pjtState(상태)
  if(pjtState == null){
    pjtState = "PROGRESS";
  }
  String planStartStartDate = request.getParameter("planStartStartDate");   //planStartStartDate(계획 시작일자 - 시작)
  String planStartEndDate = request.getParameter("planStartEndDate");     //planStartEndDate(계획 시작일자 - 끝)
  String planEndStartDate = request.getParameter("planEndStartDate");     //planEndStartDate(계획 종료일자 - 시작)
  String planEndEndDate = request.getParameter("planEndEndDate");       //planEndEndDate(계획 종료일자 - 끝)
  String pjtModel = request.getParameter("modelcode");            //pjtModel(모델[차종])
  String pjtDivision = request.getParameter("divisioncode");          //pjtDivision(개발유형)
  String pjtLevel = request.getParameter("levelcode");            //pjtLevel(난이도)
  String pjtProduct = request.getParameter("productcode");          //pjtProduct(제품군)
  String pjtCustomer = request.getParameter("customercode");          //pjtCustomer(발주처)
  String pjtDevCompany = request.getParameter("devcompanycode");        //pjtDevCompany(개발조직)
  String pjtMakeCompany = request.getParameter("makecompanycode");      //pjtMakeCompany(생산조직)



  /*
  out.println("#######################################################<br>");
  out.println("<br>command "+command);
  out.println("<br>pjtNo "+pjtNo);
  out.println("<br>pjtName "+pjtName);
  out.println("<br>pjtType "+pjtType);
  out.println("<br>pjtState "+pjtState);
  out.println("<br>planStartStartDate "+planStartStartDate);
  out.println("<br>planStartEndDate "+planStartEndDate);
  out.println("<br>planEndStartDate "+planEndStartDate);
  out.println("<br>planEndEndDate "+planEndEndDate);
  out.println("<br>pjtModel "+pjtModel);
  out.println("<br>pjtDivision "+pjtDivision);
  out.println("<br>pjtLevel "+pjtLevel);
  out.println("<br>pjtProduct "+pjtProduct);
  out.println("<br>pjtCustomer "+pjtCustomer);
  out.println("<br>pjtDevCompany "+pjtDevCompany);
  out.println("<br>pjtMakeCompany "+pjtMakeCompany);
  out.println("<br>#######################################################");
  */



  if(command == null)
    command = "search";

  if(isSelect == null)
    isSelect = "";

  if(mode == null)
    mode = "";

  if (command.length() > 0) {
    map.put("command", command);
  }

  if (isSelect.length() > 0) {
    map.put("isSelect", isSelect);
  }


  if (mode.length() > 0) {
    map.put("mode", mode);
  }


  ////////////////////////////////////////////////////////////////
  // 검색
  ////////////////////////////////////////////////////////////////

  if (pjtNo != null && pjtNo.length() > 0) {
    map.put("pjtNo", pjtNo);
  }
  if (pjtName != null && pjtName.length() > 0) {
    map.put("pjtName", pjtName);
  }
  if (pjtState != null && pjtState.length() > 0 ) {
    map.put("pjtState", pjtState);
  }
  if (pjtType != null && pjtType.length() > 0) {
    map.put("pjtType", pjtType);
  }
  if (planStartStartDate != null && planStartStartDate.length() > 0) {
    map.put("planStartStartDate", planStartStartDate);
  }
  if (planStartEndDate != null && planStartEndDate.length() > 0) {
    map.put("planStartEndDate", planStartEndDate);
  }
  if (planEndStartDate != null && planEndStartDate.length() > 0) {
    map.put("planEndStartDate", planEndStartDate);
  }
  if (planEndEndDate != null && planEndEndDate.length() > 0) {
    map.put("planEndEndDate", planEndEndDate);
  }
  if (pjtModel != null && pjtModel.length() > 0) {
    map.put("pjtModel", pjtModel);
  }
  if (pjtDivision != null && pjtDivision.length() > 0) {
    map.put("pjtDivision", pjtDivision);
  }
  if (pjtLevel != null && pjtLevel.length() > 0) {
    map.put("pjtLevel", pjtLevel);
  }
  if (pjtProduct != null && pjtProduct.length() > 0) {
    map.put("pjtProduct", pjtProduct);
  }
  if (pjtCustomer != null && pjtCustomer.length() > 0) {
    map.put("pjtCustomer", pjtCustomer);
  }
  if (pjtDevCompany != null && pjtDevCompany.length() > 0) {
    map.put("pjtDevCompany", pjtDevCompany);
  }
  if (pjtMakeCompany != null && pjtMakeCompany.length() > 0) {
    map.put("pjtMakeCompany", pjtMakeCompany);
  }

  if(setPm != null && setPm.length()>0){
    map.put("setPm", setPm);
  }

  int psize = 15;
  int cpage = 1;
  int total = 0;
  int pageCount = 10;

  //page
  String sessionidstring = request.getParameter("sessionid");
  if (sessionidstring == null)
    sessionidstring = "0";
  long sessionid = Long.parseLong(sessionidstring);
  String pagestring = request.getParameter("tpage");
  if (pagestring != null && pagestring.length() > 0) {
    cpage = Integer.parseInt(pagestring);
  }

  PagingQueryResult result = null;
  if (sessionid <= 0) {
    result = SearchPagingProjectHelper.openPagingSession(map, 0, psize);
  } else {
    result = PagingSessionHelper.fetchPagingSession((cpage - 1)  * psize, psize, sessionid);
  }

  if (result != null) {
    total = result.getTotalSize();
    sessionid = result.getSessionId();
  }


%>

<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03069") %><%--프로젝트 목록--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<script language=JavaScript>
<!--
    function viewProject(oid) {
      //oid : Program OID
      var url = "/plm/jsp/project/chart/ProjectChart.jsp?oid="+oid;
      getOpenWindow(url, "viewProjectPopUp", "1100", "700");
    }


    function gotoPage(p) {
      document.forms[0].command.value='search';
      document.forms[0].sessionid.value='<%=sessionid%>';
      document.forms[0].tpage.value=p;
      document.forms[0].submit();
    }

    function search() {
      document.forms[0].command.value='search';
      document.forms[0].sessionid.value ="0";
      document.forms[0].tpage.value = "";

      document.forms[0].method = "post";
      document.forms[0].action = "/plm/jsp/project/chart/ProjectMainChart2.jsp";
      document.forms[0].submit();
    }


    function checkAll() {
      form = document.forms[0];
      if(form.poid) {
        var chkLen = form.poid.length;
        if(chkLen) {
          for(var i = 0; i < chkLen; i++) {
            form.poid[i].checked = form.chkAll.checked;
          }
        }else{
          form.poid.checked = form.chkAll.checked;
        }
      }
    }

    function checkedCheck() {
      form = document.forms[0];
      if(form.poid) {
        var chkLen = form.poid.length;
        if(chkLen) {
          for(var i = 0; i < chkLen; i++) {
            if(form.poid[i].checked == true) {
              return true;
            }
          }
        }else{
          if(form.poid.checked == true) {
            return true;
          }
        }
      }
      return false;
    }

    function selectProject() {

      var sresult = new Array();
      if(document.forms[0].poid==null) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01812") %><%--선택된 프로젝트가 없습니다--%>");
        return;
      }
      var count = 0;
      if(document.forms[0].poid.length==null) {
        if(document.forms[0].poid.checked) {
          sresult[count++] = document.forms[0].poid.value;
        } else {
          alert("<%=messageService.getString("e3ps.message.ket_message", "01812") %><%--선택된 프로젝트가 없습니다--%>");
          return;
        }
      }

      for(var i=0; i< document.forms[0].poid.length; i++) {
        if(document.forms[0].poid[i].checked) {
          sresult[count++] = document.forms[0].poid[i].value;
        }
      }
      if(sresult.length==0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01812") %><%--선택된 프로젝트가 없습니다--%>");
        return;
      }

      <%if("one".equals(mode)){%>
        opener.document.forms[0].projoid.value=sresult[0];
        opener.document.forms[0].submit();
        self.close();
      <%}else if("oneSelect".equals(mode)){%>
      resultArr = getCheckedData();
      opener.addProject(resultArr);
      self.close();
      <%}else{%>
      var addparts = "";
      for(var i=0; i< sresult.length; i++){
        addparts += "<input type=hidden name=projoid value='"+sresult[i]+"'>";
      }
      opener.document.all.addMPart.innerHTML = addparts;
      <%} %>
      <%if(!"oneSelect".equals(mode)){%>
      opener.document.forms[0].submit();
      <%} %>
    }

    function getCheckedData() {
      form = document.forms[0];
      if(form.poid == null) {
        //alert("검색 결과가 없습니다.");
        return;
      }

      var arr = new Array();
      var idx = 0;
      var chkLen = form.poid.length;
      if(chkLen) {
        for(var i = 0; i < chkLen; i++) {
          if(form.poid[i].checked == true) {
            rArr = new Array();
            rArr[0] = form.poid[i].value;
            rArr[1] = form.poid[i].pjtNo;
            rArr[2] = form.poid[i].pjtName;
            rArr[3] = form.poid[i].pjtProduct;
            rArr[4] = form.poid[i].pjtFabName;
            arr[idx++] = rArr;
          }
        }
      }else{
        if(form.poid.checked == true) {
          rArr = new Array();
          rArr[0] = form.poid.value;
          rArr[1] = form.poid.pjtNo;
          rArr[2] = form.poid.pjtName;
          rArr[3] = form.poid.pjtProduct;
          rArr[4] = form.poid.pjtFabName;
          arr[idx++] = rArr;
        }
      }
      return arr;
    }

function viewProject(oid) {
  //oid : Program OID
  var url = "/plm/jsp/project/chart/ProjectChart2.jsp?oid="+oid;
  getOpenWindow(url, "", "1100", "700");
}


function viewProjectIssue(oid) {

  var url = "/plm/jsp/project/chart/ProjectIssueChart2.jsp?oid="+oid;
  getOpenWindow(url, "", "800", "600");
}



function onKeyPress() {
  var keycode;
  if (window.event) {
    keycode = window.event.keyCode;
  }else{
    return true;
  }

  if (keycode == 13) {    //엔터키를 눌렀을때
    search();
    return false
  }
  return true
}
document.onkeypress = onKeyPress;

function selectUser(target){
  var url="/plm/jsp/groupware/company/selectPeopleFrm.jsp?mode=o&function=addUserToTarget&target="+target;
  openOtherName(url,"process","700","410","status=1,scrollbars=no,resizable=no");
}

function clearUser(str){
  var cUser = document.getElementById(str);
  var cTempUser = document.getElementById("temp"+str);
  cUser.value="";
  cTempUser.value="";
}

//-->
</script>

<style type="text/css">
<!--
body {
  background-image: url(/plm/portal/images/img_default/background2.gif);
  background-repeat:repeat-x;
  background-color: #ffffff;
  margin-top: 24px;
  margin-left:15px;
  margin-right:10px;

  overflow-x:hidden;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;
}
-->
</style>

<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<!-- Hidden Value -->


<input type="hidden" name="pjtType" value="<%=pjtType%>">
<input type='hidden' name="command"  value="<%=command%>">
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>

<input type='hidden' name='mode' value="<%=mode%>">
<!-- //Hidden Value -->

<%
  String tempTitle = "";
  if("myworkProject".equals(command)) {
    tempTitle = "MY ";
  }
%>
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td valign="top" style="padding:0px 0px 0px 0px">
        <%  if (!command.equals("selectdoc")) {  %>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
          <tr>
          <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=tmpTitle%> <%=messageService.getString("e3ps.message.ket_message", "03124") %><%--프로젝트현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%>  &gt; <%=tmpTitle%> <%=messageService.getString("e3ps.message.ket_message", "03124") %><%--프로젝트현황--%></td>
          <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
          </tr>
        </table>
        <%  }  %>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td>&nbsp;</td>
            <td align="right">
              <a href="javascript:search();">
              <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
              </a>
              <%  if(command.equals("select")) {  %>
              &nbsp;&nbsp;
              <a href="javascript:selectProject();">
              <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
              </a>
              &nbsp;&nbsp;
              <a href="javascript:history.back();">
                            <img src="/plm/portal/images/img_default/button/board_btn_back.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01309") %><%--뒤로--%>' width="62" height="20" border="0">
              </a>
              &nbsp;&nbsp;
              <a href="javascript:self.close();">
                            <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' width="62" height="20" border="0">
              </a>
              <%  }  %>
            </td>
          </tr>
        </table>
        <%  if (!command.equals("selectdoc")) {  %>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td  class="tab_btm1"></td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <col width='10%'><col width='10%'>
          <col width='10%'><col width='10%'>
          <col width='10%'><col width='10%'>
          <col width='10%'><col width='10%'>
          <col width='10%'><col width='10%'>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
          <td class="tdwhiteL">
            <input name="pjtName" value="<%=(request.getParameter("pjtName")==null)?"":request.getParameter("pjtName")%>"  class="txt_field" style="width:95%;text-transform:'uppercase'" />
          </td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
          <td class="tdwhiteL">
            <select name="modelcode" style="width:95%" onChange="javascript:search();">
              <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
              <%
              Vector tMap = NumberCodeHelper.manager.getNumberCodeLevel("MODELCODE", "parent");
              for(int i = 0; i < tMap.size(); i++) {
                NumberCode tNum = (NumberCode)tMap.get(i);
                if(pjtModel==null){pjtModel="";}
              %>
                <option value="<%=tNum.getCode()%>"
                <%if(pjtModel.equals(tNum.getCode())){out.print("selected");} %>
                ><%=tNum.getName()%></option>
              <%
              }
              %>
            </select>
          </td>
          <td class="tdblueM">PM</td>
          <td class="tdwhiteL">
            <input TYPE="hidden" name="setPm" value="">
            <input name="tempsetPm" class="txt_field" size="12" value="" engnum="engnum"  readonly onclick="javascript:selectUser('setPm');"/>
            <input type="button" class="p_select" onclick="javascript:selectUser('setPm');"/>
            <a href="JavaScript:clearUser('setPm')"><img src="/plm/portal/images/img_common/x.gif" border=0></a>
          </td>

          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td class="tdwhiteL0">
            <select name="pjtState" style="width:95%" onChange="javascript:search();">
                            <OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></OPTION>
              <%  //String cState = request.getParameter("pjtState");
                //if(cState == null)
                //  cState = "";

                if(true) {//if(CommonUtil.isAdmin()) {
                  try {
                    LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_Project",WCUtil.getWTContainerRef());
                    Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
                    PhaseTemplate pt = null;
                    wt.lifecycle.State lcState = null;
                    for (int i = 0; i < states.size(); i++) {
                      pt = (PhaseTemplate) states.get(i);
                      lcState = pt.getPhaseState();
                      if(lcState.toString().equals("PROGRESS") || lcState.toString().equals("PLANWORK") || lcState.toString().equals("COMPLETED")){
              %>
                      <OPTION VALUE="<%=lcState.toString()%>" <%if(pjtState.equals(lcState.toString())){%>selected<%}%>><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
              <%        }
                    }

                  }
                  catch(Exception e) {
                      Kogger.error(e);
                  }
                }
              %>
            </select>

          </td>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <%  }  %>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td class="space5"> </td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="tab_btm1"></td>
          </tr>
        </table>

        <!-- NEW Start -->
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <%  if (command.equals("select")|| command.equals("selectdoc") ) {  %>
          <COL width="3%">
          <%  }  %>

          <COL width="10%"><COL width="25%"><COL width="20%"><COL width="10%">
          <COL width="8%"><COL width="10%"><COL width="10%"><COL width="7%">

          <tr>
            <%  if (command.equals("select") || command.equals("selectdoc")) {  %>
              <%  if (mode.equals("multi") || mode.equals("one") || mode.equals("oneSelect")) {  %>
              <td class="tdblueM"> <input name="chkAll" type="checkbox" class="Checkbox" onclick="JavaScript:checkAll();"></td>
              <%  } else {  %>
              <td class="tdblueM">&nbsp;</td>
              <%  }  %>
            <%  }  %>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00398") %><%--PROJECT NO(PROJECT 명)--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02729") %><%--진행단계--%></td>
            <td class="tdblueM">PM</td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02296") %><%--이슈--%></td>
          </tr>
          <%
            int listCount = result.getTotalSize();
            int count = result.getTotalSize();
            int sortcount = 0;
            int countAll = count;

            if(cpage > 1)
              sortcount = (15 * cpage) - 15;

            if(cpage > 1 &&  (countAll-sortcount) > 0)
              countAll = count - sortcount;
            boolean checkOut = false;
            while (result.hasMoreElements()) {
              Object[] obj = (Object[]) result.nextElement();
              JELProjectData data = new JELProjectData((JELProject) obj[0]);
              JELProject jelproject = data.jelProject;
              checkOut =jelproject.isCheckOut();


              QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(jelproject);

              int issueCount = pjtResult.size();

          %>
          <tr>
            <%  if (command.equals("select") || command.equals("selectdoc")) {  %>
              <%  if (mode.equals("multi") || mode.equals("one") || mode.equals("oneSelect")) {  %>
              <td class="tdblueM"><input name="poid" type="checkbox" value="<%=data.jelPjtOID %>" pjtNo="<%=data.pjtNo %>" pjtName="<%=data.pjtName %>" class="Checkbox"></td>
              <%  } else {  %>
              <td class="tdblueM">&nbsp;</td>
              <%  }  %>
            <%  }  %>
            <td class="tdwhiteL">&nbsp;<%=(data.pjtModelCodeName==null)?"":data.pjtModelCodeName%></td>
            <td class="tdwhiteL">&nbsp;<font color="#0000FF">
            <a href="javascript:viewProject('<%=CommonUtil.getOIDString(jelproject) %>')"><B><%=data.pjtNo%> (<%=data.pjtName%>)</B></a>
            </font>

            </td>
            <td class="tdwhiteL">
              <%=data.getStateStr(data.stateKorea)%><font color="red"><%=(checkOut == true)?messageService.getString("e3ps.message.ket_message", "00120")/*CheckOut됨*/ : ""%></font>
            </td>
            <td class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(data.currentTaskName)%></td>

            <td class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(data.pjtPmName)%></td>
            <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(data.pjtPlanStartDate, "d")%></td>
            <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(data.pjtPlanEndDate, "d")%></td>
            <td class="tdwhiteL">&nbsp;
              <a href="javascript:viewProjectIssue('<%=CommonUtil.getOIDString(jelproject) %>')"><B><%=issueCount==0?0:issueCount %></B></a>
            </td>

          </tr>
          <% } %>
          <%  if (result.getTotalSize() == 0) {  %>
          <tr>
            <td class='tdwhiteM0' align='center' colspan='8'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
          </tr>
          <%  }  %>
        </table>

        <!-- 리스트 시작 //-->
        <%
          int ksize = total / psize;
          int x = total % psize;
          if (x > 0)
            ksize++;
          int temp = cpage / pageCount;
          if ((cpage % pageCount) > 0)
            temp++;
          int start = (temp - 1) * pageCount + 1;

          int end = start + pageCount - 1;
          if (end > ksize) {
            end = ksize;
          }
        %>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
                        <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
            <td>
              <table border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40" align="center">
                    <%  if (start > 1) {  %>
                                        <a href="JavaScript:gotoPage(1)" class="small"><%=messageService.getString("e3ps.message.ket_message", "02792") %><%--처음--%></a>
                    <%  }  %>
                  </td>
                  <td width="1" bgcolor="#dddddd"></td>
                  <%  if (start > 1) {  %>
                  <td width="60" class="quick" align="center"><a
                                        href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a>
                  </td>
                  <td width="1" bgcolor="#dddddd"></td>
                  <%  }  %>
                  <%  for (int i = start; i <= end; i++) {  %>
                  <td style="padding:2 8 0 7;cursor:hand"  onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
                    <%  if (i == cpage) {  %>
                      <b><%=i%>
                    <%  } else {  %>
                      <a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a>
                    <%  }  %>
                  </td>
                  <%  }  %>
                  <%  if (end < ksize) {  %>
                  <td width="1" bgcolor="#dddddd"></td>
                  <td width="60" align="center">
                    <a href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "01186") %><%--다음--%></a>
                  </td>
                  <%  }  %>
                  <td width="1" bgcolor="#dddddd"></td>
                  <td width="45" align="center">
                    <%  if (end < ksize) {  %>
                                        <a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a>
                    <%  }  %>
                  </td>
                </tr>
              </table>
            </td>
                        <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
</body>
</html>

<%!
  private boolean isProjectUser(WTUser wtuser, int memberType) {
    try {
      QuerySpec qs = new QuerySpec();
      Class peopleProjectLinkClass = ProjectMemberLink.class;
      int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

      qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType), peopleProjectLinkClassPos);
      qs.appendAnd();
      long oidValue = CommonUtil.getOIDLongValue(wtuser);
      qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
      QueryResult queryresult = PersistenceHelper.manager.find(qs);
      if (queryresult != null && queryresult.size() > 0) {
        return true;
      }
    } catch (QueryException e) {
	Kogger.error(e);
    } catch (WTException e) {
	Kogger.error(e);
    }
    return false;
  }
%>
