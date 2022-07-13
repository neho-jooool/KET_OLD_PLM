<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="e3ps.common.service.KETCommonHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.common.util.*"%>
<%@page import ="wt.query.*"%>
<%@page import="e3ps.project.historyprocess.*" %>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.beans.TemplateProjectData"%>
<%@page import="e3ps.project.beans.SearchPagingProjectHelper"%>
<%@page import="e3ps.common.query.SearchUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%
  String cmd = request.getParameter("cmd");
  String oid = request.getParameter("oid");
  boolean isAdmin = CommonUtil.isAdmin();
  TemplateProject templateOid = null;
  String projectMasterOid = null;
  String projectType = null; // P|M|R

  if(cmd == null){
    cmd = "";
  }

  Object historyOid = CommonUtil.getObject(oid);
  E3PSProject project =(E3PSProject)historyOid;
  templateOid = (TemplateProject)historyOid;
  //QueryResult qr = null;
  //qr = HistoryHelper.getHistory(project);
  WTObject wtobj = KETCommonHelper.service.getPBO((WTObject)CommonUtil.getObject(oid));
  KETWfmApprovalMaster master = null;
  Vector newVec = null;
  if(wtobj!=null)
    master = (KETWfmApprovalMaster)WorkflowSearchHelper.manager.getMaster(wtobj);
  if(master!=null){
    newVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
  }

  //out.println(newVec.size());


  QuerySpec  qs = null;
  long loid;
  Class target = null;
  qs = new QuerySpec();

  if(project instanceof TemplateProject ){
    target = TemplateProject.class;
    TemplateProject template = (TemplateProject)project;
    loid = template.getMaster().getPersistInfo().getObjectIdentifier().getId();
    projectMasterOid = CommonUtil.getOIDString(template.getMaster());
  }
  else{
    target = E3PSProject.class;
    loid = project.getMaster().getPersistInfo().getObjectIdentifier().getId();
    projectMasterOid = CommonUtil.getOIDString(project.getMaster());
  }

  if (project instanceof ProductProject) {
    projectType = "P";
  }
  else if (project instanceof MoldProject) {
    projectType = "M";
  }
  else if (project instanceof ReviewProject) {
    projectType = "R";
  }

  int idx = qs.addClassList(target , true);
  qs.appendWhere(new SearchCondition(target,"masterReference.key.id", SearchCondition.EQUAL,
      loid),new int[] {idx});
  SearchUtil.setOrderBy(qs,target,idx, "thePersistInfo.theObjectIdentifier.id" , "c1", true);


  int psize = 10;
  int cpage = 1;
  int total = 0;
  int pageCount = 10;

  //page
  String sessionidstring = request.getParameter("sessionid");
  if (sessionidstring == null){
    sessionidstring = "0";
  }
  long sessionid = Long.parseLong(sessionidstring);
  String pagestring = request.getParameter("tpage");
  if (pagestring != null && pagestring.length() > 0) {
    cpage = Integer.parseInt(pagestring);
  }

  PagingQueryResult result = null;
  if (sessionid <= 0) {
    result = PagingSessionHelper.openPagingSession((cpage-1)*psize, psize, qs);
  } else {
    result = PagingSessionHelper.fetchPagingSession((cpage - 1)  * psize, psize, sessionid);
  }

  if (result != null) {
    total = result.getTotalSize();
    sessionid = result.getSessionId();
  }

  if(cmd.equals("History")){
    boolean isCheckValue = HistoryHelper.checkOutIn(templateOid);

    if(isCheckValue){
  %>
    <script>
      alert(" <%=messageService.getString("e3ps.message.ket_message", "00252") %><%--History가 추가 되었습니다--%>");
      document.location.href = "/plm/jsp/project/ProjectHistoryList.jsp?oid=<%=oid%>";
    </script>
  <%
    }
  }
  %>
<title><%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%></title>
<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/common/processing.html"%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
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

