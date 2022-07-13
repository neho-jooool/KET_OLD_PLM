<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@include file="/jsp/common/context.jsp"%>

<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.*"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.JELProjectData"%>
<%@page import="e3ps.project.beans.ProjectIssueHelper"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectManagerData"%>
<%@page import="java.util.Vector"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.beans.ProjectStateFlag"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="e3ps.common.web.QueryBroker"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="e3ps.common.web.PageQueryBroker"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="wt.fc.PersistenceHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
String command = request.getParameter("command");
String oemTypeValue = request.getParameter("oemType");
String pmName = request.getParameter("pmName");
String setPm = request.getParameter("setPm");
String tempsetPm = request.getParameter("tempsetPm");
String oemtypecode = request.getParameter("oemtypecode");
String modelcode = request.getParameter("modelcode");

OEMProjectType searOem = null;
if(pmName == null){
  pmName = "";
}
if(oemtypecode == null){
  oemtypecode = "";
}
if(command == null){
  command = "";
}
if(setPm == null){
  setPm = "";
}
if(tempsetPm == null){
  tempsetPm = "";
}


String modelOid = "";
if(modelcode == null || modelcode.length() == 0){
  modelcode = "";
}else{
  NumberCode code = NumberCodeHelper.manager.getNumberCode("MODELCODE", modelcode);
  modelOid = CommonUtil.getOIDString(code);
}

if(oemTypeValue == null){
  oemTypeValue = "";
}else{
  searOem = (OEMProjectType)CommonUtil.getObject(oemTypeValue);
}

PageQueryBroker broker  = null;
QueryResult result = null;
QuerySpec qs = new QuerySpec();

if(command.equals("search")){

  Class target = ProjectManager.class;
  int idx_target = qs.addClassList(target, true);


  if(pmName.length() > 0 ){
    if(qs.getConditionCount() > 0)
      qs.appendAnd();
    //StringSearch stringsearch = new StringSearch("name");
    //stringsearch.setValue("%"+pmName.trim()+"%");
    //qs.appendWhere(stringsearch.getSearchCondition(pm),new int[]{pm_idx});

    qs.appendWhere(new SearchCondition(target,"name", SearchCondition.LIKE,  "%"+pmName.toUpperCase()+"%"),new int[] {idx_target});

  }

  if(searOem!= null){
    if(qs.getConditionCount() > 0)
      qs.appendAnd();
    //StringSearch stringsearch = new StringSearch("name");
    //stringsearch.setValue("%"+pmName.trim()+"%");
    //qs.appendWhere(stringsearch.getSearchCondition(pm),new int[]{pm_idx});

    qs.appendWhere(new SearchCondition(target,"name", SearchCondition.LIKE,  "%"+searOem.getName()+"%"),new int[] {idx_target});
  }


  if(setPm.length() > 0 ){
    if(qs.getConditionCount() > 0)
      qs.appendAnd();
    qs.appendWhere(new SearchCondition(target,"pm.key.id", SearchCondition.EQUAL,  CommonUtil
        .getOIDLongValue(setPm)),new int[] {idx_target});
  }

  if(modelcode.length() > 0 ){
    if(qs.getConditionCount() > 0)
      qs.appendAnd();
    qs.appendWhere(new SearchCondition(target,"modelReference.key.id", SearchCondition.EQUAL,  CommonUtil
        .getOIDLongValue(modelOid)),new int[] {idx_target});
  }



  SearchUtil.setOrderBy(qs, ProjectManager.class, idx_target, "sopDate", "sopDate", false);
  broker = new PageQueryBroker(request, qs);

  result = broker.search();

}else{
  Class target = ProjectManager.class;
  int idx_target = qs.addClassList(target, true);

  if(qs.getConditionCount() > 0)
    qs.appendAnd();
  qs.appendWhere(new SearchCondition(target,"name", SearchCondition.EQUAL, "검색된 항목 없음"),new int[] {idx_target});

  broker = new PageQueryBroker(request, qs);
  result = broker.search();
}

