<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="e3ps.dms.service.KETDmsHelper"%>
<%@page import="e3ps.dms.entity.KETDocumentCategory"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.web.ParamUtil,e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.common.util.*"%>
<%@page import="wt.fc.*,wt.query.*,wt.util.*,wt.introspection.*,wt.org.*,wt.session.*"%>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*,e3ps.common.web.*"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  boolean isDRR = Boolean.parseBoolean(request.getParameter("isDRR")); //개발검토의뢰서 인지 여부 체크
  String pboOid = StringUtil.checkNull(request.getParameter("pboOid"));
  WTPrincipal creator = (WTPrincipal) SessionHelper.manager.getPrincipal();
  boolean teamManagerCheck = false;
//  boolean officerCheck = false;

  PeopleData data = new PeopleData(creator);
    String aa = data.chief;
    String duty = data.duty;
    if(StringUtil.checkString(pboOid)){
      Persistable per = (Persistable) CommonUtil.getObject(pboOid);
      KETProjectDocument pDoc = null;
      if (per != null && per instanceof KETProjectDocument) {
  
  		pDoc = (KETProjectDocument) CommonUtil.getObject(pboOid);
  		String cName = pDoc.getFolderingInfo().getParentFolder().getName();
  
  		//자가결재 체크
  		if ("회장".equals(duty) || "사장".equals(duty) || "부사장".equals(duty) || "전무".equals(duty) || "상무".equals(duty) || "상무보".equals(duty) || "이사대우".equals(duty)) {
  		    teamManagerCheck = true;
  		} else if (aa.length() > 0) {
  		    teamManagerCheck = true;
  		    if ("DR회의록".equals(cName) || "Project카드".equals(cName) || "프로젝트완료보고".equals(cName) || "품의서".equals(cName)) {
  			teamManagerCheck = false;
  		    }
  		}
      }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@include file="UserLoading.html"%>
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
<script type="text/javascript">
//startUserLoading();
var checkDup = false;
var isDRR = <%=isDRR%>;

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function search() {
  document.searchPeople.target="iframe";
  document.searchPeople.action = "SearchPeople.jsp";
  document.searchPeople.submit();
}

function addUser(selMember){
  document.searchPeople.memList.value = selMember;
}

function sortRow(tableObj, trSize) {

  for(i=0; i<tableObj.rows.length; i++){
    var number = tableObj.rows[i].childNodes[0].innerHTML;
    if( trSize-i != number )tableObj.rows[i].childNodes[0].innerHTML = trSize-i;
  }
}
function deleteUser(type){
  var selectObj1 = document.getElementById('selectBeforeD');
  var selectObj2 = document.getElementById('selectDiscuss');
  var selectObj3 = document.getElementById('selectAfterD');
  var tableObj1 = document.getElementById('beforeD');
  var tableObj2 = document.getElementById('discuss');
  var tableObj3 = document.getElementById('afterD');
  var s1=selectObj1.length;
  var s2=selectObj2.length;
  var s3=selectObj3.length;

  if(type=='all'){
    var msg = confirm('<%=messageService.getString("e3ps.message.ket_message", "01376") %><%--모든 사용자를 삭제하시겠습니까?--%>');
    if(msg){
      //[START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-22, 김무준
      if (existPreAdded(selectObj2, selectObj3, true) == true) return; // selected 변경 전 확인
      //[END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-22, 김무준
      while(s1>0){selectObj1.options[s1-1].selected=true; s1--;}
      while(s2>0){selectObj2.options[s2-1].selected=true; s2--;}
      while(s3>0){selectObj3.options[s3-1].selected=true; s3--;}
    }else return;
  }
  //[START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-22, 김무준
  else {
    if (existPreAdded(selectObj2, selectObj3, false)) return;
  }
  //[END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-22, 김무준

  for(i=selectObj1.length-1; i>=0; i--){
    if(selectObj1.options[i].selected==true){
      selectObj1.removeChild(selectObj1.options[i]);
      tableObj1.deleteRow(selectObj1.length-i);
      }
  } sortRow(tableObj1 ,tableObj1.rows.length);
  for(i=selectObj2.length-1; i>=0; i--){
    if(selectObj2.options[i].selected==true){
      selectObj2.removeChild(selectObj2.options[i]);
      tableObj2.deleteRow(selectObj2.length-i);
    }
  }sortRow(tableObj2 , tableObj2.rows.length);
  for(i=selectObj3.length-1; i>=0; i--){
    if(selectObj3.options[i].selected==true){
      selectObj3.removeChild(selectObj3.options[i]);
      tableObj3.deleteRow(selectObj3.length-i);

    }
  }sortRow(tableObj3 , tableObj3.rows.length);
}

//[START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-22, 김무준
function containsId(idArray, id) {
  for (var i = 0; i < idArray.length; ++i) {
    if (idArray[i] == id) return true;
  }
  return false;
}
function existPreAdded(selectDiscuss, selectAfterD, checkAll) {
  if (preAddedByRoleAry != null) {
    for(var i=selectDiscuss.length-1; i>=0; i--){
      if(checkAll == true || selectDiscuss.options[i].selected==true){
        var id = selectDiscuss.options[i].value;
        if (containsId(preAddedByRoleAry, id)) {
          alert('<%=messageService.getString("e3ps.message.ket_message", "02361") %><%--일정변경사유 결재요청 시 Role에 의해 지정된 합의자는 삭제할 수 없습니다--%>');
          return true;
        }
      }
    }
  }

  if (preAddedChief != null) {
    for(var i=selectAfterD.length-1; i>=0; i--){
      if(checkAll == true || selectAfterD.options[i].selected==true){
        var id = selectAfterD.options[i].value;
        if (preAddedChief == id) {
          alert('<%=messageService.getString("e3ps.message.ket_message", "02362") %><%--일정변경사유 결재요청 시 팀장으로 지정된 승인자는 삭제할 수 없습니다--%>');
          return true;
        }
      }
    }
  }

  return false;
}
//[END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-22, 김무준

function selectMember(selectID ,optValue, trObj){
  var selBox = document.getElementById(selectID);
  for(i=0; i<selBox.length; i++){
    if((selBox.options[i].value == optValue) && !selBox.options[i].selected) {
      selBox.options[i].selected = true;
      for(j=0; j<trObj.childNodes.length; j++){
        trObj.childNodes[j].className = "tdgrayM";
        if(j==trObj.childNodes.length-1)trObj.childNodes[j].className = "tdgrayM0";
      }
    }
    else if((selBox.options[i].value == optValue) && selBox.options[i].selected) {
      selBox.options[i].selected = false;
      for(j=0; j<trObj.childNodes.length; j++){
        trObj.childNodes[j].className = "tdwhiteM";
        if(j==trObj.childNodes.length-1)trObj.childNodes[j].className = "tdwhiteM0";
      }
    }
  }
}

function addTR(userArray, tID, sId, trnumber) {
  var xmlhttp = new XMLHttpRequest();
  var url = "UserInfoAjax.jsp?userid="+encodeURIComponent(userArray);
  xmlhttp.open("GET",url,false);
  xmlhttp.onreadystatechange = function(){
    if(xmlhttp.readyState==4){
      if(xmlhttp.status==200){
        var userinfo = xmlhttp.responseText;
        var arrayResponse = userinfo.split('+');
          if(arrayResponse!=""){
          var tableID = document.getElementById(tID);
          var selectBox = document.getElementById(sId);
          var dataID = userArray;
          var newTD1 = document.createElement('TD');
          newTD1.innerHTML = trnumber;
          newTD1.className = 'tdwhiteM';
          //newTD1.width = '50';
          var newTD2 = document.createElement('TD');
          if(tID!='discuss')newTD2.innerHTML = '<%=messageService.getString("e3ps.message.ket_message", "00716") %><%--검토--%>';
          else newTD2.innerHTML = '<%=messageService.getString("e3ps.message.ket_message", "03156") %><%--합의--%>';
          newTD2.className = 'tdwhiteM';
          //newTD2.width = '70';
          var newTD3 = document.createElement('TD');
          newTD3.innerHTML = arrayResponse[0];
          newTD3.className = 'tdwhiteM';
          //newTD3.width = '155';
          var newTD4 = document.createElement('TD');
          newTD4.innerHTML = arrayResponse[1];
          newTD4.className = 'tdwhiteM';
          //newTD4.width = '90';
          var newTD5 = document.createElement('TD');
          newTD5.innerHTML = arrayResponse[2];
          newTD5.className = 'tdwhiteM0';
          //newTD5.width = '78';
          var newTR = tableID.insertRow(0);
          newTR.height = '20';
          newTR.style.cursor='hand';
          newTR.addEventListener('click',function(){selectMember(sId, dataID, newTR)});
          newTR.appendChild(newTD1);
          newTR.appendChild(newTD2);
          newTR.appendChild(newTD3);
          newTR.appendChild(newTD4);
          newTR.appendChild(newTD5);
          selectBox.options.add(new Option(arrayResponse[2],dataID));
          }
        }
      }
    }
  xmlhttp.send(null);
}

function getUserArray(cboxName){

    var useridArr = new Array();
    $('input[name='+ cboxName +']:checked').each(function() {
        checkedFlag = true;
        useridArr[useridArr.length] = $(this).val();
    });
    return useridArr;
}

function beforeDiscuss(){
    
    var userArray = new Array();
    var active = $('#tabs').tabs('option', 'active');
    if(active == 0){ // 검색 탭
        userArray = getUserArray('searchUserChkbox');
    }
    else if(active == 1){ // 조직도 탭
        userArray = document.searchPeople.memList.value.split(',');
    }
    else if(active == 2){ // 프로젝트 탭
        userArray = getUserArray('projectCFTChkbox');
    }
    else if(active == 3){ // 자주 지정하는 결재자 탭
        userArray = getUserArray('approvalLineMemberChkbox');
    }
    else{
        userArray = document.searchPeople.memList.value.split(',');
    }
  
  var strArray = document.getElementById('selectBeforeD');
  var tempArray1 = document.getElementById('selectAfterD');
  var tempArray2 = document.getElementById('selectDiscuss');
  if(strArray.length+userArray.length>11){
    alert('<%=messageService.getString("e3ps.message.ket_message", "01677", "10") %><%--사용자는 {0}명까지만 선택하실 수 있습니다--%>');
    return;
  }
  var trnumber1 = strArray.length;
  var tableObj = document.getElementById('beforeD');
  checkDup = false;
  for(i=0; i<userArray.length; i++){
    <%if( !teamManagerCheck){%>
      if(userArray[i] == '<%=creator.getName()%>') {
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "05164") %><%--본인을 결재선에 등록할 수 없습니다.--%>');
      }
    <%}%>
    for(j=0; j<strArray.length; j++){
      if((userArray[i] == strArray.options[j].value)) {
        checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03157") %><%--합의 전 검토에 동일한 사용자가 있습니다--%>');
      }
    }
    for(var k=0; k<tempArray1.length; k++){
      if(userArray[i]==tempArray1.options[k].value){
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03167") %><%--합의후 검토에 이미 지정된 사용자가 있습니다--%>');
      }
    }
    for(k=0; k<tempArray2.length; k++){
      if(userArray[i]==tempArray2.options[k].value){
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03162") %><%--합의에 이미 지정된 사용자가 있습니다--%>');
      }
    }
    trnumber1++;
    if(userArray[i]!="" && !checkDup) addTR(userArray[i], 'beforeD', 'selectBeforeD', trnumber1);
    checkDup = false;
  }
  if(active == 0){ // 검색 탭
      unCheckedAll(document.forms[0].searchUserChkboxAll, document.forms[0].searchUserChkbox);
  }
  else if(active == 1){ // 조직도 탭
      document.frames("deptTree").unselectAll();
  }
  else if(active == 2){ // 프로젝트 탭
      unCheckedAll(document.forms[0].projectCFTChkboxAll, document.forms[0].projectCFTChkbox);
  }
  else if(active == 3){ // 자주 지정하는 결재자 탭
      unCheckedAll(document.forms[0].approvalLineMemberChkboxAll, document.forms[0].approvalLineMemberChkbox);
  }
}

