<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
        e3ps.dms.entity.KETDocumentCategory"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String categoryCode = request.getParameter("oid");
  String docCatePath = request.getParameter("path");

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01609") %><%--분류체계등록--%></title>
<link rel="stylesheet" href="../../portal/css/e3ps.css" type="text/css">
<script language="JavaScript">
<!--
//필수항목체크
function valcheck() {
  var d = document.forms[0];
  var code;

  if( isNull(d.categoryName.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01612") %><%--분류체계명은 반드시 입력해야 합니다--%>');
    return false;
  }

  if(IsSpecialStr(d.categoryName.value) ) {
    return false;
  }

  var s = d.categoryName.value;
  if (s.length > 200){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01611") %><%--분류체계명은 200자를 초과할 수 없습니다--%>');
    return false;
  }

  code = d.docTypeCode.value;

  if( isNull(d.docTypeCode.value) ) {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02910") %><%--코드는 2자리로 반드시 입력해야 합니다--%>');
    return false;
  }

  if( code.length != 2 ) {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02910") %><%--코드는 2자리로 반드시 입력해야 합니다--%>');
    return false;
  }else{

    if((code.charCodeAt(0)<65)||(code.charCodeAt(0)>90)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02911") %><%--코드는 영문대문자여야 합니다--%>');
      return false;
    }

  }

  if ( isNull(d.sortNo.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02509") %><%--정렬순서는 반드시 입력해야 합니다--%>');
    return false;
  }

  if ( isNotDigit(d.sortNo.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02508") %><%--정렬순서는 반드시 숫자여야 합니다--%>');
    return false;
  }

  return true;
}

function isNull(str) {
  if(str==null||str==""){
    return true;
  }
  return false;
}

function isNotDigit(str) {
  var pattern = /^[0-9]+$/;

  if(!pattern.test(str)){
    return true;
  }
  return false;
}

function insertDocCategory() {
//입력된 분류체계를 DocuCateServlet를 호출하여 저장한다.
  if (!valcheck()) return;
  else {
    var d = document.forms[0];
    //d.categoryName.value = encodeURIComponent(d.categoryName.value);
    d.action = '/plm/servlet/e3ps/DocuCateServlet';
    d.submit();
  }
}

function cancel(oid)
{
  document.location.href="/plm/jsp/dms/ViewDocuCate.jsp?oid="+oid;
}

function IsSpecialStr( obname ) {
    //var specialChars='~`!@#$%%^&*-=+\|[{]};:\',<.>/?';
    var specialChars='/';
    var str=obname;
    var i, j;
    if (str == '') {

    }else{
      for (i = 0; i < str.length; i++) {

        for (j = 0; j < specialChars.length; j++) {

          if (str.charAt(i) == specialChars.charAt(j)){
            alert(str.charAt(i)+'<%=messageService.getString("e3ps.message.ket_message", "00021") %><%--{0}는 사용불가합니다--%>');
            return true;
          //str = str.replace(str.charAt(i), "");
        }
        }
      }
  }

    return false;
  }
//-->
</script>
</head>

<body style="margin:30px 15px" bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form name=form01 method="post">
<input type="hidden" name="cmd" value="create">
<input type=hidden name=oid value="<%=categoryCode%>">
<input type=hidden name=docCatePath value="<%=docCatePath%>">

<div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "01609") %><%--분류체계등록--%>
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 100%">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:insertDocCategory();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="cancel('<%=categoryCode%>');" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 100%" summary="">
          <colgroup>
            <col width="20%" />
            <col width="80%" />
          </colgroup>
          <tbody>
            <tr>
              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01610") %><%--분류체계명--%><span class="red">*</span></td>
              <td>
                <input type=text size=40 name="categoryName">
              </td>
            </tr>
            <%
				  Map<String, Object> parameter = new HashMap<String, Object>();
				  parameter.put("codeType", "LANGUAGE");
				  List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);
				  Map<String, Object> numberCode = new HashMap<String, Object>();
				  for ( Map<String, Object> item : list ) {
				      if ( !item.get("code").equals("ko") ) {
				  %>
				          <tr>
				              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01610") %><%--분류체계명--%>(<%=item.get("value") %>)</td>
				              <td><input type='text' name='categoryName_<%=item.get("code") %>' size=40 value=""></td>
				          </tr>
				  <%
				      }
				  }
			 %>
			 <tr>
			     <% if(categoryCode.equals("ROOT")){%>
			     <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%><span class="red">*</span></td>
			     <td><input type=text size=10 name="docTypeCode"></td>
			     <%}else{%>
			     <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
                 <td><%=categoryCode.substring(0,2)%><input type=hidden name=docTypeCode value="<%=categoryCode.substring(0,2)%>"></td>
			     <%}%>
			 </tr>
			 
			 <tr>
			     <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01758") %><%--상위경로--%></td>
			     <td><%=docCatePath%></td>
			 </tr>
			 
			 <tr>
			     <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03461") %><%--채번특성--%>1</td>
			     <td><input type=text size=40 name="numberingcode_levle_1" value="" onfocus='blur' ></td>
			 </tr>
			 
			 <tr>
			     <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03461") %><%--채번특성--%>2</td>
			     <td><input type=text size=40 name="numberingcode_levle_2" value="" onfocus='blur' style="width:40%" ><Font Color = 'red'> 채번특성1이 입력되어 있는 경우, 채번특성2가 입력되지 않으면 개발산출문서 생성시 [품번]이 문서번호로 사용됩니다</Font></td>
			 </tr>
			 
			 <tr>
			     <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02507") %><%--정렬순서--%><% if((categoryCode.substring(2,3).equals("1"))||(categoryCode.substring(2,3).equals("O"))){%><span class="red">*</span><%}%></td>
			     <td><input type=text size=10 name="sortNo" value='0' ></td>
			 </tr>
			 
			 <tr>
                 <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01672") %><%--사용여부--%><span class="red">*</span></td>
                 <td>
                    <input name="isUsed" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01669") %><%--사용--%>
                    <input name="isUsed" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01450") %><%--미사용--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">APQP <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isAPQP" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isAPQP" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">PSO10 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isPSO10" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isPSO10" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">ESIR <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isESIR" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isESIR" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00258") %><%--ISIR(Car) 대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isISIRCar" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isISIRCar" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">ISIR(Elec) <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isISIRElec" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isISIRElec" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">ANPQP <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isANPQP" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name=isANPQP type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">SYMC <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isSYMC" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name=isSYMC type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">DRCheckSheet <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isDRCheckSheet" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isDRCheckSheet" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">부서 권한 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isDesignSheet" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isDesignSheet" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">대내비 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isSecurity" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isSecurity" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">자재중복체크 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isMatlDupCk" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isMatlDupCk" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
			 <tr>
                 <td class="title">정기개정필요 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                 <td>
                    <input name="isPrdRvsRqr" type="radio" class="Checkbox" value="true" checked><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isPrdRvsRqr" type="radio" class="Checkbox" value="false" ><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
             </tr>
             
          </tbody>
        </table>
      </div>
</div>
</form>
</body>
</html>
