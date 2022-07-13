<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
                e3ps.common.util.StringUtil,
                java.util.ArrayList"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
String method = StringUtil.trimToEmpty(request.getParameter("method"));
if( (method == null) || (method.trim().length() == 0)){
    method = "setDevRequest";
}

String mode = StringUtil.trimToEmpty(request.getParameter("mode"));
if( (mode == null) || (mode == "multi") || (mode.trim().length() == 0)){
    mode = "multi";
}else{
    mode = "one";
}

String developmentStep = StringUtil.trimToEmpty(request.getParameter("developmentStep"));
if( (developmentStep == null) || (developmentStep.trim().length() == 0)){
    developmentStep = "0";
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00603") %><%--개발(검토)의뢰검색팝업--%></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>

</style>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
<!--
function deleteValue(param){
      document.getElementById(param).value = "";
}


function doSubmit(){

    var obj = document.getElementById('dummy');

    var d = document.form01;
    if(d.DevelopmentStep.disabled == false){
        d.DevelopmentStepStr.value  = d.DevelopmentStep.value;
    }
    obj.src = "./SearchDevRequestPopList.jsp?mode=" + d.mode.value + "&RequestNo=" + d.RequestNo.value + "&DevelopmentStep=" + d.DevelopmentStep.value + "&DevProductName=" + d.DevProductName.value;
}

function doClear(){
    var tBody = document.getElementById("iDrTable");
    var tLength = tBody.rows.length;

    for(var i=0; i<tLength-1; i++){
        tBody.deleteRow(1);
    }
}

function controlChk(){
    var body = document.getElementById("iDrTable");
    if (body.rows.length == 1) return;
    if (document.forms[0].mode.value == "one") return;
    var inputs = body.getElementsByTagName('input');
    var drChecks = document.forms[0].iDrChAll;

    for (var i = 0; i < inputs.length; i++){
        var item = inputs.item(i);
        if (item.id.indexOf("iDrChk") > -1){
            if (drChecks.checked){
                item.checked = true;
            }else{
                item.checked = false;
            }
        }
    }
}

function getCheckedOid(){
    var body = document.getElementById("iDrTable");
    var inputs = body.getElementsByTagName('input');
    var docuChecks = false;
    var arr = new Array();
    var subArr = new Array();
    var form = document.form01;
    var idx = 0;

    if (body.rows.length == 1) return arr;


    for (var i = 0; i < inputs.length; i++){
        var item = inputs.item(i);
        if (item.id.indexOf("iDrChk") > -1){
            if (item.checked){
                subArr = new Array();
                subArr[0] = item.iDrOid;
                subArr[1] = item.drNo;
                subArr[2] = item.drName;
                subArr[3] = item.drState;
                arr[idx++] = subArr;
                //alert("arr==>"+idx+":"+arr[idx-1]);
            }
        }
    }

    return arr;
}

function doSelect() {
    var arr = getCheckedOid();

    if(arr.length == 0) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02255") %><%--의뢰서가 선택되지 않았습니다--%>');
        return;
    }
    var opener = window.dialogArguments;
    if(opener.<%=method%>) {
        opener.<%=method%>(arr);
    }

    self.close();
}

function doClear1(){
    document.form01.reset();
    doClear();
}
//////////////아래와 같이 호출한다.
function selectDocu(){
    var url="../../jsp/dms/SearchDevRequestPop.jsp?method=selDr";
    openWindow(url,"","800","600","status=1,scrollbars=no,resizable=no");
}

function selDr(arr){

    for (var i = 0; i < arr.length; i++){
       var drOid = arr[i];
    }
}


//-->
</script>
<script id='dummy'></script>
<body>
<form name=form01 method="post" >
<input type=hidden name=DevelopmentStepStr value="<%=developmentStep%>">
<input type=hidden name=mode value="<%=mode%>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="../../portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00600") %><%--개발(검토)의뢰 검색--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doClear1();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                    </table></td>
                    <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSubmit();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                    </tr>
                    </table>
                 </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>

             <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>

                <td width="65" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                <td colspan="3" width="150" class="tdwhiteL0">
                  <select name="DevelopmentStep" <%if(!developmentStep.equals("0")) out.print("disabled");%> class="fm_jmp" style="width:150">
                      <option value="0" <%if(developmentStep.equals("0")){%> selected <%}%>> <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
                      <option value="R" <%if(developmentStep.equals("R")){%> selected <%}%>> <%=messageService.getString("e3ps.message.ket_message", "00617") %><%--개발검토의뢰--%></option>
                      <option value="D" <%if(developmentStep.equals("D")){%> selected <%}%>><%=messageService.getString("e3ps.message.ket_message", "00656") %><%--개발의뢰--%></option>
                  </select>
                </td>
              </tr>
              <tr>
                <td width="65" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></td>
                <td class="tdwhiteL0"><input type="text" name="RequestNo" class="txt_field"  style="width:90" id="textfield"></td>
                <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00605") %><%--개발(검토)제품명--%></td>
                <td class="tdwhiteL0"><input type="text" name="DevProductName" class="txt_field"  style="width:150" id="textfield"></td>
              </tr>

            </table>

            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>

            <table border="0" cellspacing="0" cellpadding="0" width="460"><tr><td>
            <DIV style="width:100%; height:280px; overflow-y:scroll; padding:0px; border:1; border-style:solid; border-color:#EBEBEB">
            <table border="0" cellspacing="0" cellpadding="0" width="440"  >
                 <tbody id="iDrTable"/ >
              <tr>
                <td width="40" class="tdblueM"><%if(mode.equals("multi")){%><input type="checkbox" id="iDrChAll" name="iDrChAll" onClick="javascript:controlChk()"><%}else{%>&nbsp;<%}%></td>
                <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></td>
                <td width="170" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00605") %><%--개발(검토)제품명--%></td>
                <td width="60" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              </tr>

            </table>
            </DIV>
            </td></tr></table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
