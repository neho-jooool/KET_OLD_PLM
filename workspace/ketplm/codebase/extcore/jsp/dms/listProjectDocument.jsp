<%@page import=" e3ps.common.util.CommonUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script src="/plm/extcore/js/dms/listProjectDocument.js?ver=0.6"></script>
<script type="text/javascript">
//==  [Start] 부품검색 팝업(PartNo)  == //
// mode = m / s
// pType=A(전체)/P(제품)/D(다이)/M(금형)
function selectPartNo(targetId){
    var pType = "";
    if ( targetId == "dieNo3" ) {
        pType = "D";
    }
    else if ( targetId == "partNo" ) {
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
//==  [End] 부품검색 팝업(PartNo)  == //
    $(document).ready(function() {

        // Combo Box
        CommonUtil.multiSelect('divisionCode', 100);
        CommonUtil.multiSelect('state', 100);
        CommonUtil.multiSelect('documentQuality', 100);
        CommonUtil.singleSelect('buyerSummit', 100);
        CommonUtil.singleSelect('version', 100);
        CommonUtil.singleSelect('dateRevision', 100);
        
        // suggest
        SuggestUtil.bind('PROJECTDOCTYPE', 'projectDocTypeTxt', 'projectDocType');
        SuggestUtil.bind('USER', 'creatorName', 'creator');
        SuggestUtil.bind('CUSTOMER', 'subcontractorTxt', 'subcontractor');
        SuggestUtil.bind('PARTNO', 'partNo');
        
        SuggestUtil.bind('DEPARTMENT', 'deptName');

        // Calener field 설정
        CalendarUtil.dateInputFormat('createDateFrom', 'createDateTo');
        // default 한달 설정
        $('[name=createDateFrom]').val(predate);
        $('[name=createDateTo]').val(postdate);

        // grid rendering
        ProjectDocument.createPaingGrid();
        
        treeGridResize("#projectDocumentGrid","#projectDocumentGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        // Project 구분 radio click event
        $("[name=pjtType]").click(function() {
            CommonUtil.deleteValue('pjtNoTxt', 'pjtNo');

            if ("" == $(this).val()) {
                $("[name=pjtType]").unbind();
            } else if ("제품" == $(this).val()) {
                SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
            } else if ("검토" == $(this).val()) {
                SuggestUtil.bind('REVIEWPROJNO', 'pjtNo');
            } else if ("금형" == $(this).val()) {
                SuggestUtil.bind('DIENO', 'pjtNo');
            }
        });

        // Enter 검색
        $("form[name=ProjectDocumentFrm]").keypress(function(e) {
            if (e.which == 13) {
                ProjectDocument.search();
                return false;
            }
        });
    });
    
    function setDevDocCategoryCallbackF(checkedNode){
        ProjectDocument.setDevDocCategory(checkedNode);
    }
    
    function setUser(attacheMembers){
        var infoArr = attacheMembers[0];
        $('[name=creator]').val(infoArr[0]);
        $('[name=creatorName]').val(infoArr[4]);
    }
    
    function setSubContractor(returnValue){
        $('[name=subcontractor]').val(returnValue[0][0]);
        $('[name=subcontractorTxt]').val(returnValue[0][2]);
    }
    
    function setProjectNo(returnValue){
        ProjectDocument.selProjectNo(returnValue);
    }
        
    function addDepartment(){
        var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?mode=s&invokeMethod=setDepartMent";
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=430,height=465";
        getOpenWindow2(url,'csList', 430, 465, opts);
     }
    
    function setDepartMent(rsltArrayObject){
    	var departName = rsltArrayObject[0].NAME;

        $('[name=deptName]').val(departName);
    }

</script>
<form name="ProjectDocumentFrm" method="POST" autocomplete="off">
  <table style="width: 100%; height: 100%;">
    <tr>
      <td valign="top">
        <table style="width: 100%;">
          <tr>
            <td>
              <table style="width: 100%; height: 28px;">
                <tr>
                  <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00639")%><%--개발산출문서 검색--%></td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01406")%><%--문서관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00639")%><%--개발산출문서 검색--%></td>
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
        <table style="width: 100%;">
          <tr>
            <td>&nbsp;</td>
            <td align="right">
              <table>
                <tr>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ProjectDocument.batchRevision();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04220")%><%--일괄개정--%>
                        </a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ProjectDocument.goCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310")%><%--등록--%>
                        </a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <%} %>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ProjectDocument.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%>
                        </a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ProjectDocument.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%>
                        </a></td>
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
        <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <table style="width: 100%;">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
            <td colspan="3" class="tdwhiteL0"><input type="text" name="projectDocTypeTxt" class="txt_field" style="width: 220px"> <input type="hidden" name="projectDocType" value="">
              <a href="javascript:SearchUtil.selectDevDocCategory('setDevDocCategoryCallbackF');"> <img src="/plm/portal/images/icon_5.png" border="0"></a> <a
              href="javascript:CommonUtil.deleteValue('projectDocTypeTxt','projectDocType');"
            ><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420")%><%--문서번호--%></td>
            <td class="tdwhiteL"><input type="text" id="documentNo" name="documentNo" class="txt_field" style="width: 270px;" value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
            <td class="tdwhiteL0"><ket:select id="divisionCode" name="divisionCode" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="DIVISIONTYPE" /></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%></td>
            <td class="tdwhiteL"><input type="text" id="documentName" name="documentName" class="txt_field" style="width: 270px" value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
            <td class="tdwhiteL0"><ket:select id="state" name="state" className="fm_jmp" style="width: 270px;" multiple="multiple" lifeCycleState="KET_COMMON_LC" /></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431")%><%--작성자--%></td>
            <td class="tdwhiteL"><input type="text" name="creatorName" class="txt_field" style="width: 220px;"> <input type="hidden" name="creator"> <a
              href="javascript:SearchUtil.selectOneUser('creator','creatorName','setUser');"
            > <img src="/plm/portal/images/icon_user.gif" border="0">
            </a> <a href="javascript:CommonUtil.deleteValue('creator','creatorName');"> <img src="/plm/portal/images/icon_delete.gif" border="0">
            </a></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
            <td class="tdwhiteL0"><input type="text" name="createDateFrom" class="txt_field" style="width: 80px;" value=""> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('createDateFrom');" style="cursor: hand;"
            > ~ <input type="text" name="createDateTo" class="txt_field" style="width: 80px;"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('createDateTo');" style="cursor: hand;"
            ></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425")%><%--작성부서--%></td>
            <td class="tdwhiteL"><input type="text" id="deptName" name="deptName" class="txt_field" style="width: 270px" value="">
            <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png"></a>
            <a href="javascript:CommonUtil.deleteValue('deptName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--접점고객--%></td>
            <td class="tdwhiteL0"><input type="text" name="subcontractorTxt" class="txt_field" style="width: 215px"> <input type="hidden" name="subcontractor"> <a
              href="javascript:SearchUtil.selectOneSubContractorPopUp('setSubContractor');"
            > <img src="/plm/portal/images/icon_5.png" border="0">
            </a> <a href="javascript:CommonUtil.deleteValue('subcontractor','subcontractorTxt');"> <img src="/plm/portal/images/icon_delete.gif" border="0">
            </a></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03104")%><%--프로젝트번호--%></td>
            <td class="tdwhiteL">
              <input type="radio" name="pjtType" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%> 
              <input type="radio" name="pjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
              <input type="radio" name="pjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%>
              <input type="radio" name="pjtType" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%> 
              <input type="text" id="pjtNo" name="pjtNo" class="txt_field" style="width: 220px;" value="" > 
              <a href="javascript:SearchUtil.selectProjectPopUp('setProjectNo')"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('pjtNo','pjtNo');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00830")%><%--고객 제출자료--%></td>
              <td class="tdwhiteL0"><ket:select id="buyerSummit" name="buyerSummit" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="BUYERSUMMIT" value="671978938" /></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03085")%><%--Project Name--%></td>
            <td class="tdwhiteL"><input type="text" id="pjtName" name="pjtName" class="txt_field" style="width: 270px" value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
            <td class="tdwhiteL0"><ket:select id="version" name="version" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="VERSION" value="671979013" /></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품번호--%></td>
            <td class="tdwhiteL"><input type="text" id="partNo" name="partNo" class="txt_field" style="width:70%"  value="<%=searchCondition.getString("partNo") %>">
                <a href="javascript:selectPartNo('partNo');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('partNo');"><img src="/plm/portal/images/icon_delete.gif"></a></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03030")%><%--품질확보절차--%></td>
            <td class="tdwhiteL0"><ket:select id="documentQuality" name="documentQuality" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="DOCUMENTQUALITY" value="" /></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09494")%><%--개정도래일--%></td>
            <td class="tdwhiteL0" colspan="3">
                <select name="dateRevision" id="dateRevision" style="width:270px;">
                    <option value="">선택</option>
                    <option value="0">지연</option>
                    <option value="1">1일전</option>
                    <option value="5">5일전</option>
                    <option value="10">10일전</option>
                    <option value="20">20일전</option>
                    <option value="30">30일전</option>
                </select>
            </td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="projectDocumentGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End -->
      </td>
    </tr>
  </table>
</form>
<!-- download target iframe -->
<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px solid #fff;"></iframe>
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<style type="text/css">
 .popupLayer{
  border:#E2EDF4;
  width:500px; height:400px; background:#FFF;
  position:absolute; top:10px; text-align:center;
  left:30%;top:100px;
}
.dim-layer {
  display: none;
  position: fixed;
  _position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 100;
}
.dim-layer .dimBg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #000;
  opacity: .2;
  filter: alpha(opacity=50);
}
</style>
<script>
$(document).ready(function(){
    window.layerClose = function(){
        $("#layerPop").hide();
    }
});
</script>
<div class="dim-layer" id="layerPop">
    <div class="dimBg"></div>
    <div class="popupLayer">
        <table style="width:100%;margin-top:10px;">
            <tr>
                <td>
                <span style="font-size:12px;font-weight:bold;">일괄개정이 실패하였습니다.</span>
                </td>
                <td style="width:70px;">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                            <a href="javascript:layerClose();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                        </td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>
        <table summary="" class="table-info fixed" style="margin:10px;margin-bottom:0px;width:480px;">
            <colgroup>
                <col width="50"/>
                <col width="120"/>
                <col width="280"/>
                <col width="17"/>
            </colgroup>
            <thead>
                <tr>
                    <td>번호</td>
                    <td>문서번호</td>
                    <td style="border-right:0px;">오류명</td>
                    <td>&nbsp;</td>
                </tr>
            </thead>
        </table>
        <div style="height:320px;overflow-y:scroll;overflow-x:hidden;margin:10px;margin-top:0px;width:480px;" class="contentLayer">
        <table summary="" class="table-info fixed" style="border-top:0px;">
            <colgroup>
                <col width="50"/>
                <col width="120"/>
                <col width="280"/>
            </colgroup>
            <tbody class="errorList">
            </tbody>
        </table>
        </div>
    </div>
</div>
