<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.web.ParamUtil" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00522") %><%--TODO 검색--%></title>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>

<script language=JavaScript src="/plm/jsp/ecm/SearchEca.js"></script>
<script language="javascript">
function init() {
    if("<%=ParamUtil.getParameter(request, "autoSearch")%>" == "Y") {
        var form = document.forms[0];
        form.page.value = "<%=ParamUtil.getParameter(request, "page")%>";
        form.totalCount.value = "<%=ParamUtil.getParameter(request, "totalCount")%>";
        form.sortColumn.value = "<%=ParamUtil.getParameter(request, "sortColumn")%>";
        form.param.value = "<%=ParamUtil.getParameter(request, "param")%>";
        form.perPage.value = "<%=ParamUtil.getParameter(request, "perPage")%>";

        form.partOid.value = "<%=ParamUtil.getParameter(request, "srchpartOid")%>";
        form.partNo.value = "<%=ParamUtil.getParameter(request, "srchpartNo")%>";
        form.partName.value = "<%=ParamUtil.getParameter(request, "srchpartName")%>";
        form.projectNo.value = "<%=ParamUtil.getParameter(request, "srchprojectNo")%>";
        form.projectName.value = "<%=ParamUtil.getParameter(request, "srchprojectName")%>";
        form.orgName.value = "<%=ParamUtil.getParameter(request, "srchorgName")%>";
        form.creatorOid.value = "<%=ParamUtil.getParameter(request, "srchcreatorOid")%>";
        form.creatorName.value = "<%=ParamUtil.getParameter(request, "srchcreatorName")%>";
        form.ecoId.value = "<%=ParamUtil.getParameter(request, "srchecorId")%>";
        form.divisionFlag.value = "<%=ParamUtil.getParameter(request, "srchdivisionFlag")%>";
        form.devYn.value = "<%=ParamUtil.getParameter(request, "srchdevYn")%>";
        form.sancStateFlag.value = "<%=ParamUtil.getParameter(request, "srchsancStateFlag")%>";
        form.ecoName.value = "<%=ParamUtil.getParameter(request, "srchecoName")%>";
        form.prodMoldCls.value = "<%=ParamUtil.getParameter(request, "srchprodMoldCls")%>";
        form.createStartDate.value = "<%=ParamUtil.getParameter(request, "srchcreateStartDate")%>";
        form.createEndDate.value = "<%=ParamUtil.getParameter(request, "srchcreateEndDate")%>";
        callSearch();
    }
}
</script>

</head>
<body onload="javascript:init();">
<form method="post" action="">
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="fromPage" value="SearchEcoForm">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sortColumn" value="1 DESC">
<input type="hidden" name="param" value="parent.">
<input type="hidden" name="autoSearch" value="Y">
<input type="hidden" name="oid" value="">
<input type="hidden" name="ecaType" value="1">

