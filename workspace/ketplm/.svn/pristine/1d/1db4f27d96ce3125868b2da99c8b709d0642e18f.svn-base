 /**
 * @(#)	createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//목록조회
function doSearch(){
    var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
    var form = document.forms[0];
    form.cmd.value = "searchMoldPlanPopup";
    form.target = "download";
    form.action = url;
    form.method = "post";
    disabledAllBtn();
    showProcessing();
    form.submit();
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
        alert("코드를 선택하십시오.");
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
                subArr[4] = form.oid[i].codedate;
                arr[idx++] = subArr;
            }
        }
    }else{
        if(form.oid.checked == true) {
            subArr = new Array();
            subArr[0] = form.oid.value;//oid
            subArr[1] = form.oid.codecode;//dieNo
            subArr[2] = form.oid.codename;//scheduleId
            subArr[3] = form.oid.codedesc;//partNo
            subArr[4] = form.oid.codedate;//activity date
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
        newTd.width = "40";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='checkbox' value='" + trArr[0] + "' name='oid' codename='" + trArr[1] + "' codecode='" + trArr[2] + "' codedesc='" + trArr[3] + "' codedate='"+trArr[7]+"' onClick='oneClick(this)'>";

        //일정 번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "110";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[1];

        //Die No
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "90";
        newTd.className = "tdwhiteM";
        str = "&nbsp;" + trArr[2] + "&nbsp;";
        newTd.innerHTML = str;

        //부품번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "100";
        newTd.className = "tdwhiteM";
        str = "&nbsp;" + trArr[3] + "&nbsp;";
        newTd.innerHTML = str;

        //부품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "185";
        newTd.className = "tdwhiteL";
        str ="<font title=\""+trArr[4]+"\">";
        str += "<div class='ellipsis' style='width:185;'><nobr>";
        str += trArr[4] +"</nobr></div></font>";
        newTd.innerHTML = str;

        //계획수립일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "80";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[7];

        //등록자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "80";
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = trArr[6];
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
    newTd.colspan = "7";
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<font color='red'>검색된 항목이 없습니다.</font>";
}


