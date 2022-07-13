<%@page import="e3ps.project.beans.ProjectScheduleHistoryHelper"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.KETParamMapUtil"%>
<jsp:useBean id="screenWidth" class="java.lang.String" scope="request" />
<jsp:useBean id="screenHeight" class="java.lang.String" scope="request" />
<jsp:useBean id="tgDataHead" class="java.lang.String" scope="request" />
<jsp:useBean id="tgDataBody" class="java.lang.String" scope="request" />
<jsp:useBean id="personRoleEnumKey" class="java.lang.String" scope="request" />
<jsp:useBean id="personRoleEnum" class="java.lang.String" scope="request" />
<jsp:useBean id="milestoneTypeEnumKey" class="java.lang.String" scope="request" />
<jsp:useBean id="milestoneTypeEnum" class="java.lang.String" scope="request" />
<jsp:useBean id="optionTypeEnumKey" class="java.lang.String" scope="request" />
<jsp:useBean id="optionTypeEnum" class="java.lang.String" scope="request" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />
<jsp:useBean id="versions" class="java.lang.String" scope="request" />
<jsp:useBean id="allPjtHistory" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");
  // EJS TreeGrid End

  String oid = searchCondition.getString("oid"); // E3PSProjectMaster oid

  String type = searchCondition.getString("type");
  String detail = searchCondition.getString("detail");
  String prehist = searchCondition.getString("prehist");
  String posthist = searchCondition.getString("posthist");
  String[] historyAry = searchCondition.getStringArray("history");
  String[] versionAry = searchCondition.getStringArray("version");
  String iteration = searchCondition.getString("iteration");
  String predate = searchCondition.getString("predate");
  String postdate = searchCondition.getString("postdate");
  String ptype = searchCondition.getString("ptype");
  // 결재대상 화면 여부
  boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

%>
<%
  String title = "";
  String pjtNo = "";
  String pjtName = "";
  int pjtVersion = 0;
  Object obj = CommonUtil.getObject(oid);
  E3PSProject project = null;

  if ( obj instanceof E3PSProject ) {

      project = (E3PSProject)obj;

      if ( project != null ) {

          // Project 일정 조회 화면 Title - Project : PJT_NO (PJT_NAME)
          pjtNo = StringUtil.checkNull(project.getPjtNo());
          pjtName = StringUtil.checkNull(project.getPjtName());
          title = "Project Name : " + pjtNo + " (" + pjtName + ")";

          // Project Version
          pjtVersion = project.getPjtHistory();
      }
  }

%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02366") %><%--일정변경이력조회--%></title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp"%>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/jsp/groupware/js/board.js"></script>
<style type="text/css">
body {
	margin-left: <%=isIframe?"0px":"10px"%>;
	margin-top: 0px;
	margin-right: <%=isIframe?"0px":"10px"%>;
	margin-bottom: 5px;
}

table {
	border-spacing: 0;
	border: 0px;
}

table th,table td {
	padding: 0
}

img {
	vertical-align: middle;
}

