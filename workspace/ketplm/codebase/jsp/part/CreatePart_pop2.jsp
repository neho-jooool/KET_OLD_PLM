<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.code.*,
         e3ps.common.jdf.config.*,
         e3ps.common.util.WCUtil,
         e3ps.common.web.HtmlTagUtil,
         e3ps.part.entity.KETNewPartList,
         e3ps.common.web.PageControl"%>
<%@page import="wt.lifecycle.LifeCycleTemplate,
         wt.fc.PersistenceHelper,
        wt.inf.container.WTContainerHelper,
        wt.org.OrganizationServicesHelper,
        wt.org.WTGroup,
        wt.org.WTUser,
        wt.lifecycle.LifeCycleHelper,
        wt.lifecycle.PhaseTemplate,
        wt.session.SessionHelper,
        java.util.Vector,
        java.util.Hashtable"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%@include file="/jsp/common/context.jsp" %>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%
  Hashtable partList = (Hashtable)request.getAttribute("partList");
  Hashtable condition = (Hashtable)request.getAttribute("condition");
  KETNewPartList ketNewPartList = new KETNewPartList();
  PageControl control = (PageControl)request.getAttribute("control");
  String selectYn = request.getParameter("select");

  String rowCount = "8";
  int count = 0;
  Vector tMap = null;

  WTUser currentUser = (WTUser)SessionHelper.manager.getPrincipal();
  WTGroup wtSysGroup = OrganizationServicesHelper.manager.getGroup("Administrators", WTContainerHelper.service.getExchangeContainer().getContextProvider());
  boolean isSysAdmin = OrganizationServicesHelper.manager.isMember(wtSysGroup, currentUser);
  WTGroup wtBizGroup = OrganizationServicesHelper.manager.getGroup("Business Administrators",WTContainerHelper.service.getExchangeContainer().getContextProvider());
  boolean isBizAdmin = OrganizationServicesHelper.manager.isMember(wtBizGroup, currentUser);
%>

<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=messageService.getString("e3ps.message.ket_message", "01598") %><%--부품채번관리--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language="JavaScript" src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/plm/portal/js/org.js"></SCRIPT>

<style>
body {
padding-bottom: 0px;
padding-left: 10px;
padding-right: 10px;
padding-top: 0px;
}
</style>
<script language="javaScript">

function hideProcessing() {}

/********************************************************************************
 * ajax 객체 생성
 *******************************************************************************/
function getXMLHTTPRequest() {
  var request = false;
  try {
    request = new XMLHttpRequest();
  } catch(err1) {
    try{
      vrequest = new ActiveXObject("Msxml2.XMLHTTP");
    } catch(err2) {
      try {
        request = new ActiveXObject("Microsoft.XMLHTTP");
      } catch(err3) {
        request = false;
      }
    }
  }
  return request;
}

function initComboList(strTarget, strCodeList, strFirst) {
  var objTarget = eval(strTarget);
  var arrCodeList = strCodeList.split(",");
  var i = 0;

  var strName  = "";
  var strValue= "";
  objTarget.options.length = 0;

  if(strFirst != null){
    objTarget.options[0] = new Option(strFirst,"");
    for(i = 0 ; i < arrCodeList.length ; i++ ) {
      strValue   = trim(arrCodeList[i].substring( 0, arrCodeList[i].indexOf("|") ));
      strName   = trim(arrCodeList[i].substring( arrCodeList[i].indexOf("|") + 1, arrCodeList[i].length ));
      objTarget.options[objTarget.options.length] = new Option(strName,strValue);
    }
  }else{
    for(i = 0 ; i < arrCodeList.length; i++ ) {
      strValue   = trim(arrCodeList[i].substring( 0, arrCodeList[i].indexOf("|") ));
      strName   = trim(arrCodeList[i].substring( arrCodeList[i].indexOf("|") + 1, arrCodeList[i].length ));
      objTarget.options[objTarget.options.length] = new Option(strName,strValue);
    }
  }
}

/********************************************************************************
 * 공백제거
 *******************************************************************************/

