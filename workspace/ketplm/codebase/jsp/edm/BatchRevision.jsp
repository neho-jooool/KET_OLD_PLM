<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="wt.epm.*,wt.fc.*,wt.folder.*,wt.org.*, wt.part.WTPart,wt.util.*,wt.inf.container.WTContainerRef"%>
<%@ page import="wt.vc.Versioned,wt.vc.Iterated,wt.vc.VersionControlHelper,wt.vc.config.LatestConfigSpec,wt.vc.wip.Workable,wt.clients.vc.CheckInOutTaskLogic"%>
<%@ page import="wt.lifecycle.LifeCycleManaged,wt.htmlutil.HtmlUtil"%>
<%@ page import="e3ps.common.util.CommonUtil,e3ps.common.util.DateUtil"%>
<%@ page import="e3ps.edm.*,e3ps.edm.beans.*,e3ps.edm.util.*"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  String webAppName = CommonUtil.getWebAppName();

  boolean isAdmin = CommonUtil.isAdmin();

  EDMProperties edmProperties = EDMProperties.getInstance();

  String command = request.getParameter("command");
  String oid = request.getParameter("oid");


  ArrayList revisedObjs = new ArrayList();

  Versioned versioned = null;

  ReferenceFactory rf = new ReferenceFactory();
  try {
    versioned = (Versioned)rf.getReference(oid).getObject();
  }
  catch(Exception e) {
      Kogger.error(e);
  }

  if((versioned != null) && !VersionHelper.isLatestRevision(versioned)) {
%>
  <script>
        alert('<%=messageService.getString("e3ps.message.ket_message", "02841") %><%--최신 버전이 아닙니다!!!\n최신 버전을 개정하시기 바랍니다--%>');
    if( window.dialogArguments != null) {
      window.returnValue=false;
      window.close();
    }

    if(opener != null) {
      window.close();
    } else {
      history.go(-1);
    }
  </script>
<%
  }

  if(versioned != null) {
    revisedObjs.add(versioned);
  }

  if( (versioned != null) && (versioned instanceof EPMDocument) ) {
    revisedObjs = EDMHelper.getAssociateDocsBy((EPMDocument)versioned);
  }

  if(revisedObjs == null) {
    revisedObjs = new ArrayList();
  }

  revisedObjs.add(0, versioned);
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
<SCRIPT language=JavaScript src="js/edm.js"></SCRIPT>
<script language=JavaScript src="/<%=webAppName%>/portal/js/jquery-1.4.3.min.js"></script>
<SCRIPT language=JavaScript src="/<%=webAppName%>/portal/js/jquery.form.js"></SCRIPT>
<SCRIPT LANGUAGE=JavaScript>
<!--
var $jquery=jQuery.noConflict(true);

$jquery(document).ready(function() {

  var options = {
        //target:        '#output1',    // target element(s) to be updated with server response
        beforeSubmit:  showRequest,      // pre-submit callback
        success:       showResponse,    // post-submit callback

        // other available options:
        //url:       url          // override for form's 'action' attribute
        type:      'post' ,          // 'get' or 'post', override for form's 'method' attribute
        dataType:  'xml'          // 'xml', 'script', or 'json' (expected server response type)
        //clearForm: true          // clear all form fields after successful submit
        //resetForm: true          // reset the form after successful submit

        // $.ajax options can be used here too, for example:
        //timeout:   3000
    };

  // bind form using 'ajaxForm'
    $jquery('#reviseForm').ajaxForm(options);
  /*
  // bind to the form's submit event
    $jquery('#reviseForm').submit(function() {
        // inside event callbacks 'this' is the DOM element so we first
        // wrap it in a jQuery object and then invoke ajaxSubmit
        $jquery(this).ajaxSubmit(options);

        // !!! Important !!!
        // always return false to prevent standard browser submit and page navigation
        return false;
    });
  */
});

// pre-submit callback
function showRequest(formData, jqForm, options) {
  return true;
  //return validate(jqForm[0]);
}

// post-submit callback
function showResponse(responseXML, statusText, xhr, jqForm)  {
  rtnRevise(responseXML);
}

function validate(_form) {
  var red = new Array();

    var redObj = _form.roid;

  if(redObj.length) {
    for(var i = 0; i < redObj.length; i++) {
      if(redObj[i].checked == true) {
        red[red.length] = redObj[i].value;
      }
    }
  }else{
    if(redObj.checked == true) {
      red[red.length] = redObj.value;
    }
  }

  if(red.length == 0) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00685") %><%--개정 대상을 선택하시기 바랍니다--%>");
    return false;
  }

  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03287") %><%--개정하시겠습니까?--%>")) {
    return false;
  }

  return true;
}

function doRevise() {
  if(!validate(document.forms[0])) {
    return;
  }
  document.forms[0].command.value="doRevise";
  document.forms[0].fsubmit.click();
}


var isModal = false;
if( window.dialogArguments != null) {
  isModal = true;
}

function rtnRevise(xmlDoc) {

  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
    return;
  }

  alert("<%=messageService.getString("e3ps.message.ket_message", "00686") %><%--개정 완료했습니다--%>");
  if( (isModal==true) || (opener != null) ) {
    if(isModal==true) {
      window.returnValue=msg;
    } else {
      opener.document.location.reload();
    }
    window.close();
  } else {
    document.location.href = "/<%=webAppName%>/jsp/edm/SearchEPMDocument.jsp";
  }
}


