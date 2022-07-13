<%@page import="e3ps.common.util.CommonUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.KETParamMapUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");
    // EJS TreeGrid End

    String projectOid = request.getParameter("projectOid");
    String oid = request.getParameter("projectOid");
    String popup = request.getParameter("popup");
    if ( projectOid == null ) projectOid = "";
    boolean isCS = CommonUtil.isMember("공정감사");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/top_include.jsp"%>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>


<script type="text/javascript">
//<![CDATA[
    function viewIssue(v) {
        var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
        openOtherName(url,"viewIssue","750","700","status=no,scrollbars=yes,resizable=no");
    }

    function clearAll(){
        $("#category").multiselect("uncheckAll");
        $("#manager").val("");
        $("#tempmanager").val("");
        $("#subject").val("");
        $("#owner").val("");
        $("#tempowner").val("");
        $("#startCreateDate").val("");
        $("#endCreateDate").val("");
    }

    function search() {
        document.frm.action = "/plm/servlet/e3ps/IssueServlet?command=searchProjectIssue";
        document.frm.submit();
    }

    // ==  [Start] 사람 검색  == //
    function delUser(targetId) {
        $("#" + targetId).val("");
        $("#temp" + targetId).val("");
    }

    function addUser(targetId) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
            return;
        }
        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "Y";
        acceptUser(targetId, acceptUsers, isAppend);
    }

    function acceptUser(targetId, arrObj, isAppend) {
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
        if ( $("#" + targetId).val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#" + targetId).val().split(", "), userId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#temp" + targetId).val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#" + targetId).val(tmpId.join(", "));
        $("#temp" + targetId).val(tmpName.join(", "));
    }
    // ==  [End] 사람 검색  == //

    //Jquery
    $(document).ready(function(){
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#category").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    });
//]]>
</script>

</head>

<body>
<%@include file="/jsp/common/processing.html"%>
    <form name="frm" method="post">
        <!-- hidden begin -->
        <input type="hidden" name="projectOid" value="<%=projectOid%>" />
        <!-- hidden end -->
        <table style="width: 100%; height: 100%;">
            <tr>
                <td valign="top">
                    <!-- [START] title & position -->
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <table style="width: 100%; height: 28px;">
                                    <tr>
                                        <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551")%><%--제품 프로젝트 상세정보--%></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table> <!-- [END] title & position --> <!-- [START] button -->
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="5"></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/jsp/project/ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/jsp/project/ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/jsp/project/ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isCS) {
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/jsp/project/ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640")%><%--비용--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>&popup=<%=popup%>"
                                                        class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01381")%><%--목표--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab">Gate/DR<%--Gate/DR--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="/plm/jsp/project/ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "03034")%><%--프로그램--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                            </td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="#" onClick="javascript:top.close();"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>    
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                            <td height="10" background="/plm/portal/images/box_14.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <table style="width: 100%;">
                                    <tr>
                                        <td style="width: 20px"><img src="/plm/portal/images/icon_4.png"></td>
                                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00394")%><%--Project Issue 목록--%></td>
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a>
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
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
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
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table> <!-- [END] button --> <!-- [START] search condition -->
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02176")%><%--완료여부--%></td>
                                        <td class="tdwhiteL" style="width: 270px;"><select id="category" name="category" class="fm_jmp"
                                            style="width: 270px;" multiple="multiple">
                                                <%
                                                    String[] category = searchCondition.getStringArray("category");
                                                %>
                                                <option value="1" <%=(KETParamMapUtil.contains(category, "1")) ? " selected" : ""%>><%=messageService.getString("e3ps.message.ket_message", "02171")%><%--완료--%></option>
                                                <option value="0" <%=(KETParamMapUtil.contains(category, "0")) ? " selected" : ""%>><%=messageService.getString("e3ps.message.ket_message", "01454")%><%--미완료--%></option>
                                        </select></td>
                                        <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01205")%><%--담당자--%></td>
                                        <td class="tdwhiteL0" style="width: 270px;"><input type="text" id="tempmanager" name="tempmanager"
                                            class="txt_fieldRO" style="width: 200px;" readonly value="<%=searchCondition.getString("tempmanager")%>">
                                            <input type="hidden" id="manager" name="manager" value="<%=searchCondition.getString("manager")%>">
                                            <a href="javascript:addUser('manager');"><img src="/plm/portal/images/icon_user.gif"></a> <a
                                            href="javascript:delUser('manager');"><img src="/plm/portal/images/icon_delete.gif"></a></td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524")%><%--제목--%></td>
                                        <td class="tdwhiteL"><input type="text" id="subject" name="subject" class="txt_field" style="width: 270px;"
                                            value="<%=searchCondition.getString("subject")%>"></td>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523")%><%--제기자--%></td>
                                        <td class="tdwhiteL0"><input type="text" id="tempowner" name="tempowner" class="txt_fieldRO"
                                            style="width: 200px;" readonly value="<%=searchCondition.getString("tempowner")%>"> <input
                                            type="hidden" id="owner" name="owner" value="<%=searchCondition.getString("owner")%>"> <a
                                            href="javascript:addUser('owner');"><img src="/plm/portal/images/icon_user.gif"></a> <a
                                            href="javascript:delUser('owner');"><img src="/plm/portal/images/icon_delete.gif"></a></td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02521")%><%--제기일--%></td>
                                        <td class="tdwhiteL0" colspan="3"><input type="text" readonly id="startCreateDate" name="startCreateDate"
                                            class="txt_fieldRO" style="width: 70px;" value="<%=searchCondition.getString("startCreateDate")%>">
                                            &nbsp;<img src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(startCreateDate)"
                                            style="cursor: hand;"> <a href="javascript:clearDate('startCreateDate')"><img
                                                src="/plm/portal/images/icon_delete.gif"></a> &nbsp;~&nbsp; <input type="text" readonly
                                            id="endCreateDate" name="endCreateDate" class="txt_fieldRO" style="width: 70px;"
                                            value="<%=searchCondition.getString("endCreateDate") %>"> &nbsp;<img
                                            src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(endCreateDate)" style="cursor: hand;">
                                            <a href="javascript:clearDate('endCreateDate')"><img src="/plm/portal/images/icon_delete.gif"></a></td>
                                    </tr>
                                </table>
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table> <!-- [END] search condition --> <!-- EJS TreeGrid Start -->
                                <div class="content-main">
                                    <div class="container-fluid">
                                        <div class="row-fluid">
                                            <div style="WIDTH: 100%; HEIGHT: 100%">
                                                <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/project/ProjectIssueViewListGridLayout.jsp"
                                                    Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST" Data_Param_Result="<%=tgData %>"
                                                    Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData"
                                                    Export_Param_File="Search_StandardDoc_List"></bdo>
                                            </div>
                                        </div>
                                    </div>
                                </div> <!-- EJS TreeGrid Start -->
                            <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
                            <td height="10" background="/plm/portal/images/box_16.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
                        </tr>
                    </table>
                 </td>
             </tr>
        </table>
    </form>
</body>
</html>