<script src="/plm/portal/js/viewObject.js"></script>
<script language=JavaScript>
<!--

  function deleteProject(){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03274") %><%--History를  삭제  하시겠습니까?--%>")) {
      return;
    }

    var cbox = document.forms[0].checkboxEach;
    var selectedList = "";
    var len = checkCbox(cbox);
    var vlen = 0;
    disabledAllBtn();

    if(cbox != null)
    {
      if(len > 1)
      {
        for(var i=0 ; i<len ; i++)
        {
          if (cbox[i].checked==true){
            selectedList += "oid="+cbox[i].value+"&";
            vlen++;
          }
        }

      }else{
          selectedList += "oid="+cbox.value;
          vlen++;
      }
    }
    enabledAllBtn();
    if(vlen == 0){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02803") %><%--체크 박스를 선택 해주세요--%>');
      return;
    }
    var url="/plm/jsp/project/AlldeleteHistory.jsp?"+selectedList;
    openOtherName(url,"AlldeleteHistory","300","200","status=no,scrollbars=yes,resizable=no");

  }

  function addHistory(oid){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03275") %><%--History를  추가  하시겠습니까?--%>")) {
      return;
    }
    disabledAllBtn();
    showProcessing();
    document.forms[0].cmd.value = "History";
    document.forms[0].oid.value = oid;
    document.forms[0].submit();
  }


  function checkCbox(cbox)
  {

    if(cbox==null)
      len=0;
    else
    {
      len=cbox.length
      if(''+len == 'undefined') len = 1;
    }
    return len;
  }

  function comparison()
  {
    var cbox = document.forms[0].checkboxEach;
    var selectedList = "";
    var len = checkCbox(cbox);
    var vlen = 0;
    disabledAllBtn();

    if(cbox != null)
    {
      if(len > 1)
      {
        for(var i=0 ; i<len ; i++)
        {
          if (cbox[i].checked==true){
            selectedList += "oid="+cbox[i].value+"&";
            vlen++;
          }
        }

      }else{
          selectedList += "oid="+cbox.value;
          vlen++;
      }
    }
    enabledAllBtn();
    if(vlen == 0){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02803") %><%--체크 박스를 선택 해주세요--%>');
      return;
    }
    if(vlen == 1){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01636") %><%--비교할 이력 정보가 없습니다--%>');
      //if (cbox.checked==true) selectedList += "oid="+cbox.value;
      return;
    }
    if(vlen > 2){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01635") %><%--비교 대상은 2개만 가능 합니다--%>');
      return;
    }

    if(selectedList!=""){
    compareView(selectedList);
    }

  }

  function compareView(oids){
    var url="/plm/jsp/project/ProjectCompareFrm.jsp?"+oids;
    openOtherName(url,"ProjectCompareFrm","1200","800","status=no,scrollbars=yes,resizable=no");
  }
  function openView( url ) {
    newWin = window.open(url,"window","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=700,resizable=no,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(800,700);
    newWin.focus();
  }
  function projectSchedule(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oldOid='+oid+"&popup=popup", '',1100,800);
  }
  function viewHistoryChange(oid){
    var url = '/plm/jsp/project/HistoryChange.jsp?oid='+oid+"&mode=view";
    window.open(url,'일정변경사유',"status=1, menu=no, width=770, height=660, scrollbars=yes, resizable=no");
  }

  function historyView(oid){
    var url= "/plm/jsp/project/ProjectHistoryView.jsp?oid=" + oid;
    openOtherName(url,"historyView","630","500","status=no,scrollbars=yes,resizable=no");
  }

  function searchHistory(projectMasterOid, projectType) {
    var url= "/plm/servlet/e3ps/ProjectScheduleHistoryServlet?cmd=search&oid=" + projectMasterOid + "&ptype=" + projectType + "&type=L";
    window.open(url,'일정변경이력조회',"status=1, menu=no, width=810, height=850, scrollbars=yes, resizable=yes");
  }

  function gotoPage(p) {
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
  }
  
//-->
</script>
</head>
<body>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
    CommonUtil.tabs('tabs');
})
</script>

    <form>
        <input type="hidden" name="cmd"> 
        <input type="hidden" name="oid" value="<%=oid%>"> 
        <input type="hidden" name="tpage">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td valign="top" style="padding: 0px 0px 0px 0px">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td background="/plm/portal/images/logo_popupbg.png">
                                            <table height="28" border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%></td>
                                                    <td width="10"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <div id="tabs">
                        <ul>
                            <li><a class="tabref" href="#tabs-1"><%=messageService.getString("e3ps.message.ket_message", "02366") %><%--일정변경이력조회--%></a></li>
                            <li><a class="tabref" href="#tabs-2" rel="/plm/jsp/project/CancelProjectList.jsp?oid=<%=oid%>">취소이력</a></li>
                            <li><a class="tabref" href="#tabs-3" rel="/plm/jsp/project/StopHistoryList.jsp?oid=<%=oid%>">중지이력</a></li>
                            <table align="right" width="50%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="right">
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </ul>
                        <!-- 첫번째 tab 화면 -->
                        <div id="tabs-1" class="tabMain">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%" height="390px">
                                <tr>
                                    <td valign="top">
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td align="right">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="javascript:searchHistory('<%=projectMasterOid%>', '<%=projectType%>');"
                                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02366")%><%--일정변경이력조회--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="0" cellpadding="0">
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
                                                <!--<td class="tdblueM">&nbsp;</td>  -->
                                                <td width="30px" class="tdblueM">No</td>
                                                <td width="60px" class="tdblueM">Project<br>No
                                                </td>
                                                <td class="tdblueM">Project Name</td>
                                                <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                                                <td width="70px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817", "<br>") %><%--계획{0}시작일--%></td>
                                                <td width="70px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00820", "<br>") %><%--계획{0}완료일--%></td>
                                                <!--td width="70px" class="tdblueM">최초<br>등록일자</td-->
                                                <td width="70px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02356") %><%--일정변경--%>
                                                    <br><%=messageService.getString("e3ps.message.ket_message", "01998") %><%--승인일--%></td>
                                                <!-- [START] [PLM System 1차개선] 최종 수정일자 제거(주석 처리), 2013-07-05, BoLee -->
                                                <!--td width="70px" class="tdblueM">최종<br>수정일자</td-->
                                                <!-- [END] [PLM System 1차개선] 최종 수정일자 제거(주석 처리), 2013-07-05, BoLee -->
                                                <td width="90px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                                <!--td width="40px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td-->
                                                <td width="90px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01682") %><%--사유--%></td>
                                                <td width="60px" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02356") %><%--일정변경--%>
                                                    <br><%=messageService.getString("e3ps.message.ket_message", "01171") %><%--내역--%></td>
                                            </tr>
                                            <%
        
                                              int listCount = 1;
                                              int count = 1;
                                              int vecCount = 0;
                                              //out.println(newVec.size());
                                              while ( result.hasMoreElements() ) {
                                                Object[] obj = (Object[])result.nextElement();
                                                TemplateProject template = (TemplateProject)obj[0];
                                                E3PSProject eproject = (E3PSProject)obj[0];
                                                E3PSProjectData e3psData = new E3PSProjectData(eproject);
                                                TemplateProjectData data = new TemplateProjectData((TemplateProject)obj[0]);
                                                KETWfmApprovalHistory history = null;
                                            
                                                //KETWfmApprovalMaster master1 = (KETWfmApprovalMaster)WorkflowSearchHelper.manager.getMaster((WTObject)template);
                                            
                                                //out.println(CommonUtil.getOIDString(master1));
                                                //if(vecCount < newVec.size()){
                                                // history = (KETWfmApprovalHistory)newVec.get(vecCount);
                                                // vecCount++;
                                              //  }
                                                 String completeDate = "";
                                                 String completeDate2 = "";
                                                // if(history.getCompletedDate()!=null)
                                                //   completeDate = DateUtil.getTimeFormat(history.getCompletedDate(),"yyyy-MM-dd");
                                                //completeDate = WorkflowSearchHelper.manager.getLastApprovalDate(eproject);
                                            
                                                KETWfmApprovalMaster master2 = WorkflowSearchHelper.manager.getMaster(eproject);
                                            
                                                if( master2 != null )
                                                {
                                                  if( master2.getCompletedDate() != null )
                                                  {
                                                    completeDate = DateUtil.getTimeFormat(master2.getCompletedDate(),"yy/MM/dd");
                                                    completeDate2 = DateUtil.getTimeFormat(master2.getCompletedDate(),"yy/MM/dd HH:mm:ss");
                                                  }
                                                }
                                            
                                            %>
                                            <tr>
                                                <%
                                            String tdMClass = "";
                                            String tdM0Class = "";
                                            String tdLClass = "";
        
                                            if ( oid.equals(CommonUtil.getOIDString(eproject)) ) {      // 현재 버전 하이라이트
                                                tdMClass    = "tdredM";
                                                tdM0Class   = "tdredM0";
                                                tdLClass    = "tdredL";
                                            }
                                            else {
                                                tdMClass    = "tdwhiteM";
                                                tdM0Class   = "tdwhiteM0";
                                                tdLClass    = "tdwhiteL";
                                            }
                                            %>
                                                <td class="<%= tdMClass %>"><%= count++ %></td>
                                                <td class="<%= tdMClass %>"><%= e3psData.pjtNo %></td>
                                                <td title="<%= data.tempName %>" class="<%= tdLClass %>">
                                                    <div
                                                        style="width: 300px; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                        <nobr>
                                                            <a href="javascript:projectSchedule('<%= e3psData.e3psPjtOID %>')"><%= data.tempName %></a>
                                                        </nobr>
                                                    </div>
                                                </td>
                                                <td class="<%= tdMClass %>"><%= data.tempDuration %><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                                                <td title="<%= DateUtil.getTimeFormat(e3psData.pjtPlanStartDate, "yy/MM/dd HH:mm:ss") %>"
                                                    class="<%= tdMClass %>"><%= DateUtil.getTimeFormat(e3psData.pjtPlanStartDate, "yy/MM/dd") %></td>
                                                <td title="<%= DateUtil.getTimeFormat(e3psData.pjtPlanEndDate, "yy/MM/dd HH:mm:ss") %>"
                                                    class="<%= tdMClass %>"><%= DateUtil.getTimeFormat(e3psData.pjtPlanEndDate, "yy/MM/dd") %></td>
                                                <!-- [START] [PLM System 1차개선] 일정 변경 승인일 수정(결재 프로세스 없는 경우 최종 수정일 표시), 2013-07-05, BoLee -->
                                                <%
                                                    if ( template.isWorkingCopy() || (template.getPjtIteration() == 0 && template.getPjtIteration() == 0 && !StringUtil.checkString(completeDate)) ) {  // 작업중이거나 승인일 없을 경우 승인일 표시하지 않음
                                                                %>
                                                <td title="" class="<%= tdMClass %>">-</td>
                                                <%
                                                    }
                                                    else if ( template.getPjtIteration() == 0 ) {                               // 결재 받은 버전일 경우 승인일 표시
                                                %>
                                                <td title="<%= completeDate2 %>" class="<%= tdMClass %>"><%= completeDate %></td>
                                                <%
                                                    }
                                                    else {                                                                      // 결재 프로세스 없는 경우 최종 수정일 표시
                                                %>
                                                <td title="<%= DateUtil.getTimeFormat(data.tempModifyDate, "yy/MM/dd HH:mm:ss") %>"
                                                    class="<%= tdMClass %>">(<%= DateUtil.getTimeFormat(data.tempModifyDate, "yy/MM/dd") %>)
                                                </td>
                                                <%
                                                }
                                                %>
                                                <!-- [END] [PLM System 1차개선] 일정 변경 승인일 수정(결재 프로세스 없는 경우 최종 수정일 표시), 2013-07-05, BoLee -->
                                                <!-- [START] [PLM System 1차개선] 최종 수정일자 제거(주석 처리), 2013-07-05, BoLee -->
                                                <!--td title=" <%--=DateUtil.getTimeFormat(data.tempModifyDate, "yy/MM/dd HH:mm:ss")--%>" class="tdwhiteM">&nbsp;<%--=DateUtil.getTimeFormat(data.tempModifyDate, "yy/MM/dd")--%></td-->
                                                <!-- [END] [PLM System 1차개선] 최종 수정일자 제거(주석 처리), 2013-07-05, BoLee -->
                                                <!-- [START] [PLM System 1차개선] 버전 표시 수정(pjtHistory.pjtIteration), 2013-07-05, BoLee -->
                                                <td class="<%= tdMClass %>">
                                                    <%
                                                        if ( template.getPjtIteration() == 0 ) {
                                                    %> <%= template.getPjtHistory() %> <%
                                                        }
                                                        else {
                                                    %> <%= template.getPjtHistory() + "." + template.getPjtIteration() %> <%
                                                        }
                                                    %> <%= (template.isWorkingCopy()) ? "(" + messageService.getString("e3ps.message.ket_message", "02441")/*작업중*/ + ")" : "" %>
                                                </td>
                                                <!-- [END] [PLM System 1차개선] 버전 표시 수정(pjtHistory.pjtIteration), 2013-07-05, BoLee -->
                                                <!--td class="tdwhiteM">&nbsp;<%= e3psData.pjtCompletion %>%</td-->
                                                <td class="<%= tdMClass %>">
                                                    <%
                                                      String historyNoteType = "";
                                                                       String historyNoteTypeValue = "";
                            
                                                      historyNoteType = String.valueOf(eproject.getHistoryNoteType());
                                                      historyNoteTypeValue = NumberCodeUtil.getNumberCodeValue("HISTORYCHANGETYPE", historyNoteType);
                            
                                                      if ( StringUtil.checkString(historyNoteTypeValue) ) {
                                                  %> <a href="javascript:viewHistoryChange('<%= e3psData.e3psPjtOID %>')"><%= historyNoteTypeValue %></a>
                                                    <%
                                                      }
                                                      else {
                                                    %> - <%
                                                    }
                                                  %>
                                                </td>
                                                <td class="<%= tdM0Class %>">
                                                    <% if ( eproject.getHistoryNoteType() == 0 ) { %>&nbsp;<% } else { %><a href="#"
                                                    onclick="javascript:historyView('<%= CommonUtil.getOIDString(eproject) %>');"><%=messageService.getString("e3ps.message.ket_message", "01527") %><%--보기--%></a>
                                                    <% } %>
                                                </td>
                                            </tr>
                                            <%
                                                  }
                                                    if(result.size() == 0) {
                                                %>
                                            <tr>
                                                <!-- [START] [PLM System 1차개선] Project 일정 변경 이력 없을 경우 colspan 수정, 2013-07-05, BoLee -->
                                                <td class='tdwhiteM0' align='center' colspan='10'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>
                                                </td>
                                                <!-- [END] [PLM System 1차개선] Project 일정 변경 이력 없을 경우 colspan 수정, 2013-07-05, BoLee -->
                                            </tr>
                                            <%  }%>
                                        </table> <%
                                          int ksize = total / psize;
                                          int x = total % psize;
                                          if (x > 0)
                                            ksize++;
                                          int temp = cpage / pageCount;
                                          if ((cpage % pageCount) > 0)
                                            temp++;
                                          int start = (temp - 1) * pageCount + 1;
                            
                                          int end = start + pageCount - 1;
                                          if (end > ksize) {
                                            end = ksize;
                                          }
                                        %>
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%>
                                                        :<%=ksize%>)
                                                </span></td>
                                                <td>
                                                    <table border="0" align="center" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td width="40" align="center">
                                                                <%
                                                                if (start > 1) {
                                                                %><a href="JavaScript:gotoPage(1)" class="small"><img
                                                                    src="/plm/portal/btn_arrow4.gif"></a> <%
                                                                }
                                                                %>
                                                            </td>
                                                            <td width="1" bgcolor="#dddddd"></td>
                                                            <%
                                                            if (start > 1) {
                                                            %>
                                                            <td width="60" class="quick" align="center"><a
                                                                href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><img
                                                                    src="/plm/portal/btn_arrow3.gif"></a></td>
                                                            <td width="1" bgcolor="#dddddd"></td>
                                                            <%
                                                            }
                                                            for (int i = start; i <= end; i++) {
                                                            %>
                                                            <td style="padding: 2 8 0 7; cursor: hand"
                                                                onMouseOver="this.style.background='#ECECEC'"
                                                                OnMouseOut="this.style.background=''" class="nav_on">
                                                                <%
                                                                  if (i == cpage) {
                                                                %><b><%=i%> <%
                                                                  } else {
                                                                %><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a> <%
                                                                  }
                                                                %>
                                                            </td>
                                                            <%
                                                                }
                                                                  if (end < ksize) {
                                                                %>
                                                            <td width="1" bgcolor="#dddddd"></td>
                                                            <td width="60" align="center"><a href="JavaScript:gotoPage(<%=end+1%>)"
                                                                class="smallblue"><img src="/plm/portal/btn_arrow1.gif"></a></td>
                                                            <%
                                                            }
                                                            %>
                                                            <td width="1" bgcolor="#dddddd"></td>
                                                            <td width="45" align="center">
                                                                <%
                                                                if (end < ksize) {
                                                                %><a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><img
                                                                    src="/plm/portal/btn_arrow2.gif"></a> <%
                                                                }
                                                                %>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)
                                                </td>
                                            </tr>
                                        </table></td>
                                </tr>
                            </table>
                        </div>
                        <!-- 2번째 tab 화면부터는 rel속성에 정의된 url을 호출합니다. -->
                        <div id="tabs-2" style="height:390px"></div>
                        <div id="tabs-3" style="height:390px"></div>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