function allCheck(_all,_itemName) {
  var chkItems = document.getElementsByName(_itemName);
  for(var i = 0; i < chkItems.length; i++) {
    if(chkItems[i].disabled==false) {
      chkItems[i].checked = _all.checked;
    }
  }
}
// -->
</SCRIPT>
</head>
<body>
<form id="reviseForm" name="reviseForm" action="/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp" method=post>
<!-- hidden -->
<input type=hidden name="command" value="">
<!-- hidden end -->
<div style="display:none"><input type="submit" name="fsubmit" value="Submit" /></div>

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
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></td>
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
            <table width="660" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:doRevise();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                <td width="50" class="tdblueM">
                  <input type="checkbox" name="chkAll" value="" onClick="javascript:allCheck(this,'roid');"/>
                </td>
                                <td width="210" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01297") %><%--도번--%></td>
                                <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01295") %><%--도명--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                <td width="100" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
              </tr>
              <%  Versioned revisedObj = null;
                for(int i = 0; i < revisedObjs.size(); i++) {
                  revisedObj = (Versioned)revisedObjs.get(i);

                  String roid = "";
                  String revisedNr = "";
                  String revisedNm = "";
                  String revisedVer = "";
                  String revisedCreator = "";

                  if( revisedObj instanceof EPMDocument ) {
                    revisedNr = ((EPMDocument)revisedObj).getNumber();
                    revisedNm = ((EPMDocument)revisedObj).getNumber();
                    revisedVer = ((EPMDocument)revisedObj).getVersionIdentifier().getSeries().getValue();
                    revisedCreator = ((EPMDocument)revisedObj).getCreatorFullName();

                    try {
                      roid = rf.getReferenceString((EPMDocument)revisedObj);
                    } catch (WTException e) {
                	Kogger.error(e);
                    }
                  } else if( revisedObj instanceof WTPart ) {
                    revisedNr = ((WTPart)revisedObj).getNumber();
                    revisedNm = ((WTPart)revisedObj).getNumber();
                    revisedVer = ((WTPart)revisedObj).getVersionIdentifier().getSeries().getValue();
                    revisedCreator = ((WTPart)revisedObj).getCreatorFullName();

                    try {
                      roid = rf.getReferenceString((WTPart)revisedObj);
                    } catch (WTException e) {
                	Kogger.error(e);
                    }
                  }

                  boolean isLatestRevision = VersionHelper.isLatestRevision(revisedObj);
                  boolean isLatestIteration = true;
                  boolean isReleased = true;
                  boolean isCheckout = false;


                  if( revisedObj instanceof LifeCycleManaged ) {
                    //String statekey = ((LifeCycleManaged)revisedObj).getState().getState().toString();
                    isReleased = edmProperties.isReleasedState(((LifeCycleManaged)revisedObj).getState().getState().toString());
                  }

                  if( revisedObj instanceof Workable ) {
                    isCheckout = CheckInOutTaskLogic.isCheckedOut((Workable)revisedObj);
                  }

                  if (revisedObj instanceof Iterated) {
                    isLatestIteration = VersionControlHelper.isLatestIteration((Iterated)revisedObj);
                  }


                  String alertMsg = "";
                  if(!isReleased) {
                    alertMsg = "▶" + messageService.getString("e3ps.message.ket_message", "01997")/*승인완료 도면/부품이 아닙니다*/ + "\n";
                  }

                  if(isCheckout) {
                    alertMsg += "▶" + messageService.getString("e3ps.message.ket_message", "02804")/*체크아웃된 도면/부품 입니다*/ + "\n";
                  }

                  if(!isLatestRevision) {
                    alertMsg += "▶" + messageService.getString("e3ps.message.ket_message", "02840")/*최신 버전이 아닙니다*/ + "\n";
                  }

                  if(!isLatestIteration) {
                    alertMsg += "▶" + messageService.getString("e3ps.message.ket_message", "02842")/*최신 이터레이션이 아닙니다*/ + "\n";
                  }

                  boolean isRevisable = true;
                  if(!isLatestRevision || !isLatestIteration || !isReleased || isCheckout) {
                    isRevisable = false;
                  }
              %>
                  <tr>
                    <td class="tdwhiteM">
                      <input type="checkbox" name="roid" value="<%=roid%>" <%if(!isRevisable){%>disabled<%}%> />
                    </td>
                    <td class="tdwhiteL" title="<%=alertMsg%>">
                      <div class="ellipsis" style="width:205;"><%if(isRevisable){%><%=revisedNr%><%}else{%><font color=red><%=revisedNr%></font><%}%></nobr></div>
                    </td>
                    <td class="tdwhiteL" title="<%=HtmlUtil.escapeFormattedHTMLContent(revisedNm)%>">
                      <div class="ellipsis" style="width:195;"><%=revisedNm%></nobr></div>
                    </td>
                    <td class="tdwhiteM"><%=revisedVer%></td>
                    <td class="tdwhiteM"><%=revisedCreator%></td>
                    <td class="tdwhiteM0"><%=DateUtil.getDateString(PersistenceHelper.getCreateStamp(revisedObj),"d")%></td>
                  </tr>
              <%
                }
              %>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
      <table width="660" border="0" cellspacing="0" cellpadding="0">
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