%>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03042") %><%--프로그램 진행현황 목록--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript>

function viewProgram(oid) {
  //oid : Program OID
  var url = "/plm/jsp/project/ViewProgram.jsp?oid="+oid+"&popup=popup";
  getOpenWindow(url, "viewProgramPopup", "800", "600");
}

function viewDepartment(oid) {
  //oid : Program OID
  var url = "/plm/jsp/project/chart/ProgramDepartmentChart.jsp?oid="+oid;
  getOpenWindow(url, "viewDepartmentPopup", "800", "600");
}

function viewDelayTask(oid) {
  //oid : Program OID
  var url = "/plm/jsp/project/chart/ProjectDelayTaskMainChart.jsp?oid="+oid;
  getOpenWindow(url, "", "800", "600");
}

function viewProject(oid) {
  //oid : Program OID
  var url = "/plm/jsp/project/chart/ProjectChart.jsp?oid="+oid;
  getOpenWindow(url, "", "1100", "700");
}

function viewProjectIssue(oid) {
  //parent.document.location.href = "/plm/jsp/project/chart/ProjectIssueMainChart.jsp?oid="+oid;
  //oid : Program OID
  var url = "/plm/jsp/project/chart/ProjectIssueMainChart.jsp?oid="+oid;
  getOpenWindow(url, "", "800", "600");
}

function search() {
  document.ProjectMainChart.command.value = "search";
  document.ProjectMainChart.method = "post";
  document.ProjectMainChart.action = "/plm/jsp/project/chart/ProjectMainChart.jsp";
  document.ProjectMainChart.sessionid.value ="0";
  document.ProjectMainChart.tpage.value = "";
  document.ProjectMainChart.submit();
}

  function addProcess(type, depth) {
    var form = document.ProjectMainChart;

    var tmpType = "";
    if(type==("divisioncode")) {
      tmpType = "DIVISIONCODE";
    } else if(type==("levelcode")) {
      tmpType = "LEVELCODE";
    } else if(type==("productcode")) {
      tmpType = "PRODUCTCODE";
    } else if(type==("customercode")) {
      tmpType = "CUSTOMERCODE";
    } else if(type==("devcompanycode")) {
      tmpType = "DEVCOMPANYCODE";
    } else if(type==("makecompanycode")) {
      tmpType = "MAKECOMPANYCODE";
    } else if(type==("modelcode")) {
      tmpType = "MODELCODE";
    }else if(type==("oemtypecode")) {
      tmpType = "OEMTYPECODE";
    }

    var mode = "one";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=3&selectedDepth="+depth+"&codetype="+tmpType+"&command=select&mode="+mode;
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
      return;
    }
    acceptProcess(returnValue, type);
  }
  function acceptProcess(arrObj, type){
    var tmpType = "";
    var tmpType1 = "";

    if(type==("divisioncode")) {
      tmpType = "divisioncodetable";
      tmpType1 = "divisioncodeoid";
    } else if(type==("levelcode")) {
      tmpType = "levelcodetable";
      tmpType1 = "levelcodeoid";
    } else if(type==("productcode")) {
      tmpType = "productcodetable";
      tmpType1 = "productcodeoid";
    } else if(type==("customercode")) {
      tmpType = "customercodetable";
      tmpType1 = "customercodeoid";
    } else if(type==("devcompanycode")) {
      tmpType = "devcompanycodetable";
      tmpType1 = "devcompanycodeoid";
    } else if(type==("makecompanycode")) {
      tmpType = "makecompanycodetable";
      tmpType1 = "makecompanycodeoid";
    } else if(type==("modelcode")) {
      tmpType = "modelcodetable";
      tmpType1 = "modelcodeoid";
    } else if(type == ("oemtypecode")){
      tmpType = "OEMTYPECODE";
    }

    var subArr;
    var form = document.ProjectMainChart;

      for(var i = 0; i < arrObj.length; i++) {
        subArr = arrObj[i];
        if(type==("divisioncode")) {
          form.divisioncode.value = subArr[1];
          form.divisioncode.name =subArr[2];
        } else if(type==("levelcode")) {
          form.levelcode.value = subArr[1];
          form.levelcode.name =subArr[2];
        } else if(type==("productcode")) {
          form.productcode.value =subArr[1];
          form.productcode.name =subArr[2];
        } else if(type==("customercode")) {
          form.customercode.value =subArr[1];
          form.customercode.name =subArr[2];
        } else if(type==("devcompanycode")) {
          form.devcompanycode.value =subArr[1];
          form.devcompanycode.name =subArr[2];
        } else if(type==("makecompanycode")) {
          form.makecompanycode.value =subArr[1];
          form.makecompanycode.name =subArr[2];
        } else if(type==("modelcode")) {
          form.modelcode.value =subArr[1];
          form.modelcode.name =subArr[2];
        }else if(type==("oemtypecode")) {
          form.oemtypecode.value =subArr[1];
          form.oemtypecode.name =subArr[2];
        }

      }
  }

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




