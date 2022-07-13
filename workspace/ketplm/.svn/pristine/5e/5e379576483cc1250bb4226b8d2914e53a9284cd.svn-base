<%@page import="e3ps.groupware.company.DepartmentHelper"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.common.util.NumberCodeUtil,
                java.io.File"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page
	import="e3ps.dms.service.KETDmsHelper,
	        e3ps.dms.entity.*,
	        wt.fc.QueryResult,
	        wt.fc.PersistenceHelper,
	        wt.fc.WTObject,
	        wt.doc.WTDocument,
	        wt.doc.WTDocumentMaster,
	        wt.content.*,
	        wt.part.WTPart,
	        wt.org.WTUser,
	        wt.lifecycle.LifeCycleHelper,
	        wt.lifecycle.LifeCycleManaged,
	        wt.session.SessionHelper,
	        e3ps.common.content.*,
	        e3ps.project.*,
	        e3ps.project.beans.*,
	        e3ps.wfm.util.WorkflowSearchHelper,
	        java.util.Vector,
	        java.util.StringTokenizer,
	        org.apache.commons.io.FilenameUtils,
	        e3ps.common.content.ContentUtil,
	        e3ps.common.util.CommonUtil,
	        e3ps.common.util.StringUtil,
	        e3ps.common.obj.ObjectUtil,
	        e3ps.common.util.DateUtil,
	        e3ps.groupware.company.DepartmentHelper,
	        e3ps.groupware.company.Department"%>

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
	
	Kogger.debug("ViewTechDocument", null, null, "ScategoryCode===>" + ScategoryCode);
	Kogger.debug("ViewTechDocument", null, null, "isSer ===>" + isSer);

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
	if (oid != null) {
	    Kogger.debug("ViewTechDocument", null, oid, "view oid ===>" + oid);
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
			    
			
			Kogger.debug("ViewTechDocument", null, null, "loginUser ===>" + loginOid);
			Kogger.debug("ViewTechDocument", null, null, "Creator ===>"
		                    + docu.getIterationInfo().getCreator().toString());

			if (loginOid.equals(docu.getIterationInfo().getCreator().toString()) || CommonUtil.isAdmin() ) {
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
			
			if("Y".equals(docu.getAttribute1()) && !CommonUtil.isMember("설계가이드등록") && !CommonUtil.isMember("설계가이드관리")){
				isDivAuth = false;
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
				if ("Y".equals(docCate.getAttribute1()))
					docCateAttr = docCateAttr + "/ 설계가이드 대상 ";
				
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
				ContentInfo info = ContentUtil.getPrimaryContent(holder);
				ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());

				appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
				String ext = FilenameUtils.getExtension(info.getName());
				
				//urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
		/* 		urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="
						+ CommonUtil.getOIDString(holder)
						+ "&adOid="
						+ appDataOid; */
				urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
				if("Y".equals(docu.getAttribute1())){
					/* if(CommonUtil.isMember("설계가이드관리")){
						urlpath = "<a href=" + urlpath+"&ketCustomFlag=DesignDoc" + " target='_blank'>"+ info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
					}else{
						urlpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')>"	+ info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
					} */
					
					//urlpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')>"    + info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
					//iconpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')><img src='"	+ ContentUtil.getContentIconPath(ext) + "' border=0></a>";
					
					//바로 열기 방식은 사용자 PC환경에 따라 영향도가 심하여 일반 다운로드 형식으로 바꿨음 원복이 필요하면 아래 주석 처리하고 위의 주석을 풀면됨 2019.07.10 황정태
					urlpath = "<a href=javascript:PLM_FILE_DOWNLOAD2('" + urlpath + "')>"
                            + info.getName() + "</a>&nbsp;(&nbsp;"
                            + info.getFileSize() + "&nbsp;)";
					iconpath = info.getIconURLStr();
				}else{
				    urlpath = "<a href=javascript:PLM_FILE_DOWNLOAD2('" + urlpath + "')>"
							+ info.getName() + "</a>&nbsp;(&nbsp;"
							+ info.getFileSize() + "&nbsp;)";
					iconpath = info.getIconURLStr();
				}
				
				
			}
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message","01127")%><%--기술문서 상세조회--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../portal/js/org.js"></script>
<script type="text/javascript" src="../../portal/js/common.js"></script>
<script language="javascript" src="/plm/jsp/dms/js/techdocFile.js"></script>
<%@include file="/jsp/common/multicombo.jsp" %>
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

function updateDocument(oid,isPop,buttenView){
//문서수정화면으로 이동한다.
    if( isPop=="Y"){
        parent.location.href="/plm/jsp/dms/UpdateTechDocument.jsp?cmd=update&oid="+oid+"&isPop="+isPop+"&buttenView="+buttenView;
    }else{
        document.location.href="/plm/jsp/dms/UpdateTechDocument.jsp?cmd=update&oid="+oid+"&nowBlock=<%=SnowBlock%>&page=<%=Spage%>&cmd=search&documentNo=<%=SdocumentNo%>&divisionCode=<%=SdivisionCode%>&categoryCode=<%=ScategoryCode%>&documentName=<%=SdocumentName%>&authorStatus=<%=SauthorStatus%>&creatorName=<%=ScreatorName%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&islastversion=<%=Sislastversion%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T";
    }
}

function reviceDocument(oid,isPop,buttenView)
{
	if(confirm("<%=messageService.getString("e3ps.message.ket_message",
    "03304")%><%--문서를 개정하시겠습니까?--%> \n" + "※<%=messageService.getString("e3ps.message.ket_message",
    "03305")%><%--문서설명란에 개정사유를 반드시 입력해 주시기 바랍니다.--%>")){
        if( isPop=="Y"){
            parent.location.href="/plm/jsp/dms/UpdateTechDocument.jsp?cmd=revice&oid="+oid+"&isPop="+isPop+"&buttenView="+buttenView;
        }else{
            document.location.href="/plm/jsp/dms/UpdateTechDocument.jsp?cmd=revice&oid="+oid;
        }
    }
}

function printDocument(oid){
	
    var url = "/plm/jsp/dms/ViewTechDocumentPop.jsp?buttenView=F&isPop=Y&print=Y&oid="+oid;
    var name = "";
    defaultWidth = 920;
    defaultHeight = 800;
    var opts = ",scrollbars=1,resizable=0"; // "toolbar=0,location=0,directory=0,status=1,menubar=0
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}

function deleteDocument(oid,isPop)
{
	if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01413")%><%--문서를 삭제하시겠습니까?--%>')){
			if (isPop == "Y") {
				parent.location.href = "/plm/servlet/e3ps/TechDocumentServlet?cmd=delete&oid=" + oid;
			} else {
				document.location.href = "/plm/servlet/e3ps/TechDocumentServlet?cmd=delete&oid=" + oid;
			}
		}
	}

	function listDocument(isPop) {
		if (isPop == "Y") {
			parent.location.href = "/plm/jsp/dms/SearchTechDocument.jsp";
		} else {
			//document.location.href="/plm/jsp/dms/SearchTechDocument.jsp";
			history.back();
		}
	}
	
	function fileOpenAjax(oid,appDataOid) {
		var start_con = "";
		var bat_file  = "";
		var filePath  = "";
		var end_con   = "";
	    $.ajax( {
	        url  : "/plm/servlet/e3ps/TechDocumentServlet?cmd=designFileOpen&oid="+oid+"&appDataOid="+appDataOid,
	        type : "POST",
	        async : false,
	        dataType : 'json',
	        success: function(data) {
	        	$.each(data.returnObj, function() {
	        		start_con = this.start_con;
	        		bat_file  = this.bat_file;
	        		filePath  = this.filePath;
	        		fileName  = this.fileName;
	        		end_con   = this.end_con;
                });
	        	
	        	start_con = decode(start_con);
	        	bat_file = decode(bat_file);
	        	filePath = decode(filePath)+fileName;
	        	end_con = decode(end_con);
	        	fnRunConnectFile(start_con, bat_file, filePath, end_con);
	        }
	    });
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
		////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
	function approvalRequest(){
		var docCate = '<%=docCatePath%>';
		if(docCate == '기술문서/규격/KET규격/시험규격'){
			alert("시험규격의 경우 결재요청시 반드시 합의(순차)결재를 지정하십시오.\r\n\r\n\r\n- 합의결재 대상 - \r\n\r\n1.신뢰성평가팀 담당자 2.신뢰성평가팀 팀장");
		}
		goPage('<%=oid%>');
		
	}
	//]]>
</script>
</head>

<body class="<%=(isIframe) ? "" : "popup-background02 popup-space"%>" onload="javascript:fnLoadContent();">
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

		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" <%if (isPop.equals("Y"))
		out.print("style='display:none'");%>>
						<tr>
							<td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="20"><img src="../../portal/images/icon_3.png"></td>
										<td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01397")%><%--문서 상세조회--%></td>

									</tr>
								</table></td>
						</tr>

						<table width="100%" border="0" cellspacing="0" cellpadding="0" <%if ((isPop.equals("Y")) && (buttenView.equals("F")))
		out.print("style='display:none'");%>>
							<tr>
								<td>&nbsp;</td>
								<td align="right"><table border="0" cellspacing="0" cellpadding="0">
										<tr>
											<%
											    if ((lifeCycleState.equals("승인완료")) && (isDivAuth == true) && (isLVer == true)) {
											%>
											<td><table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="10"><img src="../../portal/images/btn_1.gif"></td>
														<td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:reviceDocument('<%=oid%>','<%=isPop%>','<%=buttenView%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684")%><%--개정--%></a></td>
														<td width="10"><img src="../../portal/images/btn_2.gif"></td>
													</tr>
												</table></td>
											<td width="5">&nbsp;</td>
											<td><table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="10"><img src="../../portal/images/btn_1.gif"></td>
														<td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:printDocument('<%=oid%>');" class="btn_blue">프린트<%--프린트--%></a></td>
														<td width="10"><img src="../../portal/images/btn_2.gif"></td>
													</tr>
												</table></td>
											<td width="5">&nbsp;</td>
											<%
											    }
											%>
											<%
											    if (((lifeCycleState.equals("작업중")) || (lifeCycleState.equals("재작업"))) && (isAuth == true)) {
											%>
											<td><table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="10"><img src="../../portal/images/btn_1.gif"></td>
														<td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:updateDocument('<%=oid%>','<%=isPop%>','<%=buttenView%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%></a></td>
														<td width="10"><img src="../../portal/images/btn_2.gif"></td>
													</tr>
												</table></td>
											<td width="5">&nbsp;</td>
											<td><table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteDocument('<%=oid%>','<%=isPop%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table></td>
                                            <td width="5">&nbsp;</td>
											<td><table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="10"><img src="../../portal/images/btn_1.gif"></td>
														<td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:approvalRequest()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778")%><%--결재요청--%></a></td>
														<td width="10"><img src="../../portal/images/btn_2.gif"></td>
													</tr>
												</table></td>
											<td width="5">&nbsp;</td>
											<%
											    }
											%>
											<td><table border="0" cellspacing="0" cellpadding="0" <%if ((isPop.equals("Y")) && (buttenView.equals("T")))
		out.print("style='display:none'");%>>
													<tr>
														<td width="10"><img src="../../portal/images/btn_1.gif"></td>
														<%
														    if (isSer.equals("T")) {
														%>
														<td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
															href="/plm/jsp/dms/SearchTechDocument.jsp?nowBlock=<%=SnowBlock%>&page=<%=Spage%>&cmd=search&documentNo=<%=SdocumentNo%>&divisionCode=<%=SdivisionCode%>&categoryCode=<%=ScategoryCode%>&documentName=<%=SdocumentName%>&authorStatus=<%=SauthorStatus%>&creatorName=<%=ScreatorName%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&islastversion=<%=Sislastversion%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T"
															class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378")%><%--목록--%></a></td>
														<%
														    } else {
														%>
														<td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:listDocument('<%=isPop%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378")%><%--목록--%></a></td>
														<%
														    }
														%>
														<td width="10"><img src="../../portal/images/btn_2.gif"></td>
													</tr>
												</table></td>
										     <td><table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:if(parent){parent.window.close();}else{window.close();}" class="btn_blue">닫기<%--닫기--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table></td>
                                            <td width="5">&nbsp;</td>
										</tr>
									</table></td>
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
								<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420")%><%--문서번호--%></td>
								<td width="350" class="tdwhiteL" colspan="3"><%=documentNo%></td>
								<td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
								<td width="200" class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("DIVISIONTYPE", docu.getDivisionCode(), messageService.getLocale().toString())%></td>
							</tr>
							<tr>
								<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
								<td width="350" class="tdwhiteL" colspan="3"><a href="javascript:viewVersionHistory('<%=oid%>');"><%=versionInfo%></a></td>
								<td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
								<td width="200" class="tdwhiteL0"><a href="javascript:viewHistory('<%=oid%>')"><%=lifeCycleState%></a></td>
							</tr>
							<tr>
								<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
								<td colspan="5" class="tdwhiteL0"><%=docCatePath%></td>
							</tr>
							<tr>
								<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%></td>
								<td width="350" class="tdwhiteL" colspan="3"><%=documentName%></td>
								<td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01530")%><%--보안등급--%></td>
								<td width="200" class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("SECURITYLEVEL", docu.getSecurity(), messageService.getLocale().toString())%>
							</tr>
							<%if("Y".equals(docu.getAttribute1())){
							    Department dept = DepartmentHelper.manager.getDepartment(docu.getAttribute2());
							%>
							<tr>
								<td width="130" class="tdblueL">부서조회권한</td>
								<td width="350" class="tdwhiteL" colspan="3"><%=dept.getName()%> 이하</td>
								<td width="100" class="tdblueL">사용자조회권한</td>
								<td width="200" class="tdwhiteL0"><%=docu.getAttribute4()%> 이상</td>
							</tr>
							<%}%>
							<tr>
								<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431")%><%--작성자--%></td>
								<td width="140" class="tdwhiteL"><%=insUser%>&nbsp;</td>
								<td width="80" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
								<td width="130" class="tdwhiteL"><%=insDate%>&nbsp;</td>
								<td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01952")%><%--수정자--%></td>
								<td width="200" class="tdwhiteL0"><%=modUser%>&nbsp;</td>
							</tr>
							<tr>
								<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951")%><%--수정일자--%></td>
								<td width="140" class="tdwhiteL"><%=updDate%>&nbsp;</td>
								<td width="80" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000")%><%--승인자--%></td>
								<td width="130" class="tdwhiteL"><%=approver%></td>
								<td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999")%><%--승인일자--%></td>
								<td width="200" class="tdwhiteL0"><%=approvedDate%></td>
							</tr>
							<tr>
							    <td width="130" class="tdblueL">내용</td>
                                <td colspan="5" class="tdwhiteL0">
                                    <!--              <textarea readonly name="documentDescription" class="txt_fieldRO" id="textfield3" style="width:655; height:96%;"><%=documentDescription%></textarea>-->
                                    <center>
                                        <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                                        <div id="divView" style="width: 100% px;" class="outline"></div>
                                        <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                                    </center>
                                </td>
                            </tr>
							<tr>
								<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667")%><%--주 첨부파일--%>11</td>
								<td colspan="5" class="tdwhiteL0"><p><%=iconpath%>&nbsp;<%=urlpath%></p></td>
							</tr>
							<tr>
								<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
								<%
								    if (docu instanceof FormatContentHolder) {
										FormatContentHolder holder = (FormatContentHolder) docu;
										Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
										if (secondaryFiles.size() > 0) {
											String ext = "";
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
													    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
													    ext = FilenameUtils.getExtension(info.getName());
													    //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
													    //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" + appDataOid;
                              							urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
													    //urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
													    
													    if("Y".equals(docu.getAttribute1())){
													    	//urlpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')>"	+ info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
													    	//iconpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')><img src='"	+ ContentUtil.getContentIconPath(ext) + "' border=0></a>";
													    	//바로 열기 방식은 사용자 PC환경에 따라 영향도가 심하여 일반 다운로드 형식으로 바꿨음 원복이 필요하면 아래 주석 처리하고 위의 주석을 풀면됨 2019.07.10 황정태
														     urlpath = "<a href=" + urlpath + " target='download'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
							                                 iconpath = info.getIconURLStr();
														}else{
															urlpath = "<a href=" + urlpath + " target='download'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
															iconpath = info.getIconURLStr();
														}

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
						</table>
						</td>
						</tr>
						<tr <%if (isPop.equals("Y"))
		out.print("style='display:none'");%>>
							<td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
						</tr>
					</table>
					<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
					</form>
</body>
</html>