function discuss(){
  var radiocheck = document.searchPeople.radiob;
  var tempArray1 = document.getElementById('selectBeforeD');
  var tempArray2 = document.getElementById('selectAfterD');

  var userArray = new Array();
  var active = $('#tabs').tabs('option', 'active');
  if(active == 0){ // 검색 탭
      userArray = getUserArray('searchUserChkbox');
  }
  else if(active == 1){ // 조직도 탭
      userArray = document.searchPeople.memList.value.split(',');
  }
  else if(active == 2){ // 프로젝트 탭
      userArray = getUserArray('projectCFTChkbox');
  }
  else if(active == 3){ // 자주 지정하는 결재자 탭
      userArray = getUserArray('approvalLineMemberChkbox');
  }
  else{
      userArray = document.searchPeople.memList.value.split(',');
  }
  for(var k=0; k<radiocheck.length; k++){
    if(radiocheck[0].checked && userArray.length>0){
      alert('<%=messageService.getString("e3ps.message.ket_message", "03163") %><%--합의유형없음을 선택하실 경우에는 사용자를 지정하실 수 없습니다--%>');
      return;
    }
  }
  var strArray = document.getElementById('selectDiscuss');
  if(strArray.length+userArray.length>11){
    alert('<%=messageService.getString("e3ps.message.ket_message", "01677", "10") %><%--사용자는 {0}명까지만 선택하실 수 있습니다--%>');
    return;
  }
  var tableObj = document.getElementById('discuss');
  var trnumber2 = strArray.length;
  checkDup = false;
  for(i=0; i<userArray.length; i++){
    if(userArray[i] == '<%=creator.getName()%>') {
        checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "05164") %><%--본인을 합의자로 선택하실 수 없습니다--%>');
    }
    for(j=0; j<strArray.length; j++){
      if(userArray[i] == strArray.options[j].value) {
        checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03160") %><%--합의에 동일한 사용자가 있습니다--%>');
      }
    }
    for(var k=0; k<tempArray1.length; k++){
      if(userArray[i]==tempArray1.options[k].value){
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03165") %><%--합의전 검토에 이미 지정된 사용자가 있습니다--%>');
      }
    }
    for(k=0; k<tempArray2.length; k++){
      if(userArray[i]==tempArray2.options[k].value){
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03167") %><%--합의후 검토에 이미 지정된 사용자가 있습니다--%>');
      }
    }
    trnumber2++;
    if(userArray[i]!="" && !checkDup) addTR(userArray[i],'discuss','selectDiscuss', trnumber2);
    checkDup = false;
  }
  if(active == 0){ // 검색 탭
      unCheckedAll(document.forms[0].searchUserChkboxAll, document.forms[0].searchUserChkbox);
  }
  else if(active == 1){ // 조직도 탭
      document.frames("deptTree").unselectAll();
  }
  else if(active == 2){ // 프로젝트 탭
      unCheckedAll(document.forms[0].projectCFTChkboxAll, document.forms[0].projectCFTChkbox);
  }
  else if(active == 3){ // 자주 지정하는 결재자 탭
      unCheckedAll(document.forms[0].approvalLineMemberChkboxAll, document.forms[0].approvalLineMemberChkbox);
  }
}

