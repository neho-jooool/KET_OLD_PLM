<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.beans.TaskHelper"%>
<%@page import="wt.lifecycle.State"%>
<%@page import="wt.fc.collections.WTArrayList"%>
<%@page import="wt.fc.collections.WTCollection"%>
<%@page import="e3ps.common.iba.AttributeData"%>
<%@page import="wt.iba.definition.litedefinition.IBAUtility"%>
<%@page import="e3ps.common.iba.IBAUtil"%>
<%@page import="wt.clients.iba.container.NewValueCreator"%>
<%@page import="wt.iba.definition.litedefinition.AttributeDefDefaultView"%>
<%@page import="wt.epm.util.EPMHelper"%>
<%@page import="e3ps.edm.listener.EDMEventListener"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.vc.Versioned"%>
<%@page import="wt.pds.StatementSpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.groupware.company.DepartmentHelper"%>
<%@page import="wt.vc.Mastered"%>
<%@page import="wt.vc.VersionControlHelper"%>
<%@page import="e3ps.common.obj.ObjectUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.fc.*,wt.content.*,
                  wt.epm.EPMDocument,wt.epm.EPMDocumentMaster,wt.part.WTPart,wt.part.WTPartMaster,
          wt.folder.*,wt.clients.folder.*,wt.util.*"%>
<%@page import = "wt.iba.value.litevalue.AbstractValueView,wt.vc.config.LatestConfigSpec,
                  wt.org.WTUser,wt.session.SessionHelper"%>
<%@page import = "e3ps.common.util.CommonUtil"%>
<%@page import = "e3ps.edm.*,ext.ket.edm.entity.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%@page import = "e3ps.project.ProjectMaster,e3ps.project.E3PSProject,e3ps.project.beans.ProjectHelper"%>
<%@page import = "e3ps.common.content.ContentUtil,
          ext.ket.part.util.PartSpecGetter,
          ext.ket.part.util.PartSpecEnum,
          e3ps.common.util.StringUtil,
          e3ps.edm.beans.EDMHelper,
          e3ps.common.util.DateUtil,
          e3ps.common.content.ContentInfo,
          e3ps.common.code.NumberCode,
          e3ps.wfm.util.WorkflowSearchHelper,
          ext.ket.shared.content.entity.ContentDTO,
          e3ps.common.service.KETCommonHelper
          "%>
<%@page import = "e3ps.ecm.beans.EcmSearchHelper,
          e3ps.ecm.entity.KETMoldChangeOrder,
          e3ps.ecm.entity.KETProdChangeOrder"%>
<%@page import="e3ps.common.util.AuthUtil" %>
<%@page import="ext.ket.edm.approval.internal.EpmApprovalHistoryHandler" %>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%

  // 권한 등 로그를 확인할 때 사용
  boolean enableLogSysOut = false;
  if(enableLogSysOut){
      Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ enable Log System.out.print");
  }

  EDMProperties edmProperties = EDMProperties.getInstance();
  EDMAttributes edmAttributes = EDMAttributes.getInstance();

  WTUser currentUser = (WTUser)SessionHelper.manager.getPrincipal();

  String webAppName = CommonUtil.getWebAppName();

  boolean isAdmin = CommonUtil.isAdmin();
  boolean isOwner = false;
  boolean isLatestRevision = false;


  //String command = StringUtil.trimToEmpty(request.getParameter("command"));
  String frompage = StringUtil.trimToEmpty(request.getParameter("frompage"));
  String oid = StringUtil.trimToEmpty(request.getParameter("oid"));

  String commandSrh = StringUtil.trimToEmpty(request.getParameter("command"));
  String numberSrh = StringUtil.trimToEmpty(request.getParameter("number"));
  String nameSrh = StringUtil.trimToEmpty(request.getParameter("name"));
  String partNumberSrh = StringUtil.trimToEmpty(request.getParameter("partNumber"));

  String stateSrh = StringUtil.trimToEmpty(request.getParameter("state"));
  String latestSrh = StringUtil.trimToEmpty(request.getParameter("latest"));

  String businessTypeSrh = StringUtil.trimToEmpty(request.getParameter("businessType"));
  String devStageSrh = StringUtil.trimToEmpty(request.getParameter("devStage"));
  String categorySrh = StringUtil.trimToEmpty(request.getParameter("category"));
  String cadAppTypeSrh = StringUtil.trimToEmpty(request.getParameter("cadAppType"));
  String isDummyFileSrh = StringUtil.trimToEmpty(request.getParameter("isDummyFile"));

  String createStartSrh = StringUtil.trimToEmpty(request.getParameter("create_start"));
  String createEndSrh = StringUtil.trimToEmpty(request.getParameter("create_end"));
  String modifyStartSrh = StringUtil.trimToEmpty(request.getParameter("modify_start"));
  String modifyEndSrh = StringUtil.trimToEmpty(request.getParameter("modify_end"));

  String creatorSrh = StringUtil.trimToEmpty(request.getParameter("creator"));
  String modifierSrh = StringUtil.trimToEmpty(request.getParameter("modifier"));

  String edmCreateDeptNameSrh = StringUtil.trimToEmpty(request.getParameter("edmCreateDeptName"));
  String edmModifyDeptNameSrh = StringUtil.trimToEmpty(request.getParameter("edmModifyDeptName"));

  String projectNo = StringUtil.trimToEmpty(request.getParameter("projectNo"));

  String descendingSrh = StringUtil.trimToEmpty(request.getParameter("descending"));
  String sortColumnSrh = StringUtil.trimToEmpty(request.getParameter("sortColumn"));
  String sortClassSrh = StringUtil.trimToEmpty(request.getParameter("sortClass"));

  String psizeStrSrh = StringUtil.trimToEmpty(request.getParameter("psize"));
  String cpageStrSrh = StringUtil.trimToEmpty(request.getParameter("cpage"));

  boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));
  
  EPMDocument epm = null;
  try {
    ReferenceFactory rf = new ReferenceFactory();
    epm = (EPMDocument)rf.getReference(oid).getObject();
  }
  catch(Exception e) {
      Kogger.error(e);
  }

  isLatestRevision = VersionHelper.isLatestRevision(epm);

  WTUser creator = (WTUser)epm.getCreator().getObject();
  isOwner = (creator.getName()).equals(currentUser.getName());

  //out.println("<br>sssss : " + EDMHelper.isReferenceEPMExist(epm, EDMHelper.REQUIRED_REFERENCE_MODEL));
  //out.println("<br>xxxx : " + EDMHelper.isReferencedByModelExist(epm, EDMHelper.REQUIRED_REFERENCE_MODEL));


  HashMap ibaValues = EDMAttributes.getIBAValues(epm,Locale.KOREAN);

  String category = "";
  if(ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
    category = EDMEnumeratedTypeUtil.getCADCategory((String)ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),Locale.KOREAN);
  }
  
  // 3D 2D 판단하기
  String cadName = epm.getCADName(); // em450022_pps_assy.drw
  
  Kogger.debug("ViewEPMDocument", null, null, "cadName: " + epm.getCADName());
  boolean is3D = cadName.toUpperCase().endsWith(".PRT")||cadName.toUpperCase().endsWith(".ASM");
  
  wt.fc.Persistable[] reledChanges = null;
  
  // 고도화 안정화 15.1.23 tklee 수정 - 도면 유형 안나오는 문제 수정 => 부품 연결 화면으로 수정
  boolean isNeedConnectPart = false;
  if(epm != null){
     if(ibaValues.get(EDMHelper.IBA_DEV_STAGE)==null){
         if(ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE) == null) { 
             isNeedConnectPart = true;
         }
     }
  }
  isAdmin = isAdmin || "shlee0622".equals(currentUser.getName());// 2022.05.10 ~ 2022.06.10
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01256") %><%--도면 상세 조회--%>(<%=(ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE)==null)? messageService.getString("e3ps.message.ket_message", "01253")/*도면*/:(String)ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE)%>)</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
}

