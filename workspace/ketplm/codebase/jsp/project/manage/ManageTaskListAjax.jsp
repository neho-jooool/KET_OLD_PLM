<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="wt.fc.*" %>
<%@page import = "e3ps.project.*,
          e3ps.project.beans.*,
          e3ps.common.util.*,
          java.util.*,
          e3ps.common.web.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

    Hashtable paramHash =WebUtil.getHttpParamsFromAjax(request);
    String oid = StringUtil.checkNull((String)paramHash.get("oid"));
    String coid = StringUtil.checkNull((String)paramHash.get("coid"));
    String deptOid = StringUtil.checkNull((String)paramHash.get("deptOid"));

    //Kogger.debug("oid ==> " + oid  + "  coid ==> " + coid);
    Object obj = CommonUtil.getObject(oid);
    String command = StringUtil.checkNull((String)paramHash.get("command"));
       String firstTaskoid = "";
    E3PSProject project = null;
    E3PSTask task = null;
    QueryResult qr = null;
    boolean isLevelOne = false;
    String pmEnd = "99999999";
    boolean isParentNull = false;
    if ( obj instanceof E3PSProject ) {
      isLevelOne = true;
      project = (E3PSProject)obj;
      qr = ProjectTaskHelper.manager.getChild(project);

    } else if ( obj instanceof E3PSTask ) {
      task = (E3PSTask)obj;

      ScheduleData sd = (ScheduleData) task.getTaskSchedule().getObject();

      qr = ProjectTaskHelper.manager.getChildWithTargetItem(task);
      if(task.getParent() == null){
        isParentNull = true;
      }
    }

    int i = 0;
    //Collections.sort(qr.getObjectVectorIfc().getVector(), new TaskPlanComparator());
    while ( qr.hasMoreElements() ) {
      Object[] objArr = (Object[])qr.nextElement();
      E3PSTask taskData = (E3PSTask)objArr[0];

      if(deptOid != null && deptOid.length() > 0){
        String departmentOid = CommonUtil.getOIDString(taskData.getDepartment());

        if(!deptOid.equals(departmentOid)){
          continue;
        }
      }
      E3PSTaskData data = new E3PSTaskData((E3PSTask)objArr[0]);
      Object objData = objArr[1];
      String sapRef = "-";
      String taskStartdate = DateUtil.getDateString(data.taskPlanStartDate,"date");
      String taskEnddate = DateUtil.getDateString(data.taskPlanEndDate,"date");
      ScheduleData sd = (ScheduleData) taskData.getTaskSchedule().getObject();
      String checked = "";
      String taskoid =CommonUtil.getOIDString(taskData);
      String desc = taskData.getTaskDesc();

      if(desc==null){
        desc = "";
      }

      if(command.equals("update") || command.equals("up") || command.equals("down")){
        if(taskoid.equals(coid)){
          checked = "checked";
          firstTaskoid = taskoid;
        }
      }

      if(command.equals("create")){
        if(!qr.hasMoreElements()){
          checked = "checked";
          firstTaskoid = taskoid;
        }
      }

      if(command.equals("delete") || command.length() == 0){
        if(i == 0){
           checked = "checked";
           firstTaskoid = taskoid;
          }
      }

      String startFont = "<FONT color='black'>";

      String endFONT = "</FONT>";

      if(isParentNull){

      }

          String ts_exstartDate = DateUtil.getTimeFormat(sd.getPlanEndDate(), "yyyyMMdd");
          if(pmEnd.compareTo(ts_exstartDate) < 0){
            startFont = "<FONT color='FF3300'>";
          }



      E3PSTaskData datanow = null;
      String taskNowStartDate = "";
      String taskNowEndDate = "";

      if(!(task == null)){

        datanow = new E3PSTaskData(task);
        taskNowStartDate  = datanow.taskPlanStartDate.toString();
        if(!taskNowStartDate.equals("")){
          //taskNowStartDate = taskNowStartDate.replaceAll("-", "/");
          taskNowStartDate = taskNowStartDate.substring(0,10);
        }
        taskNowEndDate  = datanow.taskPlanEndDate.toString();
        if(!taskNowEndDate.equals("")){
          //taskNowEndDate = taskNowEndDate.replaceAll("-", "/");
          taskNowEndDate = taskNowEndDate.substring(0,10);
        }
        int dra = DateUtil.getDuration(datanow.taskPlanStartDate, datanow.taskPlanEndDate) + 1;
        String draString = ""+dra;
        //Kogger.debug("ListAjax ===>  " + draString);
        %>


        document.manageProjectTaskList.taskDuration.value = "<%=draString%>" ;
        document.manageProjectTaskList.tcstartdate.value = "<%=taskNowStartDate%>" ;
        document.manageProjectTaskList.tcenddate.value = "<%=taskNowEndDate%>" ;

<%
      }

        //String taskName = data.taskName;
        String taskName = StringUtil.htmlCharEncode(data.taskName);
%>
        var objRow =objBody.insertRow(objBody.rows.length);
        var cobjCel0 = objRow.insertCell();
        cobjCel0.className = "tdwhiteM";
        cobjCel0.style.textAlign="center";
        cobjCel0.innerHTML = "<input type='radio' name='check' " + "<%=checked%>" +
        " value='"+"<%=taskoid%>"+"' onClick=oneClick(this);selectTask("+"'<%=taskoid%>'"+");>";

        var cobjCell = objRow.insertCell();
        cobjCell.className = "tdwhiteM";
        cobjCell.style.textAlign="center";
        cobjCell.innerHTML = "<%=i+1%>";

        var cobjCel2 = objRow.insertCell();
        cobjCel2.className = "tdwhiteM";
        cobjCel2.style.textAlign="left";
        cobjCel2.innerHTML = "<div style='width:130;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><a href=# onClick=checkClick('<%= taskoid%>');selectTask("+"'<%=taskoid%>'"+");>"+unescape("<%=taskName%>")+"</a></nobr></div>";

        //var cobjCel3 = objRow.insertCell();
        //cobjCel3.className = "tdwhiteM";
        //cobjCel3.innerHTML = "<%//=wbsCode%>";

        var cobjCel4 = objRow.insertCell();
        cobjCel4.className = "tdwhiteM";
        cobjCel4.innerHTML = "<%=startFont%><%=taskStartdate%><%=endFONT%>";

        var cobjCel5 = objRow.insertCell();
        cobjCel5.className = "tdwhiteM";
        cobjCel5.innerHTML = "<%=startFont%><%=taskEnddate%><%=endFONT%>";

        var cobjCel6 = objRow.insertCell();
        cobjCel6.className = "tdwhiteM";
        cobjCel6.innerHTML = "<%=sd.getDuration()%>"+" <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>";

        var cobjCel7 = objRow.insertCell();
        cobjCel7.className = "tdwhiteM";
        cobjCel7.innerHTML = "<%=data.milestone%>";

        var cobjCel8 = objRow.insertCell();
        cobjCel8.className = "tdwhiteM";
        cobjCel8.innerHTML = "<%=(data.tasktype==null)?messageService.getString("e3ps.message.ket_message", "02345")/*일반*/:data.tasktype%>";

        var cobjCel9 = objRow.insertCell();
        cobjCel9.className = "tdwhiteM0";
        cobjCel9.innerHTML = "<%=(data.optiontype==null)?"&nbsp;":data.optiontype%>";


<%
    i++;
    }
%>
<%
      if(qr.size() > 0){
%>
              selectTask( '<%=firstTaskoid%>');

<%      }%>
