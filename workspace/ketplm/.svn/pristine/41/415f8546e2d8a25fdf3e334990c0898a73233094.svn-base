<%@page import="java.util.Vector" %>
<%@page import="java.util.Hashtable" %>
<%@page import="java.util.ArrayList" %>
<%@page import="org.apache.commons.lang.StringUtils" %>

<%@page import="wt.content.ContentHolder
                            ,wt.content.ContentHelper" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="wt.org.WTUser" %>
<%@page import="wt.session.SessionHelper" %>

<%@page import="wt.util.WTProperties" %>

<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.ecm.servlet.MoldEcoServlet" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.web.ParamUtil" %>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrder" %>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrderVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLink" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECALinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldEcoEcrLinkVO" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.beans.EcmSearchHelper" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="ketMoldChangeOrderVO" class="e3ps.ecm.entity.KETMoldChangeOrderVO" scope="request" />
<%
WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
String loginUserOid = (String)CommonUtil.getOIDString(user);

WTUser owner = (WTUser)ketMoldChangeOrderVO.getKetMoldChangeOrder().getCreator().getPrincipal();
String isCompleteModify = "N";
boolean isViewableState = true;
if( ketMoldChangeOrderVO.getProgressState().equalsIgnoreCase("ACTIVITYCOMPLETED") )
{
    isCompleteModify = "Y";
}

if("EXCUTEACTIVITY".equals(ketMoldChangeOrderVO.getProgressState()) ) {
    isViewableState = false;
}

boolean isOwner = false;
boolean isEpmDoc = false;
boolean isBom = false;
boolean isDoc = false;
boolean isEcn = false;

String readOnly = "";
String disabled = "";
if( owner.equals( user ) ) {
    isOwner = true;
}

String ecaType = ParamUtil.getParameter(request, "ecaType", "1");//(1:도면, 2:BOM, 3:표준품, 4:ECN)
if("1".equals(ecaType)) {
    isEpmDoc = true;
} else if("2".equals(ecaType)) {
    isBom = true;
} else if("3".equals(ecaType)) {
    isDoc = true;
} else if("4".equals(ecaType)) {
    isEcn = true;
}

int size = 0;
String chkChangeReason[] = new String[9];
String chkIncreaseProdType[] = new String[12];
String codeChangeReason[] = new String[9];
String codeIncreaseProdType[] = new String[12];
if(ketMoldChangeOrderVO.getTotalCount() > 0) {
    if(loginUserOid.equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoWorkerId())) {//로그인사용자oid=eco담당자oid
        isOwner = true;
    }
    if(!isOwner) {
        readOnly = " readOnly";
        disabled = " disabled";
    }
    ArrayList arrChangeReason = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getChangeReason(), "|");
    ArrayList arrIncreaseProdType = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getIncreaseProdType(), "|");
    int i = 0;
    int idx = 0;
    for(i=0; i<9; i++) {
        chkChangeReason[i] = "";
        codeChangeReason[i] = "REASON_" + (i+1);
    }
    for(i=0; i<12; i++) {
        chkIncreaseProdType[i] = "";
        if( i+1 < 10)
        {
            codeIncreaseProdType[i] ="INCR_0" + (i+1);
        }
        else
        {
            codeIncreaseProdType[i] ="INCR_" + (i+1);
        }
    }
    size = arrChangeReason.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrChangeReason.get(i), codeChangeReason);
        if(idx >= 0) {
            chkChangeReason[idx] = "checked";
        }
    }
    size = arrIncreaseProdType.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrIncreaseProdType.get(i), codeIncreaseProdType);
        if(idx >= 0) {
            chkIncreaseProdType[idx] = "checked";
        }
    }
} else {
    if(!"".equals(StringUtil.checkNull(ketMoldChangeOrderVO.getOid()))) {
%>
<script language="javascript">
alert('<%=messageService.getString("e3ps.message.ket_message", "02655") %><%--조회된 자료가 없습니다--%>');
</script>
<%
    }
}



//변경 전
String webEditor = "";
String webEditorText = "";

//변경 후
String webEditor1 = "";
String webEditorText1 = "";

if(ketMoldChangeOrderVO.getKetMoldChangeOrder() != null) {
	webEditor = ((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditor());
	webEditorText = ((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditorText());
	
	webEditor1 = ((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditor1());
	webEditorText1 = ((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditorText1());
}


// 초도인지 아닌지
String changeReason = StringUtils.stripToEmpty(ketMoldChangeOrderVO.getKetMoldChangeOrder().getChangeReason());
boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01031") %><%--금형ECO 변경 활동--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<!-- script language=JavaScript src="/plm/jsp/ecm/CreateMoldEcoChange.js"></script -->
<script language="javascript">
function clickTabBtn(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabActivity = document.getElementById("tabActivity");
    var tabEcn = document.getElementById("tabEcn");
    
    if(tabId == 1) {
        tabBasic.style.display = "block";
        tabActivity.style.display = "none";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 2;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabBasic';
        
    } else if(tabId == 2) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "block";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "block";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 2;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabActivity';
        
    } else if(tabId == 3) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "none";
        tabEcn.style.display = "block";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "block";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 2;
        
        document.forms[0].tabName.value = 'tabEcn';

      }
}

//처리중 화면 전환
/* 
function clickTabBtn(tabId) {
	var tabBasic = document.getElementById("tabBasic");
	var tabActivity = document.getElementById("tabActivity");
	if(tabId == 1) {
	  tabBasic.style.visibility = "visible";
	  tabActivity.style.visibility = "hidden";
	} else {
	  tabBasic.style.visibility = "hidden";
	  tabActivity.style.visibility = "visible";
	}
	
	var imgBasic = document.getElementById("imgBasic");
	var imgActivity = document.getElementById("imgActivity");
	if(tabId == 1) {
	  imgBasic.src = "/plm/portal/images/tab7_1.png";
	  imgActivity.src = "/plm/portal/images/tab8_2.png";
	} else {
	  imgBasic.src = "/plm/portal/images/tab7_2.png";
	  imgActivity.src = "/plm/portal/images/tab8_1.png";
	}
}
*/

//초기화면세팅
function onLoad() {
  var form = document.forms[0];

  /* 
  var stat = form.chkChangeReason[6].checked;
  var objName = "otherReasonDesc";
  setEtcValueStatus(objName, stat);

  stat = form.chkIncreaseProdType[11].checked;
  objName = "otherIncreaseProdType";
  setEtcValueStatus(objName, stat);
  */
  
  if(form.ecaType.value == "4") {
	  clickTabBtn("3");
  } else {
	  if(form.initTab.value == "1" || form.initTab.value == "2" || form.initTab.value == "3") {
	    clickTabBtn("2");
	  } else if(form.initTab.value == "4") {
	    clickTabBtn("3");
	  } else {
	    clickTabBtn("1");
	  }
  }
  //clickTabBtn(form.initTab.value);
  
  /* 
  setOwnerObj(form.isOwner.value);
  */
  
}

<% /* deprecated */ %>
//기타입력항목 입력상태 처리
function setEtcValueStatus(objName, stat) {
  var obj = eval("document.forms[0]." + objName);
  if(stat) {
    obj.disabled = false;
  } else {
    obj.disabled = true;
  }
}

//프로젝트상세조회 팝업
function viewProjectPopup(projectNo) {
  alert("프로젝트상세조회:" + projectNo);
}

//프로젝트상세조회 팝업
function popupProject() {
  var form = document.forms[0];
  var devflag;
  //개발/양산구분
  if(form.rdoDevYn[0].checked) {
    devflag = form.rdoDevYn[0].value;
  } else {
    devflag = form.rdoDevYn[1].value;
  }
//  var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&dev_flag="+devflag+"&status=P&type=P";
  var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=M";
  openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
}

function setProject(objArr) {
  if(objArr.length == 0) {
    return;
  }

  var trArr;
  var str = "";
  var form = document.forms[0];
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    form.projectNo.value = trArr[1];
    form.projectName.value= trArr[2];
    form.projectOid.value=trArr[0];
  }
}

//프로젝트상세조회 팝업
function clearProject() {
  var form = document.forms[0];
  form.projectNo.value = "";
  form.projectName.value = "";
  form.projectOid.value="";
}

//자료 취소
function doCancel(){
  if( confirm("작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?") )
  {
    //location.href = "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
    self.close();
  }
  else
  {
    return;
  }
}

<% /* deprecated */ %>
//입력항목 체크
function checkValidate(){
  var form = document.forms[0];
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;

  var dev_yn = document.all("rdoDevYn");
  var str_dev_yn = '';
  for( var inx=0; inx < dev_yn.length ; inx++)
  {
    if( dev_yn[inx].checked )
    {
      str_dev_yn  = dev_yn[inx].value;
    }
  }

  if(isNullData(form.ecoName.value)) {
    alert("제목을 입력하여 주십시오");
    return false;
    } else if( str_dev_yn == ''  ){
    alert("단계구분을 선택하세요");
    return false;
  } else if( str_dev_yn == 'D' && form.projectOid.value == ''   ){
    alert("프로젝트 정보를 입력하세요");
    return false;
  } else if(tableRows < 1){
    alert("관련부품을 1개이상 입력하여 주십시오");
    return false;
  } else if(isNullData(form.changeReason.value)) {
    alert("설계변경사유를 입력하여 주십시오");
    return false;
  } else if(isNullData(form.ecoWorkerId.value)) {
    alert("ECO 담당자를 입력하여 주십시오");
    return false;
  }
  else if( form.changeReason.value.indexOf("7") > -1 &&  form.otherReasonDesc.value == "" )
  {
    alert('기타 사유를 입력하여 주십시오');
    return false;
  }
  else if( form.increaseProdType.value.indexOf("12") > -1 && form.otherIncreaseProdType.value == "" )
  {
    alert('기타 내용을 입력하여 주십시오');
    return false;
  }



  //표준품 입력항목 체크
  var relDocTable = document.getElementById("relDocTable");
  var relDocTableRows = relDocTable.rows.length - 2;
  var workerCnt = 0;
  var docCnt = 0;
  if(relDocTableRows > 0) {
    if(relDocTableRows > 1) {
      for(var i = 0; i < relDocTableRows; i++) {
        if(form.relEcaDocWorkerOid[i].value != "") {
          workerCnt++;
        }
        if(form.relEcaDocOid[i].value != "") {
          docCnt++;
        }
      }
    }else{
      if(form.relEcaDocWorkerOid.value != "") {
        workerCnt++;
      }
      if(form.relEcaDocOid.value != "") {
        docCnt++;
      }
    }
    if(docCnt != relDocTableRows) {
      alert("표준품을 입력하세요.");
      return false;
    }
    if(workerCnt != relDocTableRows) {
      alert("표준품 담당자를 입력하세요.");
      return false;
    }
  }
  return true;
}

//연계ECR 검색 팝업 호출
function popupRelEcr() {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp";
  url += "&obj=prodMoldCls^2&obj=mode^multi";
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=1000px; dialogHeight:670px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  insertRelEcrLine(attache);
}

function isDuplicateEcr( ecr_id ) {
//부품추가시 선택된 부품정보를 중복체크한다.
  var tBody = document.getElementById("relEcrTable");
  var rowsLen = tBody.rows.length;
  if(rowsLen > 0)
  {
    var primarEcr = document.getElementsByName("relEcrOid");
    var ecrLength = primarEcr.length;
    if( ecrLength > 0 )
    {
      for(var i = 0; i < ecrLength; i++)
      {
        if( primarEcr[i].value == ecr_id )
        {
          return true;
        }
      }
    }
    else
    {
      if( primarEcr.value == ecr_id )
      {
        return true;
      }
    }
  }
  return false;
}

//연계ECR 라인추가
function insertRelEcrLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relEcrTable");
    var str = "";
  var trArr;
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    if( !isDuplicateEcr(trArr[0]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      //newTd.width='40';
      newTd.innerHTML = "<input name='chkSelectRelEcr' type='checkbox' value=''>";

      //ECR번호
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      //newTd.width='100';
      newTd.innerHTML = "<a href=\"javascript:viewRelEcr('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      newTd.innerHTML += "<input type='hidden' name='relEcrOid' value='" + trArr[0] + "'>";
      newTd.innerHTML += "<input type='hidden' name='relEcrLinkOid' value=''>";

      //ECR 제목
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width='162';
      newTd.className = "tdwhiteL";
      str ="";
      str +="<font title=\""+trArr[5]+"\">";
      str += "<div class='ellipsis' style='width:165;'><nobr>";
      str += trArr[5] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //작성부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      //newTd.width='100';
      newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";

      //작성자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      //newTd.width='90';
      newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";

      //승인일자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      //newTd.width='100';
      newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";
    }
  }
}

