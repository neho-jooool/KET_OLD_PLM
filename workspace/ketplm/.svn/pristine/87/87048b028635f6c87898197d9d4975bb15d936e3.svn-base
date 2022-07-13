//<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
/**
 * @(#)	common.js
 * Copyright (c) e3ps. All rights reserverd
 *
 * @author Seung-hwan Choi, skyprda@e3ps.com
 */
function disabledAllBtn() {
    var f = document.forms[0];
    for ( var i = 0; i < f.length; i++) {
        if (f[i].type == "button") f[i].disabled = true;
    }
    f = document.getElementsByTagName('A');
    for ( var i = 0; i < f.length; i++) {
        f[i].disabled = true;
        // f[i].href = '#';
    }
}

function enabledAllBtn() {
    var f = document.forms[0];
    for ( var i = 0; i < f.length; i++) {
        if (f[i].type == "button") f[i].disabled = false;
    }
    f = document.getElementsByTagName('A');
    for ( var i = 0; i < f.length; i++) {
        f[i].disabled = false;
        // f[i].href = '#';
    }
}

function isNullData(str) {
    if (str.length == 0) return true;
    for ( var i = 0; i < str.length; i++)
        if (str.charCodeAt(i) != 32) return false;
    return true;
}

function checkField(obj, fieldName) {
    if (isNullData(obj.value)) {
        // alert( fieldName + unescape("%uC744%28%uB97C%29%20%uC785%uB825%uD558%uC138%uC694"));
        obj.focus();
        return true;
    }
    return false;
}

function checkFieldLength(obj, limit, fieldName) {
    if (!isNullData(obj.value)) {
        if (obj.value.length > limit) {
            // alert( fieldName + " " +limit + unescape("%uC790%uB9AC%uAE4C%uC9C0%20%uC785%uB825%uAC00%uB2A5%uD569%uB2C8%uB2E4"));
            obj.createTextRange();
            obj.select();
            alert(obj.value.length);
            return true;
        }
    }
    return false;
}

// textarea maxlength 지정하기
function Limit(obj) {
    var maxLength = parseInt(obj.getAttribute("maxlength"));
    if (obj.value.length > maxLength) {
        alert("내용은 "+maxLength+"자를 초과할 수 없습니다. 초과된 내용은 삭제됩니다.");
        obj.value = obj.value.substring(0, maxLength);
        return;
    }
}

function openWindow(url, name, width, height) {
    return getOpenWindow(url, name, width, height);
}

function openWindow2(url, name, width, height) {
	return getOpenWindow2(url, name, width, height);
}

function openWindow3(url, name, width, height) {
	return getOpenWindow3(url, name, width, height);
}

function getOpenWindow(url, name, width, height) {
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
    if (width == 'full') {
        // rest = "width=" + (screen.availWidth-10) + ",height=" + (screen.availHeight-60)+',left=0,top=0';

        leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
        toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

        rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
    } else {
        leftpos = (screen.availWidth - width) / 2;
        toppos = (screen.availHeight - 60 - height) / 2;

        rest = "width=" + width + ",height=" + height + ',left=' + leftpos + ',top=' + toppos;
    }
    
    var newname = changePopupNameForIE9(name);
    var newwin = open(url, newname, (opts + rest));
    newwin.focus();
    return newwin;
}

function getOpenWindow2(url, name, width, height, opts) {
    var opts = (typeof opts == "undefined") ? "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1" : opts;
    if (width == 'full') {
        // rest = "width=" + (screen.availWidth-10) + ",height=" + (screen.availHeight-60)+',left=0,top=0';

        leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
        toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

        rest = ",width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
    } else {
        leftpos = (screen.availWidth - width) / 2;
        toppos = (screen.availHeight - 60 - height) / 2;

        rest = ",width=" + width + ",height=" + height + ',left=' + leftpos + ',top=' + toppos;
    }
    
    var newname = changePopupNameForIE9(name);
    var newwin = open(url, newname, (rest + opts));
    newwin.focus();
    return newwin;
}

function getOpenWindow3(url, name, width, height) {
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
    if (width == 'full') {
        // rest = "width=" + (screen.availWidth-10) + ",height=" + (screen.availHeight-60)+',left=0,top=0';

        leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
        toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

        rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
    } else {
        leftpos = (screen.availWidth - width) / 2;
        toppos = (screen.availHeight - 60 - height) / 2;

        rest = "width=" + width + ",height=" + height + ',left=' + leftpos + ',top=' + toppos;
    }

    var newname = changePopupNameForIE9(name);
    var newwin = open(url, newname, (opts + rest));
    newwin.focus();
    return newwin;
}

