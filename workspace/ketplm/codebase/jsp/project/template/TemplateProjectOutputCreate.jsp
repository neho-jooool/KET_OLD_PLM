<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.org.*,
        wt.fc.QueryResult,
        wt.fc.PersistenceHelper,
        wt.query.QuerySpec,
        wt.query.SearchCondition,
        java.util.*"%>
<%@page import="e3ps.project.*,
        e3ps.project.beans.*,
        e3ps.common.util.*" %>
        
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>        

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
  String oid = request.getParameter("oid");
  Object obj = CommonUtil.getObject(oid);
  TemplateProject project = null;
  TemplateTask task = null;

  if ( obj instanceof TemplateProject ) {
    project = (TemplateProject)obj;
  } else if ( obj instanceof TemplateTask ) {
    task = (TemplateTask)obj;
    project = task.getProject();
  }

  String command = StringUtil.checkNull(request.getParameter("command"));


  String name = "";
  String description = "";
  String role = "";
  String location = "";


  DocCodeType docType = null;
  if(location.length() == 0) {
    docType = DocCodeTypeHelper.getRoot();
  }
  else {
    docType = DocCodeTypeHelper.getDocCodeTypeToPath(location);
  }

  Kogger.debug("docType : " + docType + " docTypePath : " +docType.getPath());
  ArrayList fullPath = DocCodeTypeHelper.getDocTypeParentPath(docType);

  boolean isJELTask = false;
  if(task instanceof JELTask) {
    isJELTask = true;
  }




  if("create".equals(command)){
    name = StringUtil.checkNull(request.getParameter("name"));
    description = StringUtil.checkNull(request.getParameter("description"));
    location = StringUtil.checkNull(request.getParameter("Location"));
    role = StringUtil.checkNull(request.getParameter("Role"));


    Kogger.debug("name == > " + name);
    Kogger.debug("description == > " + description);
    //Kogger.debug("location == > " + location);
    Kogger.debug("role == > " + role);

    ProjectOutput output = ProjectOutput.newProjectOutput();
    output.setOutputName(name);
    output.setOutputDesc(description);
    //output.setOutputLocation(location);
    output.setOutputRole(role);
    output.setProject(project);

    if ( task != null ){ output.setTask(task);}

    output =(ProjectOutput)ProjectOutputHelper.manager.createProjectOutput(output);
  }



/////////////////////////////////////////////////////////////////////////////////////////



