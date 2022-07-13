<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil,
        e3ps.common.util.StringUtil,
        e3ps.groupware.company.Department,
        wt.fc.QueryResult,
        wt.fc.PersistenceHelper,
        e3ps.access.service.*
        "%>

<%@page import="java.util.StringTokenizer"%>
<%@page import="e3ps.common.jdf.config.ConfigEx"%>
<%@page import="java.util.Hashtable"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt" %>

<%
  e3ps.common.jdf.config.Config conf = ConfigEx.getInstance("document");
  String oid = request.getParameter("oid");
  ProjectOutPutType docType = null;

  String[] attrName = conf.getArray("document.attr.name.list");
  String[] attr = conf.getArray("document.attr.list");


    docType = (ProjectOutPutType) CommonUtil.getObject(oid);
    String tempName = null;
    String tempCode = null;
    String tempPath = null;
    String tempSort = null;
    String tempFolder = "";
    String isDisabled = "";
    StringTokenizer tempAttr = null;
    Hashtable attrHash = new Hashtable();
    Hashtable attr2Hash = new Hashtable();


    boolean isModify = false;
    boolean isRoot = false;
    QueryResult qr = null;
    if(oid!=null)
    {
      docType = (ProjectOutPutType)CommonUtil.getObject(oid);

      if(docType.getName().equals("ROOT")) isRoot = true;
      tempName = docType.getName();
      tempCode = docType.getCode();
      tempPath = docType.getPath();

      isDisabled = "" + docType.isIsDisabled();



      qr = PersistenceHelper.manager.navigate(docType, "parent", ParentChildLink.class);
        while (qr.hasMoreElements())
        {
          ProjectOutPutType tempDct = (ProjectOutPutType)qr.nextElement();
          if(tempDct.getName().equals("ROOT"))
            isRoot = true;
        }
    }
    for(int i=0;i<attrName.length;i++)
    {
        attrHash.put(attr[i],attrName[i]);
    }

    if(docType!=null && docType.getDescription()!=null)
    {
      tempAttr = new StringTokenizer(docType.getDescription(),";");
      while(tempAttr.hasMoreElements())
      {
          String key = tempAttr.nextToken();
          if(attrHash.get(key)!=null)
          {
            attr2Hash.put(key,tempAttr);

          }
      }
    }

%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.project.outputtype.ProjectOutPutType"%>
<%@page import="e3ps.common.impl.ParentChildLink"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01409") %><%--문서관리 관리(수정)--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
<!--
function updateData() {
  if (!check()) return;
  else {
    document.forms[0].action = '/plm/servlet/e3ps/ProjectOutPutTypeServlet';
    document.forms[0].submit();
  }
}

function check() {
  var doc = document.forms[0];


  if( isNullData(doc.oid.value) ) {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01959") %><%--수정할 카테고리를 선택해주세요--%>');
    return false;
  } else {
    if( doc.oid.value == 'root' ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02838") %><%--최상의 카테고리는 수정할 수 없습니다--%>');
      return false;
    } else {
      return true;
    }
  }
}
function selectFolder(target)
{
  var url = "/plm/jsp/groupware/folder/SelectFolder.jsp?folderpath=/Document&target="+target;
  openWindow(url, "SelectFolder", 300, 300);
}

function deleteFolder()
{
  document.forms[0].FOLDERName.value = '';
  document.forms[0].FOLDER.value = '';
}

function cancel(oid)
{
  document.location.href="/plm/jsp/doc/doctype/ViewDocType.jsp?oid="+oid;
}

