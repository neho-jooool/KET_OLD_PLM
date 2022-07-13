<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.content.*,wt.epm.*,wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*"%>
<%@ page import="wt.iba.value.IBAHolder,wt.inf.container.WTContainerRef"%>
<%@ page import="wt.session.SessionHelper,wt.org.WTUser,wt.htmlutil.HtmlUtil"%>
<%@ page import="e3ps.edm.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%@ page import="e3ps.common.code.*,
                e3ps.common.content.ContentInfo,
        e3ps.common.content.ContentUtil,
        e3ps.common.util.CommonUtil,
        e3ps.common.util.DateUtil,
        e3ps.common.util.StringUtil"%>
        
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>        
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
  EDMProperties edmProperties = EDMProperties.getInstance();
  EDMAttributes edmAttributes = EDMAttributes.getInstance();

  WTContainerRef wtContainerRef = EDMUtil.getWTContainerRef(edmProperties.getContainer());
  WTUser currentUser = (WTUser)SessionHelper.manager.getPrincipal();

  boolean isAdmin = CommonUtil.isAdmin();
  String webAppName = CommonUtil.getWebAppName();
  String[] definedListCounts = edmProperties.getDefinedListCounts();

  //condions
  String command = StringUtil.trimToEmpty(request.getParameter("command"));
  String number = StringUtil.trimToEmpty(request.getParameter("number"));
  String name = StringUtil.trimToEmpty(request.getParameter("name"));
  String partNumber = StringUtil.trimToEmpty(request.getParameter("partNumber"));

  String state = StringUtil.trimToEmpty(request.getParameter("state"));
  String latest = StringUtil.trimToEmpty(request.getParameter("latest"));

  String businessType = StringUtil.trimToEmpty(request.getParameter("businessType"));
  String devStage = StringUtil.trimToEmpty(request.getParameter("devStage"));
  String category = StringUtil.trimToEmpty(request.getParameter("category"));
  String cadAppType = StringUtil.trimToEmpty(request.getParameter("cadAppType"));
  String isDummyFile = StringUtil.trimToEmpty(request.getParameter("isDummyFile"));

  String createStart = StringUtil.trimToEmpty(request.getParameter("create_start"));
  String createEnd = StringUtil.trimToEmpty(request.getParameter("create_end"));
  String modifyStart = StringUtil.trimToEmpty(request.getParameter("modify_start"));
  String modifyEnd = StringUtil.trimToEmpty(request.getParameter("modify_end"));

  String creator = StringUtil.trimToEmpty(request.getParameter("creator"));
  String modifier = StringUtil.trimToEmpty(request.getParameter("modifier"));

  String edmCreateDeptName = StringUtil.trimToEmpty(request.getParameter("edmCreateDeptName"));
  String edmModifyDeptName = StringUtil.trimToEmpty(request.getParameter("edmModifyDeptName"));

  String projectNo = StringUtil.trimToEmpty(request.getParameter("projectNo"));

  String descending = StringUtil.trimToEmpty(request.getParameter("descending"));
  String sortColumn = StringUtil.trimToEmpty(request.getParameter("sortColumn"));
  String sortClass = StringUtil.trimToEmpty(request.getParameter("sortClass"));

  if(latest.length() == 0) { latest = "true";  }
  if(descending.length() == 0) { descending = "true";  }
  if(sortColumn.length() == 0) { sortColumn = "thePersistInfo.createStamp"; }
  if(sortClass.length() == 0) { sortClass = wt.epm.EPMDocument.class.getName(); }

  //페이지 관련 Parameter
  String psizeStr = StringUtil.trimToEmpty(request.getParameter("psize"));
  String cpageStr = StringUtil.trimToEmpty(request.getParameter("cpage"));

  if(psizeStr.length() == 0) { psizeStr = definedListCounts[0];  }

  //페이지 처리
  int psize = 10;
  int cpage = 1;
  int total = 0;
  int pageCount = 5;

  long sessionId = 0L;

  if(psizeStr.trim().length() > 0) {
    psize = Integer.parseInt(psizeStr);
  }

  if(cpageStr.trim().length() > 0) {
    cpage = Integer.parseInt(cpageStr);
  }

  int listStart = (cpage-1)*psize+1;
  int listEnd = listStart+psize;

  //QueryResult result = null;
  PagingQueryResult result = null;
  if("search".equals(command)) {
    try {
      if(sessionId > 0L) {
        result = EDMQueryHelper.fetchPagingSession(listStart-1,psize,sessionId);
      } else {
        HashMap map = new HashMap();
        map.put("command",command);
        map.put("number",number);
        map.put("name",name);
        map.put("partNumber",partNumber);
        map.put("state",state);
        map.put("latest",latest);

        map.put("businessType",businessType);

        map.put("create_start",createStart);
        map.put("create_end",createEnd);
        map.put("modify_start",modifyStart);
        map.put("modify_end",modifyEnd);

        map.put("creator",creator);
        map.put("modifier",modifier);

        map.put("edmCreateDeptName",edmCreateDeptName);
        map.put("edmModifyDeptName",edmModifyDeptName);

        map.put("projectNo",projectNo);

        map.put("className",wt.epm.EPMDocument.class.getName());

        //IBA 값 처리
        if(devStage.length() > 0) {
          map.put(EDMHelper.IBA_DEV_STAGE, DevStage.toDevStage(devStage).getDisplay(Locale.KOREA));
        }
        if(category.length() > 0) {
          map.put(EDMHelper.IBA_CAD_CATEGORY, CADCategory.toCADCategory(category).getDisplay(Locale.KOREA));
        }
        if(cadAppType.length() > 0) {
          map.put(EDMHelper.IBA_CAD_APP_TYPE, CADAppType.toCADAppType(cadAppType).getDisplay(Locale.KOREA));
        }
        if(isDummyFile.length() > 0) {
          map.put(EDMHelper.IBA_DUMMY_FILE, isDummyFile);
        }

        Vector sorts = new Vector();
        Object[] so = new Object[3];
        so[0] = sortColumn;
        so[1] = Class.forName(sortClass);//wt.epm.EPMDocumentMaster.class;
        so[2] = new Boolean("true".equals(descending.toLowerCase()));
        sorts.add(so);
        map.put("sort",sorts);

        result = EDMQueryHelper.openPagingSession(map,listStart-1,psize);

        sessionId = result.getSessionId();
        total = result.getTotalSize();
      }
    }
    catch(Exception e) {
	Kogger.error(e);
    }
    finally {
      if(sessionId != 0L) {
        PagingSessionHelper.closePagingSession(sessionId);
      }
    }
  }

  if( result == null || result.size() == 0 )
  {
    sortClass = "";
  }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  overflow-x:hidden;
  overflow-y:hidden;
  /*
  scrollbar-face-color:FFFFFF;
  scrollbar-shadow-color:929292;
  scrollbar-highlight-color:929292;
  scrollbar-3dlight-color:FFFFFF;
  scrollbar-darkshadow-color:FFFFFF;
  scrollbar-track-color:FFFFFF;
  scrollbar-arrow-color:929292;
  */
}

