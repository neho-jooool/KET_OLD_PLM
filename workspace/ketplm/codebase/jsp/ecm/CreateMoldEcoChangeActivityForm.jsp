<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="wt.part.WTPart"%>

<%@page import="e3ps.ecm.entity.*"%>
<%@page import="e3ps.common.util.*"%>
<%@page import="ext.ket.part.util.*"%>
<%@page import="ext.ket.part.sap.ErpPartHandler"%>


<%
ErpPartHandler erpPartHandler = new ErpPartHandler();
%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
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
</style>

<script type="text/javascript">
<!--
//자료 저장
function doSave(initTab){
   
  var url = "/plm/servlet/e3ps/MoldEcoChangeServlet";
  var form = document.forms[0];
  form.initTab.value = initTab;
  
  form.cmd.value = "update";
    
  form.isChanged.value = 'N';
  form.target = "download";
  form.action = url;
  form.method = "post";
  form.encoding = 'multipart/form-data';
  
  disabledAllBtn();
  showProcessing();
  form.submit();
  
}

//작업완료
<% /* deprecated */ %>
function doComplete() {
  alert('deprecated, function doComplete in CreateMoldEcoChangeActivityForm.jsp');
  
  <%-- 
  var isNotChangedEpmDoc = true;
  if( document.forms[0].isChanged.value == 'N' )
  {
      if( '<%=ecaType%>' == '1' )
      {
          chgFlagList = document.getElementsByName('relEcaEpmChangeYn');

          for( var cnt=0; cnt < chgFlagList.length; cnt++ )
          {
              if( chgFlagList[cnt].value == 'N' )
              {
                  isNotChangedEpmDoc = false;
              }
          }
      }

      if( !isNotChangedEpmDoc )
      {
          alert('<%=messageService.getString("e3ps.message.ket_message", "01517") %>변경을 하지 않은 도면이 있습니다');
          return;
      }

      <%
      Hashtable<String, String> rtnHash = EcmSearchHelper.manager.getCanCompleteInMoldECO(ketMoldChangeOrderVO.getKetMoldChangeOrder(), ecaType);

      if( rtnHash !=null && !rtnHash.isEmpty() )
      {
          if( rtnHash.get("flag").equals("TRUE") )
          {
      %>
              disabledAllBtn();
              showProcessing();
              
              /* 
              setOwnerObj("true");
              */
              
              var url = "/plm/servlet/e3ps/MoldEcoChangeServlet";
              var form = document.forms[0];
              form.cmd.value = "complete";
              form.target = "download";
              form.action = url;
              form.method = "post";
              form.encoding = 'multipart/form-data';
              form.submit();
      <%
          }else{
      %>
              
              alert('<%=rtnHash.get("msg")%>');
             
              
              if(confirm('<%=rtnHash.get("msg")%>\n그래도 계속하시겠습니까?')) {
                  disabledAllBtn();
                  showProcessing();
                  
                  /* 
                  setOwnerObj("true");
                  */
                  
                  var url = "/plm/servlet/e3ps/MoldEcoChangeServlet";
                  var form = document.forms[0];
                  form.cmd.value = "complete";
                  form.target = "download";
                  form.action = url;
                  form.method = "post";
                  form.encoding = 'multipart/form-data';
                  form.submit();
              }
              
      <%
          }
      }
      %>
  }
  else
  {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02458") %>저장 후 작업완료를 수행하세요');
      return;
  }
  --%>
}

<% /* 작업완료 */ %>
function doComplete2()
{
    if( confirm("<%=messageService.getString("e3ps.message.ket_message", "04410") %><%--정말로 작업완료하시겠습니까? 작업완료 후 MyTodo에서 사라집니다.--%>") )
    { 
    
        document.forms[0].action= '/plm/servlet/e3ps/MoldEcoChangeServlet';
        document.forms[0].cmd.value='complete';
        //document.forms[0].target='_self';
        document.forms[0].target='download';
        disabledAllBtn();
        showProcessing();
        
        document.forms[0].method = "post";
        document.forms[0].encoding = 'multipart/form-data';
        document.forms[0].submit();
        
    }
}

