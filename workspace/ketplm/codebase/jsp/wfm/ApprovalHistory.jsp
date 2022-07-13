<%@page import="e3ps.project.E3PSTask"%>
<%@page import="ext.ket.project.task.service.ProjectTaskCompHelper"%>
<%@page import="ext.ket.project.gate.entity.GateAssessResult"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.jasypt.commons.CommonUtils"%>
<%@page import="ext.ket.wfm.entity.KETWfmApprovalHistoryDTO"%>
<%@page import="ext.ket.edm.approval.KetEpmApprovalHelper"%>
<%@page import="ext.ket.shared.util.ComparatorUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.wfm.util.WFMProperties"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.common.service.KETCommonHelper"%>
<%@page import="wt.fc.WTObject"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.part.WTPart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.edm.util.EDMProperties"%>
<%@page import="e3ps.edm.util.EDMEnumeratedTypeUtil"%>
<%@page import="e3ps.edm.beans.EDMHelper"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.edm.util.EDMAttributes"%>
<%@page import="ext.ket.shared.log.Kogger"%>
<%@page import="java.util.HashMap"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String pboOid = request.getParameter("pboOid");
    int gateDegree = 0;
    if(StringUtils.isNotEmpty(request.getParameter("gateDegree"))){
	   gateDegree = Integer.parseInt(request.getParameter("gateDegree"));
    }
    
    Object obj = CommonUtil.getObject(pboOid);
    

    Persistable targetObj = null;
    if (obj instanceof EPMDocument) {
	   EPMDocument epm = (EPMDocument) obj;
       try{
            // EPM의 결재정보 가져오는 것은 느리고 복잡해서 별도 HISTORY 테이블의 정보를 가져와서 뿌려준다.
	        targetObj = ext.ket.edm.approval.KetEpmApprovalHelper.service.getSavedPbo( epm ); // getSavedPbo getPbo
       }catch(Exception e){
            Kogger.error("ApprovalHistory.jsp", (String)null, pboOid, "EPM get PBO INFO ERROR", e);
	        targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(pboOid));
       }
    }else{
	   targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(pboOid));
    }
    
    ReferenceFactory rf = new ReferenceFactory();
    KETWfmApprovalMaster master = null;
    Vector vec = null;
    if (targetObj != null)
		master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
    if (master != null) {
	
	    if (obj instanceof GateAssessResult && gateDegree == 0) {
		    GateAssessResult gateAssRslt = (GateAssessResult) obj;
	        E3PSTask gate = ProjectTaskCompHelper.service.getTask(gateAssRslt);
	        gateDegree = ProjectTaskCompHelper.service.getMaxGateDegree(CommonUtil.getOIDString(gate));
	        
	    }
		
		if(gateDegree > 0){
		    vec = WorkflowSearchHelper.manager.getApprovalHistory(master, gateDegree);    
		}else{
		    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
		}
		
        Collections.sort(vec, ComparatorUtil.APPROVALHISTORYSORT);
    }
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<style>
.noTitleStuff .ui-dialog-titlebar {display:none}

#dialog-background {
    display: none;
    position: fixed;
    top: 0; left: 0;
    width: 100%; height: 100%;
    background: rgba(0,0,0,.3);
    z-index: 10;
}

