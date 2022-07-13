 /**
 * @(#)	createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//화면 닫기
function close() {
    top.close();
}

//처리중 화면 전환
function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
}

//(공통)목록전체 선택/해제
function checkAll(formName, checkboxName, allCheckName) {
    var formNameStr = "document." + formName + ".";
    var objChecks = eval(formNameStr + checkboxName);
    var objAllChecks = eval(formNameStr + allCheckName);
    if(objChecks) {
        var chkLen = objChecks.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                objChecks[i].checked = objAllChecks.checked;
            }
        }else{
            objChecks.checked = objAllChecks.checked;
        }
    }
}

//입력항목 체크
function checkValidate(){
    var form = document.forms[0];
//	if(isNullData(form.ecrName.value)) {
//		alert("제목을 입력하여 주십시오");
//		form.ecrName.focus();
//		return false;
//	}
    return true;
}

//자료 검색
function doSearch(){
    var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
    if(checkValidate()){
        document.forms[0].target = "download";
        document.forms[0].action = url;
        document.forms[0].method = "post";
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

//작성자 검색 팝업
function popupUser(){
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url, window, "help=no; toolbar=no; location=no; directory=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    setUser(attacheMembers);
}

//작성자 검색 팝업 리턴 포맷
function setUser(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];
    form.creatorId.value = trArr[0];
    form.creatorName.value = trArr[4];
}

//작성자 초기화
function clearUser(){
    var form = document.forms[0];
    form.creatorId.value = '';
    form.creatorName.value = '';
}

//작성일자 초기화
function clearCreateCal(objName){
    eval("document.forms[0]." + objName).value = '';
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
                subArr[1] = form.oid[i].subject;
                subArr[2] = form.oid[i].creatorname;
                subArr[3] = form.oid[i].createdate;
                arr[idx++] = subArr;
            }
        }
    }else{
        if(form.oid.checked == true) {
            subArr = new Array();
            subArr[0] = form.oid.value;
            subArr[1] = form.oid.subject;
            subArr[2] = form.oid.creatorname;
            subArr[3] = form.oid.createdate;
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

    var oneclick = "";
    if(mode == "single") {
        oneclick = " onclick='javascript:oneClick(this);'";
    }
    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);

        //선택
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='checkbox' value='" + trArr[0] + "' name='oid' deptname='" + trArr[6] + "' ecrid='" + trArr[1] + "' creatorname='" + trArr[3] + "' approvedate='" + trArr[5] + "'" + oneclick + ">";

        //ECR 번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = trArr[1];

        //ECR 제목
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        var str = getTruncStr(trArr[2], 50);
        newTd.innerHTML = "&nbsp;" + str + "&nbsp;";

        //작성자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";

        //작성일자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";

        //승인일자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = "&nbsp;" + trArr[5] + "&nbsp;";
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
    newTd.colspan = "6";
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<font color='red'>검색된 항목이 없습니다.</font>";
}