function afterDiscuss() {

    var userArray = new Array();
    var active = $('#tabs').tabs('option', 'active');
    if(active == 0){ // 검색 탭
        userArray = getUserArray('searchUserChkbox');
    }
    else if(active == 1){ // 조직도 탭
        userArray = document.searchPeople.memList.value.split(',');
    }
    else if(active == 2){ // 프로젝트 탭
        userArray = getUserArray('projectCFTChkbox');
    }
    else if(active == 3){ // 자주 지정하는 결재자 탭
        userArray = getUserArray('approvalLineMemberChkbox');
    }
    else{
        userArray = document.searchPeople.memList.value.split(',');
    }
    
  var strArray = document.getElementById('selectAfterD');
  var tempArray1 = document.getElementById('selectBeforeD');
  var tempArray2 = document.getElementById('selectDiscuss');
  if(strArray.length+userArray.length>11){
    alert('<%=messageService.getString("e3ps.message.ket_message", "01677", "10") %><%--사용자는 {0}명까지만 선택하실 수 있습니다--%>');
    return;
  }
  var tableObj = document.getElementById('afterD');
  var trnumber3 = strArray.length;
  checkDup = false;
  for(i=0; i<userArray.length; i++){
    <%if( !teamManagerCheck){%>
      if(userArray[i] == '<%=creator.getName()%>') {
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "05164") %><%--본인을 합의자로 선택하실 수 없습니다--%>');
      }
    <%}%>
    for(j=0; j<strArray.length; j++){
      if(userArray[i] == strArray.options[j].value) {
        checkDup = true;
        alert("<%=messageService.getString("e3ps.message.ket_message", "00721") %><%--검토 및 승인에 동일한 사용자가 있습니다--%>");
      }
    }
    for(var k=0; k<tempArray1.length; k++){
      if(userArray[i]==tempArray1.options[k].value){
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03165") %><%--합의전 검토에 이미 지정된 사용자가 있습니다--%>');
      }
    }
    for(k=0; k<tempArray2.length; k++){
      if(userArray[i]==tempArray2.options[k].value){
          checkDup = true;
        alert('<%=messageService.getString("e3ps.message.ket_message", "03162") %><%--합의에 이미 지정된 사용자가 있습니다--%>');
      }
    }
    trnumber3++;
    if(userArray[i]!="" && !checkDup) addTR(userArray[i], 'afterD', 'selectAfterD', trnumber3);
    checkDup = false;
  }
  if(active == 0){ // 검색 탭
      unCheckedAll(document.forms[0].searchUserChkboxAll, document.forms[0].searchUserChkbox);
  }
  else if(active == 1){ // 조직도 탭
      document.frames("deptTree").unselectAll();
  }
  else if(active == 2){ // 프로젝트 탭
      unCheckedAll(document.forms[0].projectCFTChkboxAll, document.forms[0].projectCFTChkbox);
  }
  else if(active == 3){ // 자주 지정하는 결재자 탭
      unCheckedAll(document.forms[0].approvalLineMemberChkboxAll, document.forms[0].approvalLineMemberChkbox);
  }
}

