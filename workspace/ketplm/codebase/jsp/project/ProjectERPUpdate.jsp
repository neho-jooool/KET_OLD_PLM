<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.project.*,
                 e3ps.project.beans.*,
                 e3ps.common.code.GenNumberCode,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="arrayList" class="java.util.ArrayList" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<html>

<%
GenNumberCode genCode = new GenNumberCode();
String oid = request.getParameter("oid");

String old_valuePN = "";          //프로젝트 NO(OLD)
String old_valuePProduct = "";        //PRODUCT(OLD)
String old_valuePName = "";          //프로젝트 명(OLD)
String old_valuePCustomer = "";        //거래처(OLD)
String old_valuePDeliveredDate = "";    //출하일자(OLD)

String new_valuePN = "";          //프로젝트 NO(NEW)
String new_valuePProduct = "";        //PRODUCT(NEW)
String new_valuePName = "";          //프로젝트 명(NEW)
String new_valuePCustomer = "";        //거래처(NEW)
String new_valuePDeliveredDate = "";    //출하일자(NEW)

E3PSProject E3PSProject = null;
E3PSProjectData projectData = null;

if( StringUtil.checkString(oid) ) {
    E3PSProject = (E3PSProject)CommonUtil.getObject(oid);
    projectData = new E3PSProjectData(E3PSProject);
    old_valuePN = projectData.pjtNo;
    old_valuePProduct = projectData.pjtProduct;
    old_valuePName = projectData.pjtName;
    old_valuePCustomer = projectData.pjtCustomerLinkName;
    old_valuePDeliveredDate = DateUtil.getDateString(projectData.pjtDeliveredDate,"D");
}

Calendar tempCal = Calendar.getInstance();
for(int i = 0; i < arrayList.size(); i++) {
    Object[] obj = (Object[])arrayList.get(i);
    new_valuePN = String.valueOf(obj[0]);
    new_valuePName = String.valueOf(obj[1]);
    String tempDeliveredDate = String.valueOf(obj[2]);
    new_valuePProduct = String.valueOf(obj[3]);
    new_valuePCustomer = String.valueOf(obj[4]);
    if(StringUtil.checkString(tempDeliveredDate)) {
        new_valuePDeliveredDate = tempDeliveredDate.substring(0, 4) + "/" + tempDeliveredDate.substring(4, 6) + "/" + tempDeliveredDate.substring(6, 8);
    }
}
%>

<head>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "03049") %><%--프로젝트 ERP정보 수정--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language="JavaScript">
function update(){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03335") %><%--정말 수정하시겠습니까?--%>")) {
        return;
    } else {
        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
        document.forms[0].method = "post";
        document.forms[0].command.value = "update";
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

function closeForm() {
    self.close();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<form>

<!-- Hidden Values -->
<input type="hidden" name="command">
<input type="hidden" name="mode" value="erpUpdate">
<input type="hidden" name="oid" value="<%=StringUtil.checkNull(oid)%>">

<input type="hidden" name="projectProduct" value="<%=StringUtil.checkNull(new_valuePProduct)%>">
<input type="hidden" name="projectName" value="<%=StringUtil.checkNull(new_valuePName)%>">
<input type="hidden" name="projectCustomer" value="<%=StringUtil.checkNull(new_valuePCustomer)%>">
<input type="hidden" name="projectDeliveredDate" value="<%=StringUtil.checkNull(new_valuePDeliveredDate)%>">
<!-- //Hidden Vlaues -->
<!-- title제목 시작 //-->
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td width="400" valign="top" background="/plm/portal/images/img_drawing/drawtitle_bg01.gif">
            <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="40">&nbsp;</td>
                    <td>
                     <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="250" height="26" align="middle">
                        <param name="allowScriptAccess" value="sameDomain" />
                        <param name="movie" value="/plm/portal/flash/sub_title.swf?title=MIS정보 수정"/>
                        <param name="quality" value="high" />
                        <param name="wmode" value="transparent" />
                        <param name="bgcolor" value="#ffffff" />
                        <embed src="/plm/portal/flash/sub_title.swf?title=MIS정보 수정" quality="high" wmode="transparent" bgcolor="#ffffff" width="250" height="26" name="sub_title" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
                      </object>
                    </td>
                </tr>
          </table>
        </td>
        <td background="/plm/portal/images/img_drawing/drawtitle_bg02.gif">
            <!-- 현재위치 시작 //-->
            <table  class="tab_w03" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="position">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "00326") %><%--MIS정보 수정--%> </td>
              </tr>
            </table>
            <!-- 현재위치 끝 //-->
        </td>
    </tr>
</table>
<!-- title제목 끝 //-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding-left:12">
<!-------------------------------------- 상단버튼 시작 //-------------------------------------->
            <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" onClick="Javascript:update()"></td>
                                <td class=fixRight></td>
                                <td>&nbsp;</td>
                                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="Javascript:closeForm()"></td>
                                <td class=fixRight></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
<!-------------------------------------- 상단버튼 끝 //-------------------------------------->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <!------------------------------ 본문 시작 //------------------------------>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false">
                <jsp:param name="tb_class" value="tab_w01"/>
                <jsp:param name="td_class" value="tab_btm2"/>
            </jsp:include>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false">
                <jsp:param name="tb_class" value="tab_w01"/>
                <jsp:param name="td_class" value="tab_btm1"/>
            </jsp:include>
            <table class="tab_w01" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
            <COL width="15%"><COL width="35%"><COL width="15%"><COL width="35%">
            <!-- ERP Interface 항목 들 -->
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=StringUtil.checkNull(new_valuePN)%>
                </td>
                <td class="tdblueL">PRODUCT</td>
                <td class="tdwhiteL0">
                    &nbsp;<%=StringUtil.checkNull(new_valuePProduct)%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00696") %><%--거래처--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=StringUtil.checkNull(new_valuePCustomer)%>
                </td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02884") %><%--출하일자--%></td>
                <td class="tdwhiteL0">
                    &nbsp;<%=StringUtil.checkNull(new_valuePDeliveredDate)%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                <td class="tdwhiteL0" colspan=3>
                    &nbsp;<%=StringUtil.checkNull(new_valuePName)%>
                </td>
            </tr>
            <!-- //ERP Interface 항목 들 -->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
<!------------------------------ 본문 끝 //------------------------------>
        </td>
    </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</table>
</form>
</body>
</html>
