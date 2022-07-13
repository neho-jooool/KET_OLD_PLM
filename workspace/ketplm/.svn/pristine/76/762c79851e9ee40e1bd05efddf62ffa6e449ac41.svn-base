<%@ page import="java.util.Hashtable
              ,java.util.ArrayList
              ,java.util.Vector
              ,java.util.List
              ,java.util.Map
              ,java.util.HashMap
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03258") %><%--후속활동계획--%></td>
      <td align="right">&nbsp;</td>
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
        <col width='50'><col width='200'><col width='100'><col width='100'><col width='100'><col width='100'><col width='*'>
        <tr>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <!-- td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td -->
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
                    
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01931") %><%--수신확인--%></td>
          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
        </tr>
      <!-- /table>
      <div style="height:100;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relActTable">
        <col width='20'><col width='50'><col width='140'><col width='50'><col width='130'><col width='130'><col width='' -->
        
        <%
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
        %>
                <tr onMouseOver='relActTable.clickedRowIndex=this.rowIndex'>
                  <td class='tdwhiteM'><%=messageService.getString("e3ps.message.ket_message", "04120") %><%--활동--%></td>
                  <td class="tdwhiteM"><%=ketMoldECALinkVO.getCustomName() %></td>
                  <td class='tdwhiteM'><%=ketMoldECALinkVO.getWorkerName()%></td>
                  <td class="tdwhiteM"><%=ketMoldECALinkVO.getCompleteRequestDate() %></td>
                  <td class="tdwhiteM"><%=ketMoldECALinkVO.getCompleteDate() %></td> 
                  <td class="tdwhiteM"><%=ketMoldECALinkVO.getReceiveConfirmedDate() %></td> 
                  <td class='tdwhiteL0' title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ketMoldECALinkVO.getActivityTypeDesc()) %>"><div class="ellipsis" style="width:420px;"><nobr><%=ketMoldECALinkVO.getActivityTypeDesc()%>&nbsp;</nobr></div></td>
                </tr>
        <%
            }   // for(int i=0; i<size; i++) {
        }
        %>
        
      </table>
      </div>
    