function trDown(tId , sId){
  var checkFlag = 0;
  var selectObj = document.getElementById(sId);

  if(selectObj.selectedIndex<0){
    alert('<%=messageService.getString("e3ps.message.ket_message", "01982") %><%--순서를 변경할 사용자를 선택하여 주십시요--%>');
    return;
  }

  for(var k=0; k<selectObj.length; k++){
    if(selectObj.options[k].selected==true) checkFlag++;
    if(checkFlag>1) {alert('한 사용자만 이동 시킬 수 있습니다'); return;}
  }

  var tableObj = document.getElementById(tId);
  var rIndex = tableObj.rows.length-1;
  var idx = selectObj.selectedIndex;
  var opt = selectObj.options[selectObj.selectedIndex];
  var trObj = tableObj.rows[rIndex-idx];
  var nIndex = trObj.rowIndex+2;
  var trSize = tableObj.rows.length;
  if(nIndex>tableObj.rows.length) return;
  var oNewRow = tableObj.insertRow(nIndex);
  oNewRow.style.cursor='hand';
  oNewRow.addEventListener('click',function(){selectMember(sId, opt.value, oNewRow)});

  for (j=0; j<trObj.childNodes.length; j++){
    if(trObj.childNodes[j].tagName=='TD'){
      var oCell = document.createElement('TD');
      oNewRow.appendChild(oCell);
      oCell.innerHTML = trObj.childNodes[j].innerHTML;
      oCell.className = "tdgrayM0";
      if(j!=trObj.childNodes.length)oCell.className = "tdgrayM";
    }
  }
  var n = parseInt(rIndex-idx);
  tableObj.deleteRow(n);

  sortRow(tableObj , trSize);

  if(idx<0) idx = obj.selectedIndex = 0;
  if(idx > 0) selectObj.insertBefore(opt, selectObj.options[idx-1]);

}

function trUp(tId , sId) {
  var checkFlag = 0;
  var selectObj = document.getElementById(sId);

  if(selectObj.selectedIndex<0){
    alert('<%=messageService.getString("e3ps.message.ket_message", "01982") %><%--순서를 변경할 사용자를 선택하여 주십시요--%>');
    return;
  }

  for(var k=0; k<selectObj.length; k++){
    if(selectObj.options[k].selected==true) checkFlag++;
    if(checkFlag>1) {alert('한 사용자만 이동 시킬 수 있습니다'); return;}
  }
  var tableObj = document.getElementById(tId);
  var rIndex = tableObj.rows.length-1;
  var idx = selectObj.selectedIndex;
  var opt = selectObj.options[selectObj.selectedIndex];
  var opt1 = selectObj.options[selectObj.selectedIndex+1];
  var trObj = tableObj.rows[rIndex-idx];
  var nIndex = trObj.rowIndex-1;
  var trSize = tableObj.rows.length;
  if(nIndex<0) return;
  var oNewRow = tableObj.insertRow(nIndex);
  oNewRow.style.cursor='hand';
  oNewRow.addEventListener('click',function(){selectMember(sId, opt.value, oNewRow)});

  for (j=0; j<trObj.childNodes.length; j++){
    if(trObj.childNodes[j].tagName=='TD'){
      var oCell = document.createElement('TD');
      oNewRow.appendChild(oCell);
      oCell.innerHTML = trObj.childNodes[j].innerHTML;
      oCell.className = "tdgrayM0";
      if(j==0) oCell.innerHTML = rIndex+idx;
      if(j!=trObj.childNodes.length)oCell.className = "tdgrayM";
    }
  }
  var n = parseInt(rIndex-idx+1);
  tableObj.deleteRow(n);

  sortRow(tableObj , trSize);

  if(idx < 0) idx = obj.selectedIndex = 0;
  if(idx < selectObj.options.length-1) selectObj.insertBefore(selectObj.options[idx+1], opt);
}

function menu(tname){
  var img1 = document.searchUser;
  var img2 = document.tree;
  var divv1 = document.getElementById('psearch');
  var divv2 = document.getElementById('deptTree');
  if(tname=='searchUser'){
    if(img1.src=="../../portal/images/tab9_1.png"){
      document.searchUser.src="../../portal/images/tab9_2.png"
      document.tree.src="../../portal/images/tab10_1.png";
      divv1.style.display = "none";
      divv2.style.display = "";
    }else{
      document.searchUser.src="../../portal/images/tab9_1.png"
      document.tree.src="../../portal/images/tab10_2.png";
      divv1.style.display = "";
      divv2.style.display = "none";
    }
  }
  else{
    if(img2.src=="../../portal/images/tab10_1.png"){
      document.searchUser.src="../../portal/images/tab9_1.png"
      document.tree.src="../../portal/images/tab10_2.png";
      divv1.style.display = "";
      divv2.style.display = "none";
    }else{
      document.searchUser.src="../../portal/images/tab9_2.png"
      document.tree.src="../../portal/images/tab10_1.png";
      divv1.style.display = "none";
      divv2.style.display = "";
    }
  }
}

