<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
    
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script type="text/javascript" src="/plm/extcore/js/cost/cost.js?ver=0.2"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
	
	
	
    SuggestUtil.bind('PRODUCTPARTNO', 'partNo');
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtno');
    SuggestUtil.bind('CARTYPE', 'carTypeName', 'carTypeCode');
    SuggestUtil.bind('USER', 'devRoleName', 'devRoleOid');
    SuggestUtil.bind('CUSTOMER', 'customerName', 'customerCode');
    
    CommonUtil.singleSelect('version', 100);
    
    cost.createPaingGrid();
    
    var pjtNos = '${pjtNos}';
    
    if(pjtNos != ''){
       $("#collapseDiv").hide();
       $("#searchTab").hide(); 
       $(".tab_btm2").hide();
       var isAuth = ${isAuth};
       if(!isAuth){
    	   $("#bt1").hide();   
       }
       $("#bt2").hide();
       $("#bt3").hide();
    }
    
    Grids.OnRenderFinish = function(grid){
      if(pjtNos != ''){
        cost.search();
      }
    }
    
    var agent = navigator.userAgent.toLowerCase();
    var isExplorer = false;
    if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {//ms 익스플로러 인지 확인
    	isExplorer = true;
    } 

    if(!isExplorer){//접속 브라우저가 Explorer 가 아닐때 그리드 리사이즈
    	
    	//크롬에서 그리드 리사이즈 하기 위한 코드 start
        var re_size = 150;

        $("#listGrid").height($(window).height()-re_size);

        $(window).resize(function(){
            $("#listGrid").height($(window).height()-re_size);
        });
        
        
        var timerId = null;
        
        var gridHeight = 0;
        
        window.editorResize = function(){
            
            var windowHeight = $(window).height();
            var gridHeight = $("#TrackingSearchGrid").height();
            
            window.console.log("gridHeight : "+gridHeight);
            window.console.log("windowHeight : "+windowHeight);
             
            //그리드가 로딩되어 높이가 측정되고 스크롤이 필요없을만큼 갯수가 적을 때 (그리드 높이가 낮을 때) 그리드 사이즈만큼 리사이즈 해준다 간이산출과 간격이 벌어지지 않기 위해    
            if(gridHeight != null && gridHeight < windowHeight){
                 window.console.log("TrackingSearchGrid : "+$("#TrackingSearchGrid").height());
                 $("#listGrid").height($(window).height()-re_size);
                 $(window).resize(function(){
                       
                       $("#listGrid").height($(window).height()-re_size);                   
                 });     
                
                 window.console.log($("#listGrid").height());
                 
            }
            if($("#TrackingSearchGrid").height() != null){
                
                clearInterval(timerId); 
            }
        }
        
        timerId = setInterval(editorResize, 100);
        
        //크롬에서 그리드 리사이즈 하기 위한 코드 end
    }
    
    
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
            cost.search();
        }
    });

});

function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    for(var i = 0; i < objArr.length; i++)
    {
        setPart(objArr[i]);
    }
}

function setPart(objArr){
    trArr = objArr;
    var tempRelatedPartOid = $('[name=relatedPartOid]').val();
    var tempRelatedPart = $('[name=partNo]').val();
    
    if(tempRelatedPartOid != ""){
        tempRelatedPartOid = tempRelatedPartOid+",";
    }
    if(tempRelatedPart != ""){
        tempRelatedPart = tempRelatedPart+",";
    }
    
    $('[name=relatedPartOid]').val(tempRelatedPartOid+trArr[0]);
    $('[name=partNo]').val(tempRelatedPart+trArr[1]);
}

function selectMultiSubContractor(returnValue){
    var nodeIdStr='', nodeNameStr='';
    for(var i=0; i < returnValue.length; i++){
            if(i == returnValue.length - 1){
                    nodeIdStr += returnValue[i][0];
                    nodeNameStr += returnValue[i][2];
            }else{
                    nodeIdStr += returnValue[i][0]+',';     
                    nodeNameStr += returnValue[i][2]+',';
            }
    }
    $('[name=customerCode]').val(nodeIdStr);
    $('[name=customerName]').val(nodeNameStr);
}

