<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 메세지 Start-->
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- 메세지 End -->
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/workTimeReport.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>

<!-- EJS TreeGrid End -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07233") %>(Report)</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Dashboard 
                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "07233") %>
                                </td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <form id="workTimeReport" name="workTimeReport">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                
                <td align="left">
                <input type="radio" id="review" name="moldType" value="review" checked="checked"><%=messageService.getString("e3ps.message.ket_message","00716")%><%--검토--%></input> 
                <input type="radio" id="product" name="moldType" value="product"><%=messageService.getString("e3ps.message.ket_message","02536")%><%--제품--%></input>
                <input type="radio" id="mold" name="moldType" value="mold"><%=messageService.getString("e3ps.message.ket_message","00997")%><%--금형--%></input> 
                </td>
               
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:WorkTime.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table>
                </td>
            </table>
            
            <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            
            <c:if test="${moldType ne 'mold'}">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueL"  width="70">Project No</td>
                    <td class="tdwhiteL" width="*" >
                        <input type="text" id="pjtNo" name="pjtNo" class="txt_field"  value="" style="width: 90%">
                    </td>
                    <td class="tdblueL"  width="60" title="Project Name"><div style="width:81px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">프로젝트명</div></td>
                    <td class="tdwhiteL" width="*" colspan="3">
                        <input type="text" id="pjtName" name="pjtName" class="txt_field"  value="" style="width: 98%">
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"  width="60">PM</td>
                    <td class="tdwhiteL" width="*" >
                        <input type="text" id="userName" name="userName" class="txt_field" style="width: 60%;">
                        <a href="javascript:SearchUtil.selectOneUser('fstCharge','userName');">
                        <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('fstCharge','userName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"  width="60" title="<%=messageService.getString("e3ps.message.ket_message", "01201") %>"><%=messageService.getString("e3ps.message.ket_message", "01201") %></div></td>
                    <td class="tdwhiteL" width="*" >
                        <input type="text" id="departName" name="departName" class="txt_field" style="width:70%">
                        <a href="javascript:SearchUtil.addDepartmentAfterFunc(false, 'selectDeptRsltFunc')">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('departName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"  width="70"><%=messageService.getString("e3ps.message.ket_message", "01662") %></td>
                    <td width="*" class="tdwhiteL">
                        <ket:select id="division" name="division" className="fm_jmp"  codeType="DIVISIONFLAG" useCodeValue="true"/>
                    </td> 
                </tr>
                <tr>
                    <td class="tdblueL"  width="60" title="<%=messageService.getString("e3ps.message.ket_message", "07234") %>"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "07234") %></div></td>
                    <td width="*" class="tdwhiteL">
                        <ket:select id="devType" name="devType" className="fm_jmp"   codeType="DEVELOPENTTYPE" multiple="multiple" useCodeValue="true"/>
                    </td>
                    <td class="tdblueL"  width="60" title="<%=messageService.getString("e3ps.message.ket_message", "00859") %>"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "00859") %></div></td>
                    <td width="*" class="tdwhiteL">
                        <input type="text" id="customCompany" name="customCompany" class="txt_field"/>
                    </td>
                    <td class="tdblueL"  width="80" title="<%=messageService.getString("e3ps.message.ket_message", "02847") %>"><div style="width:81px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "02847") %></div></td>
                    <td width="*" class="tdwhiteL">
                      <%--   <ket:select id="vLastCompany" name="vLastCompany" className="fm_jmp"  codeType="DEVELOPENTTYPE" multiple="multiple" useCodeValue="true"/> --%>
                         <ket:select id="lastCompany" name="lastCompany" className="fm_jmp" style="width:100px;" codeType="CUSTOMEREVENT"  depthLevel="child" multiple="multiple"  useCodeValue="true"/>
                    </td>  
                </tr>
               <c:if test="${moldType eq 'product'}">
                <tr>                    
                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">PartNo</div></td>
                    <td width="*" class="tdwhiteL">
                        <input type="text" id="partNo" name="partNo" class="txt_fieldRO" style="width:110px"  readonly>
                        <a href="javascript:selectPartNo('partNo');"><img src="/plm/portal/images/icon_5.png"></a>
                        <a href="javascript:clearPartNo('partNo');"><img src="/plm/portal/images/icon_delete.gif"></a>
                    </td>
                    <td class="tdblueL"  width="60" title="<%=messageService.getString("e3ps.message.ket_message", "01248") %>"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "01248") %></div></td>
                    <td width="*" class="tdwhiteL">
                        <input type="text" id="carName" name="carName" class="txt_field" style="width:110px">
                    </td>
                    <td class="tdblueL"  width="100">Rank</td>
                    <td width="*" class="tdwhiteL">
                        <select class="fm_jmp" multiple="multiple" id="rank" name="rank">
                            <option>S</option>
                            <option>A</option>
                            <option>B</option>
                            <option>C</option>
                        </select>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <td class="tdblueL">
                        <ket:select id="dateType" name="dateType" className="fm_jmp"  codeType="DEVELOPDATETYPE" useCodeValue="true"/>
                    </td>
                    <c:if test="${moldType ne 'mold'}">
                    <td width="*" class="tdwhiteL" colspan="3">
                        <input type="text" name="startDate" class="txt_field" style="width: 80px;" value="">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('startDate');" style="cursor: hand;"> 
                        ~ 
                        <input type="text" name="endDate" class="txt_field" style="width: 80px;">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endDate');" style="cursor: hand;">
                    </td>
                    </c:if>
                    <td class="tdblueL"  width="60"><%=messageService.getString("e3ps.message.ket_message", "01760") %></td>
                    <td width="*" class="tdwhiteL">
                        <c:if test="${moldType eq 'review'}">
                        <ket:select id="state" name="state" className="fm_jmp"   multiple="multiple" lifeCycleState="KET_REVIEW_PMS_LC" useCodeValue="true"/>
                        </c:if>
                        <c:if test="${moldType eq 'product'}">
                        <ket:select id="state" name="state" className="fm_jmp"  multiple="multiple" lifeCycleState="KET_PRODUCT_PMS_LC" useCodeValue="true"/>
                        </c:if>
                    </td>
                </tr>
            </table>
            </c:if>
            <c:if test="${moldType eq 'mold'}">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueL"  width="100">Die No</td>
                    <td class="tdwhiteL" width="*" >
                        <input type="text" id="dieNo" name="dieNo" value="" class="txt_field" style="width: 70%;">
		                <a href="javascript:selectPartNo('dieNo');"><img src="/plm/portal/images/icon_5.png"></a>
		                <a href="javascript:clearPartNo('dieNo');"><img src="/plm/portal/images/icon_delete.gif"></a>
                    </td>
                   <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "07234") %></div></td>
                    <td class="tdwhiteL" width="*" >
                        <ket:select id="devType" name="devType" className="fm_jmp"  codeType="DEVELOPENTTYPE" multiple="multiple" useCodeValue="true"/>
                    </td>
                   <td class="tdblueL"  width="100"><%=messageService.getString("e3ps.message.ket_message", "01248") %></td>
                    <td class="tdwhiteL" width="*" >
                        <input type="text" id="carName" name="carName" class="txt_field"  value="" style="width: 98%">
                    </td>
                  </tr>
                <tr>
                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">Part No</div></td>
                    <td class="tdwhiteL" width="*">
                        <input type="text" id="partNo" name="partNo" class="txt_fieldRO" style="width:110px"  readonly>
                        <a href="javascript:selectPartNo('partNo');"><img src="/plm/portal/images/icon_5.png"></a>
                        <a href="javascript:clearPartNo('partNo');"><img src="/plm/portal/images/icon_delete.gif"></a>
                    </td>
                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">PartName</div></td>
                    <td class="tdwhiteL" width="*" colspan="3">
                        <input type="text" id="partName" name="partName" class="txt_field"  value="" style="width: 98%">
                    </td>
                </tr>   
                 <tr>
                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "01051") %></div></td>
                    <td class="tdwhiteL" width="*">
                        <ket:select id="moldType1" name="moldType1" className="fm_jmp"  onChange="javascript:dieCategoryChange();" codeType="DIETYPE" multiple="multiple" useCodeValue="true"/>
                    </td>
                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "01078") %></div></td>
                    <td class="tdwhiteL" width="*">
                        <ket:select id="dieCategory" name="dieCategory" className="fm_jmp"  codeType="MOLDPRODUCTSTYPE" code="MOL100,MOL200" multiple="multiple" useCodeValue="true"/>
                    </td>
                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "01094") %></div></td>
                    <td class="tdwhiteL" width="*" >
                        <ket:select id="moldCategory" name="moldCategory" className="fm_jmp"  codeType="MOLDTYPE" multiple="multiple" useCodeValue="true"/>
                    </td>
                  </tr> 
                   <tr>
                        <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "02532") %></div></td>
	                    <td class="tdwhiteL" width="*" >
	                        <ket:select id="making" name="making" className="fm_jmp" codeType="MAKINGTYPE" multiple="multiple" useCodeValue="true"/>
	                    </td>
	                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "02533") %></div></td>
	                    <td class="tdwhiteL" width="*" >
	                        <input type="text" id="outsourcing" name="outsourcing" class="txt_field"  value="" style="width: 98%">
	                    </td>
	                    <td class="tdblueL"  width="60"><%=messageService.getString("e3ps.message.ket_message", "01201") %></div></td>
	                    <td class="tdwhiteL" width="*">
	                        <input type="text" id="departName" name="departName" class="txt_field" style="width:70%">
	                        <a href="javascript:SearchUtil.addDepartmentAfterFunc(false, 'selectDeptRsltFunc')">
	                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	                        <a href="javascript:CommonUtil.deleteValue('departName');">
	                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                    </td>
                   </tr>
                <tr>
                    <td class="tdblueL"  width="95"><div style="width:96px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "01056") %></div></td>
                    <td class="tdwhiteL" width="*">
                        <input type="text" id="userName" name="userName" class="txt_field" style="width: 60%;">
                        <a href="javascript:SearchUtil.selectOneUser('fstCharge','userName');">
                        <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('fstCharge','userName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"  width="60"><div style="width:61px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "07235") %></div></td>
                    <td class="tdwhiteL" width="*" >
                        <select class="fm_jmp" multiple="multiple" id="rank" name="rank">
                            <option>S</option>
                            <option>A</option>
                            <option>B</option>
                            <option>C</option>
                            <option>D</option>
                        </select>
                    </td>
                    <td class="tdblueL"  width="30"><div style="width:31px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=messageService.getString("e3ps.message.ket_message", "01760") %></div></td>
                    <td class="tdwhiteL" width="*" >
                        <ket:select id="state" name="state" className="fm_jmp"   multiple="multiple" lifeCycleState="KET_MOLD_PMS_LC" useCodeValue="true"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL">
                        <ket:select id="dateType" name="dateType" className="fm_jmp"  codeType="DEVELOPDATETYPE" useCodeValue="true"/>
                    </td>
                    <td width="*" class="tdwhiteL" colspan="5">
                        <input type="text" name="startDate" class="txt_field" style="width: 80px;" value="">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('startDate');" style="cursor: hand;"> 
                        ~ 
                        <input type="text" name="endDate" class="txt_field" style="width: 80px;">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endDate');" style="cursor: hand;">
                    </td>
                </tr>
            </table>
            </c:if>
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
                                    <div id="listGrid"></div>
                                </div>
                            </div>
                        <!-- EJS TreeGrid Start -->
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   


<script type="text/javascript">
$(document).ready(function(){
	
	 $("[name=startDate]").val("${dashBoardDTO.startDate}");
     $("[name=endDate]").val("${dashBoardDTO.endDate}");
     
     SuggestUtil.bind('CUSTOMER', 'customCompany', null);
     SuggestUtil.bind('DEPARTMENT', 'departName', null);
	
	 CommonUtil.singleSelect('division',100);
	 CommonUtil.multiSelect('devType',100);
	 CommonUtil.multiSelect('state',120);
	 CommonUtil.multiSelect('lastCompany',100);
	 CommonUtil.singleSelect('dateType',100);
	 CommonUtil.multiSelect('rank',100);
	 CommonUtil.multiSelect('moldType1',100);
	 CommonUtil.multiSelect('moldCategory',100);
	 CommonUtil.multiSelect('making',100);
	 CommonUtil.multiSelect('dieCategory',100);
	 
	 CalendarUtil.dateInputFormat('startDate','endDate');
	 
	
	 if("${moldType}" == "review"){
         $("#review").attr("checked",true);
     }else if("${moldType}" == "product"){
    	 $("#product").attr("checked",true);
     }else if("${moldType}" == "mold"){
    	 $("#mold").attr("checked",true);
     }
	 
	 var type = "${moldType}";
	 var division = $("#division").val();
	 var startDate = "${dashBoardDTO.startDate}";
	 var endDate = "${dashBoardDTO.endDate}";
	 WorkTime.createPaingGrid(type,division);
	 treeGridResize("#SampleSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	 
	 $("#review").click(function(){
		 $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeReport",method:"post"}).submit();
	 });
	 
	 $("#product").click(function(){
         $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeReport",method:"post"}).submit();
     });
	 
	 $("#mold").click(function(){
		 $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeReport",method:"post"}).submit();
     });
});

function selectDeptRsltFunc(rsltArray){
    var name = "";
    var oid = "";
    var code = "";
    
    for(var i = 0; i < rsltArray.length ; i++){
         if(i != 0){
             oid += ",";
             name += ",";
             code += ",";
         }
        name += rsltArray[i].NAME;
        oid += rsltArray[i].OID;
        code += rsltArray[i].CODE;
    }
    
    $('[name=drawingDistDeptArray]').val(code);
    $('[name=departName]').val(name);
}

function selectPartNo(targetId){
    var pType = "";
    if ( targetId == "dieNo" ) {
        pType = "D";
    }
    else if ( targetId == "partNo3" ) {
        pType = "P";
    }
    else {
        pType = "A"
    }
    
    _callBackFuncTargetId = targetId;
    showProcessing();
    SearchUtil.selectPart("callBackFuncPartPopup", "pType=" + pType);

}

var _callBackFuncTargetId;
function callBackFuncPartPopup(selectedPartAry){
    if ( typeof selectedPartAry != "undefined" && selectedPartAry.length > 0) {
        var isAppend = "Y";
        acceptPartNo(_callBackFuncTargetId, selectedPartAry, isAppend);
    }
}

function acceptPartNo(targetId, arrObj, isAppend) {
    var partNoArr = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtpart oid       // [1] - part number    // [2] - part name
        // [3] - part version     // [4] - part type      // [5] - die number
        // [6] - die name         // [7] - die cnt
        var infoArr = arrObj[i];
        partNoArr[i] = infoArr[1];
    }

    var tmpNo = new Array();
    var tmpName = new Array();
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
        tmpNo = $.merge($("#"+targetId).val().split(", "), partNoArr);
        tmpNo = $.unique(tmpNo.sort());
    }
    else {
        tmpNo = partNoArr.sort();
    }

    $("#"+targetId).val(tmpNo.join(", "));
}
function clearPartNo(targetId) {
  $("#" + targetId).val("");
}

function dieCategoryChange(){
	var dieCategoryType = $("#moldType").val();
	$("#dieCategory").empty().data('options');
    if ( dieCategoryType == "Mold" ) {
        numCodeAjax("MOLDPRODUCTSTYPE", "MOL100", "dieCategory");
    }
    else if ( dieCategoryType == "Press" ) {
        numCodeAjax("MOLDPRODUCTSTYPE", "MOL200", "dieCategory");
    }
    else {
        numCodeAjax("MOLDPRODUCTSTYPE", "MOL100", "dieCategory");
        numCodeAjax("MOLDPRODUCTSTYPE", "MOL200", "dieCategory");
    }
    $("#dieCategory").multiselect("refresh"); 
}

function numCodeAjax(codeType, code, targetId) {
    $.ajax( {
        url : "/plm/servlet/e3ps/NumberCodeAjax",
        type : "POST",
        data : {codeType:codeType, code:code},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.numCode, function() {
                $("#"+targetId).append("<option value='"+this.code+"' vendercode='"+this.vendercode+"'>"+ this.value +"</option>");
            });
        }
    });
}
</script>