<%@page import="ext.ket.wfm.service.KETWorkflowHelper"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="ext.ket.shared.mail.service.KETMailHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="ext.ket.part.entity.KETPartClassification"%>
<%@page import="wt.lifecycle.PhaseTemplate"%>
<%@page import="wt.lifecycle.LifeCycleTemplate"%>
<%@page import="wt.workflow.work.WorkItem"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.workflow.work.WfAssignedActivity"%>
<%@page import="wt.workflow.engine.WfProcess"%>
<%@page import="wt.lifecycle.LifeCycleHelper"%>
<%@page import="wt.lifecycle.LifeCycleManaged"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.org.WTPrincipalReference"%>
<%@page import="wt.workflow.work.WorkflowHelper"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.E3PSProjectHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.beans.TemplateProjectData"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.dms.entity.KETDevelopmentRequest"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.mail.MailUtil"%>
<%@page import="e3ps.project.beans.MoldProjectHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%
  wt.fc.Persistable obj = null;
  String oid = request.getParameter("oid");
  ReferenceFactory rf = new ReferenceFactory();
  WorkItem item = (WorkItem)rf.getReference(oid).getObject();
  WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
  obj = item.getPrimaryBusinessObject().getObject();

  // 프로젝트 정보
  E3PSProject project = null;
  E3PSProjectData projectData = null;
  if(obj != null){
    project = (E3PSProject)obj;
    projectData = new E3PSProjectData(project);
  }
  TemplateProject tp = null;
  TemplateProjectData tpdata = null;
  if(project.getTemplateCode() != null) {
    tp = ProjectHelper.getTemplate(project.getTemplateCode());
    tpdata = new TemplateProjectData(tp);
  }
  ReviewProject rp = null;  
  ProductProject pp = null;
  if(obj instanceof ReviewProject){
    rp = (ReviewProject)obj;
  }else if(obj instanceof ProductProject){
    pp = (ProductProject)obj;
  }


  KETDevelopmentRequest ketdr = null;
  String ketdrOid = "";
  try{
    if(project.getDevRequest() != null){
      ketdr = project.getDevRequest();
      ketdrOid = CommonUtil.getOIDString(ketdr);
    }
  }catch(Exception e){
%>
  <script>
    //alert("관리자 한테 문의 하십시오. [개발(검토)의뢰서 삭제됨]");
    //parent.document.location.reload();
  </script>
<%
    e.printStackTrace();
  }



  String pjtOid = CommonUtil.getOIDString(project);
  String cmd = request.getParameter("cmd");
  if(cmd == null){
    cmd = "";
  }
  String devUser = request.getParameter("devUser");
  if(devUser == null){
    devUser = "";
  }
  String pjtType = ""+ project.getPjtType();
  E3PSProject uPjt = null;
  if(cmd.equals("complete")){
    Hashtable hash = new Hashtable();
    hash.put("devUser", devUser);
    hash.put("oid", pjtOid);
    hash.put("projectType", pjtType);
    try{
      //uPjt = E3PSProjectHelper.service.updateE3PSProject(hash);
       WTUser wtdevUser = null;
       if(devUser !=null && devUser.length() != 0){
           wtdevUser = (WTUser) CommonUtil.getObject(devUser);
       }
       ProjectUserHelper.manager.replacePM(project, wtdevUser);

    if(wtdevUser == null){
%>
  <script>
    alert('<%=messageService.getString("e3ps.message.ket_message", "03058") %><%--프로젝트 객체 오류 관리자한테 문의 하시기 바랍니다--%>');
    document.location.href= "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
  </script>
<%
    }else{
      Vector vec = null;
      vec = new Vector();
      WTUser dev =(WTUser)ProjectUserHelper.manager.getPM(project);
      if(dev != null){
        WTPrincipalReference prinRef = WTPrincipalReference.newWTPrincipalReference(dev);
        // 개발팀이관시 원가 인터페이스 테이블에 등록 되게 되어 있었음
        // 개발자가 등록 되지 않았 었기 때문에 개발자 지정후 업무 완료시 원가 인터페이스 테이블에 개발자 업데이트 해주 도록 수정
        //e3ps.project.beans.ProductHelper.syncProjectCostIF(project);
        //KETWorkflowHelper.service.workComplete(item, prinRef, vec);
        WorkflowHelper.service.workComplete(item, SessionHelper.manager.getPrincipalReference(), vec);
        
        List<WTUser> toUser = new ArrayList<WTUser>();
        toUser.add(wtdevUser);
        if (toUser.size() > 0) {
            KETMailHelper.service.sendFormMail("08046", "NoticeMailLine2.html", project, (WTUser) SessionHelper.getPrincipal(), toUser);
        }
      }
      %>
      <script>
        var pm = "<%=dev.getFullName()%>";
        alert(pm+" PM으로 지정됨,업무가 완료되었습니다");
        <%-- alert("<%=messageService.getString("e3ps.message.ket_message", "00005", dev.getFullName()) %>{0} 개발 담당자로 지정됨, 업무가 완료 되었습니다 "); --%>
        try{
        	opener.location.reload(true);    	
        }catch(exception){
        }
        self.close();

      </script>
    <%
      ProjectHelper.manager.projectSendMailAssign(project, "devAssign", dev, true);
    }
    }catch(Exception e){
	Kogger.error(e);
    }
  }

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01208") %><%--담당자 지정--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/common.js"></script>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<%@include file="/jsp/common/multicombo.jsp"%>
<SCRIPT>

  function addRoleMember(rname) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }
    acceptRoleMember(rname,attacheMembers);
  }

  function acceptRoleMember(rname, objArr) {
    if(objArr.length == 0) {
      return;
    }

    var displayName = document.getElementById("temp" + rname);
    var keyName = document.getElementById(rname);
    var DeptName = document.getElementById(rname+"Dept");


    /*
      subArr[0] = chkobj[i].value;//wtuser oid
      subArr[1] = chkobj[i].poid;//people oid
      subArr[2] = chkobj[i].doid;//dept oid
      subArr[3] = chkobj[i].uid;//uid
      subArr[4] = chkobj[i].sname;//name
      subArr[5] = chkobj[i].dname;//dept name
      subArr[6] = chkobj[i].duty;//duty
      subArr[7] = chkobj[i].dutycode;//duty code
      subArr[8] = chkobj[i].email;//email
    */

    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
      infoArr = objArr[i];
      displayName.value = infoArr[4];
      keyName.value = infoArr[0];
      DeptName.value = infoArr[5];
    }
  }

  function deleteRoleMember(rname) {
    document.getElementById("temp" + rname).value = "";
    document.getElementById(rname).value = "";
  }


  function workCompleteAction(){
    if(document.forms[0].devUser.value == ''){
      <%-- alert("<%=messageService.getString("e3ps.message.ket_message", "00589") %>");개발 담당자를 지정 하십시오--%>
      alert("PM을 지정하십시오.");
      return;
    }

    <%-- if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03285") %>개발 지정 업무를 완료  하시겠습니까?")) {
      return;
    } --%>
    if(!confirm("PM 지정 업무를 완료하시겠습니까?")){
    	return;
    }
    document.forms[0].cmd.value = "complete";
    document.forms[0].submit();
  }

  function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","400","status=no,scrollbars=no,resizable=no");
  }
  function requestPop(oid){
    var url = '/plm/jsp/dms/ViewDevRequestPop.jsp?oid='+oid;
    window.open(url,'의뢰서',"status=1, menu=no, width=830, height=800");
  }
  function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1080,800);
  }

  $(document).ready(function(){
	   //사용자 suggest
	   SuggestUtil.multiBind('USERDEPT', 'tempdevUser', 'devUser','devUserDept',null);
  });
