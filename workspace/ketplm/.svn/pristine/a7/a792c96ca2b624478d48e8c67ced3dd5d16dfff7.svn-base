<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="e3ps.common.web.ParamHash"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>

<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<html>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01340") %><%--디버깅 차수 등록--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<script language=JavaScript src="/plm/portal/js/org.js"></script>

</head>
<%
String[] taskNames = request.getParameterValues("taskNames");
String oid = request.getParameter("oid");
//Kogger.debug("@@@@@==oid=="+oid);
String reason = request.getParameter("reason");
String special = request.getParameter("special");
/*
String productStart = request.getParameter("start1");
String productEnd = request.getParameter("end1");
String designStart = request.getParameter("start2");
String designtEnd = request.getParameter("end2");
String parttStart = request.getParameter("start3");
String partEnd = request.getParameter("end3");
String assemStart = request.getParameter("start4");
String addemEnd = request.getParameter("end4");
String tryStart = request.getParameter("start5");
String tryEnd = request.getParameter("end5");
String reqStart = request.getParameter("start6");
String reqEnd = request.getParameter("end6");
String transStart = request.getParameter("start7");
String transEnd = request.getParameter("end7");
String reviewStart = request.getParameter("start8");
String reviewEnd = request.getParameter("end8");
*/

String popup = request.getParameter("popup");

Vector vec = new Vector();

//Kogger.debug("adfe323333 = " + test.length);
String taskName = "";
for(int i = 0; i < taskNames.length; i++){
    if(request.getParameter("end" + i) == null || (request.getParameter("end" + i)).length() == 0){
        continue;
    }
    ParamHash returnHash = new ParamHash();
    taskName = taskNames[i];
    returnHash.put("cname", taskName);

    returnHash.put("cstartdate", request.getParameter("start" + i));
//  Kogger.debug("@@@@@@@@@@@@ = " + request.getParameter("start" + i));
    returnHash.put("cenddate", request.getParameter("end" + i));
    returnHash.put("debug_sub", "true");

    if(taskName.equals("금형Try")){
        returnHash.put("taskType", "Try");
    }else{
        returnHash.put("taskType", "일반");    
    }
    vec.add(returnHash);
}

E3PSTask task = ProjectTaskHelper.createDebugTask(oid, reason, special, vec);

String copyOid = CommonUtil.getOIDString(task.getProject());

/*
Kogger.debug("qerdfadsfasdf = " + vec.size());
Kogger.debug("reason = " + reason);
Kogger.debug("special = " + special);

for(int i = 0; i < vec.size(); i++){
    Kogger.debug("!!!!!!!!!!!$$$$$$$$$$$$$");
    ParamHash returnHash2 = (ParamHash)vec.get(i);

    Kogger.debug(returnHash2.get("cname"));
    Kogger.debug(returnHash2.get("cstartdate"));
    Kogger.debug(returnHash2.get("cenddate"));
    Kogger.debug(returnHash2.get("debug_sub"));
}
*/

/*
String oid = (String) paramHash.get("oid");                    //
String cname = (String) paramHash.get("cname");                  //Task Name
//int cseq = StringUtil.parseInt((String) paramHash.get("cseq"), 1);      //Task 순서
String cstartdate = (String) paramHash.get("cstartdate");            //Task 시작일자
String cenddate = (String) paramHash.get("cenddate");              //Task 종료일자
String desc = (String)paramHash.get("desc");                  //Task 설명
String stdWork = (String) paramHash.get("stdWork");                //Task 표준공수

String debug_sub = (String) paramHash.get("debug_sub");                        // 금형  N차 Task Sub 냐?

String optionType = (String)paramHash.get("optionType");
String mileStone = (String)paramHash.get("mileStone");
String taskType = (String)paramHash.get("taskType");
String drValue = (String)paramHash.get("drValue");
*/

%>

<body>

<script>
    alert("<%=messageService.getString("e3ps.message.ket_message", "00041", task.getTaskName()) %><%--{0}이 생성되었습니다--%>");
    opener.parent.movePopup('<%=copyOid%>', '/plm/jsp/project/MoldProjectView.jsp?oid=<%=copyOid%>&popup=popup');
    self.close();
</script>
</body>
</html>
