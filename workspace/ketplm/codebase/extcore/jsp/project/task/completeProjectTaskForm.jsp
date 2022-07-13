<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%@ page import = "wt.vc.Versioned"%>
<%@ page import = "wt.vc.VersionControlHelper"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import = "wt.org.*,wt.session.*"%>
<%@ page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 e3ps.project.*,
                 ext.ket.project.task.entity.*,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%
String oid = request.getParameter("oid");
E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
E3PSProject project = (E3PSProject)task.getProject();
ExtendScheduleData schData = (ExtendScheduleData)task.getTaskSchedule().getObject();
Timestamp planStartDateTime =  schData.getPlanStartDate();
String planStartDate = "";
if(planStartDateTime!=null) planStartDate = DateUtil.getTimeFormat(planStartDateTime,"yyyy-MM-dd");

Timestamp planEndDateTime =  schData.getPlanEndDate();
String planEndDate = "";
if(planEndDateTime!=null)  planEndDate = DateUtil.getTimeFormat(planEndDateTime,"yyyy-MM-dd");

java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
java.util.Date beginDate = formatter.parse(planStartDate);
java.util.Date endDate = formatter.parse(planEndDate);
long diffDays = (endDate.getTime() - beginDate.getTime()) /( 24 * 60 * 60 * 1000);

Timestamp execStartDateTime =  schData.getExecStartDate();
String execStartDate =  "";
if(execStartDateTime!=null) execStartDate = DateUtil.getTimeFormat(execStartDateTime,"yyyy-MM-dd");

Timestamp execEndDateTime =  schData.getExecEndDate();
String execEndDate =  "";
if(execEndDateTime!=null) execEndDate = DateUtil.getTimeFormat(execEndDateTime,"yyyy-MM-dd");


wt.fc.QueryResult outputQr = e3ps.project.beans.ProjectOutputHelper.manager.getTaskOutput(task);
int outputCnt = 0;
if(outputQr!=null) outputCnt = outputQr.size();


%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">


$(document).ready(function(){
    CommonUtil.singleSelect('delayType',100);

});

//완료버튼 클릭
function fn_taskSchComplete() {
	
	var now = new Date(); 
	var todayAtMidn = new Date(now.getFullYear(), now.getMonth(), now.getDate());
	
	// Set specificDate to a specified date at midnight.
	var planEndDt = new Date('<%=planEndDate %>T15:20:00Z');
	var dayOfMonth = planEndDt.getDate();
	planEndDt.setDate(dayOfMonth-1);
	
//     alert('a:'+todayAtMidn.getTime() + ',b:'+planEndDt.getTime());
//     alert('c:'+document.projectTaskForm.delayReason.value + ',d:'+document.projectTaskForm.execWorkTime.value);
    if (document.forms[0].execWorkTime.value=='' || document.forms[0].execWorkTime.value=='0' || document.forms[0].execWorkTime.value=='0.0') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07160")%><%--실제작업시간은 필수입니다.--%>');
        document.forms[0].execWorkTime.focus();
        return;
    }
    document.forms[0].execWorkTime.value = Number(document.forms[0].execWorkTime.value);

    if (todayAtMidn.getTime() > planEndDt.getTime()) {
	    if (document.forms[0].delayReason.value=='' || document.forms[0].delayType.value=='') {
	    	alert('<%=messageService.getString("e3ps.message.ket_message", "07159")%><%--계획종료일이 지난 경우 \n 지연사유를 입력하셔야 완료\n 할 수 있습니다.--%>');
	    	document.forms[0].delayReason.focus();
	    	return;
	    }
	}
	
  <%
  if(outputCnt==0) {
  %>
    if (document.forms[0].compReason.value=='' || document.forms[0].compReason.value=='0') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07161")%><%--근거사유는 필수입니다.--%>');
        return;
    }
  <%
  }
  %>
    
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "07162")%><%--테스크완료하시겠습니까?--%>")){
        return;
    }
    //document.forms[0].action = "/plm/jsp/project/PopupReasonAction.jsp";
    document.forms[0].action = "/plm/ext/project/task/completeProjectTaskSchedule.do";
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//저장버튼 클릭
function fn_taskSchSave() {
    if (document.forms[0].execWorkTime.value=='' || document.forms[0].execWorkTime.value=='0') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07160")%><%--실제작업시간은 필수입니다.--%>');
        return;
    }
    
