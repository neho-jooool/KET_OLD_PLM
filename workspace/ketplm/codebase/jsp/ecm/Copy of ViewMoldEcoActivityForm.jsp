<%@page import="org.apache.commons.lang.StringUtils"%>

<%@page import="wt.fc.*"%>
<%@page import="wt.epm.*"%>

<%@page import="e3ps.bom.common.iba.*"%>
<%@page import="e3ps.edm.beans.*"%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
.headerLock4 {
  position: relative;
  top:expression(document.getElementById("div_scroll4").scrollTop);
  z-index:99;
 }

.headerLock5 {
  position: relative;
  top:expression(document.getElementById("div_scroll5").scrollTop);
  z-index:99;
 }

.headerLock6 {
  position: relative;
  top:expression(document.getElementById("div_scroll6").scrollTop);
  z-index:99;
 }
</style>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td valign="top"
                ><table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td class="space5"></td>
                </tr>
                </table>
                <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                      <td width="50" class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%></td>
                     <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><a href="javascript:doEpmDocExcel()"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                      </tr>
                    </table></td>
                  </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <!-- div id="div_scroll4" style="height:180;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border" -->
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relEdmTable" class="table_border" >
                  <col width='40'><col width='130'><col width='200'><col width=''><col width='50'><col width='50'><col width='80'><col width='80'><col width='90'>

                              <tr>
                                <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                                <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
                                <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                                <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                                <td class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
                                <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                                <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                                <!-- td class="tdblueM0" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td -->
                              </tr>
                              <tr>
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!--  변경 전 -->
                                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
                              </tr>
                  <%
                  int docCnt = 0;
                  if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                      ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();
                    if(ketMoldECALinkVOList == null) {
                          size = 0;
                    } else {
                          size = ketMoldECALinkVOList.size();
                      }
                      KETMoldECALinkVO ketMoldECALinkVO = null;
                      String type = "";
                      for(int i=0; i<size; i++) {
                          ketMoldECALinkVO = (KETMoldECALinkVO)ketMoldECALinkVOList.get(i);
                          type = ketMoldECALinkVO.getActivityType();
                          if(!"1".equals(type)) {//도면이외이면 다음 자료 처리
                              continue;
                          }
                          docCnt++;
                          
                          String epmDocumentOidPre = EcmUtil.getEPMDocumentOid(ketMoldECALinkVO.getRelEcaObjectNo(),ketMoldECALinkVO.getRelEcaObjectPreRev());
                          // 도면 유형
                          String value = "";
                          Persistable persistable = CommonUtil.getObject(epmDocumentOidPre);
                          if (persistable instanceof EPMDocument) {
                              value = IBAUtil.getAttrValue((EPMDocument) persistable, EDMHelper.IBA_CAD_CATEGORY);
                          }
                          
                  %>
                  <tr>
                      <td class='tdwhiteM'><%=docCnt%></td>
                      <td class='tdwhiteM' title="<%=value %>"><div class="ellipsis" style="width:110px;"><nobr><%=value %></nobr></div></td>
                      <td class='tdwhiteM' title="<%=ketMoldECALinkVO.getRelEcaObjectNo() %>"><div class="ellipsis" style="width:190px;"><nobr><%=ketMoldECALinkVO.getRelEcaObjectNo()%></nobr></div></td>
                      <td class='tdwhiteL' title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ketMoldECALinkVO.getRelEcaObjectName()) %>"><div class="ellipsis" style="width:290px;"><nobr><%=ketMoldECALinkVO.getRelEcaObjectName()%></nobr></div></td>
                      <td class='tdwhiteM'>&nbsp;<a href="javascript:viewEpmDocPopup('<%=epmDocumentOidPre %>');"><%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>&nbsp;</a></td>
                      <%if( StringUtil.checkNull(ketMoldECALinkVO.getCompleteDate()).length()>0 ){ %>
                      <td class='tdwhiteM'>&nbsp;<a href="javascript:viewEpmDocPopup('<%=EcmUtil.getEPMDocumentOid(ketMoldECALinkVO.getRelEcaObjectNo(),ketMoldECALinkVO.getRelEcaObjectAfterRev()) %>');"><%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%>&nbsp;</a></td>
                      <td class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getChangeType()%>&nbsp;</td>
                      <%}else{ %>
                      <td class='tdwhiteM'>&nbsp;</td>
                      <td class='tdwhiteM'>&nbsp;</td>
                      <%} %>
                      <td class="tdwhiteM" >&nbsp;<%=ketMoldECALinkVO.getWorkerName()%>&nbsp;</td>
                      <%if( StringUtil.checkNull(ketMoldECALinkVO.getCompleteDate()).length()>0 ){%>
                      <td class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getCompleteDate()%>&nbsp;</td>
                      <%}else{%>
                      <td class='tdwhiteM'><font color="#A9A9A9">&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>&nbsp;</font></td>
                      <%} %>
                      <!-- td class='tdwhiteL0'><div class="ellipsis" style="width:160px;"><nobr><%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%></nobr></div></td -->
                    </tr>
                <%
                    }
                }
                %>
                </table>
                <!-- /div -->

                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td class="space5"></td>
                </tr>
                </table>

                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td class="space5"></td>
                </tr>
                </table>

                <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td width="50" class="font_03">BOM</td>
                    <td align="right">&nbsp;</td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <!-- div id="div_scroll5" style="height:180;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border" -->
                <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relBomTable" class="table_border" >
                <col width='40'><col width='50'><col width='*'><col width='50'><col width='50'><col width='55'><col width='55'><col width='50'><col width='50'><col width='80'><col width='90'>
                              
                <%
                KETMoldECALinkVO bomHeaderLink = null;
                String bomHeaderItemNo = "-";
                String headerItemVer = "-";
                if(ketMoldChangeOrderVO.getTotalCount() > 0)
                {
                    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();

                    if( ketMoldECALinkVOList != null && ketMoldECALinkVOList.size() > 0  )
                    {

                        for(int i=0; i< ketMoldECALinkVOList.size(); i++)
                        {
                            bomHeaderLink = (KETMoldECALinkVO)ketMoldECALinkVOList.get(i);
                            if( bomHeaderLink.getActivityType().equals("2") )
                            {
                                bomHeaderItemNo = bomHeaderLink.getRelEcaObjectNo();
                                if( StringUtil.checkNull(bomHeaderLink.getCompleteDate()).length()>0 && StringUtil.checkNull( bomHeaderLink.getRelEcaObjectAfterRev() ).length() > 0 )
                                {
                                    headerItemVer = bomHeaderLink.getRelEcaObjectAfterRev();
                                }
                                else
                                {
                                    headerItemVer = bomHeaderLink.getRelEcaObjectPreRev();
                                }

                
                                break;
                            }
                        }
                    }
                }
                
                %>
                <tr>
                  <%if( !headerItemVer.equals("-") ){ %>
                    <td class="tdblueL02" colspan="11"><%=messageService.getString("e3ps.message.ket_message", "01364") %><%--모 부품번호--%> : <%=bomHeaderItemNo %> / <%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%> : <a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHeaderItemNo,headerItemVer )%>');"><%=headerItemVer %></a> &nbsp;</td>
                  <%}else{ %>
                    <td class="tdblueL02" colspan="11"><%=messageService.getString("e3ps.message.ket_message", "01364") %><%--모 부품번호--%> : <%=bomHeaderItemNo %> / <%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%> : <%=headerItemVer %>&nbsp;</td>
                  <%} %>
                </tr>
                <tr>
                  <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                  <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                  <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02388") %><%--자 부품번호--%></td>
                  <td class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
                  <td class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
                  <td class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "00792") %><%--경도--%></td>
                  <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                  <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                </tr>
                <tr>
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
                </tr>

                <%
                docCnt = 0;
                if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();
                    if(ketMoldECALinkVOList == null) {
                        size = 0;
                    } else {
                        size = ketMoldECALinkVOList.size();
                    }
                    KETMoldECALinkVO ketMoldECALinkVO = null;
                    String type = "";
                    String jobCls = "";
                    for(int i=0; i<size; i++) {
                        ketMoldECALinkVO = (KETMoldECALinkVO)ketMoldECALinkVOList.get(i);
                        type = ketMoldECALinkVO.getActivityType();
                        if(!"2".equals(type) || bomHeaderItemNo.equalsIgnoreCase(ketMoldECALinkVO.getDieNo())) {//부품이외이면 다음 자료 처리
                            continue;
                        }
                        docCnt++;
                %>
	                    <tr>
	                      <td width='' class='tdwhiteM'><%=docCnt%></td>
	                      <td width='' class='tdwhiteM'>&nbsp;<%=StringUtil.checkReplaceStr(ketMoldECALinkVO.getBomChgFlag(),"BOM")%>&nbsp;</td>
	                      <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getDieNo() %>&nbsp;</td>
	                      <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getBeforeQty()%>&nbsp;</td>
	                      <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getAfterQty()%>&nbsp;</td>
	                      <td width='' class='tdwhiteM' title="<%=StringUtils.stripToEmpty(ketMoldECALinkVO.getBeforeMaterial()) %>"><div class="ellipsis" style="width:55;"><nobr>&nbsp;<%=StringUtils.stripToEmpty(ketMoldECALinkVO.getBeforeMaterial()) %>&nbsp;</nobr></div></td>
	                      <td width='' class='tdwhiteM' title="<%=StringUtils.stripToEmpty(ketMoldECALinkVO.getAfterMaterial()) %>"><div class="ellipsis" style="width:55;"><nobr>&nbsp;<%=StringUtils.stripToEmpty(ketMoldECALinkVO.getAfterMaterial()) %>&nbsp;</nobr></div></td>
	                      <td width='' class='tdwhiteM'>&nbsp;<%=StringUtils.stripToEmpty(ketMoldECALinkVO.getBeforeHardness()) %>&nbsp;</td>
	                      <td width='' class='tdwhiteM'>&nbsp;<%=StringUtils.stripToEmpty(ketMoldECALinkVO.getAfterHardness()) %>&nbsp;</td>
	                      <td class="tdwhiteM" >&nbsp;<%=ketMoldECALinkVO.getWorkerName()%>&nbsp;</td>
	                      <%if( StringUtil.checkNull(ketMoldECALinkVO.getCompleteDate()).length()>0 ){%>
	                      <td class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getCompleteDate()%>&nbsp;</td>
	                      <%}else{%>
	                      <td class='tdwhiteM'><font color="#A9A9A9">&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%></font>&nbsp;</td>
	                      <%} %>
	                      <!-- <td width='' class='tdwhiteL0' title="<%=ketMoldECALinkVO.getRelEcaObjectActivityComment() %>"><div class="ellipsis" style="width:130;"><nobr><%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>&nbsp;</nobr></div></td>-->
	                    </tr>
                <%
                    }   // for(int i=0; i<size; i++) {
                }   // if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                %>
              </table>
              </div>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space15"></td>
                    </tr>
                  </table>
                  <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                      <td width="90" class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03017") %><%--표준품 List--%></td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><a href="javascript:doDocExcel()"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                      </tr>
                    </table></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <!-- div id="div_scroll6" style="height:180;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border" -->
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relDocTable" class="table_border" >

                                  <tr>
                                      <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                                      <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                                      <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                                      <td class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                      <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01232") %><%--대상부품/유형--%></td>
                                      <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                                      <td class="tdblueM0" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                                      <!--<td class="tdblueM0" rowspan="2">변경내용</td>-->
                                    </tr>
                                      <tr>
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
                                          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
                                      </tr>
                  <%
                  docCnt = 0;
                  if(ketMoldChangeOrderVO.getTotalCount() > 0) {
                      ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();
                    if(ketMoldECALinkVOList == null) {
                          size = 0;
                    } else {
                          size = ketMoldECALinkVOList.size();
                      }
                      KETMoldECALinkVO ketMoldECALinkVO = null;
                      for(int i=0; i<size; i++) {
                          ketMoldECALinkVO = (KETMoldECALinkVO)ketMoldECALinkVOList.get(i);
                          if(!"3".equals(ketMoldECALinkVO.getActivityType())) {
                              continue;
                          }
                          docCnt++;
                  %>
                  <tr>
                  <td width='' class='tdwhiteM'><%=docCnt%></td>
                  <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectNo()%>&nbsp;</td>
                  <td width='' class='tdwhiteL' title='<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ketMoldECALinkVO.getRelEcaObjectName()) %>'><div class="ellipsis" style="width:145;"><%=ketMoldECALinkVO.getRelEcaObjectName()%>&nbsp;</nobr></div></td>
                  <td width='' class='tdwhiteM'>&nbsp;<a href="javascript:viewRelDoc('<%=EcmUtil.getDocumentOid( ketMoldECALinkVO.getRelEcaObjectNo(),ketMoldECALinkVO.getRelEcaObjectPreRev() ) %>');"><%=ketMoldECALinkVO.getRelEcaObjectPreRev()%></a>&nbsp;</td>
                  <%if(isViewableState || StringUtil.checkNull(ketMoldECALinkVO.getCompleteDate()).length()>0){ %>
                  <td width='' class='tdwhiteM'>&nbsp;<a href="javascript:viewRelDoc('<%=EcmUtil.getDocumentOid( ketMoldECALinkVO.getRelEcaObjectNo(),ketMoldECALinkVO.getRelEcaObjectAfterRev() )  %>');"><%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%></a>&nbsp;</td>
                  <td width='' class='tdwhiteM'>&nbsp;<a href="javascript:popupStdPart('<%=ketMoldECALinkVO.getRelEcaObjectLinkOid() %>', '<%=ketMoldECALinkVO.getRelEcaObjectNo() %>');"><%=ketMoldECALinkVO.getChangeType()%></a>&nbsp;</td>
                  <%}else{ %>
                  <td width='' class='tdwhiteM'>&nbsp;</td>
                  <td width='' class='tdwhiteM' >&nbsp;</td>
                  <%} %>
                  <td class="tdwhiteM" >&nbsp;<%=ketMoldECALinkVO.getWorkerName()%>&nbsp;</td>
                  <%if( StringUtil.checkNull(ketMoldECALinkVO.getCompleteDate()).length()>0 ){%>
                  <td class='tdwhiteM0'>&nbsp;<%=ketMoldECALinkVO.getCompleteDate()%>&nbsp;</td>
                  <%}else{%>
                  <td class='tdwhiteM0'><font color="#A9A9A9">&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>&nbsp;</font></td>
                  <%} %>
                  <!--<td class='tdwhiteL0'>&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>&nbsp;</td>-->
                  </tr>
                  <%
                    }
                }
                %>
                  </table>
                  </div></td>
              </tr>
            </table>
