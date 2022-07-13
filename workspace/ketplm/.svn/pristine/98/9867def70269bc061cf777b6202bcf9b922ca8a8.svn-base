<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Vector,
                 java.util.Hashtable,
                 e3ps.common.code.*,
                 e3ps.common.jdf.config.*,
                 e3ps.common.util.WCUtil,
                 e3ps.common.web.HtmlTagUtil,
                 e3ps.part.entity.KETNewPartList"%>
<%@page import="wt.lifecycle.LifeCycleTemplate,
                 wt.fc.PersistenceHelper,
                 wt.inf.container.WTContainerHelper,
                 wt.org.OrganizationServicesHelper,
                 wt.org.WTGroup,
                 wt.org.WTUser,
                 wt.lifecycle.LifeCycleHelper,
                 wt.lifecycle.PhaseTemplate,
                 wt.session.SessionHelper"%>
<%@include file="/jsp/common/context.jsp" %>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

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
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language="JavaScript" src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/plm/portal/js/org.js"></SCRIPT>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language="javaScript">

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

/********************************************************************************
 * 콤보리스트 생성
 *******************************************************************************/
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

/********************************************************************************
 * 등록
 *******************************************************************************/
function doCreate() {
    if(document.form.partNumber.value == "") {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01590") %><%--부품번호를 입력하세요--%>');
        document.form.partNumber.focus();
    } else if(document.form.partName.value == "") {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01587") %><%--부품명을 입력하세요--%>');
        document.form.partName.focus();
    } else if(document.form.rawMaterial.value == "") {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02222") %><%--원재료명을 입력하세요--%>');
        document.form.rawMaterial.focus();
    } else if(document.form.description.value == "") {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00627") %><%--개발구분을 입력하세요--%>");
        document.form.description.focus();
    } else {
        partSize = 0;
        fPart = true;

        chkPartNumber(); //부품번호 동일한게 있는지 체크
        if(fPart) {
            var url = "/plm/servlet/e3ps/KETNewPartListServlet";
            document.forms[0].action = url;
            document.form.cmd.value = "create";
            document.form.submit();
        }
    }
}

/********************************************************************************
 * 삭제
 *******************************************************************************/
function doDelete(str) {
    if(chkDelete()) {
        var x=document.getElementById("createPartList");
        var y=(x.contentWindow || x.contentDocument);
        if (y.document)y=y.document;
        if(str == "1"){
            y.form.cmd.value = "delete";
            y.form.submit();
        }else{
            if( confirm("<%=messageService.getString("e3ps.message.ket_message", "01827") %><%--선택된 자료를 영구히 삭제합니다.\n정말로 진행하시겠습니까?--%>") ){
                y.form.cmd.value = "deleteReal";
                y.form.submit();
            }
        }

    } else {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01710") %><%--삭제할 데이터를 선택하세요--%>");
    }

}

function chkDelete() {
    var chk = false;
    var x=document.getElementById("createPartList");
    var y=(x.contentWindow || x.contentDocument);
    if (y.document)y=y.document;
    for(var ii=0; ii<y.getElementsByName("checkbox").length; ii++) {
        if(chk) {
            return chk;
        }
        if(y.getElementsByName("checkbox")[ii].checked == true) {
            chk = true;
        }

    }
    return chk;
}

function doClear() {
    document.form.oId.value = "";
    document.form.partNumber.value = "";
    document.form.partName.value = "";
    document.form.rawMaterial.value = "";
    document.form.customer.value = "";
    document.form.description.value = "";
}

var fPart;
var partSize = 1;

function doUpdate() {
    partSize = 1;
    if(document.getElementById("oId").value != "") {
        fPart = true;
        chkPartNumber();
        if(fPart) {
            if(!document.getElementById("del").value || document.getElementById("adminFlag").value ) {
                document.form.cmd.value = "update";
                document.form.submit();
            } else {
                alert("<%=messageService.getString("e3ps.message.ket_message", "01956") %><%--수정할 권한이 없습니다--%>");
            }
        }
    } else {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01957") %><%--수정할 데이터를 선택하세요--%>");
    }
}

function doSelect() {
    var url = "/plm/servlet/e3ps/KETNewPartListServlet";
    document.form.cmd.value = "select";
    document.forms[0].target = "createPartList";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

function doExcel() {
    var url = "/plm/servlet/e3ps/KETNewPartListServlet";
    document.form.cmd.value = "excel";
    document.forms[0].target = "createPartList";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

function doDownload() {
    var url = "/plm/jsp/part/download.jsp";
    document.forms[0].target = "createPartList";
    document.forms[0].action = url;
    document.forms[0].submit();
}

//Number Code Ajax
function numCodeAjax(codeType, code, targetId) {
    $.ajax( {
        url : "/plm/servlet/e3ps/NumberCodeAjax",
        type : "POST",
        data : {codeType:codeType, code:code},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.numCode, function() {
                $("#"+targetId).append("<option value='"+this.code+"' >"+ this.value +"</option>");
            });
        }
    });
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

    $("#select1").empty().data('options');

    numCodeAjax(codeType, "", "select1");

    changeSelect2();

//     xmlHttp = getXMLHTTPRequest();
//     var gourl = "/plm/jsp/part/CreatePart_ajax.jsp?type=getNumberCodeLevel&codeType="+codeType+"&code="+code;
//     xmlHttp.onreadystatechange = callbackFunction2;
//     xmlHttp.open("GET", gourl, true);
//     xmlHttp.send(null);
}

