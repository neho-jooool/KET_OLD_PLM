<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*"%>
<%@ page import="wt.epm.EPMDocument,wt.vc.VersionControlHelper"%>
<%@ page import="wt.htmlutil.HtmlUtil"%>
<%@ page import="e3ps.common.util.CommonUtil,e3ps.common.util.DateUtil"%>
<%@ page import="e3ps.edm.*,e3ps.edm.beans.*,e3ps.edm.util.*"%>
<%@page import="e3ps.common.iba.IBAUtil"%>
<%@page import = "ext.ket.part.util.PartSpecGetter,
          ext.ket.part.util.PartSpecEnum,
          e3ps.common.util.StringUtil" %>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>          
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
  String webAppName = CommonUtil.getWebAppName();

  boolean isAdmin = CommonUtil.isAdmin();

  String command = request.getParameter("command");
  String linkOid = request.getParameter("linkOid");
  String oid = request.getParameter("oid");

  if( (command == null) || (command.trim().length() == 0) ) {
    command = "";
  }

  if( (linkOid == null) || (linkOid.trim().length() == 0) ) {
    linkOid = "";
  }

  if("deleteLink".equals(command)) {
    try {

      ReferenceFactory rf = new ReferenceFactory();
      Object oo = rf.getReference(linkOid).getObject();
      PersistenceHelper.manager.delete((Persistable)oo);
    }
    catch(Exception e) {
    Kogger.error(e);
    }
  }

  EPMDocument epm = null;
  try {
    ReferenceFactory rf = new ReferenceFactory();
    epm = (EPMDocument)rf.getReference(oid).getObject();
  }
  catch(Exception e) {
      Kogger.error(e);
  }

  QueryResult result = VersionControlHelper.service.allVersionsOf(epm.getMaster());
  //QueryResult result = VersionControlHelper.service.allIterationsOf(epm.getMaster());


%>
<html>
<head>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<SCRIPT LANGUAGE=JavaScript>
<!--
function doViewEPM(_oid) {
  var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid="+_oid;
  newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=820,height=800');
  newWinSPart.focus();
  newWinSPart.location.href = url;
}

