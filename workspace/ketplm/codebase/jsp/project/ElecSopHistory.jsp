<%@page import="wt.query.SubSelectExpression"%>
<%@page import="wt.query.SQLFunction"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="e3ps.project.elecSop.CustomerSopHistory"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.elecSop.ProjectSopLink"%>
<%@page import="wt.query.QuerySpec"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,
                wt.org.*,
                wt.project.Role,
                wt.session.*,
                wt.team.*"%>
<%@page import="e3ps.common.code.*,
                e3ps.common.util.*,
                e3ps.common.web.*,
                e3ps.groupware.company.*,
                e3ps.common.jdf.config.Config,
                e3ps.common.jdf.config.ConfigImpl,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.project.outputtype.OEMProjectType"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
	String pjtno = request.getParameter("pjtno");
	E3PSProject project = ProjectHelper.manager.getProject(pjtno);
	
	String firstDate = "";
	String devProductModifyDate = "";
	
	if (project.getDevRequest() != null) {
	    firstDate = project.getDevRequest().getProductSaleFirstYear();
	    devProductModifyDate = DateUtil.getDateString(project.getDevRequest().getModifyTimestamp(), "date");
	}
	
	String oid = CommonUtil.getOIDString(project);
	ProjectMaster pjtMaster = project.getMaster();
	
	QuerySpec spec = new QuerySpec(); 
	int idx = spec.appendClassList(ProjectSopLink.class, true);
	spec.appendWhere(new SearchCondition(ProjectSopLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(pjtMaster) ), new int[] { idx });
	
	spec.appendOrderBy(new OrderBy(new ClassAttribute(ProjectSopLink.class, "thePersistInfo.updateStamp"), true), new int[] { idx });
	
	QueryResult qr = PersistenceHelper.manager.find(spec);
	
	E3PSProjectData projectData = new E3PSProjectData(project);
	String salesOid = projectData.salesOid;
	
	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	String userOid = CommonUtil.getOIDString(user);
	
	boolean isSalesRole = userOid.equals(salesOid);
	if(CommonUtil.isAdmin()){
	    isSalesRole = true;
	}
	
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE>고객SOP관리</TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
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
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/processing.html"%>
<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript">
<!--
	$(document).ready(function(){
	    CalendarUtil.dateInputFormat('sop');
	});
    //Create Function
    function onSave(){
    	
    	if(!CommonUtil.checkEsseOk()){ 
            return;
        }
    	
        if(confirm("SOP를 갱신하시겠습니까?")){
        	var form = document.forms[0];
            
            showProcessing();
            
            $.ajax({
                url : "/plm/jsp/project/ActionProductProject.jsp",
                type : "POST",
                data : $('[name=SopHistory]').serialize(),
                async : false,
                success: function(data) {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "01315")%>');
                    hideProcessing();
                },
                error : function(){
                    alert('<%=messageService.getString("e3ps.message.ket_message", "01317")%>');
                    hideProcessing();
                }
            });

            
            hideProcessing();
            parent.opener.location.href='/plm/jsp/project/ProjectView.jsp?oid='+form.oid.value;
            location.reload();
        }
        
    }
</script>

</head>
<body>
    <form name="SopHistory" method="post">
        <!-- hidden begin -->
        <input type="hidden" name="pjtno" value="<%=pjtno%>">
        <input type="hidden" name="oid" value="<%=oid%>"> 
        <input type="hidden" name="command" value="ElecSopCreate">
        <!-- hidden end -->
        
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top" height="43">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01">전자사업부 고객 SOP 관리</td>
                                    </tr>
                                </table>
                            </td>
                            
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                            
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="space5"></td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <%if(isSalesRole){ %>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">고객SOP 갱신</td>
                            <%} %>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td class="font_03"></td>
                                        <%if(isSalesRole){ %>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onClick="onSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310")%><%--등록--%></a>
                                        </td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <%} %>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:window.close();" class="btn_blue">닫기</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
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
                    
                    <%if(isSalesRole){ %>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="70" class="tdblueL">고객SOP<span class="red">*</span></td>
                            <td class="tdwhiteL0"  ><input type="text" name="sop" class="txt_field"
                                style="width: 100px"  esse='true' esseLabel='고객SOP'> <img src="/plm/portal/images/icon_delete.gif" border="0"
                                onclick="javascript:CommonUtil.deleteDateValue('planStartDate');" style="cursor: hand;"></td>
                        </tr>
                        <tr>
                            <td width="70" class="tdblueL">변경사유<span class="red">*</span></td>
                            <td class="tdwhiteL0"  ><input type="text" name="changeReason" class="txt_field" style="width: 100%" esse='true' esseLabel='변경사유'></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <%} %>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">SOP이력</td>
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
                    
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" id="templatetable">
                        <tr>
                            <td width="5" class="tdblueM">Rev</td>
                            <td width="40" class="tdblueM">(목표)고객SOP</td>
                            <td width="170" class="tdblueM">변경사유</td>
                            <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
                        </tr>
                        
                        <%
                        while(qr.hasMoreElements()){
                            Object[] obj = (Object[]) qr.nextElement();
                            ProjectSopLink link = (ProjectSopLink)obj[0];
                            CustomerSopHistory history = (CustomerSopHistory)link.getSop();
                            String sopDate = DateUtil.getDateString(history.getSopDate(), "date");
                            String modifyDate = DateUtil.getDateString(link.getModifyTimestamp(), "date");
                        %>
                        <tr>
                            <td width="5" class="tdwhiteM"><%=history.getRev() %></td>
                            <td width="40" class="tdwhiteM"><%=sopDate %></td>
                            <td width="170" class="tdwhiteM"><%=history.getChangeReason() %></td>
                            <td width="40" class="tdwhiteM"><%=modifyDate %></td>
                        </tr>
                        <%
                        }
                        %>
                        
                        <%if(!"".equals(firstDate)) {%>
                        <tr>
                            <td width="5" class="tdwhiteM">0</td>
                            <td width="40" class="tdwhiteM"><%=firstDate %></td>
                            <td width="170" class="tdwhiteM">최초작성(개발의뢰서의 고객SOP)</td>
                            <td width="40" class="tdwhiteM"><%=devProductModifyDate %></td>
                        </tr>
                        <%} %>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
</form>
</body>
</html>
