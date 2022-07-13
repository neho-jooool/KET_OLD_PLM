<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.StringUtil,
                e3ps.common.util.KETParamMapUtil"%>

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
    if ( projectOid == null ) projectOid = "";

    String mode = StringUtil.checkNull(request.getParameter("mode"));
    if ( mode.equals("") ) {
        mode = "multi";//(single:한건선택 multi:다건선택)
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

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/top_include.jsp"%>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 5px;
    margin-top: 5px;
    margin-right: 5px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script src="/plm/jsp/ecm/ProjectIssueViewListPopup.js"></script>

<script type="text/javascript">
//<![CDATA[
    function viewIssue(v) {
        var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
        //openOtherName(url,"viewIssue","750","350","status=no,scrollbars=yes,resizable=no");
        openWindow2(url,"","750","320","scrollbars=no,resizable=no,top=200,left=250");
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
        document.forms[0].action = "/plm/servlet/e3ps/IssueServlet?command=searchProjectIssuePopup";
        document.forms[0].submit();
    }

    function onSelect() {
        var G = Grids[0];
        var arr = new Array();
        var subArr = new Array();
        var idx = 0;

        if( G != null ){

            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert("<%=messageService.getString("e3ps.message.ket_message", "00279") %><%--Issue를 선택해 주십시오--%>");
                return;
            }

            for ( var i=0; i<R.length; i++ ) {
                 subArr = new Array();
                 subArr[0] = R[i].IssueOid;       //IssueOid
                 subArr[1] = R[i].Subject;        //Subject
                 subArr[2] = R[i].Owner;          //Owner - 작성자
                 subArr[3] = R[i].CreateDate;     //CreateDate
                 arr[idx++] = subArr;
            }
        }

        parent.selectModalDialog(arr);
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
<form name="frm" method="post">

<!-- hidden begin -->
<input type = "hidden" name="projectOid" value="<%=projectOid%>" />
<input type = "hidden" name="mode" value="<%=mode%>" />
<!-- hidden end -->

<table style="width: 100%; height: 100%;">
<tr>
    <td height="50" valign="top">
        <table style="width: 100%;">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;">
                  <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00266") %><%--Issue 검색--%></td>
                  </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td valign="top">
        <table style="width:710px; height: 100%;">
        <tr>
            <td width="10">&nbsp;</td>
            <td valign="top">
                <table style="width: 770px;">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table>
                        <tr>
                            <td>
                                <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
                <table style="width: 770px;">
                <tr>
                    <td class="space5"></td>
                </tr>
                </table>
                <table style="width: 770px;">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
                </table>
                <table style="width: 770px;">
                <tr>
                    <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
                    <td class="tdwhiteL" style="width: 270px;">
                        <select id="category" name="category" class="fm_jmp" style="width:270px;" multiple="multiple">
                        <%
                        String[] category = searchCondition.getStringArray("category");
                        %>
                            <option value="1" <%=(KETParamMapUtil.contains(category, "1") ) ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></option>
                            <option value="0" <%=(KETParamMapUtil.contains(category, "0") ) ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "01454") %><%--미완료--%></option>
                        </select>
                    </td>
                    <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                    <td class="tdwhiteL0" style="width: 270px;">
                        <input type="text" id="tempmanager" name="tempmanager" class="txt_fieldRO" style="width:210px;" readonly value="<%=searchCondition.getString("tempmanager") %>">
                        <input type="hidden" id="manager" name="manager" value="<%=searchCondition.getString("manager") %>">
                        <a href="javascript:addUser('manager');"><img src="/plm/portal/images/icon_user.gif"></a>
                        <a href="javascript:delUser('manager');"><img src="/plm/portal/images/icon_delete.gif"></a>
                    </td>
                </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                  <td class="tdwhiteL">
                      <input type="text" id="subject" name="subject" class="txt_field" style="width:270px;" value="<%=searchCondition.getString("subject") %>">
                  </td>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                  <td class="tdwhiteL0">
                      <input type="text" id="tempowner" name="tempowner" class="txt_fieldRO" style="width:210px;" readonly value="<%=searchCondition.getString("tempowner") %>">
                      <input type="hidden" id="owner" name="owner" value="<%=searchCondition.getString("owner") %>">
                      <a href="javascript:addUser('owner');"><img src="/plm/portal/images/icon_user.gif"></a>
                      <a href="javascript:delUser('owner');"><img src="/plm/portal/images/icon_delete.gif"></a>
                  </td>
                </tr>
                <tr>
                    <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02521") %><%--제기일--%></td>
                    <td class="tdwhiteL0" colspan="3">
                        <input type="text" readonly id="startCreateDate" name="startCreateDate" class="txt_fieldRO" style="width:70px;" value="<%=searchCondition.getString("startCreateDate") %>">
                        &nbsp;<img src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(startCreateDate)" style="cursor:hand;">
                        <a href="javascript:clearDate('startCreateDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
                        &nbsp;~&nbsp;
                        <input type="text" readonly id="endCreateDate" name="endCreateDate" class="txt_fieldRO"  style="width:70px;" value="<%=searchCondition.getString("endCreateDate") %>">
                        &nbsp;<img src="/plm/portal/images/icon_6.png" onClick="javascript:showCal(endCreateDate)" style="cursor:hand;">
                        <a href="javascript:clearDate('endCreateDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
                    </td>
                </tr>
                </table>
                <table style="width: 780px;">
                <tr>
                    <td class="space5"></td>
                </tr>
                </table>
                <!-- EJS TreeGrid Start -->
                <div class="content-main">
                    <div class="container-fluid">
                        <div class="row-fluid">
                            <div style="WIDTH:100%;HEIGHT:100%">
                                <bdo Debug="1" AlertError="0"
                                    Layout_Url="/plm/jsp/ecm/ProjectIssueViewListPopupGridLayout.jsp"
                                    Layout_Param_Mode="<%=mode %>"
                                    Data_Url="/plm/jsp/common/searchGridData.jsp"
                                    Data_Method="POST"
                                    Data_Param_Result="<%=tgData %>"
                                ></bdo>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- EJS TreeGrid Start -->
                <table style="width: 770px;">
                <tr>
                    <td align="center">
                        <table>
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td height="30" valign="bottom">
        <table style="width: 470px;">
        <tr>
            <td width="10">&nbsp;</td>
            <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="770px;" height="24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
        </table>
    </td>
</tr>
</table>
</form>
</body>
</html>
