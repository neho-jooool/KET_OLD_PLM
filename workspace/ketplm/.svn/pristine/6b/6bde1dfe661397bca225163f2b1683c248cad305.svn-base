<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%@page import="java.util.Locale"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<html>
<%

  HashMap map = new HashMap();
  String command = request.getParameter("command");
  String isSelect = request.getParameter("isSelect");
  String mode = request.getParameter("mode");
  String initType = request.getParameter("radio");
  String sortKey = ParamUtil.getStrParameter(request.getParameter("sortKey"));
    String dsc = ParamUtil.getStrParameter(request.getParameter("dsc"));
    if(dsc.length() == 0){
    dsc = "false";
  }
  String imgDsc = "(▲)";
  if(dsc.equals("false")){
    imgDsc = "(▼)";
  }
  if(sortKey.length() == 0){
    sortKey = "thePersistInfo.createStamp";
  }
  if(initType==null) initType="2";

  String CUSTOMEREVENT1 = StringUtil.checkNull(request.getParameter("CUSTOMEREVENT1"));
  String SUBCONTRACTOR1 = StringUtil.checkNull(request.getParameter("SUBCONTRACTOR1"));
  String carTypeInfo = StringUtil.checkNull(request.getParameter("carTypeInfo"));
  String partNo = request.getParameter("partNo");
  String partName = request.getParameter("partName");
  String dType = "";
  String pjtNo = "";
  String pjtName  = "";
  String pjtState = "";
  String setPm = "";
  String planStartStartDate = "";
  String planStartEndDate = "";
  String planEndStartDate = "";
  String planEndEndDate = "";
  String rdevUserDept = "";
  String rdevDeptOid = "";
  String pdevUserDept = "";
  String pdevDeptOid = "";
  String PRODUCTTYPE = "";
  String RANK = "";
  String ASSEMBLEDTYPE = "";
  String DEVELOPENTTYPE = "";
  String sap = StringUtil.checkNull(request.getParameter("sap"));

  if(initType.equals("1")){
    pjtNo = request.getParameter("pjtNo");
    dType = StringUtil.checkNull(request.getParameter("dType"));
    pjtName = request.getParameter("pjtName");
    pjtState = request.getParameter("pjtState");
    setPm = request.getParameter("setPm");
    planStartStartDate = StringUtil.checkNull(request.getParameter("planStartStartDate"));
    planStartEndDate = StringUtil.checkNull(request.getParameter("planStartEndDate"));
    planEndStartDate = StringUtil.checkNull(request.getParameter("planEndStartDate"));
    planEndEndDate = StringUtil.checkNull(request.getParameter("planEndEndDate"));
    rdevUserDept = StringUtil.checkNull(request.getParameter("rdevUserDept"));
    rdevDeptOid = StringUtil.checkNull(request.getParameter("rdevDeptOid"));
    PRODUCTTYPE = StringUtil.checkNull(request.getParameter("PRODUCTTYPE"));
    if(dType.equals("2")){
      PRODUCTTYPE = StringUtil.checkNull(request.getParameter("PRODUCTTYPE1"));
    }

    RANK = StringUtil.checkNull(request.getParameter("RANK"));
    if(dType.equals("2")){
      RANK = StringUtil.checkNull(request.getParameter("RANK1"));
    }
    ASSEMBLEDTYPE = StringUtil.checkNull(request.getParameter("ASSEMBLEDTYPE"));
    DEVELOPENTTYPE = StringUtil.checkNull(request.getParameter("DEVELOPENTTYPE"));
    CUSTOMEREVENT1 = StringUtil.checkNull(request.getParameter("CUSTOMEREVENT3"));
    SUBCONTRACTOR1 = StringUtil.checkNull(request.getParameter("SUBCONTRACTOR3"));
  } else if(initType.equals("2")) {
    pjtNo = request.getParameter("pjtNo1");
    dType = StringUtil.checkNull(request.getParameter("dType1"));
    pjtName = request.getParameter("pjtName1");
    pjtState = request.getParameter("pjtState1");
    setPm = request.getParameter("setPm1");
    planStartStartDate = StringUtil.checkNull(request.getParameter("planStartStartDate1"));
    planStartEndDate = StringUtil.checkNull(request.getParameter("planStartEndDate1"));
    planEndStartDate = StringUtil.checkNull(request.getParameter("planEndStartDate1"));
    planEndEndDate = StringUtil.checkNull(request.getParameter("planEndEndDate1"));
    PRODUCTTYPE = StringUtil.checkNull(request.getParameter("PRODUCTTYPE2"));

    if(dType.equals("2")){
      PRODUCTTYPE = StringUtil.checkNull(request.getParameter("PRODUCTTYPE3"));
    }


    RANK = StringUtil.checkNull(request.getParameter("RANK2"));
    if(dType.equals("2")){
      RANK = StringUtil.checkNull(request.getParameter("RANK3"));
    }
    ASSEMBLEDTYPE = StringUtil.checkNull(request.getParameter("ASSEMBLEDTYPE1"));
    DEVELOPENTTYPE = StringUtil.checkNull(request.getParameter("DEVELOPENTTYPE1"));
    pdevUserDept = StringUtil.checkNull(request.getParameter("pdevUserDept"));
    pdevDeptOid = StringUtil.checkNull(request.getParameter("pdevDeptOid"));

  } else if(initType.equals("3")) {

    String dieNo = StringUtil.checkNull(request.getParameter("dieNo"));
    String devUserDept = StringUtil.checkNull(request.getParameter("devUserDept"));
    String devDeptOid = StringUtil.checkNull(request.getParameter("devDeptOid"));
    String productpjtNo = StringUtil.checkNull(request.getParameter("productpjtNo"));
    String itemType = StringUtil.checkNull(request.getParameter("itemType"));
    String moldProductType = StringUtil.checkNull(request.getParameter("moldProductType"));
    String productPartNo = StringUtil.checkNull(request.getParameter("productPartNo"));
    String productName = StringUtil.checkNull(request.getParameter("productName"));
    String making = StringUtil.checkNull(request.getParameter("making"));
    String moldType = StringUtil.checkNull(request.getParameter("moldType"));
    String outsourcing = StringUtil.checkNull(request.getParameter("outsourcing"));
    String tempsetProductPm = StringUtil.checkNull(request.getParameter("tempsetProductPm"));
    String setProductPm = StringUtil.checkNull(request.getParameter("setProductPm"));
    String tempsetMoldCharger = StringUtil.checkNull(request.getParameter("tempsetMoldCharger"));
    String setMoldCharger = StringUtil.checkNull(request.getParameter("setMoldCharger"));
    String carTypeInfo2 = StringUtil.checkNull(request.getParameter("carTypeInfo2"));
    String DEVELOPENTTYPE2 = StringUtil.checkNull(request.getParameter("DEVELOPENTTYPE2"));

    map.put("dieNo", dieNo);
    map.put("devDeptOid", devDeptOid);
    map.put("productpjtNo", productpjtNo);
    map.put("itemType", itemType);
    map.put("moldProductType", moldProductType);
    map.put("productPartNo", productPartNo);
    map.put("productName", productName);
    map.put("making", making);
    map.put("moldType", moldType);
    map.put("outsourcing", outsourcing);
    map.put("tempsetProductPm", tempsetProductPm);
    map.put("setProductPm", setProductPm);
    map.put("tempsetMoldCharger", setProductPm);
    map.put("setMoldCharger", setMoldCharger);
    map.put("carTypeInfo2", carTypeInfo2);
    map.put("DEVELOPENTTYPE2", DEVELOPENTTYPE2);

    String moldPjtState = StringUtil.checkNull(request.getParameter("moldPjtState"));
    String moldRank = StringUtil.checkNull(request.getParameter("moldRank"));
    String planStartStartDate2 = StringUtil.checkNull(request.getParameter("planStartStartDate2"));
    String planStartEndDate2 = StringUtil.checkNull(request.getParameter("planStartEndDate2"));
    String planEndStartDate2 = StringUtil.checkNull(request.getParameter("planEndStartDate2"));
    String planEndEndDate2 = StringUtil.checkNull(request.getParameter("planEndEndDate2"));
    String tempsetMoldPm = StringUtil.checkNull(request.getParameter("tempsetMoldPm"));
    String setMoldPm = StringUtil.checkNull(request.getParameter("setMoldPm"));
    pjtState = moldPjtState;
    setPm = setMoldPm;
    planStartStartDate = planStartStartDate2;
    planStartEndDate = planStartEndDate2;
    planEndStartDate = planEndStartDate2;
    planEndEndDate = planEndEndDate2;
    RANK = moldRank;
  }
  //Kogger.debug("dType####################################################"+ dType);
  if(command == null)
    command = "";

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

  if(sortKey.length() > 0 ){
    map.put("sortKey", sortKey);
  }

  if(dsc.length() > 0 ){
    map.put("dsc", dsc);
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
  if (initType != null && initType.length() > 0) {
    map.put("pjtType", initType);
  }
  if (dType != null && dType.length() > 0) {
    map.put("dType", dType);
  }
  if (setPm != null && setPm.length() > 0 ){
    map.put("setPm", setPm);
  }

  if (RANK != null && RANK.length() > 0 ){
    map.put("RANK", RANK);
  }
  if (PRODUCTTYPE != null && PRODUCTTYPE.length() > 0 ){
    map.put("PRODUCTTYPE", PRODUCTTYPE);
  }
  if (CUSTOMEREVENT1 != null && CUSTOMEREVENT1.length() > 0 ){
    map.put("CUSTOMEREVENT1", CUSTOMEREVENT1);
  }
  if (SUBCONTRACTOR1 != null && SUBCONTRACTOR1.length() > 0 ){
    map.put("SUBCONTRACTOR1", SUBCONTRACTOR1);
  }
  if (ASSEMBLEDTYPE != null && ASSEMBLEDTYPE.length() > 0 ){
    map.put("ASSEMBLEDTYPE", ASSEMBLEDTYPE);
  }
  if (DEVELOPENTTYPE != null && DEVELOPENTTYPE.length() > 0 ){
    map.put("DEVELOPENTTYPE", DEVELOPENTTYPE);
  }
  if (carTypeInfo != null && carTypeInfo.length() > 0 ){
    map.put("carTypeInfo", carTypeInfo);
  }

  if (partNo != null && partNo.length() > 0) {
    map.put("partNo", partNo);
  }
  if (partName != null && partName.length() > 0) {
    map.put("partName", partName);
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
  if (pdevDeptOid != null && pdevDeptOid.length() > 0) {
    map.put("pdevDeptOid", pdevDeptOid);
  }
  if (rdevDeptOid != null && rdevDeptOid.length() > 0) {
    map.put("rdevDeptOid", rdevDeptOid);
  }

  if(sap != null && sap.length() > 0){
    map.put("sap", sap);
  }

  //out.println("=========="+sap);

  if("projectExcelDown".equals(command)){
    //Kogger.debug("projectExcelDown########################################################################################");
    map.put("command", "search");
    String fileName = new String("list".getBytes("euc-kr"), "8859_1");
    response.reset();

    response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
    response.setHeader("Content-Disposition","attachment; filename=" + "ProjectList.xls");
    ProjectHelper.excelDown(map, response.getOutputStream());
    return;

  }

  String perPageValue = request.getParameter("perPage");
  if(perPageValue == null){
    perPageValue = "10";
  }

  int psize = Integer.parseInt(perPageValue);
  int cpage = 1;
  int total = 0;
  int pageCount = 10;

  //page
  String sessionidstring = request.getParameter("sessionid");
  if (sessionidstring == null){
    sessionidstring = "0";
  }
  long sessionid = Long.parseLong(sessionidstring);
  String pagestring = request.getParameter("tpage");
  if (pagestring != null && pagestring.length() > 0) {
    cpage = Integer.parseInt(pagestring);
  }

  PagingQueryResult result = null;
  if (sessionid <= 0) {
    if(command.equals("")){
      result = null;
    }else{
      result = SearchPagingProjectHelper.openPagingSession(map, 0, psize);
    }
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
    function gotoPage(p) {
      document.forms[0].command.value='search';
      document.forms[0].sessionid.value='<%=sessionid%>';
      document.forms[0].tpage.value=p;
      document.forms[0].submit();
    }

    function viewProject(oid){
      parent.document.location.href = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
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

  function addProcess(type, depth) {
    var form = document.ListProgram;

    var tmpType = "";
    if(type==("divisioncode")) {
      tmpType = "DIVISIONCODE";
    }

    var mode = "one";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth="+depth+"&codetype="+tmpType+"&command=select&mode="+mode;
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
    }

    var subArr;
    var form = document.forms[0];

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

  function sorting(key){
    document.forms[0].action = "/plm/jsp/project/ProjectSearch.jsp?command=<%=command%>";
    document.forms[0].sessionid.value ="0";
    document.forms[0].command.value = "search";
    document.forms[0].method = "post";
    if(key == document.forms[0].sortKey.value){
      if(document.forms[0].dsc.value == "false"){

        document.forms[0].dsc.value = "true";

      }else{
        document.forms[0].dsc.value = "false";
      }
    }else{
      document.forms[0].dsc.value = "false";
    }

    document.forms[0].sortKey.value = key;
    document.forms[0].submit();
  }
    function requestPop(oid){
    var url = '/plm/jsp/dms/ViewDevRequestPop.jsp?oid='+oid;
    window.open(url,'의뢰서',"status=1, menu=no, width=830, height=800");
  }

    function onKeyPress() {
      var keycode;
      if (window.event) {
        keycode = window.event.keyCode;
      }else{
        return true;
      }

      if (keycode == 13) {    //엔터키를 눌렀을때
        dosubmit();
        return false
      }
      return true
    }
    document.onkeypress = onKeyPress;

//-->
</script>
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
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/processing.html"%>
<form>
<!-- Hidden Value -->

<input type="hidden" name="initType" value="<%=initType%>">
<input type='hidden' name="command"  value="<%=command%>">
<input type='hidden' name='sessionid' value="<%=sessionidstring %>">
<input type='hidden' name='tpage'>
<input type = "hidden" name = "sortKey" value="<%=sortKey%>">
<input type = "hidden" name = "dsc" value="<%=dsc%>">
<input type='hidden' name='mode' value="<%=mode%>">
<!-- //Hidden Value -->
<%//if(result!= null){ %>
        <table border="0" cellspacing="0" cellpadding="0" width="780">
          <tr>
            <td  class="tab_btm2"></td>
          </tr>
        </table>
        <!-- NEW Start -->
        <%
        if(initType.equals("1")){
        %>
        <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td width="50" class="tdblueM">No</td>
                <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02192") %><%--요청번호--%></td>
                <td width="80" class="tdblueM"><a href = "javaScript:sorting('<%=E3PSProject.PJT_NO%>');"><%=E3PSProject.PJT_NO.equals(sortKey) ? "<u>Project No</u>" : "Project No" %></a></td>
                <td width="120" class="tdblueM"><a href = "javaScript:sorting('<%=E3PSProject.PJT_NAME%>');"><%=E3PSProject.PJT_NAME.equals(sortKey) ? "<u>Project Name</u>" : "Project Name" %></a></td>
                <td width="80" class="tdblueM"><a href = "javaScript:sorting('planStartDate');"><%="planStartDate".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "02018") /*시작일*/+"</u>" : messageService.getString("e3ps.message.ket_message", "02018") /*시작일*/ %></a></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00828") %><%--고객--%></td>
                <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02136") %><%--영업담당--%></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00409") %><%--Project 현황--%></td>
              <td width="90" class="tdblueM0"><a href = "javaScript:sorting('state.state');"><%="state.state".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "01760")/*상태*/+"</u>" : messageService.getString("e3ps.message.ket_message", "01760")/*상태*/ %></a></td>
              </tr>
              <%
              int listCount = total - (psize*(cpage-1));
          if(listCount>0){
            boolean checkOut = false;
            while (result.hasMoreElements()) {
              Object[] obj = (Object[]) result.nextElement();
              E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
              E3PSProject project = data.e3psProject;
              checkOut = project.isCheckOut();

              String devROid = "";
              String devRNumber = "&nbsp;";
              try{
                if(project.getDevRequest() != null){
                  devROid = CommonUtil.getOIDString(project.getDevRequest());
                  devRNumber = project.getDevRequest().getNumber();
                }
              }catch(Exception e){
                 //project = (E3PSProject)ProjectHelper.manager.SynDevRequestCheck(project);
        	  Kogger.error(e);
              }


              %>

              <tr>
                <td class="tdwhiteM"><%=listCount-- %></td>
                <td class="tdwhiteM"><a href="#" onclick="javascript:requestPop('<%=devROid%>');"><%=devRNumber%></a></td>
                <td class="tdwhiteM"><a href="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=data.e3psPjtOID%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=data.e3psPjtOID%>');"><%=data.pjtNo==null?"":data.pjtNo%>&nbsp;</a></td>
                <td class="tdwhiteL" title="<%=data.pjtName==null?"":data.pjtName%>">
                <a href="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=data.e3psPjtOID%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=data.e3psPjtOID%>');">
                <div style="width:115;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr>
                <%=data.pjtName==null?"":data.pjtName%>&nbsp;</nobr></div></a>
                </td>
                <td class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanStartDate, "d")%>&nbsp;</td>
                <td class="tdwhiteM" title="<%=data.dependence%>"><div style="width:97;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                <nobr><%=data.dependence%>&nbsp;</nobr>
                </div></td>
                <td class="tdwhiteM"><%=data.salesName %>&nbsp;</td>
                <td class="tdwhiteM"><%=data.getStateStr(data.stateKorea, false)%></td>
              <td class="tdwhiteM"><%=data.stateKorea%></td>
              </tr>
              <%
              }
          }else{
              %>
                <tr>
                  <td class='tdwhiteM0' align='center' colspan='9'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>

              <%
          }
              %>
            </table>
            <%
            }else if(initType.equals("2")){
            %>
              <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                  <td width="50" class="tdblueM">NO</td>
                  <td width="70" class="tdblueM"><a href = "javaScript:sorting('<%=E3PSProject.PJT_NO%>');"><%=E3PSProject.PJT_NO.equals(sortKey) ? "<u>Project No</u>" : "Project No" %></a></td>
                  <td width="180" class="tdblueM"><a href = "javaScript:sorting('<%=E3PSProject.PJT_NAME%>');"><%=E3PSProject.PJT_NAME.equals(sortKey) ? "<u>Project Name</u>" : "Project Name" %></a></td>
                  <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00828") %><%--고객--%></td>
                  <td width="80" class="tdblueM"><%if("2".equals(dType)) {%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}else {%><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%><%}%></td>
                  <td width="90" class="tdblueM"><a href = "javaScript:sorting('planStartDate');"><%="planStartDate".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "02018") /*시작일*/+"</u>" : messageService.getString("e3ps.message.ket_message", "02018") /*시작일*/ %></a></td>
                  <td width="90" class="tdblueM"><a href = "javaScript:sorting('planEndDate');"><%="planEndDate".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "02179") /*완료일*/+"</u>" : messageService.getString("e3ps.message.ket_message", "02179") /*완료일*/ %></a></td>
                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00409") %><%--Project 현황--%></td>
                  <td width="90" class="tdblueM"><a href = "javaScript:sorting('state.state');"><%="state.state".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "01760")/*상태*/+"</u>" : messageService.getString("e3ps.message.ket_message", "01760")/*상태*/ %></a></td>
                  <td width="40" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01871") %><%--설변--%></td>

                </tr>
                <%
                int listCount = total - (psize*(cpage-1));
                String ecoCount = "";
            if(listCount>0){
              boolean checkOut = false;
              while (result.hasMoreElements()) {
                Object[] obj = (Object[]) result.nextElement();
                E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
                ProductProject proProject = (ProductProject)obj[0];
                E3PSProject project = data.e3psProject;

                checkOut = project.isCheckOut();
                String oemCar = "";
                int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
                    ecoCount = ""+ecoCountInt;
                /*
                OEMProjectType ot = null;
                try{
                  if(project.getOemType() != null){
                    ot = project.getOemType();
                    oemCar = project.getOemType().getName();
                  }
                }catch(Exception e){
                  project = ProjectHelper.manager.SynOEMTypeCheck(proProject);
                  e.printStackTrace();
                }
                */


                %>
                <tr>
                  <td width="50" class="tdwhiteM"><%=listCount-- %></td>
                  <td width="70" class="tdwhiteL"><a href="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=data.e3psPjtOID%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=data.e3psPjtOID%>');"><%=data.pjtNo==null?"":data.pjtNo%>&nbsp;</a></td>
                  <td width="180" class="tdwhiteL"title="<%=data.pjtName==null?"":data.pjtName%>"><div style="width:175;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr><a href="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=data.e3psPjtOID%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=data.e3psPjtOID%>');"><%=data.pjtName==null?"":data.pjtName%>&nbsp;</a></nobr></div></td>
                  <td width="90" class="tdwhiteM"title="<%=data.dependence==null?"":data.dependence %>"><div style="width:70;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr>&nbsp;<%=data.dependence %></nobr></div></td>
                  <td width="80" class="tdwhiteM"><%=data.representModel==null || data.representModel.equals("")?"&nbsp;":data.representModel %></td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanStartDate, "d")%>&nbsp;</td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanEndDate, "d") %>&nbsp;</td>
                  <td width="100" class="tdwhiteM"><%=data.getStateStr(data.stateKorea, false)%></td>
                  <td width="90" class="tdwhiteM"><%=data.stateKorea%></td>
                  <td width="40" class="tdwhiteM0"><%=ecoCount%></td>
                </tr>
                <%

            }
          }else{
              %>
                <tr>
                  <td class='tdwhiteM0' align='center' colspan='10'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>

              <%
          }
              %>
            </table>


            <%

            }else if(initType.equals("3")){
            %>
              <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                  <td width="30" class="tdblueM">No</td>
                  <td width="70" class="tdblueM"><a href = "javaScript:sorting('<%=E3PSProject.PJT_NO%>');"><%=E3PSProject.PJT_NO.equals(sortKey) ? "<u>Die No</u>" : "Die No" %></a></td>
                  <td width="90" class="tdblueM"><a href = "javaScript:sorting('<%="moldPartNo"%>');"><%="moldPartNo".equals(sortKey) ? "<u>Part No</u>" : "Part No" %></a></td>
                  <td width="130" class="tdblueM"><a href = "javaScript:sorting('<%="moldPartName"%>');"><%="moldPartName".equals(sortKey) ? "<u>Part Name</u>" : "Part Name" %></a></td>
                  <td width="90" class="tdblueM"><a href = "javaScript:sorting('planStartDate');"><%="planStartDate".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "02018") /*시작일*/+"</u>" : messageService.getString("e3ps.message.ket_message", "02018") /*시작일*/ %></a></td>
                  <td width="90" class="tdblueM"><a href = "javaScript:sorting('planEndDate');"><%="planEndDate".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "02179") /*완료일*/+"</u>" : messageService.getString("e3ps.message.ket_message", "02179") /*완료일*/ %></a></td>
                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00409") %><%--Project 현황--%></td>
                  <td width="60" class="tdblueM"><a href = "javaScript:sorting('state.state');"><%="state.state".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "01760")/*상태*/+"</u>" : messageService.getString("e3ps.message.ket_message", "01760")/*상태*/ %></a></td>
                  <td width="80" class="tdblueM"><a href = "javaScript:sorting('<%="making"%>');"><%="making".equals(sortKey) ? "<u>"+messageService.getString("e3ps.message.ket_message", "02532") /*제작구분*/+"</u>" : messageService.getString("e3ps.message.ket_message", "02532") /*제작구분*/ %></a></td>
                  <td width="40" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01871") %><%--설변--%></td>
                </tr>
                 <%
                int listCount = total - (psize*(cpage-1));
            boolean checkOut = false;
            String ecoCount = "";
            if(listCount>0){
              while (result.hasMoreElements()) {
                Object[] obj = (Object[]) result.nextElement();
                E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
                MoldProject project = (MoldProject)data.e3psProject;
                MoldItemInfo itemInfo = project.getMoldInfo();
                String itemPartNo = itemInfo.getPartNo();
                if(itemPartNo == null || itemPartNo.length() == 0){
                  itemPartNo = "&nbsp";
                }

                String itemPartName = itemInfo.getPartName();
                if(itemPartName == null || itemPartName.length() == 0){
                  itemPartName = "&nbsp";
                }

                ProductProject productProject = itemInfo.getProject();
                String productName = productProject.getPjtName();
                checkOut = project.isCheckOut();

                String making = itemInfo.getMaking();
                if(making == null || making.length() == 0){
                  making = "&nbsp";
                }
                int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
                    ecoCount = ""+ecoCountInt;

                %>
                <tr>
                  <td width="30" class="tdwhiteM"><%=listCount--%></td>
                  <td width="70" class="tdwhiteL"><a href="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=data.e3psPjtOID%>', '/plm/jsp/project/MoldProjectView_2.jsp?oid=<%=data.e3psPjtOID%>');"><%=data.pjtNo == null? "" : data.pjtNo%>&nbsp;</a></td>
                  <td width="90" class="tdwhiteL"><div style="width:85;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=itemPartNo%></nobr></div></td>
                  <td width="130" class="tdwhiteL"><div style="width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=itemPartName%></nobr></div></td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanStartDate, "d")%>&nbsp;</td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanEndDate, "d") %>&nbsp;</td>
                  <td width="100" class="tdwhiteM"><%=data.getStateStr(data.stateKorea, false)%></td>
                  <td width="60" class="tdwhiteM"><%=data.stateKorea%></td>
                  <td width="80" class="tdwhiteM"><%=making%></td>
                  <td width="40" class="tdwhiteM0"><%=ecoCount%></td>
                </tr>
               <%
                 }

            }else{
               %>
               <tr>
                  <td class='tdwhiteM0' align='center' colspan='9'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>
                <%
            }
                %>
              </table>



            <%
            }
            %>
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
    <table border="0" cellspacing="0" cellpadding="0" width="780">
          <tr>
            <td class="space10"></td>
          </tr>
        </table>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <tr>
        <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%>:<%=ksize%>)</span></td>
        <td>
        <table border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>

            <%
            if (1 < ksize) {
            %><td width="25" align="center"><a href="JavaScript:gotoPage(1)" class="small"><img src="/plm/portal/images/btn_arrow4.gif" style='border:0'></a></td>
            <%
            }else{
            %>
            <td width="25" align="center">&nbsp;</td>
            <%} %>
            <td width="1" bgcolor="#dddddd"></td>
            <%
            if (start > 1) {
            %>
            <td width="25" class="quick" align="center"><a
              href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow3.gif" style='border:0'></a></td>
            <td width="1" bgcolor="#dddddd"></td>
            <%
              }
              for (int i = start; i <= end; i++) {
            %>
            <td style="padding:2 8 0 7;cursor:hand"
              onMouseOver="this.style.background='#ECECEC'"
              OnMouseOut="this.style.background=''" class="nav_on">
            <%
            if (i == cpage) {
            %><b><font color=006699><%=i%></font></b>
            <%
            } else {
            %><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a>
            <%
            }
            %>
            </td>
            <%
              }
              if (end < ksize) {
            %>
            <td width="1" bgcolor="#dddddd"></td>
            <td width="25" align="center"><a
              href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow1.gif" style='border:0'></a></td>
            <%
            }
            %>
            <td width="1" bgcolor="#dddddd"></td>

            <%
            if (1 < ksize) {
            %><td width="25" align="center">
            <a href="JavaScript:gotoPage(<%=ksize%>)"
              class="small"><img src="/plm/portal/images/btn_arrow2.gif" style='border:0'></a></td>
            <%
            }else{
            %>
            <td width="25" align="center">&nbsp;</td>
            <%} %>
          </tr>
        </table>
        </td>
        <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
      </tr>
    </table>

<%//}else{ %>
<%//} %>
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

public void makeVector(ProjectTreeNode node, Vector vector){

  vector.add(node);
  for(int i = 0; i < node.getChildCount(); i++){
    makeVector((ProjectTreeNode)node.getChildAt(i), vector);
  }

}
%>
