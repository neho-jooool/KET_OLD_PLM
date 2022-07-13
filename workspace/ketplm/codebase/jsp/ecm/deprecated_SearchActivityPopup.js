 /**
 * @(#)	createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//초기화면세팅
var tab = 1;
function init(ecoEcaCls) {
    var form = document.forms[0];
    clickTabBtn(ecoEcaCls);
}

function checkEnterKey(cls) {
    var eKey = window.event.keyCode;
    if (eKey == "13") {// Enter Key 처리
        if(cls == 1) {
            doSearchEpm();
        } else if(cls == 2) {
            doSearchBom();
        }
    }
}

//BOM 자료 검색
function doSearchBom() {

    var form = document.forms[0];
    // validation
      if(form.partNumber.value == "" && form.partName.value == ""){
          alert("하나 이상의 검색조건을 입력해 주십시오.");
          form.partName.focus();
          return;
      }
    form.cmd.value = "searchPartPopup";
    form.sortColumn.value = "2 ASC";
    form.page.value = "";
    form.totalCount.value = "0";
    var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
    form.target = "download";
    form.action = url;
    form.method = "post";
    /*
    disabledAllBtn();
    showProcessing();
    */
    form.submit();
}

function deleteBomAllList() {
    var body = document.getElementById("listBomTable");
    if (body.rows.length == 0) return;
    for (var i = body.rows.length; i > 0; i--) {
        body.deleteRow(i - 1);
    }
}

function addBomAllList(objArr) {
    var targetTable = document.getElementById("listBomTable");
    var trArr;
    var str = "";

    if(objArr.length == 0)
    {
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);

        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "100%";
        newTd.colspan = "5";
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = "<font color='red'>검색된 항목이 없습니다.</font>";
    }

    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);

        //선택
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='checkbox' value='" + trArr[0] + "' name='bomoid' codename='" + trArr[2] + "' codecode='" + trArr[1] + "' codedesc='" + trArr[4]+ "' codecls='" + trArr[5] + "'>";

        //부품유형
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        if( trArr[6] == 'P' )
        {
            str = "&nbsp;제품&nbsp;";
        }
        else if( trArr[6] == 'D' )
        {
            str = "&nbsp;Die No&nbsp;";
        }
        else if( trArr[6] == 'M' )
        {
            str = "&nbsp;금형부품&nbsp;";
        }

        newTd.innerHTML = str;

        //부품번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width="165";
        newTd.setAttribute("title", trArr[1]);
        newTd.innerHTML = trArr[1];

        //부품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.setAttribute("title", trArr[2]);
        str = "";
        str += "<div class='ellipsis' style='width:245;'><nobr>";
        str += trArr[2] +"</nobr></div>";
        newTd.innerHTML = str;

        //Ver
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[4];

        //결재상태
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        str = "&nbsp;" + trArr[3] + "&nbsp;";
        newTd.innerHTML = str;
    }
}

function addBomNotFound() {
    var targetTable = document.getElementById("listBomTable");
    var trArr;
    var str = "";

    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);

    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "100%";
    newTd.colspan = "5";
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<font color='red'>검색된 항목이 없습니다.</font>";
}


//EPM 자료 검색
function doSearchEpm() {
    var form = document.forms[0];
    // validation
    if(form.docNumber.value == "" && form.docName.value == ""){
        alert("하나 이상의 검색조건을 입력해 주십시오.");
        form.docName.focus();
        return;
    }
    form.cmd.value = "searchEpmPopup";
    form.sortColumn.value = "2 ASC";
    form.page.value = "";
    form.totalCount.value = "0";
//	var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
    var url = "/plm/jsp/ecm/SearchActivityEpmPopupListForm.jsp";
    form.target = "download";
    form.action = url;
    form.method = "post";
    disabledAllBtn();
    showProcessing();
    form.submit();
}


function deleteEpmAllList() {
    var body = document.getElementById("listEpmTable");
    if (body.rows.length == 0) return;
    for (var i = body.rows.length; i > 0; i--) {
        body.deleteRow(i - 1);
    }
}

