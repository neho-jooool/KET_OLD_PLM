<%@page import="java.math.BigDecimal"%>
<%@page import="ext.ket.cost.util.CostCalculateUtil"%>
<%@page import="e3ps.project.sap.MoldPriceData"%>
<%@page import="e3ps.project.sap.SAPMoldPrice"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
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

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

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
  String popup = StringUtil.checkNull( request.getParameter("popup") );
  ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
  String projectNumber = project.getPjtNo();
%>

<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/multicombo.jsp"%>
<style type="text/css">
<!--
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">
$(document).ready(function(){
	
	const projectNo = '<%=projectNumber%>';
	
	$('#exepenseBtn').click(function(){
		
		var url = "/plm/ext/project/product/projectExpenseForm.do?pjtNo="+projectNo;
		
	    getOpenWindow2(url, 'expense'+projectNo, 1300, 800);
    });
	
	window.numberWithCommas = function(num){
		var parts = num.toString().split("."); 
        return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1] : "");
	}
	
	window.removeString = function(key){
		var str = $('#'+key).text();
		var regExp = /[r\n\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;
        return str.replace(/ /gi, "").replace(regExp,'');
    }

	var param = {
			pjtNo : projectNo,
			amt01 : removeString('amt01'),
			amt07 : removeString('amt07'),
			amt06 : removeString('amt06'),
			totalAmt01 : removeString('totalAmt01'),
			totalAmt07 : removeString('totalAmt07'),
			totalAmt06 : removeString('totalAmt06')
    }
	
	ajaxCallServer("/plm/ext/project/product/getExpenseTotalByProject", param, function(data){
		//amt01 : 경비 예산총합 , amt07 : 경비 집행 실적, amt06 : 경비 잔액
		for (var key in data) {
			
	    	const elem = $('#'+key);
	    	if (elem.length) {
	    		const value = data[key];
	    		if(value == 0){
	    			continue;
	    		}
	    		if(value < 0){
	    			elem.html("<span class=red>"+numberWithCommas(data[key])+"</>");//경비 예산총합
	    		}else{
	    			elem.html(numberWithCommas(data[key]));//경비 예산총합	
	    		}
	    	}
        }
    });
	
});
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

  function updateCostInfo(){
    width = 890;
    height = 700;
    var screenWidth = (screen.availWidth-width)/2;
    var screenHeight = (screen.availHeight-height)/2;
    var url = "/plm/jsp/project/UpdateCostInfo.jsp?oid=<%=oid%>";
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=1,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(890,700);
    newWin.focus();
  }
  
  function viewMoldProject(oid){
      openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
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
<%@include file="/jsp/common/processing.html"%>
    <input type='hidden' name='popup' value="<%=popup%>">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
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
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                    href="ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></td>
                                                <td><img src="/plm/portal/images/tab_5.png"></td>
                                            </tr>
                                        </table></td>
                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                    href="ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></a></td>
                                                <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                        </table></td>
                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                    href="ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                        </table></td>
                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_1.png"></td>
                                                <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640")%><%--비용--%></td>
                                                <td><img src="/plm/portal/images/tab_2.png""></td>
                                            </tr>
                                        </table></td>
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
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                    href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09500") %><%--평가관리--%></a></td>
                                                <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                        </table></td>
                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                    href="ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03034")%><%--프로그램--%></a></td>
                                                <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                        </table></td>
