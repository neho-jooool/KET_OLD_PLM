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
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td  class="tab_btm2"></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <col width="130"><col width="248"><col width="130"><col width="220">
                <tr>
                  <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
                  <td colspan="3" class="tdwhiteL0"
                      ><input type="text" name="ecoName" class="txt_field"  style="width:99%" id="textfield" <%=readOnly%>
value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoName()));}%>"></td>
                </tr>
                <%
                String rdoDevYn[] = {"checked", ""};
                String rdoDivisionFlag[] = {"checked", ""};
                if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                    if("P".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getDevYn())) {//양산
                        rdoDevYn[0] = "";
                        rdoDevYn[1] = "checked";
                    }
                    if("E".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getDivisionFlag())) {
                        rdoDivisionFlag[0] = "";
                        rdoDivisionFlag[1] = "selected";
                    }
                }
                String cmbVendorFlag[] = {"selected", ""};
                if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                    if("o".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getVendorFlag())) {
                        cmbVendorFlag[0] = "";
                        cmbVendorFlag[1] = "selected";
                    }
                }
                %>
                <tr>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%><span class="red">*</span></td>
                <td width="" class="tdwhiteL"><input name="rdoDevYn" type="radio" <%=readOnly%> class="Checkbox" value="D" <%=rdoDevYn[0]%>>
                  <%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%>
                  <input name="rdoDevYn" type="radio" <%=readOnly%> class="Checkbox" value="P" <%=rdoDevYn[1]%>>
                  <%=messageService.getString("e3ps.message.ket_message", "02094") %><%--양산단계--%></td>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%><span class="red">*</span></td>
                  <td width="" class="tdwhiteL0"><select name="divisionFlag" class="Checkbox" style="width:240" style="text-align:center;" <%=readOnly%>>
                    <option value="C" <%=rdoDivisionFlag[0]%>><%=messageService.getString("e3ps.message.ket_message", "02401") %><%--자동차사업부--%></option>
                    <option value="E" <%=rdoDivisionFlag[1]%>><%=messageService.getString("e3ps.message.ket_message", "02483") %><%--전자사업부--%></option>
                  </select></td>
                </tr>
                <tr>
                <td width="" class="tdblueL">Project No</td>
                <td colspan="3" class="tdwhiteL0"><table border="0" width="100%" cellspacing="0" cellpadding="0"
                ><tr><td width="205"><input type="hidden" name="projectOid"
                    value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getProjectOid()));}%>"
                ><input type="text" name="projectNo" class="txt_fieldRO" style="width:200" readonly
                    value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getProjectNo()));}%>"></td
                ><td width="20"><a href="<%if(isOwner){%>javascript:popupProject();<%} else {%>#<%}%>" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a></td
                ><td width="30"><a href="<%if(isOwner){%>javascript:clearProject();<%} else {%>#<%}%>" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td
                ><td width=""><input type="text" name="projectName" class="txt_fieldRO" style="width:353" id="textfield3" readOnly value="<%=StringUtil.checkNull(ketMoldChangeOrderVO.getProjectName())%>"></td
                ></tr></table>
                </td>
              </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02121") %><%--연계 ECR 정보--%></td>
                  <td colspan="3" class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr>
                        <td>&nbsp;</td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
                                    ><a href="<%if(isOwner){%>javascript:popupRelEcr();<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
                                    ><a href="<%if(isOwner){%>javascript:deleteDataLine('forms[0]', 'relEcrTable', 'chkSelectRelEcr', 'chkAllRelEcr', 'deleteRelEcrList');<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table>
                    <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                      <tr>
                          <td width="40" class="tdgrayM"
                          ><input name="chkAllRelEcr" type="checkbox" onclick="javascript:checkAll('forms[0]', 'chkSelectRelEcr', 'chkAllRelEcr');"></td>
                               <td width="100" class="tdgrayM">ECR No</td>
                            <td width="176" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
                            <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                            <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                            <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                      </tr>
                    </table>
                    <div style="width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
                    <table width="100%" cellpadding="0" cellspacing="0" id="relEcrTable">
                      <col width='40'><col width='100'><col width=''><col width='100'><col width='90'><col width='100'>
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
                            <td class="tdwhiteM"><input name="chkSelectRelEcr" type="checkbox" value="<%=ketMoldEcoEcrLinkVO.getRelEcrLinkOid()%>"></td>
                          <td class="tdwhiteM"><a href="javascript:viewRelEcr('<%=ketMoldEcoEcrLinkVO.getRelEcrOid()%>');" onfocus="this.blur();"><%=ketMoldEcoEcrLinkVO.getRelEcrId()%></a>
                          <input type='hidden' name='relEcrOid' value='<%=ketMoldEcoEcrLinkVO.getRelEcrOid()%>'>
                          <input type='hidden' name='relEcrLinkOid' value='<%=ketMoldEcoEcrLinkVO.getRelEcrLinkOid()%>'></td>
                          <td class="tdwhiteL" title="<%=ketMoldEcoEcrLinkVO.getRelEcrName()%>"><div class="ellipsis" style="width:165px;"><nobr><%=ketMoldEcoEcrLinkVO.getRelEcrName()%></nobr></div></td>
                          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoEcrLinkVO.getCreateDeptName()%>&nbsp;</td>
                          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoEcrLinkVO.getCreatorName()%>&nbsp;</td>
                          <td class="tdwhiteM0">&nbsp;<%=ketMoldEcoEcrLinkVO.getApproveDate()%>&nbsp;</td>
                        </tr>
                      <%
                          }
                      }
                      %>
                      </table>
                    </div>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02122") %><%--연계 제품ECO 정보--%></td>
                  <td colspan="3" class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr>
                        <td>&nbsp;</td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
                                    ><a href="<%if(isOwner){%>javascript:popupRelProdEco();<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
                                    ><a href="<%if(isOwner){%>javascript:deleteDataLine('forms[0]', 'relProdEcoTable', 'chkSelectRelProdEco', 'chkAllRelProdEco', 'deleteRelProdEcoList');<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table>
                    <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                      <tr>
                          <td width="40" class="tdgrayM"
                          ><input name="chkAllRelProdEco" type="checkbox" onclick="javascript:checkAll('forms[0]', 'chkSelectRelProdEco', 'chkAllRelProdEco');"></td>
                        <td width="100" class="tdgrayM">ECO No</td>
                        <td width="144" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
                        <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                        <td width="75" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                        <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                        <td width="75" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td>
                      </tr>
                    </table>
                    <div style="width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
                    <table width="100%" cellpadding="0" cellspacing="0" id="RelProdEcoTable">
                      <col width='39'><col width='100'><col width='136'><col width='98'><col width='74'><col width='79'><col width='75'>
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
                            <td class="tdwhiteM"><input name="chkSelectRelProdEco" type="checkbox" value="<%=ketMoldEcoProdEcoLinkVO.getRelEcrLinkOid()%>"></td>
                          <td class="tdwhiteM"><a href="javascript:viewRelProdEco('<%=ketMoldEcoProdEcoLinkVO.getRelEcrOid()%>');"><%=ketMoldEcoProdEcoLinkVO.getRelEcrId()%></a>
                          <input type='hidden' name='relProdEcoOid' value='<%=ketMoldEcoProdEcoLinkVO.getRelEcrOid()%>'>
                          <input type='hidden' name='relProdEcoLinkOid' value='<%=ketMoldEcoProdEcoLinkVO.getRelEcrLinkOid()%>'></td>
                          <td class="tdwhiteL"><span title="<%=ketMoldEcoProdEcoLinkVO.getRelEcrName() %>" style="width:136px;overflow:hidden; text-overflow:ellipsis;white-space:nowrap;"><%=ketMoldEcoProdEcoLinkVO.getRelEcrName()%>&nbsp;</span></td>
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
                    </div>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%><span class="red">*</span></td>
                  <td colspan="3" class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr>
                        <td>&nbsp;</td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
                                    ><a href="<%if(isOwner){%>javascript:popupRelPart();<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
                                    ><a href="<%if(isOwner){%>javascript:deleteDataLine('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table>
                      <div id="div_scroll2" style="width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border" >
                      <table width="100%" cellpadding="0" cellspacing="0">
                     <tr class="headerLock2">
                         <td>
                            <table width="100%" cellpadding="0" cellspacing="0" style=table-layout:fixed >
                              <tr>
                                 <td width="40" class="tdgrayM"><input name="chkAllRelPart" type="checkbox" onclick="javascript:checkAll('forms[0]', 'chkSelectRelPart', 'chkAllRelPart');"></td>
                                <td width="85" class="tdgrayM">Die No</td>
                                <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                                <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                <td width="45" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
                                <td width="112" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td>
                              </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    <td>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relPartTable" style=table-layout:fixed >
                    <col width=40><col width=85><col width=''><col width=90><col width=45><col width=100><col width=112>
                    <%
                    if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                        ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeOrderVO.getKetMoldECOPartLinkVOList();
                        size = ketMoldECOPartLinkVOList.size();
                        KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
                        for ( int i = 0 ; i<size; i++ ) {
                            ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO)ketMoldECOPartLinkVOList.get(i);
                    %>
                        <tr height="30" onMouseOver='relPartTable.clickedRowIndex=this.rowIndex'>
                          <td width="" class="tdwhiteM"><input name="chkSelectRelPart" type="checkbox" value="<%=ketMoldECOPartLinkVO.getRelPartLinkOid()%>"></td>
                        <td width="" class="tdwhiteM"><a href="javascript:viewRelPart('<%=ketMoldECOPartLinkVO.getRelPartOid()%>');" onfocus="this.blur();"><%=KETStringUtil.dataSubstring(ketMoldECOPartLinkVO.getRelPartNumber(), 10)%>&nbsp;</a>
                        <input type='hidden' name='relPartOid' value='<%=ketMoldECOPartLinkVO.getRelPartOid()%>'>
                        <input type='hidden' name='relPartNumber' value='<%=ketMoldECOPartLinkVO.getRelPartNumber()%>'>
                        <input type='hidden' name='relPartLinkOid' value='<%=ketMoldECOPartLinkVO.getRelPartLinkOid()%>'>
                        <input type='hidden' name='secureBudgetYn' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYn()%>'></td>
                        <td width="" class="tdwhiteL" title="<%=ketMoldECOPartLinkVO.getRelPartName() %>"><div class="ellipsis" style="width:80px;"><nobr><%=ketMoldECOPartLinkVO.getRelPartName()%>&nbsp;</nobr></div></td>
                        <td width="" class="tdwhiteM" title="<%=EcmSearchHelper.manager.getRelatedPartNo(ketMoldECOPartLinkVO.getRelPartNumber())%>"><div class="ellipsis" style="width:90px;"><nobr><%=EcmSearchHelper.manager.getRelatedPartNo(ketMoldECOPartLinkVO.getRelPartNumber())%>&nbsp;</nobr></div></td>
                        <td width="" class="tdwhiteM"><%=ketMoldECOPartLinkVO.getRelPartRev()%>&nbsp;</td>
                        <td width="" class="tdwhiteM"><input type='text' name='expectCost' class='txt_fieldR' style='width:90px' value="<%=ketMoldECOPartLinkVO.getExpectCost()%>"></td>
                        <td width="" class="tdwhiteL0">
                        <%if( ketMoldECOPartLinkVO.getRelPartNumber().charAt(1) == 'T' ){ %>
                        <table border='0' cellspacing='0' cellpadding='0'>
                          <tr>
                            <td width='45' align='middle'><input type='hidden' name='budgetYnName' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYnName()%>' ></td>
                            <td width='1'></td>
                            <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>
                              <tr>
                                  <td width="30"></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table>
                        <%}else{ %>
                        <table border='0' cellspacing='0' cellpadding='0'>
                          <tr>
                            <td width='45' align='middle'><input type='text' name='budgetYnName' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYnName()%>' class='txt_field' style='width:45' readonly></td>
                            <td width='1'></td>
                            <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>
                              <tr>
                                <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
                                <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='<%if(isOwner){%>javascript:checkBudget();<%} else {%>#<%}%>' class='btn_blue' onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                                <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table>

                        <%} %>
                        </td>
                      </tr>
                    <%
                        }
                    }
                    %>
                    </table>
                    </td>
                    </tr>
                    </div>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td  width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
                  <td  width="" class="tdwhiteL"><table border="0" width="100%" cellspacing="0" cellpadding="0">
                      <tr><td width="55"><select name="vendorFlag" class="Checkbox" style="width:50" style="text-align:center;">
                        <option value="i" <%=cmbVendorFlag[0]%>><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>
                        <option value="o" <%=cmbVendorFlag[1]%>><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option>
                      </select></td>
                      <td width=""><input type="hidden" name="prodVendor"
