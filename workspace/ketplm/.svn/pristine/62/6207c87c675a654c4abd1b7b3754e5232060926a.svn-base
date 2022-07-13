<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.HashMap,
                java.util.List,
                java.util.Map"%>

<%@page import="e3ps.common.util.StringUtil,
                e3ps.common.util.DateUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.PropertiesUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<%
    //EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;

    if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 사업부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONFLAG");
    List<Map<String, Object>> divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 개발구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVYN");
    parameter.put("exclude",  "N");
    List<Map<String, Object>> devYnNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // ECO 구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "NEWPARTTYPE");
    List<Map<String, Object>> newPartTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 상태
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ECOREPORTSTATE");
    List<Map<String, Object>> ecoReportStateNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    ArrayList searchYearCondList  = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("searchYear")), ", " );
    ArrayList prodMoldClsCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("prodMoldCls")), ", " );
    ArrayList stateCondList       = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("sancStateFlag")), ", " );
    ArrayList devYnCondList       = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("devYn")), ", " );
    ArrayList divisionCondList    = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("divisionFlag")), ", " );

    if ( searchYearCondList.size() == 0 ) {
        searchYearCondList.add(0, DateUtil.getThisYear());
    }

    // Grid Header 연도
    String selectYear = messageService.getString("e3ps.message.ket_message", "00015", searchYearCondList.get(0).toString()); /*{0}년*/
Kogger.debug("====="+selectYear);
    if ( prodMoldClsCondList.size() == 0 ) {
        prodMoldClsCondList.add(0, "PT001");
    }

    String divisionCode = "";
    if ( CommonUtil.isMember("전자사업부") ) {
        divisionCode = "E";
    }
    else if ( CommonUtil.isMember("자동차사업부") ) {
        divisionCode = "C";
    }
    else if ( CommonUtil.isKETSUser() ) {
        divisionCode = "K";
    }

    if ( divisionCondList.size() == 0 ) {
        if ( CommonUtil.isMember("전자사업부") ) {
            divisionCondList.add(0, "E");
        }
        else if ( CommonUtil.isMember("자동차사업부") ) {
            divisionCondList.add(0, "C");
        }
        else if ( CommonUtil.isKETSUser() ) {
            divisionCondList.add(0, "K");
        }
    }
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><%=messageService.getString("e3ps.message.ket_message", "02226") %><%--월간 ECO 현황 조회--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
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

    //처리중 화면 전환
    function hideProcessing() {
        var div1 = document.getElementById('div1');
        var div2 = document.getElementById('div2');
        div1.style.display = "none";
        div2.style.display = "none";
    }

    function doCancel(){
        $("#divisionFlag").multiselect("uncheckAll");
        if ( "<%=divisionCode %>" != "" ) {
            $("#divisionFlag").val("<%=divisionCode %>").attr('selected','selected');
        }
        $("#divisionFlag").multiselect("refresh");
        $("#prodMoldCls").val("PT001").attr('selected','selected');
        $("#prodMoldCls").multiselect("refresh");
        $("#searchYear").val("<%=DateUtil.getThisYear() %>").attr('selected','selected');
        $("#searchYear").multiselect("refresh");
        $("#devYn").multiselect("uncheckAll");
        $("#sancStateFlag").multiselect("uncheckAll");

        delDepartment();
        clearUser();

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    //자료 검색
    function search(){
        document.frm.cmd.value = "searchMonthlyEcoReport";
        document.frm.target = "_self";
        document.frm.action = "/plm/servlet/e3ps/SearchEcoReportServlet";
        disabledAllBtn();
        showProcessing();
        document.frm.submit();
    }

    //엑셀저장
    function excelDown() {
        document.frm.cmd.value = "excelDownMonthlyEcoReport";
        document.frm.action = "/plm/servlet/e3ps/SearchEcoReportServlet";
        document.frm.submit();
    }

    // ==  [Start] 부서 검색  == //
    function delDepartment() {
        $("#orgOid").val("");
        $("#orgName").val("");
    }

    function addDepartment() {
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
        attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
        if(typeof attacheDept == "undefined" || attacheDept == null) {
            return;
        }

        if ( typeof(attacheDept) != "object" ) {
            var deptSplit = attacheDept.split(",");
            for ( var i=0; i<deptSplit.length-1; i++ ) {
                var param = "command=deptInfo&deptOid=" + deptSplit[i];
                var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
                callServer(url, acceptDept);
            }
        }
        else {
            var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
            callServer(url, acceptDept);
        }
    }

    function acceptDept(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg != 'true') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
            return;
        }

        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");
        var l_name = showElements[0].getElementsByTagName("l_name");
        var l_code = showElements[0].getElementsByTagName("l_code");
        var l_chiefOid = showElements[0].getElementsByTagName("l_chiefOid");
        var l_chiefName = showElements[0].getElementsByTagName("l_chiefName");

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text));
    }

    function deptMerge(deptOid, deptName) {
        if ( document.frm.orgOid.value == "" ) {
            document.frm.orgOid.value = deptOid;
            document.frm.orgName.value = deptName;
        }
        else {
            var deptOidArr = document.frm.orgOid.value.split(", ");
            var deptNameArr = document.frm.orgName.value.split(", ");
            // 선택된 부서 push
            deptOidArr.push(deptOid);
            deptNameArr.push(deptName);
            // 중복 부서 정리
            deptOidArr = $.unique(deptOidArr.sort());
            deptNameArr = $.unique(deptNameArr.sort());

            document.frm.orgOid.value = deptOidArr.join(", ");
            document.frm.orgName.value = deptNameArr.join(", ");
        }
    }
    // ==  [End] 부서 검색  == //

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

    //Jquery
    $(document).ready(function(){

        $("#searchYear").multiselect({
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });

        $("#prodMoldCls").multiselect({
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });
        $("#devYn").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#divisionFlag").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
        });
        $("#sancStateFlag").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    });
    //]]>
