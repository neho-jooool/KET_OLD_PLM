<%@page import="e3ps.project.ProductProject"%>
<%@page import="java.util.TreeMap"%>
<%@page import="ext.ket.part.base.service.PartBaseHelper"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="wt.part.WTPartMaster"%>
<%@page import="wt.util.WTAttributeNameIfc"%>
<%@page import="ext.ket.shared.util.SearchUtil"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.groupware.company.DepartmentHelper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="ext.ket.shared.content.service.KETContentHelper"%>
<%@page import="ext.ket.shared.content.entity.ContentDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="ext.ket.wfm.service.KETWorkflowHelper"%>
<%@page import="e3ps.wfm.service.KETWfmHelper"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrder"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeActivityLink"%>
<%@page import="e3ps.ecm.entity.KETMoldECADocLink"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeActivity"%>
<%@page import="e3ps.ecm.entity.KETProdChangeOrder"%>
<%@page import="e3ps.ecm.entity.KETProdChangeActivityLink"%>
<%@page import="e3ps.ecm.entity.KETProdECADocLink"%>
<%@page import="e3ps.ecm.entity.KETProdChangeActivity"%>
<%@page import="wt.part.WTPart"%>
<%@page import="e3ps.dms.entity.KETDocumentPartLink"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.dms.entity.KETDocumentOutputLink"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.dms.entity.KETDocumentProjectLink"%>
<%@page import="e3ps.dms.service.KETDmsHelper"%>
<%@page import="e3ps.dms.entity.KETDocumentCategoryLink"%>
<%@page import="e3ps.dms.entity.KETDocumentCategory"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.obj.ObjectUtil"%>
<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="e3ps.common.service.KETCommonHelper"%>
<%@page import="wt.fc.WTObject"%>
<%@page import="wt.org.WTGroup"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    ProjectOutput po = null;
    String oid =  request.getParameter("oid");
    String isPop =  request.getParameter("isPop");
    String buttenView = request.getParameter("buttenView");
    String isRefresh = request.getParameter("isRefresh");
    if ( (buttenView == null) || (buttenView.trim().length() == 0) ) {
        buttenView = "F";
    }
    isPop = StringUtil.checkNull(isPop);
    boolean isAdmin = CommonUtil.isAdmin();
    //검색조건
    String SdocumentNo = StringUtil.checkNull(request.getParameter("documentNo"));
    String SdivisionCode = StringUtil.checkNull(request.getParameter("divisionCode"));
    String ScategoryCode = StringUtil.checkNull(request.getParameter("categoryCode"));
    String SdocumentName = StringUtil.checkNull(request.getParameter("documentName"));
    String SauthorStatus = StringUtil.checkNull(request.getParameter("authorStatus"));
    String ScreatorName = StringUtil.checkNull(request.getParameter("creatorName"));
    String Spredate = StringUtil.checkNull(request.getParameter("predate"));
    String Spostdate = StringUtil.checkNull(request.getParameter("postdate"));
    String SisBuyerSummit = StringUtil.checkNull(request.getParameter("isBuyerSummit"));
    String SbuyerCodeStr = StringUtil.checkNull(request.getParameter("buyerCodeStr"));
    String Sislastversion = StringUtil.checkNull(request.getParameter("islastversion"));
    String Squality = StringUtil.checkNull(request.getParameter("quality"));

    String SprojectNo = StringUtil.checkNull(request.getParameter("projectNo"));
    String SprojectName = StringUtil.checkNull(request.getParameter("projectName"));

    String SsortKey = StringUtil.checkNull(request.getParameter("sortKey"));
    String SsortKeyD = StringUtil.checkNull(request.getParameter("sortKeyD"));
    String SpageCnt = StringUtil.checkNull(request.getParameter("pageCnt"));
    String Spage = StringUtil.checkNull(request.getParameter("page"));
    String SnowBlock = StringUtil.checkNull(request.getParameter("nowBlock"));
    String isSer = StringUtil.checkNull(request.getParameter("isSer"));
    if ( (isSer == null) || (isSer.trim().length() == 0) ) {
        isSer = "F";
    }

    String documentNo = null;
    String documentName = null;
    String divisionCode = null;
    String versionInfo = null;
    String iterationInfo = null;
    String deptName = null;
    String lifeCycleState = null;

    String firstRegistrationStage = null;
    Integer tmpInt = 0;
    String isBuyerSummit = null;
    String buyerCode = null;
    String isDRCheckSheet = null;
    String dRCheckPoint = null;
    String documentDescription = null;

    String categoryCode = null;
    String categoryName = null;
    String docCatePath = null;
    String urlpath="";
    String iconpath="";
    String docCateAttr = "";
    String tmpStr = "";

    String insDate = "";
    String updDate = "";
    String insUser = "who";
    String pjtNo = "";
    String pjtName = "";
    String taskName = "";
    String pjtOid = "";
    String taskOid = "";
    String appDataOid = "";
    String outputOid = "0";
    KETProjectDocument docu = null;

    String stateState = "";
    String approver = "-";
    String approvedDate = "-";

    String security = "";
    String securityName = "";

    String stateDecision = "";
    String stateComments = "";
    
    String pubDateYn = "";
    String pubDate = "";
    String pubCycle = "";

    String loginOid = "";
    Boolean isAuth = false;
    Boolean isDivAuth = false;
    Boolean isSecu = false;
    Boolean isLVer = false;

    // Web Editor
    Object webEditor = null;
    Object webEditorText = null;
    
    // 품질표준문서
    String qualityNo = "";
    
    String analysisNo = "";
    String analysisCode = "";
    
    //원가비고사항
    String costComment = "";
    
    boolean isCostInfo = false;
    boolean isWebEditorOpen = true;
    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
    
    
    boolean isDocCateSecurity = false;

    if ( oid != null ) {
        if ( oid.equals("0000") ) {
            //불필요
        }
        else{
            //KETProjectDocument의 oid로 해당 객체의 정보를 화면에 나타낸다.
            docu = (KETProjectDocument)CommonUtil.getObject(oid);
            
            //분류체계의 패스 정보를 화면에 나타낸다.
            KETDocumentCategory docCate = null;
            QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);
            if ( r.hasMoreElements() ) {
                docCate = (KETDocumentCategory) r.nextElement();
                categoryCode=docCate.getCategoryCode();
                
                categoryName = docCate.getCategoryName();
                if(docCate.getIsAPQP()) docCateAttr = docCateAttr + "/ APQP대상 ";
                if(docCate.getIsPSO10()) docCateAttr = docCateAttr + "/ PSO12대상 ";
                if(docCate.getIsESIR()) docCateAttr = docCateAttr + "/ ESIR대상 ";
                if(docCate.getIsISIRCar()) docCateAttr = docCateAttr + "/ ISIRCar대상 ";
                if(docCate.getIsISIRElec()) docCateAttr = docCateAttr + "/ ISIRElec대상 ";
                if(docCate.getIsANPQP()) docCateAttr = docCateAttr + "/ ANPQP대상 ";
                if(docCate.getIsSYMC()) docCateAttr = docCateAttr + "/ SYMC대상 ";
                if(docCateAttr.length()>0) docCateAttr=docCateAttr.substring(1);

                docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode);
                docCatePath= docCatePath.substring(1);

                isDRCheckSheet=docCate.getIsDRCheckSheet().toString();
                
                if("Y".equals(docCate.getAttribute4())){
                	isDocCateSecurity = true; //분류체계의 대내비 여부 설정되어 있으면 문서열람 권한이 강화된다 -> 작성자, 결재자만 열람가능
                }
                
                if("Y".equals(docCate.getAttribute6())){
    		    	pubDateYn = "Y";
    		    }else{
    		    	pubDateYn = "N";
    		    }
            }
            if ( isDRCheckSheet == null ) {
                isDRCheckSheet = "false";
            }

            versionInfo = docu.getVersionIdentifier().getValue();
            isLVer = ObjectUtil.isLatestVersion(docu);

            WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
            loginOid = CommonUtil.getOIDString(loginUser);

            if ( loginOid.equals(docu.getIterationInfo().getCreator().toString()) ) {
                isAuth = true;
                isSecu = true;
            }
            if ( isSecu==false) {
                isSecu = WorkflowSearchHelper.manager.userInApproval(docu,loginUser.getName());
            }
            if ( CommonUtil.isAdmin() ) {
                
        	    // HEENEETODO : 관리자는 모두 할 수 있어야 하지 않을까?
        	    //isSecu = false;
        	    isSecu = true;
            }
            
            if("PD303148".equals(categoryCode)){
                if(isAuth || isSecu || CommonUtil.isMember("원가관리") || CommonUtil.isMember("원가조회")){
                    isCostInfo = true;
                }else{
                    isWebEditorOpen = false;
                }
            }

            documentNo =  docu.getDocumentNo();
            qualityNo = StringUtil.checkNull(docu.getAttribute1());
            analysisNo = StringUtil.checkNull(docu.getAttribute3());
            analysisCode = StringUtil.checkNull(docu.getAttribute2());
            
            //사업부 정보를 화면에 나타낸다.
            divisionCode =  StringUtil.checkNull(docu.getDivisionCode());
            String logDiv;
            if ( CommonUtil.isMember("전자사업부") ) {
                logDiv = "ER";
            }
            else if ( CommonUtil.isMember("자동차사업부") ) {
                logDiv = "CA";
            }
            else {
                logDiv = "0";
            }
            if ( divisionCode.equals(logDiv) || CommonUtil.isMember("지원조직") ) {
                isDivAuth = true;
            }

            if ( divisionCode.equals("CA") ) {
                divisionCode = "자동차사업부";
            }
            else if ( divisionCode.equals("ER") ) {
                divisionCode = "전자사업부";
            }
            else {
                divisionCode = "-";
            }

            security =  StringUtil.checkNull(docu.getSecurity());
            if ( !security.equals("S2") ) {
                isSecu = true;
            }
            QuerySpec qs = new QuerySpec();
            int idx = qs.appendClassList(NumberCode.class, true);
            SearchCondition sc2 = new SearchCondition(NumberCode.class, "codeType", "=", "SECURITYLEVEL"); // PRODCATEGORYCODE
            qs.appendWhere(sc2, new int[] {idx});
            qs.appendAnd();
            SearchCondition sc3 = new SearchCondition(NumberCode.class, "parentReference.key.classname", true);
            qs.appendWhere(sc3, new int[] {idx});
            ClassAttribute ca2 = new ClassAttribute(NumberCode.class,"code");
            qs.appendOrderBy(new OrderBy(ca2, false), new int[] { idx });

            QueryResult qrs = PersistenceHelper.manager.find( qs );

            while ( qrs.hasMoreElements() ) {
                Object[] o = (Object[])qrs.nextElement();
                NumberCode nCode = (NumberCode) o[0];
                if ( security.equals(nCode.getCode()) ) {
                    securityName=nCode.getName();
                }
            }

            iterationInfo = docu.getIterationIdentifier().getValue();
            lifeCycleState = docu.getLifeCycleState().getDisplay();
            stateState = docu.getLifeCycleState().toString();

            if ( !stateState.equals("INWORK") ) {

            	WTObject pbo = KETCommonHelper.service.getPBO( docu );

                if ( pbo != null ) {
                	WTUser appUser = WorkflowSearchHelper.manager.getLastApprover(pbo);
                    approver = (appUser == null)? "&nbsp;":appUser.getFullName();
                    approvedDate = WorkflowSearchHelper.manager.getLastApprovalDate(pbo);
                }
            }
            insUser = docu.getIterationInfo().getCreator().getFullName();

            //분류체계의 패스 정보를 화면에 나타낸다.
            /* KETDocumentCategory docCate = null;
            QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);
            if ( r.hasMoreElements() ) {
                docCate = (KETDocumentCategory) r.nextElement();
                categoryCode=docCate.getCategoryCode();

                if(docCate.getIsAPQP()) docCateAttr = docCateAttr + "/ APQP대상 ";
                if(docCate.getIsPSO10()) docCateAttr = docCateAttr + "/ PSO12대상 ";
                if(docCate.getIsESIR()) docCateAttr = docCateAttr + "/ ESIR대상 ";
                if(docCate.getIsISIRCar()) docCateAttr = docCateAttr + "/ ISIRCar대상 ";
                if(docCate.getIsISIRElec()) docCateAttr = docCateAttr + "/ ISIRElec대상 ";
                if(docCate.getIsANPQP()) docCateAttr = docCateAttr + "/ ANPQP대상 ";
                if(docCate.getIsSYMC()) docCateAttr = docCateAttr + "/ SYMC대상 ";
                if(docCateAttr.length()>0) docCateAttr=docCateAttr.substring(1);

                docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode);
                docCatePath= docCatePath.substring(1);

                isDRCheckSheet=docCate.getIsDRCheckSheet().toString();
            }
            if ( isDRCheckSheet == null ) {
                isDRCheckSheet = "false";
            } */

            E3PSProject proj1 = null;
            QueryResult r4 = PersistenceHelper.manager.navigate(docu, "project" , KETDocumentProjectLink.class );
            
            boolean isCompleted = true;
            int costCnt = 0;
            while ( r4.hasMoreElements() ) {
                 proj1 = (E3PSProject) r4.nextElement();
                 proj1 = ProjectHelper.getLastProject(proj1.getMaster());
                 if ( isSecu==false && isDocCateSecurity == false ) {
                     isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject)proj1);
                 }
                 String state = proj1.getState().toString();
                 if(!"COMPLETED".equals(state)){
                     isCompleted = false;
                 }
                 
