
function popup(fileName, intWidth, intHeight){//, intLeft, intTop, vScrollbars, vResizable, vStatus){

		var winname_1;
		var openF = 0;
        today = new Date();
        winName = today.getTime();

        var fileName, intWidth, intHeight;
        var screenWidth = screen.availwidth;
        var screenHeight = screen.availheight;

        if(intWidth >= screenWidth){
                intWidth = screenWidth - 40;
                vScrollbars = 1;
        }
        if(intHeight >= screenHeight){
                intHeight = screenHeight - 40;
                intWidth = intWidth + 20;
                vScrollbars = 1;
        }
        //if(intLeft == 'auto' || intTop == 'auto'){ 
                var intLeft = (screenWidth - intWidth) / 2;
                var intTop = (screenHeight - intHeight) / 2;
        //}
        var features = eval("'width=" + intWidth + ",height=" + intHeight + ",left=" + intLeft + ",top=" + intTop + ", scrollbars=1, resizable=1, status=1'");
        if(openF == 1){
                if(winname_1.closed){
                        winname_1 = window.open(fileName,winName,features);
                }else{
                        winname_1.close();
                        winname_1 = window.open(fileName,winName,features);
                }
        }else{
                winname_1 = window.open(fileName,winName,features);
                openF = 1;
        }
        winname_1.focus();
}


function openViewProject(oid, width, height, auth)
{
	if(width == null)
	{
		width = 'full';
		height = 'full';
	}
	var url = getViewURL(oid);
	
	if(url != "") 
	{
		if(auth != null){
			url += '&key=auth&value='+auth; 
			url += '&key=projectValue&value=true';
		}else{	
			url += '&key=projectValue&value=true';
		}
		
		popup(url, 900, 600);
		
	}
}


function openView(oid, width, height, auth)
{
	if(width == null)
	{
		width = 'full';
		height = 'full';
	}
	var url = getViewURL(oid);
	
	if(url != "") 
	{
		if(auth != null){
			//url += '&key=auth&value='+auth; 
		}
		if( oid.indexOf("EPMDocument") > 0) {
			//openWindow( url, '', 1100, 600);
			popup(url, 830, 600);
		}else if( oid.indexOf("Document") > 0){
			//openWindow( url, '', 870, 500);
			popup(url, 830, 650);
		}else if( oid.indexOf("TemplateProject") > 0){
			url += '&key=popup&value=popup';
			popup(url, 1180, 800);		
		}else if( oid.indexOf("AsmApproval") > 0) {
			//openWindow( url, '', 850, 300);
			popup(url, 850, 700);
		}else if( oid.indexOf("EChangeRequest") > 0) {
			//openWindow( url, '', 850, 300);
			popup(url, 1024, 768);
		}else if( oid.indexOf("EChangeOrder") > 0) {
			//openWindow( url, '', 850, 300);
			popup(url, 1024, 768);
		}else if( oid.indexOf("EChangeNotify") > 0) {
			//openWindow( url, '', 850, 300);
			popup(url, 1024, 768);
		}else if( oid.indexOf("EChange") > 0) {
			//openWindow( url, '', 850, 300);
			popup(url, 1024, 768);
		}else if( oid.indexOf("MPart") > 0) {
			//openWindow( url, '', 850, 300);
			popup(url, 858, 500);
		}else if( oid.indexOf("Project") > 0) {
			//openWindow( url, '', 850, 400);
			url += '&key=popup&value=popup';
			popup(url, 858, 700);
		}else if( oid.indexOf("UnitErrorProcess") > 0){
			popup(url, 900, 700);
		}else if( oid.indexOf("UnitErrorAssign") > 0){
			popup(url, 900, 700);	
		}else if( oid.indexOf("ERPSendMaster") > 0){
			popup(url, 858, 500);		
		}else if( oid.indexOf("DsecMcr") > 0){
			url += '&key=state&value=popup';
			popup(url, 858, 500);	
		}else if( oid.indexOf("DsecCsr") > 0){
			url += '&key=state&value=popup';
			popup(url, 858, 500);	
		}else if( oid.indexOf("DsecPamRequest") > 0){
			url += '&key=state&value=popup';
			popup(url, 858, 500);	
		}else if( oid.indexOf("ProjectManager") > 0){
			url += '&key=state&value=popup';
			popup(url, 858, 500);	
		}
		
		else{
			//openWindow( url, '', width, height);	
			popup(url, 1100, 600);
		}
	}
}

function notOpenView(oid)
{
	var url = getViewURL(oid);
	if(url != "")	location.href = url;
}

