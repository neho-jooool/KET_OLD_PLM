<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.beans.ProjectIssueHelper"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.web.PageControl"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    response.addHeader("Cache-Control", "max-age=1, must-revalidate");   

	//********************** Department 가져오기************************/
	e3ps.groupware.company.Department department = null;
	String departmentName = "";
	String gateDrTabName = "";
	//로그인 사용자 정보 (OID)
	try {
	    wt.org.WTUser wtuser = (wt.org.WTUser) wt.session.SessionHelper.manager.getPrincipal();
	    
	    wt.fc.QueryResult qr = wt.fc.PersistenceHelper.manager.navigate(wtuser, "people", e3ps.groupware.company.WTUserPeopleLink.class);
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

    String pagingList = PropertiesUtil.getSearchPagingSizeList();
    String pagingNameList = PropertiesUtil.getSearchPagingSizeNameList();
    String gridStyle = PropertiesUtil.getSearchGridStyle();
    String cookiesType = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth = PropertiesUtil.getSearchColMinWidth();
    String pageSize = PropertiesUtil.getSearchPagingSizeDefault();
    String oid = request.getParameter("oid");
    
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);
    String popup = StringUtil.checkNull( request.getParameter("popup") );

    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
    boolean isCS = CommonUtil.isMember("공정감사");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
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
<script type="text/javascript">
var pagingList = '<%=pagingList%>';
var pagingNameList = '<%=pagingNameList%>';
var gridStyle = '<%=gridStyle%>';
var cookiesType = '<%=cookiesType%>';
var alternateType = '<%=alternateType%>';
var maxSort = '<%=maxSort%>';
var sortIcons = '<%=sortIcons%>';
var colMinWidth = '<%=colMinWidth%>';
var pageSize = '<%=pageSize%>';
var locale = '<%=messageService.getLocale()%>';
</script>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/project/cost/cost.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">

	function openCostHistory(oid){
		var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
        //openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
        
        getOpenWindow2(url, oid, 'full', 10);
	}

    $(document).ready(function(){
    	Cost.createDevCostGrid();
    });
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form name="CostListForm">    
    <input type="hidden" name="projectOid" value="<%=oid%>"> 
    <input type="hidden" name="projectNo" value="<%=project.getPjtNo()%>">
    <input type="hidden" name="division" value="<%=projectData.teamType.equals("자동차 사업부")?"1":"2"%>"> 
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551")%><%--제품 프로젝트 상세정보--%></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
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
                                                        href="ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isCS) {
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640")%><%--비용--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView10.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView5.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">CFT요청</a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>&popup=<%=popup%>"
                                                        class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01381")%><%--목표--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=gateDrTabName%><%--Gate/DR--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td><img src="/plm/portal/images/tab_4.png"></td>
                                              <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03034") %><%--프로그램--%></a></td>
                                              <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                          </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_1.png"></td>
                                                <td background="/plm/portal/images/tab_3.png" class="btn_tab">원가</td>
                                                <td><img src="/plm/portal/images/tab_2.png""></td>
                                            </tr>
                                        </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                    href="ProjectExtView9.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">ATFT</a></td>
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
                            <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <!--내용-->
                                <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                    <tr>
                                        <td valign="top">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03">개발원가 산출 결과</td>
                                                    <td align="right"><%-- <table border="0" cellspacing="0" cellpadding="0" id="buttonTable" style="display: none">
                                                            <tr>
                                                                <td width="10">&nbsp;</td>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                                    <a href="#" onClick="javascript:Cost.goCreate('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %>등록</a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                <td width="5">&nbsp;</td>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                                    <a href="#" onClick="javascript:Cost.goRevise('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684") %>개정</a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>                                                            
                                                            </tr>
                                                        </table> --%></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td>
                                                        <!-- EJS TreeGrid Start -->
                                                        <div class="content-main">
                                                            <div class="container-fluid">
                                                                <div class="row-fluid">
                                                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 300px"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- EJS TreeGrid End -->
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
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