/*                  if(proj1 instanceof ProductProject){ //원가정보 보여 줄 때 Pilot 프로젝트만 유효하게 하는 로직 
                     ProductProject pjt = (ProductProject)proj1;
                     String process = pjt.getProcess().getCode();
                     if("PC002".equals(process)){
                	 costCnt++;
                     }
                 } */
            }
            
/*             if(costCnt == 0){
        	   isCostInfo = false;
            } */
            
            if(!isSecu){
        	   isSecu = isCompleted;//관련 프로젝트가 모두 완료되었다면 보안등급에 따른 제약 무효화
            }
            costComment = StringUtil.checkNull(docu.getCostComment());
            if(StringUtil.checkString(costComment)){
        	   costComment = costComment.replaceAll("\r\n", "<br/>");
            }
            
            documentName = docu.getTitle();
            deptName = docu.getDeptName();
            tmpInt = docu.getFirstRegistrationStage();
            firstRegistrationStage = tmpInt.toString();

            tmpInt = docu.getDRCheckPoint();
            dRCheckPoint = tmpInt.toString();
            isBuyerSummit = docu.getIsBuyerSummit();
            //고객제출자료 여부에 따라 buyerCode입력여부가 결정된다.
            //if ( isBuyerSummit.equals("1") ) {
                buyerCode = StringUtil.checkNull(docu.getBuyerCode());

                if ( !StringUtil.checkNull(buyerCode).equals("") ) {
                    StringTokenizer st = new StringTokenizer(buyerCode, ",");
                    String ct="";
                    String bName="";
                    while ( st.hasMoreTokens() ) {
                        ct=st.nextToken();
                        if(ct.indexOf("NumberCode") > 0){
                            NumberCode nCode1 = (NumberCode)CommonUtil.getObject(ct);
                            if ( nCode1==null ) {
                                bName=bName + "," + ct;
                            }
                            else {
                                bName=bName + "," + nCode1.getName();
                            }
                        }
                    }
                    if ( !bName.equals("") ) {
                        buyerCode=bName.substring(1);
                        buyerCode = buyerCode.replace(",","<br>");
                    }
                }
            //}
            //else {
            //    buyerCode = "-";
            //}

            if ( isDRCheckSheet.equals("true") ) {
                stateDecision = StringUtil.checkNull(docu.getApprovalResult());
                stateComments = StringUtil.checkNull(docu.getLastApprovalComment());
            }

            documentDescription = docu.getDocumentDescription();

            webEditor = docu.getWebEditor();
            webEditorText = docu.getWebEditorText();
            
            //pubDateYn = docu.getAttribute8();
            pubDate = docu.getAttribute9();
            pubCycle = docu.getAttribute10();

            // 과거버전에 대해 webEditor Column으로 Migration 되지 않아서
            // null 인경우는 예전 Column에서 가져오도록 변경
            if ( webEditor == null ) {
                webEditor = docu.getDocumentDescription();
            }

            insDate = DateUtil.getTimeFormat(docu.getCreateTimestamp(),"yyyy-MM-dd");
            updDate = DateUtil.getTimeFormat(docu.getModifyTimestamp(),"yyyy-MM-dd");


            QueryResult r33 = PersistenceHelper.manager.navigate(docu, "output" , KETDocumentOutputLink.class);
            if ( r33.hasMoreElements() ) {
                while ( r33.hasMoreElements() ) {
                    po = (ProjectOutput)r33.nextElement();
                    outputOid = CommonUtil.getOIDString(po);

                    E3PSTask task = (E3PSTask)po.getTask();
                    E3PSProject project = (E3PSProject)task.getProject();
                    if ( isSecu == false && isDocCateSecurity == false ) {
                    	project = ProjectHelper.getLastProject(project.getMaster());
                        isSecu = ProjectUserHelper.manager.isProjectRoleUser((TemplateProject)project);
                    }
                }
            }
        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script>
