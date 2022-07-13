function openView(oid){
		var url = "";
	if( oid.indexOf("KETBomHeader") > 0)
	{
		oid = oid.replace("e3ps.bom.entity.KETBomHeader:","");
		url = "/plm/jsp/bom/WFBomView.jsp?oid="+oid;
		popup(url,"1100","800");
	}
	else if(oid.indexOf("KETBomEcoHeader")>0)
	{
		oid = oid.replace("e3ps.bom.entity.KETBomEcoHeader:","");
		url = "/plm/jsp/bom/WFBomEcoView.jsp?oid="+oid;
		popup(url,"1100","800");
	}
	else if( oid.indexOf("KETProjectDocument") > 0)
	{
		url = "/plm/jsp/dms/ViewDocumentPop.jsp?isRefresh=N&oid="+oid;
		popup(url,"800","640");
	}
	else if( oid.indexOf("KETTechnicalDocument") > 0)
	{
		url = "/plm/jsp/dms/ViewTechDocumentPop.jsp?isRefresh=N&oid="+oid;
		popup(url,"830","600");
	}
	else if( oid.indexOf("KETDevelopmentRequest") > 0)
	{
		url = "/plm/jsp/dms/ViewDevRequestPop.jsp?isRefresh=N&oid="+oid;
		popup(url,"830","800");
	}
	else if( oid.indexOf("KETMoldChangeOrder") > 0)
	{
		url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid="+oid;
		popup(url,"800","680");
	}
	else if( oid.indexOf("KETMoldChangeRequest") > 0)
	{
		url = "/plm/servlet/e3ps/MoldEcrServlet?cmd=popupview&oid="+oid;
		popup(url,"1024","768");
	}
	else if( oid.indexOf("KETProdChangeOrder") > 0)
	{
		url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid="+oid;
		popup(url,"800","680");
	}
	else if( oid.indexOf("KETProdChangeRequest") > 0)
	{
		url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid="+oid;
		popup(url,"1024","768");
	}
	/*
	 * ECR - 주관부서, 회의록
	 */
	else if( oid.indexOf("KETCompetentDepartment") > 0 || oid.indexOf("KETMeetingMinutes") > 0)
	{
		url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid="+oid;
		popup(url,"1024","768");
	}
	else if( oid.indexOf("EPMDocument") > 0)
	{
		url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid="+oid;
		popup(url,"820","800");
	}
	else if( oid.indexOf("KETWfmMultiApprovalRequest") > 0)
	{
		url = "/plm/jsp/wfm/ViewMultiApprovalPopup.jsp?oid="+oid;
		popup(url,"790","640");
	}
	else if( oid.indexOf("KETEarlyWarningPlan") > 0)
	{
		url = "/plm/jsp/ews/ViewEarlyWarningPlanPopup.jsp?planOid="+oid;
		popup(url, "800", "600");
	}
	else if( oid.indexOf("KETEarlyWarningEndReq") > 0)
	{
		url = "/plm/jsp/ews/ViewEarlyWarningEndReqPopup.jsp?endReqOid="+oid;
		popup(url,"830","600");
	}
	else if( oid.indexOf("KETEarlyWarningEnd") > 0)
	{
		url = "/plm/jsp/ews/ViewEarlyWarningEndPopup.jsp?endOid="+oid;
		popup(url, "830","600");
	}
	else if( oid.indexOf("KETEarlyWarning") > 0)
	{
		url = "/plm/jsp/ews/ViewEarlyWarningPopup.jsp?oid="+oid;
		popup(url, "800", "600");
	}
	else if( oid.indexOf("Performance") > 0)
	{
		url = "/plm/jsp/project/performance/ViewPerformance.jsp?cmd=view&pOid="+oid;
		popup(url, "850", "730");
	}
	else if( oid.indexOf("ReviewProject")>0)
	{
	    // [START] [PLM System 1차개선] Project 일정 조회 화면 표시, 2013-08-08, BoLee
	    var screenWidth = screen.availWidth;
	    var screenHeight = screen.availHeight;
	    url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&cmd=view&width=" + screenWidth + "&height=" + screenHeight;
	    openOtherName(url, "ViewProjectSchedule", screenWidth, screenHeight, "status=no,scrollbars=no,resizable=no");
        // [END] [PLM System 1차개선] Project 일정 조회 화면 표시, 2013-08-08, BoLee
	}
	else if( oid.indexOf("ProductProject")>0)
	{
        // [START] [PLM System 1차개선] Project 일정 조회 화면 표시, 2013-08-08, BoLee
        var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&cmd=view&width=" + screenWidth + "&height=" + screenHeight;
        openOtherName(url, "ViewProjectSchedule", screenWidth, screenHeight, "status=no,scrollbars=no,resizable=no");
        // [END] [PLM System 1차개선] Project 일정 조회 화면 표시, 2013-08-08, BoLee
	}
	else if( oid.indexOf("MoldProject")>0)
	{
        // [START] [PLM System 1차개선] Project 일정 조회 화면 표시, 2013-08-08, BoLee
        var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&cmd=view&width=" + screenWidth + "&height=" + screenHeight;
        openOtherName(url, "ViewProjectSchedule", screenWidth, screenHeight, "status=no,scrollbars=no,resizable=no");
        // [END] [PLM System 1차개선] Project 일정 조회 화면 표시, 2013-08-08, BoLee
	}
	else if( oid.indexOf("KETDqmRaise")>0)
	{
		url = '/plm/ext/dqm/dqmMainForm.do?type=raise&oid='+oid;
		popup(url, "1024", "768");
        // [END] [PLM System 1차개선] Project 일정 조회 화면 표시, 2013-08-08, BoLee
	}
	else if( oid.indexOf("KETDqmAction")>0)
	{
		url = '/plm/ext/dqm/dqmMainForm.do?type=action&oid='+oid;
		popup(url, "1024", "768");
    }

	if(url != "") return;
	else return alert("뷰 페이지가 없는 문서입니다  \n 관리자에게 문의하세요");
}

function openView2(oid) {
	var url = "/plm/jsp/dms/ViewDocMultiApproval.jsp?oid\=" + oid;
	popup(url, "850", "650");
	if(url != "") return;
	else return alert("뷰 페이지가 없는 문서입니다  \n 관리자에게 문의하세요");
}

function popup(url, tWidth, tHeight){

	var features = eval("'width=" + tWidth + ",height=" + tHeight + ", scrollbars=1, resizable=0, status=1, toolbar=0 ,location=0'");
	window.open(url,"viewObject",features);

}