//parent.document.location.href = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
</script>
</head>
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
<form name = ProjectMainChart >
<input type='hidden' name='command'>
<input type='hidden' name='searchYear'>
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td valign="top" style="padding:0px 0px 0px 0px">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
          <tr>
          <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03042") %><%--프로그램 진행현황 목록--%></td>
          <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
          </tr>
        </table>
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
          <col width='10%'><col width='10%'><col width='10%'><col width='10%'><col width='10%'><col width='20%'><col width='10%'><col width='15%'><col width='5%'>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
          <td class="tdwhiteL"><input type='text' name="pmName" style="width=98%" value="<%=pmName%>"></td>

          <td class="tdblueL">OEM</td>
          <td class="tdwhiteL">
          <select name="oemType" >
            <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
<%
            Vector oemvt = new Vector();
            OEMProjectType oemRoot = null;
            QuerySpec oemqs = new QuerySpec(OEMProjectType.class);
            oemqs.appendWhere(new SearchCondition(OEMProjectType.class, "path", "=", "ROOT"), new int[] { 0 });
            QueryResult oemqr = PersistenceHelper.manager.find(oemqs);
            OEMProjectType oemObject = null;
            if(oemqr.size()>0){
              while (oemqr.hasMoreElements()) {
                oemRoot = (OEMProjectType) oemqr.nextElement();
              }
              QuerySpec subqs = OEMTypeHelper.getCodeQuerySpec(oemRoot);
              QueryResult subqr = PersistenceHelper.manager.find(subqs);

              Object obj[] = null;
              while (subqr.hasMoreElements()){
                obj = (Object[])subqr.nextElement();
                oemObject = (OEMProjectType)obj[0];


%>
            <option style="background-color:#EEE8AA;"  value="<%=CommonUtil.getOIDString(oemObject) %>"
            <%
              if(oemTypeValue.equals(CommonUtil.getOIDString(oemObject))){
                out.println("selected");
              }
            %>
            ><%=oemObject.getName() %></option>
<%
              }
            }