</script>
</head>

<body>
<form name="frm" method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="oid" value="">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02228") %><%--월간 현황조회--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00195") %><%--ECO 현황--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02228") %><%--월간 현황조회--%>
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
                                <a href="javascript:doCancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02654") %><%--조회년도--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <select name="searchYear" id="searchYear" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                int year = StringUtil.parseInt(DateUtil.getThisYear(), 2010);
                int yearSt = year - 10;
                int yearEd = year + 5;
                for ( int i=yearSt; i<=yearEd; i++ ) {
                %>
                <option value="<%=i%>" <%=searchYearCondList.contains( Integer.toString(i) ) ? " selected" : "" %>><%=i%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "00197") %><%--ECO구분--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <select id="prodMoldCls" name="prodMoldCls" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                for ( int i=0; i<newPartTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=newPartTypeNumCode.get(i).get("code") %>" <%=prodMoldClsCondList.contains( newPartTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=newPartTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
            <td class="tdwhiteL">
                  <select id="devYn" name="devYn" class="fm_jmp" style="width:270px" multiple="multiple">
                  <%
                for ( int i=0; i<devYnNumCode.size(); i++ ) {
                %>
                <option value="<%=devYnNumCode.get(i).get("code") %>" <%=devYnCondList.contains( devYnNumCode.get(i).get("code") ) ? " selected" : "" %>><%=devYnNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
            <td class="tdwhiteL0">
                <select id="divisionFlag" name="divisionFlag" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                for ( int i=0; i<divisionNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionNumCode.get(i).get("code") %>" <%=divisionCondList.contains( divisionNumCode.get(i).get("code") ) ? " selected" : "" %>><%=divisionNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
            <td class="tdwhiteL">
                <input style="width:210px;" type="text" id="orgName" name="orgName" class="txt_fieldRO" readonly value="<%=searchCondition.getString("orgName") %>">
                <input type=hidden id="orgOid" name="orgOid" value="<%=searchCondition.getString("orgOid") %>">
                <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment();"><img src="/plm/portal/images/icon_delete.gif"></a>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="creatorName" name="creatorName" readonly class="txt_fieldRO"  style="width:210px;" value="<%=searchCondition.getString("creatorName") %>">
                <input type="hidden" id="creator" name="creator" value="<%=searchCondition.getString("creator") %>">
                &nbsp;<a href="javascript:selectUser()"><img src="/plm/portal/images/icon_user.gif"></a>
                &nbsp;<a href="JavaScript:clearUser()"><img src="/plm/portal/images/icon_delete.gif"></a>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL0" colspan="3">
                <select name="sancStateFlag"  id="sancStateFlag" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                for ( int i=0; i<ecoReportStateNumCode.size(); i++ ) {
                %>
                <option value="<%=ecoReportStateNumCode.get(i).get("code") %>" <%=stateCondList.contains( ecoReportStateNumCode.get(i).get("code") ) ? " selected" : "" %>><%=ecoReportStateNumCode.get(i).get("value")%></option>
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
        <!-- [END] Page Size -->

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/ecm/SearchMonthlyEcoReportGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Year="<%=selectYear %>"
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
