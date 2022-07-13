<%@page import="wt.org.WTPrincipal"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.web.ParamUtil, e3ps.common.query.SearchUtil,e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.*,wt.query.*,wt.util.*,wt.introspection.*, wt.org.WTUser" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*,e3ps.common.web.*" %>
<%@page import="wt.session.SessionHelper" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  WTPrincipal creator = (WTPrincipal) SessionHelper.manager.getPrincipal();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="UserLoading.html" %>
<script type="text/javascript">
//startUserLoading();

var checkDup = false;

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
  document.searchPeople1.target="iframe";
  document.searchPeople1.action = "SearchPeople.jsp";
  document.searchPeople1.submit();
}

function addUser(selMember){
  document.searchPeople1.memList.value = selMember;
}

function sortRow(tableObj, trSize) {

  for(i=0; i<tableObj.rows.length; i++){
    var number = tableObj.rows[i].childNodes[0].innerHTML;
    if( trSize-i != number )tableObj.rows[i].childNodes[0].innerHTML = trSize-i;
  }
}
function deleteUser(type){
  var selectObj1 = document.getElementById('selectReceiver');
  var selectObj2 = document.getElementById('selectReference');
  var tableObj1 = document.getElementById('receiver');
  var tableObj2 = document.getElementById('reference');
  var s1=selectObj1.length;
  var s2=selectObj2.length;

  if(type=='all'){
    var msg = confirm('<%=messageService.getString("e3ps.message.ket_message", "01376") %><%--모든 사용자를 삭제하시겠습니까?--%>');
    if(msg){
      while(s1>0){selectObj1.options[s1-1].selected=true; s1--;}
      while(s2>0){selectObj2.options[s2-1].selected=true; s2--;}
    }else return;
  }

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
}

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
          if(arrayResponse!=null){
          var tableID = document.getElementById(tID);
          var selectBox = document.getElementById(sId);
          var dataID = userArray;
          var newTR = tableID.insertRow(0);
          newTR.style.cursor='hand';
          newTR.addEventListener('click',function(){selectMember(sId, dataID, newTR)});
          var newTD1 = document.createElement('TD');
          newTD1.innerHTML = trnumber;
          newTD1.className = 'tdwhiteM';
          //newTD1.width = '40';
          var newTD2 = document.createElement('TD');
          newTD2.innerHTML = arrayResponse[0];
          newTD2.className = 'tdwhiteM';
          //newTD2.width = '130';
          var newTD3 = document.createElement('TD');
          newTD3.innerHTML = arrayResponse[1];
          newTD3.className = 'tdwhiteM';
          //newTD3.width = '100';
          var newTD4 = document.createElement('TD');
          newTD4.innerHTML = arrayResponse[2];
          newTD4.className = 'tdwhiteM0';
          //newTD4.width = '110';
          newTR.appendChild(newTD1);
          newTR.appendChild(newTD2);
          newTR.appendChild(newTD3);
          newTR.appendChild(newTD4);
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

function receiver(){

    var userArray = new Array();
    var active = $('#tabs').tabs('option', 'active');
    if(active == 0){ // 검색 탭
        userArray = getUserArray('searchUserChkbox');
    }
    else if(active == 1){ // 조직도 탭
        userArray = document.searchPeople1.memList.value.split(',');
    }
    else if(active == 2){ // 프로젝트 탭
        userArray = getUserArray('projectCFTChkbox');
    }
    else if(active == 3){ // 자주 지정하는 결재자 탭
        userArray = getUserArray('approvalLineMemberChkbox');
    }
    else{
        userArray = document.searchPeople1.memList.value.split(',');
    }
  var strArray = document.getElementById('selectReceiver');
  var tempArray = document.getElementById('selectReference');
  var parentObj = window.opener.document.requestApproval;
  var totalList = parentObj.bReview.value + parentObj.discuss.value + parentObj.aReview.value;
  var approvalArray = totalList.split(',');
  if(strArray.length+userArray.length>31){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01677", "30") %><%--사용자는 {0}명까지만 선택하실 수 있습니다--%>');
    return;
  }
  var tableObj = document.getElementById('receiver');
  var trnumber1 = strArray.length;

  checkDup = false;
  for(i=0; i<userArray.length; i++){
    if(userArray[i] == '<%=creator.getName()%>') {
        checkDup = true;
      alert('<%=messageService.getString("e3ps.message.ket_message", "05165") %><%--본인을 수신자로 선택하실 수 없습니다--%>');
    }
    for(j=0; j<strArray.length; j++){
      if(userArray[i] == strArray.options[j].value) {
          checkDup = true;
          alert("<%=messageService.getString("e3ps.message.ket_message", "01930") %><%--수신자에 동일한 사용자가 있습니다--%>");
          //return;
      }
    }
    for(var k=0; k<approvalArray.length; k++){
      if(userArray[i] == approvalArray[k] && approvalArray[k]!=''){
          checkDup = true;
          alert('<%=messageService.getString("e3ps.message.ket_message", "00773") %><%--결재선에 이미 지정되어 있는 사용자 입니다--%>');
        //return;
      }
    }
    for(k=0; k<tempArray.length; k++){
      if(userArray[i]==tempArray.options[k].value){
          checkDup = true;
          alert("<%=messageService.getString("e3ps.message.ket_message", "02770") %><%--참조자에 이미 지정된 사용자가 있습니다--%>");
        //return;
      }
    }
    trnumber1++;
    if(!checkDup && (userArray[i]!="")) addTR(userArray[i], 'receiver', 'selectReceiver', trnumber1);
    checkDup = false;
  }
  if(active == 0){ // 검색 탭
      unCheckedAll(document.forms[0].searchUserChkboxAll, document.forms[0].searchUserChkbox);
  }
  else if(active == 1){ // 조직도 탭
      document.frames.deptTree.unselectAll();
  }
  else if(active == 2){ // 프로젝트 탭
      unCheckedAll(document.forms[0].projectCFTChkboxAll, document.forms[0].projectCFTChkbox);
  }
  else if(active == 3){ // 자주 지정하는 결재자 탭
      unCheckedAll(document.forms[0].approvalLineMemberChkboxAll, document.forms[0].approvalLineMemberChkbox);
  }
}

function reference() {

  var userArray = new Array();
  var active = $('#tabs').tabs('option', 'active');
  if(active == 0){ // 검색 탭
      userArray = getUserArray('searchUserChkbox');
  }
  else if(active == 1){ // 조직도 탭
      userArray = document.searchPeople1.memList.value.split(',');
  }
  else if(active == 2){ // 프로젝트 탭
      userArray = getUserArray('projectCFTChkbox');
  }
  else if(active == 3){ // 자주 지정하는 결재자 탭
      userArray = getUserArray('approvalLineMemberChkbox');
  }
  else{
      userArray = document.searchPeople1.memList.value.split(',');
  }

  var strArray = document.getElementById('selectReference');
  var tempArray = document.getElementById('selectReceiver');
  var parentObj = window.opener.document.requestApproval;
  var totalList = parentObj.bReview.value + parentObj.discuss.value + parentObj.aReview.value;
  var approvalArray = totalList.split(',');
  if(strArray.length+userArray.length>31){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01677", "30") %><%--사용자는 {0}명까지만 선택하실 수 있습니다--%>');
    return;
  }
  var tableObj = document.getElementById('reference');
  var trnumber3 = strArray.length;
  checkDup = false;
  for(i=0; i<userArray.length; i++){
    if(userArray[i] == '<%=creator.getName()%>') {
        checkDup = true;
      alert('<%=messageService.getString("e3ps.message.ket_message", "05166") %><%--본인을 참조자로 선택하실 수 없습니다--%>');
    }
    for(j=0; j<strArray.length; j++){
      if(userArray[i] == strArray.options[j].value) {
        checkDup = true;
        alert("<%=messageService.getString("e3ps.message.ket_message", "02769") %><%--참조자에 동일한 사용자가 있습니다--%>");
        //return;
      }
    }
    for(var k=0; k<approvalArray.length; k++){
      if(userArray[i] == approvalArray[k]&& approvalArray[k]!=''){
          checkDup = true;
          alert('<%=messageService.getString("e3ps.message.ket_message", "00773") %><%--결재선에 이미 지정되어 있는 사용자 입니다--%>');
        //return;
      }
    }
    for(k=0; k<tempArray.length; k++){
      if(userArray[i]==tempArray.options[k].value){
          checkDup = true;
          alert("<%=messageService.getString("e3ps.message.ket_message", "01930") %><%--수신자에 동일한 사용자가 있습니다--%>");
        //return;
      }
    }
    trnumber3++;
    if(!checkDup && (userArray[i]!="")) addTR(userArray[i], 'reference', 'selectReference', trnumber3);
    checkDup = false;
  }
  if(active == 0){ // 검색 탭
      unCheckedAll(document.forms[0].searchUserChkboxAll, document.forms[0].searchUserChkbox);
  }
  else if(active == 1){ // 조직도 탭
      document.frames.deptTree.unselectAll();
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
  var totalArray = new Array();
  var totalcnt = 0;
  //var tableObj1 = document.getElementById('receiver');
  //var tableObj2 = document.getElementById('reference');
  var tableObj1 = document.getElementById('reference');
  var tableObj2 = document.getElementById('receiver');

  for(i=tableObj1.rows.length-1; i>=0; i--){
    var targetArray = new Array(5);
    for(j=0; j<tableObj1.rows[i].childNodes.length+1; j++){
      if(j==0) {
        targetArray[j]=tableObj1.rows[i].childNodes[j].innerText
      }
      else if(j==1) {
        //targetArray[j]='수신자';
        targetArray[j]='참조자';
      }
      else {
        targetArray[j] = tableObj1.rows[i].childNodes[j-1].innerText;
      }
    }
    totalArray[totalcnt] = targetArray;
    totalcnt++;
  }

  for(i=tableObj2.rows.length-1; i>=0; i--){
    var targetArray = new Array(5);
    for(j=0; j<tableObj2.rows[i].childNodes.length+1; j++){
      if(j==0) {
        targetArray[j]=tableObj2.rows[i].childNodes[j].innerText
      }
      else if(j==1) {
        //targetArray[j]='참조자';
        targetArray[j]='수신자';
      }
      else {
        targetArray[j] = tableObj2.rows[i].childNodes[j-1].innerText;
      }
    }
    totalArray[totalcnt] = targetArray;
    totalcnt++;
  }

  var obj4 = document.getElementById('selectReceiver');
  var obj5 = document.getElementById('selectReference');

  window.opener.deldistribute();
  window.opener.saveSelectBox('type4',obj4,'distribute');
  window.opener.saveSelectBox('type5',obj5,'distribute');
  window.opener.addTR(totalArray , 'distribute');
  self.close();
<%-- 
  if(totalArray.length!=0){
    var obj4 = document.getElementById('selectReceiver');
    var obj5 = document.getElementById('selectReference');

    window.opener.deldistribute();
    window.opener.saveSelectBox('type4',obj4,'distribute');
    window.opener.saveSelectBox('type5',obj5,'distribute');
    window.opener.(totalArray , 'distribute');
    self.close();
  }else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02712") %>지정된 사용자가 없습니다');
    return;
  }
 --%>
}

function chkEnter(code){
  if(code==13)search();
  else return;
}
</script>

<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<%@include file="/jsp/common/multicombo.jsp"%>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
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
        
        // 팝업 오픈 시 지정되어 있는 배포처 셋팅
        var v_receiver = opener.document.requestApproval.receiver.value;
        var v_reference = opener.document.requestApproval.reference.value;

        var userArray = v_receiver.split(",");
        var trnumber =  document.getElementById('selectReceiver').length;
        for(var i=0; i < userArray.length-1; i++){
            addTR(userArray[i], 'receiver', 'selectReceiver', ++trnumber);
        }
        userArray = v_reference.split(",");
        trnumber =  document.getElementById('selectReference').length;
        for(var i=0; i < userArray.length-1; i++){
            addTR(userArray[i], 'reference', 'selectReference', ++trnumber);
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
<form name="searchPeople1" method="post">
<body onLoad="MM_preloadImages('../../portal/images/btn_up_s.gif','../../portal/images/btn_down_s.gif')">
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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01479")%><%--배포처지정--%></td>
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
                            <table height="575px" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td>
                                  <div name="showDRR2" id="showDRR2" style="position:absolute;top:240px;">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                        <td width="10" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:receiver();" class="btn_blue">&gt;&gt;</a></td>
                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                      </tr>
                                    </table>
                                  </div>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                <div style="position:absolute;top:520px;">
                                  <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                      <td width="10" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:reference();" class="btn_blue">&gt;&gt;</a></td>
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
                                  <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01929")%><%--수신자--%></td>
                                  <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td><a href="javascript:trDown('receiver','selectReceiver');" onMouseOut="MM_swapImgRestore()"
                                          onMouseOver="MM_swapImage('Image25','','../../portal/images/btn_down_s.gif',1)"
                                        ><img src="../../portal/images/btn_down.gif" name="Image25" border="0"></a></td>
                                        <td width="5"></td>
                                        <td><a href="javascript:trUp('receiver','selectReceiver');" onMouseOut="MM_swapImgRestore()"
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
                                  <col width="200">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                                <tr>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01981")%><%--순서--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02714")%><%--직급--%></td>
                                  <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                </tr>
                              </table>
                              <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 215px;">
                              <select id="selectReceiver" multiple="multiple" style="display: none;"></select>
                                <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                  <colgroup>
                                  <col width="40">
                                  <col width="200">
                                  <col width="90">
                                  <col width="*">
                                  </colgroup>
                                  <tbody id="receiver"></tbody>
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
                                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02768")%><%--참조자--%></td>
                                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td><a href="javascript:trDown('reference','selectReference');" onMouseOut="MM_swapImgRestore()"
                                        onMouseOver="MM_swapImage('Image2511','','../../portal/images/btn_down_s.gif',1)"
                                      ><img src="../../portal/images/btn_down.gif" name="Image2511" border="0"></a></td>
                                      <td width="5"></td>
                                      <td><a href="javascript:trUp('reference','selectReference');" onMouseOut="MM_swapImgRestore()"
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
                                  <col width="200">
                                  <col width="90">
                                  <col width="*">
                              </colgroup>
                              <tr>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01981")%><%--순서--%></td>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02714")%><%--직급--%></td>
                                <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                              </tr>
                            </table>
                            <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 215px;">
                              <select id="selectReference" multiple="multiple" style="display: none;"></select>
                              <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                <colgroup>
                                  <col width="40">
                                  <col width="200">
                                  <col width="90">
                                  <col width="*">
                                </colgroup>
                                <tbody id="reference"></tbody>
                              </table>
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
</body>
</html>