%>
<%@page import="e3ps.doc.DocCodeType"%>
<%@page import="e3ps.doc.beans.DocCodeTypeHelper"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01732") %><%--산출물 정의--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language=JavaScript>
<!--

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////문서분류 시작
  var selectLevelIndex = 0;

  var initIndex = 0;
  var targetSelectTD = "selectTD";


  function onChangeFolder(fObj) {
    setSelectTree(fObj);
  }

  function setSelectTree(fObj) {
    form = document.forms[0];

    selectLevelIndex = new Number(fObj.selectlevel);
    removeSelect(selectLevelIndex);

    if(fObj.value != '') {
      selectSearch(fObj.value);
    }
  }

  function selectSearch(svalue) {
    onProgressBar();

    var param = "command=select&oid=" + svalue;
    var url = "/plm/jsp/project/template/DocTypeAjaxAction.jsp?" + param;
    callServer(url, onSelectOption);
  }

  function removeSelect(selectlevel) {
    var fTD = document.all.item(targetSelectTD);
    var spans = fTD.all.tags("SPAN");

    initIndex = new Number(fTD.initIndex);
    len = spans.length;
    for(var i = (len-1); i > selectlevel; i--) {
      var rspan = fTD.all.tags("SPAN")[i];
      if(i > (initIndex-1)) {
        fTD.removeChild(rspan);
      }else{
        var cSelect = rspan.children(0);
        selectNodeInit(cSelect);
      }
    }
  }

  function selectNodeValue() {
    var fTD = document.all.item(targetSelectTD);
    var spans = fTD.all.tags("SPAN");
    len = spans.length;
    for(var i = len; i > 0; i--) {
      var rspan = spans[i-1];
      var cSelect = rspan.children(0);
      if(cSelect.value.length > 0) {
        return cSelect.value;
      }
    }

    return '';
  }

  function selectNodeInit(selObj) {
    var selLen = selObj.length;
    for(var i = selLen; i > 1; i--) {
      var selectOption = selObj.options[i-1];
      selObj.removeChild(selectOption);
    }
  }

  function onSelectOption(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;

    showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");

    var arr = new Array();
    for(var i = 0; i < l_oid.length; i++) {
      subArr = new Array();
      subArr[0] = decodeURIComponent(l_oid[i].text);
      subArr[1] = decodeURIComponent(l_name[i].text);
      arr[i] = subArr;
    }

    addSelectNode(arr);
  }

  function addSelectNode(vArr) {
    var fTD = document.all.item(targetSelectTD);
    initIndex = new Number(fTD.initIndex);

    if(selectLevelIndex >= (initIndex-1)) {
      if(vArr.length == 0){
        return;
      }
    }

    if(selectLevelIndex == (initIndex-1)) {
      var newSpan = document.createElement("SPAN");
      var htmlStr = "<select name='selFolder' selectlevel=" + ++selectLevelIndex + " class='fm_jmp' style='width:110' onChange='javascript:onChangeFolder(this);'><option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%><\/option>";
      for(var i = 0; i < vArr.length; i++) {
        htmlStr += "<option value=" + vArr[i][0] + ">" + vArr[i][1] + "<\/option>";
      }
      htmlStr += "<\/select>&nbsp;";
      newSpan.innerHTML = htmlStr;
      fTD.appendChild(newSpan);
    }else{
      var newSpan = fTD.all.tags("SPAN")[selectLevelIndex+1];
      var newSelect = newSpan.children(0);
      for(var i = 0; i < vArr.length; i++) {
        newOpt = document.createElement("OPTION");
        newOpt.text = vArr[i][1];
        newOpt.value = vArr[i][0];
        newSelect.add(newOpt);
      }
    }
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////문서 분류 끝




  function onSave() {
    if(checkCreate()) {
      if(checkRole()) {
        disabledAllBtn();
        showProcessing();
        document.forms[0].command.value = "create";
        //document.forms[0].Location.value = "";
        document.forms[0].Role.value = document.forms[0].role.value;
        document.forms[0].submit();
      }
       else {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00452") %><%--Role을 선택하지 않았습니다 \nRole을 선택하십시요--%>");
      }
    }

  }

  function checkCreate() {
    if(checkField(document.forms[0].name, "문서제목")){
      return false;
    }
    return true;
  }

  function checkRole() {
    var form = document.forms[0];
    if(form.role.selectedIndex > 0) {
      return true;
    }
    return false;
  }

  function registryClose(){
    opener.document.forms[0].submit();
    self.close();
  }

  function registryUser(){
  <%  if ( task != null ) {  %>
    var str="/plm/jsp/project/SelectTaskPeopleList.jsp?oid=<%=oid%>&mode=s&function=registryUserProjectOutput";
  <%  } else {  %>
    var str="/plm/jsp/project/SelectProjectPeopleList.jsp?oid=<%=oid%>&mode=s&function=registryUserProjectOutput";
  <%  }  %>
    newWin = window.open(str,"registryUser", "scrollbars=yes,status=yes,menubar=no,toolbar=no,location=no,directories=no,width=600,height=410,resizable=yes,mebar=no,left=80,top=105");
    newWin.focus();
  }
  <%if("create".equals(command)){%>registryClose();<%}%>
//-->
</script>
</head>
<body  bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<FORM method=post name="projectOutputCreate">
<!-- Hidden Values -->


<input type="hidden" name="number">
<input type=hidden name=oid value=<%=oid%>>
<input type=hidden name=command>
<input type=hidden name=Location value=<%=docType.getPath()%> >
<input type=hidden name=Role>

  <table border="0" cellpadding="0" cellspacing="0" class="popBox">
    <tr>
      <td class="boxTLeft"></td>
      <td class="boxTCenter"></td>
      <td class="boxTRight"></td>
    </tr>
    <tr>
      <td class="boxLeft"></td>
      <td>

<!------------------------------------- 본문 시작 //----------------------------------------->
    <table width="100%" order="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td>
          <!--title//-->
          <table border=0 cellpadding=0 cellspacing=0 width="100%">
            <tr>
              <td class='titleP'><%=messageService.getString("e3ps.message.ket_message", "01732") %><%--산출물 정의--%></td>
            </tr>
          </table>
          <!--title end//-->
        </td>
        <td align="right">
          <input type=button onclick="JavaScript:onSave();" value='<%=messageService.getString("e3ps.message.ket_message", "01795") %><%--생성--%>' class="s_font">&nbsp;
          <input type=button onclick="JavaScript:self.close();" value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' class="s_font">
        </td>
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
    <table border="0" cellpadding="1" cellspacing="1" width="100%"  align="center">


      <tr>
        <td width="1%" class="tdblueM"  colspan=2><%=messageService.getString("e3ps.message.ket_message", "01735") %><%--산출물 정의시--%> <font id=star>*</font> <%=messageService.getString("e3ps.message.ket_message", "00026") %><%--{0}는 필수항목입니다--%></td>
      </tr>


