<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  try{
  String oid =  request.getParameter("oid");
  String oldOid =  request.getParameter("compareTaskOid");

  E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
  String pjtNo = task.getProject().getPjtNo();
  //out.println(project.getPjtType());

  // 제품측정현황 정보
  String checkDescPoint = task.getCheckDescPoint();
  String nonPassPoint = task.getNonPassPoint();
  String checkResult = task.getCheckResult();
  String checkEtc = task.getCheckEtc();
  
  String checkDescPoint_i = task.getCheckDescPoint_i();
  String nonPassPoint_i = task.getNonPassPoint_i();
  String checkResult_i = task.getCheckResult_i();
  
  boolean isKqisTask_ = "과거차문제점검토서".equals(task.getTaskName()) || "과거차문제점반영".equals(task.getTaskName());

  if(checkDescPoint == null){
    checkDescPoint = "";
  }

  if(nonPassPoint == null){
    nonPassPoint = "";
  }

  if(checkResult == null){
    checkResult = "";
  }
  
  if(checkDescPoint_i == null){
      checkDescPoint_i = "";
  }

  if(nonPassPoint_i == null){
      nonPassPoint_i = "";
  }

  if(checkResult_i == null){
      checkResult_i = "";
  }

  if(checkEtc == null){
    checkEtc = "";
  }

  boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
  boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin

  // 진행현황 % 입력
  TaskViewButtonAuth buttonAuth = new TaskViewButtonAuth(task);

  
  //이슈 삭제
  String deleteIssue = request.getParameter("deleteIssue");
 if ( deleteIssue != null ) {
   ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
 }

  //팝업 여부
  String popup = request.getParameter("popup");

  /*
    ####################  권한 설정  ####################
  */
  boolean isProtoGate1Check=false;//TaskViewCommon.jsp에서 변수로 사용(Proto Gate1 체크용)
  boolean isProtoGate1Pass=false;//TaskViewCommon.jsp에서 변수로 사용(Proto Gate1 체크용)
  
  String DR_PASS_CHECK = "";
  int drPoint = 0;
  int drTarget = 0;
  int drvalueCondition = 0;
  String drApprovalResult = "";
  String lastComment = "";
  e3ps.dms.entity.KETProjectDocument drCheckDocument = null;
  if("DR".equals(task.getTaskType())){
      drCheckDocument = ProjectOutputHelper.manager.getDRTaskOutput(task);
      
      if(drCheckDocument != null){
        E3PSTaskData taskData = new E3PSTaskData(task);
        drPoint = drCheckDocument.getDRCheckPoint();
        drTarget = (taskData!=null && !StringUtil.isEmpty(taskData.drvalue))?Integer.parseInt(taskData.drvalue):0;
        drvalueCondition = (taskData!=null && !StringUtil.isEmpty(taskData.drvalueCondition))?Integer.parseInt(taskData.drvalueCondition):0;

        if(drCheckDocument != null) {

//           if(drDocument.getApprovalResult() != null) drApprovalResult = drDocument.getApprovalResult();
//           if("승인".equals(drApprovalResult)) drApprovalResult = "GOOD";
//           else if("반려".equals(drApprovalResult)) drApprovalResult = "<span class='red'>NG</span>";
            if(drPoint>0 && drPoint>=drTarget) {
               drApprovalResult = "GOOD";
            }else if(drvalueCondition>0 && drPoint>=drvalueCondition) {
               drApprovalResult = "조건부승인";
            }else if(drPoint<drTarget) {
               drApprovalResult = "<span class='red'>NG</span>";
               DR_PASS_CHECK = "NG";
            }
            
            if(drCheckDocument.getLastApprovalComment() != null){
        	   lastComment = drCheckDocument.getLastApprovalComment();
            }
             
        }
     }
  }
%>
<html>
<head>
<title></title>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/common/processing.html"%>
<script type="text/javascript" src="/plm/extcore/js/project/trycondition/tryCondition.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	var isKqisTask_ = document.querySelector('#isKqisTask_').value;
	
	if(isKqisTask_){
		setKqisList();
	}
});

