<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="ext.ket.bom.query.KETBOMQueryBean"%>

<%
KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
%>

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
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "04700") %><%--부품--%></td>
    <td align="right">&nbsp;</td>
    <td width="20" align="right"><img src="/plm/portal/images/iocn_excel.png" onclick="fnExcelReport()" style="cursor:pointer;"/></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space5"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<!-- div id="div_scroll5" style="height:162;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border"  -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="">
    <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="bomList">
        <tr>
          <!-- td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td -->
          <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="120" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01377") %><%--모 부품번호--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04021") %><%--모 부품명--%></td>
          <td width="40"  rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="120" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02388") %><%--자 부품번호--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04022") %><%--자 부품명--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
          <td width="50" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td>
          <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <!-- td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01236") %><%--대체부품--%></td -->
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td width="100" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <!-- td width="120" rowspan="2" class="tdblueM0">변경내용</td -->
        </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
          <!-- td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td --><!-- 변경 전 -->
          <!-- td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td --><!-- 변경 후 -->
        </tr>
      <!-- /table>
    </td>
  </tr>
  <tr>
      <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="bomList" style=table-layout:fixed  -->
        <%
        Hashtable<String, String> bomHash = null;
        int totalBomCnt = 1;
        int totalBom = 0;
        if( bomList != null && bomList.size() >0 )
        {
            int bomLength = bomList.size();
            totalBom = bomLength;
            for( int bomCnt=0; bomCnt < bomList.size(); bomCnt++ )
            {
                bomHash = bomList.get(bomCnt);
        %>
                <tr>
                  <!-- td width="40" class="tdwhiteM"><%=totalBomCnt++%>&nbsp;</td -->
                  <td class="tdwhiteM"><%=bomHash.get("bom_type") %></td>
                  <td class="tdwhiteM"><%=bomHash.get("parent_item_code") %>&nbsp; </td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("parent_item_name")) %>"><div class="ellipsis" style="width:100%;"><nobr><%=bomHash.get("parent_item_name") %></nobr></div></td>
                  
                  <%if( StringUtil.checkNull( bomHash.get("eca_obj_after_rev") ).length() > 0 ) {%>
                    <td class="tdwhiteM"><a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHash.get("parent_item_code"),bomHash.get("eca_obj_after_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("parent_item_code"),bomHash.get("eca_obj_after_rev")) %></a></td>
                  <%}else{%>
                   <td class="tdwhiteM"><a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHash.get("parent_item_code"), "0" )%>');">0</a></td>
                  <%} %>
                  
                  <td class="tdwhiteM"><%=bomHash.get("child_item_code") %>&nbsp; </td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("child_item_name")) %>"><div class="ellipsis" style="width:100%;"><nobr><%=bomHash.get("child_item_name") %></nobr></div></td>
                  <td class="tdwhiteM"><%=bomHash.get("expectcost") %>&nbsp; </td>
                  <td class="tdwhiteM"><%=bomHash.get("securebudgetyn") %>&nbsp; </td>
                  <td class="tdwhiteM"><%=bomHash.get("before_qty") %>&nbsp; </td>
                  <td class="tdwhiteM"><%=bomHash.get("atfer_qty") %>&nbsp; </td>
                  <!-- td class="tdwhiteM" title="<%=bomHash.get("before_substitute") %>"><div class="ellipsis" style="width:200;"><nobr><%=bomHash.get("before_substitute") %>&nbsp;</nobr></div></td>
                  <td class="tdwhiteM" title="<%=bomHash.get("after_substitute")%>"><div class="ellipsis" style="width:200;"><nobr><%=bomHash.get("after_substitute") %>&nbsp;</nobr></div> </td -->
                  <td class="tdwhiteM"><%=bomHash.get("worker_name") %>&nbsp;</td>
                  <%
                  if( StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() > 0 )
                  {
                  %>
                  <td class="tdwhiteM"><%=StringUtil.checkNull( bomHash.get("eca_complete_date") )%>&nbsp;</td>
                  <%
                  } else {
                  %>
                  <td class="tdwhiteM"><font color="#A9A9A9"><%=StringUtil.checkNull(bomHash.get("plan_date")) %>&nbsp;</font></td>
                  <%
                  }
                  %>
                  <!-- td width="120" class="tdwhiteL0" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_obj_act_comment")) %>"><div class="ellipsis" style="width:120px;"><nobr><%=bomHash.get("eca_obj_act_comment") %>&nbsp;</nobr></div></td -->
                </tr>
        <%
              }
          }
          if( parentItemList != null && parentItemList.size() > 0 )
          {
              int parentItemLength = parentItemList.size();
              totalBom +=parentItemLength;
              for( int itemCnt=0; itemCnt < parentItemList.size(); itemCnt++ )
              {
                  bomHash = parentItemList.get(itemCnt);
        %>
                <tr>
                  <!-- td class="tdwhiteM"><%=totalBomCnt++%>&nbsp; </td -->
                  <td class="tdwhiteM">BOM</td>
                  
                  <%if( StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() == 0 && StringUtil.checkNull( bomHash.get("masschange_yn") ).equals("Y") ){ %>
                  <td class="tdwhiteM"><%=bomHash.get("eca_parent_item_no") %>&nbsp;</td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_parent_item_name")) %>"><div class="ellipsis" style="width:160px;"><nobr><%=bomHash.get("eca_parent_item_name") %></nobr></div></td>
                  <%}else{ %>
                  <td class="tdwhiteM"><%=bomHash.get("eca_obj_no") %>&nbsp; </td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_obj_name")) %>"><div class="ellipsis" style="width:160px;"><nobr><%=bomHash.get("eca_obj_name") %></nobr></div></td>
                  <%} %>

                  <%if( StringUtil.checkNull( bomHash.get("eca_obj_after_rev") ).length() > 0 &&  StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() > 0 ) {%>
                  <td class="tdwhiteM"><a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_after_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_after_rev")) %>&nbsp;</a></td>
                  <%}else{%>
                   <td class="tdwhiteM"><a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_pre_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_pre_rev")) %>&nbsp;</a></td>
                  <%} %>
                  
                  
                  <%if( StringUtil.checkNull( bomHash.get("masschange_yn") ).equals("N") ){ %>
                  <td class="tdwhiteM">&nbsp; </td>
                  <td class="tdwhiteL">&nbsp; </td>
                  <%}else{ %>
                  <td class="tdwhiteM"><%=bomHash.get("eca_obj_no") %>&nbsp;</td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_obj_name")) %>"><div class="ellipsis" style="width:160px;"><nobr><%=bomHash.get("eca_obj_name") %></nobr></div></td>
                  <%} %>
                  <td class="tdwhiteM"><%=bomHash.get("expect_cost") %>&nbsp; </td>
                  <td class="tdwhiteM"><%=bomHash.get("secure_budget_yn") %>&nbsp; </td>
                  <td class="tdwhiteM">&nbsp; </td>
                  <td class="tdwhiteM">&nbsp; </td>
                  <!-- td class="tdwhiteM">&nbsp; </td>
                  <td class="tdwhiteM">&nbsp; </td -->
                  <td class="tdwhiteM"><%=bomHash.get("worker_name") %></td>
                   <%
                   if( StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() > 0 )
                   {
                   %>
                  <td class="tdwhiteM"><%=StringUtil.checkNull( bomHash.get("eca_complete_date") )%>&nbsp;</td>
                  <%
                   }else{
                  %>
                  <td class="tdwhiteM"><font color="#A9A9A9"><%=StringUtil.checkNull(bomHash.get("plan_date")) %>&nbsp;</font></td>
                  <%} %>
                  <!-- td width="120" class="tdwhiteL0" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_obj_act_comment")) %>"><div class="ellipsis" style="width:120px;"><nobr><%=bomHash.get("eca_obj_act_comment") %>&nbsp;</nobr></div></td -->


                  <%-- 디버깅 --%>
                  <!-- td class="tdwhiteL0"><%=bomHash.get("eca_oid") %>&nbsp;</td>
                  <td class="tdwhiteL0"><%=((e3ps.ecm.entity.KETProdChangeActivity)e3ps.common.util.CommonUtil.getObject(bomHash.get("eca_oid"))).getLifeCycleState().toString() %>&nbsp;</td -->


                </tr>
        <%
                 }
          }
          if(totalBom==0)
          {
        %>
           <tr>
               <td colspan=12 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01494") %><%--변경 BOM이 없습니다--%>.</td>
           </tr>
        <%
        }
        %>
      </table>
      
      
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="bomListExcel" style="display: none">
        <tr>
          <!-- td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td -->
          <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="120" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01377") %><%--모 부품번호--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04021") %><%--모 부품명--%></td>
          <td width="40"  rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="120" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02388") %><%--자 부품번호--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04022") %><%--자 부품명--%></td>
          <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <!-- td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01236") %><%--대체부품--%></td -->
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td width="100" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <!-- td width="120" rowspan="2" class="tdblueM0">변경내용</td -->
        </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
          <!-- td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td --><!-- 변경 전 -->
          <!-- td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td --><!-- 변경 후 -->
        </tr>
        <%
        Hashtable<String, String> bomHash_ = null;
        int totalBomCnt_ = 1;
        int totalBom_ = 0;
        if( bomList != null && bomList.size() >0 )
        {
            int bomLength = bomList.size();
            totalBom_ = bomLength;
            for( int bomCnt=0; bomCnt < bomList.size(); bomCnt++ )
            {
                bomHash_ = bomList.get(bomCnt);
        %>
                <tr>
                  <!-- td width="40" class="tdwhiteM"><%=totalBomCnt_++%>&nbsp;</td -->
                  <td class="tdwhiteM"><%=bomHash_.get("bom_type") %></td>
                  <td class="tdwhiteM"><%=bomHash_.get("parent_item_code") %>&nbsp; </td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash_.get("parent_item_name")) %>"><div class="ellipsis" style="width:180px;"><nobr><%=bomHash_.get("parent_item_name") %></nobr></div></td>
                  
                  <%if( StringUtil.checkNull( bomHash_.get("eca_obj_after_rev") ).length() > 0 ) {%>
                    <td class="tdwhiteM"><%=ketBOMQueryBean.getNewVersionCode(bomHash_.get("parent_item_code"),bomHash_.get("eca_obj_after_rev")) %></td>
                  <%}else{%>
                   <td class="tdwhiteM">0</td>
                  <%} %>
                  
                  <td class="tdwhiteM"><%=bomHash_.get("child_item_code") %>&nbsp; </td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash_.get("child_item_name")) %>"><div class="ellipsis" style="width:180px;"><nobr><%=bomHash_.get("child_item_name") %></nobr></div></td>
                  <td class="tdwhiteM"><%=bomHash_.get("before_qty") %>&nbsp; </td>
                  <td class="tdwhiteM"><%=bomHash_.get("atfer_qty") %>&nbsp; </td>
                  <!-- td class="tdwhiteM" title="<%=bomHash_.get("before_substitute") %>"><div class="ellipsis" style="width:200;"><nobr><%=bomHash_.get("before_substitute") %>&nbsp;</nobr></div></td>
                  <td class="tdwhiteM" title="<%=bomHash_.get("after_substitute")%>"><div class="ellipsis" style="width:200;"><nobr><%=bomHash_.get("after_substitute") %>&nbsp;</nobr></div> </td -->
                  <td class="tdwhiteM"><%=bomHash_.get("worker_name") %>&nbsp;</td>
                  <%
                  if( StringUtil.checkNull( bomHash_.get("eca_complete_date") ).length() > 0 )
                  {
                  %>
                  <td class="tdwhiteM"><%=StringUtil.checkNull( bomHash_.get("eca_complete_date") )%>&nbsp;</td>
                  <%
                  } else {
                  %>
                  <td class="tdwhiteM"><font color="#A9A9A9"><%=StringUtil.checkNull(bomHash_.get("plan_date")) %>&nbsp;</font></td>
                  <%
                  }
                  %>
                  <!-- td width="120" class="tdwhiteL0" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash_.get("eca_obj_act_comment")) %>"><div class="ellipsis" style="width:120px;"><nobr><%=bomHash_.get("eca_obj_act_comment") %>&nbsp;</nobr></div></td -->
                </tr>
        <%
              }
          }
          if( parentItemList != null && parentItemList.size() > 0 )
          {
              int parentItemLength = parentItemList.size();
              totalBom_ +=parentItemLength;
              for( int itemCnt=0; itemCnt < parentItemList.size(); itemCnt++ )
              {
                  bomHash_ = parentItemList.get(itemCnt);
        %>
                <tr>
                  <td class="tdwhiteM">BOM</td>
                  
                  <%if( StringUtil.checkNull( bomHash_.get("eca_complete_date") ).length() == 0 && StringUtil.checkNull( bomHash_.get("masschange_yn") ).equals("Y") ){ %>
                  <td class="tdwhiteM"><%=bomHash_.get("eca_parent_item_no") %>&nbsp;</td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash_.get("eca_parent_item_name")) %>"><div class="ellipsis" style="width:160px;"><nobr><%=bomHash_.get("eca_parent_item_name") %></nobr></div></td>
                  <%}else{ %>
                  <td class="tdwhiteM"><%=bomHash_.get("eca_obj_no") %>&nbsp; </td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash_.get("eca_obj_name")) %>"><div class="ellipsis" style="width:160px;"><nobr><%=bomHash_.get("eca_obj_name") %></nobr></div></td>
                  <%} %>

                  <%if( StringUtil.checkNull( bomHash_.get("eca_obj_after_rev") ).length() > 0 &&  StringUtil.checkNull( bomHash_.get("eca_complete_date") ).length() > 0 ) {%>
                  <td class="tdwhiteM"><%=ketBOMQueryBean.getNewVersionCode(bomHash_.get("eca_obj_no"),bomHash_.get("eca_obj_after_rev")) %>&nbsp;</td>
                  <%}else{%>
                   <td class="tdwhiteM"><%=ketBOMQueryBean.getNewVersionCode(bomHash_.get("eca_obj_no"),bomHash_.get("eca_obj_pre_rev")) %>&nbsp;</td>
                  <%} %>
                  
                  
                  <%if( StringUtil.checkNull( bomHash_.get("masschange_yn") ).equals("N") ){ %>
                  <td class="tdwhiteM">&nbsp; </td>
                  <td class="tdwhiteL">&nbsp; </td>
                  <%}else{ %>
                  <td class="tdwhiteM"><%=bomHash_.get("eca_obj_no") %>&nbsp;</td>
                  <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash_.get("eca_obj_name")) %>"><div class="ellipsis" style="width:160px;"><nobr><%=bomHash_.get("eca_obj_name") %></nobr></div></td>
                  <%} %>
                  <td class="tdwhiteM">&nbsp; </td>
                  <td class="tdwhiteM">&nbsp; </td>
                  <!-- td class="tdwhiteM">&nbsp; </td>
                  <td class="tdwhiteM">&nbsp; </td -->
                  <td class="tdwhiteM"><%=bomHash_.get("worker_name") %></td>
                   <%
                   if( StringUtil.checkNull( bomHash_.get("eca_complete_date") ).length() > 0 )
                   {
                   %>
                  <td class="tdwhiteM"><%=StringUtil.checkNull( bomHash_.get("eca_complete_date") )%>&nbsp;</td>
                  <%
                   }else{
                  %>
                  <td class="tdwhiteM"><font color="#A9A9A9"><%=StringUtil.checkNull(bomHash_.get("plan_date")) %>&nbsp;</font></td>
                  <%} %>
                </tr>
        <%
                 }
          }
          if(totalBom_==0)
          {
        %>
           <tr>
               <td colspan=12 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01494") %><%--변경 BOM이 없습니다--%>.</td>
           </tr>
        <%
        }
        %>
      </table>
      
    </td>
  </tr>