function trim(str){
   //정규 표현식을 사용하여 화이트스페이스를 빈문자로 전환
   str = str.replace(/^\s*/,'').replace(/\s*$/, '');
   return str; //변환한 스트링을 리턴.
}

function doSelect() {
  var url = "/plm/servlet/e3ps/KETNewPartListServlet";
  document.form.cmd.value = "selectPop";
  document.forms[0].action = url;
  document.forms[0].method = "post";
  document.forms[0].submit();
}

var codeType = null;
function getSelect1(val) { //두번째 콤보 리스트 가져오기
  var code = null;

  if("PT001" == val) {
      codeType = "NEWPRODUCTTYPE";
      code = "top";
  } else {
      codeType = "NEWDIETYPE";
      code = "top";
  }

  xmlHttp = getXMLHTTPRequest();
  var gourl = "/plm/jsp/part/CreatePart_ajax.jsp?type=getNumberCodeLevel&codeType="+codeType+"&code="+code;
  xmlHttp.onreadystatechange = callbackFunction2;
  xmlHttp.open("GET", gourl, true);
  xmlHttp.send(null);
}

function callbackFunction2() {
  if(xmlHttp.readyState == 4) {
    if(xmlHttp.status == 200) {
      var optionArr = xmlHttp.responseText;
      var y=document.getElementById("select1");
      y.options[0] = new Option("<%=messageService.getString("e3ps.message.ket_message", "00609") %><%--개발검토--%>","NP007");
      changeSelect2();
    }
  }
}

function changeSelect2() {
  var y=document.getElementById("select1");

  xmlHttp = getXMLHTTPRequest();
  var gourl = "/plm/jsp/part/CreatePart_ajax.jsp?type=getNumberCodeLevelType&codeType="+codeType+"&code="+y.options[y.selectedIndex].text;
  xmlHttp.onreadystatechange = callbackFunction;
  xmlHttp.open("GET", gourl, true);
  xmlHttp.send(null);
}

function callbackFunction() {
  if(xmlHttp.readyState == 4) {
    if(xmlHttp.status == 200) {
      var optionArr = xmlHttp.responseText;
      initComboList("form._select2", optionArr, null);
    }
  }
}

//페이지 조회
function gotoPage(pageno){
  document.getElementById("page").value = pageno;
  doSelect();
}

function doDisplay(idx) {

    document.form.partNumber.value = document.getElementById("partNumber"+idx).value.substring(partcodeLen, document.getElementById("partNumber"+idx).value.length);
    document.form.partName.value = document.getElementById("partName"+idx).value;
    document.form.rawMaterial.value = document.getElementById("rawMaterial"+idx).value;
    document.form.customer.value = document.getElementById("customer"+idx).value;
    document.form.description.value = document.getElementById("description"+idx).value;
    document.form.del.value = document.getElementById("oId"+idx).checked;
    document.form.oId.value = document.getElementById("oId"+idx).value;
}

//초기값 셋팅
function initPage(){
  <%
  if( condition != null && !condition.isEmpty() ){
    %>
    document.form.page.value = '<%=condition.get("page")%>';

    <%
  }
  if( control != null ){
%>
    document.forms[0].totalCount.value = '<%=control.getTotalCount()%>';
<%
  }
  if(!"no".equals(selectYn)) {
%>
    doSelect();
<%
  }
%>

}

function controlChk(val, val2) {
  for(var ii=0; ii<document.form.checkbox.length; ii++) {
    if(ii != Number(val)) {
      document.form.checkbox[ii].checked = false;
    }
  }
  document.form.partNumber.value = document.getElementById("partNumber"+val2).value;
  document.form.partName.value = document.getElementById("partName"+val2).value;
}

function doClose() {
  var len = document.form.checkbox.length;
  if(len) {
    for(var ii=0; ii<document.form.checkbox.length; ii++) {
      if(document.form.checkbox[ii].checked == true) {
        opener.document.getElementById("number").value = document.getElementById("partNumber").value;
        opener.document.getElementById("name").value = document.getElementById("partName").value;
        self.close();
      }
    }
  } else {
    if(document.form.checkbox.checked == true) {
      opener.document.getElementById("number").value = document.getElementById("partNumber").value;
      opener.document.getElementById("name").value = document.getElementById("partName").value;
      self.close();
    }
  }
}