.border-top-red{border-top:2px solid #FF0000 !important;}
.border-left-red{border-left:2px solid #FF0000 !important;}
.border-right-red{border-right:2px solid #FF0000 !important;}
.border-side-red{border-left:2px solid #FF0000 !important;border-right:2px solid #FF0000 !important;}
.border-bottom-red{border-bottom:2px solid #FF0000 !important;}

 </style>

<%@include file="/jsp/common/multicombo.jsp" %>

<script type="text/javascript">
$(document).ready(function(){

    $('.content').click(function(e){
        $('#dialog-background').css("display", "block");
        var messagetoSend = $(this).html();
        
        messagetoSend = messagetoSend.replace(/\n/g, "<br />");
        
        $( "#divValidationResultContent" ).html(messagetoSend);
        

        $( "#divValidationResult" ).dialog(
                { 
                    dialogClass: 'noTitleStuff' , 
                    width: 500, height: 300 , 
                    show: {
                        effect: 'fade',
                        duration: 500
                    },
                    hide: {
                        effect: 'fade',
                        duration: 500
                    }
                }
        );
        
        //e.preventDefault();
        
        
    });
    $("#activityName").multiselect({
        minWidth: 130,
        multiple: false,
        header: false,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
        selectedList: 1
    });
    
    var KetPlmApprovalType = getCookie('KetPlmApprovalType');
    
    if(KetPlmApprovalType == 'app'){
    	$("#activityName").val("app");
        $("#activityName").multiselect('refresh');
        activityChange(KetPlmApprovalType);
    }

})
    
function lfn_hideValidationResult() {
	$('#dialog-background').css("display", "none");
    $( "#divValidationResult" ).dialog("destroy");
}

function activityChange(obj){
	
	var actVal = "";
	
	if ( typeof(obj) == "object" ) {
		actVal = obj.value;
    }
    else {
    	actVal = obj;
    }
	
	if(actVal == 'app'){
		$('#appTable').find('tr[data-id=etc]').hide();	
	}else{
		$('#appTable').find('tr[data-id=etc]').show();
	}
	setCookie('KetPlmApprovalType',actVal,730);
	
}

function setCookie(cname,cvalue,exdays){
	var d = new Date();
	d.setTime(d.getTime() + (exdays*24*60*60*1000))
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + escape(cvalue) + ";" + expires + ";path=/";
}


function getCookie(cname) {

    var name = cname + "=";
    var decodedCookie = document.cookie;
    var ca = decodedCookie.split(";")
    for ( var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return unescape(c.substring(name.length, c.length));
        }
    }
    return "";
}
</script>
</head>
<body class="popup-background popup-space">

<div id="dialog-background"></div>    
<div id="divValidationResult" style="display: none;">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" align="right">

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
          <td class="font_01">결재의견</td>
          <td align="right" width="70">        
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:lfn_hideValidationResult();" class="btn_blue">닫기<%--닫기--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table> 
          </td>  
        </tr>   
      <tr>
        <td colspan="3" style="height: 30px;">&nbsp;</td>
      </tr>                       
      <tr>
        <td id="divValidationResultContent" name="divValidationResultContent" colspan="3" valign="top" align="left">여기에 에러 메세지가 들어간다.</td>
      </tr>                    
      </table>

    </td>
  
  </tr>

</table>
</div>


  <div class="contents-wrap">
    <div class="sub-title b-space5">
      <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "00785")%><%--결재이력--%>
    </div>
    
    <div class="b-space5 float-l">
    <select id="activityName" name="activityName" class="fm_jmp" style="width: 130px;" multiple="multiple"  onchange="javascript:activityChange(this);">
            <option value="all">전체</option>
            <option value="app">요청,검토,승인,합의</option>
            </select>
    </div>
          
    <div class="b-space5 float-r" style="text-align: right">
      <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a href="javascript:self.close();"
            class="btn_blue"
          ><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <div class="float-l" style="width: 100%">
      <table cellpadding="0" class="table-style-01" summary="" id="appTable" name="appTable">
        <colgroup>
          <col width="3%" />
          <col width="4%" />
          <col width="11%" />
          <col width="6%" />
          <col width="6%" />
          <col width="13%" />
          <col width="6%" />
          <col width="51%" />
        </colgroup>
        <thead>
          <tr>
            <td><%=messageService.getString("e3ps.message.ket_message", "01983")%><%--순차--%></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%>
            </td>
            <td><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "02714")%><%--직급--%></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "03364")%><%--결재일시--%></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "03365")%><%--의사결정--%></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "00783")%><%--결재의견--%></td>
          </tr>
        </thead>
        <tbody>
          <%
              String type = "";
              if (vec != null) {
          		//boolean isApprover = true;
          		String refType = WFMProperties.getInstance().getString("wfm.reference");
          		String activityName = "&nbsp;";
          		
          		int gate_seq = vec.size();
          		
                boolean receiver = false;
          		for (int i = 0; i < vec.size(); i++) {
          		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
          		    activityName = StringUtil.checkNull(history.getActivityName());
          		    receiver = KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName) || KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName) || KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(activityName);
          		    if (history.isLast() && activityName.equals("검토")) {
            			activityName = "승인";
            			//isApprover = false;
          		    }
          		    People people = PeopleHelper.manager.getPeople(history.getOwner().getName());
          		    String dept = "&nbsp;";
          		    String completeDate = "&nbsp;";
          		    if (people.getDepartment() == null)
          			   dept = "지정안됨";
          		    else
          			   dept = people.getDepartment().getName();
          		    if (history.getCompletedDate() != null)
          			   completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd HH:mm:ss");
          		    String comments = ParamUtil.checkStrParameter(history.getComments(), "&nbsp;");
          		    String checkComment = history.getComments();
          		    boolean riskCheck = history.isRiskCheck();
          		    if(StringUtils.isNotEmpty(checkComment)){
          			   checkComment =comments.replaceAll("\\p{Z}", "");
          		    }
          		    
          		    String owner = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
          		    if("요청".equals(activityName) || "승인".equals(activityName) || "검토".equals(activityName) || "합의".equals(activityName) ||
          			  "취소 요청".equals(activityName) || "중지 검토".equals(activityName) || "성공 요청".equals(activityName) || "중지 합의".equals(activityName) ||
          			  "취소 검토".equals(activityName) || "중지 요청".equals(activityName)){
          			   type = "app"; 
          		    }else{
          			   type = "etc";
          		    }
          		    if (history.getDelegate() != null)
          			   owner = owner + "(" + history.getDelegate() + ")";
          %>
          <tr data-id="<%=type%>">
            <%if (obj instanceof GateAssessResult) { %>
            <td class="center"><%=gate_seq%></td>
            
            <% 
            gate_seq = gate_seq-1;
            }else{ %>
            <td class="center"><%=history.getSeqNum()%></td>
            <%} %>
            <td class="center"><%=activityName%></td>
            <td class="center"><%=dept%></td>
            <td class="center"><%=ParamUtil.checkStrParameter(people.getDuty(), "&nbsp;")%></td>
            <td class="center"><%=owner%></td>
            <td class="center"><%=completeDate%></td>
            <td class="center <%if(riskCheck) {%> border-top-red border-left-red border-right-red border-bottom-red<%}%>"><%=ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;")%></td>
            
            <%if(StringUtils.isNotEmpty(checkComment) ) {%>
            <td class="left"><div class="content" ><br><%=comments.replaceAll(System.getProperty("line.separator") , "<br>")%></div></br></td>
            <%}else{ %>
            <%-- <td class="center"><textarea class="content" style="height: 5px; width: 92%; <%= receiver?"background-color: #EFEFEF;":""%>" <%= receiver?"disabled=\"disabled\"":"readonly=\"readonly\"" %>><%=comments%></textarea></td> --%>
            <td class="left"><div class="content" ></div></td>
            <%} %>
            