col.fixedlayout { layout-grid:both fixed none none; }
div.TableHeaderScroll { background: #f4f4f4; border: 1px solid #e5e4e4; padding: 0; margin: 0; width:17px; layout-grid:both fixed none none; overflow:hidden; }

span.upArrow { color: gray;font-size: .80em;position: relative;padding: 0;margin:-3 0 0 0;border: 0;float: left;text-decoration: none; }
span.downArrow { color: gray;font-size: .80em;position: relative;padding: 0;margin: -10 0 0 0;border: 0;float: right;text-decoration: none; }

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
//-->
</style>
<script language=JavaScript src="/<%=webAppName%>/portal/js/jquery-1.4.3.min.js"></script>
<script type="text/Javascript">
<!--
var $jquery=jQuery.noConflict();
 //-->
</script>

<SCRIPT LANGUAGE=JavaScript>
<!--

function gotoPage(p){
  document.forms[0].command.value='search';
  document.forms[0].cpage.value=p;
  document.forms[0].submit();
}

function doViewEPM(_oid) {
  form = document.forms[0];
  if(parent) {
    parent.doViewEPM(_oid,
      form.psize.value,
      form.cpage.value,
      form.descending.value,
      form.sortColumn.value,
      form.sortClass.value
    );
  } else {
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid="+_oid;
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=820,height=800');
    newWinSPart.focus();
    newWinSPart.location.href = url;
  }
}

function MouseOver(obj) {
  //obj.style.fontSize='.80em';
  obj.style.cursor='hand';
  obj.style.color='blue';
  //obj.style.backgroundColor='#ffff00';
}

function MouseOut(obj) {
  //obj.style.fontSize='.80em';
  obj.style.cursor='auto';
  obj.style.color='';
  //obj.style.backgroundColor='';
}

function gosort(_class,_column, _descending) {
  var form = document.forms[0];
  form.descending.value = _descending;
  form.sortColumn.value = _column;
  form.sortClass.value = _class;

  gotoPage('');
}

// -->
</SCRIPT>

</head>
<body>
<form>

<!-- hidden -->
<input type="hidden" name="command" value="<%=command%>">
<input type="hidden" name="number" value="<%=number%>">
<input type="hidden" name="name" value="<%=name%>">
<input type="hidden" name="partNumber" value="<%=partNumber%>">

<input type="hidden" name="state" value="<%=state%>">
<input type="hidden" name="latest" value="<%=latest%>">

<input type="hidden" name="businessType" value="<%=businessType%>">
<input type="hidden" name="devStage" value="<%=devStage%>">
<input type="hidden" name="category" value="<%=category%>">
<input type="hidden" name="cadAppType" value="<%=cadAppType%>">
<input type="hidden" name="isDummyFile" value="<%=isDummyFile%>">

<input type="hidden" name="create_start" value="<%=createStart%>">
<input type="hidden" name="create_end" value="<%=createEnd%>">
<input type="hidden" name="modify_start" value="<%=modifyStart%>">
<input type="hidden" name="modify_end" value="<%=modifyEnd%>">

<input type="hidden" name="creator" value="<%=creator%>">
<input type="hidden" name="modifier" value="<%=modifier%>">

<input type="hidden" name="edmCreateDeptName" value="<%=edmCreateDeptName%>">
<input type="hidden" name="edmModifyDeptName" value="<%=edmModifyDeptName%>">

<input type="hidden" name="projectNo" value="<%=projectNo%>">

<input type="hidden" name="descending" value="<%=descending%>">
<input type="hidden" name="sortColumn" value="<%=sortColumn%>">
<input type="hidden" name="sortClass" value="<%=sortClass%>">

<!-- paging -->
<input type="hidden" name="psize" value="<%=psizeStr%>">
<input type="hidden" name="cpage" value="<%=cpage%>">
<!-- hidden end -->

<table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<div id='TableHeader_HD0' style='width:100%;margin:0;padding:0;border:0;'>
  <div id='TableHeaderLabel_HD0' style='float:left;margin:0;padding:0;border:0;overflow:hidden;layout-grid: both fixed none none;'>
    <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;">
    <col class="fixedlayout" width="40">
    <col class="fixedlayout" width="150">
    <col class="fixedlayout" width="35">
    <col class="fixedlayout" width="35">
    <col class="fixedlayout" width="170">
    <col class="fixedlayout" width="100">
    <col class="fixedlayout" width="70">
    <col class="fixedlayout" width="70">
    <col class="fixedlayout" width="60">
    <col class="fixedlayout" width="50">

      <tr>
        <td class="tdblueM" style="height: 24px;"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
        <td class="tdblueM" style="height: 24px;">
          <%if( sortClass.equals(wt.epm.EPMDocumentMaster.class.getName())&& sortColumn.equals("number") ){%>
            <%if( "true".equals(descending) ){%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocumentMaster.class.getName()%>','number','false');"><u><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></u></span>
            <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocumentMaster.class.getName()%>','number','true');"><u><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></u></span>
            <%}%>
          <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocumentMaster.class.getName()%>','number','true');"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></span>
          <%}%>
        </td>
        <td class="tdblueM" style="height: 24px;">D/W</td>
        <td class="tdblueM" style="height: 24px;">
          <%if( sortClass.equals(wt.epm.EPMDocument.class.getName())&& sortColumn.equals("versionInfo.identifier.versionId") ){%>
            <%if( "true".equals(descending) ){%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocument.class.getName()%>','versionInfo.identifier.versionId','false');"><u><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></u></span>
            <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocument.class.getName()%>','versionInfo.identifier.versionId','true');"><u><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></u></span>
            <%}%>
          <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocument.class.getName()%>','versionInfo.identifier.versionId','true');"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></span>
          <%}%>
        </td>
        <td class="tdblueM" style="height: 24px;">
          <%if( sortClass.equals(wt.epm.EPMDocumentMaster.class.getName())&& sortColumn.equals("name") ){%>
            <%if( "true".equals(descending) ){%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocumentMaster.class.getName()%>','name','false');"><u><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></u></span>
            <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocumentMaster.class.getName()%>','name','true');"><u><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></u></span>
            <%}%>
          <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocumentMaster.class.getName()%>','name','true');"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></span>
          <%}%>
        </td>
        <td class="tdblueM" style="height: 24px;">
          <%if( sortClass.equals(wt.iba.definition.StringDefinition.class.getName())&& sortColumn.equals(EDMHelper.IBA_CAD_APP_TYPE) ){%>
            <%if( "true".equals(descending) ){%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.iba.definition.StringDefinition.class.getName()%>','<%=EDMHelper.IBA_CAD_APP_TYPE%>','false');"><u><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></u></span>
            <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.iba.definition.StringDefinition.class.getName()%>','<%=EDMHelper.IBA_CAD_APP_TYPE%>','true');"><u><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></u></span>
            <%}%>
          <%}else{%>
            <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.iba.definition.StringDefinition.class.getName()%>','<%=EDMHelper.IBA_CAD_APP_TYPE%>','true');"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></span>
          <%}%>
        </td>
        <td class="tdblueM" style="height: 24px;">
          <%if( sortClass.equals(e3ps.groupware.company.People.class.getName())&& sortColumn.equals("name") ){%>
            <%if( "true".equals(descending) ){%>
                        <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=e3ps.groupware.company.People.class.getName()%>','name','false');"><u><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></u></span>
            <%}else{%>
                        <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=e3ps.groupware.company.People.class.getName()%>','name','true');"><u><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></u></span>
            <%}%>
          <%}else{%>
                        <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=e3ps.groupware.company.People.class.getName()%>','name','true');"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></span>
          <%}%>
        </td>
        <td class="tdblueM" style="height: 24px;">
          <%if( sortClass.equals(wt.epm.EPMDocument.class.getName())&& sortColumn.equals("thePersistInfo.createStamp") ){%>
            <%if( "true".equals(descending) ){%>
                        <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocument.class.getName()%>','thePersistInfo.createStamp','false');"><u><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></u></span>
            <%}else{%>
                        <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocument.class.getName()%>','thePersistInfo.createStamp','true');"><u><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></u></span>
            <%}%>
          <%}else{%>
                        <span onmouseover="MouseOver(this);" onmouseout="MouseOut(this);" onclick="javascript:gosort('<%=wt.epm.EPMDocument.class.getName()%>','thePersistInfo.createStamp','true');"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></span>
          <%}%>
        </td>
        <td class="tdblueM" style="height: 24px;"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
        <td class="tdblueM0" style="height: 24px;">Dummy</td>
      </tr>
    </table>
  </div>
  <div id='TableHeaderScroll_HD0' class='TableHeaderScroll' style='float:right;border-top: 0;border-right: 0;'>&nbsp;</div>
</div>
<div id='TableContent_HD0' style='width:100%;height:241;overflow-x:auto;overflow-y:auto;' onscroll="javascript:doScrollEvent(event,'HD0',true, false);">
  <!-- <table id="TableList_HD0" class="list" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;" onResize="javascript:doScrollSizeEvent(event,'HD0',22);"> -->
  <table id="TableList_HD0" class="list" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;">
  <col class="fixedlayout" width="40">
  <col class="fixedlayout" width="150">
  <col class="fixedlayout" width="35">
  <col class="fixedlayout" width="35">
  <col class="fixedlayout" width="170">
  <col class="fixedlayout" width="100">
  <col class="fixedlayout" width="70">
  <col class="fixedlayout" width="70">
  <col class="fixedlayout" width="60">
  <col class="fixedlayout" width="50">
  <%  if( (result == null) || (result.size() == 0)) { %>
      <tr><td class="tdwhiteM0" colspan="10"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td></tr>
  <%  } else { %>
    <%  ReferenceFactory rf = new ReferenceFactory();
        int rowOrderNumber = listStart;

        WTUser creator0 = null;

        // Added by MJOH, 2011-01-24 첨부파일 링크 생성
        FormatContentHolder holder = null;
        ContentItem item = null;
        ContentInfo info = null;
        String appDataOid = "";
        String iconpath = "";
        String primaryURL = "";
        ///////////////////////////////////////////////////////////////

        Object oo[] = null;
        while(result.hasMoreElements()) {
          oo = (Object[])result.nextElement();
          String nr = (String)oo[1];
          String nm = (String)oo[2];
          String nv = (String)oo[3];
          String estate = (String)oo[4];
          String noid = (String)oo[0];

          Timestamp createStamp = (Timestamp)oo[5];

          HashMap ibaValues = null;

          try {
            creator0 = (WTUser)rf.getReference((String)oo[7]).getObject();

            ibaValues = edmAttributes.getIBAValues((IBAHolder)rf.getReference(noid).getObject(), Locale.KOREAN);
          }
          catch(Exception e) {
              Kogger.error(e);
            creator0= null;
          }

          String _dummy = "";
          if(ibaValues.get(EDMHelper.IBA_DUMMY_FILE) != null) {
            _dummy = (String)ibaValues.get(EDMHelper.IBA_DUMMY_FILE);
          }

          // Added by MJOH, 2011-01-24 주 첨부파일 링크 생성
          holder = (FormatContentHolder)ContentHelper.service.getContents( (EPMDocument)rf.getReference(noid).getObject() );
          item = ContentHelper.getPrimary(holder);

          if( item != null )
          {
            info = ContentUtil.getContentInfo( holder, item );
            appDataOid = item.getPersistInfo().getObjectIdentifier().getStringValue();
            iconpath = info.getIconURLStr();
            iconpath = iconpath.substring(iconpath.indexOf("<img"));
            //primaryURL = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
            primaryURL = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + item.getRole().toString();
            primaryURL = "<a href=" + primaryURL + " target='_blank'>" + iconpath;
          }
          else
          {
            info = null;
            holder = null;
            item = null;
            appDataOid = "";
            iconpath = "";
            primaryURL = "&nbsp;";
          }
      %>
          <tr>
            <td class="tdwhiteM"><%=rowOrderNumber++%></td>
            <td class="tdwhiteL" title="<%=HtmlUtil.escapeFormattedHTMLContent(nr)%>">
              <div class="ellipsis" style="width:140;"><nobr>
                <a href="javascript:;" onClick="javascript:doViewEPM('<%=noid%>');"><%=nr%></a>
              </nobr></div>
            </td>
            <td class="tdwhiteM"><%=primaryURL %></td>
            <td class="tdwhiteM"><%=nv%></td>
            <td class="tdwhiteL" title="<%=HtmlUtil.escapeFormattedHTMLContent(nm)%>">
              <div class="ellipsis" style="width:170;"><nobr>
                <%=nm%>
              </nobr></div>
            </td>
            <!-- <td width="80" class="tdwhiteM"><%=(ibaValues.get(EDMHelper.IBA_CAD_CATEGORY)==null)? "&nbsp;":ibaValues.get(EDMHelper.IBA_CAD_CATEGORY)%></td> -->
            <td class="tdwhiteM"><%=(ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE)==null)? "&nbsp;":ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE)%></td>
            <td class="tdwhiteM"><%=(creator0==null)? "&nbsp;":creator0.getFullName()%></td>
            <td class="tdwhiteM"><%=DateUtil.getDateString(createStamp,"d")%></td>
            <td class="tdwhiteM"><%=State.toState(estate).getDisplay(messageService.getLocale())%></td>
            <td class="tdwhiteM0"><%=("Y".equals(_dummy))? _dummy:"&nbsp;"%></td>
          </tr>
      <%
        }
      %>

  <%  } %>
  </table>
