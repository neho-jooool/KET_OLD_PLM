<%@page import="e3ps.common.util.CommonUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@page import="java.util.ArrayList,
                java.util.Map,
                java.util.HashMap,
                java.util.List"%>

<%@page import="e3ps.common.util.StringUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.KETParamMapUtil,
                e3ps.common.code.NumberCodeHelper"%>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    // EJS TreeGrid Start
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
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "CUSTOMERVIEWTYPE");
    List<Map<String, Object>> customerViewTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02406") %><%--자동차일정 검색--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/context.jsp"%>
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
table th,table td {
    padding: 0
}
img {
    vertical-align: middle;
    border: 0;
}
input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/custom-jquery.css" />
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript">
//<![CDATA[
    // 상세 페이지 이동
    function gotoView(oid){
    	getOpenWindow2('/plm/jsp/project/ViewProgram.jsp?oid='+oid,'CarDateViewPopup',850,770);
    }

    // 필드 값 초기화
    function deleteValueAll(){
        $("#customer").val("");
        $("#cartype").val("");
        $("#customerViewType").multiselect("uncheckAll");
        $("#predate").val("");
        $("#postdate").val("");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function perPage(v) {
        document.ListProgram.perPage.value = v;

        search();
    }

    // 검색
    function search() {

        /* showProcessing();     // 진행중 Bar 표시
        disabledAllBtn();     // 버튼 비활성화

        document.ListProgram.action =  "/plm/servlet/e3ps/CarServlet?command=search";
        document.ListProgram.submit(); */
        
        loadEjsGrid();
    }
    
    function loadEjsGrid(){
        try{
           var idx = 0;
           var D = Grids[idx].Data;
           var formName = "ListProgram";
           
           D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
           
           D.Data.Url = "/plm/servlet/e3ps/CarServlet?command=search";
           D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
           
           Grids[idx].Reload(D);
           
           var S = document.getElementById("Status"+idx);
           if(S) S.innerHTML="Loading";
        
        }catch(e){
            alert(e.message);
        }
    }

    function excelDown() {
        document.ListProgram.action = "/plm/servlet/e3ps/CarServlet?command=excelDown";
        document.ListProgram.submit();
    }
    
    function createPopup(){
    	getOpenWindow2('/plm/jsp/project/CreateProgram.jsp','CarDateCreatePopup',820,630);
    }
    
    function changeDetailCustomer(){
    	
    	if($("input:checkbox[id='detailCustomer']").is(":checked")){
    		$('[id=customerDetail]').css("display","");
    		$("#criticalCustomer").multiselect("uncheckAll");
    		$("select[id=criticalCustomer]").siblings().attr('disabled', 'disabled');
    		
    		$('[id=detailCustomer]').removeAttr("disabled");

        }else{
        	$('[name=customerEvent1Txt]').val('');
        	$('[id=customerDetail]').css("display","none");
        	$("select[id=criticalCustomer]").siblings().removeAttr("disabled");
        }

    }
    
    
    function selectLastUsingBuyer1(returnValue){
        $('[name=customerEvent1Txt]').val('');
        $('[name=customerEvent1]').val('');
        var customerEvent1Txt = '';
        var customerEvent1 = '';        
        for(var i=0; i<returnValue.length;i++){
            if(i != returnValue.length-1){
                customerEvent1Txt += returnValue[i][2]+',';         
                customerEvent1 += returnValue[i][0]+',';            
            }else{
                customerEvent1Txt += returnValue[i][2];         
                customerEvent1 += returnValue[i][0];            
            }
        }
        $('[name=customerEvent1Txt]').val(customerEvent1Txt);           
        $('[name=customerEvent1]').val(customerEvent1);             
    }
    
    function printTime(option) {
        
        var now = new Date();                                                  // 현재시간
        var standardYear  = Number(now.getFullYear());
        var str ="";
        //$("#sop").append("<option class='export_option' value='after'>오늘 이후</option>");
        for(var i=0; i <= 10; i++){
            str += "<option class='export_option' value='"+standardYear+"'>"+standardYear+"</option>";
            standardYear--;
        }
        $("#sop").append(str);


        //$("#years").val(now.getFullYear()).attr("selected", "selected");
        //var month = now.getMonth()+1;
    }
    
    function changeSop(){
    	
        if($("input:checkbox[id='todaySop']").is(":checked")){
        	
        	$("#todaySop").val('after');
        	$("#sop").multiselect("uncheckAll");
        	$("select[id=sop]").siblings().attr('disabled', 'disabled');
        	$('[id=todaySop]').removeAttr("disabled");
        }else{
        	$("#todaySop").val('');
        	$("select[id=sop]").siblings().removeAttr("disabled");
        }
    }
    
    function setCarType(returnValue){
    	$('[name=cartype]').val(returnValue[0][1]);
    }
    
    
    // Jquery
    $(document).ready( function(){
        // Multi Combo 생성
        $("#customerViewType").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("form[name=ListProgram]").keypress(function(e) {
            //Enter key
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });
        //고객처(사) suggest
        SuggestUtil.bind('CUSTOMER', 'customer');
        //차종 suggest
        SuggestUtil.bind('CARTYPE', 'cartype');
        // Calener field 설정
        CalendarUtil.dateInputFormat('predate','postdate'); //기한 설정시 start와 end field를 설정한다.
        
        SuggestUtil.bind('CUSTOMEREVENT', 'customerEvent1Txt', 'customerEvent1');
        
        $("#criticalCustomer").multiselect({
            minWidth: 130,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#formType").multiselect({
            minWidth: 130,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        printTime();
        
        $("#sop").multiselect({
            minWidth: 130,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        treeGridResize("#ListProgramGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId

        });
//]]>
</script>
</head>

<body class="body-space">
<form name="ListProgram" method="POST">

<!-- hidden begin -->
<input type="hidden" name="perPage" value="<%=pagingSize %>">
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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02406") %><%--자동차일정 검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02391") %><%--자동차--%>
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
        <table style="width: 100%;">
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <table>
                <tr>
                    <td>
                        <!-- <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%> -->
                    </td>
                    <%if(CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO") || CommonUtil.isMember("자동차일정")){ %>
                    <td style="width: 15px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:createPopup();" class="btn_blue">등록</a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%} %>
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:deleteValueAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] button -->
        <!-- [START] search condition -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div> 
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
        </tr>
        <td class="tdblueL"  style="width: 15%;"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
        <td class="tdwhiteL" style="width: 35%;">
            <select onBlur="fm_jmp" id="criticalCustomer" name="criticalCustomer" multiple="multiple" style="width:200px">
                <option value="HMC">HMC</option>
                <option value="KMC">KMC</option>
                <option value="삼성전자">삼성전자</option>
                <option value="LG전자">LG전자</option>
                <option value="중국OEM">중국 OEM</option>
                <option value="GlobalOEM">Global OEM</option>
                <option value="ETC">기타</option>
            </select>
            &nbsp;&nbsp;상세조회<input type="checkbox" id="detailCustomer" name="detailCustomer" onChange="changeDetailCustomer();"/>
            <span id="customerDetail" style="display : none;">
            <input type="text" name="customerEvent1Txt" class="txt_field" style="width: 30%">
            <input type="hidden" name="customerEvent1"><a href="javascript:SearchUtil.selectLastUsingBuyer(selectLastUsingBuyer1);">
            <img src="/plm/portal/images/icon_5.png" border="0"></a>
            <a href="javascript:CommonUtil.deleteValue('customerEvent1Txt','customerEvent1');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </span>
        </td>
        <td class="tdblueL" style="width: 15%;"><%=messageService.getString("e3ps.message.ket_message", "03223") %><%--형태--%></td>
        <td class="tdwhiteL" style="width: 35%;">
            <ket:select id="formType" name="formType" className="fm_jmp" style="width: 170px;" codeType="FORMTYPE" multiple="multiple" useCodeValue="true"/>
        </td>
        <tr>
            <td class="tdblueL"  style="width: 100px;">SOP</td>
            <td class="tdwhiteL">
              오늘 이후&nbsp;&nbsp;<input type="checkbox" id="todaySop" name="todaySop" onChange="changeSop();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select id="sop" name="sop" class="fm_jmp" style="width:170px;" multiple="multiple"></select>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <input type="text" id="cartype" name="cartype" class="txt_field"  style="width: 200px;" value="<%=searchCondition.getString("cartype") %>">
                <a href="javascript:SearchUtil.selectCarType(null,'cartype','setCarType');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('cartype');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02744") %><%--차량일정--%></td>
            <td class="tdwhiteL0" colspan="3">
                <select id="customerViewType" name="customerViewType" style="width: 275px;" multiple="multiple">
                <%
                String[] customerViewType = searchCondition.getStringArray("customerViewType");
                for ( int i=0; i<customerViewTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=customerViewTypeNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(customerViewType, customerViewTypeNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=customerViewTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
                <input type="text" id="predate" name="predate" class="txt_field" style="width:100px;" value="<%=searchCondition.getString("predate") %>">
                &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('predate');" style="cursor: hand;"></a>
                &nbsp;~&nbsp;
                <input type="text" id="postdate" name="postdate" class="txt_field"  style="width:100px;" value="<%=searchCondition.getString("postdate") %>">
                &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('postdate');" style="cursor: hand;"></a>
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
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%; min-height:200px">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/ListProgramGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
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
<!-- <tr>
    <td style="height: 30px" valign="bottom">
        <table style="width: 100%;">
        <tr>
            <td style="width: 10px">&nbsp;</td>
            <td style="height: 30px">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 100%; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr> -->
<!-- [END] copyright -->

</table>
</form>
</body>
</html>
