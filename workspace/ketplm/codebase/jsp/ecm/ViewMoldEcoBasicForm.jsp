<%@page import="e3ps.ecm.beans.EcmSearchHelper"%>
<%@page import="e3ps.ecm.beans.EcmUtil" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
<!--
.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.headerLock2 {
  position: relative;
  top:expression(document.getElementById("div_scroll2").scrollTop);
  z-index:99;
 }

 .headerLock3 {
  position: relative;
  top:expression(document.getElementById("div_scroll3").scrollTop);
  z-index:99;
 }
-->
</style>

<script type="text/javascript">
$(document).ready(function(){
    var strHTMLCode = document.forms[0].webEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);

    var strHTMLCode1 = document.forms[0].webEditor1.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode1, false, 1);

})
</script>
<%
String rdoDevYn[] = {"checked", ""};
String rdoDivisionFlag[] = {"checked", ""};

if(ketMoldChangeOrderVO.getTotalCount() > 0) {
    if("P".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getDevYn())) {
        rdoDevYn[0] = "";
        rdoDevYn[1] = "checked";
    }
    if("E".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getDivisionFlag())) {
        rdoDivisionFlag[0] = "";
        rdoDivisionFlag[1] = "checked";
    }
}
%>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td  class="tab_btm2"></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <col width="110"><col width="270"><col width="110"><col width="200">
              <%//1line%>
              <tr>
                <td width="" class="tdblueL">ECO No</td>
                <td class="tdwhiteL"><%=ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoId()%>&nbsp;</td>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00607") %><%--개발/양산구분--%></td>
                <td width="" class="tdwhiteL0"><%=ketMoldChangeOrderVO.getDevYnName()%>&nbsp;</td>
                </tr>
                <%//2line%>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td colspan="3" class="tdwhiteL0"
><%if(ketMoldChangeOrderVO.getTotalCount() > 0) {out.print(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoName());}%>&nbsp;</td>
              </tr>
              <%//3line%>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
                <td width="" class="tdwhiteL"><%=ketMoldChangeOrderVO.getDivisionFlagName()%>&nbsp;</td>
                <td width="" class="tdblueL">Project No</td>
                <%
                String porjectOid = StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getProjectOid());
                if("".equals(porjectOid)) {%>
                <td width="" class="tdwhiteL0">-</td>
                <%} else {%>
                <td width="" class="tdwhiteL0"><a href="#" onfocus="this.blur();"
onclick="javascript:openProject('<%=ketMoldChangeOrderVO.getProjectNo()%>');"><%=ketMoldChangeOrderVO.getProjectNo()%>/<%=ketMoldChangeOrderVO.getProjectName()%></a>&nbsp;</td>
                <%}%>
                </tr>
                <%//4line%>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                <td width="" class="tdwhiteL"
><%if(ketMoldChangeOrderVO.getTotalCount() > 0) {out.print(ketMoldChangeOrderVO.getOrgName());}%>&nbsp;</td>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="" class="tdwhiteL0"><%if(ketMoldChangeOrderVO.getTotalCount() > 0) {out.print(ketMoldChangeOrderVO.getCreatorName());}%>&nbsp;</td>
                </tr>
                <%//5line%>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td width="" class="tdwhiteL"
