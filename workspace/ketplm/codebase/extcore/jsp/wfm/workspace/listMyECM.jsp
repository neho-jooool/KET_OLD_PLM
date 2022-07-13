<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    //단계구분 -  개발/양산구분
    parameter.clear();
    parameter.put("locale", messageService.getLocale());
    parameter.put("codeType", "DEVYN");
    List<Map<String, Object>> devynCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 설계변경사유
    parameter = new HashMap<String, Object>();
    parameter.clear();
    parameter.put("locale", messageService.getLocale());
    parameter.put("codeType", "PRODCHAGEREASON");
    List<Map<String, Object>> prodChangeReasonList = NumberCodeHelper.manager.getNumberCodeList(parameter);
%>
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script src="/plm/extcore/js/wfm/workspace/listMyECM.js"></script>
<script type="text/javascript">
    $(document).ready(function(e) {
        MyECM.createPaingGrid();
        CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');
        SuggestUtil.bind('PARTNO', 'partNo', 'partOid');
        
        // Project 구분 radio click event
        $("[name=searchPjtType]").click(function() {
            CommonUtil.deleteValue('searchPjtNoTxt', 'projectNo', 'projectOid');
            if("" == $(this).val()){
                $("[name=searchPjtNo]").unbind();
            }
            else if("제품" == $(this).val()){
                SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', 'projectOid');
            }
            else if("검토" == $(this).val()){
                SuggestUtil.bind('REVIEWPROJNO', 'projectNo', 'projectOid');
            }
            else if("금형" == $(this).val()){
                SuggestUtil.bind('DIENO', 'projectNo', 'projectOid');
            }
        });
        
        $("form[name=form01]").keypress(function(e) {
            //Enter key
            if ( e.which == 13 ) {
                MyECM.search();
                return false;
            }
        });
        
        $("#prodMoldCls").multiselect({
            minWidth: 180,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498")%><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500")%><%--전체해제--%>'
        });
        $("#devYn").multiselect({
            minWidth: 180,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498")%><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500")%><%--전체해제--%>'
        });
        // 설계변경사유
        $("#prodChangeReason").multiselect({
            minWidth: 180,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498")%><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500")%><%--전체해제--%>'
        });
        $("#sancStateFlag").multiselect({
            minWidth: 180,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498")%><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500")%><%--전체해제--%>'
        });
    });

    //프로젝트상세조회 팝업
    function popupProject(targetId) {

        var url = "/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P&modal=Y&mode=m"
        var selectedPartAry = window.showModalDialog(url, window, "help=no; resizable=no; status=no; scroll=yes; dialogWidth=1024px; dialogHeight:768px; center:yes");
        if (selectedPartAry != undefined && selectedPartAry.length > 0) {
            var isAppend = "Y";
            setProject(targetId, selectedPartAry, isAppend);
        }
    }
    function setProject(targetId, arrObj, isAppend) {
        var arrId = new Array();
        var arrNo = new Array();
        for ( var i = 0; i < arrObj.length; i++) {
            // [0] -  oid       // [1] - project number
            var infoArr = arrObj[i];
            arrId[i] = infoArr[0];
            arrNo[i] = infoArr[1];
        }

        var tmpId = new Array();
        var tmpNo = new Array();
        if ($("#" + targetId).val() != "" && isAppend == "Y") {
            tmpId = $.merge($("#projectOid").val().split(", "), arrId);
            tmpId = $.unique(tmpNo.sort());

            tmpNo = $.merge($("#" + targetId).val().split(", "), arrNo);
            tmpNo = $.unique(tmpNo.sort());
        } else {
            tmpId = arrId.sort();
            tmpNo = arrNo.sort();
        }

        $("#projectOid").val(tmpId.join(", "));
        $("#" + targetId).val(tmpNo.join(", "));
    }

    //부품번호 팝업
    function popupPart(targetId) {
        
        _callBackFuncTargetId = targetId;
        showProcessing();
        SearchUtil.selectPart("callBackFuncPartPopup");
    }
    
    var _callBackFuncTargetId;
    function callBackFuncPartPopup(selectedPartAry){
        if ( typeof selectedPartAry != "undefined" && selectedPartAry.length > 0) {
            var isAppend = "Y";
            acceptPartNo(_callBackFuncTargetId, selectedPartAry, isAppend);
        }
    }
    
    function acceptPartNo(targetId, arrObj, isAppend) {
        var partData = new Array();
        for ( var i = 0; i < arrObj.length; i++) {
            // [0] - wtpart oid       // [1] - part number    // [2] - part name
            // [3] - part version     // [4] - part type      // [5] - die number
            // [6] - die name         // [7] - die cnt
            var infoArr = arrObj[i];
            partData[i] = infoArr[1];
        }
        var tmpNo = new Array();
        if ($("#" + targetId).val() != "" && isAppend == "Y") {
            // ID 중복 제거
            tmpNo = $.merge($("#" + targetId).val().split(", "), partData);
            tmpNo = $.unique(tmpNo.sort());
        } else {
            tmpNo = partData.sort();
        }
        $("#" + targetId).val(tmpNo.join(", "));
    }