<%
if(outputCnt==0) {
%>
    if (document.forms[0].compReason.value=='' || document.forms[0].compReason.value=='0') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07161")%><%--근거사유는 필수입니다.--%>');
        document.forms[0].compReason.focus();
        return;
    }
<%
}
%>
    
	if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "02463")%><%--저장하시겠습니까?--%>"))
	{
        return;
    }
    //document.forms[0].action = "/plm/jsp/project/PopupReasonAction.jsp";
    document.forms[0].action = "/plm/ext/project/task/saveCompleteProjectTaskSchedule.do";
    document.forms[0].method = "post";
    document.forms[0].submit();
}


function Limit(obj){
	var maxLength = parseInt(obj.getAttribute("maxlength"));
	//alert("maxLength:"+obj.value.length);
	if(obj.value.length >= maxLength){
		alert('<%=messageService.getString("e3ps.message.ket_message", "03268", "지연사유") %><%--{0}은(는) 200자를 초과할 수 없습니다.--%>');
 		obj.value = obj.value.substring(0,maxLength-1);
		return;
	}
}

function checkInputValue(obj, e) {
	
    if(checkValue(obj.value)==false) {  //document.forms[0].execWorkTime.value
<%--         alert('<%=messageService.getString("e3ps.message.ket_message", "07291")%>실제 작업시간은 소수점 1자리까지 입력할수 있습니다'); --%>
        document.forms[0].execWorkTime.value ='';
        document.forms[0].execWorkTime.focus();
        return;
    }
    
    if (e.keyCode == 13) {
	    fn_taskSchSave();
    }
    

}
//Devpia
//소수점 체크
function checkValue(strValue)
{
	
    var digitStr = "01234567890.";
    var isOK = true;
    var firstDotPos;
    var numUnderDotStr;

    // 숫자만으로 되어있는지 체크
    for (var i=0; i<strValue.length; i++) {
        if (digitStr.indexOf(strValue.charAt(i)) == -1) {
            isOK = false;
//             alert(strValue+',strValue:'+strValue.charAt(i));
        }
    }

    // 소수점이 하나만 있는지 체크
    firstDot = strValue.indexOf(".")
    if (firstDot != -1) { // 소수점이 하나이상 존재
        if (strValue.indexOf(".", firstDot+1) != -1) { // 소수점이 두개이상 존재
            alert('소수점을 한개만 입력바랍니다');
            return false;
        } else {
            numUnderDotStr = strValue.substr(firstDot+1);

//             alert('numUnderDotStr:'+numUnderDotStr+',firstDot:'+firstDot);
            if (numUnderDotStr.length > 1) { // 소수점 아래 숫자가 2개 이상이면
            	alert('소수점 아래 한자리만 입력바랍니다');
                return false;
            }
        }
    }
    if(firstDot>=0) {
    	var strValueLast = strValue.substr(strValue.length-1);
        //alert(strValue+',strValueLast:'+strValueLast+',strValue.length:'+strValue.length);
    	if(strValueLast!=0 && strValueLast!=5 && strValueLast!='.') {
    		alert('0.5 시간 단위로 입력바랍니다');
    		return false;
    	}
    }

    
//     var str1 = Math.round(strValue*2);
//     var str2 = Math.floor(strValue*2);
//     var str0 = strValue*2;
    
//     alert('strValue:'+strValue+',isOK:'+isOK);

    if (isOK == false) {
        return false;
    }

    return true;
}

$(function() {
    $("#execWorkTime").on("keyup", function(e){

        if(e.which==13)
        {
            return;
        }
    });
});

