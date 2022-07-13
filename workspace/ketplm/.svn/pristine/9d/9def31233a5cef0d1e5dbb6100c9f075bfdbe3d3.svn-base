<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.dms.service.KETDmsHelper,
                e3ps.dms.entity.KETDocumentCategory,
                e3ps.common.util.StringUtil,
                e3ps.common.web.ParamUtil,
                java.util.ArrayList"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String mode = StringUtil.trimToEmpty(request.getParameter("mode"));
    String isReview = StringUtil.trimToEmpty(request.getParameter("isReview"));
    String method = StringUtil.trimToEmpty(request.getParameter("method"));

    if ( searchCondition == null || searchCondition.isEmpty() ) {
        if ( (mode == null) || (mode.trim().length() == 0) ) {
            mode = "multi";
        } else {
            mode = "one";
        }
    }
    else {
        mode = searchCondition.getString("mode");
    }

    String authorStatus = StringUtil.trimToEmpty(request.getParameter("authorStatus"));
    if ( (authorStatus == null) || (authorStatus.trim().length() == 0) ) {
        authorStatus = "APPROVED";
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

<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 5px;
    margin-bottom: 0px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script type="text/javascript">
$(document).ready(function(e) {
	CalendarUtil.dateInputFormat('predate','postdate'); //기한 설정시 start와 end field를 설정한다.
	SuggestUtil.bind('USER', 'creatorName', 'creator');
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', '', 'projectName');
	
	$(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	search();
        }
    });
    
});

//<![CDATA[
    function deleteValue(param){
        document.getElementById(param).value = "";
    }

    function hideProcessing() {
        var div1 = document.getElementById('div1');
        var div2 = document.getElementById('div2');
        div1.style.display = "none";
        div2.style.display = "none";
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
            userId[i] = infoArr[3];
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

    function search(){
    	
    	// 검색조건 2가지 이상
    	var conditionCnt = 0;
    	if($("input[name='documentNo']").val() != '') conditionCnt++;
    	if($("input[name='creator']").val() != '') conditionCnt++;
    	if($("input[name='documentName']").val() != '') conditionCnt++;
    	if($("input[name='predate']").val() != '') conditionCnt++;
    	if($("input[name='postdate']").val() != '') conditionCnt++;
    	if($("input[name='projectNo']").val() != '') conditionCnt++;
    	if(conditionCnt < 1) {
    		alert('<%=messageService.getString("e3ps.message.ket_message", "03135") %><%--하나 이상의 검색조건을 입력해 주십시오--%>');
    		return;
    		
    	} else {
	    	
	    	if(!valiDate()) return;
	        showProcessing();
	        document.form01.action = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=searchDocumentPop";
	        document.form01.target = "_self";
	        document.form01.submit();
	        
    	}
    }

    function valiDate(){
        var startDate;
        var endDate;

        startDate = document.form01.predate.value;
        endDate = document.form01.postdate.value;

        if ((startDate != "")&&(endDate != "")){
            if(startDate>endDate){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01166") %><%--날짜순서가 맞지 않습니다--%>');
                return false;
            }
        }
        return true;
    }

    function selProjectNo(){
        var d = document.forms[0];

        var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&modal=Y";
        //openWindow(url,"","725","500","status=0,scrollbars=no,resizable=no");
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes");

        if(returnValue==null) {
            return;
        }

        var trArr;
        trArr = returnValue[0];

        d.projectNo.value = trArr[1];
        d.projectName.value = trArr[2];
    }

    function delProjectNo(){
        var d = document.forms[0];
        d.projectNo.value = "";
        d.projectName.value = "";
    }

    function doClear1(){
        
        $('[name=form01]')[0].reset();
        //doClear();
    }

    function doSelect() {

        var G = Grids[0];
        var arr = new Array();
        var subArr = new Array();
        var idx = 0;

        if( G != null ){

            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01414") %><%--문서를 선택해 주십시오--%>');
                return;
            }

            for ( var i=0; i<R.length; i++ ) {
                 subArr = new Array();
                 subArr[0] = R[i].IDocuOid;     //Doc Oid
                 subArr[1] = R[i].DocNo;        //Doc No
                 subArr[2] = R[i].DocName;      //Doc Name
                 subArr[3] = R[i].DocVer;       //Doc Vaerion
                 subArr[4] = R[i].DeptName;     //Dept Name
                 subArr[5] = R[i].CreatorName;  //Creator Name
                 subArr[6] = R[i].PjtNo;        //Project No
                 arr[idx++] = subArr;
            }
        }

        if(opener) {
            if(opener.<%=method%>) {
                opener.<%=method%>(arr);
            }
        }

        //안수학추가
        if(parent) {
            if(parent.<%=method%>) {
                parent.<%=method%>(arr);
            }
        }
        self.close();
    }

    //////////////아래와 같이 호출한다.
    function selectDocu(){
        var url="/plm/jsp/dms/SearchDocumentPop.jsp?method=selDocu";
        openWindow(url,"","500","500","status=1,scrollbars=no,resizable=no");
    }


    function selDocu(arr){

        for (var i = 0; i < arr.length; i++){
           var docuOid = arr[i];
        }
    }