//연계ECO 검색 팝업 호출
function popupRelProdEco() {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcoPopupForm.jsp";
  url += "&obj=prodMoldCls^0&obj=mode^multi";
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=740px; dialogHeight:550px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  insertRelProdEcoLine(attache);
}

function isDuplicateEco( oid )
{
  var isDuplicate = false;
  var oidList = document.getElementsByName("relProdEcoOid");

  for( var i=0; i < oidList.length; i++ )
  {
    if( oid == oidList[i].value )
    {
      isDuplicate = true;
    }
  }

  return isDuplicate;

}

//연계ECO 라인추가
function insertRelProdEcoLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("RelProdEcoTable");

  var trArr;
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( !isDuplicateEco(trArr[0]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input name='chkSelectRelProdEco' type='checkbox' value=''>";

      //ECO번호
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<a href=\"javascript:viewRelProdEco('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      newTd.innerHTML += "<input type='hidden' name='relProdEcoOid' value='" + trArr[0] + "'>";
      newTd.innerHTML += "<input type='hidden' name='relProdEcoLinkOid' value=''>";

      //ECO제목
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      str ="";
      str +="<font title=\""+trArr[6]+"\">";
      str += "<div class='ellipsis' style='width:126;'><nobr>";
      str += trArr[6] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //작성부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";

      //작성자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";

      //승인일자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[5] + "&nbsp;";
    }
  }
}

//연계부품 검색 팝업 호출
function popupRelPart() {
//  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcmPartPopup.jsp";
//  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=100%px; dialogHeight:500px; center:yes");
//  if(typeof attache == "undefined" || attache == null) {
//    return;
//  }
//  selectedPart(attache);

  var chk_chg_reason = document.all("chkChangeReason");
  var str_chk_chg_reason = '';
    for( var inx=0; inx < chk_chg_reason.length ; inx++)
  {
    if( chk_chg_reason[inx].checked )
    {
      str_chk_chg_reason  += chk_chg_reason[inx].value;
    }
  }
  if( str_chk_chg_reason != '' )
  {
    var url="/plm/ext/part/base/listPartPopup.do?mode=multi&pType=D";
    openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
  }
  else
  {
    alert("설계변경사유를 선택하세요.");
    return;
  }
}

//연계부품 라인추가
function selectedPart(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relPartTable");

  var reasonList = document.getElementsByName("chkChangeReason");

  var moldDwgChk = false;
  for( var cnt=0; cnt<reasonList.length; cnt++)
  {
    if( reasonList[cnt].value == 'REASON_6' &&  reasonList[cnt].checked == true )
    {
      moldDwgChk = true;
    }
  }

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);
    newTr.onmouseover=function(){relPartTable.clickedRowIndex=this.rowIndex};
    newTr.height = 30;

    //전체선택 checkbox
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input name='chkSelectRelPart' type='checkbox' value=''>";

    //Die No
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = "";
    str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + getTruncStr(trArr[1], 10) + "</a>&nbsp;";
    str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
    str += "<input type='hidden' name='relPartNumber' value='" + trArr[1] + "'>";
    str += "<input type='hidden' name='relPartLinkOid' value=''>";
    newTd.innerHTML = str;

    //Part Name
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteL";
    newTd.setAttribute("title", trArr[2]);
    str = "";
    str += "<div class='ellipsis' style='width:143;'><nobr>";
    str += trArr[2] +"</nobr></div>";
    newTd.innerHTML = str;

    //Part No
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.setAttribute("title", trArr[5]);
    str = "";
    str += "<div class='ellipsis' style='width:90;'><nobr>";
    str += trArr[5] +"&nbsp;</nobr></div>";
    newTd.innerHTML = str;

    //Rev
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = trArr[3] + "&nbsp;";

    //예상비용(천원)
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type='text' name='expectCost' class='txt_fieldR' style='width:90'>";

    //비용확보여부
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteL0";
    str = "";

    if( trArr[1].charAt(1) == 'T' || moldDwgChk )
      {
        str += "<table width='100' border='0' cellspacing='0' cellpadding='0'>";
        str += "  <tr>";
        str += "    <td width='45' align='middle'><input type='hidden' name='budgetYnName' value='확보'><input type='hidden' name='secureBudgetYn' value='Y'></td>";
        str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
        str += "      <tr>";
        str += "      <td width='30'>&nbsp;</td>";
        //str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
        //str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'>확인</a></td>";
        //str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
        str += "      </tr>";
        str += "    </table></td>";
        str += "  </tr>";
        str += "</table>";
      }
      else
      {
        str += "<table width='100' border='0' cellspacing='0' cellpadding='0'>";
        str += "  <tr>";
        str += "    <td width='45' align='middle'><input type='text' name='budgetYnName' value='미확보' class='txt_field' style='width:45' readonly><input type='hidden' name='secureBudgetYn' value='N'></td>";
        str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
        str += "      <tr>";
        str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
        str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'>확인</a></td>";
        str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
        str += "      </tr>";
        str += "    </table></td>";
        str += "  </tr>";
        str += "</table>";
      }
    newTd.innerHTML = str;
  }
}


//첨부파일 라인추가
function insertFileLine() {
  var targetTable = document.getElementById("filetable");
  var tableRows = targetTable.rows.length;
  var newTr = targetTable.insertRow(tableRows);

  //전체선택 checkbox
  newTd = newTr.insertCell(newTr.cells.length);
  newTd.width = "40";
  newTd.className = "tdwhiteM";
  newTd.innerHTML = "<input name='addFileSelect' type='checkbox' value=''>";

  //첨부파일
  var filePath = "filePath" + tableRows;
  newTd = newTr.insertCell(newTr.cells.length);
  newTd.width = "";
  newTd.className = "tdwhiteL";
  newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' size='75'>";
}

//(공통)선택자료삭제
//1.form name
//2.dhtml tableElementId
//3.개별 checkboxName
//4.타이틀바의 전체 checkboxName
//5.삭제oid문자열:oid1*oid2
//function deleteDataLine(formName, tableElementId, checkboxName, allCheckName) {
//  deleteDataLine(formName, tableElementId, checkboxName, allCheckName, '');
//}

function deleteDataLine(formName, tableElementId, checkboxName, allCheckName, listVarName) {
  var body = document.getElementById(tableElementId);
  if (body.rows.length == 0) return;
  var formNameStr = "document." + formName + ".";
  var objChecks = eval(formNameStr + checkboxName);
  var objAllChecks = eval(formNameStr + allCheckName);

  if( isChecked(checkboxName) )
  {
    var listVal = "";
    var objList;
    if(listVarName != "") {
      objList = eval(formNameStr + listVarName);
      listVal = objList.value;
    }

    if(body.rows.length == 1) {
      if (objChecks.checked || objChecks[0].checked) {
        if(objChecks.checked) {
          if(listVal == "") {
            listVal = objChecks.value;
          } else {
            listVal += "*" + objChecks.value;
          }
        } else if(objChecks[0].checked) {
          if(listVal == "") {
            listVal = objChecks[0].value;
          } else {
            listVal += "*" + objChecks[0].value;
          }
        }
        body.deleteRow(0);
      }
    } else {
      for (var i = body.rows.length; i > 0; i--) {
        if (objChecks[i-1].checked) {
          if(listVal == "") {
            listVal = objChecks[i-1].value;
          } else {
            listVal += "*" + objChecks[i-1].value;
          }
          body.deleteRow(i - 1);
        }
      }
    }
    if (body.rows.length < 1) {
      objAllChecks.checked = false;
    }
    if(listVarName != "") {
      objList.value = listVal;
    }
  }
  else
  {
    alert("삭제할 항목을 선택하세요");
    return;
  }
}

function deleteDataLineMinus1(formName, tableElementId, checkboxName, allCheckName, listVarName) {
  var body = document.getElementById(tableElementId);
  if (body.rows.length <= 1) return;
  var formNameStr = "document." + formName + ".";
  var objChecks = eval(formNameStr + checkboxName);
  var objAllChecks = eval(formNameStr + allCheckName);

  if( isChecked(checkboxName) )
  {
    var listVal = "";
    var objList;
    if(listVarName != "") {
      objList = eval(formNameStr + listVarName);
      listVal = objList.value;
    }

    var size = body.rows.length - 1;
    var curRow = 0;
    if(size == 1) {
      if (objChecks.checked || objChecks[0].checked) {
        if(objChecks.checked) {
          if(listVal == "") {
            listVal = objChecks.value;
          } else {
            listVal += "*" + objChecks.value;
          }
        } else if(objChecks[0].checked) {
          if(listVal == "") {
            listVal = objChecks[0].value;
          } else {
            listVal += "*" + objChecks[0].value;
          }
        }
        body.deleteRow(1);
      }
    } else {
      for (var i = body.rows.length; i > 1; i--) {
        curRow = i - 2;
        if (objChecks[curRow].checked) {
          if(listVal == "") {
            listVal = objChecks[curRow].value;
          } else {
            listVal += "*" + objChecks[curRow].value;
          }
          body.deleteRow(i - 1);
        }
      }
    }
    if (size < 1) {
      objAllChecks.checked = false;
    }
    if(listVarName != "") {
      objList.value = listVal;
    }
  }
  else
  {
    alert("삭제할 항목을 선택하세요");
    return;
  }
}

