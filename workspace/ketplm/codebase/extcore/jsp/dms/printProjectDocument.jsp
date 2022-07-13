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

    String loginOid = "";
    Boolean isAuth = false;
    Boolean isDivAuth = false;
    Boolean isSecu = false;
    Boolean isLVer = false;

    // Web Editor
    Object webEditor = null;
    Object webEditorText = null;

    if ( oid != null ) {
        if ( oid.equals("0000") ) {
            //불필요
        }
        else{
            //KETProjectDocument의 oid로 해당 객체의 정보를 화면에 나타낸다.
            docu = (KETProjectDocument)CommonUtil.getObject(oid);

            versionInfo = docu.getVersionIdentifier().getValue();
            isLVer = ObjectUtil.isLatestVersion(docu);

            WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
            loginOid = CommonUtil.getOIDString(loginUser);

            if ( loginOid.equals(docu.getIterationInfo().getCreator().toString()) ) {
                isAuth = true;
                isSecu = true;
            }
            if ( isSecu==false ) {
                isSecu = WorkflowSearchHelper.manager.userInApproval(docu,loginUser.getName());
            }
            if ( CommonUtil.isAdmin() ) {
                
                // HEENEETODO : 관리자는 모두 할 수 있어야 하지 않을까?
                //isSecu = false;
                isSecu = true;
            }

            documentNo =  docu.getDocumentNo();

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
            KETDocumentCategory docCate = null;
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
            }

            E3PSProject proj1 = null;
            QueryResult r4 = PersistenceHelper.manager.navigate(docu, "project" , KETDocumentProjectLink.class );
            while ( r4.hasMoreElements() ) {
                 proj1 = (E3PSProject) r4.nextElement();
                 proj1 = ProjectHelper.getLastProject(proj1.getMaster());
                 if ( isSecu==false ) {
                     isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject)proj1);
                 }
             }

            documentName = docu.getTitle();
            deptName = docu.getDeptName();
            tmpInt = docu.getFirstRegistrationStage();
            firstRegistrationStage = tmpInt.toString();

            tmpInt = docu.getDRCheckPoint();
            dRCheckPoint = tmpInt.toString();
            isBuyerSummit = docu.getIsBuyerSummit();
            //고객제출자료 여부에 따라 buyerCode입력여부가 결정된다.
            if ( isBuyerSummit.equals("1") ) {
                buyerCode = docu.getBuyerCode();

                if ( !StringUtil.checkNull(buyerCode).equals("") ) {
                    StringTokenizer st = new StringTokenizer(buyerCode, ",");
                    String ct="";
                    String bName="";
                    while ( st.hasMoreTokens() ) {
                        ct=st.nextToken();
                        NumberCode nCode1 = (NumberCode)CommonUtil.getObject(ct);

                        if ( nCode1==null ) {
                            bName=bName + "," + ct;
                        }
                        else {
                            bName=bName + "," + nCode1.getName();
                        }
                    }
                    if ( !bName.equals("") ) {
                        buyerCode=bName.substring(1);
                        buyerCode = buyerCode.replace(",","<br>");
                    }
                }
            }
            else {
                buyerCode = "-";
            }

            if ( isDRCheckSheet.equals("true") ) {
                stateDecision = StringUtil.checkNull(docu.getApprovalResult());
                stateComments = StringUtil.checkNull(docu.getLastApprovalComment());
            }

            documentDescription = docu.getDocumentDescription();

            webEditor = docu.getWebEditor();
            webEditorText = docu.getWebEditorText();

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
                    if ( isSecu == false ) {
                        project = ProjectHelper.getLastProject(project.getMaster());
                        isSecu = ProjectUserHelper.manager.isProjectRoleUser((TemplateProject)project);
                    }
                }
            }

            //문서객체로 주첨부파일의 정보를 가져온다.
            if ( docu instanceof FormatContentHolder ) {
                FormatContentHolder holder = (FormatContentHolder)docu;
                ContentInfo info = ContentUtil.getPrimaryContent(holder);
                if ( info != null ) {
                    ContentItem ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());

                    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                    //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                    //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                    urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                    urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                    iconpath = info.getIconURLStr();
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
<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
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
<!--
$(document).ready(function() {
    $.ajax({
        url : "/plm/ext/dms/getApprovalLine.do",
        type : "POST",
        data : {
            pbooid : '<%= oid %>'
        },
        dataType : 'json',
        async : false,
        success : function(data) {
            $("#approvalLineDiv").html(data);
        }
    });
});
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
    //]]>

    function doPrint(){
        $('#buttonTable').hide();
        //window.print();
        //여백설정
        IEPageSetupX.topMargin=0;
        IEPageSetupX.bottomMargin=0;
        //가로설정
        IEPageSetupX.Orientation = 0;
        //머리글 설정
        IEPageSetupX.header="";
        //바닥글 설정
        IEPageSetupX.footer="";
        //배경색
        IEPageSetupX.PrintBackground = true;
        //미리보기
        IEPageSetupX.Preview();
        //인쇄
        //IEPageSetupX.Print();
        
        doneyet();
        
    }

    function doneyet() { 
        $('#buttonTable').show();
    } 

    </script>

    <OBJECT id=IEPageSetupX classid="clsid:41C5BC45-1BE8-42C5-AD9F-495D6C8D7586" codebase="/plm/extcore/js/dashboard/IEPageSetupX.cab#version=1,4,0,3" width=0 height=0><param name="copyright" value="http://isulnara.com">
    </OBJECT>   
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
    <table style="table-layout: fixed;width: 980px" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top"><%-- 
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td background="/plm/portal/images/logo_popupbg.png">
                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <!--  문서 상세조회  -->
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00638") %>개발산출문서</td>
                  </tr>
                </table>
              </td>
              <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
            </tr>
          </table> --%>
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>
          <table id="buttonTable" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
              <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doPrint();" class="btn_blue">인쇄</a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    <td>
                    <td width="5">&nbsp;</td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close()" class="btn_blue">닫기</a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
          <div id="approvalLineDiv"></div>
          <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
              <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00638")%><!-- 개발산출문서 --></td>
            </tr>
            <tr>
              <td class="head_line" colspan="2"></td>
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
              <td colspan=3 class="tdwhiteL"><%=documentNo%></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
              <td class="tdwhiteL0"><%=divisionCode%></td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
              <td colspan="3" class="tdwhiteL"><%=docCatePath%></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03030")%><%--품질확보절차--%></td>
              <td class="tdwhiteL0"><%=docCateAttr%>&nbsp;</td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%></td>
              <td colspan="5" class="tdwhiteL0"><%=documentName%></td>
            </tr>
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
            <%
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
                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00496")%><%--Task명--%></td>
                  </tr>
                  <%
                      QueryResult r3 = PersistenceHelper.manager.navigate(docu, "output", KETDocumentOutputLink.class);
                      if (r3.hasMoreElements()) {
                        while (r3.hasMoreElements()) {
                            po = (ProjectOutput) r3.nextElement();
                            outputOid = CommonUtil.getOIDString(po);

                            E3PSTask task = (E3PSTask) po.getTask();
                            E3PSProject project = (E3PSProject) task.getProject();
                            project = ProjectHelper.getLastProject(project.getMaster());
                            if (isSecu == false) {
                            isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject) project);
                            }

                            pjtOid = CommonUtil.getOIDString(project);
                            taskOid = CommonUtil.getOIDString(task);
                            pjtNo = StringUtil.checkNull(project.getPjtNo());
                            pjtName = StringUtil.checkNull(project.getPjtName());
                            taskName = StringUtil.checkNull(task.getTaskName());
                  %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewProject('<%=pjtOid%>')"><%=pjtNo%>&nbsp;</a></td>
                    <td class="tdwhiteL"><%=pjtName%>&nbsp;</td>
                    <td class="tdwhiteL0"><a href="javascript:viewProject('<%=taskOid%>')"><%=taskName%>&nbsp;</a></td>
                  </tr>
                  <%
                      }
                      }
                  %>
                  <%
                      E3PSProject project = null;
                      QueryResult qr = PersistenceHelper.manager.navigate(docu, "project", KETDocumentProjectLink.class);
                      while (qr.hasMoreElements()) {
                        project = (E3PSProject) qr.nextElement();
                        pjtOid = CommonUtil.getOIDString(project);
                        pjtNo = StringUtil.checkNull(project.getPjtNo());
                        pjtName = StringUtil.checkNull(project.getPjtName());
                        taskName = " ";
                  %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewProject('<%=pjtOid%>')"><%=pjtNo%>&nbsp;</a></td>
                    <td class="tdwhiteL"><%=pjtName%>&nbsp;</td>
                    <td class="tdwhiteL0"><%=taskName%>&nbsp;</td>
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
                      WTPart wtPart = null;
                      String partOid = "";
                      qr = PersistenceHelper.manager.navigate(docu, "part", KETDocumentPartLink.class);
                      if (qr.hasMoreElements()) {
                        while (qr.hasMoreElements()) {
                            wtPart = (WTPart) qr.nextElement();
                            partOid = CommonUtil.getOIDString(wtPart);
                  %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewPart('<%=partOid%>')"><%=wtPart.getNumber()%></a></td>
                    <td class="tdwhiteL"><%=wtPart.getName()%></td>
                  </tr>
                  <%
                      }
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
                                appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();

                                iconpath = info.getIconURLStr();
                                if (isSecu == false) {
                                //iconpath = iconpath.substring(iconpath.indexOf("<img src="), iconpath.indexOf("</a>"));
                                //iconpath = "<a href=JavaScript:alert('권한이&nbsp;없습니다.');>" + iconpath + "</a>";
                                //urlpath = urlpath.substring(urlpath.indexOf("_blank")+8, urlpath.indexOf("</a>&nbsp"));
                                //urlpath = "<a href=JavaScript:alert('권한이&nbsp;없습니다.');>" + urlpath + "</a>";
                                //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" + appDataOid;
                                urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                urlpath = "<a href=JavaScript:alert('" + messageService.getString("e3ps.message.ket_message", "00990")/*권한이 없습니다*/
                                    + "');>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                                } else {
                                //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" + appDataOid;
                                urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
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
            <tr>
              <td colspan="6" class="tdwhiteL0">
                <!--            <textarea readonly name="documentDescription" class="txt_fieldRO" id="textfield3" style="width:98%; height:95%;"><%=documentDescription%></textarea>--> <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                <div id="divView" style="width: 95%;" class="outline"></div> <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</body>
</html>