window.openFullWindow = function(url,name,opts){
	
	var opts = (typeof opts == "undefined") ? "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1" : opts;
	
    leftpos = (screen.availWidth - screen.availWidth) / 2;
    toppos = (screen.availHeight - screen.availHeight) / 2;

    rest = "width=" + (screen.availWidth) + ",height=" + (screen.availHeight) + ',left=' + leftpos + ',top=' + toppos;
    var newname = changePopupNameForIE9(name);
    open(url, "BOMEditor", (rest + opts));
}

function reSubmit() {
    document.forms[0].submit();
}

function oneClick(check) {
    if (!check.checked) return;
    var chk = document.forms[0];
    str = check.value;
    for ( var i = 0; i < chk.length; i++) {
        if (chk[i].type == "checkbox") if (str != chk[i].value) chk[i].checked = false;
    }
}

function getSelect() {
    var chk = document.forms[0];
    var str = "";
    for ( var i = 0; i < chk.length; i++) {
        if (chk[i].type == "checkbox") {
            if (chk[i].checked == true) {
                str = chk[i].value;
                break;
            }
        }
    }
    return str;
}

function closeWindow() {
    if (opener != null)
        self.close();
    else
        history.back();
}

function isNotNumData(str) {
    if (str.length == 0) return true;
    for ( var i = 0; i < str.length; i++) {
        var sss = str.charCodeAt(i);
        if (48 > sss || 57 < sss) {
            return true;
        }
    }
    return false;
}

// TreeGrid 표시안되서 막음...
// function parseInt(str){
// var result = 0;
// var level = 1;
// for(var i=0; i<str.length-1; i++)
// level *= 10;
// for(var i=0;i<str.length;i++){
// var sss = str.charCodeAt(i)-48;
// result += (level * sss);
// level = level / 10;
// }
// return result;
// }

var screenWidth = screen.availWidth / 2 - 150;
var screenHeight = screen.availHeight / 2 - 75;

function openSameName(url, width, height, state) {
    var opt = launchCenter(width, height);
    if (state.length > 0) opt = opt + ", " + state
    var windowWin = window.open(url, "newwindow", opt);
    windowWin.resizeTo(width, height);
    windowWin.focus();
}

function openOtherName(url, name, width, height, state) {
    var opt = launchCenter(width, height);
    if (state.length > 0) opt = opt + ", " + state
    var windowWin = window.open(url, name, opt);
    windowWin.resizeTo(width, height);
    windowWin.focus();
}

function launchCenter(width, height) {
    var str = "height=" + height + ",innerHeight=" + height;
    str += ",width=" + width + ",innerWidth=" + width;
    if (window.screen) {
        var ah = screen.availHeight - 30;
        var aw = screen.availWidth - 10;

        var xc = (aw - width) / 2;
        var yc = (ah - height) / 2;

        str += ",left=" + xc + ",screenX=" + xc;
        str += ",top=" + yc + ",screenY=" + yc;
    }
    return str;
}
function addBgColorEvent() {
    var f = document.forms[0];
    for ( var i = f.length - 1; i > -1; i--) {
        f[i].onfocus = changeColor1;
        f[i].onblur = changeColor2;
    }
}
function changeColor1() {
    event.srcElement.style.backgroundColor = '#efefef';
}
function changeColor2() {
    event.srcElement.style.backgroundColor = '#ffffff';
}

function printTitle(_title) {
    document.write("<table border=0 cellpadding=0 cellspacing=0 >");
    document.write("<tr>");
    document.write("<td><img src=/plm/portal/images/title2_left.gif></td>");
    document.write("<td background=/plm/portal/images/title_back.gif>");
    document.write(_title);
    document.write("</td>");
    document.write("<td><img src=/plm/portal/images/title2_right.gif></td>");
    document.write("</tr>");
    document.write("</table>");
}

// get length of String
function getBytes(_value) {
    var count = 0;
    var tmpStr = new String(_value);

    var onechar;
    for ( var i = tmpStr.length - 1; i > -1; i--) {
        onechar = tmpStr.charAt(i);
        if (escape(onechar).length > 4)
            count += 3;
        else
            count += 1;
    }
    return count;
}

function selectOptionTrue(obj, val) {
    if (obj == null || val == null || val.length == 0) return;

    for ( var i = obj.length - 1; i > -1; i--) {
        if (obj[i].value == val) {
            obj[i].selected = true;
            break;
        }
    }
}

