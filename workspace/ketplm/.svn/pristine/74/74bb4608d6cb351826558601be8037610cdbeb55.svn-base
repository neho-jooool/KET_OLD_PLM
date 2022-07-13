<%@page contentType="text/html;charset=UTF-8" buffer="16kb"%>
<%@page import="java.util.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<%
    wt.fc.Persistable per = null;

    String oid = request.getParameter("oid");
    WorkItem workitem = (WorkItem)CommonUtil.getObject(oid);
    per = WorklistHelper.getPBO(workitem);
    TaskDepartmentProjectAssign ta = (TaskDepartmentProjectAssign)per;
    JELProject pjt = (JELProject)ta.getProject();
    JELProjectData pd = new JELProjectData(pjt);

    String taskSize = request.getParameter("taskSize");

    int ts = 0;
    if(taskSize != null){
        ts = Integer.parseInt(taskSize);
    }


    String cmd = request.getParameter("cmd");

    if(cmd == null){
        cmd = "";
    }


    ProjectManager pm = null;
    try{
        pm = pjt.getManager();
    }catch(Exception e){
	Kogger.error(e);
    }




%>
<%@page import="e3ps.project.JELProject"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.workflow.work.WorkItem"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.groupware.workprocess.beans.WorklistHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="e3ps.project.beans.JELTaskData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.beans.TaskUserHelper"%>
<%@page import="e3ps.project.TaskMemberLink"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.beans.JELProjectData"%>
<%@page import="e3ps.groupware.workprocess.beans.WFItemHelper"%>
<%@page import="e3ps.groupware.workprocess.WFItemUserLink"%>
<%@page import="wt.org.WTUser"%>