><%if(ketMoldChangeOrderVO.getTotalCount() > 0) {out.print(ketMoldChangeOrderVO.getCreateDate());}%>&nbsp;</td>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                <td width="" class="tdwhiteL0"
><%if(ketMoldChangeOrderVO.getTotalCount() > 0) {out.print(ketMoldChangeOrderVO.getUpdateDate());}%>&nbsp;</td>
                </tr>
                <%//6line%>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                <td width="" class="tdwhiteL"><%=StringUtil.checkReplaceStr(ketMoldChangeOrderVO.getApprovalName(),"-") %></td>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                <td width="" class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ketMoldChangeOrderVO.getApprovalDate(),"-") %></td>
                </tr>
                <%//7line%>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
                <td width="" class="tdwhiteL0" colspan="3"><a href="javascript:viewHistory('<%=CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder())%>');" onfocus="this.blur();"><%=ketMoldChangeOrderVO.getProgressStateName()%></a>&nbsp;</td>
                </tr>
                <%//8line%>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02121") %><%--연계 ECR 정보--%></td>
                  <td colspan="3" class="tdwhiteL0">
                    <table width="100%" cellpadding="0" cellspacing="0" id="relEcrTable">
                      <tr>
                        <!-- td width="40" class="tdgrayM">No</td -->
                        <td width="100" class="tdgrayM">ECR No</td>
                        <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
                        <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                        <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                        <td width="100" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                      </tr>
                    <!-- /table>
                    <div style="height=68;width=100%;overflow-x:hidden;overflow-y:auto;">
                    <table width="100%" cellpadding="0" cellspacing="0" id="relEcrTable" style="table-layout:fixed" class="table_border"  -->

                      <%
                      if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                          ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoEcrLinkVOList = ketMoldChangeOrderVO.getKetMoldEcoEcrLinkVOList();
                          if(ketMoldEcoEcrLinkVOList == null) {
                              size = 0;
                          } else {
                              size = ketMoldEcoEcrLinkVOList.size();
                          }
                          KETMoldEcoEcrLinkVO ketMoldEcoEcrLinkVO = null;
                          for ( int i = 0 ; i<size; i++ ) {
                              ketMoldEcoEcrLinkVO = (KETMoldEcoEcrLinkVO)ketMoldEcoEcrLinkVOList.get(i);
                      %>
                        <tr>
                          <!-- td width="40" class="tdwhiteM"><%=(i+1)%></td -->
                          <td width="100" class="tdwhiteM"><a href="javascript:viewRelEcr('<%=ketMoldEcoEcrLinkVO.getRelEcrOid()%>');" onfocus="this.blur();"><%=ketMoldEcoEcrLinkVO.getRelEcrId()%></a>
                          <input type='hidden' name='relEcrOid' value='<%=ketMoldEcoEcrLinkVO.getRelEcrOid()%>'>
                          <input type='hidden' name='relEcrLinkOid' value='<%=ketMoldEcoEcrLinkVO.getRelEcrLinkOid()%>'></td>
                          <td width="180" class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ketMoldEcoEcrLinkVO.getRelEcrName()) %>"><div class="ellipsis" style="width:340px;"><nobr><%=ketMoldEcoEcrLinkVO.getRelEcrName()%></nobr></div></td>
                          <td width="100" class="tdwhiteM">&nbsp;<%=ketMoldEcoEcrLinkVO.getCreateDeptName()%>&nbsp;</td>
                          <td width="90" class="tdwhiteM">&nbsp;<%=ketMoldEcoEcrLinkVO.getCreatorName()%>&nbsp;</td>
                          <td width="" class="tdwhiteM0">&nbsp;<%=ketMoldEcoEcrLinkVO.getApproveDate()%>&nbsp;</td>
                        </tr>
                      <%
                          }
                      }
                      %>
                    </table>
                    <!-- /div>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table -->
                  </td>
                </tr>
                <%//9line%>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02122") %><%--연계 제품ECO 정보--%></td>
                  <td colspan="3" class="tdwhiteL0">
                    <table width="100%" cellpadding="0" cellspacing="0" id="relProdEcoTable">
                      <tr>
                        <!-- td width="40" class="tdgrayM">No</td -->
                        <td width="100" class="tdgrayM">ECO No</td>
                        <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
                        <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                        <td width="75" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                        <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                        <td width="75" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td>
                      </tr>
                    <!-- /table>
                    <div style="height=68;width=100%;overflow-x:hidden;overflow-y:auto;">
                    <table width="100%" cellpadding="0" cellspacing="0" id="relProdEcoTable" class="table_border">
                      <col width='41'><col width='101'><col width='135'><col width='100'><col width='75'><col width='80'><col width='75' -->
                      <%
                      if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                          ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoProdEcoLinkVOList = ketMoldChangeOrderVO.getKetMoldEcoProdEcoLinkVOList();
                          if(ketMoldEcoProdEcoLinkVOList == null) {
                              size = 0;
                          } else {
                              size = ketMoldEcoProdEcoLinkVOList.size();
                          }
                          KETMoldEcoEcrLinkVO ketMoldEcoProdEcoLinkVO = null;
                          for ( int i = 0 ; i<size; i++ ) {
                              ketMoldEcoProdEcoLinkVO = (KETMoldEcoEcrLinkVO)ketMoldEcoProdEcoLinkVOList.get(i);
                      %>
                        <tr>
                          <!-- td class="tdwhiteM"><%=(i+1)%></td -->
                          <td class="tdwhiteM"><a href="javascript:viewRelProdEco('<%=ketMoldEcoProdEcoLinkVO.getRelEcrOid()%>');"><%=ketMoldEcoProdEcoLinkVO.getRelEcrId()%></a>
                          <input type='hidden' name='relProdEcoOid' value='<%=ketMoldEcoProdEcoLinkVO.getRelEcrOid()%>'>
                          <input type='hidden' name='relProdEcoLinkOid' value='<%=ketMoldEcoProdEcoLinkVO.getRelEcrLinkOid()%>'></td>
                          <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ketMoldEcoProdEcoLinkVO.getRelEcrName()) %>"><div class="ellipsis" style="width:340px;"><nobr><%=ketMoldEcoProdEcoLinkVO.getRelEcrName()%></nobr></div></td>
                          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getCreateDeptName()%>&nbsp;</td>
                          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getCreatorName()%>&nbsp;</td>
                          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getApproveDate()%>&nbsp;</td>
                          <td class="tdwhiteM0">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getSecureBudgetYn()%>&nbsp;</td>
                        </tr>
                      <%
                          }
                      }
                      %>
                    </table>
                    <!-- /div>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table -->
                  </td>
                </tr>
                <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%></td>
                  <td colspan="3" class="tdwhiteL0">
                    <!-- div id="div_scroll2" style="width=100%;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
                        <table width="100%" cellpadding="0" cellspacing="0">
                            <tr class="">
                              <!-- td width="40"  class="tdgrayM">No</td -->
                              <td width="85"  class="tdgrayM">Die No</td>
                              <td width=""    class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                              <!-- td width="90"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td -->
                              <td width="45"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                              <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
                              <td width="112" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td>
                             </tr>

                    <%
                    if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                        ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeOrderVO.getKetMoldECOPartLinkVOList();
                        size = ketMoldECOPartLinkVOList.size();
                        KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
                        for ( int i = 0 ; i<size; i++ ) {
                            ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO)ketMoldECOPartLinkVOList.get(i);
                    %>
                      <tr>
                        <!-- td class="tdwhiteM"><%=(i+1)%></td -->
                        <td class="tdwhiteM">
                          <a href="javascript:viewRelPart('<%=EcmUtil.getPartOid(ketMoldECOPartLinkVO.getRelPartNumber(), ketMoldECOPartLinkVO.getRelPartRev()) %>');"><%=ketMoldECOPartLinkVO.getRelPartNumber() %></a>&nbsp;
                          <!-- a href="javascript:viewRelPart('<%=ketMoldECOPartLinkVO.getRelPartOid()%>');"><%=ketMoldECOPartLinkVO.getRelPartNumber()%></a -->
                          <input type="hidden" name="relPartNumber" value="<%=ketMoldECOPartLinkVO.getRelPartNumber() %>">  
                        </td>
                        <td class="tdwhiteL"><span title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ketMoldECOPartLinkVO.getRelPartName()) %>" style="width:145;overflow:hidden; text-overflow:ellipsis;white-space:nowrap;"><%=ketMoldECOPartLinkVO.getRelPartName()%>&nbsp;</span></td>
                        <!-- td class="tdwhiteM"><%=EcmSearchHelper.manager.getRelatedPartNo(ketMoldECOPartLinkVO.getRelPartNumber())%>&nbsp;</td -->
                        <td class="tdwhiteM"><%=ketMoldECOPartLinkVO.getRelPartRev()%>&nbsp;</td>
                        <td class="tdwhiteR"><%=ketMoldECOPartLinkVO.getExpectCost()%>&nbsp;</td>
                        <%
                        if( ketMoldECOPartLinkVO.getRelPartNumber().charAt(1) == 'T' )
                        {
                        %>
                        <td width="" class="tdwhiteM0">&nbsp;<input type='hidden' name='secureBudgetYn' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYn()%>'></td>
                        <%
                        }else{
                        %>
                        <td width="" class="tdwhiteM0"><%=ketMoldECOPartLinkVO.getSecureBudgetYnName()%>&nbsp;<input type='hidden' name='secureBudgetYn' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYn()%>'></td>
                        <%} %>
                      </tr>
                    <%
                        }
                    }
                    %>
                    </table>
                    <!-- /div>
                    <table border="0" cellspacing="0" cellpadding="0" width="600">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table -->
                  </td>
                </tr>
                <tr>
                  <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
                  <td width="" class="tdwhiteL">
                      <a href="#"></a><%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getProdVendorName()));}%>&nbsp;</td>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00187") %><%--ECO 담당자--%></td>
                <td width="" class="tdwhiteL0"><%=ketMoldChangeOrderVO.getEcoWorkerName()%>&nbsp;</td>
                </tr>
                </tr>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%></td>
                <td colspan="3" class="tdwhiteL0"><%=StringUtil.checkNull(ketMoldChangeOrderVO.getChangeReasonName())%><%if(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherReasonDesc()).length()>0){ %>(<%=ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherReasonDesc()%>)<%} %></td>
                </tr>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01786") %><%--생산성향상유형--%></td>
                <td colspan="3" class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ketMoldChangeOrderVO.getIncreaseProdTypeName(),"-")%><%if(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherIncreaseProdType()).length()>0){ %>(<%=ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherIncreaseProdType()%>)<%} %></td>
                </tr>
                <tr>
                    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "05153") %><%--변경 전--%></td>
                    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "05154") %><%--변경 후--%></td>
                </tr>
				<tr>
									
					<!-- 이노디터 JS Include Start -->
					<script type="text/javascript">
					    //<![CDATA[
					
					    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
					    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
					    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
					    var g_arrSetEditorArea = new Array();
					    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
					    g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER1";// 이노디터를 위치시킬 영역의 ID값 설정
					
					    //]]>
					</script>
					<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
					<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui_half.js"></script>
					<script type="text/javascript">
					    //<![CDATA[
					
					    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
					
					    // Skin 재정의
					    //g_nSkinNumber = 0;
					
					    // 크기, 높이 재정의
					    g_nEditorWidth = 460;
					    g_nEditorHeight = 200;
					
					    // 메뉴바 설정
					    g_bCustomize_MenuBar_Display = false;
					    // 1번 툴바 설정
					    g_bCustomize_ToolBar1_Display = false;
					    // 2번 툴바 설정
					    g_bCustomize_ToolBar2_Display = false;
					    // 3번 툴바 설정
					    g_bCustomize_ToolBar3_Display = false;
					    // 탭바 설정
					    g_bCustomize_TabBar_Display = false;
					    // 조회모드
					    g_nCustomFormEditMode = parseInt('2');
				        
				        
				        // 편집창의 focus/blur 이벤트 발생 후 추가적인 처리를 위한 함수 호출 설정
				        // 이노디터를 호출하는 곳에서 해당 함수가 정의되어 있어야 함
				        g_strCustomFocusEventFunction = "fnTestFocusEventFunction";
				    
				        //]]>
				    </script>
				    <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
				    
				    <script language=javascript>
				    var isBlur = 0;
				    // 고객사에서 정의한 함수 - focus 이벤트 발생시 호출되는 함수
				    // 매개변수 1,2 값은 자동으로 이노디터에서 넘겨줌
				    // 매개변수 1 - 에디터번호
				    // 매개변수 2 - focus 인 경우는 1 리턴, 아닌 경우 0 리턴
				    function fnTestFocusEventFunction(EditNumber, FocusYN) {
				        
				        if(isBlur >= 2) {
				            
				            // 이노디터 제공함수 - 편집창모드 true, 소스창모드 false 리턴
				            if(1 == FocusYN) {
				                fnEventHandler_OnFocus(EditNumber);
				            
				            } else {
				                fnEventHandler_OnBlur(EditNumber);
				                
				            }
				            
				        } else {
				            //document.forms[0].ecr_desc.focus();
				            isBlur++;
				        }
				    }
				    
				    
				    //click event에 대한 Handler
				    function fnEventHandler_OnFocus(EditNumber) {
				        fnResizeEditor(0, 460, 600);
				        fnResizeEditor(1, 460, 600);
				    
				    }
				    function fnEventHandler_OnBlur(EditNumber) {
				        fnResizeEditor(0, 460, 200);
				        fnResizeEditor(1, 460, 200);
				        
				    }
				    </script>
				    
				    <!-- 이노디터 JS Include End -->
				  
				    <td colspan="2" class="tdwhiteL">
				    
				    
				                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
				                  <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor %></textarea> 
				                  <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=webEditorText %></textarea> 
				                  <!-- Editor Area Container : Start -->
				                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
				                  <div id="EDITOR_AREA_CONTAINER"></div> 
				                  <!-- Editor Area Container : End -->
				                  
				                  
				    </td>
				    <td colspan="2" class="tdwhiteL0">
				    
				    
				                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
				                  <textarea name="webEditor1" rows="0" cols="0" style="display: none"><%=webEditor1 %></textarea> 
				                  <textarea name="webEditorText1" rows="0" cols="0" style="display: none"><%=webEditorText1 %></textarea> 
				                  <!-- Editor Area Container : Start -->
				                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
				                  <div id="EDITOR_AREA_CONTAINER1"></div> 
				                  <!-- Editor Area Container : End -->
				                  
				                  
				    </td>            
				</tr>                    
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                  <td colspan="3" class="tdwhiteL0">
                    <table width="100%" cellpadding="0" cellspacing="0" id="filetable">
                      <tr>
                        <!-- td width="40" class="tdgrayM">No</td -->
                        <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                      </tr>
                    <!-- /table>
                    <div style="height=68;width=600;overflow-x:hidden;overflow-y:auto;">
                    <table width="600" cellpadding="0" cellspacing="0" id="filetable" class="table_border">
                    <col width="40"><col width="" -->
                    <%
                    Vector moldEcoAttacheVec = new Vector();
                    ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)ketMoldChangeOrderVO.getKetMoldChangeOrder() );
                    moldEcoAttacheVec = ContentUtil.getSecondaryContents(holder);
                    int max = moldEcoAttacheVec.size();

                    ContentInfo cntInfo = null;
                    ContentItem ctf = null;
                    String appDataOid = "";
                    String url = "";

                    for ( int j = 0 ; j<max; j++ ) {
                        cntInfo = (ContentInfo)moldEcoAttacheVec.elementAt(j);
                          ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
                        appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();

                        //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                        url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                        url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
                    %>
                    <tr>
                      <!-- td width="" class="tdwhiteM"><%=(j+1)%></td -->
                      <td width="" class="tdwhiteL0"><%=url%></td>
                    </tr>
                    <%
                    }
                    %>
                    </table>
                    <!-- /div -->
                 </td>
              </tr>
              </table>