%>
          </select>
          </td>

          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
          <td class="tdwhiteL">
            <select name="modelcode" style="width:80%" >
              <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
              <%
              Vector tMap = NumberCodeHelper.manager.getNumberCodeLevel("MODELCODE", "child");
              for(int i = 0; i < tMap.size(); i++) {
                NumberCode tNum = (NumberCode)tMap.get(i);

              %>
                <option value="<%=tNum.getCode()%>"
                <%if(modelcode.equals(tNum.getCode())){out.print("selected");} %>
                ><%=tNum.getName()%></option>
              <%
              }
              %>
            </select>&nbsp;&nbsp;
            <!-- input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onClick="javascript:addProcess('modelcode', 2);" class="s_font" -->
          </td>
          <td class="tdblueL">PM</td>
          <td class="tdwhiteL">
            <input TYPE="hidden" name="setPm" value="<%=setPm%>">
            <input name="tempsetPm" class="txt_field" size="12" value="<%=tempsetPm%>" engnum="engnum"  readonly onclick="javascript:selectUser('setPm');"/>
            <input type="button" class="p_select" onclick="javascript:selectUser('setPm');"/>
            <a href="JavaScript:clearUser('setPm')"><img src="/plm/portal/images/img_common/x.gif" border=0></a>
          </td>
          <td align="right">
            &nbsp;
            <a href="javascript:search();">
            <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
            </a>
          </td>

        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>

        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td class="space5"> </td>
          </tr>
        </table>



        <!-- Search -->
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

          <tr>
            <!--td class="tdblueM" rowspan=2>고객</td-->
            <td class="tdblueM" rowspan=2>NO</td>
                        <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
            <td class="tdblueM" colspan=5><%=messageService.getString("e3ps.message.ket_message", "00675") %><%--개발현황--%></td>
            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "01559") %><%--부서별 현황--%></td>
                        <!--td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td-->
                        <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02296") %><%--이슈--%></td>
            <!--td class="tdblueM" rowspan=2>계획일자</td-->
                        <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02755") %><%--착수일자--%></td>
            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00345") %><%--OEM 일정--%></td>
            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00469") %><%--SOP 일자--%></td>
          </tr>
          <tr>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01453") %><%--미시작--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834") %><%--총합--%></td>
          </tr>
          <%
          boolean checkOut = false;
          int i = 1;
          double tmpState = 0;

          while (result.hasMoreElements()) {
            int issueCount = 0;

            int noReadNo = 0;          //미시작 Count
            int readyNo = 0;          //진행 Count
            int doneNo = 0;            //완료 Count
            int delayNo = 0;          //지연 Count
            int delayIngNo = 0;          //지연 진행중 Count

            Timestamp pjtStartDate = null;    //프로젝트 계획 시작일자
            Timestamp pjtEndDate = null;    //프로젝트 계획 종료일자

            String viewState = "";

            Object[] obj = (Object[]) result.nextElement();
            ProjectManager manager = (ProjectManager) obj[0];
            ProjectManagerData managerData = new ProjectManagerData(manager);

            boolean checkDeley = false;
            Vector pjtVec = managerData.getJELProject(manager);
            for(int a = 0; a < pjtVec.size(); a++) {
              JELProject pjt = (JELProject) pjtVec.get(a);
              QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);

              issueCount += pjtResult.size();

              //JELProjectData pjtData = new JELProjectData(pjt);
              String state = pjt.getLifeCycleState().toString();
              //Lifecycle 상태 [프로젝트]
            //  if( state.equals("일정수립") ) {
              //  ++noReadNo;
            //  }

              if( state.equals("COMPLETED") ) {        //완료 Count
                ++doneNo;
              }
              //else if( state.equals("PROGRESS") ) {    //진행 Count
              else{
                //e3ps.project.beans.ProjectTreeNode root = (e3ps.project.beans.ProjectTreeNode)e3ps.project.beans.ProjectScheduleHelper.manager.getRoot(pjt, false);

                if( pjt.getPjtState() == ProjectStateFlag.PROJECT_STATE_DELAY ) {
                  ++delayNo;
                } else if(pjt.getPjtState() == ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS) {
                  ++readyNo;
                } else{
                  ++readyNo;
                }
              }
            }

              //진행상태
            if(pjtVec.size() == 0){
                            viewState = "<table><tr title='" + messageService.getString("e3ps.message.ket_message", "03105")/*프로젝트가 등록 되지 않았습니다*/ + "'>"
                          + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                          + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_green_bar.gif></td>"
                          + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                          + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                      + "</tr></table>";
            } else{
              if(pjtVec.size() == doneNo){
                viewState ="<table><tr title='" + messageService.getString("e3ps.message.ket_message", "02657")/*종료되었습니다*/ + "'>"
                            + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                            + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_green_bar.gif></td>"
                            + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                            + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                        + "</tr></table>";
              } else if(delayNo > 0) {
                                viewState = "<table><tr title='" + messageService.getString("e3ps.message.ket_message", "02703")/*지연*/ + "'>"
                      + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                        + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                        + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                        + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_red_bar.gif></td>"
                        + "</tr></table>";
              } else if(readyNo > 0) {
                                viewState = "<table><tr title='" + messageService.getString("e3ps.message.ket_message", "02726")/*진행*/ + "'>"
                  + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif></td>"
                      + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                      + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                      + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                      + "</tr></table>";
              } else if(delayIngNo>0){
                                viewState = "<table><tr title='" + messageService.getString("e3ps.message.ket_message", "02705")/*지연 진행중*/ + "'>"
                    + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                      + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                      + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_yellow_bar.gif></td>"
                      + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                      + "</tr></table>";

              }else {
                                viewState = "<table><tr title='" + messageService.getString("e3ps.message.ket_message", "02727")/*진행 예정*/ + "'>"
                    + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif></td>"
                    + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                        + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                        + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
                        + "</tr></table>";
              }
            }


          %>
          <tr>
            <!--td class="tdwhiteL">&nbsp;<%//=managerData.managerCustomer.getName() %></td-->
            <td class="tdwhiteL">&nbsp;<%=i %></td>
            <td class="tdwhiteL">&nbsp;
              <!--a href="javascript:viewProgram('<%//=CommonUtil.getOIDString(manager)%>')"><B><%//=managerData.managerName %></B></a-->
              <a href="javascript:viewProject('<%=CommonUtil.getOIDString(manager) %>')"><B><%=managerData.managerName %></B></a>
              <!--a href="javascript:parent.movePaage('/plm/portal/common/display_submenu.jsp', '/plm/jsp/project/chart/ProgramDepartmentChart.jsp?oid=<%//=CommonUtil.getOIDString(manager) %>');">
              <%//=managerData.managerName %>
              </a-->
            </td>
            <td class="tdwhiteM">&nbsp;<%=noReadNo %></td>
            <td class="tdwhiteM">&nbsp;<%=readyNo %></td>
            <td class="tdwhiteM">&nbsp;<%=doneNo %></td>
            <td class="tdwhiteM">&nbsp;<%=delayNo %></td>
            <td class="tdwhiteM">&nbsp;<%=managerData.jelProjectVec.size() %> </td>
            <td class="tdwhiteM"><%=viewState %>
              <a href="javascript:viewDepartment('<%=CommonUtil.getOIDString(manager)%>')"><B>[VIEW]</B></a>
            </td>
            <!--td class="tdwhiteM">
              <%//=viewState%><font color="red"><%//=(checkOut == true)?"CheckOut됨" : ""%></font>
              &nbsp;<a href="javascript:viewDelayTask('<%//=CommonUtil.getOIDString(manager)%>')">[VIEW]</a>
            </td-->
            <td class="tdwhiteM">&nbsp;
              <a href="javascript:viewProjectIssue('<%=CommonUtil.getOIDString(manager) %>')"><B><%=issueCount==0?0:issueCount %></B></a>
              <!--a href="javascript:parent.movePaage('/plm/portal/common/display_submenu.jsp', '/plm/jsp/project/chart/ProjectIssueMainChart.jsp?oid=<%//=CommonUtil.getOIDString(manager) %>');">
              <%//=issueCount==0?0:issueCount %></a-->
            </td>
            <!--td class="tdwhiteM">&nbsp;
              <a href="javascript:viewProject('<%//=CommonUtil.getOIDString(manager) %>')">
              <%//=DateUtil.getDateString(pjtStartDate, "d") %> ~ <%//=DateUtil.getDateString(pjtEndDate, "d") %>
              </a>
            </td-->
            <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(managerData.managerSOPStartDate, "d") %></td>
            <td class="tdwhiteM">&nbsp;
            <%
            //out.println("OEMType<<<< "+manager.getOemType().getName());

            OEMProjectType typeObject = (OEMProjectType)manager.getOemType();
            ArrayList oem_al = new ArrayList();
            oem_al = OEMTypeHelper.getOEMTypeTree(typeObject);
            for(int al = 0 ; al < oem_al.size() ; al++){
              OEMProjectType oemType = (OEMProjectType)CommonUtil.getObject((String)oem_al.get(al));
              //out.println("oemType>>>> " +oemType.getName());
              if(oemType.getOLevel() == 2) {
                OEMPlan op = OEMTypeHelper.getOEMPlan(manager, oemType);

                String os="";
                String oe="";
                String ds="";
                String de="";
                if(op != null){
                  os = (DateUtil.getDateString(op.getOemStartDate(), "d")==null)?"":DateUtil.getDateString(op.getOemStartDate(), "d");
                  oe = (DateUtil.getDateString(op.getOemEndDate(), "d")==null)?"":DateUtil.getDateString(op.getOemEndDate(), "d");
                  ds = (DateUtil.getDateString(op.getDsStartDate(), "d")==null)?"":DateUtil.getDateString(op.getDsStartDate(), "d");
                  de = (DateUtil.getDateString(op.getDsEndDate(), "d")==null)?"":DateUtil.getDateString(op.getDsEndDate(), "d");
                }

                //out.println("OEMStart>>>>> "+os);
                //out.println("OEMEnd>>>>> "+oe);
                //out.println("DSStart>>>>> "+ds);
                //out.println("DSEnd>>>>> "+de);

                int duration = DateUtil.getDaysFromTo(DateUtil.getDateString(managerData.managerSOPDate, "d"), DateUtil.getToDay());
                //out.println("duration>>>> " +duration);
                //out.println("ASDF<<<<< " + duration/30);

                //out.println("ToDay>>>> " + DateUtil.getDateString(DateUtil.getCurrentTimestamp(), "d"));

                //out.println("A>>> " +(DateUtil.getCurrentTimestamp().after(op.getDsStartDate())));
                //out.println("B>>> " +(DateUtil.getCurrentTimestamp().before(op.getDsEndDate())));
                String tmpColor = "";
                if( (DateUtil.getCurrentTimestamp().after(op.getDsStartDate())) && (DateUtil.getCurrentTimestamp().before(op.getDsEndDate())) ) {
                  tmpColor = "blue";
                } else {
                  tmpColor = "";
                }
            %>
            <%
                String img_str = "<img src='/plm/portal/images/img_default/button/icon_01.gif' width='7' height='11'>";
                if(tmpColor != "" && tmpColor.equals("blue")) {
            %>
              <B><font color="<%=tmpColor %>"><%=oemType.getName() %>[<%=duration/30 %>]</font></B><% if(al < oem_al.size()-1) out.println("&nbsp;&nbsp;"+img_str+"&nbsp;&nbsp;"); %>
            <%
                } else {
            %>
              <%=oemType.getName() %><% if(al < oem_al.size()-1) out.println("&nbsp;&nbsp;"+img_str+"&nbsp;&nbsp;"); %>
            <%
                }
              }
            }
            %>
            </td>
            <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(managerData.managerSOPDate, "d") %></td>
          </tr>
          <%
            i++;
          }
          %>

          <% if(result.size() == 0) { %>
          <tr>
            <td colspan="12" class=tdwhiteM0><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
          </tr>
          <% } %>
        </table>
      </td>
    </tr>
  </table>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" align=center valign=top>
    <tr height=35>
    <td>

    <%=broker.getHtml("ProjectMainChart")%>

    </td></tr>
  </table>
</form>
</body>
</html>