value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getProdVendor()));}%>"
                        ><input type="text" name="prodVendorName" class="txt_fieldRO" style="width:150" readonly
value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getProdVendorName()));}%>">&nbsp;</td>
                        <td width="20" align="right"><a href="<%if(isOwner){%>javascript:popupVendor();<%} else {%>#<%}%>" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a></td>
                        <td width="30">&nbsp;<a href="<%if(isOwner){%>javascript:clearVendor();<%} else {%>#<%}%>" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </tr></table>
                </td>
                <td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00187") %><%--ECO 담당자--%><span class="red">*</span></td>
                <td width="" class="tdwhiteL0"
                ><input type="hidden" name="oldEcoWorkerId" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoWorkerId()));}%>"
                ><input type="hidden" name="ecoWorkerId" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoWorkerId()));}%>"
                ><input type="text" name="ecoWorkerName" class="txt_fieldRO" style="width:190" id="textfield2"
                    value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getEcoWorkerName()));}%>" readonly>
                    &nbsp;<a href="<%if(isOwner){%>javascript:popupEcoUser();<%} else {%>#<%}%>" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;<a href="<%if(isOwner){%>javascript:clearEcoUser();<%} else {%>#<%}%>" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%><span class="red">*</span></td>
                  <td colspan="3" class="tdwhiteL0"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <col width="152"><col width="152"><col width="152"><col width="152">
                  <tr>
                      <td width="">
                        <input type="checkbox" name="chkChangeReason" id="checkbox3" value="REASON_1" <%=chkChangeReason[0]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "00850") %><%--고객요청--%></td>
                      <td width=""><input type="checkbox" name="chkChangeReason" id="checkbox4" value="REASON_2" <%=chkChangeReason[1]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "03027") %><%--품질문제--%></td>
                      <td width=""><input type="checkbox" name="chkChangeReason" id="checkbox5" value="REASON_3" <%=chkChangeReason[2]%>>
                      <%=messageService.getString("e3ps.message.ket_message", "02601") %><%--제품설계변경--%></td>
                      <td width=""><input type="checkbox" name="chkChangeReason" id="checkbox6" value="REASON_4" <%=chkChangeReason[3]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "01063") %><%--금형보완--%></td>
                    </tr>
                    <tr>
                      <td width=""><input type="checkbox" name="chkChangeReason" id="checkbox7" value="REASON_5" <%=chkChangeReason[4]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "01784") %><%--생산성{0} 향상--%></td>
                      <td width=""><input type="checkbox" name="chkChangeReason" id="checkbox8" value="REASON_6" <%=chkChangeReason[5]%>><%=messageService.getString("e3ps.message.ket_message", "01086") %><%--금형설계 도면정리--%></td>
                      <td width=""><input type="checkbox" name="chkChangeReason" id="checkbox10" value="REASON_8" <%=chkChangeReason[7]%>><%=messageService.getString("e3ps.message.ket_message", "00084") %><%--BOM 변경--%></td>
                      <td width=""><input type="checkbox" name="chkChangeReason" id="checkbox11" value="REASON_9" <%=chkChangeReason[8]%>><%=messageService.getString("e3ps.message.ket_message", "02903") %><%--치수 및 공차 변경--%></td>
                  </tr>
                  <tr>
                  <td colspan="4"><input type="checkbox" name="chkChangeReason" id="checkbox9" value="REASON_7" <%=chkChangeReason[6]%> onclick="javascript:setEtcValueStatus('otherReasonDesc', this.checked);">
                        <%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>&nbsp;
                        <input type="text" name="otherReasonDesc" class="txt_field" style="width:247" <%=readOnly%>
    value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherReasonDesc()));}%>"></td>
                  </tr>
                  </table></td>
                </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01786") %><%--생산성향상유형--%></td>
                  <td colspan="3" class="tdwhiteL0"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <col width="152"><col width="152"><col width="152"><col width="">
                  <tr>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox10" value="INCR_01" <%=chkIncreaseProdType[0]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "01451") %><%--미성형--%></td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox11" value="INCR_02" <%=chkIncreaseProdType[1]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "03027") %><%--품질문제--%></td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox12" value="INCR_03" <%=chkIncreaseProdType[2]%>>
                        BURR</td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox13" value="INCR_04" <%=chkIncreaseProdType[3]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "02608") %><%--제품이송--%></td>
                    </tr>
                    <tr>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox17" value="INCR_05" <%=chkIncreaseProdType[4]%>>
                        WELD</td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox18" value="INCR_06" <%=chkIncreaseProdType[5]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "02390") %><%--자국발생--%></td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox19" value="INCR_07" <%=chkIncreaseProdType[6]%>>
                        GAS</td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox20" value="INCR_08" <%=chkIncreaseProdType[7]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "01986") %><%--스크랩 상승--%></td>
                    </tr>
                    <tr>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox21" value="INCR_09" <%=chkIncreaseProdType[8]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "02623") %><%--제품취출--%></td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox22" value="INCR_10" <%=chkIncreaseProdType[9]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "01605") %><%--부품파손--%></td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox23" value="INCR_11" <%=chkIncreaseProdType[10]%>>
                        <%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%></td>
                      <td width=""><input type="checkbox" name="chkIncreaseProdType" id="checkbox24" value="INCR_12" <%=chkIncreaseProdType[11]%> onclick="javascript:setEtcValueStatus('otherIncreaseProdType', this.checked);">
                        <%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>
                        <input type="text" name="otherIncreaseProdType" class="txt_field" style="width:100" <%=readOnly%>
    value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherIncreaseProdType()));}%>"></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                  <td colspan="3" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="5">&nbsp;</td>
                      <td align="right"><table width="100%" cellpadding="0" cellspacing="0">
                      <tr>
                        <td>&nbsp;</td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isOwner){%>javascript:insertFileLine();<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isOwner){%>javascript:deleteDataLine('forms[0]', 'filetable', 'addFileSelect', 'chkFileAll', 'deleteFileList');<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table>
                    <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                      <tr>
                        <td width="40" class="tdgrayM"><input name="chkFileAll" type="checkbox" onclick="javascript:checkAll('forms[0]', 'addFileSelect', 'chkFileAll');"></td>
                        <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                      </tr>
                    </table>
                    <div style="width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
                    <table width="100%" cellpadding="0" cellspacing="0" id="filetable">
<%
Vector moldEcoAttacheVec = new Vector();
moldEcoAttacheVec = ContentUtil.getSecondaryContents(ketMoldChangeOrderVO.getKetMoldChangeOrder());
size = moldEcoAttacheVec.size();
for ( int j = 0 ; j<size; j++ ) {
    ContentInfo info = (ContentInfo)moldEcoAttacheVec.get(j);
    StringBuffer sb = new StringBuffer();
    sb.append("<a target='download' href='");
    sb.append(info.getDownURL().toString());
    sb.append("'>&nbsp;" + info.getName());
    sb.append("</a>");
    %>
    <tr>
    <td width="40" class="tdwhiteM"><input type="checkbox" name="addFileSelect" value="<%=info.getContentOid()%>"></td>
    <td width="" class="tdwhiteL0"><%=sb.toString()%></td>
    </tr>
    <%
}
%>
                    </table>
                    </div>
                    </td>
                    </tr>
                  </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table></td>
                </tr>
              </table>
