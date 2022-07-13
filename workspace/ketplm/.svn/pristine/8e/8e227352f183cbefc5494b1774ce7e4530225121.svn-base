<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.List,
                java.util.Map,
                java.util.HashMap"%>

<%@page import="e3ps.common.util.PropertiesUtil,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.KETStringUtil"%>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;

    if( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End

    String initType = request.getParameter("initType");

    ArrayList pjtStateCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("pjtStateList")), "," );
    ArrayList taskCompleteCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("taskCompleteList")), "," );

    Map<String, Object> parameter = new HashMap<String, Object>();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MYTASKPJTSTATE");
    List<Map<String, Object>> pjtStateNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MYTASKCOMPLETE");
    List<Map<String, Object>> taskCompleteNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    if( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("searchYN") == null ) {
        pjtStateCondList.add(0, "PROGRESS");
        taskCompleteCondList.add(0, "0");
    }
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/script.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/viewObject.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>

<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}

img {
    vertical-align: middle;border: 0px;
}

input {
    vertical-align:middle;line-height:22px;
}
</style>

<script type="text/javascript">

//   function viewPeople(oid) {
//     var url = "/plm/e3pscore/org/selectPeopleView.jsp?viewtype=open&oid="+oid;
//     openSameName(url,"560","400","status=no,scrollbars=no,resizable=no");
//   }
    function viewTask(oid){
        var url = "/plm/jsp/project/TaskView.jsp?oid="+oid;
        openOtherName(url,"","830","600","status=1,scrollbars=yes,resizable=1");
    }

    function viewProject(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
    }

    function perPage(v) {
        document.myTask.perPage.value = v;

        search();
    }

    function search() {
        document.myTask.pjtStateList.value = $("#searchPjtState").val();
        document.myTask.taskCompleteList.value = $("#searchState").val();
        document.myTask.action = "/plm/servlet/e3ps/ProjectServlet?command=searchMyTask";
        document.myTask.submit();
    }

    function deleteValue(str) {
        document.getElementById(str).value = "";
    }

    function clearAll(){
        $("#searchPjtNo").val("");
        $("#searchTaskName").val("");
        $("#planStartStartDate").val("");
        $("#planStartEndDate").val("");
        $("#planEndStartDate").val("");
        $("#planEndEndDate").val("");
        $("#searchPjtState").multiselect("uncheckAll");
        $("#searchPjtState").val("PROGRESS").prop('selected','selected');
        $("#searchPjtState").multiselect("refresh");
        $("#searchState").multiselect("uncheckAll");
        $("#searchState").val("0").prop('selected','selected');
        $("#searchState").multiselect("refresh");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }


    //Jquery
    $(document).ready(function(){
        // Enter 검색
        $("form[name=myTask]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#searchPjtState").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#searchState").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

    });
</script>
</head>

<form name="myTask" method="POST">

<!-- hidden begin -->
<input type="hidden" name="perPage" value="<%=pagingSize %>">
<input type="hidden" name="pjtStateList" />
<input type="hidden" name="taskCompleteList" />
<input type="hidden" name="searchYN" value="Y" />
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
                    <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01">My Task</td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>
                        <img src="/plm/portal/images/icon_navi.gif">My Task
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
                                <a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
                                <a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
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
            <td class="tdblueL"  style="width: 120px;">Project No</td>
            <td class="tdwhiteL" style="width: 270px;">
                <input type="text" id="searchPjtNo" name="searchPjtNo" class="txt_field"  style="width:270px;" value="<%=searchCondition.getString("searchPjtNo") %>">
            </td>
            <td class="tdblueL"   style="width: 120px;">Task Name</td>
            <td class="tdwhiteL0" style="width: 270px;">
                <input type="text" id="searchTaskName" name="searchTaskName" class="txt_field"  style="width:270px;" value="<%=searchCondition.getString("searchTaskName") %>">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
            <td class="tdwhiteL">
                <input type="text" readonly id="planStartStartDate" name="planStartStartDate" class="txt_fieldRO" style="width:70px;" value="<%=searchCondition.getString("planStartStartDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(planStartStartDate)" style="cursor:hand;">
                <a href="javascript:deleteValue('planStartStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
                &nbsp;~&nbsp;
                <input type="text" readonly id="planStartEndDate" name="planStartEndDate" class="txt_fieldRO"  style="width:70px;" value="<%=searchCondition.getString("planStartEndDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(planStartEndDate)" style="cursor:hand;">
                <a href="javascript:deleteValue('planStartEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
            <td class="tdwhiteL0">
                <input type="text" readonly id="planEndStartDate" name="planEndStartDate" class="txt_fieldRO" style="width:70px;" value="<%=searchCondition.getString("planEndStartDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(planEndStartDate)" style="cursor:hand;">
                <a href="javascript:deleteValue('planEndStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
                &nbsp;~&nbsp;
                <input type="text" readonly id="planEndEndDate" name="planEndEndDate" class="txt_fieldRO"  style="width:70px;" value="<%=searchCondition.getString("planEndEndDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(planEndEndDate)" style="cursor:hand;">
                <a href="javascript:deleteValue('planEndEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03076") %><%--프로젝트 상태--%></td>
            <td class="tdwhiteL">
                <select id="searchPjtState" name="searchPjtState" style="width:270px;" multiple="multiple">
                <%
                for ( int i=0; i<pjtStateNumCode.size(); i++ ) {
                %>
                <option value="<%=pjtStateNumCode.get(i).get("code") %>" <%=pjtStateCondList.contains(pjtStateNumCode.get(i).get("code")) ? " selected" : "" %>><%=pjtStateNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
            <td class="tdwhiteL0">
                <select id="searchState" name="searchState" style="width:270px;" multiple="multiple">
                <%
                for ( int i=0; i<taskCompleteNumCode.size(); i++ ) {
                %>
                <option value="<%=taskCompleteNumCode.get(i).get("code") %>" <%=taskCompleteCondList.contains(taskCompleteNumCode.get(i).get("code")) ? " selected" : "" %>><%=taskCompleteNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
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
                            Layout_Url="/plm/jsp/project/ListMyTaskGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_StandardDoc_List"
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
