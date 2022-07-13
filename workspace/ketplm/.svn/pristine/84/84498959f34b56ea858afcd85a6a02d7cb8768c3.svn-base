<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,
                 e3ps.common.util.*,
                 e3ps.project.beans.*,
                 e3ps.groupware.company.*,
                 java.util.*"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.lifecycle.*"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.equipment.UnitErrorProcess"%>
<%@page import="e3ps.equipment.beans.UnitErrorData"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="e3ps.equipment.ErrorProcessTaskLink"%>
<%@page import="e3ps.project.issue.IssueEcrLink"%>
<%@page import="e3ps.equipment.UnitErrorAssign"%>
<%@page import="e3ps.equipment.beans.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<html>
<%
    String issueType = request.getParameter("Issue");
    String oid = request.getParameter("oid");

    String sessionidstring = request.getParameter("sessionid");
    String pagestring = request.getParameter("tpage");

    String pjtState = request.getParameter("pjtState");
    String disposerchecked = request.getParameter("disposerchecked");
    String requestchecked = request.getParameter("requestchecked");
    String projectOid = request.getParameter("projectOid");
    String errorName = request.getParameter("errorName");
    String pstate = request.getParameter("pstate");
    String createECR = request.getParameter("createECR");
    String projectNo = request.getParameter("projectNo");

    if(projectNo == null || projectNo.length() == 0){
        projectNo = "";
    }

    if(createECR == null || createECR.length() == 0){
        createECR = "";
    }

    if(pstate == null){
        pstate = "1";
    }

    String isCheckedDisposer = "";
    String isCheckedRequest = "";

    if(errorName == null){
        errorName = "";
    }

    if(issueType == null){
        issueType = "";
    }

    if(pjtState == null){
        pjtState = "";
    }

    if(disposerchecked == null){
        disposerchecked = "";
    }

    if(requestchecked == null){
        requestchecked = "";
    }

    if(projectOid == null || projectOid.equals("undefined")){
        projectOid = "";
    }

    if(disposerchecked.equals("true")){
        isCheckedDisposer = "checked";
    }

    if(requestchecked.equals("true")){
        isCheckedRequest = "checked";
    }

    if(sessionidstring == null)
        sessionidstring = "0";

    if(pagestring == null)
        pagestring = "";


    int psize = 15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;


    long sessionid = Long.parseLong(sessionidstring);

    WTUser wtuser = null;

    if(pagestring.length()>0)
        cpage = Integer.parseInt(pagestring);

    PagingQueryResult qr = null;


    QuerySpec spec = null;



    E3PSProject project = null;

    if(oid != null)
    {
    project = (E3PSProject)CommonUtil.getObject(oid);
    }

    Vector vector = null;

    if(issueType.equals("Issue")) {
         vector = ProjectHelper.getIssuesWithPath(project);
    }
    else {

        if(sessionid <= 0){
            wtuser = (WTUser)SessionHelper.manager.getPrincipal();

            spec = new QuerySpec();
            Class issueClass = UnitErrorProcess.class;
            int issueClassPos = spec.addClassList(issueClass, true);
            int task_indx = spec.appendClassList(E3PSTask.class, false);
            int project_index = spec.appendClassList(E3PSProject.class, false);
            int link_index = spec.appendClassList(ErrorProcessTaskLink.class, false);


            ClassAttribute c0 = new ClassAttribute(E3PSTask.class, "projectReference.key.id");
            ClassAttribute c1 = new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id");

            SearchCondition psc = new SearchCondition(c0, "=", c1);
            psc.setFromIndicies(new int[] {task_indx, project_index}, 0);
            psc.setOuterJoin(0);
            spec.appendWhere(psc, new int[] {task_indx, project_index});


            if(spec.getConditionCount() > 0) {
                spec.appendAnd();
            }
            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new int[]{project_index});

            if(spec.getConditionCount() > 0) {
                spec.appendAnd();
            }
            spec.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {project_index});

            if(!projectNo.equals("")) {
                if(spec.getConditionCount() > 0) {
                    spec.appendAnd();
                }
                spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.LIKE, "%"+projectNo.toUpperCase()+"%"), new int[]{project_index});
                //setUpperNLikeCondition(spec, E3PSProject.class, project_index, E3PSProject.PJT_NO, projectNo );
            }

            spec.appendAnd();


            ClassAttribute ca0 = new ClassAttribute(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id");
            ClassAttribute ca1 = new ClassAttribute(ErrorProcessTaskLink.class, "roleAObjectRef.key.id");

            SearchCondition sc = new SearchCondition(ca0, "=", ca1);
            sc.setFromIndicies(new int[]{task_indx, link_index}, 0);
            sc.setOuterJoin(0);
            spec.appendWhere(sc, new int[]{task_indx, link_index});

            spec.appendAnd();

            ClassAttribute ca00 = new ClassAttribute(UnitErrorProcess.class, "thePersistInfo.theObjectIdentifier.id");
            ClassAttribute ca11 = new ClassAttribute(ErrorProcessTaskLink.class, "roleBObjectRef.key.id");

            SearchCondition sc2 = new SearchCondition(ca00, "=", ca11);
            sc2.setFromIndicies(new int[]{issueClassPos, link_index}, 0);
            sc2.setOuterJoin(0);
            spec.appendWhere(sc2, new int[]{issueClassPos, link_index});



            if(!projectOid.equals("")){
                if(spec.getConditionCount() > 0){
                    spec.appendAnd();
                }
                spec.appendWhere(new SearchCondition(E3PSTask.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(projectOid)), new int[]{task_indx});

            }


            // 이슈 상태
            if(spec.getConditionCount() > 0) {
                spec.appendAnd();
            }
            spec.appendOpenParen();
            spec.appendWhere(new SearchCondition(issueClass, "state.state", SearchCondition.EQUAL, "RESULTCOMFIRM"),new int[] {issueClassPos});
            spec.appendOr();
            spec.appendWhere(new SearchCondition(issueClass, "state.state", SearchCondition.EQUAL, "ERRORINWORK"),new int[] {issueClassPos});
            spec.appendOr();
            spec.appendWhere(new SearchCondition(issueClass, "state.state", SearchCondition.EQUAL, "ERRORCOMPLETED"),new int[] {issueClassPos});
            spec.appendCloseParen();



            //disposer
            long oidValue = CommonUtil.getOIDLongValue(wtuser);

               //if(disposerchecked.equals("true") || requestchecked.equals("true") ){
                if(spec.getConditionCount() > 0){
                    spec.appendAnd();
                }

                 //spec.appendOpenParen();

                //if(disposerchecked.equals("true")){
                    //spec.appendWhere(new SearchCondition(issueClass, "processAssign.key.id", SearchCondition.EQUAL, oidValue),new int[] {issueClassPos});
                  //}

                if(true){//requestchecked.equals("true")){
                    //if(disposerchecked.equals("true")){
                          //spec.appendOr();
                    //}
                       spec.appendWhere(new SearchCondition(issueClass, "processCreator.key.id", SearchCondition.EQUAL, oidValue), new int[] {issueClassPos});
                   }

                //spec.appendCloseParen();
               //}

            if(!errorName.equals("")){
                if(spec.getConditionCount() > 0){
                    spec.appendAnd();
                }
                spec.appendWhere(new SearchCondition(issueClass, UnitErrorProcess.ERROR_NAME, SearchCondition.LIKE, "%"+errorName+"%" ), new int[] {issueClassPos});
            }

            if(!createECR.equals("")){
                if(spec.getConditionCount() > 0){
                    spec.appendAnd();
                }

                if(createECR.equals("true")){
                    spec.appendWhere(new SearchCondition(issueClass,UnitErrorProcess.IS_CREATE_ECR,SearchCondition.IS_TRUE),new int[] {issueClassPos});
                }else{
                    spec.appendOpenParen();
                    spec.appendWhere(new SearchCondition(issueClass,UnitErrorProcess.IS_CREATE_ECR,SearchCondition.IS_FALSE),new int[] {issueClassPos});
                    spec.appendOr();
                    spec.appendWhere(new SearchCondition(issueClass,UnitErrorProcess.IS_CREATE_ECR,SearchCondition.IS_NULL),new int[] {issueClassPos});
                    spec.appendCloseParen();
                }
            }


            Timestamp tstamp = null;
            if(pjtState.equals("1")){

            }else if(pjtState.equals("2")){

                if(spec.getConditionCount() > 0){
                      spec.appendAnd();
                }
                spec.appendWhere(new SearchCondition(issueClass, UnitErrorProcess.LAST_IN_DATE, true), new int[] {issueClassPos});

            }else if(pjtState.equals("3")){
                if(spec.getConditionCount() > 0){
                      spec.appendAnd();
                }
                spec.appendWhere(new SearchCondition(issueClass, UnitErrorProcess.LAST_IN_DATE, false), new int[] {issueClassPos});
            }

            //out.println("Issue List=> "+spec);
             ClassAttribute sortCa = null;
            OrderBy orderby = null;
            int sortIdx = 0;
            sortCa = new ClassAttribute(UnitErrorProcess.class, "thePersistInfo.modifyStamp");
            sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
            spec.appendSelect(sortCa, new int[]{issueClassPos}, true);
            orderby = new OrderBy(sortCa, true, null);
            spec.appendOrderBy(orderby, new int[]{issueClassPos});

              qr = PagingSessionHelper.openPagingSession(0, psize, spec);

        }
        else {
            qr = PagingSessionHelper.fetchPagingSession((cpage-1) * psize, psize, sessionid);
        }


        if(qr != null) {
            total = qr.getTotalSize();
            sessionid = qr.getSessionId();
        }

    }
