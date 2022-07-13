<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String command = request.getParameter("command");
    String tmpName = request.getParameter("tmpName");
    String tmpCode = request.getParameter("tmpCode");
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");
    String type = request.getParameter("type");
    String wType = request.getParameter("wType");
    String selectReview = request.getParameter("selectReview");
    String moldType = request.getParameter("moldType");    
    String moid = request.getParameter("oid");
    
    MoldProject project = (MoldProject)CommonUtil.getObject(moid);
    MoldItemInfo mInfo = project.getMoldInfo();
    String dieNo = mInfo.getDieNo();
    String divisionValue = mInfo.getProject().getTeamType();

    ExtendScheduleData sdata = (ExtendScheduleData)project.getPjtSchedule().getObject();

    Timestamp startDate = sdata.getPlanStartDate();
    String startDate_str = DateUtil.getDateString(startDate, "d");

    boolean isPurchase = "구매품".equals(mInfo.getItemType());
    
    String makeType = "";

    String making = mInfo.getMaking();

    if("사내".equals(making)){
        makeType = "1";
    }else if("외주".equals(making)){
        makeType = "2";
    }else if("OEM".equals(making)){
        makeType = "3";
    }

    if(command == null)
        command = "select";

    if(tmpName == null) {
        tmpName = "";
    }
    if(tmpCode == null) {
        tmpCode = "";
    }

    if(mode == null) {
        mode = "one";
    }
    if(invokeMethod == null) {
        invokeMethod = "";
    }
    if(type == null){
        type = "";
    }
    if(wType == null){
        wType = "3";
    }
    if(selectReview == null){
        selectReview = "";
    }

%>


<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.MoldProject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%if(isPurchase){ %>
<title>구매품 프로젝트 WBS 등록<%--구매품 프로젝트 WBS 등록--%></title>
<%}else{ %>
<title><%=messageService.getString("e3ps.message.ket_message", "01025") %><%--금형 프로젝트 WBS 등록--%></title>
<%} %>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<script>

function onSearch() {
    form = document.forms[0];

    var wname = form.tmpName.value;
    var wbstypeV = form.wType.value;

    form.method = "post";
    form.action = "/plm/jsp/project/template/ProjectTempListTable.jsp?name=" + wname + "&code=&type=<%=type%>" + "&wType=<%=wType%>&selectReview=<%=selectReview%>" + "&makeType=<%=makeType%>&division=<%=divisionValue%>&isPurchase=<%=isPurchase %>";
    form.target = "list";
    form.submit();
}

function onSelect() {
    form = document.forms[0];

    var arr = list.checkList();
    if(arr.length == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01817") %><%--선택한 Template이 없습니다--%>");
        return;
    }

<%  if(invokeMethod.length() == 0) {  %>
    //modal dialog
    selectModalDialog(arr);
<%  } else {  %>
    //open window
    selectOpenWindow(arr);
<%  }  %>

}

function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
    window.close();
}

/*****  날짜 check 시작  *******/
//문자열 일괄전환 함수
function funcReplaceStrAll(org_str, find_str, replace_str) {
  var pos = org_str.indexOf(find_str);
  while(pos != -1) {
      pre_str  = org_str.substring(0, pos);
      post_str = org_str.substring(pos + find_str.length, org_str.length);
      org_str  = pre_str + replace_str + post_str;
      pos = org_str.indexOf(find_str);
  }
  return org_str;
}

//*******************************************************************
//년월 입력시 마지막 일자
function  getEndOfMonthDay( yy, mm ) {
  var max_days=0;
  if(mm == 1) {
      max_days = 31 ;
  } else if(mm == 2) {
      if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))  max_days = 29;
      else                                                         max_days = 28;
  }
  else if (mm == 3)   max_days = 31;
  else if (mm == 4)   max_days = 30;
  else if (mm == 5)   max_days = 31;
  else if (mm == 6)   max_days = 30;
  else if (mm == 7)   max_days = 31;
  else if (mm == 8)   max_days = 31;
  else if (mm == 9)   max_days = 30;
  else if (mm == 10)  max_days = 31;
  else if (mm == 11)  max_days = 30;
  else if (mm == 12)  max_days = 31;
  else                return '';

  return max_days;
}