<%--                                     <%if(CommonUtil.isAdmin() || CommonUtil.isMember("원가_임원")){%>
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
                        <td width="10" height="10"><img src="../../portal/images/box_9.gif"></td>
                        <td height="10" background="../../portal/images/box_14.gif"></td>
                        <td width="10" height="10"><img src="../../portal/images/box_10.gif""></td>
                    </tr>
                    <tr>
                        <td width="10" background="../../portal/images/box_13.gif">&nbsp;</td>
                        <td valign="top">
                            <!--내용--> <%
                                 int pressCount = 0;
                                 int moldCount = 0;
                                 int handmadeCount = 0;
                                 int qdmCount = 0;
                                 int gCount = 0;
                            
                                 QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject) project);
                                 while (rt.hasMoreElements()) {
                             		Object[] obj = (Object[]) rt.nextElement();
                             		MoldItemInfo miInfo = (MoldItemInfo) obj[0];
                            
                             		if ("Press".equals(miInfo.getItemType()))
                             		    pressCount++;
                             		else if ("Mold".equals(miInfo.getItemType()))
                             		    moldCount++;
                             		else if ("Handmade".equals(miInfo.getItemType()))
                             		    handmadeCount++;
                             		else if ("QDM".equals(miInfo.getItemType()))
                             		    qdmCount++;
                             		else if ("구매품".equals(miInfo.getItemType()))
                             		    gCount++;
                                 }
                             %>
                            <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                <tr>
                                    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00283")%><%--Item 현황--%></td>
                                                <td align="right">&nbsp;</td>
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
                                                <td width="195" class="tdblueM">Press</td>
                                                <td width="195" class="tdblueM">Mold</td>
                                                <td width="195" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00966")%><%--구매품--%></td>
                                                <td width="195" class="tdblueM0">Total</td>
                                            </tr>
                                            <tr>
                                                <td width="195" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00581", pressCount)%><%--{0}개--%></td>
                                                <td width="195" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00581", moldCount)%><%--{0}개--%></td>
                                                <td width="195" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00581", gCount)%><%--{0}개--%></td>
                                                <td width="195" class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00581", (pressCount + moldCount + handmadeCount + qdmCount + gCount))%><%--{0}개--%></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td class="space15"></td>
                                            </tr>
                                        </table>
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01644")%><%--비용관리(세부내용)--%></td>
                                                <td align="right">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td align="right" valign="bottom"><%=messageService.getString("e3ps.message.ket_message", "01196")%><%--단위 : 천원--%></td>

                                                            <%
                                                                
                                                                if (auth.isLatest && !auth.isCompleted && (CommonUtil.isAdmin() || auth.isPM || CommonUtil.isMember("자동차사업부_기획") || (auth.isCarBuse && auth.isPMO))) {
                                                            %>
                                                            <td width="10">&nbsp;</td>
                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                            <td width="80" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                href="#" onClick="javascript:updateCostInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01650")%><%--비용정보 수정--%></a></td>
                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            <%
                                                                }
                                                            %>

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
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td class="tab_btm2"></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                            <tr>
                                                <td width="20px" class="tdblueM" rowspan=2>No</td>
                                                <td width="35px" class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                                <td width="60px" class="tdblueM" rowspan=2>Part No</td>
                                                <td width="60px" class="tdblueM" rowspan=2>Die No</td>
                                                <td width="90px" class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02942")%><%--투자명--%></td>
                                                <td width="50px" class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02944")%><%--투자오더--%></td>
                                                <td class="tdblueM" colspan=3><%=messageService.getString("e3ps.message.ket_message", "02143")%><%--예산--%></td>
                                                <td class="tdblueM" colspan=4><%=messageService.getString("e3ps.message.ket_message", "09495")%><%--발주실적 --%></td>
                                                <td class="tdblueM" colspan=2><%=messageService.getString("e3ps.message.ket_message", "09496")%><%--집행실적--%></td>
                                                <td class="tdblueM" rowspan=2>환율<br><span style="font-size : 10px">(<%=messageService.getString("e3ps.message.ket_message", "01195") %><%--단위 : 원--%>)</span></td>
                                            </tr>
                                            <tr>
                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "09498")%><%--초기--%></td>
                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "09497")%><%--증액/삭감--%></td>
                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834")%><%--총합(A)--%>(A)</td>
                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07215")%><%--금형제작--%></td>
                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01339")%><%--금형디버깅--%></td>
                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834")%><%--총합(B)--%>(B)</td>
                                                <td class="tdorgM"><%=messageService.getString("e3ps.message.ket_message", "02443")%><%--잔액--%><BR>(A-B)</td>
                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834")%><%--총합--%>(C)</td>
                                                <td width="60px" class="tdorgM"><%=messageService.getString("e3ps.message.ket_message", "02443")%><%--잔액--%><BR>(A-C)</td>
                                            </tr>
                                            <%
                                                
                                            
                                                long targetCost1 = 0;
                                                long targetCost2 = 0;
                                                long targetCost3 = 0;
                                                long targetCost4 = 0;
                                                long budget1 = 0;
                                                long budget2 = 0;
                                                long budget3 = 0;
                                                long budget4 = 0;
                                                long resultsCost1 = 0;
                                                long resultsCost2 = 0;
                                                long resultsCost3 = 0;
                                                long resultsCost4 = 0;
                                                long balanceCost1 = 0;
                                                long balanceCost2 = 0;
                                                long balanceCost3 = 0;
                                                long balanceCost4 = 0;
                                                
                                                
                                                long baljuPrice = 0; //금형 발주 실적
                                                long baljuPrice1 = 0; //설비 발주 실적
                                                long baljuPrice2 = 0; //JIG 발주 실적
                                                long baljuPrice3 = 0; //경비 발주 실적 (경비가 투자오더가 있는지 모르겠지만 있으면 가지고 오도록..)
                                                long baljuPriceTotal = 0; //발주 실적 총합
                                                
                                                long baljuBalance = 0; //금형 발주 잔액
                                                long baljuBalance1 = 0; //설비 발주 잔액
                                                long baljuBalance2 = 0; // JIG 발주 잔액
                                                long baljuBalance3 = 0; //경비 발주 잔액
                                                long baljuBalanceTotal = 0; //발주 잔액 총합

                                                java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
                                                int count = 0;
                                                QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
                                                
                                                
                                                while (rtCost.hasMoreElements()) {
                                                    
                                                    Vector priceV = null;
                                                    String initmoldTotalPrice = "0";
                                                    String debugingTotalPrice = "0";
                                                    String moldTotalPrice = "0";
                                                    String balancePrice = "0";
                                                    String orderNumber = "";
                                                    String mOid = "";
                                                    boolean isTotal = false;
                                                    boolean isOrderPrcMinus = false;
                                                    
                                            		Object[] obj = (Object[]) rtCost.nextElement();
                                            		CostInfo costInfo = (CostInfo) obj[0];
                                            		    
                                            		long budget = 0; //예산
                                            		long executionCost = 0; //초기집행가
                                            		long editCost = 0; //추가집행가
                                            		long totalExpense = 0; //총집행가
                                            		long balanceCost = 0; //잔액
                                            		long addPrice = 0;//증액/삭감
                                            		long initPrice = 0; //초기예산
                                            		long tempBalancePrice = 0;
                                            		String exchange = "";
                                            		String currency = "";
                                            		if ("경비".equals(costInfo.getCostType())) {//ERP에서 실시간 조회하는 것으로 변경함 2021-04-11
                                            		    continue;
                                            		}
                                            		if (costInfo.getOrderInvest() != null) {
                                            		    
                                            		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest(),costInfo.getCostType());
                                            		    
                                            		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
                                            		    Long addPriceObj = (Long) datas.get(ProductPrice.ADDPRICE);
                                            		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
                                            		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
                                            		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);
                                            		    Long initPriceObj = (Long) datas.get(ProductPrice.INITPRICE);
                                            		    exchange = (String)datas.get(ProductPrice.EXCHANGE);
                                            		    if("KRW".equals(exchange)){
                                            		        exchange = "";
                                            		    }
                                            		    if("금형(로컬)".equals(costInfo.getCostType()) && StringUtils.isNotEmpty(exchange) ){

                                            			    Double doubleVal = Math.floor(Double.parseDouble(exchange)*100)/100.0;
                                                            exchange = doubleVal.toString();
                                                            currency = (String)datas.get(ProductPrice.CURRENCY);
                                            		    }else{
                                            			    exchange = "";
                                                            currency = "";
                                            		    }
                                            		    
                                            		     

                                            		    if (totalPriceObj != null)
                                            			budget = totalPriceObj.longValue();
                                            		    if (initExpenseObj != null)
                                            			executionCost = initExpenseObj.longValue(); //초기집행가
                                            		    if (addExpenseObj != null)
                                            			editCost = addExpenseObj.longValue(); //추가집행가
                                            		    if (totalExpenseObj != null)
                                            			totalExpense = totalExpenseObj.longValue(); //총집행가
                                            		    balanceCost = budget - totalExpense; //잔액
                                            		    
                                            		    if (addExpenseObj != null)
                                            			addPrice = addPriceObj.longValue(); //증액/삭감
                                            			
                                            			if (addExpenseObj != null)
                                            			initPrice = initPriceObj.longValue(); //초기예산

                                            			orderNumber = costInfo.getOrderInvest();
                                            			MoldProject mProject = null;
                                            			
                                            			if(StringUtils.isNotEmpty(costInfo.getDieNo()) &&  !"-".equals(costInfo.getDieNo()) ){//금형 투자오더에 대한 발주실적 가져오는 부분
                                            			    mProject = MoldProjectHelper.getMoldProject(costInfo.getDieNo());
                                            			    mOid = CommonUtil.getOIDString(mProject); 
                                            			    if(mProject != null || "금형(로컬)".equals(costInfo.getCostType())){
                                            				    Integer integer = null;
                                                                Vector debugDatas = new Vector();
                                                                
                                                                if(mProject != null){
                                                                    debugDatas = MoldProjectHelper.getDebugingTasks(mProject);    
                                                                }
                                                                
                                                                            
                                                                priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, mProject, debugDatas);
                                                                int initMold = 0;
                                                            
                                                                if (priceV.size() > 0) {
                                                                    Hashtable hhh = (Hashtable) priceV.get(0);
                                                                    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);
                                                               
	                                                                if (integer != null) {
	                                                                    initMold = integer.intValue();
	                                                                    if("금형(로컬)".equals(costInfo.getCostType())){
	                                                                	    if(StringUtils.isNotEmpty(exchange)){
	                                                                		    Double doubleVal = Double.parseDouble(integer.toString()) * Double.parseDouble(exchange);//금형(로컬) 인 경우 환율 환산
	                                                                            BigDecimal b = new BigDecimal(doubleVal.toString());
	                                                                            initMold = new Integer(b.intValue());
	                                                                	    }
	                                                                	    
	                                                                    }
	                                                                }
                                                                }
                                                            
                                                                initmoldTotalPrice = nf.format(initMold / 1000);

                                                                int debugingtotal = 0;
                                                                
                                                                for (int i = 1; i < priceV.size(); i++) {
                                                                    Hashtable hhh = (Hashtable) priceV.get(i);
                                                                    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

                                                                    if (integer != null) {
                                                                	    if("금형(로컬)".equals(costInfo.getCostType()) && StringUtils.isNotEmpty(exchange)){
                                                                		    Double doubleVal = Double.parseDouble(integer.toString()) * Double.parseDouble(exchange);//금형(로컬) 인 경우 환율 환산
                                                                            BigDecimal b = new BigDecimal(doubleVal.toString());
                                                                            integer = new Integer(b.intValue());
                                                                        }
                                                                	    debugingtotal += integer.intValue();
                                                                    }
                                                                }
                                                            
                                                            
                                                                debugingTotalPrice = nf.format(debugingtotal/1000);
                                                                moldTotalPrice = nf.format((initMold + debugingtotal) / 1000);
                                                                
                                                                baljuPrice = baljuPrice+initMold + debugingtotal; 
                                                            
                                                                if ((initMold + debugingtotal) > ((Long) datas.get(ProductPrice.TOTALPRICE)).intValue()) {
                                                                    isTotal = true;
                                                                }
                                                                
                                                                tempBalancePrice = ( (Long) datas.get(ProductPrice.TOTALPRICE) - (initMold + debugingtotal) ) /1000;
                                                                
                                                                balancePrice = nf.format(tempBalancePrice);
                                                                
                                                                baljuBalance = baljuBalance + ( (Long) datas.get(ProductPrice.TOTALPRICE) - (initMold + debugingtotal) );
                                                            }
                                                        }
                                            			
                                            			if(mProject == null && !"금형(로컬)".equals(costInfo.getCostType()) ){//금형이 아닌 경우 발주실적을 가져오는 부분 
                                            			    Long price = SAPMoldPrice.totalPrice(orderNumber, "구매발주가");
                                            			    moldTotalPrice = nf.format(price / 1000);
                                            			    
                                            			    tempBalancePrice = ( (Long) datas.get(ProductPrice.TOTALPRICE) - (price) ) /1000; 
                                            				    
                                            			    balancePrice = nf.format(tempBalancePrice);
                                            			    
                                            			    if ("금형".equals(costInfo.getCostType())) {
                                            				   baljuPrice = baljuPrice + price;
                                            				   baljuBalance = ((Long) datas.get(ProductPrice.TOTALPRICE) - (price)) + baljuBalance;
                                            			    } else if ("설비".equals(costInfo.getCostType())) {
                                            			       baljuPrice1 = baljuPrice1 + price;
                                            			       baljuBalance1 = baljuBalance1 + ((Long) datas.get(ProductPrice.TOTALPRICE) - price);
                                                            } else if ("JIG".equals(costInfo.getCostType())) {
                                                        	   baljuPrice2 = baljuPrice2 + price;
                                                               baljuBalance2 = baljuBalance2 + ((Long) datas.get(ProductPrice.TOTALPRICE) - price);
                                                            }else {
                                                        	   baljuPrice3 = baljuPrice3 + price;
                                                               baljuBalance3 = baljuBalance3 + ((Long) datas.get(ProductPrice.TOTALPRICE) - price);
                                                            }
                                            			}
                                            			
                                            			if(tempBalancePrice < 0){
                                            			    isOrderPrcMinus = true;
                                            			}
                                            			
                                            		} else {
                                            		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                                            			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); //예산

                                            		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
                                            			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); //초기집행가
                                            		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                                            			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); //초기집행가

                                            		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
                                            			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); //추가집행가

                                            		    totalExpense = executionCost + editCost; //총집행가
                                            		    balanceCost = budget - totalExpense; //잔액
                                            		}
                                            		
                                            		if(!costInfo.getCostType().equals("금형(로컬)") && costInfo.getCostType().endsWith("(로컬)") ){
                                            		    
                                            		    initPrice = Integer.parseInt(StringUtil.checkNullZero(costInfo.getTargetCost()).replaceAll(",", ""));
                                            		    addPrice =  Integer.parseInt(StringUtil.checkNullZero(costInfo.getEditCost()).replaceAll(",", "")); //비용관리(세부내용) - 예산(증액/삭감)
                                            		    budget = initPrice + addPrice; //비용관리(세부내용) - 예산 - 총합(A)
                                            		    totalExpense = Integer.parseInt(StringUtil.checkNullZero(costInfo.getDecideCost()).replaceAll(",", "")); //비용관리(세부내용) - 집행실적 - 총합(C)
                                            		    balanceCost = budget - totalExpense; //비용관리(세부내용) - 집행실적 - 잔액(A-C)
                                            		
/*                                             		    if(costInfo.getCostType().equals("금형(로컬)")){
                                                            
                                                            budget1 = budget1 + budget;//비용관리(종합) - 예산총합(금형)
                                                            
                                                            baljuBalance = budget1 - baljuPrice;//비용관리(종합) - 발주 잔액 (금형)

                                                            resultsCost1 = resultsCost1 + totalExpense;   //비용관리(종합) - 집행실적 - 잔액(A-C)
                                                            balanceCost1 = balanceCost1 + balanceCost; //비용관리(세부내용) - 집행실적 - 잔액(A-C)
                                            		    } */
                                            		    if(costInfo.getCostType().equals("설비(로컬)")){

                                                            budget2 = budget2 + budget;//비용관리(종합) - 예산총합(설비)
                                                                
                                                            baljuBalance1 = budget2 - baljuPrice1;//비용관리(종합) - 발주 잔액 (설비)
                                                            
                                                            resultsCost2 = resultsCost2 + totalExpense;   //비용관리(종합) - 집행실적 - 잔액(A-C)
                                                            balanceCost2 = balanceCost2 + balanceCost; //비용관리(세부내용) - 집행실적 - 잔액(A-C)
                                                        }
                                            		    if(costInfo.getCostType().equals("JIG(로컬)")){

                                                            budget4 = budget4 + budget;//비용관리(종합) - 예산총합(JIG)
                                                                            
                                                            baljuBalance2 = budget4 - baljuPrice2;//비용관리(종합) - 발주 잔액 (JIG)
                                                            
                                                            resultsCost4 = resultsCost4 + totalExpense;   //비용관리(종합) - 집행실적 - 잔액(A-C)
                                                            balanceCost4 = balanceCost4 + balanceCost; //비용관리(세부내용) - 집행실적 - 잔액(A-C)
                                                        }
                                            		    if(costInfo.getCostType().equals("경비(로컬)")){

                                                            budget3 = budget3 + budget;//비용관리(종합) - 예산총합(경비)
                                                                        
                                                            baljuBalance3 = budget3 - baljuPrice3;//비용관리(종합) - 발주 잔액 (경비)
                                                            
                                                            resultsCost3 = resultsCost3 + totalExpense;   //비용관리(종합) - 집행실적 - 잔액(A-C)
                                                            balanceCost3 = balanceCost3 + balanceCost; //비용관리(세부내용) - 집행실적 - 잔액(A-C)
                                                        }
                                            		    
                                                    }else{
                                                	   if ("금형".equals(costInfo.getCostType()) || costInfo.getCostType().equals("금형(로컬)") ) {
                                                            if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                                                            targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
                                                            budget1 = budget1 + budget;
                                                            resultsCost1 = resultsCost1 + executionCost + editCost;
                                                            balanceCost1 = balanceCost1 + balanceCost;
                                                        } else if ("설비".equals(costInfo.getCostType())) {
                                                            if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                                                            targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
                                                            budget2 = budget2 + budget;
                                                            resultsCost2 = resultsCost2 + executionCost + editCost;
                                                            balanceCost2 = balanceCost2 + balanceCost;
                                                        } else if ("JIG".equals(costInfo.getCostType())) {
                                                            if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                                                            targetCost4 = targetCost4 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
                                                            budget4 = budget4 + budget;
                                                            resultsCost4 = resultsCost4 + executionCost + editCost;
                                                            balanceCost4 = balanceCost4 + balanceCost;
                                                        } else{
                                                            if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                                                            targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
                                                            budget3 = budget3 + budget;
                                                            resultsCost3 = resultsCost3 + executionCost + editCost;
                                                            balanceCost3 = balanceCost3 + balanceCost;
                                                        }
                                                    }

                                            		
                                            %>
                                            <tr>
                                                <td class="tdwhiteM"><%=++count%></td>
                                                <td class="tdwhiteM"><%=costInfo.getCostType()%></td>
                                                <td class="tdwhiteM"
                                                    title="<%=costInfo.getPartNo() != null ? costInfo.getPartNo() : "&nbsp;"%>"><div
                                                        style="width: 60; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                        <nobr><%=costInfo.getPartNo() != null ? costInfo.getPartNo() : "&nbsp;"%></nobr>
                                                    </div></td>
                                                <td class="tdwhiteM" title="<%=costInfo.getDieNo() != null ? costInfo.getDieNo() : "&nbsp;"%>">
                                                    <div style="width: 60; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                    <nobr>
                                                    
                                                    
                                                    <%if(StringUtil.checkString(mOid)){%>
                                                                <a href="javascript:;" onClick="javascript:viewMoldProject('<%=mOid%>')">
                                                                <%} %><%=costInfo.getDieNo() != null ? costInfo.getDieNo() : "&nbsp;"%>
                                                                    <%if(StringUtil.checkString(costInfo.getDieNo())){%>
                                                                </a>
                                                                <%} %>
                                                                
                                                    
                                                    </nobr>
                                                    </div>
                                                </td>
                                                <td class="tdwhiteL"
                                                    title="<%=costInfo.getCostName() != null ? costInfo.getCostName() : "&nbsp;"%>">
                                                    <div
                                                        style="width: 85; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                        <nobr>
                                                            <%=costInfo.getCostName() != null ? costInfo.getCostName() : "&nbsp;"%>
                                                        </nobr>
                                                    </div>
                                                </td>
                                                <td class="tdwhiteR"><%=costInfo.getOrderInvest() != null ? costInfo.getOrderInvest() : "-"%></td>
                                                <td class="tdwhiteR"><%=nf.format(initPrice / 1000)%></td>
                                                <td class="tdwhiteR"><%=nf.format(addPrice / 1000)%></td>
                                                <td class="tdwhiteR"><%=nf.format(budget / 1000)%></td>
                                                
                                                <td class="<%="0".equals(initmoldTotalPrice)?"tdwhiteM":"tdwhiteR"%>"><%=initmoldTotalPrice%></td>
                                                    <td class="<%="0".equals(debugingTotalPrice)?"tdwhiteM":"tdwhiteR"%>"><%=debugingTotalPrice%></td>
                                                    <td class="<%="0".equals(moldTotalPrice)?"tdwhiteM":"tdwhiteR"%>"><%=moldTotalPrice%>
