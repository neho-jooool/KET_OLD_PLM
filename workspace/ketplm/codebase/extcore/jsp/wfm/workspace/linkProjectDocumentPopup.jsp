<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/wfm/workspace/linkProjectDocumentPopup.js"></script>
<script type="text/javascript">

$(document).ready(function(){

    //tab binding
    var tab = CommonUtil.tabs('tabs');
    
    // Combo Box
    CommonUtil.multiSelect('state1', 100);
    CommonUtil.singleSelect('version1', 100);

    // suggest
    SuggestUtil.bind('PROJECTDOCTYPE', 'documentCategoryTxt', 'documentCategory');
    SuggestUtil.bind('PARTNO', 'partNo');

    // Calener field 설정
    CalendarUtil.dateInputFormat('createDateFrom1', 'createDateTo1');

    // grid rendering
    MyDocument.createPaingGrid();
    
    // Project 구분 radio click event
    $("[name=pjtType1]").click(function() {
        CommonUtil.deleteValue('pjtNoTxt1', 'pjtNo1');

        if ("" == $(this).val()) {
            $("[name=pjtType1]").unbind();
        } else if ("제품" == $(this).val()) {
            SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo1');
        } else if ("검토" == $(this).val()) {
            SuggestUtil.bind('REVIEWPROJNO', 'pjtNo1');
        } else if ("금형" == $(this).val()) {
            SuggestUtil.bind('DIENO', 'pjtNo1');
        }
    });

    // Combo Box
    CommonUtil.multiSelect('divisionCode', 100);
    CommonUtil.multiSelect('state2', 100);
    CommonUtil.multiSelect('documentQuality', 100);
    CommonUtil.singleSelect('buyerSummit', 100);
    CommonUtil.singleSelect('version2', 100);

    // suggest
    SuggestUtil.bind('PROJECTDOCTYPE', 'projectDocTypeTxt', 'projectDocType');
    SuggestUtil.bind('USER', 'creatorName', 'creator');
    SuggestUtil.bind('CUSTOMER', 'subcontractorTxt', 'subcontractor');
    SuggestUtil.bind('PARTNO', 'partNo');

    // Calener field 설정
    CalendarUtil.dateInputFormat('createDateFrom2', 'createDateTo2');
    
    // grid rendering
    ProjectDocument.createPaingGrid();

    // Project 구분 radio click event
    $("[name=pjtType2]").click(function() {
        CommonUtil.deleteValue('pjtNoTxt2', 'pjtNo2');

        if ("" == $(this).val()) {
            $("[name=pjtType2]").unbind();
        } else if ("제품" == $(this).val()) {
            SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo2');
        } else if ("검토" == $(this).val()) {
            SuggestUtil.bind('REVIEWPROJNO', 'pjtNo2');
        } else if ("금형" == $(this).val()) {
            SuggestUtil.bind('DIENO', 'pjtNo2');
        }
    });

    // Enter 검색
    $("form[name=frm]").keypress(function(e) {
        if (e.which == 13) {
            MyDocument.search();
            return false;
        }
    });
    $("form[name=ProjectDocumentFrm]").keypress(function(e) {
        if (e.which == 13) {
            ProjectDocument.search();
            return false;
        }
    });
    treeGridResize("#myDocumentGrid","#myDocumentGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    
    $(window).resize(function(){
    	$('#projectDocumentGridDiv').height($('#myDocumentGridDiv').height()-200);                   
    });

    //MyDocument.search();
    //ProjectDocument.search();

});
</script>
<%
	String returnTargetUrl = request.getParameter("returnTargetUrl");
   Object outputoidObj = request.getParameterValues("outputoid");
   if(outputoidObj instanceof String[]){
       String[] outputoids = (String[]) outputoidObj;
       for(String outputoid : outputoids){
%>
<input type="hidden" name="outputoid" value="<%= outputoid %>">
<%     
       }
   }
   else{
       String outputoid = (String) outputoidObj;
%>
<input type="hidden" name="outputoid" value="<%= outputoid %>">
<%            
   }
%>
<table style="width: 100%; height: 100%;">
  <tr>
    <td valign="top">
      <table style="width: 100%;">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table style="height: 28px;">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01719")%><!-- 산출물 등록 --></td>
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<div id="tabs">
  <ul>
    <li><a class="tabref" href="#tabs-1">My Document</a></li>
    <li><a class="tabref" href="#tabs-2"><%=messageService.getString("e3ps.message.ket_message", "00638")%><!-- 개발산출문서 --></a></li>
  </ul>
  <div id="tabs-1">
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
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyDocument.selectDocument();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyDocument.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
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
    <table style="width: 100%;">
      <tr>
        <td class="tab_btm2"></td>
      </tr>
    </table>
    <form name="frm">
<input type="hidden" name="returnTargetUrl" value="<%= returnTargetUrl %>">
    <table style="width: 100%;">
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
        <td colspan="3" class="tdwhiteL0">
          <input type="text" name="documentCategoryTxt" value="${categoryName }" class="txt_field" style="width: 220px"> 
          <input type="hidden" name="documentCategory" value="${categoryCode }"><a href="javascript:SearchUtil.selectDevDocCategory(MyDocument.selectDocCategory);"><img src="/plm/portal/images/icon_5.png" border="0"></a>
          <a href="javascript:CommonUtil.deleteValue('documentCategoryTxt','documentCategory');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
      </tr>
      <tr>
        <td class="tdblueL""><%=messageService.getString("e3ps.message.ket_message", "01420")%><%--문서번호--%></td>
        <td class="tdwhiteL"><input type="text" id="documentNo" name="documentNo" class="txt_field" style="width: 220px;" value=""></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%></td>
        <td class="tdwhiteL0"><input type="text" id="documentName" name="documentName" class="txt_field" style="width: 220px" value=""></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
        <td class="tdwhiteL"><ket:select id="state1" name="state1" className="fm_jmp" style="width: 220px" multiple="multiple" lifeCycleState="KET_COMMON_LC" value="INWORK" /></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
        <td class="tdwhiteL0">
          <input type="text" name="createDateFrom1" class="txt_field" style="width: 80px;" value=""> 
          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateFrom1');" style="cursor: hand;" > ~ 
          <input type="text" name="createDateTo1" class="txt_field" style="width: 80px;">
          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateTo1');" style="cursor: hand;"></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00397")%><%--Project No--%></td>
        <td colspan="3" class="tdwhiteL0">
          <input type="radio" name="pjtType1" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%> 
          <input type="radio" name="pjtType1" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
          <input type="radio" name="pjtType1" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%>
          <input type="radio" name="pjtType1" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%> 
          <input type="text" id="pjtNo1" name="pjtNo1" class="txt_field" style="width: 220px;" value="" > 
          <a href="javascript:ProjectDocument.selProjectNo()"><img src="/plm/portal/images/icon_5.png" border="0"></a>
          <a href="javascript:CommonUtil.deleteValue('pjtNo1','pjtNo1');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
        <td class="tdwhiteL"><ket:select id="version1" name="version1" className="fm_jmp" style="width: 220px;" multiple="multiple" codeType="VERSION" value="671979013" /></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품번호--%></td>
        <td class="tdwhiteL0"><input type="text" id="partNo" name="partNo" class="txt_field" style="width: 220px;" value=""></td>
      </tr>
    </table>
    </form>
    <table style="width: 100%;">
      <tr>
        <td class="space5"></td>
      </tr>
    </table> <!-- EJS TreeGrid Start -->
    <div class="container-fluid">
      <div class="row-fluid">
        <div id="myDocumentGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
      </div>
    </div> <!-- EJS TreeGrid End -->
  </div>
  <div id="tabs-2">
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
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ProjectDocument.selectDocument();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ProjectDocument.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
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
    <table style="width: 100%;">
      <tr>
        <td class="tab_btm2"></td>
      </tr>
    </table>
    <form name="ProjectDocumentFrm">
    <table style="width: 100%;">
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
        <td colspan="3" class="tdwhiteL0">
          <input type="text" name="projectDocTypeTxt" value="${categoryName }" class="txt_field" style="width: 220px"><input type="hidden" name="projectDocType" value="${categoryCode }">
          <a href="javascript:SearchUtil.selectDevDocCategory(ProjectDocument.setDevDocCategory);"><img src="/plm/portal/images/icon_5.png" border="0"></a>
          <a href="javascript:CommonUtil.deleteValue('projectDocTypeTxt','projectDocType');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
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
        <td class="tdwhiteL0"><ket:select id="state2" name="state2" className="fm_jmp" style="width: 270px;" multiple="multiple" lifeCycleState="KET_COMMON_LC" /></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431")%><%--작성자--%></td>
        <td class="tdwhiteL"><input type="text" name="creatorName" class="txt_field" style="width: 220px;"><input type="hidden" name="creator">
          <a href="javascript:SearchUtil.selectOneUser('creator','creatorName');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
          <a href="javascript:CommonUtil.deleteValue('creator','creatorName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
        <td class="tdwhiteL0">
          <input type="text" name="createDateFrom2" class="txt_field" style="width: 80px;" value="">
          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateFrom2');" style="cursor: hand;"> ~ 
          <input type="text" name="createDateTo2" class="txt_field" style="width: 80px;">
          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateTo2');" style="cursor: hand;"></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00830")%><%--고객 제출자료--%></td>
        <td class="tdwhiteL"><ket:select id="buyerSummit" name="buyerSummit" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="BUYERSUMMIT" value="671978938" /></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%></td>
        <td class="tdwhiteL0"><input type="text" name="subcontractorTxt" class="txt_field" style="width: 215px"> <input type="hidden" name="subcontractor"> 
          <a href="javascript:SearchUtil.selectOneSubContractor('subcontractor','subcontractorTxt');"> <img src="/plm/portal/images/icon_5.png" border="0"> </a> 
          <a href="javascript:CommonUtil.deleteValue('subcontractor','subcontractorTxt');"> <img src="/plm/portal/images/icon_delete.gif" border="0"> </a></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03104")%><%--프로젝트번호--%></td>
        <td colspan="3" class="tdwhiteL0">
          <input type="radio" name="pjtType2" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%> 
          <input type="radio" name="pjtType2" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
          <input type="radio" name="pjtType2" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%>
          <input type="radio" name="pjtType2" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%> 
          <input type="text" id="pjtNo2" name="pjtNo2" class="txt_field" style="width: 220px;" value="" > 
          <a href="javascript:ProjectDocument.selProjectNo()"><img src="/plm/portal/images/icon_5.png" border="0"></a>
          <a href="javascript:CommonUtil.deleteValue('pjtNo2','pjtNo2');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03085")%><%--Project Name--%></td>
        <td class="tdwhiteL"><input type="text" id="pjtName" name="pjtName" class="txt_field" style="width: 270px" value=""></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
        <td class="tdwhiteL0"><ket:select id="version2" name="version2" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="VERSION" value="671979013" /></td>
      </tr>
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품번호--%></td>
        <td class="tdwhiteL"><input type="text" name="partNo" class="txt_field" style="width: 100%" value=""></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03030")%><%--품질확보절차--%></td>
        <td class="tdwhiteL0"><ket:select id="documentQuality" name="documentQuality" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="DOCUMENTQUALITY" value="" /></td>
      </tr>
    </table>
    </form>
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
  </div>
</div>