function isChecked( checkboxName )
{
  var isChecked = false;
   var objCheck = document.getElementsByName(checkboxName);

   for( var i=0; i < objCheck.length; i++ )
   {
     if( objCheck[i].checked )
     {
       isChecked = true;
     }
   }

   return isChecked;
}

function deleteDataLineMinus2(formName, tableElementId, checkboxName, allCheckName, listVarName) {
  var body = document.getElementById(tableElementId);
  if (body.rows.length <=2) return;
  var formNameStr = "document." + formName + ".";
  var objChecks = eval(formNameStr + checkboxName);
  var objAllChecks = eval(formNameStr + allCheckName);

  if( isChecked(checkboxName) )
  {
    var listVal = "";
    var objList;
    if(listVarName != "") {
      objList = eval(formNameStr + listVarName);
      listVal = objList.value;
    }
    var size = body.rows.length - 2;
    var curRow = 0;
    var objPreRev;
    var objAfterRev;
    if(size == 0) {
      return;
    } else if(size == 1) {
      if (objChecks.checked || objChecks[0].checked) {
        if(objChecks.checked) {
          if(tableElementId == "relEpmTable") {
            objPreRev = eval(formNameStr + "relEcaEpmPreRev");
            objAfterRev = eval(formNameStr + "relEcaEpmAfterRev");
          } else if(tableElementId == "relBomTable") {
            objPreRev = eval(formNameStr + "relEcaBomPreRev");
            objAfterRev = eval(formNameStr + "relEcaBomAfterRev");
          } else if(tableElementId == "relDocTable") {
            objPreRev = eval(formNameStr + "relEcaDocPreRev");
            objAfterRev = eval(formNameStr + "relEcaDocAfterRev");
          }
          if(objAfterRev.value > objPreRev.value) {
            alert("개정된 자료는 삭제 불가합니다");
            return;
          }
          if(listVal == "") {
            listVal = objChecks.value;
          } else {
            listVal += "*" + objChecks.value;
          }
        } else if(objChecks[0].checked) {
          if(tableElementId == "relEpmTable") {
            objPreRev = eval(formNameStr + "relEcaEpmPreRev[0]");
            objAfterRev = eval(formNameStr + "relEcaEpmAfterRev[0]");
          } else if(tableElementId == "relBomTable") {
            objPreRev = eval(formNameStr + "relEcaBomPreRev[0]");
            objAfterRev = eval(formNameStr + "relEcaBomAfterRev[0]");
          } else if(tableElementId == "relDocTable") {
            objPreRev = eval(formNameStr + "relEcaDocPreRev[0]");
            objAfterRev = eval(formNameStr + "relEcaDocAfterRev[0]");
          }
          if(objAfterRev.value > objPreRev.value) {
            alert("개정된 자료는 삭제 불가합니다");
            return;
          }
          if(listVal == "") {
            listVal = objChecks[0].value;
          } else {
            listVal += "*" + objChecks[0].value;
          }
        }
        body.deleteRow(2);
      }
    } else {
      for (var i = body.rows.length; i > 2; i--) {
        curRow = i - 3;
        if (objChecks[curRow].checked) {
          if(tableElementId == "relEpmTable") {
            objPreRev = eval(formNameStr + "relEcaEpmPreRev[" + curRow + "]");
            objAfterRev = eval(formNameStr + "relEcaEpmAfterRev[" + curRow + "]");
          } else if(tableElementId == "relBomTable") {
            objPreRev = eval(formNameStr + "relEcaBomPreRev[" + curRow + "]");
            objAfterRev = eval(formNameStr + "relEcaBomAfterRev[" + curRow + "]");
          } else if(tableElementId == "relDocTable") {
            objPreRev = eval(formNameStr + "relEcaDocPreRev[" + curRow + "]");
            objAfterRev = eval(formNameStr + "relEcaDocAfterRev[" + curRow + "]");
          }
          if(objAfterRev.value > objPreRev.value) {
            alert("개정된 자료는 삭제 불가합니다");
            return;
          }
          if(listVal == "") {
            listVal = objChecks[curRow].value;
          } else {
            listVal += "*" + objChecks[curRow].value;
          }
          body.deleteRow(i - 1);
        }
      }
    }
    if (size <= 2) {
      objAllChecks.checked = false;
    }
    if(listVarName != "") {
      objList.value = listVal;
    }

    document.forms[0].isChanged.value = 'Y';
  }
  else
  {
    alert("삭제할 항목을 선택하세요");
    return;
  }
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


//표준품 검색 팝업 호출
function popupRelDoc(tableId) {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one&obj=authorStatus^APPROVED";
//  attache = window.open(url,"window","help=no; resizable=yes; status=no; scroll=no; dialogWidth=500px; dialogHeight:500px; center:yes");
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=800px; dialogHeight:540px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  var pos = eval(tableId).clickedRowIndex - 2;
  checkDocAjax(attache, pos);
}

function checkDocAjax(objArr, inx){
  var trArr = objArr[0];
  var url = "/plm/jsp/ecm/CheckDocAjaxAction.jsp?oid="+trArr[0]+"&no="+trArr[1]+"&name="+trArr[2]+"&ver="+trArr[3]+"&inx=" + inx;
  callServer(url, checkDocResult);
}

function checkDocResult(req) {
  var xmlDoc = req.responseXML;

  showElements = xmlDoc.selectNodes("//data_info");
  var l_flag = showElements[0].getElementsByTagName("l_flag");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_no = showElements[0].getElementsByTagName("l_no");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_ver = showElements[0].getElementsByTagName("l_ver");
  var l_inx = showElements[0].getElementsByTagName("l_inx");

  var flag = decodeURIComponent(l_flag[0].text);
  var oid = decodeURIComponent(l_oid[0].text);
  var no = decodeURIComponent(l_no[0].text);
  var name = decodeURIComponent(l_name[0].text);
  var ver = decodeURIComponent(l_ver[0].text);
  var inx = decodeURIComponent(l_inx[0].text);

  if ( flag == 'true'){
    alert("설계변경이 진행중인 문서입니다.");
    return;
  }

   setRelDoc(oid, no, name, ver, inx);
}

//표준품 검색 팝업 리턴 세팅
function setRelDoc(oid, no, name, ver, inx) {
  if(oid.length == 0) {
    return;
  }

  var form = document.forms[0];
  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 3){
    form.relEcaDocOid[inx].value = oid;
    form.relEcaDocNo[inx].value = no;
    form.relEcaDocPreRev[inx].value = ver;
  } else {
    form.relEcaDocOid.value = oid;
    form.relEcaDocNo.value = no;
    form.relEcaDocPreRev.value = ver;
  }
}

//문서 초기화
function clearRelDoc(tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 2;
  if(tableRows > 3){
    form.relEcaDocOid[pos].value = '';
    form.relEcaDocNo[pos].value = '';
    form.relEcaDocPreRev[pos].value = '';
  } else {
    form.relEcaDocOid.value = '';
    form.relEcaDocNo.value = '';
    form.relEcaDocPreRev.value = '';
  }
}


//표준품 라인 추가
function addRelDoc() {
  var arr = new Array();
  var idx = 0;
  rArr = new Array();
  rArr[0] = "";//oid
  rArr[1] = "";//code
  rArr[2] = "";//version
  arr[idx++] = rArr;
  insertRelDocLine(arr);
}

//표준품 라인추가
function insertRelDocLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var form = document.forms[0];
  var targetTable = document.getElementById("relDocTable");

  var trArr;
  var str = "";
  //if( targetTable.rows.length < 3 )
  //{
    for(var i = 0; i < objArr.length; i++) {
      trArr = objArr[i];
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.height="29";
      newTr.onmouseover=function(){relDocTable.clickedRowIndex=this.rowIndex};
      var currRow = targetTable.rows.length - 1;

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "40";
      newTd.className = "tdwhiteM";
      str = "";
      
      <!-- ECN을 위한 값으로 여기서는 필요없지만, 서버에서 저장 로직을 같이 사용하고 있기때문에 없으면 Null Exception이 난다. -->
      str += "<input type='hidden' name='customName' value=''>";
      str += "<input type='hidden' name='completeRequestDate' value=''>";
      str += "<input type='hidden' name='activityTypeDesc' value=''>";
      str += "<input type='hidden' name='activityBackDesc' value=''>";
          
      str += "<input type='hidden' name='relEcaDocActivityType' value='3'>";
      str += "<input type='hidden' name='relEcaDocLinkOid' value=''>";
      str += "<input type='hidden' name='moldEcaDocOid' value=''>";
      str += "<input type='hidden' name='relEcaDocWorkerOid' value='" + form.loginUserOid.value + "'>";
      str += "<input type='hidden' name='relEcaDocPlanDate' value=''>";
      str += "<input type='checkbox' name='chkSelectRelDoc'>";
      newTd.innerHTML = str;

      //문서번호
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='hidden' name='relEcaDocOid' value='" + trArr[0] + "'>";
      str += "<input type='text' name='relEcaDocNo' class='txt_fieldCRO' style='width:80%' readonly value='" + trArr[1] + "'>";
      str += "&nbsp;<a href=\"javascript:popupRelDoc('relDocTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
      str += "&nbsp;<a href=\"javascript:clearRelDoc('relDocTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      newTd.innerHTML = str;

      //PrevRev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "<input type='text' name='relEcaDocPreRev' class='txt_fieldCRO' style='width:40;cursor:hand' readonly value='" + trArr[2] + "' onclick='javascript:viewRelDoc();'>";
      newTd.innerHTML = str;

      //AfterRev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "<input type='text' name='relEcaDocAfterRev' class='txt_fieldCRO' style='width:40' readonly value=''>";
      newTd.innerHTML = str;

      //대상부품
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "&nbsp;";
      //str += "<input type='text' name='targetPartNumber' ";
      //str += "class='txt_field' style='width:60' readonly value=''";
      //str += ">&nbsp;<a href='javascript:popupDocTargetPart();'";
      //str += "><img src='/plm/portal/images/icon_5.png' border='0'";
      //str += "></a>&nbsp;<a href='javascript:clearDocTargetPart();'";
      //str += "><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      newTd.innerHTML = str;

      //유형
      //newTd = newTr.insertCell(newTr.cells.length);
      //newTd.className = "tdwhiteM";
      //str = "<select name='relEcaDocChangeType'>";
      //str += "<option value='가공' selected>가공</option>";
      //str += "<option value='수정'>수정</option>";
      //str += "<option value='BOM정리'>BOM정리</option>";
      //str += "</select>";
      //newTd.innerHTML = str;

      //변경내용
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='text' name='relEcaDocActivityComment' class='txt_field'  style='width:130'>";
      newTd.innerHTML = str;


      //작업
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.colSpan='2';
      str = "";
      str += "<table border='0' cellspacing='0' cellpadding='0'>";
      str += "<tr>";
      str += "<td><table border='0' cellspacing='0' cellpadding='0'>";
      str += "<tr>";
      str += "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
      str += "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:popupChangeDoc2();' class='btn_blue' onfocus='this.blur();'>변경문서검색</a></td>";
      str += "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
      str += "</tr>";
      str += "</table></td>";
      str += "<td width='5'>&nbsp;</td>";
      str += "<td><table border='0' cellspacing='0' cellpadding='0'>";
      str += "<tr>";
      str += "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
      str += "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href=\"javascript:clearChangeDoc();\" class='btn_blue' onfocus='this.blur();'>변경문서삭제</a></td>";
      str += "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
      str += "</tr>";
      str += "</table></td>";
      str += "</tr>";
      str += "</table>";
      newTd.innerHTML = str;
    }
  //}
  //else
  //  {
  //  alert("표준품리스트는 1개만 등록할 수 있습니다.");
  //}