<script src='/plm/extcore/js/cost/costCommon.js?ver=0.1'></script>
<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js?ver=0.2"></script>

<style type="text/css">

p.indent{ padding-right: 2.97em }

<!--
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 5px;
}

table .costTb {
    border: 1px solid #d0d0d0;
    border-collapse: collapse;
    table-layout: fixed;
}

td .costTbHeader {

    border-bottom-style: solid;
    border-bottom-color: #CCCCCC;
    border-bottom-width: 1px;
    border-right-style: solid;
    border-right-color: #CCCCCC;
    border-right-width: 1px;
    background-color: #E0ECF8;
    height: 25px;
    padding-top: 2px;
    color: #404040;
    text-align: center;
    font-family: NanumGothic, "나눔고딕", Nanumgo, Dotum;
    font-size: 13px;
    font-weight: bold;
}

td .costTd {
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    border-bottom-width: 1px;
    border-bottom-style: solid;
    border-bottom-color: #CCCCCC;
    border-right-width: 1px;
    border-right-style: solid;
    border-right-color: #CCCCCC;
    background-color: #f7f7f7;
    height: 22px;
    color: #575757;
    padding-top: 2px;
    padding: 5px !important;
    font-family: NanumGothic, "나눔고딕", Nanumgo, Dotum;
    font-size: 12px;
    text-align: right;
}
-->
</style>
<script type="text/javascript">