function selectCheckTrue(obj, val) {
    if (obj == null || val == null || val.length == 0) return;

    if (obj.length == null) {
        obj.checked = true;
    } else {
        for ( var i = obj.length - 1; i > -1; i--) {
            if (obj[i].value == val) {
                obj[i].checked = true;
                break;
            }
        }
    }
}

function COMMON_openPopup(nw, nh) {
    var opt, args = COMMON_openPopup.arguments;

    opt = "width=" + nw + ",height=" + nh + ",scrollbars=yes,resizable=yes" + ((args.length > 2) ? ",left=" + args[2] : "") + ((args.length > 3) ? "top=" + args[3] : "");
    return window.open("about:blank", "", opt);
}

function common_CheckStrLength(obj, maxmsglen) {

    var temp;
    var f = obj.value.length;
    var msglen = maxmsglen; // 최대 길이
    var tmpstr = "";
    var strlen;

    // 초기 최대길이를 텍스트 박스에 뿌려준다.
    for (k = 0; k < f; k++) {
        temp = obj.value.charAt(k);
        if (escape(temp).length > 4)
            msglen -= 1;
        else
            msglen--;
        if (msglen < 0) {
            alert("총 영문 / 한글 " + maxmsglen + "자 까지 쓰실 수 있습니다.");
            obj.value = tmpstr;
            break;
        } else {
            tmpstr += temp;
        }
    }
}
// KET Workflow