//  document.recalc();
}

//표준품 담당자검색 팝업
function popupUser(tableId){
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  var pos = eval(tableId).clickedRowIndex;
  setRelDocUser(attacheMembers, pos);
}

//표준품 담당자 검색 팝업 리턴 포맷
function setRelDocUser(objArr, pos) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 1){
    form.relEcaDocWorkerOid[pos].value = trArr[0];
    form.relEcaDocWorkerName[pos].value = trArr[4];
  } else {
    form.relEcaDocWorkerOid.value = trArr[0];
    form.relEcaDocWorkerName.value = trArr[4];
  }
}

//담당자 초기화
function clearUser(tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  if(tableRows > 1){
    form.relEcaDocWorkerOid[pos].value = '';
    form.relEcaDocWorkerName[pos].value = '';
  } else {
    form.relEcaDocWorkerOid.value = '';
    form.relEcaDocWorkerName.value = '';
  }
}

//변경예정일 팝업
function popupCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  showCal(objName);
}

//변경예정일 초기화
function clearCal(objName, tableId){
  var pos = eval(tableId).clickedRowIndex;
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  eval("document.forms[0]." + objName).value = '';
}


//자부품 추가/연계도면/BOM 검색 팝업 호출
var newwin = null;
function popupRelEpm2() {
    var url = "/plm/jsp/ecm/SearchActivityPopupForm.jsp"
            //+ "?obj=prodMoldCls^2&obj=mode^multi&obj=partType^D";
            + "?prodMoldCls=2&mode=multi&partType=D";
                
    
    /* 
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
    url += "&obj=prodMoldCls^2&obj=mode^multi&obj=ecoEcaCls^eca&obj=epmBomCls^2&obj=partType^D";
    url += "&obj=partNumber^" + partNumber;
    */
    
    // 기본사항에 추가된 제품
    var pNums = "";
    var relPartNumber = document.getElementsByName("relPartNumber");
    var relPartNumberLength = (relPartNumber != null) ? relPartNumber.length : 0;
    for(var i=0; i < relPartNumberLength; i++) {
        pNums += "&pNums="+  relPartNumber[i].value;
        
    }
    url += pNums;
    
    // 설변부품/도면에 추가된 부품
    /* 
    var pNums2 = "";
    $("#relEpmTable").find("[name=addedPartNumberAtEca]").each(function(i) {
        pNums2 += "&pNums2="+  $(this).val();
        
    });
    url += pNums2;
    */
    

    // 설계변경사유
    $("[name=chkChangeReason]").each(function(i) {
        if($(this).is(':checked')) {
            if($(this).val() == 'REASON_10') {
                url += "&isChodo=Y";
                return;
            }
        }
    });
    
    // ECO No
    url += "&currentEcoNo="+ $("[name=ecoId]").val();
    
    newwin = openWindow(url,"popupRelEpm2","1000","600","status=1,scrollbars=no,resizable=no");
    //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=725px; dialogHeight:570px; center:yes");
    
}

//연계도면/BOM 검색 팝업 호출
<% /* deprecated */ %>
function popupRelEpm() {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
  url += "&obj=prodMoldCls^2&obj=mode^multi&obj=ecoEcaCls^eca&obj=epmBomCls^1";
//  openWindow(url,"","770","600","status=1,scrollbars=no,resizable=no");
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=900px; dialogHeight:600px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
//  var attache = getSampleRelEpmData();
  insertRelEpmLine(attache);
}

//도면 라인추가
function insertRelEpmLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  
  var trArr;
  var str = "";
  var isAdded = false;
  var ecaType = document.forms[0].ecaType.value;
  var epmdupcnt = 0;
  var bomdupcnt = 0;
  
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    
    // 도면일 경우
    if(trArr[0] == '1') {
    	if( !isDuplicateEpm(trArr[2]) && bomdupcnt == 0 )
        {
		    var targetTable = document.getElementById("relEpmTable");
		    var tableRows = targetTable.rows.length;
		    var newTr = targetTable.insertRow(tableRows);
		    newTr.height="29";
		    newTr.onmouseover=function(){relEpmTable.clickedRowIndex=this.rowIndex};
		    var currRow = targetTable.rows.length - 2;
		
		    //전체선택 checkbox
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    str = "";
		    str += "<input type='hidden' name='relEcaEpmActivityType' value='" + trArr[0] + "'>";
		    str += "<input type='hidden' name='relEcaEpmLinkOid' value=''>";
		    str += "<input type='hidden' name='relEcaEpmOid' value='" + trArr[1] + "'>";
		    str += "<input type='hidden' name='relEcaEpmNo' value='" + trArr[2] + "'>";
            str += "<input type='hidden' name='relEcaEpmName' value='" + trArr[3] + "'>";
            str += "<input type='hidden' name='relEcaEpmPreRev' value='" + trArr[4] + "'>";
		    str += "<input type='hidden' name='relEcaEpmAfterRev' value=''>";
		    str += "<input type='hidden' name='relEcaAfterEpmOid' value=''>";
		    str += "<input type='hidden' name='moldEcaEpmOid' value=''>";
		    str += "<input type='hidden' name='relEcaEpmWorkerOid' value='" + trArr[5] + "'>";
		    str += "<input type='hidden' name='relEcaEpmPlanDate' value=''>";
		    str += "<input type='hidden' name='relEcaEpmChangeYn' value='N'>";
		    str += "<input type='hidden' name='relEcaEpmDocReviseYn' value='N'>";
		    str += "<input type='hidden' name='relEcaEpmDocCancelRevYn' value='N'>";
		    str += "<input type='hidden' name='relEcaEpmDocType' value='" + trArr[7] + "'>";
		    str += "<input type='hidden' name='relEcaEpmActivityComment'>";
		    
		    str += "<input type='checkbox' name='chkSelectRelEpm'>";
		    newTd.innerHTML = str;
		
		    //구분
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    newTd.innerHTML = trArr[13];
		
		    //도면번호
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    newTd.innerHTML = "<font title='"+trArr[2]+"'>"
		                      + trArr[2] +"</font>";
		
		    //도면명
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    newTd.innerHTML = "<font title=\""+trArr[3]+"\"><div class='ellipsis' style='width:120px;'><nobr>"
		                      + trArr[3] +"</nobr></div></font>";
		
		    //작업
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    str = "";
		//    if(trArr[7] == "2D") {
		//      str = str + "<td width='' class='tdwhiteM0' align='left'><table border='0' cellspacing='0' cellpadding='0'>";
		//      str = str + "<tr>";
		//      str = str + "<td><table border='0' cellspacing='0' cellpadding='0'>";
		//      str = str + "<tr>";
		//      str = str + "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
		//      str = str + "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc();' class='btn_blue' onfocus='this.blur();'>도면변경</a></td>";
		//      str = str + "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
		//      str = str + "</tr>";
		//      str = str + "</table></td>";
		//      str = str + "</tr>";
		//      str = str + "</table></td>";
		//    } else {
		//      str = "&nbsp;";
		//    }
		    str = "&nbsp;";
		    newTd.innerHTML = str;
		
		    //PrevRev
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    newTd.innerHTML = trArr[4];
		
		    //NewRev
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    newTd.innerHTML = "&nbsp;";
		
		    //유형
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    str = "<select name='relEcaEpmChangeType' style='width:98%;'>";
		    str += "<option value='가공' selected>가공</option>";
		    str += "<option value='수정'>수정</option>";
		    str += "<option value='도면정리'>도면정리</option>";
		    str += "<option value='가공+수정'>가공+수정</option>";
		    str += "</select>";
		    newTd.innerHTML = str;
		
		    //연계일정
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM";
		    str = "";
		    str += "<input type='hidden' name='oldMoldChangePlanOid' value=''>";
		    str += "<input type='hidden' name='newMoldChangePlanOid' value=''>";
		    str += "<input type='hidden' name='moldEcoPlanLinkOid' value=''>";
		    str += "<input type='hidden' name='dieNo' value=''>";
		    str += "<input type='text' name='scheduleId' class='txt_field' style='width:80px' readonly>";
		    str += "&nbsp;<a href=\"javascript:popupRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
		    str += "&nbsp;<a href=\"javascript:clearRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
		    newTd.innerHTML = str;
		
		
		    //변경내용
		    /* 
		    newTd = newTr.insertCell(newTr.cells.length);
		    newTd.className = "tdwhiteM0";
		    str = "";
		    str += "<input type='text' name='relEcaEpmActivityComment' class='txt_field'  style='width:90%'>";
		    newTd.innerHTML = str;
		    */
		    
		    isAdded = true;
		    if(ecaType == 2){
		    	bomdupcnt = 0;
		    }
        }else{
        	if(ecaType == '1'){
        		epmdupcnt = 1;
        	}
        }
    }
    else {
    	
    	if( !isDuplicateBom(trArr[2]) && epmdupcnt == 0)
        {
	    	var targetTable = document.getElementById("relBomTable");
	        var tableRows = targetTable.rows.length;
	        var newTr = targetTable.insertRow(tableRows);
	        newTr.height="29";
	        newTr.onmouseover=function(){relEpmTable.clickedRowIndex=this.rowIndex};
	        var currRow = targetTable.rows.length - 2;
	
	        //전체선택 checkbox
	        newTd = newTr.insertCell(newTr.cells.length);
	        newTd.width = "40";
	        newTd.className = "tdwhiteM";
	        str = "";
	        str += "<input type='hidden' name='relEcaBomActivityType' value='2'>";
	        str += "<input type='hidden' name='relEcaBomLinkOid' value=''>";
	        str += "<input type='hidden' name='relEcaBomOid' value='" + trArr[1] + "'>";
	        str += "<input type='hidden' name='relEcaBomNo' value='" + trArr[2] + "'>";
	        str += "<input type='hidden' name='relEcaBomName' value='" + trArr[3] + "'>";
	        str += "<input type='hidden' name='relEcaBomPreRev' value='" + trArr[4] + "'>";
	        str += "<input type='hidden' name='relEcaBomAfterRev' value=''>";
	        str += "<input type='hidden' name='moldEcaBomOid' value=''>";
	        str += "<input type='hidden' name='beforePartOid' value='" + trArr[1] + "'>";
	        str += "<input type='hidden' name='relEcaBomWorkerOid' value='" + trArr[5] + "'>";
	        str += "<input type='hidden' name='relEcaBomChangeYn' value='N'>";
	        str += "<input type='hidden' name='relEcaBomReviseYn' value='N'>";
	        str += "<input type='hidden' name='relEcaBomReviseCancel' value='N'>";
	        str += "<input type='hidden' name='relEcaBomPlanDate' value=''>";
	        str += "<input type='hidden' name='relEcaBomActivityComment'>";
	        
	        str += "<input type='checkbox' name='chkSelectRelBom' value=''>";
	        
	        str += "<input type='hidden' name='afterWTPartOid' value=''>";
	        
	        newTd.innerHTML = str;
	
	        //금형부품번호
	        newTd = newTr.insertCell(newTr.cells.length);
	        newTd.className = "tdwhiteM";
	        newTd.innerHTML = trArr[2];
	
	        //금형부품명
	        newTd = newTr.insertCell(newTr.cells.length);
	        newTd.className = "tdwhiteL";
	        newTd.innerHTML = "<font title=\""+trArr[3]+"\"><div class='ellipsis' style='width:150;'><nobr>"
	                          + trArr[3] +"</nobr></div></font>";
	
	        //PrevRev
	        newTd = newTr.insertCell(newTr.cells.length);
	        newTd.className = "tdwhiteM";
	        str = trArr[4];
	        newTd.innerHTML = str;
	
	        //NewRev
	        newTd = newTr.insertCell(newTr.cells.length);
	        newTd.className = "tdwhiteM";
	        newTd.innerHTML = "&nbsp;";
	
	        //변경내용
	        /* 
	        newTd = newTr.insertCell(newTr.cells.length);
	        newTd.className = "tdwhiteM";
	        str = "<input type='text' name='relEcaBomActivityComment' class='txt_field'  style='width:140'>";
	        newTd.innerHTML = str;
	        */
	        
	        //작업
	        newTd = newTr.insertCell(newTr.cells.length);
	        newTd.className = "tdwhiteM0";
	        str = "";
	        str += "<table border='0' cellspacing='0' cellpadding='0'>";
	        str += "<tr>";
	        str += "<td><table border='0' cellspacing='0' cellpadding='0'>";
	        str += "<tr>";
	        str += "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
	        str += "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:reviseBom();' class='btn_blue' onfocus='this.blur();'>개정</a></td>";
	        str += "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
	        str += "</tr>";
	        str += "</table></td>";
	        //str += "<td width='5'>&nbsp;</td>";
	        //str += "<td><table border='0' cellspacing='0' cellpadding='0'>";
	        //str += "<tr>";
	        //str += "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
	        //str += "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href=\"javascript:changeBom();\" class='btn_blue' onfocus='this.blur();'>BOM 변경</a></td>";
	        //str += "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
	        //str += "</tr>";
	        //str += "</table></td>";
	        str += "</tr>";
	        str += "</table>";
	        newTd.innerHTML = str;
	        
	        isAdded = true;
	        if(ecaType == 1){
	        	epmdupcnt = 0;
	        }
        }else{
        	if(ecaType == '2'){
        		bomdupcnt = 1;
        	}
        }	
    }
    
    
    if(isAdded) document.forms[0].isChanged.value = 'Y';
  }
    
  //if(newwin != null) newwin.window.close();
  if(isAdded) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "04045") %><%--\'선택\'하신 Part가 추가되었습니다.--%>');
      newwin.window.focus();
  }else{
	  alert("중복 데이터는 추가할 수 없습니다.");  
  }
  
}
function isDuplicateBom( item_code )
{
    var isDuplicate = false;
    var bomList = document.getElementsByName("relEcaBomNo");

    for( var inx=0; inx < bomList.length; inx++ )
    {
        if( bomList[inx].value == item_code )
        {
             isDuplicate = true;
        }
    }
    var ecaType = document.forms[0].ecaType.value;
    if(ecaType == '1'){//todo가 도면일때 이미 추가되어있는 부품 리스트가 없으므로 도번을 _ 기준으로 잘라서 부품의 중복여부를 판단한다. 도면이 추가되어있다면 반드시 부품이 추가되었을것이므로..
	    var epmList = document.getElementsByName("relEcaEpmNo");
	    var temp = '';
	    for( var inx=0; inx < epmList.length; inx++ )
	    {
	    	temp = epmList[inx].value.substring(0,epmList[inx].value.lastIndexOf("_"));
	        if( temp == item_code )
	        {
	             isDuplicate = true;
	        }
	    }
    }
    
    return isDuplicate;
}
function isDuplicateEpm( item_code )
{
    var isDuplicate = false;
    var epmList = document.getElementsByName("relEcaEpmNo");

    for( var inx=0; inx < epmList.length; inx++ )
    {
        if( epmList[inx].value == item_code )
        {
             isDuplicate = true;
        }
    }

    return isDuplicate;
}