</SCRIPT>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form method="post">
        <input type=hidden name=cmd> <input type=hidden name=oid value="<%=oid%>">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table style="width: 100%;">
                        <tr>
                            <td valign="top">
                                <table style="width: 100%;">
                                    <tr>
                                        <td background="/plm/portal/images/logo_popupbg.png">
                                            <table style="height: 28px;">
                                                <tr>
                                                    <td width="7px"></td>
                                                    <td width="20px"><img src="/plm/portal/images/icon_3.png"></td>
                                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01208") %><%--담당자 지정--%></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="176px" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01209")%><%--담당자 지정 업무--%></td>
                            <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:workCompleteAction();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02108")%><%--업무완료--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td width="5px"></td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                        href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                </tr>
                                                            </table></td>
                                                    </table></td>
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
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tdblueL" width="150">PM<%--<%=messageService.getString("e3ps.message.ket_message", "00585")%> 개발 담당자 --%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL" width="235">
                                <input id="tempdevUser" type='text' name='tempdevUser' value='<%=projectData.pjtPmName%>' class='txt_field'>
                                 <%
                                     WTUser pmUser = null;
                                     String pmStr = "";
                                     if (projectData.pjtPm != null) {
                                 		pmUser = projectData.pjtPm;
                                 		pmStr = CommonUtil.getOIDString(pmUser);
                                     }
                                 %> <input id="devUser" type='hidden' name='devUser' value='<%=pmStr%>'> 
                                    <a href="javascript:addRoleMember('devUser');"> 
                                    <img src="/plm/portal/images/icon_user.gif" border=0></a></td>
                            <td class="tdblueL" width="150"><%=messageService.getString("e3ps.message.ket_message", "00632")%><%--개발담당부서--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL0" width="240">
                                <input type=text id="devUserDept" name="devUserDept" style="width: 60%; border: 0" size=8
                                class='txt_fieldRO' readonly value='<%=projectData.devDept%>'></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table width="100%" height=20 " border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03090")%><%--프로젝트 정보--%></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table> <!-- 개발검토 프로파일  정보 -->
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03104")%><%-- 프로젝트 No --%></td>
                            <td width="240" class="tdwhiteL">&nbsp;<%=projectData.ViewpjtNo%></td>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%><span class="red"></span></td>
                            <td width="240" class="tdwhiteL0">&nbsp;<%=project.getLifeCycleState().getDisplay(messageService.getLocale())%>
                                <%
                                    if (CommonUtil.isAdmin()) {
                                %> <select name="state0" class="fm_jmp" style="width: 110"
                                onchange="javascript:onStateChange(this, '<%=project.getPersistInfo().getObjectIdentifier().getStringValue()%>');">
                                    <%
                                        String curState = project.getLifeCycleState().toString();
                                            try {
                                                //LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_ECA");
                                                LifeCycleTemplate lct = (LifeCycleTemplate) project.getLifeCycleTemplate().getObject();
                                                Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
                                                PhaseTemplate pt = null;
                                                wt.lifecycle.State lcState = null;
                                                for (int i = 0; i < states.size(); i++) {
                                                pt = (PhaseTemplate) states.get(i);
                                                lcState = pt.getPhaseState();
                                    %>
                                    <OPTION VALUE="<%=lcState.toString()%>"
                                        <%if (curState.equals(lcState.toString())) {%> selected <%}%>><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
                                    <%
                                        }
                                            } catch (Exception e) {
                                        	Kogger.error(e);
                                            }
                                    %>
                            </select> <%
                                 }
                             %>
                            </td>
                            
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02506")%><%--접수번호--%></td>
                            <td class="tdwhiteL">&nbsp; <%
                                 String devROid = "";
                                 String devRNumber = "";
                                 try {
                                    if (project.getDevRequest() != null) {
                                        devROid = CommonUtil.getOIDString(project.getDevRequest());
                                        devRNumber = project.getDevRequest().getNumber();
                                    }
                            
                                 } catch (Exception e) {
                                     Kogger.error(e);
                                 }
                             %> <a href="#" onclick="javascript:requestPop('<%=devROid%>');"><%=devRNumber%> </a>
                            </td>
                            <td class="tdblueL">Rank</td>
                            <td class="tdwhiteL0">&nbsp;<%=projectData.rankName%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
                            <td class="tdwhiteL">&nbsp;<%=rp.getAttr1()%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02139")%><%--영업담당자--%></td>
                            <%
                                WTUser creator = (WTUser) project.getCreator().getObject();
                                String creatorFullName = project.getCreatorFullName();
                                String creatorOid = CommonUtil.getOIDString(creator);
                            %>
                            <td class="tdwhiteL0">&nbsp;<%=projectData.salesName%></td>
                            
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578")%><%--제품구분--%></td>
                            <td class="tdwhiteL">
                            <%
                            String className = "";;
                            if(StringUtil.checkString(project.getProductTypeLevel2())){
                                KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(project.getProductTypeLevel2());
                                if(partClaz != null){
                                    className = partClaz.getParent().getClassKrName() + "/" +partClaz.getClassKrName();
                                }
                            }
                            %>
                            <%=className%>
                            </td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625")%><%--개발구분--%></td>
                            <td class="tdwhiteL0">&nbsp;<%=project.getDevelopentType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", project.getDevelopentType().getCode(), messageService.getLocale().toString()))%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01201")%><%--담당부서--%></td>
                            <td class="tdwhiteL">&nbsp;<%=projectData.devDept%></td>
                            <td class="tdblueL">PM</td>
                            <td class="tdwhiteL0">&nbsp;<%=projectData.pjtPmName%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로젝트명 --%></td>
                            <td colspan=3 class="tdwhiteL0">&nbsp;<%=projectData.pjtName%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00649")%><%--개발원가 제출일--%></td>
                            <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(rp.getEstimateDate(), "D")%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00663")%><%--개발제안서 제출일--%></td>
                            <td class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(rp.getProposalDate(), "D")%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%-- 최종사용처 --%></td>
                            <td class="tdwhiteL">
                                <%
                                    if (projectData.customereventVec != null && projectData.customereventVec.size() > 0) {
                                        
                                        for (int i = 0; i < projectData.customereventVec.size(); i++) {
                                            NumberCode nc = (NumberCode) projectData.customereventVec.get(i);
                                            String masterName = NumberCodeUtil.getNumberCodeValue("CUSTOMEREVENT", nc.getCode(), messageService.getLocale().toString());
                                            if(i!=0){
                                               out.print(",");   
                                            }
                                            out.print(masterName);
                                        }
                                    }
                                %>&nbsp;
                            </td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                            <td class="tdwhiteL0">
                                <%
                                    if (projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
                                        for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
                                            NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
                                            String masterName = NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode(), messageService.getLocale().toString());
                                            if(i!=0){
                                                out.print(",");   
                                            }
                                            out.print(masterName);
                                        }
                                    }
                                %>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817","")%><%--계획시작일--%></td>
                            <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(projectData.pjtPlanStartDate, "D")%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00820","")%><%--계획완료일--%></td>
                            <td class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(projectData.pjtPlanEndDate, "D")%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02064")%><%--실제시작일--%></td>
                            <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(projectData.pjtExecStartDate, "D")%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02062")%><%--실제 완료일--%></td>
                            <td class="tdwhiteL0">&nbsp;<%=DateUtil.getDateString(projectData.pjtExecEndDate, "D")%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07121")%><%--예상작업시간(hr)--%></td>
                            <td class="tdwhiteL">&nbsp;<%=projectData.planWorkTime %></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07122")%><%--실제작업시간--%></td>
                            <td class="tdwhiteL0">&nbsp;<%=projectData.execWorkTime %></td>
                        </tr>
                    </table>
    </form>
</body>
</html>