<%
  if(isJELTask) {
%>
          <col width='20%'><col width='30%'><col width='20%'><col width='30%'>
<%
  } else {
%>
          <col width='20%'><col width='80%'>
<%
  }

  int colspan = 1;
  if(isJELTask) {
    colspan = 3;
  }
%>


          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01431") %><%--문서위치--%> <font color="red">*</font></td>
            <td id='selectTD' class="tdwhiteL0" initIndex=4 colspan='<%=colspan%>'>
<%
  int defaultLength = 4;
  int pathLength = defaultLength;
  if(fullPath.size() > pathLength) {
    pathLength = fullPath.size();
  }

  if(pathLength == defaultLength) {
    ++pathLength;
  }


  ArrayList childLevels = null;
  DocCodeType childType = null;
  DocCodeType parentType = null;

  String docTypeName = "";
  String docTypeOid = "";
  String currentOid = "";
  String selected = "";

  for(int i = 0; i < pathLength; i++) {
    parentType = null;
    if(i < fullPath.size()) {
      parentType = (DocCodeType)fullPath.get(i);
    }

    currentOid = "";
    childLevels = null;
    if(parentType != null) {
      if(i < (fullPath.size()-1)) {
        currentOid = ((DocCodeType)fullPath.get(i+1)).getPersistInfo().getObjectIdentifier().getStringValue();
      }
      childLevels = DocCodeTypeHelper.getChildCode(parentType);
    }

    if(childLevels == null) {
      childLevels = new ArrayList();
    }

    if( (i == (pathLength-1)) && (childLevels.size() == 0) ) {
      continue;
    }
%>
              <span>
                <select name="docType" class="fm_jmp" style="width:110;" onChange="javascript:onChangeFolder(this);" selectlevel=<%=i%>>
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
    if(childLevels.size() > 0) {
      for(int j = 0; j < childLevels.size(); j++) {
        childType = (DocCodeType)childLevels.get(j);
        docTypeName = childType.getName();
        docTypeOid = childType.getPersistInfo().getObjectIdentifier().getStringValue();
        selected = "";
        if(currentOid.equals(docTypeOid)) {
          selected = "selected";
        }
        Kogger.debug(docTypeName);
%>
                  <option value="<%=docTypeOid%>" <%=selected%>><%=docTypeName%></option>
<%
      }
    }
%>
                </select>
              </span>
<%
  }

%>
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%> <font color="red">*</font></td>
            <td class="tdwhiteL0" <%if(isJELTask) {%>colspan="3"<%}%>>
              <input name="name" class="txt_field" style="width:99%;" value="<%=name%>">
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
            <td class="tdwhiteL0" <%if(isJELTask) {%>colspan="3"<%}%>>
              <textarea name="description" cols="75" rows="5" class="fm_area"onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"><%=description%></textarea>
            </td>
          </tr>
          <tr>
            <td class="tdblueL">Role <%if(!isJELTask){%><font color="red">*</font><%}%></td>
            <td <%if(isJELTask) {%>class="tdwhiteL"<%}else{%>class="tdwhiteL0"<%}%>>
              <select name='role' class="fm_jmp" style="width:110" <%if(isJELTask) {%>onChange="javascript:roleChange();"<%}%>>
                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%  // 담당을 '사용자'에서 'Role'로 변경

    wt.team.TeamTemplate tempTeam = wt.team.TeamHelper.service.getTeamTemplate("Team_Project");
    if(tempTeam != null) {
      Vector vec = tempTeam.getRoles();
      selected = "";
      for (int i = vec.size(); i > 0; i--)
      {
        wt.project.Role opRole = (wt.project.Role) vec.get(i-1);

        selected = "";
        if(role.equals(opRole.toString())) {
          selected = "selected";
        }
%>
                <option value='<%=opRole.toString()%>' <%=selected%>><%=opRole.getDisplay(messageService.getLocale())%></option>
<%
      }
    }
%>
              </select>
            </td>
          </tr>



    </table>
<!------------------------------------- 본문 끝 //----------------------------------------->
      </td>
      <td class="boxRight"></td>
    </tr>
    <tr>
      <td class="boxBLeft"></td>
      <td valign="bottom" class="boxBCenter"></td>
      <td class="boxBRight"></td>
    </tr>
  </table>
</form>
</body>
</html>
