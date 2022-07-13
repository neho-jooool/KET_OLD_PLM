<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

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
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03255") %><%--후속변경대상문서--%></td>
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

<script type="text/javascript">
<!--
//유효성 검사
function checkDoc()
{
    <%
    boolean hasNotWorker = false;
    boolean hasNotDoc = false;
    
    int docListSize = docList.size();
    for( int i=0; i < docListSize; i++ )
    {
	    Hashtable<String, String> docHash = docList.get(i);
	    // 담당자
        String relEcaDocWorkerOid = docHash.get("worker_id");
	    if(relEcaDocWorkerOid == null || relEcaDocWorkerOid.equals("")) {
		    hasNotWorker = true;
		    break;
	    }
	    
	    String activity_type = StringUtils.stripToEmpty(docHash.get("activity_type"));
	    if(activity_type.equals("3")) {  // 타입이 '문서'일 경우에만    
		    // KETProjectDocument OID
	        String eca_obj_oid = docHash.get("eca_obj_oid");
	        if(eca_obj_oid == null || eca_obj_oid.equals("")) {
	            hasNotDoc = true;
	            break;
	        }
	    }    
    }
    
    if(hasNotWorker) {
	%>
	    alert( '<%=messageService.getString("e3ps.message.ket_message", "04140") %><%-- ECO를 수정하여 후속활동 담당자를 입력하세요 --%>' );
	    clickTabBtn(3);
        return false;
	<%   
    }
    
    if(hasNotDoc) {
    %>
        //alert( '<%=messageService.getString("e3ps.message.ket_message", "04150") %><%-- ECO를 수정하여 후속활동 문서를 입력하세요 --%>' );

        //clickTabBtn(3);
        //return false;
        
        // HEENEETODO : 도면 또는 BOM 없이도 ECO를 태울 수 있어야 하지 않을까?
        var message = "<%=messageService.getString("e3ps.message.ket_message", "04150") %><%--ECO를 수정하여 후속활동 문서를 입력하세요--%>\n"
                    + "<%=messageService.getString("e3ps.message.ket_message", "04260") %><%--그래도 작업을 계속 진행하시겠습니까?--%>"
                    ;
        if(!confirm(message)) return false;
        else return true;
    <%   
    }
    %>
       
    return true;
}

function viewEcnWeightHistory() {
    var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=ViewEcnWeightHistory&oid=<%=KETObjectUtil.getOidString(prodEco) %>";
    openWindow(url,"","600","500","scrollbars=no,resizable=no,top=200,left=250");
}
//-->
</script>
<!-- div id="div_scroll6" style="height:162;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border"  -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="">
    <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>

          <!-- td width="40"  rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td -->
          <!-- td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01410") %><%--문서구분--%></td --><!-- 문서구분 -->
          <td width="30" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--Type--%></td>
          <td width="110" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
          <!-- td width="90" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01745") %><%--상세구분--%></td -->
          <td width="" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td><!-- 문서명 -->
          <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="100"    rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01931") %><%--수신확인--%></td>
          <td width="120" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td><!-- 내용 -->
        </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
        </tr>
      <!-- /table>
    </td>
  </tr>
  <tr>
      <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" style=table-layout:fixed  -->
        <%
        Hashtable<String, String> docHash = null;

        if( docList != null && docList.size() > 0 )
        {
            int docListLength = docList.size();
            for( int i=0; i< docList.size(); i++ )
            {
                docHash = docList.get(i);
        %>
        <tr>
          <!-- td width="40" class="tdwhiteM"><%=i+1 %></td -->
          <%
          String activity_type = StringUtils.stripToEmpty(docHash.get("activity_type"));
          String docActFlag = (activity_type.equals("") || activity_type.equalsIgnoreCase("3")) ? "DOC" : "ACT";
          String docActFlagDisplayName = "";
          if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04119");
          else docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04120");
          %>
          <td class="tdwhiteM"><%=docActFlagDisplayName %></td>
          <td class="tdwhiteM"><%=docHash.get("activity_type_name") %></td>
          <!-- td class="tdwhiteM"><%=docHash.get("doc_type_desc") %>&nbsp;</td -->
          <%
          if(docHash.get("obj_type").equalsIgnoreCase("DOC_08") && docHash.get("receive_confirmed_date") != null && !docHash.get("receive_confirmed_date").equals("")) {
          %>
          <td class="tdwhiteL">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:viewEcnWeightHistory();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02270") %><%--이력--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>                                
              </tr>
            </table>
          </td>
          <%
          } 
          else {
          %>
          <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(StringUtil.checkReplaceStr(docHash.get("eca_obj_name"),"-")) %>"><div class="ellipsis" style="width:300px;"><nobr><%=StringUtil.checkReplaceStr(docHash.get("eca_obj_name"),"-") %></nobr></div></td>
          <%
          }
          %>
          <td class="tdwhiteM"><a href="javascript:viewRelDoc('<%=EcmUtil.getDocumentOid(docHash.get("eca_obj_no"),docHash.get("eca_obj_pre_rev") )%>');"><%=docHash.get("eca_obj_pre_rev") %>&nbsp;</a></td>
          <%if( StringUtil.checkNull(docHash.get("complete_date")).length() > 0 ){ %>
          <td class="tdwhiteM"><a href="javascript:viewRelDoc('<%=EcmUtil.getDocumentOid(docHash.get("eca_obj_no"),docHash.get("eca_obj_after_rev") )%>');"><%=docHash.get("eca_obj_after_rev") %>&nbsp;</a></td>
          <%}else{ %>
          <td class="tdwhiteM">&nbsp;</td>
          <%} %>
          <td class="tdwhiteM"><%=StringUtils.stripToEmpty(docHash.get("worker_name")) %>&nbsp;</td>
          <td class="tdwhiteM"><%=StringUtils.stripToEmpty(docHash.get("complete_request_date")) %>&nbsp;</td>
          <td class="tdwhiteM"><%=docHash.get("complete_date") %>&nbsp;</td>
          <td class="tdwhiteM"><%=docHash.get("receive_confirmed_date") %>&nbsp;</td>
          <td class="tdwhiteL0" title='<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(docHash.get("activity_type_desc")) %>'><div class="ellipsis" style="width:120px;"><nobr><%=docHash.get("activity_type_desc") %>&nbsp;</nobr></div></td>
        </tr>
        <%
            }
        }
        else
        {
        %>
          <tr>
              <td colspan=10 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01497") %><%--변경 문서가 없습니다--%>.</td>
          </tr>
        <%
        }
        %>
      </table>
    </td>
  </tr>
</table>
<!-- /div -->