</table>
<!-- /div -->
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space15"></td>
  </tr>
</table>
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%></td>
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
    <td  class="tab_btm2"></td>
  </tr>
</table>
<!-- div id="div_scroll4" style="height:162;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border" -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="">
    <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="epmDocList">
        <tr>
          <!-- td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td -->
          <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
          <td width="200" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
          <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td width="100" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <!-- td width="*" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td -->
        </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!--  변경 전 -->
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
        </tr>
      <!-- /table>
    </td>
  </tr>
  <tr>
      <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="epmDocList" style=table-layout:fixed  -->
          <%
          Hashtable<String, String> epmDocHash = null;

          if( epmDocList != null && epmDocList.size() > 0 )
          {
              int epmDocLength = epmDocList.size();
              for( int epmDocCnt=0; epmDocCnt < epmDocList.size(); epmDocCnt++ )
              {
                  epmDocHash =  epmDocList.get(epmDocCnt);
          %>
        <tr>
          <!-- td width="40" class="tdwhiteM"><%=epmDocCnt+1 %></td -->
          <td class="tdwhiteM" ><%=epmDocHash.get("CADCategory")%></td>
          <td class="tdwhiteM"><%=epmDocHash.get("eca_obj_no")%></td>
          <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(epmDocHash.get("eca_obj_name")) %>"><div class="ellipsis" style="width:420px;"><nobr><%=epmDocHash.get("eca_obj_name")%></nobr></div></td>
          <td class="tdwhiteM"><a href="javascript:viewEpmDocPopup('<%=EcmUtil.getEPMDocumentOid( epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_pre_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode2(epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_pre_rev")) %></a></td>
          <%if( !isAddableActivity ||  StringUtil.checkNull(epmDocHash.get("complete_date")).length() > 0 ) {%>
          <td class="tdwhiteM"><a href="javascript:viewEpmDocPopup('<%=EcmUtil.getEPMDocumentOid( epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_after_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode2(epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_after_rev")) %>&nbsp;</td>
          <%}else{ %>
          <td class="tdwhiteM">&nbsp;</td>
          <%} %>
          <td class="tdwhiteM"><%=epmDocHash.get("worker_name") %>&nbsp;</td>
          <%
          if( StringUtil.checkNull(epmDocHash.get("complete_date")).length() > 0 )
          {
          %>
          <td class="tdwhiteM"><%=epmDocHash.get("complete_date") %>&nbsp;</td>
          <%
          }else{
          %>
          <td class="tdwhiteM"><font color="#A9A9A9"><%=epmDocHash.get("plan_date") %>&nbsp;</font></td>
          <%
          }
          %>
          <!-- td class="tdwhiteL0"><%=epmDocHash.get("eca_obj_act_comment") %>&nbsp;</td -->
          
          
          <%-- 디버깅 --%>
          <!-- td class="tdwhiteL0"><%=epmDocHash.get("eca_oid") %>&nbsp;</td>
          <td class="tdwhiteL0"><%=((e3ps.ecm.entity.KETProdChangeActivity)e3ps.common.util.CommonUtil.getObject(epmDocHash.get("eca_oid"))).getLifeCycleState().toString() %>&nbsp;</td -->
          
          
        </tr>
        <%
            }
        }
        else
        {
        %>
        <tr>
            <td colspan=8 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01495") %><%--변경 도면이 없습니다--%>.</td>
        </tr>
        <%
        }
        %>
      </table>
    </td>
  </tr>
</table>
<!-- /div -->