</script>
<form name="form01" method="post">
  <table style="width: 100%;" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top">
        <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table style="width: 100%;" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "05112")%><%--My ECM--%></td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435")%><%--작업공간--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "05112")%><%--My ECM--%></td>
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
                    <table>
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyECM.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a>
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
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyECM.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
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
          <colgroup>
            <col width="15%">
            <col width="35%">
            <col width="15%">
            <col width="35%">
          </colgroup>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품 번호--%></td>
            <td class="tdwhiteL">
              <input type="text" name="partNo" id="partNo" class="txt_field" value="" style="width: 250px">
              <input type="hidden" name="partOid" id="partOid">
              <a href="javascript:popupPart('partNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('partOid','partNo');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586")%><%--부품명--%></td>
            <td class="tdwhiteL0"><input type="text" name="partName" class="txt_field" value="" style="width: 98%"></td>
          </tr>
          <tr>
            <td class="tdblueL">Project No</td>
            <td class="tdwhiteL">
              <input type="radio" name="searchPjtType" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%>
              <input type="radio" name="searchPjtType" value="제품" ><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%>
              <input type="radio" name="searchPjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%>
              <input type="radio" name="searchPjtType" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%>
              <input type="text" name="projectNo" id="projectNo" class="txt_field" value="" style="width: 250px">
              <input type="hidden" name="projectOid" id="projectOid" value="">
              <a href="javascript:popupProject('projectNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('projectOid','projectNo');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00395")%><%--Project Name--%></td>
            <td class="tdwhiteL0"><input type="text" name="projectName" class="txt_field" value="" style="width: 98%"></td>
          </tr>
          <tr>
            <td class="tdblueL">No</td>
            <td class="tdwhiteL"><input type="text" id="searchEcmNo" name="searchEcmNo" class="txt_field" style="width: 98%;" value=""></td>
            <td class="tdblueL">제목</td>
            <td class="tdwhiteL0"><input type="text" id="searchTitle" name="searchTitle" class="txt_field" style="width: 98%;" value=""></td>
          </tr>
          <tr>
            <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
            <td class="tdwhiteL">
              <select name="prodMoldCls" id="prodMoldCls" class="fm_jmp" multiple="multiple">
                <option value="1"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%></option>
                <option value="2"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%></option>
            </select></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193")%><%--단계구분--%></td>
            <td class="tdwhiteL0">
              <select name="devYn" id="devYn" class="fm_jmp" multiple="multiple">
                <%
                    for (int i = 0; i < devynCode.size(); i++) {
                %>
                <option value="<%=devynCode.get(i).get("code")%>"><%=devynCode.get(i).get("value")%></option>
                <%
                    }
                %>
            </select></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
            <td class="tdwhiteL">
              <input type="text" name="createStartDate" id="createStartDate" class="txt_field" style="width: 70px" value="">
              <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createStartDate');" style="cursor: hand;">
              &nbsp;~&nbsp;
              <input type="text" name="createEndDate" id="createEndDate" class="txt_field" style="width: 70px" value="">
              <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createEndDate');" style="cursor: hand;"></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%></td>
            <td class="tdwhiteL0"><select name="sancStateFlag" id="sancStateFlag" class="fm_jmp" multiple="multiple">
                <option value="INWORK"><%=messageService.getString("e3ps.message.ket_message", "02441")%><%--작업중--%></option>
                <option value="UNDERREVIEW"><%=messageService.getString("e3ps.message.ket_message", "00732")%><%--검토중--%></option>
                <option value="APPROVED"><%=messageService.getString("e3ps.message.ket_message", "01996")%><%--승인완료--%></option>
                <option value="REJECTED"><%=messageService.getString("e3ps.message.ket_message", "01468")%><%--반려됨--%></option>
                <option value="REWORK"><%=messageService.getString("e3ps.message.ket_message", "02451")%><%--재작업--%></option>
            </select></td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table>  
           <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="listMyECMGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End -->
      </td>
    </tr>
  </table>
</form>