//자부품 추가/연계도면/BOM 검색 팝업 호출
function popupRelBom2() {
	popupRelEpm2();
}

//연계BOM 검색 팝업 호출
<% /* deprecated */ %>
function popupRelBom() {
  var partNumber = "";

  /* 
  var form = document.forms[0];
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;
  
  if(tableRows > 0){
    if(tableRows > 1){
      partNumber = form.relPartNumber[0].value;
    } else {
      partNumber = form.relPartNumber.value;
    }
  }
  */
  
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
  url += "&obj=prodMoldCls^2&obj=mode^multi&obj=ecoEcaCls^eca&obj=epmBomCls^2&obj=partType^D";
  //url += "&obj=partNumber^" + partNumber;
    

  var pNums = "";
  var relPartNumber = document.getElementsByName("relPartNumber");
  var relPartNumberLength = (relPartNumber != null) ? relPartNumber.length : 0;
  for(var i=0; i < relPartNumberLength; i++) {
      pNums += "&obj=pNums^"+  relPartNumber[i].value;
      
  }
  url += pNums;
  //alert(url);
  
  
  openWindow(url,"","770","600","status=1,scrollbars=no,resizable=no");
  /* 
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=900px; dialogHeight:600px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
//  var attache = getSampleRelEpmData();
  insertRelBomLine(attache);
  */
}

//연계BOM 라인추가
<% /* deprecated */ %>
function insertRelBomLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relBomTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);
    newTr.height="29";
    newTr.onmouseover=function(){relEpmTable.clickedRowIndex=this.rowIndex};
    var currRow = targetTable.rows.length - 2;

    //전체선택 checkbox
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "40";
    newTd.className = "tdwhiteM";
    str = "";
    str += "<input type='hidden' name='relEcaBomActivityType' value='2'>";
    str += "<input type='hidden' name='relEcaBomLinkOid' value=''>";
    str += "<input type='hidden' name='relEcaBomOid' value='" + trArr[1] + "'>";
    str += "<input type='hidden' name='relEcaBomNo' value='" + trArr[2] + "'>";
    str += "<input type='hidden' name='relEcaBomName' value='" + trArr[3] + "'>";
    str += "<input type='hidden' name='relEcaBomPreRev' value='" + trArr[4] + "'>";
    str += "<input type='hidden' name='relEcaBomAfterRev' value=''>";
    str += "<input type='hidden' name='moldEcaBomOid' value=''>";
    str += "<input type='hidden' name='beforePartOid' value='" + trArr[1] + "'>";
    str += "<input type='hidden' name='relEcaBomWorkerOid' value='" + trArr[5] + "'>";
    str += "<input type='hidden' name='relEcaBomChangeYn' value='N'>";
    str += "<input type='hidden' name='relEcaBomReviseYn' value='N'>";
    str += "<input type='hidden' name='relEcaBomReviseCancel' value='N'>";
    str += "<input type='hidden' name='relEcaBomPlanDate' value=''>";
    str += "<input type='checkbox' name='chkSelectRelBom' value=''>";
    
    str += "<input type='hidden' name='afterWTPartOid' value=''>";
    
    newTd.innerHTML = str;

    //금형부품번호
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = trArr[2];

    //금형부품명
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteL";
    newTd.innerHTML = "<font title=\""+trArr[3]+"\"><div class='ellipsis' style='width:150;'><nobr>"
                      + trArr[3] +"</nobr></div></font>";

    //PrevRev
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = trArr[4];
    newTd.innerHTML = str;

    //NewRev
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "&nbsp;";

    //변경내용
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = "<input type='text' name='relEcaBomActivityComment' class='txt_field'  style='width:140'>";
    newTd.innerHTML = str;

    //작업
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM0";
    str = "";
    str += "<table border='0' cellspacing='0' cellpadding='0'>";
    str += "<tr>";
    str += "<td><table border='0' cellspacing='0' cellpadding='0'>";
    str += "<tr>";
    str += "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
    str += "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:reviseBom();' class='btn_blue' onfocus='this.blur();'>개정</a></td>";
    str += "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
    str += "</tr>";
    str += "</table></td>";
    //str += "<td width='5'>&nbsp;</td>";
    //str += "<td><table border='0' cellspacing='0' cellpadding='0'>";
    //str += "<tr>";
    //str += "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
    //str += "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href=\"javascript:changeBom();\" class='btn_blue' onfocus='this.blur();'>BOM 변경</a></td>";
    //str += "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
    //str += "</tr>";
    //str += "</table></td>";
    str += "</tr>";
    str += "</table>";
    newTd.innerHTML = str;

    document.forms[0].isChanged.value = 'Y';
  }
}


//연계일정검색 팝업
function popupRelPlan(tableId){

   var oid = "e3ps.ecm.entity.KETMoldChangeOrder:";
   if( document.forms[0].oid.value != '' )
   {
      oid = document.forms[0].oid.value;
   }

  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchMoldPlanPopupForm.jsp?oid="+oid;
  arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=770px; dialogHeight:500px; center:yes");
  if(typeof arr == "undefined" || arr == null) {
    return;
  }
  var pos = eval(tableId).clickedRowIndex - 2;
  setRelPlan(arr, pos);
}

//연계일정 검색 팝업 리턴 포맷
function setRelPlan(objArr, pos) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  var targetTable = document.getElementById("relEpmTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 3){//테이블 헤더가 포함되어 있기 때문에.
    form.newMoldChangePlanOid[pos].value = trArr[0];
    form.scheduleId[pos].value = trArr[2];
    form.dieNo[pos].value = trArr[1];
    form.relEcaEpmPlanDate[pos].vaule = trArr[4];
  } else {
    form.newMoldChangePlanOid.value = trArr[0];
    form.scheduleId.value = trArr[2];
    form.dieNo.value = trArr[1];
    form.relEcaEpmPlanDate.value = trArr[4];
  }
}

