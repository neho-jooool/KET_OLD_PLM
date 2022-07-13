<%@page import="e3ps.project.WorkProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import="e3ps.project.beans.ProjectIssueAnswerData"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.IssueAnswerComparator"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="e3ps.project.beans.ProjectIssueData"%>
<%@page import="e3ps.project.ProjectIssue"%>
<%@page import="e3ps.project.beans.ProjectIssueHelper"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.*"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.KETParamMapUtil"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%

	//********************** Department 가져오기************************/
	e3ps.groupware.company.Department department = null;
	String departmentName = "";
	String gateDrTabName = "";
	//로그인 사용자 정보 (OID)
	try {
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    
	    QueryResult qr = PersistenceHelper.manager.navigate(wtuser, "people", e3ps.groupware.company.WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
	        e3ps.groupware.company.People people = (e3ps.groupware.company.People) qr.nextElement();
	        department = people.getDepartment();
	        departmentName = department.getName();
	    }   
	}catch(Exception e){
	    Kogger.error(e);
	}
	
	gateDrTabName = "평가관리";
	//********************** Department 가져오기************************/
	

  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  E3PSProjectData projectData = new E3PSProjectData(project);
  String popup = StringUtil.checkNull( request.getParameter("popup") );

  ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
  boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
  boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
  boolean isCS = CommonUtil.isMember("공정감사");
  boolean isPM = ProjectUserHelper.manager.isPM(project);
  //이슈 삭제
  String deleteIssue = request.getParameter("deleteIssue");
  if ( deleteIssue != null ) {
    ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
  }
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!--<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>  -->
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<%@include file="/jsp/common/top_include.jsp"%>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>


<script type="text/javascript">
//<![CDATA[
    function addIssue() {
        var url = "/plm/jsp/project/projectIssueCreate.jsp?oid=<%=oid%>";
        openOtherName(url,"addIssue","750","650","status=no,scrollbars=yes,resizable=no");
    }

    function viewIssue(v) {
        var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
        openOtherName(url,"viewIssue","750","700","status=no,scrollbars=yes,resizable=no");
    }

    function clearAll(){
        $("#category").multiselect("uncheckAll");
        $("#manager").val("");
        $("#tempmanager").val("");
        $("#subject").val("");
        $("#owner").val("");
        $("#tempowner").val("");
        $("#startCreateDate").val("");
        $("#endCreateDate").val("");
    }

    function search() {
        document.frm.action = "/plm/servlet/e3ps/IssueServlet?command=searchProjectIssue";
        document.frm.submit();
    }

    // ==  [Start] 사람 검색  == //
    function delUser(targetId) {
        $("#" + targetId).val("");
        $("#temp" + targetId).val("");
    }

    function addUser(targetId) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
            return;
        }
        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "Y";
        acceptUser(targetId, acceptUsers, isAppend);
    }

    function acceptUser(targetId, arrObj, isAppend) {
        var userId = new Array();     // Id
        var userName = new Array();   // Nmae
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - wtuser oid // [1] - people oid // [2] - dept oid
            // [3] - user id    // [4] - name       // [5] - dept name
            // [6] - duty       // [7] - duty code  // [8] - email

            var infoArr = arrObj[i];
            userId[i] = infoArr[0];
            userName[i] = infoArr[4];
        }

        var tmpId = new Array();
        var tmpName = new Array();
        if ( $("#" + targetId).val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#" + targetId).val().split(", "), userId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#temp" + targetId).val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#" + targetId).val(tmpId.join(", "));
        $("#temp" + targetId).val(tmpName.join(", "));
    }
    // ==  [End] 사람 검색  == //
    
    function deleteIssue(v) {
        if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
          document.forms[0].action = "/plm/jsp/project/ProjectView2.jsp?deleteIssue="+v+"&oid=<%=oid%>";
          document.forms[0].method = "post";
          document.forms[0].submit();
        }
    }
    var loadFlag = true;
    //Jquery
    $(document).ready(function(){
    	treeGridResize("#ProjectViewIssueGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#category").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        Grids.OnRenderFinish = function(){
        	if(loadFlag){
        		loadFlag = false;
        		loadEjsGrid();
        	}
        	
        }
    });
    function openCostHistory(oid){
    	var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
        //openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
        
        getOpenWindow2(url, oid, 'full', 10);
    }
    
    function loadEjsGrid(){
    	
    	try{
            var idx = 0;
            var D = Grids[idx].Data;
            var formName = "frm";    //ProjectSearch 

            //D.Layout.Param.Pagingsize = $("input[name='perPage']").val();

            D.Data.Url = "/plm/servlet/e3ps/IssueServlet"; 
            D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
            D.Data.Param.command = "searchDataByProjectNo";
            
            
            D.Page.Url = "/plm/servlet/e3ps/IssueServlet";
            D.Page.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
            D.Page.Param.command = "searchDataByProjectNoPage";
            
            Grids[idx].Reload(D);
         }catch(e){
             alert(e.message);
         }
    }
