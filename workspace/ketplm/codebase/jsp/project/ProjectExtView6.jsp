<%@page contentType="text/html; charset=UTF-8"%>

<%@page import = "java.util.*"%>
<%@page import="
            wt.fc.*,
            wt.folder.*,
            wt.lifecycle.*,
            wt.org.*,
            wt.part.*,
            wt.project.Role,
            wt.query.*,
            wt.session.*,
            wt.team.*,
            wt.vc.*,
            wt.vc.wip.*,
            wt.workflow.engine.WfActivity,
            wt.workflow.engine.WfProcess,
            wt.workflow.work.WorkItem"%>
<%@page import="
            e3ps.common.content.*,
            e3ps.common.jdf.config.Config,
            e3ps.common.jdf.config.ConfigImpl,
            e3ps.common.jdf.log.Logger,
            e3ps.common.query.*,
            e3ps.common.util.*,
            e3ps.groupware.company.*,
            e3ps.project.*,
            e3ps.project.beans.*,
            e3ps.project.historyprocess.HistoryHelper,
            e3ps.project.outputtype.OEMPlan,
            e3ps.project.outputtype.OEMProjectType,
            e3ps.sap.*,
            e3ps.wfm.entity.KETWfmApprovalMaster
            " %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%@page import="e3ps.project.sap.ProductPrice"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>

<%
  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  String popup = StringUtil.checkNull( request.getParameter("popup") );
  boolean isCS = CommonUtil.isMember("공정감사");

%>

<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

  function updateCostInfo(){
    width = 890;
    height = 400;
    var screenWidth = (screen.availWidth-width)/2;
    var screenHeight = (screen.availHeight-height)/2;
    var url = "/plm/jsp/project/UpdateCostInfo.jsp?oid=<%=oid%>";
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(890,400);
    newWin.focus();
  }
  
  function openCostHistory(oid){
	  var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
      //openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
      
      getOpenWindow2(url, oid, 'full', 10);
  }
//-->
</script>
</head>
<body>
<input type='hidden' name='popup' value="<%=popup %>">
<table width="820" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="820" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="820" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551") %><%--제품 프로젝트 상세정보--%></td>
<%if(popup == null){ %>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02401") %><%--자동차사업부--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03117") %><%--프로젝트상세정보--%></td>
                <%} %>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5"></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/plm/portal/images/tab_4.png"></td>
              <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></a></td>
              <td><img src="/plm/portal/images/tab_5.png"></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/plm/portal/images/tab_4.png"></td>
              <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></a></td>
              <td><img src="/plm/portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/plm/portal/images/tab_4.png"></td>
              <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327") %><%--인원--%></a></td>
              <td><img src="/plm/portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <%if(!isCS){ %>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/plm/portal/images/tab_4.png"></td>
              <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></a></td>
              <td><img src="/plm/portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <%} %>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/plm/portal/images/tab_4.png"></td>
              <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></a></td>
              <td><img src="/plm/portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/plm/portal/images/tab_1.png"></td>
              <td background="/plm/portal/images/tab_3.png" class="btn_tab">Gate/DR<%--Gate/DR--%></td>
              <td><img src="/plm/portal/images/tab_2.png""></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/plm/portal/images/tab_4.png"></td>
              <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03034") %><%--프로그램--%></a></td>
              <td><img src="/plm/portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td><img src="/plm/portal/images/tab_4.png"></td>
	                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab">
	                                                <a href="javascript:openCostHistory('<%=oid %>')" class="btn_tab">개발원가</a></td>
	                                                <td><img src="/plm/portal/images/tab_5.png""></td>
	                                            </tr>
	                                        </table>
	                                        
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright<%if("popup".equals(popup)){ %>_t<%} %>.html" name="copyright" width="820" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</body>
</html>