<%@page import="e3ps.project.PlanTaskWork"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.project.ProjectManager"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.ProjectManagerWTUserLink"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.beans.TemplateProjectData"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="wt.project.Role"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.project.TaskDepartmentProjectAssign"%>
<%@page import="e3ps.project.JELTaskProjectAssignLink"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.beans.TaskPlanComparator"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language="JavaScript" src="/plm/portal/js/checkbox2.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/jquery-1.3.2.min.js"></SCRIPT>
<script language='javascript'>
<!-- //
    //http://daesung.e3ps.com/plm/jsp/project/ProjectViewFrm.jsp?oid=e3ps.project.JELProject:316245&popup=popup
    function projectSchedule(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", 'sapLog',1150,800);
    }
    function reload(){
        document.location.reload();
    }

    function addMasterTask(oid){
        var taskOid = oid;
        //alert(taskOid);
        var url = "/plm/jsp/project/SelectProjectPeopleListWF.jsp?oid=<%=CommonUtil.getOIDString(pjt)%>&mode=s&taskOid="+taskOid+"&workItemOid=<%=oid%>";
        openSameName(url,"850","500","status=no,scrollbars=yes,resizable=no");
    }


    function deleteMaster(deleteValue, taskValue){
        if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03340") %><%--책임자를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
        document.forms[0].action = "/plm/jsp/groupware/workprocess/DefaultProjectLine.jsp?deleteMaster="+deleteValue + "&taskOid=" + taskValue;
        document.forms[0].method = "post";
        document.forms[0].deleteMaster.value = deleteValue;
        document.forms[0].taskOid.value= taskValue;
        document.forms[0].submit();
        }
    }

    function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
    }


    function doSave(){

        document.forms[0].action = "/plm/jsp/groupware/workprocess/DefaultProjectLine.jsp?cmd=saveas";
        document.forms[0].cmd.value = "saveas";
        document.forms[0].submit();
    }


    //********************************************************************
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


    //*********************************************************************
    //날짜 유효성 검증하는 함수
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

    obj.value = yyyy+ "/" +mm+ "/" +dd;
    return true;
    }

    function viewErrMsg(obj,msg) {
        alert(obj.value + " " + msg);
    }

    function setBeforeDate(obj, start, end) {
      var pstart = start;
      var pend = end;
      //alert(pstart+":"+pend);
      var startDate = document.getElementById(pstart).value;
      var startDate_222 = document.getElementById(pend).value;
      var value = parseInt(obj.value) - 1;

      if(startDate != "" && startDate_222 == ""){
          var date = new Date(  startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + value);
          var endDate = document.getElementById(pend);

          var year=String(date.getYear());
          var month=String(date.getMonth()+1);
          var day=String(date.getDate());

          if(month.length==1)month="0"+month;
          if(day.length==1)day="0"+day;
          endDate.value=year+ "/" + month + "/" + day;

      }else if(startDate_222 != "" && startDate == ""){
          var date = new Date(startDate_222.substring(0,4),Number(startDate_222.substring(5,7))-1, Number(startDate_222.substring(8,10))- value);
          var endDate_222 = document.getElementById(pstart);

          var year=String(date.getYear());
          var month=String(date.getMonth()+1);
          var day=String(date.getDate());

          if(month.length==1)month="0"+month;
          if(day.length==1)day="0"+day;
          endDate_222.value=year+ "/" +month+ "/" +day;
      }else if(startDate_222 != "" && startDate != ""){
          var date = new Date(  startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + value);
          var endDate = document.getElementById(pend);

          var year=String(date.getYear());
          var month=String(date.getMonth()+1);
          var day=String(date.getDate());

          if(month.length==1)month="0"+month;
          if(day.length==1)day="0"+day;
          endDate.value=year+ "/" + month + "/" + day;
      }
      else if(startDate_222 == "" && startDate == ""){

          alert("시작일자 및 종료일자 입력이 필요 합니다");
          obj.value = "";
          return;
      }
    }

    function isChangeDuration(start, end, duration) {
        var pstart = start;
          var pend = end;
        var dr = duration;
        //alert(pstart+":"+pend);
        var startDate = document.getElementById(pstart).value;
          var endDate = document.getElementById(pend).value;
        var duration = document.getElementById(dr).value;
        var durationValue;

        if(startDate == "") {
            return;
        }

        if(duration == "") {
            return;
        } else {
           durationValue = parseInt(duration)-1;
        }

        var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + durationValue);
        var year=String(date.getYear());
        var month=String(date.getMonth()+1);
        var day=String(date.getDate());

        if(month.length==1)month="0"+month;
        if(day.length==1)day="0"+day;

        endDate=year+ "/" + month + "/" + day;

        document.getElementById(pend).value = endDate;
        document.getElementById(dr).value = duration;
        //document.forms[0].pend.value = endDate;
        //document.forms[0].dr.value = duration;
    }

    function isChangeDuration2(start, end, duration) {
        var dr = duration;
        var pend = end;
        var pstart = start;
        //alert(pstart+":"+pend);
        var startDate = document.getElementById(pstart).value;
          var endDate = document.getElementById(pend).value;
        var duration = document.getElementById(dr).value;

        var durationValue;

        if(startDate == "") {
            return;
        }

        if(duration == "") {
            return;
        } else {
           durationValue = parseInt(duration)-1;
        }

        var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) - durationValue);
        var year=String(date.getYear());
        var month=String(date.getMonth()+1);
        var day=String(date.getDate());

        if(month.length==1)month="0"+month;
        if(day.length==1)day="0"+day;

        endDate=year+ "/" + month + "/" + day;

        document.getElementById(pstart).value = endDate;
        document.getElementById(dr).value = duration;
        //document.forms[0].pstart.value = endDate;
        //document.forms[0].dr.value = duration;
    }

    function openCal2(param) {
        var str="/plm/jsp/common/calendar.jsp?chageObj=true&form=forms[0]&obj="+param;
        newWin = window.open(str,"cel","scrollbars=no,status=yes,menubar=no,toolbar=no,location=no,directories=no,width=220,height=220,resizable=no,mebar=no,left=250,top=65");
        newWin.focus();

    }
    function SetNum(obj){
        val=obj.value;
        re=/[^0-9]/gi;
        obj.value=val.replace(re,"");
    }

    function viewTemplate(oid){
        var url = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+oid;
        openOtherName(url,"ViewTemplate","800","500","status=1,scrollbars=no,resizable=1");
    }
    function projectDepartSchedule(oid){
        openWindow('/plm/jsp/project/ProjectSchedule.jsp?oid='+oid, 'ProjectSchedule',870,500);
    }
// -->
</script>
<style type="text/css">

