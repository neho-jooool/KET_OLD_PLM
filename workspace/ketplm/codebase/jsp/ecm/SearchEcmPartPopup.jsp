<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/tooltip.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">

#dhtmltooltip{
position: absolute;
width: 150px;
border: 2px solid black;
padding: 2px;
background-color: lightyellow;
visibility: hidden;
z-index: 100;
/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
}

</style>
<script language="javascript">
//페이지 조회
function gotoPage(pageno){
    var form = document.forms[0];
    form.cmd.value = "searchPartPopup";
    form.page.value = pageno;
    callSearch();
}

//자료 검색
function doSearch(){
    var form = document.forms[0];
    form.cmd.value = "searchEcmPartPopup";
    form.sortColumn.value = "1 ASC";
    form.page.value = "";
    form.totalCount.value = "0";
    callSearch();
}

//자료 검색
function callSearch(){
    var form = document.forms[0];
    var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
//  if(checkValidate()){
        form.target = "download";
        form.action = url;
        form.method = "post";
        //disabledAllBtn();
        //showProcessing();
        form.submit();
//  }
}

//처리중 화면 전환
function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
}

function onSelect() {
    if(!checkedCheck()) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "02912") %><%--코드를 선택하십시오--%>");
        return;
    }
    var form = document.forms[0];
    var arr = new Array();
    var subArr = new Array();
    var idx = 0;
    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                subArr = new Array();
                subArr[0] = form.oid[i].value;
                subArr[1] = form.oid[i].codecode;
                subArr[2] = form.oid[i].codename;
                subArr[3] = form.oid[i].codedesc;
                arr[idx++] = subArr;
            }
        }
    }else{
        if(form.oid.checked == true) {
            subArr = new Array();
            subArr[0] = form.oid.value;
            subArr[1] = form.oid.codecode;
            subArr[2] = form.oid.codename;
            subArr[3] = form.oid.codedesc;
            arr[idx++] = subArr;
        }
    }
    parent.selectModalDialog(arr);
}

function checkedCheck() {
    form = document.forms[0];
    if(form.oid == null) {
        return false;
    }

    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                return true;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            return true;
        }
    }

    return false;

}

function deleteAllList() {
    var body = document.getElementById("listTable");
    if (body.rows.length == 0) return;
    for (var i = body.rows.length; i > 0; i--) {
        body.deleteRow(i - 1);
    }
}

function addAllList(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var targetTable = document.getElementById("listTable");
    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);

        //선택
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "38";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='checkbox' value='" + trArr[0] + "' name='oid' codename='" + trArr[2] + "' codecode='" + trArr[1] + "' codedesc='" + trArr[4] + "' onClick='oneClick(this)'>";

        //순번
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "40";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[3];

        //부품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "345";
        newTd.className = "tdwhiteL";
        str = getTruncStr(trArr[2], 50);
        str = "<span onmouseover=\"javascript:ddrivetip('" + trArr[2] + "','yellow', 300);\" onmouseout=\"javascript:hideddrivetip();\">" + str + "</span>";
        newTd.innerHTML = str;

        //Ver
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "40";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[4];

        //부품번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "";
        newTd.className = "tdwhiteM0";
        str = getTruncStr(trArr[1], 50);
        newTd.innerHTML = str;
    }
}

function addNotFound() {
    var targetTable = document.getElementById("listTable");
    var trArr;
    var str = "";

    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);

    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "100%";
    newTd.colspan = "4";
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<font color='red'><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font>";
}

</script>

</head>
<body>
<div id="dhtmltooltip"></div>

<script type="text/javascript">