.fixedlayout { layout-grid:both fixed none none; }

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/jquery-1.4.3.min.js"></script>
<!-- <SCRIPT language=JavaScript src="js/jquery-latest.min.js"></SCRIPT> -->
<script language="javascript">
<!--
var $jquery=jQuery.noConflict();

function doModifyPage() {
  document.location.href = "/<%=webAppName%>/jsp/edm/CreateEPMDocument.jsp?oid="+document.forms[0].oid.value;
}

function doViewPart(_poid) {
  var url = "/<%=webAppName%>/jsp/bom/ViewPart.jsp?poid="+_poid;
  newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=100,left=100,width=1024,height=768');
  newWinSPart.focus();
  newWinSPart.location.href = url;
}

function doViewEPM(_oid) {
  var url = "/<%=webAppName%>/jsp/edm/ViewEPMDocument.jsp?oid="+_oid;
  newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=10,left=100,width=800,height=800');
  newWinSPart.focus();
  newWinSPart.location.href = url;
}

function doChangeState(_obj,_oid) {
  if(_obj.value == '') {
    return;
  }

  var param = "";
  param += "command=ChangeState";
  param += "&oid=" + _oid;
  param += "&state=" + _obj.value;
  param += "&terminate=true";

  //({id : this.getAttribute('id')}),

  var url = "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp";

  $jquery.ajax({
    type: "POST",
    url: "/plm/jsp/edm/EDMAjaxAction.jsp",
    data: param,
    async: false,
    error: function() {    alert(" error (function : doChangeState)");  },
    success: function(xml) {
      resultChangeState(xml);
    }
  });
}

function resultChangeState(xmlDoc) {

  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
    return;
  }

  var showElements = xmlDoc.selectNodes("//data_info");
  var l_state = showElements[0].getElementsByTagName("l_state");
  var l_oid = showElements[0].getElementsByTagName("l_oid");


  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03313") %><%--상태 변경 완료했습니다.\n새로고침하시겠습니까?--%>")) {
    return;
  }

  document.location.href="/<%=webAppName%>/jsp/edm/ViewEPMDocument.jsp?oid="+unescape(l_oid[0].text);
}

function doRevisionHistory(_oid) {
  var url = "/<%=webAppName%>/jsp/edm/ViewRevisionHistory.jsp?oid="+_oid;
  newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=100,left=100,width=700,height=600');
  newWinSPart.focus();
  newWinSPart.location.href = url;
}

// CAD2BOM
function doCADtoBOM(_oid) {
  var url = "/<%=webAppName%>/extcore/jsp/bom/KETCad2BomMain.jsp?modelOid="+_oid;
  newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=10,left=100,width=1200,height=600');
  newWinSPart.focus();
  newWinSPart.location.href = url;
}

// 도면 부품 연계 
function connEpmPart(_oid) {
  var url = "/plm/ext/part/base/connEpmPartPopup.do?epmOid="+_oid;
  newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=10,left=100,width=800,height=400');
  newWinSPart.focus();
  newWinSPart.location.href = url;
}

function doRevise(_oid) {
  if(_oid.value == '') {
    return;
  }


  //var url = "/<%=webAppName%>/jsp/edm/BatchRevision.jsp?oid="+_oid;
  //newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=100,left=100,width=700,height=600');
  //newWinSPart.focus();
  //newWinSPart.location.href = url;

  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03287") %><%--개정하시겠습니까?--%>")) {
    return;
  }

  var param = "";
  param += "command=doReviseByView";
  param += "&oid="+_oid;

  $jquery.ajax({
    type: "POST",
    url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
    data: param,
    async: false,
    error: function() {    alert(" error (function : doRevise)");  },
    success: function(xml) {
      rtnRevise(xml);
    }
  });
}

function rtnRevise(xmlDoc) {

  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
    return;
  }

  var showElements = xmlDoc.selectNodes("//data_info");
  var l_isRevisable = showElements[0].getElementsByTagName("l_isRevisable");
  var l_newVersionOid = showElements[0].getElementsByTagName("l_newVersionOid");

  var lisRevisable = unescape(l_isRevisable[0].text);
  if(lisRevisable == 'false') {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01228") %><%--대상 도면이나 모델 중 개정할 수 없는 데이터가 있습니다--%>');
    return;
  }

  alert("<%=messageService.getString("e3ps.message.ket_message", "00689") %><%--개정완료했습니다--%>");

  var lnewVersionOid = unescape(l_newVersionOid[0].text);
  if(lnewVersionOid.length > 0) {
    document.forms[0].oid.value = lnewVersionOid;
    document.forms[0].method="post";
    document.forms[0].action="/<%=webAppName%>/jsp/edm/ViewEPMDocument.jsp";
    document.forms[0].submit();
  }
}

//var $jquery=jQuery.noConflict();

function doDelete(_oid) {


  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01708") %><%--삭제된 데이터는 복구할 수 없습니다. 삭제하시겠습니까?--%>")) {
    return;
  }

  var param = "";
  param += "command=doDelete";
  param += "&oid="+_oid;

  var url = "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp";

  $jquery.ajax({
    type: "POST",
    url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
    data: param,
    async: false,
    error: function() {    alert(" error (function : doDelete)");  },
    success: function(xml) {
      rtnDelete(xml);
    }
  });
}

function rtnDelete(xmlDoc) {

  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
    return;
  }

  alert('<%=messageService.getString("e3ps.message.ket_message", "01696") %><%--삭제 완료했습니다--%>');
  if(opener != null) {
    opener.document.location.reload();
    window.close();
  } else {
    goSearchPage();
    //document.location.href = "/<%=webAppName%>/jsp/edm/SearchEPMDocument.jsp";
  }
}


function goSearchPage() {
  //document.forms[0].command.value="search";
  document.forms[0].method="post";
  document.forms[0].action="/<%=webAppName%>/jsp/edm/SearchEPMDocument.jsp";
  document.forms[0].submit();
}

//ECO 상세조회
function viewEO(oid, _ismold) {
  if(typeof oid != "undefined" && oid != null){
      if(oid.indexOf('KETProdChangeOrder') > 0){
          viewRelProdEco(oid);
      }else{
          viewRelMoldEco(oid);
      }
  } 
}

//제품ECO 상세조회 팝업
function viewRelProdEco(oid) {
  
  //openWindow2(url, "", 1200,680,",scrollbars=no,resizable=no,top=200,left=250,center=yes");
  
  var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid="+oid;
  var name = "";
  var defaultWidth = 1100;
  var defaultHeight = 800;
  var opts = ",toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";  
  getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
  
}

//금형ECO 상세조회 팝업
function viewRelMoldEco(oid) {
    
  //openWindow2(url, "", 1200,680,",scrollbars=no,resizable=no,top=200,left=250,center=yes");
  
  var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid="+oid;
  var name = "";
  var defaultWidth = 1100;
  var defaultHeight = 800;
  var opts = ",toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
  getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
  
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
  var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
  openWindow(url, '',1050,800);
}