//연계일정 초기화
function clearRelPlan(tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 2;
  if(tableRows > 3){
    form.newMoldChangePlanOid[pos].value = '';
    form.scheduleId[pos].value = '';
    form.dieNo[pos].value = '';
  } else {
    form.newMoldChangePlanOid.value = '';
    form.scheduleId.value = '';
    form.dieNo.value = '';
  }
}

//변경예정일 팝업
function popupEpmCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 1;
  if(tableRows > 2){
    objName = objName + "[" + pos + "]";
  }
  showCal(objName);
}

//변경예정일 초기화
function clearEpmCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 1;
  if(tableRows > 2){
    objName = objName + "[" + pos + "]";
  }
  eval("document.forms[0]." + objName).value = '';
}

//ECO담당자검색 팝업
function popupEcoUser(){
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  setEcoUser(attacheMembers);
}

//표준품 담당자 검색 팝업 리턴 포맷
function setEcoUser(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];
  form.ecoWorkerId.value = trArr[0];
  form.ecoWorkerName.value = trArr[4];
}

//ECO담당자 초기화
function clearEcoUser(){
  var form = document.forms[0];
  form.ecoWorkerId.value = '';
  form.ecoWorkerName.value = '';
}


//생산처 검색 팝업
function popupVendor(){
  var form = document.forms[0];
  if(form.vendorFlag.value == "i") {//사내
    selectDepartment();
  } else {//외주
    selectPartner();
  }
}

//생산처 초기화
function clearVendor(){
  var form = document.forms[0];
  form.prodVendor.value = '';
  form.prodVendorName.value = '';
}

//협력사검색 팝업 Open
function selectPartner() {
  var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
  openWindow(url,"","500","500","status=1,scrollbars=no,resizable=no");
}

//협력사 검색결과 셋팅
function linkPartner(arr){
  if(arr.length == 0) {
    alert("협력사를 선택하시기 바랍니다.");
    return;
  }
  var form = document.forms[0];
  form.prodVendor.value = arr[0][0];
  form.prodVendorName.value = arr[0][1];
}

//사내생산처 검색 팝업  Open
function selectDepartment() {
  var mode = "single";
  var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode;
  returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
  if(typeof returnValue == "undefined" || returnValue == null) {
    return;
  }
  linkDept(returnValue);
}

//사내생산처 검색결과 셋팅
function linkDept(arr){
  if(arr.length == 0) {
    alert("부서를 선택하시기 바랍니다.");
    return;
  }
  var form = document.forms[0];
  form.prodVendorName.value = arr[0][2];
  form.prodVendor.value = arr[0][1];
}

//부품 상세조회 팝업
function viewRelPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

var posDocTargetPart = 0;
//표준품대상부품 검색 팝업 호출
function popupDocTargetPart() {
  var url="/plm/ext/part/base/listPartPopup.do?mode=multi&fncall=selectedDocTargetPart";
  openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
  posDocTargetPart = relDocTable.clickedRowIndex;
}

//표준품대상부품 세팅
function selectedDocTargetPart(objArr) {
  var size = objArr.length;
  if(size == 0) {
    return;
  }
  var targetTable = document.getElementById("relDocTable");

  var form = document.forms[0];
  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;
//  var pos = eval(tableId).clickedRowIndex;
  var pos = posDocTargetPart - 2
  var trArr;
  var str = "";
  if(size > 0) {
    trArr = objArr[0];
    str = trArr[1];
    for(var i=1; i<size; i++) {
      trArr = objArr[i];
      str += "," + trArr[1];
    }
  }
  if(tableRows > 3){
    form.targetPartNumber[pos].value = str;
  } else {
    form.targetPartNumber.value = str;
  }
}

//표준품 대상부품 초기화
function clearDocTargetPart(){
  var form = document.forms[0];
  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;
  var pos = relDocTable.clickedRowIndex - 2;
  if(tableRows > 3) {
    form.targetPartNumber[pos].value = '';
  } else {
    form.targetPartNumber.value = '';
  }
}

//변경문서 초기화
function clearChangeDoc(){
  var form = document.forms[0];
  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;
  var pos = relDocTable.clickedRowIndex - 2;
  if(tableRows > 3) {
    form.relEcaDocAfterRev[pos].value = '';
  } else {
    form.relEcaDocAfterRev.value = '';
  }
}


//표준품 변경문서검색 팝업 호출
function popupChangeDoc(docNo) {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one&obj=authorStatus^APPROVED&obj=isReview^Y&obj=docNo^"+docNo;
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=800px; dialogHeight:540px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  var pos = relDocTable.clickedRowIndex - 2;
  setChangeDoc(attache, pos);
}

//표준품 변경문서검색 팝업 호출
function popupChangeDoc2() {
  var pos = relDocTable.clickedRowIndex - 2;
  var form = document.forms[0];

  var docNo;

  var targetTable = document.getElementById("relDocTable");
  var length = targetTable.rows.length;
   if( length > 3 ){
     docNo = form.relEcaDocNo[pos].value;
   }else{
     docNo = form.relEcaDocNo.value;
   }

  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one&obj=authorStatus^APPROVED&obj=isReview^Y&obj=docNo^"+docNo;
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=800px; dialogHeight:540px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }

  setChangeDoc(attache, pos);
}

//표준품 변경문서검색 팝업 리턴 세팅
function setChangeDoc(objArr, pos) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 3) {
    if(form.relEcaDocNo[pos].value != trArr[1]) {
      alert("문서번호가 불일치합니다.");
      return;
    } else if(form.relEcaDocPreRev[pos].value > trArr[3]) {
      alert("변경후 버전이 변경전 버전보다 커야 합니다.");
      return;
    }
    form.relEcaDocAfterRev[pos].value = trArr[3];
  } else {
    if(form.relEcaDocNo.value != trArr[1]) {
      alert("문서번호가 불일치합니다.");
      return;
    } else if(form.relEcaDocPreRev.value > trArr[3]) {
      alert("변경후 버전이 변경전 버전보다 커야 합니다.");
      return;
    }
    form.relEcaDocAfterRev.value = trArr[3];
  }
}

//도면변경 팝업
function changeEpmDoc(oid) {
  var form = document.forms[0];
  var pos = relEpmTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relEpmTable");
  var tableRows = targetTable.rows.length;

  var url = "/plm/jsp/ecm/ModalFormScroll.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&obj=oid^" + oid+"&obj=isECM^"+"Y";
  
  var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=1024,height=768";
  window.open(url,'CreateEPMDocument',opts);
  
  /* arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=auto; dialogWidth=1024px; dialogHeight:768px; center:yes");
  if(typeof arr == "undefined" || arr == null) {
    return;
  }
  if(arr == false) {
    //alert("도면변경이 실패했습니다.")
  } else {
    //alert("도면변경이 완료되었습니다.")
    if(tableRows > 3){
      form.relEcaEpmChangeYn[pos].value = "Y";
    } else {
      form.relEcaEpmChangeYn.value = "Y";
    }
    doSave(2);
  } */
}

//도면개정
function reviseEpmDoc() {
  var body = document.getElementById("relEpmTable");
  if (body.rows.length < 3) return;
  var formNameStr = "document.forms[0].";
  var form = document.forms[0];
  var objChecks = eval(formNameStr + "chkSelectRelEpm");

  if( document.forms[0].isChanged.value == 'N' )
  {
    if( isChecked( "chkSelectRelEpm" ) )
    {
      var listVal = "";

      var size = body.rows.length - 2;
      var selCnt = 0;
      var curRow = 0;
      if(size <= 1) {
        if (objChecks.checked || objChecks[0].checked) {
          if(objChecks.checked) {
            if(form.relEcaEpmPreRev.value >= form.relEcaEpmAfterRev.value) {
              form.relEcaEpmDocReviseYn.value = "Y";
              selCnt++;
            }
          } else if(objChecks[0].checked) {
            if(form.relEcaEpmPreRev[0].value >= form.relEcaEpmAfterRev[0].value) {
              form.relEcaEpmDocReviseYn[0].value = "Y";
              selCnt++;
            }
          }
        }
      } else {
        //for (var i = body.rows.length; i > 2; i--) {
        for( var i=0; i <body.rows.length-2 ; i++ ){
          //curRow = i - 3;
          curRow = i;
          if (objChecks[curRow].checked) {
            if(form.relEcaEpmPreRev[curRow].value >= form.relEcaEpmAfterRev[curRow].value) {
              form.relEcaEpmDocReviseYn[curRow].value = "Y";
              selCnt++;
            }
          }
        }
      }
      if(selCnt > 0) {
        doSave(2);
      } else {
        alert("개정할 도면이 없습니다.");
      }
    }
    else
    {
      alert("개정할 도면이 없습니다.");
    }
  }
  else
  {
    alert("저장 후 개정을 수행하세요.");
    return;
  }
}

//도면 개정취소
function cancelRevEpmDoc() {
  if(!confirm("개정을 취소하시겠습니까?")) return;

  var body = document.getElementById("relEpmTable");
  if (body.rows.length < 3) return;
  var formNameStr = "document.forms[0].";
  var form = document.forms[0];
  var objChecks = eval(formNameStr + "chkSelectRelEpm");

  if( document.forms[0].isChanged.value == 'N' )
  {
    if( isChecked( "chkSelectRelEpm" ) )
    {
      var listVal = "";

      var size = body.rows.length - 2;
      var selCnt = 0;
      var curRow = 0;
      if(size <= 1) {
        if (objChecks.checked || objChecks[0].checked) {
          if(objChecks.checked) {
            if(form.relEcaEpmPreRev.value < form.relEcaEpmAfterRev.value) {
              form.relEcaEpmDocCancelRevYn.value = "Y";
              selCnt++;
            }
          } else if(objChecks[0].checked) {
            if(form.relEcaEpmPreRev[0].value < form.relEcaEpmAfterRev[0].value) {
              form.relEcaEpmDocCancelRevYn[0].value = "Y";
              selCnt++;
            }
          }
        }
      } else {
        //for (var i = body.rows.length; i > 2; i--) {
        for( var i=0; i <body.rows.length-2 ; i++ ){
          //curRow = i - 3;
          curRow = i;
          if (objChecks[curRow].checked) {
            if(form.relEcaEpmPreRev[curRow].value < form.relEcaEpmAfterRev[curRow].value) {
              form.relEcaEpmDocCancelRevYn[curRow].value = "Y";
              selCnt++;
            }
          }
        }
      }
      if(selCnt > 0) {
        doSave(2);
      } else {
        alert("개정 취소할 도면이 없습니다.");
      }
    }
    else
    {
      alert("개정 취소할 도면이 없습니다.");
    }
  }
  else
  {
    alert("저장 후 개정취소를 수행하세요");
    return;
  }
}