<%--             <%if(StringUtils.isEmpty(checkComment) ) {%>
            <td class="center"><textarea class="content" style="height: 40px; width: 92%; <%= receiver?"background-color: #EFEFEF;":""%>" <%= receiver?"disabled=\"disabled\"":"readonly=\"readonly\"" %>><%=comments%></textarea></td>
            <%}else{ %>
            <td class="center"><textarea class="content" style="width: 92%; <%= receiver?"background-color: #EFEFEF;":""%>" <%= receiver?"disabled=\"disabled\"":"readonly=\"readonly\"" %>><%=comments.replaceAll(System.getProperty("line.separator") , "\n")%></textarea>
            </td>
            <%} %> --%>
            
            <%-- <td class="center"><%=(checkComment=="")?"":comments.replaceAll(System.getProperty("line.separator") , "<br>")%></td> --%>
            
          <%--   <%if(StringUtils.isNotEmpty(checkComment) ) {%>
            <td class="center"><textarea style="overflow-x:hidden; overflow-y:auto; width: 92%; <%= receiver?"background-color: #EFEFEF;":""%>" <%= receiver?"disabled=\"disabled\"":"readonly=\"readonly\"" %>><%=checkComment%></textarea></td>
            <%}else{ %>
            <td class="center"><textarea style="height: 40px; width: 92%; <%= receiver?"background-color: #EFEFEF;":""%>" <%= receiver?"disabled=\"disabled\"":"readonly=\"readonly\"" %>><%=comments%></textarea></td>
            <%} %> --%>
          </tr>
          <%
          		}
              } else {
          %>
          <tr>
            <td colspan="8" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "05126")%><!-- 결재 이력이 없습니다 --></font></td>
          </tr>
          <%
              }
          %>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>