%>


<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "00271") %><%--Issue 목록--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language="javascript" src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>


<script language="JavaScript">
<!--
    function viewError(oid) {
        openWindow('/plm/jsp/equipment/equipmentView.jsp?oid='+oid,'Revise', 870, 600);
        //openView(oid, 870, null, null);
    }
    function viewIssue(oid) {
        var url = "/plm/jsp/project/IssueView.jsp?oid="+oid;
        openSameName(url,"870","500","status=no,scrollbars=no,resizable=yes");
    }

    function viewTask(oid) {
        var url = "/plm/jsp/project/TaskView.jsp?oid="+oid;
        openSameName(url,"900","700","status=no,scrollbars=yes,resizable=yes");
    }

    function gotoPage(p){
        document.forms[0].sessionid.value='<%=sessionid%>';
        document.forms[0].tpage.value=p;
        document.forms[0].submit();
    }

    function IssueAdd(issueOid){
    var url="/plm/jsp/equipment/UnitErrorAssignWorkComfirm.jsp?oid=" + issueOid;
    openOtherName(url,"IssueCreate","900","700","status=no,scrollbars=yes,resizable=yes");
    }

    function search() {
    if(document.forms[0].sessionid) document.forms[0].sessionid.value = "0";
    if(document.forms[0].page) document.forms[0].page.value = "";

    //if(document.forms[0].disposerIssue.checked == false && document.forms[0].requestIssue.checked == false){
        //alert("검색 조건의 이슈 체크 박스를 선택 해주세요 ");
        //return;
    //}


    //if(document.forms[0].disposerIssue.checked == true){
        //document.forms[0].disposerchecked.value = 'true';
    //}else{
        //document.forms[0].disposerchecked.value = "";
    //}

    //if(document.forms[0].requestIssue.checked == true){
        //document.forms[0].requestchecked.value = 'true';
    //}else{
        //document.forms[0].requestchecked.value = "";
    //}
    //if(document.forms[0].projectName.value != ""){
    //  document.forms[0].projectOid.value = document.forms[0].projectName.value;
    //}
    document.forms[0].pstate.value = document.forms[0].pjtState.value;
    document.forms[0].errorName.value = document.forms[0].errorName.value;
    document.forms[0].projectNo.value = document.forms[0].projectNo.value;

    //document.forms[0].target = "_self";
    //document.forms[0].action = "/plm/jsp/project/IssueList.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit();
}