//부서 가져오기 시작 ........................................................................................
//............................................................................................................
  function addDepartment() {

    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?isModal=true&isOpen=true&invokeMethod=openerCall&mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
      return;
    }
    //var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
    //attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    //if(typeof attacheDept == "undefined" || attacheDept == null) {
    //  return;
    //}

    //onProgressBar();
    //var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
    //var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    //callServer(url, acceptDept);
  }

  function openerCall(oArr) {

    onProgressBar();
    var param = "command=deptInfo&deptOid=" + oArr[0][0];
    var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    callServer(url, acceptDept);
  }

  function acceptDept(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_code = showElements[0].getElementsByTagName("l_code");

    var targetTable = document.getElementById("depttable");

    for(var i = 0; i < l_oid.length; i++) {
      loid = decodeURIComponent(l_oid[i].text);
      lname = decodeURIComponent(l_name[i].text);
      //lcode = decodeURIComponent(l_code[i].text);
      if(isDeptCheck(loid)) {
        continue;
      }
      tableRows = targetTable.rows.length;
      newTr = targetTable.insertRow(tableRows);

      //부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = lname;

      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteDept('" + loid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='deptOid' value='" + loid + "'>";
    }

  }


  function isDeptCheck(deptid) {
    form = document.forms[0];
    if(form.deptOid == null || form.deptOid == 'undefined') {
      return false;
    }

    var deptLen = form.deptOid.length;
    if(deptLen) {
      for(var i = 0; i < deptLen; i++) {
        if(form.deptOid[i].value == deptid) {
          return true;
        }
      }
    }else {
      if(form.deptOid.value == deptid) {
        return true;
      }
    }

    return false;
  }

  function onDeleteDept(deptid) {
    form = document.forms[0];

    targetTable = document.getElementById("depttable");

    var deptLen = form.deptOid.length;
    if(deptLen) {
      for(var i = 0; i < deptLen; i++) {
        if(form.deptOid[i].value == deptid) {
          targetTable.deleteRow(i+1);
          return;
        }
      }
    }else {
      if(form.deptOid.value == deptid) {
        targetTable.deleteRow(1);
        return;
      }
    }
  }

  function getDeptParameters() {
    form = document.forms[0];

    if(form.deptOid == null || form.deptOid == 'undefined') {
      return "";
    }

    var deptParam = "";
    var deptLen = form.deptOid.length;
    if(deptLen) {
      for(var i = 0; i < deptLen; i++) {
        deptParam += "&deptOid=" + form.deptOid[i].value;
      }
    }else {
      deptParam += "&deptOid=" + form.deptOid.value;
    }

    return deptParam;
  }

  //부서 가져오기 끝 ........................................................................................
  //............................................................................................................

//사용자 가져오기 시작 ........................................................................................
//............................................................................................................
function addMember() {
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  acceptMember(attacheMembers);
}

function acceptMember(objArr) {
  if(objArr.length == 0) {
    return;
  }

  /*
    subArr[0] = chkobj[i].value;//wtuser oid
    subArr[1] = chkobj[i].poid;//people oid
    subArr[2] = chkobj[i].doid;//dept oid
    subArr[3] = chkobj[i].uid;//uid
    subArr[4] = chkobj[i].sname;//name
    subArr[5] = chkobj[i].dname;//dept name
    subArr[6] = chkobj[i].duty;//duty
    subArr[7] = chkobj[i].dutycode;//duty code
    subArr[8] = chkobj[i].email;//email
  */

  var nonUserArr = new Array();
  for(var i = 0; i < objArr.length; i++) {
    infoArr = objArr[i];
    if(isMemberCheck(infoArr[0])) {
      continue;
    }

    nonUserArr[nonUserArr.length] = infoArr;
  }

  if(nonUserArr.length == 0) {
    return;
  }

  var targetTable = document.getElementById("membertable");

  for(var i = 0; i < nonUserArr.length; i++) {
    tableRows = targetTable.rows.length;

    infoArr = nonUserArr[i];
    newTr = targetTable.insertRow(tableRows);

    //이름
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerText = infoArr[4];

    //직위
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerText = infoArr[6];

    //부서
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerText = infoArr[5];

    //삭제
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteMember('" + infoArr[0] + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='userOid' value='" + infoArr[0] + "'>";
  }

}

function isMemberCheck(wtuserid) {
  form = document.forms[0];
  if(form.userOid == null || form.userOid == 'undefined') {
    return false;
  }

  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      if(form.userOid[i].value == wtuserid) {
        return true;
      }
    }
  }else {
    if(form.userOid.value == wtuserid) {
      return true;
    }
  }

  return false;
}

function onDeleteMember(wtuserid) {
  form = document.forms[0];

  targetTable = document.getElementById("membertable");

  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      if(form.userOid[i].value == wtuserid) {
        targetTable.deleteRow(i+1);
        return;
      }
    }
  }else {
    if(form.userOid.value == wtuserid) {
      targetTable.deleteRow(1);
      return;
    }
  }
}

function getMemberParameters() {
  form = document.forms[0];

  if(form.userOid == null || form.userOid == 'undefined') {
    return "";
  }

  var userParam = "";
  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      userParam += "&userOid=" + form.userOid[i].value;
    }
  }else {
    userParam += "&userOid=" + form.userOid.value;
  }

  return userParam;
}
//사용자 가져오기 끝 ........................................................................................
//............................................................................................................





//parent.tree.location.reload();
//-->
</script>
</head>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form method="post">

<input type="hidden" name="cmd" value="modify">
<input type="hidden" name="oid" value="<%=oid%>">

<div class=div_title><script>printTitle('<%=messageService.getString("e3ps.message.ket_message", "01435") %><%--문서타입 관리--%> <b>[<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>]</b>')</script></div>
<div class=div_title_action>
  <input type="button" id="button2" value="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" onclick="javascript:updateData()">
    <input type="button" id="button2" value="<%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%>" onclick="javascript:cancel('<%=oid%>')">
</div>
<div class=div_body_title><%=messageService.getString("e3ps.message.ket_message", "01435") %><%--문서타입 관리--%><b>[<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>]</b></div>
<div class=div_body_row>
    <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></div>
  <div class=div_row_long>
    <input type=text size=40 name="NAME" id=i  value="<%=StringUtil.checkNull(tempName) %>" onfocus='blur' >
  </div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></div>
  <div class=div_row_long>
    <input type=text size=40 name="CODE" id=i value="<%=StringUtil.checkNull(tempCode) %>" onfocus='blur' >
  </div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "00795") %><%--경로--%></div>
  <div class=div_row_long>
    <input type=text size=40 name="PATH" id=i value="<%=StringUtil.checkNull(tempPath) %>" onfocus='blur()'>
  </div>
</div>

<%if(CommonUtil.isAdmin() )
  //out.println(isDisabled);
{%>
<div class=div_body_row>
    <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "01670") %><%--사용 불가--%></div>
  <%if(isDisabled.equals("true")){ %>
  <div class=div_row_long>
    [  false <input type=radio size=40 name="isDisabled" id=i value="false">
    true<input type=radio size=40 name="isDisabled" id=i value="true" checked> ]
  </div>
  <%}else { %>
  <div class=div_row_long>
    [  false <input type=radio size=40 name="isDisabled" id=i value="false" checked>
    true<input type=radio size=40 name="isDisabled" id=i value="true"> ]
  </div>
  <%} %>
</div>
<%} %>



&nbsp;
</form>
</body>
</html>