function setKqisList(){
    
    var param = new Object();
    param.pjtNo = "<%=pjtNo%>";
    
    ajaxCallServer("/plm/ext/project/task/getKQISFastListQuery", param, function(data){
        
        var kqisList = data.kqisList;
        
        for(var i = 0; i < kqisList.length; i++){
            addKqisList(nullConvert(kqisList[i]));
        }
    });
}

var kqis_Flag = 0;

function kqisLinkPop(url){
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;
    var rest = "width=1050,height="+ (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='+ toppos;
    var pop = '';
    
    if(kqis_Flag == 0){
        pop = window.open(url, '', (opts + rest));
        setTimeout(function(){
            window.open(url, '', (opts + rest));
            pop.close();
        }, 1000);
        //window.open('/plm/ext/project/purchase/kqisDocForm.do?url='+encodeURIComponent(url),'', (opts + rest));   
    }else{
        
        window.open(url, '', (opts + rest));
    }
    
    kqis_Flag++;
}

function nullConvert(data){
    
    
    for (let key in data) {
        var value = data[key] ;
        
        if(value == null || value == 'null'){
            data[key] = "";
        }
        
    }
    return data;
}

function addKqisList(data){
    
    var row = "<tr>";
    var fontColor = "";
    
    if(data.DELAY_STATUS == '지연'){
    	fontColor = "#FF0000";	
    }
    
    row += "<td class=tdwhiteL style='text-align: center; color : " +fontColor + "'>" + data.DELAY_STATUS + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + data.WRT_DAY + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + data.WRT_USER + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'><a href=javascript:kqisLinkPop('"+data.URL +"');>" + data.PART_NO + "</td>";
    row += "<td class=tdwhiteL>" + data.PART_NM + "</td>";
    row += "<td class=tdwhiteL style='text-align: left'>" + data.CAR_TYPE + "</td>";
    row += "<td class=tdwhiteL style='text-align: right'>" + data.REP_REQ_DAY + "</td>";
    row += "<td class=tdwhiteL style='text-align: right'>" + data.REF_REQ_CNT + "</td>";
    row += "<td class=tdwhiteL style='text-align: right'>" + data.REF_REP_CNT + "</td>";
    row += "<td class=tdwhiteL style='text-align: right'>" + data.VAL_CNT + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + data.DOC_STEP_NAME + "</td>";
    row += "<td class=tdwhiteL style='text-align: left'>" + data.NO_REPLY_PERSON + "</td>";

    row += "</tr>";
    
    $("#kqisListPast").append(row);
}

function deleteIssue(v) {
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
      document.forms[0].action = "/plm/jsp/project/TaskView.jsp?deleteIssue="+v;
      document.forms[0].method = "post";
      document.forms[0].submit();
    }
  }

</script>
</head>
<body style="padding-right:15px;padding-left:15px;">
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form name="TaskViewForm">
<!-- Hidden Value -->
<input type=hidden name='taskOid' value='<%=oid%>'>
<input type=hidden name='oid' value='<%=oid%>'>
<input type=hidden name='cmd' >
<input type=hidden name='issueOid'>
<input type=hidden name='popup' value='<%=popup%>'>
<input type=hidden name='ExeDate'>
<input type=hidden name='isKqisTask_' id='isKqisTask_' value='<%=isKqisTask_%>'>
<!-- //Hidden Value -->