-->
</script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
.style2 {color: #ffff00}
.style3 {color: #008000}
-->
</style>

<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>

</head>
<body>
<form>
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<input type='hidden' name='disposerchecked'>
<input type='hidden' name='requestchecked'>
<input type='hidden' name ='projectOid'>
<input type='hidden' name ='pstate'>


<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01">
                <%
                String issueTitle = "";
                if(!issueType.equals("Issue")) {
                issueTitle = messageService.getString("e3ps.message.ket_message", "00336")/*MY ISSUE 목록*/;
                }else{
                issueTitle = messageService.getString("e3ps.message.ket_message", "03050")/*프로젝트 ISSUE 목록*/;
                } %>
                <%=issueTitle%>
                </td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=issueTitle%></td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <%
                String pstateChecked = "checked";
                if(!issueType.equals("Issue")){
            %>
            <table  border="0" cellspacing="0" cellpadding="0" width="100%" >
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>&nbsp;</td>
                                <td width="">&nbsp;&nbsp;<span class="small"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%> :</span>&nbsp;</td>
                                <td width="" ><input name="errorName" value ="<%=errorName%>" size=15 class="txt_field" /></td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td width="">&nbsp;&nbsp;<span class="small"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%> :</span>&nbsp;</td>
                                <td width="" ><input name="projectNo" value ="<%=projectNo%>" size=15 class="txt_field" /></td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <!--  td><input type="checkbox" name ="disposerIssue" <%=isCheckedDisposer%> >&nbsp;<span class="small">처리 해야 할 이슈</span>&nbsp; </td>
                                <td><input type="checkbox" name ="requestIssue" <%=isCheckedRequest%>>&nbsp;&nbsp;<span class="small">요청한  이슈</span> &nbsp;</td>
                                <td>&nbsp;</td -->
                                <td width="" >&nbsp;&nbsp;<span class="small"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%> : </span>&nbsp;</td>
                                <td width="" >
                                    <select name="pjtState" onChange="javascript:search();">
                                        <option value="1" <%=(pjtState.equals("1"))?"selected" :""%>><%=messageService.getString("e3ps.message.ket_message", "01374") %><%--모두--%></option>
                                        <option value="2" <%=(pjtState.equals("2"))?"selected" :""%>><%=messageService.getString("e3ps.message.ket_message", "02791") %><%--처리중--%></option>
                                        <option value="3" <%=(pjtState.equals("3"))?"selected" :""%>><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></option>
                                    </select>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td width="" ><span class="small"><%=messageService.getString("e3ps.message.ket_message", "00211") %><%--ECR등록--%> :</span>&nbsp;</td>
                                <td width="" >
                                    <select name="createECR" onChange="javascript:search();">
                                            <option value="">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01374") %><%--모두--%></option>
                                            <option value="true" <%=(createECR.trim().equals("true"))?"selected":"" %>>&nbsp;Y</option>
                                            <option value="false" <%=(createECR.trim().equals("false"))?"selected":""%>>&nbsp;N</option>
                                    </select>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td>
                                    <a href="javascript:search();">
                                    <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                                    </a>
                                </td>

                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <%  } %>
            <%  if(issueType.equals("Issue")){ %>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <col width='10%'><col width='15%'>
                <col width='10%'><col width='15%'>
                <col width='10%'><col width='15%'>
                <col width='10%'><col width='15%'>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></td>
                <td class="tdwhiteL">
                    <%
                        int total_count = UnitErrorStatusHelper.getUnitErrorCount(project, true, false, false);
                    %>
                    <%=total_count%>
                </td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
                <td class="tdwhiteL">
                    <%
                        int completed_count = UnitErrorStatusHelper.getUnitErrorCount(project, false, true, false);
                    %>
                    <%=completed_count%>
                </td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
                <td class="tdwhiteL">
                    <%
                        int progress_count = UnitErrorStatusHelper.getUnitErrorCount(project, false, false, false);
                    %>
                    <%=progress_count%>
                </td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
                <td class="tdwhiteL0">
                    <%
                        int delayed_count = UnitErrorStatusHelper.getUnitErrorCount(project, false, false, true);
                    %>
                    <b><font color='brown'><%=delayed_count%></font><b>
                </td>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"> </td>
                </tr>
            </table>
            <%  } %>

        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="tab_btm2"> </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="tab_btm1"> </td>
            </tr>
        </table>
        <table height="24" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <%if(!issueType.equals("Issue")){ %>
                <td class="tdblueM"> <%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%> </td>
                <%}%>
                <td class="tdblueM" > TASK </td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02311") %><%--이슈 제목--%></td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02319") %><%--이슈요청자--%> </td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02318") %><%--이슈상태--%> </td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02788") %><%--처리요청일--%> </td>
                <%if(issueType.equals("Issue")){ %>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02787") %><%--처리예정일--%> </td>
                <%} %>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02855") %><%--최종완료일--%> </td>
                <%if(!issueType.equals("Issue")){ %>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02785") %><%--처리상태--%> </td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "00211") %><%--ECR등록--%></td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%> </td>
                <%}else{%>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02707") %><%--지연기간--%> </td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "02784") %><%--처리기간--%> </td>
                <td class="tdblueM" > <%=messageService.getString("e3ps.message.ket_message", "00211") %><%--ECR등록--%></td>
                <%} %>
            </tr>