//]]>
</script>

</head>

<body>
<%@include file="/jsp/common/processing.html"%>
    <form name="frm" method="post">
        <!-- hidden begin -->
        <input type="hidden" name="oid" value="<%=oid%>" />
        <!-- hidden end -->
        <table style="width: 100%; height: 100%;">
            <tr>
                <td valign="top">
                    <!-- [START] title & position -->
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <table style="width: 100%; height: 28px;">
                                    <tr>
                                        <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551")%><%--제품 프로젝트 상세정보--%></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table> <!-- [END] title & position --> <!-- [START] button -->
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="5"></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/jsp/project/WorkProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/jsp/project/WorkProjectView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                            </td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="#" onClick="javascript:top.close();"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>    
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                            <td height="10" background="/plm/portal/images/box_14.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_10.gif"></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <!--내용-->
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00267")%><%--Issue 관리--%></td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <%
                                                        //QueryResult qrq = ProjectUserHelper.manager.getAllProjectUser(project);
                                                        WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

                                                        QueryResult qrq = ProjectUserHelper.manager.getQueryForTeamUsers(project, "ROLEMEMBER");
                                                        ProjectMemberLink pplink = null;
                                                        PeopleData pdata = null;
                                                        boolean buttonAuth = false;
                                                        while (qrq.hasMoreElements()) {
                                                    		pplink = (ProjectMemberLink) qrq.nextElement();
                                                    		pdata = new PeopleData(pplink.getMember());
                                                    		if (sessionUser.getName().equals(pdata.id)) {
                                                    		    buttonAuth = true;
                                                    		    break;
                                                    		}
                                                        }
                                                    %>
                                                    <%
                                                        if (auth.isLatest && !auth.isCheckOutOrg && (auth.isProjectUser || auth.isPMO || buttonAuth || isPM || isAdmin)) {
                                                    %>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:addIssue();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><%--추가--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    <%
                                                        }
                                                    %>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="780">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table> 
                              <!-- EJS TreeGrid Start -->
                                <div class="content-main">
                                    <div class="container-fluid">
                                        <div class="row-fluid">
                                            <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%">
                                                <bdo Debug="1" AlertError="0" 
                                                     Layout_Url="/plm/jsp/project/ProjectViewIssueGridLayout.jsp"
                                                     Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
                                                     Data_Method="POST"
                                                     Data_Param_Result="<%=tgData %>"
                                                     Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_StandardDoc_List"
                                                     
                                                     Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp" 
							                         Page_Format="Internal" 
							                         Page_Data="TGData" 
							                         Page_Method="POST" 
                                                     >
                                                </bdo>
                                            </div>
                                        </div>
                                    </div>
                                </div> <!-- EJS TreeGrid Start -->
                                </td>
                            <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
                            <td height="10" background="/plm/portal/images/box_16.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
                        </tr>
                    </table>
                 </td>
             </tr>
        </table>
    </form>
</body>
</html>
