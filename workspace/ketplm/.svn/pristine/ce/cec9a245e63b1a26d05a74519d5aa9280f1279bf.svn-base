<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    
    
    SuggestUtil.bind('CARTYPE', 'carTypeName', 'carTypeCode');
    SuggestUtil.bind('PRODUCTPROJNO', 'project_id', 'project_oid', 'project_name');
    
    
    <%
    if( isOwner && !ecaType.equals("3") && !ecaType.equals("4") )
    {
    %>
      ownerInit();
    <%
    }
    %>

})
</script>
<script type="text/javascript">
<!--

function ownerInit()
{
    var form = document.forms[0];

    deleteAllSelectBox( form.div_flag );
    deleteAllSelectBox( form.effective_date );
    deleteAllSelectBox( form.inv_process );

    if( '<%=ecoHash.get("division_flag").toString()%>' == 'C'  )
    {
      deleteAllSelectBox( form.domestic_yn );
    }

    <%
    Hashtable<String, String> divHash = new Hashtable<String, String>();
    for(int divCnt = 0 ; divCnt < divList.size(); divCnt++ )
    {
      divHash = divList.get(divCnt);
    %>
      addSelectBox( form.div_flag, '<%=divHash.get("name")%>', '<%=divHash.get("code")%>', '<%=ecoHash.get("division_flag").toString()%>');
    <%
    }

    Hashtable<String, String> effDate = new Hashtable<String, String>();
    for( int eCnt=0; eCnt < effDateList.size() ; eCnt++ )
    {
      effDate = effDateList.get( eCnt );
    %>
      addSelectBox( form.effective_date, '<%=effDate.get("name")%>', '<%=effDate.get("code")%>', '<%=ecoHash.get("effective_date_flag").toString()%>');
    <%
    }

    Hashtable<String, String> invProc = new Hashtable<String, String>();
    for( int pCnt=0 ; pCnt < invProcList.size(); pCnt++ )
    {
      invProc = invProcList.get( pCnt );
    %>
      addSelectBox( form.inv_process, '<%=invProc.get("name")%>', '<%=invProc.get("code")%>', '<%=ecoHash.get("inv_clear").toString()%>');
    <%
    }
    %>

    if( '<%=ecoHash.get("division_flag").toString()%>' == 'C'  )
    {
      addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>', '', '<%=ecoHash.get("domestic_yn_code").toString()%>');
      addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00983") %><%--국내--%>', '1000', '<%=ecoHash.get("domestic_yn_code").toString()%>');
      addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00985") %><%--국외--%>', '2000', '<%=ecoHash.get("domestic_yn_code").toString()%>');

      searchCarData( 'maker', '<%=ecoHash.get("domestic_yn_code").toString()%>' , '<%=ecoHash.get("car_maker").toString()%>');
      searchCarData( 'car', '<%=ecoHash.get("car_maker").toString()%>', '<%=ecoHash.get("car_category").toString()%>' );
    }

    var chkChgReason = document.getElementsByName( 'chk_chg_reason' );
    <%
    String strChgReason = ecoHash.get("chg_reason").toString();
    StringTokenizer st = new StringTokenizer( strChgReason, "|" );
    ArrayList<String> chgReasonList = new ArrayList<String>();
    while( st.hasMoreTokens() )
    {
      chgReasonList.add( st.nextToken() );
    }

    for( int rCnt=0; rCnt < chgReasonList.size(); rCnt++ )
    {
    %>
      for( var inx=0; inx < chkChgReason.length ; inx++)
      {
        if( chkChgReason[inx].value == '<%=chgReasonList.get(rCnt)%>' )
        {
          chkChgReason[inx].checked = true;
        }

        if( inx == 5 )
        {
          disable('other_reason', chkChgReason[inx].checked );
        }
      }
    <%
    }
    %>

    var chkCustom = document.getElementsByName( 'chk_cust' );
    <%
    String strChkCustom = ecoHash.get("cust_chk").toString();
    st = new StringTokenizer( strChkCustom, "|" );
    ArrayList<String> customChkList = new ArrayList<String>();
    while( st.hasMoreTokens() )
    {
      customChkList.add( st.nextToken() );
    }

    for( int cCnt=0; cCnt < customChkList.size(); cCnt++ )
    {
    %>
      for( var inx=0; inx < chkCustom.length ; inx++)
      {
        if( chkCustom[inx].value == '<%=customChkList.get(cCnt)%>' )
        {
          chkCustom[inx].checked = true;
        }

        if( inx == 3 )
        {
          disable( 'other_cust_flag', chkCustom[inx].checked );
        }
      }
    <%
    }
    %>

    var chgFact = document.getElementsByName( 'chk_chg_fact' );
    <%
    String strChgFact = ecoHash.get("chg_fact").toString();
    st = new StringTokenizer( strChgFact, "|" );
    ArrayList<String> factList = new ArrayList<String>();
    while( st.hasMoreTokens() )
    {
      factList.add( st.nextToken() );
    }

    for( int fCnt=0; fCnt < factList.size(); fCnt++ )
    {
    %>
      for( var inx=0; inx < chgFact.length ; inx++)
      {
        if( chgFact[inx].value == '<%=factList.get(fCnt)%>' )
        {
          <% isExistMChange = true;  %>
          chgFact[inx].checked = true;
        }
      }
    <%
    }
    %>

    var chChgChk = document.getElementsByName( 'chk_cu' );

    for( var inx=0; inx < chChgChk.length ; inx++)
    {
      if( chChgChk[inx].value == '<%=ecoHash.get("cu_chg_yn").toString()%>' )
      {
        chChgChk[inx].checked = true;
      }
    }
}
//-->
</script>
              <table border="0" cellspacing="0" cellpadding="0" width="740">
                <tr>
                  <td  class="tab_btm2"></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="740">
                <tr>
                      <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
                      <td colspan="3" class="tdwhiteL0"><input type="text" name="eco_name" class="txt_field"  style="width:99%" value='<%=ecoHash.get("eco_name").toString() %>' ></td>
                </tr>
                <tr>
                    <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%><span class="red">*</span></td>
                    <td width="260" class="tdwhiteL">
                     <%
                      Hashtable<String, String> devFlag = new Hashtable<String, String>();
                      for( int devCnt=0; devCnt < devFlagList.size(); devCnt++ )
                      {
                          devFlag = devFlagList.get(devCnt);
                      %>
                          <input name="dev_yn" type="radio" class="Checkbox" value='<%=devFlag.get("code") %>'  id ="dev_yn"  <%if( ecoHash.get("dev_yn").toString().equals(devFlag.get("code"))){ %>checked<%} %>><%=devFlag.get("name") %>
                       <%
                       }
                       %>
                      </td>
                    <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%><span class="red">*</span></td>
                    <td width="260" class="tdwhiteL0">
                        <select name="div_flag" class="fm_jmp" style="width:250"></select>
                      </td>
                </tr>
                <tr>
                        <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00920") %><%--관련 프로젝트--%></td>
                        <td colspan="3" class="tdwhiteL0">
                            <input type='hidden' name='project_oid' value='<%=ecoHash.get("project_oid").toString() %>'>
                              <input type="text" name="project_id" class="txt_fieldRO" style="width:200" value='<%=ecoHash.get("project_no").toString() %>' readonly>
                            &nbsp;<a href="javascript:popupProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                            &nbsp;&nbsp;<a href="#" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;&nbsp;
                            <input type="text" name="project_name" class="txt_fieldRO" style="width:360" value='<%=ecoHash.get("project_name").toString() %>'  readOnly>
                        </td>
                  </tr>
                  <tr>
                          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
                          <td class="tdwhiteL">
                              <select name="domestic_yn" class="fm_jmp" style="width:125" onchange="javascript:searchCarData('maker', this.value);">
                            </select>
                              <select name="car_maker"  id='car_maker' class="fm_jmp" style="width:125" onchange="javascript:searchCarData('car', this.value);" >
                                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
                            </select>
                        </td>
                          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                          <td class="tdwhiteL0">
                            <!-- >select name="car_category" class="fm_jmp" style="width:250">
                                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
                            </select -->
       <input type="text" name="carTypeName" class="txt_field" style="width: 70%"> 
       <input type="hidden" name="carTypeCode">
       <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
       <img src="/plm/portal/images/icon_5.png" border="0"></a> 
       <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
       <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                   
                          </td>
                  </tr>
                <tr>
                          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02121") %><%--연계 ECR 정보--%></td>
                          <td colspan="3" class="tdwhiteM0">
                              <table border="0" cellspacing="0" cellpadding="0" width="620">
                                <tr>
                                      <td class="space5"></td>
                                </tr>
                              </table>
                            <table width="620" border="0" cellspacing="0" cellpadding="0" >
                                  <tr>
                                    <td>&nbsp;</td>
                                    <td align="right">
                                        <table border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEcr();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                          </tr>
                                                        </table>
                                                    </td>
                                                <td width="5">&nbsp;</td>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                                <a href="javascript:deleteDataLine('forms[0]', 'relEcrTable', 'chkSelectRelEcr', 'chkAllRelEcr');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a>
                                                            </td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                               <table border="0" cellspacing="0" cellpadding="0" width="620">
                                  <tr>
                                    <td class="space5"></td>
                                  </tr>
                            </table>
                            <table width="620" cellpadding="0" cellspacing="0" class="table_border">
                                  <tr>
                                      <td width="40" class="tdgrayM"><input name="chkAllRelEcr" type="checkbox" onclick="javascript:allCheck( 'chkSelectRelEcr', this.checked );"></td>
                                    <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00206") %><%--ECR 번호--%></td>
                                    <td width="145" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                                    <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                    <td width="145" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                                </tr>
                            </table>
                            <div style="height=50;width=620;overflow-x:hidden;overflow-y:auto;" class="table_border">
                                <table width="620" cellpadding="0" cellspacing="0" id="relEcrTable">
                                      <col width=37><col width=159><col width=145><col width=129><col width=145>
                                   <%
                                   ArrayList<Hashtable<String, String>> ecrList = (ArrayList)ecoHash.get("ecrList");
                                   Hashtable<String, String> ecrHash = null;
                                   if( ecrList != null && ecrList.size() > 0 )
                                   {
                                       for( int ecrCnt=0; ecrCnt < ecrList.size(); ecrCnt++ )
                                       {
                                           ecrHash = ecrList.get(ecrCnt);
                                   %>
                                  <tr>
                                      <td width="37" class="tdwhiteM"><input name="chkSelectRelEcr" type="checkbox"></td>
                                    <td width="159" class="tdwhiteM"><a href="javascript:viewRelEcr('<%=ecrHash.get("oid") %>');"><%=ecrHash.get("ecr_id") %>
                                    <input type="hidden" name="relEcrOid" value='<%=ecrHash.get("oid") %>' >
                                    <input type="hidden" name="relEcrLinkOid" value='<%=ecrHash.get("link_oid") %>' >
                                    </td>
                                    <td width="145" class="tdwhiteM"><%=ecrHash.get("dept_name") %></td>
                                    <td width="129" class="tdwhiteM"><%=ecrHash.get("creator_name") %></td>
                                    <td width="145" class="tdwhiteM"><%=EcmUtil.getLastApproveDate( (KETProdChangeRequest)KETObjectUtil.getObject(ecrHash.get("oid")) )%>&nbsp;</td>
                                </tr>
                                   <%
                                       }
                                   }
                                   %>
                                </table>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" width="620">
                                  <tr>
                                    <td class="space5"></td>
                                  </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%><span class="red">*</span></td>
                          <td colspan="3" class="tdwhiteM0">
                              <table border="0" cellspacing="0" cellpadding="0" width="620">
                                <tr>
                                      <td class="space5"></td>
                                </tr>
                              </table>
                            <table width="620" border="0" cellspacing="0" cellpadding="0" >
                                  <tr>
                                    <td>&nbsp;</td>
                                    <td align="right">
                                        <table border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupPart();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                          </tr>
                                                      </table>
                                                  </td>
                                                <td width="5">&nbsp;</td>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                          </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="620">
                                  <tr>
                                    <td class="space5"></td>
                                  </tr>
                              </table>
                            <table width="620" cellpadding="0" cellspacing="0" class="table_border">
                                  <tr>
                                      <td width="40" class="tdgrayM"><input name="chkAllRelPart" type="checkbox" onclick="javascript:checkAll('forms[0]', 'chkSelectRelPart', 'chkAllRelPart');"></td>
                                    <td width="125" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                    <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                                    <td width="70" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                    <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
                                    <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%><span class="red">*</span></td>
                                  </tr>
                            </table>
                            <div style="height=57;width=620;overflow-x:hidden;overflow-y:auto;" class="table_border">
                            <table width="620" cellpadding="0" cellspacing="0" id="relPartTable">
                             <%
                             ArrayList<Hashtable<String, String>> partList =  (ArrayList)ecoHash.get("partList");
                             Hashtable<String, String> partHash = null;
                             if( partList != null && partList.size() > 0 )
                             {
                                 for( int partCnt=0; partCnt < partList.size(); partCnt++ )
                                 {
                                     partHash = partList.get(partCnt);
                             %>
                                <tr>
                                      <td width="40" class="tdwhiteM"><input name="chkSelectRelPart" type="checkbox"></td>
                                    <td width="125" class="tdwhiteM"><%=partHash.get("part_no") %></td>
                                    <td width="150" class="tdwhiteM"><%=partHash.get("part_name") %></td>
                                    <td width="70" class="tdwhiteM"><%=partHash.get("part_ver") %></td>
                                    <td width="100" class="tdwhiteM"><input type='text' name='expectCost'  value='<%=partHash.get("expect_cost") %>' class='txt_fieldR' style='width:90'></td>
                                    <td width="" class="tdwhiteM">
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                  <td><%=partHash.get("budget_yn") %></td>
                                                  <td width="5"></td>
                                                  <td>
                                                      <table border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                           </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                  </tr>
                               <%
                                 }
                             }
                               %>
                              </table>
                          </div>
                          <table border="0" cellspacing="0" cellpadding="0" width="620">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                     </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%><span class="red">*</span></td>
                    <td colspan="3" class="tdwhiteL0"><table width="620" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                         <%
                         Hashtable<String, String> chgRs = new Hashtable<String, String>();
                         for( int chgRCnt=0; chgRCnt < 4 ; chgRCnt++)
                         {
                            chgRs = chgReason.get(chgRCnt);
                         %>
                          <td width="155"><input type="checkbox" name="chk_chg_reason" id="checkbox"  value='<%=chgRs.get("code") %>'><%=chgRs.get("name") %></td>
                         <%
                         }
                         %>
                        </tr>
                        <tr>
                        <%
                        for( int chgRCnt=4; chgRCnt < 8; chgRCnt++ )
                        {
                            chgRs = chgReason.get(chgRCnt);
                        %>
                          <td width="150"><input type="checkbox" name="chk_chg_reason" id="checkbox12" value='<%=chgRs.get("code") %>'  ><%=chgRs.get("name") %></td>
                       <%
                        }
                       %>
                        </tr>
                        <tr>
                        <%
                        for( int chgRCnt=8; chgRCnt < chgReason.size(); chgRCnt++ )
                        {
                            chgRs = chgReason.get(chgRCnt);
                        %>
                          <td colspan="4"><input type="checkbox" name="chk_chg_reason" id="checkbox13"  value='<%=chgRs.get("code") %>' onclick="javascript:disable( 'other_reason', this.checked );"><%=chgRs.get("name") %>&nbsp;&nbsp;
                            <input type="text" name="other_reason" class="txt_field" style="width:550" id="textfield2"></td>
                       <%
                        }
                       %>
                        </tr>
                      </table>
                    </td>
                 </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00863") %><%--고객확인 구분--%></td>
                      <td colspan="3" class="tdwhiteL0"><table width="620" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                        <%
                        Hashtable<String, String> custFlag = new Hashtable<String, String>();
                        for( int custCnt=0; custCnt < custChkList.size(); custCnt++ )
                        {
                            custFlag = custChkList.get(custCnt);
                          if( custCnt == custChkList.size() -1 )
                          {
                          %>
                              <td><input type="checkbox" name="chk_cust" id="checkbox15" value='<%=custFlag.get("code") %>'  onclick="javascript:disable( 'chk_cust', this.value );"><%=custFlag.get("name") %>&nbsp;<input type="text" name="other_cust_flag" class="txt_field" style="width:235" id="textfield3">
                          <%
                          }
                          else
                          {
                          %>
                              <td width="155"><input type="checkbox" name="chk_cust" id="checkbox15" value='<%=custFlag.get("code") %>'  onclick="javascript:disable( 'chk_cust', this.value );"><%=custFlag.get("name") %></td>
                          <%
                          }
                          %>
                        <%
                        }
                        %>
                         </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02646") %><%--조정 및--%><br><%=messageService.getString("e3ps.message.ket_message", "01512") %><%--변경사항--%></td>
                      <td colspan="3" class="tdwhiteL0"><table width="620" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                         <%
                         Hashtable<String, String> chgFact = new Hashtable<String, String>();
                         for( int chgFCnt=0; chgFCnt < chgFactList.size() ; chgFCnt++)
                         {
                             chgFact = chgFactList.get(chgFCnt);
                         %>
                          <td width="160"><input type="checkbox" name="chk_chg_fact" id="checkbox"  value='<%=chgFact.get("code") %>'><%=chgFact.get("name") %></td>
                         <%
                         }
                         %>
                        </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00134", "<br>") %><%--CU도면{0} 변경여부--%></td>
                      <td colspan="3" class="tdwhiteL0">
                          <input name="chk_cu" type="radio" class="Checkbox" value="Y">Yes
                          <input name="chk_cu" type="radio" class="Checkbox" value="N"> No
                      </td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02469") %><%--적용시기--%></td>
                      <td class="tdwhiteL"><select name="effective_date" class="fm_jmp" style="width:250">
                      </select></td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02445") %><%--재고처리--%></td>
                      <td class="tdwhiteL0"><select name="inv_process" class="fm_jmp" style="width:250">
                      </select></td>
                    </tr>
                <tr>
                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                  <td colspan="3" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="620">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table>
                    <table width="620" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20">&nbsp;&nbsp;</td>
                      <td align="right"><table width="620" cellpadding="0" cellspacing="0">
                      <tr>
                        <td>&nbsp;</td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:insertFileLine();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="620">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table>
                    <table width="620" cellpadding="0" cellspacing="0" class="table_border">
                      <tr>
                        <td width="40" class="tdgrayM"><input name="chkFileAll" type="checkbox" onclick="javascript:allCheck( 'fileSelect', this.checked);"></td>
                        <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                      </tr>
                    </table>
                    <div style="height=48;width=620;overflow-x:hidden;overflow-y:auto;" class="table_border">
                        <table width="670" cellpadding="0" cellspacing="0" class="table_border" id="fileTable">
                          <col width=40><col width=630>
                          <%
                          ContentInfo info = null;
                          String url = null;
                          if( attachFileList != null )
                          {
                              for( int fileCnt=0; fileCnt < attachFileList.size() ; fileCnt++ )
                             {
                                  info = (ContentInfo)attachFileList.get( fileCnt );
                                  url = info.getDownURL().toString();
                          %>
                            <tr>
                                  <td width="40" class="tdwhiteM"><input name='fileSelect' type='checkbox' class='chkbox'  value='<%=info.getContentOid()%>'></td>
                                   <td width="630" class="tdwhiteM"><a href='<%=url %>'><%=info.getName()%></a></td>
                              </tr>
                          <%
                              }
                          }
                          else
                          {
                          %>
                             <tr>
                                 <td colspan="2" class="tdwhiteM">&nbsp;</td>
                             </tr>
                           <%
                          }
                           %>
                        </table>
                    </div>
                    </td>
                    </tr>
                  </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="620">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table></td>
                </tr>
              </table>