</div>

<%  if( (result != null) && (result.size() > 0)) {
%>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <tr>
        <td class="space3"></td>
      </tr>
    </table>
    <%
      int ksize = total/psize;
      int x = total%psize;
      if(x>0) ksize++;
      int temp = cpage/pageCount;
      if( (cpage%pageCount)>0)temp++;
      int start = (temp-1)*pageCount+1;

      int end = start + pageCount-1;
      if(end> ksize){
        end = ksize;
      }
    %>
    <table id="paging_table" border="0" cellspacing="0" cellpadding="0" width="100%">
      <tr>
                <td class="small" align="left">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> : <%=ksize%> )</td>
        <td>
          <table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <!-- 처음 -->
              <td width="20" align="center"><% if(start > 1) { %><a href="javascript:;" onClick="JavaScript:gotoPage(1);" class='small'><img src='../../portal/images/btn_arrow4.gif' style='border:0'></a><%  } %></td>
              <td width="1" bgcolor="#dddddd"></td>
              <!-- 이전 -->
              <%  if(start > 1) { %>
                  <td width="20" align="center"><a  href="javascript:;" onClick="JavaScript:gotoPage(<%=start-1%>);" class='small'><img src='../../portal/images/btn_arrow3.gif' style='border:0'></a></td>
                  <td width="1" bgcolor="#dddddd"></td>
              <%  } %>
              <!-- 페이지 -->
              <%  for(int i=start; i<= end; i++){ %>
                  <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on"><font color=777777><%if(i==cpage){%><b><font color=006699><%=i%></font></b><%}else{%><a href="javascript:;" onClick="JavaScript:gotoPage(<%=i%>);"><%=i%></a><%}%></font></td>
              <%  } %>
              <!-- 다음 -->
              <%  if(end < ksize) { %>
                  <td width="1" bgcolor="#dddddd"></td>
                  <td width="20"align="center"><a  href="javascript:;" onClick="JavaScript:gotoPage(<%=end+1%>);" class='small'><img src='../../portal/images/btn_arrow1.gif' style='border:0'></a></td>
              <%  } %>
              <!-- 마지막 -->
              <td width="1" bgcolor="#dddddd"></td>
              <td width="20"align="center"><%  if(end < ksize) { %><a  href="javascript:;" onClick="JavaScript:gotoPage(<%=ksize%>);" class='small'><img src='../../portal/images/btn_arrow2.gif' style='border:0'></a><% } %></td>
            </tr>
          </table>
        </td>
                <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%> : <%=total%>)</td>
      </tr>
    </table>