function parentSumbmit(){
  var radiocheck = document.searchPeople.radiob;
  var totalArray = new Array();
  var totalcnt = 0;
  var tableObj1 = document.getElementById('beforeD');
  var tableObj2 = document.getElementById('discuss');
  var tableObj3 = document.getElementById('afterD');

  if(radiocheck[0].checked==true && tableObj2.rows.length>0){
    alert('<%=messageService.getString("e3ps.message.ket_message", "03159") %><%--합의없음 유형에서 지정된 사용자가 있습니다--%>');
    return;
  }else if(radiocheck[0].checked==false && !isDRR){
    if(tableObj2.rows.length<1){
      alert('<%=messageService.getString("e3ps.message.ket_message", "03161") %><%--합의에 사용자를 지정하지 않으셨습니다--%>');
      return;
    }
    if(tableObj3.rows.length<1){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00728") %><%--검토및승인에 사용자를 지정하여 주시기 바랍니다--%>');
      return;
    }
  }
  for(i=tableObj1.rows.length-1; i>=0; i--){
    var targetArray = new Array(5);
    for(j=0; j<tableObj1.rows[i].childNodes.length; j++){
      if(tableObj1.rows[i].childNodes[j].tagName=='TD'){
        targetArray[j]=tableObj1.rows[i].childNodes[j].innerText;
      }
    }
    totalArray[totalcnt] = targetArray;
    totalcnt++;
  }

  for(i=tableObj2.rows.length-1; i>=0; i--){
    var targetArray = new Array(5);
    for(j=0; j<tableObj2.rows[i].childNodes.length; j++){
      if(tableObj2.rows[i].childNodes[j].tagName=='TD'){
        targetArray[j]=tableObj2.rows[i].childNodes[j].innerText;
      }
    }
    totalArray[totalcnt] = targetArray;
    totalcnt++;
  }

  for(i=tableObj3.rows.length-1; i>=0; i--){
    var targetArray = new Array(5);
    for(j=0; j<tableObj3.rows[i].childNodes.length; j++){
      if(tableObj3.rows[i].childNodes[j].tagName=='TD'){
        targetArray[j]=tableObj3.rows[i].childNodes[j].innerText;
      }
    }
    totalArray[totalcnt] = targetArray;
    totalcnt++;
  }

  if(totalArray.length!=0){
    var obj1 = document.getElementById('selectBeforeD');
    var obj2 = document.getElementById('selectDiscuss');
    var obj3 = document.getElementById('selectAfterD');

    window.opener.delapproval();
    window.opener.saveSelectBox('type1',obj1,'approval');
    window.opener.saveSelectBox('type2',obj2,'approval');
    window.opener.saveSelectBox('type3',obj3,'approval');
    window.opener.addTR(totalArray , 'approval');
    self.close();
  }else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02712") %><%--지정된 사용자가 없습니다--%>');
            return;
        }
    }

    function saveType(type) {
        window.opener.requestApproval.discussType.value = type;
    }

    function chkEnter(code) {
        if (code == 13)
            search();
        else
            return;
    }

    //[START] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
    var preAddedByRoleAry = null;
    var preAddedChief = null;

    function setPreAdded(_preAddedByRole, _preAddedChief) {
        if (_preAddedByRole != null && _preAddedByRole != undefined) {
            preAddedByRoleAry = _preAddedByRole.split(",");
        }
        if (_preAddedChief != null && _preAddedChief != undefined) {
            preAddedChief = _preAddedChief;
        }
    }
    //[END] [PLM System 1차개선] 일정 변경 사유 입력, 2013-07-19, 김무준