//도면 C/O Workspace
function callCOWorkspace(epmoid) {
  var form = document.forms[0];
  
  var path = '<%=WTProperties.getServerCodebase()%>';
  //var uri = path+'servlet/WindchillAuthGW/wt.enterprise.URLProcessor/URLTemplateAction?u8&newpjl=true&ref=project&oid=OR:'+epmoid+'&action=WFDOWNLOAD&soln=pjl';
  var uri = path+'servlet/WindchillAuthGW/wt.enterprise.URLProcessor/URLTemplateAction?u8&Action=UwgmRequestProcessor&newpjl=true&ref=project&oid=OR:'+epmoid+'&action=WFDOWNLOAD&soln=pjl';
		  
  AddToWorkSpaceWin = window.open(uri,'AddToWorkSpaceWin','width=1200,height=700, status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes , left=0, top=0');
  AddToWorkSpaceWin.focus();
}

//금형 BOM 일괄개정 추가 
function reviseBomAll(){
        // 체크가 하나라도 되어 있어야 한다.
        if($("input:checkbox[name='chkSelectRelBom']").is(":checked") == false) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "04012") %><%--개정할 부품을 리스트에서 선택하여 주십시오.--%>');
            return;
        }
        
        var form = document.forms[0];
        
        var chkSelectRelBom = document.getElementsByName("chkSelectRelBom");
        if(chkSelectRelBom == null || typeof chkSelectRelBom == 'undefined') return;
        var chkSelectRelBomLength = chkSelectRelBom.length;
        
		if (!confirm('<%=messageService.getString("e3ps.message.ket_message", "04340") %><%--부품을 일괄개정하시겠습니까?--%>') ) return;
		        
		        if(chkSelectRelBomLength == 1) {
		        	if(chkSelectRelBom.checked == true){
		                if(form.relEcaBomOid.value != '') {
		                    form.chkSelectRelBom.checked = true;
		                } else {
		                    
		                    alert('<%=messageService.getString("e3ps.message.ket_message", "04300") %><%--개정할 부품이 존재하지 않습니다.--%>');
		                    form.chkSelectRelBom.checked = false;
		                    return;
		                    
		                }
		        	}
		        } else {
		            for(var i=0; i < chkSelectRelBomLength; i++) {
		            	if(chkSelectRelBom[i].checked == true){
		                    if(form.relEcaBomOid[i].value != '') {
		                        form.chkSelectRelBom[i].checked = true;
		                    } else {
		                        
		                        alert('<%=messageService.getString("e3ps.message.ket_message", "04300") %><%--개정할 부품이 존재하지 않습니다.--%>');
		                        for(var i=0; i < chkSelectRelBomLength; i++) {
		                            form.chkSelectRelBom[i].checked = false;
		                        }
		                        return;
		                        
		                    }
		            	}
		                    
		            }
		            
		        }
		        
       if( form.isChanged.value == 'N' )
       {
		for(var index=0; index < chkSelectRelBomLength; index++){
				 if(chkSelectRelBom[index].checked == true){
					   if(chkSelectRelBomLength > 1){					 
						  oid = form.relEcaBomOid[index].value;
					      form.relEcaBomChangeYn[index].value = "Y";
					      form.relEcaBomReviseYn[index].value = "Y";
					   }else{
						   oid = form.relEcaBomOid.value;
					       form.relEcaBomChangeYn.value = "Y";
					       form.relEcaBomReviseYn.value = "Y";					   
					   }
			      }
			    
			  }
		   doSave(2);
		}else
        {
            alert("저장 후 개정을 수행하세요");
            return;
        }
		        
}

//금형 BOM 일괄개정 취소
function cancelReviseBomAll(){
	
	if(!confirm("개정을 취소하시겠습니까?")) return;
	
	
	
	var oid;
	var form = document.forms[0];
	var chkSelectRelBom = document.getElementsByName("chkSelectRelBom");
	var chkSelectRelBomLength = chkSelectRelBom.length;
    for(var i=0; i < chkSelectRelBomLength; i++) {
        if(chkSelectRelBomLength == 1){
        	if(form.chkSelectRelBom.checked) {  
        		if(form.relEcaBomAfterRev.value != '') {
        			   	form.chkSelectRelBom.checked = true;
        		}else{
        			alert('<%=messageService.getString("e3ps.message.ket_message", "04310") %><%--개정취소할 부품이 존재하지 않습니다.--%>');
                        form.chkSelectRelBom.checked = false;
                        form.chkAllRelBom.checked = false;
                    return;
        		}
        	}
        }else{
	        if(chkSelectRelBom[i].checked == true) {
	                if(form.relEcaBomAfterRev[i].value != '') {
	                    form.chkSelectRelBom[i].checked = true;
	                } else {
	                    
	                    alert('<%=messageService.getString("e3ps.message.ket_message", "04310") %><%--개정취소할 부품이 존재하지 않습니다.--%>');
	                    for(var i=0; i < chkSelectRelBomLength; i++) {
	                        form.chkSelectRelBom[i].checked = false;
	                        form.chkAllRelBom.checked = false;
	                    }
	                    return;
	                    
	                }
	                   
	        } else {
	            form.chkSelectRelBom[i].checked = false;
	            
	        }
        }
    }

	for(var index=0; index < chkSelectRelBomLength; index++){
           if(chkSelectRelBomLength == 1){
        	   if(form.chkSelectRelBom.checked){
        		   oid = form.relEcaBomOid.value;
                   form.relEcaBomChangeYn.value = "Y";
                   form.relEcaBomReviseCancel.value="Y"; 
        	   }
           }else{
			   if(chkSelectRelBom[index].checked == true){
	        	    if(chkSelectRelBomLength > 1){
	        	    	oid = form.relEcaBomOid[index].value;
	        	        form.relEcaBomChangeYn[index].value = "Y";
	        	        form.relEcaBomReviseCancel[index].value="Y";
	        	    }else{
	        	    	oid = form.relEcaBomOid.value;
	        	        form.relEcaBomChangeYn.value = "Y";
	        	        form.relEcaBomReviseCancel.value="Y";  
	        	    }
	           }
           }
           form.isChanged.value = "Y";
    }
	  doSave(2);
}


//BOM 개정
function reviseBom() {
  var oid;
  var form = document.forms[0];
  var pos = relBomTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relBomTable");
  var tableRows = targetTable.rows.length;
  if( form.isChanged.value == 'N' )
  {
    if(tableRows > 3){
      oid = form.relEcaBomOid[pos].value;
      form.relEcaBomChangeYn[pos].value = "Y";
      form.relEcaBomReviseYn[pos].value = "Y";
    } else {
      oid = form.relEcaBomOid.value;
      form.relEcaBomChangeYn.value = "Y";
      form.relEcaBomReviseYn.value = "Y";
    }
    doSave(2);
  }
  else
  {
    alert("저장 후 개정을 수행하세요");
    return;
  }

//  var url = "/plm/jsp/ecm/ModalFormScroll.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&obj=oid^" + oid;
//  arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=auto; dialogWidth=1024px; dialogHeight:500px; center:yes");
//  if(typeof arr == "undefined" || arr == null) {
//    return;
//  }
//  if(arr == false) {
//    alert("도면변경이 완료되었습니다.")
//  } else {
//    alert("도면변경이 실패했습니다.")
//  }
}

function cancelReviseBom() {
  if(!confirm("개정을 취소하시겠습니까?")) return;

  var oid;
  var form = document.forms[0];
  var pos = relBomTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relBomTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 3){
    oid = form.relEcaBomOid[pos].value;
    form.relEcaBomChangeYn[pos].value = "Y";
    form.relEcaBomReviseCancel[pos].value="Y";
  } else {
    oid = form.relEcaBomOid.value;
    form.relEcaBomChangeYn.value = "Y";
    form.relEcaBomReviseCancel.value="Y";
  }

  form.isChanged.value = 'Y';
  doSave(2);

//  var url = "/plm/jsp/ecm/ModalFormScroll.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&obj=oid^" + oid;
//  arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=auto; dialogWidth=1024px; dialogHeight:500px; center:yes");
//  if(typeof arr == "undefined" || arr == null) {
//    return;
//  }
//  if(arr == false) {
//    alert("도면변경이 완료되었습니다.")
//  } else {
//    alert("도면변경이 실패했습니다.")
//  }
}

<% /* 변경 */ %>
function changeBom2(pos)
{
    var form = document.forms[0];
    //var pos = relBomTable.clickedRowIndex - 2;
       
    var chkSelectRelBom = document.getElementsByName("chkSelectRelBom");
    if(chkSelectRelBom == null || typeof chkSelectRelBom == 'undefined') return;
    var chkSelectRelBomLength = chkSelectRelBom.length;

      
    // 일괄변경
    if(pos == null || typeof pos == 'undefined') {

    } else {
        var relObjOid = "";
        if(chkSelectRelBomLength == 1) {
                
            relObjOid = form.relObjOid.value;
            
        } else {
            relObjOid = form.relObjOid[pos].value;
        }
        
        form.isChanged.value = 'Y';
        var url = "/plm/extcore/jsp/bom/KETBomEditorMain.jsp?oid="+ relObjOid;
        openWindow(url,"BOMEditor","1024","700","menubar=no,toolbar=no,location=no,scrollbars=no,status=no");       
            
    }   
    
}

<% /* @deprecated */ %>
//BOM변경
function changeBom() {
  
  var afterWTPartOid = "";
  var oid;
  var form = document.forms[0];
  var pos = relBomTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relBomTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 3){
    //oid = form.ecoheaderoid[pos].value;
	  
      afterWTPartOid = form.afterWTPartOid[pos].value;
      oid = form.beforePartOid[pos].value;
  } else {
    //oid = form.ecoheaderoid.value;
    
      afterWTPartOid = form.afterWTPartOid.value;
      oid = form.beforePartOid.value;
  }

  
  if(afterWTPartOid != null && afterWTPartOid != '') {
	  oid = afterWTPartOid;
      
  }
  
  
  document.forms[0].isChanged.value = 'Y';
  
  //form.isChanged.value = 'Y';
  var url = "/plm/extcore/jsp/bom/KETBomEditorMain.jsp?oid="+ oid;
  openWindow(url,"BOMEditor","1024","700","menubar=no,toolbar=no,location=no,scrollbars=no,status=no");       

  /* 
  var url = "/plm/jsp/bom/BOMEditorLoading.jsp?oid=VR:" + oid + "&ecoType=standard";
  openWindow(url,"BOMEditor","400","400","height=400,menubar=no,toolbar=no,location=no,scrollbars=no,status=no");
  */
}

