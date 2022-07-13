<%@page import=" e3ps.common.util.CommonUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="wt.org.*,wt.session.SessionHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%WTUser user = (WTUser) SessionHelper.manager.getPrincipal();%>
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script src="/plm/extcore/js/dms/listProjectDocument.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        
    	$("[name=projectDocType]").on("click", function(){
    		if($(this).index() == 0){
                $("[name=projectDocType]").eq(0).prop("checked", true);
                for(var i=1; i < $("[name=projectDocType]").length; i++){
                    $("[name=projectDocType]").eq(i).attr("checked", false);
                }
    		}else{
    			$("[name=projectDocType]").eq(0).attr("checked", false);
    		}
    	});
    	
        // Combo Box
        CommonUtil.multiSelect('divisionCode', 100);
        CommonUtil.multiSelect('state', 100);
        CommonUtil.multiSelect('documentQuality', 100);
        CommonUtil.singleSelect('buyerSummit', 100);
        CommonUtil.singleSelect('version', 100);

        // suggest
        SuggestUtil.bind('USER', 'creatorName', 'creator');
        SuggestUtil.bind('CUSTOMER', 'subcontractorTxt', 'subcontractor');
        SuggestUtil.bind('PARTNO', 'partNo');

        // Calener field 설정
        CalendarUtil.dateInputFormat('createDateFrom', 'createDateTo');
        // default 한달 설정
        $('[name=createDateFrom]').val(predate);
        $('[name=createDateTo]').val(postdate);

        // grid rendering
        ProjectDocument.createQualityPaingGrid();

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
    
</script>
<form name="ProjectDocumentFrm" method="POST">
  <table style="width: 100%; height: 100%;">
    <tr>
      <td valign="top">
        <table style="width: 100%;">
          <tr>
            <td>
              <table style="width: 100%; height: 28px;">
                <tr>
                  <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01">품질표준문서 검색</td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01406")%><%--문서관리--%>
                    <img src="/plm/portal/images/icon_navi.gif">품질표준문서 검색</td>
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
                  <%if(!user.getName().startsWith("kplus2")){ %>   
                  <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ProjectDocument.goQualityCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310")%><%--등록--%>
                        </a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <%}} %>
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
            <td colspan="3" class="tdwhiteL0">
                <input type="checkbox" name="projectDocType" value="PD303066,PD303072,PD303001,PD303017,PD303034,PD303046,PD303047" checked>전체
                <input type="checkbox" name="projectDocType" value="PD303066">DFMEA
                <input type="checkbox" name="projectDocType" value="PD303072">PFMEA
                <input type="checkbox" name="projectDocType" value="PD303001">검사기준서
                <input type="checkbox" name="projectDocType" value="PD303017">관리계획서
                <input type="checkbox" name="projectDocType" value="PD303034">설비점검기준서
                <input type="checkbox" name="projectDocType" value="PD303046">작업표준서
                <input type="checkbox" name="projectDocType" value="PD303047">제조공정도
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420")%><%--문서번호--%></td>
            <td class="tdwhiteL""><input type="text" id="documentNo" name="documentNo" class="txt_field" style="width: 270px;" value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
            <td class="tdwhiteL0""><ket:select id="divisionCode" name="divisionCode" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="DIVISIONTYPE" /></td>
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
              href="javascript:SearchUtil.selectOneUser('creator','creatorName');"
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
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00830")%><%--고객 제출자료--%></td>
            <td class="tdwhiteL"><ket:select id="buyerSummit" name="buyerSummit" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="BUYERSUMMIT" value="671978938" /></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%></td>
            <td class="tdwhiteL0"><input type="text" name="subcontractorTxt" class="txt_field" style="width: 215px"> <input type="hidden" name="subcontractor"> <a
              href="javascript:SearchUtil.selectOneSubContractor('subcontractor','subcontractorTxt');"
            > <img src="/plm/portal/images/icon_5.png" border="0">
            </a> <a href="javascript:CommonUtil.deleteValue('subcontractor','subcontractorTxt');"> <img src="/plm/portal/images/icon_delete.gif" border="0">
            </a></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03104")%><%--프로젝트번호--%></td>
            <td colspan="3" class="tdwhiteL0">
              <input type="radio" name="pjtType" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%> 
              <input type="radio" name="pjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
              <input type="radio" name="pjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%>
              <input type="radio" name="pjtType" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%> 
              <input type="text" id="pjtNo" name="pjtNo" class="txt_field" style="width: 220px;" value="" > 
              <a href="javascript:ProjectDocument.selProjectNo()"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('pjtNo','pjtNo');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03085")%><%--Project Name--%></td>
            <td class="tdwhiteL"><input type="text" id="pjtName" name="pjtName" class="txt_field" style="width: 270px" value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
            <td class="tdwhiteL0"><ket:select id="version" name="version" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="VERSION" value="671979013" /></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품번호--%></td>
            <td class="tdwhiteL"><input type="text" name="partNo" class="txt_field" style="width: 100%" value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03030")%><%--품질확보절차--%></td>
            <td class="tdwhiteL0"><ket:select id="documentQuality" name="documentQuality" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="DOCUMENTQUALITY" value="" /></td>
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