// viewable 재생성
function regenerateViewable(oid){

    $jquery.ajax({
        type: 'get',
        async: false,
        url: '/plm/ext/part/base/regeneateVW.do?oid=' + oid,
        success: function (data) {
            if(data != 'Fail'){
                alert('썸네일을 재생성을 시작했습니다. 경우에 따라서 재생성하는 데 몇 분이 걸릴 수 있습니다.');
            }else{
                alert("썸네일을 재생성 에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("썸네일을 재생성 에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
            hideProcessing();
        }
    });
}
 

// -->
</script>
</head>
<body>
<form>
<!-- hidden -->
<input type="hidden" name="oid" value="<%=oid%>">

<!-- search condition begin -->
<input type="hidden" name="command" value="<%=commandSrh%>">
<input type="hidden" name="number" value="<%=numberSrh%>">
<input type="hidden" name="name" value="<%=nameSrh%>">
<input type="hidden" name="partNumber" value="<%=partNumberSrh%>">

<input type="hidden" name="state" value="<%=stateSrh%>">
<input type="hidden" name="latest" value="<%=latestSrh%>">

<input type="hidden" name="businessType" value="<%=businessTypeSrh%>">
<input type="hidden" name="devStage" value="<%=devStageSrh%>">
<input type="hidden" name="category" value="<%=categorySrh%>">
<input type="hidden" name="cadAppType" value="<%=cadAppTypeSrh%>">
<input type="hidden" name="isDummyFile" value="<%=isDummyFileSrh%>">

<input type="hidden" name="create_start" value="<%=createStartSrh%>">
<input type="hidden" name="create_end" value="<%=createEndSrh%>">
<input type="hidden" name="modify_start" value="<%=modifyStartSrh%>">
<input type="hidden" name="modify_end" value="<%=modifyEndSrh%>">

<input type="hidden" name="creator" value="<%=creatorSrh%>">
<input type="hidden" name="modifier" value="<%=modifierSrh%>">

<input type="hidden" name="edmCreateDeptName" value="<%=edmCreateDeptNameSrh%>">
<input type="hidden" name="edmModifyDeptName" value="<%=edmModifyDeptNameSrh%>">

<input type="hidden" name="projectNo" value="<%=projectNo%>">

<input type="hidden" name="descending" value="<%=descendingSrh%>">
<input type="hidden" name="sortColumn" value="<%=sortColumnSrh%>">
<input type="hidden" name="sortClass" value="<%=sortClassSrh%>">

<input type="hidden" name="psize" value="<%=psizeStrSrh%>">
<input type='hidden' name="cpage" value="<%=cpageStrSrh%>">
<!-- search condition end -->

<!-- hidden end -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody id='popup_log' style="display:none;">
  <tr>
    <td height="50" valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none":""%>">
        <tr>
          <td background="../../portal/images/logo_popupbg.png">
            <table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01256") %><%--도면 상세 조회--%>(<%=(ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE)==null)? messageService.getString("e3ps.message.ket_message", "01253")/*도면*/:(String)ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE)%>)</td>
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table>
    </td>
  </tr>
  </tbody>
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"  style="<%=(isIframe)?"display:none":""%>">
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <%
                  String state = "";
                  if(epm != null) {
                    state = epm.getState().getState().toString();
                  }
                %>
                <%  if(isAdmin) { %>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>

                      <%  Vector lcStates = null;
                        try {
                          lcStates = wt.lifecycle.LifeCycleHelper.service.getPhaseTemplates((wt.lifecycle.LifeCycleTemplate)epm.getLifeCycleTemplate().getObject());
                        }
                        catch(Exception e) {
                            Kogger.error(e);
                          lcStates = null;
                        }
                      %>
                      <td width="10">
                        <select name='state' class="fm_jmp" onChange="javascript:doChangeState(this,'<%=oid%>');">
                          <option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                          <%  if(lcStates != null) {
                              wt.lifecycle.PhaseTemplate pt = null;
                              wt.lifecycle.State lcState = null;
                              for(int i = 0; i < lcStates.size(); i++) {
                                pt = (wt.lifecycle.PhaseTemplate)lcStates.get(i);
                                lcState = pt.getPhaseState();
                          %>
                                <option value="<%=lcState.toString()%>"><%=lcState.getDisplay(messageService.getLocale())%></option>
                          <%
                              }
                            }
                          %>
                        </select>
                      </td>
                    </tr>
                  </table>
                </td>
                <%  } %>
                
                <%-- CAD2BOM --%>
                 <%  
                 if(enableLogSysOut){
                     
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ is3D :"  + is3D);
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ state :"  + state);
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ version :"  + epm.getVersionIdentifier().getSeries().getValue());
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ isLatestRevision :"  + isLatestRevision); // check in 안된 wrk 상태에서는 false가 떨어짐. c/o, c/i은 true입
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ isOwner :"  + isOwner);
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ isAdmin :"  + isAdmin);
                     
                 }
                 
                        if( is3D && ("INWORK".equals(state) || "REWORK".equals(state))
                        && "0".equals(epm.getVersionIdentifier().getSeries().getValue()) 
                        && isLatestRevision && ( isOwner || isAdmin ) ) {
                 %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:doCADtoBOM('<%=oid%>');" class="btn_blue">CADtoBOM</a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%
                    }
                %>
                <%-- viewable(썸네일) 재생성 --%>
                <%--  if(isAdmin) { --%>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:regenerateViewable('<%=oid%>');" class="btn_blue">썸네일 재생성</a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%--  } --%>
                <%-- 이력 --%>
                <%  if(isAdmin) { %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:doRevisionHistory('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02270") %><%--이력--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  } %>
                
               <%-- 부품연결 --%>
                 <%  
                 if(enableLogSysOut){
                     
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ isNeedConnectPart :"  + isNeedConnectPart);
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ isLatestRevision :"  + isLatestRevision); // check in 안된 wrk 상태에서는 false가 떨어짐. c/o, c/i은 true입
                     Kogger.debug("ViewEPMDocument", null, null, "######$$$$$ isOwner :"  + isOwner);
                     
                 }
                 
                    if(isLatestRevision && isAdmin ) {
                 %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:connEpmPart('<%=oid%>');" class="btn_blue">부품연계</a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%
                    }
                %>
                
                <%-- 개정 --%>
                <% 
                    /* 
                          개정버튼이 활성화되어 있으면 안됨.(개정은 설변을 통해서만 가능하다)
                          개정은 Admin 권한 
                        => 수정안 : 그러나 개발검토도면은 여기서 개정을 해서 결재를 올리기로 했음.
                    */
                    if( (edmProperties.isReleasedState(state)
                    && isLatestRevision
                    && ("DEV_REVIEW_DRAWING".equals(category) ) // || "PRODUCT_REFERENCE_DRAWING".equals(category)
                    && ( edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString()) || (isAdmin) ))
                    ) {
                %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:doRevise('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  } %>

                <%-- 수정 --%>
                <%
                  boolean isRefedModel = EDMHelper.isRefedModel(epm);
                  Department dept = new PeopleData((currentUser)).department;
                %>
                <%  //if( dept != null && ((!dept.getName().equals("연구지원팀") && isAdmin) || (("INWORK".equals(state) || "REWORK".equals(state)))
                    if( dept != null && ((isAdmin) || (("INWORK".equals(state) || "REWORK".equals(state)))
                    && ( edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())
                      || (!edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())
                        && ("UG".equals(epm.getAuthoringApplication().toString())||("PROE".equals(epm.getAuthoringApplication().toString())&&"CUSTOMER_DRAWING".equals(category.trim())))
                        && (category.trim().length() > 0)
                        && !isRefedModel)
                      )
                    && isLatestRevision
                    && isOwner)) {
                %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:doModifyPage();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01286") %><%--도면수정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  } %>
                  <%-- 결재요청 --%>
                <%  boolean ischange = EDMHelper.isECAEpmDocLink(epm, true);//설계변경 중인 도면 여부.
                %>
                <%  if( ("INWORK".equals(state)|| "REWORK".equals(state))
                    && (category.length() > 0)
                    && !isRefedModel
                    && ("DEV_REVIEW_DRAWING".equals(category) || "PRODUCT_REFERENCE_DRAWING".equals(category))
                    && (ischange == false)
                    && (isAdmin || isOwner)) { %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:goPage('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  } %>
                   <%-- 결재내역 --%>
                <%  if(!"INWORK".equals(state)) { %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:viewHistory('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00758") %><%--결재내역--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  } %>
                    <%-- 삭제 --%>
                <%  if( ("INWORK".equals(state) || "REWORK".equals(state))
                    && isLatestRevision
                    && edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())
                    && (ischange == false)
                    && (isAdmin || isOwner) ) {
                       
                        /*  
                               승인안된 ECO에 연결되어 있을 경우 삭제할 수 없다.
                        */
                        boolean isCanDeleteByECO = true;
                        wt.fc.Persistable[] persistables = null;
                        if(reledChanges == null){
                            reledChanges = ext.ket.edm.approval.KetEpmApprovalHelper.service.getRelatedEcoList(oid);
                            persistables = reledChanges;
                        }else{
                            persistables = reledChanges;
                        }
                        int persistablesSize = (persistables != null) ? persistables.length : 0;
                        for(int i=0; i < persistablesSize; i++) {
                          wt.change2.WTChangeOrder2 eco = (wt.change2.WTChangeOrder2) persistables[i];
                          wt.lifecycle.State ecoState = eco.getState().getState();
                          if(!ecoState.equals(wt.lifecycle.State.toState("APPROVED"))) {
                              isCanDeleteByECO = false;
                              break;
                          }
                        }
                        
                        if(isCanDeleteByECO) {   
                %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:doDelete('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01281") %><%--도면삭제--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  
                        } 
                    } 
                
                %>
                <%-- 도면 목록 --%>
                <%  if( "SearchPage".equals(frompage) ) { %>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:goSearchPage();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01272") %><%--도면목록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  } %>
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <col class="fixedlayout" width="100">
      <col class="fixedlayout" width="290">
      <col class="fixedlayout" width="100">
      <col class="fixedlayout" width="290">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
          <td colspan="2" class="tdwhiteL0">
          <%
            NumberCode bizCode0 = (NumberCode)EDMHelper.getBizType(epm);
            if(bizCode0 != null) {
              out.print(bizCode0.getName());
            } else {
              out.print("&nbsp;");
            }
          %>
          </td>
          <td rowspan="<%if(ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION) != null){%>7<%}else{%>6<%}%>" class="tdwhiteL0">
            <table width="100%" height="98%" border="0" align="center" cellpadding="0" cellspacing="10" class="table_border">
              <tr>
                <td align="center" valign="middle">
                <%if(AuthUtil.isContentSecu(oid)) {%>
                  <%--
                  <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
                    <jsp:param name="oid" value=""/>
                  </jsp:include>
                       <iframe marginwidth="0" marginheight="10" scrolling="no"  width="300" height="200"  src="/Windchill/extcore/jsp/caddoc/thumnail.jsp?oid=< broker.getOid() >" frameborder="0"></iframe>
                   </td>
                   --%>
                   <iframe marginwidth="0" marginheight="10" scrolling="no"  width="300" height="200"  src="/plm/jsp/edm/thumnail.jsp?oid=<%=oid%>" frameborder="0"></iframe>
                <%}else{%>
                  <jsp:include page="/jsp/edm/thumbImg.jsp" flush="false">
                    <jsp:param name="oid" value="<%=oid%>"/>
                  </jsp:include>
                <%} %>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
          <td colspan="2" class="tdwhiteL0"><%=epm.getNumber()%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
          <td colspan="2" class="tdwhiteL0"><%=epm.getName()%></td>
        </tr>
        <tr>
          <td class="tdblueL">Rev<%--양산도면버전--%></td>
          <td colspan="2" class="tdwhiteL0"><a href="javascript:;" onclick="javascript:viewVersionHistory('<%=oid%>');"><%=(ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION)==null)? "&nbsp;":(String)ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION)%></a></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></td>
          <td colspan="2" class="tdwhiteL0"><%=(ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE)==null)? "&nbsp;":(String)ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE)%></td>
        </tr>
        <!--  2013.02.07 shkim 보안등급 추가 -->
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01530") %><%--보안등급--%></td>
          <% if(ibaValues.get(EDMHelper.IBA_SECURITY) != null) { %>
          <td colspan="2" class="tdwhiteL0"><%=("S2".equals((String)ibaValues.get(EDMHelper.IBA_SECURITY))? messageService.getString("e3ps.message.ket_message", "01225") /*대내비*/ : messageService.getString("e3ps.message.ket_message", "01234") /*대외비*/ ) %></td>
          <%}else{ %>
          <td colspan="2" class="tdwhiteL0">&nbsp;</td>
          <%} %>
          </td>
         </tr>
         <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
          <td colspan="3" class="tdwhiteL0">
          <%  ProjectMaster pjtMst = (ProjectMaster)EDMHelper.getProject(epm);
              E3PSProject  project = null; 
            if(pjtMst != null) {
               project = ProjectHelper.getProject(pjtMst.getPjtNo());
          %>
              <a href="javascript:;" onclick="javascript:viewProjectPopup('<%=PersistenceHelper.getObjectIdentifier(project).getStringValue()%>');"><%=project.getPjtNo()%></a>
          <%
            } else {
              out.print("&nbsp;");
            }
          %>
          <%

          //  HashMap viewMap = EDMAttributes.getAttributeValues(epm,Locale.KOREAN);
          //out.println(viewMap);
          //String s = EDMHelper.getCADManageType(epm, viewMap);
          //out.println(s);
          %>
          </td>
        </tr>
         <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
          <td colspan="3" class="tdwhiteL0">
          <%  
            if( project!= null) {
          %>
              <%=project.getPjtName()%>
          <%
            } else {
              out.print("&nbsp;");
            }
          %>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01264") %><%--도면구분--%></td>
          <td class="tdwhiteL"><%=(ibaValues.get(EDMHelper.IBA_DEV_STAGE)==null)? "&nbsp;":(String)ibaValues.get(EDMHelper.IBA_DEV_STAGE)%></td>
          <td class="tdblueL" style="layout-grid:both fixed none none;"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
          <td class="tdwhiteL0"><%=(ibaValues.get(EDMHelper.IBA_CAD_CATEGORY)==null)? "&nbsp;":(String)ibaValues.get(EDMHelper.IBA_CAD_CATEGORY)%></td>
        </tr>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
          <td class="tdwhiteL">
          <%
            PeopleHelper.manager.getPeople(epm.getCreatorName());
            String createDeptName = "";
            EDMUserData ud = EDMHelper.getEDMUserData(epm);
            //out.println(CommonUtil.getOIDString(ud));
            if( (ud != null) && (ud.getCreatorDeptName() != null) ) {
              createDeptName = ud.getCreatorDeptName();
            }
            if(createDeptName.trim().length()==0) { out.print("&nbsp;"); }else{ out.print(createDeptName.trim()); }
          %>
          </td>
                    <td class="tdblueL" style="layout-grid:both fixed none none;"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td class="tdwhiteL0"><%=epm.getCreatorFullName()%></td>
        </tr>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
          <td class="tdwhiteL"><%=DateUtil.getDateString(epm.getCreateTimestamp(),"d")%></td>
          <td class="tdblueL" style="layout-grid:both fixed none none;"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td class="tdwhiteL0">
            <a href="javascript:;" onclick="javascript:viewHistory('<%=oid%>');">
            <%=epm.getState().getState().getDisplay(messageService.getLocale())%>
            </a>
          </td>
        </tr>
        <%  if(true) { //if(edmProperties.isReleasedState(state)) {

            String approver = "-";
            String approvedDate = "-";

            KETEpmApprovalHis approvalHis = ext.ket.edm.approval.KetEpmApprovalHelper.service.getApprovalHis( epm ); // getSavedPbo  getPbo
             

            //out.println(State.toState(epm.getState().toString()));

            if( approvalHis != null ) {
              approver = (approvalHis.getApproverName()== null)? "&nbsp;":approvalHis.getApproverName();
              approvedDate = (approvalHis.getApprovalDate() == null)? "&nbsp;":DateUtil.getTimeFormat(approvalHis.getApprovalDate(), "yyyy-MM-dd HH:mm:ss");
            }
        %>
            <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
              <td class="tdwhiteL">
                <%if(edmProperties.isReleasedState(state)){%><%=approver%><%}else{%>미승인상태<%}%>
              </td>
                            <td class="tdblueL" style="layout-grid:both fixed none none;"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
              <td class="tdwhiteL0">
                <%if(edmProperties.isReleasedState(state)){%><%=approvedDate%><%}else{%>미승인상태<%}%>
              </td>
            </tr>
        <%  } %>

        <%if(isAdmin){%>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
          <td class="tdwhiteL"><%=epm.getOwnerApplication().getDisplay(messageService.getLocale())%></td>
                    <td class="tdblueL" style="layout-grid:both fixed none none;"><%=messageService.getString("e3ps.message.ket_message", "02245") %><%--응용프로그램--%></td>
          <td class="tdwhiteL0"><%=epm.getAuthoringApplication().getDisplay(messageService.getLocale())%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01402") %><%--문서 카테고리--%></td>
          <td class="tdwhiteL">
          <%  if(epm.getDocType() != null) { out.print(epm.getDocType().getDisplay(messageService.getLocale())); }else{ out.print("&nbsp;"); } %>
          -
          <%  if(epm.getDocSubType() != null) { out.print(epm.getDocSubType().getDisplay(messageService.getLocale())); }else{ out.print("&nbsp;"); } %>
          </td>
          <td class="tdblueL" style="layout-grid:both fixed none none;"><%=messageService.getString("e3ps.message.ket_message", "03001") %><%--폴더 위치--%></td>
          <td class="tdwhiteL0" title="<%=EDMFolderUtil.getFolderLocation(epm)%>"><%=EDMFolderUtil.getFolderLocation(epm)%></td>
        </tr>
        <%}%>

        <%  List<ContentDTO> primaryFiles = new ArrayList<ContentDTO>();
        List<ContentDTO> gerberFiles = new ArrayList<ContentDTO>();
        List<ContentDTO> secondaryFiles = new ArrayList<ContentDTO>();

          ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(epm);
          Vector cntFiles = ContentHelper.getContentListAll(holder);
          for(int i = 0; i < cntFiles.size(); i++) {
            ContentItem item = (ContentItem)cntFiles.get(i);

            if( !(item.getRole().equals(ContentRoleType.PRIMARY) || item.getRole().equals(ContentRoleType.SECONDARY)) ) {
              continue;
            }

            if(item instanceof ApplicationData) {
              ApplicationData appData = (ApplicationData)item;
              if( ContentRoleType.PRIMARY.equals(appData.getRole()) ) {
                primaryFiles.add(new ContentDTO(holder, item));
                continue;
              }

              if( (appData.getDescription() != null) && "gerber".equals(appData.getDescription()) ) {
                gerberFiles.add(new ContentDTO(holder, item));
                continue;
              }
              secondaryFiles.add(new ContentDTO(holder, item));
            }
          }
        %>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02666") %><%--주 도면 첨부--%></td>
          <td <%if(!"DEV_REVIEW_DRAWING".equals(category)){%>colspan="3"<%}%> class="<%if(!"DEV_REVIEW_DRAWING".equals(category)){%>tdwhiteL0<%}else{%>tdwhiteL<%}%>">
            <div style="border:0;padding:0;margin:0;"><ul style="margin:0;padding:0;list-style-type:none;">
            <%  if(primaryFiles.size()==0) {
                out.print("<li>&nbsp;</li>");
              } else {
                for(int i = 0; i < primaryFiles.size(); i++) {
                    ContentDTO cntInfo = primaryFiles.get(i);
                    String fileCntName = cntInfo.getName();
                    if("{$CAD_NAME}".equals(fileCntName)){
                       fileCntName = epm.getCADName();
                    }
                        
            %>
                  <li>
                      <a href="<%=cntInfo.getDownURLStr()%>" target='download'><%=fileCntName%></a>
                  </li>
            <%    }
              }
            %>
            </ul></div>
          </td>
          <%
            if("DEV_REVIEW_DRAWING".equals(category)) {
              String isDummyFile = "N";
              if(ibaValues.get(EDMHelper.IBA_DUMMY_FILE) != null) {
                isDummyFile = (String)ibaValues.get(EDMHelper.IBA_DUMMY_FILE);
              }
          %>
              <td class="tdblueL" style="layout-grid:both fixed none none;"><%=messageService.getString("e3ps.message.ket_message", "00173") %><%--Dummy File--%></td>
              <td class="tdwhiteL0">
                <input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_YES%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_YES)){%>checked<%}%> disabled>예
                <input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_NO%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_NO)){%>checked<%}%> disabled>아니오
              </td>
          <%
            }
          %>
        </tr>

        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01283") %><%--도면설명--%></td>
          <td colspan="3" class="tdwhiteL0" style="height:100;">
            <textarea class="txt_field" cols="105" rows="10" style="width:100%; height:96%" readOnly><%=(epm.getDescription()==null)? "":epm.getDescription()%></textarea>
          </td>
        </tr>
        <%  if(gerberFiles.size() > 0) {//if(edmProperties.isCADCatsByEcad(category)) {
        %>
            <tr>
              <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00244") %><%--Gerber 데이터--%></td>
              <td colspan="3" class="tdwhiteL0" valign='top'>
                <div width="680" style="border:0;padding:0;margin:0;"><ul style="margin:0;padding:0;list-style-type:none;">
                <%  if(gerberFiles.size()==0) {
                    out.print("<li>&nbsp;</li>");
                  } else {
                    for(int i = 0; i < gerberFiles.size(); i++) {
                    ContentDTO cntInfo = gerberFiles.get(i);
                %>
                              <li>
                              <a href="<%=cntInfo.getDownURLStr()%>" target='download'><%=cntInfo.getName()%></a>
                              </li>
                <%    }
                  }
                %>
                </ul></div>
              </td>
            </tr>
        <%  } %>
        <%  if(secondaryFiles.size() > 0) { %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01537") %><%--부도면 첨부--%></td>
          <td colspan="3" class="tdwhiteL0" style="height:<%=24*(secondaryFiles.size()+1)%>;" valign='top'>
            <div width="680" style="border:0;padding:0;margin:0;"><ul style="margin:0;padding:0;list-style-type:none;">
            <%  if(secondaryFiles.size()==0) {
                out.print("<li>&nbsp;</li>");
              } else {
                for(int i = 0; i < secondaryFiles.size(); i++) {
                    ContentDTO cntInfo = secondaryFiles.get(i);
            %>
                          <li>
                            <a href="javascript:PLM_FILE_DOWNLOAD2('<%=cntInfo.getDownURLStr()%>');"><%=cntInfo.getName()%></a>
                          </li>
            <%    }
              }
            
            %>
            </ul></div>
        </tr>
        <%  } %>


        <!-- 3D 참조 모델인 경우 시작 -->
        <%if(isAdmin){ %>
          <%
            if("EPM".equals(epm.getOwnerApplication().toString())) {
              Vector refedbys = EDMHelper.getReferencedBy(epm,true);
              Vector refes = EDMHelper.getReferences(epm,true);

              if( (refedbys.size() > 0) || (refes.size() > 0) ) {
        %>
              <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02763") %><%--참조도면--%></td>
                <td colspan="3" class="tdwhiteL0">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="210" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                      <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                      <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                            <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                            <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                    </tr>
                    <%  EPMDocument drawing = null;
                      for(int i = 0; i < refedbys.size(); i++) {
                        drawing = (EPMDocument)refedbys.get(i);
                    %>
                        <tr>
                          <td width="210" class="tdwhiteM"><%if(drawing==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(drawing).getStringValue()%>');"><%=drawing.getNumber()%></a><%}%></td>
                          <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(drawing==null)? "&nbsp;":drawing.getName()%></nobr></div></td>
                          <td width="50" class="tdwhiteM"><%=(drawing==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(drawing, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                          <td width="110" class="tdwhiteM"><%=(drawing==null)? "&nbsp;":drawing.getCreatorFullName()%></td>
                          <td width="110" class="tdwhiteM0"><%=(drawing==null)? "&nbsp;":DateUtil.getDateString(drawing.getPersistInfo().getCreateStamp(),"d")%></td>
                        </tr>
                    <%  } %>
                    <%
                      for(int i = 0; i < refes.size(); i++) {
                        drawing = (EPMDocument)refes.get(i);
                    %>
                        <tr>
                          <td width="210" class="tdwhiteM"><%if(drawing==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(drawing).getStringValue()%>');"><%=drawing.getNumber()%></a><%}%></td>
                          <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(drawing==null)? "&nbsp;":drawing.getName()%></nobr></div></td>
                          <td width="50" class="tdwhiteM"><%=(drawing==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(drawing, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                          <td width="110" class="tdwhiteM"><%=(drawing==null)? "&nbsp;":drawing.getCreatorFullName()%></td>
                          <td width="110" class="tdwhiteM0"><%=(drawing==null)? "&nbsp;":DateUtil.getDateString(drawing.getPersistInfo().getCreateStamp(),"d")%></td>
                        </tr>
                    <%  } %>
                  </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                </td>
              </tr>

        <%
              }

            }
          %>

        <%
          ArrayList refedDrawings = EDMHelper.getReferenceEPMs(epm, -1);
          if(refedDrawings.size() > 0) {
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01239") %><%--대표도면--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="210" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
              </tr>
              <%  EPMDocument drawing = null;
                for(int i = 0; i < refedDrawings.size(); i++) {
                  drawing = (EPMDocument)((Object[])refedDrawings.get(i))[1];
              %>
                  <tr>
                    <td width="210" class="tdwhiteM"><%if(drawing==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(drawing).getStringValue()%>');"><%=drawing.getNumber()%></a><%}%></td>
                    <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(drawing==null)? "&nbsp;":drawing.getName()%></nobr></div></td>
                    <td width="50" class="tdwhiteM"><%=(drawing==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(drawing, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                    <td width="110" class="tdwhiteM"><%=(drawing==null)? "&nbsp;":drawing.getCreatorFullName()%></td>
                    <td width="110" class="tdwhiteM0"><%=(drawing==null)? "&nbsp;":DateUtil.getDateString(drawing.getPersistInfo().getCreateStamp(),"d")%></td>
                  </tr>
              <%  } %>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%  } %>

        <%  ArrayList refsParts = null;
          try {
            refsParts = EDMHelper.getReferencedByParts(epm,edmProperties.getReferenceTypeForModel(null),-1);
          }
          catch(Exception e) {
              Kogger.error(e);
          }

          if(refsParts.size() > 0) {
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01242") %><%--대표부품--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="210" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
              </tr>
              <%  WTPart refsPart = null;
                for(int i = 0; i < refsParts.size(); i++) {
                  refsPart = (WTPart)((Object[])refsParts.get(i))[1];
              %>
                  <tr>
                    <td width="210" class="tdwhiteM"><%if(refsPart==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewPart('<%=PersistenceHelper.getObjectIdentifier(refsPart).getStringValue()%>');"><%=refsPart.getNumber()%></a><%}%></td>
                    <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(refsPart==null)? "&nbsp;":refsPart.getName()%></nobr></div></td>
                    <td width="50" class="tdwhiteM"><%=(refsPart==null)? "&nbsp;":StringUtil.checkNull(PartSpecGetter.getPartSpec(refsPart, PartSpecEnum.SpPartRevision))%></td>
                    <td width="110" class="tdwhiteM"><%=(refsPart==null)? "&nbsp;":refsPart.getCreatorFullName()%></td>
                    <td width="110" class="tdwhiteM0"><%=(refsPart==null)? "&nbsp;":DateUtil.getDateString(refsPart.getPersistInfo().getCreateStamp(),"d")%></td>
                  </tr>
              <%  } %>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%  } %>

        <%}%>

        <!-- 3D 참조 모델인 경우 끝 -->

        <%  if(edmProperties.isRefModel(category) && edmProperties.isNonPart(category)) {
            ArrayList modelArry = EDMHelper.getReferencedByModels(epm,EDMHelper.REQUIRED_REFERENCE_MODEL);
            if(modelArry.size() > 0) {
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00902") %><%--관련 3D모델--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01372") %><%--모델번호--%></td>
                <td width="240" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01371") %><%--모델명--%></td>
                <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
              </tr>
              <%  EPMDocument model = null;
                for(int i = 0; i < modelArry.size(); i++) {
                  model = (EPMDocument)((Object[])modelArry.get(i))[1];
              %>
                  <tr>
                    <td height="23" class="tdwhiteM"><%if(model==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(model).getStringValue()%>');"><%=model.getNumber()%></a><%}%></td>
                    <td class="tdwhiteL"><div class="ellipsis" style="width:240;"><nobr><%=(model==null)? "&nbsp;":model.getName()%></nobr></div></td>
                    <td class="tdwhiteM"><%=(model==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(model, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                    <td class="tdwhiteM"><%=(model==null)? "&nbsp;":model.getCreatorFullName()%></td>
                    <td class="tdwhiteM"><%=(model==null)? "&nbsp;":DateUtil.getDateString(model.getPersistInfo().getCreateStamp(),"d")%></td>
                  </tr>
              <%  }
              %>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%    }
          }
        %>


        <%
          WTPart part = null;
          EPMDocument model = null;
          ArrayList relateds = null;
          try {
            relateds = EDMHelper.getReferencedByParts(epm,edmProperties.getReferenceType(category),EDMHelper.REQUIRED_STANDARD);
            if( (relateds != null) && (relateds.size() > 0) ) {
              part = (WTPart)((Object[])relateds.get(0))[1];
            }

            if(part != null) {
              model = EDMHelper.getAssociatedModel(epm, part,edmProperties.getReferenceType(category), EDMHelper.REQUIRED_STANDARD);
            }
          }
          catch(Exception e) {
              Kogger.error(e);
          }
        %>
        <%  if(part != null) {
            String pnumb = part.getNumber();
            String pname = part.getName();
            String pver = "";
            String pverKet = "";
            try {
              pver = wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue();
              pverKet = StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision));
            }
            catch(Exception e) {
            Kogger.error(e);
            }

            String mnumb = "";
            String mname = "";
            String mver = "";
            String mverKet = "";
            if(model != null) {
              mnumb = model.getNumber();
              mname = model.getName();

              try {
                mver = wt.vc.VersionControlHelper.getVersionIdentifier(model).getValue();
                mverKet = StringUtil.checkNull(IBAUtil.getAttrValue(model, EDMHelper.IBA_MANUFACTURING_VERSION));
              }
              catch(Exception e) {
              Kogger.error(e);
              }
            }
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01242") %><%--대표부품--%><br><%=messageService.getString("e3ps.message.ket_message", "00051") %><%--3D 모델--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03022") %><%--품번--%></td>
                <td width="240" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
                <td width="50"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00052") %><%--3D 모델 번호--%></td>
                <td width="50"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
              </tr>

              <tr>
                <td width="160" height="23" class="tdwhiteM"><%if(pnumb.length()==0){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewPart('<%=PersistenceHelper.getObjectIdentifier(part).getStringValue()%>');"><%=pnumb%></a><%}%></td>
                <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:240;"><nobr><%=(pname.length()==0)? "&nbsp;":pname%></nobr></div></td>
                <td width="50" class="tdwhiteM"><%=(pverKet.length()==0)? "&nbsp;":pverKet%></td>
                <td width="160" class="tdwhiteM"><%if(mnumb.length()==0){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(model).getStringValue()%>');"><%=mnumb%></a><%}%></td>
                <td width="50" class="tdwhiteM"><%=(mverKet.length()==0)? "&nbsp;":mverKet%></td>
              </tr>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%  } %>

        <%
          ArrayList relatedParts = null;
          try {
            relatedParts = EDMHelper.getReferencedByParts(epm,edmProperties.getReferenceType(category),EDMHelper.REQUIRED_RELATED);
          }
          catch(Exception e) {
              Kogger.error(e);
          }
        %>
        <%
          //relateds = EDMHelper.getPartToEPMLink(epm,EDMHelper.REFERENCE_TYPE_RELATED,EDMHelper.REQUIRED_RELATED);
          if(relatedParts.size() > 0) {
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00934", "<br>") %><%--관련부품{0}[3D 모델]--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03022") %><%--품번--%></td>
                <td width="240" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
                <td width="50"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00052") %><%--3D 모델 번호--%></td>
                <td width="50"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
              </tr>
              <%  WTPart relatedPart0 = null;
                EPMDocument relatedModel0 = null;

                ArrayList models = null;
                for(int i = 0; i < relatedParts.size(); i++) {
                  relatedPart0 = (WTPart)((Object[])relatedParts.get(i))[1];
                  relatedModel0 = EDMHelper.getAssociatedModel(epm, relatedPart0,edmProperties.getReferenceType(category), EDMHelper.REQUIRED_RELATED);
              %>
                  <tr>
                    <td width="160" height="23" class="tdwhiteM"><%if(relatedPart0==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewPart('<%=PersistenceHelper.getObjectIdentifier(relatedPart0).getStringValue()%>');"><%=relatedPart0.getNumber()%></a><%}%></td>
                    <td width="240" class="tdwhiteL"><div class="ellipsis" style="width:240;"><nobr><%=(relatedPart0==null)? "&nbsp;":relatedPart0.getName()%></nobr></div></td>
                    <td width="50" class="tdwhiteM"><%=(relatedPart0==null)? "&nbsp;":StringUtil.checkNull(PartSpecGetter.getPartSpec(relatedPart0, PartSpecEnum.SpPartRevision))%></td>
                    <td width="160" class="tdwhiteM"><%if(relatedModel0==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(relatedModel0).getStringValue()%>');"><%=relatedModel0.getNumber()%></a><%}%></td>
                    <td width="50" class="tdwhiteM"><%=(relatedModel0==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(relatedModel0, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                  </tr>
              <%  } %>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%  } %>

        <%
          if("ECAD_DRAWING".equals(category)) {
            EPMDocument ecadPCB = null;
            EPMDocument ecadSCH = null;
            EPMDocument ecadDWG = null;
            if( (epm != null) && (part != null) ) {
              ArrayList ecads = EDMHelper.getAssociatedDocsByECAD(epm, part);
              if(ecads.size() <= 1)
              {
                ecads = EDMHelper.getAssociatedDocsByECAD2(epm, part);
              }
              for(int i = 0; i < ecads.size(); i++) {
                EPMDocument ecad = (EPMDocument)ecads.get(i);
                if("PADS".equals(ecad.getAuthoringApplication().toString())) {
                  ecadPCB = ecad;
                }
                else if("PADS_SCH".equals(ecad.getAuthoringApplication().toString())) {
                  ecadSCH = ecad;
                }
                else if("ACAD".equals(ecad.getAuthoringApplication().toString())) {
                  ecadDWG = ecad;
                }
              }
            }
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00924") %><%--관련ECAD--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="210" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="200" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              </tr>
              <%  if( (ecadPCB != null) && !PersistenceHelper.isEquivalent(ecadPCB, epm) ) { %>
                  <tr>
                    <td width="210" class="tdwhiteM"><%if(ecadPCB==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(ecadPCB).getStringValue()%>');"><%=ecadPCB.getNumber()%></a><%}%></td>
                    <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(ecadPCB==null)? "&nbsp;":ecadPCB.getName()%></nobr></div></td>
                    <td width="50" class="tdwhiteM"><%=(ecadPCB==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(ecadPCB, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                    <td width="100" class="tdwhiteM"><%=(ecadPCB==null)? "&nbsp;":ecadPCB.getCreatorFullName()%></td>
                    <td width="100" class="tdwhiteM0"><%=(ecadPCB==null)? "&nbsp;":ecadPCB.getState().getState().getDisplay(messageService.getLocale())%></td>
                  </tr>
              <%   } %>
              <%  if( (ecadSCH != null) && !PersistenceHelper.isEquivalent(ecadSCH, epm) ) { %>
                  <tr>
                    <td width="210" class="tdwhiteM"><%if(ecadSCH==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(ecadSCH).getStringValue()%>');"><%=ecadSCH.getNumber()%></a><%}%></td>
                    <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(ecadSCH==null)? "&nbsp;":ecadSCH.getName()%></nobr></div></td>
                    <td width="50" class="tdwhiteM"><%=(ecadSCH==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(ecadSCH, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                    <td width="100" class="tdwhiteM"><%=(ecadSCH==null)? "&nbsp;":ecadSCH.getCreatorFullName()%></td>
                    <td width="100" class="tdwhiteM0"><%=(ecadSCH==null)? "&nbsp;":ecadSCH.getState().getState().getDisplay(messageService.getLocale())%></td>
                  </tr>
              <%   } %>
              <%  if( (ecadDWG != null) && !PersistenceHelper.isEquivalent(ecadDWG, epm) ) { %>
                  <tr>
                    <td width="210" class="tdwhiteM"><%if(ecadDWG==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(ecadDWG).getStringValue()%>');"><%=ecadDWG.getNumber()%></a><%}%></td>
                    <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(ecadDWG==null)? "&nbsp;":ecadDWG.getName()%></nobr></div></td>
                    <td width="50" class="tdwhiteM"><%=(ecadDWG==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(ecadDWG, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                    <td width="100" class="tdwhiteM"><%=(ecadDWG==null)? "&nbsp;":ecadDWG.getCreatorFullName()%></td>
                    <td width="100" class="tdwhiteM0"><%=(ecadDWG==null)? "&nbsp;":ecadDWG.getState().getState().getDisplay(messageService.getLocale())%></td>
                  </tr>
              <%   } %>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%  } %>


        <%  //관련 도면 검색
          ArrayList refedParts = null;
          try {
            refedParts = EDMHelper.getReferencedByParts(epm,edmProperties.getReferenceType(category),-1);
          }
          catch(Exception e) {
              Kogger.error(e);
          }
        %>

        <!-- 고객제출도 -->
        <%  ArrayList custRefDocs = new ArrayList();
          if( (refedParts != null) && (refedParts.size() > 0) ) {
            WTPart refedPart = null;
            for(int i = 0; i < refedParts.size(); i++) {
              refedPart = (WTPart)((Object[])refedParts.get(i))[1];
              try {
                ArrayList refdocs = EDMHelper.getReferenceDocs(refedPart,EDMHelper.REFERENCE_TYPE_CU,-1);
                if( (refdocs != null) && (refdocs.size() > 0) ) {
                  for(int k = 0; k < refdocs.size(); k++) {
                    if( !PersistenceHelper.isEquivalent((Persistable)epm, (Persistable)((Object[])refdocs.get(k))[1]) ) {
                      custRefDocs.add( ((Object[])refdocs.get(k))[1] );
                    }
                  }
                }
              }
              catch(Exception e) {
              Kogger.error(e);
              }
            }
          }
        %>
        <%  if(custRefDocs.size() > 0) { %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00855") %><%--고객제출도--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="210" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="200" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              </tr>
              <%  EPMDocument doc = null;
                  Map<String, String> checkMap = new HashMap<String, String>();//중복체크용 임시맵
                  
                for(int i = 0; i < custRefDocs.size(); i++) {
                  doc = (EPMDocument)custRefDocs.get(i);
                  if(doc.getNumber().equals(checkMap.get(doc.getNumber()))){
                      continue;
                  }
                  checkMap.put(doc.getNumber(), doc.getNumber());
              %>
                  <tr>
                    <td width="210" height="23" class="tdwhiteM"><%if(doc==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(doc).getStringValue()%>');"><%=doc.getNumber()%></a><%}%></td>
                    <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=(doc==null)? "&nbsp;":doc.getName()%></nobr></div></td>
                    <td width="50" class="tdwhiteM"><%=(doc==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(doc, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                    <td width="100" class="tdwhiteM"><%=(doc==null)? "&nbsp;":doc.getCreatorFullName()%></td>
                    <td width="100" class="tdwhiteM"><%=(doc==null)? "&nbsp;":doc.getState().getState().getDisplay(messageService.getLocale())%></td>
                  </tr>
              <%  } %>

            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%  } %>

        <!-- 승인도 -->
        <%  ArrayList appRefDocs = new ArrayList();
          if( (refedParts != null) && (refedParts.size() > 0) ) {
            WTPart refedPart = null;
            for(int i = 0; i < refedParts.size(); i++) {
              refedPart = (WTPart)((Object[])refedParts.get(i))[1];
              try {
                ArrayList refdocs = EDMHelper.getReferenceDocs(refedPart,EDMHelper.REFERENCE_TYPE_APP,-1);
                if( (refdocs != null) && (refdocs.size() > 0) ) {
                  for(int k = 0; k < refdocs.size(); k++) {
                    if( !PersistenceHelper.isEquivalent((Persistable)epm, (Persistable)((Object[])refdocs.get(k))[1]) ) {
                      appRefDocs.add( ((Object[])refdocs.get(k))[1] );
                    }
                  }
                }
              }
              catch(Exception e) {
              Kogger.error(e);
              }
            }
          }
        %>
        <%  if(appRefDocs.size() > 0) { %>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01991") %><%--승인도면--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                <td width="40"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="80"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00051") %><%--3D 모델--%></td>
              </tr>
              <%  EPMDocument doc = null;
                for(int i = 0; i < appRefDocs.size(); i++) {
                  doc = (EPMDocument)appRefDocs.get(i);
              %>
                  <tr>
                    <td width="150" height="23" class="tdwhiteM"><%if(doc==null){%>&nbsp;<%}else{%><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(doc).getStringValue()%>');"><%=doc.getNumber()%></a><%}%></td>
                    <td width="150" class="tdwhiteL"><div class="ellipsis" style="width:145;"><nobr><%=(doc==null)? "&nbsp;":doc.getName()%></nobr></div></td>
                    <td width="40" class="tdwhiteM"><%=(doc==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(doc, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                    <td width="120" class="tdwhiteM"><%=(doc==null)? "&nbsp;":doc.getCreatorFullName()%></td>
                    <td width="80" class="tdwhiteM"><%=(doc==null)? "&nbsp;":doc.getState().getState().getDisplay(messageService.getLocale())%></td>
                    <td width="120" class="tdwhiteM">&nbsp;</td>
                  </tr>
              <%  } %>

            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <%  } %>

        <%
          String manageType0 = "";
          if(ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE) != null) {
            manageType0 = EDMEnumeratedTypeUtil.getCADManageType((String)ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE),Locale.KOREAN);
          }

          if("MOLD_DRAWING".equals(manageType0.trim())) {//if( "INJECTION_MOLD_SET_DRAWING".equals(category) || "PRESS_MOLD_SET_DRAWING".equals(category) ) {
            /*
            ArrayList moldSetDrws = new ArrayList();
            if(part != null) {
              moldSetDrws = EDMHelper.getReferenceDocs(part, edmProperties.getReferenceType(category), -1);
            }
            */
            String dieNo = "";
            if(epm.getNumber().indexOf("-") > 0) {
              dieNo = (epm.getNumber()).substring(0, epm.getNumber().indexOf("-"));
            } else {
              dieNo = epm.getNumber();
            }

            ArrayList moldSetDrws = null;

            if(dieNo.length() > 0){
              moldSetDrws = EDMHelper.getReferenceSetByDie(dieNo, "APPROVED");
            }

            if( (moldSetDrws != null) && (moldSetDrws.size() > 0) ) {
        %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01011") %><%--금형 SET--%><br><%=messageService.getString("e3ps.message.ket_message", "00926") %><%--관련도면--%></td>
              <td colspan="3" class="tdwhiteL0">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                  <tr>
                                        <td width="210" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01297") %><%--도번--%></td>
                    <td width="200" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                    <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01274") %><%--도면버전--%></td>
                                        <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                  </tr>
                  <%
                    for(int i = 0; i < moldSetDrws.size(); i++) {
                      EPMDocument moldSetDrw = (EPMDocument)moldSetDrws.get(i);
                      if(PersistenceHelper.isEquivalent(epm, moldSetDrw)) {
                        continue;
                      }

                      if(!edmProperties.isAppTypeByPLM(moldSetDrw.getOwnerApplication().toString())) {
                        continue;
                      }
                  %>
                      <tr>
                        <td width="210" height="23" class="tdwhiteM"><a href="javascript:;" onclick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(moldSetDrw).getStringValue()%>');"><%=moldSetDrw.getNumber()%></a></td>
                        <td width="200" class="tdwhiteL"><div class="ellipsis" style="width:195;"><nobr><%=moldSetDrw.getName()%></nobr></div></td>
                        <td width="60" class="tdwhiteM"><%=StringUtil.checkNull(IBAUtil.getAttrValue(moldSetDrw, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                        <td width="90" class="tdwhiteM"><%=moldSetDrw.getCreatorFullName()%></td>
                        <td width="100" class="tdwhiteM"><%=moldSetDrw.getState().getState().getDisplay(messageService.getLocale())%></td>
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
        <%
            }
          }
        %>


        <%
          if(reledChanges == null){
              reledChanges = ext.ket.edm.approval.KetEpmApprovalHelper.service.getRelatedEcoList(oid);
          }
          if( (reledChanges != null) && (reledChanges.length > 0) ) {
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01863") %><%--설계변경정보--%></td>
          <td colspan="3" class="tdwhiteL0">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
              <tr> 
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00199") %><%--ECO번호--%></td>
                <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00189") %><%--ECO 상태--%></td>
                <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
              </tr>
              <%
              EpmApprovalHistoryHandler history = new EpmApprovalHistoryHandler();
              
                for(int i = 0; i < reledChanges.length; i++) {
                  String changeNumber = "";//ecoId
                  String changeName = "";//ecoName
                  String changeState = "";
                  String changeCreator = "";
                  String eoClassName = "";
                  String eoOid = "";
                  boolean isMold = false;
                  if(reledChanges[i] instanceof KETProdChangeOrder) {
                    KETProdChangeOrder kpo = (KETProdChangeOrder)reledChanges[i];
                    changeNumber = kpo.getEcoId();
                    changeName = kpo.getEcoName();
                    changeState = kpo.getLifeCycleState().getDisplay(Locale.KOREAN);
                    changeCreator = kpo.getCreatorFullName();
                    eoClassName = KETProdChangeOrder.class.getName();
                    eoOid = kpo.getPersistInfo().getObjectIdentifier().getStringValue();

                    history.getApprovalHistory(kpo);

                  } else if(reledChanges[i] instanceof KETMoldChangeOrder) {
                    KETMoldChangeOrder kpo = (KETMoldChangeOrder)reledChanges[i];
                    changeNumber = kpo.getEcoId();
                    changeName = kpo.getEcoName();
                    changeState = kpo.getLifeCycleState().getDisplay(Locale.KOREAN);
                    changeCreator = kpo.getCreatorFullName();
                    eoClassName = KETProdChangeOrder.class.getName();
                    eoOid = kpo.getPersistInfo().getObjectIdentifier().getStringValue();
                    isMold = true;
                    
                    history.getApprovalHistory(kpo);
                  }
                  
              %>
                  <tr>
                    <td class="tdwhiteM"><a href="javascript:viewEO('<%=eoOid%>','<%=isMold%>');"><%=changeNumber%></a></td>
                    <td class="tdwhiteL"><div class="ellipsis" style="width:245px;"><nobr><%=changeName%></nobr></div></td>
                    <td class="tdwhiteM"><%=changeState%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getDateString(history.getApprovalDate(),"d"), "-") %></td>
                    <td class="tdwhiteM"><%=changeCreator%></td>
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
        <%  }
        %>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe id="copyright" src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
<script language="javascript">
//var opener = window.dialogArguments;
var isModal = false;
if( window.dialogArguments != null) {
  isModal = true;
}

if( isModal || (window.opener) ) {
  document.getElementById("copyright").src = "../../portal/common/copyright_p.jsp";
  document.getElementById("popup_log").style.display="";
  //document.getElementById("view_log").style.display="none";

}
</script>