//]]>
</script>
</head>

<body>

<form name="form01" method="post" >
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="method" value="<%=method%>">
<input type="hidden" name="authorStatus" value="<%=authorStatus%>">
<input type="hidden" name="isReview" value="<%=isReview%>">

<table style="width: 100%; height: 100%;">
<tr>
    <td height="50" valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;">
                <tr>
                    <td style="width: 7px"></td>
                    <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01404") %><%--문서검색--%></td>
                </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        </table>
        <!-- [END] title & position -->
    </td>
</tr>
<tr>
    <td valign="top">
        <table style="width: 100%; height: 100%;">
        <tr>
            <td width="10">&nbsp;</td>
            <td valign="top">
                <table style="width: 100%;">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table>
                        <tr>
                            <td>
                                <table>
                                <tr>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                        <a href="javascript:doClear1();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
                                    </td>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                                </table>
                            </td>
                            <td>&nbsp;</td>
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
                            <td>&nbsp;</td>
                            <td>
                                <table>
                                <tr>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                                </table>
                            </td>
                            <td>&nbsp;</td>
                            <td>
                                <table>
                                <tr>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
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
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [START] search condition -->
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table id="searchCondition" style="width: 100%;">
        <colgroup>
            <col width="25%" />
            <col width="25%" />
            <col width="20%" />
            <col width="30%" />
        </colgroup>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
            <td class="tdwhiteL">
                <input type="text" id="documentNo" name="documentNo" class="txt_field"  style="width: 130px;" value="<%=searchCondition.getString("documentNo") %>">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
                <input type="text" name="creatorName" class="txt_field" style="width: 70%" value="<%=searchCondition.getString("creatorName") %>">
                <input type="hidden" name="creator"  value="<%=searchCondition.getString("creator") %>">
                <a href="javascript:SearchUtil.selectOneUser('creator','creatorName');">
                <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('creator','creatorName');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
            <td class="tdwhiteL0" colspan="3">
                <input type="text" id="documentName" name="documentName" class="txt_field"  style="width:99%" value="<%=searchCondition.getString("documentName") %>">
            </td>
        </tr>
        <tr>
            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td colspan="3" class="tdwhiteL0">
                <input type="text" name="predate" class="txt_field" style="width: 80px;" value="<%=searchCondition.getString("predate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('predate');" style="cursor: hand;"> 
                ~ 
                <input type="text" name="postdate" class="txt_field" style="width: 80px;" value="<%=searchCondition.getString("postdate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('postdate');"
                style="cursor: hand;">    
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03115") %><%--프로젝트번호/명--%></td>
            <td colspan="3" class="tdwhiteL0">
                <input type="text" id="projectNo" name="projectNo" class="txt_field" style="width:105px" value="<%=searchCondition.getString("projectNo") %>">
                <input type="text" id="projectName" name="projectName" class="txt_fieldRO" readonly style="width:190px" value="<%=searchCondition.getString("projectName") %>">
                &nbsp;<a href="javascript:selProjectNo()"><img src="/plm/portal/images/icon_5.png"></a>
                &nbsp;<a href="javascript:delProjectNo()"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        </table>
        <table style="width: 100%;">
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
                            Layout_Url="/plm/jsp/dms/SearchDocumentPopGridLayout.jsp"
                            Layout_Param_Mode="<%=mode %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Document_List"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid Start -->
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td height="30" valign="bottom">
        <table style="width: 100%;">
        <tr>
            <td width="10">&nbsp;</td>
            <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460px" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
        </table>
    </td>
</tr>
</table>
</form>
</body>
</html>