</script>
<table border="0" cellpadding="0" cellspacing="0" width="740px">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="740px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="740px" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07163")%><%--테스크 완료--%></td>
                      <td width="10"></td>
                    </tr>
                </table>
                </td>
                <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="715px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                              href="javascript:fn_taskSchComplete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                              href="javascript:fn_taskSchSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                              <!-- 목록  --> <a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
                          </td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      
      
      <form name="projectTaskForm" method="post">
      <input type="hidden" name="oid" value="<%=oid%>"/>
      
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07164")%><%--테스크--%> : <%=task.getTaskName()%><%--테스크--%></td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <COL width="10%"><COL width="15%"><COL width="10%"><COL width="15%">
      
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07165")%><%--계획시작일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="planStartDate" class="txt_field" style="width: 100%" value=""> -->
                <%=planStartDate %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07166")%><%--계획종료일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="planEndDate" class="txt_field" style="width: 100%" value=""> -->
                <%=planEndDate %>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07167")%><%--실제시작일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="execStartDate" class="txt_field" style="width: 100%" value=""> -->
                <%=execStartDate %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07168")%><%--실제종료일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="execEndDate" class="txt_field" style="width: 100%" value=""> -->
                <%=execEndDate %>
            </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07169")%><%--작업시간--%></td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
      
        <COL width="10%"><COL width="15%"><COL width="10%"><COL width="15%">
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07101")%><%--계획작업시간--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="planWorkTime" class="txt_field" style="width: 100%" value=""> -->
                <%=task.getPlanWorkTime()%> hr
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07122")%><%--실제작업시간--%><span class="red">*</span></td>
            <td width="*" class="tdwhiteL">
                <input type="text" id="execWorkTime" name="execWorkTime" style="width: 80%" class="txt_field" value="<%=task.getExecWorkTime()%>" onkeypress="return;" onkeydown="return;" onkeyup="checkInputValue(this, event)"> hr
                
            </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00992")%><%--근거사유--%>
            </td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <COL width="10%"><COL width="40%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00992")%><%--근거사유--%>
  <%
  if(outputCnt==0) {
  %>
  <span class="red">*</span>
  <%
  }
  %>
          </td>
          <td width="*" class="tdwhiteL" cospan="3">
<!--               <input type="textarea" name="execWorkTime" class="txt_field" style="width: 100%"> -->
              <textarea rows="3" cols="60" name="compReason"><%=StringUtil.checkNull(task.getCompReason()) %></textarea>
          </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07170")%><%--지연사유--%></td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <COL width="10%"><COL width="40%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07171")%><%--지연사유유형--%></td>
          <td class="tdwhiteL" cospan="3">
<!--               <input type="text" name="delayType" class="txt_field" style="width: 100%"> -->
              <ket:select id="delayType" name="delayType" className="fm_jmp" style="width: 160px;" codeType="TASKDELAYTYPE" useNullValue="true"  value="<%=task.getDelayType() %>"/>
              
<!--               <select name="planWorkTime"> -->
<!--                 <option value="">선택</option> -->
<!--                 <option value="유형1">유형1</option> -->
<!--               </select> -->
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07170")%><%--지연사유--%></td>
          <td width="*" class="tdwhiteL" cospan="3">
<!--               <input type="textarea" name="delayReason" class="txt_field" style="width: 100%"> -->
              <textarea rows="5" cols="60" name="delayReason" maxlength="200" onkeyup="Limit(this)"><%=StringUtil.checkNull(task.getDelayReason()) %></textarea>
          </td>
        </tr>
      </table>
      <div id="hiddenText" style="visibility:hidden">
       <input type="text" name="hiddenText" value=""/><%--submit방지용 --%>
      </div>
      </form>
    </td>
  </tr>
</table>
         
<script type="text/javascript">
<!--
<%
String isSuccess = request.getParameter("isSuccess");
if("Y".equals(isSuccess)) {
%>
//opener.reviseSuccess();
//opener.parent.document.location.reload();
opener.goTaskPage();
this.close();
<%
}
%>
//-->
</script>
