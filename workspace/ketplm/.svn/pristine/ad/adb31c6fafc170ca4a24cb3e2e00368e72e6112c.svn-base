<%--
    /**
     * @(#)  updatepeople.jsp
     * Copyright (c) whois. All rights reserverd
     *
     * @version 1.00
     * @since jdk 1.4.02
     * @author Cho Sung Ok
     */
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*,e3ps.common.web.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    String oid = ParamUtil.getStrParameter(request.getParameter("oid"));
    PeopleData data = new PeopleData(CommonUtil.getObject(oid));
%>
<html>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<title>Untitled Document</title>
<%@include file="/jsp/common/top_include.jsp" %>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=javascript>

function isValidFormat(input,format) {
    if (input.value.search(format) != -1) {
        return true; //올바른 포맷 형식
    }
    return false;
}
function isValidEmail(input) {
    var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    return isValidFormat(input,format);
}

var processState = true;
function update() {
    if ( checkField(document.updatepeople.name, "이름")
        || checkField(document.updatepeople.email, "E-mail") ) {
        return;
    }

    if (!isValidEmail(document.updatepeople.email)) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02164") %><%--올바른 이메일 주소가 아닙니다--%>');
        return;
    }

    <%  if ( CommonUtil.isAdmin() ) {  %>
    if ( document.updatepeople.imageFile.value.length > 0 ) {
        if (!CheckImageFile()){
            document.updatepeople.imageFile.focus();
            return;
        }
    }
    //  if(checkField(document.updatepeople.sortNum, "소트넘버") ) {  return; }
    <%  } %>

    if( document.updatepeople.isChief.checked ) {
    <% if(PeopleHelper.manager.getChiefUser(data.department) != null) { %>
        <% if( !(CommonUtil.getOIDString(PeopleHelper.manager.getChiefUser(data.department)).equals(CommonUtil.getOIDString(data.people.getUser()))) ) { %>
            alert('<%=messageService.getString("e3ps.message.ket_message", "03181") %><%--해당 부서에 타인물이 부서장으로 지정되어 있습니다 확인하시기 바랍니다--%>');
            return;
        <% } %>
        //alert("해당 부서에 부서장이 이미 정의되어 있습니다. 확인하시기 바랍니다.");
        //return;
    <% } %>
        //alert("Checked");
    //} else {
        //alert("UnChecked");
    }

    if ( processState ) {
        disabledAllBtn();
        //var url = '/plm/jsp/common/processing.jsp?img=modify_process.gif';
        //openOtherName(url,"window","310","180","status=no,scrollbars=no,resizable=no");
        //document.updatepeople.target = "window";
        document.updatepeople.action = "peopleUpdate.jsp";
        document.updatepeople.method = "post";
        document.updatepeople.submit();
    }
}

function CheckImageFile(){
    /*
    var imgCapacity = document.all.tempImg.fileSize;
    if(imgCapacity < 0){
        alert("이미지파일은 가능하며 비정상적인 이미지는 올릴수 없습니다.");
        return false;
    }
    */
    var imgFileName = document.updatepeople.imageFile.value
    if ( imgFileName.indexOf(".PNG") != -1 ||
        imgFileName.indexOf(".JPG") != -1 ||
        imgFileName.indexOf(".GIF") != -1 ||
        imgFileName.indexOf(".png") != -1 ||
        imgFileName.indexOf(".gif") != -1 ||
        imgFileName.indexOf(".jpg") != -1 ){
        return true;
    } else {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02709") %><%--지원하지 않는 파일입니다--%>');
        return false;
    }
}

function checkImgFormat(imgPath){
    if ( imgPath.indexOf(".PNG") != -1 ||
        imgPath.indexOf(".JPG") != -1 ||
        imgPath.indexOf(".GIF") != -1 ||
        imgPath.indexOf(".png") != -1 ||
        imgPath.indexOf(".gif") != -1 ||
        imgPath.indexOf(".jpg") != -1 ){
    } else {
        if ( imgPath != "" ) alert('<%=messageService.getString("e3ps.message.ket_message", "02709") %><%--지원하지 않는 파일입니다--%>');
    }
}

function Imgview () {
  document.all.tempImg.src = "";
  document.all.tempImg.src = document.updatepeople.imageFile.value;
}
</script>

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>

<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
    margin-right:5px;

    overflow-x:hidden;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}