function addDepartment(targetId) {
	SearchUtil.addDepartmentAfterFunc(false,'addDeptCallBack');
}

function addDeptCallBack(infoArr){
    
    var code = "";
    var name = "";
    for(var i= 0 ; i < infoArr.length; i++){
        if(i != 0){
            code += ",";
            name += ",";
        }
        code += infoArr[i].OID;
        name += infoArr[i].NAME;
    }
    $("#devTeamOid").val( code );
    $("#devTeamName").val( name );
    
}

function setProject(arr){
	cost.setProjectNo(arr);
}

function delDepartment(targetId, targetName) {
    $("#" + targetId).val("");
    $("#" + targetName).val("");
}

function openProfitAnalysis(){
	
	// single 선택 확인 버튼 클릭 시
	    
    try{
	    var G = Grids[0];
	    
	    window.console.log(G);
	    
	    if( G != null ){
	        
	        var R = G.GetSelRows();
	        
	        window.console.log(R);
	        
	        if( R.length == 0 ){
	            alert('항목을 선택하세요.');
	            return;
	        }
	        
	        var row = R[0];
	        
	        getOpenWindow2("/plm/ext/cost/costProfitAnalysis.do?partOid=" + row.partOid, "ProfitAnalysis", 1280, 720);
	    }
    }catch(e){
    	
    }
}
</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">수익율 추적관리</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">원가관리
                                <img src="/plm/portal/images/icon_navi.gif">Report
                                <img src="/plm/portal/images/icon_navi.gif">수익율 추적관리</td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <c:if>
            
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td id="bt1" name="bt1">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:openProfitAnalysis()" class="btn_blue">수익율 분석</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td id="bt2" name="bt2">
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:cost.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                   </tr>
                               </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td id="bt3" name="bt3">
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:cost.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
            <form name="searchForm">
                <input type='hidden' id='costPartNos' name='costPartNos' value='${costPartNos}'/>
                <input type='hidden' id='pjtNos' name='pjtNos' value='${pjtNos}'/>
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                
                <table border="0" cellspacing="0" cellpadding="0" width="100%" id='searchTab'>
                    <colgroup>
                        <col width="10%" />
                        <col width="15%" />
                        <col width="10%" />
                        <col width="15%" />
                        <col width="10%" />
                        <col width="15%" />
                        <col width="10%" />
                        <col width="15%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL">
                          <input type="text" name="pjtNo" class="txt_field" style="width:70%" value="">
                          <input type="hidden" name="pjtOid" value="">
                          <a href="javascript:SearchUtil.selectOneProject('setProject');">
                          <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('pjtNo','pjtOid');">
                          <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">프로젝트명</td>
                        <td class="tdwhiteL">
                            <input type="text" name="pjtName" class="txt_field" style="width:70%" value="">
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09005") %><%--부품 NO--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="partNo" class="txt_field" style="width: 70%">
                            <input type="hidden" name="relatedPartOid">
                            <a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc','pType=P');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partNo', 'relatedPartOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="customerName" class="txt_field" style="width: 70%" value="">
                            <input type="hidden" name="customerCode" value="">
                            <a href="javascript:SearchUtil.selectMultiSubContractor('selectMultiSubContractor');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('customerCode','customerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="carTypeName" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="carTypeCode">
                            <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">개발담당부서</td>
			            <td class="tdwhiteL">
			                <input style="width:70%;" type="text" id="devTeamName" name="devTeamName" class="txt_field" value="">
			                <input type=hidden id="devTeamOid" name="devTeamOid" value="">
			                <a href="javascript:addDepartment('devTeamName');"><img src="/plm/portal/images/icon_5.png"></a>
			                <a href="javascript:delDepartment('devTeamOid','devTeamName');"><img src="/plm/portal/images/icon_delete.gif"></a>
			            </td>
                        <td class="tdblueL">
                            개발담당
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="devRoleName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="devRoleOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('devRoleOid','devRoleName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('devRoleOid','devRoleName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
                        <td class="tdwhiteL0"><ket:select id="version" name="version" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="VERSION" value="671979013" /></td>
                    </tr>
                </table>
                
            </form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <!-- EJS TreeGrid Start -->
                                    
                                    <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                            <!-- EJS TreeGrid Start -->
                        </div>

                            <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>