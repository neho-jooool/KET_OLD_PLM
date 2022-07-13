<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.wfm.util.WFMProperties"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.common.service.KETCommonHelper"%>
<%@page import="wt.fc.WTObject"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.part.WTPart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="e3ps.edm.util.EDMProperties"%>
<%@page import="e3ps.edm.util.EDMEnumeratedTypeUtil"%>
<%@page import="e3ps.edm.beans.EDMHelper"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.edm.util.EDMAttributes"%>
<%@page import="java.util.HashMap"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page
    import="e3ps.dms.service.KETDmsHelper,e3ps.dms.entity.*,wt.fc.QueryResult,wt.fc.PersistenceHelper,wt.fc.WTObject,wt.doc.WTDocument,wt.doc.WTDocumentMaster,wt.content.*,wt.part.WTPart,wt.org.WTUser,wt.lifecycle.LifeCycleHelper,wt.lifecycle.LifeCycleManaged,wt.session.SessionHelper,e3ps.common.content.*,e3ps.project.*,e3ps.project.beans.*,e3ps.wfm.util.WorkflowSearchHelper,java.util.Vector,java.util.StringTokenizer,e3ps.common.util.CommonUtil,e3ps.common.util.StringUtil,e3ps.common.obj.ObjectUtil,e3ps.common.util.DateUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String oid = request.getParameter("oid");
    String isPop = request.getParameter("isPop");
    String buttenView = request.getParameter("buttenView");
    if ((buttenView == null) || (buttenView.trim().length() == 0)) {
        buttenView = "F";
    }
    isPop = StringUtil.checkNull(isPop);
  boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

    //검색조건
    String SdocumentNo = StringUtil.checkNull(request
            .getParameter("documentNo"));
    String SdivisionCode = StringUtil.checkNull(request
            .getParameter("divisionCode"));
    String ScategoryCode = StringUtil.checkNull(request
            .getParameter("categoryCode"));
    String SdocumentName = StringUtil.checkNull(request
            .getParameter("documentName"));
    String SauthorStatus = StringUtil.checkNull(request
            .getParameter("authorStatus"));
    String ScreatorName = StringUtil.checkNull(request
            .getParameter("creatorName"));
    String Spredate = StringUtil.checkNull(request
            .getParameter("predate"));
    String Spostdate = StringUtil.checkNull(request
            .getParameter("postdate"));
    String Sislastversion = StringUtil.checkNull(request
            .getParameter("islastversion"));
    String SsortKey = StringUtil.checkNull(request
            .getParameter("sortKey"));
    String SsortKeyD = StringUtil.checkNull(request
            .getParameter("sortKeyD"));
    String SpageCnt = StringUtil.checkNull(request
            .getParameter("pageCnt"));
    String Spage = StringUtil.checkNull(request.getParameter("page"));
    String SnowBlock = StringUtil.checkNull(request
            .getParameter("nowBlock"));
    String isSer = StringUtil.checkNull(request.getParameter("isSer"));
    if ((isSer == null) || (isSer.trim().length() == 0)) {
        isSer = "F";
    }

    Kogger.debug("ViewTechDocumentPrint", null, null, "ScategoryCode===>" + ScategoryCode);
    Kogger.debug("ViewTechDocumentPrint", null, null, "isSer ===>" + isSer);

    String documentNo = null;
    String documentName = null;
    String divisionCode = null;
    String versionInfo = null;
    String iterationInfo = null;
    String deptName = null;
    String lifeCycleState = null;

    Integer tmpInt = 0;
    String documentDescription = null;

    String categoryCode = null;
    String docCatePath = null;
    String appDataOid = "";
    String urlpath = "";
    String iconpath = "";
    String docCateAttr = "";
    String tmpStr = "";

    String insDate = "";
    String updDate = "";
    String insUser = "";
    String modUser = "";
    String stateState = "";
    String approver = "-";
    String approvedDate = "-";

    KETTechnicalDocument docu = null;
    Boolean isAuth = false;
    Boolean isDivAuth = false;
    Boolean isLVer = false;

    // Web Editor
    Object webEditor = null;
    Object webEditorText = null;

    String loginOid = "";
    
    // approval
    int totalApprovalSize = 0;
    int totalApprovalSizePercent = 0;
    List<String> appGubunList = new ArrayList<String>();
    List<String> appUserList = new ArrayList<String>();
    List<String> appCompDateList = new ArrayList<String>();
    if (oid != null) {
	   Kogger.debug("ViewTechDocumentPrint", null, oid, "view oid ===>" + oid);
        if (oid.equals("0000")) {
            docCatePath = "";
            documentName = "";
            divisionCode = "";
            deptName = "";
            documentDescription = "";
        } else {
            //KETTechnicalDocument의 oid로 해당 객체의 정보를 화면에 나타낸다.
            docu = (KETTechnicalDocument) CommonUtil.getObject(oid);
            isLVer = ObjectUtil.isLatestVersion(docu);

            WTUser loginUser = (WTUser) SessionHelper.manager
                    .getPrincipal();
            loginOid = CommonUtil.getOIDString(loginUser);
            
            Kogger.debug("ViewTechDocumentPrint", null, null, "loginUser ===>" + loginOid);
            Kogger.debug("ViewTechDocumentPrint", null, null, "Creator ===>"
                    + docu.getIterationInfo().getCreator().toString());

            if (loginOid.equals(docu.getIterationInfo().getCreator()
                    .toString())) {
                isAuth = true;
            }

            documentNo = docu.getNumber();

            //사업부 정보를 화면에 나타낸다.
            divisionCode = StringUtil.checkNull(docu.getDivisionCode());
            String logDiv;
            if (CommonUtil.isMember("전자사업부")) {
                logDiv = "ER";
            } else if (CommonUtil.isMember("자동차사업부")) {
                logDiv = "CA";
            } else {
                logDiv = "0";
            }
            if (divisionCode.equals(logDiv)
                    || CommonUtil.isMember("지원조직")) {
                isDivAuth = true;
            }

            if (divisionCode.equals("CA")) {
                divisionCode = "자동차사업부";
            } else if (divisionCode.equals("ER")) {
                divisionCode = "전자사업부";
            } else {
                divisionCode = "-";
            }
            versionInfo = docu.getVersionIdentifier().getValue();
            iterationInfo = docu.getIterationIdentifier().getValue();
            lifeCycleState = docu.getLifeCycleState().getDisplay();
            stateState = docu.getLifeCycleState().toString();

            if (!stateState.equals("INWORK")) {
                if (WorkflowSearchHelper.manager.getLastApprover(docu) != null) {
                    approver = WorkflowSearchHelper.manager
                            .getLastApprover(docu).getFullName();
                    approvedDate = WorkflowSearchHelper.manager
                            .getLastApprovalDate(docu);
                }
            }

            insUser = docu.getIterationInfo().getCreator()
                    .getFullName();
            modUser = docu.getIterationInfo().getModifier()
                    .getFullName();

            //분류체계의 패스 정보를 화면에 나타낸다.
            KETDocumentCategory docCate = null;
            QueryResult r = PersistenceHelper.manager.navigate(docu,
                    "documentCategory", KETTechnicalCategoryLink.class);
            if (r.hasMoreElements()) {
                docCate = (KETDocumentCategory) r.nextElement();
                categoryCode = docCate.getCategoryCode();

                if (docCate.getIsAPQP())
                    docCateAttr = docCateAttr + "/ APQP대상 ";
                if (docCate.getIsPSO10())
                    docCateAttr = docCateAttr + "/ PSO10대상 ";
                if (docCate.getIsESIR())
                    docCateAttr = docCateAttr + "/ ESIR대상 ";
                if (docCate.getIsISIRCar())
                    docCateAttr = docCateAttr + "/ ISIRCar대상 ";
                if (docCate.getIsISIRElec())
                    docCateAttr = docCateAttr + "/ ISIRElec대상 ";
                if (docCate.getIsANPQP())
                    docCateAttr = docCateAttr + "/ ANPQP대상 ";
                if (docCate.getIsSYMC())
                    docCateAttr = docCateAttr + "/ SYMC대상 ";
                if (docCateAttr.length() > 0)
                    docCateAttr = docCateAttr.substring(1);

                docCatePath = KETDmsHelper.service
                        .selectCategoryPath(categoryCode);
                docCatePath = docCatePath.substring(1);
            }

            documentName = docu.getTitle();
            deptName = docu.getDeptName();

            documentDescription = docu.getDocumentDescription();

            webEditor = docu.getWebEditor();
            webEditorText = docu.getWebEditorText();

            // 과거버전에 대해 webEditor Column으로 Migration 되지 않아서
            // null 인경우는 예전 Column에서 가져오도록 변경
            if (webEditor == null) {
                webEditor = docu.getDocumentDescription();
            }

            insDate = DateUtil.getTimeFormat(docu.getCreateTimestamp(),
                    "yyyy-MM-dd");
            updDate = DateUtil.getTimeFormat(docu.getModifyTimestamp(),
                    "yyyy-MM-dd");

            //문서객체로 주첨부파일의 정보를 가져온다.
            if (docu instanceof FormatContentHolder) {
                FormatContentHolder holder = (FormatContentHolder) docu;
                ContentInfo info = ContentUtil
                        .getPrimaryContent(holder);
                ContentItem ctf = (ContentItem) CommonUtil
                        .getObject(info.getContentOid());

                appDataOid = ctf.getPersistInfo().getObjectIdentifier()
                        .getStringValue();
                //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                /* urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="
                        + CommonUtil.getOIDString(holder)
                        + "&adOid="
                        + appDataOid; */
                urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                urlpath = "<a href=" + urlpath + " target='_blank'>"
                        + info.getName() + "</a>&nbsp;(&nbsp;"
                        + info.getFileSize() + "&nbsp;)";
                iconpath = info.getIconURLStr();
            }
        }
        
        ////////////////////////////////
        // 결재 이력 정보 추가
        ////////////////////////////////
        String pboOid = oid;
        ReferenceFactory rf = new ReferenceFactory();
        KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
        KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
        KETWfmApprovalMaster master = null;
        Object temp = new Object();
        Vector vec = null;
        WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(pboOid));
        if (targetObj != null)
            master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
        if (master != null) {
            vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
            for (int i = 0; i < vec.size() - 1; i++) {
                for (int j = i + 1; j < vec.size(); j++) {
                    history1 = (KETWfmApprovalHistory) vec.get(i);
                    history2 = (KETWfmApprovalHistory) vec.get(j);
                    if (history1.getSeqNum() < history2.getSeqNum()) {
                        temp = vec.get(i);
                        vec.set(i, vec.get(j));
                        vec.set(j, temp);
                    }
                }
            }
        }
        
        if (vec != null) {
            boolean isApprover = true;
            String refType = WFMProperties.getInstance().getString("wfm.reference");
            String activityName = "&nbsp;";
            for (int i = 0; i < vec.size(); i++) {
                KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
                activityName = StringUtil.checkNull(history.getActivityName());

                /*
                if( activityName.equals("수신") || activityName.equals("참조") || activityName.equals("추가배포요청") ){
                   vec.remove(i);
                   continue;
                }
                */
                
                if (isApprover && activityName.equals("검토")) {
                    activityName = "승인";
                    isApprover = false;
                }
                
                People people = PeopleHelper.manager.getPeople(history.getOwner().getName());
                String completeDate = "&nbsp;";
                if (history.getCompletedDate() != null){
                   completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
                }
                String owner = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
                if (history.getDelegate() != null){
                   owner = owner + "(" + history.getDelegate() + ")";
                }
                    
                appGubunList.add(activityName);
                appUserList.add(owner);
                appCompDateList.add(completeDate);
            }
        }
        
        totalApprovalSize = (vec != null) ? vec.size() : totalApprovalSize;
        if(vec != null){
            int k100 = 100;
            if(totalApprovalSize == 0){
        	    totalApprovalSizePercent = k100;
            }else{
        	    totalApprovalSizePercent = k100 / totalApprovalSize;
            }
        }
        
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=messageService.getString("e3ps.message.ket_message","01127")%><%--기술문서 상세조회--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../portal/js/org.js"></script>
<script type="text/javascript" src="../../portal/js/common.js"></script>
<style type="text/css">
<!-- /* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
-->
</style>
<script type="text/javascript">
<!--

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
</head>

<body>
    <form name="form01" method="post">
    
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
        
	<div class="contents-wrap">
		    <%
		       String leftTopAppSize = "";
		       if(totalApprovalSize <= 2){
			      leftTopAppSize = "30";
		       }else if(totalApprovalSize <= 3){
			      leftTopAppSize = "40";
		       }else if(totalApprovalSize <= 5){
			      leftTopAppSize = "50";
		       }else if(totalApprovalSize <= 6){
			      leftTopAppSize = "60";
		       }else if(totalApprovalSize <= 7){
			      leftTopAppSize = "70";
		       }else if(totalApprovalSize <= 8){
			      leftTopAppSize = "80";
		       }else if(totalApprovalSize >= 9){
			      leftTopAppSize = "100";
		       }

		    %> 
		<div class="b-space30 t-space15 float-r">
			<table cellpadding="0" summary="0" class="table-style-01 float-r" style="width:<%=leftTopAppSize %>%">
				<colgroup>
				    <%
				      for(int z=0; z<totalApprovalSize; z++){
				    %>
					<col width="<%=totalApprovalSizePercent %>%" />
				    <%
				      }
				    %>
				</colgroup>
				<thead>
					<tr>
					<%
                      for(String appGubun : appGubunList){
                    %>
                    <td class="title02"><%=appGubun%></td>
                    <%
                      }
                    %>
					</tr>
				</thead>
				<tbody>
					<tr>
					<%
                      for(String appUser : appUserList ){
                    %>
					<td class="center"><%=appUser%></td>
                    <%
                      }
                    %>
					</tr>
					<tr>
					<%
                      for(String appCompDate : appCompDateList ){
                    %>
					<td class="center"><%=appCompDate%></td>
                    <%
                      }
                    %>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="b-space40 clear">
			<table cellpadding="0" class="table-style-01" summary="">
				<colgroup>
					<col width="13%" />
					<col width="20%" />
					<col width="13%" />
					<col width="20%" />
					<col width="13%" />
					<col width="21%" />
				</colgroup>
				<tbody>
				<tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01420")%><%--문서번호--%></td>
                    <td colspan="3"><%=documentNo%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
                    <td ><%=NumberCodeUtil.getNumberCodeValue("DIVISIONTYPE", docu.getDivisionCode(), messageService.getLocale().toString())%></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
                    <td colspan="3"><a href="javascript:viewVersionHistory('<%=oid%>');"><%=versionInfo%></a></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
                    <td ><a href="javascript:viewHistory('<%=oid%>')"><%=lifeCycleState%></a></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
                    <td colspan="5" ><%=docCatePath%></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%></td>
                    <td colspan="3"><%=documentName%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01530")%><%--보안등급--%></td>
                    <td ><%=NumberCodeUtil.getNumberCodeValue("SECURITYLEVEL", docu.getSecurity(), messageService.getLocale().toString())%>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02431")%><%--작성자--%></td>
                    <td ><%=insUser%>&nbsp;</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
                    <td ><%=insDate%>&nbsp;</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01952")%><%--수정자--%></td>
                    <td ><%=modUser%>&nbsp;</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01951")%><%--수정일자--%></td>
                    <td ><%=updDate%>&nbsp;</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02000")%><%--승인자--%></td>
                    <td ><%=approver%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01999")%><%--승인일자--%></td>
                    <td ><%=approvedDate%></td>
                </tr>
                <tr>
                    <td class="title">내용</td>
                    <td colspan="5">
                        <!--              <textarea readonly name="documentDescription" class="txt_fieldRO" id="textfield3" style="width:655; height:96%;"><%=documentDescription%></textarea>-->
                        <center>
                            <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                            <div id="divView" style="width: 100% px;" class="outline"></div>
                            <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                        </center>
                    </td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02667")%><%--주 첨부파일--%></td>
                    <td colspan="5" ><p><%=iconpath%>&nbsp;<%=urlpath%></p></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                    <%
                        if (docu instanceof FormatContentHolder) {
                            FormatContentHolder holder = (FormatContentHolder) docu;
                            Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
                            if (secondaryFiles.size() > 0) {
                    %>
                    <td colspan="5" ><p>
                            <%
                                for (int i = 0; i < secondaryFiles.size(); i++) {
                                        ContentInfo info = (ContentInfo) secondaryFiles.elementAt(i);

                                        if (info == null) {
                                            iconpath = "";
                                            urlpath = "";
                                        } else {
                                            ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());
                                            appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                                            //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                            //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" + appDataOid;
                                            urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                            urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                                            iconpath = info.getIconURLStr();
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
                    <td colspan="5" ><p>&nbsp;</p></td>
                    <%
                        }
                        } else {
                    %>
                    <td colspan="5" ><p>&nbsp;</p></td>
                    <%
                        }
                    %>
                </tr>
				</tbody>
			</table>
		</div>
	</div>
  </form>
</body>
</html>