.style1 {color: #FF0000}
.stype2 {color: #000000}
.stype3 {color: #daa520}
</style>
<input type="hidden" name=cmd value="<%=cmd%>">

<%if(pm != null){ %>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space20"> </td>
            </tr>
        </table>

                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="titleP">프로그램 정보</td>
                                <td align="right">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>

                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="tab_btm2"></td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="tab_btm1"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%"   >
                            <COL width="12%"><COL width="20%"><COL width="12%"><COL width="20%"><COL width="13%"><COL width="20%">
                            <%

                            String sopStr = (DateUtil.getDateString(pm.getSopDate(), "d")==null)?"":DateUtil.getDateString(pm.getSopDate(), "d");
                            String sopStartStr = (DateUtil.getDateString(pm.getSopStartDate(), "d")==null)?"":DateUtil.getDateString(pm.getSopStartDate(), "d");
                            %>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
                                <td class="tdwhiteL">&nbsp;<%=(pm.getName()==null)?"":pm.getName()%></td>
                                <td class="tdblueL">OEM</td>
                                <td class="tdwhiteL">&nbsp;<%=(pm.getOemType()==null)?"":pm.getOemType().getPath().substring(5)%></td>
                                <td class="tdblueL">OEM 생산처</td>
                                <%
                                    NumberCode oemNc = pm.getOemtypecode();

                                %>

                                <td class="tdwhiteL0" title="<%=(oemNc==null)?"":oemNc.getName()%>">&nbsp;<%=(oemNc==null)?"":oemNc.getName()%></td>              </tr>
                            <tr>

                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
                                <td class="tdwhiteL" >&nbsp;

                                <%try{
                                    if(pm.getModel() != null){ %>
                                <%=StringUtil.checkNull(pm.getModel().getName() )%>
                                <%} %>
                                </td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%></td>
                                <td class="tdwhiteL" >&nbsp;<%=sopStartStr%></td>
                                <td class="tdblueL">SOP</td>
                                <td class="tdwhiteL0">&nbsp;<%=sopStr%></td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01474") %><%--발주처--%></td>
                                <td class="tdwhiteL" >&nbsp;
                                <%if(pm.getCustomer() != null){ %>
                                <%=StringUtil.checkNull(pm.getCustomer().getName() )%>
                                <%}
                                }catch(Exception e) {Kogger.error(e);}
                                %>
                                </td>
                                <td class="tdblueL">PM</td>
                                <td class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(pm.getPm().getFullName()) %></td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02139") %><%--영업담당자--%></td>
                                <td class="tdwhiteL0">&nbsp;<%=StringUtil.checkNull(pm.getSale().getFullName() )%>
                            </tr>
                            <tr>

                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01970") %><%--수주번호--%></td>
                                <td class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(pm.getOrderNo()) %></td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                                <td class="tdwhiteL0" colspan=3 title="<%=pm.getCarInfo() %>">&nbsp;<%=(pm.getCarInfo()==null)?"":pm.getCarInfo()%></td>
                            </tr>
                            <tr>
                                <td class="tdblueL">적용 Template</td>

                                <%
                                    TemplateProject pt = (TemplateProject)CommonUtil.getObject(pm.getTemplateOid());
                                %>
                                <td class="tdwhiteL" colspan=5>&nbsp;<%=StringUtil.checkNull(pt.getPjtName())%>
                                </td>
                            </tr>
                        </table>

                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space20"> </td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="titleP">서브 PM</td>
                                <td align="right">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="tab_btm2"></td>
                            </tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="tab_btm1"></td>
                            </tr>
                        </table>

                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <COL width="12%"><COL width="*">
                            <tr>
                                <!-- Template  -->
                                <td class="tdblueL">서브 PM</td>
                                <td class="tdwhiteL0">
                                    <div style="width=99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <COL width="20%"><COL width="30%"><COL width="30%"><COL width="20%">
                                            <tr>
                                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%></td>
                                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "03381") %><%--등록할 프로젝트 설명--%></td>
                                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "03382") %><%--기한일--%></td>

                                            </tr>
                            <%

                            QuerySpec qs = new QuerySpec();
                            Class pw_class = ProjectManagerWTUserLink.class;
                            int pw_idx = qs.addClassList(pw_class, true);

                            qs.appendWhere(new SearchCondition(pw_class,
                                    "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pm)), pw_idx);

                            QueryResult result = PersistenceHelper.manager.find(qs);

                            //Kogger.debug("sub PM===>"+CommonUtil.getOIDLongValue(pm)+":"+result.size());

                            Object[] obj = null;
                            ProjectManagerWTUserLink pwl = null;

                            while(result.hasMoreElements())
                            {
                                obj = (Object[])result.nextElement();
                                pwl = (ProjectManagerWTUserLink)obj[0];
                                WTUser wtu = pwl.getWtuser();
                                String validity = (DateUtil.getDateString(pwl.getValidityDate(), "d")==null)?"":DateUtil.getDateString(pwl.getValidityDate(), "d");

                            %>
                                            <tr>
                                                <td class="tdwhiteM" >&nbsp;<%=StringUtil.checkNull(pwl.getPwMsg()) %> </td>
                                                <td class="tdwhiteM" >&nbsp;<%=StringUtil.checkNull(pwl.getPwDesc()) %> </td>
                                                <td class="tdwhiteM" >&nbsp;<%=wtu.getFullName() %> </td>
                                                <td class="tdwhiteM" >&nbsp;<%=validity%> </td>
                                            </tr>

                            <%} %>
                                        </table>
                                    </div>
                                </td>
                            </tr>

                        </table>



        <table border="0" cellspacing="0" cellpadding="0" >
            <tr>
                <td class="space20"> </td>
            </tr>
        </table>

        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="titleP">프로그램 일정 계획</td>
                <td align="right">
                    &nbsp;
                </td>
            </tr>
        </table>

        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm1"></td>
            </tr>
        </table>

                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <COL width="7%"><COL width="21%"><COL width="18%"><COL width="18%"><COL width="18%"><COL width="18%">
                            <tr>
                                <td class="tdblueM" rowspan=2>Level</td>
                                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00343") %><%--OEM 개발 단계--%></td>
                                <td class="tdblueM" colspan=2>OEM 계획</td>
                                <td class="tdblueM" colspan=2><%=messageService.getString("e3ps.message.ket_message", "03147") %><%--한국단자 계획--%></td>
                            </tr>
                            <tr>
                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02018") %><%--시작일--%></td>
                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02658") %><%--종료일--%></td>
                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02018") %><%--시작일--%></td>
                                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02658") %><%--종료일--%></td>
                            </tr>
                            <%
                            if(pm.getOemType() != null){
                                OEMProjectType typeObject = (OEMProjectType)pm.getOemType();
                                ArrayList oem_al = new ArrayList();
                                oem_al = OEMTypeHelper.getOEMTypeTree(typeObject);
                                String levelStr = "";
                                boolean addOEMCheck = false;

                                for(int i = 0 ; i < oem_al.size() ; i++){

                                    OEMProjectType oemType = (OEMProjectType)CommonUtil.getObject((String)oem_al.get(i));
                                    if(oemType.getOLevel()==0){
                                        levelStr = "";
                                    }else if(oemType.getOLevel() == 1){
                                        levelStr = "...";
                                        addOEMCheck = false;
                                    }else if(oemType.getOLevel() == 2) {
                                        levelStr = "......";
                                        addOEMCheck = true;
                                    }
                                    //pm
                                    //oemType
                                    OEMPlan op = OEMTypeHelper.getOEMPlan(pm,oemType);
                                    String os="";
                                    String oe="";
                                    String ds="";
                                    String de="";
                                    //Kogger.debug("==>" +op);
                                    if(op != null){
                                        os = (DateUtil.getDateString(op.getOemStartDate(), "d")==null)?"":DateUtil.getDateString(op.getOemStartDate(), "d");
                                        oe = (DateUtil.getDateString(op.getOemEndDate(), "d")==null)?"":DateUtil.getDateString(op.getOemEndDate(), "d");
                                        ds = (DateUtil.getDateString(op.getDsStartDate(), "d")==null)?"":DateUtil.getDateString(op.getDsStartDate(), "d");
                                        de = (DateUtil.getDateString(op.getDsEndDate(), "d")==null)?"":DateUtil.getDateString(op.getDsEndDate(), "d");
                                    }




                            %>

                            <tr>
                                <%if(addOEMCheck){%>
                                <td class="tdwhiteL" >&nbsp;<%=levelStr%><%=oemType.getOLevel() %></td>
                                <td class="tdwhiteM" >&nbsp;<%=oemType.getName() %></td>

                                <input type="hidden" name=oemtypeOid value="<%=CommonUtil.getOIDString(oemType)%>">
                                <td class="tdwhiteR" >&nbsp;<%=os%>
                                </td>
                                <td class="tdwhiteR" >&nbsp;<%=oe%>
                                </td>
                                <td class="tdwhiteR" >&nbsp;<%=ds%>
                                </td>
                                <td class="tdwhiteR" >&nbsp;<%=de%>
                                </td>

                                <%
                                }else if(false){ %>
                                <td class="tdwhiteR" >&nbsp;
                                </td>
                                <td class="tdwhiteR" >&nbsp;
                                </td>
                                <td class="tdwhiteR" >&nbsp;
                                </td>
                                <td class="tdwhiteR" >&nbsp;
                                </td>
                                <%} %>

                            </tr>
                            <%
                                }
                            }
                            %>

                        </table>
<%} %>

<%

    WTUser workUser = PeopleHelper.manager.getChiefUser(ta.getDepart());
    String workUserName = "";
    if(workUser != null){
        workUserName = workUser.getFullName();
    }


%>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space20"> </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="titleP">기능부서 일정 계획 &nbsp;&nbsp;&nbsp;[<%=messageService.getString("e3ps.message.ket_message", "01201") %><%--담당부서--%>: <%=ta.getDepart().getName() %>] [<%=messageService.getString("e3ps.message.ket_message", "01562") %><%--부서장--%> : <%=workUserName%>]</td>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>&nbsp;</td>
                            <td class=fixLeft></td>
                            <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01560") %><%--부서별일정수립현황--%>" onClick="javascript:projectDepartSchedule('<%=CommonUtil.getOIDString(pjt)%>');"></td>
                            <td class=fixRight></td>
                        </tr>
                    </table>


                </td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm1"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%" >
            <col width="5%"><col width="30%"><col width="15%"><col width="15%">
            <col width="10%"><col width="10%"><col width="15%">
            <tr>

                <td class="tdblueM" >Level</td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00343") %><%--OEM 개발 단계--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02922") %><%--태스크 담당자--%></td>

            </tr>
        <%



            QueryResult qrChild = PersistenceHelper.manager.navigate(ta, "task", JELTaskProjectAssignLink.class, true);
            Collections.sort(qrChild.getObjectVectorIfc().getVector(), new TaskPlanComparator());

            Vector vt = new Vector();
            while(qrChild.hasMoreElements()){

                    TemplateTask task2 =(TemplateTask)qrChild.nextElement();
                    vt.add(task2);
                    QueryResult qrChild3 =  ProjectTaskHelper.manager.getChild(task2);
                    Collections.sort(qrChild3.getObjectVectorIfc().getVector(), new TaskPlanComparator());
                    boolean isCheckAddTask = false;
                    while(qrChild3.hasMoreElements()){
                        Object[] taskObj3 = (Object[])qrChild3.nextElement();
                        TemplateTask child3 = (TemplateTask) taskObj3[0];
                        vt.add(child3);
                    }



            }

        %>
            <input type=hidden name=taskSize value="<%=qrChild.size()%>">
        <%
            int planwork = 0;
            //while(qrChild2.hasMoreElements()){
            Timestamp pmPlanEndDateCheckValue = null;
            String submitCheck = "";

            for(int i = 0 ; i < vt.size() ; i++){
                Object taskObj = (Object)vt.get(i);
                TemplateTask child = (TemplateTask) taskObj;
                //JELTaskData dependData = new JELTaskData( (JELTask)child);
                ExtendScheduleData schedule = (ExtendScheduleData)child.getTaskSchedule().getObject();

                boolean isCheckAddTask = ProjectTaskHelper.manager.isChild(child);

                String taskLevel = "";

                String styleValue2="";
                boolean isCheckCompareTask = false;
                if(isCheckAddTask){
                    taskLevel = "2";
                    isCheckCompareTask = true;
                    styleValue2 = "style='background-color:#b0c4da;'";
                }else{
                    taskLevel = "...3";
                    styleValue2 = "style='background-color:#ffefd5;'";
                }



                String taskPlanStartDateStr = "";
                String taskPlanEndDatStr = "";
                String pmPlanStartDateStr = "";
                String pmPlanEndDatStr = "";
                String taskDuraction = String.valueOf(DateUtil.getDuration(schedule.getPlanStartDate(), schedule.getPlanEndDate()) + 1);

                OEMProjectType et = null;
                try{
                    et = child.getOemType();

                }catch(Exception e){
                    Kogger.error(e);
                    et = null;
                    }


                String oemLevelValue = "";
                if(et != null){
                    oemLevelValue = et.getName();
                }



                if(schedule.getPlanStartDate() != null){
                    taskPlanStartDateStr = DateUtil.getDateString(schedule.getPlanStartDate(), "D");
                }
                if(schedule.getPlanEndDate() != null){
                    taskPlanEndDatStr = DateUtil.getDateString(schedule.getPlanEndDate(), "D");
                }
                if(schedule.getPmStartDate() != null){
                    pmPlanStartDateStr = DateUtil.getDateString(schedule.getPmStartDate(), "D");
                }
                if(schedule.getPmEndDate() != null){
                    pmPlanEndDatStr = DateUtil.getDateString(schedule.getPmEndDate(), "D");
                }

                if(isCheckCompareTask && (schedule.getPmEndDate() != null)){
                    pmPlanEndDateCheckValue = schedule.getPmEndDate();

                }

                String taskUser = "";
                boolean ischeckDept = false;


                if(child.getDepartment() != null){
                    WTUser wtuser = PeopleHelper.manager.getChiefUser(child.getDepartment());
                    if(wtuser != null && workUser != null){
                        taskUser = wtuser.getFullName();
                        if(workUser.getName().equals(wtuser.getName())){
                            ischeckDept = true;
                        }
                    }

                }

                QueryResult masterList = TaskUserHelper.manager.getMaster(child);

                PeopleData data = null;
                while ( masterList.hasMoreElements() ) {
                    Object o[]=(Object[])masterList.nextElement();
                    TaskMemberLink link = (TaskMemberLink)o[0];
                    data = new PeopleData(link.getMember().getMember());

                }
                String userOid = "";
                if(data != null){
                    userOid = data.wtuserOID;
                }

                String stylefont="style2";
                if(!isCheckCompareTask && (schedule.getPlanEndDate() != null) && (pmPlanEndDateCheckValue != null)){

                    if(schedule.getPlanEndDate().after(pmPlanEndDateCheckValue)){
                    stylefont = "style1";
                    submitCheck = "true";
                    }
                }


            if(ischeckDept || isCheckAddTask){

        %>

            <tr>
                <td  class="tdwhiteL" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"> <%=taskLevel%></span></td>
                <td  class="tdwhiteL" <%=styleValue2%> title="<%=child.getTaskName()%>" >&nbsp;<span class="<%=stylefont%>"><%=child.getTaskName()%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=pmPlanStartDateStr%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=pmPlanEndDatStr%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=taskDuraction%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=oemLevelValue%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;
                <%
                if(isCheckAddTask){

                }else{
                    if(data == null){
                        out.print(" ");
                    }else{
                    out.print(StringUtil.checkNull(data.name));
                    %>

                    <%}%>
                <%} %>
                </td>

            </tr>


        <%

            planwork++;
            }
            }
        %>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" >
            <tr>
                <td class="space20"> </td>
            </tr>
        </table>

        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "03090") %><%--프로젝트 정보--%></td>
                <td align="right">
                    &nbsp;
                </td>
            </tr>
        </table>

        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space10"> </td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm1"></td>
            </tr>
        </table>
        <%
        JELProjectData projectData = new JELProjectData(pjt);

        %>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="15%"><COL width="20%"><COL width="13%"><COL width="20%"><COL width="12%"><COL width="20%">
            <tr>
                <td class="tdblueM0" colspan=6>
                    <a href="#" onClick="Javascript:projectSchedule('<%=CommonUtil.getOIDString(pjt)%>')">
                    <img src='/plm/portal/icon/project.gif'>&nbsp;
                    <B><SPAN style="FONT-SIZE: 11pt"><span class="stype3">[<%=projectData.pjtNo%>]</span></SPAN></B></a>

                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                <td class="tdwhiteL">
                    &nbsp;<%=projectData.ViewpjtNo%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02860") %><%--최초등록일자--%></td>
                <td class="tdwhiteL">
                    &nbsp;<%=DateUtil.getDateString(projectData.pjtCreateDate,"D")%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02846") %><%--최종 수정일자--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=DateUtil.getDateString(projectData.pjtModifyDate,"D")%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                <td class="tdwhiteL" colspan=3>
                    <a href="javascript:parent.viewContentPage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=projectData.jelPjtOID%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=projectData.jelPjtOID%>');">
                    &nbsp;<span class="stype3"><%=projectData.pjtName%></span>
                    </a>
                </td>
                <!--td class="tdblueL">프로젝트 종류</td>
                <td class="tdwhiteL0">
                    &nbsp;<%//=projectData.pjtTypeName%>
                </td-->
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03074") %><%--프로젝트 산출물 인증방식--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=projectData.pjtOutputType%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02828") %><%--총기간--%></td>
                <td class="tdwhiteL">
                    &nbsp;<%=projectData.pjtDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
                <td class="tdwhiteL">
                    &nbsp;<%=DateUtil.getDateString(projectData.pjtPlanStartDate,"D")%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=DateUtil.getDateString(projectData.pjtPlanEndDate,"D")%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
                <td class="tdwhiteL">
                    &nbsp;<%=pjt.getDivision()==null?"":StringUtil.checkNull(pjt.getDivision().getName())%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01162") %><%--난이도--%>
