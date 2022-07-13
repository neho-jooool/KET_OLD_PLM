<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import = "e3ps.project.*,
          e3ps.project.beans.*,
          e3ps.common.util.*,
          e3ps.common.web.*,
          java.util.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<%

    Hashtable paramHash =WebUtil.getHttpParamsFromAjax(request);

    String coid = StringUtil.checkNull((String)paramHash.get("coid"));
    String oid = StringUtil.checkNull((String)paramHash.get("oid"));
    String command = StringUtil.checkNull((String)paramHash.get("command"));


    Object obj= CommonUtil.getObject(oid);

    TemplateProject project = null;
    TemplateTask task = null;

    QueryResult qr = null;

    boolean isLevelOne = false;

    if ( obj instanceof TemplateProject ) {
      project = (TemplateProject)obj;
      qr = ProjectTaskHelper.manager.getChild(project);
    } else if ( obj instanceof TemplateTask ) {
      task = (TemplateTask)obj;
      qr = ProjectTaskHelper.manager.getChildWithWBSItemLink(task);
    }
    int i = 0;
    String firstTaskOid = "";

    while ( qr.hasMoreElements() ) {

      Object[] objArr = (Object[])qr.nextElement();

      TemplateTask taskData = (TemplateTask)objArr[0];
      TemplateTaskData data = new TemplateTaskData(taskData);

      String wbsCode = "&nbsp;";


      Object objData = objArr[1];

      String sapRef = "-";


      String desc = data.description;
      if(desc == null){
        desc = "";
      }

      String checked = "";
      String taskoid =CommonUtil.getOIDString(taskData);

      if( command.equals("create") ){
        if(!qr.hasMoreElements()){
          checked = "checked";
          firstTaskOid = taskoid;
        }
      }
      if( command.equals("update") || command.equals("up") || command.equals("down")){
        if(taskoid.equals(coid)){
          checked = "checked";
          firstTaskOid = taskoid;
        }
      }
      if( command.equals("delete") || command.length() == 0 ){

        if(i == 0){
          Kogger.debug("task OID ==> " + taskoid);
          checked = "checked";
          firstTaskOid = taskoid;
          }
      }

      TemplateTaskData tdata = null;
      if(task != null){
        tdata = new TemplateTaskData(task);

%>
      document.forms[0].tduration.value = "<%=tdata.duration%>";
<%
      }
%>
      var objRow =objBody.insertRow(objBody.rows.length);
      var cobjCel0 = objRow.insertCell();
      cobjCel0.className = "tdwhiteM";
      cobjCel0.style.textAlign="center";
      cobjCel0.innerHTML = "<input type='radio' name='check' " + "<%=checked%>" +
      " value='"+"<%=taskoid%>"+"' onClick=oneClick(this);selectTask("+
      "'<%=taskoid%>'"+");>";

      var cobjCell = objRow.insertCell();
      cobjCell.className = "tdwhiteM";
      cobjCell.style.textAlign="center";
      cobjCell.innerHTML = "<%=i+1%>";

      var cobjCel2 = objRow.insertCell();
      cobjCel2.className = "tdwhiteM";
      cobjCel2.style.textAlign="left";
      cobjCel2.innerHTML = "<div style='width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><a href=# onClick=checkClick('<%= taskoid%>');selectTask("+
      "'<%=taskoid%>'"+");>"+"<%=data.name%>"+"</a></nobr></div>";

      var cobjCel5 = objRow.insertCell();
      cobjCel5.className = "tdwhiteM";
      cobjCel5.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00138", data.duration) %><%--{0}일--%>";

      var cobjCel7 = objRow.insertCell();
      cobjCel7.className = "tdwhiteM";
      cobjCel7.innerHTML = "<%=data.milestone%>";

      var cobjCel8 = objRow.insertCell();
      cobjCel8.className = "tdwhiteM0";
      cobjCel8.innerHTML = "<%=(data.tasktype==null || "null".equals(data.tasktype))?messageService.getString("e3ps.message.ket_message", "02345")/*일반*/:data.tasktype%>";

<%
  i++;
}

if(qr.size() > 0){
%>
        selectTask("<%=firstTaskOid%>");
<%}%>