$(document).ready(function(){
	var categoryCode = $("input[name=categoryCode]").val();
	
	if(categoryCode == 'PD303148'){
		setCostList();	
	}
	
});
<!--

    function updateDocument(oid,isPop,buttenView,isRefresh){
	
    //문서수정화면으로 이동한다.
        if( isPop=="Y" ) {
            parent.location.href="/plm/jsp/dms/UpdateDocument.jsp?cmd=update&oid="+oid+"&isPop="+isPop+"&buttenView="+buttenView+"&isRefresh="+isRefresh;
        }
        else {
            document.location.href="/plm/jsp/dms/UpdateDocument.jsp?cmd=update&oid="+oid+"&nowBlock=<%=SnowBlock%>&page=<%=Spage%>&cmd=search&documentNo=<%=SdocumentNo%>&divisionCode=<%=SdivisionCode%>&categoryCode=<%=ScategoryCode%>&documentName=<%=SdocumentName%>&authorStatus=<%=SauthorStatus%>&creatorName=<%=ScreatorName%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&isBuyerSummit=<%=SisBuyerSummit%>&buyerCodeStr=<%=SbuyerCodeStr%>&quality=<%=Squality%>&projectNo=<%=SprojectNo%>&projectName=<%=SprojectName%>&islastversion=<%=Sislastversion%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T";
        }
    }

    function reviceDocument(oid,isPop,buttenView,isRefresh){
        if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03304") %><%--문서를 개정하시겠습니까?--%> \n" + "※<%=messageService.getString("e3ps.message.ket_message", "03305") %><%--문서설명란에 개정사유를 반드시 입력해 주시기 바랍니다.--%>") ) {
            if ( isPop=="Y" ) {
                parent.location.href="/plm/jsp/dms/UpdateDocument.jsp?cmd=revice&oid="+oid+"&isPop="+isPop+"&buttenView="+buttenView+"&isRefresh="+isRefresh;
            }
            else {
                document.location.href="/plm/jsp/dms/UpdateDocument.jsp?cmd=revice&oid="+oid+"&nowBlock=<%=SnowBlock%>&page=<%=Spage%>&cmd=search&documentNo=<%=SdocumentNo%>&divisionCode=<%=SdivisionCode%>&categoryCode=<%=ScategoryCode%>&documentName=<%=SdocumentName%>&authorStatus=<%=SauthorStatus%>&creatorName=<%=ScreatorName%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&isBuyerSummit=<%=SisBuyerSummit%>&buyerCodeStr=<%=SbuyerCodeStr%>&quality=<%=Squality%>&projectNo=<%=SprojectNo%>&projectName=<%=SprojectName%>&islastversion=<%=Sislastversion%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T";
            }
        }
    }
    
    function viewAnalysis(){
        openWindow('/plm/ext/arm/info/armInfoViewForm.do?oid=<%=analysisCode %>','ArmInfoViewPopup',800,600);
    }

    //프로젝트 상세조회 팝업
    function viewProject(oid){
        if ( oid=="" ) return;
        openView(oid);
        //openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
    }

    //부품 상세조회 팝업
    function viewPart(v_poid){
        //var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
        //openWindow(url,"","750","650","scrollbars=yes,resizable=no,top=200,left=250");
        openView(v_poid);
    }

    //금형ECO 상세조회 팝업
    function viewRelEco(oid){
        openView(oid);
        //var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + oid;
        //openWindow2(url,"","800","680","scrollbars=yes,resizable=no,top=200,left=250,center=yes");
    }

    function viewRelEco1(oid){
        openView(oid);
        //var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + oid;
        //openWindow2(url,"","810","680","scrollbars=yes,resizable=no,top=200,left=250,center=yes");
    }

    function selDocu(arr){
        for ( var i = 0; i < arr.length; i++ ) {
            var docuOid = arr[i];
            alert(docuOid);
        }
    }

    function deleteDocument(oid,isPop){
        // confirm("문서를 삭제하시겠습니까?")
        if ( confirm('<%= messageService.getString("e3ps.message.ket_message", "01413") %><%--문서를 삭제하시겠습니까?--%>')) {
			if (isPop == "Y") {
				document.location.href = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=deletePop&oid="
						+ oid;
			} else {
				document.location.href = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=delete&oid="
						+ oid;
			}
		}
	}

	function listDocument(isPop) {
		if (isPop == "Y") {
			parent.location.href = "/plm/jsp/dms/SearchDocument.jsp";
		} else {
			document.location.href = "/plm/jsp/dms/SearchDocument.jsp";
		}
	}
	
	function doPrint(){
	    getOpenWindow2('/plm/extcore/jsp/dms/printProjectDocument.jsp?oid=<%= oid %>&isPop=Y', '', 1000, 800);
    }
	
	
	function getAuthDoc(){
		var rtn = '';
		
		$.ajax({
            url : "/plm/ext/dms/isgAuthUserDOC.do",
            type : "POST",
            data : {
                oid : '<%=oid%>'
            },
            dataType : 'json',
            async : false,
            success : function(data) {
                rtn = data;
            }
        });
		
		return rtn;
		
	}
	

	
	function authAlarm(path){
		var isAdmin = '';
		
		
		isAdmin = JSON.parse('<%=isAdmin%>');
		
		console.log("isAdmin : "+isAdmin);
		
		if(isAdmin != true){
			
			var rtn = getAuthDoc();
			console.log("rtn : "+rtn);
	        if(rtn != "Y"){
	            alert('<%= messageService.getString("e3ps.message.ket_message", "00990") %><%--권한이 없습니다.--%>');
	            return;
	        }
		}
		
		PLM_FILE_DOWNLOAD2(path);
        
	}
	
	function checkApproval(){
		
		var partNoList = new Array();
        $("input[name=partNumber]").each(function(){
        	var partNoValue = $(this).val();
        	partNoList.push(partNoValue.toString());
        });
        
        var param = {
       		currentDocNo : "<%=documentNo%>",
        	categoryCode : $("input[name=categoryCode]").val(),
        	partNoList : partNoList
        }
        var categoryName = $("input[name=categoryName]").val();
		var data = ajaxCallServer("/plm/ext/dms/chkDupMatPart", param, null, false);
        var dupList = data.dupList;
        
        if(data.isDupMatChk && partNoList.length == 0){
        	alert(categoryName+" 는 반드시 관련 부품이 1개이상 존재해야 합니다.\r\n※문의사항이 있으시면 경영혁신팀 '박상수차장'에게 문의 바랍니다.");
        	return;
        }
        
        if(dupList.length > 0){
        	
        	var msg = "동일 자재가 다른 문서에 기 등록되어 있습니다.\n";
    		
    		for(var i = 0; i < dupList.length; i++){
    			msg += dupList[i] + "\n";
    		}
    		
    		msg += "중복 등록 시 결재가 불가하오니 조치 후 진행 바랍니다.";
    		
    		alert(msg);
    		
        }else{
        	goPage('<%=oid%>');
        }
	}
	
	
	function openCostProfitPopup() {
		var costPartNos = '';
		var pjtNos = '';

		$('input[name=costPjtNo]').each(function(i){
			if($(this).val() != ''){
				pjtNos += $(this).val()+",";
	            costPartNos +=$("input[name=costPartNo]").eq(i).val()+",";	
			}
        });
		
	    url = "/plm/ext/cost/ProjectCostTrackingList.do?costPartNos="+costPartNos+"&pjtNos="+pjtNos;
	    
	    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
	    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
	    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

	    var rest = "width=" + (screen.availWidth * 0.9) + ",height="
	            + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
	            + toppos;

	    window.open(url, '', (opts + rest));
	}
	
	
	function openCostReportByDoc(taskOid) {
		var docOid = '<%=oid%>';
	    url = "/plm/ext/cost/costReportPopup.do?taskOid="+taskOid+"&docOid="+docOid;
	    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
	    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
	    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

	    var rest = "width=" + (screen.availWidth * 0.9) + ",height="
	            + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
	            + toppos;

	    window.open(url, '', (opts + rest));
	}
	
	var costAttrCheck = true;
	
	window.setCostList = function(){
		
		costAttrCheck = true;
		
		var pjtNos = "";
		
		$('input[name=pjtNos]').each(function(i){
			if (pjtNos.indexOf($(this).val()) < 0) {
				pjtNos += $(this).val()+",";
            }
            
        });
	    
	    var param = new Object();
	    param.pjtNos = pjtNos;
	    
	    ajaxCallServer("/plm/ext/cost/getCostInfoList", param, function(data){
	        
	        var costList = data.costList;
	        
	        if(costList.length == 0){
	        	addCostList(null);
	        }else{
	        	
	            var checkCnt = 0;
                var listCnt = 0;
                
                for(var i = 0; i < costList.length; i++){
                	
                	if(costList[i].EXIST == 'NO'){
                		listCnt++;
                	}
                	
                    if(costList[i].SALESTARGETGB2 != null && costList[i].SALESTARGETGB2 != ''){
                        checkCnt++;
                    }
                }

                if(listCnt != checkCnt){
                	costAttrCheck = false;
                }
                
	        	for(var i = 0; i < costList.length; i++){
	                addCostList(costList[i]);
	            }
	        	rowSpanCostTable();
	        }
	    });
	}
	
	window.numberConvert = function(data){
		if(data == null || data == ''){
			return '';
		}else{
			return data.commaFormat(1);
		}
	}
	
	window.rowSpanCostTable = function(){
		var table = document.getElementsByClassName("costTb")[0];
		var trArr = table.getElementsByTagName("tr");
		var rowSpan = 0;
		var compText = '';

		for (var trIdx = 1; trIdx < trArr.length; trIdx++) {
		    var td = table.rows[trIdx].cells[0]; 
		    if (compText == '') {
		        compText = td.outerText;
		        continue;
		    }
		    if (compText == td.outerText) {
		    	rowSpan++;
		    	td.setAttribute("class", "del");
		    } else {
		    	var td = table.rows[trIdx-1-rowSpan].cells[0];
		    	td.setAttribute("rowspan", rowSpan+1);
		    	rowSpan = 0;
		    	compText = table.rows[trIdx].cells[0].outerText;
		   }
		 
		   if ( trIdx == trArr.length-1 && rowSpan > 0 ) {
			   var cell = table.rows[trIdx-rowSpan].cells[0];
		       cell.setAttribute("rowspan", rowSpan+1);
		       rowSpan = 0;
		       compText = '';
		   }
	   }
		        
	   table = document.getElementsByClassName("costTb")[0];
	   var dels = table.getElementsByClassName("del");

	   for(var i = dels.length-1; i >= 0; i--){
		   dels[i].remove();
	   }
	}
	
	window.addCostList = function(data){
		
	    var costRow = "<tr>";
	    
	    if(data == null){
	    	costRow += "<td class='costTd' colspan='12' style= 'text-align: center; height: 40px; color: #585858;'><b>시스템을 통해 산출된 원가이력이 없습니다.</b></td>";
	    }else{
	    	costRow += "<td class='costTd' style= 'text-align: center;' title='"+data.PJTNO+"'><b>"+data.PJTNO+"</b></td>";
	    	costRow += "<td class='costTd' style= 'text-align: left;' title='"+data.REALPARTNO+"'>"+data.REALPARTNO+"<input type='hidden' id='costPartNo' name='costPartNo' value='"+data.REALPARTNO+"'></td>";
	        costRow += "<td class='costTd' style= 'text-align: left;' title='"+data.PARTNAME+"'>"+data.PARTNAME+"</td>";
	        if(data.EXIST == 'NO'){
	            costRow += "<td class='costTd' colspan='9' style= 'text-align: center;'>원가이력이 없습니다.<input type='hidden' id='costPjtNo' name='costPjtNo' value=''></td>";  
	        }else{
	            if(data.TOTALCOST == '' || data.TOTALCOST == null){
	            	costRow += "<td class='costTd'></td>";	
	            }else{
	            	costRow += "<td class='costTd'>"+numberConvert(data.TOTALCOST)+" <img src='/plm/portal/images/icon_5.png' style='cursor:pointer;' onclick=openCostReportByDoc('"+data.TASKOID+"')></td>";
	            }
	        	
                costRow += "<td class='costTd'>"+numberConvert(data.TOTALCOST2)+" <img src='/plm/portal/images/icon_5.png' style='cursor:pointer;' onclick=openCostReportByDoc('"+data.TASKOID2+"')></td>";
                costRow += "<td class='costTd' id='totalCost'><span class='compare'>"+numberConvert(data.TOTALCOST_CHANGE)+"</span></td>";
                costRow += "<td class='costTd' >"+numberConvert(data.SALESTARGET)+"<input type='hidden' id='costPjtNo' name='costPjtNo' value='"+data.PJTNO+"'></td>";
                
	        	var SALESTARGETGB2 = ''; 
	            
	            if(data.SALESTARGETGB2 != null && data.SALESTARGETGB2 != ''){
	                
	            	SALESTARGETGB2 = ' ('+data.SALESTARGETGB2+')';
	            	costRow += "<td class='costTd' >"+numberConvert(data.SALESTARGET2)+"<b>"+SALESTARGETGB2+"</b></td>";
	                
	            }else{
	            	
	            	if(!costAttrCheck){
	            		costRow += "<td class='costTd' ><p class='indent'>"+numberConvert(data.SALESTARGET2)+"</p></td>";
	            	}else{
	            		costRow += "<td class='costTd' >"+numberConvert(data.SALESTARGET2)+"</td>";
	            	}
	            }
	            
	            
	            costRow += "<td class='costTd' id='salesTarget'><span class='compare'>"+numberConvert(data.SALESTARGET_CHANGE)+"</span></td>";
	            if(data.TOTALCOST == '' || data.TOTALCOST == null){
	            	costRow += "<td class='costTd' ></td>";
	            }else{
	            	if(data.PROFITRATE < 0){
	            		costRow += "<td class='costTd' style='color : #FF0000'>"+data.PROFITRATE+"</td>";
	            	}else{
	            		costRow += "<td class='costTd' >"+data.PROFITRATE+"</td>";	
	            	}
	            		
	            }
	            
	            if(data.PROFITRATE2 < 0){
	            	costRow += "<td class='costTd' style='color : #FF0000'>"+data.PROFITRATE2+"</td>";
	            }else{
	            	costRow += "<td class='costTd' >"+data.PROFITRATE2+"</td>";	
	            }
	            
	            
	            if(data.TOTALCOST == '' || data.TOTALCOST == null){
	            	costRow += "<td class='costTd' id='profitRate'></td>";	
	            }else{
	            	costRow += "<td class='costTd' id='profitRate'><span class='compare'>"+data.PROFITRATE_CHANGE+"</span></td>";
	            }
	               
	        }
	    }
	    costRow += "</tr>";
	    
	    $("#costList").append(costRow);
	    
	    if(data != null){
	    	$(".compare").each(function(){
	            var orgVal = $(this).text();
	            var value = $(this).text();
	            
	            if(value.indexOf("%") >= 0){
	                value = value.substring(0, value.indexOf("%"));
	            }
	            value = value.commaRemove();
	            if(value != null && value.length > 0){
	                if(value == '0.0'){
	                    
	                }else{
	                    var compare = eval("0<=" + value);
	                    
	                    var id = $(this).parent("td:first")[0].id;
	                    
	                    if(compare){
	                        
	                        if(id == 'totalCost'){
	                            $(this).parent("td:first").css("color", "#FF0000");
	                            $(this).parent("td:first").text(orgVal+" ▲");   
	                        }else{
	                            $(this).parent("td:first").css("color", "#0000FF");
	                            $(this).parent("td:first").text(orgVal+" ▲");   
	                        }
	                    }else{
	                        
	                        if(id == 'totalCost'){
	                            $(this).parent("td:first").css("color", "#0000FF");
	                            $(this).parent("td:first").text(orgVal+" ▼"); 
	                        }else{
	                            $(this).parent("td:first").css("color", "#FF0000");
	                            $(this).parent("td:first").text(orgVal+" ▼");   
	                        }

	                    }
	                }
	                
	                $(this).parent("td:first").css("font-weight", "bold");
	            }
	        });
	    }
	    
	    
	}
    -->