<% /* deprecated */ %>
function setOwnerObj(isOwner) {
  var flag = false;
  if(isOwner == "false") {
    flag = true;
  }
  var form = document.forms[0];

  form.divisionFlag.disabled = flag;
//  form.divisionFlag.setAttribute("class", "txt_field");
  form.vendorFlag.disabled = flag;
  for (var i = 0; i<2; i++) {
    form.rdoDevYn[i].disabled = flag;
  }
  for (var i = 0; i<7; i++) {
    form.chkChangeReason[i].disabled = flag;
  }
  for (var i = 0; i<12; i++) {
    form.chkIncreaseProdType[i].disabled = flag;
  }
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
  var url = "/plm/servlet/e3ps/MoldEcrServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","800","680","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}
//문서상세조회 팝업
function viewRelDoc()
{
  var inx = eval('relDocTable').clickedRowIndex;
  var form = document.forms[0];

  var newOid;

  var targetTable = document.getElementById("relDocTable");
  var length = targetTable.rows.length;
   if( length > 3 ){
     newOid = form.relEcaDocOid[inx-2].value;
   }else{
     newOid = form.relEcaDocOid.value;
   }

  var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid="+newOid+"&buttenView=T"+"&isRefresh=N";
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//제품ECO 상세조회 팝업
function viewRelProdEco(oid){
  var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","850","680","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

function checkBudget()
{
  var form = document.forms[0];
  var pos = relPartTable.clickedRowIndex;
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;

  var division  = "";
  var dev_yn = "";
  var dieno = "";
  var expectCost = "";

  if( form.divisionFlag.value == 'C' )
  {
    division = "1";
  }
  else
  {
    division = "2";
  }

  var dev_yn = document.all("rdoDevYn");
  var str_dev_yn = '';
  for( var inx=0; inx < dev_yn.length ; inx++)
  {
    if( document.forms[0].rdoDevYn[inx].checked )
    {
      str_dev_yn  = document.forms[0].rdoDevYn[inx].value;
    }
  }

  if( str_dev_yn == "D" )
  {
    dev_yn = "1";
  }
  else
  {
    dev_yn = "2";
  }

  if( tableRows > 1 )
  {
    dieno = form.relPartNumber[pos].value;
    expectCost = form.expectCost[pos].value;
    expectCost = expectCost.replace(",","");
  }
  else
  {
    dieno = form.relPartNumber.value;
    expectCost =form.expectCost.value;
    expectCost = expectCost.replace(",","");
  }

  if( isNumber(expectCost) )
  {
    document.forms[0].target="download";
    document.forms[0].action="/plm/jsp/ecm/BudgetInterfaceCheck.jsp?devYn="+dev_yn+"&division="+division+"&dieNo="+dieno+"&cost="+expectCost+"&rowInx="+pos;
    document.forms[0].submit();
  }
  else
  {
      alert("비용이 존재하지 않아서 예산을 확인할 수 없습니다.");
      if( tableRows > 1 )
      {
        form.expectCost[pos].focus();
      }
      else
      {
        form.expectCost.focus();
      }

      return;
  }
}

function setBudgetYn( budget_yn, row_inx, msg )
{
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;

  if( tableRows > 1 )
  {
    if( budget_yn == 'N' )
    {
       document.forms[0].budgetYnName[row_inx].value = '미확보';
     }
     else
     {
       document.forms[0].budgetYnName[row_inx].value = '확보';
     }

     document.forms[0].secureBudgetYn[row_inx].value = budget_yn;
     if( msg != '' )
     {
       alert(msg);
     }
   }
   else
   {
     if( budget_yn == 'N' )
    {
       document.forms[0].budgetYnName.value = '미확보';
     }
     else
     {
       document.forms[0].budgetYnName.value = '확보';
     }

     document.forms[0].secureBudgetYn.value = budget_yn;

     if( msg != '' )
     {
       alert(msg);
     }
   }
}

//프로젝트상세조회 팝업
function setStdPartLine() {
  var form = document.forms[0];

  var pos = relDocTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;

  var docLinkOid = "";
  var docNo = "";

  if(tableRows > 3){
    docLinkOid = form.relEcaDocLinkOid[pos].value;
    docNo = form.relEcaDocNo[pos].value;
  } else {
    docLinkOid = form.relEcaDocLinkOid.value;
    docNo = form.relEcaDocNo.value;
  }

  var url = "/plm/servlet/e3ps/MoldStdPartServlet?docLinkOid="+docLinkOid+"&docNo="+docNo+"&ecoOid="+form.oid.value+"&ecoNo="+form.ecoId.value;

  var height  = screen.height;
  var width  = screen.width;
  var leftpos = width/2 - 200/2;
  var toppos  = height/2 - 400/2;
  if(leftpos<0) leftpos=0;
  if(toppos<0) toppos=0;

  window.open('', "StdPart", "status=no,  width=650, height=500, resizable=no, scrollbars=no, statusbar=no, left="+leftpos+", top="+toppos+",center=yes");

    form.action= url ;
  form.target="StdPart";
  form.method= "post"
  form.submit();
}

</script>

</head>
<body onload="javascript:onLoad();">
<form method="post" action="">
<input type="hidden" name="cmd" value="create">
<input type="hidden" name="oid" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(ketMoldChangeOrderVO.getOid());}%>">
<input type="hidden" name="loginUserOid" value="<%=loginUserOid%>">
<input type="hidden" name="ecaType" value="<%=ecaType%>">
<input type="hidden" name="isOwner" value="<%=isOwner%>">
<input type="hidden" name="ecoId" value="<%=ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoId()%>">
<input type="hidden" name="devYn" value="">
<input type="hidden" name="changeReason" value="">
<input type="hidden" name="increaseProdType" value="">
<input type="hidden" name="deleteFileList" value="">
<input type="hidden" name="deleteRelEcrList" value="">
<input type="hidden" name="deleteRelProdEcoList" value="">
<input type="hidden" name="deleteRelPartList" value="">
<input type="hidden" name="deleteRelBomList" value="">
<input type="hidden" name="deleteRelEpmList" value="">
<input type="hidden" name="deleteRelDocList" value="">
<input type="hidden" name="isToDo" value="Y">
<input type="hidden" name="isChanged" value="N">
<input type="hidden" name="isCompleteModify" value="<%=isCompleteModify%>">

<input type="hidden" name="page" value="<%=ParamUtil.getParameter(request, "page")%>">
<input type="hidden" name="totalCount" value="<%=ParamUtil.getParameter(request, "totalCount")%>">
<input type="hidden" name="sortColumn" value="<%=ParamUtil.getParameter(request, "sortColumn")%>">
<input type="hidden" name="param" value="<%=ParamUtil.getParameter(request, "param")%>">
<input type="hidden" name="perPage" value="<%=ParamUtil.getParameter(request, "perPage")%>">
<input type="hidden" name="autoSearch" value="<%=ParamUtil.getParameter(request, "autoSearch")%>">
<input type="hidden" name="srchpartOid" value="<%=ParamUtil.getParameter(request, "partOid")%>">
<input type="hidden" name="srchpartNo" value="<%=ParamUtil.getParameter(request, "srchpartNo")%>">
<input type="hidden" name="srchpartName" value="<%=ParamUtil.getParameter(request, "srchpartName")%>">
<input type="hidden" name="srchprojectNo" value="<%=ParamUtil.getParameter(request, "srchprojectNo")%>">
<input type="hidden" name="srchprojectName" value="<%=ParamUtil.getParameter(request, "srchprojectName")%>">
<input type="hidden" name="srchorgName" value="<%=ParamUtil.getParameter(request, "srchorgName")%>">
<input type="hidden" name="srchcreatorOid" value="<%=ParamUtil.getParameter(request, "srchcreatorOid")%>">
<input type="hidden" name="srchcreatorName" value="<%=ParamUtil.getParameter(request, "srchcreatorName")%>">
<input type="hidden" name="srchecoId" value="<%=ParamUtil.getParameter(request, "srchecoId")%>">
<input type="hidden" name="srchdivisionFlag" value="<%=ParamUtil.getParameter(request, "srchdivisionFlag")%>">
<input type="hidden" name="srchdevYn" value="<%=ParamUtil.getParameter(request, "srchdevYn")%>">
<input type="hidden" name="srchsancStateFlag" value="<%=ParamUtil.getParameter(request, "srchsancStateFlag")%>">
<input type="hidden" name="srchecoName" value="<%=ParamUtil.getParameter(request, "srchecoName")%>">
<input type="hidden" name="srchprodMoldCls" value="<%=ParamUtil.getParameter(request, "srchprodMoldCls")%>">
<input type="hidden" name="srchcreateStartDate" value="<%=ParamUtil.getParameter(request, "srchcreateStartDate")%>">
<input type="hidden" name="srchcreateEndDate" value="<%=ParamUtil.getParameter(request, "srchcreateEndDate")%>">
<input type="hidden" name="initTab" value="<%=ParamUtil.getParameter(request, "initTab", "1")%>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01031") %><%--금형ECO 변경 활동--%></td>
                  <!-- td align="right">
                       <img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                       <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                       <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01001") %><%--금형 ECO 등록/수정--%>
                </tr -->
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
    
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">&nbsp;</td>
            <td class="font_03">
              <table id="infoShow" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>   
                </tr>
              </table>
              <table id="infoHide" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>   
                </tr>
              </table>
              <table id="infoEcn" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                                       
                </tr>
                </table>              
            </td>
            <td align="right">
	            <table border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                  <td><table border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
	                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                      </tr>
	                    </table></td>
	              </tr>
	            </table>
            </td>
        </tr>
      </table>
 
      <table width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">
            <!-- table width="100%" height="700" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">&nbsp;</td>
              </tr>
            </table -->
            
            <!--내용-->
            <div style="position:; width:100%; height:670px; overflow-x:auto; overflow-y:auto;">
            <div id="tabBasic" style="position:; display:none; z-index:2; ">

                <%@include file="/jsp/ecm/ViewMoldEcoBasicForm.jsp" %>

            </div>
            <div id="tabActivity" style="position:; display:none; z-index:1; ">
              
              <%
              if( ecaType.equals("1") || ecaType.equals("2") || ecaType.equals("3") ) { 
              %>
                  <%@include file="/jsp/ecm/CreateMoldEcoChangeActivityForm.jsp" %>
              <%
              } else { 
              %>
                  <%@include file="/jsp/ecm/ViewMoldEcoActivityForm.jsp" %>
              <%
              }
              %>
              
            </div>
            <div id="tabEcn" style="position:; display:none; z-index:1; ">
              
              <%
              if("APPROVED".equals(ketMoldChangeOrderVO.getProgressState()) ) {
              %>
                <%@include file="/jsp/ecm/reform/ToDoMoldEcnForm.jsp" %>
              <%
              } else {
              %>
                <%@include file="/jsp/ecm/reform/ViewMoldEcnForm.jsp" %>
              <%
              }
              %>
             
            </div>  
            </div>             
            
            </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table></td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
