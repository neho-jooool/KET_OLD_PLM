<%@ page import="java.util.Hashtable
              ,java.util.ArrayList
              ,java.util.Vector
              ,java.util.List
              ,java.util.Map
              ,java.util.HashMap
              ,e3ps.common.util.*
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.QueryResult
              ,wt.fc.PersistenceHelper
              ,wt.query.*
              ,wt.content.ContentHelper
              ,wt.content.ContentHolder
              ,wt.content.ContentItem
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<%@page import="e3ps.ecm.entity.*"%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
<!--
//자료 저장
function doSave4Ecn(){
   
  var url = "/plm/servlet/e3ps/MoldEcoChangeServlet";
  var form = document.forms[0];
  form.initTab.value = 3;
  
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
function doComplete4Ecn() {
    if( confirm("<%=messageService.getString("e3ps.message.ket_message", "04410") %><%--정말로 작업완료하시겠습니까? 작업완료 후 MyTodo에서 사라집니다.--%>") )
    { 
		
		disabledAllBtn();
		showProcessing();
		
		var url = "/plm/servlet/e3ps/MoldEcoChangeServlet";
		var form = document.forms[0];
		form.cmd.value = "complete";
		form.target = "download";
		form.action = url;
		form.method = "post";
		form.encoding = 'multipart/form-data';
		form.submit();
		
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
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave4Ecn();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                    <td width="5">&nbsp;</td>
                    <td><table border="0" cellspacing="0" cellpadding="0"> 
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete4Ecn();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                  </tr>
                </table>
            </td>
        </tr>
      </table>
          
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03258") %><%--후속활동계획--%></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space5"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="tab_btm2"></td>
  </tr>
</table>  


      <div id="div_scroll4" class="table_border">
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relActTable">
        <col width='20'><col width='200'><col width='80'><col width='100'><col width='180'><col width='*'>
        <tr>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <!-- td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td -->
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
                    
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
        </tr>
      <!-- /table>
      <div style="height:100;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relActTable">
        <col width='20'><col width='50'><col width='140'><col width='50'><col width='130'><col width='130'><col width='' -->
        
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
            for(int i=0; i<size; i++) {
                ketMoldECALinkVO = (KETMoldECALinkVO)ketMoldECALinkVOList.get(i);
                if(!"4".equals(ketMoldECALinkVO.getActivityType())) {
                    continue;
                }
                docCnt++;
                
                
                String relEcaOid = ketMoldECALinkVO.getRelEcaOid();
                KETMoldChangeActivity eca = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);
                if(eca != null) {
                    // To-Do 에서 오는 경우로 수신일을 set 한다.
                    Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
                    if(receiveConfirmedDate == null) {
                        eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
                        
                        PersistenceHelper.manager.save(eca);
                        PersistenceHelper.manager.refresh(eca);
                    }
                
                }
                
                
        %>
                <tr onMouseOver='relActTable.clickedRowIndex=this.rowIndex'>
                  <td class='tdwhiteM'>
                    <div style="display:none;">
                      <input type='hidden' name='relEcaDocActivityType' value='4'>
                      <input type='hidden' name='relEcaDocLinkOid' value="<%=ketMoldECALinkVO.getRelEcaObjectLinkOid()%>">
                      <input type='hidden' name='moldEcaDocOid' value="<%=ketMoldECALinkVO.getRelEcaOid()%>">
                    
                      <input type='hidden' name='relEcaDocOid' value='<%=ketMoldECALinkVO.getRelEcaObjectOid()%>'>
                      <input type='hidden' name='relEcaDocNo' value='<%=ketMoldECALinkVO.getRelEcaObjectNo()%>'>
                      <input type='hidden' name='relEcaDocPreRev' value='<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>'>
                      <input type='hidden' name='relEcaDocAfterRev' value='<%=ketMoldECALinkVO.getRelEcaObjectAfterRev()%>'>
                      
                      <input type='hidden' name='relEcaDocPlanDate' value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
                      <input type='hidden' name='relEcaDocActivityComment' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'>
                      
                      <input type='hidden' name='activityTypeDesc' value='<%=ketMoldECALinkVO.getActivityTypeDesc()%>'>
                    </div>
                    
                    <!-- a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relActTable', 'chkSelectRelEcn', 'chkAllRelDoc', 'deleteRelDocList');"><img src="/plm/portal/images/b-minus.png"></a -->
                    <div style="display:none;"><input type='checkbox' name='chkSelectRelEcn' value='<%=ketMoldECALinkVO.getRelEcaOid() + "^" + ketMoldECALinkVO.getRelEcaObjectLinkOid()%>'></div>
                  
                          <input type='hidden' name="docActFlag" value="ACT">
                          <%=messageService.getString("e3ps.message.ket_message", "04120") %><%--활동--%>
                  </td>
                  
                  <!-- td class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocOid' value='<%=ketMoldECALinkVO.getRelEcaObjectOid()%>'>
                    <input type='text' name='relEcaDocNo' class='txt_fieldC' style='width:90' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectNo()%>'>
                    <a href="javascript:popupRelAct2('relActTable');"><img src='/plm/portal/images/icon_5.png' border='0'></a>
                    <a href="javascript:clearRelDoc2('relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                  <td class='tdwhiteM'>
                    <input type='text' name='relEcaDocPreRev' class='txt_fieldC' style='width:38px' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>'>
                  </td -->
                  <td class="tdwhiteM">
                    <input type="hidden" name="customName" value="<%=ketMoldECALinkVO.getCustomName() %>">
                    <%=ketMoldECALinkVO.getCustomName() %>  
                  </td>
                      
                  <td class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocWorkerOid' value='<%=ketMoldECALinkVO.getWorkerId()%>'>
                    <input type='hidden' name='relEcaDocWorkerName' value="<%=ketMoldECALinkVO.getWorkerName()%>">
                    <%=ketMoldECALinkVO.getWorkerName()%>
                  </td>
                  
                  <%-- 
                  <td class='tdwhiteM'>
                    <input type='text' name='relEcaDocPlanDate' class='txt_field' value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
                    <!-- a href="#" onclick="javascript:popupCal('relEcaDocPlanDate','relActTable');"><img src='/plm/portal/images/icon_6.png' border='0'></a>
                    <a href="javascript:clearCal('relEcaDocPlanDate','relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a -->
                    <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("relEcaDocPlanDate");' style='cursor: hand;'>
                  </td>
                  --%>
                  <td style="width:110px" class="tdwhiteM">
                    <input type="hidden" id="completeRequestDate<%=docCnt %>" name="completeRequestDate" value="<%=ketMoldECALinkVO.getCompleteRequestDate()%>" >
                    <%=ketMoldECALinkVO.getCompleteRequestDate()%>
                  </td> 
                      
                  <td class='tdwhiteL0'>
                    <input type='text' name='activityBackDesc' class='txt_field'  style='width:100%' value='<%=ketMoldECALinkVO.getActivityBackDesc()%>'>
                  </td>
                </tr>
        <%
            }   // for(int i=0; i<size; i++) {
        }
        %>
        
      </table>
      </div>
    