</script>
<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<%@include file="/jsp/common/multicombo.jsp"%>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        //tab binding
        var tab = CommonUtil.tabs('tabs');
        // suggest
        //SuggestUtil.bind('USER', 'searchUserName', 'searchUser');
        //SuggestUtil.bind('USER', 'searchUserALName', 'searchUserAL');
        
        // 진행중인 프로젝트 검색
        searchMyProject();
        // 자주 지정하는 결재자 검색
        searchMyFavorApprovalLine();

        // Enter 검색
        $("[name=searchUserName]").keypress(function(e) {
            if (e.which == 13) {
                searchUser('searchUserName');
                return false;
            }
        });
        $("[name=searchUserALName]").keypress(function(e) {
            if (e.which == 13) {
                searchUser('searchUserALName');
                return false;
            }
        });
        
        // 팝업 오픈 시 지정되어 있는 결재라인 셋팅
        var v_bReview = opener.document.requestApproval.bReview.value;
        var v_discuss = opener.document.requestApproval.discuss.value;
        var v_discussType = opener.document.requestApproval.discussType.value;
        var v_aReview = opener.document.requestApproval.aReview.value;

        var userArray = v_bReview.split(",");
        var trnumber =  document.getElementById('selectBeforeD').length;
        for(var i=0; i < userArray.length-1; i++){
            addTR(userArray[i], 'beforeD', 'selectBeforeD', ++trnumber);
        }
        if(v_discussType=='noDiscuss'){
          document.searchPeople.radiob[0].checked = true;
        }
        else if(v_discussType=='sequential'){
          document.searchPeople.radiob[1].checked = true;
        }
        else if(v_discussType=='parallel'){
          document.searchPeople.radiob[2].checked = true;
        }else {
          document.searchPeople.radiob[0].checked = true;
        }
        userArray = v_discuss.split(",");
        trnumber =  document.getElementById('selectDiscuss').length;
        for(var i=0; i < userArray.length-1; i++){
            addTR(userArray[i], 'discuss', 'selectDiscuss', ++trnumber);
        }
        userArray = v_aReview.split(",");
        trnumber =  document.getElementById('selectAfterD').length;
        for(var i=0; i < userArray.length-1; i++){
            addTR(userArray[i], 'afterD', 'selectAfterD', ++trnumber);
        }
    });
    
    function deleteRows(tBody){
        if(tBody.rows.length > 0){
            for(var i=tBody.rows.length; i > 0; i--){
                tBody.deleteRow(tBody.rows[i]);
            }
        }
    }
    
    function addUserRow(bodyId, chkboxName, peopleData){

        var tBody = document.getElementById(bodyId);
        var innerRow = tBody.insertRow();
        var innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerHTML = "<input type='checkbox' name='"+ chkboxName +"' id='"+ chkboxName +"' value='"+ peopleData.id +"'>";
        
        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerText = peopleData.name;

        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerText = peopleData.departmentName;

        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM0";
        innerCell.innerText = peopleData.duty;
    }
    
    function searchUser(elementName){
        $.ajax({
            url : "/plm/ext/wfm/workspace/searchUser.do",
            type : "POST",
            data : {
                paramUserName : $("[name="+elementName+"]").val()
            },
            dataType : 'json',
            async : true,
            success : function(data) {
                var i = 0;
                if("searchUserName" == elementName){
                    var tBody = document.getElementById("searchUserDiv");
                    deleteRows(tBody);
                }
                else{
                    var tBody = document.getElementById("searchUserALDiv");
                    deleteRows(tBody);
                }
                $.each(data, function() {
                    if("searchUserName" == elementName){
                        addUserRow('searchUserDiv', 'searchUserChkbox', this);
                    }
                    else{
                        addUserRow('searchUserALDiv', 'searchUserALChkbox', this);
                    }
                });
            }
        });
    }

    function addMyProjectRow(bodyId, radioName, eachData){

        var tBody = document.getElementById(bodyId);
        var innerRow = tBody.insertRow();
        var innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerHTML = "<input type='radio' name='"+ radioName +"' id='"+ radioName +"' value='"+ eachData.oid +"' onclick='projectRowOnClick(this)'>";
        
        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM text-left";
        innerCell.innerText = eachData.projectname;

        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerText = eachData.pm;
    }
    
    function projectRowOnClick(radio){
        $.ajax({
            url : "/plm/ext/wfm/workspace/searchProjectCFTMember.do",
            type : "POST",
            data : {
                projectoid : radio.value
            },
            dataType : 'json',
            async : true,
            success : function(data) {
                var i = 0;
                var tBody = document.getElementById("projectCFTMemberDiv");
                deleteRows(tBody);
                $.each(data, function() {
                    addUserRow('projectCFTMemberDiv', 'projectCFTChkbox', this);
                });
            }
        });
    }
    
    function searchMyProject(){
        $.ajax({
            url : "/plm/ext/wfm/workspace/searchMyProject.do",
            type : "POST",
            dataType : 'json',
            async : true,
            success : function(data) {
                var tBody = document.getElementById("inProgressProjDiv");
                deleteRows(tBody);
                $.each(data, function() {
                    addMyProjectRow('inProgressProjDiv', 'myProjectRadio', this);
                });
            }
        });
    }
    
    function searchMyFavorApprovalLine(){
        $.ajax({
            url : "/plm/ext/wfm/workspace/searchMyFavorApprovalLine.do",
            type : "POST",
            dataType : 'json',
            async : true,
            success : function(data) {
                var tBody = document.getElementById("approvalLineMemberDiv");
                deleteRows(tBody);
                $.each(data, function() {
                    addUserRow('approvalLineMemberDiv', 'approvalLineMemberChkbox', this);
                });
            }
        });
    }
    
    function addApprovalLine(){
        var checkedFlag = false;
        var useridArr = new Array();
        $('input[name=searchUserALChkbox]:checked').each(function() {
            checkedFlag = true;
            useridArr[useridArr.length] = $(this).val();
        });
        if(!checkedFlag){
            alert('등록할 사용자를 선택하여 주십시오.');
            return;
        }
        $.ajax({
            url : "/plm/ext/wfm/workspace/addFavorApprovalUser.do",
            type : "POST",
            data : decodeURIComponent($.param({userids : useridArr}, true)),
            dataType : 'json',
            async : true,
            success : function(flag) {
                if(flag){
                    searchMyFavorApprovalLine();
                }
                else{
                    alert('등록 중 에러가 발생하였습니다.\n관리자에게 문의하여 주십시오.');
                }
            }
        });
    }

    function deleteApprovalLine(){
        var checkedFlag = false;
        var useridArr = new Array();
        $('input[name=approvalLineMemberChkbox]:checked').each(function() {
            checkedFlag = true;
            useridArr[useridArr.length] = $(this).val();
        });
        if(!checkedFlag){
            alert('삭제할 사용자를 선택하여 주십시오.');
            return;
        }
        $.ajax({
            url : "/plm/ext/wfm/workspace/deleteFavorApprovalUser.do",
            type : "POST",
            data : decodeURIComponent($.param({userids : useridArr}, true)),
            dataType : 'json',
            async : true,
            success : function(flag) {
                if(flag){
                    searchMyFavorApprovalLine();
                }
                else{
                    alert('삭제 중 에러가 발생하였습니다.\n관리자에게 문의하여 주십시오.');
                }
            }
        });
    }
    