/***********************************************
* Cool DHTML tooltip script- ⓒ Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

var offsetxpoint=-60 //Customize x offset of tooltip
var offsetypoint=20 //Customize y offset of tooltip
var ie=document.all
var ns6=document.getElementById && !document.all
var enabletip=false
if (ie||ns6)
var tipobj=document.all? document.all["dhtmltooltip"] : document.getElementById? document.getElementById("dhtmltooltip") : ""

function ietruebody(){
return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
}

function ddrivetip(thetext, thecolor, thewidth){
if (ns6||ie){
if (typeof thewidth!="undefined") tipobj.style.width=thewidth+"px"
if (typeof thecolor!="undefined" && thecolor!="") tipobj.style.backgroundColor=thecolor
tipobj.innerHTML=thetext
enabletip=true
return false
}
}

function positiontip(e){
if (enabletip){
var curX=(ns6)?e.pageX : event.clientX+ietruebody().scrollLeft;
var curY=(ns6)?e.pageY : event.clientY+ietruebody().scrollTop;
//Find out how close the mouse is to the corner of the window
var rightedge=ie&&!window.opera? ietruebody().clientWidth-event.clientX-offsetxpoint : window.innerWidth-e.clientX-offsetxpoint-20
var bottomedge=ie&&!window.opera? ietruebody().clientHeight-event.clientY-offsetypoint : window.innerHeight-e.clientY-offsetypoint-20

var leftedge=(offsetxpoint<0)? offsetxpoint*(-1) : -1000

//if the horizontal distance isn't enough to accomodate the width of the context menu
if (rightedge<tipobj.offsetWidth)
//move the horizontal position of the menu to the left by it's width
tipobj.style.left=ie? ietruebody().scrollLeft+event.clientX-tipobj.offsetWidth+"px" : window.pageXOffset+e.clientX-tipobj.offsetWidth+"px"
else if (curX<leftedge)
tipobj.style.left="5px"
else
//position the horizontal position of the menu where the mouse is positioned
tipobj.style.left=curX+offsetxpoint+"px"

//same concept with the vertical position
if (bottomedge<tipobj.offsetHeight)
tipobj.style.top=ie? ietruebody().scrollTop+event.clientY-tipobj.offsetHeight-offsetypoint+"px" : window.pageYOffset+e.clientY-tipobj.offsetHeight-offsetypoint+"px"
else
tipobj.style.top=curY+offsetypoint+"px"
tipobj.style.visibility="visible"
}
}

function hideddrivetip(){
if (ns6||ie){
enabletip=false
tipobj.style.visibility="hidden"
tipobj.style.left="-1000px"
tipobj.style.backgroundColor=''
tipobj.style.width=''
}
}

document.onmousemove=positiontip

</script>
<form method="post" action="/plm/servlet/e3ps/SearchMoldEcoServlet">
<input type="hidden" name="cmd" value="searchPartPopup">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="param" value="parent.">
<input type="hidden" name="sortColumn" value="1 ASC">
<table width="720" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="7"></td>
              <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
              <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01566") %><%--부품 검색 팝업--%></td>

            </tr>
          </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><strong><img src="/plm/portal/images/icon_3.gif" width="10" height="10" align="absmiddle"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%> :</strong></td>
                    <td width="5"></td>
                    <td><input type="text" name="textfield" class="txt_field"  style="width:150" id="textfield"></td>
                    <td width="5"></td>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                        href="#" class="btn_blue" onclick="javascript:doSearch();" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table></td>
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
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="40" class="tdblueM" onMouseover="javascript:ddrivetip('JavaScriptKit.com JavaScript tutorials','yellow', 300);" onMouseout="javascript:hideddrivetip();"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></td>
                <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01980") %><%--순번--%></td>
                <td width="350" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td width="40" class="tdblueM">Ver</td>
                <td width="" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01603") %><%--부품코드--%></td>
              </tr>
            </table>
            <div style="height=233;width=100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border" id="listTable">
            </table>
            </div>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table height="30" border="0" cellspacing="0" cellpadding="0" width="100%" id="pageControlTable">
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                    href="#" class="btn_blue" onclick="javascript:onSelect();" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                <td>&nbsp;</td>

                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                    href="#" class="btn_blue" onclick="javascript:self.close();" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
<iframe name="download" align="center" width="700" height="200" border="0"></iframe>
</html>