function doDeleteLink(_linkoid) {
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01708") %><%--삭제된 데이터는 복구할 수 없습니다. 삭제하시겠습니까?--%>")) {
    return;
  }
  document.forms[0].linkOid.value=_linkoid;
  document.forms[0].command.value="deleteLink";

  var url = "/<%=webAppName%>/jsp/edm/ViewRevisionHistory.jsp";
  document.forms[0].method="post";
  document.forms[0].target="_self";
  document.forms[0].action=url;
  document.forms[0].submit();
}
// -->
</SCRIPT>
</head>
<body>
<form method=post>
<!-- hidden -->
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="linkOid" value="<%=oid%>">
<input type="hidden" name="command" value="<%=oid%>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png">
            <table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00690") %><%--개정이력--%></td>
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td valign="top">
      <table width="660" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                                <td width="210" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01297") %><%--도번--%></td>
                                <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01295") %><%--도명--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                <td width="100" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
              </tr>
              <%  if( (result == null) || (result.size() == 0)) { %>
                  <tr><td class="tdwhiteM0" colspan="6"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td></tr>
              <%  } else { %>
                  <%
                    boolean referencedbymodel = EDMHelper.isRefedModel(epm);
                    EPMDocument revision = null;
                    while(result.hasMoreElements()) {
                      revision = (EPMDocument)result.nextElement();
                      HashMap ibaValues = EDMAttributes.getIBAValues(revision, Locale.KOREAN);
                  %>
                      <tr>
                        <td class="tdwhiteL">
                          <div class="ellipsis" style="width:205;"><nobr><a href="javascript:;" onClick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(revision).getStringValue()%>');"><%=revision.getNumber()%></a>
                          </nobr></div>
                        </td>
                        <td class="tdwhiteL" title="<%=HtmlUtil.escapeFormattedHTMLContent(revision.getName())%>">
                          <div class="ellipsis" style="width:195;"><nobr><%=revision.getName()%></nobr></div>
                        </td>
                        <td class="tdwhiteM">
                        <%=(ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION)==null)? "&nbsp;":(String)ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION)%>
                          <%--=revision.getVersionIdentifier().getSeries().getValue()--%-->
                          <%--if(isAdmin){% >< %="."+revision.getIterationIdentifier().getSeries().getValue()% >< %} --%>
                        </td>
                        <td class="tdwhiteM"><%=revision.getCreatorFullName()%></td>
                        <td class="tdwhiteM0"><%=DateUtil.getDateString(PersistenceHelper.getCreateStamp(revision),"d")%></td>
                      </tr>
                      <%if(!isAdmin) { continue; }%>
                      <%  ArrayList modelrefs = null;
                        if(referencedbymodel) {
                          modelrefs = EDMHelper.getReferenceEPMs(revision,-1);
                        } else {
                          modelrefs = EDMHelper.getReferencedByModels(revision, -1);
                        }


                        if(modelrefs != null) {
                          EPMDocument fmodel = null;
                          Object[] refs = null;
                          for(int i = 0; i < modelrefs.size(); i++) {
                            refs = (Object[])modelrefs.get(i);

                            fmodel = (EPMDocument)refs[1];

                            String linkoid = "";
                            int required = 0;
                            if(refs[0] instanceof ModelReferenceLink) {
                              ModelReferenceLink modellink = (ModelReferenceLink)refs[0];
                              required = modellink.getRequired();
                              linkoid = PersistenceHelper.getObjectIdentifier(modellink).getStringValue();
                            } else if(refs[0] instanceof DrawingToModelReferenceLink) {
                              DrawingToModelReferenceLink historylink = (DrawingToModelReferenceLink)refs[0];
                              required = historylink.getRequired();
                              linkoid = PersistenceHelper.getObjectIdentifier(historylink).getStringValue();
                            }
                      %>
                            <tr>
                              <td class="tdwhiteR0" style="background-color:#EEE9BF" colspan="5">
                                【<%=fmodel.getNumber()%>】[<%=StringUtil.checkNull(IBAUtil.getAttrValue(fmodel, EDMHelper.IBA_MANUFACTURING_VERSION)) %>]&nbsp;&nbsp;-&nbsp;&nbsp;
                                <%
                                  if(required == 1) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "01238")/*대표*/);
                                  }
                                  else if(required == 2) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "01365")/*모델*/);
                                  }
                                  else if(required == 0) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "00901")/*관련*/);
                                  }
                                %>
                                【<a href="javascript:;" onclick="javascript:doDeleteLink('<%=linkoid%>');"><img src="../../portal/images/icon_delete.gif" border="0" alt='<%=linkoid%>'></a>】
                              </td>
                            </tr>
                      <%
                          }
                        }
                      %>

                      <%
                        ArrayList refsParts = null;
                        try {
                          refsParts = EDMHelper.getReferencedByParts(revision,null,-1, false);
                        }
                        catch(Exception e) {
                            Kogger.error(e);
                        }

                        if(refsParts != null) {

                          EPMLink epmlink = null;
                          PartToEPMLink historylink = null;

                          WTPart part = null;
                          Object[] refs = null;
                          for(int i = 0; i < refsParts.size(); i++) {
                            refs = (Object[])refsParts.get(i);

                            part = (WTPart)refs[1];

                            String linkoid = "";
                            String referenceType = "";
                            int required = 0;
                            if(refs[0] instanceof EPMLink) {
                              epmlink = (EPMLink)refs[0];
                              referenceType = epmlink.getReferenceType();
                              required = epmlink.getRequired();
                              linkoid = PersistenceHelper.getObjectIdentifier(epmlink).getStringValue();
                            } else if(refs[0] instanceof PartToEPMLink) {
                              historylink = (PartToEPMLink)refs[0];
                              referenceType = historylink.getReferenceType();
                              required = historylink.getRequired();
                              linkoid = PersistenceHelper.getObjectIdentifier(historylink).getStringValue();
                            }

                            if(referenceType == null) {
                              referenceType = "null";
                            }
                      %>
                            <tr>
                              <td class="tdwhiteR0" style="background-color:#EEE8CD" colspan="5">
                                【<%=part.getNumber()%>】[<%=StringUtil.checkNull(IBAUtil.getAttrValue(part, EDMHelper.IBA_MANUFACTURING_VERSION)) %>]&nbsp;&nbsp;-&nbsp;&nbsp;
                                <%
                                  if(required == 1) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "01238")/*대표*/);
                                  }
                                  else if(required == 2) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "01365")/*모델*/);
                                  }
                                  else if(required == 0) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "00901")/*관련*/);
                                  }
                                %>
                                &nbsp;-&nbsp;
                                <%
                                  if(referenceType.equals(EDMHelper.REFERENCE_TYPE_MODEL)) {
                                                                        out.print(messageService.getString("e3ps.message.ket_message", "02766") /*참조모델(3D)*/);
                                  }
                                  else if(referenceType.equals(EDMHelper.REFERENCE_TYPE_RELATED)) {
                                                                        out.print(messageService.getString("e3ps.message.ket_message", "02556") /*제품/금형도면 참조*/);
                                  }
                                  else if(referenceType.equals(EDMHelper.REFERENCE_TYPE_APP)) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "01992")/*승인도면 참조*/);
                                  }
                                  else if(referenceType.equals(EDMHelper.REFERENCE_TYPE_CU)) {
                                    out.print(messageService.getString("e3ps.message.ket_message", "00857")/*고객제출도면 참조*/);
                                  }
                                  else if(referenceType.equals(EDMHelper.REFERENCE_TYPE_REF)) {
                                                                        out.print(messageService.getString("e3ps.message.ket_message", "02621") /*제품참고도면 참조*/);
                                  }
                                  else {
                                    out.print(messageService.getString("e3ps.message.ket_message", "01136")/*기타*/ + "("+referenceType+")");
                                  }
                                %>
                                【<a href="javascript:;" onclick="javascript:doDeleteLink('<%=linkoid%>');"><img src="../../portal/images/icon_delete.gif" border="0" alt='<%=linkoid%>'></a>】
                              </td>
                            </tr>
                  <%
                          }
                        }
                    }
                  %>

              <%  } %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
      <table width="680" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="660" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