<%--                                                         <%
                                                            if (isTotal) {
                                                        %><font color=red>
                                                            <%
                                                                }
                                                            %><%=moldTotalPrice%>
                                                            <%
                                                                if (isTotal) {
                                                            %>
                                                    </font>
                                                        <%
                                                            }
                                                        %> --%>
                                                    </td>
                                                    <td class="<%="0".equals(balancePrice)?"tdwhiteM":"tdwhiteR"%>"><%if(isOrderPrcMinus){ %><font color=red><%}%><%=balancePrice%><%if(isOrderPrcMinus){ %></font><%} %></td>
                                                
                                                <td class="tdwhiteR"><%=nf.format(totalExpense / 1000)%></td>
                                                <td class="tdwhiteR">
                                                    <%
                                                        if (balanceCost < 0) {
                                                    %><span class="red">
                                                        <%
                                                            }
                                                        %><%=nf.format(balanceCost / 1000)%>
                                                        <%if(balanceCost < 0) {%>
                                                </span>
                                                    <%}%>
                                                </td>
                                                <td class="tdwhiteL" ><%=currency%><br><%=exchange%></td>
                                            </tr>
                                            <%
                                            }
                                            %>
                                        </table>
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td class="space15"></td>
                                            </tr>
                                        </table>
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01645") %><%--비용관리(종합)--%></td>
                                                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "01195") %><%--단위 : 원--%></td>
                                                <td width="10">&nbsp;</td>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td width="50" class="btn_blue" background="../../portal/images/btn_bg1.gif">
                                                <a href="#" class="btn_blue" id='exepenseBtn'>경비 상세</td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
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
                                                <td class="space5"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                        <tr>
                                                            <td width="156" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                                            <td width="156" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02143") %>(<%=messageService.getString("e3ps.message.ket_message", "02834") %>)<%--예산(총합)--%></td>
                                                            <td width="156" class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "09544")%><%--발주 --%></td>
                                                            <td width="156" class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "02742")%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="78" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032")%><%--실적--%></td>
                                                            <td width="78" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02443")%><%--잔액--%></td>
                                                            <td width="78" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032")%><%--실적--%></td>
                                                            <td width="78" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02443")%><%--잔액--%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(budget1)%></td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(baljuPrice)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                            <%if(baljuBalance < 0) {%><span class="red">
                                                                    <%}%> <%=nf.format(baljuBalance)%> <%if(baljuBalance < 0) {%>
                                                            </span>
                                                                <%}%>
                                                            </td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(resultsCost1)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                            <%if(balanceCost1 < 0) {%><span class="red">
                                                                <%}%> <%=nf.format(balanceCost1)%> <%if(balanceCost1 < 0) {%>
                                                            </span>
                                                            <%}%>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01874") %><%--설비--%></td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(budget2)%></td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(baljuPrice1)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                            <%if(baljuBalance1 < 0) {%><span class="red">
                                                                    <%}%> <%=nf.format(baljuBalance1)%> <%if(baljuBalance1 < 0) {%>
                                                            </span>
                                                                <%}%>
                                                            </td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(resultsCost2)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                            <%if(balanceCost2 < 0) {%><span class="red">
                                                                <%}%> <%=nf.format(balanceCost2)%> <%if(balanceCost2 < 0) {%>
                                                            </span>
                                                            <%}%>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="156" class="tdblueM">JIG</td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(budget4)%></td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(baljuPrice2)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                            <%if(baljuBalance2 < 0) {%><span class="red">
                                                                    <%}%> <%=nf.format(baljuBalance2)%> <%if(baljuBalance2 < 0) {%>
                                                            </span>
                                                                <%}%>
                                                            </td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(resultsCost4)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                            <%if(balanceCost4 < 0) {%><span class="red">
                                                                <%}%> <%=nf.format(balanceCost4)%> <%if(balanceCost4 < 0) {%>
                                                            </span>
                                                            <%}%>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00796") %><%--경비--%></td>
                                                            <td width="156" class="tdwhiteR" id="amt01"><%=nf.format(budget3)%></td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(baljuPrice3)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                            <%if(baljuBalance3 < 0) {%><span class="red">
                                                                    <%}%> <%=nf.format(baljuBalance3)%> <%if(baljuBalance3 < 0) {%>
                                                            </span>
                                                                <%}%>
                                                            </td>
                                                            <td width="156" class="tdwhiteR" id="amt07"><%=nf.format(resultsCost3)%></td>
                                                            <td width="156" class="tdwhiteR" id="amt06">
                                                            <%if(balanceCost3 < 0) {%><span class="red">
                                                                <%}%> <%=nf.format(balanceCost3)%> <%if(balanceCost3 < 0) {%>
                                                            </span>
                                                            <%}%>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="156" class="tdblueM">Total</td>
                                                            <td width="156" class="tdwhiteR"  id="totalAmt01"><%=nf.format(budget1+budget2+budget3+budget4)%></td>
                                                            <td width="156" class="tdwhiteR"><%=nf.format(baljuPrice+baljuPrice1+baljuPrice2+baljuPrice3)%></td>
                                                            <td width="156" class="tdwhiteR">
                                                                <%if(baljuBalance+baljuBalance1+baljuBalance2+baljuBalance3 < 0) {%><span
                                                                class="red">
                                                                    <%}%> <%=nf.format(baljuBalance+baljuBalance1+baljuBalance2+baljuBalance3)%>
                                                                    <%if(baljuBalance+baljuBalance1+baljuBalance2+baljuBalance3 < 0) {%>
                                                            </span>
                                                                <%}%>
                                                            </td>
                                                            <td width="156" class="tdwhiteR" id="totalAmt07"><%=nf.format(resultsCost1+resultsCost2+resultsCost3+resultsCost4)%></td>
                                                            <td width="156" class="tdwhiteR" id="totalAmt06">
                                                                <%if(balanceCost1+balanceCost2+balanceCost3+balanceCost4 < 0) {%><span
                                                                class="red">
                                                                    <%}%> <%=nf.format(balanceCost1+balanceCost2+balanceCost3+balanceCost4)%>
                                                                    <%if(balanceCost1+balanceCost2+balanceCost3+balanceCost4 < 0) {%>
                                                            </span>
                                                                <%}%>
                                                            </td>
                                                        </tr>
                                                    </table></td>
                                            </tr>
                                        </table></td>
                                </tr>
                            </table>
                        </td>
                        <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
                    </tr>
                    <tr>
                        <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
                        <td height="10" background="../../portal/images/box_16.gif"></td>
                        <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
                    </tr>
                </table></td>
        </tr>
    </table>
</body>
</html>