<%
    long longRequestDate = 0;
    long longLastInDate = 0;
    long longdevelopmentDate = 0;
    long longLastInDate2 = 0;
    String unitState = "";
    if(issueType.equals("Issue")){
        for(int i = 0; i < vector.size(); i++){
            UnitErrorData data = (UnitErrorData)vector.get(i);
            E3PSTaskData taskData = new E3PSTaskData(data.getTask());
            String path = data.getPath();
            UnitErrorProcess issue = data.getIssue();
            E3PSTask task = (E3PSTask)data.getTask();
            E3PSProject E3PSProject = (E3PSProject) task.getProject();
            unitState = data.unitState;
            long delay = 0;
            long addTime = 0;

            Timestamp requestDate = issue.getRequestDate();
            Timestamp planDate = issue.getPlanDate();
            Timestamp lastInDate = issue.getLastInDate();
%>
            <tr>
                <td class="tdwhiteL" title = "<%=path%>" >
                    <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                        &nbsp;<nobr>
                        <img src="/plm/portal/icon/task.gif">
                        <a href="/plm/jsp/project/TaskView.jsp?oid=<%=taskData.E3PSTaskOID%>"><%=path%> </a>
                        </nobr>
                    </div>
                </td>
                <td class="tdwhiteL" title = "<%=issue.getErrorName() %>" >
                    <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">&nbsp;
                        <nobr>
                        <a href="#" onClick="javascript:viewError('<%=CommonUtil.getOIDString(issue)%>')"> <%=issue.getErrorName()%>
                        </a>
                        </nobr>
                    </div>
                </td>
                <td class="tdwhiteM" > &nbsp;<%=issue.getProcessCreator().getFullName()%></td>
                <td class="tdwhiteM" > &nbsp;<%=unitState%></td>
                <td class="tdwhiteM" > &nbsp;<%=DateUtil.getDateString(PersistenceHelper.getCreateStamp(issue), "D")%></td>
                <td class="tdwhiteM" > &nbsp;<%=DateUtil.getDateString(issue.getRequestDate(), "D")%></td>

                <td class="tdwhiteM" > &nbsp;
                <%
                    boolean planDelay = false;
                    if( (requestDate != null) && (planDate != null) ) {
                        if( requestDate.getTime() < planDate.getTime()) {
                            planDelay = true;
                        }
                    }
                %>
                <span <%if(planDelay){%>class="style1"<%}%>><%=planDate==null? "&nbsp;":DateUtil.getTimeFormat(planDate, "yyyy/MM/dd")%></span>
                </td>
                <td class="tdwhiteM" > &nbsp;<%=DateUtil.getDateString(issue.getLastInDate(), "D")%></td>
                <td class="tdwhiteM">&nbsp;
                    <%
                    if(issue.getRequestDate() != null && issue.getLastInDate() != null){
                        longRequestDate = issue.getRequestDate().getTime();//issue.getPlanDate().getTime();
                        longLastInDate = issue.getLastInDate().getTime();
                        delay = (longRequestDate - longLastInDate)/(60*60000);
                        // 녹색
                        if(delay > 0){
                    %>
                        <span class="style3" title='<%=messageService.getString("e3ps.message.ket_message", "02805") %><%--초과--%>'><%=Math.abs(delay)/24%>.<%=Math.abs(delay)%24%></span>
                    <%
                        }else if(delay == 0){
                    %>
                        <span class="style2" title='<%=messageService.getString("e3ps.message.ket_message", "02517") %><%--정상--%>'><%=Math.abs(delay)/24%>.<%=Math.abs(delay)%24%></span>
                    <%
                        // 빨간
                        }else if(delay < 0){
                    %>
                        <span class="style1" title="<%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%>"><%=Math.abs(delay)/24%>.<%=Math.abs(delay)%24%></span>
                    <%
                        }
                        //out.println( longRequestDate + "-"+longLastInDate + "="+ (longLastInDate -longRequestDate)/(60*60000));
                    }else if(issue.getRequestDate() != null && issue.getLastInDate() == null){
                        longRequestDate = issue.getRequestDate().getTime();
                        delay = (longRequestDate -System.currentTimeMillis())/(60*60000);

                        if(delay > 0){
                    %>
                        <span class="style3" title='<%=messageService.getString("e3ps.message.ket_message", "02805") %><%--초과--%>'><%=Math.abs(delay)/24%>.<%=Math.abs(delay)%24%></span>
                    <%
                        }else if(delay == 0){
                    %>
                        <span class="style2" title='<%=messageService.getString("e3ps.message.ket_message", "02517") %><%--정상--%>'><%=Math.abs(delay)/24%>.<%=Math.abs(delay)%24%></span>
                    <%
                        }else if(delay < 0){
                    %>
                        <span class="style1" title="<%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%>"><%=Math.abs(delay)/24%>.<%=Math.abs(delay)%24%></span>
                    <%   }%>
                    <%} %>
                    </td>
                    <%
                        if(issue.getDevelopmentDate() != null && issue.getLastInDate() != null){
                            longLastInDate2 = issue.getLastInDate().getTime();
                            longdevelopmentDate =issue.getDevelopmentDate().getTime();
                            addTime =(longLastInDate2 - longdevelopmentDate)/(60*60000);
                        }
                    %>

                    <td class="tdwhiteM">&nbsp;<%=Math.abs(addTime)/24%>.<%=Math.abs(addTime)%24%>
                    </td>
                    <td class="tdwhiteM"><%=(issue.isIsCreateECR()==true)?"Y" : "N"%>

            </tr>
<%    }%>
            <%if(vector.size() == 0) {%>
                <tr>
                    <td colspan=12" class=tdwhiteM0><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
                </tr>
            <%} %>
<%   }
    else {
        String unitState2 = "";
        while(qr.hasMoreElements()){
            Object[] object = (Object[]) qr.nextElement();

            UnitErrorProcess issue = (UnitErrorProcess)object[0];
            UnitErrorData unitData = new UnitErrorData(issue);
            E3PSTask task = (E3PSTask)unitData.getTask();
            E3PSTaskData taskData = new E3PSTaskData(task);
            E3PSProject E3PSProject = (E3PSProject) task.getProject();
            unitState2 = issue.getLifeCycleState().toString();
            //String lcState = issue.getState().toString();
            UnitErrorAssign assign = (UnitErrorAssign)UnitErrorProcessHelper.getAssignValue(issue);

            //out.println("assign ==>"+ assign);

%>
            <tr>
                <td class="tdwhiteL" title = "<%=unitData.projectNo%>" >
                    <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                        &nbsp;<nobr><%=unitData.projectNo%></nobr>
                    </div>
                </td>
                <td class="tdwhiteL" title = "<%=task.getTaskName()%>" >
                    <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                        &nbsp;<nobr><a href="#" onClick="javascript:viewError('<%=CommonUtil.getOIDString(issue)%>')"><%=task.getTaskName()%></a></nobr>
                    </div>
                </td>
                <td class="tdwhiteL" title = "<%=issue.getErrorName() %>" >
                    <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">&nbsp;
                        <nobr>
                        <a href="#" onClick="javascript:viewError('<%=CommonUtil.getOIDString(issue)%>')"> <%=issue.getErrorName()%>
                        </a>
                        </nobr>
                    </div>
                </td>
                <td class="tdwhiteM" ><%=issue.getProcessCreator().getFullName()%></td>
                <td class="tdwhiteM" ><%=State.toState(unitState2).getDisplay(messageService.getLocale())%></td>
                <td class="tdwhiteM" ><%=DateUtil.getDateString(PersistenceHelper.getCreateStamp(issue), "D")%></td>
                <td class="tdwhiteM" ><%=issue.getRequestDate()==null? "&nbsp;":DateUtil.getDateString(issue.getRequestDate(), "D")%></td>
                <td class="tdwhiteM" ><%=issue.getLastInDate()==null? "&nbsp;":DateUtil.getDateString(issue.getLastInDate(), "D")%></td>
                <td class="tdwhiteM" ><%=(DateUtil.getDateString(issue.getLastInDate(), "D")=="")? messageService.getString("e3ps.message.ket_message", "02791")/*처리중*/ : messageService.getString("e3ps.message.ket_message", "02173")/*완료됨*/ %></td>
                <td class="tdwhiteM"><%=(issue.isIsCreateECR()==true)?"Y" : "N"%>
                <td class="tdwhiteM" >
                <%
                    if( ("RESULTCOMFIRM".equals(unitState2))
                        || ("ERRORINWORK".equals(unitState2) && (assign == null)) ) {
                %>
                    <a href="#" onClick = "javascript:IssueAdd('<%=CommonUtil.getOIDString(issue)%>');">
                    <img src="/plm/portal/images/img_default/button/board_btn_handle.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02775") %><%--처리--%>" width="62" height="20" border="0">
                    </a>
                <%
                    } else if(DateUtil.getDateString(issue.getLastInDate(), "D")=="" ) {
                        out.println(messageService.getString("e3ps.message.ket_message", "02791")/*처리중*/);
                    } else {
                        out.println(messageService.getString("e3ps.message.ket_message", "02171")/*완료*/);
                    }
                %>
                </td>

            </tr>
<%
        }%>
        <%if(qr.size() == 0) {%>
        <tr>
            <td colspan="11" class=tdwhiteM0><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
        </tr>
        <%} %>