<%  }
%>
</form>
</body>
</html>
<script language="JavaScript">
<!--

function doScrollInit(hdID, tdheight) {

  var thr0 = document.getElementById("TableHeader_"+hdID);
  var thl0 = document.getElementById("TableHeaderLabel_"+hdID);
  var ths0 = document.getElementById("TableHeaderScroll_"+hdID);
  var tct0 = document.getElementById("TableContent_"+hdID);
  var tlt0 = document.getElementById("TableList_"+hdID);

  var thr0Width = document.body.offsetWidth;

  tct0.style.height = document.body.offsetHeight - 74;//document.body.offsetHeight - 50;

  var curheight = tlt0.rows.length * tdheight;
  //alert('dev height ; ' + mainObj.offsetHeight);
  if(curheight > tct0.offsetHeight) {
    ths0.style.display = "";
    thl0.style.width = thr0Width - 18;
  } else {
    ths0.style.display = "none";
    thl0.style.width = thr0Width;
  }

  ths0.style.height = thl0.offsetHeight;

  //alert(thr0Width+'\n'+thl0.style.width+'\n'+document.body.offsetWidth);

  return;
}

function doScrollEvent(rsEvent,hdID, horizontalSync, verticalSync) {

  var tct0 = document.getElementById("TableContent_"+hdID);
  var thl0 = document.getElementById("TableHeaderLabel_"+hdID);

  if(horizontalSync) {
    thl0.scrollLeft = tct0.scrollLeft;
  }

  if(verticalSync) {
    thl0.scrollTop = tct0.scrollTop;
  }
}

function doScrollSizeEvent(rsEvent, hdID, tdheight) {
  doScrollInit(hdID, tdheight);
}

//***************************************************************************
// 목록 테이블 초기화
//***************************************************************************
doScrollInit('HD0',22);

//-->
</script>

<script language="javascript">

function resizeIframe() {
  //onResize="javascript:doScrollSizeEvent(event,'HD0',22);"
  doScrollInit('HD0',22);
};

//window.onresize = resizeIframe;

var resizeTimer = null;
$jquery(document).ready(function(){
        $jquery(window).bind('resize',function() {
                if (resizeTimer) clearTimeout(resizeTimer);
                resizeTimer = setTimeout(resizeIframe, 100);
        });

        // Call resizeIframe at page load to set the iframe height
        resizeIframe();
});

</script>