<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00522") %><%--TODO 검색--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00184") %><%--ECO 검색--%></td>
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
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doCancel();"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doSearch();"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01570") %><%--부품번호/명--%></td>
      <td class="tdwhiteL0" colspan="3"><input type="text" name="partNo" class="txt_field" style="width:215" readonly
      >&nbsp;&nbsp;<input type="text" name="partName" readOnly class="txt_field" style="width:390"
      ><input type="hidden" name="partOid"
      >&nbsp;&nbsp;<a href="javascript:popupPart();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(1);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
    </tr>
    <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03115") %><%--프로젝트번호/명--%></td>
        <td class="tdwhiteL0" colspan="3"><input type="text" name="projectNo" class="txt_field" style="width:215" readonly
        >&nbsp;&nbsp;<input type="text" name="projectName" readOnly class="txt_field" style="width:390"
        >&nbsp;&nbsp;<a href="javascript:popupProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(2);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
    </tr>
    <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
    <td class="tdwhiteL"><input type="text" name="orgName" class="txt_field" style="width:215"
    ><input type="hidden" name="orgOid"
    >&nbsp;&nbsp;<a href="javascript:selectDepartment();"><img src="/plm/portal/images/icon_5.png" border="0"
    ></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(3);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
    <td class="tdwhiteL0"><input type="text" name="creatorName" class="txt_field" style="width:200" readonly
    ><input type="hidden" name="creatorOid"
    >&nbsp;&nbsp;<a href="javascript:popupUser();" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif" border="0"
    ></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(4);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;&nbsp;</td>
  </tr>
  <tr>
  <td class="tdblueL" width="250"><%=messageService.getString("e3ps.message.ket_message", "00199") %><%--ECO번호--%></td>
  <td class="tdwhiteL"><input type="text" name="ecoId" class="txt_field" style="width:215" readonly
  ><input type="hidden" name="ecoOid"
  >&nbsp;&nbsp;<a href="javascript:popupRelEco();"><img src="/plm/portal/images/icon_5.png" border="0"
  ></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(5);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
  <td class="tdblueL" width="135"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
  <td class="tdwhiteL0"><select name="divisionFlag" class="fm_jmp" style="width:150" style="text-align:center;">
      <option value="" selected><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
      <option value="C"><%=messageService.getString("e3ps.message.ket_message", "02401") %><%--자동차사업부--%></option>
      <option value="E"><%=messageService.getString("e3ps.message.ket_message", "02483") %><%--전자사업부--%></option>
      <option value="K"><%=messageService.getString("e3ps.message.ket_message", "04142") %><%--KETS--%></option>
      
    </select></td>
    </tr>
    <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
    <td class="tdwhiteL"><input type="text" name="createStartDate" class="txt_field"  style="width:71" readonly>
    &nbsp;<a href="#" onclick="javascript:showCal('createStartDate');"><img src="/plm/portal/images/icon_6.png"border="0"
    ></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(6);"
    ><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;~&nbsp;
    <input type="text" name="createEndDate" class="txt_field"  style="width:71" readonly>
    &nbsp;<a href="#" onclick="javascript:showCal('createEndDate');"><img src="/plm/portal/images/icon_6.png"border="0"
    ></a>&nbsp;&nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(7);"
    ><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02555") %><%--제품/금형--%></td>
    <td class="tdwhiteL0"><select name="prodMoldCls" class="fm_jmp" style="width:150" style="text-align:center;">
      <option value="1"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></option>
      <option value="2" selected><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></option>
    </select></td>
    </tr>
        <tr>
            <td class="tdblueL" width="250"><%=messageService.getString("e3ps.message.ket_message", "00606") %><%--개발/양산--%></td>
            <td class="tdwhiteL"><select name="devYn" class="fm_jmp" style="width:150" style="text-align:center;">
              <option value="" selected><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
            <option value="D"><%=messageService.getString("e3ps.message.ket_message", "00582") %><%--개발--%></option>
            <option value="P"><%=messageService.getString("e3ps.message.ket_message", "02078") %><%--양산--%></option>
          </select></td>
          <td class="tdblueL" width="135"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td class="tdwhiteL0"><select name="sancStateFlag" class="fm_jmp" style="width:150" style="text-align:center;">
              <option value="" selected><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
                <option value="PLANNING"><%=messageService.getString("e3ps.message.ket_message", "00815") %><%--계획수립--%></option>
                <option value="EXCUTEACTIVITY"><%=messageService.getString("e3ps.message.ket_message", "03243") %><%--활동수행--%></option>
                <option value="ACTIVITYCOMPLETED"><%=messageService.getString("e3ps.message.ket_message", "03244") %><%--활동완료--%></option>
                <option value="UNDERREVIEW"><%=messageService.getString("e3ps.message.ket_message", "00732") %><%--검토중--%></option>
                <option value="APPROVED"><%=messageService.getString("e3ps.message.ket_message", "01996") %><%--승인완료--%></option>
                <option value="REJECTED"><%=messageService.getString("e3ps.message.ket_message", "01468") %><%--반려됨--%></option>
              </select></td>
        </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
            <td class="tdwhiteL0" colspan="3"><input type="text" name="ecoName" class="txt_field" style="width:100%" id="textfield4"></td>
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
                  <td><a href="javascript:doExcel();"><img src="/plm/portal/images/iocn_excel.png" border="0" onfocus="this.blur();"></a></td>
                </tr>
              </table></td>
              <td width="10"></td>
              <td align="right"><select name="perPage" class="fm_jmp" style="width:50" style="text-align:center;">
              <option value="10" selected>10</option>
              <option value="30">30</option>
              <option value="50">50</option>
              <option value="100">100</option>
              </select></td>
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
      <iframe name="frmList" width="781" height="170" src="" border="0" cellspacing="0" cellpadding="0" frameborder="0" marginwidth="0" marginheight="0" scrolling="auto"></iframe>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
        <table border="0" cellspacing="0" cellpadding="0" width="780" id="pageControlTable">
        </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</form>
</body>
</html>