</script>
<!-- 글 내용을 보여줄 외곽 style 설정 : 사이트 특성에 맞게 수정 가능 및 제거 가능 -->
<!-- 이노디터의 직접적인 설정과는 무관하며, View 영역을 표시하기 위한 Style 설정임 -->
<style type="text/css">
/*<![CDATA[*/
.outline {
	border: 0px solid #c4cad1;
	padding: 5px;
}
/*]]>*/
</style>
<!-- Start : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->
<!--
    이노디터 기본글꼴을 굴림, 기본글크기를 10pt 로 설정하였다면
    View 페이지에서도 작성된 곳의 보여주는 부분을 동일하게 설정
-->
<style type="text/css">
/*<![CDATA[*/
#divView {
	font-family: 굴림;
	font-size: 10pt;
	color: #000000;
	line-height: normal;
	text-align: left;
	overflow-x: auto;
	word-wrap: break-word;
}

#divView TD {
	font-family: 굴림;
	font-size: 10pt;
	color: #000000;
	line-height: normal;
}

#divView P {
	margin-top: 2px;
	margin-bottom: 2px;
} /* IE 에서 줄바꿈(엔터친 경우) style 설정 */
#divView BLOCKQUOTE {
	margin-top: 2px;
	margin-bottom: 2px;
} /* IE 에서 들여쓰기 style 설정 */
/*]]>*/
</style>
<!-- End : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->
<script type="text/javascript">
    //<![CDATA[
    function fnLoadContent() {

        // 이노디터에서 작성된 내용을 전달
        var strContent = document.form01["webEditor"].value;
        if($("#divView").length){
        	var objView = document.getElementById("divView");
            objView.innerHTML = strContent;

            ////////////////////////////////////////////////////////////////////////////////////////////////////
            // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
            var strBackgroundColor = document.form01["hdnBackgroundColor"].value;
            if ("" != strBackgroundColor) {
                objView.style.backgroundColor = strBackgroundColor;
            }

            var strBackgroundImage = document.form01["hdnBackgroundImage"].value;
            if ("" != strBackgroundImage) {
                var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

                if ("none" == strCopyBackgroundImage) {
                    objView.style.backgroundImage = strBackgroundImage;
                } else {
                    objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
                }
            }

            var strBackgroundRepeat = document.form01["hdnBackgroundRepeat"].value;
            if ("" != strBackgroundRepeat) {
                objView.style.backgroundRepeat = strBackgroundRepeat;
            }
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    //]]>
</script>
</head>
<body onload="javascript:fnLoadContent();">
  <form name="form01">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor%></textarea>
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
    <input type="hidden" name="categoryName" value="<%=categoryName %>" />
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isPop.equals("Y"))?"display:none":"" %>">
            <tr>
              <td background="/plm/portal/images/logo_popupbg.png">
                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <!--  문서 상세조회  -->
                    <td class="font_01"><%if(!"".equals(qualityNo)){%><%=messageService.getString("e3ps.message.ket_message", "09521") %><%--개발산출문서--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "00638") %><%--개발산출문서--%><%} %></td>
                  </tr>
                </table>
              </td>
              <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
            </tr>
            <tr>
              <td class="space10"></td>
            </tr>
          </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isPop.equals("Y") && buttenView.equals("F"))?"display:none":"" %>">
            <tr>
              <td>&nbsp;</td>
              <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <%
                        //if ((stateState.equals("APPROVED")) && (isDivAuth == true) && (isSecu == true) && (isLVer == true)) {
                        //isDivAuth제거 사업부 상관없이 개정가능할 수 있도록 변경 2015.06.29 박주미 요청 연구기획팀 협의
                        if ((stateState.equals("APPROVED")) && (isSecu == true) && (isLVer == true)) {    
                    %>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviceDocument('<%=oid%>','<%=isPop%>','<%=buttenView%>','<%=isRefresh%>')"
                            class="btn_blue"
                          ><%=messageService.getString("e3ps.message.ket_message", "00684")%><%--개정--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <%
                        }
                        if (((stateState.equals("INWORK")) || (stateState.equals("REWORK"))) && (isAuth == true || CommonUtil.isAdmin())) {
                    %>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:updateDocument('<%=oid%>','<%=isPop%>','<%=buttenView%>','<%=isRefresh%>');"
                            class="btn_blue"
                          ><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDocument('<%=oid%>','<%=isPop%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:checkApproval();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778")%><%--결재요청--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <%
                        }
                    %>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doPrint()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03126")%><%--프린트--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:parent.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
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
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td class="tab_btm2"></td>
            </tr>
          </table>
          <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
            <colgroup>
              <col width="13%">
              <col width="20%">
              <col width="13%">
              <col width="20%">
              <col width="13%">
              <col width="20%">
            </colgroup>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420")%><%--문서번호--%></td>
              <%if(!"".equals(qualityNo)){ %>
              <td class="tdwhiteL"><%=documentNo%></td>
              <td class="tdblueL">품질문서번호</td>
              <td class="tdwhiteL"><%//=StringUtil.checkNull(qualityNo.substring(0, qualityNo.indexOf("-"))) %>
              <%if("null".equals(qualityNo.substring(qualityNo.indexOf("-")+1, qualityNo.length()))){out.println(StringUtil.checkNull(qualityNo.substring(0, qualityNo.indexOf("-")+1)));}else{out.println(qualityNo);}%>
              </td>
              <%}else{ %>
              <td colspan="3" class="tdwhiteL"><%=documentNo%></td>
              <%} %>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
              <td class="tdwhiteL0"><%=divisionCode%></td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
              <td colspan="3" class="tdwhiteL"><%=docCatePath%><input type="hidden" name="categoryCode" value="<%=categoryCode %>" /></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03030")%><%--품질확보절차--%></td>
              <td class="tdwhiteL0"><%=docCateAttr%>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%></td>
              <td colspan="5" class="tdwhiteL0"><%=documentName%></td>
            </tr>
            <%if(!"".equals(analysisNo)){ %>
            <tr>
              <td class="tdblueL">해석의뢰문서</td>
              <td colspan="5" class="tdwhiteL0"><a href="javascript:viewAnalysis();"><%=analysisNo%></a></td>
            </tr>
            <%} %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02858")%><%--최초 등록시점--%></td>
              <td class="tdwhiteL">
                <%
                    if (firstRegistrationStage.equals("1")) {
                %> <%=messageService.getString("e3ps.message.ket_message", "00629")%><%--개발단계--%> <%
     } else {
 %> <%=messageService.getString("e3ps.message.ket_message", "02094")%><%--양산단계--%> <%
     }
 %>
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
              <td class="tdwhiteL"><a href="javascript:viewVersionHistory('<%=oid%>');"><%=versionInfo%></a></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
              <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=oid%>')"><%=lifeCycleState%></a></td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00830")%><%--고객 제출자료--%></td>
              <td class="tdwhiteL">
                <%
                    if (isBuyerSummit.equals("1")) {
                %> <%=messageService.getString("e3ps.message.ket_message", "01227")%><%--대상--%> <%
     } else {
 %> <%=messageService.getString("e3ps.message.ket_message", "01637")%><%--비대상--%> <%
     }
 %>
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01530")%><%--보안등급--%></td>
              <td class="tdwhiteL"><%=securityName%>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%></td>
              <td class="tdwhiteL0"><%=buyerCode%></td>
            </tr>
             <%if("Y".equals(pubDateYn)){ %>
            <tr>
				<td class="tdblueL">
					<%=messageService.getString("e3ps.message.ket_message", "09523")%><%--발행일자--%>
				</td>
				<td class="tdwhiteL0">
					<%=pubDate%>
				</td>
				<td class="tdblueL">
					<%=messageService.getString("e3ps.message.ket_message", "09524")%><%--발행주기--%>
				</td>
				<td colspan="3" class="tdwhiteL0">
					<%=pubCycle%><%=StringUtil.checkString(pubCycle) ? "개월" : "" %>
				</td>
			</tr>
			<%} %>
            <%
            if("true".equals(docu.getAttribute7())){
                
                Department dept = null;
                if(StringUtils.isNotEmpty(docu.getAttribute4())){
                    dept = DepartmentHelper.manager.getDepartment(docu.getAttribute4());
                }
                String devDeptName = "";
                if(dept != null) devDeptName = dept.getName() + " 이하";
                String dutyName = StringUtil.checkNull(docu.getAttribute6());
                
                if(StringUtil.checkString(dutyName)) dutyName += " 이상";
            %>
            <tr>
            
				<td class="tdblueL">부서조회권한</td>
				<td class="tdwhiteL" colspan="3"><%=devDeptName%></td>
				<td class="tdblueL">사용자조회권한</td>
				<td class="tdwhiteL0"><%=dutyName%></td>
			</tr>
            <%
            }
                if (isDRCheckSheet.equals("true")) {
            		if (stateState.equals("APPROVED")) {
            %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00166")%><%--DR평가점수--%></td>
              <td colspan="3" class="tdwhiteL"><%=dRCheckPoint%> <%=messageService.getString("e3ps.message.ket_message", "02502")%><%--점--%></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01990")%><%--승인결과--%></td>
              <td class="tdwhiteL0"><%=stateDecision%>&nbsp;</td>
            </tr>
            <%
                } else {
            %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00166")%><%--DR평가점수--%></td>
              <td colspan="5" class="tdwhiteL0"><%=dRCheckPoint%> <%=messageService.getString("e3ps.message.ket_message", "02502")%><%--점--%></td>
            </tr>
            <%
                }
            %>
            <%
                if (stateState.equals("APPROVED")) {
            %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02854")%><%--최종승인 의견--%></td>
              <td colspan="5" class="tdwhiteL0"><%=stateComments%>&nbsp;</td>
            </tr>
            <%
                }
                }
            %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425")%><%--작성부서--%></td>
              <td class="tdwhiteL"><%=deptName%>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431")%><%--작성자--%></td>
              <td class="tdwhiteL"><%=insUser%>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
              <td class="tdwhiteL0"><%=insDate%>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951")%><%--수정일자--%></td>
              <td class="tdwhiteL"><%=updDate%>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000")%><%--승인자--%></td>
              <td class="tdwhiteL"><%=approver%>&nbsp;</td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999")%><%--승인일자--%></td>
              <td class="tdwhiteL0"><%=approvedDate%>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03046")%><%--프로젝트--%></td>
              <td colspan="5" class="tdwhiteM0">
                <table border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                    <colgroup>
                      <col width="200">
                      <col width="345">
                      <col width="*">
                    </colgroup>
                  <tr>
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03114")%><%--프로젝트번호--%></td>
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로젝트명--%></td>
                    <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00496")%><%--Task명--%></td>
                    <%} %>
                  </tr>
                  <%
                      HashMap<String, E3PSProject> pjtMap = new HashMap<String, E3PSProject>();
                      HashMap<String, HashMap<String, String>> taskMap = new HashMap<String, HashMap<String, String>>();
                  
                      QueryResult r3 = PersistenceHelper.manager.navigate(docu, "output", KETDocumentOutputLink.class);
                      if (r3.hasMoreElements()) {
                        while (r3.hasMoreElements()) {
                            po = (ProjectOutput) r3.nextElement();
                            outputOid = CommonUtil.getOIDString(po);

                            E3PSTask task = (E3PSTask) po.getTask();
                            E3PSProject project = (E3PSProject) task.getProject();
                            project = ProjectHelper.getLastProject(project.getMaster());
                            if (isSecu == false && isDocCateSecurity == false) {
                            isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject) project);
                            }

                            pjtOid = CommonUtil.getOIDString(project);
                            taskOid = CommonUtil.getOIDString(task);
                            pjtNo = StringUtil.checkNull(project.getPjtNo());
                            pjtName = StringUtil.checkNull(project.getPjtName());
                            taskName = StringUtil.checkNull(task.getTaskName());
                            pjtMap.put(pjtOid, project);
                            
                            String taskMapKey = pjtOid + "#" + taskName;
                            HashMap<String, String> rowData = new HashMap<String, String>();
                            rowData.put("pjtOid", pjtOid);
                            rowData.put("taskOid", taskOid);
                            rowData.put("pjtNo", pjtNo);
                            rowData.put("pjtName", pjtName);
                            rowData.put("taskName", taskName);
                            if(taskMap.containsKey(taskMapKey)){
                        	    HashMap<String, String> map = taskMap.get(taskMapKey);
                                long tempTaskOid1 = CommonUtil.getOIDLongValue(map.get("taskOid"));
                                long tempTaskOid2 = CommonUtil.getOIDLongValue(rowData.get("taskOid"));
                                if(tempTaskOid1 < tempTaskOid2){
                                    taskMap.put(taskMapKey, rowData);
                                }
                            }
                            else{
                                taskMap.put(taskMapKey, rowData);
                            }
                        }
                      }
                      Iterator<String> iter = taskMap.keySet().iterator();
                      while(iter.hasNext()){
                  	      String key = iter.next();
                  	      HashMap<String, String> rowData = (HashMap<String, String>) taskMap.get(key);
                  	      String pjtOid2 = rowData.get("pjtOid");
                          String pjtNo2 = rowData.get("pjtNo");
                          String pjtName2 = rowData.get("pjtName");
                          String taskOid2 = rowData.get("taskOid");
                          String taskName2 = rowData.get("taskName");
                  %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewProject('<%=pjtOid2%>')"><%=pjtNo2%>&nbsp;</a><input type="hidden" id='pjtNos' name="pjtNos" value="<%=pjtNo2%>" /></td>
                    <td class="tdwhiteM text-left"><%=pjtName2%>&nbsp;</td>
                    <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                    <td class="tdwhiteM"><a href="javascript:viewProject('<%=taskOid2%>')"><%=taskName2%>&nbsp;</a><input type="hidden" id='pjtNos' name="pjtNos" value="<%=pjtNo2%>" /></td>
                    <%} %>
                  </tr>
                  <%
                      }
                  %>
                  <%
                      E3PSProject project = null;
                      QueryResult qr = PersistenceHelper.manager.navigate(docu, "project", KETDocumentProjectLink.class);
                      while (qr.hasMoreElements()) {
                        project = (E3PSProject) qr.nextElement();
                        project = ProjectHelper.getLastProject(project.getMaster());
                        pjtOid = CommonUtil.getOIDString(project);
                        pjtNo = StringUtil.checkNull(project.getPjtNo());
                        pjtName = StringUtil.checkNull(project.getPjtName());
                        taskName = " ";
                        if(pjtMap.containsKey(pjtOid)){
                            continue;
                        }
                        else{
                            pjtMap.put(pjtOid, project);
                        }
                  %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewProject('<%=pjtOid%>')"><%=pjtNo%>&nbsp;</a><input type="hidden" id='pjtNos' name="pjtNos" value="<%=pjtNo%>" /></td>
                    <td class="tdwhiteM text-left"><%=pjtName%>&nbsp;</td>
                    <td class="tdwhiteM"><%=taskName%>&nbsp;</td>
                  </tr>
                  <%
                      }
                  %>
                </table>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
              </td>
            </tr>
            <%if(isCostInfo){ %>
            <tr>
              <td class="tdblueL">제품원가<br>(DR단계별추이 <img src='/plm/portal/images/icon_5.png' style='cursor:pointer;' onclick='javascript:openCostProfitPopup();'>)</td>
              <td colspan="5" class="tdwhiteM0">
                <table border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <table width="100%" cellpadding="0" cellspacing="0" class="costTb">
                    <colgroup>
                      <col width="5%">  
                      <col width="5%">
                      <col width="15%">
                      <col width="7%">
                      <col width="7%">
                      <col width="7%">
                      <col width="7%">
                      <col width="7%">
                      <col width="7%">
                      <col width="7%">
                      <col width="7%">
                      <col width="7%">
                    </colgroup>
                  <thead>
                  <tr>
                    <td class="costTbHeader" rowspan="2">PJT No</td>
                    <td class="costTbHeader" rowspan="2">PartNo</td>
                    <td class="costTbHeader" rowspan="2">Part Name</td>
                    <td class="costTbHeader" colspan="3">원가</td>
                    <td class="costTbHeader" colspan="3">판매가</td>
                    <td class="costTbHeader" colspan="3">수익율</td>
                  </tr>
                  <tr>
                    <td class="costTbHeader">초기</td>
                    <td class="costTbHeader">최종</td>
                    <td class="costTbHeader">변동</td>
                    <td class="costTbHeader">초기</td>
                    <td class="costTbHeader">최종</td>
                    <td class="costTbHeader">변동</td>
                    <td class="costTbHeader">초기</td>
                    <td class="costTbHeader">최종</td>
                    <td class="costTbHeader">변동</td>
                  </tr>
                  </thead>
                  
                  <tbody id="costList">
                  </tbody>
                </table>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
              </td>
            </tr>
            
            <tr>
                <td class="tdblueL">원가 비고사항</td>
                <td colspan="5" class="tdwhiteL0"><%=costComment %></td>
            </tr>
            <%} %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01564")%><%--부품--%></td>
              <td colspan="5" class="tdwhiteM0">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                    <colgroup>
                      <col width="200">
                      <col width="*">
                    </colgroup>
                  <tr>
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품번호--%></td>
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586")%><%--부품명--%></td>
                  </tr>
                  <%
                      QuerySpec qs = new QuerySpec();
                      qs.addClassList(WTPart.class, true);
                      qs.addClassList(KETDocumentPartLink.class, false);
                      
                      SearchUtil.setOrderBy(qs, WTPart.class, 0, "versionInfo.identifier.versionSortId", true);
                      qr = PersistenceHelper.manager.navigate(docu, KETDocumentPartLink.PART_ROLE, qs, true);
                      
                      Map<String, WTPart> partMap = new HashMap<String, WTPart>();
                      
                      while (qr.hasMoreElements()) {
                          WTPart part = (WTPart) qr.nextElement();
                          String partNo = part.getNumber();
                          if(!partMap.containsKey(partNo)) partMap.put(partNo, part);
                      }
                      TreeMap<String, WTPart> treePartMap = new TreeMap<String, WTPart>(partMap);
                      Set<String> st = treePartMap.keySet();
                      Iterator<String> it = st.iterator();
                      
                      while(it.hasNext()){
                          String partNo = it.next();
                          WTPart part = partMap.get(partNo);
                          part = PartBaseHelper.service.getLatestPart(part.getNumber());
                  %>
                  <tr>
                    <td class="tdwhiteM">
                    	<a href="javascript:viewPart('<%=CommonUtil.getOIDString(part)%>')"><%=part.getNumber()%></a>
                    	<input type="hidden" name="partNumber" value="<%=part.getNumber()%>" />
                   	</td>
                    <td class="tdwhiteM text-left"><%=part.getName()%></td>
                  </tr>
                  <%
                      }
                  %>
                </table>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01844")%><%--설계변경--%></td>
              <td colspan="5" class="tdwhiteM0">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                    <colgroup>
                      <col width="200">
                      <col width="*">
                    </colgroup>
                  <tr>
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01847")%><%--설계변경 번호--%></td>
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01851")%><%--설계변경 제목--%></td>
                  </tr>
                  <%
                      try {
                        QueryResult qr1 = null;
                        KETProdChangeActivity pca = null;
                        qr = PersistenceHelper.manager.navigate(docu, "prodECA", KETProdECADocLink.class);
                        while (qr.hasMoreElements()) {
                            pca = (KETProdChangeActivity) qr.nextElement();
                            qr1 = PersistenceHelper.manager.navigate(pca, "prodECO", KETProdChangeActivityLink.class);
                            if (qr1.hasMoreElements()) {
                            KETProdChangeOrder ecoObj = (KETProdChangeOrder) qr1.nextElement();
                            String ecoOid = CommonUtil.getOIDString(ecoObj);
                  %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewRelEco1('<%=ecoOid%>')"><%=ecoObj.getEcoId()%></a></td>
                    <td class="tdwhiteL"><%=ecoObj.getEcoName()%></td>
                  </tr>
                  <%
                      }
                        }
                      } catch (Exception e) {
                	  Kogger.error(e);
                      }
                  %>
                  <%
                      try {
                        QueryResult qr1 = null;
                        KETMoldChangeActivity mca = null;
                        qr = PersistenceHelper.manager.navigate(docu, "moldECA", KETMoldECADocLink.class);
                        while (qr.hasMoreElements()) {
                            mca = (KETMoldChangeActivity) qr.nextElement();
                            qr1 = PersistenceHelper.manager.navigate(mca, "moldECO", KETMoldChangeActivityLink.class);
                            if (qr1.hasMoreElements()) {
                            KETMoldChangeOrder ecoObj = (KETMoldChangeOrder) qr1.nextElement();
                            String ecoOid = CommonUtil.getOIDString(ecoObj);
                  %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewRelEco('<%=ecoOid%>')"><%=ecoObj.getEcoId()%></a></td>
                    <td class="tdwhiteL"><%=ecoObj.getEcoName()%></td>
                  </tr>
                  <%
                      }
                        }
                      } catch (Exception e) {
                	  Kogger.error(e);
                      }
                  %>
                </table>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667")%><%--주 첨부파일--%></td>
              <% 
              //문서객체로 주첨부파일의 정보를 가져온다.
            if ( docu instanceof FormatContentHolder ) {
                FormatContentHolder holder = (FormatContentHolder)docu;
                ContentInfo info = ContentUtil.getPrimaryContent(holder);
                if ( info != null ) {
                    ContentItem ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());

                    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                    //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                    //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                    String path = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                    urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                    urlpath = "<a href=" + urlpath + " target='download'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                    iconpath = info.getIconURLStr();
                    
                    urlpath = "<a href=javascript:authAlarm('"+path+"');>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                    
                    iconpath = "<a href=javascript:authAlarm('"+path+"');>"  + info.getIconImg() + "</a>";

                    
                }
            }
              %>
                           
              <td colspan="5" class="tdwhiteL0"><p><%=iconpath%>&nbsp;<%=urlpath%></p></td>

              
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
              <%
                  if (docu instanceof FormatContentHolder) {
              		FormatContentHolder holder = (FormatContentHolder) docu;
              		Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
              		if (secondaryFiles.size() > 0) {
              %>
              <td colspan="5" class="tdwhiteL0"><p>
                  <%
                      for (int i = 0; i < secondaryFiles.size(); i++) {
                  			ContentInfo info = (ContentInfo) secondaryFiles.elementAt(i);

                  			if (info == null) {
                  			    iconpath = "";
                  			    urlpath = "";
                  			} else {
                  			    ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());
                  			    String ext = ctf.getFormat().getFormatName();
                  			    if(!"PDF".equals(ext)){
                  	                ext = "NOTPDF";
                  	            }
                  			    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();

                  			    iconpath = info.getIconURLStr();
                  			    
                  			    String path = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                urlpath = "<a href=javascript:authAlarm('"+path+"');>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                                          
                                iconpath = "<a href=javascript:authAlarm('"+path+"');>"+ info.getIconImg() + "</a>";
/*                   			    if (isSecu == false) {
                  				//urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" + appDataOid;
                  				String path = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                urlpath = "<a href=javascript:authAlarm('"+path+"');>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                                
                                iconpath = "<a href=javascript:authAlarm('"+path+"');>" + info.getIconImg() + "</a>";
                  			    } else {
                  				//urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" + appDataOid;
                  				urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                  				urlpath = "<a href=" + urlpath + " target='download'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                  			    } */
                  			}
                  %>
                  <%=iconpath%>&nbsp;<%=urlpath%><br>
                  <%
                      }
                  %>
                </p></td>
              <%
                  } else {
              %>
              <td colspan="5" class="tdwhiteL0"><p>&nbsp;</p></td>
              <%
                  }
                  } else {
              %>
              <td colspan="5" class="tdwhiteL0"><p>&nbsp;</p></td>
              <%
                  }
              %>
            </tr>
            <%if(isWebEditorOpen){ %>
            <tr>
              <td colspan="6" class="tdwhiteL0">
                <!--            <textarea readonly name="documentDescription" class="txt_fieldRO" id="textfield3" style="width:98%; height:95%;"><%=documentDescription%></textarea>--> <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                <div id="divView" style="width: 95%;" class="outline"></div> <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
              </td>
            </tr>
            <%} %>
          </table>
        <!-- download target iframe -->
        
        </td>
      </tr>
    </table>
  </form>
</body>
</html>