-->
</style>
</head>
<body >
<form name=updatepeople method=post enctype="MULTIPART/FORM-DATA">
<input type="hidden" name="oid" value="<%=oid%>">
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
<!-- title제목 시작 //-->
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01676") %><%--사용자 정보수정--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "02435") %><%--작업공간--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "01676") %><%--사용자 정보수정--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
                    <td align="right">
                        <b><span class="style1"> *</span><%=messageService.getString("e3ps.message.ket_message", "00026") %><%--{0}는 필수항목입니다--%></b>&nbsp;&nbsp;
                        <a href="#" onClick="javascript:update();">
                        <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;
                        <a href="#" onClick="javascript:history.back();">
                        <img src="/plm/portal/images/img_default/button/board_btn_back.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01309") %><%--뒤로--%>' width="62" height="20" border="0">
                        </a>
                  </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space5"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
          </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02074") %><%--아이디--%></td>
                    <td class="tdwhiteL"><%=data.id%></td>
                    <td width="200" rowspan="6" align="center" class="tdwhiteM" style='margin:2px;'><img src="<%=data.imgUrl%>" width="100" height="120"></td>
                </tr><tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%><span class="style1"> *</span></td>
                    <td class="tdwhiteL">
                        <input name="name" value="<%=data.name%>" class="txt_field" size="30"  style="width:90%"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL">E-mail<span class="style1"> *</span></td>
                    <td class="tdwhiteL">
                        <input name="email" value="<%=data.email%>" class="txt_field" size="60" style="width:90%"/>
                    </td>
                </tr>
                <%
                    String departmentStr = "";
                    if ( data.department != null ) departmentStr = data.departmentName;
                    else departmentStr = "지정안됨";
                %>
                <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%><span class="style1"> *</span><br></td>
                    <td class="tdwhiteL">&nbsp;<%=departmentStr%>
                    &nbsp;&nbsp;[<%=messageService.getString("e3ps.message.ket_message", "01562") %><%--부서장--%> :
                    <%
                    String isChief = "";
                    if(StringUtil.checkString(data.chief))
                        isChief = "checked";
                    %>
                    <input type="checkbox" name="isChief" <%=isChief%>> ]
                    </td>
                </tr>
                <%
                    String dutyStr = "";
                    if( data.duty == null || data.duty.trim().length() < 1 ) dutyStr = messageService.getString("e3ps.message.ket_message", "03357")/*지정안됨*/;
                    else dutyStr = data.duty;
                %>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%><span class="style1"> *</span></td>
                    <td class="tdwhiteL">&nbsp;<%= dutyStr %></td>
                </tr>
                <tr>
                    <td class="tdblueL">CcCode</td>
                    <td class="tdwhiteL">&nbsp;<%= StringUtil.checkNull(data.cccCode) %></td>
                </tr>
        </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "01912") %><%--세부정보--%></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
          </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03260") %><%--휴대전화--%></td>
                    <td class="tdwhiteL"><input name="cellTel" value="<%=data.cellTel%>" class="txt_field" size="60" engnum="engnum"/>
                      (<%=messageService.getString("e3ps.message.ket_message", "03358") %><%--'_'입력--%>)</td>
                </tr><tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01658") %><%--사내전화--%></td>
                    <td class="tdwhiteL"><input name="officeTel" value="<%=data.officeTel%>" class="txt_field" size="60" engnum="engnum"/>
                    (<%=messageService.getString("e3ps.message.ket_message", "03358") %><%--'_'입력--%>)</td>
                </tr>
                <!--
                <tr>
                    <td class="tdblueL">집 전회번호</td>
                  <td class="tdwhiteL"><input name="homeTel" value="<%=data.homeTel%>" class="txt_field" size="60" engnum="engnum"/>
                    ('_'입력)</td>
                </tr>
                <tr>
                    <td class="tdblueL">주&nbsp;&nbsp;&nbsp;&nbsp;소</td>
                    <td class="tdwhiteL"><input name="address" value="<%=data.address%>" class="txt_field" size="95" engnum="engnum"/></td>
                </tr>
                 -->
                 <input type='hidden' name='homeTel' value=''>
                 <input type='hidden' name='address' value=''>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03356") %><%--사진--%></td>
                    <td class="tdwhiteL"><input name="imageFile" type="file" class="txt_field" size="80"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
<!------------------------------ 본문 끝 //------------------------------>
        </table>
      </td>
    </tr>
</table>
<!-- 본문외관테두리 시작 //-->
</form>
</body>
</html>