function lfn_feedback_After_Complete() {
  //alert('lfn_feedback_After_Complete');
  try { opener.location.reload(); } catch(e) {}
  window.close();
}

//-->
</script>
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">&nbsp;</td>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave(1);" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                    <td width="5">&nbsp;</td>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete2();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                  </tr>
                </table>
            </td>
        </tr>
      </table>
          
            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td  class="tab_btm2"></td>
            </tr>
            <tr>
              <td height="1">&nbsp;</td>
            </tr>
            </table -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
                              
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td valign="top">
                
                  <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                  <td class="font_03">BOM</td>
                  <%if(isBom){ %>
                  <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        
                        <%
                        if(!isTheFirst) {
                        %>
                        <td>
			                <table border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseBomAll();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04220") %><%--일괄개정--%></a></td>
			                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                </tr>
			                </table>
			            </td>
			            <td width="5">&nbsp;</td>
			            <td>
			                <table border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancelReviseBomAll();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04230") %><%--일괄개정취소--%></a></td>
			                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                </tr>
			                </table>
			            </td>
			            <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isBom){%>javascript:popupRelBom2();<%} else {%>#<%}%>" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01573") %><%--부품 추가--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isBom){%>javascript:deleteDataLineMinus2('forms[0]', 'relBomTable', 'chkSelectRelBom', 'chkAllRelBom', 'deleteRelBomList');<%} else {%>#<%}%>" class="btn_blue" onfocus='this.blur();'>부품 삭제</a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                        <%
                        }
                        %>  
                          
                      </tr>
                    </table>
                  </td>
                    <%}else{ %>
                    <td align="right">&nbsp;</td>
                    <%} %>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td class="space5"></td>
                </tr>
              </table>
              <div id="div_scroll2" style="height:150px;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
              <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relBomTable">
              <col width='40'><col width='180'><col width=''><col width='50'><col width='50'><col width='200'>
              <tr class="headerLock2">
                  <td colspan="7">
                  <table border="0" cellpadding="0" cellspacing="0" width="100%">
                          <col width='40'><col width='180'><col width=''><col width='50'><col width='50'><col width='200'>
                          <tr>
                            <td width='' class='tdblueM' rowspan='2'><input name="chkAllRelBom" type="checkbox" onclick="javascript:checkAll('forms[0]', 'chkSelectRelBom', 'chkAllRelBom', 'deleteRelBomList');"></td>
                            <td width='' class='tdblueM' rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01075") %><%--금형부품번호--%></td>
                            <td width='' class='tdblueM' rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01074") %><%--금형부품명--%></td>
                            <td width='' class='tdblueM' colspan='2'><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                            <!-- td width='' class='tdblueM' rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td -->
                            <td width='' class='tdblueM0' rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
                          </tr>
                          <tr>
                            <td width='' class='tdblueM'><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
                            <td width='' class='tdblueM'><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
                          </tr>
                        </table>
                  </td>
              </tr>
              <tr><td></td></tr>
              <%
              int bomCnt = -1;
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
                      if(!"2".equals(type)) {//부품이 아니면 다음 자료 처리
                          continue;
                      }
                      bomCnt++;
                      
                      
                      String relEcaOid = ketMoldECALinkVO.getRelEcaOid();
                      KETMoldChangeActivity eca = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);
                      

                      // To-Do 에서 오는 경우로 수신일을 set 한다.
                      Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
                      if(receiveConfirmedDate == null) {
                          eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
                          
                          PersistenceHelper.manager.save(eca);
                          PersistenceHelper.manager.refresh(eca);
                      }
                      
                      
                      // 부품이 초도일 경우 KETBomHeader, 아닐 경우 KETEcoHeader에 set 한다.
                      //boolean existErp = erpPartHandler.existErpPart(ketMoldECALinkVO.getRelEcaObjectNo());
                      boolean existErp = false;
                      
                      String before_part_oid = ketMoldECALinkVO.getBeforePartOid();
                      WTPart part = (WTPart) CommonUtil.getObject(before_part_oid);
                      String partState = part.getState().toString();
                      
                      /* 
                      String bomflag = PartSpecGetter.getPartSpec((WTPart) part, PartSpecEnum.SpBOMFlag);
                      if (bomflag != null && bomflag.equalsIgnoreCase("OLD")) {
                          existErp = false;
                      }
                      else {
                          existErp = true;
                      }
                      */
                      
              %>
              <tr height="29" onMouseOver='relBomTable.clickedRowIndex=this.rowIndex'>
              <td width='' class='tdwhiteM'>
                  <input type='hidden' name='relEcaBomActivityType' value='<%=type%>'>
                  <input type='hidden' name='relEcaBomLinkOid' value="<%=ketMoldECALinkVO.getRelEcaObjectLinkOid()%>">
                  <input type='hidden' name='relEcaBomOid' value="<%=ketMoldECALinkVO.getRelEcaObjectOid()%>">
                  <input type='hidden' name='relEcaBomNo' value="<%=ketMoldECALinkVO.getRelEcaObjectNo()%>">
                  <input type='hidden' name='relEcaBomName' value="<%=ketMoldECALinkVO.getRelEcaObjectName()%>">
                  <input type='hidden' name='relEcaBomPreRev' value="<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>">
                  <input type='hidden' name='relEcaBomAfterRev' value="<%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%>">
                  <input type='hidden' name='moldEcaBomOid' value="<%=ketMoldECALinkVO.getRelEcaOid()%>">
                  <input type='hidden' name='beforePartOid' value="<%=ketMoldECALinkVO.getBeforePartOid()%>">
                  <input type='hidden' name='relEcaBomWorkerOid' value="<%=ketMoldECALinkVO.getWorkerId()%>">
                  <input type='hidden' name='relEcaBomChangeYn' value="<%=ketMoldECALinkVO.getRelEcaEpmChangeYn()%>">
                  <input type='hidden' name='relEcaBomReviseCancel' value="N">
                  <input type='hidden' name='relEcaBomReviseYn' value="N">
                  <input type='hidden' name='relEcaBomPlanDate' value="<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>">
                  <input type='hidden' name='relEcaBomActivityComment' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'>
                  
                  <input type='hidden' name='ecoheaderoid' value="<%=ketMoldECALinkVO.getEcoHeaderOid()%>">
                  
                  <%
                  ext.ket.bom.query.KETBOMQueryBean ketBOMQueryBean = new ext.ket.bom.query.KETBOMQueryBean();
                  String afterWTPartOid = ketBOMQueryBean.getPartOid(ketMoldECALinkVO.getRelEcaObjectNo(), ketMoldECALinkVO.getRelEcaObjectAfterRev());
                  %>
                  <input type='hidden' name='afterWTPartOid' value='<%=afterWTPartOid %>'>
                  
                  <input type='checkbox' name='chkSelectRelBom' value="<%if(!"".equals(ketMoldECALinkVO.getRelEcaObjectLinkOid())) {out.print(type + ":" + ketMoldECALinkVO.getRelEcaObjectLinkOid());}%>"></td>
              
              
              <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectNo()%>&nbsp;</td>
              <td width='' class='tdwhiteL' title="<%=ketMoldECALinkVO.getRelEcaObjectName()%>"><div class="ellipsis" style="width:130;"><nobr><%=ketMoldECALinkVO.getRelEcaObjectName()%>&nbsp;</nobr></div></td>
              <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>&nbsp;</td>
              <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%>&nbsp;</td>
              <!-- td width='' class='tdwhiteM'><input type='text' name='relEcaBomActivityComment' class='txt_field'  style='width:140' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>' maxlength="100"></td -->
              <td width='' class='tdwhiteM0' align="left"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <%
                  if(!existErp && !partState.equalsIgnoreCase("APPROVED")) {
                  %>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:changeBom();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00084") %><%--BOM 변경--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                  <%                      
                  } else {
                      if( StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectAfterRev()).equals("")  )
                      {
                  %>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseBom();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                   <%
                      }
                      else
                      {
                   %>
                     <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancelReviseBom();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00691") %><%--개정취소--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                     <td width="5">&nbsp;</td>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:changeBom();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00084") %><%--BOM 변경--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                  <%
                      }
                  }
                  %>
                  </tr>
                </table></td>
            </tr>
            <%
                }
            }
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
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%></td>
                      <%if(!isEpmDoc) {%>
                      <td align="right">&nbsp;</td>
                      <%}else{ %>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                          
                            <%
                            if(!isTheFirst) {
                            %>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isEpmDoc){%>javascript:popupRelEpm2();<%} else {%>#<%}%>" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01259") %><%--도면 추가--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isEpmDoc){%>javascript:deleteDataLineMinus2('forms[0]', 'relEpmTable', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelEpmList');<%} else {%>#<%}%>" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01255") %><%--도면 삭제--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isEpmDoc){%>javascript:reviseEpmDoc();<%} else {%>#<%}%>" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isEpmDoc){%>javascript:cancelRevEpmDoc();<%} else {%>#<%}%>" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00691") %><%--개정취소--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <%
                            }
                            %>  
                              
                            <!--td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isEpmDoc){%>javascript:callCOWorkspace();<%} else {%>#<%}%>" class="btn_blue" onfocus='this.blur();'>C/O Workspace</a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td-->
                          </tr>
                        </table></td>
                        <%} %>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  
                  
                  <div id="div_scroll" style="height:155px;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relEpmTable">
                    <col width='30'><col width='110'><col width='200'><col width='130'><col width='150'><col width='50'><col width='50'><col width='90'><col width='140'>
                    <tr class="headerLock">
                      <td colspan="10">
                          <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <col width='30'><col width='110'><col width='200'><col width='130'><col width='150'><col width='50'><col width='50'><col width='90'><col width='140'>
                            <tr onMouseOver='relEpmTable.clickedRowIndex=this.rowIndex'>
                              <td width="" class="tdblueM" rowspan='2'><input name="chkAllRelEpm" type="checkbox" onclick="javascript:checkAll('forms[0]', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelEpmList');"></td>
                              <td width="" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
                              <td width="" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                              <td width="" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                              <td width="" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
                              <td width="" class="tdblueM" colspan='2'><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                              <td width="" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
                              <td width="" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "02125") %><%--연계일정--%></td>
                              <!-- td width="" class="tdblueM0" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td -->
                            </tr>
                            <tr>
                              <td width="" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
                              <td width="" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
                            </tr>
                          </table>
                      </td>
                    </tr>
                    <tr><td></td></tr>
                  <%
                  int docCnt = -1;
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
                          if(!"1".equals(type)) {//도면이 아니면 다음 자료 처리
                              continue;
                          }
                          docCnt++;
                          
                          
                          String relEcaOid = ketMoldECALinkVO.getRelEcaOid();
                          KETMoldChangeActivity eca = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);
                          

                          // To-Do 에서 오는 경우로 수신일을 set 한다.
                          Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
                          if(receiveConfirmedDate == null) {
                              eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
                              
                              PersistenceHelper.manager.save(eca);
                              PersistenceHelper.manager.refresh(eca);
                          }
                        
                          
                  %>
                  <tr height="29" onMouseOver='relEpmTable.clickedRowIndex=this.rowIndex'>
                  <td width='' class='tdwhiteM'>
                    <input type='hidden' name='relEcaEpmActivityType' value='<%=type%>'>
                    <input type='hidden' name='relEcaEpmLinkOid' value="<%=ketMoldECALinkVO.getRelEcaObjectLinkOid()%>">
                    <input type='hidden' name='relEcaEpmOid' value="<%=ketMoldECALinkVO.getRelEcaObjectOid()%>">
                    <input type='hidden' name='relEcaEpmNo' value="<%=ketMoldECALinkVO.getRelEcaObjectNo()%>">
                    <input type='hidden' name='relEcaEpmPreRev' value="<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>">
                    <input type='hidden' name='relEcaEpmAfterRev' value="<%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%>">
                    <input type='hidden' name='relEcaAfterEpmOid' value="<%=ketMoldECALinkVO.getAfterPartOid()%>">
                    <input type='hidden' name='moldEcaEpmOid' value="<%=ketMoldECALinkVO.getRelEcaOid()%>">
                    <input type='hidden' name='relEcaEpmWorkerOid' value="<%=ketMoldECALinkVO.getWorkerId()%>">
                    <input type='hidden' name='relEcaEpmPlanDate' value="<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>">
                    <input type='hidden' name='relEcaEpmChangeYn' value="<%=ketMoldECALinkVO.getRelEcaEpmChangeYn()%>">
                    <input type='hidden' name='relEcaEpmDocReviseYn' value="N">
                    <input type='hidden' name='relEcaEpmDocCancelRevYn' value="N">
                    <input type='hidden' name='relEcaEpmDocType' value="<%=ketMoldECALinkVO.getRelEcaEpmDocType()%>">
                    <input type='hidden' name='relEcaEpmActivityComment' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'>
                    
                    <input type='checkbox' name='chkSelectRelEpm' value='<%if(!"".equals(ketMoldECALinkVO.getRelEcaObjectLinkOid()) && ketMoldECALinkVO.getRelEcaObjectAfterRev().length() < 1) {out.print(type + ":" + ketMoldECALinkVO.getRelEcaObjectLinkOid());}else{out.print("");}%>'>
                  </td>
                  <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getCadCategory()%>&nbsp;</td>
                  <td width='' class='tdwhiteM' title="<%=ketMoldECALinkVO.getRelEcaObjectNo()%>"><%=ketMoldECALinkVO.getRelEcaObjectNo()%></td>
                  <td width='' class='tdwhiteM' title="<%=ketMoldECALinkVO.getRelEcaObjectName()%>"><div class="ellipsis" style="width:120px;"><nobr><%=ketMoldECALinkVO.getRelEcaObjectName()%></nobr></div></td>
                  <%if("2D".equals(ketMoldECALinkVO.getRelEcaEpmDocType())
                  && StringUtil.parseInt(ketMoldECALinkVO.getRelEcaObjectAfterRev(),0) > StringUtil.parseInt(ketMoldECALinkVO.getRelEcaObjectPreRev(),0)) {%>
                  <td width='' class='tdwhiteM' align='left'><table border='0' cellspacing='0' cellpadding='0'>
                      <tr>
                        <td>
                        
                          <%
                          if(!isTheFirst) {
                          %>
                          <table border='0' cellspacing='0' cellpadding='0'>
                            <tr>
                              <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
                              <%if( ketMoldECALinkVO.getRelEcaEpmChangeYn().equals("N") ){ %>
                              <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc("<%=ketMoldECALinkVO.getAfterPartOid()%>");' class='btn_blue' onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01280") %><%--도면변경--%></a></td>
                              <%}else{ %>
                              <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc("<%=ketMoldECALinkVO.getAfterPartOid()%>");' class='btn_blue' onfocus='this.blur();'><font color="#0000FF"><%=messageService.getString("e3ps.message.ket_message", "01516") %><%--변경완료--%></font></a></td>
                              <%} %>
                              <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
                            </tr>
                          </table>
                          <%
                          }
                          %>
                            
                        </td>
                      </tr>
                    </table></td>
                  <%} else if("3D".equals(ketMoldECALinkVO.getRelEcaEpmDocType())
                  && StringUtil.parseInt(ketMoldECALinkVO.getRelEcaObjectAfterRev(),0) > StringUtil.parseInt(ketMoldECALinkVO.getRelEcaObjectPreRev(),0)) {%>
                  <td width='' class='tdwhiteM' align='left'><table border='0' cellspacing='0' cellpadding='0'>
                      <tr>
                        <td>
                        
                          <%
                          if(!isTheFirst) {
                          %>
                          <table border='0' cellspacing='0' cellpadding='0'>
                            <tr>
                              <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
                              <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:callCOWorkspace("<%=ketMoldECALinkVO.getAfterPartOid()%>");' class='btn_blue' onfocus='this.blur();'>C/O Workspace</a></td>
                              <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
                            </tr>
                          </table>
                          <%
                          }
                          %>
                          
                          </td>
                      </tr>
                    </table></td>
                  <%} else {%>
                  <td width='' class='tdwhiteM' align='left'>&nbsp;</td>
                  <%}%>
                  <td width='' class='tdwhiteM'><%=ketMoldECALinkVO.getRelEcaObjectPreRev()%></td>
                  <td width='' class='tdwhiteM'>&nbsp;<%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%>&nbsp;</td>
                  <td width='' class='tdwhiteM'>
                    <select name='relEcaEpmChangeType' style="width:98%;">
                    
                      <%
                      String changeType = StringUtils.stripToEmpty(ketMoldECALinkVO.getChangeType());
                      if(changeType.equals("") && isTheFirst) {
                      %>
                      <option value='수정'><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>
                      <option value='도면정리'><%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%></option>
                      <option value='가공+수정'><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option>
                      <option value='가공' selected><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>
                      <%
                      } else {
                      %>
                      <option value='수정' <%if("수정".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>
                      <option value='도면정리' <%if("도면정리".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%></option>
                      <option value='가공+수정' <%if("가공+수정".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option>
                      <option value='가공' <%if("가공".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>
                      <%
                      }
                      %>
                      
                    </select>
                  </td>
                  <td width='' class='tdwhiteM'><input type='hidden' name='dieNo' value='<%=ketMoldECALinkVO.getDieNo()%>'
                      ><input type='hidden' name='oldMoldChangePlanOid' value='<%=ketMoldECALinkVO.getMoldChangePlanOid()%>'
                         ><input type='hidden' name='newMoldChangePlanOid' value='<%=ketMoldECALinkVO.getMoldChangePlanOid()%>'
                      ><input type='text' name='scheduleId' class='txt_field' style='width:80px' readonly value="<%=ketMoldECALinkVO.getScheduleId()%>"
                      >&nbsp;<a href="javascript:popupRelPlan('relEpmTable');"
                      ><img src='/plm/portal/images/icon_5.png' border='0'
                      ></a>&nbsp;<a href="javascript:clearRelPlan('relEpmTable');"
                      ><img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>
                  <!-- td width='' class='tdwhiteM0'><input type='text' name='relEcaEpmActivityComment' class='txt_field'  style='width:90%' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'></td -->
                </tr>
                <%
                    }
                }
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
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03017") %><%--표준품 List--%></td>
                      <%if(isDoc){ %>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isDoc){%>javascript:addRelDoc();<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01401") %><%--문서 추가--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="<%if(isDoc){%>javascript:deleteDataLineMinus2('forms[0]', 'relDocTable', 'chkSelectRelDoc', 'chkAllRelDoc', 'deleteRelDocList');<%} else {%>#<%}%>" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01396") %><%--문서 삭제--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                        <%}else{ %>
                         <td align="right">&nbsp;</td>
                        <%} %>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                  <div id="div_scroll3" style="height:150px;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relDocTable" style=table-layout:fixed >
                    <col width='30'><col width='310'><col width='50'><col width='50'><col width='145'><col width='145'><col width='250'>
                    <tr class="headerLock3">
                      <td colspan="8">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style=table-layout:fixed >
                          <col width='30'><col width='310'><col width='50'><col width='50'><col width='145'><col width='145'><col width='250'>
                          <tr>
                            <td width="30" class="tdblueM" rowspan='2'><input name="chkAllRelDoc" type="checkbox" onclick="javascript:checkAll('forms[0]', 'chkSelectRelDoc', 'chkAllRelDoc', 'deleteRelDocList');"></td>
                            <td width="310" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                            <td width="100" class="tdblueM" colspan='2'><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                            <td width="145" class="tdblueM" rowspan='2' ><%=messageService.getString("e3ps.message.ket_message", "01232") %><%--대상부품/유형--%></td>
                            <!--<td width="" class="tdblueM" rowspan='2'>유형</td>-->
                            <td width="145" class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td>
                            <td width="250" class="tdblueM0" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
                          </tr>
                          <tr>
                            <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
                            <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr><td></td></tr>
                  <%
                  // int docCnt = -1;
                  docCnt = -1;
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
                          
                          
                          String relEcaOid = ketMoldECALinkVO.getRelEcaOid();
                          KETMoldChangeActivity eca = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);
                          

                          // To-Do 에서 오는 경우로 수신일을 set 한다.
                          Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
                          if(receiveConfirmedDate == null) {
                              eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
                              
                              PersistenceHelper.manager.save(eca);
                              PersistenceHelper.manager.refresh(eca);
                          }
                          
                          
                  %>
                  <tr height="29" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex'>
                  <td width='30' class='tdwhiteM'>
                    <!-- ECN을 위한 값으로 여기서는 필요없지만, 서버에서 저장 로직을 같이 사용하고 있기때문에 없으면 Null Exception이 난다. -->
                    <input type='hidden' name='customName' value=''>
                    <input type='hidden' name='completeRequestDate' value=''>
                    <input type='hidden' name='activityTypeDesc' value=''>
                    <input type='hidden' name='activityBackDesc' value=''>
                  
                    <input type='hidden' name='relEcaDocActivityType' value='3'>
                    <input type='hidden' name='relEcaDocLinkOid' value="<%=ketMoldECALinkVO.getRelEcaObjectLinkOid()%>">
                    <input type='hidden' name='moldEcaDocOid' value="<%=ketMoldECALinkVO.getRelEcaOid()%>">
                    <input type='hidden' name='relEcaDocWorkerOid' value='<%=ketMoldECALinkVO.getWorkerId()%>'>
                    <input type='hidden' name='relEcaDocPlanDate' value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
                    <input type='checkbox' name='chkSelectRelDoc' value='<%=ketMoldECALinkVO.getRelEcaOid() + "^" + ketMoldECALinkVO.getRelEcaObjectLinkOid()%>'>
                  </td>
                  <td width='310' class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocOid' value='<%=ketMoldECALinkVO.getRelEcaObjectOid()%>'>
                    <input type='text' name='relEcaDocNo' class='txt_fieldCRO' style='width:80%' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectNo()%>'>&nbsp;<a href="javascript:popupRelDoc('relDocTable');"><img src='/plm/portal/images/icon_5.png' border='0'></a>&nbsp;<a href="javascript:clearRelDoc('relDocTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                  <td width='50' class='tdwhiteM'><input type='text' name='relEcaDocPreRev' class='txt_fieldCRO' style='width:45px;cursor:hand' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>' onclick='javascript:viewRelDoc();'></td>
                  <td width='50' class='tdwhiteM'><input type='text' name='relEcaDocAfterRev' class='txt_fieldCRO' style='width:45px;' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%>' ></td>
                  <td width='145' class='tdwhiteM' >
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:setStdPartLine();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01231") %><%--대상부품 지정--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table></td>
                       </tr>
                     </table>
                  </td>
                  <!--<td width='' class='tdwhiteM'><input type='text' name='targetPartNumber'
                      class='txt_field' style='width:60' readonly value="<%=StringUtil.checkNull(ketMoldECALinkVO.getDieNo())%>"
                      >&nbsp;<a href="javascript:popupDocTargetPart();"
                      ><img src='/plm/portal/images/icon_5.png' border='0'
                      ></a>&nbsp;<a href="javascript:clearDocTargetPart();"
                      ><img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>-->
                  <!-- <td width='' class='tdwhiteM'><select name='relEcaDocChangeType'
                      ><option value='가공' <%if("가공".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option
                      ><option value='수정' <%if("수정".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option
                      ><option value='가공+수정' <%if("가공+수정".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option
                      ><option value='BOM정리' <%if("BOM정리".equals(ketMoldECALinkVO.getChangeType())) {out.print("selected");}%>>BOM정리</option
                      ></select></td>-->
                  <td width='145' class='tdwhiteM'><input type='text' name='relEcaDocActivityComment' class='txt_field'  style='width:130' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'></td>
                  <td width='250' class='tdwhiteM0' align="left" colspan='2'>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupChangeDoc2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01510") %><%--변경문서검색--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:clearChangeDoc();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01511") %><%--변경문서삭제--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                  </td>
                  <%
                    }
                }
                %>
                  </tr>
                  </table>
                  </div></td>
              </tr>
            </table>