function isValidDate(obj, maxLength) {
  var retVal = true;
  var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
    //document.forms[0].duration.value = "";

    if(obj.value == "") {
        return;
    }

    val=obj.value;
    re=/[^0-9]/gi;
    obj.value=val.replace(re,"");

  var inputDate = funcReplaceStrAll(obj.value,  '년', '');
  inputDate     = funcReplaceStrAll(inputDate,  '월', '');
  inputDate     = funcReplaceStrAll(inputDate,  '일', '');

  var yyyy = inputDate.substring(0, 4);
  var mm   = (maxLength >= 6)?inputDate.substring(4, 6):"01";
  var dd   = (maxLength == 8)?inputDate.substring(6, 8):"01";

  if (isNaN(yyyy) || parseInt(yyyy) < 1000) return viewErrMsg(obj, msg);
  if (isNaN(mm) || parseFloat(mm) > 12 || parseFloat(mm) < 1) return viewErrMsg(obj, msg);
  if (isNaN(dd) || parseFloat(dd) < 1 || (parseFloat(dd) > getEndOfMonthDay(parseFloat(yyyy.substring(2,4)), parseFloat(mm))) ) return viewErrMsg(obj, msg);

    if(inputDate.length == 8) {
      inputDate = inputDate.substring(0, 8); //미봉책
    }else{
        alert('<%=messageService.getString("e3ps.message.ket_message", "02383") %><%--입력된 값이 8자리 숫자가 아닙니다 8자리 숫자를 입력해주세요--%>');
        return;
    }

    obj.value = yyyy+ "-" +mm+ "-" +dd;
  return true;
}

function viewErrMsg(obj,msg) {
    alert(msg);
}
/*****  날짜 check 끝 *******/

function delDate(rname){
    document.getElementById(rname).value = "";
}

function onSave(){
    var arr = list.checkList();
    if(arr.length == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01817") %><%--선택한 Template이 없습니다--%>");
        return;
    }

	showProcessing();
    form = document.forms[0];

    form.mode.value = "wbs";
    form.method = "post";
    form.action = "/plm/jsp/project/MoldProjectAction.jsp?oid=" + arr[0][0];
    form.submit();
}
$(document).ready(function(){
	CalendarUtil.dateInputFormat('date');
});
</script>
</head>
<body>
<form>
<input type="hidden" name="wType" value="3"></input>
<input type="hidden" name="mode" value="<%=mode%>">
<input type='hidden' name='command' value="<%=command%>">
<input type='hidden' name='moid' value="<%=moid %>"></input>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <%if(isPurchase){ %>
                <td class="font_01">구매품 프로젝트 WBS 등록<%--구매품 프로젝트 WBS 등록--%></td>
                <%}else{ %>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01109") %><%--금형프로젝트 WBS 등록--%></td>
                <%} %>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="710" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                  <td width="75" class="tdblueL">Die No</td>
                  <td width="100" class="tdwhiteL"><%=dieNo %></td>
                <td width="75" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%><span class="red">*</span></td>
                <td width="150" class="tdwhiteL">
                  <input type="text" name="date" class="txt_field"  style="width:100" id="date" value="<%=startDate_str %>" >
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                           <tr>
                               <td>
                                <input type="hidden" name=wType value="<%=wType%>" >
                                <b>Name</b> : <input name="tmpName" class="txt_field" width="50px" value="<%=tmpName%>">
                               </td>
                            <td>&nbsp;</td>
                            <td>
                                   <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                    </table>
                            </td>
                           </tr>
                          </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="700">
                              <tr>
                                <td class="space5"></td>
                              </tr>
                            </table>
                          <table border="0" cellpadding="0" cellspacing="0" width="700">
                            <tr>
                                <td>
                                    <iframe src="/plm/jsp/project/template/ProjectTempListTable.jsp?command=select&mode=<%=mode%>&wType=<%=wType%>&selectReview=<%=selectReview%>&makeType=<%=makeType %>&division=<%=project.getMoldInfo().getProject().getTeamType()%>&moldType=<%=divisionValue%>&isPurchase=<%=isPurchase %>" id="list" name="list" frameborder="0" width="100%" height="400" leftmargin="0" topmargin="0" scrolling="no"></iframe>
                                </td>
                            </tr>
                        </table>

                    </td>
                    </tr>
                </table>
            </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</form>
</html>