function callbackFunction2() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
            var optionArr = xmlHttp.responseText;
            initComboList("form.select1", optionArr, null);
            changeSelect2();
        }
    }
}

function changeSelect2() {

    $("#select2").empty().data('options');
    numCodeAjax(codeType, $("#select1 option:selected").val(), "select2");

    var x=document.getElementById("select0");
    setPartCode(x.options[x.selectedIndex].value); // partCode 에 값 넣거나 텍스트필드 없애기


//     var y=document.getElementById("select1");
//     xmlHttp = getXMLHTTPRequest();
//     var gourl = "/plm/jsp/part/CreatePart_ajax.jsp?type=getNumberCodeLevelType&codeType="+codeType+"&code="+y.options[y.selectedIndex].text;
//     xmlHttp.onreadystatechange = callbackFunction;
//     xmlHttp.open("GET", gourl, true);
//     xmlHttp.send(null);
}

function callbackFunction() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
            var optionArr = xmlHttp.responseText;
            initComboList("form.select2", optionArr, null);

            var x=document.getElementById("select0");
            setPartCode(x.options[x.selectedIndex].value); // partCode 에 값 넣거나 텍스트필드 없애기
        }
    }
}

function setPartCode(val) { // partCode 에 값 넣거나 텍스트필드 없애기
    doClear();
    var x=document.getElementById("select2");
    document.getElementById("partCode").value = ""; // partCode 를 초기화

    if(x.options[x.selectedIndex].value.search("A~J") != -1) {
        document.getElementById("partCode").value = x.options[x.selectedIndex].value.substring(0, 1);
    } else {
        document.getElementById("partCode").value = x.options[x.selectedIndex].value;
    }
}

function resizeHeight(fr) {
    var frbody = fr.contentWindow.document.body;
    fr.style.height = frbody.scrollHeight + ( frbody.offsetHeight - frbody.clientHeight );
}

function openPartNoList() {  // 파트 넘버 체계
    //window.open("/plm/jsp/part/CreatePart_pop.jsp", "_blank", "width=1000, height=500");
}
/********************************************************************************
 * 부품번호 자릿수 체크
 *******************************************************************************/
function chkPartNumberLength() {
    var y=document.getElementById("select1");

    //alert(y.options[y.selectedIndex].value);
    //alert("part : "+getPartLen("part"));
    //alert("len : "+getPartLen("len"));
    if(y.options[y.selectedIndex].value == "NP010" || y.options[y.selectedIndex].value == "NP011" || y.options[y.selectedIndex].value == "NP012") {
        return true;
    }else {
        if(getPartLen("part") != getPartLen("len")) {
            alert(y.options[y.selectedIndex].text + " 번호는 "+ getPartLen("txt") + '<%=messageService.getString("e3ps.message.ket_message", "02412") %><%--자리 기준 입니다--%>');
            fPart = false;
            document.form.partNumber.focus();
            return false;
        } else {
            return true;
        }
    }
}

function getPartLen(val) {
    var y=document.getElementById("select1");
    var len = 0;
    var lenTxt = 0;

    if(y.options[y.selectedIndex].value == "NP001") { // 자동차 모듈
        len = 4;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP002") { // 커넥터류
        len = 4;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP003") { // 프레스 제품
        len = 4;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP004") { // 구매품
        len = 4;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP005") { // IT ASSY
        len = 4;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP006") { // 구매품(MG/R)
        len = 4;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP007") { // 개발검토
        len = 6;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP008") { // 하네스류
        len = 4;
        lenTxt = 6;
    } else if(y.options[y.selectedIndex].value == "NP009") { // 전장모듈EM
        len = 4;
        lenTxt = 8;
    } else if(y.options[y.selectedIndex].value == "ND001") { // 사출금형
        len = 7;
        lenTxt = 8;
    } else if(y.options[y.selectedIndex].value == "ND002") { // 프레스금형
        len = 7;
        lenTxt = 8;
    } else if(y.options[y.selectedIndex].value == "ND003") { // 분단금형
        len = 6;
        lenTxt = 8;
    } else if(y.options[y.selectedIndex].value == "ND004") { // 시험금형
        len = 6;
        lenTxt = 8;
    } else if(y.options[y.selectedIndex].value == "ND005") { // OEM금형
        len = 7;
        lenTxt = 8;
    }

    if(val == "len") {
        return len;
    } else if(val == "txt") {
        return lenTxt;
    } else if(val == "part") {
        var x=document.getElementById("select0");
        if(x.options[x.selectedIndex].value == "PT001") {
            return getNumberLength(document.form.partNumber.value);
        } else {
            return document.form.partNumber.value.length;
        }
    }
}