</script>
</head>
<body>
  <form name="searchPeople" method="post">
    <input type="hidden" name="key" value="<%=People.NAME%>">
    <input type="hidden" name="memList" value="">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="50" valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="7"></td>
                    <td width="20"><img src="../../portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00774")%><%--결재선지정--%></td>
                  </tr>
                </table></td>
              <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td height="30" align="right">
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteUser('multi');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><!-- 삭제 --></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteUser('all');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01375")%><!-- 모두삭제 --></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:parentSumbmit();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><!-- 확인 --></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td valign="top">
          <table style="width: 100%; height: 100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="10">&nbsp;</td>
              <td valign="top">
                <table style="width: 100%; height: 100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top" height="100%">
                      <table style="width: 100%; height: 100%" border="0" cellspacing="0" cellpadding="0">
                        <colgroup>
                          <col width="*">
                          <col width="70">
                          <col width="470">
                          <col width="10">
                        </colgroup>
                        <tr>
                          <td valign="top" height="575px;">
                            <div id="tabs">
                              <ul>
                                <li><a class="tabref" href="#tabs-1"><%=messageService.getString("e3ps.message.ket_message", "00705")%><!-- 검색 --></a></li>
                                <li><a class="tabref" href="#tabs-2"><%=messageService.getString("e3ps.message.ket_message", "02648")%><!-- 조직도 --></a></li>
                                <li><a class="tabref" href="#tabs-3"><%=messageService.getString("e3ps.message.ket_message", "03046")%><!-- 프로젝트 --></a></li>
                                <li><a class="tabref" href="#tabs-4"><%=messageService.getString("e3ps.message.ket_message", "05103")%><!-- 자주지정하는결재자 --></a></li>
                              </ul>
                              <div id="tabs-1">
                                <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
                                  <tr height="100%">
                                    <td valign="top">
                                      <table style="width: 100%; border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td>
                                            <input type="text" name="searchUserName" class="txt_field" style="width: 150px;"><input type="hidden" name="searchUser">
                                            <!-- <a href="javascript:SearchUtil.selectOneUser('searchUser','searchUserName');"><img src="/plm/portal/images/icon_user.gif" border="0"></a> -->
                                            <a href="javascript:CommonUtil.deleteValue('searchUser','searchUserName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                          </td>
                                          <td align="right">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:searchUser('searchUserName');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><!-- 검색 --></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="space5"></td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="tab_btm2"></td>
                                        </tr>
                                      </table>
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tr>
                                          <td class="tdblueM"><input type="checkbox" name="searchUserChkboxAll" onclick="selectAll(document.forms[0].searchUserChkboxAll, document.forms[0].searchUserChkbox)"></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02715")%><%--직위--%></td>
                                        </tr>
                                      </table>
                                      <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 500px;">
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tbody id="searchUserDiv"></tbody>
                                      </table>
                                      </div>
                                    </td>
                                  </tr>
                                </table>
                              </div>
                              <div id="tabs-2">
                                <iframe src="WfmDepartmentTree.jsp" name="deptTree" id="deptTree" frameborder="0" width="365" height="575px;" scrolling="auto" style="overflow-x:hidden;overflow-y:auto;"></iframe>
                              </div>
                              <div id="tabs-3">
                                <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
                                  <tr height="50%">
                                    <td valign="top">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "05101")%><%--참여중 프로젝트(진행중)--%></td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="space5"></td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="tab_btm2"></td>
                                        </tr>
                                      </table>
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="240">
                                          <col width="*">
                                        </colgroup>
                                        <tr>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로젝트명--%></td>
                                          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00370")%><%--PM--%></td>
                                        </tr>
                                      </table>
                                      <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 230px;">
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="240">
                                          <col width="*">
                                        </colgroup>
                                        <tbody id="inProgressProjDiv"></tbody>
                                      </table>
                                      </div>
                                    </td>
                                  </tr>
                                  <tr height="50%">
                                    <td valign="top">
                                      <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "05102")%><%--결재자(프로젝트CFT)--%></td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="space5"></td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="tab_btm2"></td>
                                        </tr>
                                      </table>
                                      <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tr>
                                          <td class="tdblueM"><input type="checkbox" name="projectCFTChkboxAll" onclick="selectAll(document.forms[0].projectCFTChkboxAll, document.forms[0].projectCFTChkbox)"></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00444")%><%--Role--%></td>
                                        </tr>
                                      </table>
                                      <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 230px;">
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tbody id="projectCFTMemberDiv"></tbody>
                                      </table>
                                      </div>
                                    </td>
                                  </tr>
                                </table>
                              </div>
                              <div id="tabs-4">
                                <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
                                  <tr height="50%">
                                    <td valign="top">
                                      <table style="width: 100%; border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td>
                                            <input type="text" name="searchUserALName" class="txt_field" style="width: 150px;"><input type="hidden" name="searchUserAL">
                                            <!-- <a href="javascript:SearchUtil.selectOneUser('searchUserAL','searchUserALName');"><img src="/plm/portal/images/icon_user.gif" border="0"></a> -->
                                            <a href="javascript:CommonUtil.deleteValue('searchUserAL','searchUserALName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                          </td>
                                          <td align="right">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:searchUser('searchUserALName');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><!-- 검색 --></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="space5"></td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="tab_btm2"></td>
                                        </tr>
                                      </table>
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tr>
                                          <td class="tdblueM"><input type="checkbox" name="searchUserALChkboxAll" onclick="selectAll(document.forms[0].searchUserALChkboxAll, document.forms[0].searchUserALChkbox)"></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02715")%><%--직위--%></td>
                                        </tr>
                                      </table>
                                      <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 220px;">
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tbody id="searchUserALDiv"></tbody>
                                      </table>
                                      </div>
                                    </td>
                                  </tr>
                                  <tr height="50%">
                                    <td valign="top">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "05103")%><%--자주 지정하는 결재자--%></td>
                                          <td align="right">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:addApprovalLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310")%><!-- 등록 --></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td align="right">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteApprovalLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><!-- 삭제 --></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="space5"></td>
                                        </tr>
                                      </table>
                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                          <td class="tab_btm2"></td>
                                        </tr>
                                      </table>
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tr>
                                          <td class="tdblueM"><input type="checkbox" name="approvalLineMemberChkboxAll" onclick="selectAll(document.forms[0].approvalLineMemberChkboxAll, document.forms[0].approvalLineMemberChkbox)"></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02715")%><%--직위--%></td>
                                        </tr>
                                      </table>
                                      <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 230px;">
                                      <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                          <col width="30">
                                          <col width="60">
                                          <col width="180">
                                          <col width="*">
                                        </colgroup>
                                        <tbody id="approvalLineMemberDiv"></tbody>
                                      </table>
                                      </div>
                                    </td>
                                  </tr>
                                </table>
                              </div>
                            </div>
                          </td>
                          <td height="575px">
                            <table height="575px" border="0" cellspacing="0" cellpadding="0" style="<%= (isDRR)?"display:none;":""%>">
                              <tr>
                                <td>
                                  <div style="position:absolute;top:200px;">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                        <td width="10" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:afterDiscuss();" class="btn_blue">&gt;&gt;</a></td>
                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                      </tr>
                                    </table>
                                  </div>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                <div style="position:absolute;top:400px;">
                                  <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                      <td width="10" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:discuss();" class="btn_blue">&gt;&gt;</a></td>
                                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                    </tr>
                                  </table>
                                  </div>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                <div style="position:absolute;top:600px;">
                                  <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                      <td width="10" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:beforeDiscuss();" class="btn_blue">&gt;&gt;</a></td>
                                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                    </tr>
                                  </table>
                                  </div>
                                </td>
                              </tr>
                            </table>
                            <table height="575px" border="0" cellspacing="0" cellpadding="0" style="<%= (isDRR)?"":"display:none;"%>">
                              <tr>
                                <td>
                                <div style="position:absolute;top:400px;">
                                  <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                      <td width="10" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:afterDiscuss();" class="btn_blue">&gt;&gt;</a></td>
                                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                    </tr>
                                  </table>
                                  </div>
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td valign="top" height="575px">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00727")%><%--검토및승인--%></td>
                                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td><a href="javascript:trDown('afterD','selectAfterD');" onMouseOut="MM_swapImgRestore()"
                                        onMouseOver="MM_swapImage('Image2511','','../../portal/images/btn_down_s.gif',1)"
                                      ><img src="../../portal/images/btn_down.gif" name="Image2511" border="0"></a></td>
                                      <td width="5"></td>
                                      <td><a href="javascript:trUp('afterD','selectAfterD');" onMouseOut="MM_swapImgRestore()"
                                        onMouseOver="MM_swapImage('Image2411','','../../portal/images/btn_up_s.gif',1)"
                                      ><img src="../../portal/images/btn_up.gif" name="Image2411" border="0"></a></td>
                                    </tr>
                                  </table></td>
                              </tr>
                            </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                              <tr>
                                <td class="space5"></td>
                              </tr>
                            </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                              <tr>
                                <td class="tab_btm2"></td>
                              </tr>
                            </table>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                <colgroup>
                                  <col width="40">
                                  <col width="60">
                                  <col width="160">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                                <tr>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01981")%><%--순서--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02714")%><%--직급--%></td>
                                  <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                </tr>
                            </table>
                            <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 143px;">
                            <select id="selectAfterD" multiple="multiple" style="display: none;"></select>
                            <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                <colgroup>
                                  <col width="40">
                                  <col width="60">
                                  <col width="160">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                              <tbody id="afterD"></tbody>
                            </table>
                            </div>
                            <div name="showDRR3" id="showDRR3" style="<%= (isDRR)?"display:none;":""%>">
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td class="space10"></td>
                                </tr>
                              </table>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                  <td width="57" class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03156")%><%--합의--%></td>
                                  <td width="205"><input name="radiob" type="radio" class="Checkbox" value="radio" onClick="javascript:saveType('noDiscuss');" checked> <%=messageService.getString("e3ps.message.ket_message", "03158")%><%--합의없음--%>
                                    <input name="radiob" type="radio" class="Checkbox" value="radio" onClick="javascript:saveType('sequential');"> <%=messageService.getString("e3ps.message.ket_message", "01983")%><%--순차--%>
                                    <input name="radiob" type="radio" class="Checkbox" value="radio" onClick="javascript:saveType('parallel');"> <%=messageService.getString("e3ps.message.ket_message", "01526")%><%--병렬--%></td>
                                  <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td><a href="javascript:trDown('discuss' , 'selectDiscuss');" onMouseOut="MM_swapImgRestore()"
                                          onMouseOver="MM_swapImage('Image251','','../../portal/images/btn_down_s.gif',1)"
                                        ><img src="../../portal/images/btn_down.gif" name="Image251" border="0"></a></td>
                                        <td width="5"></td>
                                        <td><a href="javascript:trUp('discuss' , 'selectDiscuss');" onMouseOut="MM_swapImgRestore()"
                                          onMouseOver="MM_swapImage('Image241','','../../portal/images/btn_up_s.gif',1)"
                                        ><img src="../../portal/images/btn_up.gif" name="Image241" border="0"></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                  <td class="space5"></td>
                                </tr>
                              </table>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                  <td class="tab_btm2"></td>
                                </tr>
                              </table>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                <colgroup>
                                  <col width="40">
                                  <col width="60">
                                  <col width="160">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                                <tr>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01981")%><%--순서--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02714")%><%--직급--%></td>
                                  <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                </tr>
                              </table>
                              <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 143px;">
                              <select id="selectDiscuss" multiple="multiple" style="display: none;"></select>
                              <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                <colgroup>
                                  <col width="40">
                                  <col width="60">
                                  <col width="160">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                                <tbody id="discuss"></tbody>
                              </table>
                              </div>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td class="space10"></td>
                                </tr>
                              </table>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                  <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03166")%><%--합의전검토--%></td>
                                  <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td><a href="javascript:trDown('beforeD','selectBeforeD');" onMouseOut="MM_swapImgRestore()"
                                          onMouseOver="MM_swapImage('Image25','','../../portal/images/btn_down_s.gif',1)"
                                        ><img src="../../portal/images/btn_down.gif" name="Image25" border="0"></a></td>
                                        <td width="5"></td>
                                        <td><a href="javascript:trUp('beforeD','selectBeforeD');" onMouseOut="MM_swapImgRestore()"
                                          onMouseOver="MM_swapImage('Image24','','../../portal/images/btn_up_s.gif',1)"
                                        ><img src="../../portal/images/btn_up.gif" name="Image24" border="0"></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                  <td class="space5"></td>
                                </tr>
                              </table>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                  <td class="tab_btm2"></td>
                                </tr>
                              </table>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                <colgroup>
                                  <col width="40">
                                  <col width="60">
                                  <col width="160">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                                <tr>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01983")%><%--순차--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02714")%><%--직급--%></td>
                                  <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                </tr>
                              </table>
                              <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 143px;">
                              <select id="selectBeforeD" multiple="multiple" style="display: none;"></select>
                              <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                <colgroup>
                                  <col width="40">
                                  <col width="60">
                                  <col width="160">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                                <tbody id="beforeD"></tbody>
                              </table>
                              </div>
                            </div>
                          </td>
                          <td width="10">&nbsp;</td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</body>
</html>