<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top">
    <!-------------------------------------- 상단버튼 시작 //-------------------------------------->
       <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
          <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02927") %><%--태스크 정보--%></td>
                      <td align="right"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
          <!-- -------------------- 공통 파일 START------------------- -->
          <%@include file="/jsp/project/TaskViewCommon.jsp"%>
          <!-- -------------------- 공통 파일 END------------------- -->
          
      
      <%if("DR".equals(task.getTaskType())){ %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00154") %><%--DR 관리--%></td>
            </tr>
        </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <%
/*       e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(task);
      int drPoint = 0;
      int drTarget = 0;
      int drvalueCondition = 0;
      String drApprovalResult = "";
      String lastComment = "";
      if(drDocument != null){
	    E3PSTaskData taskData = new E3PSTaskData(task);
        drPoint = drDocument.getDRCheckPoint();
        drTarget = (taskData!=null && !StringUtil.isEmpty(taskData.drvalue))?Integer.parseInt(taskData.drvalue):0;
        drvalueCondition = (taskData!=null && !StringUtil.isEmpty(taskData.drvalueCondition))?Integer.parseInt(taskData.drvalueCondition):0;

        if(drDocument != null) {

//           if(drDocument.getApprovalResult() != null) drApprovalResult = drDocument.getApprovalResult();
//           if("승인".equals(drApprovalResult)) drApprovalResult = "GOOD";
//           else if("반려".equals(drApprovalResult)) drApprovalResult = "<span class='red'>NG</span>";
            if(drPoint>0 && drPoint>=drTarget) {
        	   drApprovalResult = "GOOD";
            }else if(drvalueCondition>0 && drPoint>=drvalueCondition) {
        	   drApprovalResult = "조건부승인";
            }else if(drPoint<drTarget) {
        	   drApprovalResult = "<span class='red'>NG</span>";
        	   DR_PASS_CHECK = "NG";
            }
             
        } */
      String dept = "&nbsp;";

      QueryResult rs = TaskUserHelper.manager.getTaskUser(task, TaskUserHelper.PL);

      while(rs.hasMoreElements()){
        Object o = ((Object[])rs.nextElement())[0];
        TaskMemberLink link = (TaskMemberLink)o;

        WTUser user = link.getMember().getMember();
        PeopleData pData = new PeopleData(user);

        dept = pData.departmentName;
      }


      %>
      <COL width="11%"><COL width="14%"><COL width="11%"><COL width="14%">
      <COL width="11%"><COL width="14%"><COL width="11%"><COL width="14%">
        <tr>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02672") %><%--주관부서--%></td>
          <td class="tdwhiteM"><%=dept %></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%></td>
          <td class="tdwhiteM"><%=(task.getDrValue()==null)?"":task.getDrValue() %></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00157") %><%--DR 점수--%></td>
          <td class="tdwhiteM"><%=drPoint%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02970") %><%--판정결과--%></td>
          <td class="tdwhiteM"><%=drApprovalResult%>&nbsp;</td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01988") %><%--승인 의견--%></td>
          <td class="tdwhiteL0" colspan=7>
            <textarea name="appDesc" style="width:100%" rows="3" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" readOnly><%=lastComment%>&nbsp;</textarea>
          </td>
        </tr>
      </table>
      <%} %>
      <%
      String taskName = task.getTaskName();
      boolean isTask = taskName.startsWith("제품측정");


      if(isTask || "제품 측정".equals(taskName)){ %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02627") %><%--제품측정현황--%></td>
              <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <%
                  String readOnlySelect = "";
                  String disFalse = "";
                  if(buttonAuth.isTaskInfoModify|| CommonUtil.isAdmin()){
                  %>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:savePoint();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  <%
                  }else{
                    readOnlySelect = "readOnly";
                    disFalse = "disabled";
                  }
                  %>
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
            <td width="50" class="tdblueL">구분</td>
            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02897") %><%--측정Point--%></td>
            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01630") %><%--불합격Point--%></td>
            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02898") %><%--측정결과--%></td>
            </tr>
            <tr>
            <td width="50" class="tdwhiteM">전 포인트</td>
            <td width="130" class="tdwhiteL"><input type="text" class="txt_field" name="checkDescPoint" value="<%=checkDescPoint %>" style="width:70%" <%=readOnlySelect%> ></input> Point</td>
            <td width="130" class="tdwhiteL"><input type="text" class="txt_field" name="nonPassPoint" value="<%=nonPassPoint %>" style="width:70%;color=red" <%=readOnlySelect%> ></input> Point</td>
            <td width="130" class="tdwhiteL0">
                <select name="checkResult" class="fm_jmp" style="width:100%">
                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <option value="합격" style="color=blue" <%if("합격".equals(checkResult)){ %>selected<%} %>  <%=disFalse %>><%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%></option>
                <option value="불합격" style="color=red" <%if("불합격".equals(checkResult)){ %>selected<%} %>  <%=disFalse %>><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%></option>
                </select>
            </td>
            </tr>
            <tr>
            <td width="50" class="tdwhiteM">중요 포인트</td>
            <td width="130" class="tdwhiteL"><input type="text" class="txt_field" name="checkDescPoint_i" value="<%=checkDescPoint_i %>" style="width:70%" <%=readOnlySelect%> ></input> Point</td>
            <td width="130" class="tdwhiteL"><input type="text" class="txt_field" name="nonPassPoint_i" value="<%=nonPassPoint_i %>" style="width:70%;color=red" <%=readOnlySelect%> ></input> Point</td>
            <td width="130" class="tdwhiteL0">
                <select name="checkResult_i" class="fm_jmp" style="width:100%">
                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <option value="합격" style="color=blue" <%if("합격".equals(checkResult_i)){ %>selected<%} %>  <%=disFalse %>><%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%></option>
                <option value="불합격" style="color=red" <%if("불합격".equals(checkResult_i)){ %>selected<%} %>  <%=disFalse %>><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%></option>
                </select>
            </td>
            </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
          <td colspan="5" class="tdwhiteL0"><textarea name="checkEtc" id="checkEtc" rows="3" class="fm_area" style="width:100%" <%=readOnlySelect%> ><%=checkEtc %></textarea></td>
        </tr>
          </table>
      <%} %>
      
      <%if(isKqisTask_){ %>
      
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03" width="140">KQIS 과거차반영 현황</td>
                <td valign="bottom"></td>
            </tr>
        </tbody>
      </table>
      
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="kqisTable" style="table-layout:fixed">
                <colgroup>
                    <col width="3%">
                    <col width="4.5%">
                    <col width="3.5%">
                    <col width="5.5%">
                    <col width="10.5%">        
                    <col width="3%">
                    <col width="4.5%">
                    <col width="3%">
                    <col width="3%">
                    <col width="3.5%">
                    <col width="4%">
                    <col width="3%">
                </colgroup>
                <thead>
                <tr>
                    <td class="tdblueM">지연여부</td>
                    <td class="tdblueM">등록일</td>
                    <td class="tdblueM">등록자</td>
                    <td class="tdblueM">품번</td>
                    <td class="tdblueM">품명</td>
                    <td class="tdblueM">차종</td>
                    <td class="tdblueM">회신요구일</td>
                    <td class="tdblueM">반영요청<br>건수</td>
                    <td class="tdblueM">반영회신<br>건수</td>
                    <td class="tdblueM">유효성확인<br>건수</td>
                    <td class="tdblueM">문서단계</td>
                    <td class="tdblueM">미회신<br>담당자</td>
                </tr>
                </thead>
                <tbody id="kqisListPast"></tbody>
       </table>
      <%} %>
      
      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->
      <!-- TASK 상세정보 -->

      <%if(!buttonAuth.isChild){ %>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
          <td class="tdwhiteL">
            <jsp:include page="/jsp/project/TaskDependencyInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oid%>"/>
              <jsp:param name="addTask" value=""/>
              <jsp:param name="deleteTask" value=""/>
              <jsp:param name="popup" value="<%=popup%>"/>
            </jsp:include>
          </td>
        </tr>
      </table>

      <%}%>

      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!------------------------------ 본문 끝 //------------------------------>

      <!-- 개발 프로젝트 -->

          </td>
        </tr>
      </table>

    </td>
  </tr>
<!--   <tr> -->
<%--     <td height="30" valign="bottom"><iframe src="../../portal/common/copyright<%Kogger.debug("popup = " + popup);if(popup != null && !("null".equals(popup))){ %>_t<%} %>.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td> --%>
<!--   </tr> -->
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
<script type="text/javascript">
function viewTask(oid){
	openView(oid);
}
</script>
</html>
<%}catch(Exception e){
}

%>