</td>
                <td class="tdwhiteL">
                    &nbsp;<%=pjt.getLevel()==null?"":StringUtil.checkNull(pjt.getLevel().getName())%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=pjt.getProduct()==null?"":StringUtil.checkNull(pjt.getProduct().getName())%>
                </td>
            </tr>

            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01474") %><%--발주처--%></td>
                <td class="tdwhiteL">
                    &nbsp;<%=pjt.getCustomer()==null?"":StringUtil.checkNull(pjt.getCustomer().getName())%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%></td>
                <td class="tdwhiteL">
                    &nbsp;<%=pjt.getDevcompany()==null?"":StringUtil.checkNull(pjt.getDevcompany().getName())%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01788") %><%--생산조직--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=pjt.getMakecompany()==null?"":StringUtil.checkNull(pjt.getMakecompany().getName())%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
                <td class="tdwhiteL" colspan=3>
                    &nbsp;<%=pjt.getModel()==null?"":StringUtil.checkNull(pjt.getModel().getName())%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td class="tdwhiteL0" >
                    &nbsp;<%=pjt.getLifeCycleState().getDisplay(messageService.getLocale())%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                <td class="tdwhiteL0" colspan=5>
                    <textarea rows="5" style="width:100%" class="fm_area" readonly><%=(projectData.pjtDesc).length()>0 ? projectData.pjtDesc:""%></textarea>
                </td>
            </tr>
            <%
            TemplateProject tp = null;
            TemplateProjectData tpdata = null;
            if(pjt.getTemplateCode() != null) {
                tp = ProjectHelper.getTemplate(pjt.getTemplateCode());
                tpdata = new TemplateProjectData(tp);
            }

            %>
            <% if(tpdata != null) { %>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00516") %><%--Template 정보--%></td>
                <td class="tdwhiteL0" colspan=5>
                    <!-- Open Window -->
                    <a href="#" onClick="javascript:viewTemplate('<%=tpdata.tempProjectOID %>');">
                    <%=tpdata.tempName%> </a>


                    <!--  페이지 이동
                    <a href="javascript:parent.movePaage('/plm/jsp/project/template/TemplateProjectViewLeftFrm.jsp?oid=<%=tpdata.tempProjectOID%>', '/plm/jsp/project/template/TemplateProjectView.jsp?oid=<%=tpdata.tempProjectOID%>');">
                    <%=tpdata.tempName%>
                      -->
                </td>
            </tr>
            <% } %>
            <%
            PeopleData pData = new PeopleData(projectData.pjtPm);
            %>
            <tr>
                <td class="tdblueL">PM</td>
                <td class="tdwhiteL0" colspan=5>&nbsp;<%=pData.name%> </td>
            </tr>

        </table>



        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space10"> </td>
            </tr>
        </table>



