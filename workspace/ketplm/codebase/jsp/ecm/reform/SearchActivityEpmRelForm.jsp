<%@page import="java.util.*" %>
<%@page import="wt.epm.EPMDocument" %>
<%@page import="wt.htmlutil.HtmlUtil" %>

<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.common.util.*" %>

<%@page import="ext.ket.bom.query.KETBOMQueryBean" %>
<%@page import="ext.ket.part.base.service.PartBaseHelper" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
    
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border" id="listEpmRelTable">
        <tr>
          <td width="40" class="tdblueM"><input type='checkbox' name='chkAllSelectObj' value='listEpmRelTable'></td>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
          <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "04002") %><%--연관 ECO--%></td>
        </tr>

		  <%
		  
		  KETBOMQueryBean ketBOMQueryBean2 = new KETBOMQueryBean();
		  List<WTPart> partList = new ArrayList<WTPart>();
	          
		  // 기본사항에 추가된 제품
	      String[] pNums0 = request.getParameterValues("pNums");
	      int pNums0Length = (pNums0 != null) ? pNums0.length : 0;
	      for(int i=0; i < pNums0Length; i++) {  
	          Hashtable hash = ketBOMQueryBean2.getPartInfo(pNums0[i]);
	          WTPart wtPart = (hash != null && hash.size() > 0) ? (WTPart) hash.get("WTPart") : null;
	          if(wtPart != null) partList.add(wtPart);
	      }
	          
	      // 설변부품/도면에 추가된 부품
	      String[] pNums2 = request.getParameterValues("pNums2");
	      int pNums2Length = (pNums2 != null) ? pNums2.length : 0;
		  for(int i=0; i < pNums2Length; i++) {  
		      Hashtable hash = ketBOMQueryBean2.getPartInfo(pNums2[i]);
		      WTPart wtPart = (hash != null && hash.size() > 0) ? (WTPart) hash.get("WTPart") : null;
		      if(wtPart != null) partList.add(wtPart);
		  }
		    
		  List<EPMDocument> relatedEpmList = PartBaseHelper.service.getRelatedEPMDocument(partList);
		  int relatedEpmListSize = (relatedEpmList != null) ? relatedEpmList.size() : 0;
		  for(int i=0; i < relatedEpmListSize; i++) {
		      EPMDocument epmDocument = (EPMDocument) relatedEpmList.get(i);
		      
		      String epmType = "";
		      if("PLMSYSTEM".equals(epmDocument.getOwnerApplication().toString())) {
	              epmType = "2D";
	          } else {
	              epmType = "3D";
	          }

		      // 연관 ECO
		      String ecoIds = "";
		      ArrayList<wt.change2.WTChangeOrder2> relatedEcoList = EcmUtil.getECOexistEcaEpmDocLink(epmDocument);
		        int relatedEcoListSize = (relatedEcoList != null) ? relatedEcoList.size() : 0;
		        if(relatedEcoListSize > 0) {
		      
		            for(int j=0; j < relatedEcoListSize; j++) {
		                String ecoId = "";
		                wt.change2.WTChangeOrder2 wtChangeOrder2 = relatedEcoList.get(j);
		                if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETProdChangeOrder) {
		                    e3ps.ecm.entity.KETProdChangeOrder eco = (e3ps.ecm.entity.KETProdChangeOrder) wtChangeOrder2;
		                    ecoId = eco.getEcoId();
		                } else if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETMoldChangeOrder) {
		                    e3ps.ecm.entity.KETMoldChangeOrder eco = (e3ps.ecm.entity.KETMoldChangeOrder) wtChangeOrder2;
		                    ecoId = eco.getEcoId();
		                }

		                ecoIds += ecoIds +", ";
		                
		            }   // for(int j=0; j < relatedEcoListSize; j++) {
		            
		            ecoIds = ecoIds.substring(0, ecoIds.lastIndexOf(", "));
		            
		        }   // if(relatedEcoListSize > 0) {

		            
		        //String disabled = (!ecoIds.equals("") || state.equals("작업중")) ? "disabled='disabled'" : "";
		        String disabled = (!ecoIds.equals("")) ? "disabled='disabled'" : "";
		  %>
	        <tr>
	          <td width="40" class="tdwhiteM">
                <input type='checkbox' name='chkSelectObj' value='<%=CommonUtil.getOIDString(epmDocument) %>'>
                <input type='hidden' name='epmBomType' value='1'>
                <input type='hidden' name='codename' value='<%=HtmlUtil.escapeFormattedHTMLContent(epmDocument.getName())%>'>
                <input type='hidden' name='codecode' value='<%=HtmlUtil.escapeFormattedHTMLContent(epmDocument.getNumber())%>'>
                <input type='hidden' name='codedesc' value='<%=VersionUtil.getMajorVersion(epmDocument) %>'>
                <input type='hidden' name='codecls' value='<%=epmType %>'>
                        
                <input type='hidden' name='dieNo' value=''>
                <input type='hidden' name='dieName' value=''>
                <input type='hidden' name='dieCnt' value='0'>
              </td>
	          <td width="150" class="tdwhiteM"><%=epmDocument.getNumber() %></td>
	          <td width="*" class="tdwhiteL"><%=epmDocument.getName() %></td>
	          <td width="50" class="tdwhiteM"><%=VersionUtil.getMajorVersion(epmDocument) %></td>
	          <td width="70" class="tdwhiteM"><%=epmDocument.getCreatorFullName() %></td>
	          <td width="110" class="tdwhiteM"><%=DateUtil.getDateString(epmDocument.getCreateTimestamp(), "d") %></td>
	          <td width="110" class="tdwhiteM"><%=epmDocument.getLifeCycleState().getDisplay() %></td>
	          <td width="110" class="tdwhiteL0" title="<%=ecoIds %>"><div class='ellipsis' style='width:90px;'><nobr><%=ecoIds %></nobr></div></td>
        
	        </tr>
          
          <%
		  }
          %>
          
        </table>
      <!-- /div -->
    
    </td>
  </tr>
</table>