function getNumberLength(val) { // 숫자가 아닌 것이 나올 때까지 숫자 카운트
    var cnt=0;
    var str;
    for(var ii=0; ii<val.length; ii++) {
        str = val.substr(ii, 1);
        if(isFinite(str)) {
            cnt+=1
        } else {
            break;
        }
    }

    return cnt;
}

/********************************************************************************
 * 부품 번호 동일한게 있는지 체크
 *******************************************************************************/
function chkPartNumber() { // 부품 번호 동일한게 있는지 체크

    xmlHttp = getXMLHTTPRequest();
    var gourl = "/plm/jsp/part/CreatePart_ajax.jsp?type=chkPartNumber&codeType="+document.form.partCode.value+"&code="+document.form.partNumber.value;
    xmlHttp.onreadystatechange = chkPartNumberCallbackFunction;
    xmlHttp.open("GET", gourl, false);
    xmlHttp.send(null);
}

function chkPartNumberCallbackFunction() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
            var partNumberSize = xmlHttp.responseText;
            if(partSize < Number(trim(partNumberSize))) {
                fPart = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "01303") %><%--동일한 부품번호가 존재 합니다--%>');
                document.form.partNumber.focus();
            } else { // 동일한 부품번호가 없이 패스 한다면..
                chkPartNumberLength(); //부품번호 자릿수 체크
            }
        }
    }
}

function openPartListPop() {
    window.open("/plm/jsp/part/CreatePart_pop2.jsp", "_blank", "width=780, height=500");
}

</script>

</head>

<body class="body-space" onload="javascript:getSelect1(document.getElementById('select0').options[document.getElementById('select0').selectedIndex].value);">
<form name="form" method="post" action="">
<input name="cmd" type="hidden">
<input name="select3Text" type="hidden">
<input name="adminFlag" type="hidden">
<input type="hidden" name="page" value="">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01598") %><%--부품채번관리--%></td>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif">BOM<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01598") %><%--부품채번관리--%></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doDownload();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02914") %><%--코드체계--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td  class="tdwhiteL0">
          <select id="select0" name="select0" class="fm_jmp" style="width:120" onChange="javascript:getSelect1(this.options[this.selectedIndex].value);">
              <%
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "NEWPARTTYPE");
              numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

              for ( int i=0; i<numCode.size(); i++ ) {
              %>
              <option value="<%=numCode.get(i).get("code") %>" ><%=numCode.get(i).get("value")%></option>
              <%
              }
              %>
          </select>
          <select id="select1" name="select1" class="fm_jmp" style="width:120" onChange="javascript:changeSelect2();">
          </select>
          <select id="select2" name="select2" class="fm_jmp" style="width:250" onChange="javascript:setPartCode(document.getElementById('select0').options[document.getElementById('select0').selectedIndex].value);">
            </select></td>
            <td align="right" class="tdwhiteR0">
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
                </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td>&nbsp;</td>
        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><a href="javascript:doExcel();"><img src="../../portal/images/iocn_excel.png" border="0"></a></td>
              </tr>
            </table></td>
            <td width="10"></td>
            <td align="right">
              <select name="perPage" class="fm_jmp" style="width:80">
                    <option value="10">10</option>
                  <option value="30">30</option>
                  <option value="50">50</option>
                  <option value="100">100</option>
                  </select>
                </td>
          </tr>
        </table></td>
      </tr>
      </table>

      <iframe name="createPartList" frameborder="0" scrolling="no" width="780" src="/plm/jsp/part/CreatePartList.jsp" onload="resizeHeight(this)"></iframe>

      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doClear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doUpdate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td>
                <%
                    if(isSysAdmin || isBizAdmin)
                    {
                %>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doDelete(1);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
                </table>
                <%
                    }
                  %>
                 </td>
                 <td width="5">&nbsp;</td>
                <td>
                <%
                    if(isSysAdmin || isBizAdmin)
                    {
                %>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doDelete(2);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02182") %><%--완전삭제--%></a></td>
                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
                </table>
                <%
                    }
                  %>
                 </td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%><span class="red">*</span></td>
          <td width="160" class="tdwhiteL"><input name="partCode"  style="width:50;border-style:groove;background:#e9e9e9;text-align:right;height:20px;margin-bottom: 2px;" type="text" readonly><input name="partNumber" style="width:100;" type="text" class="txt_field"></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%><span class="red">*</span></td>
          <td width="160" class="tdwhiteL"><input name="partName" type="text" class="txt_field"  style="width:150">
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
          <td width="160" class="tdwhiteL0"><input name="customer" type="text" class="txt_field"  style="width:150"></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%><span class="red">*</span></td>
          <td width="160" class="tdwhiteL"><input name="rawMaterial" type="text" class="txt_field" style="width:150"></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0"><input name="description" type="text" class="txt_field" style="width:150" value="전략개발"><input type="hidden" name="oId"><input type="hidden" name="del"></td>
        </tr>
      </table>
      </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