function getViewURL(oid)
{
	var url = "";
	if( oid.indexOf("CustomEPM") > 0)
	{
		url = "/plm/jsp/doc/epmDocView.jsp"
	}
	else if( oid.indexOf("EPM") > 0)
	{
		url = "/plm/jsp/edm/ViewEPMDocument.jsp"
	}
	else if( oid.indexOf("Drawing") > 0)
	{
		url = "/plm/jsp/drawing/ViewDrawing.jsp"
	}
	else if( oid.indexOf("Document") > 0)
	{
		url = "/plm/jsp/dms/ViewDocumentPop.jsp"
		url += '&key=buttenView&value=T';
	}
	else if( oid.indexOf("WTPart") > 0 )
	{
		url = '/plm/jsp/part/ViewPart.jsp'
	}
	else if( oid.indexOf("EChangeRequest") > 0 )
	{
		url = '/plm/jsp/change/ecr/ViewECR.jsp'
	}
	else if( oid.indexOf("EChangeOrder") > 0 )
	{
		url = '/plm/jsp/change/eco/ViewECO.jsp'
	}
	else if( oid.indexOf("EChangeNotify") > 0 )
	{
		url = '/plm/jsp/change/ecn/ViewECN.jsp'
	}
	else if( oid.indexOf("AsmApproval") > 0 )
	{
		url = '/plm/jsp/groupware/workprocess/ViewAsmApproval.jsp'
	}
	else if( oid.indexOf("MPart") > 0 )
	{
		url = '/plm/jsp/part/ViewMPart.jsp'
	}
	else if( oid.indexOf("EducationHistory") > 0 ){
		url = '/plm/jsp/manage/edu/EducationHistoryView.jsp'
	}
	else if( oid.indexOf("WorkProcessForm") > 0 ){
		url = '/plm/jsp/groupware/workprocess/form/ViewFormProcess.jsp'
	}
	else if( oid.indexOf("JELProject") > 0 ){
		url = '/plm/jsp/project/ProjectOnlyView.jsp'
	}
	else if( oid.indexOf("E3PSTask") > 0 ){
		url = '/plm/jsp/project/taskView.jsp'
	}
	else if(oid.indexOf("DistributeCompanyOpinion")>0){
		url='/plm/jsp/distribute/ViewDistCompanyOpinion.jsp'
	}
	else if(oid.indexOf("DistributeProcess")>0){
		url='/plm/jsp/cpc/ViewDistProcess.jsp'
	}
	else if(oid.indexOf("WTUser")>0){
		url='/plm/jsp/groupware/company/peopleView.jsp'
	}
	else if(oid.indexOf("WTGroup")>0){
		url='/plm/jsp/groupware/group/ViewGroup.jsp'
	}
	else if(oid.indexOf("KYQuality")>0){
		url='/plm/jsp/quality/ViewQuality.jsp'
	}
	else if(oid.indexOf("UnitErrorProcess")>0){
		url='/plm/jsp/equipment/equipmentView.jsp'
	}
	else if(oid.indexOf("UnitErrorAssign")>0){
		url='/plm/jsp/equipment/UnitErrorAssignView.jsp'
	}
	else if(oid.indexOf("TemplateProject")>0){
		url = '/plm/jsp/project/template/TemplateProjectViewFrm.jsp'
	}
	else if(oid.indexOf("ERPSendMaster")>0){
		url = '/plm/jsp/part/ViewSendHistory.jsp'
	}
	else if(oid.indexOf("ProcessControl")>0){
		url = '/plm/jsp/process/ViewProcess.jsp'
	}
	else if(oid.indexOf("DsecMcr")>0){
		url = '/plm/jsp/mrm/jsp/ViewProdMoldRequest.jsp'
	}
	else if(oid.indexOf("DsecCsr")>0){
		url = '/plm/jsp/mrm/jsp/ViewSelVendorRequest.jsp'
	}
	else if(oid.indexOf("DsecPamRequest")>0){
		url = '/plm/jsp/mrm/jsp/ViewPartApprovalRequest.jsp'
	}
	else if(oid.indexOf("ProjectManager")>0){
		url = '/plm/jsp/project/ViewProgram.jsp'
	}
	else if( oid.indexOf("TaskDepartmentProjectAssign") > 0 ){
		url = '/plm/jsp/project/ProjectOnlyView.jsp'
	}
	
	if(url != "") return "/plm/jsp/common/loading.jsp?url="+url+"&key=oid&value="+oid;
	return '';
}

function gfn_showApprovalHistory(oid) {
	if(oid != "" || oid != null)
		openWindow('/plm/jsp/groupware/workprocess/ApprovalOldHistoryView.jsp?oid='+oid, 'processHistory',780,500);
}