input {
	vertical-align: middle;
	line-height: 22px;
}
</style>
<script type="text/javascript">
//<![CDATA[

  function search() {
    var form01 = document.forms[0];
    form01.action = "/plm/servlet/e3ps/ProjectScheduleHistoryServlet?cmd=search";
    form01.submit();
  }

  //Jquery
  $(document).ready(function(){
/*
      // Enter 검색
      $("form[name=form01]").keypress(function(e) {
          if ( e.which == 13 ) {
              search();
              return false;
          }
      });
*/
      $("#history").multiselect({
          minWidth: 200,
          noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
          checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
          uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
      });
      $("#version").multiselect({
          minWidth: 200,
          noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
          checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
          uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
      });
  });

  function clickLabel(inputId) {
    var inputObj = document.getElementById(inputId);
    if (inputObj.type == "radio") {
      inputObj.checked = true; // not toggle
    }
    else if (inputObj.type == "checkbox") {
      inputObj.checked = ! inputObj.checked; // toggle
    }

    checkChoice(inputId);
  }

  function checkChoice(inputId) {
    if (inputId == "typeL" || inputId == "typeW" || inputId == "typeD") {
      if (document.getElementById(inputId).checked == true) {
        document.getElementById("detail").checked = false; // '버전' 외 선택 시 '상세' 선택 해제
        onClickDetail('detail');
      }
    }
  }

  function onClickDetail(detailId) {
    var detailObj = document.getElementById(detailId)
    if (detailObj.checked == true) {
      document.getElementById("typeV").checked = true; // '상세' 선택 시 '버전' 선택
    }
    setDetailUi(detailObj.checked)
  }
  function setDetailUi(isDetail) {
    var select_normal = document.getElementById("select_normal");
     var select_detail = document.getElementById("select_detail");
    if (isDetail == true) {
      select_normal.style.display = "none";
      select_detail.style.display = "inline";
    }
    else {
      select_normal.style.display = "inline";
      select_detail.style.display = "none";
    }
  }

  function onChangeHistory() {
    var selectedAry = $("#history").val();
    $("#version").empty();
    if (selectedAry != null && selectedAry != undefined && selectedAry.length > 0) {
      versionAjax("<%=oid %>", "<%=ptype %>
    ", selectedAry.join(","), "version");
        }
        $("#version").multiselect("refresh");
    }

    function versionAjax(pjtMstOid, ptype, history, targetId) {
        $.ajax({
            url : "/plm/servlet/e3ps/ProjectScheduleHistoryServlet?cmd=versionAjax",
            type : "POST",
            data : {
                oid : pjtMstOid,
                ptype : ptype,
                history : history
            },
            dataType : 'json',
            async : false,
            success : function(data) {
                $.each(data.versionList, function() {
                    $("#" + targetId).append("<option value='"+this+"'>" + this + "</option>");
                });
            }
        });
    }

    //]]>
</script>
</head>
<body scroll="auto" style="overflow-x: hidden">
  <form name="form01" method="post">
    <input type="hidden" name="oid" value="<%=oid%>">
    <input type="hidden" name="ptype" value="<%=ptype%>">
    <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td style="height: 50px; <%=(isIframe) ? "display:none;" : ""%>" valign="top">
          <table style="width: 100%; " border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td style="width: 7px;"></td>
                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02366")%><%--일정변경이력조회--%></td>
                  </tr>
                </table>
              </td>
              <td style="width: 136px;" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td valign="top">
          <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top">
                <table style="width: 100%; <%=(isIframe) ? "display:none;" : ""%>" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                          <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                          <td>&nbsp;</td>
                          <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                          <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
                <table style="width: 100%; <%=(isIframe) ? "display:none;" : ""%>" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <table style="width: 100%; <%=(isIframe) ? "display:none;" : ""%>" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="tab_btm2"></td>
                  </tr>
                </table>
                <table style="width: 100%;<%=(isIframe) ? "display:none;" : ""%>" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="tdwhiteL0" colspan="2"><input type="radio" id="typeL" name="type" value="L" <%=(StringUtil.isEmpty(type) || "L".equals(type)) ? "checked" : ""%>
                        onclick="javascript:checkChoice('typeL');"
                      > <span style="font-size: 9pt; cursor: default;" onclick="javascript:clickLabel('typeL');"><%=messageService.getString("e3ps.message.ket_message", "02839")%><%--최신--%></span></td>
                  </tr>
                  <tr>
                    <td class="tdwhiteL0" colspan="2"><input type="radio" id="typeW" name="type" value="W" <%=("W".equals(type)) ? "checked" : ""%> onclick="javascript:checkChoice('typeW');">
                      <span style="font-size: 9pt; cursor: default;" onclick="javascript:clickLabel('typeW');"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%></span></td>
                  </tr>
                  <tr>
                    <td class="tdwhiteL0" style="width: 120px; height: 35px;"><input type="radio" id="typeV" name="type" value="V" <%=("V".equals(type)) ? "checked" : ""%>
                        onclick="javascript:checkChoice('typeV');"
                      > <span style="font-size: 9pt; cursor: default;" onclick="javascript:clickLabel('typeV');"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></span>
                      &nbsp;&nbsp;&nbsp; <input type="checkbox" id="detail" name="detail" value="Y" <%=("V".equals(type) && "Y".equals(detail)) ? "checked" : ""%>
                        onclick="javascript:onClickDetail('detail')"
                      > <span style="font-size: 9pt; cursor: default;" onclick="javascript:clickLabel('detail');javascript:onClickDetail('detail')"><%=messageService.getString("e3ps.message.ket_message", "01743")%><%--상세--%></span>
                    </td>
                    <td class="tdwhiteL0"><span id="select_normal" <%=("V".equals(type) && "Y".equals(detail)) ? "style='display:none;'" : ""%>> <select id="prehist" name="prehist"
                        class="fm_jmp" style="width: 100px;"
                      >
                          <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                          <%
                              for (int i = 0; i < allPjtHistory.size(); ++i) {
                          		String pjtHistory = (String) allPjtHistory.get(i);
                          %>
                          <option value="<%=pjtHistory%>" <%=("V".equals(type) && pjtHistory.equals(prehist)) ? "selected" : ""%>>Ver
                            <%=pjtHistory%></option>
                          <%
                              }
                          %>
                      </select> &nbsp;~&nbsp; <select id="posthist" name="posthist" class="fm_jmp" style="width: 100px;">
                          <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                          <%
                              for (int i = 0; i < allPjtHistory.size(); ++i) {
                          		String pjtHistory = (String) allPjtHistory.get(i);
                          %>
                          <option value="<%=pjtHistory%>" <%=("V".equals(type) && pjtHistory.equals(posthist)) ? "selected" : ""%>>Ver
                            <%=pjtHistory%></option>
                          <%
                              }
                          %>
                      </select>
                    </span> <!-- [START] 버전 상세 MultiCombo --> <span id="select_detail" <%=("V".equals(type) == false || "Y".equals(detail) == false) ? "style='display:none;'" : ""%>> <select
                        id="history" name="history" class="fm_jmp" style="width: 200px;" multiple="multiple" onchange="javascript:onChangeHistory();"
                      >
                          <%
                              for (int i = 0; i < allPjtHistory.size(); ++i) {
                          		String pjtHistory = (String) allPjtHistory.get(i);
                          %>
                          <option value="<%=pjtHistory%>" <%=("V".equals(type) && "Y".equals(detail) && KETParamMapUtil.contains(historyAry, pjtHistory)) ? "selected" : ""%>>Ver
                            <%=pjtHistory%></option>
                          <%
                              }
                          %>
                      </select> <select id="version" name="version" class="fm_jmp" style="width: 200px;" multiple="multiple">
                          <%
                              if ("V".equals(type) && "Y".equals(detail)) {
                          		long pjtMstIda2a2 = CommonUtil.getOIDLongValue(oid);
                          		String pjtHistories = searchCondition.getString("history");
                          		List<String> versionList = ProjectScheduleHistoryHelper.getVersionOfProject(ptype, pjtMstIda2a2, pjtHistories);
                          		for (int i = 0; i < versionList.size(); ++i) {
                          		    String version = versionList.get(i);
                          %>
                          <option value="<%=version%>" <%=("V".equals(type) && "Y".equals(detail) && KETParamMapUtil.contains(versionAry, version)) ? "selected" : ""%>><%=version%></option>
                          <%
                              }
                              }
                          %>
                      </select>
                    </span> <!-- [END] 버전 상세 MultiCombo --></td>
                  </tr>
                  <tr>
                    <td class="tdwhiteL0" colspan="2"><input type="radio" id="typeD" name="type" value="D" <%=("D".equals(type)) ? "checked" : ""%> onclick="javascript:checkChoice('typeD');">
                      <span style="font-size: 9pt; cursor: default;" onclick="javascript:clickLabel('typeD');"><%=messageService.getString("e3ps.message.ket_message", "01520")%><%--변경일--%></span>
                      &nbsp;&nbsp; <input type="text" readonly id="predate" name="predate" class="txt_fieldRO" style="width: 65px;" value="<%=("D".equals(type)) ? predate : ""%>"
                        onChange="javascript:changeDate1()"
                      > <img src="/plm/portal/images/icon_6.png" border="0" onClick="javascript:showCal(document.getElementById('predate'))" style="cursor: hand;"> &nbsp;<a
                      href="javascript:clearDate('predate');"
                    ><img src="/plm/portal/images/icon_delete.gif" border="0"></a> &nbsp;~&nbsp; <input type="text" readonly id="postdate" name="postdate" class="txt_fieldRO" style="width: 65px;"
                        value="<%=("D".equals(type)) ? postdate : ""%>" onChange="javascript:changeDate2()"
                      > <img src="/plm/portal/images/icon_6.png" border="0" onClick="javascript:showCal(document.getElementById('postdate'))" style="cursor: hand;"> &nbsp;<a
                      href="javascript:clearDate('postdate');"
                    ><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                  </tr>
                </table>
                <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="space10"></td>
                  </tr>
                </table> <!-- [END] search condition --> <!-- EJS TreeGrid Start -->
                <div class="content-main">
                  <div class="container-fluid">
                    <div class="row-fluid">
                      <div style="WIDTH: 100%; HEIGHT: 100%">
                        <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/project/schedule/ViewProjectScheduleHistoryGridLayout.jsp"
                          Data_Url="/plm/jsp/project/schedule/ViewProjectScheduleHistoryGridLayout.jsp" Data_Method="POST" Data_Param_Result="<%=tgData%>" Data_Param_Versions="<%=versions%>"
                          Data_Param_Detail="<%=("V".equals(type) && "Y".equals(detail)) ? "Y" : "N"%>" Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData"
                          Export_Param_File="View_Project_Schedule_History"
                        ></bdo>
                      </div>
                    </div>
                  </div>
                </div> <!-- EJS TreeGrid End -->
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
</body>
</html>