function addEpmAllList(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var targetTable = document.getElementById("listEpmTable");
    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);

        //선택
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='checkbox' value='" + trArr[0] + "' name='epmoid' codename='" + trArr[2] + "' codecode='" + trArr[1] + "' codedesc='" + trArr[4]+ "' codecls='" + trArr[6] + "'>";

        //도면번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width="150";
        newTd.setAttribute("title", trArr[1]);
        str = "";
        str += "<div class='ellipsis' style='width:150;'><nobr>";
        str += trArr[1] +"</nobr></div>";
        newTd.innerHTML = str;

        //도면명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.setAttribute("title", trArr[2]);
        newTd.width="225";
        str = "";
        str += "<div class='ellipsis' style='width:225;'><nobr>";
        str += trArr[2] +"</nobr></div>";
        newTd.innerHTML = str;

        //Ver
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr[4];

        //작성자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        str = "&nbsp;" + trArr[3] + "&nbsp;";
        newTd.innerHTML = str;

        //작성일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        str = "&nbsp;" + trArr[5] + "&nbsp;";
        newTd.innerHTML = str;
    }
}

function addEpmNotFound() {
    var targetTable = document.getElementById("listEpmTable");
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

var arr = new Array();
var idx = 0;

function onSelect() {
    if(!checkedCheck()) {
        alert("코드를 선택하십시오.");
        return;
    }
    var form = document.forms[0];
    if(form.workerId.value == '') {
//		alert("테스트를 위하여 ECO담당자를 임시로 user001로 지정합니다.");
//		form.workerName.value = "user001";
//		form.workerId.value = "wt.org.WTUser:25020";
        alert("담당자를 입력하여 주십시요.");
        return;
    }
    if(tab == 1) {
        oid = form.epmoid;
    } else if(tab == 2) {
        oid = form.bomoid;
    }
    var subArr = new Array();
    len = oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(oid[i].checked == true) {
                subArr = new Array();
                subArr[0] = tab;
                subArr[1] = oid[i].value;
                subArr[2] = oid[i].codecode;
                subArr[3] = oid[i].codename;
                subArr[4] = oid[i].codedesc;
                subArr[5] = form.workerId.value;
                subArr[6] = form.workerName.value;
                subArr[7] = oid[i].codecls;
                arr[idx++] = subArr;
            }
        }
    }else{
        if(oid.checked == true) {
            subArr = new Array();
            subArr[0] = tab;
            subArr[1] = oid.value;
            subArr[2] = oid.codecode;
            subArr[3] = oid.codename;
            subArr[4] = oid.codedesc;
            subArr[5] = form.workerId.value;
            subArr[6] = form.workerName.value;
            subArr[7] = oid.codecls;
            arr[idx++] = subArr;
        }
    }
    //onConfirm();
    parent.opener.insertRelEpmLine(arr);
    window.close();
}

function onConfirm() {
    parent.selectModalDialog(arr);
}

function checkedCheck() {
    form = document.forms[0];
    var oid;
    if(tab == 1) {
        oid = form.epmoid;
    } else if(tab == 2) {
        oid = form.bomoid;
    }

    if(oid == null) {
        return false;
    }
    len =oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(oid[i].checked == true) {
                return true;
            }
        }
    }
    else {
        if(oid.checked == true) {
            return true;
        }
    }

    return false;

}

//처리중 화면 전환
function clickTabBtn(tabId) {
    var tabEpm = document.getElementById("tabEpm");
    var tabBom = document.getElementById("tabBom");
    if(tabId == 1) {
        tab = 1;
        tabEpm.style.display = "block";
        tabBom.style.display = "none";
    } else {
        tab = 2;
        tabEpm.style.display = "none";
        tabBom.style.display = "block";
    }
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

//담당자검색 팝업
function popupUser(){
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    setUser(attacheMembers);
}

//담당자 검색 팝업 리턴 포맷
function setUser(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];
    form.workerId.value = trArr[0];
    form.workerName.value = trArr[4];
}

//담당자 초기화
function clearUser(){
    var form = document.forms[0];
    form.workerId.value = '';
    form.workerName.value = '';
}

//팝업 닫기
function close() {
    top.self.close();
}