// 결재요청
function goPage(targetOid) {
    var addr = "/plm/jsp/wfm/RequestApproval.jsp?pboOid=" + targetOid;
    var topener = window.open(addr, "approval", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
    topener.focus();
}

// 결재내역 확인
function viewHistory(pboOid) {
    var addr = "/plm/jsp/wfm/ApprovalHistory.jsp?pboOid=" + pboOid;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
    //var topener = getOpenWindow2(addr, "history", 1024, 800, opts);
    var topener = getOpenWindow2(addr,'history','full',800,opts);
    // var topener = window.open(addr, "history" ,"toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=1024,height=800");
    topener.focus();
}

// 버전이력 확인
function viewVersionHistory(pboOid) {
    var addr = "/plm/servlet/e3ps/VersionHistoryServlet?oid=" + pboOid;
    window.open(addr, "버전이력보기", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=0,width=750,height=380");
}

/*******************************************************************************************************************************************************************************************************
 * ============================================================================= 입력값이 숫자인지를 확인한다. param : obj 입력폼 style='IME-MODE: disabled' : 입력창에 한글입력방지
 * ============================================================================
 */
function SetNum(obj) {
    val = obj.value;
    re = /[^0-9]/gi;
    obj.value = val.replace(re, "");
}

/**
 *
 */
function viewProcessHistoryInfo(oid) {
    openWindow("/plm/jsp/groupware/workprocess/ProcessHistoryInfo.jsp?oid=" + oid + "&isPopup=true", "ProcessHistoryInfo", 845, 245);
}

/*
 * 분리자를 이용하여 날짜의 유효성 체크 예) 2000.03.24 -> '.'을 이용하여 체크한다. @param inputDate 체크할 날짜 @param point 년,월,일 분리자
 */
function dateCheck(inputDate, point) {
    var dateElement = new Array(3);

    if (point != "") {
        dateElement = inputDate.split(point);
        if (inputDate.length != 10 || dateElement.length != 3) {
            return false;
        }
    } else {
        dateElement[0] = inputDate.substring(0, 4);
        dateElement[1] = inputDate.substring(4, 6);
        dateElement[2] = inputDate.substring(6, 9);
    }
    // 년도 검사
    if (!(1800 <= dateElement[0] && dateElement[0] <= 4000)) {
        return false;
    }

    // 달 검사
    if (!(0 < dateElement[1] && dateElement[1] < 13)) {
        return false;
    }

    // 해당 년도 월의 마지막 날
    var tempDate = new Date(dateElement[0], dateElement[1], 0);
    var endDay = tempDate.getDate();

    // 일 검사
    if (!(0 < dateElement[2] && dateElement[2] <= endDay)) {
        return false;
    }

    return true;
}

/*
 * 날짜 비교 종료일이 시작일 보다 작을때 false를 정상 기간일 경우 true를 리턴한다. @param startDate 시작일 @param endDate 종료일 @param point 날짜 구분자
 */
function dateCompare(startDate, endDate, point) {
    // 정상 날짜인지 체크한다.
    var startDateChk = dateCheck(startDate, point);
    if (!startDateChk) {
        return false;
    }

    var endDateChk = dateCheck(endDate, point, "end");

    if (!endDateChk) {
        return false;
    }

    // 년 월일로 분리 한다.
    var start_Date = new Array(3);
    var end_Date = new Array(3);

    if (point != "") {

        start_Date = startDate.split(point);
        end_Date = endDate.split(point);

        if (start_Date.length != 3 && end_Date.length != 3) {
            return false;
        }
    } else {

        start_Date[0] = startDate.substring(0, 4);
        start_Date[1] = startDate.substring(4, 6);
        start_Date[2] = startDate.substring(6, 9);

        end_Date[0] = endDate.substring(0, 4);
        end_Date[1] = endDate.substring(4, 6);
        end_Date[2] = endDate.substring(6, 9);
    }

    // Date 객체를 생성한다.

    var sDate = new Date(start_Date[0], getMonthStr(start_Date[1]), start_Date[2]);
    var eDate = new Date(end_Date[0], getMonthStr(end_Date[1]), end_Date[2]);

    if (sDate > eDate) {
        return false;
    }

    return true;
}

function viewDelegateHistoryPopup(oid) {
    var url = "/plm/ext/wfm/workspace/viewDelegateHistoryPopup.do";
    getOpenWindow2('/plm/jsp/common/loading2.jsp?url=' + url + '&key=workItemoid&value=' + oid, "viewDelegateHistoryPopup", 520, 350);
}

function openView(oid, width, height, isMail) {
	
	if(oid == null) return;
	
    var url, defaultWidth, defaultHeight, opts, popupName;
    if (oid.indexOf('WTPart') > 0) {
        url = '/plm/jsp/bom/ViewPart.jsp';
        defaultWidth = 'full'
//        defaultWidth = 1024; // 1024
//        defaultHeight = 768; // 759
//        opts = ',scrollbars=0,resizable=1';
    } else if (oid.indexOf('KETTechnicalDocument') > 0) {
        url = '/plm/jsp/dms/ViewTechDocumentPop.jsp?buttenView=T';
        defaultWidth = 900;
        defaultHeight = 600;
        opts = ',scrollbars=1,resizable=1';
    } else if (oid.indexOf('KETProjectDocument') > 0) {
        url = '/plm/jsp/dms/ViewDocument.jsp';
        defaultWidth = 1000;
        defaultHeight = 800;
        popupName = 'KETProjectDocumentPOPUP';
        opts = ',scrollbars=1,resizable=1';
    } else if (oid.indexOf('ReviewProject') > 0 || oid.indexOf('MoldProject') > 0 || oid.indexOf('ProductProject') > 0 || oid.indexOf('WorkProject') > 0) {
        url = '/plm/jsp/project/ProjectViewFrm.jsp';
        defaultWidth = 1100;
        defaultHeight = 800;
        opts = ',scrollbars=0,resizable=1';
    } else if (oid.indexOf('KETProgramSchedule') > 0) {
        url = '/plm/ext/project/program/viewProgramForm.do';
        defaultWidth = 800;
        defaultHeight = 620;
    } else if (oid.indexOf('EPMDocument') > 0) {
        url = '/plm/jsp/edm/ViewEPMDocument.jsp&key=ketCustomFlag&value=Y';
        defaultWidth = 820; // 800
        defaultHeight = 850; // 620
        opts = ',scrollbars=1,resizable=1';
    } else if (oid.indexOf('KETDevelopmentRequest') > 0) {
        url = '/plm/jsp/dms/ViewDevRequest.jsp&key=isPop&value=Y';
        defaultWidth = 1024;
        defaultHeight = 768;
    } else if (oid.indexOf('E3PSTask') > 0) {
        url = '/plm/jsp/project/ProjectViewFrm.jsp';
        defaultWidth = 1100;
        defaultHeight = 800;
        opts = ',scrollbars=0,resizable=1';
    } else if (oid.indexOf('GateAssessResult') > 0) {
        url = '/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y';
        defaultWidth = 1150;
        defaultHeight = 800;
    } else if (oid.indexOf('AssessSheet') > 0) {
        url = '/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y';
        defaultWidth = 1150;
        defaultHeight = 800;
    } else if (oid.indexOf('KETWfmMultiApprovalRequest') > 0) {
        url = '/plm/jsp/wfm/ViewMultiApproval.jsp';
        defaultWidth = 1024;
        defaultHeight = 768;
    } else if (oid.indexOf('KETTryPress') > 0) {
        url = '/plm/ext/project/trycondition/viewTryPressForm.do';
        defaultWidth = 900;
        defaultHeight = 800;
    } else if (oid.indexOf('KETTryMold') > 0) {
        url = '/plm/ext/project/trycondition/viewTryMoldForm.do';
        defaultWidth = 900;
        defaultHeight = 800;
    } else if (oid.indexOf('KETProdChangeRequest') > 0 || oid.indexOf('KETMoldChangeRequest') > 0 || oid.indexOf('KETCompetentDepartment') > 0 || oid.indexOf('KETMeetingMinutes') > 0) {
        url = '/plm/servlet/e3ps/ProdEcrServlet&key=cmd&value=PopupView';
        defaultWidth = 1024;
        defaultHeight = 768;
        popupName = 'KETChangePOPUP';
    } else if (oid.indexOf('KETProdChangeOrder') > 0 ) {
    	url = '/plm/servlet/e3ps/ProdEcoServlet&key=cmd&value=View';
        defaultWidth = 1024;
        defaultHeight = 768;
    } else if (oid.indexOf('KETMoldChangeOrder') > 0) {
    	url = '/plm/servlet/e3ps/MoldEcoServlet&key=cmd&value=View';
        defaultWidth = 1024;
        defaultHeight = 768;
    } else if (oid.indexOf('KETDrawingDistRequest') > 0) {
        url = '/plm/ext/edm/drawingDistRequestViewForm.do';
        defaultWidth = 1100;
        defaultHeight = 600;
    } else if (oid.indexOf('KETDqmHeader') > 0) {
    	url = '/plm/ext/dqm/dqmMainForm.do?type=view';
        defaultWidth = 1100;
        defaultHeight = 768;
    } else if (oid.indexOf('KETDqmAction') > 0) {
        url = '/plm/ext/dqm/dqmMainForm.do?type=action';
        defaultWidth = 1100;
        defaultHeight = 768;
    } else if (oid.indexOf('KETDqmRaise') > 0) {
        url = '/plm/ext/dqm/dqmMainForm.do?type=raise';
        defaultWidth = 1100;
        defaultHeight = 768;
    } else if (oid.indexOf('KETSampleRequest') > 0) {
        url = '/plm/ext/sample/sampleRequstMainForm.do';
        defaultWidth = 1024;
        defaultHeight = 670;
    } else if (oid.indexOf('KETBomHeader') > 0) {
        oid = oid.replace("e3ps.bom.entity.KETBomHeader:", "");
        url = '/plm/jsp/bom/WFBomView.jsp';
        defaultWidth = 1100;
        defaultHeight = 800;
    } else if (oid.indexOf('KETBomEcoHeader') > 0) {
        oid = oid.replace("e3ps.bom.entity.KETBomEcoHeader:", "");
        url = '/plm/jsp/bom/WFBomEcoView.jsp';
        defaultWidth = 1100;
        defaultHeight = 800;
    } else if (oid.indexOf('KETEarlyWarningPlan') > 0) {
        url = '/plm/jsp/ews/ViewEarlyWarningPlanPopup.jsp&key=planOid&value=' + oid;
        defaultWidth = 800;
        defaultHeight = 600;
    } else if (oid.indexOf('KETEarlyWarningEndReq') > 0) {
        url = '/plm/jsp/ews/ViewEarlyWarningEndReqPopup.jsp&key=endReqOid&value=' + oid;
        defaultWidth = 830;
        defaultHeight = 600;
    } else if (oid.indexOf('KETEarlyWarningEnd') > 0) {
        url = '/plm/jsp/ews/ViewEarlyWarningEndPopup.jsp&key=endOid&value=' + oid;
        defaultWidth = 830;
        defaultHeight = 600;
    } else if (oid.indexOf('KETEarlyWarning') > 0) {
        url = '/plm/jsp/ews/ViewEarlyWarningPopup.jsp';
        defaultWidth = 800;
        defaultHeight = 600;
    } else if (oid.indexOf('Performance') > 0) {
        url = '/plm/jsp/project/performance/ViewPerformance.jsp&key=cmd&value=view&key=pOid&value=' + oid;
        defaultWidth = 850;
        defaultHeight = 730;
    } else if (oid.indexOf('ProjectIssue') > 0) {
        url = '/plm/jsp/project/projectIssueView.jsp&key=oid&value=' + oid;
        defaultWidth = 750;
        defaultHeight = 700;
    } else if (oid.indexOf('KETAnalysisRequestMaster') > 0) {
        url = '/plm/ext/arm/master/armMasterViewForm.do';
        defaultWidth = 750;
        defaultHeight = 700;
    } else if (oid.indexOf('KETAnalysisRequestInfo') > 0) {
        url = '/plm/ext/arm/info/armInfoViewForm.do';
        defaultWidth = 750;
        defaultHeight = 700;        
    } else if (oid.indexOf('ImproveBoard') > 0) {
    	url = '/plm/jsp/groupware/help/improve/ViewImproveBoard_a.jsp?oid=' + oid;
        defaultWidth = 800;
        defaultHeight = 700;        
    } else if (oid.indexOf('KETSalesProject') > 0) {
    	url = '/plm/ext/sales/project/viewSalesProjectForm.do';
        defaultWidth = 1100;
        defaultHeight = 800;
    } else if (oid.indexOf('KETIssueMaster') > 0) {
    	url = '/plm/ext/issue/viewIssuePopup';
        defaultWidth = 1280;
        defaultHeight = 800;
        opts = ',scrollbars=1,resizable=1';
    } else if (oid.indexOf('KETIssueTask') > 0) {
    	url = '/plm/ext/issue/issueTaskPopup';
        defaultWidth = 900;
        defaultHeight = 720;
        opts = ',scrollbars=1,resizable=1';
    } else if (oid.indexOf('KETInvestMaster') > 0) {
    	url = '/plm/ext/invest/viewInvestPopup';
        defaultWidth = 1280;
        defaultHeight = 800;
        opts = ',scrollbars=1,resizable=1';
    } else if (oid.indexOf('KETInvestTask') > 0) {
    	url = '/plm/ext/invest/investTaskPopup';
        defaultWidth = 900;
        defaultHeight = 720;
        opts = ',scrollbars=1,resizable=1';
    }else if (oid.indexOf('KETSalesCSMeetingApproval') > 0) {
    	url = '/plm/ext/sales/project/viewSalesCSForm.do';
        defaultWidth = 1100;
        defaultHeight = 800;
    }else if (oid.indexOf('MoldMaterial') > 0) {
    	url = '/plm/ext/material/viewMaterialPopup';
        defaultWidth = 1280;
        defaultHeight = 800;
        opts = ',scrollbars=1,resizable=1';
    }else if (oid == 'notExist') {
    	alert('존재하지 않는 Object입니다.(상신취소 또는 일정변경취소 등의 사유)');
    	this.close();
    }
    
    if (!url) return;
    if(isMail){
    	/*window.resizeTo(popupName?popupName:oid, defaultWidth,defaultHeight);*/
    	window.resizeTo(defaultWidth,defaultHeight);
    	location.href = '/plm/jsp/common/loading2.jsp?url=' + url + '&key=oid&value=' + oid;
    }else{
    	getOpenWindow2('/plm/jsp/common/loading2.jsp?url=' + url + '&key=oid&value=' + oid, popupName?popupName:oid, width ? width : defaultWidth, height ? height : defaultHeight, opts);    	
    }
}

// ECO NO로 ECO 조회
function openViewEco(ecoId) {
	if(ecoId != "null"){
    getOpenWindow2('/plm/servlet/e3ps/ProdEcoServlet?cmd=View4EcoId&ecoId=' + ecoId, ecoId, 1024, 768, "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=0");
	}
}

//ECO NO로 ECO 조회 KETbomEdior.jsp 전용
function PartOpenViewEco(ecoId) {
	if(ecoId != "null"){
	ecoId = ecoId.replace(/-/g,"");//IE9 버전으로 실행되기 때문에 특수문자 들어가면 팝업이 실행안되므로 replace처리한다
    getOpenWindow2('/plm/servlet/e3ps/ProdEcoServlet?cmd=View4EcoId&ecoId=' + ecoId, ecoId, 1024, 768, "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=0");
	}
}

//부품번호로 부품조회
function openViewMatchingPart(partOid){
	
	if( typeof partOid == "undefined" || partOid == null || partOid == ""){
		return;
	}
	
	var url = "/plm/ext/part/base/viewMatchingPartPopup.do?partOid="+partOid;
    var name = "";
    defaultWidth = 900;
    defaultHeight = 300;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

// 부품번호로 부품조회
function openViewPart(partNo){
	partNo = partNo+"";
	if( typeof partNo == "undefined" || partNo == null || partNo == ""){
		return;
	}
	
	var _partNo = "";
	if(partNo.indexOf(",") == -1){
		_partNo = partNo;
	}else{
		var partNoArry = partNo.split(",");
		for(var i=0; i< partNoArry.length; i++){
			if(confirm("'" + partNoArry[i] + "' 부품을 조회하시겠습니까?")){
				_partNo = partNoArry[i];
				break;
			}
		}
	}
	
	if( typeof _partNo == "undefined" || _partNo == null || _partNo == "" || _partNo.indexOf(",") != -1){
		return;
	}
	
	var url = "/plm/ext/part/base/viewPartByNo.do?partNumber="+_partNo;
    var name = "";
    defaultWidth = 1024;
    defaultHeight = 768;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

//도면번호로 도면조회
function openViewEpm(epmNo){
	
	if( typeof epmNo == "undefined" || epmNo == null || epmNo == ""){
		return;
	}
	
	var _epmNo = "";
	if(epmNo.indexOf(",") == -1){
		_epmNo = epmNo;
	}else{
		var epmNoArry = epmNo.split(",");
		for(var i=0; i< epmNoArry.length; i++){
			if(confirm("'" + epmNoArry[i] + "' Drawing을 조회하시겠습니까?")){
				_epmNo = epmNoArry[i];
				break;
			}
		}
	}
	
	if( typeof _epmNo == "undefined" || _epmNo == null || _epmNo == "" || _epmNo.indexOf(",") != -1){
		return;
	}
	
	var url = "/plm/ext/part/base/viewEpmByNo.do?epmNumber="+_epmNo;
	var name = "";
	defaultWidth = 820;
	defaultHeight = 850;
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
	getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function openCostReport(taskOid) {
	url = "/plm/ext/cost/costReportPopup.do?taskOid="+taskOid;
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
	leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
	toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

	var rest = "width=" + (screen.availWidth * 0.9) + ",height="
			+ (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
			+ toppos;

	window.open(url, '', (opts + rest));
}

function openProject(pjtNo){
	var url, defaultWidth, defaultHeight, opts;
	url = '/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value='+pjtNo;
	getOpenWindow2('/plm/jsp/common/loading2.jsp?url=' + url, pjtNo, 1100, 800);
}

/**
 * 부품 조회화면에서 썸네일이 깨졌다 이유는
 * windchill OOTB extjs와 고도화에 사용한 extjs가 충돌이 발생함.
 * 해결방법은 일단 한가지 호환모드를 IE9으로 즉 <meta http-equiv="X-UA-Compatible" content="IE=9" />
 * 이렇게 하는 순간 팝업에 특수문자 들어가면 에러가 나 버려서
 * 아래와 같이 팝업 NAME에 :등이 들어간 경우 처리하고 있음. 
 * @param name
 * @returns
 */
function changePopupNameForIE9(name){
	var rename = name;
    try{
    	if(typeof name != "undefined" && name!= null){
    		if(rename.indexOf(':') > 0){
    			rename = rename.replace(/:/gi, "");
    			rename = rename.replace(/./gi, "");
    		}
    	}
    }catch(e){}
    
    return rename;
}

/**
 * 
 */
Array.prototype.contains = function(element) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == element) {
			return true;
		}
	}
	return false;
}

Array.prototype.remove = function() {
    var what, a = arguments, L = a.length, ax;
    while (L && this.length) {
        what = a[--L];
        while ((ax = this.indexOf(what)) !== -1) {
            this.splice(ax, 1);
        }
    }
    return this;
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

function sleep(milliseconds) {
	var start = new Date().getTime();
	for (var i = 0; i < 1e7; i++) {
		if ((new Date().getTime() - start) > milliseconds){
			break;
		}
	}
}
window.ajaxCallServer = function(requestURL, param, cbMethod, isProgress){
	
	var retVal = new Object();
	var isSync = false;
	if(cbMethod != null) isSync = true;
	if(isProgress == null) isProgress = true;
	if(isProgress){
		showProcessing();
	}
	
	var ajaxParam = {
		type : "POST",
		url : requestURL,
		data : param,
		async : isSync,
		success : function(res){
			
			if(res.message){
				alert(res.message);
			}
			var result = eval(res.result);
			
			if(result){
				
				if(cbMethod != null ) {
					cbMethod(res)
				}else{
					retVal = res;
				}
			}
			if(isProgress){
				hideProcessing();
			}
		},
		error : function(res, stat, error){
			window.console.log("ERROR PARAMETER : ", res);
			hideProcessing();
			alert("CODE : " + stat + "\n" + "MESSAGE : " + res.responseText + "\n" + "ERROR : " + error);
		}
	}
	
	try{
		if(typeof FormData != "undefined" && param instanceof FormData){
			ajaxParam.processData = false;
			ajaxParam.contentType = false;
		}else if(param instanceof Object){
			ajaxParam.contentType = "application/json";
			ajaxParam.data = JSON.stringify(param);
			ajaxParam.dataType = "json";
		}
	}catch(e){
		ajaxParam.contentType = "application/json";
		ajaxParam.data = JSON.stringify(param);
		ajaxParam.dataType = "json";
	}
	
	$.ajax(ajaxParam);
	
	return retVal;
}

window.checkNull = function(str){
	
	var type = typeof str;
	if('number' == type) str = str.toString();
	if('boolean' == type) str = str.toString();
	
	if (str == null || "undefined" == str.trim() || "null" == str.trim()) return "";
	
	return str;
}

var popupCache = new Object();
window.openPopup = function(url, name, width, height) {
	
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
    if (width == 'full') {
        // rest = "width=" + (screen.availWidth-10) + ",height=" + (screen.availHeight-60)+',left=0,top=0';

        leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
        toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

        rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
    } else {
        leftpos = (screen.availWidth - width) / 2;
        toppos = (screen.availHeight - 60 - height) / 2;

        rest = "width=" + width + ",height=" + height + ',left=' + leftpos + ',top=' + toppos;
    }
    
    if(!popupCache[name] || popupCache[name].closed){
    	popupCache[name] = open(url, name, (opts + rest));
    }else{
    	popupCache[name].location = url;
    	popupCache[name].focus();
    }
    
    return popupCache[name];
}

window.treeGridResize = function(gridId,divId,isApply){
	var agent = navigator.userAgent.toLowerCase();
    var isExplorer = false;
    if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {//ms 익스플로러 인지 확인
           isExplorer = true;
    }
    
    if(isApply){
    	isExplorer = false;
    }
    if(!isExplorer){
    	//크롬에서 그리드 리사이즈 하기 위한 코드 start
        var re_size = 150;
    
        $(divId).height($(window).height()-re_size);
    
        $(window).resize(function(){
            $(divId).height($(window).height()-re_size);
        });
        
    
        var timerId = null;
    
        var gridHeight = 0;
    
        window.editorResize = function(){
        
            var windowHeight = $(window).height();
            var gridHeight = $(gridId).height();
        
            window.console.log("gridHeight : "+gridHeight);
            window.console.log("windowHeight : "+windowHeight);
         
            //그리드가 로딩되어 높이가 측정되고 스크롤이 필요없을만큼 갯수가 적을 때 (그리드 높이가 낮을 때) 그리드 사이즈만큼 리사이즈 해준다    
            if(gridHeight != null && gridHeight < windowHeight){
             
                 $(divId).height($(window).height()-re_size);
                 $(window).resize(function(){
                       
                       $(divId).height($(window).height()-re_size);                   
                 });     
                
                 window.console.log($(divId).height());
             
             }
            if($(gridId).height() != null){
            
                clearInterval(timerId); 
            }
       }
    
       timerId = setInterval(editorResize, 100);
    
        //크롬에서 그리드 리사이즈 하기 위한 코드 end
    }
}


window.PLM_FILE_DOWNLOAD = function(url){
	
	var isIE = false;
    
    var agent = navigator.userAgent.toLowerCase();

    if( navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1 || (agent.indexOf("msie") != -1)) {
        isIE = true;
    }
    
    var download = document.getElementById("download") || document.getElementsByName("download")[0];
    
	if(!download){
		var iframe = document.createElement( 'iframe' );
		iframe.name = 'download';
		iframe.id = 'download';
		iframe.width = 0;
		iframe.height = 0;
		document.body.appendChild( iframe );
	}
    
    if(isIE){
        var winObj = null;
        var popName = "download";
        var ot = "width=0 height=0 menubar=no status=no";
        winObj = window.open(url, popName, ot); 
    }else{
    	$("#download").load(url, function(){
        });
    }
    
}

window.PLM_FILE_DOWNLOAD2 = function(url){
	var isIE = false;
    
    var agent = navigator.userAgent.toLowerCase();

    if( navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1 || (agent.indexOf("msie") != -1)) {
        isIE = true;
    }
    
    if(isIE){
        var winObj = null;
        var popName = "download";
        var ot = "width=0 height=0 menubar=no status=no";
        winObj = window.open(url, popName, ot); 
    }else{
    	var _width = '400';
        var _height = '300';
     
        // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
        var _left = Math.ceil(( window.screen.width - _width )/2);
        var _top = Math.ceil(( window.screen.height - _height )/2); 
     
        window.open(url, 'popup-test', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );         
    }
}

//select 태그 disabled 속성을 제거한다. submit시 값이 전달되지 않는 현상 방지
window.selectAttributeRemove = function(){
	var selectList = document.getElementsByTagName('select');
	
	var listLen = selectList.length;
    
    for(var i=0; i<listLen; i++){
        $('#' + selectList[i].id).removeAttr('disabled'); 
    }
}