</script>

</head>

<body onload="javascript:getSelect1(document.getElementById('_select0').options[document.getElementById('_select0').selectedIndex].value);initPage();">
<form name="form" method="post" action="">
<input name="cmd" type="hidden">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sort" value="1 DESC">
<input type="hidden" name="partNumber">
<input type="hidden" name="partName">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
            <table height="28" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00132") %><%--CP도면번호추가--%></td>
                  <td width="10"></td>
                </tr>
            </table>
            </td>
            <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
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
          <td >
          <input type="hidden" value="PT001" name="select0">
          <select name="_select0" style="width:0" disabled>
          <%
            tMap = NumberCodeHelper.manager.getNumberCodeLevel("NEWPARTTYPE", "");

      for(int i = 0; i < tMap.size(); i++) {
        NumberCode tNum = (NumberCode)tMap.get(i);
      %>
        <option value="<%=tNum.getCode()%>"><%=tNum.getName()%></option>
      <%
        }
      %>
          </select>
          <select name="select1"  style="width:0" disabled>
          </select>
          <input type="hidden" value="CP" name="select2">
          <select name="_select2"  style="width:0" disabled>
            </select></td>
            <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doClose();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a></td>
                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
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
       <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
              <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></td>
                <td width="40" class="tdblueM">No</td>
                <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="160" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%></td>
                <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td width="70" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
              </tr>
              <%
                  int lastInx = 0;
                   if( control != null) {
                     Kogger.debug("control.getTotalCount = " + control.getTotalCount() + "CurrentPage = " + control.getCurrentPage() + "getInitPerPage = "+ control.getInitPerPage());
                     lastInx = control.getTotalCount() - ((control.getCurrentPage() - 1) * control.getInitPerPage());
                   }
              if( partList != null && partList.size() > 0 ){
                int idx = 0;
                String style = "";
                for(int ii = 0 ; ii <  partList.size(); ii++){
                  idx = partList.size()-ii;
                  ketNewPartList = (KETNewPartList) partList.get(ii+"");

                  if(ketNewPartList.getDel()) {
                    style = "style='background-color: #E9E9E9;'";
                  } else {
                    style = "";
                  }
              %>
              <tr>
                <td width="40" class="tdwhiteM" <%=style%> ><input type="checkbox" name="checkbox" id="oId<%=idx%>" onClick="javascript:controlChk('<%=ii%>', '<%=idx%>');" /></td>
                <td width="40" class="tdwhiteM" <%=style%>><%=idx%></td>
                <td width="70" class="tdwhiteM" <%=style%> id="partNumber<%=idx%>" value="<%=ketNewPartList.getPartNumber()%>"><%=ketNewPartList.getPartNumber()%></td>
                <td width="160" class="tdwhiteM" <%=style%> id="partName<%=idx%>" value="<%=ketNewPartList.getPartName()%>"><%=ketNewPartList.getPartName()%></td>
                <td width="150" class="tdwhiteM" <%=style%> id="rawMaterial<%=idx%>" value="<%=ketNewPartList.getRawMaterial()%>"><%=ketNewPartList.getRawMaterial()%></td>
                <td width="90" class="tdwhiteM" <%=style%> id="customer<%=idx%>" value="<%=ketNewPartList.getCustomer()%>"><%=ketNewPartList.getCustomer()%></td>
                <td width="60" class="tdwhiteM" <%=style%> ><%=ketNewPartList.getRegister()%></td>
                <td width="75" class="tdwhiteM" <%=style%> ><%=ketNewPartList.getRegDate()%></td>
                <td width="70" class="tdwhiteM0" <%=style%> id="description<%=idx%>" value="<%=ketNewPartList.getDescription()%>"><%=ketNewPartList.getDescription()%></td>
              </tr>

              <%
                  }
                  } else {
            %>
                <tr>
                  <td colSpan="11"  class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%>
                  </td>
                </tr>
              <%
                  }
          %>
          </table>
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td class="space10"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="pageControlTable">
          <%if(partList != null) {%>
            <%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
        <%} %>
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

      </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
