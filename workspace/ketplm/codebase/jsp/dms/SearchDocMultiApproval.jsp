<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Vector"%>

<%@page import="wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.State,
                wt.org.WTUser"%>

<%@page import="e3ps.common.util.WCUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.wfm.util.WFMProperties"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");

  String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
  String pagingSize = null;

  if( searchCondition != null && !searchCondition.isEmpty() ) {
      pagingSize = searchCondition.get("perPage").toString();
  }

  if ( pagingSize == null || pagingSize.trim().length() == 0 ) {
      pagingSize = pagingSizeDefault;
  }
  // EJS TreeGrid End

  ArrayList stateCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("lcState")), "," );
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><%=messageService.getString("e3ps.message.ket_message", "02406") %><%--자동차일정 검색--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script type="text/javascript">
//<![CDATA[
    // 상세조회
    function gotoView(oid){
        document.location.href="/plm/jsp/dms/ViewDocMultiApproval.jsp?oid="+oid;
    }

    function clearAll(){
        $("#reqName").val("");
        $("#authorStatus").multiselect("uncheckAll");
        $("#predate").val("");
        $("#postdate").val("");
        $("#creator").val("");
        $("#creatorName").val("");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    // ==  [Start] 사람 검색  == //
    function clearUser() {
        $("#creator").val("");
        $("#creatorName").val("");
    }

    function selectUser() {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if ( typeof attacheMembers == "undefined" || attacheMembers == null ) {
            return;
        }
        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "Y";
        acceptMember(attacheMembers, isAppend);
    }

    function acceptMember(arrObj, isAppend) {
        var userId = new Array();     // Id
        var userName = new Array();   // Nmae
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - wtuser oid // [1] - people oid // [2] - dept oid
            // [3] - user id    // [4] - name       // [5] - dept name
            // [6] - duty       // [7] - duty code  // [8] - email

            var infoArr = arrObj[i];
            userId[i] = infoArr[0];
            userName[i] = infoArr[4];
        }

        var tmpId = new Array();
        var tmpName = new Array();
        if ( $("#creator").val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#creator").val().split(", "), userId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#creatorName").val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#creator").val(tmpId.join(", "));
        $("#creatorName").val(tmpName.join(", "));
    }
    // ==  [End] 사람 검색  == //


    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    // 검색
    function search() {
        if( $("#predate").val() != "" && ( $("#postdate").val() != "" ) ) {
            if( $("#predate").val() > $("#postdate").val() ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02430") %><%--작성일자의 범위가 잘못 지정되어 있습니다--%>');
                $("#predate").val("");
                $("#postdate").val("");
                return;
            }
        }

        showProcessing();     // 진행중 Bar 표시
        disabledAllBtn();     // 버튼 비활성화

        document.frm.lcState.value = $("#authorStatus").val();
        document.frm.action = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=searchMultiApp"
        document.frm.submit();
    }

    function excelDown(){
        document.frm.action = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=excelDownMultiApp";
        document.frm.submit();
    }

    // Jquery
    $(document).ready(function(){
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            //Enter key
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        // Multi Combo 생성
        $("#authorStatus").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    });
    //]]>
</script>
</head>

<body>
<form id="frm" name="frm" method="POST">

<!-- hidden begin -->
<input type="hidden" name="lcState" value="">
<input type="hidden" name="perPage" value="<%=pagingSize %>">
<!-- hidden end -->

<table style="width: 780px; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 780px;">
        <tr>
            <td>
                <table style="width: 780px; height: 28px;">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02339") %><%--일괄결재요청검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00638") %><%--개발산출문서--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02339") %><%--일괄결재요청검색--%>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="head_line"></td>
        </tr>
        <tr>
            <td class="space10"></td>
        </tr>
        </table>
        <!-- [END] title & position -->
        <!-- [START] button -->
        <table style="width: 780px;">
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <table>
                <tr>
                    <td>
                        <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                    </td>
                    <td style="width: 15px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:clearAll()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:search()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <table style="width: 780px;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] button -->

        <!-- [START] search condition -->
        <table style="width: 780px;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 780px;">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02197") %><%--요청제목--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <input type="text" id="reqName" name="reqName" class="txt_field"  style="width: 270px;" id="reqName" value="<%=StringUtil.checkNull( (String)searchCondition.get("reqName") ) %>">
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <select id="authorStatus" name="authorStatus" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                String templateName = WFMProperties.getInstance().getString("wfm.template.common");
                LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate(templateName, WCUtil.getPDMLinkProduct().getContainerReference());
                Vector lcStates = LifeCycleHelper.service.findStates(LCtemplate);
                State lstate = null;

                for ( int i=0; i<lcStates.size(); i++ ) {
                    lstate = (State)lcStates.elementAt(i);
                %>
                    <option value="<%=lstate %>" <%=stateCondList.contains(lstate.toString()) ? " selected" : "" %>><%=lstate.getDisplay(messageService.getLocale()) %></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02195") %><%--요청일자--%></td><!--  -->
            <td class="tdwhiteL">
                <input type="text" readonly id="predate" name="predate" class="txt_fieldRO" style="width:70px;" id="textfield9" value="<%=StringUtil.checkNull( (String)searchCondition.get("predate") ) %>" onChange="javascript:changeDate1()">
            &nbsp;<img src="/plm/portal/images/icon_6.png"border="0" onClick="javascript:showCal(predate)" style="cursor:hand;">
                <a href="javascript:clearDate('predate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            &nbsp;~&nbsp;
                <input type="text" readonly id="postdate" name="postdate" class="txt_fieldRO"  style="width:70px;" id="textfield10" value="<%=StringUtil.checkNull( (String)searchCondition.get("postdate") ) %>" onChange="javascript:changeDate2()">
            &nbsp;<img src="/plm/portal/images/icon_6.png"border="0" onClick="javascript:showCal(postdate)" style="cursor:hand;">
                <a href="javascript:clearDate('postdate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02196") %><%--요청자--%></td>
            <td class="tdwhiteL0">
                <input type="hidden" id="creator" name="creator" id="creator" value="<%=StringUtil.checkNull( (String)searchCondition.get("creator") ) %>">
                <input type="text" id="creatorName" name="creatorName" class="txt_fieldRO"  style="width:210px;" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorName") ) %>" readOnly>
                &nbsp;<a href="javascript:selectUser()"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                &nbsp;<a href="javascript:clearUser()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        </table>
        <table style="width: 780px;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] search condition -->

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/dms/SearchDocMultiApprovalGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_DocMultiApp_List"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid Start -->
    </td>
</tr>

<!-- [START] copyright -->
<tr>
    <td style="height: 30px" valign="bottom">
        <table style="width: 780px;">
        <tr>
            <td style="width: 10px">&nbsp;</td>
            <td style="height: 30px">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 780px; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr>
<!-- [END] copyright -->

</table>
</form>
</body>
</html>