<%   }  %>
        </table>
    <table border="0" cellspacing="0" cellpadding="0" width="900">
        <tr>
            <td class="space10"> </td>
        </tr>
    </table>
<%
    int ksize = total/psize;
    int x = total%psize;
    if(x>0) ksize++;
    int temp = cpage/pageCount;
    if( (cpage%pageCount)>0)temp++;
    int start = (temp-1)*pageCount+1;

    int end = start + pageCount-1;
    if(end> ksize){
        end = ksize;
    }

    if(!issueType.equals("Issue")){
%>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
          <td>
                <table border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="40" align="center"><%if(start>1){%><a href="JavaScript:gotoPage(1)" class="small"><%=messageService.getString("e3ps.message.ket_message", "02792") %><%--처음--%></a><%}%></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%if(start>1){%>
                        <td width="60" class="quick" align="center"><a href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%}
                        for(int i=start; i<= end; i++){
                        %>
                        <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
                            <%if(i==cpage){%><b><%=i%><%}else{%><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a><%}%></td>
                        <%}
                        if(end < ksize){
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="60" align="center"><a href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "01186") %><%--다음--%></a></td>
                        <%}%>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="45"align="center"><%if(end<ksize){%><a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a><%}%></td>
                    </tr>
                </table>
          </td>
            <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
        </tr>
    </table>
<%  } %>

</form>
</body>
</html>
