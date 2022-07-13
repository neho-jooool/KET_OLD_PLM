<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    response.addHeader("Cache-Control", "max-age=1, must-revalidate");   

	//********************** Department 가져오기************************/
	e3ps.groupware.company.Department department = null;
	String departmentName = "";
	String gateDrTabName = "";
	//로그인 사용자 정보 (OID)
	wt.org.WTUser wtuser = null;
	try {
	    wtuser = (wt.org.WTUser) wt.session.SessionHelper.manager.getPrincipal();
	    
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
    
    String atftEdit = "N";
    //wt.org.WTUser QCuser = projectData.pjtCarQC;
    boolean isQC = false;
    /* if(QCuser != null){
		if(QCuser.getName().equals(wtuser.getName()))  {
		    isQC = true;//선행품질관리 ROle
		}
    } */
    
    QueryResult roleQs = ProjectUserHelper.manager.getProjectRoleMember(project, "Team_PRODUCT49,Team_PRODUCT39"); //선행품질관리 ROle, QC(中)
    if (roleQs != null && roleQs.size() > 0) {
	    while(roleQs.hasMoreElements()){
		    ProjectMemberLink memberLink = (ProjectMemberLink) roleQs.nextElement();
	        wt.org.WTUser QCuser = memberLink.getMember();
	        
	        if(QCuser.getName().equals(wtuser.getName()))  {
	            isQC = true;
	            break;
	        }
	    }
        
    }

    
    if((auth.isLatest && !auth.isCheckOut && auth.isProgress) && (isAdmin || isQC)){
	   atftEdit = "Y";
    }
    
    
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
var atftEdit = '<%=atftEdit%>';
var P1_completeYN = 'Y';
var P2_completeYN = 'Y';
</script>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/project/atft/atft.js?ver=1"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">
	
	function openCostHistory(oid){
		var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
        //openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
        
        getOpenWindow2(url, oid, 'full', 10);
	}
		
    $(document).ready(function(){
    	var td_data_class = "";
        var td_data_desision = "";
        var td_data_division = "";
        var td_data_type = "";
        var tdObj = "";
        var p1_state = "#60F49B";
        var p2_state = "#60F49B";
        var check_1 = "G";
        var check_2 = "G";
        var font_color_1 = "#010000";
        var font_color_2 = "#010000";
        var isExistCnt = 0;
        var p1_statestate = "";
        var p2_statestate = "";
        var rev = "";
        var reviseOid = "";
        var p1_oid = "";
        var p2_oid = "";
        var P1_DESISION_CNT = 0;
        var updateInfo = "";
        try{
            $.ajax({
                url : "/plm/ext/project/atft/getAtftIfno.do?pjtNo="+'<%=project.getPjtNo()%>',
                type : "POST",
                dataType : 'json',
                async : false,
                cache : false,
                success: function(data) {
                    $('#createTable td').each(function() {
                        if ($(this).is("#category")){
                            //console.log($(this).data("classification"));
                            
                            td_data_class = $(this).data("classification");
                            td_data_division = $(this).data("sheetdivision");
                            td_data_type = $(this).data("type");
                            tdObj = $(this);
                            
                            $.each(data, function(i) {
                            	p1_statestate = this.P1_STATE;
                            	p2_statestate = this.P2_STATE;
                            	rev = this.REV;
                            	reviseOid = this.P1_OID;
                            	p1_oid = this.P1_OID;
                            	p2_oid = this.P2_OID;
                            	updateInfo = "최종수정 : "+this.LASTDATE+" "+this.USERINFO;
                            	isExistCnt++;
                                if (td_data_class == this.CLASSIFICATION) {
                                    
                                    if(td_data_division == 'P1' && td_data_type == 'desision'){
                                    	if(this.P1_DESISION != null){
                                    		P1_DESISION_CNT++;
                                    	}
                                        if(this.P1_DESISION == 'NG'){
                                        	//alert(this.P1_DESISION);
                                            p1_state = "#FB4E54";
                                            check_1 = "R";
                                            font_color_1 = "#FFFFFF";
                                            //$(tdObj).css("background-color",'#FB4E54');
                                            $(tdObj).html("<font color='red'>"+this.P1_DESISION+"</font>");
                                            $(tdObj).css("text-align",'center');
                                        }else{
                                            //$(tdObj).css("background-color",'#60F49B');
                                            $(tdObj).html(this.P1_DESISION);
                                            $(tdObj).css("text-align",'center');
                                        }
                                        
                                        /* if(this.P1_DESISION == '' || this.P1_DESISION == null || this.P1_BASIS == '' || this.P1_BASIS == null){
                                        	P1_completeYN = 'N';
                                        } */
                                        
                                        if(this.P1_BASIS == '' || this.P1_BASIS == null){
                                        	P1_completeYN = 'N';
                                        }
                                        
                                    }
                                    
                                     if(td_data_division == 'P2'&& td_data_type == 'desision'){
        
                                        if(this.P2_DESISION == 'NG' || (this.ATFT1LEVEL == 'ATFT4' && this.P2_DESISION == null)){
                                            p2_state = "#FB4E54";
                                            check_2 = "R";
                                            font_color_2 = "#FFFFFF";
                                            //$(tdObj).css("background-color",'#FB4E54');
                                            $(tdObj).html("<font color='red'>NG</font>");
                                            $(tdObj).css("text-align",'center');
                                        }else{
                                        	$(tdObj).html(this.P2_DESISION);
                                            //$(tdObj).css("background-color",'#60F49B');
                                            $(tdObj).css("text-align",'center');
                                        }
                                        
                                        /* if(this.P2_DESISION == '' || this.P2_DESISION == null || this.P2_BASIS == '' || this.P2_BASIS == null){
                                            P2_completeYN = 'N';
                                        } */
                                        if(this.P2_BASIS == '' || this.P2_BASIS == null){
                                            P2_completeYN = 'N';
                                        }
                                    }
                                    
                                    if(td_data_division == 'P1' && td_data_type == 'basis'){
                                    	//console.log(this.P1_BASIS);
                                    	
                                        $(tdObj).html(this.P1_BASIS.replace(/\n/g, "<br>"));
                                    }
                                    
                                    if(td_data_division == 'P2' && td_data_type == 'basis'){
                                        $(tdObj).html(this.P2_BASIS.replace(/\n/g, "<br>"));
                                    }
                                    //console.log(this.CLASSIFICATION);
                                }else{
                                    return true; //continue
                                }
                                
                            });
                        }
                    });
                    var cnt = 0;
                    
                    if(isExistCnt > 0){
                    	$('#createTable td').each(function() {
                    		
                            if ($(this).is("#p1_state") && P1_DESISION_CNT > 0){
                                $(this).html("<b><font color="+font_color_1+">"+check_1+"</font></b>");
                                $(this).css("background-color",p1_state);
                                cnt++;
                            }
                            if ($(this).is("#p2_state")){
                                $(this).html("<b><font color="+font_color_2+">"+check_2+"</font></b>");
                                $(this).css("background-color",p2_state);
                                cnt++;
                            }
                            if(cnt == 2){
                                return false; //break;
                            }
                        });
                    }
                    
                    var oid = '<%=oid%>';
                    var projectNo = '<%=project.getPjtNo()%>';
                    var stateNm = "";
                    if(p1_statestate != 'APPROVED' && isExistCnt > 0){
                        stateNm = "(ALL-Tool 작업중)";
                    }
                    if(p2_statestate != 'APPROVED' && isExistCnt > 0){
                        stateNm = "(Full-Tool 작업중)";
                    }
                    if(p1_statestate != 'APPROVED' && p2_statestate != 'APPROVED' && isExistCnt > 0){
                        stateNm = "(작업중)";
                    }
                    $('[name=p1_oid]').val(p1_oid);
                    $('[name=p2_oid]').val(p2_oid);
                    
                    addContent = "<tr>";
                    addContent += "<td class='try-title'>&nbsp;Rev : "+rev+"<b><font color='black' size='2px'>&nbsp;&nbsp;"+stateNm+"</font></b></td>";
                    //addContent +="<a href='#' onClick=javascript:Atft.viewRevise('"+reviseOid+"'); return false;>"+rev+"</a></td>";
                    addContent += "<td width='30'>&nbsp;\</td>";
                    if(isExistCnt == 0 && atftEdit =='Y'){
                    addContent +="<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>"; 
                    addContent +="<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'>"
                    addContent +="<a href='#' onClick=javascript:Atft.goCreate('"+oid+"'); return false; class='btn_blue'>등록</a></td>"; 
                    addContent +="<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
                    addContent += "<td width='5'>&nbsp;</td>";
                    }
                    if(isExistCnt > 0 && atftEdit =='Y'){
                    	
                    	if(p1_statestate == 'INWORK' || p2_statestate == 'INWORK'){
                    		addContent +="<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>"; 
                            addContent +="<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'>"
                            addContent +="<a href='#' onClick=javascript:Atft.goModify('"+projectNo+"','"+oid+"'"+"); class='btn_blue'>수정</a></td>"; 
                            addContent +="<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
                            addContent += "<td width='5'>&nbsp;</td>";
                    	}
                    
                        if(p1_statestate == 'INWORK'){
                    	
	                    	
	                        addContent +="<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>"; 
	                        addContent +="<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'>"
	                        addContent +="<a href='#' onClick=javascript:Atft.goComplete('P1'); class='btn_blue'>All-Tool 완료</a></td>"; 
	                        addContent +="<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
	                        addContent += "<td width='5'>&nbsp;</td>";
                        }
                        
                        if(p2_statestate == 'INWORK'){
                            
                            addContent +="<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>"; 
                            addContent +="<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'>"
                            addContent +="<a href='#' onClick=javascript:Atft.goComplete('P2'); class='btn_blue'>Full-Tool 완료</a></td>"; 
                            addContent +="<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
                        }
                    
	                    addContent += "<td width='5'>&nbsp;</td>";
	                    
	                    if(p1_statestate == 'APPROVED' && p2_statestate == 'APPROVED'){
		                    addContent +="<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>"; 
		                    addContent +="<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'>"
		                    addContent +="<a href='#' onClick=javascript:Atft.goRevise(); class='btn_blue'>개정</a></td>"; 
		                    addContent +="<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
	                    }
                    }
                    addContent +="</tr>";
                    
                    $('#buttonTB').append(addContent);
                    
                    if(updateInfo != ""){
                    	$('#ATFTTITLE').html(updateInfo);	
                    }
                    

               /*      
                    if(isExistCnt == 0){
                    	
                    	$("#save_bt").css("display","");
                    	
                    }else if(isExistCnt > 0){
                    	$("#update_bt").css("display","");
                        $("#revise_bt").css("display","");
                    } */
                    hideProcessing();
                },
                
                error    : function(xhr, status, error){
                             hideProcessing();
                             alert(xhr+"  "+status+"  "+error);
                             
                }
            });
        }catch(e){
            alert(e);
        }
        
        
    
    });
    
    window.openATFTAutoCheck = function(sheetdivision){
    	var oid = "<%=oid%>";
        getOpenWindow2("/plm/ext/project/atft/atftAutoCheck.do?projectOid="+oid+"&sheetdivision="+sheetdivision+"&readOnly=yes", 'ATFT자동점검', 1280, 720);
    }
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form name="AtftListForm" method="post" enctype="multipart/form-data">    
    <input type="hidden" name="projectOid" value="<%=oid%>"> 
    <input type="hidden" name="projectNo" value="<%=project.getPjtNo()%>">
    <input type="hidden" name="division" value="<%=projectData.teamType.equals("자동차 사업부")?"1":"2"%>">
    <input type="hidden" name="p1_oid" value="">
    <input type="hidden" name="p2_oid" value=""> 
    <input type="hidden" name="sheetdivision" value="">
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
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09500") %><%--평가관리--%></a></td>
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
<%--                                         <%if(CommonUtil.isAdmin() || CommonUtil.isMember("원가_임원")){%>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                    href="ProjectExtView8.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">원가</a></td>
                                                <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                        </table></td>
                                        <%} %> --%>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_1.png"></td>
                                                <td background="/plm/portal/images/tab_3.png" class="btn_tab">ATFT</td>
                                                <td><img src="/plm/portal/images/tab_2.png""></td>
                                            </tr>
                                        </table></td>
                                        <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td><img src="/plm/portal/images/tab_4.png"></td>
	                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab">
	                                                <a href="javascript:openCostHistory('<%=oid %>')" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09518") %><%--개발원가--%></a></td>
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
                                                    <td class="font_03" id="ATFTTITLE">All-Tool, Full-Tool</td>
                                                    <td align="right">
                                                        <table border="0" cellspacing="0" cellpadding="0" id="buttonTB">
                                                        </table>
                                                    </td>
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
                                            <table id = "createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
							                <colgroup>
							                    <col width="19%" />
							                    <col width="19%" />
							                    <col width="4%" />
							                    <col width="27%" />
							                    <col width="4%" />
							                    <col width="27%" />
							                </colgroup>
                
							                <tbody>
							                    <tr>
							                        <td class="tdblueM" rowspan="2" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "00969")%><!-- 구분 --></td>
							                        <td class="tdblueM" id="p1_state"></td>
							                        <td class="tdblueM">P1/PPV</td>
							                        <td class="tdblueM" id="p2_state"></td>
							                        <td class="tdblueM">P2/MVBs</td>
							                        
							                    </tr>
							                    <tr>
							                        <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01760")%><!-- 상태 --></td>
							                        <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "09499")%><!-- 판정근거 --> <img src="/plm/portal/images/icon_5.png" style="cursor:pointer;" onclick="javascript:openATFTAutoCheck('P1')"></td>
							                        <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01760")%><!-- 상태 --></td>
							                        <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "09499")%><!-- 판정근거--> <img src="/plm/portal/images/icon_5.png" style="cursor:pointer;" onclick="javascript:openATFTAutoCheck('P2')"></td>
							                    </tr>
							                    
							                    <tr>
							                        <td class="tdwhiteM" align="center" rowspan="4" colspan="1"><font color="#000000"><b>제품</b></font></td>
							                        <td class="tdwhiteM" align="center">치수</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="치수" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="치수" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="치수" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="치수" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    
							                    <tr>
							                        <td class="tdwhiteM" align="center">외관</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="외관" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="외관" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="외관" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="외관" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    
							                    <tr>
							                        <td class="tdwhiteM" align="center">기능/성능</td>                        
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="기능/성능" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="기능/성능" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="기능/성능" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="기능/성능" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">신뢰성</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="신뢰성" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="신뢰성" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="신뢰성" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="신뢰성" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    
							                    <tr>
							                        <td class="tdwhiteM" align="center" rowspan="3" colspan="1"><font color="#000000"><b>공정조건 (CAPA)</b></font></td>
							                        <td class="tdwhiteM" align="center">사출(C/T)</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="사출(C/T)" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="사출(C/T)" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="사출(C/T)" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="사출(C/T)" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">프레스(SPM)</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="프레스(SPM)" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="프레스(SPM)" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="프레스(SPM)" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="프레스(SPM)" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">조립(T/T)</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="조립(T/T)" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="조립(T/T)" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="조립(T/T)" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="조립(T/T)" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    
							                    <tr>
							                        <td class="tdwhiteM" align="center" rowspan="5" colspan="1"><font color="#000000"><b>품질확보</b></font></td>
							                        <td class="tdwhiteM" align="center">Error-Proof</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="Error-Proof" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="Error-Proof" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="Error-Proof" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="Error-Proof" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">공정불량</td>
							                        <td class="tdwhiteM"  id="category" name="category"  data-classification="공정불량" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM"  id="category" name="category"  data-classification="공정불량" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM"  id="category" name="category"  data-classification="공정불량" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="공정불량" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">협력사</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="협력사" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="협력사" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="협력사" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="협력사" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">작업자/검사자</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="작업자/검사자" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="작업자/검사자" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="작업자/검사자" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="작업자/검사자" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">JIG준비<br>(취출/조립/검사)</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    
							                    <tr>
							                        <td class="tdwhiteM" align="center" rowspan="8" colspan="1"><font color="#000000"><b>양산준비<br>(System 및 기타)</b></font></td>
							                        <td class="tdwhiteM" align="center">개발BOM</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="개발BOM" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="개발BOM" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="개발BOM" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="개발BOM" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">양산BOM</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="양산BOM" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="양산BOM" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="양산BOM" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="양산BOM" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">판매가</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="판매가" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="판매가" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="판매가" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="판매가" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">구매가</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="구매가" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="구매가" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="구매가" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="구매가" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">임가공가</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="임가공가" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="임가공가" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="임가공가" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="임가공가" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">포장사양</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="포장사양" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="포장사양" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="포장사양" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="포장사양" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    <tr>
							                        <td class="tdwhiteM" align="center">생산처</td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="생산처" data-sheetdivision="P1" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="생산처" data-sheetdivision="P1" data-type="basis">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="생산처" data-sheetdivision="P2" data-type="desision">
							                        </td>
							                        <td class="tdwhiteM" id="category" name="category"  data-classification="생산처" data-sheetdivision="P2" data-type="basis">
							                        </td>
							                    </tr>
							                    
							                </tbody>
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
        <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
    </form>
</body>
</html>
