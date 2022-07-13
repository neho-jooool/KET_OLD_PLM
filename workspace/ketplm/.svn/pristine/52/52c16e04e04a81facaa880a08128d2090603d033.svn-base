<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.web.ParamUtil,e3ps.common.query.SearchUtil,e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.*,wt.query.*,wt.util.*,wt.introspection.*,wt.org.*,wt.session.*" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*,e3ps.common.web.*" %>
<%@page import="wt.session.SessionHelper" %>
<%@page import="java.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    boolean isDRR = Boolean.parseBoolean(request.getParameter("isDRR")); //개발검토의뢰서 인지 여부 체크

    WTPrincipal creator = (WTPrincipal) SessionHelper.manager.getPrincipal();

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="UserLoading.html" %>
<script type="text/javascript">
startUserLoading();
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
    var selectObj3 = document.getElementById('selectAfterD');
    var tableObj3 = document.getElementById('afterD');
    var s3=selectObj3.length;

    if(type=='all'){
        var msg = confirm('<%=messageService.getString("e3ps.message.ket_message", "01376") %><%--모든 사용자를 삭제하시겠습니까?--%>');
        if(msg){
            while(s3>0){selectObj3.options[s3-1].selected=true; s3--;}
        }else return;
    }

    for(i=selectObj3.length-1; i>=0; i--){
        if(selectObj3.options[i].selected==true){
            selectObj3.removeChild(selectObj3.options[i]);
            tableObj3.deleteRow(selectObj3.length-i);

        }
    }sortRow(tableObj3 , tableObj3.rows.length);
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
                    if(arrayResponse!=""){
                    var tableID = document.getElementById(tID);
                    var selectBox = document.getElementById(sId);
                    var dataID = userArray;
                    var newTR = tableID.insertRow(0);
                    newTR.style.cursor='hand';
                    newTR.attachEvent('onclick',function(){selectMember(sId, dataID, newTR)});
                    var newTD1 = document.createElement('TD');
                    newTD1.innerHTML = trnumber;
                    newTD1.className = 'tdwhiteM';
                    newTD1.width = '41';
                    var newTD2 = document.createElement('TD');
                    if(tID!='discuss')newTD2.innerHTML = '<%=messageService.getString("e3ps.message.ket_message", "00716") %><%--검토--%>';
                    else newTD2.innerHTML = '<%=messageService.getString("e3ps.message.ket_message", "03156") %><%--합의--%>';
                    newTD2.className = 'tdwhiteM';
                    newTD2.width = '49';
                    var newTD3 = document.createElement('TD');
                    newTD3.innerHTML = arrayResponse[0];
                    newTD3.className = 'tdwhiteM';
                    newTD3.width = '100';
                    var newTD4 = document.createElement('TD');
                    newTD4.innerHTML = arrayResponse[1];
                    newTD4.className = 'tdwhiteM';
                    newTD4.width = '70';
                    var newTD5 = document.createElement('TD');
                    newTD5.innerHTML = arrayResponse[2];
                    newTD5.className = 'tdwhiteM0';
                    newTD5.width = '100';
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

function afterDiscuss() {

    var userArray = document.searchPeople.memList.value.split(',');
    var strArray = document.getElementById('selectAfterD');
    if(strArray.length+userArray.length>11){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01677", "10") %><%--사용자는 {0}명까지만 선택하실 수 있습니다--%>');
        return;
    }
    var tableObj = document.getElementById('afterD');
    var trnumber3 = strArray.length;
    checkDup = false;
    for(i=0; i<userArray.length; i++){
        if(userArray[i] == '<%=creator.getName()%>') {
            checkDup = true;
          alert('<%=messageService.getString("e3ps.message.ket_message", "05164") %><%--본인을 결재선에 등록할 수 없습니다.--%>');
        }
        for(j=0; j<strArray.length; j++){
            if(userArray[i] == strArray.options[j].value) {
                checkDup = true;
                alert("<%=messageService.getString("e3ps.message.ket_message", "00721") %><%--검토 및 승인에 동일한 사용자가 있습니다--%>");
            }
        }
        trnumber3++;
        if(!checkDup && (userArray[i]!="")) addTR(userArray[i], 'afterD', 'selectAfterD', trnumber3);
        checkDup = false;
    }
    document.frames("deptTree").unselectAll();
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
    oNewRow.attachEvent('onclick',function(){selectMember(sId, opt.value, oNewRow)});

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
    oNewRow.attachEvent('onclick',function(){selectMember(sId, opt.value, oNewRow)});

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

    var tableObj3 = document.getElementById('afterD');

    if(tableObj3.rows.length<1){
        alert('<%=messageService.getString("e3ps.message.ket_message", "00728") %><%--검토및승인에 사용자를 지정하여 주시기 바랍니다--%>');
        return;
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
        var obj3 = document.getElementById('selectAfterD');

        window.opener.delapproval();
        window.opener.saveSelectBox('type3',obj3,'approval');
        window.opener.addTR(totalArray , 'approval');
        self.close();
    }else {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02712") %><%--지정된 사용자가 없습니다--%>');
        return;
    }

}

function saveType(type){
    window.opener.requestApproval.discussType.value = type;
}

function chkEnter(code){
    if(code==13)search();
    else return;
}

</script>
</head>
<form name="searchPeople" method="post">
<body>
<input type="hidden" name="key" value="<%=People.NAME%>">
<input type="hidden" name="memList" value="">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00774") %><%--결재선지정--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="710" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="700" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="210" valign="top" height="100%"><table width="205" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="27"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="5"></td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><a href="javascript:menu('searchUser');"><img src="../../portal/images/tab9_1.png" name="searchUser" border="0"></a></td>
                                </tr>
                              </table></td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><a href="javascript:menu('tree');"><img src="../../portal/images/tab10_2.png" name="tree" border="0"></a></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td valign="top"><table width="205" height="100%" border="0" cellspacing="0" cellpadding="5" class="table_border2">
                          <tr>
                              <td>
                                <div id="psearch" >
                              <table border="0" cellspacing="0" cellpadding="0" width="185">
                                       <tr>
                                      <td class="tdblueL0"><%=messageService.getString("e3ps.message.ket_message", "01674") %><%--사용자 이름 검색--%></td>
                                    </tr>
                                    <tr>
                                      <td class="tdwhiteL0"><input name="keyvalue" type="text" class="txt_field" style="width:160" value="" onkeydown="javascript:chkEnter(event.keyCode);">
                                      <a href="javascript:search();"><img src="../../portal/images/icon_user.gif" border="0"></a></td>
                                    </tr>
                               </table>
                               <iframe src="/plm/jsp/wfm/SearchPeople.jsp" name="iframe" width=100% height=82% frameborder="0"></iframe>
                              </div>
                              <iframe src="WfmDepartmentTree.jsp" name="deptTree" id="deptTree" frameborder="0" width="190" height="100%" scrolling="yes" style="overflow-x:hidden;overflow-y:auto;display:none"></iframe>
                             </td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td width="5"></td>
                <td width="120" valign="top"><table width="100" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="27">&nbsp;</td>
                    </tr>
                    <tr>
                      <td valign="top"><table width="120" height="300" border="0" cellspacing="0" cellpadding="5" class="table_border3">
                          <tr>
                            <td align="center" valign="middle"><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td height="5"></td>
                                </tr>
                                <tr>
                                  <td><table border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                        <td width="86" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:afterDiscuss();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00727") %><%--검토및승인--%>&gt;&gt;</a></td>
                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                  <td height="40"></td>
                                </tr>
                                <tr>
                                  <td><table border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                        <td width="86" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteUser('multi');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                  <td height="5"></td>
                                </tr>
                                <tr>
                                  <td><table border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                        <td width="86" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteUser('all');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01375") %><%--모두삭제--%></a></td>
                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td width="10"></td>
                <td width="360" valign="top"><table border="0" cellspacing="0" cellpadding="0" width="360">
                    <tr>
                      <td class="space3"></td>
                    </tr>
                  </table>
                  <table width="360" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20"><img src="../../portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00727") %><%--검토및승인--%></td>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><a href="javascript:trDown('afterD','selectAfterD');"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2511','','../../portal/images/btn_down_s.gif',1)"><img src="../../portal/images/btn_down.gif" name="Image2511" border="0"></a></td>
                            <td width="5"></td>
                            <td><a href="javascript:trUp('afterD','selectAfterD');"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2411','','../../portal/images/btn_up_s.gif',1)"><img src="../../portal/images/btn_up.gif" name="Image2411" border="0"></a></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="360">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="360">
                    <tr>
                      <td class="tab_btm2"></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="360">
                    <tr>
                      <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01981") %><%--순서--%></td>
                      <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                      <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                      <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02714") %><%--직급--%></td>
                      <td width="100" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    </tr>
                  </table>
                <div style="width=360;height=260;overflow-x:hidden;overflow-y:auto;">
                  <table id="afterD" border="0" cellspacing="0" cellpadding="0" width="360">
                  <select id="selectAfterD"  multiple="multiple" style="display:none"></select>
                  </table>
                </div>
                  </td>
              </tr>
            </table>
            <table width="700" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td background="../../portal/images/btn_bg1.gif"><a href="javascript:parentSumbmit();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="710" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
